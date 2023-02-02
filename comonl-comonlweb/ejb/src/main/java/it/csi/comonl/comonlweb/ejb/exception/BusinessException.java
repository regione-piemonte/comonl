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
package it.csi.comonl.comonlweb.ejb.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.ApplicationException;

import it.csi.comonl.comonlweb.lib.dto.ApiError;

/**
 * Exception signaling a failed business check
 */
@ApplicationException(rollback = true)
public class BusinessException extends ServiceException {

	/** For serialization */
	private static final long serialVersionUID = 4398533896440369883L;
	// private final ApiError error;
	private final List<ApiError> listApiError;
	
	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public BusinessException(String message) {
		super(message);
		this.listApiError = null;
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.listApiError = null;
	}
	
	/**
	 * @param apiError the api error
	 * @see RuntimeException#RuntimeException(String)
	 */
	public BusinessException(ApiError apiError) {
		super(apiError.getFullErrorMessage());
		List<ApiError> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	/**
	 * @param message the message
	 * @param apiErrors the api errors
	 */
	public BusinessException(String message, List<ApiError> apiErrors) {
		super(message);
		this.listApiError = Collections.unmodifiableList(new ArrayList<>(apiErrors));
	}
	
	/**
	 * @param apiError the api error
	 * @param cause the cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public BusinessException(ApiError apiError, Throwable cause) {
		super(apiError.getFullErrorMessage(), cause);
		List<ApiError> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	
	/**
	 * @param message the message
	 * @param apiError the api error
	 * @see RuntimeException#RuntimeException(String)
	 */
	public BusinessException(String message, ApiError apiError) {
		super(message);
		List<ApiError> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}

	/**
	 * @param message the message
	 * @param apiError the api error
	 * @param cause the cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public BusinessException(String message, ApiError apiError, Throwable cause) {
		super(message, cause);
		List<ApiError> tmp = new ArrayList<>();
		tmp.add(apiError);
		this.listApiError = Collections.unmodifiableList(tmp);
	}
	/**
	 * @param message the message
	 * @param apiErrors the api errors
	 * @param cause the cause
	 */
	public BusinessException(String message, List<ApiError> apiErrors, Throwable cause) {
		super(message, cause);
		this.listApiError = Collections.unmodifiableList(new ArrayList<>(apiErrors));
	}

	/**
	 * @return the error
	 */
	public List<ApiError> getErrors() {
		return listApiError;
	}

}
