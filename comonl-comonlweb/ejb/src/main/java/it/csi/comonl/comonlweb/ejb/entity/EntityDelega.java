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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;

/**
 * The persistent class for the DELEGA database table.
 * 
 */
@Entity
@Table(name = "DELEGA")
@NamedQuery(name = "Delega.findAll", query = "SELECT d FROM EntityDelega d")
@SequenceGenerator(name = "SEQUENCE_DELEGA", sequenceName = "SEQ_ID_DELEGA", initialValue = 1, allocationSize = 1)
public class EntityDelega extends BaseAuditedEntity<Long> implements Serializable {

	@Override
	public Long getId() {
		return idDelega;
	}

	@Override
	public void setId(Long id) {
		idDelega = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_DELEGA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_DELEGA")
	private Long idDelega;

	@Column(name = "ANNO_PROTOCOLLO")
	private BigDecimal annoProtocollo;

	@Column(name = "C_ENTE")
	private String cEnte;

	@Column(name = "CAP_DELEGANTE")
	private String capDelegante;

	@Column(name = "CAP_DELEGATO")
	private String capDelegato;

	private String carica;

	@Column(name = "CF_DELEGANTE")
	private String cfDelegante;

	@Column(name = "CF_DELEGATO")
	private String cfDelegato;

	@Column(name = "CF_IMPRESA")
	private String cfImpresa;

	@Column(name = "COGNOME_DELEGANTE")
	private String cognomeDelegante;

	@Column(name = "COGNOME_DELEGATO")
	private String cognomeDelegato;

	@Column(name = "COMUNE_DELEGANTE")
	private String comuneDelegante;

	@Column(name = "COMUNE_DELEGATO")
	private String comuneDelegato;

	@Column(name = "DENOMINAZIONE_IMPRESA")
	private String denominazioneImpresa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INSERT")
	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PROTOCOLLO")
	private Date dtProtocollo;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REVOCA")
	private Date dtRevoca;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ULT_MOD")
	private Date dtUltMod;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_VERIFICA")
	private Date dtVerifica;

	@Column(name = "FAX_DELEGATO")
	private String faxDelegato;

	@Column(name = "ID_USER_INSERT")
	private String idUserInsert;

	@Column(name = "ID_USER_ULT_MOD")
	private String idUserUltMod;

	@Column(name = "INDIRIZZO_DELEGANTE")
	private String indirizzoDelegante;

	@Column(name = "INDIRIZZO_DELEGATO")
	private String indirizzoDelegato;

	@Column(name = "MAIL_DELEGANTE")
	private String mailDelegante;

	@Column(name = "MAIL_DELEGATO")
	private String mailDelegato;

	@Column(name = "N_PROTOCOLLO")
	private Long numeroProtocollo;

	@Column(name = "NOME_DELEGANTE")
	private String nomeDelegante;

	@Column(name = "NOME_DELEGATO")
	private String nomeDelegato;

	@Column(name = "PIVA_DELEGATO")
	private String pivaDelegato;

	@Column(name = "PIVA_IMPRESA")
	private String pivaImpresa;

	@Column(name = "PV_DELEGANTE")
	private String pvDelegante;

	@Column(name = "PV_DELEGATO")
	private String pvDelegato;

	@Column(name = "TELEFONO_DELEGANTE")
	private String telefonoDelegante;

	@Column(name = "TELEFONO_DELEGATO")
	private String telefonoDelegato;

	private String verifica;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "C_COM_DELEGATO")
	private ComTComune comTComDelegato;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "C_COM_DELEGANTE")
	private ComTComune comTComDelegante;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "C_STATO_DELEGATO")
	private ComTStatiEsteri comTStatiEsteriDelegato;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "C_STATO_DELEGANTE")
	private ComTStatiEsteri comTStatiEsteriDelegante;

	// bi-directional many-to-one association to Personalizzazione
	@ManyToOne
	@JoinColumn(name = "PV_COMPETENZA")
	private EntityPersonalizzazione personalizzazione;

	// bi-directional many-to-one association to StatoDelega
	@ManyToOne
	@JoinColumn(name = "C_STATO")
	private EntityStatoDelega statoDelega;

	// bi-directional many-to-one association to TipoDelega
	@ManyToOne
	@JoinColumn(name = "C_TIPO_DELEGA")
	private EntityTipoDelega tipoDelega;

	public EntityDelega() {
	}

	public long getIdDelega() {
		return this.idDelega;
	}

	public void setIdDelega(long idDelega) {
		this.idDelega = idDelega;
	}

	public BigDecimal getAnnoProtocollo() {
		return this.annoProtocollo;
	}

	public void setAnnoProtocollo(BigDecimal annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	public String getCEnte() {
		return this.cEnte;
	}

	public void setCEnte(String cEnte) {
		this.cEnte = cEnte;
	}

	public String getCapDelegante() {
		return this.capDelegante;
	}

	public void setCapDelegante(String capDelegante) {
		this.capDelegante = capDelegante;
	}

	public String getCapDelegato() {
		return this.capDelegato;
	}

	public void setCapDelegato(String capDelegato) {
		this.capDelegato = capDelegato;
	}

	public String getCarica() {
		return this.carica;
	}

	public void setCarica(String carica) {
		this.carica = carica;
	}

	public String getCfDelegante() {
		return this.cfDelegante;
	}

	public void setCfDelegante(String cfDelegante) {
		this.cfDelegante = cfDelegante;
	}

	public String getCfDelegato() {
		return this.cfDelegato;
	}

	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}

	public String getCfImpresa() {
		return this.cfImpresa;
	}

	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}

	public String getCognomeDelegante() {
		return this.cognomeDelegante;
	}

	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}

	public String getCognomeDelegato() {
		return this.cognomeDelegato;
	}

	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}

	public String getComuneDelegante() {
		return this.comuneDelegante;
	}

	public void setComuneDelegante(String comuneDelegante) {
		this.comuneDelegante = comuneDelegante;
	}

	public String getComuneDelegato() {
		return this.comuneDelegato;
	}

	public void setComuneDelegato(String comuneDelegato) {
		this.comuneDelegato = comuneDelegato;
	}

	public String getDenominazioneImpresa() {
		return this.denominazioneImpresa;
	}

	public void setDenominazioneImpresa(String denominazioneImpresa) {
		this.denominazioneImpresa = denominazioneImpresa;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtProtocollo() {
		return this.dtProtocollo;
	}

	public void setDtProtocollo(Date dtProtocollo) {
		this.dtProtocollo = dtProtocollo;
	}

	public Date getDtRevoca() {
		return this.dtRevoca;
	}

	public void setDtRevoca(Date dtRevoca) {
		this.dtRevoca = dtRevoca;
	}

	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	public Date getDtVerifica() {
		return this.dtVerifica;
	}

	public void setDtVerifica(Date dtVerifica) {
		this.dtVerifica = dtVerifica;
	}

	public String getFaxDelegato() {
		return this.faxDelegato;
	}

	public void setFaxDelegato(String faxDelegato) {
		this.faxDelegato = faxDelegato;
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

	public String getIndirizzoDelegante() {
		return this.indirizzoDelegante;
	}

	public void setIndirizzoDelegante(String indirizzoDelegante) {
		this.indirizzoDelegante = indirizzoDelegante;
	}

	public String getIndirizzoDelegato() {
		return this.indirizzoDelegato;
	}

	public void setIndirizzoDelegato(String indirizzoDelegato) {
		this.indirizzoDelegato = indirizzoDelegato;
	}

	public String getMailDelegante() {
		return this.mailDelegante;
	}

	public void setMailDelegante(String mailDelegante) {
		this.mailDelegante = mailDelegante;
	}

	public String getMailDelegato() {
		return this.mailDelegato;
	}

	public void setMailDelegato(String mailDelegato) {
		this.mailDelegato = mailDelegato;
	}

	public Long getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(Long numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getNomeDelegante() {
		return this.nomeDelegante;
	}

	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}

	public String getNomeDelegato() {
		return this.nomeDelegato;
	}

	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}

	public String getPivaDelegato() {
		return this.pivaDelegato;
	}

	public void setPivaDelegato(String pivaDelegato) {
		this.pivaDelegato = pivaDelegato;
	}

	public String getPivaImpresa() {
		return this.pivaImpresa;
	}

	public void setPivaImpresa(String pivaImpresa) {
		this.pivaImpresa = pivaImpresa;
	}

	public String getPvDelegante() {
		return this.pvDelegante;
	}

	public void setPvDelegante(String pvDelegante) {
		this.pvDelegante = pvDelegante;
	}

	public String getPvDelegato() {
		return this.pvDelegato;
	}

	public void setPvDelegato(String pvDelegato) {
		this.pvDelegato = pvDelegato;
	}

	public String getTelefonoDelegante() {
		return this.telefonoDelegante;
	}

	public void setTelefonoDelegante(String telefonoDelegante) {
		this.telefonoDelegante = telefonoDelegante;
	}

	public String getTelefonoDelegato() {
		return this.telefonoDelegato;
	}

	public void setTelefonoDelegato(String telefonoDelegato) {
		this.telefonoDelegato = telefonoDelegato;
	}

	public String getVerifica() {
		return this.verifica;
	}

	public void setVerifica(String verifica) {
		this.verifica = verifica;
	}

	public ComTComune getComTComDelegato() {
		return comTComDelegato;
	}

	public void setComTComDelegato(ComTComune comTComuneDelegato) {
		this.comTComDelegato = comTComuneDelegato;
	}

	public ComTComune getComTComDelegante() {
		return comTComDelegante;
	}

	public void setComTComDelegante(ComTComune comTComuneDelegante) {
		this.comTComDelegante = comTComuneDelegante;
	}

	public ComTStatiEsteri getComTStatiEsteriDelegato() {
		return comTStatiEsteriDelegato;
	}

	public void setComTStatiEsteriDelegato(ComTStatiEsteri comTStatiEsteriDelegato) {
		this.comTStatiEsteriDelegato = comTStatiEsteriDelegato;
	}

	public ComTStatiEsteri getComTStatiEsteriDelegante() {
		return comTStatiEsteriDelegante;
	}

	public void setComTStatiEsteriDelegante(ComTStatiEsteri comTStatiEsteriDelegante) {
		this.comTStatiEsteriDelegante = comTStatiEsteriDelegante;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	public EntityStatoDelega getStatoDelega() {
		return this.statoDelega;
	}

	public void setStatoDelega(EntityStatoDelega statoDelega) {
		this.statoDelega = statoDelega;
	}

	public EntityTipoDelega getTipoDelega() {
		return this.tipoDelega;
	}

	public void setTipoDelega(EntityTipoDelega tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

}
