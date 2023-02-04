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
/*
 * *********************************************
 * CSI PIEMONTE
 **********************************************/
package it.csi.comonl.comonlweb.web.business.be.api;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer.MimeType;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PagedResponseRicercaComunicazioni;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PagedResponseRicercaVardatori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperRapporto;
import it.csi.comonl.comonlweb.web.dto.WebComunicazioneFileHolder;

/**
 * API interface for /comunicazione path
 */
@Path("comunicazione")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "comunicazione")
public interface ComunicazioneApi {

	@POST
	@Path("upload-comunicazioni")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Upload per verifica file", tags = "comunicazione", nickname = "uploadComunicazioni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = String.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	@ApiImplicitParams(
			{
				@ApiImplicitParam(dataType = "file", name = "attachment", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "email", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "nomeFile", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isVerifica", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "ilRuolo", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "codiceFiscaleUtente", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "dsCognome", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "dsNome", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "codiceFiscaleAzienda", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "consulenteRespo", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isAmministratore", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isOperatoreProvinciale", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isDelegatoRespo", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isLegaleRappresentante", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "boolean", name = "isPersonaAutorizzata", paramType = "formData", required = true),
				@ApiImplicitParam(dataType = "string", name = "emailRuolo", paramType = "formData", required = false),
			})
	public Response uploadComunicazioni(
			@ApiParam(hidden = true) @MultipartForm WebComunicazioneFileHolder webComunicazioneFileHolder,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


	@POST
	@Path("stampa-ricerca-comunicazioni")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "stampa ricerca comunicazioni", tags = "comunicazione", nickname = "stampaRicercaComunicazioni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaRicercaComunicazioni(
			FormRicercaComunicazione ricercaComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	@POST
	@Path("stampa-ricerca-comunicazioni-vardatori")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "stampa ricerca comunicazioni vardatori", tags = "comunicazione", nickname = "stampaRicercaComunicazioniVardatori")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaRicercaComunicazioniVardatori(
			FormRicercaComunicazione ricercaComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);


	@POST
	@Path("ricerca")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "ricerca comunicazioni", tags = "comunicazione", nickname = "postRicercaComunicazioni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaComunicazioni.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaComunicazioni(
			FormRicercaComunicazione ricercaComunicazione,
			@Min(0) @QueryParam("offset") Integer page,
			@Min(1) @Max(100) @QueryParam("limit") Integer limit, @QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	@POST
	@Path("recupero")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "recupero comunicazioni", tags = "comunicazione", nickname = "postRecuperoComunicazioni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = RecuperoComunicazione.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRecuperoComunicazioni(
			FormRecuperoComunicazione recuperoComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);



