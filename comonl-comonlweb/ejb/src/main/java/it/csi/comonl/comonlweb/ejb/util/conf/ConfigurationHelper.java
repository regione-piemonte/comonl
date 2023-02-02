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
package it.csi.comonl.comonlweb.ejb.util.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.servlet.ServletContext;

/**
 * Helper for the configuration handling
 */
@Singleton
public class ConfigurationHelper {

	/** The underlying properties */
	private final Properties properties = new Properties();
	private volatile boolean initialized = false;

	/**
	 * Initialization of the properties
	 * @param servletContext the servlet context
	 */
	public void initializeProperties(ServletContext servletContext) {
		if(initialized) {
			return;
		}
		initialized = true;
		
		String configurationPath = "/WEB-INF/classes/configuration";
			
		Optional.ofNullable(servletContext.getResourcePaths(configurationPath))
			.orElseGet(HashSet<String>::new)
			.stream()
			.forEach(file -> loadPropertyFile(servletContext, file));
		
		//printProperties();
		
	}
	
	@SuppressWarnings("unused")
	private void printProperties() {
		
		System.out.println("=================================================================");
		System.out.println("PROPERTIES DI SISTEMA");
		for (Object key: properties.keySet()) {
            System.out.println(key + ": " + properties.getProperty(key.toString()));
        }
		System.out.println("=================================================================");
	}

	/**
	 * Property file loading
	 * @param servletContext the servlet context
	 * @param fileName the file name
	 */
	private void loadPropertyFile(ServletContext servletContext, String fileName) {
		
		//System.out.println("PROPERTIES FILES:" + fileName);
		try(InputStream applicationPropertiesStream = servletContext.getResourceAsStream(fileName)) {
			properties.load(applicationPropertiesStream);
		} catch (IOException e) {
			throw new IllegalStateException("Properties file \"" + fileName + "\" cannot be loaded");
		} catch (NullPointerException npe) {
			// Ignore
			//npe.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a property
	 * @param value the property name
	 * @return the value
	 */
	public String getProperty(ConfigurationValue value) {
		return properties.getProperty(value.getPropertyName());
	}
}
