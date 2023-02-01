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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;

/**
 * The Class UploadComunicazioniResponse.
 */
public class PostComunicazioneUrgenzaResponse extends BaseGetResponse<Long> {

	private Long idComunicazione;
	
	
	
	

	@Override
	protected Long getEntity() {
		return idComunicazione;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [PostComunicazioneUrgenzaResponse=").append(idComunicazione).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public Long getIdComunicazione() {
		return idComunicazione;
	}


	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}


}
