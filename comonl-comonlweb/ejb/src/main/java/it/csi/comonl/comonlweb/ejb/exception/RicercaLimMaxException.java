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

public class RicercaLimMaxException extends RuntimeException {

	/** For serialization */
	private static final long serialVersionUID = -6151808156585443325L;
	
	private int limMaxRisultati;

	/**
	 * @see Exception#Exception(String)
	 */
	public RicercaLimMaxException(int limMaxRisultati, String message) {
		super(message);
		this.limMaxRisultati = limMaxRisultati;
	}

	
	public int getLimMaxRisultati() {
		return limMaxRisultati;
	}
	
}
