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
import java.math.BigDecimal;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class ParamAbilitazIride.
 */
public class ParamAbilitazIride extends BaseDto<String> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String mailDestinatario;
	private String mailMittente;
	private BigDecimal numMaxInvii;
	private BigDecimal numMaxRisultati;
	private String oggettoMail;
	private String testoMail;

	/**
	 * @return the mailDestinatario
	 */
	public String getMailDestinatario() {
		return mailDestinatario;
	}
	
	/**
	 * @param mailDestinatario the mailDestinatario to set
	 */
	public void setMailDestinatario(String mailDestinatario) {
		this.mailDestinatario = mailDestinatario;
	}

	/**
	 * @return the mailMittente
	 */
	public String getMailMittente() {
		return mailMittente;
	}
	
	/**
	 * @param mailMittente the mailMittente to set
	 */
	public void setMailMittente(String mailMittente) {
		this.mailMittente = mailMittente;
	}

	/**
	 * @return the numMaxInvii
	 */
	public BigDecimal getNumMaxInvii() {
		return numMaxInvii;
	}
	
	/**
	 * @param numMaxInvii the numMaxInvii to set
	 */
	public void setNumMaxInvii(BigDecimal numMaxInvii) {
		this.numMaxInvii = numMaxInvii;
	}

	/**
	 * @return the numMaxRisultati
	 */
	public BigDecimal getNumMaxRisultati() {
		return numMaxRisultati;
	}
	
	/**
	 * @param numMaxRisultati the numMaxRisultati to set
	 */
	public void setNumMaxRisultati(BigDecimal numMaxRisultati) {
		this.numMaxRisultati = numMaxRisultati;
	}

	/**
	 * @return the oggettoMail
	 */
	public String getOggettoMail() {
		return oggettoMail;
	}
	
	/**
	 * @param oggettoMail the oggettoMail to set
	 */
	public void setOggettoMail(String oggettoMail) {
		this.oggettoMail = oggettoMail;
	}

	/**
	 * @return the testoMail
	 */
	public String getTestoMail() {
		return testoMail;
	}
	
	/**
	 * @param testoMail the testoMail to set
	 */
	public void setTestoMail(String testoMail) {
		this.testoMail = testoMail;
	}

}
