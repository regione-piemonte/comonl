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

public class RemoveConsulenteRequest implements BaseRequest{
	
	private final String cfDelegato;
	private final Long idSoggettoAbilitato;
	
	
	public RemoveConsulenteRequest(String cfDelegato, Long idSoggettoAbilitato) {
		super();
		this.cfDelegato = cfDelegato;
		this.idSoggettoAbilitato = idSoggettoAbilitato;
	}
	
	
	public String getCfDelegato() {
		return cfDelegato;
	}


	public Long getIdSoggettoAbilitato() {
		return idSoggettoAbilitato;
	}
	
	
	
	

}
