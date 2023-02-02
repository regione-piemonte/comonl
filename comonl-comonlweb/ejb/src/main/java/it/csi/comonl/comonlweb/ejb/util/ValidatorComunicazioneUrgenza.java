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
package it.csi.comonl.comonlweb.ejb.util;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;

public class ValidatorComunicazioneUrgenza extends Validator<Comunicazione> {

	private final DecodificaDad decodificaDad;
	private final ComunicazioneDad comunicazioneDad;
	protected final ControlliDad controlliDad;

	private static final String REGEX_EMAIL = new String(
			"([A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*@[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*)?");

	public ValidatorComunicazioneUrgenza(WrapperComunicazione wrapper, ComunicazioneDad comunicazioneDad, DecodificaDad decodificaDad, ControlliDad controlliDad) {
		super(wrapper.getComunicazione());
		this.decodificaDad = decodificaDad;
		this.comunicazioneDad = comunicazioneDad;
		this.controlliDad = controlliDad;
		ruolo =  wrapper.getRuolo();
	}
	
	@Override
	public boolean isOk() {
		
		ValidazioneCampi validazioneCampi = ValidazioneCampi.getInstance();
		
		checkModel(controlObject.getTipoComunicazione(), "tipoComunicazione");
		checkModel(controlObject.getTipoTracciato(), "tipoTracciato");
		checkNotEmpty(controlObject.getFlgSommin(), "flgSommin");
		checkNotEmpty(controlObject.getEmail(), "email");
		checkCondition(validazioneCampi.checkMail(controlObject.getEmail()),MsgComonl.COMCOMW002.getError());
		checkModel(controlObject.getTipoComunicazioneTu(), "tipoComunicazioneTu");
		checkNotEmpty(controlObject.getFlgComDatLav(),"flgComDatLav");
		
		if (controlObject.getFlgComDatLav() != null && controlObject.getFlgComDatLav().equals(ComonlConstants.FLAG_N)) {
			checkModel(controlObject.getTipoSoggettoAbilitato(), "tipoSoggettoAbilitato");
			checkNotEmpty(controlObject.getCodiceFiscaleSoggetto(), "codiceFiscaleSoggetto");
			checkNotEmpty(controlObject.getEmailsoggetto(), "emailsoggetto");
			checkNotNull(controlObject.getDtTimbroPostale(),"dtTimbroPostale");
			
			if (controlObject.getDtTimbroPostale()!=null) {
				Date dataAvvioSistema = ComonlDateUtils.parseDate("11/01/2008"); // parametro su db?
				Date sysdate = new Date();
				checkCondition(controlObject.getDtTimbroPostale().compareTo(dataAvvioSistema)>=0 && controlObject.getDtTimbroPostale().compareTo(sysdate)<=0, MsgComonl.COMCOME1300.getError());
			}
			
			if(controlObject.getCodiceFiscaleSoggetto() != null) {
				checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(controlObject.getCodiceFiscaleSoggetto(), decodificaDad), MsgComonl.COMURGE0001.getError());
			}
		}
		
