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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

public class GetDelegatoImpresaSpecRequest implements BaseRequest{
	
	private final String cfImpresa;
	
	public GetDelegatoImpresaSpecRequest(String cfImpresa) {
		super();
		this.cfImpresa = cfImpresa;
	}

	public String getCfImpresa() {
		return cfImpresa;
	}
}
