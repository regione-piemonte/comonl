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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.comonl.comonlweb.web.business.be.api.SystemApi;
import it.csi.comonl.comonlweb.web.util.annotation.Logged;

/**
 * Implementation for SystemApi
 */
@Logged
public class SystemApiServiceImpl extends BaseRestServiceImpl implements SystemApi {

	@Override
	public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		String message = "ping versione allineata al 2022/01/04 ore 09.13";
		System.out.println(message);
		log.info("ping", message);
		return Response.ok().build();
	}

	@Override
	public Response testError(Integer code, SecurityContext securityContext, HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest) {
		String message = "test error - code: " + code;
		System.out.println(message);
		log.info("testError", message);

		Set<String> setKey = httpHeaders.getRequestHeaders().keySet();
		Iterator<String> iterKey = setKey.iterator();
		while (iterKey.hasNext()) {
			String headerName = (String) iterKey.next();
			String headerValue = httpHeaders.getHeaderString(headerName);
			log.info("testError", "keys - " + headerName + " = " + headerValue);
		}

		Enumeration<String> headers = httpRequest.getHeaderNames();
		while (headers != null && headers.hasMoreElements()) {
			String headerName = (String) headers.nextElement();
			// if (!headerName.equals("Shib-Attributes") &&
			// !headerName.equals("Shib-Application-ID")
			// && ((headerName.startsWith("Shib-") ||
			// headerName.equalsIgnoreCase("remote_user")))) {
			log.info("testError", "request - " + headerName + " = " + httpRequest.getHeader(headerName));
			// }
		}

		return Response.status(code).build();
	}

}
