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

import org.apache.commons.lang.time.DateUtils;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiProroga;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutDatiProrogaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutDatiProrogaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRapportoMissioneService
 */
public class PutDatiProrogaService
		extends BaseComunicazioneService<PutDatiProrogaRequest, PutDatiProrogaResponse> {
	
	private WrapperComunicazione wrapperComunicazioneClient;
	Comunicazione comunicazioneClient;
	private RapportoDad rapportoDad;
	private DatoreDad datoreDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PutDatiProrogaService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, DatoreDad datoreDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.datoreDad = datoreDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazioneClient = request.getWrapperComunicazione();
		checkModel(wrapperComunicazioneClient.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		// CDU-15
		log.debug("execute----------->", "Entro nel metodo execute");
		comunicazioneClient = wrapperComunicazioneClient.getComunicazione();
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazioneClient, wrapperComunicazioneClient.getRuolo());

		Comunicazione comunicazioneDaRettificare = null;
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazioneClient.getIdComDComunicPrecedente());
		}

		Validator<Comunicazione> validator = new ValidatorDatiProroga(comunicazioneClient, checkDatiEssenziali, comunicazioneDaRettificare);
		if (!validator.isOk()) {
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		comunicazioneClient = validator.getControlObject();// oggetto validato e completato per la persistenza
		response.setApiWarnings(validator.getApiWarnings());

		Comunicazione comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(comunicazioneClient.getId()), "comunicazione");

		TipoSomministrazione tipoSomministrazione = comunicazione.getTipoSomministrazione();
		Long idComDComunicPrecedente = comunicazione.getIdComDComunicPrecedente();

		boolean aggiornaRapportoP = tipoSomministrazione == null || (
									comunicazione.getTipoSomministrazione()!= null && (
									comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE));

		boolean aggiornaRapportoM = comunicazione.getTipoSomministrazione()!= null && (
									comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE);

		
		Date dtFineProroga = null;
		if (aggiornaRapportoP) {
			
			dtFineProroga = comunicazioneClient.getRapporto().getDtFineProroga();
			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato RAPPORTO P NON PRESENTE
			rapporto.setDtFineProroga(dtFineProroga);
			//rapporto.setDtEvento(new Date());
			//data inserimento se la data inserimento <= data proroga,  la data fine proroga negli altri casi, per le proroghe
			Date dtInsertComunicazione = comunicazione.getDtInsert();
			if(dtInsertComunicazione != null && dtFineProroga != null) {
				if(dtInsertComunicazione.compareTo(dtFineProroga) <= 0) {
					rapporto.setDtEvento(dtInsertComunicazione);
				}else {
					rapporto.setDtEvento(dtFineProroga);
				}
			}
			// se la data fine Proroga > dtFine Raporto allora si proroga il rapporto
			boolean prorogaRapportoP = rapporto.getDtFineRapporto() == null || dtFineProroga.compareTo(rapporto.getDtFineRapporto())>0;
			
			if (prorogaRapportoP) {
				rapporto.setDtFineRapporto(dtFineProroga);
			}
			
			rapportoDad.updateRapporto(rapporto);
		}
		if (aggiornaRapportoM) {

			Datore aziUtilizzatrice = comunicazioneClient.getMissione().getAziUtilizzatrice();
			datoreDad.updateDatore(aziUtilizzatrice);
			
			dtFineProroga = comunicazioneClient.getMissione().getDtFineProroga();
			Rapporto rapporto = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			checkBusinessCondition(rapporto!=null, MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato RAPPORTO MISSIONE NON PRESENTE
			rapporto.setDtFineProroga(dtFineProroga);
			//rapporto.setDtEvento(new Date());
			Date dtInsertComunicazione = comunicazione.getDtInsert();
			if(dtInsertComunicazione != null && dtFineProroga != null) {
				if(dtInsertComunicazione.compareTo(dtFineProroga) <= 0) {
					rapporto.setDtEvento(dtInsertComunicazione);
				}else {
					rapporto.setDtEvento(dtFineProroga);
				}
			}
			
			rapportoDad.updateRapporto(rapporto);
		}
		
		// AGGIORNA DATA RIFERIMENTO COMUNICAZIONE
		Date dtRiferimento = null;
		if (aggiornaRapportoP) {
			Rapporto rapportoPrecedente = null;
			if(idComDComunicPrecedente!=null) {
				rapportoPrecedente = rapportoDad.getRapportoByTipoRapportoAzienda(idComDComunicPrecedente, ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
			}
			if(rapportoPrecedente!=null && (rapportoPrecedente.getDtFinePeriodoFormativo()!=null || rapportoPrecedente.getDtFineRapporto() != null)) {
				Date date = rapportoPrecedente.getDtFinePeriodoFormativo()!=null ? rapportoPrecedente.getDtFinePeriodoFormativo() : rapportoPrecedente.getDtFineRapporto();
				dtRiferimento = DateUtils.addDays(date, 1);
			}else {
				dtRiferimento = comunicazione.getDtInsert().compareTo(dtFineProroga)<=0 ? comunicazione.getDtInsert() : dtFineProroga;
			}
		}else if (aggiornaRapportoM) {
			Rapporto rapportoPrecedente = null;
			if(idComDComunicPrecedente!=null) {
				rapportoPrecedente = rapportoDad.getRapportoByTipoRapportoAzienda(idComDComunicPrecedente, ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			}
			if(rapportoPrecedente!=null && (rapportoPrecedente.getDtFinePeriodoFormativo()!=null || rapportoPrecedente.getDtFineMissione() != null)) {
				Date date = rapportoPrecedente.getDtFinePeriodoFormativo()!=null ? rapportoPrecedente.getDtFinePeriodoFormativo() : rapportoPrecedente.getDtFineMissione();
				dtRiferimento = DateUtils.addDays(date, 1);
			}else {
				dtRiferimento = comunicazione.getDtInsert().compareTo(dtFineProroga)<=0 ? comunicazione.getDtInsert() : dtFineProroga;
			}
		}
		comunicazione.setDataRiferimento(dtRiferimento);
		comunicazione = comunicazioneDad.updateComunicazione(comunicazione);
		
		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
}
