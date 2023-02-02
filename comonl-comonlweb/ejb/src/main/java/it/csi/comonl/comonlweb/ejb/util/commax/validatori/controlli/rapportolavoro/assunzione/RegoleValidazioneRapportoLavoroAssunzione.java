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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.assunzione;

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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatiInizioRapportoDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.util.TUConstants;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneRapportoLavoroAssunzione extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{		

		if ( isApplicaRegoleValidazioneRapportoLavoroAssunzione() ) {

			if(isUNILAV()){			
				validazioneUnilav(elencoErrori, commonDad, decodificaDad);
			}
			if(isUNISOM()){
				validazioneUnisomm(elencoErrori, commonDad, decodificaDad);
			}
		}
	}

	private boolean isApplicaRegoleValidazioneRapportoLavoroAssunzione() {
		return tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)
				|| tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE_CONTESTUALE_A_INIZIO_MISSIONE );
	}

	private void validazioneUnilav(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws  Exception {
		String tipoContratto = comunicazione.getDatiInizio().getTipologiaContrattuale();			

		InizioRapportoUnilavDTO inizioRappUnilav = ((InizioRapportoUnilavDTO)comunicazione.getDatiInizio());				
		Date dataFineRapporto = inizioRappUnilav.getDataFine();
		Date dataInizioRapporto = inizioRappUnilav.getDataInizio();

		controlliComuniAUnilavUnisom(inizioRappUnilav, elencoErrori, commonDad, decodificaDad);
		//Adeguamenti 2019 - agevolazioni non ammesse
		// agevolazioni
		List elencoAgevolazioni =((InizioRapportoUnilavDTO)comunicazione.getDatiInizio()).getAgevolazioni();
		if (elencoAgevolazioni != null && !elencoAgevolazioni.isEmpty()) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.DATI_ASSUNZIONE_AGEVOLAZIONI_NON_AMMESSE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		// flag socio lavoratore

		if( isValorizzato(inizioRappUnilav.getFlgSocioLavoratore()))
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(inizioRappUnilav.getFlgSocioLavoratore()), 
					ErrorConstants.DATI_ASSUNZIONE_FLG_SOCIO_LAVORATORE_ERRATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


		if (isValorizzato(inizioRappUnilav.getCcnlApplicato())) {
			boolean esitoCcnl = false;
			Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, inizioRappUnilav.getCcnlApplicato(), null);
			esitoCcnl = (ccnl != null && ccnl.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCcnl, 
					ErrorConstants.DATI_ASSUNZIONE_CCNL_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		} else {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.DATI_ASSUNZIONE_CCNL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		if((UtilConstant.FLAG_NO).equalsIgnoreCase(inizioRappUnilav.getFlgLavoroAgricoltura())){
			if( inizioRappUnilav.getTipoLavorazione()!= null){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if(inizioRappUnilav.getGiornateLavorativePrevisteAgricoltura()!=null){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_NUM_GG_PREVISTI_AGR_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
		}
		// QUALIFICA PROF
		if (inizioRappUnilav.getQualificaProfessionaleIstat() != null) {
			boolean esitoIstat = false;
			Istat2001livello5 istat = (Istat2001livello5) decodificaDad.getTfromMin(Istat2001livello5.class, inizioRappUnilav.getQualificaProfessionaleIstat(), null);
			esitoIstat = (istat != null && istat.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoIstat, 
					ErrorConstants.DATI_ASSUNZIONE_QUALIFICA_PROFESSIONALE_ISTAT_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		//adeguamenti 2016
		addErrorsToRegistroErrori(regoleComuni.validateAssunzioneObbligatoria(inizioRappUnilav, 
				null, 
				tipoComunicazione, 
				isUNILAV(), 
				isUNISOM(), 
				isVARDATORI(), 
				false, decodificaDad, commonDad), commonDad, elencoErrori);

		// tipo orario
		String tipoOrario = inizioRappUnilav.getTipoOrario();
		// ore settimanali medie
		Long oreSettimanaliMedie = inizioRappUnilav.getOreSettimanaliMedie();
		//tipo contratto
		if(isValorizzato(tipoContratto)) {

			// PAT INAIL
			String pat = inizioRappUnilav.getPatInail();
			controllaCampoObbligatorio(pat, ErrorConstants.DATI_ASSUNZIONE_PAT_INAIL_NON_VALORIZZATO, commonDad, elencoErrori);
			if (isValorizzato(pat)) {
				boolean esitoPatInail = isLunghezzaStringaMinEMaxValida(pat , 8 , 10);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoPatInail, 
						ErrorConstants.DATI_ASSUNZIONE_PATINAIL_FORMATO_LUNGHEZZA_MIN_MAX_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}



			controllaERegistraErroreTipoOrarioENumOreSettimanali(
					tipoContratto,
					tipoOrario,
					oreSettimanaliMedie,
					ErrorConstants.DATI_ASSUNZIONE_TIPO_ORARIO_ERRATO_UNILAV, 
					ErrorConstants.DATI_ASSUNZIONE_ORE_SETTIMANALI_MEDIE_VALORIZZATE,
					ErrorConstants.DATI_ASSUNZIONE_ORE_SETTIMANALI_MEDIE_NON_VALORIZZATE,
					ErrorConstants.DATI_ASSUNZIONE_TIPO_ORARIO_NON_AMMESSO_PER_TIPO_CONTRATTO,
					true,
					commonDad, 
					decodificaDad, 
					elencoErrori, 
					null);


			boolean isLavCoobbligatoValorizzato = getLavoratoreCoobbligato()!=null;
			if(isContrattoRipartito(tipoContratto)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isLavCoobbligatoValorizzato, 
						ErrorConstants.DATI_ASSUNZIONE_LAVORATORE_COOBBLIGATO_NON_VALORIZZATO_PER_TIPO_CONTRATTO_RIPARTITO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			//72160-1549 (16.04.2015)
			if (isLavCoobbligatoValorizzato && !isContrattoRipartito(tipoContratto)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_LAVORATORE_COOBBLIGATO_SU_TIPO_CONTRATTO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}


			Date dataFinePeriodoFormativo = inizioRappUnilav.getDataFinePeriodoFormativo();
			String lavoroStagionale = inizioRappUnilav.getLavoroStagionale();

			//					Secondo il documento ministeriale dei controlli il Flag Stagionale e' permesso coi seguenti contratti:
			//					A.02.00, A.02.01, B.01.00, B.02.00, B.03.00, C.03.00, A.03.08, A.03.09, A.03.10, A.04.02, A.08.02, A.07.02, A.05.02,H.02.00
			if (!isContrattoPerLavoroStagionale(tipoContratto) && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_FLAG_LAVORO_STAGIONALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}

			//72160-1596: fix data fine periodo formativo obbligatoria per contratti apprendistato-ASSUNZIONE UNILAV (ammesso)
			addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(dataFinePeriodoFormativo, 
					dataInizioRapporto, 
					tipoComunicazione, 
					lavoroStagionale, 
					dataFineRapporto, 
					isUNILAV(), 
					isUNISOM(), 
					isVARDATORI(), 
					isApprendistato(tipoContratto), 
					false), commonDad, elencoErrori);


			// controlli sulla data fine
			if(dataFineRapporto != null){

				if( isFlgFormaPerTipoContrattoIndeterminata(tipoContratto, decodificaDad) 
						&& !(isApprendistato(tipoContratto) 
								&& lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI))) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
							ErrorConstants.DATI_ASSUNZIONE_TEMPO_INDETERMINATO_CON_DATA_FINE_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				}
			}
			else
			{
				if (isContrattoPerLavoroStagionale(tipoContratto) && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)) {
					//72160-1503 controlli su lavoro stagionale
					//se il tipo contratto  e'  uno tra A.02.00, A.02.01, B.01.00, B.02.00, C.03.00, A.03.08, A.03.09, A.03.10, A.04.02, A.08.02, A.07.02, A.05.02.
					//il flag lavoro stagionale = SI --> la data fine rapporto non puo' essere null
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
							ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA_CON_FLAG_LAVORO_STAGIONALE_SI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


				} else if(isFlgFormaPerTipoContrattoDeterminata(tipoContratto, decodificaDad)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
							ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


				} else if (isFlgFormaPerTipoContrattoIndeterminata(tipoContratto, decodificaDad)){
					if(isApprendistato(tipoContratto) && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

				} else if(isFlgFormaPerTipoContrattoDeterminataEIndeterminata(tipoContratto, decodificaDad)){

					if(lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
				} 							
			}
			controllaRapportoTempoIndeterminatoConLavoroStagionale(tipoContratto,lavoroStagionale, ErrorConstants.DATI_ASSUNZIONE_TEMPO_INDETERMINATO_CON_LAVORO_STAGIONALE, commonDad, elencoErrori);

		}
		
		//adeguamenti 2019
		controlliAdeguamenti2019Retribuzione(inizioRappUnilav,tipoOrario, oreSettimanaliMedie, decodificaDad, commonDad, elencoErrori);
	}
	private void controlliAdeguamenti2019Retribuzione(
			InizioRapportoUnilavDTO inizioRappUnilav, String tipoOrario,
			Long oreSettimanaliMedie, 
			DecodificaDad decodificaDad, 
			CommonDad commonDad,
			List<ApiError> elencoErrori) throws Exception {
		//Adeguamenti 2019 - 
		//SE il tipo contratto e'  A.03.04 il CCNL deve avere il settore "eEnte pubblico"
		if (inizioRappUnilav.getCcnlApplicato() != null && inizioRappUnilav.getTipologiaContrattuale() != null) {
			if (UtilConstant.TIPO_CONTRATTO_FORMAZIONE_LAVORO_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(inizioRappUnilav.getTipologiaContrattuale())
					&& !CalcoloRetribuzioneHelper.isCCNLEntePubblico(inizioRappUnilav.getCcnlApplicato(), decodificaDad )) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.CCNL_NON_VALIDO_PER_CONTRATTO_SOLO_PUBBLICA_AMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				return;
			}
		}
		
		
		//1- se il tipo di contratto selezionato  e' compreso nella lista il CCNL deve essere valorizzato a ND
		String tipiContrattoCcnlND = commonDad.getParametroCommaxById(ParametriConstants.VINCOLI_LISTA_TIPI_CONTRATTO_CCNL_AMMESSO_ND).getValoreParametro();

		Vector vCont = new Vector();
		if (tipiContrattoCcnlND.indexOf(";") != -1) {
			vCont = Util.tokenizerForParameterValues(tipiContrattoCcnlND, ";");
		}
		if (!CalcoloRetribuzioneHelper.isTipoContrattoVincolatoCongruenteCCNL(inizioRappUnilav.getCcnlApplicato(),inizioRappUnilav.getTipologiaContrattuale(), vCont)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.DATI_ASSUNZIONE_CCNL_INCONGRUENTE_TIPO_CONTRATTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			return;
		}

		
		
		controllaCampoObbligatorio(inizioRappUnilav.getLivelloInquadramento(), ErrorConstants.DATI_ASSUNZIONE_LIVELLO_INQUADRAMENTO_NON_VALORIZZATO,  commonDad,  elencoErrori);
		controllaCampoObbligatorio(Format.toString(inizioRappUnilav.getRetribuzione()), ErrorConstants.DATI_ASSUNZIONE_RETRIBUZIONE_NON_VALORIZZATA, commonDad,  elencoErrori);
		
		// 05.02.2020 - 72160:
		/*
		 * Se il parametro controlli.bloccante.retribuzione.ugualeZero.attivo (id=28) = S
		 * LA RETRIBUZIONE NON PUO' ESSERE = 0
		 * 
		 * */
		if (!CalcoloRetribuzioneHelper.checkValoreRetribuzioneIfControlloAttivoAndDeterminaSeValida(inizioRappUnilav.getRetribuzione(), inizioRappUnilav.getTipologiaContrattuale(), commonDad) ) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.RETRIBUZIONE_UGUALE_ZERO_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			return;
		}

		//
					
		if (inizioRappUnilav.getCcnlApplicato() != null 
				&& inizioRappUnilav.getLivelloInquadramento() != null
				&& !"".equals(inizioRappUnilav.getLivelloInquadramento()) 
				&& !"".equals(inizioRappUnilav.getCcnlApplicato())) {
			
			Long idLivello = CalcoloRetribuzioneHelper.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(inizioRappUnilav.getCcnlApplicato(), inizioRappUnilav.getLivelloInquadramento(), decodificaDad);
			controllaCampoObbligatorio(Format.toString(idLivello), ErrorConstants.DATI_ASSUNZIONE_LIVELLO_INQUADRAMENTO_NON_VALIDO_PER_CCNL, commonDad,  elencoErrori);
			
			// 05.02.2020 - 72160:
			/*
			 * Se il parametro controlli.retribuzioneCalcolata.retribuzioneInserita.attivo (id=30) = S
			 * EFFETTUIAMO IL CONTROLLO SULLA VALIDITA' DELLA RETRIBUZIONE INSERITA IN BASE AL CALCOLO TABELLARE
			 * 
			 * */
			String controllaRetribuzioneCalcolata = commonDad.getParametroCommaxById(ParametriConstants.CONTROLLI_RETRIBUZIONECALCOLATA_RETRIBUZIONEINSERITA_ATTIVO).getValoreParametro();
			if ("S".equalsIgnoreCase(controllaRetribuzioneCalcolata) && idLivello != null) {
				Long retribuzioneCalcolata = CalcoloRetribuzioneHelper.determinaTuplaLivelloAndCalcolaRetribuzione(idLivello, tipoOrario,oreSettimanaliMedie,inizioRappUnilav.getRetribuzione(), decodificaDad);
				if (retribuzioneCalcolata == null) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
							ErrorConstants.DATI_ASSUNZIONE_IMPOSSIBILE_CALCOLARE_RETRIBUZIONE_MANCANZA_CAMPI_OBBLIGATORI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				} else {
					if (inizioRappUnilav.getRetribuzione().longValue() < retribuzioneCalcolata.longValue()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_ASSUNZIONE_RETRIBUZIONE_INSERITA_NON_VALIDA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
				}
			}
		}
	}



	private void validazioneUnisomm(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws  Exception {
		String tipoContratto = comunicazione.getDatiInizio().getTipologiaContrattuale();			

		InizioRapportoUniSommDTO inizioRappUniSomm = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());
		Date dataFineRapporto = inizioRappUniSomm.getDataFine();
		Date dataInizioRapporto = inizioRappUniSomm.getDataInizio();

		controlliComuniAUnilavUnisom(inizioRappUniSomm, elencoErrori, commonDad, decodificaDad);

		if (isContrattoRipartito(tipoContratto)){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.DATI_ASSUNZIONE_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNISOM, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}


		//72160-1596: fix data fine periodo formativo obbligatoria per contratti apprendistato-UNISOMM (ammesso)
		addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(inizioRappUniSomm.getDataFinePeriodoFormativo(), 
				dataInizioRapporto, 
				tipoComunicazione, 
				null, 
				null, 
				isUNILAV(), 
				isUNISOM(), 
				isVARDATORI(), 
				isApprendistato(tipoContratto), 
				false), commonDad, elencoErrori);

		controllaRapportoDataFineUnisomm(tipoContratto, 
				dataFineRapporto, 
				ErrorConstants.DATI_ASSUNZIONE_TEMPO_INDETERMINATO_CON_DATA_FINE_VALORIZZATA,
				ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA,
				decodificaDad, commonDad, elencoErrori);

		if (Util.isNotVoid(comunicazione.getMissione()) ){
			//adeguamenti 2016
			addErrorsToRegistroErrori(regoleComuni.validateAssunzioneObbligatoria(null, 
					comunicazione.getMissione().getDatiInizioMissione(), 
					tipoComunicazione, 
					isUNILAV(), 
					isUNISOM(), 
					isVARDATORI(), 
					true, decodificaDad, commonDad), commonDad, elencoErrori
					);
		}
	}


	private void controlliComuniAUnilavUnisom(DatiInizioRapportoDTO datiInizioRapporto, List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws  Exception {	
		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();
		Date dataInizioRapporto = datiInizioRapporto.getDataInizio();
		Date dataFineRapporto = datiInizioRapporto.getDataFine();


		if( dataInizioRapporto != null && dataFineRapporto != null ) {
			if( ! DateUtilities.isData1MaggioreOUgualeAData2( dataFineRapporto , dataInizioRapporto ) )
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_DATA_FINE_MINORE_O_UGUALE_A_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}
		controlloDataFineMenoDataInizioNonMaggioreDi50(dataFineRapporto, dataInizioRapporto, ErrorConstants.DATI_ASSUNZIONE_DIFFERENZA_DATA_FINE_DATA_INIZIO_SUPERIORE_A_50_ANNI, commonDad, elencoErrori);

		if( isValorizzato(tipoContratto)) {	

			boolean esitoTipoContratto = false;
			TipoContratti contratti = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
			esitoTipoContratto = (contratti != null && contratti.getId() !=null ? true : false);

			if(!esitoTipoContratto){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						ErrorConstants.DATI_ASSUNZIONE_TIPO_CONTRATTO_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			else{
				if(!isTipoContrattoAmmessoPerComunicazioneDB(tipoContratto,tipoComunicazione,dataInizioRapporto,isUNISOM(),false, decodificaDad)){

					if(isUNILAV()){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_ASSUNZIONE_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
					else{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_ASSUNZIONE_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNISOM, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}				

				}

			}
		}

		String entePrevidenziale = comunicazione.getDatiInizio().getEntePrevidenziale();
		if( isValorizzato(entePrevidenziale))
		{
			boolean esitoEntePrev = false;
			EntePrevidenziale ente = (EntePrevidenziale) decodificaDad.getTfromMin(EntePrevidenziale.class, entePrevidenziale, null);
			esitoEntePrev = (ente != null && ente.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoEntePrev, 
					ErrorConstants.DATI_ASSUNZIONE_ENTE_PREVIDENZIALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


			if (entePrevidenziale.equalsIgnoreCase(UtilConstant.NESSUN_ENTE_PREVIDENZIALE))
			{
				String codEntePrev = comunicazione.getDatiInizio().getCodEntePrevidenziale();

				if(isValorizzato(codEntePrev)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
							ErrorConstants.DATI_ASSUNZIONE_NESSUN_ENTE_PREV_MA_COD_ENTE_PREVIDENZIALE_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				}
			}
		}
	}



}
