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
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
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
public class PostRettificaComunicazioneService
		extends BaseComunicazioneService<PostComunicazioneRequest, PostComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private LegaleRapprDad legaleRapprDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoDad rapportoDad;
	private  ControlliDad controlliDad;
	private  DecodificaDad decodificaDad;
	private  RapportoLavoratoreDad rapportoLavoratoreDad;
	private  TutoreDad tutoreDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostRettificaComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,LegaleRapprDad legaleRapprDad,
			RapportoDad rapportoDad, LavoratoreDad lavoratoreDad, ControlliDad controlliDad, DecodificaDad decodificaDad,RapportoLavoratoreDad rapportoLavoratoreDad, TutoreDad tutoreDad) {
		super(configurationHelper, comunicazioneDad);
		this.datoreDad = datoreDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.legaleRapprDad = legaleRapprDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.tutoreDad = tutoreDad;
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

	    checkBusinessCondition(comunicazioneRettificabile, MsgComonl.COMCOME0015.getError(
	    		"tipoComunicazione",comunicazioneDaRettificare.getTipoComunicazione().getDsComTTipoComunicazione(),
	    		"dtRiferimento",Format.getStringDate(comunicazioneDaRettificare.getDataRiferimento()),
	    		"codiceRegionale",comunicazioneDaRettificare.getCodiceComunicazioneReg()));

		boolean checkDatiEssenziali = !wrapperComunicazione.getRuolo().isOperatoreProvinciale() &&  isFuoriTempoPerRettifica(comunicazione);

		Validator<Comunicazione> validator = ValidatorFactory.getValidator(wrapperComunicazione, comunicazioneDad, controlliDad, decodificaDad);
		validator.setCheckDatiEssenziali(checkDatiEssenziali);
		if (checkDatiEssenziali) {
			validator.setDbObject(comunicazioneDaRettificare);
		}
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
				comunicazioneDad, datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,rapportoLavoratoreDad, tutoreDad);
		comunicazione = insertComunicazioneService.insertComunicazione(comunicazione);

		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
