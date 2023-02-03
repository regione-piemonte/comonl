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
package it.csi.comonl.comonlweb.lib.dto.decodifiche;

import java.io.Serializable;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class Cpi.
 */
public class Cpi extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cUtente;
	private String codCpi;
	private String dsComTCpi;
	private Date dtFine;
	private Date dtInizio;

	/**
	 * @return the cUtente
	 */
	public String getCUtente() {
		return cUtente;
	}

	/**
	 * @param cUtente the cUtente to set
	 */
	public void setCUtente(String cUtente) {
		this.cUtente = cUtente;
	}

	/**
	 * @return the codCpi
	 */
	public String getCodCpi() {
		return codCpi;
	}

	/**
	 * @param codCpi the codCpi to set
	 */
	public void setCodCpi(String codCpi) {
		this.codCpi = codCpi;
	}

	/**
	 * @return the dsComTCpi
	 */
	public String getDsComTCpi() {
		return dsComTCpi;
	}

	/**
	 * @param dsComTCpi the dsComTCpi to set
	 */
	public void setDsComTCpi(String dsComTCpi) {
		this.dsComTCpi = dsComTCpi;
	}

	/**
	 * @return the dtFine
	 */
	public Date getDtFine() {
		return dtFine;
	}

	/**
	 * @param dtFine the dtFine to set
	 */
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	/**
	 * @return the dtInizio
	 */
	public Date getDtInizio() {
		return dtInizio;
	}

	/**
	 * @param dtInizio the dtInizio to set
	 */
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

}
