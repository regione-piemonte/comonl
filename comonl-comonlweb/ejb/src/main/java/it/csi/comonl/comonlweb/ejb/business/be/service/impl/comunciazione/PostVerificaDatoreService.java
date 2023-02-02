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
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatore;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostVerificaDatoreRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostVerificaDatoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

/**
 * PostRicercaProspetto
 */
public class PostVerificaDatoreService
		extends BaseComunicazioneService<PostVerificaDatoreRequest, PostVerificaDatoreResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	
	private DecodificaDad decodificaDad;
	
	private ControlliDad controlliDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostVerificaDatoreService(ConfigurationHelper configurationHelper, 
			ComunicazioneDad comunicazioneDad,
			DecodificaDad decodificaDad,
			ControlliDad controlliDad
			) {
		super(configurationHelper, comunicazioneDad);
		this.decodificaDad = decodificaDad;
		this.controlliDad = controlliDad;
	}

	 @Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkNotNull(wrapperComunicazione.getComunicazione(), "wrapper.comunicazione", true);
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
			validator = new ValidatorDatore(comunicazione, checkDatiEssenziali, comunicazioneDaRettificare, controlliDad, decodificaDad, null);
		}else {
			validator = new ValidatorDatore(comunicazione, controlliDad, decodificaDad, null);
		}
		
		if (!validator.isOk()){
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setComunicazione(comunicazione);
	}
}
