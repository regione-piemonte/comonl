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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_MOTIVO_PERMESSO database table.
 * 
 */
@Entity
@Table(name="COM_T_MOTIVO_PERMESSO")
@NamedQuery(name="ComTMotivoPermesso.findAll", query="SELECT c FROM ComTMotivoPermesso c")
public class ComTMotivoPermesso implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTMotivoPermesso;
	}

	@Override
	public void setId(Long id) {
		idComTMotivoPermesso = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_MOTIVO_PERMESSO")
	private long idComTMotivoPermesso;

	@Column(name="COD_MOTIVO_MIN")
	private String codMotivoMin;

	@Column(name="DS_COM_T_MOTIVO_PERMESSO")
	private String dsComTMotivoPermesso;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTMotivoPermesso")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores;

	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTMotivoPermesso")
//	private List<ComDLavoratore> comDLavoratores;

	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTMotivoPermesso")
//	private List<ComDLegaleRappr> comDLegaleRapprs;

	//bi-directional many-to-one association to ComTMotivoPermesso
	@ManyToOne
	@JoinColumn(name="ID_NEW_MOTIVO_PERMESSO")
	private ComTMotivoPermesso comTMotivoPermesso;

	//bi-directional many-to-one association to ComTMotivoPermesso
//	@OneToMany(mappedBy="comTMotivoPermesso")
//	private List<ComTMotivoPermesso> comTMotivoPermessos;

	public ComTMotivoPermesso() {
	}

	public long getIdComTMotivoPermesso() {
		return this.idComTMotivoPermesso;
	}

	public void setIdComTMotivoPermesso(long idComTMotivoPermesso) {
		this.idComTMotivoPermesso = idComTMotivoPermesso;
	}

	public String getCodMotivoMin() {
		return this.codMotivoMin;
	}

	public void setCodMotivoMin(String codMotivoMin) {
		this.codMotivoMin = codMotivoMin;
	}

	public String getDsComTMotivoPermesso() {
		return this.dsComTMotivoPermesso;
	}

	public void setDsComTMotivoPermesso(String dsComTMotivoPermesso) {
		this.dsComTMotivoPermesso = dsComTMotivoPermesso;
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
//		comDAnagraficaLavoratore.setComTMotivoPermesso(this);
//
//		return comDAnagraficaLavoratore;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().remove(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTMotivoPermesso(null);
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
//		comDLavoratore.setComTMotivoPermesso(this);
//
//		return comDLavoratore;
//	}
//
//	public ComDLavoratore removeComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().remove(comDLavoratore);
//		comDLavoratore.setComTMotivoPermesso(null);
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
//		comDLegaleRappr.setComTMotivoPermesso(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTMotivoPermesso(null);
//
//		return comDLegaleRappr;
//	}

	public ComTMotivoPermesso getComTMotivoPermesso() {
		return this.comTMotivoPermesso;
	}

	public void setComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
		this.comTMotivoPermesso = comTMotivoPermesso;
	}

//	public List<ComTMotivoPermesso> getComTMotivoPermessos() {
//		return this.comTMotivoPermessos;
//	}
//
//	public void setComTMotivoPermessos(List<ComTMotivoPermesso> comTMotivoPermessos) {
//		this.comTMotivoPermessos = comTMotivoPermessos;
//	}
//
//	public ComTMotivoPermesso addComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
//		getComTMotivoPermessos().add(comTMotivoPermesso);
//		comTMotivoPermesso.setComTMotivoPermesso(this);
//
//		return comTMotivoPermesso;
//	}
//
//	public ComTMotivoPermesso removeComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
//		getComTMotivoPermessos().remove(comTMotivoPermesso);
//		comTMotivoPermesso.setComTMotivoPermesso(null);
//
//		return comTMotivoPermesso;
//	}

}
