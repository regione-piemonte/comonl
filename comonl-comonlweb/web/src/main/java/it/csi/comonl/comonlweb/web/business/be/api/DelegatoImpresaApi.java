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
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaSpec;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoAllCfImpresaRes;



/**
 * API interface for /delegato-impresa path
 */
@Path("delegato-impresa")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "DelegatoImpresa")
public interface DelegatoImpresaApi {
	
	
	/**
	 * 
	 * @param cfDelegato
	 * @param tipoAnagrafica
	 * @param securityContext
	 * @param httpHeaders
	 * @param httpRequest
	 * @return
	 */
	@PUT
	@Path("annullamento-ripristino/cf-delegato/{cfDelegato}/tipo-anagrafica/{tipoAnagrafica}/cf-impresa{cfImpresa}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Annulla o ripristina un DelegatoImpresa impostando dataAnnullamento a data di sistema", tags = "DelegatoImpresa", nickname = "setDataAnnullamentoDelegatoImpresa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Date.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response setDataAnnullamentoDelegatoImpresa(@PathParam("cfDelegato") String cfDelegato, @PathParam("tipoAnagrafica") String tipoAnagrafica,@PathParam("cfImpresa") String cfImpresa, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	/**
	 * 
	 * @param cfImpresa
	 * @param securityContext
	 * @param httpHeaders
	 * @param httpRequest
	 * @return
	 */
	@PUT
	@Path("annullamento-ripristino/cf-impresa/{cfImpresa}/auto/{flgAutorizzazione}/flg-annullamento/{flgAnnullamento}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Annulla o ripristina un DelegatoImpresa impostando dataAnnullamento a data di sistema", tags = "DelegatoImpresa", nickname = "setDataAnnullamentoAllCfImpresa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = SetDataAnnullamentoAllCfImpresaRes.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response setDataAnnullamentoAllCfImpresa(@PathParam("cfImpresa") String cfImpresa, @PathParam("flgAutorizzazione") Boolean flgAutorizzazione,@PathParam("flgAnnullamento") Boolean flgAnnullamento,@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	@GET
	@Path("delegato-impresa-spec/cf-impresa/{cfImpresa}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restuisce i DelegatoImpresa arricchiti di una impresa", tags = "DelegatoImpresa", nickname = "getDelegatoImpresaSpec")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DelegatoImpresaSpec.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getDelegatoImpresaSpec(@PathParam("cfImpresa") String cfImpresa, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@POST
	@ApiOperation(value = "Inserisce il delegato impresa", tags = "DelegatoImpresa", nickname = "postDelegatoImpresa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DelegatoImpresa.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postDelegatoImpresa(
			DelegatoImpresa delegatoImpresa
			, @Context SecurityContext securityContext	
			, @Context HttpHeaders httpHeaders
			, @Context HttpServletRequest httpRequest);
	
}
