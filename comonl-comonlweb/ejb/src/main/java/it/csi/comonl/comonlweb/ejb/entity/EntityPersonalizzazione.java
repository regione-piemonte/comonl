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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the PERSONALIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="PERSONALIZZAZIONE")
@NamedQuery(name="Personalizzazione.findAll", query="SELECT p FROM EntityPersonalizzazione p")
public class EntityPersonalizzazione implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return pv;
	}

	@Override
	public void setId(String id) {
		this.pv = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	private String pv;

	@Column(name="ABILITA_SCELTA_INOLTRO_INAIL")
	private String abilitaSceltaInoltroInail;

	@Column(name="ABILITA_SCELTA_INOLTRO_INPS")
	private String abilitaSceltaInoltroInps;

	@Column(name="C_UTENTE")
	private String cUtente;

	@Column(name="DEN_DESTINATARIO")
	private String denDestinatario;

	private String descrizione;

	@Column(name="DIR_CSS")
	private String dirCss;

	@Column(name="DIR_IMAGES")
	private String dirImages;

	@Column(name="DIR_PDF")
	private String dirPdf;

	@Column(name="ID_AAO_DESTINATARIO")
	private String idAaoDestinatario;

	@Column(name="ID_ENTE")
	private String idEnte;

	@Column(name="ISTANZA_DB")
	private String istanzaDb;

	@Column(name="LINK_COMMAX_ENABLED")
	private String linkCommaxEnabled;

	@Column(name="LOG_ENABLED")
	private String logEnabled;

	@Column(name="MAIL_SERVER_NAME")
	private String mailServerName;

	@Column(name="MAX_NUMBER_OF_LINES")
	private String maxNumberOfLines;

	@Column(name="MAX_RECOVERY_NUMBER")
	private String maxRecoveryNumber;

	@Column(name="TIPO_DESTINATARIO")
	private String tipoDestinatario;

	@Column(name="TIPO_PROTOCOLLO")
	private String tipoProtocollo;

	@Column(name="URL_SITO_LAVORO")
	private String urlSitoLavoro;

	@Column(name="URL_SITO_PV")
	private String urlSitoPv;

	@Column(name="USER_MAIL_NAME")
	private String userMailName;

	@Column(name="USER_MAIL_PSW")
	private String userMailPsw;

	@Column(name="USER_SERVER_NAME")
	private String userServerName;

//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="personalizzazione")
//	private List<AnagraficaDelegato> anagraficaDelegatos;
//
//	//bi-directional many-to-one association to CaricaPersonaPv
//	@OneToMany(mappedBy="personalizzazione")
//	private List<CaricaPersonaPv> caricaPersonaPvs;
//
//	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="personalizzazione")
//	private List<Delega> delegas;
//
//	//bi-directional many-to-one association to ParametriTipoComunicazione
//	@OneToMany(mappedBy="personalizzazione")
//	private List<ParametriTipoComunicazione> parametriTipoComunicaziones;
//
//	//bi-directional many-to-one association to ParametriTipoDelega
//	@OneToMany(mappedBy="personalizzazione")
//	private List<ParametriTipoDelega> parametriTipoDelegas;

	//bi-directional one-to-one association to ComRPersonalizProvince
	@OneToOne
	@JoinColumn(name="PV")
	private ComRPersonalizProvince comRPersonalizProvince;

	public EntityPersonalizzazione() {
	}

	public String getPv() {
		return this.pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getAbilitaSceltaInoltroInail() {
		return this.abilitaSceltaInoltroInail;
	}

	public void setAbilitaSceltaInoltroInail(String abilitaSceltaInoltroInail) {
		this.abilitaSceltaInoltroInail = abilitaSceltaInoltroInail;
	}

	public String getAbilitaSceltaInoltroInps() {
		return this.abilitaSceltaInoltroInps;
	}

	public void setAbilitaSceltaInoltroInps(String abilitaSceltaInoltroInps) {
		this.abilitaSceltaInoltroInps = abilitaSceltaInoltroInps;
	}

	public String getCUtente() {
		return this.cUtente;
	}

	public void setCUtente(String cUtente) {
		this.cUtente = cUtente;
	}

	public String getDenDestinatario() {
		return this.denDestinatario;
	}

	public void setDenDestinatario(String denDestinatario) {
		this.denDestinatario = denDestinatario;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDirCss() {
		return this.dirCss;
	}

	public void setDirCss(String dirCss) {
		this.dirCss = dirCss;
	}

	public String getDirImages() {
		return this.dirImages;
	}

	public void setDirImages(String dirImages) {
		this.dirImages = dirImages;
	}

	public String getDirPdf() {
		return this.dirPdf;
	}

	public void setDirPdf(String dirPdf) {
		this.dirPdf = dirPdf;
	}

	public String getIdAaoDestinatario() {
		return this.idAaoDestinatario;
	}

	public void setIdAaoDestinatario(String idAaoDestinatario) {
		this.idAaoDestinatario = idAaoDestinatario;
	}

	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getIstanzaDb() {
		return this.istanzaDb;
	}

	public void setIstanzaDb(String istanzaDb) {
		this.istanzaDb = istanzaDb;
	}

	public String getLinkCommaxEnabled() {
		return this.linkCommaxEnabled;
	}

	public void setLinkCommaxEnabled(String linkCommaxEnabled) {
		this.linkCommaxEnabled = linkCommaxEnabled;
	}

	public String getLogEnabled() {
		return this.logEnabled;
	}

	public void setLogEnabled(String logEnabled) {
		this.logEnabled = logEnabled;
	}

	public String getMailServerName() {
		return this.mailServerName;
	}

	public void setMailServerName(String mailServerName) {
		this.mailServerName = mailServerName;
	}

	public String getMaxNumberOfLines() {
		return this.maxNumberOfLines;
	}

	public void setMaxNumberOfLines(String maxNumberOfLines) {
		this.maxNumberOfLines = maxNumberOfLines;
	}

	public String getMaxRecoveryNumber() {
		return this.maxRecoveryNumber;
	}

	public void setMaxRecoveryNumber(String maxRecoveryNumber) {
		this.maxRecoveryNumber = maxRecoveryNumber;
	}

	public String getTipoDestinatario() {
		return this.tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public String getTipoProtocollo() {
		return this.tipoProtocollo;
	}

	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}

	public String getUrlSitoLavoro() {
		return this.urlSitoLavoro;
	}

	public void setUrlSitoLavoro(String urlSitoLavoro) {
		this.urlSitoLavoro = urlSitoLavoro;
	}

	public String getUrlSitoPv() {
		return this.urlSitoPv;
	}

	public void setUrlSitoPv(String urlSitoPv) {
		this.urlSitoPv = urlSitoPv;
	}

	public String getUserMailName() {
		return this.userMailName;
	}

	public void setUserMailName(String userMailName) {
		this.userMailName = userMailName;
	}

	public String getUserMailPsw() {
		return this.userMailPsw;
	}

	public void setUserMailPsw(String userMailPsw) {
		this.userMailPsw = userMailPsw;
	}

	public String getUserServerName() {
		return this.userServerName;
	}

	public void setUserServerName(String userServerName) {
		this.userServerName = userServerName;
	}

//	public List<AnagraficaDelegato> getAnagraficaDelegatos() {
//		return this.anagraficaDelegatos;
//	}
//
//	public void setAnagraficaDelegatos(List<AnagraficaDelegato> anagraficaDelegatos) {
//		this.anagraficaDelegatos = anagraficaDelegatos;
//	}
//
//	public AnagraficaDelegato addAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
//		getAnagraficaDelegatos().add(anagraficaDelegato);
//		anagraficaDelegato.setPersonalizzazione(this);
//
//		return anagraficaDelegato;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
//		getAnagraficaDelegatos().remove(anagraficaDelegato);
//		anagraficaDelegato.setPersonalizzazione(null);
//
//		return anagraficaDelegato;
//	}

//	public List<CaricaPersonaPv> getCaricaPersonaPvs() {
//		return this.caricaPersonaPvs;
//	}
//
//	public void setCaricaPersonaPvs(List<CaricaPersonaPv> caricaPersonaPvs) {
//		this.caricaPersonaPvs = caricaPersonaPvs;
//	}
//
//	public CaricaPersonaPv addCaricaPersonaPv(CaricaPersonaPv caricaPersonaPv) {
//		getCaricaPersonaPvs().add(caricaPersonaPv);
//		caricaPersonaPv.setPersonalizzazione(this);
//
//		return caricaPersonaPv;
//	}
//
//	public CaricaPersonaPv removeCaricaPersonaPv(CaricaPersonaPv caricaPersonaPv) {
//		getCaricaPersonaPvs().remove(caricaPersonaPv);
//		caricaPersonaPv.setPersonalizzazione(null);
//
//		return caricaPersonaPv;
//	}

//	public List<Delega> getDelegas() {
//		return this.delegas;
//	}
//
//	public void setDelegas(List<Delega> delegas) {
//		this.delegas = delegas;
//	}
//
//	public Delega addDelega(Delega delega) {
//		getDelegas().add(delega);
//		delega.setPersonalizzazione(this);
//
//		return delega;
//	}
//
//	public Delega removeDelega(Delega delega) {
//		getDelegas().remove(delega);
//		delega.setPersonalizzazione(null);
//
//		return delega;
//	}

//	public List<ParametriTipoComunicazione> getParametriTipoComunicaziones() {
//		return this.parametriTipoComunicaziones;
//	}
//
//	public void setParametriTipoComunicaziones(List<ParametriTipoComunicazione> parametriTipoComunicaziones) {
//		this.parametriTipoComunicaziones = parametriTipoComunicaziones;
//	}
//
//	public ParametriTipoComunicazione addParametriTipoComunicazione(ParametriTipoComunicazione parametriTipoComunicazione) {
//		getParametriTipoComunicaziones().add(parametriTipoComunicazione);
//		parametriTipoComunicazione.setPersonalizzazione(this);
//
//		return parametriTipoComunicazione;
//	}
//
//	public ParametriTipoComunicazione removeParametriTipoComunicazione(ParametriTipoComunicazione parametriTipoComunicazione) {
//		getParametriTipoComunicaziones().remove(parametriTipoComunicazione);
//		parametriTipoComunicazione.setPersonalizzazione(null);
//
//		return parametriTipoComunicazione;
//	}

//	public List<ParametriTipoDelega> getParametriTipoDelegas() {
//		return this.parametriTipoDelegas;
//	}
//
//	public void setParametriTipoDelegas(List<ParametriTipoDelega> parametriTipoDelegas) {
//		this.parametriTipoDelegas = parametriTipoDelegas;
//	}
//
//	public ParametriTipoDelega addParametriTipoDelega(ParametriTipoDelega parametriTipoDelega) {
//		getParametriTipoDelegas().add(parametriTipoDelega);
//		parametriTipoDelega.setPersonalizzazione(this);
//
//		return parametriTipoDelega;
//	}
//
//	public ParametriTipoDelega removeParametriTipoDelega(ParametriTipoDelega parametriTipoDelega) {
//		getParametriTipoDelegas().remove(parametriTipoDelega);
//		parametriTipoDelega.setPersonalizzazione(null);
//
//		return parametriTipoDelega;
//	}

	public ComRPersonalizProvince getComRPersonalizProvince() {
		return this.comRPersonalizProvince;
	}

	public void setComRPersonalizProvince(ComRPersonalizProvince comRPersonalizProvince) {
		this.comRPersonalizProvince = comRPersonalizProvince;
	}

}
