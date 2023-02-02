/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.request.common;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

public class PreCompilaAnagraficaByCfRequest implements BaseRequest {

	private final String cf;

	public PreCompilaAnagraficaByCfRequest(String cf) {
		this.cf = cf;
	}

	public String getCf() {
		return cf;
	}

	
}
