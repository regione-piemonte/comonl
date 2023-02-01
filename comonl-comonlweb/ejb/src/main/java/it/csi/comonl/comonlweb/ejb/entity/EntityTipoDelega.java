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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the TIPO_DELEGA database table.
 * 
 */
@Entity
@Table(name="TIPO_DELEGA")
@NamedQuery(name="TipoDelega.findAll", query="SELECT t FROM EntityTipoDelega t")
public class EntityTipoDelega implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idTipoDelega;
	}

	@Override
	public void setId(String id) {
		idTipoDelega = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_DELEGA")
	private String idTipoDelega;

	@Column(name="DS_TIPO_DELEGA")
	private String dsTipoDelega;

	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="tipoDelega")
//	private List<Delega> delegas;
//
//	//bi-directional many-to-one association to ParametriTipoDelega
//	@OneToMany(mappedBy="tipoDelega")
//	private List<ParametriTipoDelega> parametriTipoDelegas;

	public EntityTipoDelega() {
	}

	public String getIdTipoDelega() {
		return this.idTipoDelega;
	}

	public void setIdTipoDelega(String idTipoDelega) {
		this.idTipoDelega = idTipoDelega;
	}

	public String getDsTipoDelega() {
		return this.dsTipoDelega;
	}

	public void setDsTipoDelega(String dsTipoDelega) {
		this.dsTipoDelega = dsTipoDelega;
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
//		delega.setTipoDelega(this);
//
//		return delega;
//	}
//
//	public Delega removeDelega(Delega delega) {
//		getDelegas().remove(delega);
//		delega.setTipoDelega(null);
//
//		return delega;
//	}

//	public List<ParametriTipoDelega> getParametriTipoDelegas() {
//		return this.parametriTipoDelegas;
//	}
//
//	public void setParametriTipoDelegas(List<ParametriTipoDelega> parametriTipoDelegas) {
//		this.parametriTipoDelegas = parametriTipoDelegas;
//	}
//
//	public ParametriTipoDelega addParametriTipoDelega(ParametriTipoDelega parametriTipoDelega) {
//		getParametriTipoDelegas().add(parametriTipoDelega);
//		parametriTipoDelega.setTipoDelega(this);
//
//		return parametriTipoDelega;
//	}
//
//	public ParametriTipoDelega removeParametriTipoDelega(ParametriTipoDelega parametriTipoDelega) {
//		getParametriTipoDelegas().remove(parametriTipoDelega);
//		parametriTipoDelega.setTipoDelega(null);
//
//		return parametriTipoDelega;
//	}

}
