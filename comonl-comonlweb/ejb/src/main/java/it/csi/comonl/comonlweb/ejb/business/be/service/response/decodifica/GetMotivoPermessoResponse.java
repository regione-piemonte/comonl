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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import java.util.ArrayList;
import java.util.List;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;

/**
 * The Class GetProvinciaResponse.
 */
public class GetMotivoPermessoResponse extends BaseGetResponse<List<MotivoPermesso>> {

	private List<MotivoPermesso> motivoPermessos= new ArrayList<>();

	public List<MotivoPermesso> getMotivoPermessos() {
		return motivoPermessos;
	}

	public void setMotivoPermessos(List<MotivoPermesso> motivoPermessos) {
		this.motivoPermessos = motivoPermessos;
	}

	@Override
	protected List<MotivoPermesso> getEntity() {
		return motivoPermessos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [motivoPermessos=").append(motivoPermessos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
