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

/**
 * The Class PutCancellaComunicazioneRequest.
 */
public class PutCancellaComunicazioneRequest implements BaseRequest {

	private Long idComunicazione;
	/**
	 * Constructor
	 */
	public PutCancellaComunicazioneRequest(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public Long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
}
