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
 * The Class Provincia.
 */
public class Provincia extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codProvinciaMin;
	private String codiceFiscalePv;
	private String dsComTProvincia;
	private String dsTarga;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private Regione regione;

	/**
	 * @return the codProvinciaMin
	 */
	public String getCodProvinciaMin() {
		return codProvinciaMin;
	}
	
	/**
	 * @param codProvinciaMin the codProvinciaMin to set
	 */
	public void setCodProvinciaMin(String codProvinciaMin) {
		this.codProvinciaMin = codProvinciaMin;
	}

	/**
	 * @return the codiceFiscalePv
	 */
	public String getCodiceFiscalePv() {
		return codiceFiscalePv;
	}
	
	/**
	 * @param codiceFiscalePv the codiceFiscalePv to set
	 */
	public void setCodiceFiscalePv(String codiceFiscalePv) {
		this.codiceFiscalePv = codiceFiscalePv;
	}

	/**
	 * @return the dsComTProvincia
	 */
	public String getDsComTProvincia() {
		return dsComTProvincia;
	}
	
	/**
	 * @param dsComTProvincia the dsComTProvincia to set
	 */
	public void setDsComTProvincia(String dsComTProvincia) {
		this.dsComTProvincia = dsComTProvincia;
	}

	/**
	 * @return the dsTarga
	 */
	public String getDsTarga() {
		return dsTarga;
	}
	
	/**
	 * @param dsTarga the dsTarga to set
	 */
	public void setDsTarga(String dsTarga) {
		this.dsTarga = dsTarga;
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
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}
	
	/**
	 * @param regione the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}

}
