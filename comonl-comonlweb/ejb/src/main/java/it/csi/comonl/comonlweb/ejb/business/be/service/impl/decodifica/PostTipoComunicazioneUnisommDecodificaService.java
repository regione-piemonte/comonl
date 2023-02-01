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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.PostTipoComunicazioneUnisommDecodificaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.PostDecodificaGenericaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * Gets the contratti decodificas
 */
public class PostTipoComunicazioneUnisommDecodificaService extends BaseDecodificaTipoService<PostTipoComunicazioneUnisommDecodificaRequest, PostDecodificaGenericaResponse> {

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public PostTipoComunicazioneUnisommDecodificaService(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}

	@Override
	protected void execute() {
		DecodificaGenerica filtro = request.getFiltro();
		List<DecodificaGenerica> tipiSomministrazione = decodificaTipoDad.getTipoSomministrazioneDecodifica(filtro);
		List<DecodificaGenerica> tipiVariazione =  decodificaTipoDad.getTipoVariazioneDecodifica(null);
		List<DecodificaGenerica> res = new ArrayList<DecodificaGenerica>();
		res.addAll(tipiSomministrazione);
		res.addAll(tipiVariazione);
		response.setDecodificaGenericas(res);
	}

}
