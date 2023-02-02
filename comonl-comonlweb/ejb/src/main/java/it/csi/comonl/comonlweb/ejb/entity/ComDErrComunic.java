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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_ERR_COMUNIC database table.
 * 
 */
@Entity
@Table(name="COM_D_ERR_COMUNIC")
@NamedQuery(name="ComDErrComunic.findAll", query="SELECT c FROM ComDErrComunic c")
public class ComDErrComunic implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComunicazione;
	}

	@Override
	public void setId(Long id) {
		idComunicazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INS")
	private Date dataIns;

	@Column(name="DES_ERR")
	private String desErr;

	@Id
	@Column(name="ID_COMUNICAZIONE")
	private Long idComunicazione;

	public ComDErrComunic() {
	}

	public Date getDataIns() {
		return this.dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getDesErr() {
		return this.desErr;
	}

	public void setDesErr(String desErr) {
		this.desErr = desErr;
	}

	public Long getIdComunicazione() {
		return this.idComunicazione;
	}

	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

}
