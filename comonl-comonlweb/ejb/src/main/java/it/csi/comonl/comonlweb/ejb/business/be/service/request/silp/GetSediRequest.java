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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.silp;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

public class GetSediRequest implements BaseRequest {

	private String idAzienda;

	public GetSediRequest(String idAzienda) {
		super();
		this.idAzienda = idAzienda;
	}

	/**
	 * @return the idAzienda
	 */
	public String getIdAzienda() {
		return idAzienda;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetSediRequest []");
		return builder.toString();
	}

}
