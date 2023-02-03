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
package it.csi.comonl.comonlweb.lib.dto.silp;

import java.io.Serializable;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class LavoratoriSilpPK.
 */
public class LavoratoriSilpPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfImpresa;
	private long idTProvincia;
	private String codiceFiscale;

	/**
	 * @return the cfImpresa
	 */
	public String getCfImpresa() {
		return cfImpresa;
	}
	
	/**
	 * @param cfImpresa the cfImpresa to set
	 */
	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	/**
	 * @return the idTProvincia
	 */
	public long getIdTProvincia() {
		return idTProvincia;
	}
	
	/**
	 * @param idTProvincia the idTProvincia to set
	 */
	public void setIdTProvincia(long idTProvincia) {
		this.idTProvincia = idTProvincia;
	}

	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
