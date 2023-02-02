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
package it.csi.comonl.comonlweb.ejb.util.commax.vo;


import java.util.Date;

import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;

public class DatiEssenzialiUnilavVO extends DatiEssenzialiVO {

	String codiceFiscaleLavoratoreCoobbligato; 							
	Date scadenzaTitoloSoggiornoCoobbligato; 
	String numTitoloSoggiornoCoobbligato; 							
	String sussistenzaSistemazioneAlloggiativaCoobbligato; 							
	String impegnoPagamentoSpesaRimpatrioCoobbligato; 

	String tipoOrario; 
	String statoCodiceTipoOrario;
	Date dataFineProroga; 
	Date dataTrasformazione;
	String codiceTrasformazione; 
	String statoCodiceTrasformazione;
	String codiceFiscaleDatoreDistaccatario; 
	Date dataCessazione;
	String causaCessazione;
	String statoCodiceCausaCessazione;
	
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
	public String getCodiceFiscaleLavoratoreCoobbligato() {
		return codiceFiscaleLavoratoreCoobbligato;
	}
	public void setCodiceFiscaleLavoratoreCoobbligato(
			String codiceFiscaleLavoratoreCoobbligato) {
		this.codiceFiscaleLavoratoreCoobbligato = codiceFiscaleLavoratoreCoobbligato;
	}
	public Date getScadenzaTitoloSoggiornoCoobbligato() {
		return scadenzaTitoloSoggiornoCoobbligato;
	}
	public void setScadenzaTitoloSoggiornoCoobbligato(
			Date scadenzaTitoloSoggiornoCoobbligato) {
		this.scadenzaTitoloSoggiornoCoobbligato = scadenzaTitoloSoggiornoCoobbligato;
	}
	public String getNumTitoloSoggiornoCoobbligato() {
		return numTitoloSoggiornoCoobbligato;
	}
	public void setNumTitoloSoggiornoCoobbligato(
			String numTitoloSoggiornoCoobbligato) {
		this.numTitoloSoggiornoCoobbligato = numTitoloSoggiornoCoobbligato;
	}
	public String getSussistenzaSistemazioneAlloggiativaCoobbligato() {
		return sussistenzaSistemazioneAlloggiativaCoobbligato;
	}
	public void setSussistenzaSistemazioneAlloggiativaCoobbligato(
			String sussistenzaSistemazioneAlloggiativaCoobbligato) {
		this.sussistenzaSistemazioneAlloggiativaCoobbligato = sussistenzaSistemazioneAlloggiativaCoobbligato;
	}
	public String getImpegnoPagamentoSpesaRimpatrioCoobbligato() {
		return impegnoPagamentoSpesaRimpatrioCoobbligato;
	}
	public void setImpegnoPagamentoSpesaRimpatrioCoobbligato(
			String impegnoPagamentoSpesaRimpatrioCoobbligato) {
		this.impegnoPagamentoSpesaRimpatrioCoobbligato = impegnoPagamentoSpesaRimpatrioCoobbligato;
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
	public String getCausaCessazione() {
		return causaCessazione;
	}
	public void setCausaCessazione(String causaCessazione) {
		this.causaCessazione = causaCessazione;
	}
	public Date getDataCessazione() {
		return dataCessazione;
	}
	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}
	public Date getDataTrasformazione() {
		return dataTrasformazione;
	}
	public void setDataTrasformazione(Date dataTrasformazione) {
		this.dataTrasformazione = dataTrasformazione;
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
		
		DatiEssenzialiUnilavVO datiEssenzialiDB = (DatiEssenzialiUnilavVO)obj;
		
		return(isCampiUguali(codiceFiscaleAzienda, datiEssenzialiDB.getCodiceFiscaleAzienda()) && 
			   isCampiUguali(codiceFiscaleLavoratore, datiEssenzialiDB.getCodiceFiscaleLavoratore()) && 							
			   isDateUguali(scadenzaTitoloSoggiorno, datiEssenzialiDB.getScadenzaTitoloSoggiorno()) && 														
			   isCampiUguali(numTitoloSoggiorno, datiEssenzialiDB.getNumTitoloSoggiorno()) && 							
			   isCampiUguali(sussistenzaSistemazioneAlloggiativa, datiEssenzialiDB.getSussistenzaSistemazioneAlloggiativa()) && 							
			   isCampiUguali(impegnoPagamentoSpesaRimpatrio, datiEssenzialiDB.getImpegnoPagamentoSpesaRimpatrio()) && 
			   isCampiUguali(codiceFiscaleLavoratoreCoobbligato, datiEssenzialiDB.getCodiceFiscaleLavoratoreCoobbligato()) && 							
			   isDateUguali(scadenzaTitoloSoggiornoCoobbligato, datiEssenzialiDB.getScadenzaTitoloSoggiornoCoobbligato()) && 														
			   isCampiUguali(numTitoloSoggiornoCoobbligato, datiEssenzialiDB.getNumTitoloSoggiornoCoobbligato()) && 							
			   isCampiUguali(sussistenzaSistemazioneAlloggiativaCoobbligato, datiEssenzialiDB.getSussistenzaSistemazioneAlloggiativaCoobbligato()) && 							
			   isCampiUguali(impegnoPagamentoSpesaRimpatrioCoobbligato, datiEssenzialiDB.getImpegnoPagamentoSpesaRimpatrioCoobbligato()) && 
			   isDateUguali(dataInizioRapporto, datiEssenzialiDB.getDataInizioRapporto()) && 
			   isDateUguali(dataFineRapporto, datiEssenzialiDB.getDataFineRapporto()) && 
			   isCampiUguali(tipologiaContrattuale, datiEssenzialiDB.getTipologiaContrattuale(), datiEssenzialiDB.getStatoCodiceTipologiaContrattuale()) && 
			   isCampiUguali(tipoOrario, datiEssenzialiDB.getTipoOrario(), datiEssenzialiDB.getStatoCodiceTipoOrario()) && 
			   isDateUguali(dataFineProroga, datiEssenzialiDB.getDataFineProroga()) &&
			   isDateUguali(dataTrasformazione, datiEssenzialiDB.getDataTrasformazione()) &&
			   isCampiUguali(codiceTrasformazione, datiEssenzialiDB.getCodiceTrasformazione(), datiEssenzialiDB.getStatoCodiceTrasformazione()) && 
			   isCampiUguali(codiceFiscaleDatoreDistaccatario, datiEssenzialiDB.getCodiceFiscaleDatoreDistaccatario()) && 
			   isDateUguali(dataCessazione, datiEssenzialiDB.getDataCessazione()) &&
			   isCampiUguali(causaCessazione, datiEssenzialiDB.getCausaCessazione(), datiEssenzialiDB.getStatoCodiceCausaCessazione()) &&
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
				.append("codiceFiscaleLavoratoreCoobbligato: ").append(codiceFiscaleLavoratoreCoobbligato).append("\n")
				.append("scadenzaTitoloSoggiornoCoobbligato: ").append(DateUtil.convertiDataInStringa(scadenzaTitoloSoggiornoCoobbligato)).append("\n")
				.append("numTitoloSoggiornoCoobbligato: ").append(numTitoloSoggiornoCoobbligato).append("\n")
				.append("sussistenzaSistemazioneAlloggiativaCoobbligato: ").append(sussistenzaSistemazioneAlloggiativaCoobbligato).append("\n")
				.append("impegnoPagamentoSpesaRimpatrioCoobbligato: ").append(impegnoPagamentoSpesaRimpatrioCoobbligato).append("\n")
				.append("dataInizioRapporto: ").append(DateUtil.convertiDataInStringa(dataInizioRapporto)).append("\n")
				.append("dataFineRapporto: ").append(DateUtil.convertiDataInStringa(dataFineRapporto)).append("\n")
				.append("tipologiaContrattuale: ").append(tipologiaContrattuale).append("\n")
				.append("tipoOrario: ").append(tipoOrario).append("\n")
				.append("dataFineProroga: ").append(DateUtil.convertiDataInStringa(dataFineProroga)).append("\n")
				.append("dataTrasformazione: ").append(DateUtil.convertiDataInStringa(dataTrasformazione)).append("\n")
				.append("codiceTrasformazione: ").append(codiceTrasformazione).append("\n")
				.append("codiceFiscaleDatoreDistaccatario: ").append(codiceFiscaleDatoreDistaccatario).append("\n")
				.append("dataCessazione: ").append(DateUtil.convertiDataInStringa(dataCessazione)).append("\n")
				.append("causaCessazione: ").append(causaCessazione).append("\n")
				.append("dataFinePeriodoFormativo: ").append(dataFinePeriodoFormativo).append("\n");
			
			return str.toString();
				
		
		
	}
	
}
