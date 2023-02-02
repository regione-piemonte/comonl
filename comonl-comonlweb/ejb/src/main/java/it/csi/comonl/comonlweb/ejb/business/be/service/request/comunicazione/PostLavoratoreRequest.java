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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;

public class PostLavoratoreRequest implements BaseRequest {

	private final Lavoratore lavoratore;

	/**
	 * Constructor
	 */
	public PostLavoratoreRequest(Lavoratore lavoratore) {
		this.lavoratore = lavoratore;
	}

	public Lavoratore getLavoratore() {
		return lavoratore;
	}

}
