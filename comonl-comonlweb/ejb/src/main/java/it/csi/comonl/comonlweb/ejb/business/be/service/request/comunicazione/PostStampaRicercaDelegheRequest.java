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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostStampaRicercaDelegheRequest implements BaseRequest {

	
	private final FormRicercaDelega ricercaDelega;

	public PostStampaRicercaDelegheRequest(FormRicercaDelega ricercaDelega) {
		super();
		this.ricercaDelega = ricercaDelega;
	}

	public FormRicercaDelega getFormRicercaDelega() {
		return ricercaDelega;
	}
	
}
