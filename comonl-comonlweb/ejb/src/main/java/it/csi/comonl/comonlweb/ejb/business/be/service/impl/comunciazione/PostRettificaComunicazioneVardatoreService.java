/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;


import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorFactory;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneVardatoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.utils.Format;

/**
 * PostRicercaProspetto
 */
public class PostRettificaComunicazioneVardatoreService
		extends BaseComunicazioneService<PostComunicazioneRequest, PostComunicazioneVardatoreResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private Comunicazione comunicazione;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private LegaleRapprDad legaleRapprDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoDad rapportoDad;
	private  ControlliDad controlliDad;
	private  DecodificaDad decodificaDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostRettificaComunicazioneVardatoreService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,LegaleRapprDad legaleRapprDad,
			RapportoDad rapportoDad, LavoratoreDad lavoratoreDad, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.datoreDad = datoreDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.legaleRapprDad = legaleRapprDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkNotNull(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Comunicazione comunicazione = setDeafultPerRuolo(wrapperComunicazione.getComunicazione(), wrapperComunicazione.getRuolo());
		List<ApiError> apiWarnings = new ArrayList<ApiError>();

		Comunicazione comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
		String dsComTStatoComunicazione = comunicazioneDaRettificare.getStatoComunicazione() != null ? comunicazioneDaRettificare.getStatoComunicazione().getDsComTStatoComunicazione() : null;
		boolean comunicazioneRettificabile =
				dsComTStatoComunicazione != null && dsComTStatoComunicazione.equalsIgnoreCase(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA) &&
				isComunicazioneUltima(comunicazioneDaRettificare) &&
				comunicazioneDaRettificare.getCodiceComunicazioneReg() != null && comunicazioneDaRettificare.getCodiceComunicazioneReg().startsWith("13");

		comunicazione.setDataRiferimento(comunicazione.getDtTrasferimentoVarDatori());

	    checkBusinessCondition(comunicazioneRettificabile, MsgComonl.COMCOME0015.getError(
	    		"tipoComunicazione",comunicazioneDaRettificare.getTipoComunicazione().getDsComTTipoComunicazione(),
	    		"dtRiferimento",Format.getStringDate(comunicazioneDaRettificare.getDataRiferimento()),
	    		"codiceRegionale",comunicazioneDaRettificare.getCodiceComunicazioneReg()));

		// data riferimento = data variazione
		//TODO controllo solo su codice regionale
		// il flgCurrentRecord è null
	    Validator<Comunicazione> validator = ValidatorFactory.getValidator(wrapperComunicazione, comunicazioneDad, controlliDad, decodificaDad);
		// TODO ValidatorFacory per identificare il tipo di invocazione estesa o meno
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}
		apiWarnings.addAll(validator.getApiWarnings());
		comunicazione = validator.getControlObject();// oggetto validato e completato per la persistenza
		response.setApiWarnings(apiWarnings);
		
		StatoComunicazione statoComunicazione = new StatoComunicazione();
		statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_RETTIFICATA_ID);
		comunicazioneDaRettificare.setStatoComunicazione(statoComunicazione);
		comunicazioneDaRettificare.setFlgCurrentRecord(null);
		comunicazioneDad.updateComunicazione(comunicazioneDaRettificare);
		
		InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
				comunicazioneDad, datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,null,null);
		comunicazione = insertComunicazioneService.insertComunicazioneSempliceVardatore(comunicazione);

		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
