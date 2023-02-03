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
 * The Class CaricaPersonaPv.
 */
public class Toponimo extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String idComTToponimo;
	private String dsComTToponimo;
	private Long ordinamento;

	public String getDsComTToponimo() {
		return dsComTToponimo;
	}

	public void setDsComTToponimo(String dsComTToponimo) {
		this.dsComTToponimo = dsComTToponimo;
	}

	public Long getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}

	@Override
	public String toString() {
		return "Toponimo [dsComTToponimo=" + dsComTToponimo + ", ordinamento=" + ordinamento + ", id=" + id
				+ ", getDsComTToponimo()=" + getDsComTToponimo() + ", getOrdinamento()=" + getOrdinamento()
				+ ", getId()=" + getId() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

	public String getIdComTToponimo() {
		return idComTToponimo;
	}

	public void setIdComTToponimo(String idComTToponimo) {
		this.idComTToponimo = idComTToponimo;
	}

}
