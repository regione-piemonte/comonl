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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.lavoratore;


import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.util.TUConstants;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneLavoratoreCoobliggato_UL extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
//		LavoratoreDTO lavoratoreCoo = comunicazione.getLavoratoreCoobbligato();

		if( getLavoratoreCoobbligato() != null && isUNILAV() )
		{
			// codice fiscale
			String campoCodFiscale = getLavoratoreCoobbligato().getCodFiscale();
			boolean isCodiceValido = false;

			if( isValorizzato(campoCodFiscale)) 
			{
				isCodiceValido = isCodiceFiscaleOPartitaIvaValido(campoCodFiscale);

				if(!isCodiceValido){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				}

				else if( isCodiceFiscale(campoCodFiscale) )
				{	
					//72160-1550: il cf del lavoratore e del lavoratoreCoo NON possono essere uguali
					if (comunicazione.getLavoratore() != null && comunicazione.getLavoratore().getCodFiscale() != null) {
						String cfLavoratore = comunicazione.getLavoratore().getCodFiscale();
						if (cfLavoratore.trim().equals(campoCodFiscale)) {
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_FISCALE_UGUALE_CODICE_FISCALE_DEL_LAVORATORE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
					}

					// cognome - COD FISCALE
					String campoCognome = getLavoratoreCoobbligato().getCognome();
					if(isValorizzato(campoCognome))
					{
						//72160-1538: pulizia campo cognome da ogni carattere non alfabetico prima del check di congruenza CF
					    campoCognome = StringUtils.pulisciStringaPreControlloCf(campoCognome);
						boolean esitoCognome = isCognomeCongruente(campoCognome , campoCodFiscale);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCognome, ErrorConstants.LAVORATORE_COOBBLIGATO_COGNOME_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
					// nome - COD FISCALE
					String campoNome = getLavoratoreCoobbligato().getNome();
					if(isValorizzato(campoNome))
					{	
						//72160-1538: pulizia campo nome da ogni carattere non alfabetico prima del check di congruenza CF
						campoNome = StringUtils.pulisciStringaPreControlloCf(campoNome);
						boolean esitoNome = isNomeCongruente(campoNome , campoCodFiscale);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoNome, ErrorConstants.LAVORATORE_COOBBLIGATO_NOME_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
				}
			}

			// comune domicilio
			String idComuneDomicilio = getLavoratoreCoobbligato().getComuneDomicilio();
			if( isValorizzato(idComuneDomicilio) )
			{

				boolean esitoComuneDomicilio = isIdComuneValido(idComuneDomicilio, decodificaDad, null);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneDomicilio, ErrorConstants.LAVORATORE_COOBBLIGATO_COMUNE_DOMICILIO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

			if( isCodiceValido && isCodiceFiscale(campoCodFiscale) )
			{
				// 	sesso - COD FISCLAE
				String campoSesso = getLavoratoreCoobbligato().getSesso();
				if( isValorizzato(campoSesso) )
				{
					boolean esitoSesso = isSessoCongruente(campoSesso , campoCodFiscale);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoSesso, ErrorConstants.LAVORATORE_COOBBLIGATO_SESSO_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				}

				// data nascita - COD FISCALE
				Date campoDataNascita = getLavoratoreCoobbligato().getDataNascita();
				if( campoDataNascita!= null )
				{
					boolean dataNascitaCongruenteConCodiceFiscale = isDataNascitaCongruenteConCodiceFiscale(campoDataNascita , campoSesso , campoCodFiscale);
					boolean dataNascitaNelFuturo = isDataNascitaNelFuturo(campoDataNascita);

					if(!dataNascitaCongruenteConCodiceFiscale || dataNascitaNelFuturo){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(dataNascitaCongruenteConCodiceFiscale, ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_NASCITA_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}

					Date dataInizioRapporto = comunicazione.getDatiInizio().getDataInizio();

					if(dataInizioRapporto != null){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(checkIntervalloEtaLavoratoreDataInizioRapporto(campoDataNascita, dataInizioRapporto), ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_NASCITA_E_DATA_INIZIO_RAPPORTO_FUORI_RANGE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


						//72160-1502: data nascita DEVE essere minore di data inizio rapporto
						if (!isDataNascitaMaggioreDataInizioRapporto(campoDataNascita, dataInizioRapporto)) {
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_NASCITA_MAGGIORE_DATA_INIZIO_RAPPORTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

					}				
				}

				// comune nascita - COD FISCALE
				String campoComuneNascita = getLavoratoreCoobbligato().getComuneOStatoEsteroNascita();
				if( isValorizzato(campoComuneNascita) )
				{
					boolean esitoComuneNascita = isComuneNascitaCongruenteConCodiceFiscale(campoComuneNascita , campoCodFiscale);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneNascita, ErrorConstants.LAVORATORE_COOBBLIGATO_COMUNE_NASCITA_INCONGRUENTE_CON_CODICE_FISCALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					boolean esisteComuneNascita = isIdComuneOStatoEsteroValido(campoComuneNascita, decodificaDad, campoDataNascita);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esisteComuneNascita, ErrorConstants.LAVORATORE_COOBBLIGATO_COMUNE_O_STATO_ESTERO_NASCITA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}

			// tipo documento
			String tipoDocumentoLavoratore = getLavoratoreCoobbligato().getTipoDocumento();

			// motivo permesso
			String campoMotivoPermesso = getLavoratoreCoobbligato().getMotivoPermesso();

			// cittadinanza
			String campoCittadinanza = getLavoratoreCoobbligato().getCittadinanza();

			if( isValorizzato(campoCittadinanza) )
			{
				boolean isCittadinanzaPresente = false;
				Cittadinanza cittadinanza = (Cittadinanza) decodificaDad.getTfromMin(Cittadinanza.class, campoCittadinanza, null);
				isCittadinanzaPresente = (cittadinanza != null && cittadinanza.getId() !=null ? true : false);
				

				if(!isCittadinanzaPresente){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_CITTADINANZA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else{

					if( isVuoto(cittadinanza.getFlgUe()) || UtilConstant.FLAG_N.equalsIgnoreCase(cittadinanza.getFlgUe()) ) 
					{

						if( isVuoto(tipoDocumentoLavoratore))
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_TIPO_DOCUMENTO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
						if( isVuoto(campoMotivoPermesso))
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_MOTIVO_PERMESSO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
						else{
							boolean isMotivoPermessoPresente = false;
							MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(MotivoPermesso.class, campoMotivoPermesso, null);
							isMotivoPermessoPresente = (motivoPermesso != null && motivoPermesso.getId() !=null ? true : false);


							if(!isMotivoPermessoPresente){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_MOTIVO_PERMESSO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

							}
							else{

								// scadenza permesso
								Date campoScadenzaPermesso = getLavoratoreCoobbligato().getDataScadenzaPermessoSoggiorno();

								if( campoScadenzaPermesso == null )
								{
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_DATA_SCADENZA_PERMESSO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								}
								else{

									if(isValorizzato(tipoDocumentoLavoratore) )
									{

										boolean isTipoDocumentoPresente = false;
										StatusStraniero statusStraniero = (StatusStraniero) decodificaDad.getTfromMin(StatusStraniero.class, tipoDocumentoLavoratore, null);
										isTipoDocumentoPresente = (statusStraniero != null && statusStraniero.getId() !=null ? true : false);

										if(!isTipoDocumentoPresente){
											RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_TIPO_DOCUMENTO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
										}
										else{		


											if( tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_ATTESA_DI_PERMESSO) ){

												String dataConvenzionaleInAttesaDiPermesso = commonDad.getParametroCommaxById(ParametriConstants.DATA_CONVENZIONALE_IN_ATTESA_DI_PERMESSO).getValoreParametro();

												if( !DateUtil.isData1EqualsData2(campoScadenzaPermesso,DateUtil.convertiStringaInData(dataConvenzionaleInAttesaDiPermesso))){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_SCADENZA_PERMESSO_DIVERSA_DA_01_01_1900, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

												}
											}

											else if( tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_CARTA_PERMANENTE) ){

												String dataConvenzionaleCartaPermanente = commonDad.getParametroCommaxById(ParametriConstants.DATA_CONVENZIONALE_CARTA_PERMANENTE).getValoreParametro();

												if( !DateUtil.isData1EqualsData2(campoScadenzaPermesso,DateUtil.convertiStringaInData(dataConvenzionaleCartaPermanente))){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_SCADENZA_PERMESSO_DIVERSA_DA_31_12_2099, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
												}
											}
											else{	

												String scadenzaPermesso = commonDad.getParametroCommaxById(ParametriConstants.SCADENZA_PERMESSO).getValoreParametro();

												if( campoScadenzaPermesso.before(DateUtil.convertiStringaInData(scadenzaPermesso))){												
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_DATA_SCADENZA_PERMESSO_PRECEDENTE_AL_01_01_2000, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

												}
											}
										}

										String questura = getLavoratoreCoobbligato().getQuesturaRilascioTitoloSoggiorno();

										if(isValorizzato(questura)){

											boolean isQuesturaPresente = false;
											Questura  questuraDeco = (Questura) decodificaDad.getTfromMin(Questura.class, questura, null);
											isQuesturaPresente = (questuraDeco != null && questuraDeco.getId() !=null ? true : false);

											if(!isQuesturaPresente){
												RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_QUESTURA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

											}
										}


										if(tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_ATTESA_DI_PERMESSO) ||
												tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_RINNOVO) ||
												tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO)){

											if(isValorizzato(questura)){
												RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_QUESTURA_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
											}
										}
										else{

											if(isVuoto(questura)){
												RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_QUESTURA_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

											}
										}	
									}
								}
							}
						}
						if(tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)){

							if(isVuoto(getLavoratoreCoobbligato().getFlgSussistSistemAllog()) && isValorizzato(getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio()) ||
									isVuoto(getLavoratoreCoobbligato().getFlgSussistSistemAllog()) && isValorizzato(getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio())){

								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_SUSSISTENZA_SISTEM_ALLOGG_E_IMPEGNO_PAGAM_RIMPATRIO_COMPILATI_PARZIALMENTE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}
							else{
								if(isValorizzato(getLavoratoreCoobbligato().getFlgSussistSistemAllog()) && isValorizzato(getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio())){

									if (!getLavoratoreCoobbligato().getFlgSussistSistemAllog().equalsIgnoreCase("SI"))
									{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_FLAG_SUSSISTENZA_SISTEMAZIONE_ALLOGGIATIVA_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}

									if (!getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio().equalsIgnoreCase("SI"))
									{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_FLAG_IMPEGNO_DATORE_PAGAMENTO_SPESE_RIMPATRIO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
								}

								else{
									// Sussistenza della sistemazione alloggiativa	
									if(isValorizzato(getLavoratoreCoobbligato().getFlgSussistSistemAllog())){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_FLAG_SUSSISTENZA_SISTEMAZIONE_ALLOGGIATIVA_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}

									//Impegno del datore di lavoro al pagamento delle spese per il rimpatrio
									if(isValorizzato(getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio())){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_FLAG_IMPEGNO_DATORE_PAGAMENTO_SPESE_RIMPATRIO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}	
								}
							}
						}			
					}
					else{
						if( isValorizzato(tipoDocumentoLavoratore)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_TIPO_DOCUMENTO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}
						if( isValorizzato(campoMotivoPermesso)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.LAVORATORE_COOBBLIGATO_CODICE_MOTIVO_PERMESSO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}
				}


				// livello istruzione 
				String campoLivelloIstr = getLavoratoreCoobbligato().getLivelloIstruzione();
				if( isValorizzato(campoLivelloIstr))
				{
					boolean esitoCampoLivel = false;
					LivelloStudio livello = (LivelloStudio) decodificaDad.getTfromMin(LivelloStudio.class, campoLivelloIstr, null);
					esitoCampoLivel = (livello != null && livello.getId() !=null ? true : false);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCampoLivel, ErrorConstants.LAVORATORE_COOBBLIGATO_LIVELLO_ISTRUZIONE_STUDIO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}
		}
	}
}
