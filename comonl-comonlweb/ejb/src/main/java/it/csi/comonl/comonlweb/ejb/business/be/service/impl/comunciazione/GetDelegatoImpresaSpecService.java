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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetDelegatoImpresaSpecRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetDelegatoImpresaSpecResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaSpec;


/**
 * Retrieves an delegatoImpresaSpec by cf impresa
 */
public class GetDelegatoImpresaSpecService
		extends BaseDelegatoImpresaService<GetDelegatoImpresaSpecRequest, GetDelegatoImpresaSpecResponse> {
	
	private String cfImpresa;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the DelegatoImpresaDad DAD
	 */
	public GetDelegatoImpresaSpecService(ConfigurationHelper configurationHelper, DelegatoImpresaDad delegatoImpresaDad) {
		super(configurationHelper, delegatoImpresaDad);
	}

	@Override
	protected void checkServiceParams() {
		cfImpresa = request.getCfImpresa();
		checkNotEmpty(cfImpresa, "cfImpresa");
	}

	@Override
	protected void execute() {
		List<DelegatoImpresaSpec> res = delegatoImpresaDad.getDelegatoImpresaSpecByCfImpresa(cfImpresa);
		response.setDelegatoImpresaSpecs(res);
	}

}
