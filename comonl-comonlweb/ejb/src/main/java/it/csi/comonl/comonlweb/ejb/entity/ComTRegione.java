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
 * The persistent class for the COM_T_REGIONE database table.
 * 
 */
@Entity
@Table(name="COM_T_REGIONE")
@NamedQuery(name="ComTRegione.findAll", query="SELECT c FROM ComTRegione c")
public class ComTRegione implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTRegione;
	}

	@Override
	public void setId(Long id) {
		idComTRegione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_REGIONE")
	private long idComTRegione;

	@Column(name="COD_AMBITO_DIFFUSIONE")
	private String codAmbitoDiffusione;

	@Column(name="COD_MOBILITAGEOG")
	private String codMobilitageog;

	@Column(name="COD_REGIONE_MIN")
	private String codRegioneMin;

	@Column(name="DS_AMBITO_DIFFUSIONE")
	private String dsAmbitoDiffusione;

	@Column(name="DS_COM_T_REGIONE")
	private String dsComTRegione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

//	@OneToMany(mappedBy="comTRegione")
//	private List<ComTProvincia> comTProvincias;

	public ComTRegione() {
	}

	public long getIdComTRegione() {
		return this.idComTRegione;
	}

	public void setIdComTRegione(long idComTRegione) {
		this.idComTRegione = idComTRegione;
	}

	public String getCodAmbitoDiffusione() {
		return this.codAmbitoDiffusione;
	}

	public void setCodAmbitoDiffusione(String codAmbitoDiffusione) {
		this.codAmbitoDiffusione = codAmbitoDiffusione;
	}

	public String getCodMobilitageog() {
		return this.codMobilitageog;
	}

	public void setCodMobilitageog(String codMobilitageog) {
		this.codMobilitageog = codMobilitageog;
	}

	public String getCodRegioneMin() {
		return this.codRegioneMin;
	}

	public void setCodRegioneMin(String codRegioneMin) {
		this.codRegioneMin = codRegioneMin;
	}

	public String getDsAmbitoDiffusione() {
		return this.dsAmbitoDiffusione;
	}

	public void setDsAmbitoDiffusione(String dsAmbitoDiffusione) {
		this.dsAmbitoDiffusione = dsAmbitoDiffusione;
	}

	public String getDsComTRegione() {
		return this.dsComTRegione;
	}

	public void setDsComTRegione(String dsComTRegione) {
		this.dsComTRegione = dsComTRegione;
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

//	public List<ComTProvincia> getComTProvincias() {
//		return this.comTProvincias;
//	}
//
//	public void setComTProvincias(List<ComTProvincia> comTProvincias) {
//		this.comTProvincias = comTProvincias;
//	}
//
//	public ComTProvincia addComTProvincia(ComTProvincia comTProvincia) {
//		getComTProvincias().add(comTProvincia);
//		comTProvincia.setComTRegione(this);
//
//		return comTProvincia;
//	}
//
//	public ComTProvincia removeComTProvincia(ComTProvincia comTProvincia) {
//		getComTProvincias().remove(comTProvincia);
//		comTProvincia.setComTRegione(null);
//
//		return comTProvincia;
//	}

}
