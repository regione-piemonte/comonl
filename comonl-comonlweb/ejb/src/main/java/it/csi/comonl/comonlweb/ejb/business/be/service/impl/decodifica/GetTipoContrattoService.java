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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoContrattoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoContrattoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Categorias
 */
public class GetTipoContrattoService
		extends BaseDecodificaTipoService<GetTipoContrattoRequest, GetTipoContrattoResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetTipoContrattoService(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}

	@Override
	protected void execute() {
		response.setTipoContratti(decodificaTipoDad.getTipoContratto());
	}

}
