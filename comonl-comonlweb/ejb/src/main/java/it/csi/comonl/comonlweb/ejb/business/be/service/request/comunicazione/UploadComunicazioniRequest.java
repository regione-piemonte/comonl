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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.FileHolder;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

public class UploadComunicazioniRequest implements BaseRequest {

	private final byte[] fileInByte;
	private final String email;
	private final String nomeFile;
	private final Boolean isVerifica;
	
	private final Ruolo ruolo;

	public UploadComunicazioniRequest(FileHolder fileHolder, Ruolo ruolo) {
		this.fileInByte = fileHolder.getAttachment();
		this.email = fileHolder.getEmail();
		this.nomeFile = fileHolder.getNomeFile();
		this.isVerifica = fileHolder.getIsVerifica();
		this.ruolo = ruolo;
	}

	public byte[] getFileInByte() {
		return fileInByte;
	}

	public String getEmail() {
		return email;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public Boolean getIsVerifica() {
		return isVerifica;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}
	
	
	
}
