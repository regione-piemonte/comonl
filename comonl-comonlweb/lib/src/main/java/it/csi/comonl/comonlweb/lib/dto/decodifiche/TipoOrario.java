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
 * The Class TipoOrario.
 */
public class TipoOrario extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTipoorarioMin;
	private String dsComTTipoOrario;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private BigDecimal idNewTipoorario;

	/**
	 * @return the codTipoorarioMin
	 */
	public String getCodTipoorarioMin() {
		return codTipoorarioMin;
	}
	
	/**
	 * @param codTipoorarioMin the codTipoorarioMin to set
	 */
	public void setCodTipoorarioMin(String codTipoorarioMin) {
		this.codTipoorarioMin = codTipoorarioMin;
	}

	/**
	 * @return the dsComTTipoOrario
	 */
	public String getDsComTTipoOrario() {
		return dsComTTipoOrario;
	}
	
	/**
	 * @param dsComTTipoOrario the dsComTTipoOrario to set
	 */
	public void setDsComTTipoOrario(String dsComTTipoOrario) {
		this.dsComTTipoOrario = dsComTTipoOrario;
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
	 * @return the idNewTipoorario
	 */
	public BigDecimal getIdNewTipoorario() {
		return idNewTipoorario;
	}
	
	/**
	 * @param idNewTipoorario the idNewTipoorario to set
	 */
	public void setIdNewTipoorario(BigDecimal idNewTipoorario) {
		this.idNewTipoorario = idNewTipoorario;
	}

}
