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
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;

/**
 * The Class WrapperComunicazione.
 */
public class WrapperLavoratoreSilpEspanso extends BaseWrapper implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private LavoratoreSilpEspanso lavoratoreSilpEspanso;
	private Comunicazione comunicazione;
	

	public LavoratoreSilpEspanso getLavoratoreSilpEspanso() {
		return lavoratoreSilpEspanso;
	}

	public void setLavoratoreSilpEspanso(LavoratoreSilpEspanso lavoratoreSilpEspanso) {
		this.lavoratoreSilpEspanso = lavoratoreSilpEspanso;
	}



	public Comunicazione getComunicazione() {
		return comunicazione;
	}

	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}
}
