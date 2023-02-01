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

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Base service implementation for the comunicazione
 * 
 * @param <Q> the request type
 * @param <R> the response type
 */
public abstract class BaseChkService<Q extends BaseRequest, R extends BaseResponse> 
	extends BaseComunicazioneService<Q, R> {

	/** Data Access Delegate for controlli */
	protected final ControlliDad controlliDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param controlliDad       the DAD for the controlli
	 */
	protected BaseChkService(ConfigurationHelper configurationHelper, ControlliDad controlliDad, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper, comunicazioneDad);
		this.controlliDad = controlliDad;
	}

}