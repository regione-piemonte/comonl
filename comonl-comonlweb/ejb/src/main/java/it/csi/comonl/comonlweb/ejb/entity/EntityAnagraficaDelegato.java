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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseAuditedEntity;

/**
 * The persistent class for the ANAGRAFICA_DELEGATO database table.
 * 
 */
@Entity
@Table(name = "ANAGRAFICA_DELEGATO")
@NamedQuery(name = "AnagraficaDelegato.findAll", query = "SELECT a FROM EntityAnagraficaDelegato a")
public class EntityAnagraficaDelegato extends BaseAuditedEntity<EntityAnagraficaDelegatoPK> implements Serializable {

	@Override
	public EntityAnagraficaDelegatoPK getId() {
		return id;
	}

	@Override
	public void setId(EntityAnagraficaDelegatoPK id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntityAnagraficaDelegatoPK id;

	@Column(name = "C_CITTADINANZA")
	private Long cittadinanza;

	@Column(name = "CAP_DOM")
	private String capDom;

	@Column(name = "CAP_RES")
	private String capRes;

	private String cognome;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ANNULLAMENTO")
	private Date dataAnnullamento;

	private String email;

	private String fax;

	@Column(name = "INDIRIZZO_DOM")
	private String indirizzoDom;

	@Column(name = "INDIRIZZO_RES")
	private String indirizzoRes;

	private String nome;

	@Column(name = "PIVA_DELEGATO")
	private String pivaDelegato;

	private String sesso;

	private String tel;

	// bi-directional many-to-one association to ComDSoggettoAbilitato
	@ManyToOne
	@JoinColumn(name = "ID_COM_D_SOGGETTO_ABILITATO")
	private ComDSoggettoAbilitato comDSoggettoAbilitato;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "C_COM_DOM")
	private ComTComune comTComuneDom;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "C_COM_RES")
	private ComTComune comTComuneRes;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "C_COM_NASC")
	private ComTComune comTComuneNasc;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "C_STATO_ESTERO")
	private ComTStatiEsteri comTStatiEsteri;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "C_STATO_ESTERO_DOM")
	private ComTStatiEsteri comTStatiEsteriDom;

	// bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name = "C_STATO_ESTERO_RES")
	private ComTStatiEsteri comTStatiEsteriRes;

	// bi-directional many-to-one association to Personalizzazione
	@ManyToOne
	@JoinColumn(name = "PV")
	private EntityPersonalizzazione personalizzazione;

	// bi-directional many-to-one association to ComDDelegatoIride
//	@OneToMany(mappedBy="anagraficaDelegato")
//	private List<ComDDelegatoIride> comDDelegatoIrides;

	// bi-directional many-to-one association to DelegatoImpresa
//	@OneToMany(mappedBy="anagraficaDelegato")
//	private List<DelegatoImpresa> delegatoImpresas;

	public EntityAnagraficaDelegato() {
	}

	public String getCapDom() {
		return this.capDom;
	}

	public void setCapDom(String capDom) {
		this.capDom = capDom;
	}

	public String getCapRes() {
		return this.capRes;
	}

	public void setCapRes(String capRes) {
		this.capRes = capRes;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIndirizzoDom() {
		return this.indirizzoDom;
	}

	public void setIndirizzoDom(String indirizzoDom) {
		this.indirizzoDom = indirizzoDom;
	}

	public String getIndirizzoRes() {
		return this.indirizzoRes;
	}

	public void setIndirizzoRes(String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPivaDelegato() {
		return this.pivaDelegato;
	}

	public void setPivaDelegato(String pivaDelegato) {
		this.pivaDelegato = pivaDelegato;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public ComDSoggettoAbilitato getComDSoggettoAbilitato() {
		return this.comDSoggettoAbilitato;
	}

	public void setComDSoggettoAbilitato(ComDSoggettoAbilitato comDSoggettoAbilitato) {
		this.comDSoggettoAbilitato = comDSoggettoAbilitato;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	public ComTComune getComTComuneDom() {
		return comTComuneDom;
	}

	public void setComTComuneDom(ComTComune comTComuneDom) {
		this.comTComuneDom = comTComuneDom;
	}

	public ComTComune getComTComuneRes() {
		return comTComuneRes;
	}

	public void setComTComuneRes(ComTComune comTComuneRes) {
		this.comTComuneRes = comTComuneRes;
	}

	public ComTComune getComTComuneNasc() {
		return comTComuneNasc;
	}

	public void setComTComuneNasc(ComTComune comTComuneNasc) {
		this.comTComuneNasc = comTComuneNasc;
	}

	public ComTStatiEsteri getComTStatiEsteri() {
		return comTStatiEsteri;
	}

	public void setComTStatiEsteri(ComTStatiEsteri comTStatiEsteri) {
		this.comTStatiEsteri = comTStatiEsteri;
	}

	public ComTStatiEsteri getComTStatiEsteriDom() {
		return comTStatiEsteriDom;
	}

	public void setComTStatiEsteriDom(ComTStatiEsteri comTStatiEsteriDom) {
		this.comTStatiEsteriDom = comTStatiEsteriDom;
	}

	public ComTStatiEsteri getComTStatiEsteriRes() {
		return comTStatiEsteriRes;
	}

	public void setComTStatiEsteriRes(ComTStatiEsteri comTStatiEsteriRes) {
		this.comTStatiEsteriRes = comTStatiEsteriRes;
	}

	public Long getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(Long cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

//	public List<ComDDelegatoIride> getComDDelegatoIrides() {
//		return this.comDDelegatoIrides;
//	}
//
//	public void setComDDelegatoIrides(List<ComDDelegatoIride> comDDelegatoIrides) {
//		this.comDDelegatoIrides = comDDelegatoIrides;
//	}
//
//	public ComDDelegatoIride addComDDelegatoIride(ComDDelegatoIride comDDelegatoIride) {
//		getComDDelegatoIrides().add(comDDelegatoIride);
//		comDDelegatoIride.setAnagraficaDelegato(this);
//
//		return comDDelegatoIride;
//	}
//
//	public ComDDelegatoIride removeComDDelegatoIride(ComDDelegatoIride comDDelegatoIride) {
//		getComDDelegatoIrides().remove(comDDelegatoIride);
//		comDDelegatoIride.setAnagraficaDelegato(null);
//
//		return comDDelegatoIride;
//	}

//	public List<DelegatoImpresa> getDelegatoImpresas() {
//		return this.delegatoImpresas;
//	}
//
//	public void setDelegatoImpresas(List<DelegatoImpresa> delegatoImpresas) {
//		this.delegatoImpresas = delegatoImpresas;
//	}
//
//	public DelegatoImpresa addDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
//		getDelegatoImpresas().add(delegatoImpresa);
//		delegatoImpresa.setAnagraficaDelegato(this);
//
//		return delegatoImpresa;
//	}
//
//	public DelegatoImpresa removeDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
//		getDelegatoImpresas().remove(delegatoImpresa);
//		delegatoImpresa.setAnagraficaDelegato(null);
//
//		return delegatoImpresa;
//	}

}
