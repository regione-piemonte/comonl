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

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;

public class ValidatorDatiTrasferimentoDistacco extends Validator<Comunicazione> {
	
	private final ControlliDad controlliDad;
	private final DecodificaDad decodificaDad;
	
	private Rapporto rapporto = null;
	private Rapporto dbRapporto = null;
	
	public ValidatorDatiTrasferimentoDistacco(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(comunicazione);
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}
	public ValidatorDatiTrasferimentoDistacco(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad, boolean checkDatiEssenziale, Comunicazione dbComunicazione) {
		super(comunicazione, checkDatiEssenziale, dbComunicazione);
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}
	
	@Override
	public boolean isOk() {
		
		boolean inSomministrazioneConMissione = controlObject.getTipoSomministrazione()!= null && (
				controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE || controlObject.getTipoSomministrazione().getId()==ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);

		if (inSomministrazioneConMissione) {
			rapporto = controlObject.getMissione();
			dbRapporto = checkDatiEssenziali ? dbObject.getMissione() : null;
		}else {
			rapporto = controlObject.getRapporto();
			dbRapporto = checkDatiEssenziali ? dbObject.getRapporto() : null;
		}
		
		boolean skipControlCobAnnullamento = skipControlCobAnnullamento(); 
		
		checkDatiEssenziali();
		
		Rapporto rapportoP = controlObject.getRapporto();
		Datore datoreDistaccatario = rapporto.getDatoreDistaccatario();
		
		Trasformazionerl trasformazionerl = controlliDad.getTrasformazionerlValid(rapporto.getTrasformazionerl(), new Date());
		checkModel(trasformazionerl, "Tipo spostamento");
		// TODO : dobbiamo settare la trasformazionerl sull'oggetto? Potrebbe arrivare con id a null ?
		
		boolean distacco =  ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_DL.equals(trasformazionerl.getCodTrasformazionirlMin());
		boolean trasferimento = ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_TL.equals(trasformazionerl.getCodTrasformazionirlMin());
		
		Date dtTrasferimentoDistacco = rapporto.getDtTrasformazione();
		Date dtFineDistacco = rapporto.getDtFineRapporto();
		
		checkNotNull(dtTrasferimentoDistacco, "data trasferimento distacco");
		
		checkCondition(dtTrasferimentoDistacco == null || 
				compareTo(dtTrasferimentoDistacco,rapportoP.getDtInizioRapporto())>=0, MsgComonl.COMTDIE1414.getError());
		
		checkCondition(controlObject.getMissione() == null || dtTrasferimentoDistacco == null || 
				compareTo(dtTrasferimentoDistacco,controlObject.getMissione().getDtInizioMissione())>0, MsgComonl.COMTDIE1417.getError());

		if(distacco) {
			checkCondition(dtFineDistacco!=null , MsgComonl.COMTDIE1402.getError());
			checkCondition(dtTrasferimentoDistacco == null || dtFineDistacco == null || 
					compareTo(dtFineDistacco,dtTrasferimentoDistacco)>=0, MsgComonl.COMTDIE1412.getError());
			checkAllowedValuesFlag(rapporto.getFlgDistaccoParziale(), "flag distacco parziale", !distacco);
			checkAllowedValuesFlag(rapporto.getFlgDistaccoSuAziEstera(), "flag distacco estero", !distacco);
			if (ComonlConstants.FLAG_N.equalsIgnoreCase(rapporto.getFlgDistaccoSuAziEstera())) {
				checkNotEmpty(datoreDistaccatario.getCodiceFiscale(),"codice fiscale datore di lavoro distaccatario");
			}
			checkNotEmpty(datoreDistaccatario.getDsDenominazioneDatore(), "denominazione datore di lavoro distaccatario");
			checkCondition(datoreDistaccatario.getCodiceFiscale()==null || 
					CodiceFiscale.getInstance().controllaCodiceFiscaleSoggetto(datoreDistaccatario.getCodiceFiscale(), decodificaDad),MsgComonl.COMTDIE1403.getError());
			// TODO Deve essere diverso dal codice fiscale lavoratore (ed eventualmente del lavoratore co-obbligato 2).
			Atecofin atecofin = controlliDad.getAtecofinValid(datoreDistaccatario.getAtecofin(), new Date());
			checkModel (atecofin, "attivita' ATECO");
			if(atecofin != null)
				datoreDistaccatario.setAtecofin(atecofin);
			
			boolean flgDistaccoEsteroS = rapporto.getFlgDistaccoSuAziEstera()!=null && rapporto.getFlgDistaccoSuAziEstera().equalsIgnoreCase(ComonlConstants.FLAG_S);
			
			checkCondition(flgDistaccoEsteroS || StringUtils.isNotEmpty(rapporto.getPatInail()), MsgComonl.COMTDIE1404.getError());
			checkCharSequenceLength(rapporto.getPatInail(),"PAT INAIL",!flgDistaccoEsteroS?8:0,!flgDistaccoEsteroS?10:0);
			checkCondition(skipControlCobAnnullamento || flgDistaccoEsteroS || datoreDistaccatario.getIdAziendaSilp()!=null, MsgComonl.COMTDIE1415.getError());//Obbligatorio in caso di “flg_distacco_estero” = No.Contiene l’ID anagrafico dell’azienda restituito dai servizi di ricerca azienda in SILP.
			
			SedeLavoro sedeLegale = datoreDistaccatario.getSedeLegale();
			StatiEsteri statiEsteri = controlliDad.getStatiEsteriValid(sedeLegale.getStatiEsteri(), new Date());
			if(statiEsteri != null) {
				datoreDistaccatario.getSedeLegale().setStatiEsteri(statiEsteri);
			}
			Comune comune = controlliDad.getComuneValid(sedeLegale.getComune(), new Date());
			
			checkCondition(statiEsteri!=null || comune !=null, MsgComonl.COMTDIE1410.getError()); // comune o stato estero DEVE essere valorizzato
			
			if (statiEsteri == null)
			{
				checkCondition(flgDistaccoEsteroS || comune != null, MsgComonl.COMTDIE1405.getError());// TODO verificare il messaggio: la provincia non c'è ...
				checkCondition(comune == null || StringUtils.isNotBlank(sedeLegale.getCodCap()), MsgComonl.COMTDIE1405.getError());// se c'è il comune deve esserci il cap
			}

			checkNotEmpty(sedeLegale.getIndirizzo(),"indirizzo sede di lavoro");
			boolean atLeastoneNotNull = StringUtils.isNotBlank(sedeLegale.getTelefono()) || StringUtils.isNotBlank(sedeLegale.getFax()) || StringUtils.isNotBlank(sedeLegale.getEmail());
			checkCondition(atLeastoneNotNull, MsgComonl.COMTDIE1416.getError());
			//TODO check validita mail.
			
			// WARNING
			checkConditionWarning(
					StringUtils.isBlank(datoreDistaccatario.getCodiceFiscale()) || controlObject.getDatoreAttuale() == null || StringUtils.isBlank(controlObject.getDatoreAttuale().getCodiceFiscale()) ||
					!datoreDistaccatario.getCodiceFiscale().equalsIgnoreCase(controlObject.getDatoreAttuale().getCodiceFiscale())
					, MsgComonl.COMTDIW1413.getError());
			
		}
		if (trasferimento && controlObject.getTipoSomministrazione()==null) {
			
			 SedeLavoro sedeLavoroPrecedente = rapporto.getSedeLavoroPrecedente();
			 
			 boolean atLeastoneNotNull = sedeLavoroPrecedente.getStatiEsteri()!=null || sedeLavoroPrecedente.getComune()!=null;
			 checkCondition(atLeastoneNotNull, MsgComonl.COMTDIE1410.getError());// TODO verificare il messaggio: la provincia non c'è ...
			 
			 if(sedeLavoroPrecedente.getComune()!=null && 
					 (sedeLavoroPrecedente.getComune().getId()!=null || sedeLavoroPrecedente.getComune().getCodComuneMin()!=null)) {
				 Comune comune = controlliDad.getComuneValid(sedeLavoroPrecedente.getComune(), new Date());
				 checkModel(comune, "comune");
			 }
			 if(sedeLavoroPrecedente.getStatiEsteri()!=null && 
					 (sedeLavoroPrecedente.getStatiEsteri().getId()!=null || sedeLavoroPrecedente.getStatiEsteri().getCodNazioneMin()!=null)) {
				 StatiEsteri statiEsteri = controlliDad.getStatiEsteriValid(sedeLavoroPrecedente.getStatiEsteri(), new Date());
				 checkModel(statiEsteri, "stato estero");
			 }
			 checkNotEmpty(sedeLavoroPrecedente.getIndirizzo(),"indirizzo");
		}
		
		return (!isInError());
	}
	
	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenzialeDate(rapporto.getDtTrasformazione(), dbRapporto.getDtTrasformazione(), "data trasferimento/distacco");
		Trasformazionerl trasformazionerlValid = controlliDad.getTrasformazionerlValid(dbRapporto.getTrasformazionerl(), new Date());
		if (trasformazionerlValid!=null)
			checkDatoEssenzialeModel(rapporto.getTrasformazionerl(), dbRapporto.getTrasformazionerl(), "tipo spostamento");
		checkDatoEssenzialeDate(rapporto.getDtFineRapporto(), dbRapporto.getDtFineRapporto(), "data fine distacco");
		checkDatoEssenziale(rapporto.getDatoreDistaccatario()!=null ? rapporto.getDatoreDistaccatario().getCodiceFiscale() : null,
				dbRapporto.getDatoreDistaccatario()!=null ? dbRapporto.getDatoreDistaccatario().getCodiceFiscale() : null, "codice fiscale datore distaccatario");
	}
	
	private boolean skipControlCobAnnullamento () {
		if (!ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN.equalsIgnoreCase(controlObject.getTipoComunicazioneTu().getCodTipoComunicazioneMin()))
			return false;
		if (controlObject.getRapporto()==null || controlObject.getRapporto().getDtInizioRapporto()==null)
			return false;
		if (dbObject==null || dbObject.getDtInsert()==null)
			return false;
		
		ComonlsParametri param = controlliDad.getParametroAbilitatoByDescrizione(ComonlConstants.COMONLS_PARAMETRI_DATA_AVVIO_COMONL);
		if (param==null)
			return false;
		
		Date dataAvvioComonl = ComonlDateUtils.parseDate(param.getValoreParametro());
		return compareTo(dbObject.getDtInsert(), dataAvvioComonl) < 0; 
	}
}


