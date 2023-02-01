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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_VARIAZIONE_SOMM database table.
 * 
 */
@Entity
@Table(name="COM_T_VARIAZIONE_SOMM")
@NamedQuery(name="ComTVariazioneSomm.findAll", query="SELECT c FROM ComTVariazioneSomm c")
public class ComTVariazioneSomm implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTVariazioneSomm;
	}

	@Override
	public void setId(Long id) {
		idComTVariazioneSomm = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_VARIAZIONE_SOMM")
	private long idComTVariazioneSomm;

	@Column(name="COD_TIPO_VARIAZIONE_MIN")
	private String codTipoVariazioneMin;

	@Column(name="DS_VARIAZIONE")
	private String dsVariazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	//bi-directional many-to-one association to ComTTipoComunicazione
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_COMUNICAZIONE")
	private ComTTipoComunicazione comTTipoComunicazione;

	//bi-directional many-to-one association to ComTTipoSomministrazione
	@ManyToOne
	@JoinColumn(name="ID_COM_T_TIPO_SOMMINISTRAZIONE")
	private ComTTipoSomministrazione comTTipoSomministrazione;

	public ComTVariazioneSomm() {
	}

	public long getIdComTVariazioneSomm() {
		return this.idComTVariazioneSomm;
	}

	public void setIdComTVariazioneSomm(long idComTVariazioneSomm) {
		this.idComTVariazioneSomm = idComTVariazioneSomm;
	}

	public String getCodTipoVariazioneMin() {
		return this.codTipoVariazioneMin;
	}

	public void setCodTipoVariazioneMin(String codTipoVariazioneMin) {
		this.codTipoVariazioneMin = codTipoVariazioneMin;
	}

	public String getDsVariazione() {
		return this.dsVariazione;
	}

	public void setDsVariazione(String dsVariazione) {
		this.dsVariazione = dsVariazione;
	}

	public Date getDtFine() {
		return this.dtFine;
	}

	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	public Date getDtInizio() {
		return this.dtInizio;
	}

	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

	public ComTTipoComunicazione getComTTipoComunicazione() {
		return this.comTTipoComunicazione;
	}

	public void setComTTipoComunicazione(ComTTipoComunicazione comTTipoComunicazione) {
		this.comTTipoComunicazione = comTTipoComunicazione;
	}

	public ComTTipoSomministrazione getComTTipoSomministrazione() {
		return this.comTTipoSomministrazione;
	}

	public void setComTTipoSomministrazione(ComTTipoSomministrazione comTTipoSomministrazione) {
		this.comTTipoSomministrazione = comTTipoSomministrazione;
	}

}
