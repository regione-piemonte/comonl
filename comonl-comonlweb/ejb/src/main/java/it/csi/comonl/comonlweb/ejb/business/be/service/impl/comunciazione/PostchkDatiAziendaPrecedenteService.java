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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiAziendaPrecedente;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkDatiAziendaPrecedenteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

public class PostchkDatiAziendaPrecedenteService extends BaseChkService<PostChkDatiAziendaPrecedenteRequest, PostComunicazioneResponse> {

	public PostchkDatiAziendaPrecedenteService(ConfigurationHelper configurationHelper, ControlliDad controlliDad) {
		super(configurationHelper, controlliDad, null);

	}

	@Override
	protected void execute() {
		Comunicazione comunicazione = request.getComunicazione();
		
		Validator<Comunicazione> validator = new ValidatorDatiAziendaPrecedente(comunicazione, controlliDad);
		
		if(validator.isOk()) {
			response.setComunicazione(comunicazione);
			return;
		}
		
		response.addApiErrors(validator.getApiErrors());
		
	}

}
