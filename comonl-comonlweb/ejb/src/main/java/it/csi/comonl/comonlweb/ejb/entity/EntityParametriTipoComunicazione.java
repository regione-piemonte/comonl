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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the PARAMETRI_TIPO_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="PARAMETRI_TIPO_COMUNICAZIONE")
@NamedQuery(name="ParametriTipoComunicazione.findAll", query="SELECT p FROM EntityParametriTipoComunicazione p")
public class EntityParametriTipoComunicazione implements Serializable, BaseEntity<EntityParametriTipoComunicazionePK> {

	@Override
	public EntityParametriTipoComunicazionePK getId() {
		return id;
	}

	@Override
	public void setId(EntityParametriTipoComunicazionePK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntityParametriTipoComunicazionePK id;

	private String mittente;

	@Column(name="PROT_ORIGINE")
	private String protOrigine;

	@Column(name="PROT_TIPO_DOC")
	private BigDecimal protTipoDoc;

	private String subject;

	@Column(name="TESTO_MAIL")
	private String testoMail;

	@Column(name="TIPO_DOC_RUPAR")
	private String tipoDocRupar;

	//bi-directional many-to-one association to Personalizzazione
	@ManyToOne
	@JoinColumn(name="PV")
	private EntityPersonalizzazione personalizzazione;

	public EntityParametriTipoComunicazione() {
	}

	public String getMittente() {
		return this.mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getProtOrigine() {
		return this.protOrigine;
	}

	public void setProtOrigine(String protOrigine) {
		this.protOrigine = protOrigine;
	}

	public BigDecimal getProtTipoDoc() {
		return this.protTipoDoc;
	}

	public void setProtTipoDoc(BigDecimal protTipoDoc) {
		this.protTipoDoc = protTipoDoc;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTestoMail() {
		return this.testoMail;
	}

	public void setTestoMail(String testoMail) {
		this.testoMail = testoMail;
	}

	public String getTipoDocRupar() {
		return this.tipoDocRupar;
	}

	public void setTipoDocRupar(String tipoDocRupar) {
		this.tipoDocRupar = tipoDocRupar;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

}
