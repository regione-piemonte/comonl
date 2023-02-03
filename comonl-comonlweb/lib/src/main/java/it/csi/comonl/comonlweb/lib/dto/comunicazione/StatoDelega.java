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
 * The Class StatoDelega.
 */
public class StatoDelega extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsStatoDelega;
	private String tUser;

	/**
	 * @return the dsStatoDelega
	 */
	public String getDsStatoDelega() {
		return dsStatoDelega;
	}
	
	/**
	 * @param dsStatoDelega the dsStatoDelega to set
	 */
	public void setDsStatoDelega(String dsStatoDelega) {
		this.dsStatoDelega = dsStatoDelega;
	}

	/**
	 * @return the tUser
	 */
	public String getTUser() {
		return tUser;
	}
	
	/**
	 * @param tUser the tUser to set
	 */
	public void setTUser(String tUser) {
		this.tUser = tUser;
	}

}
