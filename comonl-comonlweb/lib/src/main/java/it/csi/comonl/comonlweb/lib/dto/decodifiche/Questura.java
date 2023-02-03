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
 * The Class Questura.
 */
public class Questura extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codQuesturaMin;
	private String dsQuestura;
	private Date dtFine;
	private Date dtInizio;

	/**
	 * @return the codQuesturaMin
	 */
	public String getCodQuesturaMin() {
		return codQuesturaMin;
	}
	
	/**
	 * @param codQuesturaMin the codQuesturaMin to set
	 */
	public void setCodQuesturaMin(String codQuesturaMin) {
		this.codQuesturaMin = codQuesturaMin;
	}

	/**
	 * @return the dsQuestura
	 */
	public String getDsQuestura() {
		return dsQuestura;
	}
	
	/**
	 * @param dsQuestura the dsQuestura to set
	 */
	public void setDsQuestura(String dsQuestura) {
		this.dsQuestura = dsQuestura;
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
