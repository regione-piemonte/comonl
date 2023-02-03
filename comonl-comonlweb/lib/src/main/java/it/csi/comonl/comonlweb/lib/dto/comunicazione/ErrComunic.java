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
 * The Class ErrComunic.
 */
public class ErrComunic extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dataIns;
	private String desErr;

	/**
	 * @return the dataIns
	 */
	public Date getDataIns() {
		return dataIns;
	}
	
	/**
	 * @param dataIns the dataIns to set
	 */
	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	/**
	 * @return the desErr
	 */
	public String getDesErr() {
		return desErr;
	}
	
	/**
	 * @param desErr the desErr to set
	 */
	public void setDesErr(String desErr) {
		this.desErr = desErr;
	}

}
