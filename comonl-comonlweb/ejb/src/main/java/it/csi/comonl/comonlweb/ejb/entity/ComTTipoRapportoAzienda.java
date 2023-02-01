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
 * The persistent class for the COM_T_TIPO_RAPPORTO_AZIENDA database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_RAPPORTO_AZIENDA")
@NamedQuery(name="ComTTipoRapportoAzienda.findAll", query="SELECT c FROM ComTTipoRapportoAzienda c")
public class ComTTipoRapportoAzienda implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoRapportoAzienda;
	}

	@Override
	public void setId(String id) {
		idComTTipoRapportoAzienda = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_RAPPORTO_AZIENDA")
	private String idComTTipoRapportoAzienda;

	@Column(name="DS_COM_T_TIPO_RAPPORTO_AZIENDA")
	private String dsComTTipoRapportoAzienda;

	//bi-directional many-to-one association to ComRRapportoLavoratore
//	@OneToMany(mappedBy="comTTipoRapportoAzienda")
//	private List<ComRRapportoLavoratore> comRRapportoLavoratores;

	public ComTTipoRapportoAzienda() {
	}

	public String getIdComTTipoRapportoAzienda() {
		return this.idComTTipoRapportoAzienda;
	}

	public void setIdComTTipoRapportoAzienda(String idComTTipoRapportoAzienda) {
		this.idComTTipoRapportoAzienda = idComTTipoRapportoAzienda;
	}

	public String getDsComTTipoRapportoAzienda() {
		return this.dsComTTipoRapportoAzienda;
	}

	public void setDsComTTipoRapportoAzienda(String dsComTTipoRapportoAzienda) {
		this.dsComTTipoRapportoAzienda = dsComTTipoRapportoAzienda;
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
//		comRRapportoLavoratore.setComTTipoRapportoAzienda(this);
//
//		return comRRapportoLavoratore;
//	}
//
//	public ComRRapportoLavoratore removeComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().remove(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComTTipoRapportoAzienda(null);
//
//		return comRRapportoLavoratore;
//	}

}
