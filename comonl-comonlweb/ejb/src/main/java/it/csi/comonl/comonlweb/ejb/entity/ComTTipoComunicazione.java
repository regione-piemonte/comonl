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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_COMUNICAZIONE")
@NamedQuery(name="ComTTipoComunicazione.findAll", query="SELECT c FROM ComTTipoComunicazione c")
public class ComTTipoComunicazione implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoComunicazione;
	}

	@Override
	public void setId(String id) {
		idComTTipoComunicazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_COMUNICAZIONE")
	private String idComTTipoComunicazione;

	@Column(name="DS_COM_T_TIPO_COMUNICAZIONE")
	private String dsComTTipoComunicazione;

	private String tipo;

	@Column(name="VALIDITA_AG_INT")
	private BigDecimal validitaAgInt;

	@Column(name="VALIDITA_CPI")
	private BigDecimal validitaCpi;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoComunicazione")
//	private List<ComDComunicazione> comDComunicaziones;

	//bi-directional many-to-many association to ComTTipoSomministrazione
//	@ManyToMany(mappedBy="comTTipoComunicaziones")
//	private List<ComTTipoSomministrazione> comTTipoSomministraziones;

	//bi-directional many-to-one association to ComRRegTracciatoContratto
//	@OneToMany(mappedBy="comTTipoComunicazione")
//	private List<ComRRegTracciatoContratto> comRRegTracciatoContrattos;

	//bi-directional many-to-one association to ComTVariazioneSomm
//	@OneToMany(mappedBy="comTTipoComunicazione")
//	private List<ComTVariazioneSomm> comTVariazioneSomms;

	public ComTTipoComunicazione() {
	}

	public String getIdComTTipoComunicazione() {
		return this.idComTTipoComunicazione;
	}

	public void setIdComTTipoComunicazione(String idComTTipoComunicazione) {
		this.idComTTipoComunicazione = idComTTipoComunicazione;
	}

	public String getDsComTTipoComunicazione() {
		return this.dsComTTipoComunicazione;
	}

	public void setDsComTTipoComunicazione(String dsComTTipoComunicazione) {
		this.dsComTTipoComunicazione = dsComTTipoComunicazione;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValiditaAgInt() {
		return this.validitaAgInt;
	}

	public void setValiditaAgInt(BigDecimal validitaAgInt) {
		this.validitaAgInt = validitaAgInt;
	}

	public BigDecimal getValiditaCpi() {
		return this.validitaCpi;
	}

	public void setValiditaCpi(BigDecimal validitaCpi) {
		this.validitaCpi = validitaCpi;
	}

//	public List<ComDComunicazione> getComDComunicaziones() {
//		return this.comDComunicaziones;
//	}
//
//	public void setComDComunicaziones(List<ComDComunicazione> comDComunicaziones) {
//		this.comDComunicaziones = comDComunicaziones;
//	}
//
//	public ComDComunicazione addComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().add(comDComunicazione);
//		comDComunicazione.setComTTipoComunicazione(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoComunicazione(null);
//
//		return comDComunicazione;
//	}

//	public List<ComTTipoSomministrazione> getComTTipoSomministraziones() {
//		return this.comTTipoSomministraziones;
//	}
//
//	public void setComTTipoSomministraziones(List<ComTTipoSomministrazione> comTTipoSomministraziones) {
//		this.comTTipoSomministraziones = comTTipoSomministraziones;
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
//		comRRegTracciatoContratto.setComTTipoComunicazione(this);
//
//		return comRRegTracciatoContratto;
//	}
//
//	public ComRRegTracciatoContratto removeComRRegTracciatoContratto(ComRRegTracciatoContratto comRRegTracciatoContratto) {
//		getComRRegTracciatoContrattos().remove(comRRegTracciatoContratto);
//		comRRegTracciatoContratto.setComTTipoComunicazione(null);
//
//		return comRRegTracciatoContratto;
//	}

//	public List<ComTVariazioneSomm> getComTVariazioneSomms() {
//		return this.comTVariazioneSomms;
//	}
//
//	public void setComTVariazioneSomms(List<ComTVariazioneSomm> comTVariazioneSomms) {
//		this.comTVariazioneSomms = comTVariazioneSomms;
//	}
//
//	public ComTVariazioneSomm addComTVariazioneSomm(ComTVariazioneSomm comTVariazioneSomm) {
//		getComTVariazioneSomms().add(comTVariazioneSomm);
//		comTVariazioneSomm.setComTTipoComunicazione(this);
//
//		return comTVariazioneSomm;
//	}
//
//	public ComTVariazioneSomm removeComTVariazioneSomm(ComTVariazioneSomm comTVariazioneSomm) {
//		getComTVariazioneSomms().remove(comTVariazioneSomm);
//		comTVariazioneSomm.setComTTipoComunicazione(null);
//
//		return comTVariazioneSomm;
//	}

}
