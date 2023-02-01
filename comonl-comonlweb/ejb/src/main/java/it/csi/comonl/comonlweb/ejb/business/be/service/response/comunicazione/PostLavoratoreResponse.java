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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePostResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;

public class PostLavoratoreResponse extends BasePostResponse<Long, Lavoratore> {

	
	
	private Lavoratore lavoratore;



	public Lavoratore getLavoratore() {
		return lavoratore;
	}

	public void setLavoratore(Lavoratore lavoratore) {
		this.lavoratore = lavoratore;
	}

	@Override
	protected Lavoratore getEntity() {
		return lavoratore;
	}

	@Override
	protected String getBaseUri() {
		return "comunicazione/lavoratore";
	}	

}
