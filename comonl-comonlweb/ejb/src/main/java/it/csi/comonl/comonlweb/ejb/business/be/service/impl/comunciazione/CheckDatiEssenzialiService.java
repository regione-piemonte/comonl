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

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.CheckDatiEssenzialiRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.CheckDatiEssenzialiResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;

/**
 * Retrieves an testataOrdine by its id
 */
public class CheckDatiEssenzialiService extends BaseComunicazioneService<CheckDatiEssenzialiRequest, CheckDatiEssenzialiResponse> {
	
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param testataOrdineDad    the testataOrdine DAD
	 */
	public CheckDatiEssenzialiService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper,comunicazioneDad);
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getComunicazione(), "comunicazione");
		checkNotNull(request.getRuolo(), "ruolo");
	}

	@Override
	protected void execute() {
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		Comunicazione comunicazione = request.getComunicazione();
		if(comunicazione == null) {
			apiErrors.add(new ApiError(MsgComonl.CHKESSE0001.getCode(),MsgComonl.CHKESSE0001.getMessage()));
			response.setApiErrors(apiErrors);
			return;
			
		}
		Boolean res = isCheckDatiEssenziali(comunicazione,request.getRuolo());
		response.setChekDatiEssenziali(res);
		
	}
}
