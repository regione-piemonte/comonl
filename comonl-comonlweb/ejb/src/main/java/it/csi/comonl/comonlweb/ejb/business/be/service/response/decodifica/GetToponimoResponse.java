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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;

/**
 * The Class GetProvinciaResponse.
 */
public class GetToponimoResponse extends BaseGetResponse<List<Toponimo>> {

	private List<Toponimo> toponimos= new ArrayList<>();


	@Override
	protected List<Toponimo> getEntity() {
		return toponimos;
	}
	
	public List<Toponimo> getToponimos() {
		return toponimos;
	}

	public void setToponimos(List<Toponimo> toponimos) {
		this.toponimos = toponimos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [toponimos=").append(toponimos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
