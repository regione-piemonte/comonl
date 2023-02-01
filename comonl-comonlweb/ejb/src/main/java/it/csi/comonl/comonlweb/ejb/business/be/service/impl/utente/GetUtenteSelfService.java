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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.utente;

import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.utente.GetUtenteSelfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.utente.GetUtenteSelfResponse;
import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.Utente;

/**
 * Retrieves an utente by its id
 */
public class GetUtenteSelfService extends BaseService<GetUtenteSelfRequest, GetUtenteSelfResponse> {

	/**
	 * Constructor
	 * @param configurationHelper the configuration helper
	 * @param utenteDad the utente DAD
	 */
	public GetUtenteSelfService(ConfigurationHelper configurationHelper) {
		super(configurationHelper);
	}

	@Override
	protected void execute() {
		Utente utente = ComonlThreadLocalContainer.UTENTE_CONNESSO.get();
		// Read from DB?
		response.setUtente(utente);
	}

}
