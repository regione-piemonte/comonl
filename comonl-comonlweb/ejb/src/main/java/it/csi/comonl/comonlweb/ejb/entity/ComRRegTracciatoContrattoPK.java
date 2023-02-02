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
 * The primary key class for the COM_R_REG_TRACCIATO_CONTRATTO database table.
 * 
 */
@Embeddable
public class ComRRegTracciatoContrattoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_COM_T_TIPO_TRACCIATO", insertable=false, updatable=false)
	private String idComTTipoTracciato;

	@Column(name="ID_COM_T_TIPO_CONTRATTO", insertable=false, updatable=false)
	private long idComTTipoContratto;

	@Column(name="ID_COM_T_TIPO_COMUNICAZIONE", insertable=false, updatable=false)
	private String idComTTipoComunicazione;

	public ComRRegTracciatoContrattoPK() {
	}
	public String getIdComTTipoTracciato() {
		return this.idComTTipoTracciato;
	}
	public void setIdComTTipoTracciato(String idComTTipoTracciato) {
		this.idComTTipoTracciato = idComTTipoTracciato;
	}
	public long getIdComTTipoContratto() {
		return this.idComTTipoContratto;
	}
	public void setIdComTTipoContratto(long idComTTipoContratto) {
		this.idComTTipoContratto = idComTTipoContratto;
	}
	public String getIdComTTipoComunicazione() {
		return this.idComTTipoComunicazione;
	}
	public void setIdComTTipoComunicazione(String idComTTipoComunicazione) {
		this.idComTTipoComunicazione = idComTTipoComunicazione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComRRegTracciatoContrattoPK)) {
			return false;
		}
		ComRRegTracciatoContrattoPK castOther = (ComRRegTracciatoContrattoPK)other;
		return 
			this.idComTTipoTracciato.equals(castOther.idComTTipoTracciato)
			&& (this.idComTTipoContratto == castOther.idComTTipoContratto)
			&& this.idComTTipoComunicazione.equals(castOther.idComTTipoComunicazione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idComTTipoTracciato.hashCode();
		hash = hash * prime + ((int) (this.idComTTipoContratto ^ (this.idComTTipoContratto >>> 32)));
		hash = hash * prime + this.idComTTipoComunicazione.hashCode();
		
		return hash;
	}
}
