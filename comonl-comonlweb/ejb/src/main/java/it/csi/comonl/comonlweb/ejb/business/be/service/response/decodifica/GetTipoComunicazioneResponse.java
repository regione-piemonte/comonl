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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoComunicazioneResponse extends BaseGetResponse<List<TipoComunicazione>> {

	private List<TipoComunicazione> tipoComunicaziones = new ArrayList<>();

	

	public List<TipoComunicazione> getTipoComunicaziones() {
		return tipoComunicaziones;
	}

	public void setTipoComunicaziones(List<TipoComunicazione> tipoComunicaziones) {
		this.tipoComunicaziones = tipoComunicaziones;
	}

	@Override
	protected List<TipoComunicazione> getEntity() {
		return tipoComunicaziones;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoComunicaziones=").append(tipoComunicaziones).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
