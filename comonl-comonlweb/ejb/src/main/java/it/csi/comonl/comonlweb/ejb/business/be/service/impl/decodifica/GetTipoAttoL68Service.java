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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoAttoL68Request;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoAttoL68Response;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Categorias
 */
public class GetTipoAttoL68Service extends BaseDecodificaTipoService<GetTipoAttoL68Request, GetTipoAttoL68Response> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetTipoAttoL68Service(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}

	@Override
	protected void execute() {
		response.setTipoAttos(decodificaTipoDad.getTipoAttoL68());
	}

}
