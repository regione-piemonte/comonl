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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;

/**
 * The Class GetRuoloResponse.
 */
public class GetParametroResponse extends BaseGetResponse<ComonlsParametri> {

	/** The model. */
	private ComonlsParametri parametro = null;

	public ComonlsParametri getParametro() {
		return parametro;
	}

	public void setParametro(ComonlsParametri parametro) {
		this.parametro = parametro;
	}

	@Override
	protected ComonlsParametri getEntity() {
		return parametro;
	}

}
