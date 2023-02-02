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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TITOLO_STUDIO database table.
 * 
 */
@Entity
@Table(name="COM_T_TITOLO_STUDIO")
@NamedQuery(name="ComTTitoloStudio.findAll", query="SELECT c FROM ComTTitoloStudio c")
public class ComTTitoloStudio implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTitoloStudio;
	}

	@Override
	public void setId(Long id) {
		idComTTitoloStudio = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TITOLO_STUDIO")
	private long idComTTitoloStudio;

	@Column(name="C_GRADOSTUDIO")
	private String cGradostudio;

	@Column(name="C_TIPOSTUDIO")
	private String cTipostudio;

	@Column(name="C_TITOLOSTUDIO")
	private String cTitolostudio;

	@Column(name="C_TITOLOSTUDIOMIN")
	private String cTitolostudiomin;

	private String gradostudio;

	private String tipostudio;

	private String titolostudio;

	private String titolostudiomin;

	//bi-directional many-to-one association to ComTLivelloStudio
	@ManyToOne
	@JoinColumn(name="ID_COM_T_LIVELLO_STUDIO")
	private ComTLivelloStudio comTLivelloStudio;

	public ComTTitoloStudio() {
	}

	public long getIdComTTitoloStudio() {
		return this.idComTTitoloStudio;
	}

	public void setIdComTTitoloStudio(long idComTTitoloStudio) {
		this.idComTTitoloStudio = idComTTitoloStudio;
	}

	public String getCGradostudio() {
		return this.cGradostudio;
	}

	public void setCGradostudio(String cGradostudio) {
		this.cGradostudio = cGradostudio;
	}

	public String getCTipostudio() {
		return this.cTipostudio;
	}

	public void setCTipostudio(String cTipostudio) {
		this.cTipostudio = cTipostudio;
	}

	public String getCTitolostudio() {
		return this.cTitolostudio;
	}

	public void setCTitolostudio(String cTitolostudio) {
		this.cTitolostudio = cTitolostudio;
	}

	public String getCTitolostudiomin() {
		return this.cTitolostudiomin;
	}

	public void setCTitolostudiomin(String cTitolostudiomin) {
		this.cTitolostudiomin = cTitolostudiomin;
	}

	public String getGradostudio() {
		return this.gradostudio;
	}

	public void setGradostudio(String gradostudio) {
		this.gradostudio = gradostudio;
	}

	public String getTipostudio() {
		return this.tipostudio;
	}

	public void setTipostudio(String tipostudio) {
		this.tipostudio = tipostudio;
	}

	public String getTitolostudio() {
		return this.titolostudio;
	}

	public void setTitolostudio(String titolostudio) {
		this.titolostudio = titolostudio;
	}

	public String getTitolostudiomin() {
		return this.titolostudiomin;
	}

	public void setTitolostudiomin(String titolostudiomin) {
		this.titolostudiomin = titolostudiomin;
	}

	public ComTLivelloStudio getComTLivelloStudio() {
		return this.comTLivelloStudio;
	}

	public void setComTLivelloStudio(ComTLivelloStudio comTLivelloStudio) {
		this.comTLivelloStudio = comTLivelloStudio;
	}

}
