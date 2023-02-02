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
 * The persistent class for the COM_R_T_LISTA_FZ_ERRORI database table.
 * 
 */
@Entity
@Table(name="COM_R_T_LISTA_FZ_ERRORI")
@NamedQuery(name="ComRTListaFzErrori.findAll", query="SELECT c FROM ComRTListaFzErrori c")
public class ComRTListaFzErrori implements Serializable, BaseEntity<ComRTListaFzErroriPK> {

	@Override
	public ComRTListaFzErroriPK getId() {
		return id;
	}

	@Override
	public void setId(ComRTListaFzErroriPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRTListaFzErroriPK id;

	//bi-directional many-to-one association to ComDFzErrori
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRTListaFzErrori column: ID_COM_D_FZ_ERRORI (should be mapped with insert="false" update="false")
	@JoinColumn(name="ID_COM_D_FZ_ERRORI", insertable=false, updatable=false)
	private ComDFzErrori comDFzErrori;

	public ComRTListaFzErrori() {
	}

	public ComDFzErrori getComDFzErrori() {
		return this.comDFzErrori;
	}

	public void setComDFzErrori(ComDFzErrori comDFzErrori) {
		this.comDFzErrori = comDFzErrori;
	}

}
