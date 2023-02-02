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

import javax.ejb.ApplicationException;

/**
 * Exception signaling a not found entity
 */
@ApplicationException(rollback = true)
public class NotFoundException extends ServiceException {

	/** For serialization */
	private static final long serialVersionUID = 4398533896440369883L;

	/**
	 * @see RuntimeException#RuntimeException(String)
	 */
	public NotFoundException(String message) {
		super(message);
	}

	/**
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
