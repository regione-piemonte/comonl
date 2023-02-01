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

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Base service implementation for the comunicazione
 * 
 * @param <Q> the request type
 * @param <R> the response type
 */
public abstract class BaseAnagraficaDelegatoService<Q extends BaseRequest, R extends BaseResponse> extends BaseService<Q, R> {

	/** Data Access Delegate for decodifica */
	protected final AnagraficaDelegatoDad anagraficaDelegatoDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad       the DAD for the comunicazione
	 */
	protected BaseAnagraficaDelegatoService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

}
