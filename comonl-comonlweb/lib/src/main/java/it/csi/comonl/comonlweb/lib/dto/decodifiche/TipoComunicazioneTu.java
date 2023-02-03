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
 * The Class TipoComunicazioneTu.
 */
public class TipoComunicazioneTu extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTipoComunicazioneMin;
	private String dsComTTipoComunicazione;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;

	/**
	 * @return the codTipoComunicazioneMin
	 */
	public String getCodTipoComunicazioneMin() {
		return codTipoComunicazioneMin;
	}
	
	/**
	 * @param codTipoComunicazioneMin the codTipoComunicazioneMin to set
	 */
	public void setCodTipoComunicazioneMin(String codTipoComunicazioneMin) {
		this.codTipoComunicazioneMin = codTipoComunicazioneMin;
	}

	/**
	 * @return the dsComTTipoComunicazione
	 */
	public String getDsComTTipoComunicazione() {
		return dsComTTipoComunicazione;
	}
	
	/**
	 * @param dsComTTipoComunicazione the dsComTTipoComunicazione to set
	 */
	public void setDsComTTipoComunicazione(String dsComTTipoComunicazione) {
		this.dsComTTipoComunicazione = dsComTTipoComunicazione;
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
