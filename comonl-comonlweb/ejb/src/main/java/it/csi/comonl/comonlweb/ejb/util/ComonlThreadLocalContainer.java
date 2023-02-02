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
package it.csi.comonl.comonlweb.ejb.util;

import it.csi.iride2.policy.entity.Identita;
import it.csi.comonl.comonlweb.lib.dto.Utente;

/**
 * Thead local container
 */
public class ComonlThreadLocalContainer {
	/** Contains the connected user */
	public static final ThreadLocal<Utente> UTENTE_CONNESSO = new ThreadLocal<>();
	public static final ThreadLocal<Identita> IDENTITA = new ThreadLocal<>();
	
	/** Private constructor */
	private ComonlThreadLocalContainer() {
		// Prevent instantiation
	}

	/**
	 * Cleanup of the thread locals
	 */
	public static void cleanup() {
		UTENTE_CONNESSO.remove();
		IDENTITA.remove();
	}

}
