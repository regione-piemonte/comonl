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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaDelegheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaDelegheResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;

/**
 * PostRicercaProspetto
 */
public class PostRicercaDelegheService
		extends BaseDelegaService<PostRicercaDelegheRequest, PostRicercaDelegheResponse> {

	private FormRicercaDelega ricercaDelega;

	/**
	 * Constructor
	 *
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostRicercaDelegheService(ConfigurationHelper configurationHelper, DelegaDad delegaDad,  DecodificaDad decodificaDad) {
		super(configurationHelper, delegaDad, decodificaDad);
	}

	 @Override
		protected void checkServiceParams() {
		 ricercaDelega = request.getRicercaDelega();
			checkNotNull(ricercaDelega, "ricercaDelega", true);
	    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");


		long count = delegaDad.countRicercaDelega(ricercaDelega);
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
		checkBusinessCondition(count <= limMaxRicerca, MsgComonl.COMCOME0023.getError() );
		
		if (request.getSize() == 0 || request.getSize() > limMaxRicerca) {
			request.setSize(limMaxRicerca);
		}
		
		
		PagedList<Delega> risultatiRicercaDeleghe = delegaDad.getRicerca(request.getPage(), request.getSize(),
				request.getSort(), request.getRicercaDelega());
		
		response.setRisultatiRicercaDeleghe(risultatiRicercaDeleghe);
	}

}
