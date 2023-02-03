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
 * The Class TipoProvenienza.
 */
public class TipoProvenienza extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTipoProvenienzaMin;
	private String dsComTTipoProvenienza;

	/**
	 * @return the codTipoProvenienzaMin
	 */
	public String getCodTipoProvenienzaMin() {
		return codTipoProvenienzaMin;
	}
	
	/**
	 * @param codTipoProvenienzaMin the codTipoProvenienzaMin to set
	 */
	public void setCodTipoProvenienzaMin(String codTipoProvenienzaMin) {
		this.codTipoProvenienzaMin = codTipoProvenienzaMin;
	}

	/**
	 * @return the dsComTTipoProvenienza
	 */
	public String getDsComTTipoProvenienza() {
		return dsComTTipoProvenienza;
	}
	
	/**
	 * @param dsComTTipoProvenienza the dsComTTipoProvenienza to set
	 */
	public void setDsComTTipoProvenienza(String dsComTTipoProvenienza) {
		this.dsComTTipoProvenienza = dsComTTipoProvenienza;
	}

}
