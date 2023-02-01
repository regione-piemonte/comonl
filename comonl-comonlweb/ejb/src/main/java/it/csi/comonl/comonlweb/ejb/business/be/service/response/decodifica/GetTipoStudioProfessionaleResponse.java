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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;


/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoStudioProfessionaleResponse extends BaseGetResponse<List<TipoSoggettoAbilitato>> {

	private List<TipoSoggettoAbilitato> tipoSoggettoAbilitatos= new ArrayList<>();


	@Override
	protected List<TipoSoggettoAbilitato> getEntity() {
		return tipoSoggettoAbilitatos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoSoggettoAbilitatos=").append(tipoSoggettoAbilitatos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

	public List<TipoSoggettoAbilitato> getTipoSoggettoAbilitatos() {
		return tipoSoggettoAbilitatos;
	}

	public void setTipoSoggettoAbilitatos(List<TipoSoggettoAbilitato> tipoSoggettoAbilitatos) {
		this.tipoSoggettoAbilitatos = tipoSoggettoAbilitatos;
	}

	
	
	

}
