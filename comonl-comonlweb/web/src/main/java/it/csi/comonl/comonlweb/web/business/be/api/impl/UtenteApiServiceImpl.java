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

import it.csi.comonl.comonlweb.ejb.business.be.facade.UtenteFacade;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.UtenteApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for InterventoApi
 */
@Logged
public class UtenteApiServiceImpl extends BaseRestServiceImpl implements UtenteApi {

	@EJB
	private UtenteFacade utenteFacade;

	@Override
	public Response getUtenteSelf(SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		log.info(ComonlClassUtils.truncClassName(getClass()), "[getUtenteSelf]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> utenteFacade.getUtenteSelf());
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getUtenteSelf()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".getUtenteSelf", "");
			watcher.stop();
		}
	}

}
