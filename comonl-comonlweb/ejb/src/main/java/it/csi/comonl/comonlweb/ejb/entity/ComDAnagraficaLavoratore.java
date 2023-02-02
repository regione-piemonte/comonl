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
 * The persistent class for the COM_D_ANAGRAFICA_LAVORATORE database table.
 * 
 */
@Entity
@Table(name="COM_D_ANAGRAFICA_LAVORATORE")
@NamedQuery(name="ComDAnagraficaLavoratore.findAll", query="SELECT c FROM ComDAnagraficaLavoratore c")
public class ComDAnagraficaLavoratore implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDAnagraficaLavoratore;
	}

	@Override
	public void setId(Long id) {
		idComDAnagraficaLavoratore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_ANAGRAFICA_LAVORATORE")
	private long idComDAnagraficaLavoratore;

	@Column(name="COD_CAP_DOM")
	private String codCapDom;

	@Column(name="COD_CAP_RES")
	private String codCapRes;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	private String cognome;

	@Column(name="DS_INDIRIZZO_DOM")
	private String dsIndirizzoDom;

	@Column(name="DS_INDIRIZZO_RES")
	private String dsIndirizzoRes;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_SCADENZA_PERMESSO_SOGG")
	private Date dtScadenzaPermessoSogg;

	private String nome;

	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;

	private String sesso;

	@Column(name="STATO_CIVILE")
	private String statoCivile;

	@Column(name="TEL_DOM")
	private String telDom;

	//bi-directional many-to-one association to ComTCittadinanza
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CITTADINANZA")
	private ComTCittadinanza comTCittadinanza;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_DOM")
	private ComTComune comTComune1;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_RES")
	private ComTComune comTComune2;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_NASC")
	private ComTComune comTComune3;

	//bi-directional many-to-one association to ComTLivelloStudio
	@ManyToOne
	@JoinColumn(name="ID_COM_T_LIVELLO_STUDIO")
	private ComTLivelloStudio comTLivelloStudio;

	//bi-directional many-to-one association to ComTMotivoPermesso
	@ManyToOne
	@JoinColumn(name="ID_COM_T_MOTIVO_PERMESSO")
	private ComTMotivoPermesso comTMotivoPermesso;

	//bi-directional many-to-one association to ComTQuestura
	@ManyToOne
	@JoinColumn(name="ID_QUESTURA_RILASCIO_PERM_SOGG")
	private ComTQuestura comTQuestura;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_NASC")
	private ComTStatiEsteri comTStatiEsteri1;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_DOM")
	private ComTStatiEsteri comTStatiEsteri2;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_RES")
	private ComTStatiEsteri comTStatiEsteri3;

	//bi-directional many-to-one association to ComTStatusStraniero
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATUS_STRANIERO")
	private ComTStatusStraniero comTStatusStraniero;

	public ComDAnagraficaLavoratore() {
	}

	public long getIdComDAnagraficaLavoratore() {
		return this.idComDAnagraficaLavoratore;
	}

	public void setIdComDAnagraficaLavoratore(long idComDAnagraficaLavoratore) {
		this.idComDAnagraficaLavoratore = idComDAnagraficaLavoratore;
	}

	public String getCodCapDom() {
		return this.codCapDom;
	}

	public void setCodCapDom(String codCapDom) {
		this.codCapDom = codCapDom;
	}

	public String getCodCapRes() {
		return this.codCapRes;
	}

	public void setCodCapRes(String codCapRes) {
		this.codCapRes = codCapRes;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDsIndirizzoDom() {
		return this.dsIndirizzoDom;
	}

	public void setDsIndirizzoDom(String dsIndirizzoDom) {
		this.dsIndirizzoDom = dsIndirizzoDom;
	}

	public String getDsIndirizzoRes() {
		return this.dsIndirizzoRes;
	}

	public void setDsIndirizzoRes(String dsIndirizzoRes) {
		this.dsIndirizzoRes = dsIndirizzoRes;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtScadenzaPermessoSogg() {
		return this.dtScadenzaPermessoSogg;
	}

	public void setDtScadenzaPermessoSogg(Date dtScadenzaPermessoSogg) {
		this.dtScadenzaPermessoSogg = dtScadenzaPermessoSogg;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return this.statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getTelDom() {
		return this.telDom;
	}

	public void setTelDom(String telDom) {
		this.telDom = telDom;
	}

	public ComTCittadinanza getComTCittadinanza() {
		return this.comTCittadinanza;
	}

	public void setComTCittadinanza(ComTCittadinanza comTCittadinanza) {
		this.comTCittadinanza = comTCittadinanza;
	}

	public ComTComune getComTComune1() {
		return this.comTComune1;
	}

	public void setComTComune1(ComTComune comTComune1) {
		this.comTComune1 = comTComune1;
	}

	public ComTComune getComTComune2() {
		return this.comTComune2;
	}

	public void setComTComune2(ComTComune comTComune2) {
		this.comTComune2 = comTComune2;
	}

	public ComTComune getComTComune3() {
		return this.comTComune3;
	}

	public void setComTComune3(ComTComune comTComune3) {
		this.comTComune3 = comTComune3;
	}

	public ComTLivelloStudio getComTLivelloStudio() {
		return this.comTLivelloStudio;
	}

	public void setComTLivelloStudio(ComTLivelloStudio comTLivelloStudio) {
		this.comTLivelloStudio = comTLivelloStudio;
	}

	public ComTMotivoPermesso getComTMotivoPermesso() {
		return this.comTMotivoPermesso;
	}

	public void setComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
		this.comTMotivoPermesso = comTMotivoPermesso;
	}

	public ComTQuestura getComTQuestura() {
		return this.comTQuestura;
	}

	public void setComTQuestura(ComTQuestura comTQuestura) {
		this.comTQuestura = comTQuestura;
	}

	public ComTStatiEsteri getComTStatiEsteri1() {
		return this.comTStatiEsteri1;
	}

	public void setComTStatiEsteri1(ComTStatiEsteri comTStatiEsteri1) {
		this.comTStatiEsteri1 = comTStatiEsteri1;
	}

	public ComTStatiEsteri getComTStatiEsteri2() {
		return this.comTStatiEsteri2;
	}

	public void setComTStatiEsteri2(ComTStatiEsteri comTStatiEsteri2) {
		this.comTStatiEsteri2 = comTStatiEsteri2;
	}

	public ComTStatiEsteri getComTStatiEsteri3() {
		return this.comTStatiEsteri3;
	}

	public void setComTStatiEsteri3(ComTStatiEsteri comTStatiEsteri3) {
		this.comTStatiEsteri3 = comTStatiEsteri3;
	}

	public ComTStatusStraniero getComTStatusStraniero() {
		return this.comTStatusStraniero;
	}

	public void setComTStatusStraniero(ComTStatusStraniero comTStatusStraniero) {
		this.comTStatusStraniero = comTStatusStraniero;
	}

}
