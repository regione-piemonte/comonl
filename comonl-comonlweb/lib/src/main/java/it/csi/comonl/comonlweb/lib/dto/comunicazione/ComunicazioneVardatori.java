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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;

/**
 * The Class AnagraficaAzienda.
 */
public class ComunicazioneVardatori extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Datore datoreAttuale;
	private Datore datorePrecedente;
	private Date dtInsert;
	private Date dtInvio;
	private Date dtEvento;
	private TipoComunicazione tipoComunicazione;
	private StatoComunicazione statoComunicazione;
	
	public Datore getDatoreAttuale() {
		return datoreAttuale;
	}
	public void setDatoreAttuale(Datore datoreAttuale) {
		this.datoreAttuale = datoreAttuale;
	}
	public Datore getDatorePrecedente() {
		return datorePrecedente;
	}
	public void setDatorePrecedente(Datore datorePrecedente) {
		this.datorePrecedente = datorePrecedente;
	}
	public Date getDtInsert() {
		return dtInsert;
	}
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}
	public Date getDtInvio() {
		return dtInvio;
	}
	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}
	public Date getDtEvento() {
		return dtEvento;
	}
	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}
	public TipoComunicazione getTipoComunicazione() {
		return tipoComunicazione;
	}
	public void setTipoComunicazione(TipoComunicazione tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	public StatoComunicazione getStatoComunicazione() {
		return statoComunicazione;
	}
	public void setStatoComunicazione(StatoComunicazione statoComunicazione) {
		this.statoComunicazione = statoComunicazione;
	}
	

}
