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

/**
 * The Class GetRuoloRequest.
 */
public class GetRuoloRequest implements BaseRequest {

	private final String codiceFiscale;

	public GetRuoloRequest(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public GetRuoloRequest() {
		this.codiceFiscale = "";
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

}
