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
 * The Class Delegato.
 */
public class Delegato extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfDelegato;
	private String cognomeDelegatoImpresa;
	private String mailDelegatoImpresa;
	private String nomeDelegatoImpresa;

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
	 * @return the cognomeDelegatoImpresa
	 */
	public String getCognomeDelegatoImpresa() {
		return cognomeDelegatoImpresa;
	}
	
	/**
	 * @param cognomeDelegatoImpresa the cognomeDelegatoImpresa to set
	 */
	public void setCognomeDelegatoImpresa(String cognomeDelegatoImpresa) {
		this.cognomeDelegatoImpresa = cognomeDelegatoImpresa;
	}

	/**
	 * @return the mailDelegatoImpresa
	 */
	public String getMailDelegatoImpresa() {
		return mailDelegatoImpresa;
	}
	
	/**
	 * @param mailDelegatoImpresa the mailDelegatoImpresa to set
	 */
	public void setMailDelegatoImpresa(String mailDelegatoImpresa) {
		this.mailDelegatoImpresa = mailDelegatoImpresa;
	}

	/**
	 * @return the nomeDelegatoImpresa
	 */
	public String getNomeDelegatoImpresa() {
		return nomeDelegatoImpresa;
	}
	
	/**
	 * @param nomeDelegatoImpresa the nomeDelegatoImpresa to set
	 */
	public void setNomeDelegatoImpresa(String nomeDelegatoImpresa) {
		this.nomeDelegatoImpresa = nomeDelegatoImpresa;
	}

}
