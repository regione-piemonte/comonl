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
 * The Class TipoContratti.
 */
public class TipoContratti extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTipoContrattoMin;
	private String dsComTTipoContratto;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String flgForma;
	private String status;
	private String tipo;
	private TipologiaRapporto tipologiaRapporto;

	/**
	 * @return the codTipoContrattoMin
	 */
	public String getCodTipoContrattoMin() {
		return codTipoContrattoMin;
	}
	
	/**
	 * @param codTipoContrattoMin the codTipoContrattoMin to set
	 */
	public void setCodTipoContrattoMin(String codTipoContrattoMin) {
		this.codTipoContrattoMin = codTipoContrattoMin;
	}

	/**
	 * @return the dsComTTipoContratto
	 */
	public String getDsComTTipoContratto() {
		return dsComTTipoContratto;
	}
	
	/**
	 * @param dsComTTipoContratto the dsComTTipoContratto to set
	 */
	public void setDsComTTipoContratto(String dsComTTipoContratto) {
		this.dsComTTipoContratto = dsComTTipoContratto;
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
	 * @return the flgForma
	 */
	public String getFlgForma() {
		return flgForma;
	}
	
	/**
	 * @param flgForma the flgForma to set
	 */
	public void setFlgForma(String flgForma) {
		this.flgForma = flgForma;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/**
	 * @return the tipologiaRapporto
	 */
	public TipologiaRapporto getTipologiaRapporto() {
		return tipologiaRapporto;
	}
	
	/**
	 * @param tipologiaRapporto the tipologiaRapporto to set
	 */
	public void setTipologiaRapporto(TipologiaRapporto tipologiaRapporto) {
		this.tipologiaRapporto = tipologiaRapporto;
	}

}
