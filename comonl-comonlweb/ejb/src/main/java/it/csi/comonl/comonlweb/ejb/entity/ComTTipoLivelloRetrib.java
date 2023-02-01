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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_LIVELLO_RETRIB database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_LIVELLO_RETRIB")
@NamedQuery(name="ComTTipoLivelloRetrib.findAll", query="SELECT c FROM ComTTipoLivelloRetrib c")
public class ComTTipoLivelloRetrib implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return codTipoLivello;
	}

	@Override
	public void setId(String id) {
		codTipoLivello = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_TIPO_LIVELLO")
	private String codTipoLivello;

	@Column(name="DESCR_TIPO_LIVELLO")
	private String descrTipoLivello;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComTLivelloRetribuzione
//	@OneToMany(mappedBy="comTTipoLivelloRetrib")
//	private List<ComTLivelloRetribuzione> comTLivelloRetribuziones;

	public ComTTipoLivelloRetrib() {
	}

	public String getCodTipoLivello() {
		return this.codTipoLivello;
	}

	public void setCodTipoLivello(String codTipoLivello) {
		this.codTipoLivello = codTipoLivello;
	}

	public String getDescrTipoLivello() {
		return this.descrTipoLivello;
	}

	public void setDescrTipoLivello(String descrTipoLivello) {
		this.descrTipoLivello = descrTipoLivello;
	}

	public Date getDtFine() {
		return this.dtFine;
	}

	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	public Date getDtInizio() {
		return this.dtInizio;
	}

	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

//	public List<ComTLivelloRetribuzione> getComTLivelloRetribuziones() {
//		return this.comTLivelloRetribuziones;
//	}
//
//	public void setComTLivelloRetribuziones(List<ComTLivelloRetribuzione> comTLivelloRetribuziones) {
//		this.comTLivelloRetribuziones = comTLivelloRetribuziones;
//	}
//
//	public ComTLivelloRetribuzione addComTLivelloRetribuzione(ComTLivelloRetribuzione comTLivelloRetribuzione) {
//		getComTLivelloRetribuziones().add(comTLivelloRetribuzione);
//		comTLivelloRetribuzione.setComTTipoLivelloRetrib(this);
//
//		return comTLivelloRetribuzione;
//	}
//
//	public ComTLivelloRetribuzione removeComTLivelloRetribuzione(ComTLivelloRetribuzione comTLivelloRetribuzione) {
//		getComTLivelloRetribuziones().remove(comTLivelloRetribuzione);
//		comTLivelloRetribuzione.setComTTipoLivelloRetrib(null);
//
//		return comTLivelloRetribuzione;
//	}

}
