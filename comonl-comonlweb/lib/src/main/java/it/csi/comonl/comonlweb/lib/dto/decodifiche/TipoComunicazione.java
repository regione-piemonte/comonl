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
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class TipoComunicazione.
 */
public class TipoComunicazione extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsComTTipoComunicazione;
	private String tipo;
	private BigDecimal validitaAgInt;
	private BigDecimal validitaCpi;

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
	 * @return the validitaAgInt
	 */
	public BigDecimal getValiditaAgInt() {
		return validitaAgInt;
	}
	
	/**
	 * @param validitaAgInt the validitaAgInt to set
	 */
	public void setValiditaAgInt(BigDecimal validitaAgInt) {
		this.validitaAgInt = validitaAgInt;
	}

	/**
	 * @return the validitaCpi
	 */
	public BigDecimal getValiditaCpi() {
		return validitaCpi;
	}
	
	/**
	 * @param validitaCpi the validitaCpi to set
	 */
	public void setValiditaCpi(BigDecimal validitaCpi) {
		this.validitaCpi = validitaCpi;
	}

}
