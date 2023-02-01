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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;

public class PutSedeResponse extends BasePostResponse<Integer, Sede> {

	/** The model. */
	private Sede sede = new Sede();

	public Sede getSedeLegale() {
		return sede;
	}

	public void setSedeLegale(Sede sede) {
		this.sede = sede;
	}

	@Override
	protected String getBaseUri() {
		return "intervento";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PutSedeResponse [sede=").append(sede).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

	@Override
	protected Sede getEntity() {
		return sede;
	}

}