	@POST
	@Path("ricerca-vardatori")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "ricerca comunicazioni vardatori", tags = "comunicazione", nickname = "postRicercaVardatori")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaVardatori.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaVardatori(
			FormRicercaComunicazione ricercaComunicazione,
			@Min(0) @QueryParam("offset") Integer page,
			@Min(1) @Max(100) @QueryParam("limit") Integer limit, @QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@POST
	@Path("ricercaVardatori")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "ricerca comunicazioni vardatori", tags = "comunicazione", nickname = "postRicercaComunicazioniVardatori")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaComunicazioni.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaComunicazioniVardatori(
			FormRicercaComunicazione ricercaComunicazione,
			@Min(0) @QueryParam("offset") Integer page,
			@Min(1) @Max(100) @QueryParam("limit") Integer limit, @QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);


	/**
	 * Gets a Comunicazione by its id
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Comunicazione", tags = "comunicazione", nickname = "getComunicazioneById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getComunicazioneById(@PathParam("id") Long id, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@POST
	@Path("urg")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Comunicazione di Urgenza", tags = "comunicazione", nickname = "postComunicazioneUrgenza")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Long.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postComunicazioneUrgenza(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	@PUT
	@Path("urg")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Comunicazione di Urgenza", tags = "comunicazione", nickname = "putComunicazioneUrgenza")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Long.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putComunicazioneUrgenza(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


	/**
	 * Gets a Comunicazione by its id
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("urg-holder/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Comunicazione", tags = "comunicazione", nickname = "getComunicazioneUrgenzaHolderById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = ComunicazioneUrgHolder.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getComunicazioneUrgHolderById(@PathParam("id") Long id, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);



	@POST
	@Path("verificaDatore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Datore.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response verificaDatore(WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);



	/**
	 * Stampa comunicazione by its id
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("stampa/{id}/{operatoreProvinciale}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "Comunicazione", tags = "comunicazione", nickname = "stampaComunicazioneById")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaComunicazioneById(@PathParam("id") Long id, @PathParam("operatoreProvinciale") Boolean operatoreProvinciale, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * Save a comunicazione
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Comunicazione", tags = "comunicazione", nickname = "postComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postComunicazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	/**
	 * Save a comunicazione vardatore
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("vardatore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Comunicazione vardatore", tags = "comunicazione", nickname = "postComunicazioneVardatore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postComunicazioneVardatore(
			WrapperComunicazione wrapper,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	/**
	 * Save a comunicazione
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put Comunicazione", tags = "comunicazione", nickname = "putComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putComunicazione(
			WrapperComunicazione wrapper,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	
	
	
	
	
	/**
	 * Save a comunicazione
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("vardatore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put Comunicazione vardatore", tags = "comunicazione", nickname = "putComunicazioneVardatore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putComunicazioneVardatore(
			WrapperComunicazione wrapper,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	
	
	
	

	/**
	 * Cancel a comunicazione
	 * 
	 * @param wrapperComunicazione   the comunicazione to save and the Role
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("annulla")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cancel Comunicazione", tags = "comunicazione", nickname = "annullaComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response annullaComunicazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	
	/**
	 * Cancel a comunicazione
	 * 
	 * @param wrapperComunicazione   the comunicazione to save and the Role
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("annulla-vardatore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cancel Comunicazione", tags = "comunicazione", nickname = "annullaComunicazioneVardatore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response annullaComunicazioneVardatore(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	

	/**
	 * Rettifica una comunicazione
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("rettifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Rettifica Comunicazione", tags = "comunicazione", nickname = "rettificaComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response rettificaComunicazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	/**
	 * Rettifica una comunicazione Vardatore
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("rettifica-vardatore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Rettifica Comunicazione", tags = "comunicazione", nickname = "rettificaComunicazioneVardatore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response rettificaComunicazioneVardatore(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * Cancel a comunicazione
	 * 
	 * @param comunicazione   the comunicazione to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("invia")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Invia Comunicazione", tags = "comunicazione", nickname = "inviaComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response inviaComunicazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


	/**
	 * Save a tutore
	 * 
	 * @param tutore   the tutore to save
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("tutore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Tutore", tags = "comunicazione", nickname = "postTutore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Tutore.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postTutore(
			Comunicazione comunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * Update a tutore
	 * 
	 * @param tutore   the tutore to update
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("tutore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put Tutore", tags = "comunicazione", nickname = "putTutore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Tutore.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putTutore(
			Comunicazione comunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * POST a rapporto tipo Missione
	 * 
	 * @param wrapperComunicazione the rapporto to save, the role
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("rapporto-missione")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Rapporto Missione", tags = "comunicazione", nickname = "postRapportoMissione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Rapporto.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postRapportoMissione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * PUT a rapporto
	 * 
	 * @param wrapperRapporto   the rapporto to update and the role
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("rapporto-missione")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post Rapporto Missione", tags = "comunicazione", nickname = "putRapportoMissione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Rapporto.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putRapportoMissione(
			WrapperRapporto wrapperRapporto,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * PUT dati proroga del rapporto
	 * 
	 * @param comunicazione     
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("dati-proroga")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put dati proroga del rapporto", tags = "comunicazione", nickname = "putDatiProroga")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putDatiProroga(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * PUT dati trasformazione del rapporto
	 * 
	 * @param comunicazione     
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("dati-trasformazione")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put dati trasformazione del rapporto", tags = "comunicazione", nickname = "putDatiTrasformazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putDatiTrasformazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * PUT dati cessazione del rapporto
	 * 
	 * @param comunicazione     
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("dati-cessazione")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put dati cessazione del rapporto", tags = "comunicazione", nickname = "putDatiCessazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putDatiCessazione(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


	/**
	 * PUT dati traferimento e distacco
	 * 
	 * @param comunicazione     
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("dati-trasferimento-distacco")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put dati trasferimento e distacco", tags = "comunicazione", nickname = "putDatiTrasferimentoDistacco")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putDatiTrasferimentoDistacco(
			WrapperComunicazione wrapperComunicazione,		
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


	/**
	 *   INIZIO API per controlli 
	 * 
	 */

	/**
	 * chkDatiGenerali
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati generali
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkDatiGenerali")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati generali", tags = "comunicazione-controlli", nickname = "chkDatiGenerali")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkDatiGenerali(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * chkDatiGeneraliVardatori
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati generali di una comunicazione di vardatori
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkDatiGeneraliVardatori")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati generali", tags = "comunicazione-controlli", nickname = "chkDatiGeneraliVardatori")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkDatiGeneraliVardatori(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	

	/**
	 * chkAziendaUtilizzatrice
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati dell'azienda utilizzatrice
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkAziendaUtilizzatrice")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione azienda utilizzatrice", tags = "comunicazione-controlli", nickname = "chkAziendaUtilizzatrice")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkAziendaUtilizzatrice(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * chkImpresa
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati dell'impresa
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkImpresa")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati anagrafici impresa", tags = "comunicazione-controlli", nickname = "chkImpresa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkImpresa(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * chkLavoratore
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati del lavoratore (forse rivedere nel caso di più lavoratori)
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkLavoratore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati anagrafici del lavoratore", tags = "comunicazione-controlli", nickname = "chkLavoratore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce il lavoratore se non ci sono errori, contenente eventalmente dei warning.", response = Lavoratore.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkLavoratore(
			Lavoratore lavoratore,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * chkLavoratoriComunicazione
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati dei lavoratore
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkLavoratoriComunicazione")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati anagrafici dei lavoratori associati alla comunicazione", tags = "comunicazione-controlli", nickname = "chkLavoratoriComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkLavoratoriComunicazione(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * chkRapporto
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al rapporto di lavoro
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkRapporto")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati del rapporto di lavoro", tags = "comunicazione-controlli", nickname = "chkRapporto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkRapporto(
			Comunicazione comunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * chkTutore
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al tutore
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkTutore")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati del tutore", tags = "comunicazione-controlli", nickname = "chkTutore")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkTutore(
			Comunicazione comunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	
	
	
	/**
	 * chkDatiAziendaPrecedente
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al rapporto di lavoro
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkDatiAziendaPrecedente")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la sezione dei dati del datorePrecedente di una comunicazione di vardatori trasefrimento", tags = "comunicazione-controlli", nickname = "chkDatiAziendaPrecedente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkDatiAziendaPrecedente(
			Comunicazione comunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * chkSediLavoro
	 * 
	 * @param comunicazione   comunicazione di cui controllare i dati relativi al rapporto di lavoro
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("chkSediLavoro")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Controlla la tripletta rapportoVd, lavoratoreVd, sedeLavoroVd di una comunicazione di vardatori trasferimento", tags = "comunicazione-controlli", nickname = "chkSediLavoro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Restituisce la comunicazione passata se non ci sono errori, contenente eventalmente dei warning.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkSediLavoro(
			Comunicazione comunicazione,
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	

	/**
	 *   FINE API per controlli 
	 * 
	 */
	////////////////////////// RITRASMISSIONI COMMAX
	@POST
	@Path("ritrasmetti-comunicazioni")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Ritrasmissioni commax", tags = "comunicazione", nickname = "ritrasmettiComunicazioni")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = String.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response ritrasmettiComunicazioni(@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	/**
	 * Puts an stato Comunicazione by its id
	 * @param id the id
	 * @param securityContext the security context
	 * @param httpHeaders the HTTP headers
	 * @param httpRequest the HTTP request
	 * @return the response
	 */
	@PUT
	@Path("cancella/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Cancella Comunicazione", tags = "comunicazione", nickname = "cancellaComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Comunicazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response cancellaComunicazione(
			@PathParam("id") Long id,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 *
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("chkRiepilogo/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Comunicazione", tags = "comunicazione-controlli", nickname = "chkRiepilogo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = ApiError.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response chkRiepilogo(@PathParam("id") Long id, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	/**
	 *
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("checkDatiEssenziali")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Comunicazione", tags = "comunicazione-controlli", nickname = "checkDatiEssenziali")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Boolean.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response checkDatiEssenziali(
			WrapperComunicazione wrapperComunicazione,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
}
