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

public class ErroriFileVO {

	Long idErrori = null;
	String descrizioneErrore = null;
	String nomeFile = null;
	String cfLavoratore = null;
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}
	public Long getIdErrori() {
		return idErrori;
	}
	public void setIdErrori(Long idErrori) {
		this.idErrori = idErrori;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getCfLavoratore() {
		return cfLavoratore;
	}
	public void setCfLavoratore(String cfLavoratore) {
		this.cfLavoratore = cfLavoratore;
	}

}
