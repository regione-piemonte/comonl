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
import javax.persistence.Embeddable;

/**
 * The primary key class for the PARAMETRI_TIPO_DELEGA database table.
 * 
 */
@Embeddable
public class EntityParametriTipoDelegaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String pv;

	@Column(insertable=false, updatable=false)
	private String tipo;

	public EntityParametriTipoDelegaPK() {
	}
	public String getPv() {
		return this.pv;
	}
	public void setPv(String pv) {
		this.pv = pv;
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EntityParametriTipoDelegaPK)) {
			return false;
		}
		EntityParametriTipoDelegaPK castOther = (EntityParametriTipoDelegaPK)other;
		return 
			this.pv.equals(castOther.pv)
			&& this.tipo.equals(castOther.tipo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pv.hashCode();
		hash = hash * prime + this.tipo.hashCode();
		
		return hash;
	}
}
