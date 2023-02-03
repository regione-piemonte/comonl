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
 * The Class TipoLavoratore.
 */
public class TipoLavoratore extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoLavoratore;

	/**
	 * @return the dsComTTipoLavoratore
	 */
	public String getDsComTTipoLavoratore() {
		return dsComTTipoLavoratore;
	}
	
	/**
	 * @param dsComTTipoLavoratore the dsComTTipoLavoratore to set
	 */
	public void setDsComTTipoLavoratore(String dsComTTipoLavoratore) {
		this.dsComTTipoLavoratore = dsComTTipoLavoratore;
	}

}
