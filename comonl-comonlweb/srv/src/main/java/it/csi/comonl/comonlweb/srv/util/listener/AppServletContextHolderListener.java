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
package it.csi.comonl.comonlweb.srv.util.listener;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Holder for the ServletContext
 */
@WebListener
public class AppServletContextHolderListener implements ServletContextListener {

	private static ServletContext sc;
	@Inject private ConfigurationHelper configurationHelper;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// Clean up the saved context
		AppServletContextHolderListener.updateServletContext(null);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// Sets the updated servlet context
		AppServletContextHolderListener.updateServletContext(servletContextEvent.getServletContext());
		configurationHelper.initializeProperties(servletContextEvent.getServletContext());
	}

	/**
	 * Returns the common servlet context
	 * @return the servlet context
	 */
	public static ServletContext getServletContext() {
		return sc;
	}

	/**
	 * Update method.
	 * <p>This method synchronizes updates to the static variable in the holder
	 * @param newValue the new ServletContext
	 */
	private static synchronized void updateServletContext(ServletContext newValue) {
		AppServletContextHolderListener.sc = newValue;
	}
}
