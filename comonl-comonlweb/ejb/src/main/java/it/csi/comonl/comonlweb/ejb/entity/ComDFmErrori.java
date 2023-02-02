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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_FM_ERRORI database table.
 * 
 */
@Entity
@Table(name="COM_D_FM_ERRORI")
@NamedQuery(name="ComDFmErrori.findAll", query="SELECT c FROM ComDFmErrori c")
public class ComDFmErrori implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDFmErrori;
	}

	@Override
	public void setId(Long id) {
		idComDFmErrori = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_FM_ERRORI")
	private long idComDFmErrori;

	@Column(name="IDENTIFICATIVO_ANNO_NUM")
	private String identificativoAnnoNum;

	@Column(name="NOME_FILE_XML_SCARTATO")
	private String nomeFileXmlScartato;

	@Column(name="TIPO_SCARTO")
	private String tipoScarto;

	//bi-directional many-to-one association to ComDUplDocumenti
	@ManyToOne
	@JoinColumn(name="ID_COM_D_UPL_DOCUMENTI")
	private ComDUplDocumenti comDUplDocumenti;

	public ComDFmErrori() {
	}

	public long getIdComDFmErrori() {
		return this.idComDFmErrori;
	}

	public void setIdComDFmErrori(long idComDFmErrori) {
		this.idComDFmErrori = idComDFmErrori;
	}

	public String getIdentificativoAnnoNum() {
		return this.identificativoAnnoNum;
	}

	public void setIdentificativoAnnoNum(String identificativoAnnoNum) {
		this.identificativoAnnoNum = identificativoAnnoNum;
	}

	public String getNomeFileXmlScartato() {
		return this.nomeFileXmlScartato;
	}

	public void setNomeFileXmlScartato(String nomeFileXmlScartato) {
		this.nomeFileXmlScartato = nomeFileXmlScartato;
	}

	public String getTipoScarto() {
		return this.tipoScarto;
	}

	public void setTipoScarto(String tipoScarto) {
		this.tipoScarto = tipoScarto;
	}

	public ComDUplDocumenti getComDUplDocumenti() {
		return this.comDUplDocumenti;
	}

	public void setComDUplDocumenti(ComDUplDocumenti comDUplDocumenti) {
		this.comDUplDocumenti = comDUplDocumenti;
	}

}
