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
 * The persistent class for the COM_T_ISTAT2001LIVELLO5 database table.
 * 
 */
@Entity
@Table(name="COM_T_ISTAT2001LIVELLO5")
@NamedQuery(name="ComTIstat2001livello5.findAll", query="SELECT c FROM ComTIstat2001livello5 c")
public class ComTIstat2001livello5 implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComIstat2001livello5;
	}

	@Override
	public void setId(Long id) {
		idComIstat2001livello5 = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_ISTAT2001LIVELLO5")
	private Long idComIstat2001livello5;

	@Column(name="COD_ISTAT2001LIVELLO5_MIN")
	private String codIstat2001livello5Min;

	@Column(name="DS_COM_ISTAT2001LIVELLO5")
	private String dsComIstat2001livello5;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="FLG_VLD_UD")
	private String flgVldUd;

	@Column(name="ID_NEW_ISTAT")
	private BigDecimal idNewIstat;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comTIstat2001livello5")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComDTutore
//	@OneToMany(mappedBy="comTIstat2001livello5")
//	private List<ComDTutore> comDTutores;

	public ComTIstat2001livello5() {
	}

	public Long getIdComIstat2001livello5() {
		return this.idComIstat2001livello5;
	}

	public void setIdComIstat2001livello5(Long idComIstat2001livello5) {
		this.idComIstat2001livello5 = idComIstat2001livello5;
	}

	public String getCodIstat2001livello5Min() {
		return this.codIstat2001livello5Min;
	}

	public void setCodIstat2001livello5Min(String codIstat2001livello5Min) {
		this.codIstat2001livello5Min = codIstat2001livello5Min;
	}

	public String getDsComIstat2001livello5() {
		return this.dsComIstat2001livello5;
	}

	public void setDsComIstat2001livello5(String dsComIstat2001livello5) {
		this.dsComIstat2001livello5 = dsComIstat2001livello5;
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

	public String getFlgVldUd() {
		return this.flgVldUd;
	}

	public void setFlgVldUd(String flgVldUd) {
		this.flgVldUd = flgVldUd;
	}

	public BigDecimal getIdNewIstat() {
		return this.idNewIstat;
	}

	public void setIdNewIstat(BigDecimal idNewIstat) {
		this.idNewIstat = idNewIstat;
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
//		comDRapporto.setComTIstat2001livello5(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComTIstat2001livello5(null);
//
//		return comDRapporto;
//	}

//	public List<ComDTutore> getComDTutores() {
//		return this.comDTutores;
//	}
//
//	public void setComDTutores(List<ComDTutore> comDTutores) {
//		this.comDTutores = comDTutores;
//	}
//
//	public ComDTutore addComDTutore(ComDTutore comDTutore) {
//		getComDTutores().add(comDTutore);
//		comDTutore.setComTIstat2001livello5(this);
//
//		return comDTutore;
//	}
//
//	public ComDTutore removeComDTutore(ComDTutore comDTutore) {
//		getComDTutores().remove(comDTutore);
//		comDTutore.setComTIstat2001livello5(null);
//
//		return comDTutore;
//	}

}
