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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;

/**
 * The persistent class for the COM_T_PROVINCIA database table.
 * 
 */
@Entity
@Table(name = "COM_T_PROVINCIA")
@NamedQuery(name = "ComTProvincia.findAll", query = "SELECT c FROM ComTProvincia c")
public class ComTProvincia implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTProvincia;
	}

	@Override
	public void setId(Long id) {
		idComTProvincia = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_T_PROVINCIA")
	private long idComTProvincia;

	@Column(name = "COD_PROVINCIA_MIN")
	private String codProvinciaMin;

	@Column(name = "CODICE_FISCALE_PV")
	private String codiceFiscalePv;

	@Column(name = "DS_COM_T_PROVINCIA")
	private String dsComTProvincia;

	@Column(name = "DS_TARGA")
	private String dsTarga;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_TMST")
	private Date dtTmst;

	// bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTProvincia")
//	private List<ComDComunicazione> comDComunicaziones;

	// bi-directional many-to-one association to ComRPersonalizProvince
//	@OneToMany(mappedBy="comTProvincia")
//	private List<ComRPersonalizProvince> comRPersonalizProvinces;

	// bi-directional many-to-one association to ComTComune
//	@OneToMany(mappedBy="comTProvincia")
//	private List<ComTComune> comTComunes;

	// bi-directional many-to-one association to ComTRegione
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_REGIONE")
	private ComTRegione comTRegione;

	public ComTProvincia() {
	}

	public long getIdComTProvincia() {
		return this.idComTProvincia;
	}

	public void setIdComTProvincia(long idComTProvincia) {
		this.idComTProvincia = idComTProvincia;
	}

	public String getCodProvinciaMin() {
		return this.codProvinciaMin;
	}

	public void setCodProvinciaMin(String codProvinciaMin) {
		this.codProvinciaMin = codProvinciaMin;
	}

	public String getCodiceFiscalePv() {
		return this.codiceFiscalePv;
	}

	public void setCodiceFiscalePv(String codiceFiscalePv) {
		this.codiceFiscalePv = codiceFiscalePv;
	}

	public String getDsComTProvincia() {
		return this.dsComTProvincia;
	}

	public void setDsComTProvincia(String dsComTProvincia) {
		this.dsComTProvincia = dsComTProvincia;
	}

	public String getDsTarga() {
		return this.dsTarga;
	}

	public void setDsTarga(String dsTarga) {
		this.dsTarga = dsTarga;
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

	public ComTRegione getComTRegione() {
		return this.comTRegione;
	}

	public void setComTRegione(ComTRegione comTRegione) {
		this.comTRegione = comTRegione;
	}

}
