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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.common;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetParametroRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetParametroResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;

/**
 * Retrieves an testataOrdine by its id
 */
public class GetParametroService extends BaseCommonService<GetParametroRequest, GetParametroResponse> {

	public GetParametroService(ConfigurationHelper configurationHelper, CommonDad commonDad) {
		super(configurationHelper, commonDad);
	}

	@Override
	protected void execute() {
		ComonlsParametri ilParametro = commonDad.getParametroByDescrizione(request.getNomeParametro());
		response.setParametro(ilParametro);
	}

}
