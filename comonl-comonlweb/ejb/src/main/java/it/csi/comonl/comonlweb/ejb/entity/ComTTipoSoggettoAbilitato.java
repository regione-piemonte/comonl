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
 * The persistent class for the COM_T_TIPO_SOGGETTO_ABILITATO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_SOGGETTO_ABILITATO")
@NamedQuery(name="ComTTipoSoggettoAbilitato.findAll", query="SELECT c FROM ComTTipoSoggettoAbilitato c")
public class ComTTipoSoggettoAbilitato implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTipoSoggettoAbilitato;
	}

	@Override
	public void setId(Long id) {
		idComTipoSoggettoAbilitato = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_TIPO_SOGGETTO_ABILITATO")
	private long idComTipoSoggettoAbilitato;

	@Column(name="COD_SOGGETTO_ABILITATO_MIN")
	private String codSoggettoAbilitatoMin;

	@Column(name="DS_COM_TIPO_SOGGETTO_ABILITATO")
	private String dsComTipoSoggettoAbilitato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoSoggettoAbilitato")
//	private List<ComDComunicazione> comDComunicaziones;

	//bi-directional many-to-one association to ComDSoggettoAbilitato
//	@OneToMany(mappedBy="comTTipoSoggettoAbilitato")
//	private List<ComDSoggettoAbilitato> comDSoggettoAbilitatos;

	public ComTTipoSoggettoAbilitato() {
	}

	public long getIdComTipoSoggettoAbilitato() {
		return this.idComTipoSoggettoAbilitato;
	}

	public void setIdComTipoSoggettoAbilitato(long idComTipoSoggettoAbilitato) {
		this.idComTipoSoggettoAbilitato = idComTipoSoggettoAbilitato;
	}

	public String getCodSoggettoAbilitatoMin() {
		return this.codSoggettoAbilitatoMin;
	}

	public void setCodSoggettoAbilitatoMin(String codSoggettoAbilitatoMin) {
		this.codSoggettoAbilitatoMin = codSoggettoAbilitatoMin;
	}

	public String getDsComTipoSoggettoAbilitato() {
		return this.dsComTipoSoggettoAbilitato;
	}

	public void setDsComTipoSoggettoAbilitato(String dsComTipoSoggettoAbilitato) {
		this.dsComTipoSoggettoAbilitato = dsComTipoSoggettoAbilitato;
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
//		comDComunicazione.setComTTipoSoggettoAbilitato(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoSoggettoAbilitato(null);
//
//		return comDComunicazione;
//	}

//	public List<ComDSoggettoAbilitato> getComDSoggettoAbilitatos() {
//		return this.comDSoggettoAbilitatos;
//	}
//
//	public void setComDSoggettoAbilitatos(List<ComDSoggettoAbilitato> comDSoggettoAbilitatos) {
//		this.comDSoggettoAbilitatos = comDSoggettoAbilitatos;
//	}
//
//	public ComDSoggettoAbilitato addComDSoggettoAbilitato(ComDSoggettoAbilitato comDSoggettoAbilitato) {
//		getComDSoggettoAbilitatos().add(comDSoggettoAbilitato);
//		comDSoggettoAbilitato.setComTTipoSoggettoAbilitato(this);
//
//		return comDSoggettoAbilitato;
//	}
//
//	public ComDSoggettoAbilitato removeComDSoggettoAbilitato(ComDSoggettoAbilitato comDSoggettoAbilitato) {
//		getComDSoggettoAbilitatos().remove(comDSoggettoAbilitato);
//		comDSoggettoAbilitato.setComTTipoSoggettoAbilitato(null);
//
//		return comDSoggettoAbilitato;
//	}

}
