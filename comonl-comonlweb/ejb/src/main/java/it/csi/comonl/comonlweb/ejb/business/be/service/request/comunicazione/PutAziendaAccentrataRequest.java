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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;

public class PutAziendaAccentrataRequest implements BaseRequest{
	
	private final AnagraficaAziAccent anagraficaAziAccent;
	
	public PutAziendaAccentrataRequest( AnagraficaAziAccent anagraficaAziAccent) {
		super();
		this.anagraficaAziAccent = anagraficaAziAccent;
	}

	public AnagraficaAziAccent getAnagraficaAziAccent() {
		return anagraficaAziAccent;
	}
}
