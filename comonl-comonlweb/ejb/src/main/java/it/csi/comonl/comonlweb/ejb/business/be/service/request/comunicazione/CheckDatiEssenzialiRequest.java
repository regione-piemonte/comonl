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

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

/**
 * Request for reading the Comunicazione by its id
 */
public class CheckDatiEssenzialiRequest implements BaseRequest {

	private Comunicazione comunicazione;
	private Ruolo ruolo;
	
	
	public CheckDatiEssenzialiRequest(Comunicazione comunicazione, Ruolo ruolo) {
		this.comunicazione = comunicazione;
		this.ruolo = ruolo;
	}


	
	

	public Ruolo getRuolo() {
		return ruolo;
	}


	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}





	public Comunicazione getComunicazione() {
		return comunicazione;
	}





	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}
	
	
}
