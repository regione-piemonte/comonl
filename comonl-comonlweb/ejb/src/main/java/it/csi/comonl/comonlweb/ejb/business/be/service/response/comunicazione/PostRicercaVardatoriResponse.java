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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaVardatori;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedListImpl;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostRicercaVardatoriResponse extends BaseGetResponse<PagedList<RicercaVardatori>> {

	private  PagedList<RicercaVardatori> risultatiRicercaComunicazioni = new PagedListImpl<>();
	
	
	public PagedList<RicercaVardatori> getRisultatiRicercaComunicazioni() {
		return risultatiRicercaComunicazioni;
	}



	public void setRisultatiRicercaComunicazioni(PagedList<RicercaVardatori> risultatiRicercaComunicazioni) {
		this.risultatiRicercaComunicazioni = risultatiRicercaComunicazioni;
	}


	@Override
	protected PagedList<RicercaVardatori> getEntity() {
		return risultatiRicercaComunicazioni;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [risultatiRicercaVardatori=").append(risultatiRicercaComunicazioni).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}

}
