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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.ApiError;

/**
 * Response for reading Comunicazione by its id.
 */
public class CheckDatiEssenzialiResponse extends BaseGetResponse<Boolean> {

	Boolean chekDatiEssenziali;

	public Boolean getChekDatiEssenziali() {
		return chekDatiEssenziali;
	}

	public void setChekDatiEssenziali(Boolean chekDatiEssenziali) {
		this.chekDatiEssenziali = chekDatiEssenziali;
	}

	@Override
	protected Boolean getEntity() {
		return chekDatiEssenziali;
	}
	
	

}
