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

import it.csi.comonl.comonlweb.lib.dto.BaseWrapper;

/**
 * The Class WrapperComunicazione.
 */
public class WrapperTutore extends BaseWrapper implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idComunicazione;
	private Tutore tutore;
	
	public long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public Tutore getTutore() {
		return tutore;
	}
	public void setTutore(Tutore tutore) {
		this.tutore = tutore;
	}
	

}
