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
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class StatoComunicazione.
 */
public class StatoComunicazione extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTStatoComunicazione;
	private String tUser;

	/**
	 * @return the dsComTStatoComunicazione
	 */
	public String getDsComTStatoComunicazione() {
		return dsComTStatoComunicazione;
	}
	
	/**
	 * @param dsComTStatoComunicazione the dsComTStatoComunicazione to set
	 */
	public void setDsComTStatoComunicazione(String dsComTStatoComunicazione) {
		this.dsComTStatoComunicazione = dsComTStatoComunicazione;
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
