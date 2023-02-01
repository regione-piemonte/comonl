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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_COMMAX_AUTH database table.
 * 
 */
@Entity
@Table(name="COM_D_COMMAX_AUTH")
@NamedQuery(name="ComDCommaxAuth.findAll", query="SELECT c FROM ComDCommaxAuth c")
public class ComDCommaxAuth implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDCommaxAuth;
	}

	@Override
	public void setId(Long id) {
		idComDCommaxAuth = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_COMMAX_AUTH")
	private long idComDCommaxAuth;

	@Column(name="CF_AZIENDA")
	private String cfAzienda;

	@Column(name="CF_OPERATORE")
	private String cfOperatore;

	@Column(name="DS_COGNOME")
	private String dsCognome;

	@Column(name="DS_EMAIL")
	private String dsEmail;

	@Column(name="DS_NOME")
	private String dsNome;

	@Column(name="DS_RUOLO_OPERATORE")
	private String dsRuoloOperatore;

	@Column(name="DS_TOKEN_AUTH")
	private String dsTokenAuth;

	@Column(name="DT_CREAZIONE")
	private String dtCreazione;

	@Column(name="TIPO_RUOLO")
	private String tipoRuolo;

	public ComDCommaxAuth() {
	}

	public long getIdComDCommaxAuth() {
		return this.idComDCommaxAuth;
	}

	public void setIdComDCommaxAuth(long idComDCommaxAuth) {
		this.idComDCommaxAuth = idComDCommaxAuth;
	}

	public String getCfAzienda() {
		return this.cfAzienda;
	}

	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	public String getCfOperatore() {
		return this.cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getDsCognome() {
		return this.dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getDsEmail() {
		return this.dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsRuoloOperatore() {
		return this.dsRuoloOperatore;
	}

	public void setDsRuoloOperatore(String dsRuoloOperatore) {
		this.dsRuoloOperatore = dsRuoloOperatore;
	}

	public String getDsTokenAuth() {
		return this.dsTokenAuth;
	}

	public void setDsTokenAuth(String dsTokenAuth) {
		this.dsTokenAuth = dsTokenAuth;
	}

	public String getDtCreazione() {
		return this.dtCreazione;
	}

	public void setDtCreazione(String dtCreazione) {
		this.dtCreazione = dtCreazione;
	}

	public String getTipoRuolo() {
		return this.tipoRuolo;
	}

	public void setTipoRuolo(String tipoRuolo) {
		this.tipoRuolo = tipoRuolo;
	}

}
