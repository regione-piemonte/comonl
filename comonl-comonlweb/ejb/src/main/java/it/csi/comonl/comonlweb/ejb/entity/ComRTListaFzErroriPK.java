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
 * The primary key class for the COM_R_T_LISTA_FZ_ERRORI database table.
 * 
 */
@Embeddable
public class ComRTListaFzErroriPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_T_LISTA_ERRORI_FZ", insertable=false, updatable=false)
	private long idComTListaErroriFz;

	@Column(name="ID_COM_D_FZ_ERRORI", insertable=false, updatable=false)
	private long idComDFzErrori;

	public ComRTListaFzErroriPK() {
	}
	public long getIdComTListaErroriFz() {
		return this.idComTListaErroriFz;
	}
	public void setIdComTListaErroriFz(long idComTListaErroriFz) {
		this.idComTListaErroriFz = idComTListaErroriFz;
	}
	public long getIdComDFzErrori() {
		return this.idComDFzErrori;
	}
	public void setIdComDFzErrori(long idComDFzErrori) {
		this.idComDFzErrori = idComDFzErrori;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRTListaFzErroriPK)) {
			return false;
		}
		ComRTListaFzErroriPK castOther = (ComRTListaFzErroriPK)other;
		return 
			(this.idComTListaErroriFz == castOther.idComTListaErroriFz)
			&& (this.idComDFzErrori == castOther.idComDFzErrori);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idComTListaErroriFz ^ (this.idComTListaErroriFz >>> 32)));
		hash = hash * prime + ((int) (this.idComDFzErrori ^ (this.idComDFzErrori >>> 32)));
		
		return hash;
	}
}
