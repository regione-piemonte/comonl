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


import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiTutore;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostTutoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostTutoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostTutoreService
 */
public class PostTutoreService
		extends BaseComunicazioneService<PostTutoreRequest, PostTutoreResponse> {
	
	private Comunicazione comunicazioneClient;
	private RapportoDad rapportoDad;
	private TutoreDad tutoreDad;
	private ControlliDad controlliDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostTutoreService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, TutoreDad tutoreDad, ControlliDad controlliDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.tutoreDad = tutoreDad;
		this.controlliDad = controlliDad;
	}

	@Override
	protected void checkServiceParams() {
		comunicazioneClient = request.getComunicazione();
		checkModel(comunicazioneClient, "comunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Validator<Comunicazione> validator = new ValidatorDatiTutore(comunicazioneClient, controlliDad);// capire se la comunicazione passata da client sia sufficientemente popolata
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}

		Tutore tutorePersist = tutoreDad.insertTutore(request.getComunicazione().getRapporto().getTutore());

		Rapporto rapporto = rapportoDad.getRapportoByIdComunicazione(request.getComunicazione().getId());
		rapporto.setTutore(tutorePersist);
		rapporto = rapportoDad.updateRapporto(rapporto);
		
		response.setTutore(tutorePersist);
	}
}
