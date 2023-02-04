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
import javax.ws.rs.POST;
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
import it.csi.comonl.comonlweb.lib.dto.common.AnagraficaGenerica;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;

/**
 * API interface for /common path
 */
@Path("common")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "common")
public interface CommonApi {

	@GET
	@Path("ruolo")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restituisce la lista di ruoli legati al codice fiscale dell'identita' di iride registrati su sistema.", tags = "common", nickname = "getRuoli")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Ruolo.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getRuoli(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@POST
	@Path("userAccessLog")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Inserisce dati dello utente loggato sulla tabella di log su sistema.", tags = "common", nickname = "insertUserAccessLog")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = UserAccessLog.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response insertUserAccessLog(UserAccessLog loUserLog, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * Gets AnagraficaGenerica pre-filled starting from the codice fiscale
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("pre-compila-anagrafica/{cf}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca province", tags = "common", nickname = "preCompilaAnagraficaByCf")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaGenerica.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response preCompilaAnagraficaByCf(@PathParam("cf") String cf, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("getParametroByDescrizione/{descrizioneParametro}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca parametro", tags = "common", nickname = "getParametroByDescrizione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = ComonlsParametri.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getParametroByDescrizione(@PathParam("descrizioneParametro") String descrizioneParametro,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@GET
	@Path("getApplicativoAbilitato")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca parametro", tags = "common", nickname = "getApplicativoAbilitato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = ComonlsParametri.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getApplicativoAbilitato(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	@GET
	@Path("getParametroCommaxById/{idParametro}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "get parametro", tags = "common", nickname = "getParametroCommaxById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = CommaxParametri.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getParametroCommaxById(@PathParam("idParametro") Long idParametro,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	


}
