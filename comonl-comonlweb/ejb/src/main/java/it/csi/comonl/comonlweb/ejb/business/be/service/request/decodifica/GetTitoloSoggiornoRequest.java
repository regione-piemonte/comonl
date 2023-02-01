/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * The Class GetProvinciaRequest.
 */
public class GetTitoloSoggiornoRequest implements BaseRequest {
	

	public GetTitoloSoggiornoRequest() {
		super();
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetTitoloSoggiornoRequest []");
		return builder.toString();
	}
}
