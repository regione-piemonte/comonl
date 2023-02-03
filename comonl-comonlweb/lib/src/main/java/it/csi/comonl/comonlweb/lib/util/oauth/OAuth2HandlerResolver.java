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
package it.csi.comonl.comonlweb.lib.util.oauth;

import java.util.List;

import javax.xml.ws.handler.Handler;

import org.apache.logging.log4j.Level;

import it.csi.comonl.comonlweb.lib.util.soap.DefaultHandlerResolver;

/**
 * JAX-WS HandlerResolver for injecting OAuth2 headers
 */
public class OAuth2HandlerResolver extends DefaultHandlerResolver {
	
	private final OAuth2Helper oAuth2Helper;
	
	/**
	 * Injection constructor
	 * @param oAuth2Helper the helper
	 * @param logLevel the log level
	 */
	public OAuth2HandlerResolver(Level logLevel, OAuth2Helper oAuth2Helper) {
		super(logLevel);
		this.oAuth2Helper = oAuth2Helper;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void addOtherHandlers(List<Handler> chain) {
		chain.add(new OAuth2SOAPHandler(oAuth2Helper));
	}

}
