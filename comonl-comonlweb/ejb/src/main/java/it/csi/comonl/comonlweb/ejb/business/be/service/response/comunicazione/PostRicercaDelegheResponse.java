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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostRicercaDelegheResponse extends BaseGetResponse<PagedList<Delega>> {

	private  PagedList<Delega> risultatiRicercaDeleghe = new PagedListImpl<>();
	


	public PagedList<Delega> getRisultatiRicercaDeleghe() {
		return risultatiRicercaDeleghe;
	}


	public void setRisultatiRicercaDeleghe(PagedList<Delega> risultatiRicercaDeleghe) {
		this.risultatiRicercaDeleghe = risultatiRicercaDeleghe;
	}


	@Override
	protected PagedList<Delega> getEntity() {
		return risultatiRicercaDeleghe;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [risultatiRicercaDeleghe=").append(risultatiRicercaDeleghe).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

}
