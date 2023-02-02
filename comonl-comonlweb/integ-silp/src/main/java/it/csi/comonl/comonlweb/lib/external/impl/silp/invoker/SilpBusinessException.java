/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SILP submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.external.impl.silp.invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiMessage;



public class SilpBusinessException extends Exception {

	/** For serialization */
	private static final long serialVersionUID = 4398533896440369883L;
	// private final ApiError error;
	private final List<ApiMessage> listApiError;
	
	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public SilpBusinessException(String message) {
		super(message);
		this.listApiError = null;
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public SilpBusinessException(String message, Throwable cause) {
		super(message, cause);
		this.listApiError = null;
	}
	
	/**
	 * @param apiError the api error
	 * @see RuntimeException#RuntimeException(String)
	 */
	public SilpBusinessException(ApiMessage apiError) {
		super(apiError.getMessage());
		List<ApiMessage> tmp = new ArrayList<ApiMessage>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	/**
	 * @param message the message
	 * @param apiErrors the api errors
	 */
	public SilpBusinessException(String message, List<ApiMessage> apiErrors) {
		super(message);
		this.listApiError = Collections.unmodifiableList(apiErrors);
		
		
	}
	
	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (listApiError != null && listApiError.size()>0) {
			for (ApiMessage m : listApiError)
				message += "\n" + m.getCode() + " - " + m.getMessage();
		}
		return message;
	}
	
	/**
	 * @param apiError the api error
	 * @param cause the cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public SilpBusinessException(ApiMessage apiError, Throwable cause) {
		super(apiError.getMessage(), cause);
		List<ApiMessage> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	
	/**
	 * @param message the message
	 * @param apiError the api error
	 * @see RuntimeException#RuntimeException(String)
	 */
	public SilpBusinessException(String message, ApiMessage apiError) {
		super(message);
		List<ApiMessage> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}

	/**
	 * @param message the message
	 * @param apiError the api error
	 * @param cause the cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public SilpBusinessException(String message, ApiMessage apiError, Throwable cause) {
		super(message, cause);
		List<ApiMessage> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	/**
	 * @param message the message
	 * @param apiErrors the api errors
	 * @param cause the cause
	 */
	public SilpBusinessException(String message, List<ApiMessage> apiErrors, Throwable cause) {
		super(message, cause);
		this.listApiError = Collections.unmodifiableList(new ArrayList<>(apiErrors));
	}

	/**
	 * @return the error
	 */
	public List<ApiMessage> getErrors() {
		return listApiError;
	}

}
