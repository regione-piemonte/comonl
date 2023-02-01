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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.trasformazione;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.CalcoloRetribuzioneHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Format;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatiInizioRapportoDTO;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.util.TUConstants;

/**
 * Regole che si applicano punti 1.1 e 1.2
 * 
 * @author 70493
 * 
 */
public class RegoleValidazioneRapportoLavoroTrasformazione extends ControlloAstrattoBloccante {

	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception 
	{

		if (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||
				tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE) 
				|| tipoComunicazione.equals(UtilConstant.TRASFERIMENTO)
				|| tipoComunicazione.equals(UtilConstant.DISTACCO)) 
		{
			if(isUNILAV()){

				InizioRapportoUnilavDTO inizioRappUnilav = ((InizioRapportoUnilavDTO)comunicazione.getDatiInizio());
				String tipoContratto = inizioRappUnilav.getTipologiaContrattuale();
				boolean isMissione = false;

				String codTrasformazione = comunicazione.getTrasformazione().getCodTrasformazione();
				Date dataTrasformazione = comunicazione.getTrasformazione().getDataTrasformazione();
				String flgDistaccoParziale = comunicazione.getTrasformazione().getFlgDistaccoParziale();
				String flgDistaccoAziendaEstera = comunicazione.getTrasformazione().getFlgDistaccoAziendaEstera();
				DatoreDiLavoroDTO datLavDistacco = comunicazione.getTrasformazione().getDatoreLavoroDistacco();
				String datIndirizzoSedeLavPrec = comunicazione.getTrasformazione().getIndirizzoSedeLavoroPrecedente();

				Date dataFineRapporto = inizioRappUnilav.getDataFine();
				String lavoroStagionale = inizioRappUnilav.getLavoroStagionale();

				if(controlliComuniAUnilavUnisomUnidom(inizioRappUnilav, decodificaDad, commonDad, elencoErrori)){

					controlliComuniAUnilavUnisom(inizioRappUnilav, decodificaDad, commonDad, elencoErrori);

					controlliCodiceTrasformazioneUnilav(inizioRappUnilav, decodificaDad, commonDad, elencoErrori);					

					Date dataInizioRapporto = inizioRappUnilav.getDataInizio();

					if( dataInizioRapporto != null && dataTrasformazione != null ) 
					{
						if( DateUtilities.isData1InferioreDiData2( dataTrasformazione , dataInizioRapporto ) )
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATA_TRASFORMAZIONE_MINORE_DI_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					// tipo orario
					String tipoOrario = inizioRappUnilav.getTipoOrario();
					// ore settimanali medie
					Long oreSettimanaliMedie = inizioRappUnilav.getOreSettimanaliMedie();
					controllaERegistraErroreTipoOrarioENumOreSettimanali(
							tipoContratto,
							tipoOrario,
							oreSettimanaliMedie,
							ErrorConstants.DATI_TRASFORMAZIONE_TIPO_ORARIO_ERRATO_UNILAV, 
							ErrorConstants.DATI_TRASFORMAZIONE_ORE_SETTIMANALI_MEDIE_VALORIZZATE,
							ErrorConstants.DATI_TRASFORMAZIONE_ORE_SETTIMANALI_MEDIE_NON_VALORIZZATE,
							ErrorConstants.DATI_TRASFORMAZIONE_TIPO_ORARIO_NON_AMMESSO_PER_TIPO_CONTRATTO,
							true, commonDad, decodificaDad,elencoErrori, null);


					// ripartito
					if (isContrattoRipartito(tipoContratto)){
						boolean isLavCoobbligatoValorizzato = comunicazione.getLavoratoreCoobbligato() != null;
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isLavCoobbligatoValorizzato, ErrorConstants.DATI_TRASFORMAZIONE_LAVORATORE_COOBBLIGATO_NON_VALORIZZATO_PER_TIPO_CONTRATTO_RIPARTITO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					//adeguamenti 2016
					ArrayList vError = regoleComuni.validateAssunzioneObbligatoria(inizioRappUnilav, null, tipoComunicazione, isUNILAV(), isUNISOM(), isVARDATORI(), isMissione, decodificaDad, commonDad);
					if (vError != null && vError.size() > 0) {
						for (int i=0;i<vError.size();i++) {
							Long codiceErr = (Long)vError.get(i);
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, codiceErr.longValue(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}
					//72160-1596: fix data fine periodo formativo UNLAV (non ammesso)
					addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(inizioRappUnilav.getDataFinePeriodoFormativo(), dataInizioRapporto, tipoComunicazione, lavoroStagionale, dataFineRapporto, 
							isUNILAV(), isUNISOM(), isVARDATORI(), isApprendistato(tipoContratto), false), commonDad, elencoErrori);

					// agevolazioni
					//Adeguamenti 2019 - agevolazioni non ammesse
					List elencoAgevolazioni = inizioRappUnilav.getAgevolazioni();
					if (elencoAgevolazioni != null && !elencoAgevolazioni.isEmpty()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_AGEVOLAZIONI_NON_AMMESSE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					// flag socio lavoratore
					if( isValorizzato(inizioRappUnilav.getFlgSocioLavoratore()))
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(inizioRappUnilav.getFlgSocioLavoratore()), ErrorConstants.DATI_TRASFORMAZIONE_FLG_SOCIO_LAVORATORE_ERRATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					// Qualifica professionale prof
					if( isValorizzato(inizioRappUnilav.getQualificaProfessionaleIstat())) {

						boolean esitoIstat = false;
						Istat2001livello5 istat = (Istat2001livello5) decodificaDad.getTfromMin(Istat2001livello5.class, inizioRappUnilav.getQualificaProfessionaleIstat(), null);
						esitoIstat = (istat != null && istat.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoIstat, 
								ErrorConstants.DATI_TRASFORMAZIONE_QUALIFICA_PROFESSIONALE_ISTAT_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					if (isValorizzato(inizioRappUnilav.getCcnlApplicato())) {
						boolean esitoCcnl = false;
						Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, inizioRappUnilav.getCcnlApplicato(), null);
						esitoCcnl = (ccnl != null && ccnl.getId() !=null ? true : false);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCcnl, 
								ErrorConstants.DATI_TRASFORMAZIONE_CCNL_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					} else {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.DATI_TRASFORMAZIONE_CCNL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					if((UtilConstant.FLAG_NO).equalsIgnoreCase(inizioRappUnilav.getFlgLavoroAgricoltura())){
						if( inizioRappUnilav.getTipoLavorazione()!= null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_LAVORAZIONE_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
						if(inizioRappUnilav.getGiornateLavorativePrevisteAgricoltura()!=null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_NUM_GG_PREVISTI_AGR_VALORIZZATO_PER_CONTRATTO_NON_AGRICOLO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}


					// CODICE_TRASFORMAZIONE_DL
					if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DL)){

						if(datLavDistacco == null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATORE_DEL_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
						else{

							if(isValorizzato(datIndirizzoSedeLavPrec)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DISTACCO_DEL_LAVORATORE_CON_DATI_TRASFERIMENTO_LAVORATORE_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

							}
							else{


								String denominazione =  datLavDistacco.getDenominazione();								
								String codiceFiscale = datLavDistacco.getCodFiscale();
								String patInail = comunicazione.getTrasformazione().getPatInail();								
								String capSedeLavoro = datLavDistacco.getCapSedeLavoro();								
								String settoreAteco  = datLavDistacco.getSettore();								
								String indirizzoSedeLavoro = datLavDistacco.getIndirizzoSedeLavoro();
								String comuneSedeLavoro = datLavDistacco.getComuneSedeLavoro();

								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(denominazione), ErrorConstants.DATI_TRASFORMAZIONE_DENOMINAZIONE_DATORE_LAVORO_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(settoreAteco), ErrorConstants.DATI_TRASFORMAZIONE_ATECO_DATORE_LAVORO_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								if(isValorizzato(settoreAteco)) {
									boolean esitoAteco = false;
									Atecofin ateco = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, settoreAteco, null);
									esitoAteco = (ateco != null && ateco.getId() !=null ? true : false);
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoAteco, ErrorConstants.DATI_TRASFORMAZIONE_ATECO_DATORE_LAVORO_DISTACCO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}

								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(indirizzoSedeLavoro), ErrorConstants.DATI_TRASFORMAZIONE_INDIRIZZO_SEDE_LAV_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								// fax email tel 
								String[] valoriControllo = {datLavDistacco.getEmailSedeLavoro(),
										datLavDistacco.getTelefonoSedeLavoro(),
										datLavDistacco.getFaxSedeLavoro() 
								};
								boolean esitoValUnCampo = isValorizzatoAlmenoUnCampo(valoriControllo);
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoValUnCampo, ErrorConstants.DATI_TRASFORMAZIONE_EMAIL_TELEFONO_E_FAX_SEDE_LAV_DISTACCO_NON_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(datLavDistacco.getEmailSedeLavoro()), ErrorConstants.DATI_TRASFORMAZIONE_SEDE_LEGALE_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								if(isVuoto(flgDistaccoAziendaEstera) || flgDistaccoAziendaEstera.equalsIgnoreCase(UtilConstant.FLAG_NO)){

									if( isValorizzato(codiceFiscale)){
										boolean esitoCodFisc = isCodiceFiscaleOPartitaIvaValido(codiceFiscale);
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, ErrorConstants.DATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									else{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_COD_FISCALE_DATORE_LAVORO_DISTACCO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}

									if( isVuoto(patInail) )
									{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_PATINAIL_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									if( isValorizzato(patInail))
									{
										boolean esitoPatInail = isLunghezzaStringaMinEMaxValida(patInail, 8, 10);
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoPatInail, ErrorConstants.DATI_TRASFORMAZIONE_PATINAIL_FORMATO_LUNGHEZZA_MIN_MAX_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}

									if( isValorizzato(comuneSedeLavoro)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isIdComuneValido(datLavDistacco.getComuneSedeLavoro(), decodificaDad, null), ErrorConstants.DATI_TRASFORMAZIONE_COD_COMUNE_SEDE_LAV_DISTACCO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									else{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_COD_COMUNE_SEDE_LAV_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(capSedeLavoro), ErrorConstants.DATI_TRASFORMAZIONE_CAP_SEDE_LAV_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}

								else if (flgDistaccoAziendaEstera.equalsIgnoreCase(UtilConstant.FLAG_SI)) {
									if(isValorizzato(codiceFiscale)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_COD_FISCALE_DATORE_LAVORO_DISTACCO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									if(isValorizzato(patInail)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_PATINAIL_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									if(isValorizzato(capSedeLavoro)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_CAP_SEDE_LAV_DISTACCO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}

									if( isValorizzato(comuneSedeLavoro)){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isStatoEsteroValido(datLavDistacco.getComuneSedeLavoro(), decodificaDad, null), ErrorConstants.DATI_TRASFORMAZIONE_COD_STATO_ESTERO_SEDE_LAV_DISTACCO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									else{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_COD_STATO_ESTERO_SEDE_LAV_DISTACCO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
								}
							}
						}			
					}

					else if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TL)){
						// COMONL-850 :: I FLAG DISTACCO PARZIALE O FLG DISTACCO AZIENDA ESTERA, IN CASO DI TRASFERIMENTO 
						// (QUINDI CODICE TRASFORMAZ 'TL') possono essere null o 'NO'.
						// Quindi deve essere segnalato con quest'errore solo il caso di TRASFERIMENTO con FLG DISTACCO = SI 
						if(isValorizzato(flgDistaccoParziale) || isValorizzato(flgDistaccoAziendaEstera)){
							if(flgDistaccoParziale.equalsIgnoreCase("SI") || flgDistaccoAziendaEstera.equalsIgnoreCase("SI"))
							{
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TRASFERIMENTO_LAVORATORE_CON_FLAG_DISTACCO_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}	
						}
						if(datLavDistacco != null){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATORE_DEL_DISTACCO_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}


					if ( codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DL)){

						if( dataFineRapporto == null)
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_CODICE_TRASFORMAZIONE_DL_DATA_FINE_RAPPORTO_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						else{ 
							if( dataTrasformazione != null)
							{
								if( DateUtilities.isData1InferioreDiData2( dataFineRapporto, dataTrasformazione ) )
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATA_FINE_RAPPORTO_INFERIORE_DATA_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}			
						}	
					}


					//72160-1503/1504 controlli su lavoro stagionale
					//se il tipo contratto NON e' uno tra A.02.00, A.02.01, B.01.00, B.02.00, C.03.00, A.03.08, A.03.09, A.03.10, A.04.02, A.08.02, A.07.02, A.05.02.
					//il flag lavoro stagionale = SI --> NON e' ammesso
					//se il contratto e' ammesso, la data fine rapporto non puo' essere null 
					if (lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)) {

						if (!isContrattoPerLavoroStagionale(tipoContratto)) {
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_FLAG_LAVORO_STAGIONALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						} else {
							if (dataFineRapporto == null) {
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA_CON_FLAG_LAVORO_STAGIONALE_SI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}
						}
					}

					controllaRapportoTempoIndeterminatoConLavoroStagionale(tipoContratto, lavoroStagionale, ErrorConstants.DATI_TRASFORMAZIONE_TEMPO_INDETERMINATO_CON_LAVORO_STAGIONALE, commonDad, elencoErrori);
					//adeguamenti 2019
					controlliAdeguamenti2019Retribuzione(inizioRappUnilav,tipoOrario, oreSettimanaliMedie, decodificaDad, commonDad, elencoErrori);
				}

			}			
			if(isUNISOM()){
				ArrayList vError = null;

				InizioRapportoUniSommDTO inizioRappUniSomm = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());
				String tipoContratto = inizioRappUniSomm.getTipologiaContrattuale();

				boolean isMissione = (Util.isNotVoid(comunicazione.getMissione()) ? true : false);

				Date dataInizioRapporto = inizioRappUniSomm.getDataInizio();
				Date dataFineRapporto = inizioRappUniSomm.getDataFine();

				if(controlliComuniAUnilavUnisomUnidom(inizioRappUniSomm, decodificaDad, commonDad, elencoErrori)){
					controlliComuniAUnilavUnisom(inizioRappUniSomm, decodificaDad, commonDad, elencoErrori);


					// COMONL-1054 - INIZIO
					controlloDataFineMenoDataInizioNonMaggioreDi50(dataFineRapporto, dataInizioRapporto, ErrorConstants.DATI_TRASFORMAZIONE_DIFFERENZA_DATA_FINE_DATA_INIZIO_SUPERIORE_A_50_ANNI, commonDad, elencoErrori);
					// COMONL-923
					// La data fine rapporto non deve essere valorizzata
					if(dataFineRapporto != null){
						if( isFlgFormaPerTipoContrattoIndeterminata(tipoContratto,decodificaDad) )
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TEMPO_INDETERMINATO_CON_DATA_FINE_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}

					else{
						if(isFlgFormaPerTipoContrattoDeterminata(tipoContratto, decodificaDad)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATA_FINE_RAPPORTO_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}						
				}
				//72160-1596: fix data fine periodo formativo obbligatoria per contratti apprendistato-UNISOMM (ammesso)
				addErrorsToRegistroErrori(regoleComuni.validateDataFinePeridioFormativo(inizioRappUniSomm.getDataFinePeriodoFormativo(), dataInizioRapporto, tipoComunicazione, null, null, 
						isUNILAV(), isUNISOM(), isVARDATORI(), isApprendistato(tipoContratto), false) ,commonDad, elencoErrori);

				//adeguamenti 2016
				if (isMissione){
					addErrorsToRegistroErrori(regoleComuni.validateAssunzioneObbligatoria(null, comunicazione.getMissione().getDatiInizioMissione(), tipoComunicazione, isUNILAV(), isUNISOM(), isVARDATORI(), true, decodificaDad, commonDad), 
							commonDad, elencoErrori);
				}
			}


		}
	}

	private void controlliCodiceTrasformazioneUnilav(InizioRapportoUnilavDTO datiInizioRapporto, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori) throws  Exception {		
		String codTrasformazione = comunicazione.getTrasformazione().getCodTrasformazione();

		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();		
		String tipoOrarioOggettone = datiInizioRapporto.getTipoOrario();
		Date  dataInizioRapporto = datiInizioRapporto.getDataInizio();
		String lavoroStagionale = datiInizioRapporto.getLavoroStagionale();

		// codice trasformazione
		if( isValorizzato(codTrasformazione) ) {

			boolean isCodiceTrasformazionePresente = false;
			Trasformazionerl trasformazione = (Trasformazionerl) decodificaDad.getTfromMin(Trasformazionerl.class, codTrasformazione, null	);
			isCodiceTrasformazionePresente = (trasformazione != null && trasformazione.getId() !=null ? true : false);

			if(!isCodiceTrasformazionePresente){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_CODICE_TRASFORMAZIONE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			else{

				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_AI) ||
						codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DI)
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_FI)
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_II)) 
				{
					if(!tipoContratto.equalsIgnoreCase(UtilConstant.TEMPO_INDETERMINATO)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_FORMA_NON_VALIDA_PER_CODICE_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}	


				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_AI) ||
						codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DI)
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_FI)
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_II)
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_FF)) 
				{

					if(lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}	


				/*
				2- in caso di comunicazioni di TRASFORMAZIONE non e' possibile indicare una trasformazione di orario 
				(da part time a full time, e viceversa) se l'utente, nella sezione del rapporto trasformato, 
				ha indicato il tipo orario "NON DEFINITO"
				 */
				// COD TRASFORMAZIONE  PP - ( tipo orario )

				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_PP)) 
				{
					if( isValorizzato(tipoOrarioOggettone))
					{
						TipoOrario tipoOrario = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class, tipoOrarioOggettone, null	);

						String idComTipoOrario = (tipoOrario != null && tipoOrario.getId() != null ? String.valueOf(tipoOrario.getId()) : null);
						// se non e' tempo pieno e non e' "NON_DEFINITO" --> ERRORE
						if(idComTipoOrario != null && ! idComTipoOrario.equals(UtilConstant.TIPO_ORARIO_TEMPO_PIENO))
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_ORARIO_NON_VALIDO_PER_CODICE_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}
				// COD TRASFORMAZIONE  TP - ( tipo orario )
				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TP)) 
				{
					if( isValorizzato(tipoOrarioOggettone))
					{
						TipoOrario tipoOrario = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class, tipoOrarioOggettone, null	);

						String idComTipoOrario = (tipoOrario !=  null && tipoOrario.getId() != null ? String.valueOf(tipoOrario.getId()) : null);
						// se e' tempo pieno o e' "NON_DEFINITO" -- > ERRORE
						if( idComTipoOrario != null 
								&& (idComTipoOrario.equals(UtilConstant.TIPO_ORARIO_TEMPO_PIENO) || idComTipoOrario.equals(UtilConstant.TIPO_ORARIO_NON_DEFINITO)))
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_ORARIO_NON_VALIDO_PER_CODICE_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}

				if(codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_PV) 
						|| codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_FI)){	

					String pubblicaAmministrazione = comunicazione.getDatoreDiLavoro().getPubblicaAmministrazione();

					boolean esitoComunicazioneRiferitaAEntePubblico = isComunicazioneRiferitaAEntePubblico(pubblicaAmministrazione);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComunicazioneRiferitaAEntePubblico, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}


				// CODICE_TRASFORMAZIONE_TL
				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TL)) 
				{
					// sede lavoro precedenteù
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(comunicazione.getTrasformazione().getComuneSedeLavoroPrecedente()), ErrorConstants.DATI_TRASFORMAZIONE_COD_COMUNE_SEDE_PREC_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					if( isValorizzato(comunicazione.getTrasformazione().getComuneSedeLavoroPrecedente()))
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isIdComuneOStatoEsteroValido(comunicazione.getTrasformazione().getComuneSedeLavoroPrecedente(), decodificaDad, null), ErrorConstants.DATI_TRASFORMAZIONE_COD_COMUNE_SEDE_PREC_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzato(comunicazione.getTrasformazione().getIndirizzoSedeLavoroPrecedente()), ErrorConstants.DATI_TRASFORMAZIONE_INDIRIZZO_SEDE_PREC_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}


				if(isContrattoTirocinio(tipoContratto) || 
						tipoContratto.equalsIgnoreCase(UtilConstant.LAVORO_O_ATTIVITA_SOCIALMENTE_UTILE)){

					if(!codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_PP) ||
							!codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TP)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}



				if(codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_FF)){

					String dataRiferimentoInizioRapportoTrasformazioneFF = 
							commonDad.getParametroCommaxById(ParametriConstants.DATA_RIFERIMENTO_INIZIO_RAPPORTO_TRASFORMAZIONE_FF).getValoreParametro();

					if(isFlgFormaPerTipoContrattoIndeterminata(tipoContratto, decodificaDad)  
							&& lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_NO) 
							&& !dataInizioRapporto.before(DateUtil.convertiStringaInData(dataRiferimentoInizioRapportoTrasformazioneFF))){

						//Unico caso ammesso

					}
					else{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}

				if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_AI)) {

					String dataRiferimentoInizioRapportoTrasformazioneAI = commonDad.getParametroCommaxById(ParametriConstants.DATA_RIFERIMENTO_INIZIO_RAPPORTO_TRASFORMAZIONE_AI).getValoreParametro();

					if( dataInizioRapporto.after(DateUtil.convertiStringaInData(dataRiferimentoInizioRapportoTrasformazioneAI))){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

				}
			}
		}
	}

	private boolean controlliComuniAUnilavUnisomUnidom(DatiInizioRapportoDTO datiInizioRapporto, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori) throws  Exception {	

		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();
		Date dataInizioRapporto = datiInizioRapporto.getDataInizio();

		boolean result = true;

		boolean esitoContratto = false;
		TipoContratti contratto = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null	);
		esitoContratto = (contratto != null && contratto.getId() !=null ? true : false);
		if(!esitoContratto){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_CONTRATTO_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			result = false;
		}

		else if (!isTipoContrattoAmmessoPerComunicazioneDB(tipoContratto,tipoComunicazione,dataInizioRapporto,isUNISOM(), false, decodificaDad)){

			result = false;

			if(isUNILAV()){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNILAV, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			else{
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPO_CONTRATTO_NON_AMMESSO_PER_TRACCIATO_UNISOM, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

		}

		return result;	

	}

	private void controlliComuniAUnilavUnisom(DatiInizioRapportoDTO datiInizioRapporto, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori ) throws Exception {
		Date dataFineRapporto = datiInizioRapporto.getDataFine();
		Date dataInizioRapporto = datiInizioRapporto.getDataInizio();

		if( dataFineRapporto != null  &&  dataInizioRapporto != null  ){						
			if( DateUtilities.isData1InferioreDiData2( dataFineRapporto , dataInizioRapporto ) )
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_DATA_FINE_RAPPORTO_INFERIORE_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}

		// ente prev
		String entePrevidenziale = comunicazione.getDatiInizio().getEntePrevidenziale();
		if( isValorizzato(entePrevidenziale))
		{
			
			boolean esitoEntePrevidenziale = false;
			EntePrevidenziale ente = (EntePrevidenziale) decodificaDad.getTfromMin(EntePrevidenziale.class, entePrevidenziale, null	);
			esitoEntePrevidenziale = (ente != null && ente.getId() !=null ? true : false);

			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoEntePrevidenziale, ErrorConstants.DATI_TRASFORMAZIONE_ENTE_PREVIDENZIALE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			// Se il tipo ente previdenziale e' uguale a 29 non deve essere valorizzato il codice ente
			if (entePrevidenziale.equalsIgnoreCase(UtilConstant.NESSUN_ENTE_PREVIDENZIALE))
			{

				String codEntePrev = comunicazione.getDatiInizio().getCodEntePrevidenziale();
				if(isValorizzato(codEntePrev))
				{
					// Riccardo :: COMONL-851
					// Se il tipo ente previdenziale e' 'NESSUN ENTE PREVIDENZIALE' (cod 29) il codice dell'ente previdenziale non deve presente.
					// Errore !! Non deve essere valorizzato
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_NESSUN_ENTE_PREV_MA_COD_ENTE_PREVIDENZIALE_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}

			}

		}

		//ADEGUAMENTI SETT 2016
		/*
		 * Si introduce un nuovo tipo di trasformazione per i tracciati UNILAV e UNISOMM:
		 * DP - TRASFORMAZIONE DA CONTRATTO DI APPRENDISTATO A CONTRATTO DI APPRENDISTATO PROFESSIONALIZZANTE
		 * L'unico tipo di contratto ammesso per questa nuova trasformazione e' A.03.09 (APPRENDISTATO PROFESSIONALIZZANTE O CONTRATTO DI MESTIERE).
		 * */
		String tipoContratto = datiInizioRapporto.getTipologiaContrattuale();
		String codTrasformazione = null; 

		if (comunicazione.getTrasformazione() != null) {
			codTrasformazione = comunicazione.getTrasformazione().getCodTrasformazione();
		} else {
			if (comunicazione.getDatiVariazione() != null
					&& comunicazione.getDatiVariazione().getDatiTrasformazione() != null) {
				codTrasformazione = comunicazione.getDatiVariazione().getDatiTrasformazione().getCodiceTrasformazione();
			}
		}



		if (codTrasformazione != null 
				&& UtilConstant.CODICE_TRASFORMAZIONE_DP.equalsIgnoreCase(codTrasformazione) 
				&& !UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_PROF_O_CONTRATTO_DI_MESTIERE.equalsIgnoreCase(tipoContratto)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}


	}
	private void controlliAdeguamenti2019Retribuzione(
			InizioRapportoUnilavDTO inizioRappUnilav, String tipoOrario,
			Long oreSettimanaliMedie, 
			DecodificaDad decodificaDad,
			CommonDad commonDad,
			List<ApiError> elencoErrori) throws Exception {
		//SE il tipo contratto e' A.03.04 il CCNL deve avere il settore "eEnte pubblico"
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
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_CCNL_INCONGRUENTE_TIPO_CONTRATTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			return;
		}


		//Adeguamenti 2019 - 
		controllaCampoObbligatorio(inizioRappUnilav.getLivelloInquadramento(), ErrorConstants.DATI_TRASFORMAZIONE_LIVELLO_INQUADRAMENTO_NON_VALORIZZATO, commonDad, elencoErrori);
		controllaCampoObbligatorio(Format.toString(inizioRappUnilav.getRetribuzione()), ErrorConstants.DATI_TRASFORMAZIONE_RETRIBUZIONE_NON_VALORIZZATA, commonDad, elencoErrori);
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
			controllaCampoObbligatorio(Format.toString(idLivello), ErrorConstants.DATI_TRASFORMAZIONE_LIVELLO_INQUADRAMENTO_NON_VALIDO_PER_CCNL, commonDad, elencoErrori);
			// 05.02.2020 - 72160:
			/*
			 * Se il parametro controlli.retribuzioneCalcolata.retribuzioneInserita.attivo (id=30) = S
			 * EFFETTUIAMO IL CONTROLLO SULLA VALIDITA' DELLA RETRIBUZIONE INSERITA IN BASE AL CALCOLO TABELLARE
			 * 
			 * */
			String controllaRetribuzioneCalcolata = commonDad.getParametroCommaxById(ParametriConstants.CONTROLLI_RETRIBUZIONECALCOLATA_RETRIBUZIONEINSERITA_ATTIVO).getValoreParametro();
			if ("S".equalsIgnoreCase(controllaRetribuzioneCalcolata) && idLivello != null) {
				Long retribuzioneCalcolata = CalcoloRetribuzioneHelper.determinaTuplaLivelloAndCalcolaRetribuzione(idLivello, tipoOrario,oreSettimanaliMedie,inizioRappUnilav.getRetribuzione(), decodificaDad );
				if (retribuzioneCalcolata == null) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_IMPOSSIBILE_CALCOLARE_RETRIBUZIONE_MANCANZA_CAMPI_OBBLIGATORI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				} else {
					if (inizioRappUnilav.getRetribuzione().longValue() < retribuzioneCalcolata.longValue()) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_TRASFORMAZIONE_RETRIBUZIONE_INSERITA_NON_VALIDA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}
			}
		}
	}

}
