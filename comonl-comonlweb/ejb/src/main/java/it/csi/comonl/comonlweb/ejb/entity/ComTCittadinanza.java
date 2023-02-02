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
 * The persistent class for the COM_T_CITTADINANZA database table.
 * 
 */
@Entity
@Table(name="COM_T_CITTADINANZA")
@NamedQuery(name="ComTCittadinanza.findAll", query="SELECT c FROM ComTCittadinanza c")
public class ComTCittadinanza implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCittadinanza;
	}

	@Override
	public void setId(Long id) {
		idComTCittadinanza = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CITTADINANZA")
	private long idComTCittadinanza;

	@Column(name="COD_CITTADINANZA_MIN")
	private String codCittadinanzaMin;

	@Column(name="COD_CITTADINANZA_OLD")
	private String codCittadinanzaOld;

	@Column(name="COD_MF")
	private String codMf;

	@Column(name="DS_COM_T_CITTADINANZA")
	private String dsComTCittadinanza;

	@Column(name="DS_NAZIONE")
	private String dsNazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="FLG_UE")
	private String flgUe;

	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTCittadinanza")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores;

	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTCittadinanza")
//	private List<ComDLavoratore> comDLavoratores;

	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTCittadinanza")
//	private List<ComDLegaleRappr> comDLegaleRapprs;

	public ComTCittadinanza() {
	}

	public long getIdComTCittadinanza() {
		return this.idComTCittadinanza;
	}

	public void setIdComTCittadinanza(long idComTCittadinanza) {
		this.idComTCittadinanza = idComTCittadinanza;
	}

	public String getCodCittadinanzaMin() {
		return this.codCittadinanzaMin;
	}

	public void setCodCittadinanzaMin(String codCittadinanzaMin) {
		this.codCittadinanzaMin = codCittadinanzaMin;
	}

	public String getCodCittadinanzaOld() {
		return this.codCittadinanzaOld;
	}

	public void setCodCittadinanzaOld(String codCittadinanzaOld) {
		this.codCittadinanzaOld = codCittadinanzaOld;
	}

	public String getCodMf() {
		return this.codMf;
	}

	public void setCodMf(String codMf) {
		this.codMf = codMf;
	}

	public String getDsComTCittadinanza() {
		return this.dsComTCittadinanza;
	}

	public void setDsComTCittadinanza(String dsComTCittadinanza) {
		this.dsComTCittadinanza = dsComTCittadinanza;
	}

	public String getDsNazione() {
		return this.dsNazione;
	}

	public void setDsNazione(String dsNazione) {
		this.dsNazione = dsNazione;
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

	public String getFlgUe() {
		return this.flgUe;
	}

	public void setFlgUe(String flgUe) {
		this.flgUe = flgUe;
	}

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores() {
//		return this.comDAnagraficaLavoratores;
//	}
//
//	public void setComDAnagraficaLavoratores(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores) {
//		this.comDAnagraficaLavoratores = comDAnagraficaLavoratores;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().add(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTCittadinanza(this);
//
//		return comDAnagraficaLavoratore;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().remove(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTCittadinanza(null);
//
//		return comDAnagraficaLavoratore;
//	}

//	public List<ComDLavoratore> getComDLavoratores() {
//		return this.comDLavoratores;
//	}
//
//	public void setComDLavoratores(List<ComDLavoratore> comDLavoratores) {
//		this.comDLavoratores = comDLavoratores;
//	}
//
//	public ComDLavoratore addComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().add(comDLavoratore);
//		comDLavoratore.setComTCittadinanza(this);
//
//		return comDLavoratore;
//	}
//
//	public ComDLavoratore removeComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().remove(comDLavoratore);
//		comDLavoratore.setComTCittadinanza(null);
//
//		return comDLavoratore;
//	}

//	public List<ComDLegaleRappr> getComDLegaleRapprs() {
//		return this.comDLegaleRapprs;
//	}
//
//	public void setComDLegaleRapprs(List<ComDLegaleRappr> comDLegaleRapprs) {
//		this.comDLegaleRapprs = comDLegaleRapprs;
//	}
//
//	public ComDLegaleRappr addComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().add(comDLegaleRappr);
//		comDLegaleRappr.setComTCittadinanza(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTCittadinanza(null);
//
//		return comDLegaleRappr;
//	}

}
