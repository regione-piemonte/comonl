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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;

/**
 * The Class PutRapportoResponse.
 */
public class PutRapportoMissioneResponse extends BasePostResponse<Long,Rapporto> {

	private Rapporto rapporto;

	public Rapporto getRapporto() {
		return rapporto;
	}

	public void setRapporto(Rapporto rapporto) {
		this.rapporto = rapporto;
	}

	@Override
	protected Rapporto getEntity() {
		return rapporto;
	}

	@Override
	protected String getBaseUri() {
		return "comunicazione/rapporto";
	}	
	
}
