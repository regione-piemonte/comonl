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
public class SetDataAnnullamentoCDERequest implements BaseRequest {

	private final String cfDelegato;
	private final String tipoAnagrafica;

	/**
	 * 
	 * @param cfSoggetto
	 * @param tipoAnagrafica
	 * @param flgAnnulla
	 */
	public SetDataAnnullamentoCDERequest(String cfDelegato,String tipoAnagrafica) {
		this.cfDelegato = cfDelegato;
		this.tipoAnagrafica = tipoAnagrafica;
	}

	

	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}


	public String getCfDelegato() {
		return cfDelegato;
	}

}
