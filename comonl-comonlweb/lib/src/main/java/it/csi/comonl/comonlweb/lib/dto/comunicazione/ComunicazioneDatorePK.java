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
 * The Class ComunicazioneDatorePK.
 */
public class ComunicazioneDatorePK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idComDComunicazione;
	private long idComDDatore;

	/**
	 * @return the idComDComunicazione
	 */
	public long getIdComDComunicazione() {
		return idComDComunicazione;
	}
	
	/**
	 * @param idComDComunicazione the idComDComunicazione to set
	 */
	public void setIdComDComunicazione(long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	/**
	 * @return the idComDDatore
	 */
	public long getIdComDDatore() {
		return idComDDatore;
	}
	
	/**
	 * @param idComDDatore the idComDDatore to set
	 */
	public void setIdComDDatore(long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}

}
