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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.ejb.business.be.facade.DelegaFacade;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.DelegaApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class DelegaApiServiceImpl extends BaseRestServiceImpl implements DelegaApi {

	@EJB
	private DelegaFacade delegaFacade;

	@Override
	public Response postRicercaDeleghe(FormRicercaDelega ricercaDelega, @Min(0) Integer page,
			@Min(1) @Max(100) Integer limit, String sort, String direction, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[postRicercaDeleghe]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegaFacade.postRicercaDeleghe(page, limit, sort, direction,
					ricercaDelega));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRicercaDeleghe()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getRicercaDeleghe", "");
			watcher.stop();
		}
	}
	
	@Override
	public Response stampaRicercaDeleghe(
			FormRicercaDelega ricercaDelega, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[stampaRicercaDeleghe]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegaFacade.stampaRicercaDeleghe(ricercaDelega));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "stampaRicercaDeleghe()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".stampaRicercaDeleghe", "");
			watcher.stop();
		}
	}
	
	
	@Override
	public Response aggiornaStatoDelega(Delega delega, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[aggiornaStatoDelega]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegaFacade.aggiornaStatoDelega(delega));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "aggiornaStatoDelega()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".aggiornaStatoDelega", "");
			watcher.stop();
		}

	}
	
	
	@Override
	public Response aggiornaDelega(Delega delega, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[aggiornaDelega]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegaFacade.aggiornaDelega(delega));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "aggiornaDelega()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".aggiornaDelega", "");
			watcher.stop();
		}

	}
	

}
