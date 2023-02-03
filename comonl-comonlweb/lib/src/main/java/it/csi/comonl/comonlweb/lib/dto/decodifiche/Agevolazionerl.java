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
 * The Class Agevolazionerl.
 */
public class Agevolazionerl extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codAgevolazionerlMin;
	private String dsComTAgevolazionerl;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;

	/**
	 * @return the codAgevolazionerlMin
	 */
	public String getCodAgevolazionerlMin() {
		return codAgevolazionerlMin;
	}
	
	/**
	 * @param codAgevolazionerlMin the codAgevolazionerlMin to set
	 */
	public void setCodAgevolazionerlMin(String codAgevolazionerlMin) {
		this.codAgevolazionerlMin = codAgevolazionerlMin;
	}

	/**
	 * @return the dsComTAgevolazionerl
	 */
	public String getDsComTAgevolazionerl() {
		return dsComTAgevolazionerl;
	}
	
	/**
	 * @param dsComTAgevolazionerl the dsComTAgevolazionerl to set
	 */
	public void setDsComTAgevolazionerl(String dsComTAgevolazionerl) {
		this.dsComTAgevolazionerl = dsComTAgevolazionerl;
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
