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
 * The Class TipoContrPeriodi.
 */
public class TipoContrPeriodi extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dtNonValidA;
	private Date dtNonValidDa;
	private TipoContratti tipoContratti;

	/**
	 * @return the dtNonValidA
	 */
	public Date getDtNonValidA() {
		return dtNonValidA;
	}
	
	/**
	 * @param dtNonValidA the dtNonValidA to set
	 */
	public void setDtNonValidA(Date dtNonValidA) {
		this.dtNonValidA = dtNonValidA;
	}

	/**
	 * @return the dtNonValidDa
	 */
	public Date getDtNonValidDa() {
		return dtNonValidDa;
	}
	
	/**
	 * @param dtNonValidDa the dtNonValidDa to set
	 */
	public void setDtNonValidDa(Date dtNonValidDa) {
		this.dtNonValidDa = dtNonValidDa;
	}

	/**
	 * @return the tipoContratti
	 */
	public TipoContratti getTipoContratti() {
		return tipoContratti;
	}
	
	/**
	 * @param tipoContratti the tipoContratti to set
	 */
	public void setTipoContratti(TipoContratti tipoContratti) {
		this.tipoContratti = tipoContratti;
	}

}
