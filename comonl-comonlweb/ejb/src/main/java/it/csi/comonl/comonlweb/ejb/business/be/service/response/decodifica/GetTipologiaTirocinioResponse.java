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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipologiaTirocinioResponse extends BaseGetResponse<List<TipologiaTirocinio>> {

	private List<TipologiaTirocinio> tipologiaTirocinios= new ArrayList<>();

	
	public List<TipologiaTirocinio> getTipologiaTirocinios() {
		return tipologiaTirocinios;
	}

	public void setTipologiaTirocinios(List<TipologiaTirocinio> tipologiaTirocinios) {
		this.tipologiaTirocinios = tipologiaTirocinios;
	}

	@Override
	protected List<TipologiaTirocinio> getEntity() {
		return tipologiaTirocinios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipologiaTirocinios=").append(tipologiaTirocinios).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
