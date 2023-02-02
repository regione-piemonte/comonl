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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.proroga;

import java.util.ArrayList;
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
import it.csi.spicom.dto.ProrogaDTO;
import it.csi.spicom.util.TUConstants;

/**
 * Regole che si applicano punti 1.1 e 1.2
 * 
 * @author 70493
 * 
 */
public class RegoleValidazioneRapportoLavoroProroga extends ControlloAstrattoBloccante 
{

	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception 
	{

		if (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)
				|| tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA_CONTESTUALE_A_PROROGA_DI_MISSIONE)
				|| tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA_MISSIONE))
		{
			if(isUNILAV()){

				InizioRapportoUnilavDTO datiInizioRapporto = ((InizioRapportoUnilavDTO)comunicazione.getDatiInizio());

				ProrogaDTO datiProroga = comunicazione.getProroga();

				controlliUnilav(datiInizioRapporto, datiProroga, commonDad, elencoErrori, decodificaDad);
				controlliComuniAUnilavUnisom(comunicazione.getDatiInizio(), decodificaDad, commonDad, elencoErrori);


				String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();


				String lavoroStagionale = datiInizioRapporto.getLavoroStagionale();

				controllaRapportoTempoIndeterminatoConLavoroStagionale(tipoContratto, lavoroStagionale, ErrorConstants.DATI_PROROGA_TEMPO_INDETERMINATO_CON_LAVORO_STAGIONALE, commonDad, elencoErrori);

				//72160-1596: fix data fine periodo formativo UNLAV (non ammesso)
				addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(datiInizioRapporto.getDataFinePeriodoFormativo(), datiInizioRapporto.getDataInizio(), tipoComunicazione, lavoroStagionale, datiInizioRapporto.getDataFine(), 
						isUNILAV(), isUNISOM(), isVARDATORI(), isApprendistato(tipoContratto), false), commonDad , elencoErrori);

				//adeguamenti 2016
				ArrayList vError = regoleComuni.validateAssunzioneObbligatoria(datiInizioRapporto, null, tipoComunicazione, isUNILAV(), isUNISOM(), isVARDATORI(), false, decodificaDad, commonDad);
				if (vError != null && vError.size() > 0) {
					for (int i=0;i<vError.size();i++) {
						Long codiceErr = (Long)vError.get(i);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, codiceErr.longValue(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}					
			}					

			if(isUNISOM()){
				InizioRapportoUniSommDTO inizioRappUniSomm = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());

				controlliUnisomm(inizioRappUniSomm, commonDad, elencoErrori);
				controlliComuniAUnilavUnisom(comunicazione.getDatiInizio(), decodificaDad, commonDad, elencoErrori);
				//72160-1596: fix data fine periodo formativo obbligatoria per contratti apprendistato-UNISOMM (ammesso)
				addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(inizioRappUniSomm.getDataFinePeriodoFormativo(), inizioRappUniSomm.getDataInizio(), tipoComunicazione, null, null, 
						isUNILAV(), isUNISOM(), isVARDATORI(), isApprendistato(inizioRappUniSomm.getTipologiaContrattuale()), false ), commonDad, elencoErrori);

				//adeguamenti 2016
				if (Util.isNotVoid(comunicazione.getMissione()) ){
					ArrayList vError = regoleComuni.validateAssunzioneObbligatoria(null, comunicazione.getMissione().getDatiInizioMissione(), tipoComunicazione, isUNILAV(), isUNISOM(), isVARDATORI(), true, decodificaDad, commonDad );
					if (vError != null && vError.size() > 0) {
						for (int i=0;i<vError.size();i++) {
							Long codiceErr = (Long)vError.get(i);
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, codiceErr.longValue(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}	
				}
			}
		}

	}



	private void controlliUnisomm(InizioRapportoUniSommDTO inizioRappUniSomm, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		String tipoContratto = inizioRappUniSomm.getTipologiaContrattuale();

		if (isContrattoRipartito(tipoContratto)){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNISOM, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}
	}

	private void controlliUnilav(InizioRapportoUnilavDTO datiInizioRapporto, ProrogaDTO datiProroga, CommonDad commonDad, List<ApiError> elencoErrori, DecodificaDad decodificaDad) throws Exception {
		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();
		Date dataInizioRapporto = datiInizioRapporto.getDataInizio();
		String tipoOrario = datiInizioRapporto.getTipoOrario();
		Long oreSettimanaliMedie = datiInizioRapporto.getOreSettimanaliMedie();	
		Date dataFineProroga = datiProroga.getDataFineProroga();
		//72160-1510: eliminare flag 
		//		String flgProsecuzioneDiFatto = datiProroga.getProsecuzioneDiFatto();


		// pat inail
		String pat = comunicazione.getDatiInizio().getPatInail();
		if( isVuoto(pat))
		{
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_PAT_INAIL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}
		if (isValorizzato(pat)) 
		{	
			boolean esitoPatInail = isLunghezzaStringaMinEMaxValida(pat, 8, 10);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoPatInail, ErrorConstants.DATI_PROROGA_PATINAIL_FORMATO_LUNGHEZZA_MIN_MAX_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}

		// tipo orario
		// ore settimanali medie

		controllaERegistraErroreTipoOrarioENumOreSettimanali(
				tipoContratto,
				tipoOrario,
				oreSettimanaliMedie,
				ErrorConstants.DATI_PROROGA_TIPO_ORARIO_ERRATO_UNILAV, 
				ErrorConstants.DATI_PROROGA_ORE_SETTIMANALI_MEDIE_VALORIZZATE,
				ErrorConstants.DATI_PROROGA_ORE_SETTIMANALI_MEDIE_NON_VALORIZZATE,
				ErrorConstants.DATI_PROROGA_TIPO_ORARIO_NON_AMMESSO_PER_TIPO_CONTRATTO,
				true, commonDad, decodificaDad, elencoErrori, null);

		//Adeguamenti 2019 - agevolazioni non ammesse
		// agevolazioni
		List elencoAgevolazioni = datiInizioRapporto.getAgevolazioni();
		if (elencoAgevolazioni != null && !elencoAgevolazioni.isEmpty()) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_AGEVOLAZIONI_NON_AMMESSE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}


		// ripartito coobbligato 
		if (isContrattoRipartito(tipoContratto)) 
		{
			boolean isLavCoobbligatoValorizzato = comunicazione.getLavoratoreCoobbligato() != null;
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isLavCoobbligatoValorizzato, ErrorConstants.DATI_PROROGA_LAVORATORE_COOBBLIGATO_NON_VALORIZZATO_PER_TIPO_CONTRATTO_RIPARTITO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}


		// flag socio lavoratore
		if( isValorizzato(datiInizioRapporto.getFlgSocioLavoratore()))
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(datiInizioRapporto.getFlgSocioLavoratore()), ErrorConstants.DATI_PROROGA_FLG_SOCIO_LAVORATORE_ERRATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		// qualifica prof
		if( isValorizzato(datiInizioRapporto.getQualificaProfessionaleIstat())) {
			boolean esitoIstat = false;
			Istat2001livello5 istat = (Istat2001livello5) decodificaDad.getTfromMin(Istat2001livello5.class, datiInizioRapporto.getQualificaProfessionaleIstat(), null);
			esitoIstat = (istat != null && istat.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoIstat, ErrorConstants.DATI_PROROGA_QUALIFICA_PROFESSIONALE_ISTAT_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		if (isValorizzato(datiInizioRapporto.getCcnlApplicato())) {
			boolean esitoCcnl = false;
			Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, datiInizioRapporto.getCcnlApplicato(), null);
			esitoCcnl = (ccnl != null && ccnl.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCcnl, 
					ErrorConstants.DATI_PROROGA_CCNL_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		} else {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
					ErrorConstants.DATI_PROROGA_CCNL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}


		if((UtilConstant.FLAG_NO).equalsIgnoreCase(datiInizioRapporto.getFlgLavoroAgricoltura())){
			if( datiInizioRapporto.getTipoLavorazione()!= null){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			if(datiInizioRapporto.getGiornateLavorativePrevisteAgricoltura()!=null){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_NUM_GG_PREVISTI_AGR_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}

		// data fine proroga
		if( dataInizioRapporto != null && dataFineProroga != null )
		{
			if(!DateUtilities.isData1MaggioreDiData2(dataFineProroga , dataInizioRapporto))
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_DATA_FINE_PROROGA_MINORE_DI_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		controlloDataFineMenoDataInizioNonMaggioreDi50(dataFineProroga, dataInizioRapporto, ErrorConstants.DATI_PROROGA_DIFFERENZA_DATA_FINE_DATA_INIZIO_SUPERIORE_A_50_ANNI, commonDad, elencoErrori);
		//adeguamenti 2019
		controlliAdeguamenti2019Retribuzione(datiInizioRapporto,tipoOrario, oreSettimanaliMedie, decodificaDad, commonDad, elencoErrori);

	}
	private void controlliAdeguamenti2019Retribuzione(
			InizioRapportoUnilavDTO inizioRappUnilav, String tipoOrario,
			Long oreSettimanaliMedie, 
			DecodificaDad decodificaDad, 
			CommonDad commonDad,
			List<ApiError> elencoErrori) throws Exception {
		//SE il tipo contratto  e'  A.03.04 il CCNL deve avere il settore "eEnte pubblico"
				if (inizioRappUnilav.getCcnlApplicato() != null && inizioRappUnilav.getTipologiaContrattuale() != null) {
					if (UtilConstant.TIPO_CONTRATTO_FORMAZIONE_LAVORO_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(inizioRappUnilav.getTipologiaContrattuale())
							&& !CalcoloRetribuzioneHelper.isCCNLEntePubblico(inizioRappUnilav.getCcnlApplicato(), decodificaDad )) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.CCNL_NON_VALIDO_PER_CONTRATTO_SOLO_PUBBLICA_AMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						return;
					}
				}
		//1- se il tipo di contratto selezionato e' compreso nella lista il CCNL deve essere valorizzato a ND
		String tipiContrattoCcnlND = commonDad.getParametroCommaxById(ParametriConstants.VINCOLI_LISTA_TIPI_CONTRATTO_CCNL_AMMESSO_ND).getValoreParametro();

		Vector vCont = new Vector();
		if (tipiContrattoCcnlND.indexOf(";") != -1) {
			vCont = Util.tokenizerForParameterValues(tipiContrattoCcnlND, ";");
		}
		if (!CalcoloRetribuzioneHelper.isTipoContrattoVincolatoCongruenteCCNL(inizioRappUnilav.getCcnlApplicato(),inizioRappUnilav.getTipologiaContrattuale(), vCont)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_CCNL_INCONGRUENTE_TIPO_CONTRATTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			return;
		}


		//Adeguamenti 2019 - 
		controllaCampoObbligatorio(inizioRappUnilav.getLivelloInquadramento(), ErrorConstants.DATI_PROROGA_LIVELLO_INQUADRAMENTO_NON_VALORIZZATO, commonDad, elencoErrori);
		controllaCampoObbligatorio(Format.toString(inizioRappUnilav.getRetribuzione()), ErrorConstants.DATI_PROROGA_RETRIBUZIONE_NON_VALORIZZATA, commonDad, elencoErrori);
		// 05.02.2020 - 72160:
		/*
		 * Se il parametro controlli.bloccante.retribuzione.ugualeZero.attivo (id=28) = S
		 * LA RETRIBUZIONE NON PUO' ESSERE = 0
		 * 
		 * */
		if (!CalcoloRetribuzioneHelper.checkValoreRetribuzioneIfControlloAttivoAndDeterminaSeValida(inizioRappUnilav.getRetribuzione(), inizioRappUnilav.getTipologiaContrattuale(), commonDad) ) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RETRIBUZIONE_UGUALE_ZERO_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			return;
		}

		//
		if (inizioRappUnilav.getCcnlApplicato() != null 
				&& inizioRappUnilav.getLivelloInquadramento() != null
				&& !"".equals(inizioRappUnilav.getLivelloInquadramento()) 
				&& !"".equals(inizioRappUnilav.getCcnlApplicato())) {

			Long idLivello = CalcoloRetribuzioneHelper.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(inizioRappUnilav.getCcnlApplicato(), inizioRappUnilav.getLivelloInquadramento(), decodificaDad);
			controllaCampoObbligatorio(Format.toString(idLivello), ErrorConstants.DATI_PROROGA_LIVELLO_INQUADRAMENTO_NON_VALIDO_PER_CCNL, commonDad, elencoErrori);
			// 05.02.2020 - 72160:
			/*
			 * Se il parametro controlli.retribuzioneCalcolata.retribuzioneInserita.attivo (id=30) = S
			 * EFFETTUIAMO IL CONTROLLO SULLA VALIDITA' DELLA RETRIBUZIONE INSERITA IN BASE AL CALCOLO TABELLARE
			 * 
			 * */
			String controllaRetribuzioneCalcolata = commonDad.getParametroCommaxById(ParametriConstants.CONTROLLI_RETRIBUZIONECALCOLATA_RETRIBUZIONEINSERITA_ATTIVO).getValoreParametro();
			if ("S".equalsIgnoreCase(controllaRetribuzioneCalcolata) && idLivello != null) {
				
				Long retribuzioneCalcolata = CalcoloRetribuzioneHelper.determinaTuplaLivelloAndCalcolaRetribuzione(idLivello, tipoOrario,oreSettimanaliMedie,inizioRappUnilav.getRetribuzione(),decodificaDad );
				if (retribuzioneCalcolata == null) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_IMPOSSIBILE_CALCOLARE_RETRIBUZIONE_MANCANZA_CAMPI_OBBLIGATORI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				} else {
					if (inizioRappUnilav.getRetribuzione().longValue() < retribuzioneCalcolata.longValue()) {
						// RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_RETRIBUZIONE_INSERITA_NON_VALIDA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						// non bloccare 
					}
				}
			}
		}
	}
	private void controlliComuniAUnilavUnisom(DatiInizioRapportoDTO datiInizioRapporto, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		// ente
		String entePrevidenziale = comunicazione.getDatiInizio().getEntePrevidenziale();
		Date dataFinePeriodo = comunicazione.getDatiInizio().getDataFinePeriodoFormativo();

		controllaValiditaContratto(datiInizioRapporto, decodificaDad, commonDad, elencoErrori);

		if( isValorizzato(entePrevidenziale))
		{
			boolean esitoEnte = false;
			EntePrevidenziale ente = (EntePrevidenziale) decodificaDad.getTfromMin(EntePrevidenziale.class, entePrevidenziale, 	null);
			esitoEnte = (ente != null && ente.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoEnte, ErrorConstants.DATI_PROROGA_ENTE_PREVIDENZIALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			if (entePrevidenziale.equalsIgnoreCase(UtilConstant.NESSUN_ENTE_PREVIDENZIALE)) 
			{
				String codEntePrev = comunicazione.getDatiInizio().getCodEntePrevidenziale();

				if(isValorizzato(codEntePrev)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_NESSUN_ENTE_PREV_MA_COD_ENTE_PREVIDENZIALE_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				}
			}
		}

	}

	private void controllaValiditaContratto(DatiInizioRapportoDTO datiInizioRapporto, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {

		//Il controllo della validita' di un tipo contratto in questa sezione veniva determinato considerando
		//il risultato tra FLG_VLD_SOM e FLG_VLD_PRO.
		//per risolvere la segnalazione  Comonl-757 punto 2. utilizzero' solo il risultato della  FLG_VLD_SOM che in questo
		//modo mi restituira' anche il contratto LAVORO_INTERINALE_O_A_SCOPO_DI_SOMMINISTRAZIONE_A_TEMPO_INDETERMINATO
		//su cui successivamente verra' fatto il controllo indicato nella segnalazione.

		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();
		Date dataInizioRapporto = datiInizioRapporto.getDataInizio();

		boolean esitoContratti = false;
		TipoContratti contratto = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
		esitoContratti = (contratto != null && contratto.getId() !=null ? true : false);

		if(!esitoContratti){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_TIPO_CONTRATTO_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		else if(!isTipoContrattoAmmessoPerComunicazioneDB(tipoContratto,tipoComunicazione,dataInizioRapporto,isUNISOM(), false, decodificaDad) && 
				!isProrogaMissioneConContrattoIndeterminato(tipoContratto, tipoComunicazione )){

			if(isUNILAV()){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			else
			{
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_PROROGA_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNISOM, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

		}
	}

	private boolean isProrogaMissioneConContrattoIndeterminato(String tipoContratto, String tipoComunicazione) {

		return (tipoContratto.equals(UtilConstant.TEMPO_INDETERMINATO) && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA_MISSIONE));

	}

}
