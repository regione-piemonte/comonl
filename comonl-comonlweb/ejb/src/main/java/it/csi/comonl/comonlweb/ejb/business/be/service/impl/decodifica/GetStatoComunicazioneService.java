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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetStatoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetStatoComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Provincias
 */
public class GetStatoComunicazioneService extends BaseDecodificaService<GetStatoComunicazioneRequest, GetStatoComunicazioneResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetStatoComunicazioneService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper, decodificaDad);
	}

	@Override
	protected void execute() {
		response.setStatoComunicaziones(decodificaDad.getStatoComunicazione(request.getFlgRicerca()));
	}

}
