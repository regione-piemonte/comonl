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
package it.csi.comonl.comonlweb.lib.dto.decodifiche;

import java.io.Serializable;
import java.util.Date;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class UnitaMisuraDurata.
 */
public class UnitaMisuraDurata extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dFine;
	private Date dInizio;
	private String descrUnitaMisuraDurata;

	/**
	 * @return the dFine
	 */
	public Date getDFine() {
		return dFine;
	}
	
	/**
	 * @param dFine the dFine to set
	 */
	public void setDFine(Date dFine) {
		this.dFine = dFine;
	}

	/**
	 * @return the dInizio
	 */
	public Date getDInizio() {
		return dInizio;
	}
	
	/**
	 * @param dInizio the dInizio to set
	 */
	public void setDInizio(Date dInizio) {
		this.dInizio = dInizio;
	}

	/**
	 * @return the descrUnitaMisuraDurata
	 */
	public String getDescrUnitaMisuraDurata() {
		return descrUnitaMisuraDurata;
	}
	
	/**
	 * @param descrUnitaMisuraDurata the descrUnitaMisuraDurata to set
	 */
	public void setDescrUnitaMisuraDurata(String descrUnitaMisuraDurata) {
		this.descrUnitaMisuraDurata = descrUnitaMisuraDurata;
	}

}
