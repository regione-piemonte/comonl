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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

/**
 * The Class PutDatiTrasformazioneRequest.
 */
public class PutDatiTrasferimentoDistaccoRequest implements BaseRequest {
	private final WrapperComunicazione wrapperComunicazione;

	/**
	 * Constructor
	 */
	public PutDatiTrasferimentoDistaccoRequest(WrapperComunicazione wrapperComunicazione) {
		this.wrapperComunicazione = wrapperComunicazione;
	}

	public WrapperComunicazione getWrapperComunicazione() {
		return wrapperComunicazione;
	}
}
