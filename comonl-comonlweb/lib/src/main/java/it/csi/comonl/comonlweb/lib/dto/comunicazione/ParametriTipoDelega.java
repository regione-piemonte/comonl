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

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class ParametriTipoDelega.
 */
public class ParametriTipoDelega extends BaseDto<ParametriTipoDelegaPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String mittente;
	private String protOrigine;
	private BigDecimal protTipoDoc;
	private String subject;
	private String testoMail;
	private String tipoDocRupar;
	private String txtDelega;
	private Personalizzazione personalizzazione;
	private TipoDelega tipoDelega;

	/**
	 * @return the mittente
	 */
	public String getMittente() {
		return mittente;
	}
	
	/**
	 * @param mittente the mittente to set
	 */
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	/**
	 * @return the protOrigine
	 */
	public String getProtOrigine() {
		return protOrigine;
	}
	
	/**
	 * @param protOrigine the protOrigine to set
	 */
	public void setProtOrigine(String protOrigine) {
		this.protOrigine = protOrigine;
	}

	/**
	 * @return the protTipoDoc
	 */
	public BigDecimal getProtTipoDoc() {
		return protTipoDoc;
	}
	
	/**
	 * @param protTipoDoc the protTipoDoc to set
	 */
	public void setProtTipoDoc(BigDecimal protTipoDoc) {
		this.protTipoDoc = protTipoDoc;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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

	/**
	 * @return the tipoDocRupar
	 */
	public String getTipoDocRupar() {
		return tipoDocRupar;
	}
	
	/**
	 * @param tipoDocRupar the tipoDocRupar to set
	 */
	public void setTipoDocRupar(String tipoDocRupar) {
		this.tipoDocRupar = tipoDocRupar;
	}

	/**
	 * @return the txtDelega
	 */
	public String getTxtDelega() {
		return txtDelega;
	}
	
	/**
	 * @param txtDelega the txtDelega to set
	 */
	public void setTxtDelega(String txtDelega) {
		this.txtDelega = txtDelega;
	}

	/**
	 * @return the personalizzazione
	 */
	public Personalizzazione getPersonalizzazione() {
		return personalizzazione;
	}
	
	/**
	 * @param personalizzazione the personalizzazione to set
	 */
	public void setPersonalizzazione(Personalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	/**
	 * @return the tipoDelega
	 */
	public TipoDelega getTipoDelega() {
		return tipoDelega;
	}
	
	/**
	 * @param tipoDelega the tipoDelega to set
	 */
	public void setTipoDelega(TipoDelega tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

}
