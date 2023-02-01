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
 * The persistent class for the COM_D_ANAGRAFICA_SEDE_LAVORO database table.
 * 
 */
@Entity
@Table(name="COM_D_ANAGRAFICA_SEDE_LAVORO")
@NamedQuery(name="ComDAnagraficaSedeLavoro.findAll", query="SELECT c FROM ComDAnagraficaSedeLavoro c")
public class ComDAnagraficaSedeLavoro implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDAnagraficSedeLavoro;
	}

	@Override
	public void setId(Long id) {
		idComDAnagraficSedeLavoro = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_ANAGRAFIC_SEDE_LAVORO")
	private long idComDAnagraficSedeLavoro;

	@Column(name="COD_CAP")
	private String codCap;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	private String email;

	private String fax;

	@Column(name="FLG_SEDE_LEGALE")
	private String flgSedeLegale;

	private String indirizzo;

	private String telefono;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE")
	private ComTComune comTComune;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI")
	private ComTStatiEsteri comTStatiEsteri;

	//bi-directional many-to-many association to ComDAnagraficaDatore
//	@ManyToMany
//	@JoinTable(
//		name="COM_R_ANAG_DATORE_ANAG_SEDE"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_D_ANAGRAFIC_SEDE_LAVORO")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_D_ANAGRAFICA_DATORE")
//			}
//		)
//	private List<ComDAnagraficaDatore> comDAnagraficaDatores;

	public ComDAnagraficaSedeLavoro() {
	}

	public long getIdComDAnagraficSedeLavoro() {
		return this.idComDAnagraficSedeLavoro;
	}

	public void setIdComDAnagraficSedeLavoro(long idComDAnagraficSedeLavoro) {
		this.idComDAnagraficSedeLavoro = idComDAnagraficSedeLavoro;
	}

	public String getCodCap() {
		return this.codCap;
	}

	public void setCodCap(String codCap) {
		this.codCap = codCap;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFlgSedeLegale() {
		return this.flgSedeLegale;
	}

	public void setFlgSedeLegale(String flgSedeLegale) {
		this.flgSedeLegale = flgSedeLegale;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ComTComune getComTComune() {
		return this.comTComune;
	}

	public void setComTComune(ComTComune comTComune) {
		this.comTComune = comTComune;
	}

	public ComTStatiEsteri getComTStatiEsteri() {
		return this.comTStatiEsteri;
	}

	public void setComTStatiEsteri(ComTStatiEsteri comTStatiEsteri) {
		this.comTStatiEsteri = comTStatiEsteri;
	}

//	public List<ComDAnagraficaDatore> getComDAnagraficaDatores() {
//		return this.comDAnagraficaDatores;
//	}
//
//	public void setComDAnagraficaDatores(List<ComDAnagraficaDatore> comDAnagraficaDatores) {
//		this.comDAnagraficaDatores = comDAnagraficaDatores;
//	}

}
