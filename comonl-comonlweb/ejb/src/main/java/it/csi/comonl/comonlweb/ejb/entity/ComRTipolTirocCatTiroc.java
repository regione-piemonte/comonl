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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_TIPOL_TIROC_CAT_TIROC database table.
 * 
 */
@Entity
@Table(name="COM_R_TIPOL_TIROC_CAT_TIROC")
@NamedQuery(name="ComRTipolTirocCatTiroc.findAll", query="SELECT c FROM ComRTipolTirocCatTiroc c")
public class ComRTipolTirocCatTiroc implements Serializable, BaseEntity<ComRTipolTirocCatTirocPK> {

	@Override
	public ComRTipolTirocCatTirocPK getId() {
		return id;
	}

	@Override
	public void setId(ComRTipolTirocCatTirocPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRTipolTirocCatTirocPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="D_FINE")
	private Date dFine;

	@Temporal(TemporalType.DATE)
	@Column(name="D_INIZIO")
	private Date dInizio;

	@Column(name="FLG_VISUALIZZA_WARNING")
	private String flgVisualizzaWarning;

	@Column(name="NUM_DURATA_MAX")
	private BigDecimal numDurataMax;

	//bi-directional many-to-one association to ComTCategTirocinante
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTiroc column: 
	// ID_COM_T_CATEG_TIROCINANTE (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_T_CATEG_TIROCINANTE")
	@JoinColumn(name="ID_COM_T_CATEG_TIROCINANTE", insertable=false, updatable=false)
	private ComTCategTirocinante comTCategTirocinante;

	//bi-directional many-to-one association to ComTTipologiaTirocinio
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTiroc column: 
	// ID_COM_T_TIPOLOGIA_TIROCINIO (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_T_TIPOLOGIA_TIROCINIO")
	@JoinColumn(name="ID_COM_T_TIPOLOGIA_TIROCINIO", insertable=false, updatable=false)
	private ComTTipologiaTirocinio comTTipologiaTirocinio;

	//bi-directional many-to-one association to ComTUnitaMisuraDurata
	@ManyToOne
	@JoinColumn(name="COD_UNITA_MISURA_DURATA")
	private ComTUnitaMisuraDurata comTUnitaMisuraDurata;

	public ComRTipolTirocCatTiroc() {
	}

	public Date getDFine() {
		return this.dFine;
	}

	public void setDFine(Date dFine) {
		this.dFine = dFine;
	}

	public Date getDInizio() {
		return this.dInizio;
	}

	public void setDInizio(Date dInizio) {
		this.dInizio = dInizio;
	}

	public String getFlgVisualizzaWarning() {
		return this.flgVisualizzaWarning;
	}

	public void setFlgVisualizzaWarning(String flgVisualizzaWarning) {
		this.flgVisualizzaWarning = flgVisualizzaWarning;
	}

	public BigDecimal getNumDurataMax() {
		return this.numDurataMax;
	}

	public void setNumDurataMax(BigDecimal numDurataMax) {
		this.numDurataMax = numDurataMax;
	}

	public ComTCategTirocinante getComTCategTirocinante() {
		return this.comTCategTirocinante;
	}

	public void setComTCategTirocinante(ComTCategTirocinante comTCategTirocinante) {
		this.comTCategTirocinante = comTCategTirocinante;
	}

	public ComTTipologiaTirocinio getComTTipologiaTirocinio() {
		return this.comTTipologiaTirocinio;
	}

	public void setComTTipologiaTirocinio(ComTTipologiaTirocinio comTTipologiaTirocinio) {
		this.comTTipologiaTirocinio = comTTipologiaTirocinio;
	}

	public ComTUnitaMisuraDurata getComTUnitaMisuraDurata() {
		return this.comTUnitaMisuraDurata;
	}

	public void setComTUnitaMisuraDurata(ComTUnitaMisuraDurata comTUnitaMisuraDurata) {
		this.comTUnitaMisuraDurata = comTUnitaMisuraDurata;
	}

}
