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
 * The persistent class for the COM_D_ANAGRAFICA_AZI_ACCENT database table.
 * 
 */
@Entity
@Table(name="COM_D_ANAGRAFICA_AZI_ACCENT")
@NamedQuery(name="ComDAnagraficaAziAccent.findAll", query="SELECT c FROM ComDAnagraficaAziAccent c")
public class ComDAnagraficaAziAccent implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDAnagraficaAziAccent;
	}

	@Override
	public void setId(Long id) {
		idComDAnagraficaAziAccent = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_ANAGRAFICA_AZI_ACCENT")
	private Long idComDAnagraficaAziAccent;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="DS_DENOMINAZIONE")
	private String dsDenominazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	public ComDAnagraficaAziAccent() {
	}

	public Long getIdComDAnagraficaAziAccent() {
		return this.idComDAnagraficaAziAccent;
	}

	public void setIdComDAnagraficaAziAccent(Long idComDAnagraficaAziAccent) {
		this.idComDAnagraficaAziAccent = idComDAnagraficaAziAccent;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDsDenominazione() {
		return this.dsDenominazione;
	}

	public void setDsDenominazione(String dsDenominazione) {
		this.dsDenominazione = dsDenominazione;
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

}
