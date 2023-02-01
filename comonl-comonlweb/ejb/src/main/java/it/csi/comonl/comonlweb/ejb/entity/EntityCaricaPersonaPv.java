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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the CARICA_PERSONA_PV database table.
 * 
 */
@Entity
@Table(name="CARICA_PERSONA_PV")
@NamedQuery(name="CaricaPersonaPv.findAll", query="SELECT c FROM EntityCaricaPersonaPv c")
public class EntityCaricaPersonaPv implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return carica;
	}

	@Override
	public void setId(String id) {
		carica = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private String carica;

	private String descrizione;

	//bi-directional many-to-one association to Personalizzazione
	@ManyToOne
	@JoinColumn(name="PV")
	private EntityPersonalizzazione personalizzazione;

	public EntityCaricaPersonaPv() {
	}

	public String getCarica() {
		return this.carica;
	}

	public void setCarica(String carica) {
		this.carica = carica;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

}
