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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_DUPLICA_GRUPPO_LOG database table.
 * 
 */
@Entity
@Table(name="COM_D_DUPLICA_GRUPPO_LOG")
@NamedQuery(name="ComDDuplicaGruppoLog.findAll", query="SELECT c FROM ComDDuplicaGruppoLog c")
public class ComDDuplicaGruppoLog implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDDuplicaGruppoLog;
	}

	@Override
	public void setId(Long id) {
		idComDDuplicaGruppoLog = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_DUPLICA_GRUPPO_LOG")
	private long idComDDuplicaGruppoLog;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INSERT")
	private Date dtInsert;

	private String messaggio;

	//bi-directional many-to-one association to ComDComunicazione
	@ManyToOne
	@JoinColumn(name="ID_COM_D_COMUNICAZ_GRUPPO")
	private ComDComunicazione comDComunicazione;

	public ComDDuplicaGruppoLog() {
	}

	public long getIdComDDuplicaGruppoLog() {
		return this.idComDDuplicaGruppoLog;
	}

	public void setIdComDDuplicaGruppoLog(long idComDDuplicaGruppoLog) {
		this.idComDDuplicaGruppoLog = idComDDuplicaGruppoLog;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public String getMessaggio() {
		return this.messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

}
