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
package it.csi.comonl.comonlweb.web.util.interceptor.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * Interceptor to write GZIP response
 */
@Provider
public class GzipWriterInterceptor implements WriterInterceptor {

	private HttpHeaders httpHeaders;
	


	/**
	 * Constructor
	 * @param httpHeaders the HTTP headers
	 */
	public GzipWriterInterceptor(@Context @NotNull HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

		MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
		List<String> acceptEncoding = requestHeaders.getOrDefault(HttpHeaders.ACCEPT_ENCODING, new ArrayList<>());
		
		// Compress if client accepts GZIP encoding
		OutputStream os = null;
		try {
			for (String s : acceptEncoding) {
				if (s.contains("gzip")) {
					MultivaluedMap<String, Object> headers = context.getHeaders();
					headers.add(HttpHeaders.CONTENT_ENCODING, "gzip");

					os = new GZIPOutputStream(context.getOutputStream());
					context.setOutputStream(os);
					break;
				}
			}
			context.proceed();
		} finally {
			if(os != null) {
				os.close();
			}
		}
	}
}
