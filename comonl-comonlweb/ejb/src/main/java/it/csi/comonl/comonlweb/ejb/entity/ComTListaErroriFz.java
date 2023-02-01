/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_COMMAX_PARAMETRI database table.
 * 
 */
@Entity
@Table(name="COM_T_LISTA_ERRORI_FZ")
@NamedQuery(name="ComTListaErroriFz.findAll", query="SELECT c FROM ComTListaErroriFz c")
public class ComTListaErroriFz implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idErrore;
	}

	@Override
	public void setId(Long id) {
		idErrore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_LISTA_ERRORI_FZ")
	private long idErrore;

	@Column(name="DS_ERRORE")
	private String dsErrore;

	public long getIdErrore() {
		return idErrore;
	}

	public void setIdErrore(long idErrore) {
		this.idErrore = idErrore;
	}

	public String getDsErrore() {
		return dsErrore;
	}

	public void setDsErrore(String dsErrore) {
		this.dsErrore = dsErrore;
	}


}
