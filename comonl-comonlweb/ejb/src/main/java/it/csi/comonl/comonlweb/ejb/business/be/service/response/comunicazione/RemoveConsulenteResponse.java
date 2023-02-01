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

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;

/**
 * The Class PostAnagraficaDelegatoResponse.
 */
public class RemoveConsulenteResponse extends BaseGetResponse<AnagraficaDelegato> {

	private AnagraficaDelegato anagraficaDelegato = new AnagraficaDelegato();
	
	

	@Override
	protected AnagraficaDelegato getEntity() {
		return anagraficaDelegato;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [RemoveConsulenteResponse=").append(anagraficaDelegato).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public AnagraficaDelegato getAnagraficaDelegato() {
		return anagraficaDelegato;
	}


	public void setAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		this.anagraficaDelegato = anagraficaDelegato;
	}
	
	


}
