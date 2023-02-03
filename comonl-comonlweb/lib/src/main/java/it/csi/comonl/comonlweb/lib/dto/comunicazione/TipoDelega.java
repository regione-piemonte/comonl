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
 * The Class TipoDelega.
 */
public class TipoDelega extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsTipoDelega;

	/**
	 * @return the dsTipoDelega
	 */
	public String getDsTipoDelega() {
		return dsTipoDelega;
	}
	
	/**
	 * @param dsTipoDelega the dsTipoDelega to set
	 */
	public void setDsTipoDelega(String dsTipoDelega) {
		this.dsTipoDelega = dsTipoDelega;
	}

}
