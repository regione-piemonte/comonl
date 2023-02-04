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
package it.csi.comonl.comonlweb.srv.util.filter;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Filter for CORS handling
 * @author Marchino Alessandro
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

	/** The default allowed methods */
	private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH";
	/** The default allowed headers */
	private static final String DEFAULT_ALLOWED_HEADERS = "Origin, Accept, Content-Type, Authorization, X-Requested-With, Access-Control-Request-Method, Access-Control-Request-Headers, X-SETTORE, X-HTTP-Method-Override";
	/** The default exposed headers */
	private static final String DEFAULT_EXPOSED_HEADERS = "Location, Info, Content-Disposition";
	/** The max age of the request */
	private static final Integer MAX_AGE = Integer.valueOf(42 * 60 * 60);
	/** Logger */
	private static final LogUtil LOG = new LogUtil(CorsFilter.class);
	
	// HEADER NAMES
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

	@Inject private ConfigurationHelper configurationHelper;
	private volatile boolean initialized = false;
	private boolean enableCors;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		final String methodName = "filter";
		try {
			if(!isCorsEnabled()) {
				LOG.trace(methodName, "Cors filter not enabled. Skip...");
				return;
			}
			
			if(HttpMethod.OPTIONS.equals(requestContext.getMethod())) {
				LOG.trace(methodName, "OPTIONS always accepted");
				responseContext.setStatus(Status.ACCEPTED.getStatusCode());
				// responseContext.setStatus(Status.OK.getStatusCode());
			}
			
			responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
			responseContext.getHeaders().add("Access-Control-Allow-Methods", ALLOWED_METHODS);
			responseContext.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS, getRequestedAllowedHeaders(requestContext));
			responseContext.getHeaders().add(ACCESS_CONTROL_EXPOSE_HEADERS, getRequestedExposeHeaders(requestContext));
			responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
			responseContext.getHeaders().add("Access-Control-Max-Age", String.valueOf(MAX_AGE));
			responseContext.getHeaders().add("X-Responded-By", "cors-response-filter");
			
		} catch (Exception e) {
			LOG.error(methodName, e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * Checks whether the CORS are enabled
	 * @return the enableCors data
	 */
	private boolean isCorsEnabled() {
		if(!initialized) {
			synchronized(CorsFilter.class) {
				if(!initialized) {
					enableCors = Boolean.parseBoolean(configurationHelper.getProperty(ConfigurationValue.CORSFILTER_ENABLECORS));
					initialized = true;
				}
			}
		}
		
		return enableCors;
	}

	/**
	 * Gets the allowed headers
	 * @param requestContext the request context
	 * @return the header
	 */
	private String getRequestedAllowedHeaders(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(ACCESS_CONTROL_ALLOW_HEADERS);
		return createHeaderList(headers, DEFAULT_ALLOWED_HEADERS);
	}

	/**
	 * Gets the exposed headers
	 * @param requestContext the request context
	 * @return the header
	 */
	private String getRequestedExposeHeaders(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(ACCESS_CONTROL_EXPOSE_HEADERS);
		return createHeaderList(headers, DEFAULT_EXPOSED_HEADERS);
	}

	/**
	 * Creates the list of headers
	 * @param headers the headers
	 * @param defaultHeaders the default headers to add
	 * @return the header
	 */
	private String createHeaderList(List<String> headers, String defaultHeaders) {
		StringJoiner joiner = new StringJoiner(",");
		if(headers != null) {
			for(String header : headers) {
				joiner.add(header);
			}
		}
		joiner.add(defaultHeaders);
		return joiner.toString();
	}

}
