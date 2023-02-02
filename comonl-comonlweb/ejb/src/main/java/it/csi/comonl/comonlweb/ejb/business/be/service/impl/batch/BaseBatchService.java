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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.batch;

import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Base service implementation for the common
 * @param <Q> the request type
 * @param <R> the response type
 */
public abstract class BaseBatchService<Q extends BaseRequest, R extends BaseResponse> extends BaseService<Q, R> {

	/** Data Access Delegate for common */
	//protected final CommonDad commonDad;

	/**
	 * Constructor
	 * @param configurationHelper the helper for the configuration
	 * @param commonDad the DAD for the common
	 */
	protected BaseBatchService(ConfigurationHelper configurationHelper) {//, CommonDad commonDad) {
		super(configurationHelper);
		//this.commonDad = commonDad;
	}

}
