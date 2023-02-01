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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_FZ_ERRORI database table.
 * 
 */
@Entity
@Table(name="COM_D_FZ_ERRORI")
@NamedQuery(name="ComDFzErrori.findAll", query="SELECT c FROM ComDFzErrori c")
public class ComDFzErrori implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDFzErrori;
	}

	@Override
	public void setId(Long id) {
		idComDFzErrori = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seqIdErrori", sequenceName = "SEQ_ID_COM_D_FZ_ERRORI", allocationSize = 1)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdErrori")
	@Column(name="ID_COM_D_FZ_ERRORI")
	private Long idComDFzErrori;

	@Column(name="CF_AZIENDA_CF_LAVORATORE")
	private String cfAziendaCfLavoratore;

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

	//bi-directional many-to-one association to ComRTListaFzErrori
//	@OneToMany(mappedBy="comDFzErrori")
//	private List<ComRTListaFzErrori> comRTListaFzErroris;

	public ComDFzErrori() {
	}

	public long getIdComDFzErrori() {
		return this.idComDFzErrori;
	}

	public void setIdComDFzErrori(long idComDFzErrori) {
		this.idComDFzErrori = idComDFzErrori;
	}

	public String getCfAziendaCfLavoratore() {
		return this.cfAziendaCfLavoratore;
	}

	public void setCfAziendaCfLavoratore(String cfAziendaCfLavoratore) {
		this.cfAziendaCfLavoratore = cfAziendaCfLavoratore;
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

//	public List<ComRTListaFzErrori> getComRTListaFzErroris() {
//		return this.comRTListaFzErroris;
//	}
//
//	public void setComRTListaFzErroris(List<ComRTListaFzErrori> comRTListaFzErroris) {
//		this.comRTListaFzErroris = comRTListaFzErroris;
//	}
//
//	public ComRTListaFzErrori addComRTListaFzErrori(ComRTListaFzErrori comRTListaFzErrori) {
//		getComRTListaFzErroris().add(comRTListaFzErrori);
//		comRTListaFzErrori.setComDFzErrori(this);
//
//		return comRTListaFzErrori;
//	}
//
//	public ComRTListaFzErrori removeComRTListaFzErrori(ComRTListaFzErrori comRTListaFzErrori) {
//		getComRTListaFzErroris().remove(comRTListaFzErrori);
//		comRTListaFzErrori.setComDFzErrori(null);
//
//		return comRTListaFzErrori;
//	}

}
