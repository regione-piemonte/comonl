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
 * The primary key class for the DEPOSITO_COMMAX database table.
 * 
 */
@Embeddable
public class EntityDepositoCommaxPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DEPOSITO_COMMAX")
	private long idDepositoCommax;

	@Column(name="PROG_XML")
	private long progXml;

	public EntityDepositoCommaxPK() {
	}
	public long getIdDepositoCommax() {
		return this.idDepositoCommax;
	}
	public void setIdDepositoCommax(long idDepositoCommax) {
		this.idDepositoCommax = idDepositoCommax;
	}
	public long getProgXml() {
		return this.progXml;
	}
	public void setProgXml(long progXml) {
		this.progXml = progXml;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EntityDepositoCommaxPK)) {
			return false;
		}
		EntityDepositoCommaxPK castOther = (EntityDepositoCommaxPK)other;
		return 
			(this.idDepositoCommax == castOther.idDepositoCommax)
			&& (this.progXml == castOther.progXml);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDepositoCommax ^ (this.idDepositoCommax >>> 32)));
		hash = hash * prime + ((int) (this.progXml ^ (this.progXml >>> 32)));
		
		return hash;
	}
}
