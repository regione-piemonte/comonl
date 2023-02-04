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

import it.csi.comonl.comonlweb.ejb.business.be.facade.DecodificaTipoFacade;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.web.business.be.api.DecodificaTipoApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;

/**
 * Implementation for DecodificaApi
 */
@Logged
public class DecodificaTipoApiServiceImpl extends BaseRestServiceImpl implements DecodificaTipoApi {

	@EJB
	private DecodificaTipoFacade decodificaTipoFacade;
	
	@Override
	public Response getTipoComunicazione(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoComunicazione());
	}
	
	@Override
	public Response getTipoComunicazioneTu(Boolean flgRicerca,SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoComunicazioneTu(flgRicerca));
	}
	
	@Override
	public Response postTipoComunicazioneUnisommDecodifica(DecodificaGenerica filtro,SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		return invoke(() -> decodificaTipoFacade.postTipoComunicazioneUnisommDecodifica(filtro));
	}
	
	@Override
	public Response postTipoComunicazioneUnilavDecodifica(DecodificaGenerica filtro,SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		return invoke(() -> decodificaTipoFacade.postTipoComunicazioneUnilavDecodifica(filtro));
	}
	
	@Override
	public Response getTipologiaTirocinio(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipologiaTirocinio());
	}

	@Override
	public Response getTipoContratto(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoContratti());
	}

	@Override
	public Response getTipoEntePromTirocinio(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoEntePromTirocinio());
	}

	@Override
	public Response getTipoOrario(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoOrario());
	}

	@Override
	public Response getTipoAttoL68(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoAttoL68());
	}
	
	@Override
	public Response getTipoSoggettoAbilitato(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoSoggettoAbilitato());
	}
	
	@Override
	public Response getTipoStudioProfessionale(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoStudioProfessionale());
	}

	@Override
	public Response getTipoContrattiByTracciatoAndTipoComunicazione(String tipoTracciato, String tipoComunicazione,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoContrattiByTracciatoAndTipoComunicazione(tipoTracciato,tipoComunicazione));
	}

	@Override
	public Response getTipoTrasferimento(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		return invoke(() -> decodificaTipoFacade.getTipoTrasferimento());
	}
	

}
