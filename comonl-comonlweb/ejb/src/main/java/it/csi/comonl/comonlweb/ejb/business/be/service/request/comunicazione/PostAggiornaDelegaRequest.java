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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;

/**
 * The Class PostValidaDelegaRequest.
 */
public class PostAggiornaDelegaRequest  implements BaseRequest {

	private final Delega delega;

	/**
	 * Constructor
	 */
	public PostAggiornaDelegaRequest(Delega delega) {
		this.delega = delega;
	}


	public Delega getDelega() {
		return delega;
	}
	

}
