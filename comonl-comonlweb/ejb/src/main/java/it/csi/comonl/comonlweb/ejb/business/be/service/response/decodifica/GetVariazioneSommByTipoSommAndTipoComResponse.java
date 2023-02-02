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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;

/**
 * The Class GetProvinciaResponse.
 */
public class GetVariazioneSommByTipoSommAndTipoComResponse extends BaseGetResponse<VariazioneSomm> {

	private VariazioneSomm variazioneSomm;


	@Override
	protected VariazioneSomm getEntity() {
		return variazioneSomm;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [variazioneSomm=").append(variazioneSomm).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}


	public VariazioneSomm getVariazioneSomm() {
		return variazioneSomm;
	}


	public void setVariazioneSomm(VariazioneSomm variazioneSomm) {
		this.variazioneSomm = variazioneSomm;
	}


	

}
