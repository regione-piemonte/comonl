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
@Table(name="COM_R_RAPPORTO_LAVORATORE")
@NamedQuery(name="ComRRapportoLavoratore.findAll", query="SELECT c FROM ComRRapportoLavoratore c")
public class ComRRapportoLavoratore implements Serializable, BaseEntity<ComRRapportoLavoratorePK> {

	@Override
	public ComRRapportoLavoratorePK getId() {
		return id;
	}

	@Override
	public void setId(ComRRapportoLavoratorePK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ComRRapportoLavoratorePK id;

	//bi-directional many-to-one association to ComDLavoratore
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore column: ID_COM_D_LAVORATORE (should be mapped with insert="false" update="false")
	@JoinColumn(name="ID_COM_D_LAVORATORE", insertable=false, updatable=false)
	private ComDLavoratore comDLavoratore;

	//bi-directional many-to-one association to ComDRapporto
	@ManyToOne
	@JoinColumn(name="ID_COM_D_RAPPORTO", insertable=false, updatable=false)
	private ComDRapporto comDRapporto;

	//bi-directional many-to-one association to ComTTipoLavoratore
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_LAVORATORE")
	private ComTTipoLavoratore comTTipoLavoratore;

	//bi-directional many-to-one association to ComTTipoRapportoAzienda
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_RAPPORTO_AZIENDA")
	private ComTTipoRapportoAzienda comTTipoRapportoAzienda;

	public ComRRapportoLavoratore() {
	}

	public ComDLavoratore getComDLavoratore() {
		return this.comDLavoratore;
	}

	public void setComDLavoratore(ComDLavoratore comDLavoratore) {
		this.comDLavoratore = comDLavoratore;
	}

	public ComDRapporto getComDRapporto() {
		return this.comDRapporto;
	}

	public void setComDRapporto(ComDRapporto comDRapporto) {
		this.comDRapporto = comDRapporto;
	}

	public ComTTipoLavoratore getComTTipoLavoratore() {
		return this.comTTipoLavoratore;
	}

	public void setComTTipoLavoratore(ComTTipoLavoratore comTTipoLavoratore) {
		this.comTTipoLavoratore = comTTipoLavoratore;
	}

	public ComTTipoRapportoAzienda getComTTipoRapportoAzienda() {
		return this.comTTipoRapportoAzienda;
	}

	public void setComTTipoRapportoAzienda(ComTTipoRapportoAzienda comTTipoRapportoAzienda) {
		this.comTTipoRapportoAzienda = comTTipoRapportoAzienda;
	}

}
