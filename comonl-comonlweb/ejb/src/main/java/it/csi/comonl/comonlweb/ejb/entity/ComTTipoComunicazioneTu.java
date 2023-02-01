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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_TIPO_COMUNICAZIONE_TU database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_COMUNICAZIONE_TU")
@NamedQuery(name="ComTTipoComunicazioneTu.findAll", query="SELECT c FROM ComTTipoComunicazioneTu c")
public class ComTTipoComunicazioneTu implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoComunicazioneTu;
	}

	@Override
	public void setId(Long id) {
		idComTTipoComunicazioneTu = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_COMUNICAZIONE_TU")
	private long idComTTipoComunicazioneTu;

	@Column(name="COD_TIPO_COMUNICAZIONE_MIN")
	private String codTipoComunicazioneMin;

	@Column(name="DS_COM_T_TIPO_COMUNICAZIONE")
	private String dsComTTipoComunicazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoComunicazioneTu")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTTipoComunicazioneTu() {
	}

	public long getIdComTTipoComunicazioneTu() {
		return this.idComTTipoComunicazioneTu;
	}

	public void setIdComTTipoComunicazioneTu(long idComTTipoComunicazioneTu) {
		this.idComTTipoComunicazioneTu = idComTTipoComunicazioneTu;
	}

	public String getCodTipoComunicazioneMin() {
		return this.codTipoComunicazioneMin;
	}

	public void setCodTipoComunicazioneMin(String codTipoComunicazioneMin) {
		this.codTipoComunicazioneMin = codTipoComunicazioneMin;
	}

	public String getDsComTTipoComunicazione() {
		return this.dsComTTipoComunicazione;
	}

	public void setDsComTTipoComunicazione(String dsComTTipoComunicazione) {
		this.dsComTTipoComunicazione = dsComTTipoComunicazione;
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

	public Date getDtTmst() {
		return this.dtTmst;
	}

	public void setDtTmst(Date dtTmst) {
		this.dtTmst = dtTmst;
	}

//	public List<ComDComunicazione> getComDComunicaziones() {
//		return this.comDComunicaziones;
//	}
//
//	public void setComDComunicaziones(List<ComDComunicazione> comDComunicaziones) {
//		this.comDComunicaziones = comDComunicaziones;
//	}
//
//	public ComDComunicazione addComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().add(comDComunicazione);
//		comDComunicazione.setComTTipoComunicazioneTu(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoComunicazioneTu(null);
//
//		return comDComunicazione;
//	}

}
