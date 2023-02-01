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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.tirocinio;

import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.util.TUConstants;

/**
 * Regole che si applicano da
 * adeguamenti gennaio 2014
 * 
 * @author 72160
 *
 */
public class RegoleValidazioneRapportoLavoroTirocinio extends ControlloAstrattoBloccante 
{

	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception {
		/*
		 * controllo 1 - 72160-1507
		 * 	Il cfSoggettoPromotoreTirocinio DEVE essere compilato solo in caso TipoContratto = "C.01.00 - TIROCINIO"
		 *  Non deve essere compilato negli altri casi.
		 *  Se prevista la compilazione il cf deve essere valido
		 *  
		 *  
		 * controllo 2 - validita/obbligatorieta dei campi
		 * Se il TIPO_CONTRATTO = TIPO_CONTRATTO_TIROCINIO devono essere valorizzati obbligatoriamente i campi:
		 * 		- tipologiaEntePromotore
		 *      - denominazione
		 *      - categoriaTirocinante
		 *      - tipologiaTirocinio
		 * Se il TIPO_CONTRATTO != TIPO_CONTRATTO_TIROCINIO NON devono essere valorizzati i campi:
		 * 		- tipologiaEntePromotore
		 *      - denominazione
		 *      - categoriaTirocinante
		 *      - tipologiaTirocinio
		 *      
		 * 
		 * controllo 3 - Tipologia tirocinio - Categoria tirocinio
		 * 
		 * Controllo vincolante: La tipologia di tirocinio deve essere valorizzata coerentemente con la categoria tirocinante. 
		 * Se la tipologia di tirocinio e' di tipo 
		 * 
		 * 	A sono ammessi solo i valori di 'categoria tirocinante' uguali a 01,02,04,05,06,07; 
		 *  B sono ammessi solo i valori di 'categoria tirocinante' uguali a 01,02,03,08; 
		 *  Z sono ammessi solo i valori di 'categoria tirocinante' uguali a 99; 
		 *
		 */		

		if(isUNILAV()) {



			/*XML MINISTERIALE PREVEDE I SEGUENTI CAMPI PER IL TAG TIROCINIO
			 * 	<xs:attribute name="tipologiaEntePromotore">
			 *  <xs:attribute name="CFSoggettoPromotore">
			 *  <xs:attribute name="Denominazione" type="co:StringaXLunga"/>
			 *  <xs:attribute name="CategoriaTirocinante">
			 *  <xs:attribute name="TipologieTirocinio">
			 * */
			String tipologiaEntePromotore = "";
			String cfSoggettoPromotoreTirocinio = "";
			String denominazione = "";
			String categoriaTirocinante = "";
			String tipologiaTirocinio = "";
			String cfDatore = "";

			if (comunicazione.getDatiAggiuntivi() != null && comunicazione.getDatiAggiuntivi().getDatiTirocinio() != null) {
				if (comunicazione.getDatiAggiuntivi().getDatiTirocinio().getTipologiaPromotore() != null) {
					tipologiaEntePromotore = comunicazione.getDatiAggiuntivi().getDatiTirocinio().getTipologiaPromotore().trim();
				}
				if (comunicazione.getDatiAggiuntivi().getDatiTirocinio().getCFSoggettoPromotoreTirocinio() != null) {
					cfSoggettoPromotoreTirocinio = comunicazione.getDatiAggiuntivi().getDatiTirocinio().getCFSoggettoPromotoreTirocinio().trim(); 
				}
				if (comunicazione.getDatiAggiuntivi().getDatiTirocinio().getAltroSoggettoPromotore() != null) {
					denominazione = comunicazione.getDatiAggiuntivi().getDatiTirocinio().getAltroSoggettoPromotore().trim();	
				}
				if (comunicazione.getDatiAggiuntivi().getDatiTirocinio().getCategoriaTirocinante() != null) {
					categoriaTirocinante = comunicazione.getDatiAggiuntivi().getDatiTirocinio().getCategoriaTirocinante().trim();
				}
				if (comunicazione.getDatiAggiuntivi().getDatiTirocinio().getTipologiaTirocinio() != null) {
					tipologiaTirocinio = comunicazione.getDatiAggiuntivi().getDatiTirocinio().getTipologiaTirocinio().trim();
				}
			}

			if (comunicazione.getDatoreDiLavoro() != null) {
				cfDatore = comunicazione.getDatoreDiLavoro().getCodFiscale();
			}

			String tipoContratto = comunicazione.getDatiInizio().getTipologiaContrattuale();			

			Date dataInizioRapporto = null;
			Date dataFineRapporto = null;			

			if (comunicazione.getDatiInizio().getDataInizio() != null && comunicazione.getDatiInizio().getDataFine() != null) {
				dataInizioRapporto = comunicazione.getDatiInizio().getDataInizio();
				dataFineRapporto = comunicazione.getDatiInizio().getDataFine();			
			}

			if (isValorizzato(tipoContratto)) {
				/*
				 * 1. 72160-1507
				 * Il campo cfSoggettoPromotoreTirocinio DEVE essere valorizzato (e valido) 
				 * solo in caso di  TIPO_CONTRATTO = TIPO_CONTRATTO_TIROCINIO (C.01.00)
				 *
				 * */
				verifyValuesTagTirocinioCFSoggettoPromotore(tipoContratto, cfSoggettoPromotoreTirocinio, cfDatore, elencoErrori, commonDad); 

				/* 2.
				 * Se il TIPO_CONTRATTO = TIPO_CONTRATTO_TIROCINIO  devono essere valorizzati obbligatoriamente i campi:
				 * 		- tipologiaEntePromotore - obbliagtorio
				 *      - denominazione          - obbliagtorio
				 *      - categoriaTirocinante   - obbliagtorio 
				 *      - tipologiaTirocinio     - obbligatorio
				 * Se il TIPO_CONTRATTO != TIPO_CONTRATTO_TIROCINIO NON devono essere valorizzati i campi:
				 * 		- tipologiaEntePromotore - non ammesso
				 *      - denominazione          - non ammesso
				 *      - categoriaTirocinante   - non ammesso 
				 *      - tipologiaTirocinio     - non ammesso 
				 * 
				 * 
				 * - INOLTRE:
				 * se i campi sono valorizzati DEVONO i codici ministeriali inseriti devono essere censiti nel DB
				 * campi con codice = 
				 * tipologiaEntePromotore
				 * CategoriaTirocinante
				 * TipologieTirocinio
				 * 
				 * */
				if (isContrattoTirocinio(tipoContratto)) {
					verifyValuesTagTirocinio(false, tipologiaEntePromotore, denominazione, categoriaTirocinante, tipologiaTirocinio, elencoErrori, commonDad );
					verifyValuesTagTirocinioCodici (tipologiaEntePromotore, categoriaTirocinante, tipologiaTirocinio, elencoErrori, commonDad, decodificaDad);

				} else {
					verifyValuesTagTirocinio(true, tipologiaEntePromotore, denominazione, categoriaTirocinante, tipologiaTirocinio, elencoErrori, commonDad );
				}


				/*
				 * 3.
				 * REGOLE DI VALIDAZIONE: Motore regole REGOLA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE
				 * 
				 * 
				 * */
				if (isContrattoTirocinio(tipoContratto) ) {


					if (isValorizzato(categoriaTirocinante) && isValorizzato(tipologiaTirocinio) )  {
						boolean regolaPresente = true;

						try {
							regolaPresente = matchRegolaTirocinio(UtilConstant.REGOLA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE, tipologiaTirocinio, categoriaTirocinante, commonDad, decodificaDad);
							if (!regolaPresente) {
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false,  getCostanteErr_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							} 
						} catch (Exception e) {
						}
					}

				}
			} 

		} 
	}


