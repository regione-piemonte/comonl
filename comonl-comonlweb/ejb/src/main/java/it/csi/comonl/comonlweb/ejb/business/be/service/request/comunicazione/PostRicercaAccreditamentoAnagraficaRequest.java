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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostRicercaAccreditamentoAnagraficaRequest extends BasePagedRequest {

	private final FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	/**
	 * Constructor
	 */
	public PostRicercaAccreditamentoAnagraficaRequest(Integer page, Integer size, String sort, String direction,
			FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		super(size, page, sort, direction);
		this.ricercaAccreditamentoAnagrafiche = ricercaAccreditamentoAnagrafiche;
	}

	public FormRicercaAccreditamentoAnagrafiche getRicercaAccreditamentoAnagrafiche() {
		return ricercaAccreditamentoAnagrafiche;
	}
}
