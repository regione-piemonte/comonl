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
package it.csi.comonl.comonlweb.lib.external.itf;

import java.util.Map;

/**
 * Wrapper for resolved external service data
 * @param <H> the external service interface type
 */
public class ExternalServiceResolveWrapper<H> {

	private final H instance;
	private final Map<String, String> params;

	/**
	 * Wrapper constructor
	 * @param instance
	 * @param params
	 */
	public ExternalServiceResolveWrapper(H instance, Map<String, String> params) {
		this.instance = instance;
		this.params = params;
	}

	/**
	 * @return the instance
	 */
	public H getInstance() {
		return instance;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}
	
}
