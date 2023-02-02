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
 * Request for reading the SoggettoAbilitato by its cfSoggetto
 */
public class GetSoggettoAbilitatoByIdRequest implements BaseRequest {

	private final Long idSoggettoAbilitato;

	/**
	 * Constructor
	 * @param id the id
	 */
	public GetSoggettoAbilitatoByIdRequest(Long idSoggettoAbilitato) {
		this.idSoggettoAbilitato = idSoggettoAbilitato;
	}

	public Long getIdSoggettoAbilitato() {
		return idSoggettoAbilitato;
	}


}
