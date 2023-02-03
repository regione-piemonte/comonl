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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;

/**
 * The Class RegTracciatoContratto.
 */
public class RegTracciatoContratto extends BaseDto<RegTracciatoContrattoPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Date dFine;
	private Date dInizio;
	private Date dRiferimento;
	private TipoComunicazione tipoComunicazione;
	private TipoContratti tipoContratti;
	private TipoTracciato tipoTracciato;

	/**
	 * @return the dFine
	 */
	public Date getDFine() {
		return dFine;
	}
	
	/**
	 * @param dFine the dFine to set
	 */
	public void setDFine(Date dFine) {
		this.dFine = dFine;
	}

	/**
	 * @return the dInizio
	 */
	public Date getDInizio() {
		return dInizio;
	}
	
	/**
	 * @param dInizio the dInizio to set
	 */
	public void setDInizio(Date dInizio) {
		this.dInizio = dInizio;
	}

	/**
	 * @return the dRiferimento
	 */
	public Date getDRiferimento() {
		return dRiferimento;
	}
	
	/**
	 * @param dRiferimento the dRiferimento to set
	 */
	public void setDRiferimento(Date dRiferimento) {
		this.dRiferimento = dRiferimento;
	}

	/**
	 * @return the tipoComunicazione
	 */
	public TipoComunicazione getTipoComunicazione() {
		return tipoComunicazione;
	}
	
	/**
	 * @param tipoComunicazione the tipoComunicazione to set
	 */
	public void setTipoComunicazione(TipoComunicazione tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
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

	/**
	 * @return the tipoTracciato
	 */
	public TipoTracciato getTipoTracciato() {
		return tipoTracciato;
	}
	
	/**
	 * @param tipoTracciato the tipoTracciato to set
	 */
	public void setTipoTracciato(TipoTracciato tipoTracciato) {
		this.tipoTracciato = tipoTracciato;
	}

}
