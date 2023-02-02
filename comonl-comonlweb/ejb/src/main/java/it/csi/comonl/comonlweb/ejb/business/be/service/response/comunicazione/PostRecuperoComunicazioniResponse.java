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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RecuperoComunicazione;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostRecuperoComunicazioniResponse extends BaseGetResponse<List<RecuperoComunicazione>> {

	private List<RecuperoComunicazione> recuperoComunicazione;
	
	public void setRecuperoComunicazione(List<RecuperoComunicazione> recuperoComunicazione) {
		this.recuperoComunicazione = recuperoComunicazione;
	}

	@Override
	protected List<RecuperoComunicazione> getEntity() {
		return recuperoComunicazione;
	}

}
