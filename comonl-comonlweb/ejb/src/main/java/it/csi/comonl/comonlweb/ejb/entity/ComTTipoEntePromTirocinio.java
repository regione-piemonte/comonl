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
 * The persistent class for the COM_T_TIPO_ENTE_PROM_TIROCINIO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_ENTE_PROM_TIROCINIO")
@NamedQuery(name="ComTTipoEntePromTirocinio.findAll", query="SELECT c FROM ComTTipoEntePromTirocinio c")
public class ComTTipoEntePromTirocinio implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoEntePromTir;
	}

	@Override
	public void setId(Long id) {
		idComTTipoEntePromTir = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_ENTE_PROM_TIR")
	private long idComTTipoEntePromTir;

	@Column(name="CODICE_ENTE_PROM_TIROCINIO_MIN")
	private String codiceEntePromTirocinioMin;

	@Column(name="DS_COM_T_TIPO_ENTE_PROM_TIR")
	private String dsComTTipoEntePromTir;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="TIP_SIGLA")
	private String tipSigla;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTipoEntePromTirocinio")
//	private List<ComDRapporto> comDRapportos;

	public ComTTipoEntePromTirocinio() {
	}

	public long getIdComTTipoEntePromTir() {
		return this.idComTTipoEntePromTir;
	}

	public void setIdComTTipoEntePromTir(long idComTTipoEntePromTir) {
		this.idComTTipoEntePromTir = idComTTipoEntePromTir;
	}

	public String getCodiceEntePromTirocinioMin() {
		return this.codiceEntePromTirocinioMin;
	}

	public void setCodiceEntePromTirocinioMin(String codiceEntePromTirocinioMin) {
		this.codiceEntePromTirocinioMin = codiceEntePromTirocinioMin;
	}

	public String getDsComTTipoEntePromTir() {
		return this.dsComTTipoEntePromTir;
	}

	public void setDsComTTipoEntePromTir(String dsComTTipoEntePromTir) {
		this.dsComTTipoEntePromTir = dsComTTipoEntePromTir;
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

	public String getTipSigla() {
		return this.tipSigla;
	}

	public void setTipSigla(String tipSigla) {
		this.tipSigla = tipSigla;
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
//		comDRapporto.setComTTipoEntePromTirocinio(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTipoEntePromTirocinio(null);
//
//		return comDRapporto;
//	}

}
