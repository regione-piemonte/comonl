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


import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiRapportoMissione;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRapportoMissioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRapportoMissioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRapportoMissioneService
 */
public class PostRapportoMissioneService
		extends BaseComunicazioneService<PostRapportoMissioneRequest, PostRapportoMissioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione; 
	private RapportoDad rapportoDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private ControlliDad controlliDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PostRapportoMissioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, RapportoLavoratoreDad rapportoLavoratoreDad, ControlliDad controlliDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.controlliDad = controlliDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkModel(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		// CDU-11
		log.debug("execute----------->", "Entro nel metodo execute");
		
		Comunicazione comunicazioneClient = wrapperComunicazione.getComunicazione(); 

		// TODO nella modifica della comunicazione INSERITA la modifica dellal lista dei lavoratori potrebbe comportare la delete della relazione del lavoratore con il rapporto di missione
		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(comunicazioneClient.getId());
		boolean inSomministrazioneConMissione = comunicazione.getTipoSomministrazione()!= null && (
				comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		checkBusinessCondition(inSomministrazioneConMissione, MsgComonl.COMCOMP0003.getError()); // definire un messaggio appropriato, Il salvataggio non è possibile se non siamo in Missione

		Rapporto rapportoP = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P);
		checkBusinessCondition(rapportoP!=null, MsgComonl.COMCOMP0002.getError());// definire messaggio appropriato RAPPORTO P NON PRESENTE
		comunicazioneClient.getMissione().setAziUtilizzatrice(rapportoP.getAziUtilizzatrice()); // ribalta l'idAziendaUtilizzatrice da Rapporto P ( inserito in quadro Rapporto) e pulire il record rapporto P

		// RETTIFICA checkDatiEssenziali
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperComunicazione.getRuolo());
		Comunicazione comunicazioneDaRettificare = null; 
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
		}
		// La validazione verifica la coerenza anche con i dati dell'azienda utilizzatrice che a questo punto del flusso non è sulla missione ma solo sul rapporto P.
		Validator<Comunicazione> validator = new ValidatorDatiRapportoMissione(comunicazioneClient, controlliDad, checkDatiEssenziali, comunicazioneDaRettificare);
		if (!validator.isOk()) {
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setApiWarnings(validator.getApiWarnings());
		
		if (comunicazioneClient.getMissione().getComunicazione()==null)
			comunicazioneClient.getMissione().setComunicazione(new Comunicazione());
		
		comunicazioneClient.getMissione().getComunicazione().setId(comunicazioneClient.getId());
		Rapporto rapportoM = rapportoDad.insertRapporto(comunicazioneClient.getMissione());
		
		// pulizia rapporto P dell'azienda utilizzatrice salvata su rapporto M
		rapportoP.setAziUtilizzatrice(null);
		rapportoDad.updateRapporto(rapportoP);
		
		//RELAZIONE MISSIONE LAVORATORE
		for(Lavoratore lavoratore : comunicazione.getLavoratori()) {
			RapportoLavoratore rapportoLavoratore = new RapportoLavoratore();
			RapportoLavoratorePK idRapportoLavoratore = new RapportoLavoratorePK();
			idRapportoLavoratore.setIdComDLavoratore(lavoratore.getId());
			idRapportoLavoratore.setIdComDRapporto(rapportoM.getId());
			rapportoLavoratore.setId(idRapportoLavoratore);
			TipoRapportoAzienda tipoRapportoAzienda = new TipoRapportoAzienda();
			tipoRapportoAzienda.setId(ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			rapportoLavoratore.setTipoRapportoAzienda(tipoRapportoAzienda);
			TipoLavoratore tipoLavoratore = new TipoLavoratore();
			tipoLavoratore.setId(ComonlConstants.TIPO_LAVORATORE_P);
			rapportoLavoratore.setTipoLavoratore(tipoLavoratore);
			rapportoLavoratoreDad.insertRapportoLavoratore(rapportoLavoratore);			
		}

		// Aggiornamento dataRiferimento comunicazione
		comunicazione.setDataRiferimento(rapportoM.getDtInizioMissione());
		comunicazioneDad.updateComunicazione(comunicazione);
		
		rapportoM.setApiWarnings(response.getApiWarnings());
		response.setRapporto(rapportoM);
	}
}
