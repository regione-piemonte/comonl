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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;

/**
 * The Class GetProvinciaResponse.
 */
public class GetCpiResponse extends BaseGetResponse<List<Cpi>> {

	private List<Cpi> cpis= new ArrayList<>();


	public List<Cpi> getCpis() {
		return cpis;
	}

	public void setCpis(List<Cpi> cpis) {
		this.cpis = cpis;
	}

	@Override
	protected List<Cpi> getEntity() {
		return cpis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [cpis=").append(cpis).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
