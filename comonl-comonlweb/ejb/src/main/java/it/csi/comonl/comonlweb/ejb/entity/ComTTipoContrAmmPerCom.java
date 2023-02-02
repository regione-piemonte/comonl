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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_CONTR_AMM_PER_COM database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_CONTR_AMM_PER_COM")
@NamedQuery(name="ComTTipoContrAmmPerCom.findAll", query="SELECT c FROM ComTTipoContrAmmPerCom c")
public class ComTTipoContrAmmPerCom implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoContrAmmCom;
	}

	@Override
	public void setId(Long id) {
		idComTTipoContrAmmCom = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_CONTR_AMM_COM")
	private long idComTTipoContrAmmCom;

	@Column(name="FLG_SOCIO_LAV")
	private String flgSocioLav;

	@Column(name="FLG_TIPO_ORARIO_NON_DEFINITO")
	private String flgTipoOrarioNonDefinito;

	@Column(name="FLG_VLD_CES")
	private String flgVldCes;

	@Column(name="FLG_VLD_DST")
	private String flgVldDst;

	@Column(name="FLG_VLD_PRO")
	private String flgVldPro;

	@Column(name="FLG_VLD_SOM")
	private String flgVldSom;

	@Column(name="FLG_VLD_TRA")
	private String flgVldTra;

	@Column(name="FLG_VLD_TRS")
	private String flgVldTrs;

	@Column(name="FLG_VLD_UD")
	private String flgVldUd;

	@Column(name="FLG_VLD_UL")
	private String flgVldUl;

	@Column(name="FLG_VLD_VD")
	private String flgVldVd;

	//bi-directional many-to-one association to ComTTipoContratti
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_CONTRATTO")
	private ComTTipoContratti comTTipoContratti;

	public ComTTipoContrAmmPerCom() {
	}

	public long getIdComTTipoContrAmmCom() {
		return this.idComTTipoContrAmmCom;
	}

	public void setIdComTTipoContrAmmCom(long idComTTipoContrAmmCom) {
		this.idComTTipoContrAmmCom = idComTTipoContrAmmCom;
	}

	public String getFlgSocioLav() {
		return this.flgSocioLav;
	}

	public void setFlgSocioLav(String flgSocioLav) {
		this.flgSocioLav = flgSocioLav;
	}

	public String getFlgTipoOrarioNonDefinito() {
		return this.flgTipoOrarioNonDefinito;
	}

	public void setFlgTipoOrarioNonDefinito(String flgTipoOrarioNonDefinito) {
		this.flgTipoOrarioNonDefinito = flgTipoOrarioNonDefinito;
	}

	public String getFlgVldCes() {
		return this.flgVldCes;
	}

	public void setFlgVldCes(String flgVldCes) {
		this.flgVldCes = flgVldCes;
	}

	public String getFlgVldDst() {
		return this.flgVldDst;
	}

	public void setFlgVldDst(String flgVldDst) {
		this.flgVldDst = flgVldDst;
	}

	public String getFlgVldPro() {
		return this.flgVldPro;
	}

	public void setFlgVldPro(String flgVldPro) {
		this.flgVldPro = flgVldPro;
	}

	public String getFlgVldSom() {
		return this.flgVldSom;
	}

	public void setFlgVldSom(String flgVldSom) {
		this.flgVldSom = flgVldSom;
	}

	public String getFlgVldTra() {
		return this.flgVldTra;
	}

	public void setFlgVldTra(String flgVldTra) {
		this.flgVldTra = flgVldTra;
	}

	public String getFlgVldTrs() {
		return this.flgVldTrs;
	}

	public void setFlgVldTrs(String flgVldTrs) {
		this.flgVldTrs = flgVldTrs;
	}

	public String getFlgVldUd() {
		return this.flgVldUd;
	}

	public void setFlgVldUd(String flgVldUd) {
		this.flgVldUd = flgVldUd;
	}

	public String getFlgVldUl() {
		return this.flgVldUl;
	}

	public void setFlgVldUl(String flgVldUl) {
		this.flgVldUl = flgVldUl;
	}

	public String getFlgVldVd() {
		return this.flgVldVd;
	}

	public void setFlgVldVd(String flgVldVd) {
		this.flgVldVd = flgVldVd;
	}

	public ComTTipoContratti getComTTipoContratti() {
		return this.comTTipoContratti;
	}

	public void setComTTipoContratti(ComTTipoContratti comTTipoContratti) {
		this.comTTipoContratti = comTTipoContratti;
	}

}
