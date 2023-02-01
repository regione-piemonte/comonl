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
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiRapportoMissione extends ValidatorDatiRapporto
{

	protected Rapporto rapportoM = null;

	public ValidatorDatiRapportoMissione(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione, controlliDad);
	}
	public ValidatorDatiRapportoMissione(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, controlliDad, checkDatiEssenziali, dbComunicazione);
	}
	
	@Override
	public boolean isOk() {
		
		checkDatiEssenziali();
		
		rapportoM = controlObject.getMissione();
		
		checkDtInizioMissione(rapportoM, controlObject.getRapporto());
		
		checkDtFineMissione(rapportoM, controlObject.getRapporto());
		
		checkDatiInquadramento();
		
		checkAltriDati();
		
		checkRetribuzioneCompenso();
		
		return (!isInError());
	}
	
	private void checkDtInizioMissione(Rapporto rapporto, Rapporto rapporotoP)  {
		
		checkNotNull(rapporto.getDtInizioMissione(),"data di inizio missione");
		if (rapporto == null || rapporto.getDtInizioMissione()==null || rapporto.getAziUtilizzatrice()==null || rapporto.getAziUtilizzatrice().getDtInizioContrattoSom()==null) 
			return;
		checkCondition(compareTo(rapporto.getDtInizioMissione(),rapporto.getAziUtilizzatrice().getDtInizioContrattoSom())>=0, MsgComonl.COMMISE0098.getError());// TODO definire messaggio : Deve essere maggiore o uguale a “data inizio contratto somministrazione”  nella sezione “ditta utilizzatrice”.
		checkCondition(compareTo(rapporto.getDtInizioMissione(),rapporotoP.getDtInizioRapporto())>=0, MsgComonl.COMMISE0101.getError());// >= data inizio rapporto P
		
	}
	
	private void checkDtFineMissione(Rapporto rapportoM, Rapporto rapportoP)  {
		
		TipoContratti tipoContratti = rapportoP.getTipoContratti();
		if (!ValidatorUtility.isTipoContrattoIndeterminato(tipoContratti))
			checkNotNull(rapportoM.getDtFineMissione(), "data di fine missione");//per contratto a tempo indeterminato non c'è il vincolo di non valorizzare la data fine missione, quindi verrà accettata sia se valorizzata che null
		
		checkCondition(
				rapportoM == null || rapportoM.getDtFineMissione() == null || rapportoM.getDtInizioMissione()==null ||
						compareTo(rapportoM.getDtFineMissione(),rapportoM.getDtInizioMissione())>=0, MsgComonl.COMMISE0092.getError());// Maggiore o uguale a “data inizio missione”
		
		checkCondition(
				rapportoM.getDtInizioMissione()==null || rapportoM.getDtFineMissione()==null ||
				getDurataRapportoInMesi(rapportoM.getDtInizioMissione(), rapportoM.getDtFineMissione()) <= 600, MsgComonl.COMRAPE0166.getError());// la differenza tra “Data fine” e “Data inizio missione” non deve essere > di 50 anni.
		
		Datore aziUtilizzatrice = rapportoM.getAziUtilizzatrice();
		
		checkCondition(
				rapportoM.getDtFineMissione() == null || aziUtilizzatrice == null || aziUtilizzatrice.getDtFineContrattoSom()==null ||
				compareTo(rapportoM.getDtFineMissione(), aziUtilizzatrice.getDtFineContrattoSom())<=0, MsgComonl.COMMISE0099.getError());//Se valorizzato, deve essere inferiore o uguale alla “data fine contratto somministrazione” nella sezione “ditta utilizzatrice”.
		
		checkCondition(
				rapportoM == null || rapportoM.getDtFineMissione() == null || rapportoP.getDtFineRapporto()==null ||
				compareTo(rapportoM.getDtFineMissione(),rapportoP.getDtFineRapporto())<=0, MsgComonl.COMMISE0102.getError());//inferiore o uguale alla “data fine”  del rapporto P
	}
	
	
	private void checkDatiInquadramento() {
		
		// CCNL ricerca , check, completamento oggetto
		Ccnl ccnl = checkCcnl(rapportoM);
		if(ccnl == null)
			return;
		this.controlObject.getMissione().setCcnl(ccnl);
		
		checkCcnlND(ccnl, controlObject.getRapporto().getTipoContratti());
		
		LivelloRetribuzione livelloRetribuzione = checkLivelloRetribuzione(rapportoM);
		if (livelloRetribuzione == null)
			return;
		this.controlObject.getMissione().setLivelloRetribuzione(livelloRetribuzione);
		
		checkCondition(livelloRetribuzione==null || 
				(livelloRetribuzione!=null && livelloRetribuzione.getCcnl().getId().compareTo(ccnl.getId())==0), MsgComonl.COMRAPE0177.getError());//Tabella  COM_T_LIVELLO_RETRIBUZIONE ( solo validi i livelli previsti per ccnl selezionato).	

		TipoOrario tipoOrario =  rapportoM.getTipoOrario();
		checkModel(tipoOrario, "tipo orario");
		checkTipoOrario(tipoOrario, controlObject.getRapporto().getTipoContratti());
		
		// ore settimanali medie
		boolean oreSettMedObb = tipoOrario != null &&  
				!tipoOrario.getCodTipoorarioMin().equals(ComonlConstants.TIPO_ORARIO_COD_MIN_N) && !tipoOrario.getCodTipoorarioMin().equals(ComonlConstants.TIPO_ORARIO_COD_MIN_F);
		if(oreSettMedObb)
			checkCondition(rapportoM.getNumOreSettMed()!=null,  MsgComonl.COMRAPE0131.getError());//Obbligatorio se “tipo orario” (7.1.i) è diverso da Tempo Pieno o Non Definito
		
		// IstatLivello5 ricerca , check, completamento oggetto
		Istat2001livello5 istat2001livello5 = checkIstat2001livello5(rapportoM);
		if(istat2001livello5!=null)
			this.controlObject.getMissione().setIstatLivello5(istat2001livello5);
		
		checkAllowedValuesFlag(rapportoM.getFlgAssunzioneObbligatoria(), "assunzione obbligatoria", false);// Può valere SI o NO, defualt NO


		checkNotEmpty(rapportoM.getPatInail(),"PAT INAIL");
		checkCharSequenceLength(rapportoM.getPatInail(),"PAT INAIL",8,10);

	}
	private void checkAltriDati() {
		
		CategLavAssObbl categLavAssObbl = rapportoM.getCategLavAssObbl();
		checkCondition(rapportoM.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_N) || 
				(rapportoM.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_S)&& categLavAssObbl!=null), MsgComonl.COMRAPE0145.getError());///Obbligatorio se “Assunzione Obbligatoria” =SI

		checkCondition(categLavAssObbl ==null || 
				ComonlConstants.CATEG_LAV_ASS_OBBL_COD_MIN_PD.equalsIgnoreCase(categLavAssObbl.getCodCategLavAssObblMin()), MsgComonl.COMRAPE0186.getError());// Unica categora accettata PD - PERSONA CON DISABILITA' (ART.1 L.68/99) Nominativa
		
		checkAllowedValuesFlag(rapportoM.getFlgLavoroAgricoltura(), "lavoro in agricoltura", false);
		checkCondition(rapportoM.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapportoM.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && rapportoM.getGiornateLavPreviste()==null), MsgComonl.COMRAPE0179.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì
		checkCondition(rapportoM.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapportoM.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && StringUtils.isEmpty(rapportoM.getDsAttivitaAgricoltura())), MsgComonl.COMMISE0100.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì

		checkNotEmpty(rapportoM.getDsAttivita(),"Descrizione attivita");
		
		checkAllowedValuesFlag(rapportoM.getFlgRischioSilicAsbe(), "Rischio silicosi/asbestosi", false);
		
		checkNotEmpty(rapportoM.getDsVoceTariffa1(),"Voce tariffa 1");
		checkCharSequenceLength(rapportoM.getDsVoceTariffa1(),"Voce tariffa 1",4,4);
		checkNotEmpty(rapportoM.getDsAttivita(),"Descrizione attivita");
		
		
	}	
	// spostare su ValidatorDatiRapporto ?  
	private void checkRetribuzioneCompenso() {
		checkNotNull(rapportoM.getRetribuzioneCompenso(), "retribuzione lorda annuale");
		if (rapportoM.getRetribuzioneCompenso()==null)
			return;
		
		ComonlsParametri paramRetribuzione0Ammessa = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RETRIBUZIONE_0_AMMESSA);
		boolean retribuzione0Ammessa = paramRetribuzione0Ammessa != null && paramRetribuzione0Ammessa.getValoreParametro().equals(ComonlConstants.FLAG_S);
		
		if (retribuzione0Ammessa) {
			ComonlsParametri p = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_RETRIBUZIONE_0_CONTRATTI);
			if (p==null)
				retribuzione0Ammessa = false;
			else { 
				retribuzione0Ammessa = Arrays.asList(p.getValoreParametro().split(";")).contains(controlObject.getRapporto().getTipoContratti().getCodTipoContrattoMin());
			}
		}
		checkCondition(retribuzione0Ammessa || 
				(!retribuzione0Ammessa && (rapportoM.getRetribuzioneCompenso()!=null) && rapportoM.getRetribuzioneCompenso().compareTo(BigDecimal.ZERO)==1), MsgComonl.COMRAPE0168.getError());// non è possibile mettere una retribuzione a 0
		
		LivelloRetribuzione livelloRetribuzione = rapportoM.getLivelloRetribuzione();
		if (livelloRetribuzione!=null && livelloRetribuzione.getId()!=null && rapportoM.getTipoOrario()!=null) {
			
			/*“WARNING. Attenzione! La retribuzione inserita è minore del valore ammesso calcolato <retribuzione calcolata>. 
			 * Verificare i dati inseriti. Procedendo con la conferma il valore sarà comunque salvato dal sistema, sotto la responsabilità dell’utente che registra la comunicazione obbligatoria”.*/
			Long retribuzioneCalcolata = calcolaRetribuzioneCompenso (
					livelloRetribuzione.getLordoMensile(),livelloRetribuzione.getMensilita(),livelloRetribuzione.getDivisoreOrario(),rapportoM.getNumOreSettMed(), rapportoM.getTipoOrario().getCodTipoorarioMin());
			checkConditionWarning(rapportoM.getRetribuzioneCompenso().intValue()>=retribuzioneCalcolata, MsgComonl.COMRAPW0055.getError("parameter",retribuzioneCalcolata.toString()));
			
		}
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(controlObject.getMissione().getDtInizioMissione(), dbObject.getMissione().getDtInizioMissione(), "data inizio missione");
		checkDatoEssenzialeDate(controlObject.getMissione().getDtFineMissione(), dbObject.getMissione().getDtFineMissione(), "data fine missione");
		checkDatoEssenzialeModel(controlObject.getMissione().getTipoOrario(), dbObject.getMissione().getTipoOrario(), "tipo orario");
	}
}
