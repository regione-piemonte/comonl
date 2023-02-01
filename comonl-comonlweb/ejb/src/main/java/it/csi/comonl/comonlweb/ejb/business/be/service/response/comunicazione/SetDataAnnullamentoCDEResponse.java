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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;

/**
 * The Class PostAnagraficaDelegatoResponse.
 */
public class SetDataAnnullamentoCDEResponse extends BaseGetResponse<Date> {

	private Date dataCancellazione;
	
	

	@Override
	protected Date getEntity() {
		return dataCancellazione;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [setDataAnnullamentoCDEResponse=").append(dataCancellazione).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public Date getDataCancellazione() {
		return dataCancellazione;
	}


	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	

}
