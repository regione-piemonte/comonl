/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.response.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Base response class
 */
public abstract class BaseResponse {
	protected final LogUtil log = new LogUtil(getClass());
	
	/** The errors */
	protected List<ApiError> apiErrors = new ArrayList<>();
	/** The warnings */
	protected List<ApiError> apiWarnings = new ArrayList<>();

	/**
	 * @return the apiErrors
	 */
	public List<ApiError> getApiErrors() {
		return apiErrors;
	}

	/**
	 * @param apiErrors the apiErrors to set
	 */
	public void setApiErrors(List<ApiError> apiErrors) {
		this.apiErrors = apiErrors;
	}

	/**
	 * Adds an error
	 * @param apiError the error to add
	 */
	public void addApiError(ApiError apiError) {
		this.apiErrors.add(apiError);
	}

	/**
	 * Adds the error messagess
	 * @param err the errors to add
	 */
	public void addApiErrors(Collection<ApiError> err) {
		this.apiErrors.addAll(err);
	}
	

	/**
	 * @return the apiWarnings
	 */
	public List<ApiError> getApiWarnings() {
		return apiWarnings;
	}

	/**
	 * @param apiWarnings the apiWarnings to set
	 */
	public void setApiWarnings(List<ApiError> apiWarnings) {
		this.apiWarnings = apiWarnings;
	}

	/**
	 * Adds an error
	 * @param apiError the error to add
	 */
	public void addApiWarnings(ApiError apiWarnings) {
		this.apiWarnings.add(apiWarnings);
	}
	
	/**
	 * Adds the error messagess
	 * @param err the errors to add
	 */
	public void addApiWarnings(Collection<ApiError> warning) {
		this.apiWarnings.addAll(warning);
	}

	/**
	 * Composes the Response
	 * @return the response
	 */
	public Response composeResponse() {
		final String methodName = "composeResponse";
		if(apiErrors != null && !apiErrors.isEmpty()) {
			String serialized = JsonUtility.serialize(apiErrors);
			logSerialized(methodName, serialized);
			return Response
					.status(Status.BAD_REQUEST)
					.entity(serialized)
					.build();
		}
		apiErrors = null;
		return composeOwnResponse();
	}
	
	protected void logSerialized(String methodName, String serialized) {
		final int MAX_LENGTH = 10 * 1024;
		if (serialized != null && serialized.length() > MAX_LENGTH) {
			String logSerialized = serialized.substring(0, MAX_LENGTH) + "...";
			log.debug(methodName, "JSON response: " + logSerialized);
		} else {
			log.debug(methodName, "JSON response: " + serialized);
		}
	}

	/**
	 * Compose own response
	 * @return the response
	 */
	protected abstract Response composeOwnResponse();
}
