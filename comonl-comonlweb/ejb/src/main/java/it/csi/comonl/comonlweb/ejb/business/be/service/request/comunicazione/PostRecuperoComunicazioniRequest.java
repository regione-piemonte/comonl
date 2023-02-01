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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;


public class PostRecuperoComunicazioniRequest implements BaseRequest {

	private final FormRecuperoComunicazione recuperoComunicazione;


	public PostRecuperoComunicazioniRequest(FormRecuperoComunicazione recuperoComunicazione) {
		this.recuperoComunicazione = recuperoComunicazione;
	}

	public FormRecuperoComunicazione getRecuperoComunicazione() {
		return recuperoComunicazione;
	}
}
