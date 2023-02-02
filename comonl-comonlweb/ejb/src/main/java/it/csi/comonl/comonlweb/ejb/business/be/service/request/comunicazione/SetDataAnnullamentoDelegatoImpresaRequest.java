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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * Request for reading the SoggettoAbilitato by its cfSoggetto
 */
public class SetDataAnnullamentoDelegatoImpresaRequest implements BaseRequest {

	private final String cfDelegato;
	private final String tipoAnagrafica;
	private final String cfImpresa;

	/**
	 * 
	 * @param cfSoggetto
	 * @param tipoAnagrafica
	 * @param cfImpresa
	 */
	public SetDataAnnullamentoDelegatoImpresaRequest(String cfDelegato,String tipoAnagrafica,String cfImpresa) {
		this.cfDelegato = cfDelegato;
		this.tipoAnagrafica = tipoAnagrafica;
		this.cfImpresa = cfImpresa;
	}

	

	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}


	public String getCfDelegato() {
		return cfDelegato;
	}

	public String getCfImpresa() {
		return cfImpresa;
	}
	
	
}
