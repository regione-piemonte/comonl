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


import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutCancellaComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutCancellaComunicazioneResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRicercaProspetto
 */
public class PutCancellaComunicazioneService
		extends BaseComunicazioneService<PutCancellaComunicazioneRequest, PutCancellaComunicazioneResponse> {

	private Long idComunicazione;

	/**
	 * Constructor
	 *
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PutCancellaComunicazioneService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad) {
		super(configurationHelper, comunicazioneDad);
	}

	@Override
	protected void checkServiceParams() {
		idComunicazione = request.getIdComunicazione();
		checkNotNull(idComunicazione, "idComunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Comunicazione comunicazione = comunicazioneDad.getComunicazioneById(idComunicazione);
		checkBusinessCondition(comunicazione.getStatoComunicazione().getId().compareTo(ComonlConstants.STATO_COMUNICAZIONE_INSERITA_ID)==0,
				MsgComonl.COMCOME0017.getError("statoComunicazione", comunicazione.getStatoComunicazione().getDsComTStatoComunicazione()));

		comunicazione.setFlgCurrentRecord(null);
		comunicazioneDad.updateStatoComunicazione(comunicazione, ComonlConstants.STATO_COMUNICAZIONE_CANCELLATA_ID);

		if (comunicazione.getIdComDComunicPrecedente()!=null) {
			Comunicazione comunicazionePrecedente = comunicazioneDad.getComunicazioneById(comunicazione.getIdComDComunicPrecedente());
			comunicazionePrecedente.setFlgCurrentRecord(ComonlConstants.FLAG_S);
			comunicazioneDad.updateStatoComunicazione(comunicazionePrecedente, ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
		}
		
		response.setComunicazione(comunicazione);
	}
}
