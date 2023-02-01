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
package it.csi.comonl.comonlweb.ejb.util.commax.vo;



import java.util.Date;

import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;

public class DatiEssenzialiUnisommVO extends DatiEssenzialiVO {

	String codiceFiscaledittaUtilizzatrice;
	Date dataInizioMissione;
	Date dataFineMissione;
	String tipoOrario;
	String statoCodiceTipoOrario;
	Date dataFineProrogaMissione;
	//Date dataInizioMissioneProroga;
	Date dataTrasformazioneMissione;
	String statoCodiceTrasformazione;
	String codiceTrasformazione;
	//Date dataInizioMissioneTrasformazione;
	Date dataCessazioneMissione;
	String codiceCessazione;
	String statoCodiceCausaCessazione;
	//Date dataInizioMissioneCessazione;

	public String getCodiceFiscaledittaUtilizzatrice() {
		return codiceFiscaledittaUtilizzatrice;
	}
	public void setCodiceFiscaledittaUtilizzatrice(
			String codiceFiscaledittaUtilizzatrice) {
		this.codiceFiscaledittaUtilizzatrice = codiceFiscaledittaUtilizzatrice;
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
	public String getTipoOrario() {
		return tipoOrario;
	}
	public void setTipoOrario(String tipoOrario) {
		this.tipoOrario = tipoOrario;
	}
	public Date getDataFineProrogaMissione() {
		return dataFineProrogaMissione;
	}
	public void setDataFineProrogaMissione(Date dataFineProrogaMissione) {
		this.dataFineProrogaMissione = dataFineProrogaMissione;
	}
	public Date getDataTrasformazioneMissione() {
		return dataTrasformazioneMissione;
	}
	public void setDataTrasformazioneMissione(Date dataTrasformazioneMissione) {
		this.dataTrasformazioneMissione = dataTrasformazioneMissione;
	}
	public String getCodiceTrasformazione() {
		return codiceTrasformazione;
	}
	public void setCodiceTrasformazione(String codiceTrasformazione) {
		this.codiceTrasformazione = codiceTrasformazione;
	}
	public Date getDataCessazioneMissione() {
		return dataCessazioneMissione;
	}
	public void setDataCessazioneMissione(Date dataCessazioneMissione) {
		this.dataCessazioneMissione = dataCessazioneMissione;
	}
	public String getCodiceCessazione() {
		return codiceCessazione;
	}
	public void setCodiceCessazione(String codiceCessazione) {
		this.codiceCessazione = codiceCessazione;
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
	
	public boolean equals(Object obj) {
		if(obj ==  null) return false;
		if(obj == this) return true;
		if(obj.getClass() != this.getClass()) return false;
		
		DatiEssenzialiUnisommVO datiEssenzialiDB = (DatiEssenzialiUnisommVO)obj;
		
		return(isCampiUguali(codiceFiscaleAzienda, datiEssenzialiDB.getCodiceFiscaleAzienda()) && 
			   isCampiUguali(codiceFiscaleLavoratore, datiEssenzialiDB.getCodiceFiscaleLavoratore()) && 							
			   isDateUguali(scadenzaTitoloSoggiorno, datiEssenzialiDB.getScadenzaTitoloSoggiorno()) && 														
			   isCampiUguali(numTitoloSoggiorno, datiEssenzialiDB.getNumTitoloSoggiorno()) && 							
			   isCampiUguali(sussistenzaSistemazioneAlloggiativa, datiEssenzialiDB.getSussistenzaSistemazioneAlloggiativa()) && 							
			   isCampiUguali(impegnoPagamentoSpesaRimpatrio, datiEssenzialiDB.getImpegnoPagamentoSpesaRimpatrio()) && 
			   isDateUguali(dataInizioRapporto, datiEssenzialiDB.getDataInizioRapporto()) && 
			   isDateUguali(dataFineRapporto, datiEssenzialiDB.getDataFineRapporto()) && 
			   isCampiUguali(tipologiaContrattuale, datiEssenzialiDB.getTipologiaContrattuale(), datiEssenzialiDB.getStatoCodiceTipologiaContrattuale()) && 		
			   isCampiUguali(codiceFiscaledittaUtilizzatrice, datiEssenzialiDB.getCodiceFiscaledittaUtilizzatrice()) &&								
			   isDateUguali(dataInizioMissione, datiEssenzialiDB.getDataInizioMissione()) &&
			   isDateUguali(dataFineMissione, datiEssenzialiDB.getDataFineMissione()) &&
			   isCampiUguali(tipoOrario, datiEssenzialiDB.getTipoOrario(), datiEssenzialiDB.getStatoCodiceTipoOrario()) &&
			   isDateUguali(dataFineProrogaMissione, datiEssenzialiDB.getDataFineProrogaMissione()) &&
			   isDateUguali(dataTrasformazioneMissione, datiEssenzialiDB.getDataTrasformazioneMissione()) &&
			   isCampiUguali(codiceTrasformazione, datiEssenzialiDB.getCodiceTrasformazione(), datiEssenzialiDB.getStatoCodiceTrasformazione()) &&
			   isDateUguali(dataCessazioneMissione, datiEssenzialiDB.getDataCessazioneMissione()) &&
			   isCampiUguali(codiceCessazione, datiEssenzialiDB.getCodiceCessazione(), datiEssenzialiDB.getStatoCodiceCausaCessazione()) &&
			   isDateUguali(dataFinePeriodoFormativo, datiEssenzialiDB.getDataFinePeriodoFormativo())); 
	}
	
	public String toString() {

		StringBuffer str = new StringBuffer();
		
		str.append("codiceFiscaleAzienda: ").append(codiceFiscaleAzienda).append("\n")
			.append("codiceFiscaleLavoratore: ").append(codiceFiscaleLavoratore).append("\n")
			.append("scadenzaTitoloSoggiorno: ").append(DateUtil.convertiDataInStringa(scadenzaTitoloSoggiorno)).append("\n")
			.append("numTitoloSoggiorno: ").append(numTitoloSoggiorno).append("\n")
			.append("sussistenzaSistemazioneAlloggiativa: ").append(sussistenzaSistemazioneAlloggiativa).append("\n")
			.append("impegnoPagamentoSpesaRimpatrio: ").append(impegnoPagamentoSpesaRimpatrio).append("\n")
			.append("dataInizioRapporto: ").append(DateUtil.convertiDataInStringa(dataInizioRapporto)).append("\n")
			.append("dataFineRapporto: ").append(DateUtil.convertiDataInStringa(dataFineRapporto)).append("\n")
			.append("tipologiaContrattuale: ").append(tipologiaContrattuale).append("\n")
			.append("codiceFiscaledittaUtilizzatrice: ").append(codiceFiscaledittaUtilizzatrice).append("\n")
			.append("dataInizioMissione: ").append(DateUtil.convertiDataInStringa(dataInizioMissione)).append("\n")
			.append("dataFineMissione: ").append(DateUtil.convertiDataInStringa(dataFineMissione)).append("\n")
			.append("tipoOrario: ").append(tipoOrario).append("\n")
			.append("dataFineProrogaMissione: ").append(DateUtil.convertiDataInStringa(dataFineProrogaMissione)).append("\n")
			.append("dataTrasformazioneMissione: ").append(DateUtil.convertiDataInStringa(dataTrasformazioneMissione)).append("\n")
			.append("codiceTrasformazione: ").append(codiceTrasformazione).append("\n")
			.append("dataCessazioneMissione: ").append(DateUtil.convertiDataInStringa(dataCessazioneMissione)).append("\n")
			.append("codiceCessazione: ").append(codiceCessazione).append("\n").append("\n")
			.append("dataFinePeriodoFormativo: ").append(dataFinePeriodoFormativo).append("\n");
		
		return str.toString();
	}

}
