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
package it.csi.comonl.comonlweb.web.util.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Servlet implementation class LogOut
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SHIBBOLETH_COOKIE_PREFIX ="_shibsession_";
	private static final String SHIBBOLETH_COOKIE_EXCLUSION_SUFFIX ="_fgwars";
	private static final String LOGOUT_URL_INIT_PARAM = "logout.shibbolethSSOURL";
	
	private String shibbolethSSOLogoutUrl;
	
	/** Logger */
	private static final LogUtil LOG = new LogUtil(LogOutServlet.class);


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.shibbolethSSOLogoutUrl = config.getInitParameter(LOGOUT_URL_INIT_PARAM);
		LOG.warn("init", "INIZIALIZZATA SERVLET LOGOUT");
	}
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String methodName = "doGet";
		LOG.warn("doGet", "in doGet");
		
		String shibCookie = "";
		Cookie[] listaCookies = request.getCookies();
		if(listaCookies == null) {
			LOG.fatal(methodName, "non ho Cookie: ");
			return;
		}
		for(Cookie co : listaCookies) {
			//LOG.warn("cookie", co.getName());
			if(co.getName().indexOf(SHIBBOLETH_COOKIE_PREFIX)!=-1 && !(co.getName().indexOf(SHIBBOLETH_COOKIE_EXCLUSION_SUFFIX)!=-1)) {
				shibCookie = co.getName().replace(SHIBBOLETH_COOKIE_PREFIX,"");
				LOG.fatal(methodName, "Cookie name: " + co.getName());
				LOG.fatal(methodName, "Cookie value: " + co.getValue());
				LOG.fatal(methodName, "shibCookie: " + shibCookie);
			}
		}
		String url = estraiUrl(shibCookie);
		response.sendRedirect(url);
//		request.getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private String estraiUrl(String shib) {
		final String methodName = "estraiUrl";
		LOG.fatal(methodName, "start");
		LOG.fatal(methodName, "shibbolethSSOLogoutURL " + shibbolethSSOLogoutUrl);

		String replacedURL = shibbolethSSOLogoutUrl.replace("%%SHIB%%", shib);
		LOG.fatal(methodName, "url di logout --> " + replacedURL);
		return replacedURL;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
