/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;

/**
 * The persistent class for the COM_T_TOPONIMO database table.
 * 
 */
@Entity
@Table(name = "COM_T_TOPONIMO")
@NamedQuery(name = "ComTToponimo.findAll", query = "SELECT c FROM ComTToponimo c")
public class ComTToponimo implements Serializable, BaseEntity<String> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_T_TOPONIMO")
	private String idComTToponimo;

	@Column(name = "DS_COM_T_TOPONIMO")
	private String dsComTToponimo;

	private Long ordinamento;

	public ComTToponimo() {
	}

	public String getIdComTToponimo() {
		return this.idComTToponimo;
	}

	public void setIdComTToponimo(String idComTToponimo) {
		this.idComTToponimo = idComTToponimo;
	}

	public String getDsComTToponimo() {
		return this.dsComTToponimo;
	}

	public void setDsComTToponimo(String dsComTToponimo) {
		this.dsComTToponimo = dsComTToponimo;
	}

	public Long getOrdinamento() {
		return this.ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}

	@Override
	public String getId() {
		return this.idComTToponimo;
	}

	@Override
	public void setId(String id) {
		this.idComTToponimo = id;
	}

}
