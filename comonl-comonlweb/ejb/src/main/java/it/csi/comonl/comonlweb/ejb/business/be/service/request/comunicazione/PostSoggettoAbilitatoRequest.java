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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;



/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostSoggettoAbilitatoRequest implements BaseRequest {

	private final SoggettoAbilitato soggettoAbilitato;

	/**
	 * Constructor
	 */
	public PostSoggettoAbilitatoRequest(SoggettoAbilitato soggettoAbilitato) {
		super();
		this.soggettoAbilitato = soggettoAbilitato;
	}

	public SoggettoAbilitato getSoggettoAbilitato() {
		return soggettoAbilitato;
	}

	

}
