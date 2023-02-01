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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

/**
 * The Class PostComunicazioneResponse.
 */
public class PostAnnullaComunicazioneResponse extends BasePostResponse<Long,Comunicazione> {

	
	
	private Comunicazione comunicazione;


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
	protected String getBaseUri() {
		return "comunicazione";
	}	
	
}
