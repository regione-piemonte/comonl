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

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatiCessazioneDTO;
import it.csi.spicom.dto.DatiProrogaDTO;
import it.csi.spicom.dto.DatiTrasformazioneDTO;
import it.csi.spicom.dto.DatiVariazioneDTO;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.MissioneDTO;

public class RegoleValidazioneVariazione extends ControlloAstrattoBloccante{
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception {
		
		if (isUNISOM()) 
		{
			DatiVariazioneDTO varazioneDto = comunicazione.getDatiVariazione();

			if(varazioneDto != null){

				DatiProrogaDTO datiProrogaDto = varazioneDto.getDatiProroga();
				DatiTrasformazioneDTO datiTrasformazioneDto = varazioneDto.getDatiTrasformazione();
				DatiCessazioneDTO datiCessazioneDto = varazioneDto.getDatiCessazione();
				
				InizioRapportoUniSommDTO inizioRapportoUniSommDTO = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());
				
				MissioneDTO missioneDto = comunicazione.getMissione();
				
				DittaUtilizzatriceDTO aziendaUtilizzatrice = comunicazione.getDittaUtilizzatrice();
				

				Date dataInizio = inizioRapportoUniSommDTO.getDataInizio();
				Date dataFine = inizioRapportoUniSommDTO.getDataFine();

				String codiceVariazione = varazioneDto.getCodiceVariazione();
				

				// **************************** PROROGA  ****************************************************************
				
				if(datiProrogaDto != null)

				{

					Date dataFineProroga = datiProrogaDto.getDataFineProroga();

					if (!DateUtilities.isData1MaggioreDiData2(dataFineProroga, dataInizio)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.VARIAZIONE_PROROGA_DATA_INIZIO_RAPPORTO_MAGGIORE_DATA_FINE_PROROGA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}


					if(codiceVariazione.equals(UtilConstant.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE) || 
							codiceVariazione.equals(UtilConstant.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO)){


						Date dataInizioMissione = missioneDto.getDatiInizioMissione().getDataInizioMissione();

						Date dataFineMissione = missioneDto.getDatiInizioMissione().getDataFineMissione();


						if (DateUtilities.isData1MaggioreDiData2(dataInizioMissione, dataFineProroga)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.VARIAZIONE_PROROGA_DATA_INIZIO_MISSIONE_MAGGIORE_DATA_FINE_PROROGA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

						if(dataFineMissione != null){

							if (DateUtilities.isData1MaggioreDiData2(dataFineMissione, dataFineProroga)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
										ErrorConstants.VARIAZIONE_PROROGA_DATA_FINE_MISSIONE_MAGGIORE_DATA_FINE_PROROGA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}

						}

					}

					if(dataFine != null){

						if (DateUtilities.isData1MaggioreDiData2(dataFineProroga, dataFine)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_PROROGA_DATA_FINE_PROROGA_MAGGIORE_DATA_FINE_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

					}

					if(isDifferenzaDataFineDataInizioMaggioreDi50Anni(dataFineProroga, dataInizio)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_PROROGA_DIFFERENZA_DATA_FINE_PROROGA_DATA_INIZIO_RAPPORTO_SUPERIORE_A_50_ANNI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

				}


				// **************************** TRASFORMAZIONE *********************************************************
				if(datiTrasformazioneDto != null)

				{

					Date dataTrasformazione = datiTrasformazioneDto.getDataTrasformazione();

					String codiceTrasf = datiTrasformazioneDto.getCodiceTrasformazione();

					if (!DateUtilities.isData1MaggioreDiData2(dataTrasformazione, dataInizio)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_TRAFORMAZIONE_DATA_INIZIO_RAPPORTO_MAGGIORE_DATA_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}


					if(codiceVariazione.equals(UtilConstant.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_COSTANZA_DI_MISSIONE) || 
							codiceVariazione.equals(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE)){


						Date dataInizioMissione = missioneDto.getDatiInizioMissione().getDataInizioMissione();


						if (DateUtilities.isData1MaggioreDiData2(dataInizioMissione, dataTrasformazione)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_TRAFORMAZIONE_DATA_INIZIO_MISSIONE_MAGGIORE_DATA_TRASFORMAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}


						Date dataFineSomministrazione = aziendaUtilizzatrice.getDataFineSomministrazione();

						if(dataFineSomministrazione != null){


							if (DateUtilities.isData1MaggioreDiData2(dataTrasformazione, dataFineSomministrazione)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
										ErrorConstants.VARIAZIONE_TRAFORMAZIONE_DATA_TRASFORMAZIONE_MAGGIORE_DATA_FINE_SOMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

							}

						}

					}


					if(dataFine != null){

						if (DateUtilities.isData1MaggioreDiData2(dataTrasformazione, dataFine)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_TRAFORMAZIONE_DATA_TRASFORMAZIONE_MAGGIORE_DATA_FINE_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}

					}


					if (codiceTrasf.equals(UtilConstant.CODICE_TRASFORMAZIONE_FI) ||
							codiceTrasf.equals(UtilConstant.CODICE_TRASFORMAZIONE_PV)) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					if(codiceTrasf.equals(UtilConstant.CODICE_TRASFORMAZIONE_DL)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_TRASFORMAZIONE_CODICE_TRASFORMAZIONE_NON_SONO_AMMESSE_TIPOLOGIE_DI_DISTACCO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					if(codiceTrasf.equals(UtilConstant.CODICE_TRASFORMAZIONE_TL)){

						if(!(codiceVariazione.equals(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE) || 
								codiceVariazione.equals(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA))){

							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_TRASFORMAZIONE_CODICE_TRASFORMAZIONE_NON_SONO_AMMESSE_TIPOLOGIE_DI_DISTACCO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
					}

					if(codiceTrasf.equals(UtilConstant.CODICE_TRASFORMAZIONE_AI)){
						
						
						String dataRiferimentoInizioRapportoTrasformazioneAI = commonDad.getParametroCommaxById(ParametriConstants.DATA_RIFERIMENTO_INIZIO_RAPPORTO_TRASFORMAZIONE_AI).getValoreParametro();

						if( dataInizio.after(DateUtil.convertiStringaInData(dataRiferimentoInizioRapportoTrasformazioneAI))){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_TRASFORMAZIONE_TIPOLOGIA_DI_TRASFORMAZIONE_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

					}

				}



				// **************************** CESSAZIONE  *************************************************************

				if (datiCessazioneDto != null) 
				{

					Date dataCessazione = datiCessazioneDto.getDataCessazione();

					String tipoContratto = inizioRapportoUniSommDTO.getTipologiaContrattuale();

					String codiceCessazione = datiCessazioneDto.getCodiceCausa();


					if (DateUtilities.isData1MaggioreDiData2(dataInizio, dataCessazione)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_CESSAZIONE_DATA_INIZIO_RAPPORTO_MAGGIORE_DATA_CESSAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}


					if(codiceVariazione.equals(UtilConstant.CESSAZIONE_DELLA_MISSIONE)){


						Date dataInizioMissione = missioneDto.getDatiInizioMissione().getDataInizioMissione();


						if (DateUtilities.isData1MaggioreDiData2(dataInizioMissione, dataCessazione)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_CESSAZIONE_DATA_INIZIO_MISSIONE_MAGGIORE_DATA_CESSAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}


						Date dataFineSomministrazione = aziendaUtilizzatrice.getDataFineSomministrazione();

						if(dataFineSomministrazione != null){

							if (DateUtilities.isData1MaggioreDiData2(dataCessazione, dataFineSomministrazione)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
										ErrorConstants.VARIAZIONE_CESSAZIONE_DATA_CESSAZIONE_MAGGIORE_DATA_FINE_SOMMINISTRAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

							}

						}
					}

					if(isDifferenzaDataFineDataInizioMaggioreDi50Anni(dataCessazione, dataInizio)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_CESSAZIONE_DIFFERENZA_DATA_CESSAZIONE_DATA_INIZIO_RAPPORTO_SUPERIORE_A_50_ANNI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					boolean isCausaCessazionePresente = false;
					Cessazionerl cessazionerl = (Cessazionerl) decodificaDad.getTfromMin(Cessazionerl.class, codiceCessazione, null);
					isCausaCessazionePresente = (cessazionerl != null && cessazionerl.getId() !=null ? true : false);
					
					
					if(!isCausaCessazionePresente){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
								ErrorConstants.VARIAZIONE_CESSAZIONE_CODICE_CAUSA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
					
					else{


						if(codiceCessazione.equals(UtilConstant.CAUSA_CESSAZIONE_DECADENZA_DAL_SERVIZIO_NELLA_P_A)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_CESSAZIONE_CODICE_CAUSA_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}

						if(isCessazionePerApprendistato(codiceCessazione) && !isApprendistato(tipoContratto)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
									ErrorConstants.VARIAZIONE_CESSAZIONE_CODICE_CAUSA_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}

					}
				}
			}
		}		
	}
}
