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
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
 

public class PutLavoratoreRequest implements BaseRequest {

	private final LavoratoreSilpEspanso lavoratoreSilpEspanso;
	/**
	 * Constructor
	 * 
	 * @param Compensazioni
	 */
	public PutLavoratoreRequest(LavoratoreSilpEspanso lavoratoreSilpEspanso) {
		this.lavoratoreSilpEspanso = lavoratoreSilpEspanso;

	}

	public LavoratoreSilpEspanso getLavoratore() {
		return lavoratoreSilpEspanso;
	}

	
}
