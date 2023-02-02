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

import java.util.ArrayList;
import java.util.List;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoContrattoResponse extends BaseGetResponse<List<TipoContratti>> {

	private List<TipoContratti> tipoContratti= new ArrayList<>();

	
	
	public List<TipoContratti> getTipoContratti() {
		return tipoContratti;
	}

	public void setTipoContratti(List<TipoContratti> tipoContratti) {
		this.tipoContratti = tipoContratti;
	}

	@Override
	protected List<TipoContratti> getEntity() {
		return tipoContratti;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoContratti=").append(tipoContratti).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
