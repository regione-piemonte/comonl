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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPOLOGIA_TIROCINIO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPOLOGIA_TIROCINIO")
@NamedQuery(name="ComTTipologiaTirocinio.findAll", query="SELECT c FROM ComTTipologiaTirocinio c")
public class ComTTipologiaTirocinio implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipologiaTirocinio;
	}

	@Override
	public void setId(Long id) {
		idComTTipologiaTirocinio = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPOLOGIA_TIROCINIO")
	private Long idComTTipologiaTirocinio;

	@Column(name="COD_TIPOLOGIA_TIROCINIO_MIN")
	private String codTipologiaTirocinioMin;

	@Column(name="DESCR_TIPOLOGIA_TIROCINIO")
	private String descrTipologiaTirocinio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTipologiaTirocinio")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComRTipolTirocCatTiroc
//	@OneToMany(mappedBy="comTTipologiaTirocinio")
//	private List<ComRTipolTirocCatTiroc> comRTipolTirocCatTirocs;

	public ComTTipologiaTirocinio() {
	}

	public Long getIdComTTipologiaTirocinio() {
		return this.idComTTipologiaTirocinio;
	}

	public void setIdComTTipologiaTirocinio(Long idComTTipologiaTirocinio) {
		this.idComTTipologiaTirocinio = idComTTipologiaTirocinio;
	}

	public String getCodTipologiaTirocinioMin() {
		return this.codTipologiaTirocinioMin;
	}

	public void setCodTipologiaTirocinioMin(String codTipologiaTirocinioMin) {
		this.codTipologiaTirocinioMin = codTipologiaTirocinioMin;
	}

	public String getDescrTipologiaTirocinio() {
		return this.descrTipologiaTirocinio;
	}

	public void setDescrTipologiaTirocinio(String descrTipologiaTirocinio) {
		this.descrTipologiaTirocinio = descrTipologiaTirocinio;
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
//		comDRapporto.setComTTipologiaTirocinio(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTipologiaTirocinio(null);
//
//		return comDRapporto;
//	}

//	public List<ComRTipolTirocCatTiroc> getComRTipolTirocCatTirocs() {
//		return this.comRTipolTirocCatTirocs;
//	}
//
//	public void setComRTipolTirocCatTirocs(List<ComRTipolTirocCatTiroc> comRTipolTirocCatTirocs) {
//		this.comRTipolTirocCatTirocs = comRTipolTirocCatTirocs;
//	}
//
//	public ComRTipolTirocCatTiroc addComRTipolTirocCatTiroc(ComRTipolTirocCatTiroc comRTipolTirocCatTiroc) {
//		getComRTipolTirocCatTirocs().add(comRTipolTirocCatTiroc);
//		comRTipolTirocCatTiroc.setComTTipologiaTirocinio(this);
//
//		return comRTipolTirocCatTiroc;
//	}
//
//	public ComRTipolTirocCatTiroc removeComRTipolTirocCatTiroc(ComRTipolTirocCatTiroc comRTipolTirocCatTiroc) {
//		getComRTipolTirocCatTirocs().remove(comRTipolTirocCatTiroc);
//		comRTipolTirocCatTiroc.setComTTipologiaTirocinio(null);
//
//		return comRTipolTirocCatTiroc;
//	}

}
