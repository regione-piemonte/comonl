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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperLavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;

/**
 * API interface for /silp path
 */
@Path("silp")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "silp")
public interface SilpApi {

	@GET
	@Path("azienda/{codiceFiscale}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Azienda su Silp", tags = "silp", nickname = "getAziendaSilp")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DatiAzienda.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getAziendaSilp(@PathParam("codiceFiscale") String codiceFiscale,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@GET
	@Path("aziendaAaep/{codiceFiscale}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Azienda su AAEP", tags = "silp", nickname = "getAziendaAaep")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DatiAzienda.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getAziendaAaep(@PathParam("codiceFiscale") String codiceFiscale,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@GET
	@Path("lavoratore/{codiceFiscale}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Lavoratore Espanso su Silp", tags = "silp", nickname = "getLavoratoreSilpEspanso")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = LavoratoreSilpEspanso.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getLavoratoreSilpEspanso(@PathParam("codiceFiscale") String codiceFiscale,
			@QueryParam("cognome") String cognome, @QueryParam("nome") String nome,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@GET
	@Path("sedi/{idAzienda}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Sedi su Silp", tags = "silp", nickname = "getSediSilp")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Sede.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getSediSilp(@PathParam("idAzienda") String idAzienda, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@PUT
	@Path("azienda/save")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put azienza", tags = "silp", nickname = "putAziendaSilp")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DatiAzienda.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putAziendaSilp(DatiAzienda datiAzienda, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@PUT
	@Path("lavoratore/save")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Put lavoratore", tags = "silp", nickname = "putLavoratoreSilp")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = LavoratoreSilpEspanso.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putLavoratoreSilp(LavoratoreSilpEspanso lavoratoreSilpEspanso,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	@PUT
	@Path("lavoratore/save-da-co/flg-rettifica/{flgRettifica}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Put lavoratore silp da co", tags = "silp", nickname = "putLavoratoreSilpDaCo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = String.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putLavoratoreSilpDaCo(WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso,
			@PathParam("flgRettifica") Boolean flgRettifica,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

}
