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
/**********************************************
 * CSI PIEMONTE
 **********************************************/
package it.csi.comonl.comonlweb.web.business.be.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.comonl.comonlweb.lib.dto.ApiError;

/**
 * API interface for /system path
 */
@Path("system")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "system")
public interface SystemApi {

	/**
	 * Ping method
	 * @param securityContext the security context
	 * @param httpHeaders the HTTP headers
	 * @param httpRequest the HTTP request
	 * @return the response
	 */
	@GET
	@Path("ping")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response ping(
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	@GET
	@Path("test-error/{code}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restituisce dati registrati su sistema.", tags = "system", nickname = "testError")
	@ApiResponses(value = {
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response testError(
			@PathParam("code") Integer code, 
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
}
