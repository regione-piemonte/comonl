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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.missione;


import java.util.Date;
import java.util.List;
import java.util.Vector;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.CalcoloRetribuzioneHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Format;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;
import it.csi.spicom.dto.InizioMissioneDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.MissioneDTO;

/**
 * Regole che si applicano punti 1.1 e 1.2
 * 
 * @author 70493
 * 
 */
public class RegoleValidazioneMissione_US extends ControlloAstrattoBloccante {



	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception {
		if (isUNISOM()) 
		{
			MissioneDTO dtoMissione = comunicazione.getMissione();
			InizioRapportoUniSommDTO inizioRapportoUniSommDTO = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());

			DittaUtilizzatriceDTO aziendaUtilizzatrice = comunicazione.getDittaUtilizzatrice();

			// **************************** INIZIO
			// ****************************************************************
			if (dtoMissione != null) 
			{
				InizioMissioneDTO dtoInizioMissione = dtoMissione.getDatiInizioMissione();
				String tipoContratto = inizioRapportoUniSommDTO.getTipologiaContrattuale();

				if (dtoInizioMissione != null) 
				{
					// data fine missione
					Date dataInizioMissione = dtoInizioMissione.getDataInizioMissione();
					Date dataFineMissione = dtoInizioMissione.getDataFineMissione();
					Date dataInizioRapporto = inizioRapportoUniSommDTO.getDataInizio();
					Date dataFineRapporto = inizioRapportoUniSommDTO.getDataFine();
					Date dataInizioContrattoSomministrazione = aziendaUtilizzatrice.getDataInizioSomministrazione();
					Date dataFineContrattoSomministrazione = aziendaUtilizzatrice.getDataFineSomministrazione();



					if(dataFineContrattoSomministrazione != null){

						if (dataFineMissione == null) {
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_INIZIO_DATA_FINE_MISSIONE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}

					}


					//					}

					if(DateUtilities.isData1InferioreDiData2(dataInizioMissione, dataInizioContrattoSomministrazione)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_DATA_INIZIO_MISSIONE_INFERIORE_ALLA_DATA_INIZIO_CONTRATTO_SOMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if(DateUtilities.isData1InferioreDiData2(dataInizioMissione, dataInizioRapporto)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_DATA_INIZIO_MISSIONE_INFERIORE_ALLA_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if (dataFineMissione != null) {

						if(dataFineContrattoSomministrazione != null){

							if(DateUtilities.isData1InferioreDiData2(dataFineContrattoSomministrazione, dataFineMissione)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_DATA_FINE_CONTRATTO_SOMMINISTRAZIONE_INFERIORE_ALLA_DATA_FINE_MISSIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}							
						}

						if (dataInizioMissione != null  && !DateUtilities.isData1MinoreOUgualeAData2(dataInizioMissione, dataFineMissione) )
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_INIZIO_DATA_FINE_MISSIONE_INFERIORE_DATA_INIZIO_MISSIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						if(isDifferenzaDataFineDataInizioMaggioreDi50Anni(dataFineMissione, dataInizioMissione)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_DIFFERENZA_DATA_FINE_DATA_INIZIO_SUPERIORE_A_50_ANNI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}						

						if(dataFineRapporto!= null && !DateUtilities.isData1MinoreOUgualeAData2(dataFineMissione, dataFineRapporto)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_INIZIO_DATA_FINE_INFERIORE_ALLA_DATA_FINE_MISSIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}

					// pat inail
					String dsPatInail = dtoInizioMissione.getDsPatInail();
					if (isVuoto(dsPatInail)) 
					{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_INIZIO_PATINAIL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					} else 
					{
						boolean esitoPatInail = isLunghezzaStringaMinEMaxValida(dsPatInail, 8, 10);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoPatInail, ErrorConstants.MISSIONE_INIZIO_PATINAIL_FORMATO_LUNGHEZZA_MIN_MAX_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					// rischio silicosi
					String flagRischioSilicosi = dtoInizioMissione.getFlgRischioSilicosiAsbestosi();
					if( isValorizzato(flagRischioSilicosi))
					{
						boolean esitoFlagRischio = isFlagValido_ValoriAmmessi_SI_o_NO(flagRischioSilicosi);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoFlagRischio, ErrorConstants.MISSIONE_INIZIO_FLAG_RISCHIO_SILICOSI_ASBETOSI_FORMATO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					//Adeguamenti 2019 - agevolazioni non ammesse
					// agevolazioni
					List elencoAgevolazioni = dtoInizioMissione.getAgevolazioni();
					if (elencoAgevolazioni != null && !elencoAgevolazioni.isEmpty()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_AGEVOLAZIONI_NON_AMMESSE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
					// tipo orario
					String tipoOrario = dtoInizioMissione.getTipoOrario();
					// ore settimanali medie
					Long oreSettimanaliMedie = dtoInizioMissione.getNumOreSettimanaliMedie();
					controllaERegistraErroreTipoOrarioENumOreSettimanali(
							tipoContratto,
							tipoOrario,
							oreSettimanaliMedie,
							ErrorConstants.MISSIONE_INIZIO_TIPO_ORARIO_NON_VALIDO, 
							ErrorConstants.MISSIONE_INIZIO_ORE_SETTIMANALI_MEDIE_VALORIZZATE,
							ErrorConstants.MISSIONE_INIZIO_ORE_SETTIMANALI_MEDIE_NON_VALORIZZATE,
							ErrorConstants.MISSIONE_TIPO_ORARIO_NON_AMMESSO_PER_TIPO_CONTRATTO,
							true, 
							commonDad, 
							decodificaDad, 
							elencoErrori, 
							null
							);


					// qualifica prof
					String qualProf = dtoInizioMissione.getQualificaProfessionale();
					if( isValorizzato(qualProf))
					{
						boolean esitoQualifica = false;
						Istat2001livello5 istat = (Istat2001livello5) decodificaDad.getTfromMin(Istat2001livello5.class, qualProf, null);
						esitoQualifica = (istat != null && istat.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoQualifica, ErrorConstants.MISSIONE_INIZIO_QUALIFICA_PROFESSIONALE_ISTAT_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}


					// ccnl
					String ccnl = dtoInizioMissione.getCcnlApplicato();
					if( isValorizzato(ccnl))
					{
						boolean esitoCcnl = false;
						Ccnl ccnlDeco = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, ccnl, null);
						esitoCcnl = (ccnlDeco != null && ccnlDeco.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCcnl, ErrorConstants.MISSIONE_INIZIO_CCNL_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if((UtilConstant.FLAG_NO).equalsIgnoreCase(dtoInizioMissione.getFlgLavoroAgricoltura())){
						if( dtoInizioMissione.getDescrizioneAttivitaAgricoltura()!= null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
						if(dtoInizioMissione.getNumGiornateLavorativePreviste()!=null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_NUM_GG_PREVISTI_AGR_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
					}
					//adeguamenti 2019
					controlliAdeguamenti2019Retribuzione(dtoInizioMissione,tipoOrario, oreSettimanaliMedie, tipoContratto, commonDad, decodificaDad, elencoErrori);
				}
			}
		}	
	}

	private void controlliAdeguamenti2019Retribuzione(
			InizioMissioneDTO dtoInizioMissione, String tipoOrario,
			Long oreSettimanaliMedie, String tipoContratto, 
			CommonDad commonDad, 
			DecodificaDad decodificaDad, 
			List<ApiError> elencoErrori) throws Exception {

		//SE il tipo contratto   A.03.04 il CCNL deve avere il settore "eEnte pubblico"
		if (dtoInizioMissione.getCcnlApplicato() != null && tipoContratto != null) {
			if (UtilConstant.TIPO_CONTRATTO_FORMAZIONE_LAVORO_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(tipoContratto)
					&& !CalcoloRetribuzioneHelper.isCCNLEntePubblico(dtoInizioMissione.getCcnlApplicato(),decodificaDad )) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.CCNL_NON_VALIDO_PER_CONTRATTO_SOLO_PUBBLICA_AMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				return;
			}
		}
		//1- se il tipo di contratto selezionato  e'  compreso nella lista il CCNL deve essere valorizzato a ND
		String tipiContrattoCcnlND = commonDad.getParametroCommaxById(ParametriConstants.VINCOLI_LISTA_TIPI_CONTRATTO_CCNL_AMMESSO_ND).getValoreParametro();

		Vector vCont = new Vector();
		if (tipiContrattoCcnlND.indexOf(";") != -1) {
			vCont = Util.tokenizerForParameterValues(tipiContrattoCcnlND, ";");
		}
		if (!CalcoloRetribuzioneHelper.isTipoContrattoVincolatoCongruenteCCNL(dtoInizioMissione.getCcnlApplicato(),tipoContratto, vCont)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_CCNL_INCONGRUENTE_TIPO_CONTRATTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			return;
		}

		//Adeguamenti 2019 - 
		controllaCampoObbligatorio(Format.toString(dtoInizioMissione.getRetribuzione()), ErrorConstants.MISSIONE_RETRIBUZIONE_NON_VALORIZZATA, commonDad, elencoErrori);
		controllaCampoObbligatorio(dtoInizioMissione.getLivelloInquadramento(), ErrorConstants.MISSIONE_LIVELLO_INQUADRAMENTO_NON_VALORIZZATO, commonDad, elencoErrori);

		// 05.02.2020 - 72160:
		/*
		 * Se il parametro controlli.bloccante.retribuzione.ugualeZero.attivo (id=28) = S
		 * LA RETRIBUZIONE NON PUO' ESSERE = 0
		 * 
		 * */
		if (!CalcoloRetribuzioneHelper.checkValoreRetribuzioneIfControlloAttivoAndDeterminaSeValida(dtoInizioMissione.getRetribuzione(), tipoContratto, commonDad) ) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RETRIBUZIONE_UGUALE_ZERO_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			return;
		}

		//
		if (dtoInizioMissione.getCcnlApplicato() != null 
				&& dtoInizioMissione.getLivelloInquadramento() != null
				&& !"".equals(dtoInizioMissione.getLivelloInquadramento()) 
				&& !"".equals(dtoInizioMissione.getCcnlApplicato())) {

			Long idLivello = CalcoloRetribuzioneHelper.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(dtoInizioMissione.getCcnlApplicato(), dtoInizioMissione.getLivelloInquadramento(), decodificaDad);
			controllaCampoObbligatorio(Format.toString(idLivello), ErrorConstants.MISSIONE_LIVELLO_INQUADRAMENTO_NON_VALIDO_PER_CCNL, commonDad, elencoErrori);
			// 05.02.2020 - 72160:
			/*
			 * Se il parametro controlli.retribuzioneCalcolata.retribuzioneInserita.attivo (id=30) = S
			 * EFFETTUIAMO IL CONTROLLO SULLA VALIDITA' DELLA RETRIBUZIONE INSERITA IN BASE AL CALCOLO TABELLARE
			 * 
			 * */
			String controllaRetribuzioneCalcolata = commonDad.getParametroCommaxById(ParametriConstants.CONTROLLI_RETRIBUZIONECALCOLATA_RETRIBUZIONEINSERITA_ATTIVO).getValoreParametro();
			if ("S".equalsIgnoreCase(controllaRetribuzioneCalcolata) && idLivello != null) {
				Long retribuzioneCalcolata = CalcoloRetribuzioneHelper.determinaTuplaLivelloAndCalcolaRetribuzione(idLivello, tipoOrario,oreSettimanaliMedie,dtoInizioMissione.getRetribuzione(), decodificaDad);
				if (retribuzioneCalcolata == null) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_IMPOSSIBILE_CALCOLARE_RETRIBUZIONE_MANCANZA_CAMPI_OBBLIGATORI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				} else {
					if (dtoInizioMissione.getRetribuzione().longValue() < retribuzioneCalcolata.longValue()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.MISSIONE_RETRIBUZIONE_INSERITA_NON_VALIDA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}
			}
		}
	}
}
