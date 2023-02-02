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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;

/**
 * The Class GetProvinciaResponse.
 */
public class GetLivelloStudioResponse extends BaseGetResponse<List<LivelloStudio>> {

	private List<LivelloStudio> livelloStudios= new ArrayList<>();
	
	public List<LivelloStudio> getLivelloStudios() {
		return livelloStudios;
	}

	public void setLivelloStudios(List<LivelloStudio> livelloStudios) {
		this.livelloStudios = livelloStudios;
	}

	@Override
	protected List<LivelloStudio> getEntity() {
		return livelloStudios;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [livelloStudios=").append(livelloStudios).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}
}
