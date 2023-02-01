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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.PostTipoComunicazioneUnilavDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostDecodificaGenericaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Gets the contratti decodificas
 */
public class PostTipoComunicazioneUnilavDecodificaService extends BaseDecodificaTipoService<PostTipoComunicazioneUnilavDecodificaRequest, PostDecodificaGenericaResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public PostTipoComunicazioneUnilavDecodificaService(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}

	@Override
	protected void execute() {
		DecodificaGenerica filtro = request.getFiltro();
		List<DecodificaGenerica> res =  decodificaTipoDad.getTipoComunicazioneDecodifica(filtro);
		response.setDecodificaGenericas(res);
	}

}
