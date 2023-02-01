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
public class GetTipoContrattiByTracciatoAndTipoComunicazioneRequest implements BaseRequest {
	
	private String tipoTracciato;
	private String tipoCmunicazione;

	public GetTipoContrattiByTracciatoAndTipoComunicazioneRequest(String tipoTracciato,String tipoCmunicazione) {
		super();
		this.tipoTracciato = tipoTracciato;
		this.tipoCmunicazione = tipoCmunicazione;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetTipoContrattiByTracciatoAndTipoComunicazioneRequest []");
		return builder.toString();
	}


	public String getTipoTracciato() {
		return tipoTracciato;
	}


	public void setTipoTracciato(String tipoTracciato) {
		this.tipoTracciato = tipoTracciato;
	}


	public String getTipoCmunicazione() {
		return tipoCmunicazione;
	}


	public void setTipoCmunicazione(String tipoCmunicazione) {
		this.tipoCmunicazione = tipoCmunicazione;
	}
}
