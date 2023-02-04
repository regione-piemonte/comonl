/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.dto;

import javax.ws.rs.FormParam;

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


}
