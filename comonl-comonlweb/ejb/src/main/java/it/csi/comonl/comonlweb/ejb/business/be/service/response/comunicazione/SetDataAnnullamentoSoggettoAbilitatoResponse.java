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


import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoSoggettoAbilitatoRes;

/**
 * The Class SetDataAnnullamentoSoggettoAbilitatoResponse.
 */
public class SetDataAnnullamentoSoggettoAbilitatoResponse extends BaseGetResponse<SetDataAnnullamentoSoggettoAbilitatoRes> {

	private SetDataAnnullamentoSoggettoAbilitatoRes setDataAnnullamentoSoggettoAbilitatoRes;
	
	

	@Override
	protected SetDataAnnullamentoSoggettoAbilitatoRes getEntity() {
		return setDataAnnullamentoSoggettoAbilitatoRes;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [SetDataAnnullamentoSoggettoAbilitatoResponse=").append(setDataAnnullamentoSoggettoAbilitatoRes).append(", apiErrors=")
				.append(getApiErrors()).append("]");
		return builder.toString();
	}


	public SetDataAnnullamentoSoggettoAbilitatoRes getSetDataAnnullamentoSoggettoAbilitatoRes() {
		return setDataAnnullamentoSoggettoAbilitatoRes;
	}


	public void setSetDataAnnullamentoSoggettoAbilitatoRes(
			SetDataAnnullamentoSoggettoAbilitatoRes setDataAnnullamentoSoggettoAbilitatoRes) {
		this.setDataAnnullamentoSoggettoAbilitatoRes = setDataAnnullamentoSoggettoAbilitatoRes;
	}


	
	
}
