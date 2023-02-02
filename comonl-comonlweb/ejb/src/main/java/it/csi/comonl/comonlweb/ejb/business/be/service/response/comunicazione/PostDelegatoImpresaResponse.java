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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaPK;


/**
 * Response for reading DelegatoImpresaSpec by its cfImpresa.
 */
public class PostDelegatoImpresaResponse extends BasePostResponse<DelegatoImpresaPK, DelegatoImpresa> {

	private DelegatoImpresa delegatoImpresa;
	

	public DelegatoImpresa getDelegatoImpresa() {
		return delegatoImpresa;
	}

	public void setDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		this.delegatoImpresa = delegatoImpresa;
	}

	@Override
	protected DelegatoImpresa getEntity() {
		return delegatoImpresa;
	}

	@Override
	protected String getBaseUri() {
		return "intervento";
	}
}
