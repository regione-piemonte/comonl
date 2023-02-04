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

import java.io.File;
import java.util.Date;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.comonl.comonlweb.ejb.util.mime.MimeTypeContainer.MimeType;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PagedResponseRicercaAccreditamentoAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.PagedResponseRicercaAccreditamentoConsulente;

@Path("anagrafica-delegato")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "AnagraficaDelegato")
public interface AnagraficaDelegatoApi {

	@POST
	@Path("ricerca-accreditamento-consulente")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca accreditamento per consulenti e persone fisiche", tags = "AnagraficaDelegato", nickname = "postRicercaAccreditamentoConsulente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaAccreditamentoConsulente.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaAccreditamentoConsulente(
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche,
			@Min(0) @QueryParam("offset") Integer page, @Min(1) @Max(100) @QueryParam("limit") Integer limit,
			@QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@POST
	@Path("ricerca-accreditamento-zienda")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca accreditamento per studio prof. e aziende", tags = "AnagraficaDelegato", nickname = "postRicercaAccreditamentoAzienda")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = PagedResponseRicercaAccreditamentoAzienda.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response postRicercaAccreditamentoAzienda(
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche,
			@Min(0) @QueryParam("offset") Integer page, @Min(1) @Max(100) @QueryParam("limit") Integer limit,
			@QueryParam("sort") @DefaultValue("") String sort,
			@QueryParam("direction") @DefaultValue("asc") String direction, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Post AnagraficaDelegato", tags = "AnagraficaDelegato", nickname = "postAnagraficaDelegato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * Gets List DelegatoImpresa by its AnagraficaDelegatoId
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("delegato-impresa/cfDelegato/{cfDelegato}/tipoAnagrafica/{tipoAnagrafica}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restuisce i DelegatoImpresa di un'AnagraficaDelegato", tags = "AnagraficaDelegato", nickname = "getDelegatoImpresaByIdAnagraficadelegato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DelegatoImpresa.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getDelegatoImpresaByIdAnagraficaDelegato(@PathParam("cfDelegato") String cfDelegato,
			@PathParam("tipoAnagrafica") String tipoAnagrafica, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Put AnagraficaDelegato", tags = "AnagraficaDelegato", nickname = "putAnagraficaDelegato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * Gets List DelegatoImpresa by its AnagraficaDelegatoId
	 * 
	 * @param id              the id
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("id-soggetto-abilitato/{idSoggettoAbilitato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restuisce un lista di AnagraficaDelegato", tags = "AnagraficaDelegato", nickname = "getAnagraficaDelegatoByIdSoggettoAbilitato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getAnagraficaDelegatoByIdSoggettoAbilitato(
			@PathParam("idSoggettoAbilitato") Long idSoggettoAbilitato, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@PUT
	@Path("consulente/cf-delegato/{cfDelegato}/soggetto-abilitato/{idSoggettoAbilitato}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "addConsulente", tags = "AnagraficaDelegato", nickname = "addConsulente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response addConsulente(@PathParam("cfDelegato") String cfDelegato,
			@PathParam("idSoggettoAbilitato") Long idSoggettoAbilitato, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	@PUT
	@Path("remove-consulente/cf-delegato/{cfDelegato}/soggetto-abilitato/{idSoggettoAbilitato}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "removeConsulente", tags = "AnagraficaDelegato", nickname = "removeConsulente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response removeConsulente(@PathParam("cfDelegato") String cfDelegato,
			@PathParam("idSoggettoAbilitato") Long idSoggettoAbilitato, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
	@Path("data-annullamento-cde/cf-delegato/{cfDelegato}/tipo-anagrafica/{tipoAnagrafica}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Annulla o ripristina un AnagraficaDelegato impostando dataAnnullamento a data di sistema", tags = "AnagraficaDelegato", nickname = "setDataAnnullamentoCDE")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Date.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response setDataAnnullamentoCDE(@PathParam("cfDelegato") String cfDelegato,
			@PathParam("tipoAnagrafica") String tipoAnagrafica, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@PUT
	@Path("ins-azienda")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "addAzienda", tags = "AnagraficaDelegato", nickname = "addAzienda")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DelegatoImpresa.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response addAzienda(DelegatoImpresa delegatoImpresa, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	/**
	 * Gets AnagraficaAziAccent by its cfImpresa
	 * 
	 * @param cfImpresa       cfImpresa
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("azienda-accentrata/{cfImpresa}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restuisce un AnagraficaAziAccent", tags = "AnagraficaDelegato", nickname = "getAziendaAccentrata")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaAziAccent.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getAziendaAccentrata(@PathParam("cfImpresa") String cfImpresa,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@PUT
	@Path("azienda-accentrata")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "putAziendaAccentrata", tags = "AnagraficaDelegato", nickname = "putAziendaAccentrata")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaAziAccent.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response putAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	@POST
	@Path("azienda-accentrata")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "postAziendaAccentrata", tags = "AnagraficaDelegato", nickname = "postAziendaAccentrata")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaAziAccent.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postAziendaAccentrata(AnagraficaAziAccent anagraficaAziAccent,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * Stampa elenco anagrafiche delegati by its id
	 * 
	 * @param FormRicercaAccreditamentoAnagrafiche the
	 *                                             FormRicercaAccreditamentoAnagrafiche
	 * @param tipoFormato                          String (pdf / xsl)
	 * @param securityContext                      the security context
	 * @param httpHeaders                          the HTTP headers
	 * @param httpRequest                          the HTTP request
	 * @return the response
	 */
	@POST
	@Path("stampa-elenco-anagrafiche-delegato/{tipoFormato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "Stampa elenco anagrafica delegati", tags = "AnagraficaDelegato", nickname = "stampa-elenco-anagrafiche-delegato")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaElencoAnagraficheDelegati(@PathParam("tipoFormato") String tipoFormato,
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * Stampa elenco studi aziende by its id
	 * 
	 * @param FormRicercaAccreditamentoAnagrafiche the
	 *                                             FormRicercaAccreditamentoAnagrafiche
	 * @param tipoFormato                          String (pdf / xsl)
	 * @param securityContext                      the security context
	 * @param httpHeaders                          the HTTP headers
	 * @param httpRequest                          the HTTP request
	 * @return the response
	 */
	@POST
	@Path("stampa-elenco-studi-aziende/{tipoFormato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MimeType.PDF })
	@ApiOperation(value = "Stampa elenco studi aziende", tags = "AnagraficaDelegato", nickname = "stampa-elenco-studi-aziende")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Dati registrati su sistema.", response = File.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response stampaElencoStudiAziende(@PathParam("tipoFormato") String tipoFormato,
			FormRicercaAccreditamentoAnagrafiche formRicercaAccreditamentoAnagrafiche,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	/**
	 * Get Anagrafica delegato by cf
	 * 
	 * @param cfDelegato      the cfDelegato
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("{cfDelegato}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Restuisce AnagraficaDelegato", tags = "AnagraficaDelegato", nickname = "getAnagraficaDelegatoByCodiceFiscale")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = AnagraficaDelegato.class, responseContainer = "AnagraficaDelegato"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class),
			@ApiResponse(code = 400, message = "Errore generico.", response = ApiError.class) })
	public Response getAnagraficaDelegatoByCodiceFiscale(@PathParam("cfDelegato") String cfDelegato,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

}
