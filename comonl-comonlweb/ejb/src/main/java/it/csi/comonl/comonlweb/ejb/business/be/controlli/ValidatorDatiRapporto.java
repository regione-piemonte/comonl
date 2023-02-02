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
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiRapporto extends Validator<Comunicazione>
{

	protected final ControlliDad controlliDad;
	protected Rapporto rapporto = null;

	public ValidatorDatiRapporto(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
	}
	public ValidatorDatiRapporto(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad = controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		/* TODO
		 * 
		 * se tipoCOntrattoRipartito -> selezionati 2 lavoratori 
		 * 
		 */
		rapporto = controlObject.getRapporto();
		checkNotNull(rapporto.getDtInizioRapporto(),"data di inizio del rapporto");
		
		checkDtInizioRapporto(rapporto.getDtInizioRapporto(), controlObject.getLavoratori());// dtInizioRapporto>dtNascita lavoratore

		return (!isInError());
	}
	
	private void checkDtInizioRapporto(Date dtInizioRapporto, List<Lavoratore> lavoratori)  {
		if (dtInizioRapporto == null ) 
			return;
		if (lavoratori == null || lavoratori.isEmpty()) 
			return;
		
		for(Lavoratore lavoratore : lavoratori) {
			checkCondition(dtInizioRapporto.compareTo(lavoratore.getDtNascita())>=0, MsgComonl.COMRAPE0141.getError("parameter",lavoratore.getCodiceFiscale()));
		}
	}
	
	protected int getDurataRapportoInMesi(java.util.Date dataInizio, java.util.Date dataFine) {
		if (dataInizio== null || dataFine == null)
			return 0;
		
		Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(dataInizio);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(dataFine);
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = (diffYear * 12) + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return Math.abs(diffMonth);
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
		if (tipoOrario==null)
			return;
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
		}else {
			checkCondition(!ComonlConstants.CCNL_ND.equalsIgnoreCase(ccnl.getCodCcnlMin()), MsgComonl.COMRAPE0185.getError());// NON Ammesso il ccnl ND per il tipocontratto selezionato
		}
	}
	protected void checkEtaLavoratore() {
		//TODO il lavoratore ha meno di 16 anni. Può quindi essere assunto solo come 'Apprendista'.
		// PTO 5 - 6 - 7 dello SFU
		
		Rapporto rapporto = controlObject.getRapporto();
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		if (rapporto == null)
			return;
		
		boolean inMobilita = ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgLavInMobilita());
		for (Lavoratore lavoratore : controlObject.getLavoratori()) {
			checkEtaLavoratore (lavoratore, inMobilita, tipoContratti);
		}
		if (controlObject.getLavoratoreCoObbligato()!=null) {
			checkEtaLavoratore (controlObject.getLavoratoreCoObbligato(), inMobilita, tipoContratti);
		}
	}
	
	private void checkEtaLavoratore (Lavoratore lavoratore, boolean inMobilita, TipoContratti tipoContratti) {
		
		int etaLavoratore = ValidatorUtility.getEtaLavoratore(lavoratore.getDtNascita(), rapporto.getDtInizioRapporto());
		
		checkConditionWarning(
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || 
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
				etaLavoratore>=16, MsgComonl.COMRAPW0152.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 6
		checkConditionWarning(
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || 
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
				etaLavoratore<=75, MsgComonl.COMRAPW0153.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 7
		
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita || 
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore>=15), MsgComonl.COMRAPW0146.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 10
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita ||
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore<26), MsgComonl.COMRAPW0147.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 11
		
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita || 
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore>=17), MsgComonl.COMRAPW0148.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 12
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita ||
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore<30), MsgComonl.COMRAPW0149.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 13
		
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita || 
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore>=18), MsgComonl.COMRAPW0150.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 14
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) || inMobilita ||
				(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) && !inMobilita && etaLavoratore<30), MsgComonl.COMRAPW0151.getError("parameter",lavoratore.getCodiceFiscale()));// SFU PTO 15
		
	}
	
	
}
