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
 * The persistent class for the COM_T_STATUS_STRANIERO database table.
 * 
 */
@Entity
@Table(name="COM_T_STATUS_STRANIERO")
@NamedQuery(name="ComTStatusStraniero.findAll", query="SELECT c FROM ComTStatusStraniero c")
public class ComTStatusStraniero implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTStatusStraniero;
	}

	@Override
	public void setId(Long id) {
		idComTStatusStraniero = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_STATUS_STRANIERO")
	private Long idComTStatusStraniero;

	@Column(name="COD_STATUS_MIN")
	private String codStatusMin;

	@Column(name="DS_COM_T_STATUS_STRANIERO")
	private String dsComTStatusStraniero;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTStatusStraniero")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTStatusStraniero")
//	private List<ComDLavoratore> comDLavoratores;
//
//	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTStatusStraniero")
//	private List<ComDLegaleRappr> comDLegaleRapprs;

	public ComTStatusStraniero() {
	}

	public Long getIdComTStatusStraniero() {
		return this.idComTStatusStraniero;
	}

	public void setIdComTStatusStraniero(Long idComTStatusStraniero) {
		this.idComTStatusStraniero = idComTStatusStraniero;
	}

	public String getCodStatusMin() {
		return this.codStatusMin;
	}

	public void setCodStatusMin(String codStatusMin) {
		this.codStatusMin = codStatusMin;
	}

	public String getDsComTStatusStraniero() {
		return this.dsComTStatusStraniero;
	}

	public void setDsComTStatusStraniero(String dsComTStatusStraniero) {
		this.dsComTStatusStraniero = dsComTStatusStraniero;
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

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores() {
//		return this.comDAnagraficaLavoratores;
//	}
//
//	public void setComDAnagraficaLavoratores(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores) {
//		this.comDAnagraficaLavoratores = comDAnagraficaLavoratores;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().add(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTStatusStraniero(this);
//
//		return comDAnagraficaLavoratore;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().remove(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTStatusStraniero(null);
//
//		return comDAnagraficaLavoratore;
//	}

//	public List<ComDLavoratore> getComDLavoratores() {
//		return this.comDLavoratores;
//	}
//
//	public void setComDLavoratores(List<ComDLavoratore> comDLavoratores) {
//		this.comDLavoratores = comDLavoratores;
//	}
//
//	public ComDLavoratore addComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().add(comDLavoratore);
//		comDLavoratore.setComTStatusStraniero(this);
//
//		return comDLavoratore;
//	}
//
//	public ComDLavoratore removeComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().remove(comDLavoratore);
//		comDLavoratore.setComTStatusStraniero(null);
//
//		return comDLavoratore;
//	}

//	public List<ComDLegaleRappr> getComDLegaleRapprs() {
//		return this.comDLegaleRapprs;
//	}
//
//	public void setComDLegaleRapprs(List<ComDLegaleRappr> comDLegaleRapprs) {
//		this.comDLegaleRapprs = comDLegaleRapprs;
//	}
//
//	public ComDLegaleRappr addComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().add(comDLegaleRappr);
//		comDLegaleRappr.setComTStatusStraniero(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTStatusStraniero(null);
//
//		return comDLegaleRappr;
//	}

}
