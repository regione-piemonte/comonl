/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class Personalizzazione.
 */
public class Personalizzazione extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String abilitaSceltaInoltroInail;
	private String abilitaSceltaInoltroInps;
	private String cUtente;
	private String denDestinatario;
	private String descrizione;
	private String dirCss;
	private String dirImages;
	private String dirPdf;
	private String idAaoDestinatario;
	private String idEnte;
	private String istanzaDb;
	private String linkCommaxEnabled;
	private String logEnabled;
	private String mailServerName;
	private String maxNumberOfLines;
	private String maxRecoveryNumber;
	private String tipoDestinatario;
	private String tipoProtocollo;
	private String urlSitoLavoro;
	private String urlSitoPv;
	private String userMailName;
	private String userMailPsw;
	private String userServerName;
	private PersonalizProvince personalizProvince;

	/**
	 * @return the abilitaSceltaInoltroInail
	 */
	public String getAbilitaSceltaInoltroInail() {
		return abilitaSceltaInoltroInail;
	}
	
	/**
	 * @param abilitaSceltaInoltroInail the abilitaSceltaInoltroInail to set
	 */
	public void setAbilitaSceltaInoltroInail(String abilitaSceltaInoltroInail) {
		this.abilitaSceltaInoltroInail = abilitaSceltaInoltroInail;
	}

	/**
	 * @return the abilitaSceltaInoltroInps
	 */
	public String getAbilitaSceltaInoltroInps() {
		return abilitaSceltaInoltroInps;
	}
	
	/**
	 * @param abilitaSceltaInoltroInps the abilitaSceltaInoltroInps to set
	 */
	public void setAbilitaSceltaInoltroInps(String abilitaSceltaInoltroInps) {
		this.abilitaSceltaInoltroInps = abilitaSceltaInoltroInps;
	}

	/**
	 * @return the cUtente
	 */
	public String getCUtente() {
		return cUtente;
	}
	
	/**
	 * @param cUtente the cUtente to set
	 */
	public void setCUtente(String cUtente) {
		this.cUtente = cUtente;
	}

	/**
	 * @return the denDestinatario
	 */
	public String getDenDestinatario() {
		return denDestinatario;
	}
	
	/**
	 * @param denDestinatario the denDestinatario to set
	 */
	public void setDenDestinatario(String denDestinatario) {
		this.denDestinatario = denDestinatario;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * @return the dirCss
	 */
	public String getDirCss() {
		return dirCss;
	}
	
	/**
	 * @param dirCss the dirCss to set
	 */
	public void setDirCss(String dirCss) {
		this.dirCss = dirCss;
	}

	/**
	 * @return the dirImages
	 */
	public String getDirImages() {
		return dirImages;
	}
	
	/**
	 * @param dirImages the dirImages to set
	 */
	public void setDirImages(String dirImages) {
		this.dirImages = dirImages;
	}

	/**
	 * @return the dirPdf
	 */
	public String getDirPdf() {
		return dirPdf;
	}
	
	/**
	 * @param dirPdf the dirPdf to set
	 */
	public void setDirPdf(String dirPdf) {
		this.dirPdf = dirPdf;
	}

	/**
	 * @return the idAaoDestinatario
	 */
	public String getIdAaoDestinatario() {
		return idAaoDestinatario;
	}
	
	/**
	 * @param idAaoDestinatario the idAaoDestinatario to set
	 */
	public void setIdAaoDestinatario(String idAaoDestinatario) {
		this.idAaoDestinatario = idAaoDestinatario;
	}

	/**
	 * @return the idEnte
	 */
	public String getIdEnte() {
		return idEnte;
	}
	
	/**
	 * @param idEnte the idEnte to set
	 */
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	/**
	 * @return the istanzaDb
	 */
	public String getIstanzaDb() {
		return istanzaDb;
	}
	
	/**
	 * @param istanzaDb the istanzaDb to set
	 */
	public void setIstanzaDb(String istanzaDb) {
		this.istanzaDb = istanzaDb;
	}

	/**
	 * @return the linkCommaxEnabled
	 */
	public String getLinkCommaxEnabled() {
		return linkCommaxEnabled;
	}
	
	/**
	 * @param linkCommaxEnabled the linkCommaxEnabled to set
	 */
	public void setLinkCommaxEnabled(String linkCommaxEnabled) {
		this.linkCommaxEnabled = linkCommaxEnabled;
	}

	/**
	 * @return the logEnabled
	 */
	public String getLogEnabled() {
		return logEnabled;
	}
	
	/**
	 * @param logEnabled the logEnabled to set
	 */
	public void setLogEnabled(String logEnabled) {
		this.logEnabled = logEnabled;
	}

	/**
	 * @return the mailServerName
	 */
	public String getMailServerName() {
		return mailServerName;
	}
	
	/**
	 * @param mailServerName the mailServerName to set
	 */
	public void setMailServerName(String mailServerName) {
		this.mailServerName = mailServerName;
	}

	/**
	 * @return the maxNumberOfLines
	 */
	public String getMaxNumberOfLines() {
		return maxNumberOfLines;
	}
	
	/**
	 * @param maxNumberOfLines the maxNumberOfLines to set
	 */
	public void setMaxNumberOfLines(String maxNumberOfLines) {
		this.maxNumberOfLines = maxNumberOfLines;
	}

	/**
	 * @return the maxRecoveryNumber
	 */
	public String getMaxRecoveryNumber() {
		return maxRecoveryNumber;
	}
	
	/**
	 * @param maxRecoveryNumber the maxRecoveryNumber to set
	 */
	public void setMaxRecoveryNumber(String maxRecoveryNumber) {
		this.maxRecoveryNumber = maxRecoveryNumber;
	}

	/**
	 * @return the tipoDestinatario
	 */
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	
	/**
	 * @param tipoDestinatario the tipoDestinatario to set
	 */
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	/**
	 * @return the tipoProtocollo
	 */
	public String getTipoProtocollo() {
		return tipoProtocollo;
	}
	
	/**
	 * @param tipoProtocollo the tipoProtocollo to set
	 */
	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}

	/**
	 * @return the urlSitoLavoro
	 */
	public String getUrlSitoLavoro() {
		return urlSitoLavoro;
	}
	
	/**
	 * @param urlSitoLavoro the urlSitoLavoro to set
	 */
	public void setUrlSitoLavoro(String urlSitoLavoro) {
		this.urlSitoLavoro = urlSitoLavoro;
	}

	/**
	 * @return the urlSitoPv
	 */
	public String getUrlSitoPv() {
		return urlSitoPv;
	}
	
	/**
	 * @param urlSitoPv the urlSitoPv to set
	 */
	public void setUrlSitoPv(String urlSitoPv) {
		this.urlSitoPv = urlSitoPv;
	}

	/**
	 * @return the userMailName
	 */
	public String getUserMailName() {
		return userMailName;
	}
	
	/**
	 * @param userMailName the userMailName to set
	 */
	public void setUserMailName(String userMailName) {
		this.userMailName = userMailName;
	}

	/**
	 * @return the userMailPsw
	 */
	public String getUserMailPsw() {
		return userMailPsw;
	}
	
	/**
	 * @param userMailPsw the userMailPsw to set
	 */
	public void setUserMailPsw(String userMailPsw) {
		this.userMailPsw = userMailPsw;
	}

	/**
	 * @return the userServerName
	 */
	public String getUserServerName() {
		return userServerName;
	}
	
	/**
	 * @param userServerName the userServerName to set
	 */
	public void setUserServerName(String userServerName) {
		this.userServerName = userServerName;
	}

	/**
	 * @return the personalizProvince
	 */
	public PersonalizProvince getPersonalizProvince() {
		return personalizProvince;
	}
	
	/**
	 * @param personalizProvince the personalizProvince to set
	 */
	public void setPersonalizProvince(PersonalizProvince personalizProvince) {
		this.personalizProvince = personalizProvince;
	}

}
