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

import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;

/**
 * The Class SoggettoAbilitato.
 */
public class SoggettoAbilitato extends BaseAuditedDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cap;
	private String cfSoggetto;
	private String cognomeDenominazione;
	private Date dtInsert;
	private Date dtUltMod;
	private String email;
	private String fax;
	private String flgAccentramento;
	private String idUserInsert;
	private String idUserUltMod;
	private String indirizzo;
	private String partitaIva;
	private String tel;
	private Comune comune;
	private TipoSoggettoAbilitato tipoSoggettoAbilitato;

	private Date dataAnnullamento;

	/**
	 * @return the cap
	 */
	public String getCap() {
		return cap;
	}

	/**
	 * @param cap the cap to set
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}

	/**
	 * @return the cfSoggetto
	 */
	public String getCfSoggetto() {
		return cfSoggetto;
	}

	/**
	 * @param cfSoggetto the cfSoggetto to set
	 */
	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	/**
	 * @return the cognomeDenominazione
	 */
	public String getCognomeDenominazione() {
		return cognomeDenominazione;
	}

	/**
	 * @param cognomeDenominazione the cognomeDenominazione to set
	 */
	public void setCognomeDenominazione(String cognomeDenominazione) {
		this.cognomeDenominazione = cognomeDenominazione;
	}

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}

	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	/**
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}

	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the flgAccentramento
	 */
	public String getFlgAccentramento() {
		return flgAccentramento;
	}

	/**
	 * @param flgAccentramento the flgAccentramento to set
	 */
	public void setFlgAccentramento(String flgAccentramento) {
		this.flgAccentramento = flgAccentramento;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}

	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}

	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the partitaIva
	 */
	public String getPartitaIva() {
		return partitaIva;
	}

	/**
	 * @param partitaIva the partitaIva to set
	 */
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the comune
	 */
	public Comune getComune() {
		return comune;
	}

	/**
	 * @param comune the comune to set
	 */
	public void setComune(Comune comune) {
		this.comune = comune;
	}

	/**
	 * @return the tipoSoggettoAbilitato
	 */
	public TipoSoggettoAbilitato getTipoSoggettoAbilitato() {
		return tipoSoggettoAbilitato;
	}

	/**
	 * @param tipoSoggettoAbilitato the tipoSoggettoAbilitato to set
	 */
	public void setTipoSoggettoAbilitato(TipoSoggettoAbilitato tipoSoggettoAbilitato) {
		this.tipoSoggettoAbilitato = tipoSoggettoAbilitato;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

}
