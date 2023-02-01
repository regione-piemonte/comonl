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
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_MODELLI_PFI database table.
 * 
 */
@Entity
@Table(name="COM_R_MODELLI_PFI")
@NamedQuery(name="ComRModelliPfi.findAll", query="SELECT c FROM ComRModelliPfi c")
public class ComRModelliPfi implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComRModelliPfi;
	}

	@Override
	public void setId(Long id) {
		this.idComRModelliPfi = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_R_MODELLI_PFI")
	private long idComRModelliPfi;

	@Column(name="ID_COM_D_CAPACITA_FORM_MOD_A")
	private BigDecimal idComDCapacitaFormModA;

	@Column(name="ID_COM_D_CAPACITA_FORM_MOD_B")
	private BigDecimal idComDCapacitaFormModB;

	public ComRModelliPfi() {
	}

	public long getIdComRModelliPfi() {
		return this.idComRModelliPfi;
	}

	public void setIdComRModelliPfi(long idComRModelliPfi) {
		this.idComRModelliPfi = idComRModelliPfi;
	}

	public BigDecimal getIdComDCapacitaFormModA() {
		return this.idComDCapacitaFormModA;
	}

	public void setIdComDCapacitaFormModA(BigDecimal idComDCapacitaFormModA) {
		this.idComDCapacitaFormModA = idComDCapacitaFormModA;
	}

	public BigDecimal getIdComDCapacitaFormModB() {
		return this.idComDCapacitaFormModB;
	}

	public void setIdComDCapacitaFormModB(BigDecimal idComDCapacitaFormModB) {
		this.idComDCapacitaFormModB = idComDCapacitaFormModB;
	}

}
