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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetAnagraficaDelegatoByIdSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;



/**
 * Retrieves an testataOrdine by its id
 */
public class GetAnagraficaDelegatoByIdSoggettoAbilitatoService
		extends BaseAnagraficaDelegatoService<GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest, GetAnagraficaDelegatoByIdSoggettoAbilitatoResponse> {
	
	private Long idSoggettoAbilitato;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public GetAnagraficaDelegatoByIdSoggettoAbilitatoService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		idSoggettoAbilitato = request.getIdSoggettoAbilitato();
		checkNotNull(idSoggettoAbilitato, "idSoggettoAbilitato");
	}

	@Override
	protected void execute() {
		List<AnagraficaDelegato> res = anagraficaDelegatoDad.findAnagraficaDelegatoByIdSoggettoAbilitato(idSoggettoAbilitato);
		response.setAnagraficaDelegatos(res);
	}

}
