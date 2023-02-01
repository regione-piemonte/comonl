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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.silp;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;

public class PutLavoratoreSilpDaCoResponse extends BaseGetResponse<String> {

	/** The model. */
	private String cfLavoratore = new String();

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PutLavoratoreSilpDaCoResponse [cfLavoratore=").append(cfLavoratore).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

	@Override
	protected String getEntity() {
		return cfLavoratore;
	}

	public String getCfLavoratore() {
		return cfLavoratore;
	}

	public void setCfLavoratore(String cfLavoratore) {
		this.cfLavoratore = cfLavoratore;
	}

	
}
