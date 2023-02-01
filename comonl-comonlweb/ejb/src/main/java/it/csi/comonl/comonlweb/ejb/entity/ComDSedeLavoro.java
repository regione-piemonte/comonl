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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_SEDE_LAVORO database table.
 * 
 */
@Entity
@Table(name="COM_D_SEDE_LAVORO")
@NamedQuery(name="ComDSedeLavoro.findAll", query="SELECT c FROM ComDSedeLavoro c")
@SequenceGenerator(name = "SEQUENCE_SEDE_LAVORO", sequenceName = "SEQ_ID_COM_D_SEDE_LAVORO", initialValue = 1, allocationSize = 1)
public class ComDSedeLavoro implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDSedeLavoro;
	}

	@Override
	public void setId(Long id) {
		idComDSedeLavoro = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_SEDE_LAVORO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SEDE_LAVORO")
	private Long idComDSedeLavoro;

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

	@Column(name="FLG_COMUNE_SILP_VARIATO")
	private String flgComuneSilpVariato;

	@Column(name="FLG_INDIRIZZO_SILP_VARIATO")
	private String flgIndirizzoSilpVariato;

	@Column(name="FLG_SEDE_LEGALE")
	private String flgSedeLegale;

	@Column(name="ID_SEDE_SILP")
	private BigDecimal idSedeSilp;

	private String indirizzo;

	private String telefono;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comDSedeLavoro")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE")
	private ComTComune comTComune;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI")
	private ComTStatiEsteri comTStatiEsteri;

	//bi-directional many-to-many association to ComDDatore
//	@ManyToMany
//	@JoinTable(
//		name="COM_R_DATORE_SEDE"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_D_SEDE_LAVORO")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_D_DATORE")
//			}
//		)
//	private List<ComDDatore> comDDatores;

	//bi-directional many-to-many association to ComDLavoratore
//	@ManyToMany
//	@JoinTable(
//		name="COM_R_SEDE_LAVORO_LAVORATORE"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_D_SEDE_LAVORO")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_D_LAVORATORE")
//			}
//		)
//	private List<ComDLavoratore> comDLavoratores;

	public ComDSedeLavoro() {
	}

	public long getIdComDSedeLavoro() {
		return this.idComDSedeLavoro;
	}

	public void setIdComDSedeLavoro(long idComDSedeLavoro) {
		this.idComDSedeLavoro = idComDSedeLavoro;
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

	public String getFlgComuneSilpVariato() {
		return this.flgComuneSilpVariato;
	}

	public void setFlgComuneSilpVariato(String flgComuneSilpVariato) {
		this.flgComuneSilpVariato = flgComuneSilpVariato;
	}

	public String getFlgIndirizzoSilpVariato() {
		return this.flgIndirizzoSilpVariato;
	}

	public void setFlgIndirizzoSilpVariato(String flgIndirizzoSilpVariato) {
		this.flgIndirizzoSilpVariato = flgIndirizzoSilpVariato;
	}

	public String getFlgSedeLegale() {
		return this.flgSedeLegale;
	}

	public void setFlgSedeLegale(String flgSedeLegale) {
		this.flgSedeLegale = flgSedeLegale;
	}

	public BigDecimal getIdSedeSilp() {
		return this.idSedeSilp;
	}

	public void setIdSedeSilp(BigDecimal idSedeSilp) {
		this.idSedeSilp = idSedeSilp;
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
//		comDRapporto.setComDSedeLavoro(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComDSedeLavoro(null);
//
//		return comDRapporto;
//	}

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

//	public List<ComDDatore> getComDDatores() {
//		return this.comDDatores;
//	}
//
//	public void setComDDatores(List<ComDDatore> comDDatores) {
//		this.comDDatores = comDDatores;
//	}
//
//	public List<ComDLavoratore> getComDLavoratores() {
//		return this.comDLavoratores;
//	}
//
//	public void setComDLavoratores(List<ComDLavoratore> comDLavoratores) {
//		this.comDLavoratores = comDLavoratores;
//	}

}
