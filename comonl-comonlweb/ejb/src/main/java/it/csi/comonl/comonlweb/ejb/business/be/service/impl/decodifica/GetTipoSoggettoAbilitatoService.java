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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Categorias
 */
public class GetTipoSoggettoAbilitatoService extends BaseDecodificaTipoService<GetTipoSoggettoAbilitatoRequest,GetTipoSoggettoAbilitatoResponse > {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetTipoSoggettoAbilitatoService(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}

	@Override
	protected void execute() {
		response.setTipoSoggettoAbilitatos(decodificaTipoDad.getTipoSoggettoAbilitato());
	}
}
