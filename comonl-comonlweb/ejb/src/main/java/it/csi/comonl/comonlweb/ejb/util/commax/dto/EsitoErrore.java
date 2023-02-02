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

/**
 * @version 1.0
 * @created 06-Mar-2008 18:07:28
 */
public class EsitoErrore {

	// Per commax viene popolato con id_com_d_upl_documenti
	private Long idReport;
	private String codiciFiscaliLavoratoreAzienda;
	private Long idMessaggio;
	/**
	 * Property aggiunta da Simone e Fabio per gestire la messaggistica dinamica
	 */
	private String dsMessaggio;

	public EsitoErrore(){

	}

	public void finalize() throws Throwable {

	}

	public String getcodiciFiscaliLavoratoreAzienda(){
		return codiciFiscaliLavoratoreAzienda;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setcodiciFiscaliLavoratoreAzienda(String newVal){
		codiciFiscaliLavoratoreAzienda = newVal;
	}

	public Long getIdReport() {
		return idReport;
	}

	public void setIdReport(Long idReport) {
		this.idReport = idReport;
	}

	public Long getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(Long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getDsMessaggio() {
		return dsMessaggio;
	}

	public void setDsMessaggio(String dsMessaggio) {
		this.dsMessaggio = dsMessaggio;
	}

}
