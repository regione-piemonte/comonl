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
 * The persistent class for the COM_T_TIPO_DATORE database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_DATORE")
@NamedQuery(name="ComTTipoDatore.findAll", query="SELECT c FROM ComTTipoDatore c")
public class ComTTipoDatore implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoDatore;
	}

	@Override
	public void setId(String id) {
		idComTTipoDatore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_DATORE")
	private String idComTTipoDatore;

	@Column(name="DS_COM_T_TIPO_DATORE")
	private String dsComTTipoDatore;

	//bi-directional many-to-one association to ComRComunicazioneDatore
//	@OneToMany(mappedBy="comTTipoDatore")
//	private List<ComRComunicazioneDatore> comRComunicazioneDatores;

	public ComTTipoDatore() {
	}

	public String getIdComTTipoDatore() {
		return this.idComTTipoDatore;
	}

	public void setIdComTTipoDatore(String idComTTipoDatore) {
		this.idComTTipoDatore = idComTTipoDatore;
	}

	public String getDsComTTipoDatore() {
		return this.dsComTTipoDatore;
	}

	public void setDsComTTipoDatore(String dsComTTipoDatore) {
		this.dsComTTipoDatore = dsComTTipoDatore;
	}

//	public List<ComRComunicazioneDatore> getComRComunicazioneDatores() {
//		return this.comRComunicazioneDatores;
//	}
//
//	public void setComRComunicazioneDatores(List<ComRComunicazioneDatore> comRComunicazioneDatores) {
//		this.comRComunicazioneDatores = comRComunicazioneDatores;
//	}
//
//	public ComRComunicazioneDatore addComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().add(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComTTipoDatore(this);
//
//		return comRComunicazioneDatore;
//	}
//
//	public ComRComunicazioneDatore removeComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().remove(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComTTipoDatore(null);
//
//		return comRComunicazioneDatore;
//	}

}
