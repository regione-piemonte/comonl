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
 * The Class Cittadinanza.
 */
public class Cittadinanza extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCittadinanzaMin;
	private String codCittadinanzaOld;
	private String codMf;
	private String dsComTCittadinanza;
	private String dsNazione;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String flgUe;

	/**
	 * @return the codCittadinanzaMin
	 */
	public String getCodCittadinanzaMin() {
		return codCittadinanzaMin;
	}
	
	/**
	 * @param codCittadinanzaMin the codCittadinanzaMin to set
	 */
	public void setCodCittadinanzaMin(String codCittadinanzaMin) {
		this.codCittadinanzaMin = codCittadinanzaMin;
	}

	/**
	 * @return the codCittadinanzaOld
	 */
	public String getCodCittadinanzaOld() {
		return codCittadinanzaOld;
	}
	
	/**
	 * @param codCittadinanzaOld the codCittadinanzaOld to set
	 */
	public void setCodCittadinanzaOld(String codCittadinanzaOld) {
		this.codCittadinanzaOld = codCittadinanzaOld;
	}

	/**
	 * @return the codMf
	 */
	public String getCodMf() {
		return codMf;
	}
	
	/**
	 * @param codMf the codMf to set
	 */
	public void setCodMf(String codMf) {
		this.codMf = codMf;
	}

	/**
	 * @return the dsComTCittadinanza
	 */
	public String getDsComTCittadinanza() {
		return dsComTCittadinanza;
	}
	
	/**
	 * @param dsComTCittadinanza the dsComTCittadinanza to set
	 */
	public void setDsComTCittadinanza(String dsComTCittadinanza) {
		this.dsComTCittadinanza = dsComTCittadinanza;
	}

	/**
	 * @return the dsNazione
	 */
	public String getDsNazione() {
		return dsNazione;
	}
	
	/**
	 * @param dsNazione the dsNazione to set
	 */
	public void setDsNazione(String dsNazione) {
		this.dsNazione = dsNazione;
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
	 * @return the flgUe
	 */
	public String getFlgUe() {
		return flgUe;
	}
	
	/**
	 * @param flgUe the flgUe to set
	 */
	public void setFlgUe(String flgUe) {
		this.flgUe = flgUe;
	}

}
