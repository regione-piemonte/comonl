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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;

/**
 * The Class GetProvinciaResponse.
 */
public class GetCategTirocinanteResponse extends BaseGetResponse<List<CategTirocinante>> {

	private List<CategTirocinante> categTirocinantes= new ArrayList<>();

	

	public List<CategTirocinante> getCategTirocinantes() {
		return categTirocinantes;
	}

	public void setCategTirocinantes(List<CategTirocinante> categTirocinantes) {
		this.categTirocinantes = categTirocinantes;
	}

	@Override
	protected List<CategTirocinante> getEntity() {
		return categTirocinantes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [categTirocinantes=").append(categTirocinantes).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
