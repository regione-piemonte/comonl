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
 * The Class DelegatoImpresaPK.
 */
public class DelegatoImpresaPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfDelegato;
	private String cfImpresa;
	private String tipoAnagrafica;

	/**
	 * @return the cfDelegato
	 */
	public String getCfDelegato() {
		return cfDelegato;
	}
	
	/**
	 * @param cfDelegato the cfDelegato to set
	 */
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}

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
	 * @return the tipoAnagrafica
	 */
	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}
	
	/**
	 * @param tipoAnagrafica the tipoAnagrafica to set
	 */
	public void setTipoAnagrafica(String tipoAnagrafica) {
		this.tipoAnagrafica = tipoAnagrafica;
	}

}
