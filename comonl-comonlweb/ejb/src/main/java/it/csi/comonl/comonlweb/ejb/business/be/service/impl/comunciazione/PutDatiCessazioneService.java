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


import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiCessazione;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiCessazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiCessazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutDatiCessazioneService
 */
public class PutDatiCessazioneService
		extends BaseComunicazioneService<PutDatiCessazioneRequest, PutDatiCessazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private RapportoDad rapportoDad;
	private ControlliDad controlliDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PutDatiCessazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, ControlliDad controlliDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.controlliDad = controlliDad;
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkModel(request.getWrapperComunicazione().getComunicazione(), "comunicazione", true);
		checkModel(request.getWrapperComunicazione().getComunicazione().getRapporto().getCessazionerl(), "rapporto.cessazione", true);
		checkNotNull(request.getWrapperComunicazione().getComunicazione().getRapporto().getDtCessazione(), "rapporto.dtCessazione", true);
    }

	@Override
	protected void execute() {
		// CDU-16
		log.debug("execute----------->", "Entro nel metodo execute");
		Comunicazione comunicazioneClient = wrapperComunicazione.getComunicazione();

		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazioneClient, wrapperComunicazione.getRuolo());
		Comunicazione comunicazioneDaRettificare = null;
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazioneClient.getIdComDComunicPrecedente());
		}
		Validator<Comunicazione> validator = new ValidatorDatiCessazione(comunicazioneClient, controlliDad, checkDatiEssenziali, comunicazioneDaRettificare);
		if (!validator.isOk())
		{
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		
		Comunicazione comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(comunicazioneClient.getId()), "comunicazione");

		TipoSomministrazione tipoSomministrazione = comunicazione.getTipoSomministrazione();
		
		boolean aggiornaRapportoP = tipoSomministrazione == null || 
									(comunicazione.getTipoSomministrazione()!= null && comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE);

		boolean aggiornaRapportoM = comunicazione.getTipoSomministrazione()!= null && comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE;

		if (aggiornaRapportoP) {
			
			Date dtCessazione = comunicazioneClient.getRapporto().getDtCessazione();
			Cessazionerl cessazionerl = comunicazioneClient.getRapporto().getCessazionerl();
			
			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato RAPPORTO P NON PRESENTE
			rapporto.setDtCessazione(dtCessazione);
			//rapporto.setDtEvento(new Date());
			rapporto.setDtEvento(dtCessazione); //la dtEvento per le cessazioni è la dtCessazione
			rapporto.setCessazionerl(cessazionerl);
			
			rapportoDad.updateRapporto(rapporto);
			
			comunicazione.setDataRiferimento(dtCessazione);
		}
		if (aggiornaRapportoM) {
			
			Date dtCessazione = comunicazioneClient.getMissione().getDtCessazione();
			Cessazionerl cessazionerl = comunicazioneClient.getMissione().getCessazionerl();
			
			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato RAPPORTO MISSIONE NON PRESENTE
			rapporto.setDtCessazione(dtCessazione);
			//rapporto.setDtEvento(new Date());
			rapporto.setDtEvento(dtCessazione); 
			rapporto.setCessazionerl(cessazionerl);
			
			rapportoDad.updateRapporto(rapporto);
			
			comunicazione.setDataRiferimento(rapporto.getDtFineMissione());
		}
		
		// UPD dtRiferimento della comunicazione
		comunicazione = comunicazioneDad.updateComunicazione(comunicazione);
		
		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
