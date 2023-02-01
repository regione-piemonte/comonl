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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;

/**
 * The Class GetTrasformazionerlByTipoResponse.
 */
public class GetTrasformazionerlByTipoResponse extends BaseGetResponse<List<Trasformazionerl>> {

	private List<Trasformazionerl> trasformazionerls = new ArrayList<>();



	public List<Trasformazionerl> getTrasformazionerls() {
		return trasformazionerls;
	}

	public void setTrasformazionerls(List<Trasformazionerl> trasformazionerls) {
		this.trasformazionerls = trasformazionerls;
	}

	@Override
	protected List<Trasformazionerl> getEntity() {
		return trasformazionerls;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [trasformazionerls=").append(trasformazionerls).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
