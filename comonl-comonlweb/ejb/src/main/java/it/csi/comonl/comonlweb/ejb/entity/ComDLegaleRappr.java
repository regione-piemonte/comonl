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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;

/**
 * The persistent class for the COM_D_LEGALE_RAPPR database table.
 * 
 */
@Entity
@Table(name = "COM_D_LEGALE_RAPPR")
@NamedQuery(name = "ComDLegaleRappr.findAll", query = "SELECT c FROM ComDLegaleRappr c")
public class ComDLegaleRappr extends BaseAuditedEntity<Long> implements Serializable {

	@Override
	public Long getId() {
		return idComDDatore;
	}

	@Override
	public void setId(Long id) {
		idComDDatore = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_D_DATORE")
	private long idComDDatore;

	@Column(name = "DS_COGNOME")
	private String dsCognome;

	@Column(name = "DS_NOME")
	private String dsNome;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INSERT")
	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_SCADENZA_PERMESSO_SOGG")
	private Date dtScadenzaPermessoSogg;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD")
	private Date dtUltMod;

	@Column(name = "FLG_RESIDENZA_ITALIANA")
	private String flgResidenzaItaliana;

	@Column(name = "FLG_SOGGIORNANTE_ITALIA")
	private String flgSoggiornanteItalia;

	@Column(name = "ID_USER_INSERT")
	private String idUserInsert;

	@Column(name = "ID_USER_ULT_MOD")
	private String idUserUltMod;

	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	
	@Column(name = "ID_LEGALE_RAPPR_SILP")
	private BigDecimal idLegaleRapprSilp;

	private String sesso;

	// bi-directional one-to-one association to ComDDatore
	@OneToOne
	@JoinColumn(name = "ID_COM_D_DATORE")
	private ComDDatore comDDatore;

	// bi-directional many-to-one association to ComTCittadinanza
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_CITTADINANZA")
	private ComTCittadinanza comTCittadinanza;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_COMUNE_NASC")
	private ComTComune comTComune;

	// bi-directional many-to-one association to ComTMotivoPermesso
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_MOTIVO_PERMESSO")
	private ComTMotivoPermesso comTMotivoPermesso;

	// bi-directional many-to-one association to ComTQuestura
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_QUESTURA")
	private ComTQuestura comTQuestura;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_STATI_ESTERI_NASC")
	private ComTStatiEsteri comTStatiEsteri;

	// bi-directional many-to-one association to ComTStatusStraniero
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_STATUS_STRANIERO")
	private ComTStatusStraniero comTStatusStraniero;

	public ComDLegaleRappr() {
	}

	public long getIdComDDatore() {
		return this.idComDDatore;
	}

	public void setIdComDDatore(long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}

	public String getDsCognome() {
		return this.dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtScadenzaPermessoSogg() {
		return this.dtScadenzaPermessoSogg;
	}

	public void setDtScadenzaPermessoSogg(Date dtScadenzaPermessoSogg) {
		this.dtScadenzaPermessoSogg = dtScadenzaPermessoSogg;
	}

	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	public String getFlgResidenzaItaliana() {
		return this.flgResidenzaItaliana;
	}

	public void setFlgResidenzaItaliana(String flgResidenzaItaliana) {
		this.flgResidenzaItaliana = flgResidenzaItaliana;
	}

	public String getFlgSoggiornanteItalia() {
		return this.flgSoggiornanteItalia;
	}

	public void setFlgSoggiornanteItalia(String flgSoggiornanteItalia) {
		this.flgSoggiornanteItalia = flgSoggiornanteItalia;
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

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public ComDDatore getComDDatore() {
		return this.comDDatore;
	}

	public void setComDDatore(ComDDatore comDDatore) {
		this.comDDatore = comDDatore;
	}

	public ComTCittadinanza getComTCittadinanza() {
		return this.comTCittadinanza;
	}

	public void setComTCittadinanza(ComTCittadinanza comTCittadinanza) {
		this.comTCittadinanza = comTCittadinanza;
	}

	public ComTComune getComTComune() {
		return this.comTComune;
	}

	public void setComTComune(ComTComune comTComune) {
		this.comTComune = comTComune;
	}

	public ComTMotivoPermesso getComTMotivoPermesso() {
		return this.comTMotivoPermesso;
	}

	public void setComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
		this.comTMotivoPermesso = comTMotivoPermesso;
	}

	public ComTQuestura getComTQuestura() {
		return this.comTQuestura;
	}

	public void setComTQuestura(ComTQuestura comTQuestura) {
		this.comTQuestura = comTQuestura;
	}

	public ComTStatiEsteri getComTStatiEsteri() {
		return this.comTStatiEsteri;
	}

	public void setComTStatiEsteri(ComTStatiEsteri comTStatiEsteri) {
		this.comTStatiEsteri = comTStatiEsteri;
	}

	public ComTStatusStraniero getComTStatusStraniero() {
		return this.comTStatusStraniero;
	}

	public void setComTStatusStraniero(ComTStatusStraniero comTStatusStraniero) {
		this.comTStatusStraniero = comTStatusStraniero;
	}

	public BigDecimal getIdLegaleRapprSilp() {
		return idLegaleRapprSilp;
	}

	public void setIdLegaleRapprSilp(BigDecimal idLegaleRapprSilp) {
		this.idLegaleRapprSilp = idLegaleRapprSilp;
	}
	
	

}
