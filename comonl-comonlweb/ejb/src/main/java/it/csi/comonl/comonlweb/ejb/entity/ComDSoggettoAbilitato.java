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
 * The persistent class for the COM_D_SOGGETTO_ABILITATO database table.
 * 
 */
@Entity
@Table(name = "COM_D_SOGGETTO_ABILITATO")
@NamedQuery(name = "ComDSoggettoAbilitato.findAll", query = "SELECT c FROM ComDSoggettoAbilitato c")
@SequenceGenerator(name = "SEQUENCE_SOGGETTO_ABILITATO", sequenceName = "SEQ_ID_COM_D_SOGGETTO_ABILIT", initialValue = 1, allocationSize = 1)
public class ComDSoggettoAbilitato extends BaseAuditedEntity<Long> implements Serializable {

	@Override
	public Long getId() {
		return idComDSoggettoAbilitato;
	}

	@Override
	public void setId(Long id) {
		idComDSoggettoAbilitato = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COM_D_SOGGETTO_ABILITATO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SOGGETTO_ABILITATO")
	private Long idComDSoggettoAbilitato;

	private String cap;

	@Column(name = "CF_SOGGETTO")
	private String cfSoggetto;

	@Column(name = "COGNOME_DENOMINAZIONE")
	private String cognomeDenominazione;

	private String email;

	private String fax;

	@Column(name = "FLG_ACCENTRAMENTO")
	private String flgAccentramento;

	private String indirizzo;

	@Column(name = "PARTITA_IVA")
	private String partitaIva;

	private String tel;

	// bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comDSoggettoAbilitato")
//	private List<AnagraficaDelegato> anagraficaDelegatos;

	// bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name = "ID_COM_T_COMUNE")
	private ComTComune comTComune;

	// bi-directional many-to-one association to ComTTipoSoggettoAbilitato
	@ManyToOne
	@JoinColumn(name = "ID_COM_TIPO_SOGGETTO_ABILITATO")
	private ComTTipoSoggettoAbilitato comTTipoSoggettoAbilitato;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ANNULLAMENTO")
	private Date dataAnnullamento;

	public ComDSoggettoAbilitato() {
	}

	public Long getIdComDSoggettoAbilitato() {
		return this.idComDSoggettoAbilitato;
	}

	public void setIdComDSoggettoAbilitato(Long idComDSoggettoAbilitato) {
		this.idComDSoggettoAbilitato = idComDSoggettoAbilitato;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCfSoggetto() {
		return this.cfSoggetto;
	}

	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	public String getCognomeDenominazione() {
		return this.cognomeDenominazione;
	}

	public void setCognomeDenominazione(String cognomeDenominazione) {
		this.cognomeDenominazione = cognomeDenominazione;
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

	public String getFlgAccentramento() {
		return this.flgAccentramento;
	}

	public void setFlgAccentramento(String flgAccentramento) {
		this.flgAccentramento = flgAccentramento;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

//	public List<AnagraficaDelegato> getAnagraficaDelegatos() {
//		return this.anagraficaDelegatos;
//	}
//
//	public void setAnagraficaDelegatos(List<AnagraficaDelegato> anagraficaDelegatos) {
//		this.anagraficaDelegatos = anagraficaDelegatos;
//	}

//	public AnagraficaDelegato addAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
//		getAnagraficaDelegatos().add(anagraficaDelegato);
//		anagraficaDelegato.setComDSoggettoAbilitato(this);
//
//		return anagraficaDelegato;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
//		getAnagraficaDelegatos().remove(anagraficaDelegato);
//		anagraficaDelegato.setComDSoggettoAbilitato(null);
//
//		return anagraficaDelegato;
//	}

	public ComTComune getComTComune() {
		return this.comTComune;
	}

	public void setComTComune(ComTComune comTComune) {
		this.comTComune = comTComune;
	}

	public ComTTipoSoggettoAbilitato getComTTipoSoggettoAbilitato() {
		return this.comTTipoSoggettoAbilitato;
	}

	public void setComTTipoSoggettoAbilitato(ComTTipoSoggettoAbilitato comTTipoSoggettoAbilitato) {
		this.comTTipoSoggettoAbilitato = comTTipoSoggettoAbilitato;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

}
