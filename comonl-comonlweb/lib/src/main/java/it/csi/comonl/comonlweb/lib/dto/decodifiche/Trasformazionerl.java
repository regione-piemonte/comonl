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
 * The Class Trasformazionerl.
 */
public class Trasformazionerl extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTrasformazionirlMin;
	private String dsComTTrasformazionerl;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String tipo;

	/**
	 * @return the codTrasformazionirlMin
	 */
	public String getCodTrasformazionirlMin() {
		return codTrasformazionirlMin;
	}
	
	/**
	 * @param codTrasformazionirlMin the codTrasformazionirlMin to set
	 */
	public void setCodTrasformazionirlMin(String codTrasformazionirlMin) {
		this.codTrasformazionirlMin = codTrasformazionirlMin;
	}

	/**
	 * @return the dsComTTrasformazionerl
	 */
	public String getDsComTTrasformazionerl() {
		return dsComTTrasformazionerl;
	}
	
	/**
	 * @param dsComTTrasformazionerl the dsComTTrasformazionerl to set
	 */
	public void setDsComTTrasformazionerl(String dsComTTrasformazionerl) {
		this.dsComTTrasformazionerl = dsComTTrasformazionerl;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
