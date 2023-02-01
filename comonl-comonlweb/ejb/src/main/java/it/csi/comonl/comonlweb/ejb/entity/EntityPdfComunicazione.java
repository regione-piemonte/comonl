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
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the PDF_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="PDF_COMUNICAZIONE")
@NamedQuery(name="PdfComunicazione.findAll", query="SELECT p FROM EntityPdfComunicazione p")
public class EntityPdfComunicazione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idPdfComunicazione;
	}

	@Override
	public void setId(Long id) {
		idPdfComunicazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PDF_COMUNICAZIONE")
	private long idPdfComunicazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INSERT")
	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TIME_MARCA")
	private Date dtTimeMarca;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ULT_MOD")
	private Date dtUltMod;

	@Column(name="ID_USER_INSERT")
	private String idUserInsert;

	@Column(name="ID_USER_ULT_MOD")
	private String idUserUltMod;

	@Lob
	private byte[] pdf;

	@Lob
	@Column(name="PDF_NON_FIRMATO")
	private byte[] pdfNonFirmato;

	//bi-directional one-to-one association to ComDComunicazione
	@OneToOne
	@JoinColumn(name="ID_PDF_COMUNICAZIONE")
	private ComDComunicazione comDComunicazione;

	public EntityPdfComunicazione() {
	}

	public long getIdPdfComunicazione() {
		return this.idPdfComunicazione;
	}

	public void setIdPdfComunicazione(long idPdfComunicazione) {
		this.idPdfComunicazione = idPdfComunicazione;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtTimeMarca() {
		return this.dtTimeMarca;
	}

	public void setDtTimeMarca(Date dtTimeMarca) {
		this.dtTimeMarca = dtTimeMarca;
	}

	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	public String getIdUserInsert() {
		return this.idUserInsert;
	}

	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	public String getIdUserUltMod() {
		return this.idUserUltMod;
	}

	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	public byte[] getPdf() {
		return this.pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public byte[] getPdfNonFirmato() {
		return this.pdfNonFirmato;
	}

	public void setPdfNonFirmato(byte[] pdfNonFirmato) {
		this.pdfNonFirmato = pdfNonFirmato;
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

}
