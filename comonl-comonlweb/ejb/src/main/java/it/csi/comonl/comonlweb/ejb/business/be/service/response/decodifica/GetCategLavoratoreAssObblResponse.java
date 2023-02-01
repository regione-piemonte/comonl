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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;

/**
 * The Class GetProvinciaResponse.
 */
public class GetCategLavoratoreAssObblResponse extends BaseGetResponse<List<CategLavAssObbl>> {

	private List<CategLavAssObbl> categLavAssObbls= new ArrayList<>();
	

	public List<CategLavAssObbl> getCategLavAssObbls() {
		return categLavAssObbls;
	}

	public void setCategLavAssObbls(List<CategLavAssObbl> categLavAssObbls) {
		this.categLavAssObbls = categLavAssObbls;
	}

	@Override
	protected List<CategLavAssObbl> getEntity() {
		return categLavAssObbls;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [categLavAssObbls=").append(categLavAssObbls).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
