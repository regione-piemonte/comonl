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
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class UserAccessLog.
 */
public class UserAccessLog extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfUtente;
	private String dsCognome;
	private String dsNome;
	private String dsRuolo;
	private Date dtEvento;
	private String flgTrovatoSuAaep;

	/**
	 * @return the cfUtente
	 */
	public String getCfUtente() {
		return cfUtente;
	}

	/**
	 * @param cfUtente the cfUtente to set
	 */
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
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
	 * @return the dsRuolo
	 */
	public String getDsRuolo() {
		return dsRuolo;
	}

	/**
	 * @param dsRuolo the dsRuolo to set
	 */
	public void setDsRuolo(String dsRuolo) {
		this.dsRuolo = dsRuolo;
	}

	/**
	 * @return the dtEvento
	 */
	public Date getDtEvento() {
		return dtEvento;
	}

	/**
	 * @param dtEvento the dtEvento to set
	 */
	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	/**
	 * @return the flgTrovatoSuAaep
	 */
	public String getFlgTrovatoSuAaep() {
		return flgTrovatoSuAaep;
	}

	/**
	 * @param flgTrovatoSuAaep the flgTrovatoSuAaep to set
	 */
	public void setFlgTrovatoSuAaep(String flgTrovatoSuAaep) {
		this.flgTrovatoSuAaep = flgTrovatoSuAaep;
	}

}
