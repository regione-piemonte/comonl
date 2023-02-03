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
 * The Class RegTracciatoContrattoPK.
 */
public class RegTracciatoContrattoPK extends BaseDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String idComTTipoTracciato;
	private long idComTTipoContratto;
	private String idComTTipoComunicazione;

	/**
	 * @return the idComTTipoTracciato
	 */
	public String getIdComTTipoTracciato() {
		return idComTTipoTracciato;
	}
	
	/**
	 * @param idComTTipoTracciato the idComTTipoTracciato to set
	 */
	public void setIdComTTipoTracciato(String idComTTipoTracciato) {
		this.idComTTipoTracciato = idComTTipoTracciato;
	}

	/**
	 * @return the idComTTipoContratto
	 */
	public long getIdComTTipoContratto() {
		return idComTTipoContratto;
	}
	
	/**
	 * @param idComTTipoContratto the idComTTipoContratto to set
	 */
	public void setIdComTTipoContratto(long idComTTipoContratto) {
		this.idComTTipoContratto = idComTTipoContratto;
	}

	/**
	 * @return the idComTTipoComunicazione
	 */
	public String getIdComTTipoComunicazione() {
		return idComTTipoComunicazione;
	}
	
	/**
	 * @param idComTTipoComunicazione the idComTTipoComunicazione to set
	 */
	public void setIdComTTipoComunicazione(String idComTTipoComunicazione) {
		this.idComTTipoComunicazione = idComTTipoComunicazione;
	}

}
