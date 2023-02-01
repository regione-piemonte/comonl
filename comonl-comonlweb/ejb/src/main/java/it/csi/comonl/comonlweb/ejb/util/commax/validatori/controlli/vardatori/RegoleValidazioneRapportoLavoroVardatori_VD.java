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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.vardatori;


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
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.dto.LavoratoreInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.util.TUConstants;


/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneRapportoLavoroVardatori_VD extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		if( isVARDATORI() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_RAMO_AZIENDALE))
		{
			DatoreDiLavoroDTO datoreLavoro = identificaDatoreLavoroDTORispettoTipoTracciato();

			//			TODO Attenzione quando sarà gestito correttamente scommentare la parte sottostante e commentare questo errore
			//	registraErroreSeEsitoControlloNegativo(false, 
			//			 ErrorConstants.RAPPLAV_VARDATORI_TRASFERIMENTO_RAMO_AZIENDALE_NON_ANCORA_GESTITO);			
			DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO[] datIntTrasfAziDTO = comunicazione.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate();

			for (int i = 0; i < datIntTrasfAziDTO.length; i++) 
			{
				DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO dtoDatore = datIntTrasfAziDTO[i];

				LavoratoreInteressatoTrasferimentoRamoAziendaleDTO[] dtoLav = dtoDatore.getVctElencoLavoratoriInteressati();

				for (int j = 0; j < dtoLav.length; j++) 
				{
					LavoratoreInteressatoTrasferimentoRamoAziendaleDTO lavTrasf = dtoLav[j];

					//CONTROLLO LAVORATORI
					boolean esitoControlloCrt = false;

					String campoCodFiscale = lavTrasf.getCodFiscale();
					String campoCognome = lavTrasf.getCognome();
					String campoNome = lavTrasf.getNome();
					String idComuneDomicilio = lavTrasf.getComuneDomicilio();

					String stringParametriPerDescrizoneErrore = generaParametroPerLavoratore(lavTrasf);

					// COD FISCALE
					if( isValorizzato(campoCodFiscale) && campoCodFiscale.length() == 16) 
					{
						esitoControlloCrt = Format.controlloCF(campoCodFiscale);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoControlloCrt, ErrorConstants.LAVORATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}

					if( esitoControlloCrt)
					{
						// cognome - COD FISCALE

						if(isValorizzato(campoCognome))
						{
							//72160-1538: pulizia campo cognome da ogni carattere non alfabetico prima del check di congruenza CF
							campoCognome = StringUtils.pulisciStringaPreControlloCf(campoCognome);
							boolean esitoCognome = isCognomeCongruente(campoCognome , campoCodFiscale);
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCognome, ErrorConstants.LAVORATORE_COGNOME_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
							
						} 
						// nome - COD FISCALE

						if(isValorizzato(campoNome))
						{	
							//72160-1538: pulizia campo nome da ogni carattere non alfabetico prima del check di congruenza CF
							campoNome = StringUtils.pulisciStringaPreControlloCf(campoNome);
							boolean esitoNome = isNomeCongruente(campoNome , campoCodFiscale);
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoNome, ErrorConstants.LAVORATORE_NOME_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}
					} 
					

					// comune domicilio

					if( isValorizzato(idComuneDomicilio) )
					{

						boolean esitoComuneDomicilio = isIdComuneValido(idComuneDomicilio, decodificaDad, null);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneDomicilio, ErrorConstants.LAVORATORE_COMUNE_DOMICILIO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);

					}

					//CONTROLLO RAPPORTI
					InizioRapportoUnilavDTO rapportoLavoro = lavTrasf.getDatiInizio();

					Date dataInizioRapporto = rapportoLavoro.getDataInizio();
					Date dataFineRapporto = rapportoLavoro.getDataFine();
					if( dataInizioRapporto != null && dataFineRapporto != null)
					{
						// data fine rapporto
						if( ! DateUtilities.isData1MaggioreDiData2(dataFineRapporto, dataInizioRapporto) )
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_DATA_FINE_RAPPORTO_MAGGIORE_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}		

					}

					// ente
					String entePrevidenziale = rapportoLavoro.getEntePrevidenziale();
					if( isValorizzato(entePrevidenziale))
					{
						boolean esitoEnte = false;
						EntePrevidenziale ente = (EntePrevidenziale) decodificaDad.getTfromMin(EntePrevidenziale.class, entePrevidenziale, null	);
						esitoEnte = (ente != null && ente.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoEnte, ErrorConstants.RAPPLAV_VARDATORI_ENTE_PREVIDENZIALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}

					// codice ente
					String codEntePrev = rapportoLavoro.getCodEntePrevidenziale();
					if( isValorizzato(codEntePrev))
					{
						if( isValorizzato(entePrevidenziale))
						{	
							if (entePrevidenziale.equalsIgnoreCase(UtilConstant.NESSUN_ENTE_PREVIDENZIALE)){

								if(isValorizzato(codEntePrev)){
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_NESSUN_ENTE_PREV_MA_COD_ENTE_PREVIDENZIALE_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
								}
							}			
						}
					}

					// patInail 
					String pat = rapportoLavoro.getPatInail();
					if( isValorizzato(pat))
					{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(pat), ErrorConstants.RAPPLAV_VARDATORI_PAT_INAIL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
					if (isValorizzato(pat))
					{
						boolean esitoPatInail = isLunghezzaStringaMinEMaxValida(pat , 8 , 10);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoPatInail, ErrorConstants.RAPPLAV_VARDATORI_PATINAIL_FORMATO_LUNGHEZZA_MIN_MAX_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
					//Adeguamenti 2019 - agevolazioni non ammesse
					// codice agevolazione
					List elencoAgevolazioni = rapportoLavoro.getAgevolazioni();
					if (elencoAgevolazioni != null && !elencoAgevolazioni.isEmpty()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_AGEVOLAZIONI_NON_AMMESSE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
					// tipologia contrattuale 
					String tipoContratto = rapportoLavoro.getTipologiaContrattuale();

					if (isValorizzato(tipoContratto)) {
						boolean esitoContratto = false;
						TipoContratti contratto = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null	);
						esitoContratto = (contratto != null && contratto.getId() !=null ? true : false);

						if(!esitoContratto){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_TIPO_CONTRATTO_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}
						else {
							boolean isTipoContrattoAmmesso = isTipoContrattoAmmessoPerComunicazioneDB(tipoContratto, tipoComunicazione,dataInizioRapporto,false, false, decodificaDad);

							if(!isTipoContrattoAmmesso){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_TIPO_CONTRATTO_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
							}
							else { 
								//cf soggetto promotore tirocinio
								String cfSoggettoPromotoreTirocinio = rapportoLavoro.getCFSoggettoPromotoreTirocinio();

								//data fine periodo formativo
								Date dataFinePeriodoFormativo = rapportoLavoro.getDataFinePeriodoFormativo();

								//lavoro stagionale
								String lavoroStagionale = rapportoLavoro.getLavoroStagionale();
								//72160-1504 controlli su lavoro stagionale
								//se il tipo contratto NON è uno tra A.02.00, A.02.01, B.01.00, B.02.00, C.03.00, A.03.08, A.03.09, A.03.10, A.04.02, A.08.02, A.07.02, A.05.02.
								//il flag lavoro stagionale = SI --> NON è ammesso
								if (!isContrattoPerLavoroStagionale(tipoContratto) && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)) {
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_FLAG_LAVORO_STAGIONALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
								} else if (isContrattoPerLavoroStagionale(tipoContratto) && lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)) {
									//1503-il contratto è ammesso per il lavoro stagionale, il flag stagionale è si--> la data fine rapporto deve essere valorizzata
									if (dataFineRapporto == null) {
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_DATA_FINE_RAPPORTO_NON_VALORIZZATA_CON_FLAG_LAVORO_STAGIONALE_SI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
									}
								}


								// 72160-1507: il CF deve essere valorizzato solo se tirocinio è di tipo C.01.00
								if(isContrattoTirocinio(tipoContratto)){

									if(isVuoto(cfSoggettoPromotoreTirocinio)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);

									}
									else {

										boolean esitoCodFisc =isCodiceFiscaleOPartitaIvaValido(cfSoggettoPromotoreTirocinio);
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, ErrorConstants.RAPPLAV_VARDATORI_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
									}

								}
								else{

									if(isValorizzato(cfSoggettoPromotoreTirocinio)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
									}

									//72160-1596: fix data fine periodo formativo obbligatoria per contratti apprendistato-VARDATORI (ammesso)
									addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(dataFinePeriodoFormativo, dataInizioRapporto, tipoComunicazione, lavoroStagionale, dataFineRapporto, 
											isUNILAV(), isUNISOM(), isVARDATORI(), isApprendistato(tipoContratto), false), commonDad, elencoErrori, stringParametriPerDescrizoneErrore);


								}

								if(dataFineRapporto != null){
									if( isFlgFormaPerTipoContrattoIndeterminata(tipoContratto, decodificaDad) && !lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI))
									{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_TEMPO_INDETERMINATO_CON_DATA_FINE_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
									}
								}
								else
								{
									if(isFlgFormaPerTipoContrattoDeterminata(tipoContratto, decodificaDad)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_TEMPO_DETERMINATO_CON_DATA_FINE_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
									}
								}
								controllaRapportoTempoIndeterminatoConLavoroStagionale(tipoContratto, 
										lavoroStagionale, 
										ErrorConstants.DATI_ASSUNZIONE_TEMPO_INDETERMINATO_CON_LAVORO_STAGIONALE,commonDad, elencoErrori, stringParametriPerDescrizoneErrore);

							}
						}
					}

					//adeguamenti 2016
					addErrorsToRegistroErrori(
							regoleComuni.validateAssunzioneObbligatoria(rapportoLavoro, null, tipoComunicazione, isUNILAV(), isUNISOM(), isVARDATORI(), false, decodificaDad, commonDad), 
							commonDad, elencoErrori, stringParametriPerDescrizoneErrore);

					
					
					// socio lavoratore
					String socio = rapportoLavoro.getFlgSocioLavoratore();
					if (isValorizzato(socio))
					{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(socio), 
								ErrorConstants.RAPPLAV_VARDATORI_TIPO_FLAG_SOCIO_LAVORATORE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}

					// tipo orario
					String tipoOrario = rapportoLavoro.getTipoOrario();
					// ore settimanali medie
					Long oreSettimanaliMedie = rapportoLavoro.getOreSettimanaliMedie();
					controllaERegistraErroreTipoOrarioENumOreSettimanali(
							tipoContratto,
							tipoOrario,
							oreSettimanaliMedie,
							ErrorConstants.RAPPLAV_VARDATORI_TIPO_ORARIO_NON_VALIDO, 
							ErrorConstants.RAPPLAV_VARDATORI_ORE_SETTIMANALI_MEDIE_VALORIZZATE,
							ErrorConstants.RAPPLAV_VARDATORI_ORE_SETTIMANALI_MEDIE_NON_VALORIZZATE,								
							ErrorConstants.RAPPLAV_VARDATORI_TIPO_ORARIO_NON_AMMESSO_PER_TIPO_CONTRATTO,
							false, 
							commonDad, 
							decodificaDad, 
							elencoErrori, 
							stringParametriPerDescrizoneErrore);


					if("NO".equalsIgnoreCase(rapportoLavoro.getFlgLavoroAgricoltura())){
						if( rapportoLavoro.getTipoLavorazione()!= null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATOR_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}
						if(rapportoLavoro.getGiornateLavorativePrevisteAgricoltura()!=null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATOR_NUM_GG_PREVISTI_AGR_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}
					}


					// qualifica prof
					String qualProf = rapportoLavoro.getQualificaProfessionaleIstat();
					if (isValorizzato(qualProf)) {

						boolean esitoQualifica = false;
						Istat2001livello5 istat = (Istat2001livello5) decodificaDad.getTfromMin(Istat2001livello5.class, qualProf, null	);
						esitoQualifica = (istat != null && istat.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoQualifica, ErrorConstants.RAPPLAV_VARDATORI_QUALIFICA_PROFESSIONALE_ISTAT_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
						


					/*72160 - Adeguamenti 2014: i campi contratto collettivo applicato - livello di inquadramento - retribuzione / compenso 
					 *                          diventano obbligatori in maniera assoluta e non più condizionata */
					// ccnl
					String ccnl = rapportoLavoro.getCcnlApplicato();

					if (isValorizzato(ccnl)) {
						boolean esitoCcnl = false;
						Ccnl ccnlDb = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, ccnl, null	);
						esitoCcnl = (ccnlDb != null && ccnlDb.getId() !=null ? true : false);

						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCcnl, ErrorConstants.RAPPLAV_VARDATORI_CCNL_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					} else {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_CCNL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
					//Retribuzione: dato obbligatorio
					if (rapportoLavoro.getRetribuzione() == null) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_RETRIBUZIONE_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					} 

					//adeguamenti 2019
					controlliAdeguamenti2019Retribuzione(ccnl, tipoContratto, rapportoLavoro.getRetribuzione(), decodificaDad, commonDad, elencoErrori, stringParametriPerDescrizoneErrore);
					
					//Livello inquadramento: dato obbligatorio
					if ( isVuoto(rapportoLavoro.getLivelloInquadramento()) ) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_LIVELLO_INQUADRAMENTO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
					}
					
					// tipo lavorazione
					String tipoLavorazione = rapportoLavoro.getTipoLavorazione();
					if( isValorizzato(tipoLavorazione) && isValorizzato(tipoContratto) )
					{
						if (!isContrattoAgricoltura(tipoContratto) && tipoLavorazione != null) 
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlgFormaPerTipoContrattoDeterminata(tipoContratto, decodificaDad), ErrorConstants.RAPPLAV_VARDATORI_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						}
					}											
				}
			}
		}
	}
	private void controlliAdeguamenti2019Retribuzione(String ccnl, String tipoContratto, Long retribuzione, 
			DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori, String stringParametriPerDescrizoneErrore) throws Exception {
		//Adeguamenti 2019 - 
		//SE il tipo contratto è A.03.04 il CCNL deve avere il settore "eEnte pubblico"
				if (ccnl != null && tipoContratto != null) {
					if (UtilConstant.TIPO_CONTRATTO_FORMAZIONE_LAVORO_PUBBLICA_AMMINISTRAZIONE.equalsIgnoreCase(tipoContratto)
							&& !CalcoloRetribuzioneHelper.isCCNLEntePubblico(ccnl, decodificaDad)) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.CCNL_NON_VALIDO_PER_CONTRATTO_SOLO_PUBBLICA_AMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);

						return;
					}
				}
		//1- se il tipo di contratto selezionato è compreso nella lista il CCNL deve essere valorizzato a ND
		String tipiContrattoCcnlND = commonDad.getParametroCommaxById(ParametriConstants.VINCOLI_LISTA_TIPI_CONTRATTO_CCNL_AMMESSO_ND).getValoreParametro();

		Vector vCont = new Vector();
		if (tipiContrattoCcnlND.indexOf(";") != -1) {
			vCont = Util.tokenizerForParameterValues(tipiContrattoCcnlND, ";");
		}
		if (!CalcoloRetribuzioneHelper.isTipoContrattoVincolatoCongruenteCCNL(ccnl,tipoContratto, vCont)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RAPPLAV_VARDATORI_CCNL_INCONGRUENTE_TIPO_CONTRATTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
			return;
		}
		
		if (!CalcoloRetribuzioneHelper.checkValoreRetribuzioneIfControlloAttivoAndDeterminaSeValida(retribuzione, tipoContratto, commonDad)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.RETRIBUZIONE_UGUALE_ZERO_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
			return;
		}
	}

	private String generaParametroPerLavoratore(LavoratoreInteressatoTrasferimentoRamoAziendaleDTO lavTrasf) {
		String result = new String();
		result = lavTrasf.getCognome()+" "+ lavTrasf.getNome()+" ("+lavTrasf.getCodFiscale()+")"; 
		return result;
	}
}
