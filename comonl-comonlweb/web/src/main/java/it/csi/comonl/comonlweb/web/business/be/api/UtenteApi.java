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
import it.csi.comonl.comonlweb.lib.dto.Utente;

/**
 * API interface for /utente path
 */
@Path("utente")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "utente")
public interface UtenteApi {

	/**
	 * Gets the Utente
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("self")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restituisce l'utente che effettua la chiamata.", tags = "utente", nickname = "getUtenteSelf")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "L'utente registrato su sistema.", response = Utente.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getUtenteSelf(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	

}
