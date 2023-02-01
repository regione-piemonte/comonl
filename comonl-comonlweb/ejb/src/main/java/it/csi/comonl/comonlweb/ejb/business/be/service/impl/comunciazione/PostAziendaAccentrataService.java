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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;


/**
 * Post an AnagraficaAziAccent
 */
public class PostAziendaAccentrataService
		extends BaseAnagraficaDelegatoService<PostAziendaAccentrataRequest, PostAziendaAccentrataResponse> {
	
	private AnagraficaAziAccent anagraficaAziAccent;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the DelegatoImpresaDad DAD
	 */
	public PostAziendaAccentrataService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		anagraficaAziAccent = request.getAnagraficaAziAccent();
	}

	@Override
	protected void execute() {
		
		anagraficaAziAccent = anagraficaDelegatoDad.insertAziendaAccentrata(anagraficaAziAccent);
		response.setAnagraficaAziAccent(anagraficaAziAccent);
	}
}
