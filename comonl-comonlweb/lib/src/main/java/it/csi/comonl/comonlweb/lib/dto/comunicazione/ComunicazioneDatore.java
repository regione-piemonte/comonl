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
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDatore;

/**
 * The Class ComunicazioneDatore.
 */
public class ComunicazioneDatore extends BaseDto<ComunicazioneDatorePK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Comunicazione comunicazione;
	private Datore datore;
	private TipoDatore tipoDatore;

	/**
	 * @return the comunicazione
	 */
	public Comunicazione getComunicazione() {
		return comunicazione;
	}
	
	/**
	 * @param comunicazione the comunicazione to set
	 */
	public void setComunicazione(Comunicazione comunicazione) {
		this.comunicazione = comunicazione;
	}

	/**
	 * @return the datore
	 */
	public Datore getDatore() {
		return datore;
	}
	
	/**
	 * @param datore the datore to set
	 */
	public void setDatore(Datore datore) {
		this.datore = datore;
	}

	/**
	 * @return the tipoDatore
	 */
	public TipoDatore getTipoDatore() {
		return tipoDatore;
	}
	
	/**
	 * @param tipoDatore the tipoDatore to set
	 */
	public void setTipoDatore(TipoDatore tipoDatore) {
		this.tipoDatore = tipoDatore;
	}

}
