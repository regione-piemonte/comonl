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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class GetProvinciaResponse.
 */
public class GetStatoEsteroResponse extends BaseGetResponse<List<StatiEsteri>> {

	private List<StatiEsteri> statoEsteros= new ArrayList<>();
	

	public List<StatiEsteri> getStatoEsteros() {
		return statoEsteros;
	}

	public void setStatoEsteros(List<StatiEsteri> statoEsteros) {
		this.statoEsteros = statoEsteros;
	}

	@Override
	protected List<StatiEsteri> getEntity() {
		return statoEsteros;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [statoEsteros=").append(statoEsteros).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
