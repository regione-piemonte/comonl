/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.business.be.api.impl;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneFacade;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.RiceviComunicazioneDaSpicomResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.srv.business.be.api.SpicomApi;
import it.csi.comonl.comonlweb.srv.util.annotation.Logged;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.util.serialization.ObjectSerializer;
import it.csi.util.performance.StopWatch;


/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class SpicomApiServiceImpl extends BaseRestServiceImpl implements SpicomApi {

	@EJB
	private ComunicazioneFacade comunicazioneFacade;

	@Override
	public Response riceviComunicazioneDaSpicom(String commXml, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		log.info(ComonlClassUtils.truncClassName(getClass()), "[riceviComunicazioneDaSpicom]");
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();


		try {
			return invoke(() -> comunicazioneFacade.riceviComunicazioneDaSpicom(commXml));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "riceviComunicazioneDaSpicom()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass()) + ".riceviComunicazioneDaSpicom", "");
			watcher.stop();
		}

	}


}
