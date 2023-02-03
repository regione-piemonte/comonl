/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;

import it.csi.comonl.comonlweb.lib.dto.BaseWrapper;

/**
 * The Class WrapperRapporto.
 */
public class WrapperRapporto extends BaseWrapper implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Rapporto rapporto;
	
	public Rapporto getRapporto() {
		return rapporto;
	}
	public void setRapporto(Rapporto rapporto) {
		this.rapporto = rapporto;
	}
	

}
