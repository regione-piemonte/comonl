/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.util.oauth;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * SOAP handler for injection the OAuth2 authorization headers
 * @author Marchino Alessandro
 */
public class OAuth2SOAPHandler implements SOAPHandler<SOAPMessageContext> {
	
	private final OAuth2Helper oAuth2Helper;

	/**
	 * Injection constructor
	 * @param oAuth2Helper the helper
	 */
	public OAuth2SOAPHandler(OAuth2Helper oAuth2Helper) {
		this.oAuth2Helper = oAuth2Helper;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (Boolean.TRUE.equals(isOutbound)) {
			// Only for outbound messages (ie: requests)
			@SuppressWarnings("unchecked")
			Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);

			if (headers == null) {
				// Initialize headers to put OAuth2 Authorization
				headers = new HashMap<>();
			}

			headers.put("Authorization", Collections.singletonList("Bearer " + oAuth2Helper.getToken()));
			context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// Ignore fault
		return false;
	}

	@Override
	public void close(MessageContext context) {
		// Nothing to close
	}

	@Override
	public Set<QName> getHeaders() {
		// No headers retrieved
		return null;
	}

}
