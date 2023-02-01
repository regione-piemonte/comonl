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
import java.util.stream.Collectors;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorFactory;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorUtility;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutComunicazioneService
 */
public class PutComunicazioneService
		extends BaseComunicazioneService<PutComunicazioneRequest, PutComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private LegaleRapprDad legaleRapprDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoDad rapportoDad;
	private  TutoreDad tutoreDad;
	private  ControlliDad controlliDad;
	private  DecodificaDad decodificaDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PutComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,LegaleRapprDad legaleRapprDad,
			RapportoDad rapportoDad, LavoratoreDad lavoratoreDad,TutoreDad tutoreDad, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.datoreDad = datoreDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.legaleRapprDad = legaleRapprDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.tutoreDad = tutoreDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkModel(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");
		List<ApiError> apiWarnings = new ArrayList<ApiError>();

		//TODO se si cambia il tipo di rapporto , si passa ad un rapporto che non prevede il tutore BISOGNA Cancellare i dati del tutore e porre a NULL la colonna relativa nella com_d_rapporto

		Comunicazione comunicazione = setDeafultPerRuolo(wrapperComunicazione.getComunicazione(), wrapperComunicazione.getRuolo());
		Validator<Comunicazione> validator = ValidatorFactory.getValidator(wrapperComunicazione, comunicazioneDad, controlliDad, decodificaDad);

		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperComunicazione.getRuolo());
		Comunicazione comunicazioneDaRettificare = null;
		validator.setCheckDatiEssenziali(checkDatiEssenziali);
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			validator.setDbObject(comunicazioneDaRettificare);
		}
		
		TipoProvenienza tipoProvenienza = null;
		if (isComunicazioneAnnulla(comunicazione)) {
			Comunicazione comunicazioneDaAnnullare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			tipoProvenienza = comunicazioneDaAnnullare.getTipoProvenienza();
			validator.setTipoProvenienza(tipoProvenienza);
		}

		// TODO ValidatorFacory per identificare il tipo di invocazione estesa o meno
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}

		apiWarnings.addAll(validator.getApiWarnings());
		comunicazione = validator.getControlObject();// oggetto validato e completato per la persistenza
		response.setApiWarnings(apiWarnings);

		Comunicazione comunicazioneDB = comunicazioneDad.getComunicazioneById(comunicazione.getId());

		boolean inSomministrazioneConMissione = comunicazione.getTipoSomministrazione()!= null && (
				comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || comunicazione.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);

		boolean isAssunzione = comunicazione.getTipoComunicazione()!=null && comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE);

		if(isAssunzione && !inSomministrazioneConMissione) {
			comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
		}
		if(isAssunzione && inSomministrazioneConMissione) {
			Rapporto rapportoM = null;
			if(comunicazione.getIdComDComunicPrecedente()!=null) {
				 rapportoM = rapportoDad.getRapportoByTipoRapportoAzienda(comunicazione.getIdComDComunicPrecedente(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_M);
			}
			if (rapportoM == null) {
				comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
			}
		}
		
		Comunicazione comunicazionePersist = comunicazioneDad.updateComunicazione(comunicazione);
		
		// TODO chiedere: su cdu è scritto: In fase di modifica di una comunicazione in stato “Inserita” i dati del Codice Fiscale non sono modificabili . Questo vuol dire che il datore non è modificabile?
		// DATORE
		Datore datore = datoreDad.updateDatore(comunicazione.getDatoreAttuale());
		
		legaleRapprDad.update(comunicazione.getDatoreAttuale().getLegaleRappr());
		
		//SEDE LEGALE
		if (comunicazione.getDatoreAttuale().getSedeLegale()!=null) {
			comunicazione.getDatoreAttuale().getSedeLegale().setEmail(trim(comunicazione.getDatoreAttuale().getSedeLegale().getEmail()));
		}
		sedeLavoroDad.updateSedeLavoro(comunicazione.getDatoreAttuale().getSedeLegale());
		
		// SEDE OPERATIVA
		if (comunicazione.getDatoreAttuale().getSedeOperativa()!=null) {
			comunicazione.getDatoreAttuale().getSedeOperativa().setEmail(trim(comunicazione.getDatoreAttuale().getSedeOperativa().getEmail()));
		}
		SedeLavoro sedeOperativa = sedeLavoroDad.updateSedeLavoro(comunicazione.getDatoreAttuale().getSedeOperativa());
				
		Rapporto rapporto = comunicazione.getRapporto();
		
		// AZIENDA UTILIZZATRICE (CDU 08)
		if (inSomministrazioneConMissione) {
			
			Datore aziendaUtilizzatrice = null;
			if (comunicazione.getMissione() != null) {
				aziendaUtilizzatrice = comunicazione.getMissione().getAziUtilizzatrice();
			}else {
				aziendaUtilizzatrice = comunicazione.getRapporto().getAziUtilizzatrice();
			}
			
			checkBusinessCondition( aziendaUtilizzatrice != null &&  aziendaUtilizzatrice.getId()!=null , MsgComonl.COMCOME0001.getError());// definire un messagio di errore specifico per il caso?
			
			datoreDad.updateDatore(aziendaUtilizzatrice);
			
			//SEDE LEGALE
			if (aziendaUtilizzatrice.getSedeLegale()!=null) {
				aziendaUtilizzatrice.getSedeLegale().setEmail(trim(aziendaUtilizzatrice.getSedeLegale().getEmail()));
			}
			sedeLavoroDad.updateSedeLavoro(aziendaUtilizzatrice.getSedeLegale());
			
			// SEDE OPERATIVA (se presente)
			if (aziendaUtilizzatrice.getSedeOperativa()!=null) {
				aziendaUtilizzatrice.getSedeOperativa().setEmail(trim(aziendaUtilizzatrice.getSedeOperativa().getEmail()));
				sedeLavoroDad.updateSedeLavoro(aziendaUtilizzatrice.getSedeOperativa());
			}
		}
		
		// RAPPORTO (CDU 10)
		boolean deleteTutore = comunicazioneDB.getRapporto().getTutore() != null && !ValidatorUtility.isTipoContrattoApprendistato(rapporto.getTipoContratti()) && !ValidatorUtility.isTipoContrattoTirocinio(rapporto.getTipoContratti());
		if (deleteTutore) {
			rapporto.setTutore(null);
		}
		
		rapporto.setComunicazione(comunicazionePersist);
		
		if (rapporto.getTutore()!=null && rapporto.getTutore().getId()==null) {
			rapporto.setTutore(null);
		}
		rapporto = rapportoDad.updateRapporto(rapporto);
		if (deleteTutore) {
			List<Rapporto> rapportos = rapportoDad.getRapportiByIdTutore(comunicazioneDB.getRapporto().getTutore().getId());
			if (rapportos!=null && rapportos.size()== 1 ) {
				tutoreDad.deleteTutore(comunicazioneDB.getRapporto().getTutore().getId());
			}
		}
		
		// LAVORATORI (CDU 9)
		List<Long> idTODELETE = comunicazioneDB.getLavoratori().stream().map(Lavoratore::getId).collect(Collectors.toList());
		List<Long> idCLIENT = comunicazione.getLavoratori().stream().filter(a->a.getId()!=null).map(Lavoratore::getId).collect(Collectors.toList());
		if(!idCLIENT.isEmpty())
			idTODELETE.removeAll(idCLIENT);
		for(Long idLavoratore: idTODELETE) {
			lavoratoreDad.deleteLavoratore(idLavoratore);
		}
		for(Lavoratore lavoratore : comunicazione.getLavoratori()) {
			if (lavoratore.getId()==null) {
				Lavoratore lav = lavoratoreDad.insertLavoratore(lavoratore, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_P);
				lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
			}else {
				// I dati del lavoratore potrebbero arrivare modificati ?
				lavoratore.setNome(trim(lavoratore.getNome()));				
				lavoratore.setCognome(trim(lavoratore.getCognome()));
				lavoratore.setCodCapRes(trim(lavoratore.getCodCapRes()));
				lavoratore.setCodCapDom(trim(lavoratore.getCodCapDom()));
				lavoratoreDad.updateLavoratore(lavoratore);
			}
		}
		
		// TODO come cambia il lavoratore CO ?  
		if (comunicazione.getLavoratoreCoObbligato()!=null) {
			Lavoratore lav = lavoratoreDad.insertLavoratore(comunicazione.getLavoratoreCoObbligato(), rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_C);
			lavoratoreDad.insertSedeLavoroLavoratore(lav.getId(), sedeOperativa.getId());
		}
		
		comunicazionePersist.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazionePersist);
	}
}
