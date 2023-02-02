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
 * The primary key class for the COM_R_TIPOL_TIROC_CAT_TIROC database table.
 * 
 */
@Embeddable
public class ComRTipolTirocCatTirocPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_T_TIPOLOGIA_TIROCINIO", insertable=false, updatable=false)
	private long idComTTipologiaTirocinio;

	@Column(name="ID_COM_T_CATEG_TIROCINANTE", insertable=false, updatable=false)
	private long idComTCategTirocinante;

	public ComRTipolTirocCatTirocPK() {
	}
	public long getIdComTTipologiaTirocinio() {
		return this.idComTTipologiaTirocinio;
	}
	public void setIdComTTipologiaTirocinio(long idComTTipologiaTirocinio) {
		this.idComTTipologiaTirocinio = idComTTipologiaTirocinio;
	}
	public long getIdComTCategTirocinante() {
		return this.idComTCategTirocinante;
	}
	public void setIdComTCategTirocinante(long idComTCategTirocinante) {
		this.idComTCategTirocinante = idComTCategTirocinante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRTipolTirocCatTirocPK)) {
			return false;
		}
		ComRTipolTirocCatTirocPK castOther = (ComRTipolTirocCatTirocPK)other;
		return 
			(this.idComTTipologiaTirocinio == castOther.idComTTipologiaTirocinio)
			&& (this.idComTCategTirocinante == castOther.idComTCategTirocinante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idComTTipologiaTirocinio ^ (this.idComTTipologiaTirocinio >>> 32)));
		hash = hash * prime + ((int) (this.idComTCategTirocinante ^ (this.idComTCategTirocinante >>> 32)));
		
		return hash;
	}
}
