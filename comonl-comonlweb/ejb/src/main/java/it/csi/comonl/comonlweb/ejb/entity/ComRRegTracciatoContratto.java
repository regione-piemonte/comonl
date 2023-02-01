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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_REG_TRACCIATO_CONTRATTO database table.
 * 
 */
@Entity
@Table(name="COM_R_REG_TRACCIATO_CONTRATTO")
@NamedQuery(name="ComRRegTracciatoContratto.findAll", query="SELECT c FROM ComRRegTracciatoContratto c")
public class ComRRegTracciatoContratto implements Serializable, BaseEntity<ComRRegTracciatoContrattoPK> {

	@Override
	public ComRRegTracciatoContrattoPK getId() {
		return id;
	}

	@Override
	public void setId(ComRRegTracciatoContrattoPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRRegTracciatoContrattoPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="D_FINE")
	private Date dFine;

	@Temporal(TemporalType.DATE)
	@Column(name="D_INIZIO")
	private Date dInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="D_RIFERIMENTO")
	private Date dRiferimento;

	//bi-directional many-to-one association to ComTTipoComunicazione
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContratto column: ID_COM_T_TIPO_COMUNICAZIONE (should be mapped with insert="false" update="false")
	@JoinColumn(name="ID_COM_T_TIPO_COMUNICAZIONE", insertable=false, updatable=false)
	private ComTTipoComunicazione comTTipoComunicazione;

	//bi-directional many-to-one association to ComTTipoContratti
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_CONTRATTO", insertable=false, updatable=false)
	private ComTTipoContratti comTTipoContratti;

	//bi-directional many-to-one association to ComTTipoTracciato
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_TRACCIATO", insertable=false, updatable=false)
	private ComTTipoTracciato comTTipoTracciato;

	public ComRRegTracciatoContratto() {
	}

	public Date getDFine() {
		return this.dFine;
	}

	public void setDFine(Date dFine) {
		this.dFine = dFine;
	}

	public Date getDInizio() {
		return this.dInizio;
	}

	public void setDInizio(Date dInizio) {
		this.dInizio = dInizio;
	}

	public Date getDRiferimento() {
		return this.dRiferimento;
	}

	public void setDRiferimento(Date dRiferimento) {
		this.dRiferimento = dRiferimento;
	}

	public ComTTipoComunicazione getComTTipoComunicazione() {
		return this.comTTipoComunicazione;
	}

	public void setComTTipoComunicazione(ComTTipoComunicazione comTTipoComunicazione) {
		this.comTTipoComunicazione = comTTipoComunicazione;
	}

	public ComTTipoContratti getComTTipoContratti() {
		return this.comTTipoContratti;
	}

	public void setComTTipoContratti(ComTTipoContratti comTTipoContratti) {
		this.comTTipoContratti = comTTipoContratti;
	}

	public ComTTipoTracciato getComTTipoTracciato() {
		return this.comTTipoTracciato;
	}

	public void setComTTipoTracciato(ComTTipoTracciato comTTipoTracciato) {
		this.comTTipoTracciato = comTTipoTracciato;
	}

}
