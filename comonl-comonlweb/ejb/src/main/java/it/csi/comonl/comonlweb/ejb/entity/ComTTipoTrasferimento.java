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
 * The persistent class for the COM_T_TIPO_TRASFERIMENTO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_TRASFERIMENTO")
@NamedQuery(name="ComTTipoTrasferimento.findAll", query="SELECT c FROM ComTTipoTrasferimento c")
public class ComTTipoTrasferimento implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTipoTrasferimento;
	}

	@Override
	public void setId(Long id) {
		idComTTipoTrasferimento = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_TRASFERIMENTO")
	private long idComTTipoTrasferimento;

	@Column(name="COD_TIPOTRASFERIMENTO_MIN")
	private String codTipotrasferimentoMin;

	@Column(name="DS_COM_T_TIPO_TRASFERIMENTO")
	private String dsComTTipoTrasferimento;

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
//	@OneToMany(mappedBy="comTTipoTrasferimento")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComTTipoTrasferimento() {
	}

	public long getIdComTTipoTrasferimento() {
		return this.idComTTipoTrasferimento;
	}

	public void setIdComTTipoTrasferimento(long idComTTipoTrasferimento) {
		this.idComTTipoTrasferimento = idComTTipoTrasferimento;
	}

	public String getCodTipotrasferimentoMin() {
		return this.codTipotrasferimentoMin;
	}

	public void setCodTipotrasferimentoMin(String codTipotrasferimentoMin) {
		this.codTipotrasferimentoMin = codTipotrasferimentoMin;
	}

	public String getDsComTTipoTrasferimento() {
		return this.dsComTTipoTrasferimento;
	}

	public void setDsComTTipoTrasferimento(String dsComTTipoTrasferimento) {
		this.dsComTTipoTrasferimento = dsComTTipoTrasferimento;
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
//		comDComunicazione.setComTTipoTrasferimento(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoTrasferimento(null);
//
//		return comDComunicazione;
//	}

}
