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
package it.csi.comonl.comonlweb.ejb.exception;

import javax.ejb.ApplicationException;

/**
 * Exception for the Service-level
 */
@ApplicationException(rollback = true)
public class ServiceException extends RuntimeException {

	/** For serialization */
	private static final long serialVersionUID = -3941671517104803947L;

	/**
	 * @see Exception#Exception(String)
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
