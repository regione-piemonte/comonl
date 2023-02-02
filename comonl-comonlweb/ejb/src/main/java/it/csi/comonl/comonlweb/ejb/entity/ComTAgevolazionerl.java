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
 * The persistent class for the COM_T_AGEVOLAZIONERL database table.
 * 
 */
@Entity
@Table(name="COM_T_AGEVOLAZIONERL")
@NamedQuery(name="ComTAgevolazionerl.findAll", query="SELECT c FROM ComTAgevolazionerl c")
public class ComTAgevolazionerl implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTAgevolazionerl;
	}

	@Override
	public void setId(Long id) {
		idComTAgevolazionerl = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_AGEVOLAZIONERL")
	private long idComTAgevolazionerl;

	@Column(name="COD_AGEVOLAZIONERL_MIN")
	private String codAgevolazionerlMin;

	@Column(name="DS_COM_T_AGEVOLAZIONERL")
	private String dsComTAgevolazionerl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-many association to ComDRapporto
//	@ManyToMany
//	@JoinTable(
//		name="COM_R_AGEVOLAZIONERL"
//		, joinColumns={
//			@JoinColumn(name="ID_COM_T_AGEVOLAZIONERL")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="ID_COM_D_RAPPORTO")
//			}
//		)
//	private List<ComDRapporto> comDRapportos;

	public ComTAgevolazionerl() {
	}

	public long getIdComTAgevolazionerl() {
		return this.idComTAgevolazionerl;
	}

	public void setIdComTAgevolazionerl(long idComTAgevolazionerl) {
		this.idComTAgevolazionerl = idComTAgevolazionerl;
	}

	public String getCodAgevolazionerlMin() {
		return this.codAgevolazionerlMin;
	}

	public void setCodAgevolazionerlMin(String codAgevolazionerlMin) {
		this.codAgevolazionerlMin = codAgevolazionerlMin;
	}

	public String getDsComTAgevolazionerl() {
		return this.dsComTAgevolazionerl;
	}

	public void setDsComTAgevolazionerl(String dsComTAgevolazionerl) {
		this.dsComTAgevolazionerl = dsComTAgevolazionerl;
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

//	public List<ComDRapporto> getComDRapportos() {
//		return this.comDRapportos;
//	}
//
//	public void setComDRapportos(List<ComDRapporto> comDRapportos) {
//		this.comDRapportos = comDRapportos;
//	}

}
