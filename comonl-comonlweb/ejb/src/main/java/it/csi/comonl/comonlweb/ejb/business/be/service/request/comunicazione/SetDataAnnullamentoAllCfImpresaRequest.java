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

/**
 * Request for set DataAnnullamento to null or sys date the DelegatoImpresas by its cfImpresa
 */
public class SetDataAnnullamentoAllCfImpresaRequest implements BaseRequest {

	private final String cfImpresa;
	private final Boolean flgAutorizzazione;
	private final Boolean flgAnnullamento;

	/**
	 * 
	 * @param cfImpresa
	 */
	public SetDataAnnullamentoAllCfImpresaRequest(String cfImpresa,Boolean flgAutorizzazione,Boolean flgAnnullamento) {
		this.cfImpresa = cfImpresa;
		this.flgAutorizzazione = flgAutorizzazione;
		this.flgAnnullamento = flgAnnullamento;
	}


	public String getCfImpresa() {
		return cfImpresa;
	}


	public Boolean getFlgAutorizzazione() {
		return flgAutorizzazione;
	}


	public Boolean getFlgAnnullamento() {
		return flgAnnullamento;
	}

}
