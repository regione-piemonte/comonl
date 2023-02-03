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

public class ResponseRecuperoComunicazioni implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RecuperoComunicazione recuperoComunicazione;

	public RecuperoComunicazione getRecuperoComunicazione() {
		return recuperoComunicazione;
	}

	public void setRecuperoComunicazione(RecuperoComunicazione recuperoComunicazione) {
		this.recuperoComunicazione = recuperoComunicazione;
	}
	
	
	
}
