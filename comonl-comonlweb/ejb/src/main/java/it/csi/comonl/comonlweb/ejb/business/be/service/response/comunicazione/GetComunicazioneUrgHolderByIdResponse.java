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
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;

/**
 * Response for reading Comunicazione by its id.
 */
public class GetComunicazioneUrgHolderByIdResponse extends BaseGetResponse<ComunicazioneUrgHolder> {

private ComunicazioneUrgHolder comunicazioneUrgHolder;

	
	
	@Override
	protected ComunicazioneUrgHolder getEntity() {
		return comunicazioneUrgHolder;
	}



	public ComunicazioneUrgHolder getComunicazioneUrgHolder() {
		return comunicazioneUrgHolder;
	}



	public void setComunicazioneUrgHolder(ComunicazioneUrgHolder comunicazioneUrgHolder) {
		this.comunicazioneUrgHolder = comunicazioneUrgHolder;
	}

}
