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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_ORARIO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_ORARIO")
@NamedQuery(name="ComTTipoOrario.findAll", query="SELECT c FROM ComTTipoOrario c")
public class ComTTipoOrario implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoOrario;
	}

	@Override
	public void setId(Long id) {
		idComTTipoOrario = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_ORARIO")
	private Long idComTTipoOrario;

	@Column(name="COD_TIPOORARIO_MIN")
	private String codTipoorarioMin;

	@Column(name="DS_COM_T_TIPO_ORARIO")
	private String dsComTTipoOrario;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="ID_NEW_TIPOORARIO")
	private BigDecimal idNewTipoorario;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTipoOrario")
//	private List<ComDRapporto> comDRapportos;

	public ComTTipoOrario() {
	}

	public Long getIdComTTipoOrario() {
		return this.idComTTipoOrario;
	}

	public void setIdComTTipoOrario(Long idComTTipoOrario) {
		this.idComTTipoOrario = idComTTipoOrario;
	}

	public String getCodTipoorarioMin() {
		return this.codTipoorarioMin;
	}

	public void setCodTipoorarioMin(String codTipoorarioMin) {
		this.codTipoorarioMin = codTipoorarioMin;
	}

	public String getDsComTTipoOrario() {
		return this.dsComTTipoOrario;
	}

	public void setDsComTTipoOrario(String dsComTTipoOrario) {
		this.dsComTTipoOrario = dsComTTipoOrario;
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

	public BigDecimal getIdNewTipoorario() {
		return this.idNewTipoorario;
	}

	public void setIdNewTipoorario(BigDecimal idNewTipoorario) {
		this.idNewTipoorario = idNewTipoorario;
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
//		comDRapporto.setComTTipoOrario(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTipoOrario(null);
//
//		return comDRapporto;
//	}

}
