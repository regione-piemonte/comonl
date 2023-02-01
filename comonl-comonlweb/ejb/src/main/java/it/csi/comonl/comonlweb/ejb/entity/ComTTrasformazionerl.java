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
 * The persistent class for the COM_T_TRASFORMAZIONERL database table.
 * 
 */
@Entity
@Table(name="COM_T_TRASFORMAZIONERL")
@NamedQuery(name="ComTTrasformazionerl.findAll", query="SELECT c FROM ComTTrasformazionerl c")
public class ComTTrasformazionerl implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTrasformazionerl;
	}

	@Override
	public void setId(Long id) {
		idComTTrasformazionerl = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TRASFORMAZIONERL")
	private Long idComTTrasformazionerl;

	@Column(name="COD_TRASFORMAZIONIRL_MIN")
	private String codTrasformazionirlMin;

	@Column(name="DS_COM_T_TRASFORMAZIONERL")
	private String dsComTTrasformazionerl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	private String tipo;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTTrasformazionerl")
//	private List<ComDRapporto> comDRapportos;

	public ComTTrasformazionerl() {
	}

	public Long getIdComTTrasformazionerl() {
		return this.idComTTrasformazionerl;
	}

	public void setIdComTTrasformazionerl(Long idComTTrasformazionerl) {
		this.idComTTrasformazionerl = idComTTrasformazionerl;
	}

	public String getCodTrasformazionirlMin() {
		return this.codTrasformazionirlMin;
	}

	public void setCodTrasformazionirlMin(String codTrasformazionirlMin) {
		this.codTrasformazionirlMin = codTrasformazionirlMin;
	}

	public String getDsComTTrasformazionerl() {
		return this.dsComTTrasformazionerl;
	}

	public void setDsComTTrasformazionerl(String dsComTTrasformazionerl) {
		this.dsComTTrasformazionerl = dsComTTrasformazionerl;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
//		comDRapporto.setComTTrasformazionerl(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTTrasformazionerl(null);
//
//		return comDRapporto;
//	}

}
