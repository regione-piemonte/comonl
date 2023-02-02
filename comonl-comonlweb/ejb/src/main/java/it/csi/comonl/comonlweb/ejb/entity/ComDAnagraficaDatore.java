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
 * The persistent class for the COM_D_ANAGRAFICA_DATORE database table.
 * 
 */
@Entity
@Table(name="COM_D_ANAGRAFICA_DATORE")
@NamedQuery(name="ComDAnagraficaDatore.findAll", query="SELECT c FROM ComDAnagraficaDatore c")
public class ComDAnagraficaDatore implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDAnagraficaDatore;
	}

	@Override
	public void setId(Long id) {
		idComDAnagraficaDatore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_ANAGRAFICA_DATORE")
	private long idComDAnagraficaDatore;

	@Column(name="C_NATURA_GIURIDICA")
	private String cNaturaGiuridica;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="DS_DENOMINAZIONE_DATORE")
	private String dsDenominazioneDatore;

	@Column(name="DS_VARIAZIONE_RAG_SOCIALE")
	private String dsVariazioneRagSociale;

	@Column(name="FLG_AZ_ARTIGIANA")
	private String flgAzArtigiana;

	@Column(name="FLG_NO_AAEP")
	private String flgNoAaep;

	@Column(name="MATRICOLA_INPS")
	private String matricolaInps;

	@Column(name="NUM_AGENZIA_SOMMIN")
	private String numAgenziaSommin;

	@Column(name="NUMERO_ISCRIZIONE_ALBO")
	private String numeroIscrizioneAlbo;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	//bi-directional many-to-one association to ComTAtecofin
	@ManyToOne
	@JoinColumn(name="ID_COM_T_ATECOFIN")
	private ComTAtecofin comTAtecofin;

	//bi-directional many-to-many association to ComDAnagraficaSedeLavoro
//	@ManyToMany(mappedBy="comDAnagraficaDatores")
//	private List<ComDAnagraficaSedeLavoro> comDAnagraficaSedeLavoros;

	//bi-directional many-to-one association to DelegatoImpresa
//	@OneToMany(mappedBy="comDAnagraficaDatore")
//	private List<DelegatoImpresa> delegatoImpresas;

	public ComDAnagraficaDatore() {
	}

	public long getIdComDAnagraficaDatore() {
		return this.idComDAnagraficaDatore;
	}

	public void setIdComDAnagraficaDatore(long idComDAnagraficaDatore) {
		this.idComDAnagraficaDatore = idComDAnagraficaDatore;
	}

	public String getCNaturaGiuridica() {
		return this.cNaturaGiuridica;
	}

	public void setCNaturaGiuridica(String cNaturaGiuridica) {
		this.cNaturaGiuridica = cNaturaGiuridica;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDsDenominazioneDatore() {
		return this.dsDenominazioneDatore;
	}

	public void setDsDenominazioneDatore(String dsDenominazioneDatore) {
		this.dsDenominazioneDatore = dsDenominazioneDatore;
	}

	public String getDsVariazioneRagSociale() {
		return this.dsVariazioneRagSociale;
	}

	public void setDsVariazioneRagSociale(String dsVariazioneRagSociale) {
		this.dsVariazioneRagSociale = dsVariazioneRagSociale;
	}

	public String getFlgAzArtigiana() {
		return this.flgAzArtigiana;
	}

	public void setFlgAzArtigiana(String flgAzArtigiana) {
		this.flgAzArtigiana = flgAzArtigiana;
	}

	public String getFlgNoAaep() {
		return this.flgNoAaep;
	}

	public void setFlgNoAaep(String flgNoAaep) {
		this.flgNoAaep = flgNoAaep;
	}

	public String getMatricolaInps() {
		return this.matricolaInps;
	}

	public void setMatricolaInps(String matricolaInps) {
		this.matricolaInps = matricolaInps;
	}

	public String getNumAgenziaSommin() {
		return this.numAgenziaSommin;
	}

	public void setNumAgenziaSommin(String numAgenziaSommin) {
		this.numAgenziaSommin = numAgenziaSommin;
	}

	public String getNumeroIscrizioneAlbo() {
		return this.numeroIscrizioneAlbo;
	}

	public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
		this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public ComTAtecofin getComTAtecofin() {
		return this.comTAtecofin;
	}

	public void setComTAtecofin(ComTAtecofin comTAtecofin) {
		this.comTAtecofin = comTAtecofin;
	}

//	public List<ComDAnagraficaSedeLavoro> getComDAnagraficaSedeLavoros() {
//		return this.comDAnagraficaSedeLavoros;
//	}
//
//	public void setComDAnagraficaSedeLavoros(List<ComDAnagraficaSedeLavoro> comDAnagraficaSedeLavoros) {
//		this.comDAnagraficaSedeLavoros = comDAnagraficaSedeLavoros;
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
//		delegatoImpresa.setComDAnagraficaDatore(this);
//
//		return delegatoImpresa;
//	}
//
//	public DelegatoImpresa removeDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
//		getDelegatoImpresas().remove(delegatoImpresa);
//		delegatoImpresa.setComDAnagraficaDatore(null);
//
//		return delegatoImpresa;
//	}

}
