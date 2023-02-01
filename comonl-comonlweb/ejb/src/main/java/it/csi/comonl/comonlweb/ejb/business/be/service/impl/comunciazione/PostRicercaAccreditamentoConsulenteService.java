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
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaAccreditamentoConsulenteResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;

/**
 * PostRicercaProspetto
 */
public class PostRicercaAccreditamentoConsulenteService
		extends BaseAnagraficaDelegatoService<PostRicercaAccreditamentoAnagraficaRequest, PostRicercaAccreditamentoConsulenteResponse> {

	private FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	/**
	 * Constructor
	 *
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostRicercaAccreditamentoConsulenteService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
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

		long count = anagraficaDelegatoDad.countRicercaAccreditamentoConsulente(ricercaAccreditamentoAnagrafiche);
		log.debug("execute----------->", "valore di count -----> " + count);
		checkBusinessCondition(count > 0, MsgComonl.COMCOMP0002.getError());
		/*
		DecodificaGenerica ilNumeroMassimoRecord = commonDad
				.getParametroByNome(ConstantsComonl.PARAM_PROSPETTI_NUMERO_MASSIMO);
		int ilNumeroEffettivo = 450;
		if (ComonlSrvUtil.isNotVoid(ilNumeroMassimoRecord)) {
			ilNumeroEffettivo = Integer.parseInt(ilNumeroMassimoRecord.getCodDecodifica());
		}*/
		
		
		int limMaxRicerca = ComonlConstants.MAX_NUM_RES_RICERCA;
		checkBusinessCondition(count <= ComonlConstants.MAX_NUM_RES_RICERCA, MsgComonl.COMCOME0023.getError() );
		
		if (request.getSize() == 0 || request.getSize() > limMaxRicerca) {
			request.setSize(limMaxRicerca);
		}
		
		
		PagedList<AnagraficaDelegato> risultatiRicercaAccreditamentoConsulente = anagraficaDelegatoDad.getRicercaConsulente(request.getPage(), request.getSize(),
				request.getSort(), request.getRicercaAccreditamentoAnagrafiche());
		
		response.setRisultatiRicercaAccreditamentoConsulente(risultatiRicercaAccreditamentoConsulente);
	}

}
