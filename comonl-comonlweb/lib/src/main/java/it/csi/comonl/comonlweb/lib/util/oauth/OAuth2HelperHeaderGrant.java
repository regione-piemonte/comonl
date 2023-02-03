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

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import it.csi.comonl.comonlweb.lib.util.http.HttpHelper;

/**
 * Thread-safe implementation of a OAuth2 Helper class via the grant sent in the header
 * @author Marchino Alessandro
 *
 */
public class OAuth2HelperHeaderGrant extends OAuth2Helper {
	
	/** The authorization header content */
	private final String authorization;
	
	/**
	 * Constructor
	 * @param oAuthURL
	 * @param consumerKey
	 * @param consumerSecret
	 * @param timeout
	 * @param staleThreshold
	 */
	OAuth2HelperHeaderGrant(String oAuthURL, String consumerKey, String consumerSecret, long timeout, long staleThreshold) {
		super(oAuthURL, consumerKey, consumerSecret, timeout, staleThreshold);
		String auth = consumerKey + ":" + consumerSecret;
		this.authorization = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
	}

	@Override
	void initToken() {
		final String methodName = "initToken";
		log.trace(methodName, () -> "OAuth2 URI: " + this.oAuthURL + " - Consumer key: " + this.consumerKey);
		
		Map<Object, Object> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(this.oAuthURL))
				.timeout(Duration.ofMillis(timeout))
				.header("Authorization", authorization)
				.header("Accept", "application/json")
				.header("Content-type", "application/x-www-form-urlencoded")
				.POST(HttpHelper.ofFormData(params))
				.build();
		
		invokeAndParseToken(request);
	}

}
