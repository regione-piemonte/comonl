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
 * The persistent class for the COM_T_CATEG_LAV_ASS_OBBL database table.
 * 
 */
@Entity
@Table(name="COM_T_CATEG_LAV_ASS_OBBL")
@NamedQuery(name="ComTCategLavAssObbl.findAll", query="SELECT c FROM ComTCategLavAssObbl c")
public class ComTCategLavAssObbl implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCategLavAssObbl;
	}

	@Override
	public void setId(Long id) {
		idComTCategLavAssObbl = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CATEG_LAV_ASS_OBBL")
	private Long idComTCategLavAssObbl;

	@Column(name="COD_CATEG_LAV_ASS_OBBL_MIN")
	private String codCategLavAssObblMin;

	@Column(name="COD_NORM_ASS_68_MIN")
	private String codNormAss68Min;

	@Column(name="DS_COM_T_CATEG_LAV_ASS_OBBL")
	private String dsComTCategLavAssObbl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Column(name="FLG_OBBLIGATORIO_BLOCCO_L68")
	private String flgObbligatorioBloccoL68;

	@Column(name="NORMA_RIF_CATEG_LAV_ASS_OBBL")
	private String normaRifCategLavAssObbl;

	private BigDecimal ordinamento;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTCategLavAssObbl")
//	private List<ComDRapporto> comDRapportos;

	public ComTCategLavAssObbl() {
	}

	public Long getIdComTCategLavAssObbl() {
		return this.idComTCategLavAssObbl;
	}

	public void setIdComTCategLavAssObbl(Long idComTCategLavAssObbl) {
		this.idComTCategLavAssObbl = idComTCategLavAssObbl;
	}

	public String getCodCategLavAssObblMin() {
		return this.codCategLavAssObblMin;
	}

	public void setCodCategLavAssObblMin(String codCategLavAssObblMin) {
		this.codCategLavAssObblMin = codCategLavAssObblMin;
	}

	public String getCodNormAss68Min() {
		return this.codNormAss68Min;
	}

	public void setCodNormAss68Min(String codNormAss68Min) {
		this.codNormAss68Min = codNormAss68Min;
	}

	public String getDsComTCategLavAssObbl() {
		return this.dsComTCategLavAssObbl;
	}

	public void setDsComTCategLavAssObbl(String dsComTCategLavAssObbl) {
		this.dsComTCategLavAssObbl = dsComTCategLavAssObbl;
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

	public String getFlgObbligatorioBloccoL68() {
		return this.flgObbligatorioBloccoL68;
	}

	public void setFlgObbligatorioBloccoL68(String flgObbligatorioBloccoL68) {
		this.flgObbligatorioBloccoL68 = flgObbligatorioBloccoL68;
	}

	public String getNormaRifCategLavAssObbl() {
		return this.normaRifCategLavAssObbl;
	}

	public void setNormaRifCategLavAssObbl(String normaRifCategLavAssObbl) {
		this.normaRifCategLavAssObbl = normaRifCategLavAssObbl;
	}

	public BigDecimal getOrdinamento() {
		return this.ordinamento;
	}

	public void setOrdinamento(BigDecimal ordinamento) {
		this.ordinamento = ordinamento;
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
//		comDRapporto.setComTCategLavAssObbl(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTCategLavAssObbl(null);
//
//		return comDRapporto;
//	}

}
