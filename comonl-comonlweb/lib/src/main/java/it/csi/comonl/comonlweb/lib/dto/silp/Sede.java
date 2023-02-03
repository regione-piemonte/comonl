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
package it.csi.comonl.comonlweb.lib.dto.silp;

import java.io.Serializable;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class SedeLegale.
 */
public class Sede extends BaseAuditedDto<Integer> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cap;
	private String codUserAggiorn;
	private String codUserInserim;
	private Date dAggiorn;
	private Date dInserim;
	private String email;
	private String fax;
	private String indirizzo;
	private String telefono;
	private DatiAzienda datiAzienda;
	private Comune comune;
	private StatiEsteri statiEsteri;
	private boolean sedeLegale = false;
	private boolean sedeOperativa = false;
	private String numeroCivico;
	private Toponimo toponimoSede;
	private String idSedeSilp;
	private String patInail;
	private String flgValida;
	private String motivoInvalida;

	private String numAgenziaSomministrazione;

	/**
	 * @return the codUserAggiorn
	 */
	public String getCodUserAggiorn() {
		return codUserAggiorn;
	}

	/**
	 * @param codUserAggiorn the codUserAggiorn to set
	 */
	public void setCodUserAggiorn(String codUserAggiorn) {
		this.codUserAggiorn = codUserAggiorn;
	}

	/**
	 * @return the codUserInserim
	 */
	public String getCodUserInserim() {
		return codUserInserim;
	}

	/**
	 * @param codUserInserim the codUserInserim to set
	 */
	public void setCodUserInserim(String codUserInserim) {
		this.codUserInserim = codUserInserim;
	}

	/**
	 * @return the dAggiorn
	 */
	public Date getDAggiorn() {
		return dAggiorn;
	}

	/**
	 * @param dAggiorn the dAggiorn to set
	 */
	public void setDAggiorn(Date dAggiorn) {
		this.dAggiorn = dAggiorn;
	}

	/**
	 * @return the dInserim
	 */
	public Date getDInserim() {
		return dInserim;
	}

	/**
	 * @param dInserim the dInserim to set
	 */
	public void setDInserim(Date dInserim) {
		this.dInserim = dInserim;
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
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the datiAzienda
	 */
	public DatiAzienda getDatiAzienda() {
		return datiAzienda;
	}

	/**
	 * @param datiAzienda the datiAzienda to set
	 */
	public void setDatiAzienda(DatiAzienda datiAzienda) {
		this.datiAzienda = datiAzienda;
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
	 * @return the statiEsteri
	 */
	public StatiEsteri getStatiEsteri() {
		return statiEsteri;
	}

	/**
	 * @param statiEsteri the statiEsteri to set
	 */
	public void setStatiEsteri(StatiEsteri statiEsteri) {
		this.statiEsteri = statiEsteri;
	}

	public boolean isSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(boolean sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public boolean isSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(boolean sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getIdSedeSilp() {
		return idSedeSilp;
	}

	public void setIdSedeSilp(String idSedeSilp) {
		this.idSedeSilp = idSedeSilp;
	}

	public String getPatInail() {
		if (patInail != null)
			return patInail.trim();
		return patInail;
	}

	public void setPatInail(String patInail) {
		this.patInail = patInail;
	}

	public Toponimo getToponimoSede() {
		return toponimoSede;
	}

	public void setToponimoSede(Toponimo toponimoSede) {
		this.toponimoSede = toponimoSede;
	}

	public String getFlgValida() {
		return flgValida;
	}

	public void setFlgValida(String flgValida) {
		this.flgValida = flgValida;
	}

	public String getMotivoInvalida() {
		return motivoInvalida;
	}

	public void setMotivoInvalida(String motivoInvalida) {
		this.motivoInvalida = motivoInvalida;
	}

	public String getNumAgenziaSomministrazione() {
		return numAgenziaSomministrazione;
	}

	public void setNumAgenziaSomministrazione(String numAgenziaSomministrazione) {
		this.numAgenziaSomministrazione = numAgenziaSomministrazione;
	}

}
