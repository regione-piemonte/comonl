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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;

/**
 * The Class PostTutoreResponse.
 */
public class PutTutoreResponse extends BasePostResponse<Long,Tutore> {

	private Tutore tutore;

	public Tutore getTutore() {
		return tutore;
	}

	public void setTutore(Tutore tutore) {
		this.tutore = tutore;
	}

	@Override
	protected Tutore getEntity() {
		return tutore;
	}

	@Override
	protected String getBaseUri() {
		return "comunicazione/tutore";
	}	
	
}
