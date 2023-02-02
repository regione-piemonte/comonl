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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the DEPOSITO_COMMAX database table.
 * 
 */
@Entity
@Table(name="DEPOSITO_COMMAX")
@NamedQuery(name="DepositoCommax.findAll", query="SELECT d FROM EntityDepositoCommax d")
	public class EntityDepositoCommax implements Serializable, BaseEntity<EntityDepositoCommaxPK> {
	@Override
	public EntityDepositoCommaxPK getId() {
		return id;
	}

	@Override
	public void setId(EntityDepositoCommaxPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntityDepositoCommaxPK id;

	@Lob
	@Column(name="B_FILE_COMMAX")
	private byte[] bFileCommax;

	@Lob
	@Column(name="B_FILE_PROC_COMMAX")
	private byte[] bFileProcCommax;

	@Column(name="CF_UTENTE")
	private String cfUtente;

	@Column(name="CODICE_COMUNICAZIONE_REG")
	private String codiceComunicazioneReg;

	@Column(name="CRC_XML")
	private BigDecimal crcXml;

	@Temporal(TemporalType.DATE)
	@Column(name="D_INSERIM")
	private Date dInserim;

	@Temporal(TemporalType.DATE)
	@Column(name="D_TRATTAMENTO")
	private Date dTrattamento;

	@Column(name="DESC_UTENTE")
	private String descUtente;

	@Column(name="EMAIL_UTENTE")
	private String emailUtente;

	@Column(name="ID_COM_D_UPL_DOCUMENTI")
	private BigDecimal idComDUplDocumenti;

	@Column(name="NOME_FILE_XML_SINGOLO")
	private String nomeFileXmlSingolo;

	@Column(name="SEQ_ELAB_COMMAX")
	private BigDecimal seqElabCommax;

	private String stato;

	@Column(name="STATO_INVIO")
	private String statoInvio;

	public EntityDepositoCommax() {
	}

	
	
	public byte[] getbFileCommax() {
		return bFileCommax;
	}

	public void setbFileCommax(byte[] bFileCommax) {
		this.bFileCommax = bFileCommax;
	}

	public byte[] getbFileProcCommax() {
		return bFileProcCommax;
	}

	public void setbFileProcCommax(byte[] bFileProcCommax) {
		this.bFileProcCommax = bFileProcCommax;
	}

	public Date getdInserim() {
		return dInserim;
	}

	public void setdInserim(Date dInserim) {
		this.dInserim = dInserim;
	}

	public Date getdTrattamento() {
		return dTrattamento;
	}

	public void setdTrattamento(Date dTrattamento) {
		this.dTrattamento = dTrattamento;
	}

	public byte[] getBFileCommax() {
		return this.bFileCommax;
	}

	public void setBFileCommax(byte[] bFileCommax) {
		this.bFileCommax = bFileCommax;
	}

	public byte[] getBFileProcCommax() {
		return this.bFileProcCommax;
	}

	public void setBFileProcCommax(byte[] bFileProcCommax) {
		this.bFileProcCommax = bFileProcCommax;
	}

	public String getCfUtente() {
		return this.cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public BigDecimal getCrcXml() {
		return this.crcXml;
	}

	public void setCrcXml(BigDecimal crcXml) {
		this.crcXml = crcXml;
	}

	public Date getDInserim() {
		return this.dInserim;
	}

	public void setDInserim(Date dInserim) {
		this.dInserim = dInserim;
	}

	public Date getDTrattamento() {
		return this.dTrattamento;
	}

	public void setDTrattamento(Date dTrattamento) {
		this.dTrattamento = dTrattamento;
	}

	public String getDescUtente() {
		return this.descUtente;
	}

	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
	}

	public String getEmailUtente() {
		return this.emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public BigDecimal getIdComDUplDocumenti() {
		return this.idComDUplDocumenti;
	}

	public void setIdComDUplDocumenti(BigDecimal idComDUplDocumenti) {
		this.idComDUplDocumenti = idComDUplDocumenti;
	}

	public String getNomeFileXmlSingolo() {
		return this.nomeFileXmlSingolo;
	}

	public void setNomeFileXmlSingolo(String nomeFileXmlSingolo) {
		this.nomeFileXmlSingolo = nomeFileXmlSingolo;
	}

	public BigDecimal getSeqElabCommax() {
		return this.seqElabCommax;
	}

	public void setSeqElabCommax(BigDecimal seqElabCommax) {
		this.seqElabCommax = seqElabCommax;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStatoInvio() {
		return this.statoInvio;
	}

	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}

}
