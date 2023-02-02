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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostComunicazioneUrgenzaRequest implements BaseRequest {

	private final WrapperComunicazione wrapperComunicazione;

	/**
	 * Constructor
	 */
	public PostComunicazioneUrgenzaRequest(WrapperComunicazione wrapperComunicazione) {
		super();
		this.wrapperComunicazione = wrapperComunicazione;
	}

	public WrapperComunicazione getWrapperComunicazione() {
		return wrapperComunicazione;
	}

	

}
