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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_COMUNICAZIONE_DATORE database table.
 * 
 */
@Entity
@Table(name="COM_R_COMUNICAZIONE_DATORE")
@NamedQuery(name="ComRComunicazioneDatore.findAll", query="SELECT c FROM ComRComunicazioneDatore c")
public class ComRComunicazioneDatore implements Serializable, BaseEntity<ComRComunicazioneDatorePK> {

	@Override
	public ComRComunicazioneDatorePK getId() {
		return id;
	}

	@Override
	public void setId(ComRComunicazioneDatorePK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRComunicazioneDatorePK id;

	//bi-directional many-to-one association to ComDComunicazione
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore column: 
	// ID_COM_D_COMUNICAZIONE (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_D_COMUNICAZIONE")
	@JoinColumn(name="ID_COM_D_COMUNICAZIONE", insertable=false, updatable=false)
	private ComDComunicazione comDComunicazione;

	//bi-directional many-to-one association to ComDDatore
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore column: 
	// ID_COM_D_DATORE (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_D_DATORE")
	@JoinColumn(name="ID_COM_D_DATORE", insertable=false, updatable=false)
	private ComDDatore comDDatore;

	//bi-directional many-to-one association to ComTTipoDatore
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_DATORE")
	private ComTTipoDatore comTTipoDatore;

	public ComRComunicazioneDatore() {
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

	public ComDDatore getComDDatore() {
		return this.comDDatore;
	}

	public void setComDDatore(ComDDatore comDDatore) {
		this.comDDatore = comDDatore;
	}

	public ComTTipoDatore getComTTipoDatore() {
		return this.comTTipoDatore;
	}

	public void setComTTipoDatore(ComTTipoDatore comTTipoDatore) {
		this.comTTipoDatore = comTTipoDatore;
	}

}
