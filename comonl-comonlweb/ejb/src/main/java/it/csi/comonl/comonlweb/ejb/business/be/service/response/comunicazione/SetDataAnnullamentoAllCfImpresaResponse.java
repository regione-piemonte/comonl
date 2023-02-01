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

import java.util.Date;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoAllCfImpresaRes;

/**
 * The Class PostAnagraficaDelegatoResponse.
 */
public class SetDataAnnullamentoAllCfImpresaResponse extends BaseGetResponse<SetDataAnnullamentoAllCfImpresaRes> {

	private SetDataAnnullamentoAllCfImpresaRes setDataAnnullamentoAllCfImpresaRes;
	
	

	@Override
	protected SetDataAnnullamentoAllCfImpresaRes getEntity() {
		return setDataAnnullamentoAllCfImpresaRes;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [SetDataAnnullamentoAllCfImpresaResponse=").append(setDataAnnullamentoAllCfImpresaRes).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public SetDataAnnullamentoAllCfImpresaRes getSetDataAnnullamentoAllCfImpresaRes() {
		return setDataAnnullamentoAllCfImpresaRes;
	}


	public void setSetDataAnnullamentoAllCfImpresaRes(
			SetDataAnnullamentoAllCfImpresaRes setDataAnnullamentoAllCfImpresaRes) {
		this.setDataAnnullamentoAllCfImpresaRes = setDataAnnullamentoAllCfImpresaRes;
	}


	
	

}
