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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_ELABORATI_OK database table.
 * 
 */
@Entity
@Table(name="COM_D_ELABORATI_OK")
@NamedQuery(name="ComDElaboratiOk.findAll", query="SELECT c FROM ComDElaboratiOk c")
public class ComDElaboratiOk implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return progCom;
	}

	@Override
	public void setId(Long id) {
		progCom = id;
	}


	private static final long serialVersionUID = 1L;


	@Column(name="ANNO_PROT_COM")
	private BigDecimal annoProtCom;

	@Column(name="CF_IMPRESA")
	private String cfImpresa;

	@Column(name="CF_LAVORATORE")
	private String cfLavoratore;

	@Column(name="CODICE_COMUNICAZIONE_REG")
	private String codiceComunicazioneReg;

	@Column(name="F_TIPO_ACQUISIZ")
	private BigDecimal fTipoAcquisiz;

	@Column(name="NOME_FILE_XML")
	private String nomeFileXml;

	@Column(name="NRO_PROT_COM")
	private String nroProtCom;

	// TODO - messo questo campo come "Id" ma non sembra univoco: da verificare
	@Id
	@Column(name="PROG_COM")
	private Long progCom;

	@Column(name="PV")
	private String pv;

	@Column(name="TIPO_COM")
	private String tipoCom;

	//bi-directional many-to-one association to ComDUplDocumenti
	@ManyToOne
	@JoinColumn(name="ID_COM_D_UPL_DOCUMENTI")
	private ComDUplDocumenti comDUplDocumenti;

	public ComDElaboratiOk() {
	}

	public BigDecimal getAnnoProtCom() {
		return this.annoProtCom;
	}

	public void setAnnoProtCom(BigDecimal annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	public String getCfImpresa() {
		return this.cfImpresa;
	}

	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	public String getCfLavoratore() {
		return this.cfLavoratore;
	}

	public void setCfLavoratore(String cfLavoratore) {
		this.cfLavoratore = cfLavoratore;
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public BigDecimal getFTipoAcquisiz() {
		return this.fTipoAcquisiz;
	}

	public void setFTipoAcquisiz(BigDecimal fTipoAcquisiz) {
		this.fTipoAcquisiz = fTipoAcquisiz;
	}

	public String getNomeFileXml() {
		return this.nomeFileXml;
	}

	public void setNomeFileXml(String nomeFileXml) {
		this.nomeFileXml = nomeFileXml;
	}

	public String getNroProtCom() {
		return this.nroProtCom;
	}

	public void setNroProtCom(String nroProtCom) {
		this.nroProtCom = nroProtCom;
	}

	public Long getProgCom() {
		return this.progCom;
	}

	public void setProgCom(Long progCom) {
		this.progCom = progCom;
	}

	public String getPv() {
		return this.pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getTipoCom() {
		return this.tipoCom;
	}

	public void setTipoCom(String tipoCom) {
		this.tipoCom = tipoCom;
	}

	public ComDUplDocumenti getComDUplDocumenti() {
		return this.comDUplDocumenti;
	}

	public void setComDUplDocumenti(ComDUplDocumenti comDUplDocumenti) {
		this.comDUplDocumenti = comDUplDocumenti;
	}

}
