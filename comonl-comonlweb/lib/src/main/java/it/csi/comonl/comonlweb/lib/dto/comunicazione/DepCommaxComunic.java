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
 * The Class DepCommaxComunic.
 */
public class DepCommaxComunic extends BaseDto<DepCommaxComunicPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codiceComunicazioneReg;
	private String flgProtGestCorrettamente;
	private Comunicazione comunicazione;
	private UplDocumenti uplDocumenti;

	/**
	 * @return the codiceComunicazioneReg
	 */
	public String getCodiceComunicazioneReg() {
		return codiceComunicazioneReg;
	}
	
	/**
	 * @param codiceComunicazioneReg the codiceComunicazioneReg to set
	 */
	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	/**
	 * @return the flgProtGestCorrettamente
	 */
	public String getFlgProtGestCorrettamente() {
		return flgProtGestCorrettamente;
	}
	
	/**
	 * @param flgProtGestCorrettamente the flgProtGestCorrettamente to set
	 */
	public void setFlgProtGestCorrettamente(String flgProtGestCorrettamente) {
		this.flgProtGestCorrettamente = flgProtGestCorrettamente;
	}

	/**
	 * @return the comunicazione
	 */
	public Comunicazione getComunicazione() {
		return comunicazione;
	}
	
	/**
	 * @param comunicazione the comunicazione to set
	 */
	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
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
