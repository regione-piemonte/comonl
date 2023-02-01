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
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoriSilpPK;

public class PutLavoratoreResponse extends BasePostResponse<LavoratoriSilpPK, LavoratoreSilpEspanso> {

	/** The model. */
	private LavoratoreSilpEspanso lavoratoreSilpEspanso = new LavoratoreSilpEspanso();

	public LavoratoreSilpEspanso getLavoratoreSilpEspanso() {
		return lavoratoreSilpEspanso;
	}

	public void setLavoratoreSilpEspanso(LavoratoreSilpEspanso lavoratoreSilpEspanso) {
		this.lavoratoreSilpEspanso = lavoratoreSilpEspanso;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PutSedeResponse [sede=").append(lavoratoreSilpEspanso).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

	@Override
	protected LavoratoreSilpEspanso getEntity() {
		return lavoratoreSilpEspanso;
	}

	@Override
	protected String getBaseUri() {
		// TODO Auto-generated method stub
		return null;
	}

}
