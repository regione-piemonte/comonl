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
import java.security.SecureRandom;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

/**
 * Il filtro agisce su tutte le richieste in input. Serve a prevenire attacchi
 * di tipo XSRF. L'idea &eacute; che il primo accesso avvenga sulla risorsa di
 * accesso della componente SPA (es. index.html). In quell'occasione il filtro
 * non fa verifiche ma si preoccupa di creare un nuovo cookie XSRF se non
 * presente e passarlo al client. Nelle successive richieste, se la richiesta
 * &eacute; destinata allo strato di servizi rest, viene effettuata la verifica
 * di presenza/corrispondenza di cookie+header XSRF. Questo comportamento, unito
 * alla corretta gestione client-side del protocollo, permette di evitare il
 * richiamo dello strato rest al di fuori del contesto corretto.
 */
public class XSRFProtectionFilter implements Filter {

	/** Logger */
	private static final LogUtil LOG = new LogUtil(XSRFProtectionFilter.class);
	/**
	 * Nome dell'header XSRF che la componente client deve inserire ad ogni
	 * richiesta rest
	 */
	private static final String XSRF_HEADER_NAME = "X-XSRF-TOKEN";
	/** Nome del cookie XSRF */
	private static final String XSRF_COOKIE_NAME = "XSRF-TOKEN";
	private static final SecureRandom RANDOM = new SecureRandom();

	/* init-params */
	/**
	 * Nome del parametro di inizializzazione che serve per disabilitare il
	 * meccanismo. Serve negli scenari di sviluppo in cui la parte client non
	 * &eacute; deployata nell'ear dei servizi
	 */
	private static final String DISABLED_PARAM_NAME = "disabled";

	/** Di default il filter abilita le verifiche */
	private boolean isDisabled = false;

	@Override
	public void destroy() {
		// nothing to do
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		isDisabled = "true".equals(cfg.getInitParameter(DISABLED_PARAM_NAME));
		// fix for local eclipse deployment
		if (cfg.getInitParameter(DISABLED_PARAM_NAME).indexOf("${") != -1) {
			isDisabled = true;
		}
	}

	/**
	 * Azione del filtro:
	 * <ul>
	 * <li>creazione di una nuova sessione se non ancora presente</li>
	 * <li>se la richiesta corrente e' destinata alla parte SPA:
	 * <ul>
	 * <li>viene creato un nuovo token che viene restituito come cookie</li>
	 * </ul>
	 * </li>
	 * <li>se la richiesta corrente &eacute; destinata alla parte REST:
	 * <ul>
	 * <li>se la request contiene una coppia di cookie e header XSRF validi =>
	 * procedo, altrimenti errore.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final String methodName = "doFilter";
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		if (!isDisabled) {
			if (!isRestRequest(httpReq)) {
				LOG.trace(methodName, "Request is NOT a REST request");
				if (!isXSRFAlive(httpReq)) {
					LOG.debug(methodName, "XSRF token NOT already initialized: creating new one");
					String newToken = createNewXSRFToken();
					addXSRFCookie(httpRes, newToken);
					LOG.debug(methodName, "New XSRF token created and added to session and response");
				} else {
					LOG.trace(methodName, "XSRF token already initialized: nothing to do");
				}
			} else {
				// REST request: token must be valid
				LOG.trace(methodName, "Request is a REST request]");
				if (validXSRFCookieAndHeader(httpReq)) {
					LOG.debug(methodName, "XSRF cookie and header matches in request => OK");
				} else {
					LOG.error(methodName, "Invalid XSRF Header in request");
					throw new ServletException("Invalid XSRF HEADER");
				}
			}
		}
		setNoCache(httpRes);
		chain.doFilter(req, res);
	}

	/**
	 * &eacute; necessario fare in modo che le risorse (pagine, etc) non siano
	 * mantenute nella cache del browser per evitare che, ad un accesso in una
	 * sessione successiva, la fase di ingresso (e quindi di ricreazione del token)
	 * venga bypassata. Se ci&oacute; accadesse le richieste REST sarebbero le prime
	 * ad arrivare al server e si ricadrebbe nella casistica di token non ancora
	 * inizializzato.
	 * 
	 * @param httpRes la response HTTP
	 */
	private void setNoCache(HttpServletResponse httpRes) {
		httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpRes.setHeader("Pragma", "no-cache");
		httpRes.setHeader("Expires", "0");
	}

	/**
	 * Controlla se il path della request corrisponda a una richiesta REST
	 * 
	 * @param httpReq la request HTTP
	 * @return true se il path rappresenta una richiesta alla parte REST
	 *         dell'applicazione che per default deve contenere la stringa "/api/v1"
	 */
	private boolean isRestRequest(HttpServletRequest httpReq) {
		return httpReq.getRequestURI() != null && httpReq.getRequestURI().contains("/api/v1");
	}

	/**
	 * Aggiunge alla response il cookie XSRF
	 * 
	 * @param httpRes la response in cui aggiungere il cookie
	 * @param token   il valore del cookie
	 */
	private void addXSRFCookie(HttpServletResponse httpRes, String token) {
		Cookie c = new Cookie(XSRF_COOKIE_NAME, token);
		c.setPath("/");
		c.setMaxAge(24 * 60 * 60);

		httpRes.addCookie(c);
	}

	/**
	 * Crea un nuovo token XSRF
	 * 
	 * @return il token creato
	 */
	private String createNewXSRFToken() {
		return RANDOM.nextLong() + "" + RANDOM.nextLong();
	}

	/**
	 * verifica se la richiesta possiede una coppia cookie/header XSRF validi e
	 * coincidenti con quanto memorizzato in sessione (se presente)
	 * 
	 * @param httpReq la request
	 * @return il risultato della verifica
	 */
	private boolean validXSRFCookieAndHeader(HttpServletRequest httpReq) {
		String actualRequestHeader = getActualXSRFHeader(httpReq);
		String actualRequestCookie = getActualXSRFCookie(httpReq);
		if (actualRequestHeader == null) {
			// TODO: to check
			actualRequestHeader = actualRequestCookie;
		}
		return actualRequestHeader != null && actualRequestCookie != null;
	}

	/**
	 *
	 * @param hreq
	 * @return l'header XSRF se presente nella request
	 */
	private String getActualXSRFHeader(HttpServletRequest hreq) {
		return hreq.getHeader(XSRF_HEADER_NAME);
	}

	/**
	 * Ottiene il cookie XSRF
	 * 
	 * @param httpReq la request
	 * @return il valore del cookie XSRF, se presente nella request; null altrimenti
	 */
	private String getActualXSRFCookie(HttpServletRequest httpReq) {
		Cookie[] cookies = httpReq.getCookies();
		if (cookies == null) {
			return null;
		}
		return Stream.of(cookies).filter(cookie -> XSRF_COOKIE_NAME.contentEquals(cookie.getName()))
				.map(Cookie::getValue).findFirst().orElse(null);
	}

	/**
	 * Controlla che l'XSRF sia presente
	 * 
	 * @param httpReq la request HTTP
	 * @return se il cookie sia presente
	 */
	private boolean isXSRFAlive(HttpServletRequest httpReq) {
		return getActualXSRFCookie(httpReq) != null;
	}
}
