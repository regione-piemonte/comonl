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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;

/**
 * The Class GetTrasformazionerlByTipoResponse.
 */
public class GetCessazionerlResponse extends BaseGetResponse<List<Cessazionerl>> {

	private List<Cessazionerl> cessazionerls = new ArrayList<>();


	public List<Cessazionerl> getCessazionerls() {
		return cessazionerls;
	}

	public void setCessazionerls(List<Cessazionerl> cessazionerls) {
		this.cessazionerls = cessazionerls;
	}

	@Override
	protected List<Cessazionerl> getEntity() {
		return cessazionerls;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [trasformazionerls=").append(cessazionerls).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
