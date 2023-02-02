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

import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorUtility;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.ChkRiepilogoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.ChkRiepilogoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;

/**
 * Retrieves an testataOrdine by its id
 */
public class ChkRiepilogoService extends BaseComunicazioneService<ChkRiepilogoRequest, ChkRiepilogoResponse> {
	
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param testataOrdineDad    the testataOrdine DAD
	 */
	public ChkRiepilogoService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper,comunicazioneDad);
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getId(), "id");
	}

	@Override
	protected void execute() {
		
		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(request.getId());
		boolean isFuoriTempoPerInvio = isFuoriTempoPerInvio(comunicazione);
		checkConditionWarning(!isFuoriTempoPerInvio, MsgComonl.COMCOMW016.getError());
		
		TipoContratti tipoContratti = comunicazione.getRapporto() != null ? comunicazione.getRapporto().getTipoContratti() : null;
		boolean isRapportoTirocinio = ValidatorUtility.isTipoContrattoTirocinio(tipoContratti);
		checkConditionWarning(!isRapportoTirocinio, MsgComonl.COMCOMW017.getError());
		
		response.getApiWarnings();
		
	}
}
