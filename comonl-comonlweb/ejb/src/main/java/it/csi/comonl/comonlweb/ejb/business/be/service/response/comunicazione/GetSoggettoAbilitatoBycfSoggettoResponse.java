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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;

/**
 * Response for reading SoggettoAbilitato by its cfSoggetto.
 */
public class GetSoggettoAbilitatoBycfSoggettoResponse extends BaseGetResponse<SoggettoAbilitato> {

	private SoggettoAbilitato soggettoAbilitato;



	@Override
	protected SoggettoAbilitato getEntity() {
		return soggettoAbilitato;
	}



	public SoggettoAbilitato getSoggettoAbilitato() {
		return soggettoAbilitato;
	}



	public void setSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		this.soggettoAbilitato = soggettoAbilitato;
	}
	

}
