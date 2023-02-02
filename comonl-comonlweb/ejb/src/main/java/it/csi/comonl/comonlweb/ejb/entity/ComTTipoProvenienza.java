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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_PROVENIENZA database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_PROVENIENZA")
@NamedQuery(name="ComTTipoProvenienza.findAll", query="SELECT c FROM ComTTipoProvenienza c")
public class ComTTipoProvenienza implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoProvenienza;
	}

	@Override
	public void setId(Long id) {
		idComTTipoProvenienza = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_PROVENIENZA")
	private long idComTTipoProvenienza;

	@Column(name="COD_TIPO_PROVENIENZA_MIN")
	private String codTipoProvenienzaMin;

	@Column(name="DS_COM_T_TIPO_PROVENIENZA")
	private String dsComTTipoProvenienza;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoProvenienza")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTTipoProvenienza() {
	}

	public long getIdComTTipoProvenienza() {
		return this.idComTTipoProvenienza;
	}

	public void setIdComTTipoProvenienza(long idComTTipoProvenienza) {
		this.idComTTipoProvenienza = idComTTipoProvenienza;
	}

	public String getCodTipoProvenienzaMin() {
		return this.codTipoProvenienzaMin;
	}

	public void setCodTipoProvenienzaMin(String codTipoProvenienzaMin) {
		this.codTipoProvenienzaMin = codTipoProvenienzaMin;
	}

	public String getDsComTTipoProvenienza() {
		return this.dsComTTipoProvenienza;
	}

	public void setDsComTTipoProvenienza(String dsComTTipoProvenienza) {
		this.dsComTTipoProvenienza = dsComTTipoProvenienza;
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
//		comDComunicazione.setComTTipoProvenienza(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoProvenienza(null);
//
//		return comDComunicazione;
//	}

}
