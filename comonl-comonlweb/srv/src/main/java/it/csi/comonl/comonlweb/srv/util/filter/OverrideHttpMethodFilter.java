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
package it.csi.comonl.comonlweb.srv.util.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * Pre-JAX-RS-mapping filter to handle the X-HTTP-Method-Override to support old-style proxies
 */
@Provider
// Pre-matching, since it must be executed BEFORE the method resolution; otherwise the mapping between the HTTP method and the declared endpoint will be already done
@PreMatching
public class OverrideHttpMethodFilter implements ContainerRequestFilter {

	private static final String HEADER_NAME = "X-HTTP-Method-Override";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String receivedMethod = requestContext.getMethod();
	    String methodFromHeader = requestContext.getHeaderString(HEADER_NAME);
	    if (methodFromHeader != null && !methodFromHeader.equals(receivedMethod)) {
	        requestContext.setMethod(methodFromHeader);
	    }
	}

}
