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
import java.math.BigDecimal;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class SedeLavoro.
 */
public class SedeLavoro extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCap;
	private Date dtFine;
	private Date dtInizio;
	private Date dtTmst;
	private String email;
	private String fax;
	private String flgComuneSilpVariato;
	private String flgIndirizzoSilpVariato;
	private String flgSedeLegale;
	private BigDecimal idSedeSilp;
	private String indirizzo;
	private String telefono;
	private Comune comune;
	private StatiEsteri statiEsteri;

	/**
	 * @return the codCap
	 */
	public String getCodCap() {
		return codCap;
	}
	
	/**
	 * @param codCap the codCap to set
	 */
	public void setCodCap(String codCap) {
		this.codCap = codCap;
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
	 * @return the dtTmst
	 */
	public Date getDtTmst() {
		return dtTmst;
	}
	
	/**
	 * @param dtTmst the dtTmst to set
	 */
	public void setDtTmst(Date dtTmst) {
		this.dtTmst = dtTmst;
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
	 * @return the flgComuneSilpVariato
	 */
	public String getFlgComuneSilpVariato() {
		return flgComuneSilpVariato;
	}
	
	/**
	 * @param flgComuneSilpVariato the flgComuneSilpVariato to set
	 */
	public void setFlgComuneSilpVariato(String flgComuneSilpVariato) {
		this.flgComuneSilpVariato = flgComuneSilpVariato;
	}

	/**
	 * @return the flgIndirizzoSilpVariato
	 */
	public String getFlgIndirizzoSilpVariato() {
		return flgIndirizzoSilpVariato;
	}
	
	/**
	 * @param flgIndirizzoSilpVariato the flgIndirizzoSilpVariato to set
	 */
	public void setFlgIndirizzoSilpVariato(String flgIndirizzoSilpVariato) {
		this.flgIndirizzoSilpVariato = flgIndirizzoSilpVariato;
	}

	/**
	 * @return the flgSedeLegale
	 */
	public String getFlgSedeLegale() {
		return flgSedeLegale;
	}
	
	/**
	 * @param flgSedeLegale the flgSedeLegale to set
	 */
	public void setFlgSedeLegale(String flgSedeLegale) {
		this.flgSedeLegale = flgSedeLegale;
	}

	/**
	 * @return the idSedeSilp
	 */
	public BigDecimal getIdSedeSilp() {
		return idSedeSilp;
	}
	
	/**
	 * @param idSedeSilp the idSedeSilp to set
	 */
	public void setIdSedeSilp(BigDecimal idSedeSilp) {
		this.idSedeSilp = idSedeSilp;
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

	@Override
	public int hashCode() {
		final int prime = 31;
//		int result = super.hashCode();
		int result  = prime * ((idSedeSilp == null) ? 0 : idSedeSilp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (getClass() != obj.getClass())
			return false;
		SedeLavoro other = (SedeLavoro) obj;
		if (idSedeSilp == null) {
			if (other.idSedeSilp != null)
				return false;
		} else if (idSedeSilp.compareTo(other.idSedeSilp)!=0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SedeLavoro [codCap=" + codCap + ", dtFine=" + dtFine + ", dtInizio=" + dtInizio + ", dtTmst=" + dtTmst
				+ ", email=" + email + ", fax=" + fax + ", flgComuneSilpVariato=" + flgComuneSilpVariato
				+ ", flgIndirizzoSilpVariato=" + flgIndirizzoSilpVariato + ", flgSedeLegale=" + flgSedeLegale
				+ ", idSedeSilp=" + idSedeSilp + ", indirizzo=" + indirizzo + ", telefono=" + telefono + ", comune="
				+ comune + ", statiEsteri=" + statiEsteri + "]";
	}
	
	
}
