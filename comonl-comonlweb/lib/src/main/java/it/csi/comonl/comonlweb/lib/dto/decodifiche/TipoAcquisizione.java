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
 * The Class TipoAcquisizione.
 */
public class TipoAcquisizione extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoAcquisizione;

	/**
	 * @return the dsComTTipoAcquisizione
	 */
	public String getDsComTTipoAcquisizione() {
		return dsComTTipoAcquisizione;
	}
	
	/**
	 * @param dsComTTipoAcquisizione the dsComTTipoAcquisizione to set
	 */
	public void setDsComTTipoAcquisizione(String dsComTTipoAcquisizione) {
		this.dsComTTipoAcquisizione = dsComTTipoAcquisizione;
	}

}
