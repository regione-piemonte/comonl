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
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class ValidatorSediLavoro extends Validator<Comunicazione>
{

	protected final ControlliDad controlliDad;
	private final DecodificaDad decodificaDad;

	public ValidatorSediLavoro(Comunicazione controlObject, ControlliDad controlliDad, DecodificaDad decodificaDad ) {
		super(controlObject);
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}
	/*
	public ValidatorSediLavoro(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad = controlliDad;
	}*/
	
	@Override
	public boolean isOk() {
		
		/* TODO
		 * 
		 * se tipoCOntrattoRipartito -> selezionati 2 lavoratori 
		 * 
		 */
		List<RapportiLavoratoriSediInteressateVD> rapportiLavoratriSedi = controlObject.getRapLavSedeVD();
		
		
		checkRapportiLavoratriSedi(rapportiLavoratriSedi);
		

		return (!isInError());
	}
	
	private void checkRapportiLavoratriSedi(List<RapportiLavoratoriSediInteressateVD> rapportiLavoratriSedi)  {
		if (rapportiLavoratriSedi == null || rapportiLavoratriSedi.isEmpty()) 
			return;
		
		for(RapportiLavoratoriSediInteressateVD rapportoLavoratreSede : rapportiLavoratriSedi) {
			
			SedeLavoro sedeLavoroVd = rapportoLavoratreSede.getSedeLavoroVD();
			
			chkSedeLavoroVd(sedeLavoroVd);
			
			Rapporto rapportoVd = rapportoLavoratreSede.getRapportoVD();
			Lavoratore lavoratreVd = rapportoLavoratreSede.getLavoratoreVD();
			
			chkRapportoVd(rapportoVd,lavoratreVd);
			
			chkLavoratoreVd(lavoratreVd);
			
		}
	}
	
	
	protected void chkSedeLavoroVd(SedeLavoro sedeLavoro) {
		checkNotNull(sedeLavoro,"Sede di lavoro");
		if(sedeLavoro != null) {
			
			
			if(sedeLavoro.getFlgSedeLegale() == null) {
				sedeLavoro.setFlgSedeLegale("N");
			}
			
			Comune comune = sedeLavoro.getComune();
			StatiEsteri statiEsteri = sedeLavoro.getStatiEsteri();
			
			 boolean atLeastoneNotNull = statiEsteri !=null || comune !=null;
			 checkCondition(atLeastoneNotNull, MsgComonl.COMTDIE1410.getError());// TODO verificare il messaggio: la provincia non c'è ...
			 if(comune!=null && 
					 (comune.getId()!=null || comune.getCodComuneMin()!=null)) {
				 	comune.setId(null);//forzo la ricerca per codiceMinisteriale
					Comune comuneValido = checkComune(comune,"Comune sede di lavoro"); 
					if (comuneValido!=null) {
						sedeLavoro.setComune(comuneValido);
					}
			 }
			 if(statiEsteri!=null && 
					 (statiEsteri.getId()!=null || statiEsteri.getCodNazioneMin()!=null)) {
				 		statiEsteri = controlliDad.getStatiEsteriValid(statiEsteri, new Date());
				 		checkModel(statiEsteri, "Stato estero");
			 }
			 
			 if(comune != null) {
				 checkNotEmpty(sedeLavoro.getCodCap(),"Cap sede di lavoro");
			 }
			 
			 checkCondition(
					 statiEsteri == null || 
					 StringUtils.isNotBlank(sedeLavoro.getCodCap()),MsgComonl.COMCOME1602.getError());
			 
			 checkNotEmpty(sedeLavoro.getIndirizzo(),"indirizzo sede di lavoro");
			 atLeastoneNotNull = StringUtils.isNotBlank(sedeLavoro.getTelefono()) || StringUtils.isNotBlank(sedeLavoro.getFax()) || StringUtils.isNotBlank(sedeLavoro.getEmail());
			 checkCondition(atLeastoneNotNull, MsgComonl.COMTDIE1416.getError());
		}
	}
	
	protected void chkRapportoVd(Rapporto rapporto,Lavoratore lavoratore) {
		
		checkNotNull(rapporto,"Dati del rapporto");
		
		if(rapporto != null) {
			checkNotNull(rapporto.getDtInizioRapporto(),"data di inizio del rapporto");
			
			TipoContratti tipoContratti = rapporto.getTipoContratti();
			checkModel(rapporto.getTipoContratti(),"tipo di rapporto");
			if (tipoContratti==null)
				return;
			
			TipoContrAmmPerCom tipoContrattoAmmPerCom = controlliDad.getTipoContrAmmPerComByTipoContratto(tipoContratti.getId());
			checkCondition(tipoContrattoAmmPerCom!=null, MsgComonl.COMCOME0019.getError("parameter","tipo contratto")); // messaggio corretto : non ho trovato il record nella tipoContrattoAmmPerCom
			if (tipoContrattoAmmPerCom!=null) {
				checkCondition(tipoContrattoAmmPerCom.getFlgVldVd().equalsIgnoreCase(ComonlConstants.FLAG_S), MsgComonl.COMRAPE0169.getError()); // il tipo contratto non è valido per tracciato VARDATORI
			}
			TipoContrPeriodi  tipoContrPeriodi= controlliDad.getTipoContrPeriodiByTipoContrattoInData(tipoContratti.getId(), rapporto.getDtInizioRapporto());
			checkCondition(tipoContrPeriodi == null,  MsgComonl.COMRAPE0139.getError());// alla data di assunzione indicata il tipo contratto non puo' essere usato.
			
			checkDtFineRapporto(rapporto);
			
			checkDtFinePeriodoFormativo(rapporto);
			
			chekDatiTirocinio(rapporto);
			
			checkNotNull(rapporto.getRetribuzioneCompenso(), "retibuzioneCompenso");
			
			checkDatiInquadramento(rapporto);
			
			// WARNING
			checkConditionWarning(
					!ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgSocioLavoratore()) ||
					ComonlConstants.FLAG_S.equalsIgnoreCase(tipoContrattoAmmPerCom.getFlgSocioLav()), MsgComonl.COMRAPW0154.getError()); // In caso di flag socio lavoratore = Sì deve essere segnalato se il tipo rapporto è ammesso (per verificare se il tipo rapporto è ammesso vedi Tipologie di contratti ammesse per tipo di comunicazione alla voce SOCIO LAVORATORE)
			checkConditionWarning(
					!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0307.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin())
					||getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) >= 9, MsgComonl.COMRAPW0155.getError());// Per la tipologia di rapporto CONTRATTO DI INSERIMENTO LAVORATIVO, la durata minima del contratto non può essere meno di 9 mesi.
			checkConditionWarning(
					!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0307.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin())
					||getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) <= 18, MsgComonl.COMRAPW0156.getError());// Per la tipologia di rapporto CONTRATTO DI INSERIMENTO LAVORATIVO, la durata massima del contratto non può essere superiore a 18 mesi.
			
			String cfDatoreLavoro = controlObject.getDatoreAttuale()==null ? null :  controlObject.getDatoreAttuale().getCodiceFiscale();
			checkConditionWarning( rapporto.getCfSoggPromotoreTirocinio()==null || cfDatoreLavoro==null || 
					!rapporto.getCfSoggPromotoreTirocinio().equalsIgnoreCase(cfDatoreLavoro), MsgComonl.COMRAPW0162.getError()); //Il codice fiscale non può essere uguale a quello del datore di lavoro.
			
		}
	}
	
	private void chkLavoratoreVd(Lavoratore lavoratore) {
		
		if (lavoratore == null) {
			addApiError(MsgComonl.COMLAVE001.getError());
			return;
		}
		
		checkNotEmpty(lavoratore.getCodiceFiscale(), "codice fiscale");
		checkNotEmpty(lavoratore.getCognome(), "cognome");
		checkNotEmpty(lavoratore.getNome(), "nome");
		
		if (ComonlUtility.isVoid(lavoratore.getComuneDom())) {
			checkNotEmpty(null, "comune domicilio");
		}else {
			lavoratore.getComuneDom().setId(null);//forzo la ricerca per codiceMinisteriale
			Comune comune = checkComune(lavoratore.getComuneDom(),"Comune domicilio (del lavoratore "+lavoratore.getCodiceFiscale()+")"); 
			if (comune!=null) {
				lavoratore.setComuneDom(comune);
			}
		}
		
		Datore datoreattuale = controlObject.getDatoreAttuale();
		checkCondition(!datoreattuale.getCodiceFiscale().equalsIgnoreCase(lavoratore.getCodiceFiscale()), MsgComonl.COMRAPE0182.getError("parameter",lavoratore.getCodiceFiscale()));
	}
	
	private void checkDtFineRapporto (Rapporto rapporto) {
		
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
	

	private void checkDtFinePeriodoFormativo(Rapporto rapporto) {
	
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
	
	private void checkDatiInquadramento(Rapporto rapporto) {
		 
		checkModel(rapporto.getCcnl(), "rapporto.getCcnl()");
		if (rapporto.getCcnl()==null || rapporto.getCcnl().getId()==null)
			return;

		// ricerca , check, completamento oggetto
		Ccnl ccnl = checkCcnl(rapporto);
		if (ccnl == null)
			return;
		rapporto.setCcnl(ccnl);
		
		// IstatLivello5 ricerca , check, completamento oggetto
		if (rapporto.getIstatLivello5()!=null && (
				!StringUtils.isBlank(rapporto.getIstatLivello5().getCodIstat2001livello5Min()) ||
				!StringUtils.isBlank(rapporto.getIstatLivello5().getDsComIstat2001livello5())
				)) {
			Istat2001livello5 istat2001livello5 = checkIstat2001livello5(rapporto);
			if(istat2001livello5!=null)
				rapporto.setIstatLivello5(istat2001livello5);
		}
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		
		checkCcnlND(ccnl, tipoContratti);
		
		checkCondition(tipoContratti==null ||
				!tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) ||
				(tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) && ccnl.getSettore().equals(ComonlConstants.CCNL_SETTORE_ENTIPUBBLICI)), MsgComonl.COMRAPE0120.getError());//Se il tipo contratto è A.03.04 - formazione lavoro (solo pubblica amministrazione), il CCNL deve essere di tipo ente pubblico ( settore= Enti pubblici)
		
		checkAllowedValuesFlag(rapporto.getFlgAssunzioneObbligatoria(),"assunzione obbligatoria", false);// Obbligatorio Può valere SI o NO
		
		checkModel(rapporto.getEntePrevidenziale(),"rapporto.entePrevidenziale");
		EntePrevidenziale entePrevidenziale = controlliDad.getEntePrevidenzialeValid(rapporto.getEntePrevidenziale(), new Date());  
		checkModel(entePrevidenziale,"ente previdenziale");
		
		checkCondition(entePrevidenziale == null || !entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) || 
				(entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) && (rapporto.getCodiceEntePrev()==null || rapporto.getCodiceEntePrev().isEmpty())), MsgComonl.COMRAPE0178.getError());//Se “ente  previdenziale” (7.1.c)  = “NESSUN ENTE PREVIDENZIALE”  il campo non deve essere valorizzato.

	}
	
	
	private void checkAltriDati(Rapporto rapporto) {

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
	
	private void chekDatiTirocinio (Rapporto rapporto) {
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		if(!ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)) {
			return;
		}
		String cfSoggPromotoreTirocinio = rapporto.getCfSoggPromotoreTirocinio();
		
		checkNotEmpty(cfSoggPromotoreTirocinio, "CfSoggPromotoreTirocinio");
		checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(cfSoggPromotoreTirocinio,decodificaDad), MsgComonl.COMRAPE0184.getError()); //Controllo Codice fiscale.
	}
	
	private void checkRetribuzioneCompenso(Rapporto rapporto) {
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
	
	protected int getDurataRapportoInMesi(Date dataInizio, Date dataFine) {
		if (dataInizio== null || dataFine == null)
			return 0;
		Period diff = Period.between(
				convertToLocalDateViaInstant(dataInizio).withDayOfMonth(1),
				convertToLocalDateViaInstant(dataFine).withDayOfMonth(1));
		
		return diff.getYears()*12 + diff.getMonths();
	}
	
	protected LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	protected Ccnl checkCcnl(Rapporto rapporto) {
		Ccnl ccnl = controlliDad.getCcnlValid(rapporto.getCcnl(), new Date());
		checkCondition(ccnl!=null && ccnl.getId()!=null, MsgComonl.COMCOME1160.getError());// CCNL non trovato
		if (ccnl == null)
			return null;
		return ccnl;
	}
	protected LivelloRetribuzione checkLivelloRetribuzione(Rapporto rapporto) {
		// livello retribuzione (inquadramento) ricerca , check, completamento oggetto
		LivelloRetribuzione livelloRetribuzione = controlliDad.getLivelloRetribuzioneValid(rapporto.getLivelloRetribuzione(), new Date());
		checkCondition(livelloRetribuzione!=null && livelloRetribuzione.getId()!=null, MsgComonl.COMCOME1140.getError());// Livello retribuzione non trovato
		if (livelloRetribuzione == null)
			return null;
		return livelloRetribuzione;
	}
	protected void checkTipoOrario(TipoOrario tipoOrario, TipoContratti tipoContratti) {
		List<TipoOrario> tipoOrarios = controlliDad.getTipoOrario();
		checkCondition(
				tipoOrarios.stream().filter(x -> x.getId()==tipoOrario.getId()).findAny().orElse(null) !=null , MsgComonl.COMCOME0019.getError("parameter","tipo orario")); //tipoOrario non valido 
		
		TipoContrAmmPerCom tipoContrAmmPerCom = controlliDad.getTipoContrAmmPerComByTipoContratto(tipoContratti.getId());
		checkCondition( !tipoOrario.getCodTipoorarioMin().equalsIgnoreCase(ComonlConstants.TIPO_ORARIO_COD_MIN_N) || tipoContrAmmPerCom == null ||
 				(tipoOrario.getCodTipoorarioMin().equalsIgnoreCase(ComonlConstants.TIPO_ORARIO_COD_MIN_N) && tipoContrAmmPerCom.getFlgTipoOrarioNonDefinito().equalsIgnoreCase(ComonlConstants.FLAG_S)), MsgComonl.COMRAPE0138.getError());//Il tipo orario ‘N’ (Non Definito) è ammesso solo per contratti che nella tabella COM_T_TIPO_CONTR_AMM_PER_COM hanno FLG_TIPO_ORARIO_NON_DEFINITO = ‘S’

	}
	protected Istat2001livello5 checkIstat2001livello5(Rapporto rapporto) {
		// IstatLivello5 ricerca , check, completamento oggetto
		Istat2001livello5 istat2001livello5 = controlliDad.getIstat2001livello5Valid(rapporto.getIstatLivello5(), new Date());
		checkCondition(istat2001livello5!=null && istat2001livello5.getId()!=null, MsgComonl.COMCOME1120.getError());// Qualifica istat non trovata
		if (istat2001livello5 == null)
			return null;
		return istat2001livello5;
	}
	protected Long calcolaRetribuzioneCompenso (
			BigDecimal lordoMensile, BigDecimal nrMensilita, BigDecimal divisoreOrario, BigDecimal nrOreSettMed, String tipoOrario) {

		Long retribuzioneCalcolata = null;
		
		if (tipoOrario.equals(ComonlConstants.TIPO_ORARIO_COD_MIN_F)|| tipoOrario.equals(ComonlConstants.TIPO_ORARIO_COD_MIN_N)) {						
			retribuzioneCalcolata = lordoMensile.multiply(nrMensilita).longValue();
		}else {
			BigDecimal lordoMensileDividedDivisoreOrario = lordoMensile.divide(divisoreOrario,5,RoundingMode.HALF_UP);
			BigDecimal oreSettimanaliPer52SettimaneInUnAnnno = nrOreSettMed.multiply(new BigDecimal(52)).divide(new BigDecimal(12),5,RoundingMode.HALF_UP);
			retribuzioneCalcolata = lordoMensileDividedDivisoreOrario.multiply(oreSettimanaliPer52SettimaneInUnAnnno).multiply(nrMensilita).longValue();
		}		
		return retribuzioneCalcolata;
	}
	protected void checkCcnlND(Ccnl ccnl, TipoContratti tipoContratti) {
		if (ccnl == null || tipoContratti==null)
			return;
		ComonlsParametri param = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_CCNL_ND);
		if (param == null)
			return;
		String valueParam = param.getValoreParametro();
		if (StringUtils.isBlank(valueParam))
			return;
		String[] listTipiContratti = valueParam.split(";");
		if (Arrays.asList(listTipiContratti).contains(tipoContratti.getCodTipoContrattoMin()))
		{
			checkCondition(ComonlConstants.CCNL_ND.equalsIgnoreCase(ccnl.getCodCcnlMin()), MsgComonl.COMRAPE0183.getError());// Ammesso solo il ccnl ND per il tipocontratto selezionato		
		}
	}
	private Comune checkComune(Comune comune, String field) {
		Comune comuneValido = controlliDad.getComuneValid(comune, new Date());
		checkDecodingValid(comuneValido, field, comune.getCodComuneMin(), comune.getDsComTComune());
		return comuneValido;
	}
}
