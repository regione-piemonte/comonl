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

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.JsonbException;
import javax.ws.rs.core.Response.Status;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.serialization.JsonUtility;

/**
 * Thread-safe implementation of a OAuth2 Helper class
 * @author Marchino Alessandro
 *
 */
public abstract class OAuth2Helper {
	
	/** The logger */
	protected final LogUtil log = new LogUtil(getClass());

	/** The URL to invoke */
	final String oAuthURL;
	/** The consumer key */
	final String consumerKey;
	/** The consumer secret */
	final String consumerSecret;
	/** The connection timeout */
	final long timeout;
	/** The threshold after which the token is to be considered stale */
	private final long staleThreshold;
	/** The HTTP client to use */
	private final HttpClient httpClient;

	/** The token */
	private volatile String token;
	/** The time when the token was got */
	volatile long timeWhenGet;
	/** The token expiration */
	private volatile Long expires;
	/** The counter for token retrieval */
	private volatile long counter;

	/**
	 * Package-protected constructor, to be used only by subclasses
	 * @param oAuthURL the URL to be used
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param timeout the connection timeout
	 * @param staleThreshold the threshold for stale keys
	 */
	OAuth2Helper(String oAuthURL, String consumerKey, String consumerSecret, long timeout, long staleThreshold) {
		this.oAuthURL = oAuthURL;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.timeout = timeout;
		this.staleThreshold = staleThreshold;
		
		this.counter = 0;
		this.httpClient = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofMillis(timeout))
				.build();
	}
	
	/**
	 * Retrieves the token
	 * @return the token
	 */
	public String getToken() {
		checkStaleness();
		if(token != null) {
			this.counter++;
			return this.token;
		}
		synchronized(this) {
			if(token == null) {
				initToken();
			}
		}
		return this.token;
	}

	/**
	 * Initializes a new token
	 * @return the token
	 */
	public String newToken() {
		synchronized(this) {
			this.token = null;
		}
		return this.getToken();
	}
	
	/**
	 * @return the time when get
	 */
	public long getTimeWhenGet() {
		return timeWhenGet;
	}
	/**
	 * @return the counter
	 */
	public long getCounter() {
		return counter;
	}
	/**
	 * @return the expires
	 */
	public Long getExpires() {
		return expires;
	}
	/**
	 * Initialization of a token
	 */
	abstract void initToken();
	
	/**
	 * Checks for token staleness
	 */
	private void checkStaleness() {
		if(this.token == null || this.expires == null) {
			return;
		}
		long now = System.currentTimeMillis();
		Duration timeSinceTokenCreation = Duration.between(Instant.ofEpochMilli(this.timeWhenGet), Instant.ofEpochMilli(now));
		long tokenLifetimeSeconds = timeSinceTokenCreation.getSeconds();
		if(this.expires.longValue() - tokenLifetimeSeconds < this.staleThreshold) {
			// Stale token: refresh
			this.token = null;
			this.expires = null;
		}
	}
	
	/**
	 * Invokes the HTTP request and parses the received token
	 * @param request the http request
	 */
	protected void invokeAndParseToken(HttpRequest request) {
		final String methodName = "invokeAndParseToken";
		HttpResponse<String> httpResponse;
		try {
			this.timeWhenGet = System.currentTimeMillis();
			this.token = null;
			this.expires = null;
			httpResponse = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
		} catch (IOException | InterruptedException e) {
			log.error(methodName, "Fatal transport error: " + e.getMessage());
			return;
		}
		if(httpResponse.statusCode() != Status.OK.getStatusCode()) {
			log.error(methodName, "Wrong HTTP status code: " + httpResponse.statusCode());
			return;
		}
		log.trace(methodName, () -> "Response: " + httpResponse.body());
		OAuth2Response response;
		try {
			response = JsonUtility.deserialize(httpResponse.body(), OAuth2Response.class);
		} catch(JsonbException e) {
			log.error(methodName, "Fatal error in JSON deserialization: " + e.getMessage());
			return;
		}
		if(response.getError() != null) {
			log.error(methodName, "Unexpected reply: " + httpResponse.body());
			return;
		}
		this.token = response.getAccessToken();
		this.expires = response.getExpiresIn();
		if(this.token == null || this.expires == null) {
			log.error(methodName, "Unexpected reply: " + httpResponse.body());
			return;
		}
		log.debug(methodName, "Token retrieved");
	}
	
	/**
	 * Implementation of Builder pattern to allow multiple configurations
	 * @author Marchino Alessandro
	 */
	public static class Builder {
		private static final Map<String, OAuth2Helper> INSTANCES = new HashMap<>();
		private static final String SEPARATOR = "|__|";

		private String oAuthURL;
		private String consumerKey;
		private String consumerSecret;
		private OAuth2Helper.Type type;
		private boolean reuseInstance = false;
		private long timeout = 30000;
		// In seconds
		private long staleThreshold = 300;
		
		/**
		 * @param url the url
		 * @return the builder instance
		 */
		public Builder oAuthURL(String url) {
			this.oAuthURL = url;
			return this;
		}
		/**
		 * @param key the key
		 * @return the builder instance
		 */
		public Builder consumerKey(String key) {
			this.consumerKey = key;
			return this;
		}
		/**
		 * @param secret the secret
		 * @return the builder instance
		 */
		public Builder consumerSecret(String secret) {
			this.consumerSecret = secret;
			return this;
		}
		/**
		 * @param doReuse whether to reuse a possibly existing instance
		 * @return the builder instance
		 */
		public Builder reuseInstance(boolean doReuse) {
			this.reuseInstance = doReuse;
			return this;
		}
		/**
		 * @param connectTimeout the connect timeout
		 * @return the builder instance
		 */
		public Builder timeout(long connectTimeout) {
			this.timeout = connectTimeout;
			return this;
		}
		/**
		 * @param threshold the threshold
		 * @return the builder instance
		 */
		public Builder staleThreshold(long threshold) {
			this.staleThreshold = threshold;
			return this;
		}
		/**
		 * @param helperType the type
		 * @return the builder instance
		 */
		public Builder type(OAuth2Helper.Type helperType) {
			this.type = helperType;
			return this;
		}
		
		/**
		 * Build the instance
		 * @return the built instance
		 */
		public OAuth2Helper build() {
			if(this.oAuthURL == null || this.consumerKey == null || this.consumerSecret == null || this.type == null) {
				throw new IllegalStateException("Type, URL, Key and Secret MUST be set");
			}
			if(!reuseInstance) {
				// Simply create a new instance, ignoring whatever may be cached
				return type.initOAuth2Helper(oAuthURL, consumerKey, consumerSecret, timeout, staleThreshold);
			}
			
			String instanceKey = new StringBuilder()
					.append(oAuthURL)
					.append(SEPARATOR)
					.append(consumerKey)
					.toString();
			OAuth2Helper res = null;
			synchronized(SEPARATOR) {
				res = INSTANCES.compute(instanceKey, (key, oldValue) -> oldValue != null ? oldValue : type.initOAuth2Helper(oAuthURL, consumerKey, consumerSecret, timeout, staleThreshold));
			}
			return res;
		}
	}
	
	/**
	 * The type of the OAuth2 helper to be used
	 */
	public static enum Type {
		/** OAuth2 helper via the use of the grant in the body */
		BODY_GRANT {
			@Override
			OAuth2Helper initOAuth2Helper(String oAuthURL, String consumerKey, String consumerSecret, long timeout, long staleThreshold) {
				return new OAuth2HelperBodyGrant(oAuthURL, consumerKey, consumerSecret, timeout, staleThreshold);
			}
		},
		/** OAuth2 helper via the use of the grant in the header */
		HEADER_GRANT {
			@Override
			OAuth2Helper initOAuth2Helper(String oAuthURL, String consumerKey, String consumerSecret, long timeout, long staleThreshold) {
				return new OAuth2HelperHeaderGrant(oAuthURL, consumerKey, consumerSecret, timeout, staleThreshold);
			}
		};
		
		/**
		 * Initialization of the OAuth2Helper
		 * @param oAuthURL the URL to be used
		 * @param consumerKey the consumer key
		 * @param consumerSecret the consumer secret
		 * @param timeout the connection timeout
		 * @param staleThreshold the threshold for stale keys
		 * @return the helper
		 */
		abstract OAuth2Helper initOAuth2Helper(String oAuthURL, String consumerKey, String consumerSecret, long timeout, long staleThreshold);
	}
	
}
