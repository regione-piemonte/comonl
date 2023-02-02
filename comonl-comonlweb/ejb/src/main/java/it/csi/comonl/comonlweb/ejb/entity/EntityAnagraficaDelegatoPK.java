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
import javax.persistence.Embeddable;

/**
 * The primary key class for the ANAGRAFICA_DELEGATO database table.
 * 
 */
@Embeddable
public class EntityAnagraficaDelegatoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CF_DELEGATO")
	private String cfDelegato;

	@Column(name="TIPO_ANAGRAFICA")
	private String tipoAnagrafica;

	public EntityAnagraficaDelegatoPK() {
	}
	public String getCfDelegato() {
		return this.cfDelegato;
	}
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
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
		if (!(other instanceof EntityAnagraficaDelegatoPK)) {
			return false;
		}
		EntityAnagraficaDelegatoPK castOther = (EntityAnagraficaDelegatoPK)other;
		return 
			this.cfDelegato.equals(castOther.cfDelegato)
			&& this.tipoAnagrafica.equals(castOther.tipoAnagrafica);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cfDelegato.hashCode();
		hash = hash * prime + this.tipoAnagrafica.hashCode();
		
		return hash;
	}
}
