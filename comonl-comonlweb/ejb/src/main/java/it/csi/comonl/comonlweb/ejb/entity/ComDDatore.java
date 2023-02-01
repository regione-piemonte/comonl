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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_DATORE database table.
 * 
 */
@Entity
@Table(name="COM_D_DATORE")
@NamedQuery(name="ComDDatore.findAll", query="SELECT c FROM ComDDatore c")
@SequenceGenerator(name = "SEQUENCE_DATORE", sequenceName = "SEQ_ID_COM_D_DATORE", initialValue = 1, allocationSize = 1)
public class ComDDatore implements Serializable, BaseEntity<Long> {

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
	@Column(name="ID_COM_D_DATORE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_DATORE")
	private Long idComDDatore;

	@ManyToOne
	@JoinColumn(name="C_NATURA_GIURIDICA")
	private EntityNaturaGiuridica naturaGiuridica;
	

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="DS_COGNOME")
	private String dsCognome;

	@Column(name="DS_DENOMINAZIONE_DATORE")
	private String dsDenominazioneDatore;

	@Column(name="DS_NOME")
	private String dsNome;

	@Column(name="DS_VARIAZIONE_RAG_SOCIALE")
	private String dsVariazioneRagSociale;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_CONTRATTO_SOM")
	private Date dtFineContrattoSom;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_CONTRATTO_SOM")
	private Date dtInizioContrattoSom;

	@Column(name="FLG_AZ_ARTIGIANA")
	private String flgAzArtigiana;

	@Column(name="FLG_DENOM_SILP_VARIATA")
	private String flgDenomSilpVariata;

	@Column(name="FLG_NO_AAEP")
	private String flgNoAaep;

	@Column(name="FLG_PUB_AMM")
	private String flgPubAmm;

	@Column(name="FLG_UTIL_ESTERA")
	private String flgUtilEstera;

	@Column(name="ID_AZIENDA_SILP")
	private BigDecimal idAziendaSilp;

	@Column(name="MATRICOLA_INPS")
	private String matricolaInps;

	@Column(name="NUM_AGENZIA_SOMMIN")
	private String numAgenziaSommin;

	@Column(name="NUM_CONTR_SOMM")
	private BigDecimal numContrSomm;

	@Column(name="NUMERO_ISCRIZIONE_ALBO")
	private String numeroIscrizioneAlbo;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	@Column(name="PAT_INAIL")
	private String patInail;

	//bi-directional many-to-one association to ComTAtecofin
	@ManyToOne
	@JoinColumn(name="ID_COM_T_ATECOFIN")
	private ComTAtecofin comTAtecofin;

	//bi-directional one-to-one association to ComDLegaleRappr
	/*@OneToOne(mappedBy="comDDatore")
	private ComDLegaleRappr comDLegaleRappr;*/

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comDDatore1")
//	private List<ComDRapporto> comDRapportos1;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comDDatore2")
//	private List<ComDRapporto> comDRapportos2;

	//bi-directional many-to-one association to ComRComunicazioneDatore
//	@OneToMany(mappedBy="comDDatore")
//	private List<ComRComunicazioneDatore> comRComunicazioneDatores;

	//bi-directional many-to-many association to ComDSedeLavoro
