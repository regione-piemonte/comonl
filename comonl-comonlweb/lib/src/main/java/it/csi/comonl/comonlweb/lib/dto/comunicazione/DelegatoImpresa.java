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
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class DelegatoImpresa.
 */
public class DelegatoImpresa extends BaseDto<DelegatoImpresaPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dtFineCarica;
	private String flgScuola;
	private String idCaricaPersonaPv;
	private String pv;
	private AnagraficaDelegato anagraficaDelegato;
	private AnagraficaDatore anagraficaDatore;

	private String denominazione;
	private Date dataAnnullamento;

	/**
	 * @return the dtFineCarica
	 */
	public Date getDtFineCarica() {
		return dtFineCarica;
	}

	/**
	 * @param dtFineCarica the dtFineCarica to set
	 */
	public void setDtFineCarica(Date dtFineCarica) {
		this.dtFineCarica = dtFineCarica;
	}

	/**
	 * @return the flgScuola
	 */
	public String getFlgScuola() {
		return flgScuola;
	}

	/**
	 * @param flgScuola the flgScuola to set
	 */
	public void setFlgScuola(String flgScuola) {
		this.flgScuola = flgScuola;
	}

	/**
	 * @return the idCaricaPersonaPv
	 */
	public String getIdCaricaPersonaPv() {
		return idCaricaPersonaPv;
	}

	/**
	 * @param idCaricaPersonaPv the idCaricaPersonaPv to set
	 */
	public void setIdCaricaPersonaPv(String idCaricaPersonaPv) {
		this.idCaricaPersonaPv = idCaricaPersonaPv;
	}

	/**
	 * @return the pv
	 */
	public String getPv() {
		return pv;
	}

	/**
	 * @param pv the pv to set
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	/**
	 * @return the anagraficaDelegato
	 */
	public AnagraficaDelegato getAnagraficaDelegato() {
		return anagraficaDelegato;
	}

	/**
	 * @param anagraficaDelegato the anagraficaDelegato to set
	 */
	public void setAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		this.anagraficaDelegato = anagraficaDelegato;
	}

	/**
	 * @return the anagraficaDatore
	 */
	public AnagraficaDatore getAnagraficaDatore() {
		return anagraficaDatore;
	}

	/**
	 * @param anagraficaDatore the anagraficaDatore to set
	 */
	public void setAnagraficaDatore(AnagraficaDatore anagraficaDatore) {
		this.anagraficaDatore = anagraficaDatore;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

}
