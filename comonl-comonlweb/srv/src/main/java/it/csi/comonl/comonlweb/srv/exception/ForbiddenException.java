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
/**
 *
 */
package it.csi.comonl.comonlweb.srv.exception;

/**
 * Eccezione per l'utente Forbidden (403)
 */
public class ForbiddenException extends RuntimeException {

	/** For serialization */
	private static final long serialVersionUID = 7896713970119939263L;

	/** Default constructor */
	public ForbiddenException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @see RuntimeException#RuntimeException(String)
	 */
	public ForbiddenException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public ForbiddenException(Throwable cause) {
		super(cause);
	}

}
