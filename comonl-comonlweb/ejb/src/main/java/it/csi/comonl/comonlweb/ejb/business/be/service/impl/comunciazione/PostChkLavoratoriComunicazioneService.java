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
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorLavoratore;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkLavoratoriComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;

public class PostChkLavoratoriComunicazioneService extends BaseChkService<PostChkLavoratoriComunicazioneRequest, PostComunicazioneResponse> {

	private WrapperComunicazione wrapperComunicazione;
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PostChkLavoratoriComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,  ControlliDad controlliDad) {
		super(configurationHelper, controlliDad, comunicazioneDad);
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
    }
	
	@Override
	protected void execute() {
		
		Comunicazione comunicazione = wrapperComunicazione.getComunicazione();
		
		// Se RETTIFICA necessario controllo dei dati essenziali qualora sia fuori tempo.
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperComunicazione.getRuolo());
		
		Comunicazione comunicazioneDaRettificare = null;
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
		}
		
		TipoProvenienza tipoProvenienza = null;
		if (isComunicazioneAnnulla(comunicazione)) {
			Comunicazione comunicazioneDaAnnullare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			tipoProvenienza = comunicazioneDaAnnullare.getTipoProvenienza();
		}
		
		Validator<Comunicazione> validator = new ValidatorLavoratore(comunicazione, controlliDad, checkDatiEssenziali, comunicazioneDaRettificare);
		validator.setTipoProvenienza(tipoProvenienza);
		if(!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
		}
		
		response.setComunicazione(comunicazione);
	}
}
