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
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorAziendaUtilizzatrice;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkAziendaUtilizzatriceRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostChkAziendaUtilizzatriceService
 */
public class PostChkAziendaUtilizzatriceService
		extends BaseChkService<PostChkAziendaUtilizzatriceRequest, PostComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param controlliDad        the DAD for the controlli
	 * @param comunicazioneDad    the DAD for the comunicazione
	 */
	public PostChkAziendaUtilizzatriceService(ConfigurationHelper configurationHelper,ControlliDad controlliDad, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper, controlliDad, comunicazioneDad);
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Comunicazione comunicazione = wrapperComunicazione.getComunicazione();
		// Se RETTIFICA necessario controllo dei dati essenziali qualora sia fuori tempo.
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperComunicazione.getRuolo());

		Validator<Comunicazione> validator = null;
		if (checkDatiEssenziali) {
			Comunicazione comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			validator = new ValidatorAziendaUtilizzatrice(comunicazione, checkDatiEssenziali, comunicazioneDaRettificare,controlliDad);
		}else {
			validator = new ValidatorAziendaUtilizzatrice(comunicazione,controlliDad);
		}
		
		if (!validator.isOk()){
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setComunicazione(comunicazione);

	}
}
