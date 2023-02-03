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
 * The Class DelegatoImpIride.
 */
public class DelegatoImpIride extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String capImpresa;
	private String dsComuneImpresa;
	private String dsDenominazioneImpresa;
	private String dsIndirizzoImpresa;
	private String dsProvinciaImpresa;
	private String flgImpresaAbilitante;
	private DelegatoImpresa delegatoImpresa;

	/**
	 * @return the capImpresa
	 */
	public String getCapImpresa() {
		return capImpresa;
	}
	
	/**
	 * @param capImpresa the capImpresa to set
	 */
	public void setCapImpresa(String capImpresa) {
		this.capImpresa = capImpresa;
	}

	/**
	 * @return the dsComuneImpresa
	 */
	public String getDsComuneImpresa() {
		return dsComuneImpresa;
	}
	
	/**
	 * @param dsComuneImpresa the dsComuneImpresa to set
	 */
	public void setDsComuneImpresa(String dsComuneImpresa) {
		this.dsComuneImpresa = dsComuneImpresa;
	}

	/**
	 * @return the dsDenominazioneImpresa
	 */
	public String getDsDenominazioneImpresa() {
		return dsDenominazioneImpresa;
	}
	
	/**
	 * @param dsDenominazioneImpresa the dsDenominazioneImpresa to set
	 */
	public void setDsDenominazioneImpresa(String dsDenominazioneImpresa) {
		this.dsDenominazioneImpresa = dsDenominazioneImpresa;
	}

	/**
	 * @return the dsIndirizzoImpresa
	 */
	public String getDsIndirizzoImpresa() {
		return dsIndirizzoImpresa;
	}
	
	/**
	 * @param dsIndirizzoImpresa the dsIndirizzoImpresa to set
	 */
	public void setDsIndirizzoImpresa(String dsIndirizzoImpresa) {
		this.dsIndirizzoImpresa = dsIndirizzoImpresa;
	}

	/**
	 * @return the dsProvinciaImpresa
	 */
	public String getDsProvinciaImpresa() {
		return dsProvinciaImpresa;
	}
	
	/**
	 * @param dsProvinciaImpresa the dsProvinciaImpresa to set
	 */
	public void setDsProvinciaImpresa(String dsProvinciaImpresa) {
		this.dsProvinciaImpresa = dsProvinciaImpresa;
	}

	/**
	 * @return the flgImpresaAbilitante
	 */
	public String getFlgImpresaAbilitante() {
		return flgImpresaAbilitante;
	}
	
	/**
	 * @param flgImpresaAbilitante the flgImpresaAbilitante to set
	 */
	public void setFlgImpresaAbilitante(String flgImpresaAbilitante) {
		this.flgImpresaAbilitante = flgImpresaAbilitante;
	}

	/**
	 * @return the delegatoImpresa
	 */
	public DelegatoImpresa getDelegatoImpresa() {
		return delegatoImpresa;
	}
	
	/**
	 * @param delegatoImpresa the delegatoImpresa to set
	 */
	public void setDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		this.delegatoImpresa = delegatoImpresa;
	}

}
