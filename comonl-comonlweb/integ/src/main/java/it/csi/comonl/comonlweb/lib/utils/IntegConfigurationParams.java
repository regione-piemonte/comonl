/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.utils;

import it.csi.comonl.comonlweb.lib.external.itf.ConfigurationParam;

/**
 * configuration params
 */
public enum IntegConfigurationParams implements ConfigurationParam {

	/** Whether to use OAuth2 */
	USE_OAUTH2("USE_OAUTH2"),

	/** OAuth2 endpoint URL */
	OAUTH2_URL("OAUTH2_URL"),

	/** OAuth2 consumer key */
	CONSUMER_KEY("CONSUMER_KEY"),

	/** OAuth2 consumer secret */
	CONSUMER_SECRET("CONSUMER_SECRET"),

	/** Identifier for the ente */
	CODICE_ENTE("CODICE_ENTE"),

	/** Identifier for the application */
	CODICE_APPLICATIVO("CODICE_APPLICATIVO"),

	/** WSDL location */
	WSDL_LOCATION("WSDL_LOCATION");

	private final String paramName;

	private IntegConfigurationParams(String paramName) {
		this.paramName = paramName;
	}

	@Override
	public String getParamName() {
		return paramName;
	}
}
