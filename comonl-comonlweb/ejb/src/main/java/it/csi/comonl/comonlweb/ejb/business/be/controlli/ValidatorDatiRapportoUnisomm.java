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

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiRapportoUnisomm extends ValidatorDatiRapporto {

	public ValidatorDatiRapportoUnisomm(Comunicazione comunicazione, ControlliDad controlliDad) {
		this(comunicazione, controlliDad, false, null);
	}
	
	public ValidatorDatiRapportoUnisomm(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, controlliDad, checkDatiEssenziali, dbComunicazione);
	}	
	
	@Override
	public boolean isOk() {
		
		checkDatiEssenziali();
		
		super.isOk(); // validazione comune
		
		checkModel(rapporto.getTipoContratti(),"tipoContratti");
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		TipoContrAmmPerCom tipoContrattoAmmPerCom = controlliDad.getTipoContrAmmPerComByTipoContratto(tipoContratti.getId());
		checkCondition(tipoContrattoAmmPerCom!=null, MsgComonl.COMCOME0019.getError("parameter","tipo contratto")); // messaggio corretto : non ho trovato il record nella tipoContrattoAmmPerCom
		if (tipoContrattoAmmPerCom!=null) {
			checkCondition(tipoContrattoAmmPerCom.getFlgVldSom().equalsIgnoreCase(ComonlConstants.FLAG_S), MsgComonl.COMRAPE0169.getError()); // il tipo contratto non è valido per tracciato UNISOMM
		}
		TipoContrPeriodi  tipoContrPeriodi= controlliDad.getTipoContrPeriodiByTipoContrattoInData(tipoContratti.getId(), rapporto.getDtInizioRapporto());
		checkCondition(tipoContrPeriodi == null,  MsgComonl.COMRAPE0139.getError());// alla data di assunzione indicata il tipo contratto non puo' essere usato.

		boolean isMultiLavoratore = controlObject.getLavoratori()!=null && controlObject.getLavoratori().size()>1;
		checkCondition(!isMultiLavoratore ||
				(isMultiLavoratore && !ValidatorUtility.isTipoContrattoRipartito(tipoContratti) && !ValidatorUtility.isTipoContrattoApprendistato(tipoContratti) && !ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)), MsgComonl.COMRAPE0140.getError());//In caso di Contratto ripartito, Apprendistato e tirocinio non è previsto il multi lavoratore
		
		boolean isProroga = controlObject.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA);
		boolean inSomministrazioneConMissione = controlObject.getTipoSomministrazione() != null && controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE;
		
		checkCondition(!isProroga || inSomministrazioneConMissione ||
				(isProroga && !inSomministrazioneConMissione && tipoContrattoAmmPerCom.getFlgVldPro().equalsIgnoreCase(ComonlConstants.FLAG_S)), MsgComonl.COMRAPE0171.getError());//Per le proroghe solo rapporti con forma DETERMINATA.
		
		checkDatiTrasformazione();
		
		checkDtFineRapporto();
		
		checkCondition(getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) <= 600, MsgComonl.COMRAPE0166.getError());// Per US: la differenza tra “Data fine” e “Data inizio rapporto” non deve essere > di 50 anni

		checkDtFinePeriodoFormativo();
		
		checkDatiInquadramento();
		
		checkAltriDati();

		// warning
		checkConditionWarning(
				!ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgSocioLavoratore()) ||
				ComonlConstants.FLAG_S.equalsIgnoreCase(tipoContrattoAmmPerCom.getFlgSocioLav()), MsgComonl.COMRAPW0154.getError()); // In caso di flag socio lavoratore = Sì deve essere segnalato se il tipo rapporto è ammesso (per verificare se il tipo rapporto è ammesso vedi Tipologie di contratti ammesse per tipo di comunicazione alla voce SOCIO LAVORATORE)
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0307.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin())
				||getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) >= 9, MsgComonl.COMRAPW0155.getError());// Per la tipologia di rapporto CONTRATTO DI INSERIMENTO LAVORATIVO, la durata minima del contratto non può essere meno di 9 mesi.
		checkConditionWarning(
				!ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0307.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin())
				||getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) <= 18, MsgComonl.COMRAPW0156.getError());// Per la tipologia di rapporto CONTRATTO DI INSERIMENTO LAVORATIVO, la durata massima del contratto non può essere superiore a 18 mesi.
		
		checkEtaLavoratore();
		
		return (!isInError());
	}
	
	// verifica coerenza tra dati rapporto e tipo trasformazione
	private void checkDatiTrasformazione() {
		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		boolean isTrasformazione = tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE);
		boolean isTrasformazioneTempoIndeterminato =  rapporto.getTrasformazionerl() != null &&
				(rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_AI)
				||  rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FI)
				||  rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_II));
		
		boolean isTrasformazioneFF =  rapporto.getTrasformazionerl() != null && rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF);
		boolean isTrasformazioneDI =  rapporto.getTrasformazionerl() != null && ComonlConstants.TRASFORMAZIONERL_DI.equalsIgnoreCase(rapporto.getTrasformazionerl().getCodTrasformazionirlMin());
		
		checkCondition(!isTrasformazione ||
				(isTrasformazione && !isTrasformazioneTempoIndeterminato) ||
				(isTrasformazione && isTrasformazioneTempoIndeterminato && tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0100)), MsgComonl.COMRAPE0121.getError());//Per le trasformazioni se si seleziona la trasformazione “da Tempo Determinato a Tempo Indeterminato” dovrà essere inserita tipo contratto (2.2)  A.01.00
		
		checkCondition( !isTrasformazione ||
				(isTrasformazione && !isTrasformazioneFF) ||
				(isTrasformazioneFF && ValidatorUtility.isTipoContrattoApprendistato(tipoContratti)), MsgComonl.COMRAPE0172.getError());//trasformazione FF è ammesso se il tipo contratto è una tipologia con forma “indeterminato” , A.03.08, A.03.09, A.03.10
		
		checkCondition( (!isTrasformazioneTempoIndeterminato && !isTrasformazioneFF &&  !isTrasformazioneDI) ||
				( (isTrasformazioneTempoIndeterminato || isTrasformazioneFF || isTrasformazioneDI)
				&& !ValidatorUtility.isLavoroStagionale(rapporto)), MsgComonl.COMRAPE0173.getError());//inoltre DI, AI, FI, II, FF non sono ammessi se FLAG_STAGIONALE = Sì.

		checkCondition(!isTrasformazioneDI ||
				(
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0100.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0402.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0802.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0702.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0502.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_M0200.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()) ||
						ComonlConstants.TIPO_CONTRATTI_COD_MIN_N0100.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin())
				 )
				, MsgComonl.COMTRAE1388.getError());// se la trasformazione è DI i contrati ammessi sono : A01, A.04.02,A.08.02,A.07.02, A.05.02,M.02.00,N.01.00 

	}
	
	// UL/US/VD
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
		
		checkCondition(rapporto.getDtFineRapporto()==null || controlObject.getMissione() ==null || controlObject.getMissione().getAziUtilizzatrice() ==null ||
				(rapporto.getDtFineRapporto()!=null && controlObject.getMissione().getAziUtilizzatrice().getDtFineContrattoSom() != null), MsgComonl.COMRAPE0136.getError()); //Per US (con MISSIONE) : se c'è la data fine rapporto allora deve esserci la data fine contratto somm dell'azienda utilizzatrice della missione 
	}
	
	//US/UL/VD
	private void checkDtFinePeriodoFormativo() {
		
		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		if (tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)
			) {
			return;
		}
		
		/*- Campo da compilare obbligatoriamente solo in caso di tipologia contrattuale di Apprendistato : A.03.08, A.03.09, A.03.10 . Per le altre tipologie contrattuali invece non deve essere compilato
			Solo per UNILAV: nei casi in cui è obbligatorio controllare che esso coincida con la Data fine rapporto (7.1.b) solo se il flag 'Lavoro stagionale (7.1.y ) = SI.
			- Se presente, deve essere maggiore della Data inizio rapporto (7.1.a)
		 * */
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		boolean tipoContrattoApprendistato = tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308)||
				 tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309)||
				 tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310);
		
		checkCondition(rapporto.getDtFinePeriodoFormativo()==null || 
				(rapporto.getDtFinePeriodoFormativo()!=null && rapporto.getDtFinePeriodoFormativo().compareTo(rapporto.getDtInizioRapporto())>0 ), MsgComonl.COMRAPE0112.getError()); //Se presente, deve essere maggiore della Data inizio rapporto 
		checkCondition(!tipoContrattoApprendistato || rapporto.getDtFinePeriodoFormativo()!=null, MsgComonl.COMRAPE0111.getError());//Campo da compilare obbligatoriamente solo in caso di tipologia contrattuale di Apprendistato
		checkCondition(tipoContrattoApprendistato ||
				(!tipoContrattoApprendistato && rapporto.getDtFinePeriodoFormativo()==null), MsgComonl.COMRAPE0174.getError());//Per le altre tipologie contrattuali invece non deve essere compilato

	}
	
	private void checkDatiInquadramento() {
		 
		checkModel(rapporto.getEntePrevidenziale(),"rapporto.entePrevidenziale");
		EntePrevidenziale entePrevidenziale = controlliDad.getEntePrevidenzialeValid(rapporto.getEntePrevidenziale(), new Date());  
		checkModel(entePrevidenziale,"ente previdenziale");
		
		checkCondition(entePrevidenziale == null || !entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) || 
				(entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) && (rapporto.getCodiceEntePrev()==null || rapporto.getCodiceEntePrev().isEmpty())), MsgComonl.COMRAPE0178.getError());//Se “ente  previdenziale” (7.1.c)  = “NESSUN ENTE PREVIDENZIALE”  il campo non deve essere valorizzato.

	}
	
	private void checkAltriDati() {
		checkNotEmpty(rapporto.getNumMatricolaLav(), "num. matricola lavoratore");
		checkAllowedValuesFlag(rapporto.getFlgLavInMobilita(), "lavoratore in mobilita",false);//Obbligatorio Può valere SI o NO, defualt NO
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtInizioRapporto(), dbObject.getRapporto().getDtInizioRapporto(), "data inizio rapporto");
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtFineRapporto(), dbObject.getRapporto().getDtFineRapporto(), "data fine rapporto");
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtFinePeriodoFormativo(), dbObject.getRapporto().getDtFinePeriodoFormativo(), "data fine periodo formativo");
		
		TipoContratti tipoContrattiValido = controlliDad.getTipoContrattiValid(dbObject.getRapporto().getTipoContratti(), new Date());
		if (tipoContrattiValido!=null)
			checkDatoEssenzialeModel(controlObject.getRapporto().getTipoContratti(), dbObject.getRapporto().getTipoContratti(), "tipologia contrattuale");
	}
}
