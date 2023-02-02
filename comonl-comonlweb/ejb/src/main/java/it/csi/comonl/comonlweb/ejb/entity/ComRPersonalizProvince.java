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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_PERSONALIZ_PROVINCE database table.
 * 
 */
@Entity
@Table(name="COM_R_PERSONALIZ_PROVINCE")
@NamedQuery(name="ComRPersonalizProvince.findAll", query="SELECT c FROM ComRPersonalizProvince c")
public class ComRPersonalizProvince implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return pv;
	}

	@Override
	public void setId(String id) {
		this.pv = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	private String pv;

	//bi-directional many-to-one association to ComTProvincia
	@ManyToOne
	@JoinColumn(name="ID_COM_T_PROVINCIA")
	private ComTProvincia comTProvincia;

	//bi-directional one-to-one association to Personalizzazione
	@OneToOne(mappedBy="comRPersonalizProvince")
	private EntityPersonalizzazione personalizzazione;

	public ComRPersonalizProvince() {
	}

	public String getPv() {
		return this.pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public ComTProvincia getComTProvincia() {
		return this.comTProvincia;
	}

	public void setComTProvincia(ComTProvincia comTProvincia) {
		this.comTProvincia = comTProvincia;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

}
