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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;

/**
 * The Class GetProvinciaResponse.
 */
public class GetNaturaGiuridicaResponse extends BaseGetResponse<List<NaturaGiuridica>> {

	private List<NaturaGiuridica> naturaGiuridicas= new ArrayList<>();

	public List<NaturaGiuridica> getNaturaGiuridicas() {
		return naturaGiuridicas;
	}

	public void setNaturaGiuridicas(List<NaturaGiuridica> naturaGiuridicas) {
		this.naturaGiuridicas = naturaGiuridicas;
	}

	@Override
	protected List<NaturaGiuridica> getEntity() {
		return naturaGiuridicas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [naturaGiuridicas=").append(naturaGiuridicas).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}
}
