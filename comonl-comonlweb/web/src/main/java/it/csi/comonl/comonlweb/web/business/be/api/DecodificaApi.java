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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;

/**
 * API interface for /decodifica path
 */
@Path("decodifica")
@Produces({ MediaType.APPLICATION_JSON })
@Api(value = "decodifica")
public interface DecodificaApi {

	/**
	 * Gets the province
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("provincia/{codRegioneMin}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca province", tags = "decodifica", nickname = "getProvincia")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Provincia.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getProvincia(
			@PathParam("codRegioneMin") String codRegioneMin,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the atecofin decodifica
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("ateco-fin-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca ateco", tags = "decodifica", nickname = "postAtecofinDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postAtecofinDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the StatoComunicazione
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("stato-comunicazione/{flgRicerca}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca StatoComunicazione", tags = "decodifica", nickname = "getStatoComunicazione")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = StatoComunicazione.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getStatoComunicazione(
			@PathParam("flgRicerca") Boolean flgRicerca,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the CategoriaTirocinante
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("categ-tirocinante")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca CategTirocinante", tags = "decodifica", nickname = "getCategTirocinante")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = CategTirocinante.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCategTirocinante(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the CpiEntePromTirocinio
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("cpi")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca CategTirocinante", tags = "decodifica", nickname = "getCpiEntePromTirocinio")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Cpi.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCpi(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the EntePrevidenziale
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("ente-previdenziale")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca CategTirocinante", tags = "decodifica", nickname = "getEntePrevidenziale")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = EntePrevidenziale.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getEntePrevidenziale(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the CategoriaLavoratore
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("categ-lavoratore")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca CategLavoratore", tags = "decodifica", nickname = "getCategLavoratoreAssObbl")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = CategLavAssObbl.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCategLavoratoreAssObbl(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the comune decodifica
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("comune-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca comune", tags = "decodifica", nickname = "postComuneDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postComuneDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the Cittadinanza
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("cittadinanza")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Cittadinanza", tags = "decodifica", nickname = "getCittadinanza")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Cittadinanza.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCittadinanza(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the TitoloSoggiorno
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("titolo-soggiorno")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca TitoloSoggiorno", tags = "decodifica", nickname = "getTitoloSoggiorno")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = StatusStraniero.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTitoloSoggiorno(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the MotivoPermesso
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("motivo-permesso")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca MotivoPermesso", tags = "decodifica", nickname = "getMotivoPermesso")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = MotivoPermesso.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getMotivoPermesso(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	
	/**
	 * Gets the stati esteri decodifica
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("stati-esteri-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca stato estero", tags = "decodifica", nickname = "postStatiEsteriDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postStatiEsteriDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	

	/**
	 * Gets the ccnl
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("ccnl-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca ccnl", tags = "decodifica", nickname = "postCcnlDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postCcnlDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	
	
	/**
	 * Gets the Questura
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("questura")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Questura", tags = "decodifica", nickname = "getQuestura")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Questura.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getQuestura(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the Qualifica istat decodifica
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("qualifica-istat-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca qualifica istat", tags = "decodifica", nickname = "postQualificaDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postQualificaDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	

	/**
	 * Gets the LivelloStudio
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("livello-studio")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca LivelloStudio", tags = "decodifica", nickname = "getLivelloStudio")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = LivelloStudio.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getLivelloStudio(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the NaturaGiuridica
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("natura-giuridica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca NaturaGiuridica", tags = "decodifica", nickname = "getNaturaGiuridica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = NaturaGiuridica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getNaturaGiuridica(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
	 * Gets the StatoEsteroCf
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("stato-estero")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca StatoEsteroCf", tags = "decodifica", nickname = "getStatoEsteroCf")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = StatiEsteri.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getStatoEsteroCf(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the livello retribuzione decodifica
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@POST
	@Path("livello-retribuzione-decodifica")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca inquadramento", tags = "decodifica", nickname = "postLivelloRetribuzioneDecodifica")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = DecodificaGenerica.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response postLivelloRetribuzioneDecodifica(DecodificaGenerica filtro, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the CaricaPersonaPv
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("carica-persona-pv")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca CaricaPersonaPv", tags = "decodifica", nickname = "getCaricaPersonaPv")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = CaricaPersonaPv.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCaricaPersonaPv(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the Toponimo
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("toponimo")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Toponimo", tags = "decodifica", nickname = "getToponimo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Toponimo.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getToponimo(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	
	/**
	 * Gets the StatoDelega
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("stato-delega")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca StatoDelega", tags = "decodifica", nickname = "getStatoDelega")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = StatoDelega.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getStatoDelega(
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	

	@GET
	@Path("trasformazionerl/{tipo}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Trasformazionerl", tags = "decodifica", nickname = "getTrasformazionerlByTipo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Trasformazionerl.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getTrasformazionerlByTipo(
			@PathParam("tipo") String tipo,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	@GET
	@Path("gradoContrattuale")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca GradoContrattuale", tags = "decodifica", nickname = "getGradoContrattuale")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = GradoContrattuale.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getGradoContrattuale(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest httpRequest);
	@GET
	@Path("cessazionerl")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca Cessazionerl", tags = "decodifica", nickname = "getCessazionerl")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Cessazionerl.class, responseContainer = "List"),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getCessazionerl(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest httpRequest);
	
	@GET
	@Path("getPersonalizzazioneByPv/{pv}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca persnalizzazine by pv", tags = "decodifica", nickname = "getPersonalizzazioneByPv")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = Personalizzazione.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getPersonalizzazioneByPv(@PathParam("pv") String pv,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	

	/**
	 * Gets the VariazioneSomm
	 * 
	 * @param securityContext the security context
	 * @param httpHeaders     the HTTP headers
	 * @param httpRequest     the HTTP request
	 * @return the response
	 */
	@GET
	@Path("variazione-somm/tipo-somm/{idTipoSomm}/tipo-comunicazione/{idTipoComunicazione}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ricerca VariazioneSomm", tags = "decodifica", nickname = "getVariazioneSommByTipoSommAndTipoCom")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Dati registrati su sistema.", response = VariazioneSomm.class),
			@ApiResponse(code = 0, message = "Errore generico.", response = ApiError.class) })
	public Response getVariazioneSommByTipoSommAndTipoCom(
			@PathParam("idTipoSomm") Long idTipoSomm,@PathParam("idTipoComunicazione") String idTipoComunicazione,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);


}
