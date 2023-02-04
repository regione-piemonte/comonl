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
package it.csi.comonl.comonlweb.srv.util.filter.auth;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import it.csi.comonl.comonlweb.lib.dto.Utente;

/**
 * Autentication adapter
 */
public interface AuthAdapter {
	/**
	 * Processes the authentication
	 * @param devMode dev mode
	 * @param uriInfo the URI info
	 * @param containerRequest the container request
	 * @return the user associated with the context
	 */
	Utente processAuth(boolean devMode, UriInfo uriInfo, ContainerRequestContext containerRequest);
}
