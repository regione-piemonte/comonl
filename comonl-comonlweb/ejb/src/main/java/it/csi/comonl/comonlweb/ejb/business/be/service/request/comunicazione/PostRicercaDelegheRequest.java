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

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BasePagedRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostRicercaDelegheRequest extends BasePagedRequest {

	private final FormRicercaDelega ricercaDelega;

	/**
	 * Constructor
	 */
	public PostRicercaDelegheRequest(Integer page, Integer size, String sort, String direction,
			FormRicercaDelega ricercaDelega) {
		super(size, page, sort, direction);
		this.ricercaDelega = ricercaDelega;
	}

	public FormRicercaDelega getRicercaDelega() {
		return ricercaDelega;
	}

	

}
