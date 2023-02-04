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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoSoggettoAbilitatoRes;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;


/**
 * API interface for /comunicazione path
 */
@Path("soggetto-abilitato")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "SoggettoAbilitato")
public interface SoggettoAbilitatoApi {
	
	/**
	 * Gets a getSoggettoAbilitatoBycfSoggetto by its cfSoggetto
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("cf-soggetto/{cfSoggetto}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "SoggettoAbilitato", tags = "SoggettoAbilitato", nickname = "getSoggettoAbilitatoBycfSoggetto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SoggettoAbilitato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getSoggettoAbilitatoBycfSoggetto(@PathParam("cfSoggetto") String cfSoggetto, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets a getSoggettoAbilitatoBycfSoggetto by its cfSoggetto
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("{idSeoggettoAbilitato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "SoggettoAbilitato", tags = "SoggettoAbilitato", nickname = "getSoggettoAbilitatoById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SoggettoAbilitato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getSoggettoAbilitatoById(@PathParam("idSeoggettoAbilitato") Long idSeoggettoAbilitato, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post SoggettoAbilitato", tags = "SoggettoAbilitato", nickname = "postSoggettoAbilitato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SoggettoAbilitato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postSoggettoAbilitato(
			SoggettoAbilitato soggettoAbilitato,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put SoggettoAbilitato", tags = "SoggettoAbilitato", nickname = "putSoggettoAbilitato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SoggettoAbilitato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putSoggettoAbilitato(
			SoggettoAbilitato soggettoAbilitato,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	/**
	 * 
	 * @param idSoggettoAbilitato
	 * @param securityContext
	 * @param httpHeaders
	 * @param httpRequest
	 * @return
	 */
	@PUT
	@Path("data-annullamento/id-soggetto-abilitato/{idSoggettoAbilitato}auto/{flgAutorizzazione}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Annulla o ripristina un SoggettoAbilitato impostando dataAnnullamento a data di sistema", tags = "SoggettoAbilitato", nickname = "setDataAnnullamento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SetDataAnnullamentoSoggettoAbilitatoRes.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response setDataAnnullamentoSoggettoAbilitato(@PathParam("idSoggettoAbilitato") Long idSoggettoAbilitato,@PathParam("flgAutorizzazione") Boolean flgAutorizzazione, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}
