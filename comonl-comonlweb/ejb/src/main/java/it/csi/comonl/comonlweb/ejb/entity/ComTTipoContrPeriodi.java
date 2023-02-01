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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_CONTR_PERIODI database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_CONTR_PERIODI")
@NamedQuery(name="ComTTipoContrPeriodi.findAll", query="SELECT c FROM ComTTipoContrPeriodi c")
public class ComTTipoContrPeriodi implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoContrPeriodi;
	}

	@Override
	public void setId(Long id) {
		idComTTipoContrPeriodi = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_CONTR_PERIODI")
	private long idComTTipoContrPeriodi;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NON_VALID_A")
	private Date dtNonValidA;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NON_VALID_DA")
	private Date dtNonValidDa;

	//bi-directional many-to-one association to ComTTipoContratti
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_CONTRATTO")
	private ComTTipoContratti comTTipoContratti;

	public ComTTipoContrPeriodi() {
	}

	public long getIdComTTipoContrPeriodi() {
		return this.idComTTipoContrPeriodi;
	}

	public void setIdComTTipoContrPeriodi(long idComTTipoContrPeriodi) {
		this.idComTTipoContrPeriodi = idComTTipoContrPeriodi;
	}

	public Date getDtNonValidA() {
		return this.dtNonValidA;
	}

	public void setDtNonValidA(Date dtNonValidA) {
		this.dtNonValidA = dtNonValidA;
	}

	public Date getDtNonValidDa() {
		return this.dtNonValidDa;
	}

	public void setDtNonValidDa(Date dtNonValidDa) {
		this.dtNonValidDa = dtNonValidDa;
	}

	public ComTTipoContratti getComTTipoContratti() {
		return this.comTTipoContratti;
	}

	public void setComTTipoContratti(ComTTipoContratti comTTipoContratti) {
		this.comTTipoContratti = comTTipoContratti;
	}

}
