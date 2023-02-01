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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * The Class GetProvinciaRequest.
 */
public class GetProvinciaRequest implements BaseRequest {
	
	private String codRegioneMin;

	public GetProvinciaRequest(String codRegioneMin) {
		super();
		this.codRegioneMin = codRegioneMin;
	}
	
	
	
	public String getCodRegioneMin() {
		return codRegioneMin;
	}



	public void setCodRegioneMin(String codRegioneMin) {
		this.codRegioneMin = codRegioneMin;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetProvinciaRequest []");
		return builder.toString();
	}
}
