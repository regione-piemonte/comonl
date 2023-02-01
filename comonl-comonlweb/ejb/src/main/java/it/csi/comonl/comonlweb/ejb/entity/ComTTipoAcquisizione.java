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
 * The persistent class for the COM_T_TIPO_ACQUISIZIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_ACQUISIZIONE")
@NamedQuery(name="ComTTipoAcquisizione.findAll", query="SELECT c FROM ComTTipoAcquisizione c")
public class ComTTipoAcquisizione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoAcquisizione;
	}

	@Override
	public void setId(Long id) {
		idComTTipoAcquisizione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_ACQUISIZIONE")
	private long idComTTipoAcquisizione;

	@Column(name="DS_COM_T_TIPO_ACQUISIZIONE")
	private String dsComTTipoAcquisizione;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoAcquisizione")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTTipoAcquisizione() {
	}

	public long getIdComTTipoAcquisizione() {
		return this.idComTTipoAcquisizione;
	}

	public void setIdComTTipoAcquisizione(long idComTTipoAcquisizione) {
		this.idComTTipoAcquisizione = idComTTipoAcquisizione;
	}

	public String getDsComTTipoAcquisizione() {
		return this.dsComTTipoAcquisizione;
	}

	public void setDsComTTipoAcquisizione(String dsComTTipoAcquisizione) {
		this.dsComTTipoAcquisizione = dsComTTipoAcquisizione;
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
//		comDComunicazione.setComTTipoAcquisizione(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoAcquisizione(null);
//
//		return comDComunicazione;
//	}

}
