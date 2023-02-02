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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;

/**
 * The Class PostAnagraficaDelegatoResponse.
 */
public class SetDataAnnullamentoDelegatoImpresaResponse extends BaseGetResponse<Date> {

	private Date dataAnnullamento;
	
	

	@Override
	protected Date getEntity() {
		return dataAnnullamento;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [SetDataAnnullamentoDelegatoImpresaResponse=").append(dataAnnullamento).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public Date getDataCancellazione() {
		return dataAnnullamento;
	}


	public void setDataCancellazione(Date dataCancellazione) {
		this.dataAnnullamento = dataCancellazione;
	}
	

}
