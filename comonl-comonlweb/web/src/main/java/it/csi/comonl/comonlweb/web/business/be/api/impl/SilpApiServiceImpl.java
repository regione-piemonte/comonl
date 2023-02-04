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

import it.csi.comonl.comonlweb.ejb.business.be.facade.SilpFacade;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperLavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.SilpApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for SilpTestApi
 */
@Logged
public class SilpApiServiceImpl extends BaseRestServiceImpl implements SilpApi {

	@EJB
	private SilpFacade silpFacade;

	@Override
	public Response getAziendaSilp(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAziendaSilp]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.getAziendaSilp(codiceFiscale));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAziendaSilp()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAziendaSilp", "");
			watcher.stop();
		}

	}

	@Override
	public Response getAziendaAaep(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getAziendaAaep]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.getAziendaAaep(codiceFiscale));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getAziendaAaep()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getAziendaAaep", "");
			watcher.stop();
		}

	}

	@Override
	public Response getLavoratoreSilpEspanso(String codiceFiscale, String cognome, String nome,
			SecurityContext securityContext, HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getLavoratoreSilpEspanso]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.getLavoratoreSilpEspanso(codiceFiscale, cognome, nome));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getLavoratoreSilpEspanso()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + "getLavoratoreSilpEspanso", "");
			watcher.stop();
		}

	}

	@Override
	public Response getSediSilp(String idAzienda, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getSediSilp]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.getSediSilp(idAzienda));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getSediSilp()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getSediSilp", "");
			watcher.stop();
		}

	}

	@Override
	public Response putAziendaSilp(DatiAzienda datiAzienda, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putAziendaSilp]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.putAziendaSilp(datiAzienda));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "putAziendaSilp()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putAziendaSilp", "");
			watcher.stop();
		}

	}

	@Override
	public Response putLavoratoreSilp(LavoratoreSilpEspanso lavoratoreSilpEspanso, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putLavoratoreSilp]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.putLavoratoreSilp(lavoratoreSilpEspanso));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "putLavoratoreSilp()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putLavoratoreSilp", "");
			watcher.stop();
		}

	}
	
	
	@Override
	public Response putLavoratoreSilpDaCo(WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso,Boolean flgRettifica, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[putLavoratoreSilpDaCo]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> silpFacade.putLavoratoreSilpDaCo(wrapperLavoratoreSilpEspanso,flgRettifica));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "putLavoratoreSilpDaCo()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".putLavoratoreSilpDaCo", "");
			watcher.stop();
		}

	}

}
