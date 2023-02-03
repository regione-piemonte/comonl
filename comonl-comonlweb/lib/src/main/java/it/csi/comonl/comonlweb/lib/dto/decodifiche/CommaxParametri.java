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
 * The Class CommaxParametri.
 */
public class CommaxParametri extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private byte[] bDato;
	private String dsParametro;
	private String tipoParametro;
	private String valoreParametro;

	/**
	 * @return the bDato
	 */
	public byte[] getBDato() {
		return bDato;
	}
	
	/**
	 * @param bDato the bDato to set
	 */
	public void setBDato(byte[] bDato) {
		this.bDato = bDato;
	}

	/**
	 * @return the dsParametro
	 */
	public String getDsParametro() {
		return dsParametro;
	}
	
	/**
	 * @param dsParametro the dsParametro to set
	 */
	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	/**
	 * @return the tipoParametro
	 */
	public String getTipoParametro() {
		return tipoParametro;
	}
	
	/**
	 * @param tipoParametro the tipoParametro to set
	 */
	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	/**
	 * @return the valoreParametro
	 */
	public String getValoreParametro() {
		return valoreParametro;
	}
	
	/**
	 * @param valoreParametro the valoreParametro to set
	 */
	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}

}
