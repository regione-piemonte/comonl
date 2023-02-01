/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_PARAM_ABILITAZ_IRIDE database table.
 * 
 */
@Entity
@Table(name="COM_T_PARAM_ABILITAZ_IRIDE")
@NamedQuery(name="ComTParamAbilitazIride.findAll", query="SELECT c FROM ComTParamAbilitazIride c")
public class ComTParamAbilitazIride implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return codFruitore;
	}

	@Override
	public void setId(String id) {
		codFruitore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_FRUITORE")
	private String codFruitore;

	@Column(name="MAIL_DESTINATARIO")
	private String mailDestinatario;

	@Column(name="MAIL_MITTENTE")
	private String mailMittente;

	@Column(name="NUM_MAX_INVII")
	private BigDecimal numMaxInvii;

	@Column(name="NUM_MAX_RISULTATI")
	private BigDecimal numMaxRisultati;

	@Column(name="OGGETTO_MAIL")
	private String oggettoMail;

	@Column(name="TESTO_MAIL")
	private String testoMail;

	public ComTParamAbilitazIride() {
	}

	public String getCodFruitore() {
		return this.codFruitore;
	}

	public void setCodFruitore(String codFruitore) {
		this.codFruitore = codFruitore;
	}

	public String getMailDestinatario() {
		return this.mailDestinatario;
	}

	public void setMailDestinatario(String mailDestinatario) {
		this.mailDestinatario = mailDestinatario;
	}

	public String getMailMittente() {
		return this.mailMittente;
	}

	public void setMailMittente(String mailMittente) {
		this.mailMittente = mailMittente;
	}

	public BigDecimal getNumMaxInvii() {
		return this.numMaxInvii;
	}

	public void setNumMaxInvii(BigDecimal numMaxInvii) {
		this.numMaxInvii = numMaxInvii;
	}

	public BigDecimal getNumMaxRisultati() {
		return this.numMaxRisultati;
	}

	public void setNumMaxRisultati(BigDecimal numMaxRisultati) {
		this.numMaxRisultati = numMaxRisultati;
	}

	public String getOggettoMail() {
		return this.oggettoMail;
	}

	public void setOggettoMail(String oggettoMail) {
		this.oggettoMail = oggettoMail;
	}

	public String getTestoMail() {
		return this.testoMail;
	}

	public void setTestoMail(String testoMail) {
		this.testoMail = testoMail;
	}

}
