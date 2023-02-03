/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class PdfComunicazione.
 */
public class PdfComunicazione extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dtInsert;
	private Date dtTimeMarca;
	private Date dtUltMod;
	private String idUserInsert;
	private String idUserUltMod;
	private byte[] pdf;
	private byte[] pdfNonFirmato;
	private Comunicazione comunicazione;

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}
	
	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	/**
	 * @return the dtTimeMarca
	 */
	public Date getDtTimeMarca() {
		return dtTimeMarca;
	}
	
	/**
	 * @param dtTimeMarca the dtTimeMarca to set
	 */
	public void setDtTimeMarca(Date dtTimeMarca) {
		this.dtTimeMarca = dtTimeMarca;
	}

	/**
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}
	
	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}
	
	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}
	
	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the pdf
	 */
	public byte[] getPdf() {
		return pdf;
	}
	
	/**
	 * @param pdf the pdf to set
	 */
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	/**
	 * @return the pdfNonFirmato
	 */
	public byte[] getPdfNonFirmato() {
		return pdfNonFirmato;
	}
	
	/**
	 * @param pdfNonFirmato the pdfNonFirmato to set
	 */
	public void setPdfNonFirmato(byte[] pdfNonFirmato) {
		this.pdfNonFirmato = pdfNonFirmato;
	}

	/**
	 * @return the comunicazione
	 */
	public Comunicazione getComunicazione() {
		return comunicazione;
	}
	
	/**
	 * @param comunicazione the comunicazione to set
	 */
	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}

}
