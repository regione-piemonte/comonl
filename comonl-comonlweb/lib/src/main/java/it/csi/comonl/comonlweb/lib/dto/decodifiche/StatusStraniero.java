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
 * The Class StatusStraniero.
 */
public class StatusStraniero extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codStatusMin;
	private String dsComTStatusStraniero;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;

	/**
	 * @return the codStatusMin
	 */
	public String getCodStatusMin() {
		return codStatusMin;
	}
	
	/**
	 * @param codStatusMin the codStatusMin to set
	 */
	public void setCodStatusMin(String codStatusMin) {
		this.codStatusMin = codStatusMin;
	}

	/**
	 * @return the dsComTStatusStraniero
	 */
	public String getDsComTStatusStraniero() {
		return dsComTStatusStraniero;
	}
	
	/**
	 * @param dsComTStatusStraniero the dsComTStatusStraniero to set
	 */
	public void setDsComTStatusStraniero(String dsComTStatusStraniero) {
		this.dsComTStatusStraniero = dsComTStatusStraniero;
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
