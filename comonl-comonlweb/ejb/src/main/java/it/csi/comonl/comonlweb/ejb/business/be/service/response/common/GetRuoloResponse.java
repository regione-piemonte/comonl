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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.common;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

/**
 * The Class GetRuoloResponse.
 */
public class GetRuoloResponse extends BaseGetResponse<List<Ruolo>> {

	/** The model. */
	private List<Ruolo> ruolos = new ArrayList<Ruolo>();

	/**
	 * @return the ruolos
	 */
	public List<Ruolo> getRuolos() {
		return ruolos;
	}

	/**
	 * @param ruolos the ruolos to set
	 */
	public void setRuolos(List<Ruolo> ruolos) {
		this.ruolos = ruolos;
	}

	@Override
	protected List<Ruolo> getEntity() {
		return ruolos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetRuoloResponse [ruolos=").append(ruolos).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

}
