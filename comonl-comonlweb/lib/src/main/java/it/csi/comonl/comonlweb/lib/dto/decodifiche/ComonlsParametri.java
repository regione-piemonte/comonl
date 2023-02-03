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
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class ComonlsParametri.
 */
public class ComonlsParametri extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String abilitato;
	private String descrizione;
	private String dsParametro;
	private String modificabileDaMaschera;
	private String tipoParametro;
	private String valoreParametro;

	/**
	 * @return the abilitato
	 */
	public String getAbilitato() {
		return abilitato;
	}
	
	/**
	 * @param abilitato the abilitato to set
	 */
	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * @return the dsParametro
	 */
	public String getDsParametro() {
		return dsParametro;
	}
	
	/**
	 * @param dsParametro the dsParametro to set
	 */
	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	/**
	 * @return the modificabileDaMaschera
	 */
	public String getModificabileDaMaschera() {
		return modificabileDaMaschera;
	}
	
	/**
	 * @param modificabileDaMaschera the modificabileDaMaschera to set
	 */
	public void setModificabileDaMaschera(String modificabileDaMaschera) {
		this.modificabileDaMaschera = modificabileDaMaschera;
	}

	/**
	 * @return the tipoParametro
	 */
	public String getTipoParametro() {
		return tipoParametro;
	}
	
	/**
	 * @param tipoParametro the tipoParametro to set
	 */
	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	/**
	 * @return the valoreParametro
	 */
	public String getValoreParametro() {
		return valoreParametro;
	}
	
	/**
	 * @param valoreParametro the valoreParametro to set
	 */
	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}

}
