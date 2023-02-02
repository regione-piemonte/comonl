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
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiRapportoVardatori extends ValidatorDatiRapporto {

	private final DecodificaDad decodificaDad;
	public ValidatorDatiRapportoVardatori(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(comunicazione, controlliDad);
		this.decodificaDad=decodificaDad;
	}
	
	@Override
	public boolean isOk() {
		
		super.isOk(); // validazione comune
		
		checkModel(rapporto.getTipoContratti(),"tipoContratti");
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		TipoContrAmmPerCom tipoContrattoAmmPerCom = controlliDad.getTipoContrAmmPerComByTipoContratto(tipoContratti.getId());
		checkCondition(tipoContrattoAmmPerCom!=null, MsgComonl.COMCOME0019.getError("parameter","tipo contratto")); // messaggio corretto : non ho trovato il record nella tipoContrattoAmmPerCom
		if (tipoContrattoAmmPerCom!=null) {
			checkCondition(tipoContrattoAmmPerCom.getFlgVldVd().equalsIgnoreCase(ComonlConstants.FLAG_S), MsgComonl.COMRAPE0169.getError()); // il tipo contratto non è valido per tracciato VARDATORI
		}
		TipoContrPeriodi  tipoContrPeriodi= controlliDad.getTipoContrPeriodiByTipoContrattoInData(tipoContratti.getId(), rapporto.getDtInizioRapporto());
		checkCondition(tipoContrPeriodi == null,  MsgComonl.COMRAPE0139.getError());// alla data di assunzione indicata il tipo contratto non puo' essere usato.

		boolean isMultiLavoratore = controlObject.getLavoratori()!=null && controlObject.getLavoratori().size()>1;
		checkCondition(!isMultiLavoratore ||
				(isMultiLavoratore && !ValidatorUtility.isTipoContrattoRipartito(tipoContratti) && !ValidatorUtility.isTipoContrattoApprendistato(tipoContratti) && !ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)), MsgComonl.COMRAPE0140.getError());//In caso di Contratto ripartito, Apprendistato e tirocinio non è previsto il multi lavoratore
		
		checkDtFineRapporto();
		
		checkDtFinePeriodoFormativo();
		
		chekDatiTirocinio();
		
		checkDatiInquadramento();
		
		checkAltriDati();
		
		return (!isInError());
	}
	
	private void checkDtFineRapporto () {
		
		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		if (tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)
			) {
			return;
		}
		
		checkCondition(
				rapporto.getDtFineRapporto() == null ||
				rapporto.getDtFineRapporto().compareTo(rapporto.getDtInizioRapporto())>=0, MsgComonl.COMRAPE0110.getError());// dtFine >= dtInizio
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		checkCondition(
				!tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_D) ||
				(tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_D) && rapporto.getDtFineRapporto()!=null), MsgComonl.COMRAPE0109.getError());// valorizzata se tipo contratto ha forma D(determinato)
		
		boolean tipoContrattoApprendistato = ValidatorUtility.isTipoContrattoApprendistato(tipoContratti);
		
		checkCondition(
				!tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I) ||
				(tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I) && tipoContrattoApprendistato) ||
				(tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I) && !tipoContrattoApprendistato &&	rapporto.getDtFineRapporto()==null)
				, MsgComonl.COMRAPE0165.getError());// Non deve essere valorizzata se tipo contratto ha forma I(indeterminato) non è Apprendistato
		
		checkCondition(
				!tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I) ||
				!tipoContrattoApprendistato ||
				!ValidatorUtility.isLavoroStagionale(rapporto) ||
				(tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_I) && tipoContrattoApprendistato && ValidatorUtility.isLavoroStagionale(rapporto) && rapporto.getDtFineRapporto()!=null)
				, MsgComonl.COMRAPE0142.getError());// Deve essere valorizzata se tipo contratto ha forma I(indeterminato) è Apprendistato e FLStagionale = S
		
		checkCondition(!ValidatorUtility.isLavoroStagionale(rapporto) || rapporto.getDtFineRapporto()!=null, MsgComonl.COMRAPE0164.getError());// Valorizzata se FLStagionale = S
	}
	
	
	private void checkDtFinePeriodoFormativo() {
		
		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		if (tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)
			) {
			return;
		}
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		boolean tipoContrattoApprendistato = ValidatorUtility.isTipoContrattoApprendistato(tipoContratti);
		
		checkCondition(rapporto.getDtFinePeriodoFormativo()==null || 
				(rapporto.getDtFinePeriodoFormativo()!=null && rapporto.getDtFinePeriodoFormativo().compareTo(rapporto.getDtInizioRapporto())>0 ), MsgComonl.COMRAPE0112.getError()); //Se presente, deve essere maggiore della Data inizio rapporto 
		checkCondition(!tipoContrattoApprendistato || rapporto.getDtFinePeriodoFormativo()!=null, MsgComonl.COMRAPE0111.getError());//Campo da compilare obbligatoriamente solo in caso di tipologia contrattuale di Apprendistato
		checkCondition(tipoContrattoApprendistato ||
				(!tipoContrattoApprendistato && rapporto.getDtFinePeriodoFormativo()==null), MsgComonl.COMRAPE0174.getError());//Per le altre tipologie contrattuali invece non deve essere compilato

	}
	private void chekDatiTirocinio () {
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		if(!ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)) {
			return;
		}
		String cfSoggPromotoreTirocinio = rapporto.getCfSoggPromotoreTirocinio();
		String cfDatoreLavoro = controlObject.getDatoreAttuale().getCodiceFiscale(); // si spacca se getDatoreAttuale is null
		
		checkNotEmpty(cfSoggPromotoreTirocinio, "CfSoggPromotoreTirocinio");
		checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(cfSoggPromotoreTirocinio,decodificaDad), MsgComonl.COMRAPE0184.getError()); //Controllo Codice fiscale.
		
		checkCondition( cfSoggPromotoreTirocinio==null || cfDatoreLavoro==null || 
				!cfSoggPromotoreTirocinio.equalsIgnoreCase(cfDatoreLavoro), MsgComonl.COMRAPE0167.getError()); //Il codice fiscale non può essere uguale a quello del datore di lavoro.
	}
	
	private void checkDatiInquadramento() {
		 
		checkModel(rapporto.getCcnl(), "rapporto.getCcnl()");
		if (rapporto.getCcnl()==null || rapporto.getCcnl().getId()==null)
			return;

		// ricerca , check, completamento oggetto
		Ccnl ccnl = checkCcnl(rapporto);
		if (ccnl == null)
			return;
		this.controlObject.getRapporto().setCcnl(ccnl);
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		checkCondition(tipoContratti==null ||
				!tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) ||
				(tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) && ccnl.getSettore().equals(ComonlConstants.CCNL_SETTORE_ENTIPUBBLICI)), MsgComonl.COMRAPE0120.getError());//Se il tipo contratto è A.03.04 - formazione lavoro (solo pubblica amministrazione), il CCNL deve essere di tipo ente pubblico ( settore= Enti pubblici)
		
		checkModel(rapporto.getLivelloRetribuzione(), "rapporto.livelloRetribuzione");
		
		// livello retribuzione (inquadramento)
		LivelloRetribuzione livelloRetribuzione = checkLivelloRetribuzione(rapporto);
		checkCondition(ccnl!=null && ccnl.getId()!=null, MsgComonl.COMCOME1140.getError());// Livello retribuzione non trovato
		if (livelloRetribuzione == null)
			return;
		this.controlObject.getRapporto().setLivelloRetribuzione(livelloRetribuzione);
		checkCondition(livelloRetribuzione==null || 
				(livelloRetribuzione!=null && livelloRetribuzione.getCcnl().getId().compareTo(ccnl.getId())==0), MsgComonl.COMRAPE0177.getError());//Tabella  COM_T_LIVELLO_RETRIBUZIONE ( solo validi i livelli previsti per ccnl selezionato).	
		
		checkRetribuzioneCompenso();
		
		checkAllowedValuesFlag(rapporto.getFlgAssunzioneObbligatoria(),"assunzione obbligatoria", false);// Obbligatorio Può valere SI o NO
		
		checkModel(rapporto.getEntePrevidenziale(),"rapporto.entePrevidenziale");
		EntePrevidenziale entePrevidenziale = controlliDad.getEntePrevidenzialeValid(rapporto.getEntePrevidenziale(), new Date());  
		checkModel(entePrevidenziale,"ente previdenziale");
		
		checkCondition(entePrevidenziale == null || !entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) || 
				(entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) && (rapporto.getCodiceEntePrev()==null || rapporto.getCodiceEntePrev().isEmpty())), MsgComonl.COMRAPE0178.getError());//Se “ente  previdenziale” (7.1.c)  = “NESSUN ENTE PREVIDENZIALE”  il campo non deve essere valorizzato.

	}
	private void checkRetribuzioneCompenso() {
		checkNotNull(rapporto.getRetribuzioneCompenso(), "retibuzioneCompenso");
		if (rapporto.getRetribuzioneCompenso()==null)
			return;
		
		ComonlsParametri paramRetribuzione0Ammessa = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RETRIBUZIONE_0_AMMESSA);
		boolean retribuzione0Ammessa = paramRetribuzione0Ammessa != null && paramRetribuzione0Ammessa.getValoreParametro().equals(ComonlConstants.FLAG_S);
		
		if (retribuzione0Ammessa) {
			ComonlsParametri p = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RETRIBUZIONE_0_CONTRATTI);
			if (p==null)
				retribuzione0Ammessa = false;
			else { 
				retribuzione0Ammessa = Arrays.asList(p.getValoreParametro().split(";")).contains(rapporto.getTipoContratti().getCodTipoContrattoMin());
			}
		}
		checkCondition(retribuzione0Ammessa || 
				(!retribuzione0Ammessa && (rapporto.getRetribuzioneCompenso()!=null) && rapporto.getRetribuzioneCompenso().compareTo(BigDecimal.ZERO)==1), MsgComonl.COMRAPE0168.getError());// non è possibile mettere una retribuzione a 0
		
		LivelloRetribuzione livelloRetribuzione = rapporto.getLivelloRetribuzione();
		if (livelloRetribuzione!=null && livelloRetribuzione.getId()!=null) {
			
			/*“WARNING. Attenzione! La retribuzione inserita è minore del valore ammesso calcolato <retribuzione calcolata>. 
			 * Verificare i dati inseriti. Procedendo con la conferma il valore sarà comunque salvato dal sistema, sotto la responsabilità dell’utente che registra la comunicazione obbligatoria”.*/
			Long retribuzioneCalcolata = calcolaRetribuzioneCompenso (
					livelloRetribuzione.getLordoMensile(),livelloRetribuzione.getMensilita(),livelloRetribuzione.getDivisoreOrario(),rapporto.getNumOreSettMed(), rapporto.getTipoOrario().getCodTipoorarioMin());
			checkConditionWarning(rapporto.getRetribuzioneCompenso().intValue()>=retribuzioneCalcolata, MsgComonl.COMRAPW0055.getError("parameter",retribuzioneCalcolata.toString()));
			
		}
	}

	private void checkAltriDati() {

		//********** TODO campi validabili solo per comonl NON per COMMAX
		
		CategLavAssObbl categLavAssObbl = rapporto.getCategLavAssObbl();
		checkCondition(rapporto.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_N) || 
				(rapporto.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_S)&& categLavAssObbl!=null), MsgComonl.COMRAPE0145.getError());///Obbligatorio se “Assunzione Obbligatoria” =SI
	
		boolean atLeastoneNotNull = rapporto.getNumeroAttoLegge68()!=null || rapporto.getDtLegge68()!=null || (rapporto.getTipoAttoL68()!=null && rapporto.getTipoAttoL68().getId()!=null);
		boolean atLeastoneNull = rapporto.getNumeroAttoLegge68()==null || rapporto.getDtLegge68()==null || (rapporto.getTipoAttoL68()==null || rapporto.getTipoAttoL68().getId()==null);

		boolean fieldsLegge68Obbl = categLavAssObbl.getCodCategLavAssObblMin().equalsIgnoreCase(ComonlConstants.CATEG_LAV_ASS_OBBL_COD_MIN_PD) ||
									categLavAssObbl.getCodCategLavAssObblMin().equalsIgnoreCase(ComonlConstants.CATEG_LAV_ASS_OBBL_COD_MIN_CP)||
									atLeastoneNotNull;
		if(fieldsLegge68Obbl) {
			checkModel(rapporto.getTipoAttoL68(), "rapporto.getTipoAttoL68()");
			checkNotNull(rapporto.getDtLegge68(), "rapporto.getDtLegge68()");
			checkNotEmpty(rapporto.getNumeroAttoLegge68(), "rapporto.getNumeroAttoLegge68()");
		}
		
		checkCondition(!atLeastoneNotNull || 
				(atLeastoneNotNull && !atLeastoneNull),MsgComonl.COMRAPE0124.getError()); // o tutti o nessuno

		//**********
		
		checkAllowedValuesFlag(rapporto.getFlgSocioLavoratore(), "socio lavoratore",false);//Obbligatorio Può valere SI o NO, defualt NO
		checkAllowedValuesFlag(rapporto.getFlgLavoroAgricoltura(), "lavoro in agricoltura",false);//Obbligatorio Può valere SI o NO, defualt NO
		checkCondition(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && rapporto.getGiornateLavPreviste()==null), MsgComonl.COMRAPE0179.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì
		checkCondition(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && rapporto.getTipoLavorazione()==null), MsgComonl.COMRAPE0180.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì
		checkAllowedValuesFlag(rapporto.getFlgLavInMobilita(), "lavoratore in mobilita",false);//Obbligatorio Può valere SI o NO, defualt NO
	}
		
}
