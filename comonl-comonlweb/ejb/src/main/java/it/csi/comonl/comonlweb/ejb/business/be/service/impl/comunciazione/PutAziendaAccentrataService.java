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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutAziendaAccentrataRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutAziendaAccentrataResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;


/**
 * Put an AnagraficaAziAccent
 */
public class PutAziendaAccentrataService
		extends BaseAnagraficaDelegatoService<PutAziendaAccentrataRequest, PutAziendaAccentrataResponse> {
	
	private AnagraficaAziAccent anagraficaAziAccent;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the DelegatoImpresaDad DAD
	 */
	public PutAziendaAccentrataService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		anagraficaAziAccent = request.getAnagraficaAziAccent();
		checkModel(anagraficaAziAccent, "anagraficaAziAccent");
	}

	@Override
	protected void execute() {
		anagraficaDelegatoDad.updateAziendaAccentrata(anagraficaAziAccent);
		response.setAnagraficaAziAccent(anagraficaAziAccent);
	}
}
