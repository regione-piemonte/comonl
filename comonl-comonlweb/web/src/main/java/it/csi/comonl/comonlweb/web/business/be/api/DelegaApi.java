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
package it.csi.comonl.comonlweb.web.business.be.api;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegaResponse;
import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer.MimeType;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PagedResponseRicercaDeleghe;

/**
 * API interface for /delega path
 */
@Path("delega")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "delega")
public interface DelegaApi {
	
	@POST
	@Path("ricerca")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "ricerca deleghe", tags = "delega", nickname = "postRicercaDeleghe")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaDeleghe.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaDeleghe(
			FormRicercaDelega ricercaDelega,
			@Min(0) @QueryParam("offset") Integer page,
			@Min(1) @Max(100) @QueryParam("limit") Integer limit, @QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	@POST
	@Path("stampa-ricerca-deleghe")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "Stampa ricerca deleghe", tags = "delega", nickname = "stampaRicercaDeleghe")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaRicercaDeleghe(
			FormRicercaDelega ricercaDelega,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, 
			@Context HttpServletRequest httpRequest);
	
	
	@POST
	@Path("aggiorna-stato")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "aggiorna stato delega", tags = "delega", nickname = "aggiornaStatoDelega")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Delega.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response aggiornaStatoDelega(
			Delega delega,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	@POST
	@Path("aggiorna")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "aggiorna - inserisce delega", tags = "delega", nickname = "aggiornaDelega")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PostDelegaResponse.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response aggiornaDelega(
			Delega delega,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
}
