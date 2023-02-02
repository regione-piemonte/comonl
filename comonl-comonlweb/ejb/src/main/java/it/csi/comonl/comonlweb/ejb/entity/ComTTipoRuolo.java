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
 * The persistent class for the COM_T_TIPO_RUOLO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_RUOLO")
@NamedQuery(name="ComTTipoRuolo.findAll", query="SELECT c FROM ComTTipoRuolo c")
public class ComTTipoRuolo implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="DS_NOME_RUOLO")
	private String dsNomeRuolo;

	@Column(name="DS_NOME_RUOLO_IRIDE")
	private String dsNomeRuoloIride;

	@Column(name="FLG_ABILITATO")
	private String flgAbilitato;

	public ComTTipoRuolo() {
	}

//	public long getId() {
//		return this.id;
//	}

//	public void setId(long id) {
//		this.id = id;
//	}

	public String getDsNomeRuolo() {
		return this.dsNomeRuolo;
	}

	public void setDsNomeRuolo(String dsNomeRuolo) {
		this.dsNomeRuolo = dsNomeRuolo;
	}

	public String getDsNomeRuoloIride() {
		return this.dsNomeRuoloIride;
	}

	public void setDsNomeRuoloIride(String dsNomeRuoloIride) {
		this.dsNomeRuoloIride = dsNomeRuoloIride;
	}

	public String getFlgAbilitato() {
		return this.flgAbilitato;
	}

	public void setFlgAbilitato(String flgAbilitato) {
		this.flgAbilitato = flgAbilitato;
	}

}
