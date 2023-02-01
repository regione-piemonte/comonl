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
 * The persistent class for the COM_T_TIPO_SOMMINISTRAZIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_SOMMINISTRAZIONE")
@NamedQuery(name="ComTTipoSomministrazione.findAll", query="SELECT c FROM ComTTipoSomministrazione c")
public class ComTTipoSomministrazione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoSomministrazione;
	}

	@Override
	public void setId(Long id) {
		idComTTipoSomministrazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_SOMMINISTRAZIONE")
	private long idComTTipoSomministrazione;

	@Column(name="DS_COM_T_TIPO_SOMMINISTRAZIONE")
	private String dsComTTipoSomministrazione;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoSomministrazione")
//	private List<ComDComunicazione> comDComunicaziones;

	//bi-directional many-to-many association to ComTTipoComunicazione
//	@ManyToMany
//	@JoinTable(
//		name="COM_R_COMUNICAZ_SOMMINISTR"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_T_TIPO_SOMMINISTRAZIONE")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_T_TIPO_COMUNICAZIONE")
//			}
//		)
//	private List<ComTTipoComunicazione> comTTipoComunicaziones;

	//bi-directional many-to-one association to ComTVariazioneSomm
//	@OneToMany(mappedBy="comTTipoSomministrazione")
//	private List<ComTVariazioneSomm> comTVariazioneSomms;

	public ComTTipoSomministrazione() {
	}

	public long getIdComTTipoSomministrazione() {
		return this.idComTTipoSomministrazione;
	}

	public void setIdComTTipoSomministrazione(long idComTTipoSomministrazione) {
		this.idComTTipoSomministrazione = idComTTipoSomministrazione;
	}

	public String getDsComTTipoSomministrazione() {
		return this.dsComTTipoSomministrazione;
	}

	public void setDsComTTipoSomministrazione(String dsComTTipoSomministrazione) {
		this.dsComTTipoSomministrazione = dsComTTipoSomministrazione;
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
//		comDComunicazione.setComTTipoSomministrazione(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoSomministrazione(null);
//
//		return comDComunicazione;
//	}

//	public List<ComTTipoComunicazione> getComTTipoComunicaziones() {
//		return this.comTTipoComunicaziones;
//	}
//
//	public void setComTTipoComunicaziones(List<ComTTipoComunicazione> comTTipoComunicaziones) {
//		this.comTTipoComunicaziones = comTTipoComunicaziones;
//	}

//	public List<ComTVariazioneSomm> getComTVariazioneSomms() {
//		return this.comTVariazioneSomms;
//	}
//
//	public void setComTVariazioneSomms(List<ComTVariazioneSomm> comTVariazioneSomms) {
//		this.comTVariazioneSomms = comTVariazioneSomms;
//	}
//
//	public ComTVariazioneSomm addComTVariazioneSomm(ComTVariazioneSomm comTVariazioneSomm) {
//		getComTVariazioneSomms().add(comTVariazioneSomm);
//		comTVariazioneSomm.setComTTipoSomministrazione(this);
//
//		return comTVariazioneSomm;
//	}
//
//	public ComTVariazioneSomm removeComTVariazioneSomm(ComTVariazioneSomm comTVariazioneSomm) {
//		getComTVariazioneSomms().remove(comTVariazioneSomm);
//		comTVariazioneSomm.setComTTipoSomministrazione(null);
//
//		return comTVariazioneSomm;
//	}

}
