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
 * The Class RapportoLavoratore.
 */
public class SedeLavoroLavoratore extends BaseDto<SedeLavoroLavoratorePK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Lavoratore lavoratore;
	private SedeLavoro sedeLavoro;

	/**
	 * @return the lavoratore
	 */
	public Lavoratore getLavoratore() {
		return lavoratore;
	}
	
	/**
	 * @param lavoratore the lavoratore to set
	 */
	public void setLavoratore(Lavoratore lavoratore) {
		this.lavoratore = lavoratore;
	}

	public SedeLavoro getSedeLavoro() {
		return sedeLavoro;
	}

	public void setSedeLavoro(SedeLavoro sedeLavoro) {
		this.sedeLavoro = sedeLavoro;
	}



}
