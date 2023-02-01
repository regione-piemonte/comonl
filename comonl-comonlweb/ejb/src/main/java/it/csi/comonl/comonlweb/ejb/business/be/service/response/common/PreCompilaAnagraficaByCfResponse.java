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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.common;


import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.common.AnagraficaGenerica;


/**
 * The Class GetRuoloResponse.
 */
public class PreCompilaAnagraficaByCfResponse extends BaseGetResponse<AnagraficaGenerica> {

	/** The model. */
	private AnagraficaGenerica anagrafica = new AnagraficaGenerica();

	

	@Override
	protected AnagraficaGenerica getEntity() {
		return anagrafica;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PreCompilaAnagraficaByCfResponse [anagrafica=").append(anagrafica).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

	public AnagraficaGenerica getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaGenerica anagrafica) {
		this.anagrafica = anagrafica;
	}
	
	

}