	/*
	 * 72160-1507
	 * Adeguamenti 2014 - 
	 * VERIFICHE SU OBBLIGATORIETA' CF SOGGETTO PROMOTORE TIROCINIO SOLO SE SIAMO IN UN CONTRATTO DI TIPO TIROCINIO "C.01.00"
	 *  
	 * */
	/*Verifica il popolamento del tag tirocinio
	 * mustBeVoid = true  --> nessun dato deve essere valorizzato
	 * mustBeVoid = false --> tutti i dati devono essere valorizzati
	 * */
	private void verifyValuesTagTirocinio (boolean mustBeVoid, 
			String tipologiaEntePromotore, 
			String denominazione, 
			String categoriaTirocinante, 
			String tipologiaTirocinio, 
			List<ApiError> elencoErrori, 
			CommonDad commonDad)  throws Exception {



		if (mustBeVoid) {
			if (isValorizzato(tipologiaEntePromotore)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isValorizzato(denominazione)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isValorizzato(categoriaTirocinante)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false,  getCostanteErr_CATEGORIA_TIROCINANTE_NON_AMMESSA (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isValorizzato(tipologiaTirocinio)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_TIPOLOGIA_TIROCINIO_NON_AMMESSA (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}

		} else {
			if (isVuoto(tipologiaEntePromotore)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isVuoto(denominazione)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO () , null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isVuoto(categoriaTirocinante)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_CATEGORIA_TIROCINANTE_NON_VALORIZZATO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
			if (isVuoto(tipologiaTirocinio)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
		}
	}

	/*
	 * RECUPERO COSTANTI ERRORE PER OGNI TIPOLOGIA DI ERRORE ASSOCIATA AL TIPO DI COMUNICAZIONE CHE SI STA VALIDANDO
	 * ASSUNZIONE
	 * PROROGA
	 * CESSAZIONE
	 * TRASFORMAZIONE
	 * 
	 * */
	//------------------------------------------------------------------------------
	private long getCostanteErr_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;
		}

		return val;

	}
	//------------------------------------------------------------------------------	
	private long getCostanteErr_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_AMMESSO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_CATEGORIA_TIROCINANTE_NON_AMMESSA () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CATEGORIA_TIROCINANTE_NON_AMMESSA;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CATEGORIA_TIROCINANTE_NON_AMMESSA;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CATEGORIA_TIROCINANTE_NON_AMMESSA;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CATEGORIA_TIROCINANTE_NON_AMMESSA;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_TIPOLOGIA_TIROCINIO_NON_AMMESSA () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_TIPOLOGIA_TIROCINIO_NON_AMMESSA;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_TIPOLOGIA_TIROCINIO_NON_AMMESSA;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_TIROCINIO_NON_AMMESSA;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_TIPOLOGIA_TIROCINIO_NON_AMMESSA;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_DENOMINAZIONE_ENTE_PROMOTORE_TIROCINIO_NON_VALORIZZATO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_CATEGORIA_TIROCINANTE_NON_VALORIZZATO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CATEGORIA_TIROCINANTE_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CATEGORIA_TIROCINANTE_NON_VALORIZZATO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CATEGORIA_TIROCINANTE_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CATEGORIA_TIROCINANTE_NON_VALORIZZATO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_TIPOLOGIA_TIROCINIO_NON_VALORIZZATO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE_ACCOPPIAMENTO_NON_VALIDO;
		}

		return val;

	}
	//------------------------------------------------------------------------------
	private long getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO;
		}

		return val;

	}

	//------------------------------------------------------------------------------
	private void verifyValuesTagTirocinioCFSoggettoPromotore(String tipoContratto, String cfSoggettoPromotoreTirocinio, String cfDatore, List<ApiError> elencoErrori, CommonDad commonDad) throws Exception {

		//se il contratto =  tirocinio C.01.00 ed il cf non e' valorizzato --> CF obbligatorio
		//se il contratto != tirocinio C.01.00 ed il cf  e'  valorizzato     --> CF non ammesso
		//se il contratto =  tirocinio C.01.00 ed il cf  e'  valorizzato     --> verifica correttezza CF
		//negli altri casi in cui il contratto != tirocinio C.01.00 e il cf non e' valorizzato --> OK

		boolean isCfObbligatorio = false;
		boolean isCfNonAmmesso   = false;
		boolean checkValiditaCf  = false;

		if (isContrattoTirocinio(tipoContratto) && isVuoto(cfSoggettoPromotoreTirocinio)) {
			isCfObbligatorio = true;
		} else if (!isContrattoTirocinio(tipoContratto) && isValorizzato(cfSoggettoPromotoreTirocinio)) {
			isCfNonAmmesso = true;
		} else if (isContrattoTirocinio(tipoContratto) && isValorizzato(cfSoggettoPromotoreTirocinio)) {
			checkValiditaCf = true;
		}

		if (isCfObbligatorio) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALORIZZATO(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}
		if (isCfNonAmmesso) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_AMMESSO(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		}

		if (checkValiditaCf) {
			boolean esitoCodFisc = isCodiceFiscaleOPartitaIvaValido(cfSoggettoPromotoreTirocinio);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, getCostanteErr_CODICE_FISCALE_SOGGETTO_PROMOTORE_TIROCINIO_NON_VALIDO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			if (!esitoCodFisc) {
			}

		}
	}



	//-------------------------------------------------------------------------------------------------------
	private void verifyValuesTagTirocinioCodici(String tipologiaEntePromotore, String categoriaTirocinante, String tipologieTirocinio, List<ApiError> elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad) throws Exception  {


		if(isValorizzato(tipologiaEntePromotore) ) {

			boolean esitoEntePromotore = false;
			TipoEntePromTirocinio tipoEnte  = (TipoEntePromTirocinio) decodificaDad.getTfromMin(TipoEntePromTirocinio.class, tipologiaEntePromotore, null	);
			esitoEntePromotore = (tipoEnte != null && tipoEnte.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoEntePromotore, getCostanteErr_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		} 
		if(isValorizzato(categoriaTirocinante) ) {
			boolean esitoCategoria = false;
			CategTirocinante tipoCategoria  = (CategTirocinante) decodificaDad.getTfromMin(CategTirocinante.class, categoriaTirocinante, null	);
			esitoCategoria = (tipoCategoria != null && tipoCategoria.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCategoria, getCostanteErr_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

		} 
		if(isValorizzato(tipologieTirocinio) ) {
			boolean esitoTipologia = false;
			TipologiaTirocinio tipoTirocinio  = (TipologiaTirocinio) decodificaDad.getTfromMin(TipologiaTirocinio.class, tipologieTirocinio, null	);
			esitoTipologia = (tipoTirocinio != null && tipoTirocinio.getId() !=null ? true : false);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoTipologia, getCostanteErr_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO (), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		} 

	}
	private long getCostanteErr_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_TIPOLOGIA_ENTE_PROMOTORE_TIROCINIO_NON_VALIDO;
		}

		return val;

	}
	private long getCostanteErr_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_CATEGORIA_TIROCINANTE_NON_VALIDO;
		}

		return val;

	}
	private long getCostanteErr_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO () {
		long val = 0L;

		if (isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			//cessazione
			val =  ErrorConstants.DATI_CESSAZIONE_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_PROROGA)) {
			//proroga
			val =  ErrorConstants.DATI_PROROGA_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && (tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) ||  
				tipoComunicazione.equals(UtilConstant.TRASFERIMENTO) ||
				tipoComunicazione.equals(UtilConstant.DISTACCO) ) ) {
			//trasformazione
			val =  ErrorConstants.DATI_TRASFORMAZIONE_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO;

		} else if (isUNILAV() && tipoComunicazione.equals(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) ) {
			//assunzione
			val = ErrorConstants.DATI_ASSUNZIONE_CODICE_TIPOLOGIA_TIROCINIO_NON_VALIDO;
		}

		return val;

	}
}
