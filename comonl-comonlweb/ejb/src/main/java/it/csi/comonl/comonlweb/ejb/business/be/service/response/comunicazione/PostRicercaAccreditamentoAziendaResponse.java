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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostRicercaAccreditamentoAziendaResponse extends BaseGetResponse<PagedList<AnagraficaAzienda>> {

	private  PagedList<AnagraficaAzienda> risultatiRicercaAccreditamentoAziendaResponse = new PagedListImpl<>();
	
	

	@Override
	protected PagedList<AnagraficaAzienda> getEntity() {
		return risultatiRicercaAccreditamentoAziendaResponse;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [risultatiRicercaAccreditamentoAziendaResponse=").append(risultatiRicercaAccreditamentoAziendaResponse).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public PagedList<AnagraficaAzienda> getRisultatiRicercaAccreditamentoAziendaResponse() {
		return risultatiRicercaAccreditamentoAziendaResponse;
	}


	public void setRisultatiRicercaAccreditamentoAziendaResponse(
			PagedList<AnagraficaAzienda> risultatiRicercaAccreditamentoAziendaResponse) {
		this.risultatiRicercaAccreditamentoAziendaResponse = risultatiRicercaAccreditamentoAziendaResponse;
	}

}
