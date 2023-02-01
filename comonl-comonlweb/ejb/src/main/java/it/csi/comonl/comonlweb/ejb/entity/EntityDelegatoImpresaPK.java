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
 * The primary key class for the DELEGATO_IMPRESA database table.
 * 
 */
@Embeddable
public class EntityDelegatoImpresaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CF_DELEGATO", insertable=false, updatable=false)
	private String cfDelegato;

	@Column(name="CF_IMPRESA")
	private String cfImpresa;

	@Column(name="TIPO_ANAGRAFICA", insertable=false, updatable=false)
	private String tipoAnagrafica;

	public EntityDelegatoImpresaPK() {
	}
	public String getCfDelegato() {
		return this.cfDelegato;
	}
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}
	public String getCfImpresa() {
		return this.cfImpresa;
	}
	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}
	public String getTipoAnagrafica() {
		return this.tipoAnagrafica;
	}
	public void setTipoAnagrafica(String tipoAnagrafica) {
		this.tipoAnagrafica = tipoAnagrafica;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EntityDelegatoImpresaPK)) {
			return false;
		}
		EntityDelegatoImpresaPK castOther = (EntityDelegatoImpresaPK)other;
		return 
			this.cfDelegato.equals(castOther.cfDelegato)
			&& this.cfImpresa.equals(castOther.cfImpresa)
			&& this.tipoAnagrafica.equals(castOther.tipoAnagrafica);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cfDelegato.hashCode();
		hash = hash * prime + this.cfImpresa.hashCode();
		hash = hash * prime + this.tipoAnagrafica.hashCode();
		
		return hash;
	}
}
