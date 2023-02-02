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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;

/**
 * The Class GetProvinciaResponse.
 */
public class GetEntePrevidenzialeResponse extends BaseGetResponse<List<EntePrevidenziale>> {

	private List<EntePrevidenziale> entePrevidenziales= new ArrayList<>();

	
	public List<EntePrevidenziale> getEntePrevidenziales() {
		return entePrevidenziales;
	}

	public void setEntePrevidenziales(List<EntePrevidenziale> entePrevidenziales) {
		this.entePrevidenziales = entePrevidenziales;
	}

	@Override
	protected List<EntePrevidenziale> getEntity() {
		return entePrevidenziales;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [entePrevidenziales=").append(entePrevidenziales).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
