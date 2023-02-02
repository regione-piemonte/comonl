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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;


/**
 * Response for reading DelegatoImpresa by its AnagraficaDelegato.
 */
public class GetDelegatoImpresaByIdAnagraficaDelegatoResponse extends BaseGetResponse<List<DelegatoImpresa>> {

	private List<DelegatoImpresa> delegatoImpresas;

	

	@Override
	protected List<DelegatoImpresa> getEntity() {
		return delegatoImpresas;
	}



	public List<DelegatoImpresa> getDelegatoImpresas() {
		return delegatoImpresas;
	}



	public void setDelegatoImpresas(List<DelegatoImpresa> delegatoImpresas) {
		this.delegatoImpresas = delegatoImpresas;
	}

}
