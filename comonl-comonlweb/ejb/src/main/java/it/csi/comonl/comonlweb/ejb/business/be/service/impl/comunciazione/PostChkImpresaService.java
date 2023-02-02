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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostChkImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;

public class PostChkImpresaService extends BaseChkService<PostChkImpresaRequest, PostComunicazioneResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DecodificaDad decodificaDad;

	public PostChkImpresaService(ConfigurationHelper configurationHelper, ControlliDad controlliDad, ComunicazioneDad comunicazioneDad, DecodificaDad decodificaDad) {
		super(configurationHelper, controlliDad, comunicazioneDad);
		this.decodificaDad=decodificaDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkNotNull(wrapperComunicazione.getComunicazione(), "comunicazione", true);
	}

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");
		
		try {

			Comunicazione comunicazione = wrapperComunicazione.getComunicazione();
			// Se RETTIFICA necessario controllo dei dati essenziali qualora sia fuori tempo.
			boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperComunicazione.getRuolo());
	
			Comunicazione comunicazioneDaRettificare = null;
			if (checkDatiEssenziali) {
				comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			}
	
			Validator<Comunicazione> validator = new ValidatorDatore(comunicazione, checkDatiEssenziali, comunicazioneDaRettificare, controlliDad, decodificaDad, wrapperComunicazione.getRuolo());
			validator.setRuolo(wrapperComunicazione.getRuolo());
			if(!validator.isOk()) {
				response.addApiErrors(validator.getApiErrors());
			}
	
			response.setComunicazione(comunicazione);
		}
		catch (Exception err) {
			err.printStackTrace();
			throw new RuntimeException(err.getMessage(), err.getCause());
		}
		
	}
}
