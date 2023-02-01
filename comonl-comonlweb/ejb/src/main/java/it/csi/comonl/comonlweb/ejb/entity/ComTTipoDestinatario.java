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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_DESTINATARIO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_DESTINATARIO")
@NamedQuery(name="ComTTipoDestinatario.findAll", query="SELECT c FROM ComTTipoDestinatario c")
public class ComTTipoDestinatario implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoDestinatario;
	}

	@Override
	public void setId(String id) {
		idComTTipoDestinatario = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_DESTINATARIO")
	private String idComTTipoDestinatario;

	@Column(name="DS_COM_T_TIPO_DESTINATARIO")
	private String dsComTTipoDestinatario;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-many association to ComDComunicazione
//	@ManyToMany
//	@JoinTable(
//		name="COM_D_DESTINATARI_IGNORARE"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_T_TIPO_DESTINATARIO")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_D_COMUNICAZIONE")
//			}
//		)
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTTipoDestinatario() {
	}

	public String getIdComTTipoDestinatario() {
		return this.idComTTipoDestinatario;
	}

	public void setIdComTTipoDestinatario(String idComTTipoDestinatario) {
		this.idComTTipoDestinatario = idComTTipoDestinatario;
	}

	public String getDsComTTipoDestinatario() {
		return this.dsComTTipoDestinatario;
	}

	public void setDsComTTipoDestinatario(String dsComTTipoDestinatario) {
		this.dsComTTipoDestinatario = dsComTTipoDestinatario;
	}

	public Date getDtFine() {
		return this.dtFine;
	}

	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	public Date getDtInizio() {
		return this.dtInizio;
	}

	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

//	public List<ComDComunicazione> getComDComunicaziones() {
//		return this.comDComunicaziones;
//	}
//
//	public void setComDComunicaziones(List<ComDComunicazione> comDComunicaziones) {
//		this.comDComunicaziones = comDComunicaziones;
//	}

}
