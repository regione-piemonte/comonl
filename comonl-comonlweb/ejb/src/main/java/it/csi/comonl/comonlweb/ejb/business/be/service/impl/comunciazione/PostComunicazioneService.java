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


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRicercaProspetto
 */
public class PostComunicazioneService
		extends BaseComunicazioneService<PostComunicazioneRequest, PostComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private LegaleRapprDad legaleRapprDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoDad rapportoDad;
	private ControlliDad controlliDad;
	private DecodificaDad decodificaDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private TutoreDad tutoreDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,LegaleRapprDad legaleRapprDad,
			RapportoDad rapportoDad, LavoratoreDad lavoratoreDad, ControlliDad controlliDad, DecodificaDad decodificaDad, RapportoLavoratoreDad rapportoLavoratoreDad,TutoreDad tutoreDad) {
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

		List<ApiError> apiWarnings = new ArrayList<ApiError>();
		Comunicazione comunicazione = setDeafultPerRuolo(wrapperComunicazione.getComunicazione(), wrapperComunicazione.getRuolo());
		
		Long idComunicazionePrec = comunicazione.getIdComDComunicPrecedente();
		
		boolean isAggiornamento = idComunicazionePrec != null;
		boolean isAggiornamentoVd = false;
		Comunicazione comunicazioneDaAggiornare = null;

		if (isAggiornamento) {
			comunicazioneDaAggiornare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			
			String dsComTStatoComunicazione = comunicazioneDaAggiornare.getStatoComunicazione() != null ? comunicazioneDaAggiornare.getStatoComunicazione().getDsComTStatoComunicazione() : null;
			
			boolean comunicazioneAggiornabile =
					dsComTStatoComunicazione != null && dsComTStatoComunicazione.equalsIgnoreCase(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA) &&
					isComunicazioneUltima(comunicazioneDaAggiornare);

		    checkBusinessCondition(comunicazioneAggiornabile, MsgComonl.COMCOME0018.getError());
		    
		    isAggiornamentoVd = ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(comunicazioneDaAggiornare.getTipoTracciato().getId());
		    boolean identificativoRapportoOk = true;
		    if (isAggiornamentoVd && comunicazioneDaAggiornare.getRapLavSedeVD()!=null && !comunicazioneDaAggiornare.getRapLavSedeVD().isEmpty()) {
		    	
		    	// verificare se per l'id_com_d_comunica_precedente = id_comunicazione Vd per lo stesso lavoratore con tipo_tracciato != VD se c'è blocco se non c'è passa.

		    	String cfLavoratore = comunicazione.getLavoratori().get(0).getCodiceFiscale();

		    	RapportiLavoratoriSediInteressateVD rapLavSedeVd =   
		    			comunicazioneDaAggiornare.getRapLavSedeVD().stream().filter(a->a.getLavoratoreVD().getCodiceFiscale().equalsIgnoreCase(cfLavoratore)).findAny().orElse(null);
		    	
		    	identificativoRapportoOk = verificaIdentificativoRapporto(comunicazioneDaAggiornare.getDatoreAttuale(), rapLavSedeVd.getRapportoVD(), rapLavSedeVd.getLavoratoreVD(), comunicazione);
		    	
		    }else {
		    	identificativoRapportoOk = verificaIdentificativoRapporto(comunicazioneDaAggiornare.getDatoreAttuale(), comunicazioneDaAggiornare.getRapporto(), comunicazioneDaAggiornare.getLavoratori().get(0), comunicazione);	
		    }
		    
		    boolean cfCoobligatoChanged = false;
		    if (comunicazioneDaAggiornare.getLavoratoreCoObbligato()!=null) {
		    	cfCoobligatoChanged = 
		    			comunicazione.getLavoratoreCoObbligato() == null || StringUtils.isBlank(comunicazione.getLavoratoreCoObbligato().getCodiceFiscale()) ||
		    			!comunicazioneDaAggiornare.getLavoratoreCoObbligato().getCodiceFiscale().equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getCodiceFiscale());
		    }
		    
		    checkBusinessCondition(identificativoRapportoOk && !cfCoobligatoChanged, MsgComonl.COMCOME0020.getError());
		}

		// TODO ValidatorFacory per identificare il tipo di invocazione estesa o meno
		Validator<Comunicazione> validator = ValidatorFactory.getValidator(wrapperComunicazione, comunicazioneDad, controlliDad, decodificaDad);
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}

		apiWarnings.addAll(validator.getApiWarnings());
		comunicazione = validator.getControlObject();// oggetto validato e completato per la persistenza
		response.setApiWarnings(apiWarnings);

		// TODO getIdRapportoLavoro == null verificare che non ci sia una comunicazione già presente (in stato VALIDATA)  che potrebbe essere collegata alla nuova che si vuole inserire
		// chiedere a Luisa come comportarsi nel caso venga trovata . Si blocca il salvataggio ?
		// occhio che se questo servizio è invocato per l'inserimento massivo delle comunicazione questo NON deve essere un controllo bloccante, per l'online invece si .

		InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
				comunicazioneDad, datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad, rapportoLavoratoreDad, tutoreDad);

		
		if(isAggiornamento) {
			if (isAggiornamentoVd) {
				comunicazione.setIdRapportoLavoro(null);
			}else {
				comunicazioneDaAggiornare.setFlgCurrentRecord(null);
				comunicazioneDad.updateComunicazione(comunicazioneDaAggiornare);
			}
			
			comunicazione = insertComunicazioneService.insertComunicazione(comunicazione);
		}else {
			comunicazione = insertComunicazioneService.insertComunicazioneSemplice(comunicazione);
		}

		comunicazione.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazione);
	}
	
	private boolean verificaIdentificativoRapporto(Datore datore, Rapporto rapporto, Lavoratore lavoratore, Comunicazione comunicazione) {
	    	boolean cfImpresaChanged =
	    		datore == null || StringUtils.isBlank(datore.getCodiceFiscale()) ||
		    	comunicazione.getDatoreAttuale() == null || StringUtils.isBlank(comunicazione.getDatoreAttuale().getCodiceFiscale()) ||
		    	!datore.getCodiceFiscale().equalsIgnoreCase(comunicazione.getDatoreAttuale().getCodiceFiscale());
		    
		    boolean dtInizioRapportoChanged =
		    		rapporto == null || rapporto.getDtInizioRapporto() == null||
		    		comunicazione.getRapporto() == null || comunicazione.getRapporto().getDtInizioRapporto() == null||
		    		compareTo(rapporto.getDtInizioRapporto(), comunicazione.getRapporto().getDtInizioRapporto())!=0;
		    
		    boolean cfLavoratoreChanged = 
		    		lavoratore == null ||  StringUtils.isBlank(lavoratore.getCodiceFiscale()) ||
		    		comunicazione.getLavoratori() == null || StringUtils.isBlank(comunicazione.getLavoratori().get(0).getCodiceFiscale()) ||
		    		!lavoratore.getCodiceFiscale().equalsIgnoreCase(comunicazione.getLavoratori().get(0).getCodiceFiscale());
		    
		    return  !cfImpresaChanged && !dtInizioRapportoChanged && !cfLavoratoreChanged;
	}
}
