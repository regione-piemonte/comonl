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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_GRADO_CONTRATTUALE database table.
 * 
 */
@Entity
@Table(name="COM_T_GRADO_CONTRATTUALE")
@NamedQuery(name="ComTGradoContrattuale.findAll", query="SELECT c FROM ComTGradoContrattuale c")
public class ComTGradoContrattuale implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTGradoContrattuale;
	}

	@Override
	public void setId(Long id) {
		idComTGradoContrattuale = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_GRADO_CONTRATTUALE")
	private Long idComTGradoContrattuale;

	@Column(name="COD_GRADO_CONTRATTUALE")
	private String codGradoContrattuale;

	@Column(name="DS_COM_T_GRADO_CONTRATTUALE")
	private String dsComTGradoContrattuale;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to ComDTutore
//	@OneToMany(mappedBy="comTGradoContrattuale")
//	private List<ComDTutore> comDTutores;

	public ComTGradoContrattuale() {
	}

	public Long getIdComTGradoContrattuale() {
		return this.idComTGradoContrattuale;
	}

	public void setIdComTGradoContrattuale(Long idComTGradoContrattuale) {
		this.idComTGradoContrattuale = idComTGradoContrattuale;
	}

	public String getCodGradoContrattuale() {
		return this.codGradoContrattuale;
	}

	public void setCodGradoContrattuale(String codGradoContrattuale) {
		this.codGradoContrattuale = codGradoContrattuale;
	}

	public String getDsComTGradoContrattuale() {
		return this.dsComTGradoContrattuale;
	}

	public void setDsComTGradoContrattuale(String dsComTGradoContrattuale) {
		this.dsComTGradoContrattuale = dsComTGradoContrattuale;
	}

	public Date getDtFine() {
		return this.dtFine;
	}

	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	public Date getDtInizio() {
		return this.dtInizio;
	}

	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

	public Date getDtTmst() {
		return this.dtTmst;
	}

	public void setDtTmst(Date dtTmst) {
		this.dtTmst = dtTmst;
	}

//	public List<ComDTutore> getComDTutores() {
//		return this.comDTutores;
//	}
//
//	public void setComDTutores(List<ComDTutore> comDTutores) {
//		this.comDTutores = comDTutores;
//	}
//
//	public ComDTutore addComDTutore(ComDTutore comDTutore) {
//		getComDTutores().add(comDTutore);
//		comDTutore.setComTGradoContrattuale(this);
//
//		return comDTutore;
//	}
//
//	public ComDTutore removeComDTutore(ComDTutore comDTutore) {
//		getComDTutores().remove(comDTutore);
//		comDTutore.setComTGradoContrattuale(null);
//
//		return comDTutore;
//	}

}
