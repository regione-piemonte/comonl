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
 * The Class TipoEntePromTirocinio.
 */
public class TipoEntePromTirocinio extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codiceEntePromTirocinioMin;
	private String dsComTTipoEntePromTir;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String tipSigla;

	/**
	 * @return the codiceEntePromTirocinioMin
	 */
	public String getCodiceEntePromTirocinioMin() {
		return codiceEntePromTirocinioMin;
	}
	
	/**
	 * @param codiceEntePromTirocinioMin the codiceEntePromTirocinioMin to set
	 */
	public void setCodiceEntePromTirocinioMin(String codiceEntePromTirocinioMin) {
		this.codiceEntePromTirocinioMin = codiceEntePromTirocinioMin;
	}

	/**
	 * @return the dsComTTipoEntePromTir
	 */
	public String getDsComTTipoEntePromTir() {
		return dsComTTipoEntePromTir;
	}
	
	/**
	 * @param dsComTTipoEntePromTir the dsComTTipoEntePromTir to set
	 */
	public void setDsComTTipoEntePromTir(String dsComTTipoEntePromTir) {
		this.dsComTTipoEntePromTir = dsComTTipoEntePromTir;
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
	 * @return the tipSigla
	 */
	public String getTipSigla() {
		return tipSigla;
	}
	
	/**
	 * @param tipSigla the tipSigla to set
	 */
	public void setTipSigla(String tipSigla) {
		this.tipSigla = tipSigla;
	}

}
