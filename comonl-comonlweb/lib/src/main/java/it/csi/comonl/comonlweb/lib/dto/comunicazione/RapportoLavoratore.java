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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;

/**
 * The Class RapportoLavoratore.
 */
public class RapportoLavoratore extends BaseDto<RapportoLavoratorePK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Lavoratore lavoratore;
	private Rapporto rapporto;
	private TipoLavoratore tipoLavoratore;
	private TipoRapportoAzienda tipoRapportoAzienda;

	/**
	 * @return the lavoratore
	 */
	public Lavoratore getLavoratore() {
		return lavoratore;
	}
	
	/**
	 * @param lavoratore the lavoratore to set
	 */
	public void setLavoratore(Lavoratore lavoratore) {
		this.lavoratore = lavoratore;
	}

	/**
	 * @return the rapporto
	 */
	public Rapporto getRapporto() {
		return rapporto;
	}
	
	/**
	 * @param rapporto the rapporto to set
	 */
	public void setRapporto(Rapporto rapporto) {
		this.rapporto = rapporto;
	}

	/**
	 * @return the tipoLavoratore
	 */
	public TipoLavoratore getTipoLavoratore() {
		return tipoLavoratore;
	}
	
	/**
	 * @param tipoLavoratore the tipoLavoratore to set
	 */
	public void setTipoLavoratore(TipoLavoratore tipoLavoratore) {
		this.tipoLavoratore = tipoLavoratore;
	}

	/**
	 * @return the tipoRapportoAzienda
	 */
	public TipoRapportoAzienda getTipoRapportoAzienda() {
		return tipoRapportoAzienda;
	}
	
	/**
	 * @param tipoRapportoAzienda the tipoRapportoAzienda to set
	 */
	public void setTipoRapportoAzienda(TipoRapportoAzienda tipoRapportoAzienda) {
		this.tipoRapportoAzienda = tipoRapportoAzienda;
	}

}
