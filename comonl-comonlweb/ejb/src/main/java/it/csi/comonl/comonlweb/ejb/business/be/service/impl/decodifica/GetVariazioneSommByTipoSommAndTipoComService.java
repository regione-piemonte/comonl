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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica.GetVariazioneSommByTipoSommAndTipoComRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica.GetVariazioneSommByTipoSommAndTipoComResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;

/**
 * Gets the Categorias
 */
public class GetVariazioneSommByTipoSommAndTipoComService extends BaseDecodificaService<GetVariazioneSommByTipoSommAndTipoComRequest, GetVariazioneSommByTipoSommAndTipoComResponse> {
	
	String idTipoComunicazione;
	Long idTipoSomm;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param decodificaDad       the DAD for the decodifica
	 */
	public GetVariazioneSommByTipoSommAndTipoComService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper, decodificaDad);
	}
	
	 @Override
		protected void checkServiceParams() {
		 idTipoComunicazione = request.getIdTipoComunicazione();
		 idTipoSomm= request.getIdTipoSomm();
			checkNotNull(idTipoComunicazione, "idTipoComunicazione", true);
			checkNotNull(idTipoSomm, "idTipoSomm", true);
	    }


	@Override
	protected void execute() {
		response.setVariazioneSomm(decodificaDad.getTipoVariazioneBySommComm(idTipoSomm, idTipoComunicazione));;
	}

}
