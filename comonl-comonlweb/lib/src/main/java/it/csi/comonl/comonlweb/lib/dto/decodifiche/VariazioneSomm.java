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
import java.util.Date;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class VariazioneSomm.
 */
public class VariazioneSomm extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codTipoVariazioneMin;
	private String dsVariazione;
	private Date dtFine;
	private Date dtInizio;
	private TipoComunicazione tipoComunicazione;
	private TipoSomministrazione tipoSomministrazione;

	/**
	 * @return the codTipoVariazioneMin
	 */
	public String getCodTipoVariazioneMin() {
		return codTipoVariazioneMin;
	}
	
	/**
	 * @param codTipoVariazioneMin the codTipoVariazioneMin to set
	 */
	public void setCodTipoVariazioneMin(String codTipoVariazioneMin) {
		this.codTipoVariazioneMin = codTipoVariazioneMin;
	}

	/**
	 * @return the dsVariazione
	 */
	public String getDsVariazione() {
		return dsVariazione;
	}
	
	/**
	 * @param dsVariazione the dsVariazione to set
	 */
	public void setDsVariazione(String dsVariazione) {
		this.dsVariazione = dsVariazione;
	}

	/**
	 * @return the dtFine
	 */
	public Date getDtFine() {
		return dtFine;
	}
	
	/**
	 * @param dtFine the dtFine to set
	 */
	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	/**
	 * @return the dtInizio
	 */
	public Date getDtInizio() {
		return dtInizio;
	}
	
	/**
	 * @param dtInizio the dtInizio to set
	 */
	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
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
	 * @return the tipoSomministrazione
	 */
	public TipoSomministrazione getTipoSomministrazione() {
		return tipoSomministrazione;
	}
	
	/**
	 * @param tipoSomministrazione the tipoSomministrazione to set
	 */
	public void setTipoSomministrazione(TipoSomministrazione tipoSomministrazione) {
		this.tipoSomministrazione = tipoSomministrazione;
	}

}
