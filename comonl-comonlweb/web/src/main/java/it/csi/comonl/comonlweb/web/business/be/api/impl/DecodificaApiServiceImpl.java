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
package it.csi.comonl.comonlweb.web.business.be.api.impl;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.ejb.business.be.facade.DecodificaFacade;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.DecodificaApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for DecodificaApi
 */
@Logged
public class DecodificaApiServiceImpl extends BaseRestServiceImpl implements DecodificaApi {

	@EJB
	private DecodificaFacade decodificaFacade;

	@Override
	public Response getProvincia(String codRegioneMIn, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getProvincia(codRegioneMIn));
	}

	@Override
	public Response postAtecofinDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.postAtecofinDecodifica(filtro));
	}

	@Override
	public Response getStatoComunicazione(Boolean flgRicerca, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getStatoComunicazione(flgRicerca));
	}

	@Override
	public Response getCategTirocinante(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCategTirocinante());
	}

	@Override
	public Response getCpi(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCpi());
	}

	@Override
	public Response getEntePrevidenziale(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getEntePrevidenziale());
	}

	@Override
	public Response getCategLavoratoreAssObbl(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCategLavoratoreAssObbl());
	}

	@Override
	public Response postComuneDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		return invoke(() -> decodificaFacade.postComuneDecodifica(filtro));
	}
	
	
	
	@Override
	public Response postCcnlDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		return invoke(() -> decodificaFacade.postCcnlDecodifica(filtro));
	}
	
	
	@Override
	public Response getCittadinanza(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCittadinanza());
	}

	@Override
	public Response getTitoloSoggiorno(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getTitoloSoggiorno());
	}
	
	@Override
	public Response getMotivoPermesso(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getMotivoPermesso());
	}

	@Override
	public Response getQuestura(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getQuestura());
	}

	@Override
	public Response getLivelloStudio(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getLivelloStudio());
	}
	
	@Override
	public Response getNaturaGiuridica(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getNaturaGiuridica());
	}
	
	@Override
	public Response getStatoEsteroCf(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getStatoEstero());
	}
	
	@Override
	public Response postLivelloRetribuzioneDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.postLivelloRetribuzioneDecodifica(filtro));
	}

	@Override
	public Response postStatiEsteriDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.postStatiEsteriDecodifica(filtro));
	}

	@Override
	public Response postQualificaDecodifica(DecodificaGenerica filtro, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.postQualificaDecodifica(filtro));
	}
	
	
	@Override
	public Response getCaricaPersonaPv(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCaricaPersonaPv());
	}
	
	@Override
	public Response getStatoDelega(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getStatoDelega());
	}
	
	@Override
	public Response getTrasformazionerlByTipo(String tipo, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getTrasformazionerlByTipo(tipo));
	}
	
	@Override
	public Response getGradoContrattuale(SecurityContext securityContext, HttpHeaders httpHeaders,HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getGradoContrattuale());
	}
	@Override
	public Response getCessazionerl(SecurityContext securityContext, HttpHeaders httpHeaders,HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getCessazionerl());
	}

	@Override
	public Response getToponimo(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getToponimo());
	}
	
	@Override
	public Response getPersonalizzazioneByPv(String pv, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getPersonalizzazioneByPv(pv));
	}

	@Override
	public Response getVariazioneSommByTipoSommAndTipoCom(Long idTipoSomm, String idTipoComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaFacade.getVariazioneSommByTipoSommAndTipoCom(idTipoSomm,idTipoComunicazione));
	}

	
}
