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
 * The persistent class for the COM_T_TIPO_ATTO_L68 database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_ATTO_L68")
@NamedQuery(name="ComTTipoAttoL68.findAll", query="SELECT c FROM ComTTipoAttoL68 c")
public class ComTTipoAttoL68 implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoAttoL68;
	}

	@Override
	public void setId(String id) {
		idComTTipoAttoL68 = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_ATTO_L68")
	private String idComTTipoAttoL68;

	@Column(name="DS_COM_T_TIPO_ATTO_L68")
	private String dsComTTipoAttoL68;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTipoAttoL68")
//	private List<ComDRapporto> comDRapportos;

	public ComTTipoAttoL68() {
	}

	public String getIdComTTipoAttoL68() {
		return this.idComTTipoAttoL68;
	}

	public void setIdComTTipoAttoL68(String idComTTipoAttoL68) {
		this.idComTTipoAttoL68 = idComTTipoAttoL68;
	}

	public String getDsComTTipoAttoL68() {
		return this.dsComTTipoAttoL68;
	}

	public void setDsComTTipoAttoL68(String dsComTTipoAttoL68) {
		this.dsComTTipoAttoL68 = dsComTTipoAttoL68;
	}

//	public List<ComDRapporto> getComDRapportos() {
//		return this.comDRapportos;
//	}
//
//	public void setComDRapportos(List<ComDRapporto> comDRapportos) {
//		this.comDRapportos = comDRapportos;
//	}
//
//	public ComDRapporto addComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().add(comDRapporto);
//		comDRapporto.setComTTipoAttoL68(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTipoAttoL68(null);
//
//		return comDRapporto;
//	}

}
