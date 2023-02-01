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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

/**
 * The Class PostComunicazioneResponse.
 */
public class PostInviaComunicazioneResponse extends BaseGetResponse<List<Comunicazione>> {

	private List<Comunicazione> comunicazioni;


	public List<Comunicazione> getComunicazioni() {
		return comunicazioni;
	}

	public void setComunicazioni(List<Comunicazione> comunicazioni) {
		this.comunicazioni = comunicazioni;
	}

	@Override
	protected List<Comunicazione> getEntity() {
		return comunicazioni;
	}

}
