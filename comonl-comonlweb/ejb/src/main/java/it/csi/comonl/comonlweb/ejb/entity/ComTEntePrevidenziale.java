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
 * The persistent class for the COM_T_ENTE_PREVIDENZIALE database table.
 * 
 */
@Entity
@Table(name="COM_T_ENTE_PREVIDENZIALE")
@NamedQuery(name="ComTEntePrevidenziale.findAll", query="SELECT c FROM ComTEntePrevidenziale c")
public class ComTEntePrevidenziale implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTEntePrevidenziale;
	}

	@Override
	public void setId(Long id) {
		idComTEntePrevidenziale = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_ENTE_PREVIDENZIALE")
	private Long idComTEntePrevidenziale;

	@Column(name="COD_ENTE_PREVIDENZIALE_MIN")
	private String codEntePrevidenzialeMin;

	@Column(name="DS_COM_T_ENTE_PREVIDENZIALE")
	private String dsComTEntePrevidenziale;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTEntePrevidenziale")
//	private List<ComDRapporto> comDRapportos;

	public ComTEntePrevidenziale() {
	}

	public Long getIdComTEntePrevidenziale() {
		return this.idComTEntePrevidenziale;
	}

	public void setIdComTEntePrevidenziale(Long idComTEntePrevidenziale) {
		this.idComTEntePrevidenziale = idComTEntePrevidenziale;
	}

	public String getCodEntePrevidenzialeMin() {
		return this.codEntePrevidenzialeMin;
	}

	public void setCodEntePrevidenzialeMin(String codEntePrevidenzialeMin) {
		this.codEntePrevidenzialeMin = codEntePrevidenzialeMin;
	}

	public String getDsComTEntePrevidenziale() {
		return this.dsComTEntePrevidenziale;
	}

	public void setDsComTEntePrevidenziale(String dsComTEntePrevidenziale) {
		this.dsComTEntePrevidenziale = dsComTEntePrevidenziale;
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
//		comDRapporto.setComTEntePrevidenziale(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTEntePrevidenziale(null);
//
//		return comDRapporto;
//	}
	

}
