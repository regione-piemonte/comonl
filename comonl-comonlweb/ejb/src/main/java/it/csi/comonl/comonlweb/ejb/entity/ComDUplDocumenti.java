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
 * The persistent class for the COM_D_UPL_DOCUMENTI database table.
 * 
 */
@Entity
@Table(name="COM_D_UPL_DOCUMENTI")
@NamedQuery(name="ComDUplDocumenti.findAll", query="SELECT c FROM ComDUplDocumenti c")
@SequenceGenerator(name = "SEQUENCE_UPLOAD", sequenceName = "SEQ_ID_COM_D_UPL_DOCUMENTI", initialValue = 1, allocationSize = 1)
public class ComDUplDocumenti  implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDUplDocumenti;
	}

	@Override
	public void setId(Long id) {
		idComDUplDocumenti = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_UPL_DOCUMENTI")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_UPLOAD")
	private Long idComDUplDocumenti;

	@Column(name="CF_AZIENDA")
	private String cfAzienda;

	@Column(name="CRC_UPLOAD")
	private BigDecimal crcUpload;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ELAB")
	private Date dataElab;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICEZIONE")
	private Date dataRicezione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_VERIFICA")
	private Date dataVerifica;

	@Column(name="DS_RUOLO_OPERATORE")
	private String dsRuoloOperatore;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_RICHIESTA_ELABORAZIONE")
	private Date dtRichiestaElaborazione;

	@Column(name="FLG_VERIFICA")
	private String flgVerifica;

	@Column(name="MITTENTE_CF")
	private String mittenteCf;

	@Column(name="MITTENTE_COGNOME")
	private String mittenteCognome;

	@Column(name="MITTENTE_EMAIL")
	private String mittenteEmail;

	@Column(name="MITTENTE_NOME")
	private String mittenteNome;

	@Column(name="NOME_FILE_UPLOAD")
	private String nomeFileUpload;

	@Column(name="PV")
	private String pv;

	@Column(name="STATO_ELABORAZIONE")
	private String statoElaborazione;

	@Column(name="TOT_COM_ELAB")
	private BigDecimal totComElab;

	@Column(name="TOT_COM_SCAR")
	private BigDecimal totComScar;

	@Column(name="TOT_COM_WARN")
	private BigDecimal totComWarn;
	
	@Column(name="VERIFICA")
	private String verifica;

//	//bi-directional many-to-one association to ComDElaboratiOk
//	@OneToMany(mappedBy="comDUplDocumenti")
//	private List<ComDElaboratiOk> comDElaboratiOks;
//
//	//bi-directional many-to-one association to ComDFmErrori
//	@OneToMany(mappedBy="comDUplDocumenti")
//	private List<ComDFmErrori> comDFmErroris;
//
//	//bi-directional many-to-one association to ComDFzErrori
//	@OneToMany(mappedBy="comDUplDocumenti")
//	private List<ComDFzErrori> comDFzErroris;
//
//	//bi-directional many-to-one association to ComRDepCommaxComunic
//	@OneToMany(mappedBy="comDUplDocumenti")
//	private List<ComRDepCommaxComunic> comRDepCommaxComunics;

	public ComDUplDocumenti() {
	}

	public Long getIdComDUplDocumenti() {
		return this.idComDUplDocumenti;
	}

	public void setIdComDUplDocumenti(Long idComDUplDocumenti) {
		this.idComDUplDocumenti = idComDUplDocumenti;
	}

	public String getCfAzienda() {
		return this.cfAzienda;
	}

	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	public BigDecimal getCrcUpload() {
		return this.crcUpload;
	}

	public void setCrcUpload(BigDecimal crcUpload) {
		this.crcUpload = crcUpload;
	}

	public Date getDataElab() {
		return this.dataElab;
	}

	public void setDataElab(Date dataElab) {
		this.dataElab = dataElab;
	}

	public Date getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public Date getDataVerifica() {
		return this.dataVerifica;
	}

	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
	}

	public String getDsRuoloOperatore() {
		return this.dsRuoloOperatore;
	}

	public void setDsRuoloOperatore(String dsRuoloOperatore) {
		this.dsRuoloOperatore = dsRuoloOperatore;
	}

	public Date getDtRichiestaElaborazione() {
		return this.dtRichiestaElaborazione;
	}

	public void setDtRichiestaElaborazione(Date dtRichiestaElaborazione) {
		this.dtRichiestaElaborazione = dtRichiestaElaborazione;
	}

	public String getFlgVerifica() {
		return this.flgVerifica;
	}

	public void setFlgVerifica(String flgVerifica) {
		this.flgVerifica = flgVerifica;
	}

	public String getMittenteCf() {
		return this.mittenteCf;
	}

	public void setMittenteCf(String mittenteCf) {
		this.mittenteCf = mittenteCf;
	}

	public String getMittenteCognome() {
		return this.mittenteCognome;
	}

	public void setMittenteCognome(String mittenteCognome) {
		this.mittenteCognome = mittenteCognome;
	}

	public String getMittenteEmail() {
		return this.mittenteEmail;
	}

	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}

	public String getMittenteNome() {
		return this.mittenteNome;
	}

	public void setMittenteNome(String mittenteNome) {
		this.mittenteNome = mittenteNome;
	}

	public String getNomeFileUpload() {
		return this.nomeFileUpload;
	}

	public void setNomeFileUpload(String nomeFileUpload) {
		this.nomeFileUpload = nomeFileUpload;
	}

	public String getPv() {
		return this.pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getStatoElaborazione() {
		return this.statoElaborazione;
	}

	public void setStatoElaborazione(String statoElaborazione) {
		this.statoElaborazione = statoElaborazione;
	}

	public BigDecimal getTotComElab() {
		return this.totComElab;
	}

	public void setTotComElab(BigDecimal totComElab) {
		this.totComElab = totComElab;
	}

	public BigDecimal getTotComScar() {
		return this.totComScar;
	}

	public void setTotComScar(BigDecimal totComScar) {
		this.totComScar = totComScar;
	}

	public BigDecimal getTotComWarn() {
		return this.totComWarn;
	}

	public void setTotComWarn(BigDecimal totComWarn) {
		this.totComWarn = totComWarn;
	}

	public String getVerifica() {
		return this.verifica;
	}

	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}

