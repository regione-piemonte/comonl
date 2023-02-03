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
 * The Class CategLavAssObbl.
 */
public class CategLavAssObbl extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCategLavAssObblMin;
	private String codNormAss68Min;
	private String dsComTCategLavAssObbl;
	private Date dtFine;
	private Date dtInizio;
	private String flgObbligatorioBloccoL68;
	private String normaRifCategLavAssObbl;
	private BigDecimal ordinamento;

	/**
	 * @return the codCategLavAssObblMin
	 */
	public String getCodCategLavAssObblMin() {
		return codCategLavAssObblMin;
	}
	
	/**
	 * @param codCategLavAssObblMin the codCategLavAssObblMin to set
	 */
	public void setCodCategLavAssObblMin(String codCategLavAssObblMin) {
		this.codCategLavAssObblMin = codCategLavAssObblMin;
	}

	/**
	 * @return the codNormAss68Min
	 */
	public String getCodNormAss68Min() {
		return codNormAss68Min;
	}
	
	/**
	 * @param codNormAss68Min the codNormAss68Min to set
	 */
	public void setCodNormAss68Min(String codNormAss68Min) {
		this.codNormAss68Min = codNormAss68Min;
	}

	/**
	 * @return the dsComTCategLavAssObbl
	 */
	public String getDsComTCategLavAssObbl() {
		return dsComTCategLavAssObbl;
	}
	
	/**
	 * @param dsComTCategLavAssObbl the dsComTCategLavAssObbl to set
	 */
	public void setDsComTCategLavAssObbl(String dsComTCategLavAssObbl) {
		this.dsComTCategLavAssObbl = dsComTCategLavAssObbl;
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
	 * @return the flgObbligatorioBloccoL68
	 */
	public String getFlgObbligatorioBloccoL68() {
		return flgObbligatorioBloccoL68;
	}
	
	/**
	 * @param flgObbligatorioBloccoL68 the flgObbligatorioBloccoL68 to set
	 */
	public void setFlgObbligatorioBloccoL68(String flgObbligatorioBloccoL68) {
		this.flgObbligatorioBloccoL68 = flgObbligatorioBloccoL68;
	}

	/**
	 * @return the normaRifCategLavAssObbl
	 */
	public String getNormaRifCategLavAssObbl() {
		return normaRifCategLavAssObbl;
	}
	
	/**
	 * @param normaRifCategLavAssObbl the normaRifCategLavAssObbl to set
	 */
	public void setNormaRifCategLavAssObbl(String normaRifCategLavAssObbl) {
		this.normaRifCategLavAssObbl = normaRifCategLavAssObbl;
	}

	/**
	 * @return the ordinamento
	 */
	public BigDecimal getOrdinamento() {
		return ordinamento;
	}
	
	/**
	 * @param ordinamento the ordinamento to set
	 */
	public void setOrdinamento(BigDecimal ordinamento) {
		this.ordinamento = ordinamento;
	}

}
