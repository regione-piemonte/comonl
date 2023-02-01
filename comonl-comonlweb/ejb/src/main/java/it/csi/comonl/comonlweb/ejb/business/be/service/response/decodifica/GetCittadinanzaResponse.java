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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;

/**
 * The Class GetProvinciaResponse.
 */
public class GetCittadinanzaResponse extends BaseGetResponse<List<Cittadinanza>> {

	private List<Cittadinanza> cittadinanzas= new ArrayList<>();

	
	public List<Cittadinanza> getCittadinanzas() {
		return cittadinanzas;
	}

	public void setCittadinanzas(List<Cittadinanza> cittadinanzas) {
		this.cittadinanzas = cittadinanzas;
	}

	@Override
	protected List<Cittadinanza> getEntity() {
		return cittadinanzas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [cittadinanzas=").append(cittadinanzas).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
