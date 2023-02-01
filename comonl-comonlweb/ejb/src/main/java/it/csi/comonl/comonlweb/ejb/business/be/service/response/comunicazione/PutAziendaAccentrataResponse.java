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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BasePutResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;


/**
 * Response for reading AnagraficaAziAccent by its cfImpresa.
 */
public class PutAziendaAccentrataResponse extends BasePutResponse<Long, AnagraficaAziAccent> {

	private AnagraficaAziAccent anagraficaAziAccent;

	@Override
	protected AnagraficaAziAccent getEntity() {
		return anagraficaAziAccent;
	}



	public AnagraficaAziAccent getAnagraficaAziAccent() {
		return anagraficaAziAccent;
	}



	public void setAnagraficaAziAccent(AnagraficaAziAccent anagraficaAziAccent) {
		this.anagraficaAziAccent = anagraficaAziAccent;
	}

	@Override
	protected String getBaseUri() {
		return "anagraficaAziAccent";
	}
}
