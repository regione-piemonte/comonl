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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;

/**
 * The Class GetProvinciaResponse.
 */
public class GetTipoComunicazioneTuResponse extends BaseGetResponse<List<TipoComunicazioneTu>> {

	private List<TipoComunicazioneTu> tipoComunicazioneTus = new ArrayList<>();

	

	public List<TipoComunicazioneTu> getTipoComunicazioneTus() {
		return tipoComunicazioneTus;
	}

	public void setTipoComunicazioneTus(List<TipoComunicazioneTu> tipoComunicazioneTus) {
		this.tipoComunicazioneTus = tipoComunicazioneTus;
	}

	@Override
	protected List<TipoComunicazioneTu> getEntity() {
		return tipoComunicazioneTus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [tipoComunicazioneTus=").append(tipoComunicazioneTus).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
