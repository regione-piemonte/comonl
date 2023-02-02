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
import javax.persistence.*;

/**
 * The primary key class for the COM_R_DATORE_SEDE database table.
 * 
 */
@Embeddable
public class ComRDatoreSedePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_D_DATORE", insertable=false, updatable=false)
	private long idComDDatore;

	@Column(name="ID_COM_D_SEDE_LAVORO", insertable=false, updatable=false)
	private long idComDSedeLavoro;

	public ComRDatoreSedePK() {
	}
	public long getIdComDDatore() {
		return this.idComDDatore;
	}
	public void setIdComDDatore(long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}
	public long getIdComDSedeLavoro() {
		return this.idComDSedeLavoro;
	}
	public void setIdComDSedeLavoro(long idComDSedeLavoro) {
		this.idComDSedeLavoro = idComDSedeLavoro;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRDatoreSedePK)) {
			return false;
		}
		ComRDatoreSedePK castOther = (ComRDatoreSedePK)other;
		return 
			(this.idComDDatore == castOther.idComDDatore)
			&& (this.idComDSedeLavoro == castOther.idComDSedeLavoro);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idComDDatore ^ (this.idComDDatore >>> 32)));
		hash = hash * prime + ((int) (this.idComDSedeLavoro ^ (this.idComDSedeLavoro >>> 32)));
		
		return hash;
	}
}
