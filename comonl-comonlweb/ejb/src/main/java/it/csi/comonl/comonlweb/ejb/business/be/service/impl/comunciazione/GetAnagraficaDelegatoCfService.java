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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAnagraficaDelegatoCfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;

/**
 * PostAnagraficaDelegatoService
 */
public class GetAnagraficaDelegatoCfService
		extends BaseAnagraficaDelegatoService<PostAnagraficaDelegatoCfRequest, PostAnagraficaDelegatoResponse> {

	private String cfDelegato;

	public GetAnagraficaDelegatoCfService(ConfigurationHelper configurationHelper,
			AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfDelegato = request.getCfDelegato();
		checkNotNull(cfDelegato, "cfDelegato", true);
	}

	@Override
	protected void execute() {

		AnagraficaDelegato anagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato, "D");

		response.setAnagraficaDelegato(anagraficaDelegato);
	}

}
