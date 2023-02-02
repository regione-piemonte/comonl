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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda;


import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.AgenziaSomministrazioneDTO;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.LegaleRappresentante;
import it.csi.spicom.dto.TitoloSoggiorno;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneDatoreDiLavoro extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		DatoreDiLavoroDTO datoreLavoro = identificaDatoreLavoroDTORispettoTipoTracciato();

		if( isUNILAV() || isVARDATORI() )
		{

			// Codice fiscale datore
			checkCodiceFiscaleItaliano(datoreLavoro, elencoErrori, commonDad);

			// Comune sede legale
			checkComuneSedeLegale(datoreLavoro, elencoErrori, commonDad, decodificaDad);

			//Cap sede Legale
			checkCapSedeLegale(datoreLavoro, elencoErrori, commonDad);

			// settore Ateco
			String campoSettoreAteco = datoreLavoro.getSettore();
			if( isValorizzato(campoSettoreAteco))
			{
				boolean esitoAteco = false;
				Atecofin ateco = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, campoSettoreAteco, null);
				esitoAteco = (ateco != null && ateco.getId() !=null ? true : false);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoAteco, ErrorConstants.DATORE_CODICE_ATECO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

			//pubblica amministrazione
			String pubblicaAmministrazione = datoreLavoro.getPubblicaAmministrazione();

			if( isVuoto(pubblicaAmministrazione)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_FLG_PUBBLICA_AMMINISTRAZIONE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			else{
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(pubblicaAmministrazione), ErrorConstants.DATORE_FLG_PUBBLICA_AMMINISTRAZIONE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

		}

		if(isUNILAV()){

			// comune o stato estero sede operativa
			String comuneSedeLavoro = datoreLavoro.getComuneSedeLavoro();

			String cap = datoreLavoro.getCapSedeLavoro();

			if(isValorizzato(comuneSedeLavoro) ){

				boolean isComuneOStatoEsteroValido = isIdComuneOStatoEsteroValido(comuneSedeLavoro, decodificaDad, null);			

				if(!isComuneOStatoEsteroValido){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_COMUNE_O_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else{

					if(isCodiceStatoEstero(comuneSedeLavoro) && isValorizzato(cap)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_ESTERA_CON_CAP_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if(isCodiceComune(comuneSedeLavoro) && isVuoto(cap)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_CON_CAP_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

				}

			}

		}

		if( isUNISOM() )
		{

			// num iscrizione ALBO
			AgenziaSomministrazioneDTO agSommDTo = (AgenziaSomministrazioneDTO)datoreLavoro;

			if( agSommDTo.getNumIscrizioneAlbo() != null )
			{
				String campoIscrAlbo = agSommDTo.getNumIscrizioneAlbo();
				if(isValorizzato(campoIscrAlbo) )
				{
					boolean esitoIscrAlbo = isNumIscrizioneAlboValido( campoIscrAlbo );
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoIscrAlbo, ErrorConstants.DATORE_NUM_ISCRIZIONE_ALBO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}




			if(UtilConstant.FLAG_SI.equalsIgnoreCase(agSommDTo.getFlgAgenziaEstera())){
				
				// Codice fiscale datore
				checkCodiceFiscaleInternazionale(datoreLavoro,  elencoErrori, commonDad, decodificaDad);
				
				//Stato estero sede legale
				String idStatoEstero = datoreLavoro.getComuneSedeLegale();

				//Cap Sede Legale
				String capSedeLegale = datoreLavoro.getCapSedeLegale();

				if( isValorizzato(idStatoEstero) )
				{
					boolean esitoStatoEsteroSedeLegale = isStatoEsteroComunitarioValido(idStatoEstero, decodificaDad, null);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoStatoEsteroSedeLegale, ErrorConstants.DATORE_SEDE_LEGALE_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}


				if(isValorizzato(capSedeLegale)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_LEGALE_CAP_VALORIZZATO_PER_AZI_ESTERA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}
			else{

				checkCodiceFiscaleItaliano(datoreLavoro, elencoErrori, commonDad);

				checkComuneSedeLegale(datoreLavoro, elencoErrori, commonDad, decodificaDad);

				checkCapSedeLegale(datoreLavoro, elencoErrori, commonDad);


			}


			// comune o stato estero sede operativa
			String idComuneSedeLavoro = datoreLavoro.getComuneSedeLavoro();

			String cap = datoreLavoro.getCapSedeLavoro();

			if(isValorizzato(idComuneSedeLavoro) ){

				boolean isComuneOStatoEsteroValido = isComuneOStatoEsteroComunitarioValido(idComuneSedeLavoro, decodificaDad, null);			

				if(!isComuneOStatoEsteroValido){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_COMUNE_O_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else{

					if(isCodiceStatoEstero(idComuneSedeLavoro) && isValorizzato(cap)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_ESTERA_CON_CAP_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if(isCodiceComune(idComuneSedeLavoro) && isVuoto(cap)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_CON_CAP_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}
			}
		}

		if( isUNILAV() || isUNISOM()){	
						
			if(isModelloQPresente() && !isDatiAnagraficiPresenti(datoreLavoro.getLegaleRappresentante())){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_DATI_ANAGRAFICI_NON_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			

			String valoriSedeOperativa[]={datoreLavoro.getTelefonoSedeLavoro(),
					datoreLavoro.getFaxSedeLavoro(),
					datoreLavoro.getEmailSedeLavoro()};


			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeOperativa), ErrorConstants.DATORE_SEDE_OPERATIVA_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


			if(isValorizzato(datoreLavoro.getEmailSedeLavoro()) ){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(datoreLavoro.getEmailSedeLavoro()), ErrorConstants.DATORE_SEDE_OPERATIVA_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
		}
		
		LegaleRappresentante legaleRappesentante = datoreLavoro.getLegaleRappresentante();

		if(legaleRappesentante!= null){

			TitoloSoggiorno titoloSoggiorno = datoreLavoro.getLegaleRappresentante().getTitoloSoggiorno();

			if(titoloSoggiorno != null &&  !isDatiAnagraficiPresenti(legaleRappesentante)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_DATI_ANAGRAFICI_NON_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}

			else{


				// comune o stato estero nascita	
				String comuneOStatoEsteroDiNascita = legaleRappesentante.getComuneOStatoEsteroNascita();

				if( isValorizzato(comuneOStatoEsteroDiNascita) )
				{
					//boolean esitoComuneONazioneNascita = isIdComuneOStatoEsteroValido(comuneOStatoEsteroDiNascita, decodificaDad, legaleRappesentante.getDataNascita());
					boolean esitoComuneONazioneNascita = isIdComuneOStatoEsteroValido(comuneOStatoEsteroDiNascita, decodificaDad, null);
					if (!esitoComuneONazioneNascita)
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneONazioneNascita, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_COMUNE_O_STATO_ESTERO_NASCITA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}

				// cittadinanza
				String campoCittadinanza = legaleRappesentante.getCittadinanza();
				Cittadinanza cittadinanza = null;
				if( isValorizzato(campoCittadinanza) )
				{

					boolean isCittadinanzaPresente = false;
					cittadinanza = (Cittadinanza) decodificaDad.getTfromMin(Cittadinanza.class, campoCittadinanza, null);
					isCittadinanzaPresente = (cittadinanza != null && cittadinanza.getId() !=null ? true : false);

					
					if(!isCittadinanzaPresente){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_CODICE_CITTADINANZA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					} else{

						String flagComunitarioUe = cittadinanza.getFlgUe();

						String soggiornanteInItalia = datoreLavoro.getLegaleRappresentante().getSoggiornanteInItalia();

						if("S".equals(flagComunitarioUe)) {


							if(titoloSoggiorno != null){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_TITOLO_SOGGIORNO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}

							if(isValorizzato(soggiornanteInItalia)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_SOGGIORNANTE_IN_ITALIA_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}
						}
						else{

							//soggiornanteInItalia

							if(isVuoto(soggiornanteInItalia)){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_SOGGIORNANTE_IN_ITALIA_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

							}
							else{


								if(soggiornanteInItalia.equalsIgnoreCase(UtilConstant.FLAG_SI)){

									if(titoloSoggiorno == null){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_TITOLO_SOGGIORNO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}
									else{

										// motivo permesso
										String campoMotivoPermesso = titoloSoggiorno.getMotivoPermesso();

										boolean isMotivoPermessoPresente = false;
										MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(MotivoPermesso.class, campoMotivoPermesso, null);
										isMotivoPermessoPresente = (motivoPermesso != null && motivoPermesso.getId() !=null ? true : false);

										if(!isMotivoPermessoPresente){
											RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_MOTIVO_PERMESSO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
										}

										// tipo documento
										String tipoDocumentoLavoratore = titoloSoggiorno.getTipoDocumento();

										// scadenza permesso
										Date campoScadenzaPermesso = titoloSoggiorno.getDataScadenzaPS();

										boolean isTipoDocumentoPresente = false;
										StatusStraniero statusStraniero = (StatusStraniero) decodificaDad.getTfromMin(StatusStraniero.class, tipoDocumentoLavoratore, null); //FIXME non so che data usare
										isTipoDocumentoPresente = (statusStraniero != null && statusStraniero.getId() !=null ? true : false);

										
										if(!isTipoDocumentoPresente){
											RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_TIPO_DOCUMENTO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
										}
										else{
											
											if( tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_ATTESA_DI_PERMESSO) ){

												String dataConvenzionaleInAttesaDiPermesso = commonDad.getParametroCommaxById(ParametriConstants.DATA_CONVENZIONALE_IN_ATTESA_DI_PERMESSO).getValoreParametro();
												
												if( !DateUtil.isData1EqualsData2(campoScadenzaPermesso, DateUtil.convertiStringaInData(dataConvenzionaleInAttesaDiPermesso))){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_DATA_SCADENZA_PERMESSO_DIVERSA_DA_01_01_1900, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

												}
											}

											else if( tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_CARTA_PERMANENTE) ){
												
												String dataConvenzionaleCartaPermanente = commonDad.getParametroCommaxById(ParametriConstants.DATA_CONVENZIONALE_CARTA_PERMANENTE).getValoreParametro();

												if( !DateUtil.isData1EqualsData2(campoScadenzaPermesso, DateUtil.convertiStringaInData(dataConvenzionaleCartaPermanente))){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_DATA_SCADENZA_PERMESSO_DIVERSA_DA_31_12_2099, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
												}
											}
											else{				
												
												String scadenzaPermesso = commonDad.getParametroCommaxById(ParametriConstants.SCADENZA_PERMESSO).getValoreParametro();
												if( campoScadenzaPermesso.before(DateUtil.convertiStringaInData(scadenzaPermesso))){												
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_DATA_SCADENZA_PERMESSO_PRECEDENTE_AL_01_01_2000, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

												}
											}


											String questura = titoloSoggiorno.getQuestura();

											if(isValorizzato(questura)){

												boolean isQuesturaPresente = false;
												Questura questuraObj = (Questura) decodificaDad.getTfromMin(Questura.class, questura, null); //FIXME non so che data mettere
												isQuesturaPresente = (questuraObj != null && questuraObj.getId() !=null ? true : false);


												if(!isQuesturaPresente){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_CODICE_QUESTURA_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
												}

												else{

													if(isQuesturaNonAmmessaPerTipoDocumento(tipoDocumentoLavoratore)){
														RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_CODICE_QUESTURA_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
													}

												}
											}
											else{

												if(isQuesturaAmessaPerTipoDocumento(tipoDocumentoLavoratore)){
													RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_CODICE_QUESTURA_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

												}

											}

										}

									}

								}
								else{

									if(titoloSoggiorno != null){
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_LEGALE_RAPPRESENTANTE_TITOLO_SOGGIORNO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
									}										
								}

							}

						}

					}

				}	
			}

		}


		String valoriSedeLegale[]={datoreLavoro.getTelefonoSedeLegale(),
				datoreLavoro.getFaxSedeLegale(),
				datoreLavoro.getEmailSedeLegale()};

		RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeLegale), ErrorConstants.DATORE_SEDE_LEGALE_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);


		if(isValorizzato(datoreLavoro.getEmailSedeLegale()) ){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(datoreLavoro.getEmailSedeLegale()), ErrorConstants.DATORE_SEDE_LEGALE_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}


		if( comunicazione.getDatiInvio() != null )
		{	

			String codiceFiscaleSoggAbilitato = comunicazione.getDatiInvio().getCodFiscaleSoggettoComunicante(); 
			String campoCodFiscaleDatoreLavoro = datoreLavoro.getCodFiscale();

			// se il soggetto non esiste o non e' accentrato, si controlla l'accentramento del datore 
			if( isSoggettoNonValorizzatoONonAbilitatoInAccentramento(codiceFiscaleSoggAbilitato, decodificaDad)  )
			{
				// Se il datore non e' in accentramento , cioe' se non e' presente nella tabella COM_D_ANAGRAFICA_AZI_ACCENT
				if( isValorizzato(campoCodFiscaleDatoreLavoro) && !isDatoreLavoroPresenteInAccentramento(campoCodFiscaleDatoreLavoro, decodificaDad) )
				{
					//e il comune non e' piemontese
					if(isUNILAV() || isUNISOM()){
						String codComuneSedeLavoro = datoreLavoro.getComuneSedeLavoro();
						comuneNonPiemontese(codComuneSedeLavoro, elencoErrori, commonDad, decodificaDad);											
					}
					else if(isVARDATORI()){
						String codComuneSedeLegale = datoreLavoro.getComuneSedeLegale();					
						comuneNonPiemontese(codComuneSedeLegale, elencoErrori, commonDad, decodificaDad);		
					}
				}
			}
		}
	}


	private boolean isQuesturaNonAmmessaPerTipoDocumento(String tipoDocumentoLavoratore) {
		return tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_ATTESA_DI_PERMESSO) ||
			   tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_IN_RINNOVO) ||
			   tipoDocumentoLavoratore.equals(UtilConstant.TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO);
	}

	private boolean isQuesturaAmessaPerTipoDocumento(String tipoDocumentoLavoratore) {
		return !isQuesturaNonAmmessaPerTipoDocumento(tipoDocumentoLavoratore);
	}

	private boolean isModelloQPresente() {
		
		return isValorizzato(getLavoratoreComunicazione().getFlgPagamSpesaRimpatrio()) && isValorizzato(getLavoratoreComunicazione().getFlgSussistSistemAllog()); 
		
	}


	private boolean isDatiAnagraficiPresenti(LegaleRappresentante  legaleRappresentante) {
		
		if(legaleRappresentante == null){
			return false;
		}

		else if(legaleRappresentante.getCognome() != null && 
				legaleRappresentante.getNome() != null && 
				legaleRappresentante.getDataNascita() != null &&
				legaleRappresentante.getComuneOStatoEsteroNascita() != null &&
				legaleRappresentante.getSesso() != null && 
				legaleRappresentante.getComuneOStatoEsteroNascita() != null){

			return true;

		}

		return false;
	}


	private void checkCodiceFiscaleItaliano(DatoreDiLavoroDTO datoreLavoro, List<ApiError> elencoErrori, CommonDad commonDad)	throws Exception {

		String campoCodFiscaleDatoreLavoro = datoreLavoro.getCodFiscale();
		if( isValorizzato(campoCodFiscaleDatoreLavoro))
		{
			boolean esitoCodFisc = isCodiceFiscaleOPartitaIvaValido(campoCodFiscaleDatoreLavoro);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, ErrorConstants.DATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
		else{
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
	}


	private void checkCodiceFiscaleInternazionale(DatoreDiLavoroDTO datoreLavoro, List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {

		String campoCodFiscaleDatoreLavoro = datoreLavoro.getCodFiscale();
		if( isValorizzato(campoCodFiscaleDatoreLavoro))
		{
			boolean esitoCodFisc = isCodiceFiscaleInternazionaleValido(campoCodFiscaleDatoreLavoro, decodificaDad);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, ErrorConstants.DATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
		else{
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
		
	}

	

	private void checkCapSedeLegale(DatoreDiLavoroDTO datoreLavoro,  List<ApiError> elencoErrori, CommonDad commonDad)	throws Exception {
		
		String capSedeLegale = datoreLavoro.getCapSedeLegale();
		if(isVuoto(capSedeLegale)){
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_LEGALE_CAP_OBBLIGATORIO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			
		}
	}


	private void checkComuneSedeLegale(DatoreDiLavoroDTO datoreLavoro,  List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {
		
		String comuneSedeLegale = datoreLavoro.getComuneSedeLegale();

		if(isValorizzato(comuneSedeLegale) )
		{
			boolean esitoComuneSedeLegale = isIdComuneValido(comuneSedeLegale, decodificaDad, null);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneSedeLegale, ErrorConstants.DATORE_SEDE_LEGALE_COMUNE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
	}


	private void comuneNonPiemontese(String codComune, List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws Exception {
		if( isValorizzato(codComune) )
		{
			if( !isComunePiemontese(codComune, decodificaDad) ){
				if(isVARDATORI()){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.VARDATORI_DATORE_SEDE_LEGALE_NON_PIEMONTESE_E_NON_ACCENTRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else if (isUNILAV() || isUNISOM()){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_SEDE_OPERATIVA_NON_PIEMONTESE_E_NON_ACCENTRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}
		}
	}

}
