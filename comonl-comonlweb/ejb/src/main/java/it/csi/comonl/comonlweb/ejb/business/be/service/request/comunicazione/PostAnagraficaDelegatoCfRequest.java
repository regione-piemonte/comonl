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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;


/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostAnagraficaDelegatoCfRequest implements BaseRequest {

	private final String cfDelegato;

	/**
	 * Constructor
	 */
	public PostAnagraficaDelegatoCfRequest(String cfDelegato) {
		super();
		this.cfDelegato = cfDelegato;
	}

	public String getCfDelegato() {
		return cfDelegato;
	}

}
