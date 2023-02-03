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
 * The Class EntePrevidenziale.
 */
public class EntePrevidenziale extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codEntePrevidenzialeMin;
	private String dsComTEntePrevidenziale;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;

	/**
	 * @return the codEntePrevidenzialeMin
	 */
	public String getCodEntePrevidenzialeMin() {
		return codEntePrevidenzialeMin;
	}
	
	/**
	 * @param codEntePrevidenzialeMin the codEntePrevidenzialeMin to set
	 */
	public void setCodEntePrevidenzialeMin(String codEntePrevidenzialeMin) {
		this.codEntePrevidenzialeMin = codEntePrevidenzialeMin;
	}

	/**
	 * @return the dsComTEntePrevidenziale
	 */
	public String getDsComTEntePrevidenziale() {
		return dsComTEntePrevidenziale;
	}
	
	/**
	 * @param dsComTEntePrevidenziale the dsComTEntePrevidenziale to set
	 */
	public void setDsComTEntePrevidenziale(String dsComTEntePrevidenziale) {
		this.dsComTEntePrevidenziale = dsComTEntePrevidenziale;
	}

	/**
	 * @return the dtFine
	 */
	public Date getDtFine() {
		return dtFine;
	}
	
	/**
	 * @param dtFine the dtFine to set
	 */
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	/**
	 * @return the dtInizio
	 */
	public Date getDtInizio() {
		return dtInizio;
	}
	
	/**
	 * @param dtInizio the dtInizio to set
	 */
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

	/**
	 * @return the dtTmst
	 */
	public Date getDtTmst() {
		return dtTmst;
	}
	
	/**
	 * @param dtTmst the dtTmst to set
	 */
	public void setDtTmst(Date dtTmst) {
		this.dtTmst = dtTmst;
	}

}
