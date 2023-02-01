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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;


/**
 * Response for reading DelegatoImpresa by its AnagraficaDelegato.
 */
public class GetAnagraficaDelegatoByIdSoggettoAbilitatoResponse extends BaseGetResponse<List<AnagraficaDelegato>> {

	private List<AnagraficaDelegato> anagraficaDelegatos;

	

	@Override
	protected List<AnagraficaDelegato> getEntity() {
		return anagraficaDelegatos;
	}



	public List<AnagraficaDelegato> getAnagraficaDelegatos() {
		return anagraficaDelegatos;
	}



	public void setAnagraficaDelegatos(List<AnagraficaDelegato> anagraficaDelegatos) {
		this.anagraficaDelegatos = anagraficaDelegatos;
	}

	

}
