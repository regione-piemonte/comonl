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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.silp;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;

public class PutAziendaResponse extends BasePostResponse<Integer, DatiAzienda> {

	/** The model. */
	private DatiAzienda datiAzienda = new DatiAzienda();

	public DatiAzienda getDatiAzienda() {
		return datiAzienda;
	}

	public void setDatiAzienda(DatiAzienda sede) {
		this.datiAzienda = sede;
	}

	@Override
	protected String getBaseUri() {
		return "intervento";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PutSedeResponse [sede=").append(datiAzienda).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

	@Override
	protected DatiAzienda getEntity() {
		return datiAzienda;
	}

}
