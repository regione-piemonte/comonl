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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetTipoContrattiByTracciatoAndTipoComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetTipoContrattoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Categorias
 */
public class GetTipoContrattiByTracciatoAndTipoComunicazioneService
		extends BaseDecodificaTipoService<GetTipoContrattiByTracciatoAndTipoComunicazioneRequest, GetTipoContrattoResponse> {
	
	private String tipoTracciato;
	private String tipoCmunicazione;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetTipoContrattiByTracciatoAndTipoComunicazioneService(ConfigurationHelper configurationHelper, DecodificaTipoDad decodificaTipoDad) {
		super(configurationHelper, decodificaTipoDad);
	}
	
	
	
	@Override
	protected void execute() {
		tipoTracciato = request.getTipoTracciato();
		tipoCmunicazione = request.getTipoCmunicazione();
		response.setTipoContratti(decodificaTipoDad.GetTipoContrattiByTracciatoAndTipoComunicazione(tipoTracciato,tipoCmunicazione));
	}

}
