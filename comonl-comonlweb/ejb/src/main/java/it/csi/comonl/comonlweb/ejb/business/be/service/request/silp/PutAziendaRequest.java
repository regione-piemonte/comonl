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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.silp;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
 

public class PutAziendaRequest implements BaseRequest {

	private final  DatiAzienda datiAzienda;
	/**
	 * Constructor
	 * 
	 * @param Compensazioni
	 */
	public PutAziendaRequest(DatiAzienda sede) {
		this.datiAzienda = sede;

	}

	public DatiAzienda getDatiAzienda() {
		return datiAzienda;
	}

	
}
