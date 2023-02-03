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
 * The Class TipoSomministrazione.
 */
public class TipoSomministrazione extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoSomministrazione;

	/**
	 * @return the dsComTTipoSomministrazione
	 */
	public String getDsComTTipoSomministrazione() {
		return dsComTTipoSomministrazione;
	}
	
	/**
	 * @param dsComTTipoSomministrazione the dsComTTipoSomministrazione to set
	 */
	public void setDsComTTipoSomministrazione(String dsComTTipoSomministrazione) {
		this.dsComTTipoSomministrazione = dsComTTipoSomministrazione;
	}

}
