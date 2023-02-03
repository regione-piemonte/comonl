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
import java.math.BigDecimal;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class ModelliPfi.
 */
public class ModelliPfi extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private BigDecimal idComDCapacitaFormModA;
	private BigDecimal idComDCapacitaFormModB;

	/**
	 * @return the idComDCapacitaFormModA
	 */
	public BigDecimal getIdComDCapacitaFormModA() {
		return idComDCapacitaFormModA;
	}
	
	/**
	 * @param idComDCapacitaFormModA the idComDCapacitaFormModA to set
	 */
	public void setIdComDCapacitaFormModA(BigDecimal idComDCapacitaFormModA) {
		this.idComDCapacitaFormModA = idComDCapacitaFormModA;
	}

	/**
	 * @return the idComDCapacitaFormModB
	 */
	public BigDecimal getIdComDCapacitaFormModB() {
		return idComDCapacitaFormModB;
	}
	
	/**
	 * @param idComDCapacitaFormModB the idComDCapacitaFormModB to set
	 */
	public void setIdComDCapacitaFormModB(BigDecimal idComDCapacitaFormModB) {
		this.idComDCapacitaFormModB = idComDCapacitaFormModB;
	}

}
