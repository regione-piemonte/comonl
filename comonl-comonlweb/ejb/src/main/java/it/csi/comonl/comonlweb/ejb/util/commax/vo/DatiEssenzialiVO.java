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

import it.csi.comonl.comonlweb.ejb.util.commax.util.Format;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;

public class DatiEssenzialiVO {
	
	String codiceFiscaleAzienda; 
	String codiceFiscaleLavoratore; 							
	Date scadenzaTitoloSoggiorno;													
	String numTitoloSoggiorno; 							
	String sussistenzaSistemazioneAlloggiativa; 							
	String impegnoPagamentoSpesaRimpatrio; 
	Date dataInizioRapporto; 
	Date dataFineRapporto; 
	String tipologiaContrattuale; 
	String statoCodiceTipologiaContrattuale;
	Date dataTimbroPstale;
	Date dataFinePeriodoFormativo;
 	
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
	public Date getDataTimbroPstale() {
		return dataTimbroPstale;
	}
	public void setDataTimbroPstale(Date dataTimbroPstale) {
		this.dataTimbroPstale = dataTimbroPstale;
	}
		
	public String getStatoCodiceTipologiaContrattuale() {
		return statoCodiceTipologiaContrattuale;
	}
	public void setStatoCodiceTipologiaContrattuale(
			String statoCodiceTipologiaContrattuale) {
		this.statoCodiceTipologiaContrattuale = statoCodiceTipologiaContrattuale;
	}

	public boolean isDateUguali(Date data1, Date data2){
		if(data1 == null && data2 == null){
			return true;
		}
		
		if((data1 == null && data2 != null) || (data1 != null && data2 == null)){
			return false;
		}
		
		if(Format.confrontaDate(data1, data2) == 0){
						
			return true;
		}
		
		return false;
	}
	
	public boolean isCampiUguali(String campo1, String campo2){
		
		if(campo1 == null && campo2 == null){
			
			return true;
			
		}
		
		if((campo1 == null && campo2 != null) || (campo1 != null && campo2 == null)){
			
			return false;
			
		}
		
		return campo1.equalsIgnoreCase(campo2);
				
	}
	
	public boolean isCampiUguali(String campo1, String campo2, String statoCodice){
		
		if(UtilConstant.STATO_CODICE_SCADUTO.equals(statoCodice)){
			return true;
		}
		
		return isCampiUguali(campo1, campo2);				
	}
	public Date getDataFinePeriodoFormativo() {
		return dataFinePeriodoFormativo;
	}
	public void setDataFinePeriodoFormativo(Date dataFinePeriodoFormativo) {
		this.dataFinePeriodoFormativo = dataFinePeriodoFormativo;
	}

}
