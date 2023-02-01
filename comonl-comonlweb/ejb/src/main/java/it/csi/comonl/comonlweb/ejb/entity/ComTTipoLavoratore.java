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
 * The persistent class for the COM_T_TIPO_LAVORATORE database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_LAVORATORE")
@NamedQuery(name="ComTTipoLavoratore.findAll", query="SELECT c FROM ComTTipoLavoratore c")
public class ComTTipoLavoratore implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoLavoratore;
	}

	@Override
	public void setId(String id) {
		idComTTipoLavoratore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_LAVORATORE")
	private String idComTTipoLavoratore;

	@Column(name="DS_COM_T_TIPO_LAVORATORE")
	private String dsComTTipoLavoratore;

	//bi-directional many-to-one association to ComRRapportoLavoratore
//	@OneToMany(mappedBy="comTTipoLavoratore")
//	private List<ComRRapportoLavoratore> comRRapportoLavoratores;

	public ComTTipoLavoratore() {
	}

	public String getIdComTTipoLavoratore() {
		return this.idComTTipoLavoratore;
	}

	public void setIdComTTipoLavoratore(String idComTTipoLavoratore) {
		this.idComTTipoLavoratore = idComTTipoLavoratore;
	}

	public String getDsComTTipoLavoratore() {
		return this.dsComTTipoLavoratore;
	}

	public void setDsComTTipoLavoratore(String dsComTTipoLavoratore) {
		this.dsComTTipoLavoratore = dsComTTipoLavoratore;
	}

//	public List<ComRRapportoLavoratore> getComRRapportoLavoratores() {
//		return this.comRRapportoLavoratores;
//	}
//
//	public void setComRRapportoLavoratores(List<ComRRapportoLavoratore> comRRapportoLavoratores) {
//		this.comRRapportoLavoratores = comRRapportoLavoratores;
//	}
//
//	public ComRRapportoLavoratore addComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().add(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComTTipoLavoratore(this);
//
//		return comRRapportoLavoratore;
//	}
//
//	public ComRRapportoLavoratore removeComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().remove(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComTTipoLavoratore(null);
//
//		return comRRapportoLavoratore;
//	}

}
