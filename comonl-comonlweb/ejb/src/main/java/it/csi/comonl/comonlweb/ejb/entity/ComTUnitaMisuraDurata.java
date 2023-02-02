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
 * The persistent class for the COM_T_UNITA_MISURA_DURATA database table.
 * 
 */
@Entity
@Table(name="COM_T_UNITA_MISURA_DURATA")
@NamedQuery(name="ComTUnitaMisuraDurata.findAll", query="SELECT c FROM ComTUnitaMisuraDurata c")
public class ComTUnitaMisuraDurata implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return codUnitaMisuraDurata;
	}

	@Override
	public void setId(String id) {
		codUnitaMisuraDurata = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_UNITA_MISURA_DURATA")
	private String codUnitaMisuraDurata;

	@Temporal(TemporalType.DATE)
	@Column(name="D_FINE")
	private Date dFine;

	@Temporal(TemporalType.DATE)
	@Column(name="D_INIZIO")
	private Date dInizio;

	@Column(name="DESCR_UNITA_MISURA_DURATA")
	private String descrUnitaMisuraDurata;

	//bi-directional many-to-one association to ComRTipolTirocCatTiroc
//	@OneToMany(mappedBy="comTUnitaMisuraDurata")
//	private List<ComRTipolTirocCatTiroc> comRTipolTirocCatTirocs;

	public ComTUnitaMisuraDurata() {
	}

	public String getCodUnitaMisuraDurata() {
		return this.codUnitaMisuraDurata;
	}

	public void setCodUnitaMisuraDurata(String codUnitaMisuraDurata) {
		this.codUnitaMisuraDurata = codUnitaMisuraDurata;
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

	public String getDescrUnitaMisuraDurata() {
		return this.descrUnitaMisuraDurata;
	}

	public void setDescrUnitaMisuraDurata(String descrUnitaMisuraDurata) {
		this.descrUnitaMisuraDurata = descrUnitaMisuraDurata;
	}

//	public List<ComRTipolTirocCatTiroc> getComRTipolTirocCatTirocs() {
//		return this.comRTipolTirocCatTirocs;
//	}
//
//	public void setComRTipolTirocCatTirocs(List<ComRTipolTirocCatTiroc> comRTipolTirocCatTirocs) {
//		this.comRTipolTirocCatTirocs = comRTipolTirocCatTirocs;
//	}
//
//	public ComRTipolTirocCatTiroc addComRTipolTirocCatTiroc(ComRTipolTirocCatTiroc comRTipolTirocCatTiroc) {
//		getComRTipolTirocCatTirocs().add(comRTipolTirocCatTiroc);
//		comRTipolTirocCatTiroc.setComTUnitaMisuraDurata(this);
//
//		return comRTipolTirocCatTiroc;
//	}
//
//	public ComRTipolTirocCatTiroc removeComRTipolTirocCatTiroc(ComRTipolTirocCatTiroc comRTipolTirocCatTiroc) {
//		getComRTipolTirocCatTirocs().remove(comRTipolTirocCatTiroc);
//		comRTipolTirocCatTiroc.setComTUnitaMisuraDurata(null);
//
//		return comRTipolTirocCatTiroc;
//	}

}
