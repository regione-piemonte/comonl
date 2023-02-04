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

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Auth adapter for IRIDE
 */
@ApplicationScoped
public class TestAuthAdapter implements AuthAdapter {

	private static final LogUtil LOG = new LogUtil(TestAuthAdapter.class);

	@Override
	public Utente processAuth(boolean devMode, UriInfo uriInfo, ContainerRequestContext containerRequest) {
		final String methodName = "processAuth";

		Utente utente = new Utente();
		utente.setCodiceFiscale("AAAAAA00A11B000J");
		utente.setNome("pro");
		utente.setCognome("logic");
		utente.setId(new Random().nextInt());

		return utente;
	}

}
