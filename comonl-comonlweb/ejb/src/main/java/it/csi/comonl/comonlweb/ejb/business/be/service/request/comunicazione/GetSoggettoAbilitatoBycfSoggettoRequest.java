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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * Request for reading the SoggettoAbilitato by its cfSoggetto
 */
public class GetSoggettoAbilitatoBycfSoggettoRequest implements BaseRequest {

	private final String cfSoggetto;

	/**
	 * Constructor
	 * @param id the id
	 */
	public GetSoggettoAbilitatoBycfSoggettoRequest(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	public String getCfSoggetto() {
		return cfSoggetto;
	}

}
