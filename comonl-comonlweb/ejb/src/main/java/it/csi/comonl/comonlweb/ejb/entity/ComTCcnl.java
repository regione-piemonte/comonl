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
import java.math.BigDecimal;
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
 * The persistent class for the COM_T_CCNL database table.
 * 
 */
@Entity
@Table(name="COM_T_CCNL")
@NamedQuery(name="ComTCcnl.findAll", query="SELECT c FROM ComTCcnl c")
public class ComTCcnl implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCcnl;
	}

	@Override
	public void setId(Long id) {
		idComTCcnl = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CCNL")
	private Long idComTCcnl;

	@Column(name="COD_CCNL_MIN")
	private String codCcnlMin;

	@Column(name="DS_COM_T_CCNL")
	private String dsComTCcnl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="ID_NEW_CCNL")
	private BigDecimal idNewCcnl;

	private String settore;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTCcnl")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComTCcnl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CCNL_PREV")
	private ComTCcnl comTCcnl;

	//bi-directional many-to-one association to ComTCcnl
//	@OneToMany(mappedBy="comTCcnl")
//	private List<ComTCcnl> comTCcnls;

	//bi-directional many-to-one association to ComTLivelloRetribuzione
//	@OneToMany(mappedBy="comTCcnl")
//	private List<ComTLivelloRetribuzione> comTLivelloRetribuziones;

	public ComTCcnl() {
	}

	public Long getIdComTCcnl() {
		return this.idComTCcnl;
	}

	public void setIdComTCcnl(Long idComTCcnl) {
		this.idComTCcnl = idComTCcnl;
	}

	public String getCodCcnlMin() {
		return this.codCcnlMin;
	}

	public void setCodCcnlMin(String codCcnlMin) {
		this.codCcnlMin = codCcnlMin;
	}

	public String getDsComTCcnl() {
		return this.dsComTCcnl;
	}

	public void setDsComTCcnl(String dsComTCcnl) {
		this.dsComTCcnl = dsComTCcnl;
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

	public BigDecimal getIdNewCcnl() {
		return this.idNewCcnl;
	}

	public void setIdNewCcnl(BigDecimal idNewCcnl) {
		this.idNewCcnl = idNewCcnl;
	}

	public String getSettore() {
		return this.settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
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
//		comDRapporto.setComTCcnl(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTCcnl(null);
//
//		return comDRapporto;
//	}

	public ComTCcnl getComTCcnl() {
		return this.comTCcnl;
	}

	public void setComTCcnl(ComTCcnl comTCcnl) {
		this.comTCcnl = comTCcnl;
	}

//	public List<ComTCcnl> getComTCcnls() {
//		return this.comTCcnls;
//	}
//
//	public void setComTCcnls(List<ComTCcnl> comTCcnls) {
//		this.comTCcnls = comTCcnls;
//	}
//
//	public ComTCcnl addComTCcnl(ComTCcnl comTCcnl) {
//		getComTCcnls().add(comTCcnl);
//		comTCcnl.setComTCcnl(this);
//
//		return comTCcnl;
//	}
//
//	public ComTCcnl removeComTCcnl(ComTCcnl comTCcnl) {
//		getComTCcnls().remove(comTCcnl);
//		comTCcnl.setComTCcnl(null);
//
//		return comTCcnl;
//	}

//	public List<ComTLivelloRetribuzione> getComTLivelloRetribuziones() {
//		return this.comTLivelloRetribuziones;
//	}
//
//	public void setComTLivelloRetribuziones(List<ComTLivelloRetribuzione> comTLivelloRetribuziones) {
//		this.comTLivelloRetribuziones = comTLivelloRetribuziones;
//	}
//
//	public ComTLivelloRetribuzione addComTLivelloRetribuzione(ComTLivelloRetribuzione comTLivelloRetribuzione) {
//		getComTLivelloRetribuziones().add(comTLivelloRetribuzione);
//		comTLivelloRetribuzione.setComTCcnl(this);
//
//		return comTLivelloRetribuzione;
//	}
//
//	public ComTLivelloRetribuzione removeComTLivelloRetribuzione(ComTLivelloRetribuzione comTLivelloRetribuzione) {
//		getComTLivelloRetribuziones().remove(comTLivelloRetribuzione);
//		comTLivelloRetribuzione.setComTCcnl(null);
//
//		return comTLivelloRetribuzione;
//	}

}
