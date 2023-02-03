/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SPICOM submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.exception;

public class IupIntegrationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IupIntegrationException() {
		super();
	}

	public IupIntegrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IupIntegrationException(String message) {
		super(message);
	}

	public IupIntegrationException(Throwable cause) {
		super(cause);
	}

}
