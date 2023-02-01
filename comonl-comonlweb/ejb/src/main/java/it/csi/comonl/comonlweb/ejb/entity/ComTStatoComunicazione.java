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
 * The persistent class for the COM_T_STATO_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_STATO_COMUNICAZIONE")
@NamedQuery(name="ComTStatoComunicazione.findAll", query="SELECT c FROM ComTStatoComunicazione c")
public class ComTStatoComunicazione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTStatoComunicazione;
	}

	@Override
	public void setId(Long id) {
		idComTStatoComunicazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_STATO_COMUNICAZIONE")
	private long idComTStatoComunicazione;

	@Column(name="DS_COM_T_STATO_COMUNICAZIONE")
	private String dsComTStatoComunicazione;

	@Column(name="T_USER")
	private String tUser;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTStatoComunicazione")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTStatoComunicazione() {
	}

	public long getIdComTStatoComunicazione() {
		return this.idComTStatoComunicazione;
	}

	public void setIdComTStatoComunicazione(long idComTStatoComunicazione) {
		this.idComTStatoComunicazione = idComTStatoComunicazione;
	}

	public String getDsComTStatoComunicazione() {
		return this.dsComTStatoComunicazione;
	}

	public void setDsComTStatoComunicazione(String dsComTStatoComunicazione) {
		this.dsComTStatoComunicazione = dsComTStatoComunicazione;
	}

	public String getTUser() {
		return this.tUser;
	}

	public void setTUser(String tUser) {
		this.tUser = tUser;
	}

//	public List<ComDComunicazione> getComDComunicaziones() {
//		return this.comDComunicaziones;
//	}
//
//	public void setComDComunicaziones(List<ComDComunicazione> comDComunicaziones) {
//		this.comDComunicaziones = comDComunicaziones;
//	}
//
//	public ComDComunicazione addComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().add(comDComunicazione);
//		comDComunicazione.setComTStatoComunicazione(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTStatoComunicazione(null);
//
//		return comDComunicazione;
//	}

}
