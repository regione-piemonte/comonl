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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;



/**
 * Retrieves an testataOrdine by its id
 */
public class GetDelegatoImpresaByIdAnagraficaDelegatoService
		extends BaseAnagraficaDelegatoService<GetDelegatoImpresaByIdAnagraficaDelegatoRequest, GetDelegatoImpresaByIdAnagraficaDelegatoResponse> {
	
	private String cfDelegato;
	private String tipoAnagrafica;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public GetDelegatoImpresaByIdAnagraficaDelegatoService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfDelegato = request.getCfDelegato();
		tipoAnagrafica = request.getTipoAnagrafica();
		checkNotNull(cfDelegato, "cfDelegato");
		checkNotNull(tipoAnagrafica, "tipoAnagrafica");
	}

	@Override
	protected void execute() {
		List<DelegatoImpresa> res = anagraficaDelegatoDad.findDelegatoimpresaByIdAnagraficaDelegato(cfDelegato, tipoAnagrafica);
		response.setDelegatoImpresas(res);
	}

}
