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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;

/**
 * The Class Tutore.
 */
public class Tutore extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cfTutore;
	private String cognome;
	private String dsLivelloInquadramento;
	private Date dtNascita;
	private Date dtVisitaMedica;
	private String flgAccettazione;
	private String flgTitolare;
	private String nome;
	private BigDecimal numAnniEsperienza;
	private String sesso;
	private GradoContrattuale gradoContrattuale;
	private Istat2001livello5 istat;

	/**
	 * @return the cfTutore
	 */
	public String getCfTutore() {
		return cfTutore;
	}
	
	/**
	 * @param cfTutore the cfTutore to set
	 */
	public void setCfTutore(String cfTutore) {
		this.cfTutore = cfTutore;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the dsLivelloInquadramento
	 */
	public String getDsLivelloInquadramento() {
		return dsLivelloInquadramento;
	}
	
	/**
	 * @param dsLivelloInquadramento the dsLivelloInquadramento to set
	 */
	public void setDsLivelloInquadramento(String dsLivelloInquadramento) {
		this.dsLivelloInquadramento = dsLivelloInquadramento;
	}

	/**
	 * @return the dtNascita
	 */
	public Date getDtNascita() {
		return dtNascita;
	}
	
	/**
	 * @param dtNascita the dtNascita to set
	 */
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	/**
	 * @return the dtVisitaMedica
	 */
	public Date getDtVisitaMedica() {
		return dtVisitaMedica;
	}
	
	/**
	 * @param dtVisitaMedica the dtVisitaMedica to set
	 */
	public void setDtVisitaMedica(Date dtVisitaMedica) {
		this.dtVisitaMedica = dtVisitaMedica;
	}

	/**
	 * @return the flgAccettazione
	 */
	public String getFlgAccettazione() {
		return flgAccettazione;
	}
	
	/**
	 * @param flgAccettazione the flgAccettazione to set
	 */
	public void setFlgAccettazione(String flgAccettazione) {
		this.flgAccettazione = flgAccettazione;
	}

	/**
	 * @return the flgTitolare
	 */
	public String getFlgTitolare() {
		return flgTitolare;
	}
	
	/**
	 * @param flgTitolare the flgTitolare to set
	 */
	public void setFlgTitolare(String flgTitolare) {
		this.flgTitolare = flgTitolare;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the numAnniEsperienza
	 */
	public BigDecimal getNumAnniEsperienza() {
		return numAnniEsperienza;
	}
	
	/**
	 * @param numAnniEsperienza the numAnniEsperienza to set
	 */
	public void setNumAnniEsperienza(BigDecimal numAnniEsperienza) {
		this.numAnniEsperienza = numAnniEsperienza;
	}

	/**
	 * @return the sesso
	 */
	public String getSesso() {
		return sesso;
	}
	
	/**
	 * @param sesso the sesso to set
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	/**
	 * @return the gradoContrattuale
	 */
	public GradoContrattuale getGradoContrattuale() {
		return gradoContrattuale;
	}
	
	/**
	 * @param gradoContrattuale the gradoContrattuale to set
	 */
	public void setGradoContrattuale(GradoContrattuale gradoContrattuale) {
		this.gradoContrattuale = gradoContrattuale;
	}

	public Istat2001livello5 getIstat() {
		return istat;
	}

	public void setIstat(Istat2001livello5 istat) {
		this.istat = istat;
	}

	/**
	 * @return the istat2001livello5
	 */

}
