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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


import it.csi.comonl.comonlweb.ejb.business.be.facade.SoggettoAbilitatoFacade;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.SoggettoAbilitatoApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class SoggettoAbilitatoApiServiceImpl extends BaseRestServiceImpl implements SoggettoAbilitatoApi {

	@EJB
	private SoggettoAbilitatoFacade soggettoAbilitatoFacade;

	@Override
	public Response getSoggettoAbilitatoBycfSoggetto(String cfSoggetto, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getSoggettoAbilitatoBycfSoggetto]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> soggettoAbilitatoFacade.getSoggettoAbilitatoBycfSoggetto(cfSoggetto));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getSoggettoAbilitatoBycfSoggetto()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getSoggettoAbilitatoBycfSoggetto", "");
			watcher.stop();
		}
	}

	@Override
	public Response postSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postSoggettoAbilitato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {

			return invoke(() -> soggettoAbilitatoFacade.postSoggettoAbilitato(soggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"postSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".postSoggettoAbilitato", "");
			watcher.stop();
		}
	}

	@Override
	public Response putSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putSoggettoAbilitato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {

			return invoke(() -> soggettoAbilitatoFacade.putSoggettoAbilitato(soggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()),
					"putSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato)",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putSoggettoAbilitato", "");
			watcher.stop();
		}
	}

	@Override
	public Response getSoggettoAbilitatoById(Long idSeoggettoAbilitato, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getSoggettoAbilitatoById]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> soggettoAbilitatoFacade.getSoggettoAbilitatoById(idSeoggettoAbilitato));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getSoggettoAbilitatoById()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getSoggettoAbilitatoById", "");
			watcher.stop();
		}
	}

	@Override
	public Response setDataAnnullamentoSoggettoAbilitato(Long idSoggettoAbilitato,Boolean flgAutorizzazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> soggettoAbilitatoFacade.setDataAnnullamentoSoggettoAbilitato(idSoggettoAbilitato,flgAutorizzazione));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "setDataAnnullamentoDelegatoImpresa()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".setDataAnnullamentoDelegatoImpresa",
					"");
			watcher.stop();
		}
		
	}

}
