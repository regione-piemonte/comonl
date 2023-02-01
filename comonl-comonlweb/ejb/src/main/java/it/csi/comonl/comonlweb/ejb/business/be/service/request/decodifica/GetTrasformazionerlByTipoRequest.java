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
 * The Class GetTrasformazionerlByTipoRequest.
 */
public class GetTrasformazionerlByTipoRequest implements BaseRequest {
	
	private String tipo;
	
	public GetTrasformazionerlByTipoRequest(String tipo) {
		super();
		this.tipo = tipo;
	}
	
	
	
	public String getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetStatoDelegaRequest []");
		return builder.toString();
	}
}