		if (controlObject.getIdComTuPrecedenteAnnullo() != null || controlObject.getIdComTuDaRettificare()!= null) {
			checkNotEmpty(controlObject.getCodiceComunRegPrec(), "codiceComunRegPrec");
			Optional<Comunicazione> optComunicazione = comunicazioneDad.getComunicazione(controlObject.getIdComDComunicPrecedente());
			Comunicazione comunicazione = optComunicazione.isPresent() ? optComunicazione.get() : null;
			checkModel(comunicazione, "comunicazione da rettificare/annullare");
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() != null) {
				checkCondition(comunicazione.getCodiceComunicazioneReg().equalsIgnoreCase(controlObject.getCodiceComunRegPrec()),MsgComonl.COMCOME1302.getError());
			}
			if(comunicazione!=null && comunicazione.getCodiceComunicazioneReg() == null) {
				checkCondition(
						controlObject.getCodiceComunRegPrec()==null ||
						controlObject.getCodiceComunRegPrec().equalsIgnoreCase("1300000000000000"),MsgComonl.COMCOME1302.getError());
			}
		}
		
		checkNotEmpty(controlObject.getDsMotivoUrgenza(), "Motivo dell'urgenza");
		Rapporto rapporto = controlObject.getRapporto();
		checkNotNull(rapporto.getDtInizioRapporto(),"data di inizio del rapporto");
		Datore datore = controlObject.getDatoreAttuale();
		chkDatore(datore);
		chkLavoratore(controlObject.getLavoratori());
		
		// WARNING

		
		return (!isInError());
	}
	
	private void chkDatore(Datore datore) {
		checkNotEmpty(datore.getCodiceFiscale(), "Codice fiscale datore di lavoro");
		checkNotEmpty(datore.getDsDenominazioneDatore(), "Denominazione/cognome e nome datore di lavoro");
		if(datore.getCodiceFiscale() != null) {
			checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(datore.getCodiceFiscale(), decodificaDad), MsgComonl.COMURGE0002.getError());
		}
		
		SedeLavoro sedeOperativa = datore.getSedeOperativa();
		Comune comune = sedeOperativa.getComune();
		if(comune!=null && 
				 (comune.getId()!=null || comune.getCodComuneMin()!=null)) {
			 		comune = controlliDad.getComuneValid(comune, new Date());
			 		checkModel(comune, "Comune sede operativa");
			 		sedeOperativa.setComune(comune);
		 }
		
		checkAziendaAccentrata(datore);
	}
	
	private void checkAziendaAccentrata(Datore datore) {
		boolean isComunicazioneVarDatori = ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(controlObject.getTipoTracciato().getId());
		SedeLavoro sedeLavoro = isComunicazioneVarDatori ? datore.getSedeLegale() : datore.getSedeOperativa();
		
		if (sedeLavoro !=null && !inPiemonte(sedeLavoro)) {
			String aziendaAccentrata = null;
			AnagraficaAziAccent anagraficaAziAccent = null;
			if (ruolo.isConsulenteRespo()) {
				aziendaAccentrata = controlliDad.getFlgAccentramento(ruolo.getCodiceFiscaleStudioProfessionale());
			}else {
				anagraficaAziAccent = controlliDad.getAnagraficaAziAccent(datore.getCodiceFiscale());
			}
			checkCondition(ComonlConstants.FLAG_S.equalsIgnoreCase(aziendaAccentrata) || anagraficaAziAccent != null, MsgComonl.COMDATE052.getError());
		}
	}
	private boolean inPiemonte(SedeLavoro sede) {
		if (sede != null) {
			return (sede.getComune() != null && sede.getComune().getProvincia() != null
					&& sede.getComune().getProvincia().getRegione() != null && ComonlConstants.CODICE_REGIONALE_PIEMONTE
							.equals(sede.getComune().getProvincia().getRegione().getCodRegioneMin()));
		}
		return false;
	}

	
	private void chkLavoratore(List<Lavoratore> lavoratori) {
		for(Lavoratore lavoratore: lavoratori) {
			checkNotEmpty(lavoratore.getCodiceFiscale(), "Codice fiscale lavoratore");
			checkNotEmpty(lavoratore.getCognome(), "Cognome lavoratore");
			checkNotEmpty(lavoratore.getNome(), "Nome lavoratore");
			if(lavoratore.getCognome() != null) {
				lavoratore.setCognome(lavoratore.getCognome().trim());
			}
			if(lavoratore.getNome() != null) {
				lavoratore.setNome(lavoratore.getNome().trim());
			}
			if(lavoratore.getCodiceFiscale() != null) {
				checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(lavoratore.getCodiceFiscale(), decodificaDad), MsgComonl.COMURGE0003.getError());
				Datore datoreattuale = controlObject.getDatoreAttuale();
				checkCondition(!datoreattuale.getCodiceFiscale().equalsIgnoreCase(lavoratore.getCodiceFiscale()), MsgComonl.COMRAPE0182.getError("parameter",lavoratore.getCodiceFiscale()));
			}
		}
	}
	
	public void checkComuneValidity(Comune comune) {

		if (comune == null || comune.getCodComuneMin() == null || "".equalsIgnoreCase(comune.getCodComuneMin())
				|| comune.getDsComTComune() == null || "".equalsIgnoreCase(comune.getDsComTComune())) {
			apiWarnings.add(MsgComonl.COMCOMW009.getError());
			return;
		}
		Long idComune = recuperaIdComune(comune.getCodComuneMin(), comune.getDsComTComune());
		if (idComune == null) {
			apiWarnings.add(MsgComonl.COMCOMW013.getError());
			return;
		}
		comune.setId(idComune);

	}

	private Long recuperaIdComune(String codice, String descrizione) {

		Long id = null;

		DecodificaGenerica filtro = new DecodificaGenerica();
		filtro.setCodDecodifica(codice);
		filtro.setDsDecodifica(descrizione);
		// TODO Verificare se necessario anche regione
		List<DecodificaGenerica> decode = decodificaDad.getComuneDecodifica(filtro);
		if (decode != null && decode.size() == 1) {
			id = decode.get(0).getIdDecodifica();
		}
		return id;
	}

	public void checkEmailValidity(String email) {
		if (!ComonlUtility.checkValore(email, REGEX_EMAIL)) {
			apiWarnings.add(MsgComonl.COMCOMW002.getError());
		}
	}

}
