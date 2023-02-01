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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiGeneraliVardatori;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkDatiGeneraliRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

/**
 * PostChkDatiGeneraliService
 */
public class PostChkDatiGeneraliVardatoriService
		extends BaseComunicazioneService<PostChkDatiGeneraliRequest, PostComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DecodificaDad decodificaDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostChkDatiGeneraliVardatoriService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkNotNull(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");
		Comunicazione comunicazione = setDeafultPerRuolo(wrapperComunicazione.getComunicazione(), wrapperComunicazione.getRuolo());
		ValidatorDatiGeneraliVardatori validator = new ValidatorDatiGeneraliVardatori(comunicazione, comunicazioneDad, decodificaDad);

		if (!validator.isOk())
		{
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setComunicazione(comunicazione);

	}
}