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
public class GetTipoComunicazioneTuRequest implements BaseRequest {
	
	private Boolean flgRicerca;
	public GetTipoComunicazioneTuRequest(Boolean flgRicerca) {
		super();
		this.flgRicerca = flgRicerca;
	}
	
	

	public Boolean getFlgRicerca() {
		return flgRicerca;
	}



	public void setFlgRicerca(Boolean flgRicerca) {
		this.flgRicerca = flgRicerca;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetTipoComunicazioneTuRequest []");
		return builder.toString();
	}
}
