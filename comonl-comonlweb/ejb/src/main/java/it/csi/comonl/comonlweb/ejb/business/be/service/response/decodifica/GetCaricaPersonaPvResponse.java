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
package it.csi.comonl.comonlweb.ejb.business.be.service.response.decodifica;

import java.util.ArrayList;
import java.util.List;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.base.BaseGetResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CaricaPersonaPv;

/**
 * The Class GetProvinciaResponse.
 */
public class GetCaricaPersonaPvResponse extends BaseGetResponse<List<CaricaPersonaPv>> {

	private List<CaricaPersonaPv> caricaPersonaPvs= new ArrayList<>();


	@Override
	protected List<CaricaPersonaPv> getEntity() {
		return caricaPersonaPvs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " [caricaPersonaPvs=").append(caricaPersonaPvs).append(", apiErrors=").append(getApiErrors())
				.append("]");
		return builder.toString();
	}

	public List<CaricaPersonaPv> getCaricaPersonaPvs() {
		return caricaPersonaPvs;
	}

	public void setCaricaPersonaPvs(List<CaricaPersonaPv> caricaPersonaPvs) {
		this.caricaPersonaPvs = caricaPersonaPvs;
	}
	
	

}
