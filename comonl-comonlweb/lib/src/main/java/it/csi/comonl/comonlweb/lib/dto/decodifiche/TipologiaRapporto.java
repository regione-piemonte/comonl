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
 * The Class TipologiaRapporto.
 */
public class TipologiaRapporto extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipologiaRapporto;

	/**
	 * @return the dsComTTipologiaRapporto
	 */
	public String getDsComTTipologiaRapporto() {
		return dsComTTipologiaRapporto;
	}
	
	/**
	 * @param dsComTTipologiaRapporto the dsComTTipologiaRapporto to set
	 */
	public void setDsComTTipologiaRapporto(String dsComTTipologiaRapporto) {
		this.dsComTTipologiaRapporto = dsComTTipologiaRapporto;
	}

}
