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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.decodifica;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * The Class GetProvinciaRequest.
 */
public class GetVariazioneSommByTipoSommAndTipoComRequest implements BaseRequest {
	
	private String idTipoComunicazione;
	private Long idTipoSomm;
	public GetVariazioneSommByTipoSommAndTipoComRequest(String idTipoComunicazione, Long idTipoSomm) {
		super();
		this.idTipoComunicazione = idTipoComunicazione;
		this.idTipoSomm = idTipoSomm;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetVariazioneSommByTipoSommAndTipoComRequest []");
		return builder.toString();
	}


	public String getIdTipoComunicazione() {
		return idTipoComunicazione;
	}


	public void setIdTipoComunicazione(String idTipoComunicazione) {
		this.idTipoComunicazione = idTipoComunicazione;
	}


	public Long getIdTipoSomm() {
		return idTipoSomm;
	}


	public void setIdTipoSomm(Long idTipoSomm) {
		this.idTipoSomm = idTipoSomm;
	}
}


	
