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

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BasePagedRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostRicercaVardatoriRequest extends BasePagedRequest {

	private final FormRicercaComunicazione ricercaComunicazione;

	/**
	 * Constructor
	 */
	public PostRicercaVardatoriRequest(Integer page, Integer size, String sort, String direction,
			FormRicercaComunicazione ricercaComunicazione) {
		super(size, page, sort, direction);
		this.ricercaComunicazione = ricercaComunicazione;
	}

	public FormRicercaComunicazione getRicercaComunicazione() {
		return ricercaComunicazione;
	}

	

}
