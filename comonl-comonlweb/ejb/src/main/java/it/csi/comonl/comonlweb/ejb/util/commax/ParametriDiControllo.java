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
package it.csi.comonl.comonlweb.ejb.util.commax;

import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

/**
 * @version 1.0
 * @created 06-Mar-2008 18:33:44
 */
public class ParametriDiControllo {

	private ComunicazioneTracciatoUnicoDTO comunicazioneCorrenteDTO;
	private String nomeFileXml;
	private java.lang.Long idReport;
	private String codiceFiscaleLavoratore;
	private String idAnno;
	private String codiceFiscaleAzienda;
	
	public String getCodiceFiscaleAzienda() {
		return codiceFiscaleAzienda;
	}

	public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda) {
		this.codiceFiscaleAzienda = codiceFiscaleAzienda;
	}

	public ParametriDiControllo(){

	}

	public void finalize() throws Throwable {

	}

	public ComunicazioneTracciatoUnicoDTO getcomunicazioneCorrenteDTO(){
		return comunicazioneCorrenteDTO;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setcomunicazioneCorrenteDTO(ComunicazioneTracciatoUnicoDTO newVal){
		comunicazioneCorrenteDTO = newVal;
	}

	public String getnomeFileXml(){
		return nomeFileXml;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setnomeFileXml(String newVal){
		nomeFileXml = newVal;
	}

	public java.lang.Long getidReport(){
		return idReport;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setidReport(java.lang.Long newVal){
		idReport = newVal;
	}
	
	public String getCodiceFiscaleLavoratore() {
		return codiceFiscaleLavoratore;
	}


	public void setCodiceFiscaleLavoratore(String codiceFiscaleLavoratore) {
		this.codiceFiscaleLavoratore = codiceFiscaleLavoratore;
	}


	public String getIdAnno() {
		return idAnno;
	}


	public void setIdAnno(String idAnno) {
		this.idAnno = idAnno;
	}
	

}
