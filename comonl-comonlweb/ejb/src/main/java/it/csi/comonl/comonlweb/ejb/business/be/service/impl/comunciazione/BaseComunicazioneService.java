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


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * Base service implementation for the comunicazione
 * 
 * @param <Q> the request type
 * @param <R> the response type
 */
public abstract class BaseComunicazioneService<Q extends BaseRequest, R extends BaseResponse> extends BaseService<Q, R> {

	/** Data Access Delegate for decodifica */
	protected final ComunicazioneDad comunicazioneDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad       the DAD for the comunicazione
	 */
	protected BaseComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper);
		this.comunicazioneDad = comunicazioneDad;
	}
	
	
	protected boolean isCheckDatiEssenziali(Comunicazione comunicazione, Ruolo ruolo) {
		if (ruolo.isOperatoreProvinciale() || !isComunicazioneRettifica(comunicazione)) 
			return false;
		return isFuoriTempoPerRettifica(comunicazione);
			
	}
	
	private boolean isComunicazioneRettifica(Comunicazione comunicazione) {
		return comunicazione.getTipoComunicazioneTu()!=null && 
				comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin().equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN);
	}
	protected boolean isComunicazioneAnnulla(Comunicazione comunicazione) {
		return comunicazione.getTipoComunicazioneTu()!=null && 
				comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin().equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN);
	}
	protected boolean isComunicazioneUltima(Comunicazione comunicazione) {
		return comunicazione.getFlgCurrentRecord() != null && comunicazione.getFlgCurrentRecord().equalsIgnoreCase(ComonlConstants.FLAG_S);
	}
	
	protected boolean isFuoriTempoPerRettifica(Comunicazione comunicazione) {
		Date dtRiferimento = null;
		
		if  (comunicazione.getDtTimbroPostale()!=null) {
			Calendar c = Calendar.getInstance();
			c.setTime(comunicazione.getDtTimbroPostale());
			c.add(Calendar.DAY_OF_YEAR, -5);
			dtRiferimento = c.getTime();		
		}else {
			LocalDate localDate = LocalDate.now().minusDays(5);
			dtRiferimento = java.sql.Date.valueOf(localDate);
		}
		
		Comunicazione comunicazionePrecedente = getComunicazionePrecedente(comunicazione);
		
		if (dtRiferimento.compareTo(comunicazionePrecedente.getDtTimbroPostale())<=0)
			return false;
		else
			return true;
	}
	
	private Comunicazione getComunicazionePrecedente(Comunicazione comunicazione) {
		if(comunicazione.getIdComDComunicPrecedente()!=null) {
			long idComunicazione = comunicazione.getIdComDComunicPrecedente();
			comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(idComunicazione), "comunicazione");
			getComunicazionePrecedente(comunicazione);
		}
		return comunicazione;
	}

	
	protected Comunicazione setDeafultPerRuolo (Comunicazione comunicazione, Ruolo ruolo) {
		
		Utente utente = getUtente();
		comunicazione.setCodiceEnte(utente.getCodiceFiscale());
		
		if (comunicazione.getCodiceFiscaleSoggetto()!=null) {
			comunicazione.setCodiceFiscaleSoggetto(comunicazione.getCodiceFiscaleSoggetto().trim());
		}
		
		if(ruolo.isOperatoreProvinciale()) {
			comunicazione.setCodiceEnte(
					StringUtils.isBlank(comunicazione.getCodiceFiscaleSoggetto()) ? ruolo.getCodiceFiscaleAzienda() : comunicazione.getCodiceFiscaleSoggetto());
		}
		if(ruolo.isAmministratore()) {
			comunicazione.setCodiceEnte(
					StringUtils.isBlank(comunicazione.getCodiceFiscaleSoggetto()) ? "97595380011" : comunicazione.getCodiceFiscaleSoggetto());
		}
		
		setDelegatoPerRuolo(comunicazione, ruolo);
		
		if (ruolo.isAmministratore() || ruolo.isOperatoreProvinciale())
			return comunicazione;
		
		setDtTimbroPostale(comunicazione);
		
		comunicazione.setFlgComDatLav(ComonlConstants.FLAG_S);
		
		if (ruolo.isConsulenteRespo()) {
			comunicazione.setFlgComDatLav(ComonlConstants.FLAG_N);
			comunicazione.setCodiceFiscaleSoggetto(ruolo.getCodiceFiscaleAzienda());
			comunicazione.setTipoSoggettoAbilitato(ruolo.getTipoSoggettoAbilitato());
			String emailSoggetto = StringUtils.isBlank(ruolo.getEmail()) ? comunicazione.getEmail() : ruolo.getEmail();
			comunicazione.setEmailsoggetto(emailSoggetto);
			comunicazione.setCodiceEnte(ruolo.getCodiceFiscaleAzienda());
		}
		
		return comunicazione;
	}

	protected int compareTo (Date dateA, Date dateB) {
		return DateUtils.truncatedCompareTo(dateA, dateB, Calendar.DATE);
	}
	
	protected boolean isFuoriTempoPerInvio(Comunicazione comunicazione) {
		
//		UNILAV e tipo_comunicazione AS e flgPubbliammi = NO
//				1) L'assunzione UL di aziende non pubbliche deve essere inviata entro la data assunzione, se viene inviata successivamente viene visualizzato un warning
//					dataTimbroPostale <= dataRiferimento(che coincide con dataInizioRapporto )
//
//				UNILAV e tipo_comunicazione AS e flgPubbliammi = SI
//				e UNISOMM
//				2) L'assunzione UL di aziende pubbliche, l'assunzione US e le comunicazioni di aggiornamento US devo essere inviate entro il 20 del mese successivo 
//				della data di riferimento della comunicazione, se viene inviata successivamente viene visualizzato un warning.
//				dtTimbro postale <= 20 meseRiferimento+1 
//
//				UNILV e tipo_comunicazione <> AS
//				3 )Le comunicazioni di aggiornamento UL devono essere inviate entro 5gg di calendario dalla data di riferimento della comunicazione,   se viene inviata successivamente viene visualizzato un warning
//				dtTimbro postale <= dataRiferimento+5
//
//
//				Se tipoTracciato UL e tipo_comunicazione <> AS e tipo_comunicazone_tu = 04 (annullamento ) e flgPubbliammi = NO
//				4)Le comunicazioni di aggiornamento UL per le aziende non pubbliche devono essere annullate entro i 5 gg di calendario dalla data riferimento comunicazione, se inviate successivamente viene visualizzato un warning.
//				dtTimbroPostale <= dataRiferimento+5
//
//				Se ( (tipoTracciato UL e flgPubbliammi = SI ) o tipoTracciato US ) e tipo_comunicazione <> AS e tipo_comunicazone_tu = 04 (annullamento )
//				5)Le comunicazioni di aggiornamento US e UL pubbliche devono essere annullate entro i 5 gg di calendario dalla data timbro postale, se inviate successivamente viene visualizzato un warning.
//				dtTimbro postale della comunicazione inserita <= dtTimbroPostale della comunicazione che sto annullando + 5
//		
		
	    boolean isUnilav =  ComonlConstants.TIPO_TRACCIATO_UNILAV_ID.equalsIgnoreCase(comunicazione.getTipoTracciato().getId());
	    boolean isUnisomm = ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID.equalsIgnoreCase(comunicazione.getTipoTracciato().getId());
	    boolean isAss = ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equalsIgnoreCase(comunicazione.getTipoComunicazione().getId());
	    boolean isPa = ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgPubAmm());
	    boolean isAnnullo = ComonlConstants.TIPO_COMUNICAZIONE_ANNULLAMENTO_4_STRING.equalsIgnoreCase(comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin());
	    
	    Date dtTimbroPostale = comunicazione.getDtTimbroPostale();
	    Date dtRiferimento   = comunicazione.getDataRiferimento();
		
	    if (isUnilav && isAss && !isPa) {
	    	return compareTo(dtTimbroPostale, dtRiferimento)>0;
	    
	    }if (isUnilav && isAss && isPa) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(dtRiferimento);
	        c.add(Calendar.MONTH, 1);
	        c.set(Calendar.DAY_OF_MONTH, 20);
	        Date newDtRiferimento = c.getTime();
			return compareTo(dtTimbroPostale, newDtRiferimento)>0;
			
	    }
	    if ((isUnilav && !isAss && !isAnnullo) || (isUnilav && !isAss && isAnnullo && !isPa)) {
	    	
	    	if (dtRiferimento == null)
	    		return false;
	    	
	    	Calendar c = Calendar.getInstance();
	        c.setTime(dtRiferimento);
	        c.add(Calendar.DAY_OF_YEAR, 5);
	        Date newDtRiferimento = c.getTime();

			return compareTo(dtTimbroPostale, newDtRiferimento)>0;
	    }
	    if ( ( (isUnilav && isPa) || isUnisomm) && !isAss && isAnnullo ) {
	    	
			Optional<Comunicazione> optComunicazione = comunicazioneDad.getComunicazione(comunicazione.getIdComTuPrecedenteAnnullo());
			Comunicazione comunicazioneDaAnnullare = optComunicazione.isPresent() ? optComunicazione.get() : null;
			checkModel(comunicazione, "comunicazione da rettificare/annullare", true);
	    	
	    	Calendar c = Calendar.getInstance();
	        c.setTime(comunicazioneDaAnnullare.getDtTimbroPostale());
	        c.add(Calendar.DAY_OF_YEAR, 5);
	        Date newDtRiferimento = c.getTime();

			return compareTo(dtTimbroPostale, newDtRiferimento)>0;
	    }
		return false;
	}
	
	protected boolean isContrattoApprendistato(Rapporto rapporto) {
		if (rapporto == null )
			return false;
		return ComonlConstants.TIPO_CONTRATTI_TIPO_AP.equalsIgnoreCase(rapporto.getTipoContratti().getTipo());
	}
	
	private void setDtTimbroPostale(Comunicazione comunicazione) {
		if (isModelNull(comunicazione)) {
			comunicazione.setDtTimbroPostale(new Date());
		}else {
			Optional<Comunicazione> optional = comunicazioneDad.getComunicazione(comunicazione.getId());
			Comunicazione comunicazioneDb = optional.isPresent() ? optional.get() : null;
			if (comunicazioneDb!=null && ComonlConstants.LOGIN_SERVIZIO.equalsIgnoreCase(comunicazioneDb.getIdUserUltMod()) && comunicazioneDb.getDtTimbroPostale()!=null) {
				comunicazione.setDtTimbroPostale(comunicazioneDb.getDtTimbroPostale());
			}else {
				comunicazione.setDtTimbroPostale(new Date());
			}
		}
	}
	
	protected void setDelegatoPerRuolo(Comunicazione comunicazione, Ruolo ruolo) {
		
		if ((ruolo.isAmministratore() || ruolo.isOperatoreProvinciale()) && ComonlConstants.FLAG_S.equals(comunicazione.getFlgComDatLav())) {
			return;// il delegato non viene toccato.
		}
		if (!ruolo.isDelegatoRespo() && !ruolo.isLegaleRappresentante() && !ruolo.isPersonaAutorizzata()) {
			comunicazione.setDelegato(null); // il delegato viene cancellato
		}else { // l'utente è settato come delegato
			Delegato delegato = comunicazioneDad.getLastDelegatoByCf(ruolo.getCodiceFiscaleUtente());
			if (delegato==null) {
				delegato = new Delegato();
				delegato.setCfDelegato(ruolo.getCodiceFiscaleUtente());
				delegato.setNomeDelegatoImpresa(ruolo.getDsNome());
				delegato.setCognomeDelegatoImpresa(ruolo.getDsCognome());
				delegato.setMailDelegatoImpresa(ruolo.getEmail());
				comunicazioneDad.insertDelegato(delegato);
			}
			comunicazione.setDelegato(delegato);
		}
	}
	
	protected boolean isTrasformazioneTempoIndeterminato (Trasformazionerl trasformazione) {
		return ComonlConstants.TRASFORMAZIONERL_AI.equalsIgnoreCase(trasformazione.getCodTrasformazionirlMin()) ||
			   ComonlConstants.TRASFORMAZIONERL_FI.equalsIgnoreCase(trasformazione.getCodTrasformazionirlMin()) ||
			   ComonlConstants.TRASFORMAZIONERL_II.equalsIgnoreCase(trasformazione.getCodTrasformazionirlMin()) ||
			   ComonlConstants.TRASFORMAZIONERL_DI.equalsIgnoreCase(trasformazione.getCodTrasformazionirlMin());
	}
	protected String trim(String val) {
		if (val!=null) {
			return val.trim();
		}
		return val;
	}
}
