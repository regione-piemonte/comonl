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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoDelegaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Provincias
 */
public class GetStatoDelegaService extends BaseDecodificaService<GetStatoDelegaRequest, GetStatoDelegaResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetStatoDelegaService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper, decodificaDad);
	}

	@Override
	protected void execute() {
		response.setStatoDelegas(decodificaDad.getStatoDelega());
	}

}
