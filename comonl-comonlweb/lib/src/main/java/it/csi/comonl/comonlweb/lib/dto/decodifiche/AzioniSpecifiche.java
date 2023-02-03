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
 * The Class AzioniSpecifiche.
 */
public class AzioniSpecifiche extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsCampo;
	private String dsNomePagina;
	private String dsValore;
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
	 * @return the dsNomePagina
	 */
	public String getDsNomePagina() {
		return dsNomePagina;
	}
	
	/**
	 * @param dsNomePagina the dsNomePagina to set
	 */
	public void setDsNomePagina(String dsNomePagina) {
		this.dsNomePagina = dsNomePagina;
	}

	/**
	 * @return the dsValore
	 */
	public String getDsValore() {
		return dsValore;
	}
	
	/**
	 * @param dsValore the dsValore to set
	 */
	public void setDsValore(String dsValore) {
		this.dsValore = dsValore;
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
