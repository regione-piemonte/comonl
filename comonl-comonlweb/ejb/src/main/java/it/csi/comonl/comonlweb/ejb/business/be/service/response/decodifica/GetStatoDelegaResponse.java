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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;

/**
 * The Class GetProvinciaResponse.
 */
public class GetStatoDelegaResponse extends BaseGetResponse<List<StatoDelega>> {

	private List<StatoDelega> statoDelegas = new ArrayList<>();

	
	public List<StatoDelega> getStatoDelegas() {
		return statoDelegas;
	}

	public void setStatoDelegas(List<StatoDelega> statoDelegas) {
		this.statoDelegas = statoDelegas;
	}

	@Override
	protected List<StatoDelega> getEntity() {
		return statoDelegas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [statoDelegas=").append(statoDelegas).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
