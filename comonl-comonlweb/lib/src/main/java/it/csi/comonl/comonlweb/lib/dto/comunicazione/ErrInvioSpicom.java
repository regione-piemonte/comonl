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
 * The Class ErrInvioSpicom.
 */
public class ErrInvioSpicom extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfUtente;
	private Date dataInvio;
	private String dsErrSpicom;
	private BigDecimal idComDComunicazione;
	private String objXmlComunicazione;

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
	 * @return the dataInvio
	 */
	public Date getDataInvio() {
		return dataInvio;
	}
	
	/**
	 * @param dataInvio the dataInvio to set
	 */
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	/**
	 * @return the dsErrSpicom
	 */
	public String getDsErrSpicom() {
		return dsErrSpicom;
	}
	
	/**
	 * @param dsErrSpicom the dsErrSpicom to set
	 */
	public void setDsErrSpicom(String dsErrSpicom) {
		this.dsErrSpicom = dsErrSpicom;
	}

	/**
	 * @return the idComDComunicazione
	 */
	public BigDecimal getIdComDComunicazione() {
		return idComDComunicazione;
	}
	
	/**
	 * @param idComDComunicazione the idComDComunicazione to set
	 */
	public void setIdComDComunicazione(BigDecimal idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	/**
	 * @return the objXmlComunicazione
	 */
	public String getObjXmlComunicazione() {
		return objXmlComunicazione;
	}
	
	/**
	 * @param objXmlComunicazione the objXmlComunicazione to set
	 */
	public void setObjXmlComunicazione(String objXmlComunicazione) {
		this.objXmlComunicazione = objXmlComunicazione;
	}

}
