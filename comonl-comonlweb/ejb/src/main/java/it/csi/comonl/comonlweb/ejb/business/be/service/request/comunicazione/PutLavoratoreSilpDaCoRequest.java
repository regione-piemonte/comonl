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
package it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione;

import it.csi.comonl.comonlweb.ejb.business.be.service.request.base.BaseRequest;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperLavoratoreSilpEspanso;

/**
 * The Class PostComunicazioneRequest.
 */
public class PutLavoratoreSilpDaCoRequest implements BaseRequest {

	private final WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso;
	
	private final Boolean flgRettifica;

	/**
	 * Constructor
	 */
	public PutLavoratoreSilpDaCoRequest(WrapperLavoratoreSilpEspanso wrapperLavoratoreSilpEspanso,Boolean flgRettifica) {
		this.wrapperLavoratoreSilpEspanso = wrapperLavoratoreSilpEspanso;
		this.flgRettifica = flgRettifica;
	}

	public WrapperLavoratoreSilpEspanso getWrapperLavoratoreSilpEspanso() {
		return wrapperLavoratoreSilpEspanso;
	}


	public Boolean getFlgRettifica() {
		return flgRettifica;
	}
	
	

	
}
