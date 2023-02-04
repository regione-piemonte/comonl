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

import it.csi.comonl.comonlweb.ejb.business.be.facade.DelegatoImpresaFacade;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.web.business.be.api.DelegatoImpresaApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;
import it.csi.util.performance.StopWatch;

/**
 * Implementation for ComunicazioneApi
 */
@Logged
public class DelegatoImpresaApiServiceImpl extends BaseRestServiceImpl implements DelegatoImpresaApi {

	@EJB
	private DelegatoImpresaFacade delegatoImpresaFacade;

	@Override
	public Response setDataAnnullamentoDelegatoImpresa(String cfDelegato, String tipoAnagrafica, String cfImpresa,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegatoImpresaFacade.setDataAnnullamentoDelegatoImpresa(cfDelegato,tipoAnagrafica,cfImpresa));
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

	@Override
	public Response setDataAnnullamentoAllCfImpresa(String cfImpresa,Boolean flgAutorizzazione, Boolean flgAnnullamento, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegatoImpresaFacade.setDataAnnullamentoAllCfImpresa(cfImpresa,flgAutorizzazione,flgAnnullamento));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "setDataAnnullamentoAllCfImpresa()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".setDataAnnullamentoAllCfImpresa",
					"");
			watcher.stop();
		}
	}

	
	@Override
	public Response getDelegatoImpresaSpec(String cfImpresa, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegatoImpresaFacade.getDelegatoImpresaSpec(cfImpresa));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getDelegatoImpresaSpec()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".getDelegatoImpresaSpec",
					"");
			watcher.stop();
		}
	}
	
	@Override
	public Response postDelegatoImpresa(DelegatoImpresa delegatoImpresa, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		StopWatch watcher = new StopWatch(ComonlConstants.COMPONENT_NAME);
		watcher.start();
		try {
			return invoke(() -> delegatoImpresaFacade.postDelegatoImpresa(delegatoImpresa));
		} catch (Exception ex) {
			log.error(ComonlClassUtils.truncClassName(getClass()), ex);
			throw ex;
		} finally {
			watcher.dumpElapsed(ComonlClassUtils.truncClassName(getClass()), "getDelegatoImpresaSpec()",
					"invocazione API " + ComonlClassUtils.truncClassName(getClass())
							+ ".getDelegatoImpresaSpec",
					"");
			watcher.stop();
		}
	}
}
