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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostVerificaDatoreResponse extends BaseGetResponse<Comunicazione> {

	private Comunicazione comunicazione = new Comunicazione();
	



	public Comunicazione getComunicazione() {
		return comunicazione;
	}


	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}


	@Override
	protected Comunicazione getEntity() {
		return comunicazione;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [PostVerificaDatoreResponse=").append(comunicazione).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


}
