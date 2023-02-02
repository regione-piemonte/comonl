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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;


public class PostDelegaResponse extends BaseGetResponse<Delega> {

	private Delega delega;

	public Delega getDelega() {
		return delega;
	}


	public void setDelega(Delega delega) {
		this.delega = delega;
	}


	@Override
	protected Delega getEntity() {
		return delega;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [delega=").append(delega).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

}
