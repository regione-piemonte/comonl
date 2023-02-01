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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;


/**
 * Retrieves an AnagraficaAziAccent by cf impresa
 */
public class GetAziendaAccentrataService
		extends BaseAnagraficaDelegatoService<GetAziendaAccentrataRequest, GetAziendaAccentrataResponse> {
	
	private String cfImpresa;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the DelegatoImpresaDad DAD
	 */
	public GetAziendaAccentrataService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfImpresa = request.getCfImpresa();
		checkNotEmpty(cfImpresa, "cfImpresa");
	}

	@Override
	protected void execute() {
		AnagraficaAziAccent anagraficaAziAccent = anagraficaDelegatoDad.getAziendaAccentrata(cfImpresa);
		response.setAnagraficaAziAccent(anagraficaAziAccent);
	}
}
