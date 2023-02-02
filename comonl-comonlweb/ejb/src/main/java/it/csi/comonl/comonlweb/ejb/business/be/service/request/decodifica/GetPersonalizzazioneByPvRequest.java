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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * The Class GetProvinciaRequest.
 */
public class GetPersonalizzazioneByPvRequest implements BaseRequest {
	
	private String pv;
	public GetPersonalizzazioneByPvRequest(String pv) {
		super();
		this.pv = pv;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetPersonalizzazioneByPvRequest []");
		return builder.toString();
	}





	public String getPv() {
		return pv;
	}





	public void setPv(String pv) {
		this.pv = pv;
	}
}
