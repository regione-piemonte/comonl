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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;

/**
 * The Class GetAziendaResponse.
 */
public class GetAziendaResponse extends BaseGetResponse<DatiAzienda> {

	/** The model. */
	private DatiAzienda datiAzienda = new DatiAzienda();

	/**
	 * @return the datiAzienda
	 */
	public DatiAzienda getDatiAzienda() {
		return datiAzienda;
	}

	/**
	 * @param datiAzienda the datiAzienda to set
	 */
	public void setDatiAzienda(DatiAzienda datiAzienda) {
		this.datiAzienda = datiAzienda;
	}

	@Override
	protected DatiAzienda getEntity() {
		return datiAzienda;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetAziendaResponse [datiAzienda=").append(datiAzienda).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

}
