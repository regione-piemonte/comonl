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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoAttoL68Response extends BaseGetResponse<List<TipoAttoL68>> {

	private List<TipoAttoL68> tipoAttos = new ArrayList<>();

	public List<TipoAttoL68> getTipoAttos() {
		return tipoAttos;
	}

	public void setTipoAttos(List<TipoAttoL68> tipoAttos) {
		this.tipoAttos = tipoAttos;
	}

	@Override
	protected List<TipoAttoL68> getEntity() {
		return tipoAttos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoAttos=").append(tipoAttos).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

}
