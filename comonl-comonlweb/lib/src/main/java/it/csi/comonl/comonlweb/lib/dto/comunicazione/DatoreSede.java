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
 * The Class DatoreSede.
 */
public class DatoreSede extends BaseDto<DatoreSedePK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Datore datore;
	private SedeLavoro sedeLavoro;
	public Datore getDatore() {
		return datore;
	}
	public void setDatore(Datore datore) {
		this.datore = datore;
	}
	public SedeLavoro getSedeLavoro() {
		return sedeLavoro;
	}
	public void setSedeLavoro(SedeLavoro sedeLavoro) {
		this.sedeLavoro = sedeLavoro;
	}
	
	
}
