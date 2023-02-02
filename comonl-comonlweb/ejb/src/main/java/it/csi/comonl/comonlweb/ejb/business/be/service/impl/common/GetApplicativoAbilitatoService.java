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
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

/**
 * Retrieves an testataOrdine by its id
 */
public class GetApplicativoAbilitatoService extends BaseCommonService<GetParametroRequest, GetParametroResponse> {

	public GetApplicativoAbilitatoService(ConfigurationHelper configurationHelper, CommonDad commonDad) {
		super(configurationHelper, commonDad);
	}

	@Override
	protected void execute() {
		ComonlsParametri ilParametro = commonDad
				.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_APPLICATIVO_ABILITATO);
		if (ComonlUtility.isNotVoid(ilParametro) && ilParametro.getValoreParametro().equals(ComonlConstants.FLAG_N)) {
			ilParametro = commonDad
					.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_APPLICATIVO_NON_ABILITATO_MESSAGGIO);
		}
		response.setParametro(ilParametro);
	}

}
