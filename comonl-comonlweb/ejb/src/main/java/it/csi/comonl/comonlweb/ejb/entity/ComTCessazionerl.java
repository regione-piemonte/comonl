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
 * The persistent class for the COM_T_CESSAZIONERL database table.
 * 
 */
@Entity
@Table(name="COM_T_CESSAZIONERL")
@NamedQuery(name="ComTCessazionerl.findAll", query="SELECT c FROM ComTCessazionerl c")
public class ComTCessazionerl implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTCessazionerl;
	}

	@Override
	public void setId(Long id) {
		idComTCessazionerl = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_CESSAZIONERL")
	private Long idComTCessazionerl;

	@Column(name="COD_CESSAZIONE_MIN")
	private String codCessazioneMin;

	@Column(name="DS_COM_T_CESSAZIONERL")
	private String dsComTCessazionerl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="ID_NEW_CESSAZIONERL")
	private BigDecimal idNewCessazionerl;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTCessazionerl")
//	private List<ComDRapporto> comDRapportos;

	public ComTCessazionerl() {
	}

	public Long getIdComTCessazionerl() {
		return this.idComTCessazionerl;
	}

	public void setIdComTCessazionerl(Long idComTCessazionerl) {
		this.idComTCessazionerl = idComTCessazionerl;
	}

	public String getCodCessazioneMin() {
		return this.codCessazioneMin;
	}

	public void setCodCessazioneMin(String codCessazioneMin) {
		this.codCessazioneMin = codCessazioneMin;
	}

	public String getDsComTCessazionerl() {
		return this.dsComTCessazionerl;
	}

	public void setDsComTCessazionerl(String dsComTCessazionerl) {
		this.dsComTCessazionerl = dsComTCessazionerl;
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

	public BigDecimal getIdNewCessazionerl() {
		return this.idNewCessazionerl;
	}

	public void setIdNewCessazionerl(BigDecimal idNewCessazionerl) {
		this.idNewCessazionerl = idNewCessazionerl;
	}

//	public List<ComDRapporto> getComDRapportos() {
//		return this.comDRapportos;
//	}
//
//	public void setComDRapportos(List<ComDRapporto> comDRapportos) {
//		this.comDRapportos = comDRapportos;
//	}
//
//	public ComDRapporto addComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().add(comDRapporto);
//		comDRapporto.setComTCessazionerl(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTCessazionerl(null);
//
//		return comDRapporto;
//	}

}
