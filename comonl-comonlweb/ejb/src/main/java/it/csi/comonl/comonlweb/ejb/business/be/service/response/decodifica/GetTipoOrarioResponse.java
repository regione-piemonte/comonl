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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoOrarioResponse extends BaseGetResponse<List<TipoOrario>> {

	private List<TipoOrario> tipoOrarios = new ArrayList<>();


	public List<TipoOrario> getTipoOrarios() {
		return tipoOrarios;
	}

	public void setTipoOrarios(List<TipoOrario> tipoOrarios) {
		this.tipoOrarios = tipoOrarios;
	}

	@Override
	protected List<TipoOrario> getEntity() {
		return tipoOrarios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoOrarios=").append(tipoOrarios).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
