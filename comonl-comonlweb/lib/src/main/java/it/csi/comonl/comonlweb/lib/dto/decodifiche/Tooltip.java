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
 * The Class Tooltip.
 */
public class Tooltip extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsCampo;
	private String dsNomeCampoInMaschera;
	private String dsNomeForm;
	private String dsTooltip;
	private String flgAbilitato;

	/**
	 * @return the dsCampo
	 */
	public String getDsCampo() {
		return dsCampo;
	}
	
	/**
	 * @param dsCampo the dsCampo to set
	 */
	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}

	/**
	 * @return the dsNomeCampoInMaschera
	 */
	public String getDsNomeCampoInMaschera() {
		return dsNomeCampoInMaschera;
	}
	
	/**
	 * @param dsNomeCampoInMaschera the dsNomeCampoInMaschera to set
	 */
	public void setDsNomeCampoInMaschera(String dsNomeCampoInMaschera) {
		this.dsNomeCampoInMaschera = dsNomeCampoInMaschera;
	}

	/**
	 * @return the dsNomeForm
	 */
	public String getDsNomeForm() {
		return dsNomeForm;
	}
	
	/**
	 * @param dsNomeForm the dsNomeForm to set
	 */
	public void setDsNomeForm(String dsNomeForm) {
		this.dsNomeForm = dsNomeForm;
	}

	/**
	 * @return the dsTooltip
	 */
	public String getDsTooltip() {
		return dsTooltip;
	}
	
	/**
	 * @param dsTooltip the dsTooltip to set
	 */
	public void setDsTooltip(String dsTooltip) {
		this.dsTooltip = dsTooltip;
	}

	/**
	 * @return the flgAbilitato
	 */
	public String getFlgAbilitato() {
		return flgAbilitato;
	}
	
	/**
	 * @param flgAbilitato the flgAbilitato to set
	 */
	public void setFlgAbilitato(String flgAbilitato) {
		this.flgAbilitato = flgAbilitato;
	}

}
