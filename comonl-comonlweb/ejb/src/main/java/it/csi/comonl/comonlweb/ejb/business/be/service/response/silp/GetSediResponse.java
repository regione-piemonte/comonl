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

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;

public class GetSediResponse extends BaseGetResponse<List<Sede>> {

	/** The model. */
	private List<Sede> leSedi = new ArrayList<Sede>();

	@Override
	protected List<Sede> getEntity() {
		return leSedi;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}

	public List<Sede> getLeSedi() {
		return leSedi;
	}

	public void setLeSedi(List<Sede> leSedi) {
		this.leSedi = leSedi;
	}

}
