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
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiTutore;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutTutoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutTutoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutTutoreService
 */
public class PutTutoreService
		extends BaseComunicazioneService<PutTutoreRequest, PutTutoreResponse> {
	
	private Tutore tutore;
	private Comunicazione comunicazioneClient;
	private TutoreDad tutoreDad;
	private ControlliDad controlliDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad the DAD for the comunicazione
	 * @param tutoreDad      the DAD for the tutore
	 * @param controlliDad   the DAD for the controlli
	 */
	public PutTutoreService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, TutoreDad tutoreDad, ControlliDad controlliDad) {
		super(configurationHelper, comunicazioneDad);
		this.tutoreDad = tutoreDad;
		this.controlliDad = controlliDad;
	}
	
	@Override
	protected void checkServiceParams() {
		comunicazioneClient = request.getComunicazione();
		checkModel(comunicazioneClient, "comunicazione", true);
		tutore = comunicazioneClient.getRapporto().getTutore();
		checkModel(tutore, "tutore", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Validator<Comunicazione> validator = new ValidatorDatiTutore(comunicazioneClient, controlliDad);// capire se la comunicazione passata da client sia sufficientemente popolata
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}

		Tutore tutorePersist = tutoreDad.updateTutore(tutore);
		
		response.setTutore(tutorePersist);
	}
}
