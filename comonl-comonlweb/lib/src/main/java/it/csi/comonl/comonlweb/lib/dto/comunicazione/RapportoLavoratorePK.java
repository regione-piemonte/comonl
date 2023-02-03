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

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class RapportoLavoratorePK.
 */
public class RapportoLavoratorePK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long idComDLavoratore;
	private long idComDRapporto;

	/**
	 * @return the idComDLavoratore
	 */
	public long getIdComDLavoratore() {
		return idComDLavoratore;
	}
	
	/**
	 * @param idComDLavoratore the idComDLavoratore to set
	 */
	public void setIdComDLavoratore(long idComDLavoratore) {
		this.idComDLavoratore = idComDLavoratore;
	}

	/**
	 * @return the idComDRapporto
	 */
	public long getIdComDRapporto() {
		return idComDRapporto;
	}
	
	/**
	 * @param idComDRapporto the idComDRapporto to set
	 */
	public void setIdComDRapporto(long idComDRapporto) {
		this.idComDRapporto = idComDRapporto;
	}

}
