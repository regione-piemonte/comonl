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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;

import java.util.Date;

public class DatiRidottiComunicazione{	
	String codiceFiscaleAzienda;
	String codiceFiscaleDittaUtilizzatrice; 
	String codiceFiscaleLavoratore; 							
	String numTitoloSoggiorno; 	
	Date scadenzaTitoloSoggiorno;																			
	String sussistenzaSistemazioneAlloggiativa; 							
	String impegnoPagamentoSpesaRimpatrio; 
	Date dataInizioRapporto; 
	Date dataFineRapporto; 
	Date dataFinePeriodoFormativo;
	String tipologiaContrattuale; 					
	String tipoOrario; 
	String codiceFiscaleDatoreDistaccatario; 
	String tipoLavoratore;	
	Date dataInizioMissione;
	Date dataFineMissione;	
	Date dataFineProroga;	
	Date dataTrasformazione;
	Date dataCessazione;	
	String codiceTrasformazione; 
	String codiceCessazione; 
	String tipoRapportoAzienda;
	Date dataTimbroPostale;
	String statoCodiceTipologiaContrattuale;
	String statoCodiceTipoOrario;
	String statoCodiceTrasformazione;
	String statoCodiceCausaCessazione;
	
	
	public Date getDataCessazione() {
		return dataCessazione;
	}
	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	public Date getDataInizioMissione() {
		return dataInizioMissione;
	}
	public void setDataInizioMissione(Date dataInizioMissione) {
		this.dataInizioMissione = dataInizioMissione;
	}
	public Date getDataFineMissione() {
		return dataFineMissione;
	}
	public void setDataFineMissione(Date dataFineMissione) {
		this.dataFineMissione = dataFineMissione;
	}

	
	public String getCodiceFiscaleAzienda() {
		return codiceFiscaleAzienda;
	}
	public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda) {
		this.codiceFiscaleAzienda = codiceFiscaleAzienda;
	}
	public String getCodiceFiscaleLavoratore() {
		return codiceFiscaleLavoratore;
	}
	public void setCodiceFiscaleLavoratore(String codiceFiscaleLavoratore) {
		this.codiceFiscaleLavoratore = codiceFiscaleLavoratore;
	}
	public Date getScadenzaTitoloSoggiorno() {
		return scadenzaTitoloSoggiorno;
	}
	public void setScadenzaTitoloSoggiorno(Date scadenzaTitoloSoggiorno) {
		this.scadenzaTitoloSoggiorno = scadenzaTitoloSoggiorno;
	}
	public String getNumTitoloSoggiorno() {
		return numTitoloSoggiorno;
	}
	public void setNumTitoloSoggiorno(String numTitoloSoggiorno) {
		this.numTitoloSoggiorno = numTitoloSoggiorno;
	}
	public String getSussistenzaSistemazioneAlloggiativa() {
		return sussistenzaSistemazioneAlloggiativa;
	}
	public void setSussistenzaSistemazioneAlloggiativa(
			String sussistenzaSistemazioneAlloggiativa) {
		this.sussistenzaSistemazioneAlloggiativa = sussistenzaSistemazioneAlloggiativa;
	}
	public String getImpegnoPagamentoSpesaRimpatrio() {
		return impegnoPagamentoSpesaRimpatrio;
	}
	public void setImpegnoPagamentoSpesaRimpatrio(
			String impegnoPagamentoSpesaRimpatrio) {
		this.impegnoPagamentoSpesaRimpatrio = impegnoPagamentoSpesaRimpatrio;
	}
	public Date getDataInizioRapporto() {
		return dataInizioRapporto;
	}
	public void setDataInizioRapporto(Date dataInizioRapporto) {
		this.dataInizioRapporto = dataInizioRapporto;
	}
	public Date getDataFineRapporto() {
		return dataFineRapporto;
	}
	public void setDataFineRapporto(Date dataFineRapporto) {
		this.dataFineRapporto = dataFineRapporto;
	}
	public String getTipologiaContrattuale() {
		return tipologiaContrattuale;
	}
	public void setTipologiaContrattuale(String tipologiaContrattuale) {
		this.tipologiaContrattuale = tipologiaContrattuale;
	}
	public String getTipoOrario() {
		return tipoOrario;
	}
	public void setTipoOrario(String tipoOrario) {
		this.tipoOrario = tipoOrario;
	}
	public Date getDataFineProroga() {
		return dataFineProroga;
	}
	public void setDataFineProroga(Date dataFineProroga) {
		this.dataFineProroga = dataFineProroga;
	}
	public String getCodiceTrasformazione() {
		return codiceTrasformazione;
	}
	public void setCodiceTrasformazione(String codiceTrasformazione) {
		this.codiceTrasformazione = codiceTrasformazione;
	}
	public String getCodiceFiscaleDatoreDistaccatario() {
		return codiceFiscaleDatoreDistaccatario;
	}
	public void setCodiceFiscaleDatoreDistaccatario(
			String codiceFiscaleDatoreDistaccatario) {
		this.codiceFiscaleDatoreDistaccatario = codiceFiscaleDatoreDistaccatario;
	}
	public Date getDataTrasformazione() {
		return dataTrasformazione;
	}
	public void setDataTrasformazione(Date dataTrasformazione) {
		this.dataTrasformazione = dataTrasformazione;
	}
	public String getCodiceCessazione() {
		return codiceCessazione;
	}
	public void setCodiceCessazione(String codiceCessazione) {
		this.codiceCessazione = codiceCessazione;
	}
	public String getTipoLavoratore() {
		return tipoLavoratore;
	}
	public void setTipoLavoratore(String tipoLavoratore) {
		this.tipoLavoratore = tipoLavoratore;
	}
	public String getTipoRapportoAzienda() {
		return tipoRapportoAzienda;
	}
	public void setTipoRapportoAzienda(String tipoRapportoAzienda) {
		this.tipoRapportoAzienda = tipoRapportoAzienda;
	}
	public String getCodiceFiscaleDittaUtilizzatrice() {
		return codiceFiscaleDittaUtilizzatrice;
	}
	public void setCodiceFiscaleDittaUtilizzatrice(
			String codiceFiscaleDittaUtilizzatrice) {
		this.codiceFiscaleDittaUtilizzatrice = codiceFiscaleDittaUtilizzatrice;
	}
	public Date getDataTimbroPostale() {
		return dataTimbroPostale;
	}
	public void setDataTimbroPostale(Date dataTimbroPostale) {
		this.dataTimbroPostale = dataTimbroPostale;
	}
	public String getStatoCodiceTipologiaContrattuale() {
		return statoCodiceTipologiaContrattuale;
	}
	public void setStatoCodiceTipologiaContrattuale(
			String statoCodiceTipologiaContrattuale) {
		this.statoCodiceTipologiaContrattuale = statoCodiceTipologiaContrattuale;
	}
	public String getStatoCodiceTipoOrario() {
		return statoCodiceTipoOrario;
	}
	public void setStatoCodiceTipoOrario(String statoCodiceTipoOrario) {
		this.statoCodiceTipoOrario = statoCodiceTipoOrario;
	}
	public String getStatoCodiceTrasformazione() {
		return statoCodiceTrasformazione;
	}
	public void setStatoCodiceTrasformazione(String statoCodiceTrasformazione) {
		this.statoCodiceTrasformazione = statoCodiceTrasformazione;
	}
	public String getStatoCodiceCausaCessazione() {
		return statoCodiceCausaCessazione;
	}
	public void setStatoCodiceCausaCessazione(String statoCodiceCausaCessazione) {
		this.statoCodiceCausaCessazione = statoCodiceCausaCessazione;
	}
	public Date getDataFinePeriodoFormativo() {
		return dataFinePeriodoFormativo;
	}
	public void setDataFinePeriodoFormativo(Date dataFinePeriodoFormativo) {
		this.dataFinePeriodoFormativo = dataFinePeriodoFormativo;
	}

}
