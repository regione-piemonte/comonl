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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli;


import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * @version 1.0
 * @created 06-Mar-2008 18:07:27
 */
public abstract class ControlloAstrattoBloccante extends ControlloAstratto {

	/**
	 * Utilizzato per popolare l'esito ErroreBloccante
	 */
	private String nomeFileXML;


	public ControlloDatiRapportoMetodiRegoleComuni regoleComuni = new ControlloDatiRapportoMetodiRegoleComuni();

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Utilizzato per popolare l'esito ErroreBloccante
	 */
	public String getnomeFileXML(){
		return nomeFileXML;
	}

	/**
	 * Utilizzato per popolare l'esito ErroreBloccante
	 * 
	 * @param newVal
	 */
	public void setnomeFileXML(String newVal){
		nomeFileXML = newVal;
	}

	protected boolean isContrattoAgricoltura(String tipoContratto) {
		return tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_AGRICOLTURA_TD);
	}

	protected boolean isContrattoCollaborazioneCoordinataContinuativa (String tipoContratto) {
		return tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_COLLABORAZIONE_COORDINATA_CONTINUATIVA);
	}
	protected boolean isContrattoTirocinio(String tipoContratto) {
		return tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_TIROCINIO); 
	}

	protected boolean isContrattoRipartito(String tipoContratto) {
		return tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_RIPARTITO);
	}


	protected boolean isApprendistato(String tipoContratto) {
		return   tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_PER_LA_QUAL_PROFESSIONALE_E_DIPLOMA_PROF) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_PROF_O_CONTRATTO_DI_MESTIERE) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_DI_ALTA_FORMAZIONE_E_RICERCA);
	}

	protected boolean isCessazionePerApprendistato(String causaCessazione){
		return causaCessazione.equalsIgnoreCase(UtilConstant.CAUSA_CESSAZIONE_RECESSO_CON_PREAVVISO_AL_TERMINE_DELLA_FORMAZIONE)||
				causaCessazione.equalsIgnoreCase(UtilConstant.CAUSA_CESSAZIONE_LICENZIAMENTO_PER_GIUSTA_CAUSA_DURANTE_FORMAZAZIONE)||
				causaCessazione.equalsIgnoreCase(UtilConstant.CAUSA_CESSAZIONE_LICENZIAMENTO_GIUSTIFICATO_MOTIVO_DURANTE_FORMAZIONE)||
				causaCessazione.equalsIgnoreCase(UtilConstant.CAUSA_CESSAZIONE_DIMISSIONI_GIUSTA_CAUSA_GIUSTIFICATO_MOTIVO_DURANTE_FORMAZIONE);

	}


	protected boolean isContrattoPerLavoroStagionale (String tipoContratto) {
		return   tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_TEMPO_DETERMINATO) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_TEMPO_DETERMINATO_PER_SOSTITUZIONE) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_LAVORO_A_DOMICILIO) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_LAVORO_INTERMITTENTE) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_PER_LA_QUAL_PROFESSIONALE_E_DIPLOMA_PROF) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_PROF_O_CONTRATTO_DI_MESTIERE) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_APPRENDISTATO_DI_ALTA_FORMAZIONE_E_RICERCA) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_RIPARTITO) ||
				tipoContratto.equalsIgnoreCase(UtilConstant.TIPO_CONTRATTO_COLLABORAZIONE_COORDINATA_CONTINUATIVA)  ||
				tipoContratto.equalsIgnoreCase(UtilConstant.LAVORO_O_ATTIVITA_SOCIALMENTE_UTILE);

	}
//	public   void registraEsito(EsitoErrore esito)throws Exception {
//		elencoErrori.aggiungiErroreBloccante(esito);
//	}

	protected  void controllaRapportoTempoIndeterminatoConLavoroStagionale(String tipoContratto, String lavoroStagionale, long errorFrom, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		if(tipoContratto.equalsIgnoreCase(UtilConstant.TEMPO_INDETERMINATO)){
			if(lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						errorFrom, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
		}
	}
	protected  void controllaRapportoTempoIndeterminatoConLavoroStagionale(String tipoContratto, String lavoroStagionale, long errorFrom, CommonDad commonDad, List<ApiError> elencoErrori, String stringParametriPerDescrizoneErrore) throws Exception {
		if(tipoContratto.equalsIgnoreCase(UtilConstant.TEMPO_INDETERMINATO)){
			if(lavoroStagionale.equalsIgnoreCase(UtilConstant.FLAG_SI)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						errorFrom, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
				
			}
		}
	}
//	
//	protected void controllaCampoObbligatorioAndValidoDaTabellaDecodifica(String valoreDaControllare, String queryValidazione, long errorFromNonValido, long errorFromNonValorizzato, boolean isObbligatorio) throws Exception {
//		if(isValorizzato(valoreDaControllare)) {
//			registraErroreSeEsitoControlloNegativo(isChiavePresenteInTabellaDecodifica(valoreDaControllare,queryValidazione),errorFromNonValido);
//		} else {
//			if (isObbligatorio) {
//				registraErroreSeEsitoControlloNegativo(false,errorFromNonValorizzato);
//			}
//		}
//	}
//	
//	
	protected void controllaCampoObbligatorio (String valoreDaControllare, long errorFromNonValorizzato, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		if (isVuoto(valoreDaControllare)) {
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, errorFromNonValorizzato, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
		}
	}
//	protected void controllaCampoValiditaCampoDaTabellaDecodificaSePresente(String valoreDaControllare, String queryValidazione, long errorFromNonValido) throws Exception  {
//		if(isValorizzato(valoreDaControllare)) {
//			registraErroreSeEsitoControlloNegativo(isChiavePresenteInTabellaDecodifica(valoreDaControllare,queryValidazione),errorFromNonValido);
//		}
//	}
//	
	protected void controlloDataFineMenoDataInizioNonMaggioreDi50(Date dataFineRapporto, Date dataInizioRapporto, long errorFrom, CommonDad commonDad, List<ApiError> elencoErrori) throws  Exception {
		// Verifico che data fine meno data inizio non sia maggiore di 50 anni
		if(dataFineRapporto != null && dataInizioRapporto!=null)
		{	
			if(isDifferenzaDataFineDataInizioMaggioreDi50Anni(dataFineRapporto,dataInizioRapporto)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						errorFrom, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}
	}
	protected void controllaRapportoDataFineUnisomm(String tipoContratto, Date dataFineRapporto, long errorFromTempoIndeterminato, long errorFromTempoDeterminato, DecodificaDad decodificaDad, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		// controlli sulla data fine
		if(dataFineRapporto != null){
			if( isFlgFormaPerTipoContrattoIndeterminata(tipoContratto, decodificaDad)) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						errorFromTempoIndeterminato, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}
		else {
			if(isFlgFormaPerTipoContrattoDeterminata(tipoContratto, decodificaDad)){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						errorFromTempoDeterminato, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}
		}
	}
}
