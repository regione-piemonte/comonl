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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaAccreditamentoAnagrafiche;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;

/**
 * The Class PostRicercaProspettoRequest.
 */
public class PostStampaElencoAnagraficheRequest implements BaseRequest {

	private final String tipoFormato;
	private final FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche;

	/**
	 * Constructor
	 */
	public PostStampaElencoAnagraficheRequest(String tipoFormato,FormRicercaAccreditamentoAnagrafiche ricercaAccreditamentoAnagrafiche) {
		super();
		this.ricercaAccreditamentoAnagrafiche = ricercaAccreditamentoAnagrafiche;
		this.tipoFormato = tipoFormato;
	}

	public FormRicercaAccreditamentoAnagrafiche getRicercaAccreditamentoAnagrafiche() {
		return ricercaAccreditamentoAnagrafiche;
	}
	
	public String getTipoFormato() {
		return tipoFormato;
	}
}
