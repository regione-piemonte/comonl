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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostRicercaAccreditamentoConsulenteResponse extends BaseGetResponse<PagedList<AnagraficaDelegato>> {

	private  PagedList<AnagraficaDelegato> risultatiRicercaAccreditamentoConsulente = new PagedListImpl<>();
	
	

	@Override
	protected PagedList<AnagraficaDelegato> getEntity() {
		return risultatiRicercaAccreditamentoConsulente;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [risultatiRicercaAccreditamentoConsulente=").append(risultatiRicercaAccreditamentoConsulente).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public PagedList<AnagraficaDelegato> getRisultatiRicercaAccreditamentoConsulente() {
		return risultatiRicercaAccreditamentoConsulente;
	}


	public void setRisultatiRicercaAccreditamentoConsulente(
			PagedList<AnagraficaDelegato> risultatiRicercaAccreditamentoConsulente) {
		this.risultatiRicercaAccreditamentoConsulente = risultatiRicercaAccreditamentoConsulente;
	}

}
