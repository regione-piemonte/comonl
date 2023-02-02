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
 * The primary key class for the COM_R_DEP_COMMAX_COMUNIC database table.
 * 
 */
@Embeddable
public class ComRDepCommaxComunicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROG_XML")
	private long progXml;

	@Column(name="ID_DEPOSITO_COMMAX")
	private long idDepositoCommax;

	@Column(name="ID_COM_D_UPL_DOCUMENTI", insertable=false, updatable=false)
	private long idComDUplDocumenti;

	@Column(name="ID_COM_D_COMUNICAZIONE", insertable=false, updatable=false)
	private long idComDComunicazione;

	public ComRDepCommaxComunicPK() {
	}
	public long getProgXml() {
		return this.progXml;
	}
	public void setProgXml(long progXml) {
		this.progXml = progXml;
	}
	public long getIdDepositoCommax() {
		return this.idDepositoCommax;
	}
	public void setIdDepositoCommax(long idDepositoCommax) {
		this.idDepositoCommax = idDepositoCommax;
	}
	public long getIdComDUplDocumenti() {
		return this.idComDUplDocumenti;
	}
	public void setIdComDUplDocumenti(long idComDUplDocumenti) {
		this.idComDUplDocumenti = idComDUplDocumenti;
	}
	public long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}
	public void setIdComDComunicazione(long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRDepCommaxComunicPK)) {
			return false;
		}
		ComRDepCommaxComunicPK castOther = (ComRDepCommaxComunicPK)other;
		return 
			(this.progXml == castOther.progXml)
			&& (this.idDepositoCommax == castOther.idDepositoCommax)
			&& (this.idComDUplDocumenti == castOther.idComDUplDocumenti)
			&& (this.idComDComunicazione == castOther.idComDComunicazione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.progXml ^ (this.progXml >>> 32)));
		hash = hash * prime + ((int) (this.idDepositoCommax ^ (this.idDepositoCommax >>> 32)));
		hash = hash * prime + ((int) (this.idComDUplDocumenti ^ (this.idComDUplDocumenti >>> 32)));
		hash = hash * prime + ((int) (this.idComDComunicazione ^ (this.idComDComunicazione >>> 32)));
		
		return hash;
	}
}
