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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetComunicazioneByIdRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetComunicazioneByIdResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.mail.MailHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

/**
 * Retrieves an testataOrdine by its id
 */
public class GetComunicazioneByIdService extends BaseComunicazioneService<GetComunicazioneByIdRequest, GetComunicazioneByIdResponse> {
	
	
	private MailHelper mailHelper;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param testataOrdineDad    the testataOrdine DAD
	 */
	public GetComunicazioneByIdService(ConfigurationHelper configurationHelper, MailHelper mailHelper,ComunicazioneDad comunicazioneDad) {
		super(configurationHelper,comunicazioneDad);
		this.mailHelper = mailHelper;
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getId(), "id");
	}

	@Override
	protected void execute() {
		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(request.getId());
		response.setComunicazione(comunicazione);
	}
}
