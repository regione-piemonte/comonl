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
 * The persistent class for the COM_T_CATEG_TIROCINANTE database table.
 * 
 */
@Entity
@Table(name="COM_T_CATEG_TIROCINANTE")
@NamedQuery(name="ComTCategTirocinante.findAll", query="SELECT c FROM ComTCategTirocinante c")
public class ComTCategTirocinante implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCategTirocinante;
	}

	@Override
	public void setId(Long id) {
		idComTCategTirocinante = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CATEG_TIROCINANTE")
	private Long idComTCategTirocinante;

	@Column(name="COD_CATEG_TIROCINANTE_MIN")
	private String codCategTirocinanteMin;

	@Column(name="DESCR_CATEG_TIROCINANTE")
	private String descrCategTirocinante;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTCategTirocinante")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComRTipolTirocCatTiroc
//	@OneToMany(mappedBy="comTCategTirocinante")
//	private List<ComRTipolTirocCatTiroc> comRTipolTirocCatTirocs;

	public ComTCategTirocinante() {
	}

	public Long getIdComTCategTirocinante() {
		return this.idComTCategTirocinante;
	}

	public void setIdComTCategTirocinante(Long idComTCategTirocinante) {
		this.idComTCategTirocinante = idComTCategTirocinante;
	}

	public String getCodCategTirocinanteMin() {
		return this.codCategTirocinanteMin;
	}

	public void setCodCategTirocinanteMin(String codCategTirocinanteMin) {
		this.codCategTirocinanteMin = codCategTirocinanteMin;
	}

	public String getDescrCategTirocinante() {
		return this.descrCategTirocinante;
	}

	public void setDescrCategTirocinante(String descrCategTirocinante) {
		this.descrCategTirocinante = descrCategTirocinante;
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
//		comDRapporto.setComTCategTirocinante(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTCategTirocinante(null);
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
//		comRTipolTirocCatTiroc.setComTCategTirocinante(this);
//
//		return comRTipolTirocCatTiroc;
//	}
//
//	public ComRTipolTirocCatTiroc removeComRTipolTirocCatTiroc(ComRTipolTirocCatTiroc comRTipolTirocCatTiroc) {
//		getComRTipolTirocCatTirocs().remove(comRTipolTirocCatTiroc);
//		comRTipolTirocCatTiroc.setComTCategTirocinante(null);
//
//		return comRTipolTirocCatTiroc;
//	}

}
