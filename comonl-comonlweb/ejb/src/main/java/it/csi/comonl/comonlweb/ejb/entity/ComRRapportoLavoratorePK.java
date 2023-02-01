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
 * The primary key class for the COM_R_RAPPORTO_LAVORATORE database table.
 * 
 */
@Embeddable
public class ComRRapportoLavoratorePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_D_LAVORATORE", insertable=false, updatable=false)
	private long idComDLavoratore;

	@Column(name="ID_COM_D_RAPPORTO", insertable=false, updatable=false)
	private long idComDRapporto;

	public ComRRapportoLavoratorePK() {
	}
	public long getIdComDLavoratore() {
		return this.idComDLavoratore;
	}
	public void setIdComDLavoratore(long idComDLavoratore) {
		this.idComDLavoratore = idComDLavoratore;
	}
	public long getIdComDRapporto() {
		return this.idComDRapporto;
	}
	public void setIdComDRapporto(long idComDRapporto) {
		this.idComDRapporto = idComDRapporto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRRapportoLavoratorePK)) {
			return false;
		}
		ComRRapportoLavoratorePK castOther = (ComRRapportoLavoratorePK)other;
		return 
			(this.idComDLavoratore == castOther.idComDLavoratore)
			&& (this.idComDRapporto == castOther.idComDRapporto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idComDLavoratore ^ (this.idComDLavoratore >>> 32)));
		hash = hash * prime + ((int) (this.idComDRapporto ^ (this.idComDRapporto >>> 32)));
		
		return hash;
	}
}
