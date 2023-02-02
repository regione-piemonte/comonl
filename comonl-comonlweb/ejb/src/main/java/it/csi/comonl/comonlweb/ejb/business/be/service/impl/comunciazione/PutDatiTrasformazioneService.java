/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;


import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiTrasformazione;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiTrasformazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiTrasformazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutDatiTrasformazioneService
 */
public class PutDatiTrasformazioneService
		extends BaseComunicazioneService<PutDatiTrasformazioneRequest, PutDatiTrasformazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	
	private RapportoDad rapportoDad;
	private ControlliDad controlliDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PutDatiTrasformazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, ControlliDad controlliDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.controlliDad = controlliDad;
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkModel(wrapperComunicazione.getComunicazione(), "comunicazione", true);
		checkModel(wrapperComunicazione.getComunicazione().getRapporto().getTrasformazionerl(), "rapporto.trasformazione", true);
		checkNotNull(wrapperComunicazione.getComunicazione().getRapporto().getDtTrasformazione(), "missione.dtTrasformazione", true);
    }

	@Override
	protected void execute() {
		// CDU-13
		log.debug("execute----------->", "Entro nel metodo execute");
		Comunicazione comunicazioneClient = wrapperComunicazione.getComunicazione();
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazioneClient, wrapperComunicazione.getRuolo());
		Comunicazione comunicazioneDaRettificare = null;
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazioneClient.getIdComDComunicPrecedente());
		}

		ValidatorDatiTrasformazione validator = new ValidatorDatiTrasformazione(comunicazioneClient, controlliDad, checkDatiEssenziali, comunicazioneDaRettificare);
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}

		Comunicazione comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(comunicazioneClient.getId()), "comunicazione");

		TipoSomministrazione tipoSomministrazione = comunicazione.getTipoSomministrazione();

		boolean aggiornaRapportoP = tipoSomministrazione == null ||
									(comunicazione.getTipoSomministrazione()!= null && comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE);

		boolean aggiornaRapportoM = comunicazione.getTipoSomministrazione()!= null && comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE;

		Date dtTrasformazione = null;

		if (aggiornaRapportoP) {
			
			dtTrasformazione = comunicazioneClient.getRapporto().getDtTrasformazione();
			Trasformazionerl trasformazionerl = comunicazioneClient.getRapporto().getTrasformazionerl();
			
			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// TODO definire messaggio appropriato RAPPORTO P NON PRESENTE

			rapporto.setDtTrasformazione(dtTrasformazione);
			rapporto.setDtEvento(dtTrasformazione);
			rapporto.setTrasformazionerl(trasformazionerl);
			
			// se la trasformazione è da DT a DI si pulisce dtFineRapporto presente sul rapporto P
			if (isTrasformazioneTempoIndeterminato(trasformazionerl)) {
				rapporto.setDtFineRapporto(null);
			}
			
			rapportoDad.updateRapporto(rapporto);
		}
		if (aggiornaRapportoM) {
			
			dtTrasformazione = comunicazioneClient.getMissione().getDtTrasformazione();
			Trasformazionerl trasformazionerl = comunicazioneClient.getMissione().getTrasformazionerl();

			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// TODO definire messaggio appropriato RAPPORTO MISSIONE NON PRESENTE
			rapporto.setDtTrasformazione(dtTrasformazione);
			rapporto.setDtEvento(new Date());
			rapporto.setTrasformazionerl(trasformazionerl);
			
			rapportoDad.updateRapporto(rapporto);
			
			// non ho già aggiornato P e la trasformazione è da DT a DI: si pulisce dtFineRapporto presente sul rapporto P.
			if (!aggiornaRapportoP && isTrasformazioneTempoIndeterminato(trasformazionerl)) {
				Rapporto rapportoP = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
				checkBusinessCondition(rapportoP!=null, MsgComonl.COMCOMP0002.getError());// TODO definire messaggio appropriato RAPPORTO P NON PRESENTE
				rapportoP.setDtFineRapporto(null);
				rapportoDad.updateRapporto(rapportoP);
			}
		}
		
		// UPD dtRiferimento della comunicazione
		comunicazione.setDataRiferimento(dtTrasformazione);
		comunicazione = comunicazioneDad.updateComunicazione(comunicazione);

		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
