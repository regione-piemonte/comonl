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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.SoggettoAbilitatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetSoggettoAbilitatoBycfSoggettoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetSoggettoAbilitatoBycfSoggettoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;

/**
 * Retrieves an testataOrdine by its id
 */
public class GetSoggettoAbilitatoBycfSoggettoService extends BaseSoggettoAbilitatoService<GetSoggettoAbilitatoBycfSoggettoRequest, GetSoggettoAbilitatoBycfSoggettoResponse> {
	
	String cfSoggetto;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param testataOrdineDad    the testataOrdine DAD
	 */
	public GetSoggettoAbilitatoBycfSoggettoService(ConfigurationHelper configurationHelper, SoggettoAbilitatoDad soggettoAbilitatoDad) {
		super(configurationHelper, soggettoAbilitatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfSoggetto = request.getCfSoggetto();
		checkNotNull(cfSoggetto, "cfSoggetto");
	}

	@Override
	protected void execute() {
		SoggettoAbilitato soggettoAbilitato = soggettoAbilitatoDad.findByCfSoggetto(cfSoggetto);
		if(soggettoAbilitato == null) {
			List<ApiError> apiErrors = new ArrayList<ApiError>();
			apiErrors.add(MsgComonl.COMCOMW100.getError());
			response.setApiErrors(apiErrors);
		}
		response.setSoggettoAbilitato(soggettoAbilitato);
	}

	

}
