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
package it.csi.comonl.comonlweb.srv.util.filter.exception;

/**
 * Exception signaling a failed authentication for a given API call
 */
public class UnsupportedApiCallException extends RuntimeException {

	/** For serialization */
	private static final long serialVersionUID = 5499165174090051377L;

	/**
	 * Failed authentication, with a message
	 * @param message the authentication failure message
	 * @see RuntimeException#RuntimeException(String)
	 */
	public UnsupportedApiCallException(String message) {
		super(message);
	}
	
	/**
	 * Failed authentication, with a message and an underlying exception
	 * @param message the authentication failure message
	 * @param cause the underlying cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public UnsupportedApiCallException(String message, Throwable cause) {
		super(message, cause);
	}

}
