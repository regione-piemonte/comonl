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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.common;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;

/**
 * The Class GetRuoloResponse.
 */
public class GetParametroCommaxResponse extends BaseGetResponse<CommaxParametri> {

	/** The model. */
	private CommaxParametri parametro = null;

	public CommaxParametri getParametro() {
		return parametro;
	}

	public void setParametro(CommaxParametri parametro) {
		this.parametro = parametro;
	}

	@Override
	protected CommaxParametri getEntity() {
		return parametro;
	}

}
