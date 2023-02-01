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

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostComuneDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostDecodificaGenericaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;

/**
 * Gets the Provincias
 */
public class PostComuneDecodificaService extends BaseDecodificaService<PostComuneDecodificaRequest, PostDecodificaGenericaResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public PostComuneDecodificaService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper, decodificaDad);
	}

	@Override
	protected void execute() {
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		List<DecodificaGenerica> res = decodificaDad.getComuneDecodifica(request.getFiltro());
		if(res == null || res.size() <= 0) {
			apiErrors.add(MsgComonl.COMCOMW099.getError());
		}
		response.setApiErrors(apiErrors);
		response.setDecodificaGenericas(res);
	}

}
