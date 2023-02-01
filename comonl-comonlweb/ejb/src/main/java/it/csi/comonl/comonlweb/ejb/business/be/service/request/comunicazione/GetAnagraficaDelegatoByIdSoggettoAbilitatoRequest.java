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

public class GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest implements BaseRequest{
	
	private final Long idSoggettoAbilitato;
	
	
	public GetAnagraficaDelegatoByIdSoggettoAbilitatoRequest(Long idSoggettoAbilitato) {
		super();
		this.idSoggettoAbilitato = idSoggettoAbilitato;
	}


	public Long getIdSoggettoAbilitato() {
		return idSoggettoAbilitato;
	}
	
	
	
	
	

}
