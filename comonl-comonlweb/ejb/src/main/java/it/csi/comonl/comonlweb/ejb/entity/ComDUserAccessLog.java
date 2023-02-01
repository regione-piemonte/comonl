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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;

/**
 * The persistent class for the COM_D_USER_ACCESS_LOG database table.
 * 
 */
@Entity
@Table(name = "COM_D_USER_ACCESS_LOG")
@NamedQuery(name = "ComDUserAccessLog.findAll", query = "SELECT c FROM ComDUserAccessLog c")
@SequenceGenerator(name = "DATI_USER_LOG", sequenceName = "SEQ_ID_COM_D_USER_ACCESS_LOG", allocationSize = 1)
public class ComDUserAccessLog implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDUserAccessLog;
	}

	@Override
	public void setId(Long id) {
		idComDUserAccessLog = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_D_USER_ACCESS_LOG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATI_USER_LOG")
	private Long idComDUserAccessLog;

	@Column(name = "CF_UTENTE")
	private String cfUtente;

	@Column(name = "DS_COGNOME")
	private String dsCognome;

	@Column(name = "DS_NOME")
	private String dsNome;

	@Column(name = "DS_RUOLO")
	private String dsRuolo;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_EVENTO")
	private Date dtEvento;

	@Column(name = "FLG_TROVATO_SU_AAEP")
	private String flgTrovatoSuAaep;

	public ComDUserAccessLog() {
	}

	public long getIdComDUserAccessLog() {
		return this.idComDUserAccessLog;
	}

	public void setIdComDUserAccessLog(long idComDUserAccessLog) {
		this.idComDUserAccessLog = idComDUserAccessLog;
	}

	public String getCfUtente() {
		return this.cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getDsCognome() {
		return this.dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsRuolo() {
		return this.dsRuolo;
	}

	public void setDsRuolo(String dsRuolo) {
		this.dsRuolo = dsRuolo;
	}

	public Date getDtEvento() {
		return this.dtEvento;
	}

	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	public String getFlgTrovatoSuAaep() {
		return this.flgTrovatoSuAaep;
	}

	public void setFlgTrovatoSuAaep(String flgTrovatoSuAaep) {
		this.flgTrovatoSuAaep = flgTrovatoSuAaep;
	}

}
