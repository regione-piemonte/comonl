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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;

public class AddAziendaRequest implements BaseRequest{
	
	private final DelegatoImpresa delegatoImpresa;
	
	
	
	
	public AddAziendaRequest(DelegatoImpresa delegatoImpresa) {
		super();
		this.delegatoImpresa = delegatoImpresa;
	}


	public DelegatoImpresa getDelegatoImpresa() {
		return delegatoImpresa;
	}

	
}
