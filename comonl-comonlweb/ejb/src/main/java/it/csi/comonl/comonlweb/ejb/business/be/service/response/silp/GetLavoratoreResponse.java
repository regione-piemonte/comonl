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
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;

/**
 * The Class GetAziendaResponse.
 */
public class GetLavoratoreResponse extends BaseGetResponse<LavoratoreSilpEspanso> {

	/** The model. */
	private LavoratoreSilpEspanso lavoratoreSilpEspanso = new LavoratoreSilpEspanso();

	@Override
	protected LavoratoreSilpEspanso getEntity() {
		return lavoratoreSilpEspanso;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetAziendaResponse [lavoratoriSilp=").append(lavoratoreSilpEspanso).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

	public LavoratoreSilpEspanso getLavoratoreSilpEspanso() {
		return lavoratoreSilpEspanso;
	}

	public void setLavoratoreSilpEspanso(LavoratoreSilpEspanso lavoratoreSilpEspanso) {
		this.lavoratoreSilpEspanso = lavoratoreSilpEspanso;
	}

}
