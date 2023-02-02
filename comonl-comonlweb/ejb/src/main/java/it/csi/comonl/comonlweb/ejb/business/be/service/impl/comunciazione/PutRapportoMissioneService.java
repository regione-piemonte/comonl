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


import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDatiRapportoMissione;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutRapportoMissioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutRapportoMissioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperRapporto;

/**
 * PutRapportoService
 */
public class PutRapportoMissioneService
		extends BaseComunicazioneService<PutRapportoMissioneRequest, PutRapportoMissioneResponse> {
	
	private WrapperRapporto wrapperRapporto;
	private RapportoDad rapportoDad;
	private ControlliDad controlliDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param rapportoDad      the DAD for the rapporto
	 */
	public PutRapportoMissioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, RapportoDad rapportoDad, ControlliDad controlliDad) {
		super(configurationHelper,comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.controlliDad = controlliDad;
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperRapporto = request.getWrapperRapporto();
		checkModel(wrapperRapporto.getRapporto(), "rapporto", true);
		checkModel(wrapperRapporto.getRapporto().getComunicazione(), "rapporto.comunicazione", true);
    }
	
	@Override
	protected void execute() {
		// CDU-11 Gestione dati missione 
		log.error("execute----------->", "Entro nel metodo execute");
		
		Rapporto rapporto = wrapperRapporto.getRapporto();
		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(rapporto.getComunicazione().getId());
		
		boolean checkDatiEssenziali = isCheckDatiEssenziali(comunicazione, wrapperRapporto.getRuolo());
		Comunicazione comunicazioneDaRettificare = null; 
		if (checkDatiEssenziali) {
			comunicazioneDaRettificare = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
		}

		comunicazione.setMissione(rapporto);
		ValidatorDatiRapportoMissione validator = new ValidatorDatiRapportoMissione(comunicazione, controlliDad, checkDatiEssenziali, comunicazioneDaRettificare);
		if (!validator.isOk()) {
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		response.setApiWarnings(validator.getApiWarnings());

		Rapporto rapportoPersist = rapportoDad.updateRapporto(rapporto);
		
		// aggiorna data riferimento comunicazione
		comunicazione.setDataRiferimento(rapporto.getDtInizioMissione());
		comunicazioneDad.updateComunicazione(comunicazione);
		
		rapportoPersist.setApiWarnings(response.getApiWarnings());
		response.setRapporto(rapportoPersist);
	}
}
