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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import java.util.ArrayList;
import java.util.List;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoEntePromTirocinioResponse extends BaseGetResponse<List<TipoEntePromTirocinio>> {

	private List<TipoEntePromTirocinio> tipoEntePromTirocinios= new ArrayList<>();


	public List<TipoEntePromTirocinio> getTipoEntePromTirocinios() {
		return tipoEntePromTirocinios;
	}

	public void setTipoEntePromTirocinios(List<TipoEntePromTirocinio> tipoEntePromTirocinios) {
		this.tipoEntePromTirocinios = tipoEntePromTirocinios;
	}

	@Override
	protected List<TipoEntePromTirocinio> getEntity() {
		return tipoEntePromTirocinios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoEntePromTirocinios=").append(tipoEntePromTirocinios).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
