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
import java.math.BigDecimal;
import java.util.Date;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class Cessazionerl.
 */
public class Cessazionerl extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCessazioneMin;
	private String dsComTCessazionerl;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private BigDecimal idNewCessazionerl;

	/**
	 * @return the codCessazioneMin
	 */
	public String getCodCessazioneMin() {
		return codCessazioneMin;
	}
	
	/**
	 * @param codCessazioneMin the codCessazioneMin to set
	 */
	public void setCodCessazioneMin(String codCessazioneMin) {
		this.codCessazioneMin = codCessazioneMin;
	}

	/**
	 * @return the dsComTCessazionerl
	 */
	public String getDsComTCessazionerl() {
		return dsComTCessazionerl;
	}
	
	/**
	 * @param dsComTCessazionerl the dsComTCessazionerl to set
	 */
	public void setDsComTCessazionerl(String dsComTCessazionerl) {
		this.dsComTCessazionerl = dsComTCessazionerl;
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

	/**
	 * @return the idNewCessazionerl
	 */
	public BigDecimal getIdNewCessazionerl() {
		return idNewCessazionerl;
	}
	
	/**
	 * @param idNewCessazionerl the idNewCessazionerl to set
	 */
	public void setIdNewCessazionerl(BigDecimal idNewCessazionerl) {
		this.idNewCessazionerl = idNewCessazionerl;
	}

}
