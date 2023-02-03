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
 * The Class TipoComunicazione.
 */
public class NaturaGiuridica extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String naturaGiuridica;
	private String descrizione;
	private Date dtFineVldt;
	private Date dtInizioVldt;
	private String busarl;
	private String busc;
	private String regolarizzazione;
	private String iscrizione;
	
	
	public String getNaturaGiuridica() {
		return naturaGiuridica;
	}
	public void setNaturaGiuridica(String naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}
	public String getBusarl() {
		return busarl;
	}
	public void setBusarl(String busarl) {
		this.busarl = busarl;
	}
	public String getBusc() {
		return busc;
	}
	public void setBusc(String busc) {
		this.busc = busc;
	}
	public String getRegolarizzazione() {
		return regolarizzazione;
	}
	public void setRegolarizzazione(String regolarizzazione) {
		this.regolarizzazione = regolarizzazione;
	}
	public String getIscrizione() {
		return iscrizione;
	}
	public void setIscrizione(String iscrizione) {
		this.iscrizione = iscrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Date getDtFineVldt() {
		return dtFineVldt;
	}
	public void setDtFineVldt(Date dtFineVldt) {
		this.dtFineVldt = dtFineVldt;
	}
	public Date getDtInizioVldt() {
		return dtInizioVldt;
	}
	public void setDtInizioVldt(Date dtInizioVldt) {
		this.dtInizioVldt = dtInizioVldt;
	}

	}
