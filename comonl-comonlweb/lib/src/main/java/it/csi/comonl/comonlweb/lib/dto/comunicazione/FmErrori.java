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
 * The Class FmErrori.
 */
public class FmErrori extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String identificativoAnnoNum;
	private String nomeFileXmlScartato;
	private String tipoScarto;
	private UplDocumenti uplDocumenti;

	/**
	 * @return the identificativoAnnoNum
	 */
	public String getIdentificativoAnnoNum() {
		return identificativoAnnoNum;
	}
	
	/**
	 * @param identificativoAnnoNum the identificativoAnnoNum to set
	 */
	public void setIdentificativoAnnoNum(String identificativoAnnoNum) {
		this.identificativoAnnoNum = identificativoAnnoNum;
	}

	/**
	 * @return the nomeFileXmlScartato
	 */
	public String getNomeFileXmlScartato() {
		return nomeFileXmlScartato;
	}
	
	/**
	 * @param nomeFileXmlScartato the nomeFileXmlScartato to set
	 */
	public void setNomeFileXmlScartato(String nomeFileXmlScartato) {
		this.nomeFileXmlScartato = nomeFileXmlScartato;
	}

	/**
	 * @return the tipoScarto
	 */
	public String getTipoScarto() {
		return tipoScarto;
	}
	
	/**
	 * @param tipoScarto the tipoScarto to set
	 */
	public void setTipoScarto(String tipoScarto) {
		this.tipoScarto = tipoScarto;
	}

	/**
	 * @return the uplDocumenti
	 */
	public UplDocumenti getUplDocumenti() {
		return uplDocumenti;
	}
	
	/**
	 * @param uplDocumenti the uplDocumenti to set
	 */
	public void setUplDocumenti(UplDocumenti uplDocumenti) {
		this.uplDocumenti = uplDocumenti;
	}

}
