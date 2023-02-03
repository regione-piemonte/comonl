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
 * The Class CaricaPersonaPv.
 */
public class CaricaPersonaPv extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String descrizione;
	private Personalizzazione personalizzazione;

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
	 * @return the personalizzazione
	 */
	public Personalizzazione getPersonalizzazione() {
		return personalizzazione;
	}
	
	/**
	 * @param personalizzazione the personalizzazione to set
	 */
	public void setPersonalizzazione(Personalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

}
