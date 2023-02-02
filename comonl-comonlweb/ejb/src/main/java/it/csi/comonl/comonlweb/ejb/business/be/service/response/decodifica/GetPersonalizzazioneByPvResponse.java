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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;

/**
 * The Class GetProvinciaResponse.
 */
public class GetPersonalizzazioneByPvResponse extends BaseGetResponse<Personalizzazione> {

	private Personalizzazione personalizzazione;


	@Override
	protected Personalizzazione getEntity() {
		return personalizzazione;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [personalizzazione=").append(personalizzazione).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}


	public Personalizzazione getPersonalizzazione() {
		return personalizzazione;
	}


	public void setPersonalizzazione(Personalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

}