//	@ManyToMany(mappedBy="comDDatores")
//	private List<ComDSedeLavoro> comDSedeLavoros;

	public ComDDatore() {
	}

	public long getIdComDDatore() {
		return this.idComDDatore;
	}

	public void setIdComDDatore(long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}



	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDsCognome() {
		return this.dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getDsDenominazioneDatore() {
		return this.dsDenominazioneDatore;
	}

	public void setDsDenominazioneDatore(String dsDenominazioneDatore) {
		this.dsDenominazioneDatore = dsDenominazioneDatore;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsVariazioneRagSociale() {
		return this.dsVariazioneRagSociale;
	}

	public void setDsVariazioneRagSociale(String dsVariazioneRagSociale) {
		this.dsVariazioneRagSociale = dsVariazioneRagSociale;
	}

	public Date getDtFineContrattoSom() {
		return this.dtFineContrattoSom;
	}

	public void setDtFineContrattoSom(Date dtFineContrattoSom) {
		this.dtFineContrattoSom = dtFineContrattoSom;
	}

	public Date getDtInizioContrattoSom() {
		return this.dtInizioContrattoSom;
	}

	public void setDtInizioContrattoSom(Date dtInizioContrattoSom) {
		this.dtInizioContrattoSom = dtInizioContrattoSom;
	}

	public String getFlgAzArtigiana() {
		return this.flgAzArtigiana;
	}

	public void setFlgAzArtigiana(String flgAzArtigiana) {
		this.flgAzArtigiana = flgAzArtigiana;
	}

	public String getFlgDenomSilpVariata() {
		return this.flgDenomSilpVariata;
	}

	public void setFlgDenomSilpVariata(String flgDenomSilpVariata) {
		this.flgDenomSilpVariata = flgDenomSilpVariata;
	}

	public String getFlgNoAaep() {
		return this.flgNoAaep;
	}

	public void setFlgNoAaep(String flgNoAaep) {
		this.flgNoAaep = flgNoAaep;
	}

	public String getFlgPubAmm() {
		return this.flgPubAmm;
	}

	public void setFlgPubAmm(String flgPubAmm) {
		this.flgPubAmm = flgPubAmm;
	}

	public String getFlgUtilEstera() {
		return this.flgUtilEstera;
	}

	public void setFlgUtilEstera(String flgUtilEstera) {
		this.flgUtilEstera = flgUtilEstera;
	}

	public BigDecimal getIdAziendaSilp() {
		return this.idAziendaSilp;
	}

	public void setIdAziendaSilp(BigDecimal idAziendaSilp) {
		this.idAziendaSilp = idAziendaSilp;
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

	public BigDecimal getNumContrSomm() {
		return this.numContrSomm;
	}

	public void setNumContrSomm(BigDecimal numContrSomm) {
		this.numContrSomm = numContrSomm;
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

	public String getPatInail() {
		return this.patInail;
	}

	public void setPatInail(String patInail) {
		this.patInail = patInail;
	}

	public ComTAtecofin getComTAtecofin() {
		return this.comTAtecofin;
	}

	public void setComTAtecofin(ComTAtecofin comTAtecofin) {
		this.comTAtecofin = comTAtecofin;
	}

	public EntityNaturaGiuridica getNaturaGiuridica() {
		return naturaGiuridica;
	}

	public void setNaturaGiuridica(EntityNaturaGiuridica naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	
	
	
	/*
	public ComDLegaleRappr getComDLegaleRappr() {
		return this.comDLegaleRappr;
	}

	public void setComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
		this.comDLegaleRappr = comDLegaleRappr;
	}*/

//	public List<ComDRapporto> getComDRapportos1() {
//		return this.comDRapportos1;
//	}
//
//	public void setComDRapportos1(List<ComDRapporto> comDRapportos1) {
//		this.comDRapportos1 = comDRapportos1;
//	}
//
//	public ComDRapporto addComDRapportos1(ComDRapporto comDRapportos1) {
//		getComDRapportos1().add(comDRapportos1);
//		comDRapportos1.setComDDatore1(this);
//
//		return comDRapportos1;
//	}
//
//	public ComDRapporto removeComDRapportos1(ComDRapporto comDRapportos1) {
//		getComDRapportos1().remove(comDRapportos1);
//		comDRapportos1.setComDDatore1(null);
//
//		return comDRapportos1;
//	}

//	public List<ComDRapporto> getComDRapportos2() {
//		return this.comDRapportos2;
//	}
//
//	public void setComDRapportos2(List<ComDRapporto> comDRapportos2) {
//		this.comDRapportos2 = comDRapportos2;
//	}
//
//	public ComDRapporto addComDRapportos2(ComDRapporto comDRapportos2) {
//		getComDRapportos2().add(comDRapportos2);
//		comDRapportos2.setComDDatore2(this);
//
//		return comDRapportos2;
//	}
//
//	public ComDRapporto removeComDRapportos2(ComDRapporto comDRapportos2) {
//		getComDRapportos2().remove(comDRapportos2);
//		comDRapportos2.setComDDatore2(null);
//
//		return comDRapportos2;
//	}
//
//	public List<ComRComunicazioneDatore> getComRComunicazioneDatores() {
//		return this.comRComunicazioneDatores;
//	}

//	public void setComRComunicazioneDatores(List<ComRComunicazioneDatore> comRComunicazioneDatores) {
//		this.comRComunicazioneDatores = comRComunicazioneDatores;
//	}
//
//	public ComRComunicazioneDatore addComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().add(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComDDatore(this);
//
//		return comRComunicazioneDatore;
//	}
//
//	public ComRComunicazioneDatore removeComRComunicazioneDatore(ComRComunicazioneDatore comRComunicazioneDatore) {
//		getComRComunicazioneDatores().remove(comRComunicazioneDatore);
//		comRComunicazioneDatore.setComDDatore(null);
//
//		return comRComunicazioneDatore;
//	}

//	public List<ComDSedeLavoro> getComDSedeLavoros() {
//		return this.comDSedeLavoros;
//	}
//
//	public void setComDSedeLavoros(List<ComDSedeLavoro> comDSedeLavoros) {
//		this.comDSedeLavoros = comDSedeLavoros;
//	}

}