//	public List<ComDElaboratiOk> getComDElaboratiOks() {
//		return this.comDElaboratiOks;
//	}
//
//	public void setComDElaboratiOks(List<ComDElaboratiOk> comDElaboratiOks) {
//		this.comDElaboratiOks = comDElaboratiOks;
//	}
//
//	public ComDElaboratiOk addComDElaboratiOk(ComDElaboratiOk comDElaboratiOk) {
//		getComDElaboratiOks().add(comDElaboratiOk);
//		comDElaboratiOk.setComDUplDocumenti(this);
//
//		return comDElaboratiOk;
//	}
//
//	public ComDElaboratiOk removeComDElaboratiOk(ComDElaboratiOk comDElaboratiOk) {
//		getComDElaboratiOks().remove(comDElaboratiOk);
//		comDElaboratiOk.setComDUplDocumenti(null);
//
//		return comDElaboratiOk;
//	}

//	public List<ComDFmErrori> getComDFmErroris() {
//		return this.comDFmErroris;
//	}
//
//	public void setComDFmErroris(List<ComDFmErrori> comDFmErroris) {
//		this.comDFmErroris = comDFmErroris;
//	}
//
//	public ComDFmErrori addComDFmErrori(ComDFmErrori comDFmErrori) {
//		getComDFmErroris().add(comDFmErrori);
//		comDFmErrori.setComDUplDocumenti(this);
//
//		return comDFmErrori;
//	}
//
//	public ComDFmErrori removeComDFmErrori(ComDFmErrori comDFmErrori) {
//		getComDFmErroris().remove(comDFmErrori);
//		comDFmErrori.setComDUplDocumenti(null);
//
//		return comDFmErrori;
//	}

//	public List<ComDFzErrori> getComDFzErroris() {
//		return this.comDFzErroris;
//	}
//
//	public void setComDFzErroris(List<ComDFzErrori> comDFzErroris) {
//		this.comDFzErroris = comDFzErroris;
//	}
//
//	public ComDFzErrori addComDFzErrori(ComDFzErrori comDFzErrori) {
//		getComDFzErroris().add(comDFzErrori);
//		comDFzErrori.setComDUplDocumenti(this);
//
//		return comDFzErrori;
//	}
//
//	public ComDFzErrori removeComDFzErrori(ComDFzErrori comDFzErrori) {
//		getComDFzErroris().remove(comDFzErrori);
//		comDFzErrori.setComDUplDocumenti(null);
//
//		return comDFzErrori;
//	}

//	public List<ComRDepCommaxComunic> getComRDepCommaxComunics() {
//		return this.comRDepCommaxComunics;
//	}
//
//	public void setComRDepCommaxComunics(List<ComRDepCommaxComunic> comRDepCommaxComunics) {
//		this.comRDepCommaxComunics = comRDepCommaxComunics;
//	}
//
//	public ComRDepCommaxComunic addComRDepCommaxComunic(ComRDepCommaxComunic comRDepCommaxComunic) {
//		getComRDepCommaxComunics().add(comRDepCommaxComunic);
//		comRDepCommaxComunic.setComDUplDocumenti(this);
//
//		return comRDepCommaxComunic;
//	}
//
//	public ComRDepCommaxComunic removeComRDepCommaxComunic(ComRDepCommaxComunic comRDepCommaxComunic) {
//		getComRDepCommaxComunics().remove(comRDepCommaxComunic);
//		comRDepCommaxComunic.setComDUplDocumenti(null);
//
//		return comRDepCommaxComunic;
//	}

}
