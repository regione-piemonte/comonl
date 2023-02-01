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
 * The persistent class for the COM_T_TIPOLOGIA_RAPPORTO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPOLOGIA_RAPPORTO")
@NamedQuery(name="ComTTipologiaRapporto.findAll", query="SELECT c FROM ComTTipologiaRapporto c")
public class ComTTipologiaRapporto implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipologiaRapporto;
	}

	@Override
	public void setId(String id) {
		idComTTipologiaRapporto = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPOLOGIA_RAPPORTO")
	private String idComTTipologiaRapporto;

	@Column(name="DS_COM_T_TIPOLOGIA_RAPPORTO")
	private String dsComTTipologiaRapporto;

	//bi-directional many-to-one association to ComTTipoContratti
//	@OneToMany(mappedBy="comTTipologiaRapporto")
//	private List<ComTTipoContratti> comTTipoContrattis;

	public ComTTipologiaRapporto() {
	}

	public String getIdComTTipologiaRapporto() {
		return this.idComTTipologiaRapporto;
	}

	public void setIdComTTipologiaRapporto(String idComTTipologiaRapporto) {
		this.idComTTipologiaRapporto = idComTTipologiaRapporto;
	}

	public String getDsComTTipologiaRapporto() {
		return this.dsComTTipologiaRapporto;
	}

	public void setDsComTTipologiaRapporto(String dsComTTipologiaRapporto) {
		this.dsComTTipologiaRapporto = dsComTTipologiaRapporto;
	}

//	public List<ComTTipoContratti> getComTTipoContrattis() {
//		return this.comTTipoContrattis;
//	}
//
//	public void setComTTipoContrattis(List<ComTTipoContratti> comTTipoContrattis) {
//		this.comTTipoContrattis = comTTipoContrattis;
//	}
//
//	public ComTTipoContratti addComTTipoContratti(ComTTipoContratti comTTipoContratti) {
//		getComTTipoContrattis().add(comTTipoContratti);
//		comTTipoContratti.setComTTipologiaRapporto(this);
//
//		return comTTipoContratti;
//	}
//
//	public ComTTipoContratti removeComTTipoContratti(ComTTipoContratti comTTipoContratti) {
//		getComTTipoContrattis().remove(comTTipoContratti);
//		comTTipoContratti.setComTTipologiaRapporto(null);
//
//		return comTTipoContratti;
//	}

}
