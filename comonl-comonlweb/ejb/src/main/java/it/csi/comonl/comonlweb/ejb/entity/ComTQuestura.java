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
 * The persistent class for the COM_T_QUESTURA database table.
 * 
 */
@Entity
@Table(name="COM_T_QUESTURA")
@NamedQuery(name="ComTQuestura.findAll", query="SELECT c FROM ComTQuestura c")
public class ComTQuestura implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTQuestura;
	}

	@Override
	public void setId(Long id) {
		idComTQuestura = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_QUESTURA")
	private long idComTQuestura;

	@Column(name="COD_QUESTURA_MIN")
	private String codQuesturaMin;

	@Column(name="DS_QUESTURA")
	private String dsQuestura;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTQuestura")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores;

	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTQuestura")
//	private List<ComDLavoratore> comDLavoratores;

	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTQuestura")
//	private List<ComDLegaleRappr> comDLegaleRapprs;

	public ComTQuestura() {
	}

	public long getIdComTQuestura() {
		return this.idComTQuestura;
	}

	public void setIdComTQuestura(long idComTQuestura) {
		this.idComTQuestura = idComTQuestura;
	}

	public String getCodQuesturaMin() {
		return this.codQuesturaMin;
	}

	public void setCodQuesturaMin(String codQuesturaMin) {
		this.codQuesturaMin = codQuesturaMin;
	}

	public String getDsQuestura() {
		return this.dsQuestura;
	}

	public void setDsQuestura(String dsQuestura) {
		this.dsQuestura = dsQuestura;
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
//		comDAnagraficaLavoratore.setComTQuestura(this);
//
//		return comDAnagraficaLavoratore;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().remove(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTQuestura(null);
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
//		comDLavoratore.setComTQuestura(this);
//
//		return comDLavoratore;
//	}
//
//	public ComDLavoratore removeComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().remove(comDLavoratore);
//		comDLavoratore.setComTQuestura(null);
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
//		comDLegaleRappr.setComTQuestura(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTQuestura(null);
//
//		return comDLegaleRappr;
//	}

}
