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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.GetRuoloRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.GetRuoloResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;

/**
 * GetRuolo
 */
public class GetRuoliPerServizioEsternoService extends BaseCommonService<GetRuoloRequest, GetRuoloResponse> {

	AnagraficaDelegatoDad anagraficaDelegatoDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param ruoloDad            the DAD for the ruolo
	 */
	public GetRuoliPerServizioEsternoService(ConfigurationHelper configurationHelper, CommonDad commonDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, commonDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

	@Override
	protected void execute() {
		try {
			List<Ruolo> listaProfiliPersonaAutorizzata = anagraficaDelegatoDad
					.findRuoliPerServizioEsposto(request.getCodiceFiscale());
			response.setRuolos(listaProfiliPersonaAutorizzata);
		} catch (Exception e) {
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			throw new RuntimeException(e);
		}
	}

}
