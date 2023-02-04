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

import it.csi.comonl.comonlweb.ejb.business.be.facade.CommonFacade;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.CommonApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for CommonApi
 */
@Logged
public class CommonApiServiceImpl extends BaseRestServiceImpl implements CommonApi {

	@EJB
	private CommonFacade commonFacade;

	@Override
	public Response getRuoli(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getRuoli]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.getRuoli());
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getRuoli()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getRuoli", "");
			watcher.stop();
		}

	}

	@Override
	public Response insertUserAccessLog(UserAccessLog loUserLog, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[insertUserAccessLog]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.insertUserAccessLog(loUserLog));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "insertUserAccessLog()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".insertUserAccessLog", "");
			watcher.stop();
		}

	}

	@Override
	public Response preCompilaAnagraficaByCf(String cf, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAnagraficaByCf]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.preCompilaAnagraficaByCf(cf));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAnagraficaByCf()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAnagraficaByCf", "");
			watcher.stop();
		}
	}

	@Override
	public Response getParametroByDescrizione(String descrizioneParametro, SecurityContext securityContext,
			HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getParametroByDescrizione]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.getParametroByDescrizione(descrizioneParametro));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getParametroByDescrizione()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getParametroByDescrizione",
					"");
			watcher.stop();
		}
	}

	@Override
	public Response getApplicativoAbilitato(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getApplicativoAbilitato]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.getApplicativoAbilitato());
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getApplicativoAbilitato()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getApplicativoAbilitato", "");
			watcher.stop();
		}
	}

	@Override
	public Response getParametroCommaxById(Long idParametro, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getParametroById]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> commonFacade.getParametroCommaxById(idParametro));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getParametroById()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getParametroById",
					"");
			watcher.stop();
		}
	}

	
}
