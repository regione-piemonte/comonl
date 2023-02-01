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

public class TimeoutRicercaException extends RuntimeException {

	/** For serialization */
	private static final long serialVersionUID = -6151808156585443325L;
	
	/**
	 * @see Exception#Exception(String)
	 */
	public TimeoutRicercaException() {
		super("Superato il limite di tempo per la ricerca eseguita");
	}
	
}
