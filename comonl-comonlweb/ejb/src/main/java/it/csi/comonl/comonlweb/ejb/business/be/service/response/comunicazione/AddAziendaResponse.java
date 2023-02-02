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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;

/**
 * The Class PostAnagraficaDelegatoResponse.
 */
public class AddAziendaResponse extends BaseGetResponse<DelegatoImpresa> {

	private DelegatoImpresa delegatoImpresa = new DelegatoImpresa();
	
	

	@Override
	protected DelegatoImpresa getEntity() {
		return delegatoImpresa;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [AddAziendaResponse=").append(delegatoImpresa).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public DelegatoImpresa getDelegatoImpresa() {
		return delegatoImpresa;
	}


	public void setDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		this.delegatoImpresa = delegatoImpresa;
	}


}
