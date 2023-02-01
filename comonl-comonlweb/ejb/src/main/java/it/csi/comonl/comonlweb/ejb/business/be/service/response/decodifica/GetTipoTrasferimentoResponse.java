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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;


/**
 * The Class GetTipoTrasferimentoResponse.
 */
public class GetTipoTrasferimentoResponse extends BaseGetResponse<List<TipoTrasferimento>> {

	private List<TipoTrasferimento> tipoTrasferimentos= new ArrayList<>();


	@Override
	protected List<TipoTrasferimento> getEntity() {
		return tipoTrasferimentos;
	}

	
	public List<TipoTrasferimento> getTipoTrasferimentos() {
		return tipoTrasferimentos;
	}


	public void setTipoTrasferimentos(List<TipoTrasferimento> tipoTrasferimentos) {
		this.tipoTrasferimentos = tipoTrasferimentos;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoTrasferimentos=").append(tipoTrasferimentos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
