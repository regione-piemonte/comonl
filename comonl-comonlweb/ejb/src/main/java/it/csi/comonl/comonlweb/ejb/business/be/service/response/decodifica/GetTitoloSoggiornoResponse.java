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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTitoloSoggiornoResponse extends BaseGetResponse<List<StatusStraniero>> {

	private List<StatusStraniero> titoloSoggiornos= new ArrayList<>();


	public List<StatusStraniero> getTitoloSoggiornos() {
		return titoloSoggiornos;
	}

	public void setTitoloSoggiornos(List<StatusStraniero> titoloSoggiornos) {
		this.titoloSoggiornos = titoloSoggiornos;
	}

	@Override
	protected List<StatusStraniero> getEntity() {
		return titoloSoggiornos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [titoloSoggiornos=").append(titoloSoggiornos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
