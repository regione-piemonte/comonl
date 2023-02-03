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
 * The Class AnagraficaAzienda.
 */
public class AnagraficaAzienda extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codiceFiscale;
	private String partitaIva;
	private String denominazione;
	private String tipoStudio;
	private String comune;
	private String provincia;
	private String tipologia;
	private String idChiave;
	
	private Date dataAnnullamento;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getTipoStudio() {
		return tipoStudio;
	}

	public void setTipoStudio(String tipoStudio) {
		this.tipoStudio = tipoStudio;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnulamento) {
		this.dataAnnullamento = dataAnnulamento;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getIdChiave() {
		return idChiave;
	}

	public void setIdChiave(String idChiave) {
		this.idChiave = idChiave;
	}

	
	
	

}
