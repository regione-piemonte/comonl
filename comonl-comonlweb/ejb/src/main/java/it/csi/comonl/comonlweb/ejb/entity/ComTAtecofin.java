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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_ATECOFIN database table.
 * 
 */
@Entity
@Table(name="COM_T_ATECOFIN")
@NamedQuery(name="ComTAtecofin.findAll", query="SELECT c FROM ComTAtecofin c")
public class ComTAtecofin implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTAtecofin;
	}

	@Override
	public void setId(Long id) {
		idComTAtecofin = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_ATECOFIN")
	private Long idComTAtecofin;

	@Column(name="COD_ATECOFIN_MIN")
	private String codAtecofinMin;

	@Column(name="DS_COM_T_ATECOFIN")
	private String dsComTAtecofin;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="ID_NEW_ATECOFIN")
	private BigDecimal idNewAtecofin;

	//bi-directional many-to-one association to ComDAnagraficaDatore
//	@OneToMany(mappedBy="comTAtecofin")
//	private List<ComDAnagraficaDatore> comDAnagraficaDatores;

	//bi-directional many-to-one association to ComDDatore
//	@OneToMany(mappedBy="comTAtecofin")
//	private List<ComDDatore> comDDatores;

	public ComTAtecofin() {
	}

	public Long getIdComTAtecofin() {
		return this.idComTAtecofin;
	}

	public void setIdComTAtecofin(Long idComTAtecofin) {
		this.idComTAtecofin = idComTAtecofin;
	}

	public String getCodAtecofinMin() {
		return this.codAtecofinMin;
	}

	public void setCodAtecofinMin(String codAtecofinMin) {
		this.codAtecofinMin = codAtecofinMin;
	}

	public String getDsComTAtecofin() {
		return this.dsComTAtecofin;
	}

	public void setDsComTAtecofin(String dsComTAtecofin) {
		this.dsComTAtecofin = dsComTAtecofin;
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

	public BigDecimal getIdNewAtecofin() {
		return this.idNewAtecofin;
	}

	public void setIdNewAtecofin(BigDecimal idNewAtecofin) {
		this.idNewAtecofin = idNewAtecofin;
	}

//	public List<ComDAnagraficaDatore> getComDAnagraficaDatores() {
//		return this.comDAnagraficaDatores;
//	}
//
//	public void setComDAnagraficaDatores(List<ComDAnagraficaDatore> comDAnagraficaDatores) {
//		this.comDAnagraficaDatores = comDAnagraficaDatores;
//	}
//
//	public ComDAnagraficaDatore addComDAnagraficaDatore(ComDAnagraficaDatore comDAnagraficaDatore) {
//		getComDAnagraficaDatores().add(comDAnagraficaDatore);
//		comDAnagraficaDatore.setComTAtecofin(this);
//
//		return comDAnagraficaDatore;
//	}

//	public ComDAnagraficaDatore removeComDAnagraficaDatore(ComDAnagraficaDatore comDAnagraficaDatore) {
//		getComDAnagraficaDatores().remove(comDAnagraficaDatore);
//		comDAnagraficaDatore.setComTAtecofin(null);
//
//		return comDAnagraficaDatore;
//	}
//
//	public List<ComDDatore> getComDDatores() {
//		return this.comDDatores;
//	}
//
//	public void setComDDatores(List<ComDDatore> comDDatores) {
//		this.comDDatores = comDDatores;
//	}
//
//	public ComDDatore addComDDatore(ComDDatore comDDatore) {
//		getComDDatores().add(comDDatore);
//		comDDatore.setComTAtecofin(this);
//
//		return comDDatore;
//	}
//
//	public ComDDatore removeComDDatore(ComDDatore comDDatore) {
//		getComDDatores().remove(comDDatore);
//		comDDatore.setComTAtecofin(null);
//
//		return comDDatore;
//	}

}
