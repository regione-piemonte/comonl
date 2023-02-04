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
package it.csi.comonl.comonlweb.web.util.rest.exception;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Exception mapper helper
 */
public class ExceptionMapperHelper {
	/** The logger */
	protected final LogUtil log = new LogUtil(getClass());

	private final int status;
	private final HttpServletResponse httpServletResponse;
	private final boolean debugMode;

	/**
	 * Constructor
	 * @param configurationHelper the helper
	 * @param httpServletResponse the HTTP servlet response
	 * @param status the status to set
	 */
	public ExceptionMapperHelper(ConfigurationHelper configurationHelper, HttpServletResponse httpServletResponse, int status) {
		this.status = status;
		this.httpServletResponse = httpServletResponse;
		this.debugMode = "true".equals(configurationHelper.getProperty(ConfigurationValue.DEBUG_MODE));
	}

	/**
	 * Converts the exception to a response
	 * @param exception the exception
	 * @return the response
	 * @deprecated usare {@link #toResponse(ApiError...)}
	 */
	@Deprecated
	public Response toResponse(Throwable exception) {
		ResponseBuilder builder = Response
				.status(status)
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.entity(JsonUtility.parseThrowable(exception, debugMode).toString());

		Collection<String> headerNames = httpServletResponse.getHeaderNames();
		for(String headerName : headerNames) {
			Collection<String> headers = httpServletResponse.getHeaders(headerName);
			for(String header : headers) {
				builder = builder.header(headerName, header);
			}
		}


		return builder.build();
	}
	
	/**
	 * Converts the exception to a response
	 * @param errors the errors
	 * @return the response
	 */
	public Response toResponse(ApiError... errors) {
		ResponseBuilder builder = Response
				.status(status)
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.entity(JsonUtility.serialize(errors));

		Collection<String> headerNames = httpServletResponse.getHeaderNames();
		for(String headerName : headerNames) {
			Collection<String> headers = httpServletResponse.getHeaders(headerName);
			for(String header : headers) {
				builder = builder.header(headerName, header);
			}
		}


		return builder.build();
	}

	/**
	 * Converts the exception to a response
	 * @param errors
	 * @return the response
	 */
	public Response toResponse(List<ApiError> errors) {
		final String methodName = "toResponse";
		
		String sJson = null;
		if (errors != null) {
			sJson = JsonUtility.serialize(errors);
		}
		log.debug(methodName, " - JSON response: " + sJson);
		
		ResponseBuilder builder = Response
				.status(status)
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.entity(sJson);

		Collection<String> headerNames = httpServletResponse.getHeaderNames();
		for(String headerName : headerNames) {
			Collection<String> headers = httpServletResponse.getHeaders(headerName);
			for(String header : headers) {
				builder = builder.header(headerName, header);
			}
		}
		return builder.build();
	}
	
}
