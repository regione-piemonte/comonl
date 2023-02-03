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
 * The Class TipoDatore.
 */
public class TipoDatore extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoDatore;

	/**
	 * @return the dsComTTipoDatore
	 */
	public String getDsComTTipoDatore() {
		return dsComTTipoDatore;
	}
	
	/**
	 * @param dsComTTipoDatore the dsComTTipoDatore to set
	 */
	public void setDsComTTipoDatore(String dsComTTipoDatore) {
		this.dsComTTipoDatore = dsComTTipoDatore;
	}

}
