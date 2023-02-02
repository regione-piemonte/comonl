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
 * The primary key class for the COM_R_COMUNICAZIONE_DATORE database table.
 * 
 */
@Embeddable
public class ComRComunicazioneDatorePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_D_COMUNICAZIONE", insertable=false, updatable=false)
	private long idComDComunicazione;

	@Column(name="ID_COM_D_DATORE", insertable=false, updatable=false)
	private long idComDDatore;

	public ComRComunicazioneDatorePK() {
	}
	public long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}
	public void setIdComDComunicazione(long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}
	public long getIdComDDatore() {
		return this.idComDDatore;
	}
	public void setIdComDDatore(long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRComunicazioneDatorePK)) {
			return false;
		}
		ComRComunicazioneDatorePK castOther = (ComRComunicazioneDatorePK)other;
		return 
			(this.idComDComunicazione == castOther.idComDComunicazione)
			&& (this.idComDDatore == castOther.idComDDatore);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idComDComunicazione ^ (this.idComDComunicazione >>> 32)));
		hash = hash * prime + ((int) (this.idComDDatore ^ (this.idComDDatore >>> 32)));
		
		return hash;
	}
}
