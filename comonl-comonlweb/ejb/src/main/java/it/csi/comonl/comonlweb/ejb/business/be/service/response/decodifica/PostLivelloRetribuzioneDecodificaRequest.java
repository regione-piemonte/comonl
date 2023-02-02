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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;


import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;

/**
 * The Class GetCcnlRequest.
 */
public class PostLivelloRetribuzioneDecodificaRequest implements BaseRequest {

	
	private final DecodificaGenerica filtro;
	
	public PostLivelloRetribuzioneDecodificaRequest(DecodificaGenerica filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the ricercaProspetto
	 */
	public DecodificaGenerica getFiltro() {
		return filtro;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostLivelloRetribuzioneDecodificaRequest []");
		return builder.toString();
	}
}
