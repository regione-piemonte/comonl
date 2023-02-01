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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;

/**
 * The Class GetProvinciaResponse.
 */
public class GetStatoComunicazioneResponse extends BaseGetResponse<List<StatoComunicazione>> {

	private List<StatoComunicazione> statoComunicaziones = new ArrayList<>();


	public List<StatoComunicazione> getStatoComunicaziones() {
		return statoComunicaziones;
	}

	public void setStatoComunicaziones(List<StatoComunicazione> statoComunicaziones) {
		this.statoComunicaziones = statoComunicaziones;
	}

	@Override
	protected List<StatoComunicazione> getEntity() {
		return statoComunicaziones;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [statoComunicaziones=").append(statoComunicaziones).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
