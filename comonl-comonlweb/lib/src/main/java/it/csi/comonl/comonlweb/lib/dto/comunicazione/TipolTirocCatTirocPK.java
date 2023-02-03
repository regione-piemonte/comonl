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
 * The Class TipolTirocCatTirocPK.
 */
public class TipolTirocCatTirocPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idComTTipologiaTirocinio;
	private long idComTCategTirocinante;

	/**
	 * @return the idComTTipologiaTirocinio
	 */
	public long getIdComTTipologiaTirocinio() {
		return idComTTipologiaTirocinio;
	}
	
	/**
	 * @param idComTTipologiaTirocinio the idComTTipologiaTirocinio to set
	 */
	public void setIdComTTipologiaTirocinio(long idComTTipologiaTirocinio) {
		this.idComTTipologiaTirocinio = idComTTipologiaTirocinio;
	}

	/**
	 * @return the idComTCategTirocinante
	 */
	public long getIdComTCategTirocinante() {
		return idComTCategTirocinante;
	}
	
	/**
	 * @param idComTCategTirocinante the idComTCategTirocinante to set
	 */
	public void setIdComTCategTirocinante(long idComTCategTirocinante) {
		this.idComTCategTirocinante = idComTCategTirocinante;
	}

}
