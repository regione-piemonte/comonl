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
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_ERR_INVIO_SPICOM database table.
 * 
 */
@Entity
@Table(name="COM_D_ERR_INVIO_SPICOM")
@NamedQuery(name="ComDErrInvioSpicom.findAll", query="SELECT c FROM ComDErrInvioSpicom c")
public class ComDErrInvioSpicom implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDErrInvioSpicom;
	}

	@Override
	public void setId(Long id) {
		idComDErrInvioSpicom = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_ERR_INVIO_SPICOM")
	private long idComDErrInvioSpicom;

	@Column(name="CF_UTENTE")
	private String cfUtente;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INVIO")
	private Date dataInvio;

	@Lob
	@Column(name="DS_ERR_SPICOM")
	private String dsErrSpicom;

	@Column(name="ID_COM_D_COMUNICAZIONE")
	private BigDecimal idComDComunicazione;

	@Lob
	@Column(name="OBJ_XML_COMUNICAZIONE")
	private String objXmlComunicazione;

	public ComDErrInvioSpicom() {
	}

	public long getIdComDErrInvioSpicom() {
		return this.idComDErrInvioSpicom;
	}

	public void setIdComDErrInvioSpicom(long idComDErrInvioSpicom) {
		this.idComDErrInvioSpicom = idComDErrInvioSpicom;
	}

	public String getCfUtente() {
		return this.cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public Date getDataInvio() {
		return this.dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getDsErrSpicom() {
		return this.dsErrSpicom;
	}

	public void setDsErrSpicom(String dsErrSpicom) {
		this.dsErrSpicom = dsErrSpicom;
	}

	public BigDecimal getIdComDComunicazione() {
		return this.idComDComunicazione;
	}

	public void setIdComDComunicazione(BigDecimal idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public String getObjXmlComunicazione() {
		return this.objXmlComunicazione;
	}

	public void setObjXmlComunicazione(String objXmlComunicazione) {
		this.objXmlComunicazione = objXmlComunicazione;
	}

}
