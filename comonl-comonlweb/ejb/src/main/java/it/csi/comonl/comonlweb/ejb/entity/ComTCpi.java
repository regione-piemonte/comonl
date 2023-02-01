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
 * The persistent class for the COM_T_CPI database table.
 * 
 */
@Entity
@Table(name="COM_T_CPI")
@NamedQuery(name="ComTCpi.findAll", query="SELECT c FROM ComTCpi c")
public class ComTCpi implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCpi;
	}

	@Override
	public void setId(Long id) {
		idComTCpi = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CPI")
	private long idComTCpi;

	@Column(name="C_UTENTE")
	private String cUtente;

	@Column(name="COD_CPI")
	private String codCpi;

	@Column(name="DS_COM_T_CPI")
	private String dsComTCpi;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTCpi")
//	private List<ComDComunicazione> comDComunicaziones;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTCpi")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComTComune
//	@OneToMany(mappedBy="comTCpi")
//	private List<ComTComune> comTComunes;

	public ComTCpi() {
	}

	public long getIdComTCpi() {
		return this.idComTCpi;
	}

	public void setIdComTCpi(long idComTCpi) {
		this.idComTCpi = idComTCpi;
	}

	public String getCUtente() {
		return this.cUtente;
	}

	public void setCUtente(String cUtente) {
		this.cUtente = cUtente;
	}

	public String getCodCpi() {
		return this.codCpi;
	}

	public void setCodCpi(String codCpi) {
		this.codCpi = codCpi;
	}

	public String getDsComTCpi() {
		return this.dsComTCpi;
	}

	public void setDsComTCpi(String dsComTCpi) {
		this.dsComTCpi = dsComTCpi;
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
//		comDComunicazione.setComTCpi(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTCpi(null);
//
//		return comDComunicazione;
//	}

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
//		comDRapporto.setComTCpi(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTCpi(null);
//
//		return comDRapporto;
//	}

//	public List<ComTComune> getComTComunes() {
//		return this.comTComunes;
//	}
//
//	public void setComTComunes(List<ComTComune> comTComunes) {
//		this.comTComunes = comTComunes;
//	}
//
//	public ComTComune addComTComune(ComTComune comTComune) {
//		getComTComunes().add(comTComune);
//		comTComune.setComTCpi(this);
//
//		return comTComune;
//	}
//
//	public ComTComune removeComTComune(ComTComune comTComune) {
//		getComTComunes().remove(comTComune);
//		comTComune.setComTCpi(null);
//
//		return comTComune;
//	}

}
