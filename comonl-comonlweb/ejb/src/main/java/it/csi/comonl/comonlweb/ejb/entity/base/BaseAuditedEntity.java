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
package it.csi.comonl.comonlweb.ejb.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Audited entity base implementation
 * 
 * @param <K> the key type
 */
@MappedSuperclass
public abstract class BaseAuditedEntity<K> implements BaseEntity<K> {

	@Column(name = "ID_USER_INSERT")
	private String idUserInsert;

	@Column(name = "ID_USER_ULT_MOD")
	private String idUserUltMod;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INSERT")
	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD")
	private Date dtUltMod;

	public String getIdUserInsert() {
		return idUserInsert;
	}

	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	public String getIdUserUltMod() {
		return idUserUltMod;
	}

	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	public Date getDtInsert() {
		return dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtUltMod() {
		return dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

}
