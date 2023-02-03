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

/**
 * The Class DichiarazioniUd.
 */
public class DichiarazioniUd extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String flgDatLavConiugeLavoratore;
	private String flgDatLavConvLavoratore;
	private String flgDatLavInvalido;
	private String flgDatLavParenteLavoratore;
	private Comunicazione comunicazione;

	/**
	 * @return the flgDatLavConiugeLavoratore
	 */
	public String getFlgDatLavConiugeLavoratore() {
		return flgDatLavConiugeLavoratore;
	}
	
	/**
	 * @param flgDatLavConiugeLavoratore the flgDatLavConiugeLavoratore to set
	 */
	public void setFlgDatLavConiugeLavoratore(String flgDatLavConiugeLavoratore) {
		this.flgDatLavConiugeLavoratore = flgDatLavConiugeLavoratore;
	}

	/**
	 * @return the flgDatLavConvLavoratore
	 */
	public String getFlgDatLavConvLavoratore() {
		return flgDatLavConvLavoratore;
	}
	
	/**
	 * @param flgDatLavConvLavoratore the flgDatLavConvLavoratore to set
	 */
	public void setFlgDatLavConvLavoratore(String flgDatLavConvLavoratore) {
		this.flgDatLavConvLavoratore = flgDatLavConvLavoratore;
	}

	/**
	 * @return the flgDatLavInvalido
	 */
	public String getFlgDatLavInvalido() {
		return flgDatLavInvalido;
	}
	
	/**
	 * @param flgDatLavInvalido the flgDatLavInvalido to set
	 */
	public void setFlgDatLavInvalido(String flgDatLavInvalido) {
		this.flgDatLavInvalido = flgDatLavInvalido;
	}

	/**
	 * @return the flgDatLavParenteLavoratore
	 */
	public String getFlgDatLavParenteLavoratore() {
		return flgDatLavParenteLavoratore;
	}
	
	/**
	 * @param flgDatLavParenteLavoratore the flgDatLavParenteLavoratore to set
	 */
	public void setFlgDatLavParenteLavoratore(String flgDatLavParenteLavoratore) {
		this.flgDatLavParenteLavoratore = flgDatLavParenteLavoratore;
	}

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

}
