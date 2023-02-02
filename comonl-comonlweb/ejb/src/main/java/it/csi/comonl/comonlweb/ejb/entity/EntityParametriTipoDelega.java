/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
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
 * The persistent class for the PARAMETRI_TIPO_DELEGA database table.
 * 
 */
@Entity
@Table(name="PARAMETRI_TIPO_DELEGA")
@NamedQuery(name="ParametriTipoDelega.findAll", query="SELECT p FROM EntityParametriTipoDelega p")
public class EntityParametriTipoDelega implements Serializable, BaseEntity<EntityParametriTipoDelegaPK> {

	@Override
	public EntityParametriTipoDelegaPK getId() {
		return id;
	}

	@Override
	public void setId(EntityParametriTipoDelegaPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntityParametriTipoDelegaPK id;

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

	@Column(name="TXT_DELEGA")
	private String txtDelega;

	//bi-directional many-to-one association to Personalizzazione
	@ManyToOne
	@JoinColumn(name="PV")
	private EntityPersonalizzazione personalizzazione;

	//bi-directional many-to-one association to TipoDelega
	@ManyToOne
	@JoinColumn(name="TIPO")
	private EntityTipoDelega tipoDelega;

	public EntityParametriTipoDelega() {
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

	public String getTxtDelega() {
		return this.txtDelega;
	}

	public void setTxtDelega(String txtDelega) {
		this.txtDelega = txtDelega;
	}

	public EntityPersonalizzazione getPersonalizzazione() {
		return this.personalizzazione;
	}

	public void setPersonalizzazione(EntityPersonalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	public EntityTipoDelega getTipoDelega() {
		return this.tipoDelega;
	}

	public void setTipoDelega(EntityTipoDelega tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

}
