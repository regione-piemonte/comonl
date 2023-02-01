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
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * The Class PostDecodificaGenericaResponse.
 */
public class PostDecodificaGenericaResponse extends BaseGetResponse<List<DecodificaGenerica>> {

	private List<DecodificaGenerica> decodificaGenericas = new ArrayList<>();

	public List<DecodificaGenerica> getDecodificaGenericas() {
		return decodificaGenericas;
	}

	public void setDecodificaGenericas(List<DecodificaGenerica> decodificaGenericas) {
		this.decodificaGenericas = decodificaGenericas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [decodificaGenericas=").append(decodificaGenericas)
				.append(", apiErrors=").append(getApiErrors()).append("]");
		return builder.toString();
	}

	@Override
	protected List<DecodificaGenerica> getEntity() {
		return decodificaGenericas;
	}

}
