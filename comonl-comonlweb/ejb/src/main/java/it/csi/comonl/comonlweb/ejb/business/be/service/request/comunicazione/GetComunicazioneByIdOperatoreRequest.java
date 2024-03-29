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

/**
 * Request for reading the Comunicazione by its id
 */
public class GetComunicazioneByIdOperatoreRequest implements BaseRequest {

	private final Long id;
	private final Boolean operatoreProvinciale;

	/**
	 * Constructor
	 * @param id the id
	 */
	public GetComunicazioneByIdOperatoreRequest(Long id, Boolean operatoreProvinciale) {
		this.id = id;
		this.operatoreProvinciale = operatoreProvinciale;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	
	public Boolean getOperatoreProvinciale() {
		return operatoreProvinciale;
	}
}
