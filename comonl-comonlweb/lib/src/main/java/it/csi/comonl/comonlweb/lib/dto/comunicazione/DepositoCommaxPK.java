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
 * The Class DepositoCommaxPK.
 */
public class DepositoCommaxPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idDepositoCommax;
	private long progXml;

	/**
	 * @return the idDepositoCommax
	 */
	public long getIdDepositoCommax() {
		return idDepositoCommax;
	}
	
	/**
	 * @param idDepositoCommax the idDepositoCommax to set
	 */
	public void setIdDepositoCommax(long idDepositoCommax) {
		this.idDepositoCommax = idDepositoCommax;
	}

	/**
	 * @return the progXml
	 */
	public long getProgXml() {
		return progXml;
	}
	
	/**
	 * @param progXml the progXml to set
	 */
	public void setProgXml(long progXml) {
		this.progXml = progXml;
	}

}
