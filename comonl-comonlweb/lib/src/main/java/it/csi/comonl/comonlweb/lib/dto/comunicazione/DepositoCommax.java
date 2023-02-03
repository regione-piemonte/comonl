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
import java.math.BigDecimal;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class DepositoCommax.
 */
public class DepositoCommax extends BaseDto<DepositoCommaxPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private byte[] bFileCommax;
	private byte[] bFileProcCommax;
	private String cfUtente;
	private String codiceComunicazioneReg;
	private BigDecimal crcXml;
	private Date dInserim;
	private Date dTrattamento;
	private String descUtente;
	private String emailUtente;
	private BigDecimal idComDUplDocumenti;
	private String nomeFileXmlSingolo;
	private BigDecimal seqElabCommax;
	private String stato;
	private String statoInvio;

	/**
	 * @return the bFileCommax
	 */
	public byte[] getBFileCommax() {
		return bFileCommax;
	}
	
	/**
	 * @param bFileCommax the bFileCommax to set
	 */
	public void setBFileCommax(byte[] bFileCommax) {
		this.bFileCommax = bFileCommax;
	}

	/**
	 * @return the bFileProcCommax
	 */
	public byte[] getBFileProcCommax() {
		return bFileProcCommax;
	}
	
	/**
	 * @param bFileProcCommax the bFileProcCommax to set
	 */
	public void setBFileProcCommax(byte[] bFileProcCommax) {
		this.bFileProcCommax = bFileProcCommax;
	}

	/**
	 * @return the cfUtente
	 */
	public String getCfUtente() {
		return cfUtente;
	}
	
	/**
	 * @param cfUtente the cfUtente to set
	 */
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	/**
	 * @return the codiceComunicazioneReg
	 */
	public String getCodiceComunicazioneReg() {
		return codiceComunicazioneReg;
	}
	
	/**
	 * @param codiceComunicazioneReg the codiceComunicazioneReg to set
	 */
	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	/**
	 * @return the crcXml
	 */
	public BigDecimal getCrcXml() {
		return crcXml;
	}
	
	/**
	 * @param crcXml the crcXml to set
	 */
	public void setCrcXml(BigDecimal crcXml) {
		this.crcXml = crcXml;
	}

	/**
	 * @return the dInserim
	 */
	public Date getDInserim() {
		return dInserim;
	}
	
	/**
	 * @param dInserim the dInserim to set
	 */
	public void setDInserim(Date dInserim) {
		this.dInserim = dInserim;
	}

	/**
	 * @return the dTrattamento
	 */
	public Date getDTrattamento() {
		return dTrattamento;
	}
	
	/**
	 * @param dTrattamento the dTrattamento to set
	 */
	public void setDTrattamento(Date dTrattamento) {
		this.dTrattamento = dTrattamento;
	}

	/**
	 * @return the descUtente
	 */
	public String getDescUtente() {
		return descUtente;
	}
	
	/**
	 * @param descUtente the descUtente to set
	 */
	public void setDescUtente(String descUtente) {
		this.descUtente = descUtente;
	}

	/**
	 * @return the emailUtente
	 */
	public String getEmailUtente() {
		return emailUtente;
	}
	
	/**
	 * @param emailUtente the emailUtente to set
	 */
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	/**
	 * @return the idComDUplDocumenti
	 */
	public BigDecimal getIdComDUplDocumenti() {
		return idComDUplDocumenti;
	}
	
	/**
	 * @param idComDUplDocumenti the idComDUplDocumenti to set
	 */
	public void setIdComDUplDocumenti(BigDecimal idComDUplDocumenti) {
		this.idComDUplDocumenti = idComDUplDocumenti;
	}

	/**
	 * @return the nomeFileXmlSingolo
	 */
	public String getNomeFileXmlSingolo() {
		return nomeFileXmlSingolo;
	}
	
	/**
	 * @param nomeFileXmlSingolo the nomeFileXmlSingolo to set
	 */
	public void setNomeFileXmlSingolo(String nomeFileXmlSingolo) {
		this.nomeFileXmlSingolo = nomeFileXmlSingolo;
	}

	/**
	 * @return the seqElabCommax
	 */
	public BigDecimal getSeqElabCommax() {
		return seqElabCommax;
	}
	
	/**
	 * @param seqElabCommax the seqElabCommax to set
	 */
	public void setSeqElabCommax(BigDecimal seqElabCommax) {
		this.seqElabCommax = seqElabCommax;
	}

	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}
	
	/**
	 * @param stato the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * @return the statoInvio
	 */
	public String getStatoInvio() {
		return statoInvio;
	}
	
	/**
	 * @param statoInvio the statoInvio to set
	 */
	public void setStatoInvio(String statoInvio) {
		this.statoInvio = statoInvio;
	}

}
