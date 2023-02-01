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

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;

public class ValidatorDatiTrasformazione extends Validator<Comunicazione> {

	private final ControlliDad controlliDad;
	
	Rapporto rapporto = null;
	Rapporto dbRapporto = null;	
	
	public ValidatorDatiTrasformazione(Comunicazione comunicazione, ControlliDad controlliDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
	}
	public ValidatorDatiTrasformazione(Comunicazione comunicazione, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad = controlliDad;
	}
	
	@Override
	public boolean isOk() {
		
		Rapporto rapportoP = controlObject.getRapporto();
		Rapporto missione  = controlObject.getMissione();
		TipoSomministrazione tipoSomministrazione = controlObject.getTipoSomministrazione(); // magari non serve
		
		boolean inSomministrazioneConMissione = controlObject.getTipoSomministrazione()!= null && (
				controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
		if (inSomministrazioneConMissione) {
			rapporto = missione;
			dbRapporto = checkDatiEssenziali ? dbObject.getMissione() : null; 
		}else {
			rapporto = rapportoP;
			dbRapporto = checkDatiEssenziali ? dbObject.getRapporto() : null;
		}
		
		checkDatiEssenziali();
		
		Datore aziUtilizzatrice = rapporto.getAziUtilizzatrice();
		TipoContratti tipoContratti = rapportoP.getTipoContratti();	
		TipoOrario tipoOrario = rapporto.getTipoOrario();
		
		Date dtTrasformazione = rapporto.getDtTrasformazione();
		
		checkNotNull(dtTrasformazione, " data trasformazione");
		
		checkCondition(dtTrasformazione == null || dtTrasformazione.compareTo(rapportoP.getDtInizioRapporto())>0, MsgComonl.COMTRAE1381.getError());
		
		checkCondition(missione == null || tipoSomministrazione == null ||
				(tipoSomministrazione != null && tipoSomministrazione.getId()!=ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE) ||
				(tipoSomministrazione.getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE && dtTrasformazione.compareTo(missione.getDtInizioMissione())>0
				&& (missione.getDtFineMissione() == null || dtTrasformazione.compareTo(missione.getDtFineMissione())<=0) 
				), MsgComonl.COMTRAE1386.getError()); // se codice variazione 3.01 o 3.03, deve essere maggiore alla data inizio missione e inferiore o uguale alla “data fine rapporto” con agenzia (se not null) 
		
		checkCondition(aziUtilizzatrice == null ||
				(aziUtilizzatrice != null && (aziUtilizzatrice.getDtFineContrattoSom() == null || dtTrasformazione.compareTo(aziUtilizzatrice.getDtFineContrattoSom())<=0) 
				), MsgComonl.COMTRAE1387.getError()); // Deve essere inferiore o uguale alla “data fine contratto somministrazione” (se not null) nella sezione “ditta utilizzatrice”.
		
		Trasformazionerl trasformazionerl = controlliDad.getTrasformazionerlValid(rapporto.getTrasformazionerl(), new Date());
		checkModel(trasformazionerl, "Tipo trasformazione");
		// TODO : dobbiamo settare la trasformazionerl sull'oggetto? Potrebbe arrivare con id a null ? 
		
		boolean isTrasformazioneTempoIndeterminato = rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_AI)
				||  rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FI)
				||  rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_II);
		boolean isTrasformazioneFF =  rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF);
		boolean isTrasformazioneDP =  ComonlConstants.TRASFORMAZIONERL_DP.equalsIgnoreCase(rapporto.getTrasformazionerl().getCodTrasformazionirlMin());
		boolean isTrasformazioneDI =  ComonlConstants.TRASFORMAZIONERL_DI.equalsIgnoreCase(rapporto.getTrasformazionerl().getCodTrasformazionirlMin());
		
		checkCondition(!isTrasformazioneTempoIndeterminato ||
				(isTrasformazioneTempoIndeterminato && tipoContratti.getCodTipoContrattoMin().equalsIgnoreCase(ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0100)), MsgComonl.COMTRAE1382.getError());// se il tipo trasformazione ha codice incluso in DI, AI, FI, II (cioè trasformazioni a tempo indeterminato) bisogna controllare che il tipo contratto (2.2) sia solo A.01.00 (cioè tempo indeterminato)
		
		checkCondition( !rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF)||
				(rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF) && ValidatorUtility.isTipoContrattoApprendistato(tipoContratti)), MsgComonl.COMRAPE0172.getError());// il tipo  trasformazione FF è ammesso se il tipo contratto è una tipologia con forma “indeterminato” e cioè A.03.08, A.03.09, A.03.10;
		
		checkCondition( 
				(!isTrasformazioneTempoIndeterminato && !isTrasformazioneFF && !isTrasformazioneDI) || 
				(( isTrasformazioneTempoIndeterminato || isTrasformazioneFF || isTrasformazioneDI) && !ValidatorUtility.isLavoroStagionale(rapporto))
				, MsgComonl.COMRAPE0173.getError());//inoltre DI, AI, FI, II, FF non sono ammessi se FLAG_STAGIONALE = Sì.
		
		checkCondition(!isTrasformazioneDP ||
				ComonlConstants.TIPO_CONTRATTI_COD_MIN_A0309.equalsIgnoreCase(tipoContratti.getCodTipoContrattoMin()), MsgComonl.COMTRAE1382.getError());// se la trasformazione è DP unica tipologia di contratto ammessa è: A.03.09
		
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
		
		//Per le trasformazioni: Il tipo orario deve essere valorizzato coerentemente con lo stato successivo alla trasformazione;
		
		boolean isTrasformazioneOrarioPP = controlObject.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) && 
				rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_PP);
		boolean isTrasformazioneOrarioTP = controlObject.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) && 
				rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_TP);
		
		checkCondition (!isTrasformazioneOrarioPP || tipoOrario == null ||
				(isTrasformazioneOrarioPP && tipoOrario.getCodTipoorarioMin().equalsIgnoreCase(ComonlConstants.TIPO_ORARIO_COD_MIN_F)), MsgComonl.COMRAPE0123.getError());//se la trasformazione è PP – TRASFORMAZIONE DA TEMPO PARZIALE A TEMPO PIENO il tipo orario dovrà essere indicato F – TEMPO PIENO; 
		
		checkCondition (!isTrasformazioneOrarioTP || tipoOrario == null ||
				(isTrasformazioneOrarioTP && !tipoOrario.getCodTipoorarioMin().equalsIgnoreCase(ComonlConstants.TIPO_ORARIO_COD_MIN_F)), MsgComonl.COMRAPE0122.getError());//se la trasformazione è TP – TRASFORMAZIONE DA TEMPO PIENO A TEMPO PARZIALE il tipo orario dovra è essere valorizzato con un codice diverso da F – TEMPO PIENO
		
		boolean checkComunicazioneEntePubblico = rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_PV) || rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FI); 
		checkCondition( !checkComunicazioneEntePubblico ||
				(checkComunicazioneEntePubblico &&  controlObject.getDatoreAttuale().getFlgPubAmm().equalsIgnoreCase(ComonlConstants.FLAG_S)), MsgComonl.COMTRAE1383.getError());// e) il motivo di trasformazione “PV” (cioè Progressione verticale nella P.A.) e 'FI' (cioè  da contratto formazione a tempo indeterminato) sarà accettata solo se la comunicazione si riferisce ad un Ente Pubblico (per verificare se è un Ente Pubblico vedi par. Ente Pubblico)
		
		checkCondition( !rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF) ||
				(rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_FF) && !ValidatorUtility.isLavoroStagionale(rapporto) 
						&& rapportoP.getDtInizioRapporto().compareTo(ComonlDateUtils.parseDate("26/10/2011"))>=0 ), MsgComonl.COMTRAE1385.getError()); // f) il motivo di trasformazione 'FF' (cioè “fine periodo formativo”) è ammesso con flag stagionale NO e con data inizio rapporto >= 26/10/2011.
		
		checkCondition( !rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_AI) ||
				(rapporto.getTrasformazionerl().getCodTrasformazionirlMin().equalsIgnoreCase(ComonlConstants.TRASFORMAZIONERL_AI) && rapportoP.getDtInizioRapporto().compareTo(ComonlDateUtils.parseDate("25/10/2011"))<=0 ), MsgComonl.COMTRAE1384.getError()); // g) il motivo di trasformazione 'AI' è ammesso solo se la data inizio rapporto è <= 25/10/2011.
		
		return (!isInError());
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(rapporto.getDtTrasformazione(), dbRapporto.getDtTrasformazione(), "data trasformazione");
		Trasformazionerl trasformazionerlValid = controlliDad.getTrasformazionerlValid(dbRapporto.getTrasformazionerl(), new Date());
		if (trasformazionerlValid!=null)
			checkDatoEssenzialeModel(rapporto.getTrasformazionerl(), dbRapporto.getTrasformazionerl(), "cod. trasformazione");
		checkDatoEssenzialeDate(rapporto.getDtTrasformazione(), dbRapporto.getDtTrasformazione(), "data trasformazione");
	}
}
