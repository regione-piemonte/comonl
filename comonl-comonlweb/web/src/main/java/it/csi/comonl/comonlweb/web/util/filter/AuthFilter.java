/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.web.util.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.web.util.filter.auth.AuthAdapter;
import it.csi.comonl.comonlweb.web.util.filter.auth.NullAuthAdapter;

/**
 * Inserisce nel SecurityContext e in un cookie:
 * <ul>
 * <li>l'identit&agrave; digitale relativa all'utente autenticato.</li>
 * <li>l'oggetto <code>currentUser</code></li>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticazione previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

	/** Logger */
	private static final LogUtil LOG = new LogUtil(AuthFilter.class);

	private volatile boolean initialized = false;
	private boolean devMode = false;
	private AuthAdapter authAdapter;

	@Inject
	private ConfigurationHelper configurationHelper;
	@Context
	private UriInfo uriInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		final String methodName = "filter";
		try {
			init();
			Utente utente = authAdapter.processAuth(devMode, uriInfo, requestContext);
			if (utente == null) {
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
				return;
			}
			// TODO: impostare l'utente nel security context
			elaborateSecurityContext(utente);

		} catch (Exception e) {
			LOG.error(methodName, e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Initialization
	 */
	private void init() {
		if (!initialized) {
			synchronized (AuthFilter.class) {
				if (!initialized) {
					devMode = Boolean
							.parseBoolean(configurationHelper.getProperty(ConfigurationValue.AUTHFILTER_DEVMODE));
					String authAdapterName = configurationHelper
							.getProperty(ConfigurationValue.AUTHFILTER_AUTHADAPTERNAME);
					try {
						Class<?> authAdapterClass = Class.forName(authAdapterName);
						authAdapter = (AuthAdapter) CDI.current().select(authAdapterClass).get();
					} catch (Throwable t) {
						LOG.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", t);
//						cnfe.printStackTrace();
						authAdapter = new NullAuthAdapter();
					}
					initialized = true;
				}
			}
		}
	}

	/**
	 * Elabora l'uso dell'utente per il SecurityContext
	 * 
	 * @param utente l'utente da controllare
	 */
	private void elaborateSecurityContext(Utente utente) {
		// Init thread local with ejb module
		ComonlThreadLocalContainer.UTENTE_CONNESSO.set(utente);
	}

}
