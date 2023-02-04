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
package it.csi.comonl.comonlweb.web.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Filtro per la gestione di Angular
 * 
 * @author Marchino Alessandro
 */
public class AngularFilter implements Filter {

	/** Logger */
	private static final LogUtil LOG = new LogUtil(AngularFilter.class);

	/** The init-param to be used for the index */
	private static final String INDEXURL_INIT_PARAM = "indexUrl";
	/** The init-param to be used for the backend */
	private static final String BACKENDURL_INIT_PARAM = "backendUrl";

	private String indexUrl;
	private String backendUrl;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final String methodName = "doFilter";
		if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
			LOG.trace(methodName, "Request or response are NOT HTTP. Skip...");
			chain.doFilter(req, res);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String contextPath = httpRequest.getContextPath();
		String requestURI = httpRequest.getRequestURI();
		String angularPath = requestURI.substring(contextPath.length());
		if (isAngularRoute(angularPath)) {
			req.getRequestDispatcher(indexUrl).forward(req, res);
			return;
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.indexUrl = filterConfig.getInitParameter(INDEXURL_INIT_PARAM);
		this.backendUrl = filterConfig.getInitParameter(BACKENDURL_INIT_PARAM);

		// fix for local eclipse deployment
		if (this.indexUrl.indexOf("${") != -1) {
			this.indexUrl = "/index.html";
		}
		if (this.backendUrl.indexOf("${") != -1) {
			this.backendUrl = "/api/v1";
		}
	}

	private boolean isAngularRoute(String angularPath) {
		LOG.trace("isAngularRoute", isLogout(angularPath)+ " "+ angularPath);
		return !angularPath.contains(backendUrl) 
				&& !angularPath.contains(indexUrl)
				&& !isStaticResourcePath(angularPath)
				&& !isLogout(angularPath);
	}

	public static boolean isStaticResourcePath(String path) {
		return path.contains("/assets/")
				// Files
				|| path.endsWith(".html") || path.endsWith(".js") || path.endsWith(".css") || path.endsWith(".ico")
				|| path.endsWith(".svg") || path.endsWith(".map")
				// Fonts
				|| path.endsWith(".woff2") || path.endsWith(".woff") || path.endsWith(".ttf") || path.endsWith(".eot");
	}
	
	public static boolean isLogout(String path) {
		return path.contains("/logout");
	}
	
}
