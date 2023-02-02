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
import java.math.BigDecimal;
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
 * The persistent class for the COM_T_LIVELLO_RETRIBUZIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_LIVELLO_RETRIBUZIONE")
@NamedQuery(name="ComTLivelloRetribuzione.findAll", query="SELECT c FROM ComTLivelloRetribuzione c")
public class ComTLivelloRetribuzione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTLivelloRetribuzione;
	}

	@Override
	public void setId(Long id) {
		idComTLivelloRetribuzione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_LIVELLO_RETRIBUZIONE")
	private Long idComTLivelloRetribuzione;

	@Column(name="COD_LIVELLO")
	private String codLivello;

	@Column(name="DES_LIVELLO")
	private String desLivello;

	@Column(name="DIVISORE_ORARIO")
	private BigDecimal divisoreOrario;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Column(name="LORDO_MENSILE")
	private BigDecimal lordoMensile;

	private BigDecimal mensilita;

	private BigDecimal ordinamento;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTLivelloRetribuzione")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComTCcnl
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CCNL")
	private ComTCcnl comTCcnl;

	//bi-directional many-to-one association to ComTTipoLivelloRetrib
	@ManyToOne
	@JoinColumn(name="COD_TIPO_LIVELLO")
	private ComTTipoLivelloRetrib comTTipoLivelloRetrib;

	public ComTLivelloRetribuzione() {
	}

	public Long getIdComTLivelloRetribuzione() {
		return this.idComTLivelloRetribuzione;
	}

	public void setIdComTLivelloRetribuzione(Long idComTLivelloRetribuzione) {
		this.idComTLivelloRetribuzione = idComTLivelloRetribuzione;
	}

	public String getCodLivello() {
		return this.codLivello;
	}

	public void setCodLivello(String codLivello) {
		this.codLivello = codLivello;
	}

	public String getDesLivello() {
		return this.desLivello;
	}

	public void setDesLivello(String desLivello) {
		this.desLivello = desLivello;
	}

	public BigDecimal getDivisoreOrario() {
		return this.divisoreOrario;
	}

	public void setDivisoreOrario(BigDecimal divisoreOrario) {
		this.divisoreOrario = divisoreOrario;
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

	public BigDecimal getLordoMensile() {
		return this.lordoMensile;
	}

	public void setLordoMensile(BigDecimal lordoMensile) {
		this.lordoMensile = lordoMensile;
	}

	public BigDecimal getMensilita() {
		return this.mensilita;
	}

	public void setMensilita(BigDecimal mensilita) {
		this.mensilita = mensilita;
	}

	public BigDecimal getOrdinamento() {
		return this.ordinamento;
	}

	public void setOrdinamento(BigDecimal ordinamento) {
		this.ordinamento = ordinamento;
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
//		comDRapporto.setComTLivelloRetribuzione(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTLivelloRetribuzione(null);
//
//		return comDRapporto;
//	}

	public ComTCcnl getComTCcnl() {
		return this.comTCcnl;
	}

	public void setComTCcnl(ComTCcnl comTCcnl) {
		this.comTCcnl = comTCcnl;
	}

	public ComTTipoLivelloRetrib getComTTipoLivelloRetrib() {
		return this.comTTipoLivelloRetrib;
	}

	public void setComTTipoLivelloRetrib(ComTTipoLivelloRetrib comTTipoLivelloRetrib) {
		this.comTTipoLivelloRetrib = comTTipoLivelloRetrib;
	}

}
