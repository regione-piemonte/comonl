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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_CONTRATTI database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_CONTRATTI")
@NamedQuery(name="ComTTipoContratti.findAll", query="SELECT c FROM ComTTipoContratti c")
public class ComTTipoContratti implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoContratto;
	}

	@Override
	public void setId(Long id) {
		idComTTipoContratto = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_CONTRATTO")
	private Long idComTTipoContratto;

	@Column(name="COD_TIPO_CONTRATTO_MIN")
	private String codTipoContrattoMin;

	@Column(name="DS_COM_T_TIPO_CONTRATTO")
	private String dsComTTipoContratto;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="FLG_FORMA")
	private String flgForma;

	private String status;

	private String tipo;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTipoContratti")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComRRegTracciatoContratto
//	@OneToMany(mappedBy="comTTipoContratti")
//	private List<ComRRegTracciatoContratto> comRRegTracciatoContrattos;

	//bi-directional many-to-one association to ComTTipologiaRapporto
	@ManyToOne
	@JoinColumn(name="TIPOLOGIA_RAPPORTO")
	private ComTTipologiaRapporto comTTipologiaRapporto;

	//bi-directional many-to-one association to ComTTipoContrAmmPerCom
//	@OneToMany(mappedBy="comTTipoContratti")
//	private List<ComTTipoContrAmmPerCom> comTTipoContrAmmPerComs;

	//bi-directional many-to-one association to ComTTipoContrPeriodi
//	@OneToMany(mappedBy="comTTipoContratti")
//	private List<ComTTipoContrPeriodi> comTTipoContrPeriodis;

	public ComTTipoContratti() {
	}

	public Long getIdComTTipoContratto() {
		return this.idComTTipoContratto;
	}

	public void setIdComTTipoContratto(Long idComTTipoContratto) {
		this.idComTTipoContratto = idComTTipoContratto;
	}

	public String getCodTipoContrattoMin() {
		return this.codTipoContrattoMin;
	}

	public void setCodTipoContrattoMin(String codTipoContrattoMin) {
		this.codTipoContrattoMin = codTipoContrattoMin;
	}

	public String getDsComTTipoContratto() {
		return this.dsComTTipoContratto;
	}

	public void setDsComTTipoContratto(String dsComTTipoContratto) {
		this.dsComTTipoContratto = dsComTTipoContratto;
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

	public String getFlgForma() {
		return this.flgForma;
	}

	public void setFlgForma(String flgForma) {
		this.flgForma = flgForma;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
//		comDRapporto.setComTTipoContratti(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTipoContratti(null);
//
//		return comDRapporto;
//	}

//	public List<ComRRegTracciatoContratto> getComRRegTracciatoContrattos() {
//		return this.comRRegTracciatoContrattos;
//	}
//
//	public void setComRRegTracciatoContrattos(List<ComRRegTracciatoContratto> comRRegTracciatoContrattos) {
//		this.comRRegTracciatoContrattos = comRRegTracciatoContrattos;
//	}
//
//	public ComRRegTracciatoContratto addComRRegTracciatoContratto(ComRRegTracciatoContratto comRRegTracciatoContratto) {
//		getComRRegTracciatoContrattos().add(comRRegTracciatoContratto);
//		comRRegTracciatoContratto.setComTTipoContratti(this);
//
//		return comRRegTracciatoContratto;
//	}
//
//	public ComRRegTracciatoContratto removeComRRegTracciatoContratto(ComRRegTracciatoContratto comRRegTracciatoContratto) {
//		getComRRegTracciatoContrattos().remove(comRRegTracciatoContratto);
//		comRRegTracciatoContratto.setComTTipoContratti(null);
//
//		return comRRegTracciatoContratto;
//	}

	public ComTTipologiaRapporto getComTTipologiaRapporto() {
		return this.comTTipologiaRapporto;
	}

	public void setComTTipologiaRapporto(ComTTipologiaRapporto comTTipologiaRapporto) {
		this.comTTipologiaRapporto = comTTipologiaRapporto;
	}

//	public List<ComTTipoContrAmmPerCom> getComTTipoContrAmmPerComs() {
//		return this.comTTipoContrAmmPerComs;
//	}
//
//	public void setComTTipoContrAmmPerComs(List<ComTTipoContrAmmPerCom> comTTipoContrAmmPerComs) {
//		this.comTTipoContrAmmPerComs = comTTipoContrAmmPerComs;
//	}
//
//	public ComTTipoContrAmmPerCom addComTTipoContrAmmPerCom(ComTTipoContrAmmPerCom comTTipoContrAmmPerCom) {
//		getComTTipoContrAmmPerComs().add(comTTipoContrAmmPerCom);
//		comTTipoContrAmmPerCom.setComTTipoContratti(this);
//
//		return comTTipoContrAmmPerCom;
//	}
//
//	public ComTTipoContrAmmPerCom removeComTTipoContrAmmPerCom(ComTTipoContrAmmPerCom comTTipoContrAmmPerCom) {
//		getComTTipoContrAmmPerComs().remove(comTTipoContrAmmPerCom);
//		comTTipoContrAmmPerCom.setComTTipoContratti(null);
//
//		return comTTipoContrAmmPerCom;
//	}

//	public List<ComTTipoContrPeriodi> getComTTipoContrPeriodis() {
//		return this.comTTipoContrPeriodis;
//	}
//
//	public void setComTTipoContrPeriodis(List<ComTTipoContrPeriodi> comTTipoContrPeriodis) {
//		this.comTTipoContrPeriodis = comTTipoContrPeriodis;
//	}
//
//	public ComTTipoContrPeriodi addComTTipoContrPeriodi(ComTTipoContrPeriodi comTTipoContrPeriodi) {
//		getComTTipoContrPeriodis().add(comTTipoContrPeriodi);
//		comTTipoContrPeriodi.setComTTipoContratti(this);
//
//		return comTTipoContrPeriodi;
//	}
//
//	public ComTTipoContrPeriodi removeComTTipoContrPeriodi(ComTTipoContrPeriodi comTTipoContrPeriodi) {
//		getComTTipoContrPeriodis().remove(comTTipoContrPeriodi);
//		comTTipoContrPeriodi.setComTTipoContratti(null);
//
//		return comTTipoContrPeriodi;
//	}

}
