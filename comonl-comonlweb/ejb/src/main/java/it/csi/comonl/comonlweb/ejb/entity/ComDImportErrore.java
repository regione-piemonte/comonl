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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the PRO_D_IMPORT_ERRORI database table.
 * 
 */
@Entity
@Table(name="COM_D_IMPORT_ERRORE")
@NamedQuery(name="ComDImportErrore.findAll", query="SELECT p FROM ComDImportErrore p")
@SequenceGenerator(name = "ERR_SEQUENCE", sequenceName = "SEQ_COM_D_IMPORT_ERRORE", initialValue = 1, allocationSize = 1)
public class ComDImportErrore implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDImportErrore;
	}

	@Override
	public void setId(Long id) {
		idComDImportErrore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_IMPORT_ERRORE" , unique=true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERR_SEQUENCE")
	private long idComDImportErrore;

	@Column(name="COD_COMUNICAZIONE_REG")
	private String codComunicazioneReg;

	@Column(name="ID_COM_T_TIPO_TRACCIATO")
	private String idComTTipoTracciato;

	@Column(name="ID_SPI_TRASMISSIONE")
	private BigDecimal idSpiTrasmissione;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_ELABORAZIONE")
	private Date dtElaborazione;
	
	@Column(name="DS_ERRORE")
	private String dsErrore;

	@Column(name="TABELLA_DESTINAZIONE")
	private String tabellaDestinazione;
	
	@Column(name="DATO_INPUT")
	private String datoInput;


	public ComDImportErrore() {
	}

	public long getIdComDImportErrore() {
		return idComDImportErrore;
	}

	public void setIdComDImportErrore(long idComDImportErrore) {
		this.idComDImportErrore = idComDImportErrore;
	}

	public String getCodComunicazioneReg() {
		return codComunicazioneReg;
	}

	public void setCodComunicazioneReg(String codComunicazioneReg) {
		this.codComunicazioneReg = codComunicazioneReg;
	}


	public String getIdComTTipoTracciato() {
		return idComTTipoTracciato;
	}

	public void setIdComTTipoTracciato(String idComTTipoTracciato) {
		this.idComTTipoTracciato = idComTTipoTracciato;
	}

	public BigDecimal getIdSpiTrasmissione() {
		return idSpiTrasmissione;
	}

	public void setIdSpiTrasmissione(BigDecimal idSpiTrasmissione) {
		this.idSpiTrasmissione = idSpiTrasmissione;
	}

	public Date getDtElaborazione() {
		return dtElaborazione;
	}

	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}

	public String getDsErrore() {
		return dsErrore;
	}

	public void setDsErrore(String dsErrore) {
		this.dsErrore = dsErrore;
	}

	public String getTabellaDestinazione() {
		return tabellaDestinazione;
	}

	public void setTabellaDestinazione(String tabellaDestinazione) {
		this.tabellaDestinazione = tabellaDestinazione;
	}

	public String getDatoInput() {
		return datoInput;
	}

	public void setDatoInput(String datoInput) {
		this.datoInput = datoInput;
	}
}
