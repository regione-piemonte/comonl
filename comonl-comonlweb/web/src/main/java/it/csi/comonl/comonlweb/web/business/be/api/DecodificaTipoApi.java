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
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;

/**
 * API interface for /decodifica path
 */
@Path("decodifica")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "decodifica")
public interface DecodificaTipoApi {
	
	/**
	 * Gets the TipoComunicazione
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-comunicazione")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoComunicazione", tags = "decodifica", nickname = "getTipoComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoComunicazione.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoComunicazione(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	

	/**
	 * Gets the TipoComunicazioneTu
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-comunicazione-tu/{flgRicerca}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoComunicazioneTu", tags = "decodifica", nickname = "getTipoComunicazioneTu")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoComunicazioneTu.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoComunicazioneTu(
			@PathParam("flgRicerca") Boolean flgRicerca,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the TipoComunicazione
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("tipo-comunicazione-unisomm")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Tipoi di comunicazione per tracciato UNISOMM", tags = "decodifica", nickname = "postTipoComunicazioneUnisommDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postTipoComunicazioneUnisommDecodifica(
			DecodificaGenerica filtro,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	/**
	 * Gets the TipoComunicazione
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("tipo-comunicazione-unilav")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Tipoi di comunicazione per tracciato UNILAV", tags = "decodifica", nickname = "postTipoComunicazioneUnilavDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postTipoComunicazioneUnilavDecodifica(
			DecodificaGenerica filtro,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipoContratto (sulla maschera Tipo rapporto)
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-contratto")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoContratto", tags = "decodifica", nickname = "getTipoContratto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoContratti.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoContratto(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipoContratto (sulla maschera Tipo rapporto)
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-contratti/tipo-tracciato/{tipoTracciato}/tipo-comunicazione/{tipoComunicazione}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoContratto", tags = "decodifica", nickname = "getTipoContrattiByTracciatoAndTipoComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoContratti.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoContrattiByTracciatoAndTipoComunicazione(
			@PathParam("tipoTracciato") String tipoTracciato, @PathParam("tipoComunicazione") String tipoComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipologiaTirocinio
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipologia-tirocinio")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipologiaTirocinio", tags = "decodifica", nickname = "getTipologiaTirocinio")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipologiaTirocinio.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipologiaTirocinio(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipologiaTirocinio
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-ente-promotore-tirocinio")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoEntePromTirocinio", tags = "decodifica", nickname = "getTipoEntePromTirocinio")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoEntePromTirocinio.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoEntePromTirocinio(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipoOrario
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-orario")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoOrario", tags = "decodifica", nickname = "getTipoOrario")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoOrario.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoOrario(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the TipoAttoL68
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-atto-l68")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoAttoL68", tags = "decodifica", nickname = "getTipoAttoL68")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoAttoL68.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoAttoL68(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	
	/**
	 * Gets the TipoSoggettoAbilitato
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-soggetto-abilitato")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoSoggettoAbilitato", tags = "decodifica", nickname = "getTipoSoggettoAbilitato")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoSoggettoAbilitato.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoSoggettoAbilitato(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the LivelloStudio
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-studio-professionale")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoSoggettoAbilitato", tags = "decodifica", nickname = "getTipoStudioProfessionale")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoSoggettoAbilitato.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoStudioProfessionale(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	
	/**
	 * Gets the LivelloStudio
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("tipo-trasferimento")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TipoVariazione", tags = "decodifica", nickname = "getTipoTrasferimento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = TipoTrasferimento.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTipoTrasferimento(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

}
