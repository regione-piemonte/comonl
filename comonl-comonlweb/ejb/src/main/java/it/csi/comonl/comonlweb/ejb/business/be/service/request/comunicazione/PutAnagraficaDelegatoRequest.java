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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;


/**
 * The Class PostRicercaProspettoRequest.
 */
public class PutAnagraficaDelegatoRequest implements BaseRequest {

	private final AnagraficaDelegato anagraficaDelegato;

	/**
	 * Constructor
	 */
	public PutAnagraficaDelegatoRequest(AnagraficaDelegato anagraficaDelegato) {
		super();
		this.anagraficaDelegato = anagraficaDelegato;
	}

	public AnagraficaDelegato getAnagraficaDelegato() {
		return anagraficaDelegato;
	}

}
