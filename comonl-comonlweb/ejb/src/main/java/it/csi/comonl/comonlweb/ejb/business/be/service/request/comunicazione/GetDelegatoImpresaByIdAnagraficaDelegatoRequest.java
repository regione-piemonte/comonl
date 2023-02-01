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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

public class GetDelegatoImpresaByIdAnagraficaDelegatoRequest implements BaseRequest{
	
	private final String cfDelegato;
	private final String tipoAnagrafica;
	
	
	public GetDelegatoImpresaByIdAnagraficaDelegatoRequest(String cfDelegato, String tipoAnagrafica) {
		super();
		this.cfDelegato = cfDelegato;
		this.tipoAnagrafica = tipoAnagrafica;
	}
	
	
	public String getCfDelegato() {
		return cfDelegato;
	}
	public String getTipoAnagrafica() {
		return tipoAnagrafica;
	}
	
	

}