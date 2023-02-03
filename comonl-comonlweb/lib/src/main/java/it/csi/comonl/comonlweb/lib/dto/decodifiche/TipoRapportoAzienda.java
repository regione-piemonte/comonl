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
 * The Class TipoRapportoAzienda.
 */
public class TipoRapportoAzienda extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoRapportoAzienda;

	/**
	 * @return the dsComTTipoRapportoAzienda
	 */
	public String getDsComTTipoRapportoAzienda() {
		return dsComTTipoRapportoAzienda;
	}
	
	/**
	 * @param dsComTTipoRapportoAzienda the dsComTTipoRapportoAzienda to set
	 */
	public void setDsComTTipoRapportoAzienda(String dsComTTipoRapportoAzienda) {
		this.dsComTTipoRapportoAzienda = dsComTTipoRapportoAzienda;
	}

}
