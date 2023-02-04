/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.web.util.rest.exception;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import it.csi.comonl.comonlweb.ejb.exception.BusinessException;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Exception mapper for the BusinessException
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	/** The configuration helper */
	@Inject protected ConfigurationHelper configurationHelper;
	/** The servlet response */
	@Context protected HttpServletResponse httpServletResponse;

	@Override
	public Response toResponse(BusinessException exception) {
		ExceptionMapperHelper emh = new ExceptionMapperHelper(configurationHelper, httpServletResponse, 400);
		// return emh.toResponse(exception.getError());
		return emh.toResponse(exception.getErrors());
	}

}
