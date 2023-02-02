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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;

/**
 * The Class GetProvinciaResponse.
 */
public class GetQuesturaResponse extends BaseGetResponse<List<Questura>> {

	private List<Questura> questuras= new ArrayList<>();

	public List<Questura> getQuesturas() {
		return questuras;
	}

	public void setQuesturas(List<Questura> questuras) {
		this.questuras = questuras;
	}

	@Override
	protected List<Questura> getEntity() {
		return questuras;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [questuras=").append(questuras).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
