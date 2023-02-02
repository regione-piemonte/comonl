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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;

/**
 * The Class GetTrasformazionerlByTipoResponse.
 */
public class GetGradoContrattualeResponse extends BaseGetResponse<List<GradoContrattuale>> {

	private List<GradoContrattuale> gradoContrattuales = new ArrayList<>();



	public List<GradoContrattuale> getGradoContrattuales() {
		return gradoContrattuales;
	}

	public void setGradoContrattuales(List<GradoContrattuale> gradoContrattuales) {
		this.gradoContrattuales = gradoContrattuales;
	}

	@Override
	protected List<GradoContrattuale> getEntity() {
		return gradoContrattuales;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [trasformazionerls=").append(gradoContrattuales).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
