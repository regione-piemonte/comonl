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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaSpec;


/**
 * Response for reading DelegatoImpresaSpec by its cfImpresa.
 */
public class GetDelegatoImpresaSpecResponse extends BaseGetResponse<List<DelegatoImpresaSpec>> {

	private List<DelegatoImpresaSpec> delegatoImpresaSpecs;

	

	@Override
	protected List<DelegatoImpresaSpec> getEntity() {
		return delegatoImpresaSpecs;
	}



	public List<DelegatoImpresaSpec> getDelegatoImpresaSpecs() {
		return delegatoImpresaSpecs;
	}



	public void setDelegatoImpresaSpecs(List<DelegatoImpresaSpec> delegatoImpresaSpecs) {
		this.delegatoImpresaSpecs = delegatoImpresaSpecs;
	}
	
}
