/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.business.be.api.impl;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseResponse;
import it.csi.comonl.comonlweb.lib.util.function.SupplierWithException;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Base REST service implementor
 */
public abstract class BaseRestServiceImpl {

	/**
	 * The logger.
	 * <p>Anti-pattern: the logger should be private static final, only one per class. We define it as such for easiness
	 */
	protected LogUtil log = new LogUtil(getClass().getSuperclass());

	/**
	 * Invokes a service
	 * @param <E> the exception type
	 * @param supplier the supplier
	 * @return the JAX-RS response
	 */
	protected <E extends EJBException, R extends BaseResponse> Response invoke(SupplierWithException<R, E> supplier) {
		// Proceed with the invocation
		R response = supplier.get();
		if(response == null) {
			// FIXME: Should otherwise send an error?
			return Response.noContent().build();
		}
		return response.composeResponse();
	}

}
