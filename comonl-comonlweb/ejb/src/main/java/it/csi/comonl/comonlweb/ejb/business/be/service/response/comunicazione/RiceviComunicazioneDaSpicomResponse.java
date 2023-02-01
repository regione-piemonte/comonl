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

public class RiceviComunicazioneDaSpicomResponse extends BaseGetResponse<Long> {
	
	private Long idComunicazione;


	public Long getIdComunicazione() {
		return idComunicazione;
	}


	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}


	@Override
	protected Long getEntity() {
		return idComunicazione;
	}

}
