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
 * The Class TipoRuolo.
 */
public class TipoRuolo extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsNomeRuolo;
	private String dsNomeRuoloIride;
	private String flgAbilitato;

	/**
	 * @return the dsNomeRuolo
	 */
	public String getDsNomeRuolo() {
		return dsNomeRuolo;
	}
	
	/**
	 * @param dsNomeRuolo the dsNomeRuolo to set
	 */
	public void setDsNomeRuolo(String dsNomeRuolo) {
		this.dsNomeRuolo = dsNomeRuolo;
	}

	/**
	 * @return the dsNomeRuoloIride
	 */
	public String getDsNomeRuoloIride() {
		return dsNomeRuoloIride;
	}
	
	/**
	 * @param dsNomeRuoloIride the dsNomeRuoloIride to set
	 */
	public void setDsNomeRuoloIride(String dsNomeRuoloIride) {
		this.dsNomeRuoloIride = dsNomeRuoloIride;
	}

	/**
	 * @return the flgAbilitato
	 */
	public String getFlgAbilitato() {
		return flgAbilitato;
	}
	
	/**
	 * @param flgAbilitato the flgAbilitato to set
	 */
	public void setFlgAbilitato(String flgAbilitato) {
		this.flgAbilitato = flgAbilitato;
	}

}
