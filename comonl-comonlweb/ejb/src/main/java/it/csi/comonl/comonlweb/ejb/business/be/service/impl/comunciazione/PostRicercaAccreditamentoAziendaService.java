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

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaAccreditamentoAnagraficaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaAccreditamentoAziendaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAzienda;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;

/**
 * PostRicercaProspetto
 */
public class PostRicercaAccreditamentoAziendaService extends
		BaseAnagraficaDelegatoService<PostRicercaAccreditamentoAnagraficaRequest, PostRicercaAccreditamentoAziendaResponse> {

	private FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	/**
	 * Constructor
	 *
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostRicercaAccreditamentoAziendaService(ConfigurationHelper configurationHelper,
			AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		ricercaAccreditamentoAnagrafiche = request.getRicercaAccreditamentoAnagrafiche();
		checkNotNull(ricercaAccreditamentoAnagrafiche, "ricercaAccreditamentoAzienda", true);
	}

	@Override
	protected void execute() {

		log.debug("execute----------->", "Entro nel metodo execute");
		int limMaxRicerca = ComonlConstants.MAX_NUM_RES_RICERCA;
		PagedList<AnagraficaAzienda> risultatiRicercaAccreditamentoAzienda = anagraficaDelegatoDad.getRicercaAzienda(limMaxRicerca,
				request.getPage(), request.getSize(), request.getSort(), request.getRicercaAccreditamentoAnagrafiche());

		response.setRisultatiRicercaAccreditamentoAziendaResponse(risultatiRicercaAccreditamentoAzienda);
	}

}
