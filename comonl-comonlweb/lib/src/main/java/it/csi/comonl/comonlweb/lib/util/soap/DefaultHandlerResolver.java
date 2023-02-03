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
package it.csi.comonl.comonlweb.lib.util.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.apache.logging.log4j.Level;

/**
 * JAX-WS HandlerResolver for injecting the Logger
 */
public class DefaultHandlerResolver implements HandlerResolver {
	
	private final Level logLevel;
	
	/**
	 * Injection constructor
	 * @param logLevel the log level
	 */
	public DefaultHandlerResolver(Level logLevel) {
		this.logLevel = logLevel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> chain = new ArrayList<>();

		// Add possibly other handlers to the chain
		chain.add(new LogSOAPHandler(logLevel));
		
		addOtherHandlers(chain);

		return chain;
	}

	/**
	 * Add other handlers to the handlerChain
	 * @param chain the chain
	 */
	@SuppressWarnings("rawtypes")
	protected void addOtherHandlers(List<Handler> chain) {
		// May be implemented in subclasses
	}

}
