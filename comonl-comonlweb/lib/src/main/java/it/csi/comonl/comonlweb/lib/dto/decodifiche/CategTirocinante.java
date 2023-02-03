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
 * The Class CategTirocinante.
 */
public class CategTirocinante extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCategTirocinanteMin;
	private String descrCategTirocinante;
	private Date dtFine;
	private Date dtInizio;

	/**
	 * @return the codCategTirocinanteMin
	 */
	public String getCodCategTirocinanteMin() {
		return codCategTirocinanteMin;
	}
	
	/**
	 * @param codCategTirocinanteMin the codCategTirocinanteMin to set
	 */
	public void setCodCategTirocinanteMin(String codCategTirocinanteMin) {
		this.codCategTirocinanteMin = codCategTirocinanteMin;
	}

	/**
	 * @return the descrCategTirocinante
	 */
	public String getDescrCategTirocinante() {
		return descrCategTirocinante;
	}
	
	/**
	 * @param descrCategTirocinante the descrCategTirocinante to set
	 */
	public void setDescrCategTirocinante(String descrCategTirocinante) {
		this.descrCategTirocinante = descrCategTirocinante;
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

}
