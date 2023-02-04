/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.web.dto;

import javax.ws.rs.FormParam;

import io.swagger.annotations.ApiModelProperty;
import it.csi.comonl.comonlweb.lib.dto.FileHolder;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

/**
 * File holder for Web
 */
public class WebComunicazioneFileHolder {

	@FormParam("attachment")
	private byte[] attachment;
	@FormParam("email")
	private String email;
	@FormParam("nomeFile")
	private String nomeFile;
	@FormParam("isVerifica")
	private Boolean isVerifica;
	@FormParam("ilRuolo")
	private String ilRuolo;
	@FormParam("codiceFiscaleUtente")
	private String codiceFiscaleUtente;
	@FormParam("dsCognome")
	private String dsCognome;
	@FormParam("dsNome")
	private String dsNome;
	@FormParam("codiceFiscaleAzienda")
	private String codiceFiscaleAzienda;
	@FormParam("consulenteRespo")
	private Boolean consulenteRespo;
	@FormParam("isAmministratore")
	private Boolean isAmministratore;
	
	@FormParam("isOperatoreProvinciale")
	private Boolean isOperatoreProvinciale;
	@FormParam("isDelegatoRespo")
	private Boolean isDelegatoRespo;
	@FormParam("isLegaleRappresentante")
	private Boolean isLegaleRappresentante;
	@FormParam("isPersonaAutorizzata")
	private Boolean isPersonaAutorizzata;
	@FormParam("emailRuolo")
	private String emailRuolo;

	/**
	 * @return the attachment
	 */
	public byte[] getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	
	

	/**
	 * To a EJB file holder
	 * 
	 * @return the file holder
	 */
	public FileHolder toFileHolder() {
		FileHolder fh = new FileHolder();
		fh.setAttachment(attachment);
		fh.setEmail(email);
		fh.setNomeFile(nomeFile);
		fh.setIsVerifica(isVerifica);
		return fh;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Boolean getIsVerifica() {
		return isVerifica;
	}

	public void setIsVerifica(Boolean isVerifica) {
		this.isVerifica = isVerifica;
	}

	public String getIlRuolo() {
		return ilRuolo;
	}

	public void setIlRuolo(String ilRuolo) {
		this.ilRuolo = ilRuolo;
	}

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public String getDsCognome() {
		return dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getCodiceFiscaleAzienda() {
		return codiceFiscaleAzienda;
	}

	public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda) {
		this.codiceFiscaleAzienda = codiceFiscaleAzienda;
	}

	public Boolean getConsulenteRespo() {
		return consulenteRespo;
	}

	public void setConsulenteRespo(Boolean consulenteRespo) {
		this.consulenteRespo = consulenteRespo;
	}

	public Boolean getIsAmministratore() {
		return isAmministratore;
	}

	public void setIsAmministratore(Boolean isAmministratore) {
		this.isAmministratore = isAmministratore;
	}

	public Boolean getIsOperatoreProvinciale() {
		return isOperatoreProvinciale;
	}

	public void setIsOperatoreProvinciale(Boolean isOperatoreProvinciale) {
		this.isOperatoreProvinciale = isOperatoreProvinciale;
	}

	public Boolean getIsDelegatoRespo() {
		return isDelegatoRespo;
	}

	public void setIsDelegatoRespo(Boolean isDelegatoRespo) {
		this.isDelegatoRespo = isDelegatoRespo;
	}

	public Boolean getIsLegaleRappresentante() {
		return isLegaleRappresentante;
	}

	public void setIsLegaleRappresentante(Boolean isLegaleRappresentante) {
		this.isLegaleRappresentante = isLegaleRappresentante;
	}

	public Boolean getIsPersonaAutorizzata() {
		return isPersonaAutorizzata;
	}

	public void setIsPersonaAutorizzata(Boolean isPersonaAutorizzata) {
		this.isPersonaAutorizzata = isPersonaAutorizzata;
	}

	public String getEmailRuolo() {
		return emailRuolo;
	}

	public void setEmailRuolo(String emailRuolo) {
		this.emailRuolo = emailRuolo;
	}
}
