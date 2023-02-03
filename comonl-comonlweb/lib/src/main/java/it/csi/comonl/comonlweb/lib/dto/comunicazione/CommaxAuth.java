/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class CommaxAuth.
 */
public class CommaxAuth extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfAzienda;
	private String cfOperatore;
	private String dsCognome;
	private String dsEmail;
	private String dsNome;
	private String dsRuoloOperatore;
	private String dsTokenAuth;
	private String dtCreazione;
	private String tipoRuolo;

	/**
	 * @return the cfAzienda
	 */
	public String getCfAzienda() {
		return cfAzienda;
	}
	
	/**
	 * @param cfAzienda the cfAzienda to set
	 */
	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	/**
	 * @return the cfOperatore
	 */
	public String getCfOperatore() {
		return cfOperatore;
	}
	
	/**
	 * @param cfOperatore the cfOperatore to set
	 */
	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	/**
	 * @return the dsCognome
	 */
	public String getDsCognome() {
		return dsCognome;
	}
	
	/**
	 * @param dsCognome the dsCognome to set
	 */
	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	/**
	 * @return the dsEmail
	 */
	public String getDsEmail() {
		return dsEmail;
	}
	
	/**
	 * @param dsEmail the dsEmail to set
	 */
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	/**
	 * @return the dsNome
	 */
	public String getDsNome() {
		return dsNome;
	}
	
	/**
	 * @param dsNome the dsNome to set
	 */
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	/**
	 * @return the dsRuoloOperatore
	 */
	public String getDsRuoloOperatore() {
		return dsRuoloOperatore;
	}
	
	/**
	 * @param dsRuoloOperatore the dsRuoloOperatore to set
	 */
	public void setDsRuoloOperatore(String dsRuoloOperatore) {
		this.dsRuoloOperatore = dsRuoloOperatore;
	}

	/**
	 * @return the dsTokenAuth
	 */
	public String getDsTokenAuth() {
		return dsTokenAuth;
	}
	
	/**
	 * @param dsTokenAuth the dsTokenAuth to set
	 */
	public void setDsTokenAuth(String dsTokenAuth) {
		this.dsTokenAuth = dsTokenAuth;
	}

	/**
	 * @return the dtCreazione
	 */
	public String getDtCreazione() {
		return dtCreazione;
	}
	
	/**
	 * @param dtCreazione the dtCreazione to set
	 */
	public void setDtCreazione(String dtCreazione) {
		this.dtCreazione = dtCreazione;
	}

	/**
	 * @return the tipoRuolo
	 */
	public String getTipoRuolo() {
		return tipoRuolo;
	}
	
	/**
	 * @param tipoRuolo the tipoRuolo to set
	 */
	public void setTipoRuolo(String tipoRuolo) {
		this.tipoRuolo = tipoRuolo;
	}

}
