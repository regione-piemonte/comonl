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
 * The persistent class for the COM_R_RAPPORTO_LAVORATORE database table.
 * 
 */
@Entity
@Table(name="COM_R_SEDE_LAVORO_LAVORATORE")
@NamedQuery(name="ComRSedeLavoroLavoratore.findAll", query="SELECT c FROM ComRSedeLavoroLavoratore c")
public class ComRSedeLavoroLavoratore implements Serializable, BaseEntity<ComRSedeLavoroLavoratorePK> {

	@Override
	public ComRSedeLavoroLavoratorePK getId() {
		return id;
	}

	@Override
	public void setId(ComRSedeLavoroLavoratorePK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRSedeLavoroLavoratorePK id;

	//bi-directional many-to-one association to ComDLavoratore
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore column: ID_COM_D_LAVORATORE (should be mapped with insert="false" update="false")
	@JoinColumn(name="ID_COM_D_LAVORATORE", insertable=false, updatable=false)
	private ComDLavoratore comDLavoratore;

	//bi-directional many-to-one association to ComDRapporto
	@ManyToOne
	@JoinColumn(name="ID_COM_D_SEDE_LAVORO", insertable=false, updatable=false)
	private ComDSedeLavoro comDSedeLavoro;

	
	public ComRSedeLavoroLavoratore() {
	}

	public ComDLavoratore getComDLavoratore() {
		return comDLavoratore;
	}

	public void setComDLavoratore(ComDLavoratore comDLavoratore) {
		this.comDLavoratore = comDLavoratore;
	}

	public ComDSedeLavoro getComDSedeLavoro() {
		return comDSedeLavoro;
	}

	public void setComDSedeLavoro(ComDSedeLavoro comDSedeLavoro) {
		this.comDSedeLavoro = comDSedeLavoro;
	}

	
}
