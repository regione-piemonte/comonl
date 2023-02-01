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
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class ValidatorDatiRapportoUnilav extends ValidatorDatiRapporto {

	private final DecodificaDad decodificaDad;
	public ValidatorDatiRapportoUnilav(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(comunicazione, controlliDad, false, null);
		this.decodificaDad=decodificaDad;
	}
	
	public ValidatorDatiRapportoUnilav(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, controlliDad, checkDatiEssenziali, dbComunicazione);
		this.decodificaDad=decodificaDad;
	}		
	
	@Override
	public boolean isOk() {
		
		checkDatiEssenziali();
		
		super.isOk(); // validazione comune
		
		checkModel(rapporto.getTipoContratti(),"tipo di rapporto");
		
		/*TIPOLOGIA CONTRATTUALE*/
		// TODO se non è passato l'id, devo recuperare l'oggetto per codice ministeriale
		// TODO codice comune ai tracciati UL , US, VD spostare in metodo protected della super classe
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		TipoContrAmmPerCom tipoContrattoAmmPerCom = controlliDad.getTipoContrAmmPerComByTipoContratto(tipoContratti.getId());
		checkCondition(tipoContrattoAmmPerCom!=null, MsgComonl.COMCOME0019.getError("parameter","tipo contratto")); // messaggio corretto : non ho trovato il record nella tipoContrattoAmmPerCom
		if (tipoContrattoAmmPerCom!=null) {
			checkCondition(tipoContrattoAmmPerCom.getFlgVldUl().equalsIgnoreCase(ComonlConstants.FLAG_S), MsgComonl.COMRAPE0169.getError()); // il tipo contratto non è valido per tracciato UNILAV
		}
		TipoContrPeriodi  tipoContrPeriodi= controlliDad.getTipoContrPeriodiByTipoContrattoInData(tipoContratti.getId(), rapporto.getDtInizioRapporto());
		checkCondition(tipoContrPeriodi == null,  MsgComonl.COMRAPE0139.getError());// alla data di assunzione indicata il tipo contratto non puo' essere usato.
		
		boolean tipoContrattoRipartito = ValidatorUtility.isTipoContrattoRipartito(tipoContratti);
		checkCondition(!tipoContrattoRipartito || (tipoContrattoRipartito && controlObject.getLavoratoreCoObbligato()!=null),  MsgComonl.COMRAPE0170.getError());//se contratto RIPARTITO deve esserci il lavoratore COOBBLIGATO

		boolean isMultiLavoratore = controlObject.getLavoratori()!=null && controlObject.getLavoratori().size()>1;
		checkCondition(!isMultiLavoratore ||
				(isMultiLavoratore && !tipoContrattoRipartito && !ValidatorUtility.isTipoContrattoApprendistato(tipoContratti) && !ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)), MsgComonl.COMRAPE0140.getError());//In caso di Contratto ripartito, Apprendistato e tirocinio non è previsto il multi lavoratore

		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		boolean isProroga = tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA);
		checkCondition(!isProroga ||
				(isProroga && tipoContrattoAmmPerCom.getFlgVldPro().equalsIgnoreCase(ComonlConstants.FLAG_S)
//						tipoContratti.getFlgForma().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_FLGFORMA_D)
						), MsgComonl.COMRAPE0171.getError());//Per le proroghe solo rapporti con flgVlPro = S
		
		checkDatiTrasformazione();
		
		checkDtFineRapporto();
		
		checkDtFinePeriodoFormativo();

		checkLavoroStagionale();
		
		checkAllowedValuesFlag(rapporto.getFlgTirocinio(), "generato da tirocinio",false);//Obbligatorio Può valere SI o NO, defualt NO

		chekDatiTirocinio();
		
		checkDatiInquadramento();
		
		checkAltriDati();

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
		checkConditionWarning(getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto()) <= 36, MsgComonl.COMRAPW0157.getError());// Per UL: la differenza tra “Data fine” e “Data inizio rapporto” non deve essere > di 36 mesi.
		
		String cfDatoreLavoro = controlObject.getDatoreAttuale()==null ? null :  controlObject.getDatoreAttuale().getCodiceFiscale();
		checkConditionWarning( rapporto.getCfSoggPromotoreTirocinio()==null || cfDatoreLavoro==null || 
				!rapporto.getCfSoggPromotoreTirocinio().equalsIgnoreCase(cfDatoreLavoro), MsgComonl.COMRAPW0162.getError()); //Il codice fiscale non può essere uguale a quello del datore di lavoro.
		
		checkEtaLavoratore();
		
		return (!isInError());
	}

	// ULD/US
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
			
			- Se presente, deve essere maggiore della Data inizio rapporto (7.1.a)
		 * */
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		boolean tipoContrattoApprendistato = ValidatorUtility.isTipoContrattoApprendistato(tipoContratti);
		
		checkCondition(rapporto.getDtFinePeriodoFormativo()==null || 
				(rapporto.getDtFinePeriodoFormativo()!=null && rapporto.getDtFinePeriodoFormativo().compareTo(rapporto.getDtInizioRapporto())>0 ), MsgComonl.COMRAPE0112.getError()); //Se presente, deve essere maggiore della Data inizio rapporto 
		checkCondition(!tipoContrattoApprendistato || rapporto.getDtFinePeriodoFormativo()!=null, MsgComonl.COMRAPE0111.getError());//Campo da compilare obbligatoriamente solo in caso di tipologia contrattuale di Apprendistato
		checkCondition(tipoContrattoApprendistato ||
				(!tipoContrattoApprendistato && rapporto.getDtFinePeriodoFormativo()==null), MsgComonl.COMRAPE0174.getError());//Per le altre tipologie contrattuali invece non deve essere compilato

		//Solo per UNILAV: nei casi in cui è obbligatorio controllare che esso coincida con la Data fine rapporto (7.1.b) solo se il flag 'Lavoro stagionale (7.1.y ) = SI.
		checkCondition(!ValidatorUtility.isLavoroStagionale(rapporto) || !tipoContrattoApprendistato ||
				(ValidatorUtility.isLavoroStagionale(rapporto) && tipoContrattoApprendistato && rapporto.getDtFinePeriodoFormativo().compareTo(rapporto.getDtFineRapporto())==0), MsgComonl.COMRAPE0113.getError());//Solo per UNILAV: nei casi in cui è obbligatorio controllare che esso coincida con la Data fine rapporto (7.1.b) solo se il flag 'Lavoro stagionale (7.1.y ) = SI.
	}
	
	// UL/VD
	private void checkLavoroStagionale() {
		
		checkAllowedValuesFlag(rapporto.getFlgLavStagionale(), "lavoro stagionale", false);// Può valere SI o NO, defualt NO
		
		checkCondition(
				!ValidatorUtility.isLavoroStagionale(rapporto) ||
				(rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0200)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0201)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_B0100)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_B0200)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_B0300)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0308)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0310)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0402)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0802)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0702)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0502)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_C0300)||
				rapporto.getTipoContratti().getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_H0200)
				), MsgComonl.COMRAPE0175.getError());//se contratto != A.02.00, A.02.01, B.03.00, C.03.00, A.03.08, A.03.09, A.03.10, A.04.02, A.08.02, A.07.02, A.05.02 Allora flgStagionale NON puo’ essere SI  DEVE ESSERE NO
 	}
	
	//UL
	private void chekDatiTirocinio () {
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		if(!ValidatorUtility.isTipoContrattoTirocinio(tipoContratti)) {
			return;
		}
		String categoriaPerTirocinioA[] = {"01","02","04","05","06","07"};
		String categoriaPerTirocinioB[] = {"01","02","03","08"};
		String categoriaPerTirocinioC[] = {"09"};
		
		TipologiaTirocinio tipologiaTirocinio = rapporto.getTipologiaTirocinio();
		checkModel(tipologiaTirocinio, "tipologia tirocinio");//Obbligatorio se tipo contratto “TIROCINIO”
		
		CategTirocinante categTirocinante = rapporto.getCategTirocinante();
		checkModel(categTirocinante, "categoria tirocinante");//Obbligatorio se tipo contratto “TIROCINIO”
		
		if (tipologiaTirocinio.getCodTipologiaTirocinioMin().equalsIgnoreCase(ComonlConstants.CATEG_TIROCINANTE_A)) {
			checkAllowedValues(categTirocinante.getCodCategTirocinanteMin(), MsgComonl.COMRAPE0130.getError(), categoriaPerTirocinioA);//Se la tipologia di tirocinio (COM_T_TIPOLOGIA_TIROCINIO) è “A” sono ammessi solo i valori di “categoria tirocinante” (COM_T_CATEG_TIROCINANTE) uguali a 01-02-04-05-06-07.
		}
		if (tipologiaTirocinio.getCodTipologiaTirocinioMin().equalsIgnoreCase(ComonlConstants.CATEG_TIROCINANTE_B)) {
			checkAllowedValues(categTirocinante.getCodCategTirocinanteMin(), MsgComonl.COMRAPE0130.getError(), categoriaPerTirocinioB);//Se la tipologia di tirocinio tirocinio (COM_T_TIPOLOGIA_TIROCINIO )  è “B” sono ammessi solo i valori di “categoria tirocinante” (COM_T_CATEG_TIROCINANTE)  uguali a 01-02-03-08.
		}
		if (tipologiaTirocinio.getCodTipologiaTirocinioMin().equalsIgnoreCase(ComonlConstants.CATEG_TIROCINANTE_C)) {
			checkAllowedValues(categTirocinante.getCodCategTirocinanteMin(), MsgComonl.COMRAPE0130.getError(), categoriaPerTirocinioC);//Se la tipologia di tirocinio tirocinio (COM_T_TIPOLOGIA_TIROCINIO )  è “C” sono ammessi solo i valori di “categoria tirocinante” (COM_T_CATEG_TIROCINANTE)  uguali a 09
		}
		
		/*  Genera WARNING
			Per comunicazioni di Assunzione, Cessazione e Distacco (unici casi in cui compare la data fine rapporto),
			In caso di scelta delle seguenti categorie tirocinanti: 04, 05, 06, 07
			la durata massima del rapporto è di 6 mesi.
			In caso di scelta di: 01, 03, 08
			la durata massima del rapporto è di 12 mesi.
			In caso di scelta di: 02
			la durata massima del rapporto è di 24 mesi.
			in caso di scelta 99
			la durata massima del rapporto è 3 mesi.
			
			NOTA: in caso di Distacco, poiché in questo caso la data fine distacco viene riportata nella data fine rapporto ed è quella a cui fare riferimento per verificare la durata del rapporto, si aggiunge in fondo al messaggio il seguente:
			
			Per adeguare la durata del rapporto, modificare opportunamente la Data fine distacco.
			*
			*/
		TipoComunicazione tipoComunicazione = controlObject.getTipoComunicazione();
		Trasformazionerl trasformazionerl = rapporto.getTrasformazionerl();
		if (tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)||
			tipoComunicazione.getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE) ||
			(trasformazionerl != null && trasformazionerl.getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.COD_MIN_TIPO_CO_DISTACCO))
			) {
			
			int durataRapportoInMesi  = getDurataRapportoInMesi(rapporto.getDtInizioRapporto(), rapporto.getDtFineRapporto());
			
			boolean max6 = 
					ComonlConstants.CATEG_TIROCINANTE_04.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin()) || 
					ComonlConstants.CATEG_TIROCINANTE_05.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin()) || 
					ComonlConstants.CATEG_TIROCINANTE_06.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin()) || 
					ComonlConstants.CATEG_TIROCINANTE_07.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin());
			boolean max12 = 
					ComonlConstants.CATEG_TIROCINANTE_01.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin()) || 
					ComonlConstants.CATEG_TIROCINANTE_03.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin()) || 
					ComonlConstants.CATEG_TIROCINANTE_08.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin());
			boolean max24 = ComonlConstants.CATEG_TIROCINANTE_02.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin());
			boolean max3 = ComonlConstants.CATEG_TIROCINANTE_99.equalsIgnoreCase(categTirocinante.getCodCategTirocinanteMin());
			
			checkConditionWarning(!max3 || durataRapportoInMesi<=3, MsgComonl.COMRAPW0161.getError());
			checkConditionWarning(!max6 || durataRapportoInMesi<=6, MsgComonl.COMRAPW0158.getError());
			checkConditionWarning(!max12 || durataRapportoInMesi<=12, MsgComonl.COMRAPW0159.getError());
			checkConditionWarning(!max24 || durataRapportoInMesi<=24, MsgComonl.COMRAPW0160.getError());
			
		}
		
		TipoEntePromTirocinio tipoEntePromTirocinio = rapporto.getTipoEntePromTirocinio(); 
		checkModel(tipoEntePromTirocinio, "tipo ente promotore tirocinio");
		checkCondition(tipoEntePromTirocinio == null || !tipoEntePromTirocinio.getTipSigla().equals("CPI") ||
				(tipoEntePromTirocinio.getTipSigla().equals("CPI") && rapporto.getCpi()!=null), MsgComonl.COMRAPE0129.getError());// CPI Obbligatorio se  “Tipo ente promotore tirocinio “=’CPI’
		
		String cfSoggPromotoreTirocinio = rapporto.getCfSoggPromotoreTirocinio();
		
		checkCondition(StringUtils.isNotBlank(cfSoggPromotoreTirocinio),MsgComonl.COMRAPE0128.getError()); 
		
		checkCondition(CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(cfSoggPromotoreTirocinio, decodificaDad), MsgComonl.COMRAPE0184.getError()); //Controllo Codice fiscale.
	}
	
	private void checkDatiInquadramento() {
 
		// CCNL ricerca , check, completamento oggetto
		Ccnl ccnl = checkCcnl(rapporto);
		if (ccnl == null)
			return;
		this.controlObject.getRapporto().setCcnl(ccnl);
		
		TipoContratti tipoContratti = rapporto.getTipoContratti();
		
		checkCcnlND(ccnl, tipoContratti);
		
		checkCondition(tipoContratti==null ||
				!tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) ||
				(tipoContratti.getCodTipoContrattoMin().equals(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0304) && ccnl.getSettore().equals(ComonlConstants.CCNL_SETTORE_ENTIPUBBLICI)), MsgComonl.COMRAPE0120.getError());//Se il tipo contratto è A.03.04 - formazione lavoro (solo pubblica amministrazione), il CCNL deve essere di tipo ente pubblico ( settore= Enti pubblici)
		
		// livello retribuzione (inquadramento) ricerca , check, completamento oggetto
		LivelloRetribuzione livelloRetribuzione = checkLivelloRetribuzione(rapporto);
		if (livelloRetribuzione == null)
			return;
		this.controlObject.getRapporto().setLivelloRetribuzione(livelloRetribuzione);
		checkCondition(livelloRetribuzione==null || 
				(livelloRetribuzione!=null && livelloRetribuzione.getCcnl().getId().compareTo(ccnl.getId())==0), MsgComonl.COMRAPE0177.getError());//Tabella  COM_T_LIVELLO_RETRIBUZIONE ( solo validi i livelli previsti per ccnl selezionato).	
		
		TipoOrario tipoOrario =  rapporto.getTipoOrario();
		checkModel(tipoOrario, "tipo orario");
		checkTipoOrario(tipoOrario, tipoContratti);
		
		boolean oreSettMedObb = !tipoOrario.getCodTipoorarioMin().equals(ComonlConstants.TIPO_ORARIO_COD_MIN_N) && !tipoOrario.getCodTipoorarioMin().equals(ComonlConstants.TIPO_ORARIO_COD_MIN_F);
		if(oreSettMedObb)
			checkCondition(rapporto.getNumOreSettMed()!=null,  MsgComonl.COMRAPE0131.getError());//Obbligatorio se “tipo orario” (7.1.i) è diverso da Tempo Pieno o Non Definito
		
		checkRetribuzioneCompenso();
		
		// IstatLivello5 ricerca , check, completamento oggetto
		Istat2001livello5 istat2001livello5 = checkIstat2001livello5(rapporto);
		if(istat2001livello5!=null)
			this.controlObject.getRapporto().setIstatLivello5(istat2001livello5);

		checkAllowedValuesFlag(rapporto.getFlgAssunzioneObbligatoria(),"assunzione obbligatoria", false);// Obbligatorio Può valere SI o NO
		
		checkNotEmpty(rapporto.getPatInail(),"PAT INAIL");
		checkCharSequenceLength(rapporto.getPatInail(),"PAT INAIL",8,10);
		
		EntePrevidenziale entePrevidenziale = controlliDad.getEntePrevidenzialeValid(rapporto.getEntePrevidenziale(), new Date());  
		checkModel(entePrevidenziale,"ente previdenziale");
		
		checkCondition(entePrevidenziale == null || !entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) || 
				(entePrevidenziale.getCodEntePrevidenzialeMin().equalsIgnoreCase(ComonlConstants.ENTE_PREVIDENZIALE_COD_MIN_29) && (rapporto.getCodiceEntePrev()==null || rapporto.getCodiceEntePrev().isEmpty())), MsgComonl.COMRAPE0178.getError());//Se “ente  previdenziale” (7.1.c)  = “NESSUN ENTE PREVIDENZIALE”  il campo non deve essere valorizzato.

	}
	
	private void checkRetribuzioneCompenso() {
		checkNotNull(rapporto.getRetribuzioneCompenso(), "retribuzione lorda annuale");
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

	
		CategLavAssObbl categLavAssObbl = rapporto.getCategLavAssObbl();
		checkCondition(rapporto.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_N) || 
				(rapporto.getFlgAssunzioneObbligatoria().equalsIgnoreCase(ComonlConstants.FLAG_S) && categLavAssObbl!=null), MsgComonl.COMRAPE0145.getError());///Obbligatorio se “Assunzione Obbligatoria” =SI
	
		boolean atLeastoneNotNull = rapporto.getNumeroAttoLegge68()!=null || rapporto.getDtLegge68()!=null || (rapporto.getTipoAttoL68()!=null && rapporto.getTipoAttoL68().getId()!=null);
		boolean atLeastoneNull = rapporto.getNumeroAttoLegge68()==null || rapporto.getDtLegge68()==null || (rapporto.getTipoAttoL68()==null || rapporto.getTipoAttoL68().getId()==null);
		boolean fieldsLegge68Obbl = (categLavAssObbl != null &&
									 (categLavAssObbl.getCodCategLavAssObblMin().equalsIgnoreCase(ComonlConstants.CATEG_LAV_ASS_OBBL_COD_MIN_PD) ||
									 categLavAssObbl.getCodCategLavAssObblMin().equalsIgnoreCase(ComonlConstants.CATEG_LAV_ASS_OBBL_COD_MIN_CP))
									) || atLeastoneNotNull;
		
		if(fieldsLegge68Obbl) {
			checkModel(rapporto.getTipoAttoL68(), "Legge 68 tipo atto");
			checkNotNull(rapporto.getDtLegge68(), "Legge 68 data nulla osta/convenzione");
			checkNotEmpty(rapporto.getNumeroAttoLegge68(), "Legge 68 num. atto");
		}
		
		checkCondition(!atLeastoneNotNull || 
				(atLeastoneNotNull && !atLeastoneNull),MsgComonl.COMRAPE0124.getError()); // o tutti o nessuno
		
		//**********
		
		checkAllowedValuesFlag(rapporto.getFlgSocioLavoratore(), "socio lavoratore",false);//Obbligatorio Può valere SI o NO, defualt NO
		checkAllowedValuesFlag(rapporto.getFlgLavoroAgricoltura(), "lavoro in agricoltura",false);//Obbligatorio Può valere SI o NO, defualt NO
		checkCondition(
				rapporto.getFlgLavoroAgricoltura() == null ||
				rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && rapporto.getGiornateLavPreviste()==null), MsgComonl.COMRAPE0179.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì
		checkCondition(
				rapporto.getFlgLavoroAgricoltura() == null ||
				rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_S) || 
				(rapporto.getFlgLavoroAgricoltura().equalsIgnoreCase(ComonlConstants.FLAG_N) && rapporto.getTipoLavorazione()==null), MsgComonl.COMRAPE0180.getError());//Valorizzato, ma non obbligatorio, se flag “Lavoro in agricoltura” = sì
		checkAllowedValuesFlag(rapporto.getFlgLavInMobilita(), "lavoratore in mobilita",false);//Obbligatorio Può valere SI o NO, defualt NO
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtInizioRapporto(), dbObject.getRapporto().getDtInizioRapporto(), "data inizio rapporto");
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtFineRapporto(), dbObject.getRapporto().getDtFineRapporto(), "data fine rapporto");
		checkDatoEssenzialeDate(controlObject.getRapporto().getDtFinePeriodoFormativo(), dbObject.getRapporto().getDtFinePeriodoFormativo(), "data fine periodo formativo");
		
		// dato essenziale da verificare solo se ad oggi valido
		TipoOrario tipoOrarioValido = controlliDad.getTipoOrarioValid(dbObject.getRapporto().getTipoOrario(), new Date());
		if (tipoOrarioValido!=null)
			checkDatoEssenzialeModel(controlObject.getRapporto().getTipoOrario(), dbObject.getRapporto().getTipoOrario(), "tipo orario");
		
		TipoContratti tipoContrattiValido = controlliDad.getTipoContrattiValid(dbObject.getRapporto().getTipoContratti(), new Date());
		if (tipoContrattiValido!=null)
			checkDatoEssenzialeModel(controlObject.getRapporto().getTipoContratti(), dbObject.getRapporto().getTipoContratti(), "tipologia contrattuale");
	}
	
	
}
