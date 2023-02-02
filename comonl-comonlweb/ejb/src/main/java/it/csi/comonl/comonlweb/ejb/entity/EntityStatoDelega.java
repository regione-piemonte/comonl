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
 * The persistent class for the STATO_DELEGA database table.
 * 
 */
@Entity
@Table(name="STATO_DELEGA")
@NamedQuery(name="StatoDelega.findAll", query="SELECT s FROM EntityStatoDelega s")
public class EntityStatoDelega implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idStatoDelega;
	}

	@Override
	public void setId(String id) {
		idStatoDelega = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STATO_DELEGA")
	private String idStatoDelega;

	@Column(name="DS_STATO_DELEGA")
	private String dsStatoDelega;

	@Column(name="T_USER")
	private String tUser;

	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="statoDelega")
//	private List<Delega> delegas;

	public EntityStatoDelega() {
	}

	public String getIdStatoDelega() {
		return this.idStatoDelega;
	}

	public void setIdStatoDelega(String idStatoDelega) {
		this.idStatoDelega = idStatoDelega;
	}

	public String getDsStatoDelega() {
		return this.dsStatoDelega;
	}

	public void setDsStatoDelega(String dsStatoDelega) {
		this.dsStatoDelega = dsStatoDelega;
	}

	public String getTUser() {
		return this.tUser;
	}

	public void setTUser(String tUser) {
		this.tUser = tUser;
	}

//	public List<Delega> getDelegas() {
//		return this.delegas;
//	}
//
//	public void setDelegas(List<Delega> delegas) {
//		this.delegas = delegas;
//	}
//
//	public Delega addDelega(Delega delega) {
//		getDelegas().add(delega);
//		delega.setStatoDelega(this);
//
//		return delega;
//	}
//
//	public Delega removeDelega(Delega delega) {
//		getDelegas().remove(delega);
//		delega.setStatoDelega(null);
//
//		return delega;
//	}

}
