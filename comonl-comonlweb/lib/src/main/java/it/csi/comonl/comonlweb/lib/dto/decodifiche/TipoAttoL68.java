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
 * The Class TipoAttoL68.
 */
public class TipoAttoL68 extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoAttoL68;

	/**
	 * @return the dsComTTipoAttoL68
	 */
	public String getDsComTTipoAttoL68() {
		return dsComTTipoAttoL68;
	}
	
	/**
	 * @param dsComTTipoAttoL68 the dsComTTipoAttoL68 to set
	 */
	public void setDsComTTipoAttoL68(String dsComTTipoAttoL68) {
		this.dsComTTipoAttoL68 = dsComTTipoAttoL68;
	}

}
