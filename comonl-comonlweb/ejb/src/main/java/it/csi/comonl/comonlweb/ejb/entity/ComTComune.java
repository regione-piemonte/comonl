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
 * The persistent class for the COM_T_COMUNE database table.
 * 
 */
@Entity
@Table(name="COM_T_COMUNE")
@NamedQuery(name="ComTComune.findAll", query="SELECT c FROM ComTComune c")
public class ComTComune implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTComune;
	}

	@Override
	public void setId(Long id) {
		idComTComune = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_COMUNE")
	private Long idComTComune;

	@Column(name="COD_CAP")
	private String codCap;

	@Column(name="COD_COMUNE_MIN")
	private String codComuneMin;

	@Column(name="COD_INPS")
	private String codInps;

	@Column(name="COD_ISTAT")
	private String codIstat;

	@Column(name="COD_PREFISSO")
	private String codPrefisso;

	@Column(name="DS_COM_T_COMUNE")
	private String dsComTComune;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTComune1")
//	private List<AnagraficaDelegato> anagraficaDelegatos1;
//
//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTComune2")
//	private List<AnagraficaDelegato> anagraficaDelegatos2;
//
//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTComune3")
//	private List<AnagraficaDelegato> anagraficaDelegatos3;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTComune1")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores1;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTComune2")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores2;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTComune3")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores3;
//
//	//bi-directional many-to-one association to ComDAnagraficaSedeLavoro
//	@OneToMany(mappedBy="comTComune")
//	private List<ComDAnagraficaSedeLavoro> comDAnagraficaSedeLavoros;
//
//	//bi-directional many-to-one association to ComDCapacitaFormativaModa
//	@OneToMany(mappedBy="comTComune1")
//	private List<ComDCapacitaFormativaModa> comDCapacitaFormativaModas1;
//
//	//bi-directional many-to-one association to ComDCapacitaFormativaModa
//	@OneToMany(mappedBy="comTComune2")
//	private List<ComDCapacitaFormativaModa> comDCapacitaFormativaModas2;
//
//	//bi-directional many-to-one association to ComDCapacitaFormativaModb
//	@OneToMany(mappedBy="comTComune1")
//	private List<ComDCapacitaFormativaModb> comDCapacitaFormativaModbs1;
//
//	//bi-directional many-to-one association to ComDCapacitaFormativaModb
//	@OneToMany(mappedBy="comTComune2")
//	private List<ComDCapacitaFormativaModb> comDCapacitaFormativaModbs2;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTComune1")
//	private List<ComDLavoratore> comDLavoratores1;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTComune2")
//	private List<ComDLavoratore> comDLavoratores2;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTComune3")
//	private List<ComDLavoratore> comDLavoratores3;
//
//	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTComune")
//	private List<ComDLegaleRappr> comDLegaleRapprs;
//
//	//bi-directional many-to-one association to ComDSedeLavoro
//	@OneToMany(mappedBy="comTComune")
//	private List<ComDSedeLavoro> comDSedeLavoros;
//
//	//bi-directional many-to-one association to ComDSoggettoAbilitato
//	@OneToMany(mappedBy="comTComune")
//	private List<ComDSoggettoAbilitato> comDSoggettoAbilitatos;

	//bi-directional many-to-one association to ComTCpi
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CPI")
	private ComTCpi comTCpi;

	//bi-directional many-to-one association to ComTProvincia
	@ManyToOne
	@JoinColumn(name="ID_COM_T_PROVINCIA")
	private ComTProvincia comTProvincia;

	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="comTComune1")
//	private List<Delega> delegas1;

	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="comTComune2")
//	private List<Delega> delegas2;

	public ComTComune() {
	}

	public Long getIdComTComune() {
		return this.idComTComune;
	}

	public void setIdComTComune(Long idComTComune) {
		this.idComTComune = idComTComune;
	}

	public String getCodCap() {
		return this.codCap;
	}

	public void setCodCap(String codCap) {
		this.codCap = codCap;
	}

	public String getCodComuneMin() {
		return this.codComuneMin;
	}

	public void setCodComuneMin(String codComuneMin) {
		this.codComuneMin = codComuneMin;
	}

	public String getCodInps() {
		return this.codInps;
	}

	public void setCodInps(String codInps) {
		this.codInps = codInps;
	}

	public String getCodIstat() {
		return this.codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getCodPrefisso() {
		return this.codPrefisso;
	}

	public void setCodPrefisso(String codPrefisso) {
		this.codPrefisso = codPrefisso;
	}

	public String getDsComTComune() {
		return this.dsComTComune;
	}

	public void setDsComTComune(String dsComTComune) {
		this.dsComTComune = dsComTComune;
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

//	public List<AnagraficaDelegato> getAnagraficaDelegatos1() {
//		return this.anagraficaDelegatos1;
//	}
//
//	public void setAnagraficaDelegatos1(List<AnagraficaDelegato> anagraficaDelegatos1) {
//		this.anagraficaDelegatos1 = anagraficaDelegatos1;
//	}
//
//	public AnagraficaDelegato addAnagraficaDelegatos1(AnagraficaDelegato anagraficaDelegatos1) {
//		getAnagraficaDelegatos1().add(anagraficaDelegatos1);
//		anagraficaDelegatos1.setComTComune1(this);
//
//		return anagraficaDelegatos1;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos1(AnagraficaDelegato anagraficaDelegatos1) {
//		getAnagraficaDelegatos1().remove(anagraficaDelegatos1);
//		anagraficaDelegatos1.setComTComune1(null);
//
//		return anagraficaDelegatos1;
//	}

//	public List<AnagraficaDelegato> getAnagraficaDelegatos2() {
//		return this.anagraficaDelegatos2;
//	}
//
//	public void setAnagraficaDelegatos2(List<AnagraficaDelegato> anagraficaDelegatos2) {
//		this.anagraficaDelegatos2 = anagraficaDelegatos2;
//	}
//
//	public AnagraficaDelegato addAnagraficaDelegatos2(AnagraficaDelegato anagraficaDelegatos2) {
//		getAnagraficaDelegatos2().add(anagraficaDelegatos2);
//		anagraficaDelegatos2.setComTComune2(this);
//
//		return anagraficaDelegatos2;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos2(AnagraficaDelegato anagraficaDelegatos2) {
//		getAnagraficaDelegatos2().remove(anagraficaDelegatos2);
//		anagraficaDelegatos2.setComTComune2(null);
//
//		return anagraficaDelegatos2;
//	}

//	public List<AnagraficaDelegato> getAnagraficaDelegatos3() {
//		return this.anagraficaDelegatos3;
//	}
//
//	public void setAnagraficaDelegatos3(List<AnagraficaDelegato> anagraficaDelegatos3) {
//		this.anagraficaDelegatos3 = anagraficaDelegatos3;
//	}
//
//	public AnagraficaDelegato addAnagraficaDelegatos3(AnagraficaDelegato anagraficaDelegatos3) {
//		getAnagraficaDelegatos3().add(anagraficaDelegatos3);
//		anagraficaDelegatos3.setComTComune3(this);
//
//		return anagraficaDelegatos3;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos3(AnagraficaDelegato anagraficaDelegatos3) {
//		getAnagraficaDelegatos3().remove(anagraficaDelegatos3);
//		anagraficaDelegatos3.setComTComune3(null);
//
//		return anagraficaDelegatos3;
//	}

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores1() {
//		return this.comDAnagraficaLavoratores1;
//	}
//
//	public void setComDAnagraficaLavoratores1(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores1) {
//		this.comDAnagraficaLavoratores1 = comDAnagraficaLavoratores1;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratores1(ComDAnagraficaLavoratore comDAnagraficaLavoratores1) {
//		getComDAnagraficaLavoratores1().add(comDAnagraficaLavoratores1);
//		comDAnagraficaLavoratores1.setComTComune1(this);
//
//		return comDAnagraficaLavoratores1;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores1(ComDAnagraficaLavoratore comDAnagraficaLavoratores1) {
//		getComDAnagraficaLavoratores1().remove(comDAnagraficaLavoratores1);
//		comDAnagraficaLavoratores1.setComTComune1(null);
//
//		return comDAnagraficaLavoratores1;
//	}

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores2() {
//		return this.comDAnagraficaLavoratores2;
//	}
//
//	public void setComDAnagraficaLavoratores2(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores2) {
//		this.comDAnagraficaLavoratores2 = comDAnagraficaLavoratores2;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratores2(ComDAnagraficaLavoratore comDAnagraficaLavoratores2) {
//		getComDAnagraficaLavoratores2().add(comDAnagraficaLavoratores2);
//		comDAnagraficaLavoratores2.setComTComune2(this);
//
//		return comDAnagraficaLavoratores2;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores2(ComDAnagraficaLavoratore comDAnagraficaLavoratores2) {
//		getComDAnagraficaLavoratores2().remove(comDAnagraficaLavoratores2);
//		comDAnagraficaLavoratores2.setComTComune2(null);
//
//		return comDAnagraficaLavoratores2;
//	}

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores3() {
//		return this.comDAnagraficaLavoratores3;
//	}
//
//	public void setComDAnagraficaLavoratores3(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores3) {
//		this.comDAnagraficaLavoratores3 = comDAnagraficaLavoratores3;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratores3(ComDAnagraficaLavoratore comDAnagraficaLavoratores3) {
//		getComDAnagraficaLavoratores3().add(comDAnagraficaLavoratores3);
//		comDAnagraficaLavoratores3.setComTComune3(this);
//
//		return comDAnagraficaLavoratores3;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores3(ComDAnagraficaLavoratore comDAnagraficaLavoratores3) {
//		getComDAnagraficaLavoratores3().remove(comDAnagraficaLavoratores3);
//		comDAnagraficaLavoratores3.setComTComune3(null);
//
//		return comDAnagraficaLavoratores3;
//	}

//	public List<ComDAnagraficaSedeLavoro> getComDAnagraficaSedeLavoros() {
//		return this.comDAnagraficaSedeLavoros;
//	}
//
//	public void setComDAnagraficaSedeLavoros(List<ComDAnagraficaSedeLavoro> comDAnagraficaSedeLavoros) {
//		this.comDAnagraficaSedeLavoros = comDAnagraficaSedeLavoros;
//	}
//
//	public ComDAnagraficaSedeLavoro addComDAnagraficaSedeLavoro(ComDAnagraficaSedeLavoro comDAnagraficaSedeLavoro) {
//		getComDAnagraficaSedeLavoros().add(comDAnagraficaSedeLavoro);
//		comDAnagraficaSedeLavoro.setComTComune(this);
//
//		return comDAnagraficaSedeLavoro;
//	}
//
//	public ComDAnagraficaSedeLavoro removeComDAnagraficaSedeLavoro(ComDAnagraficaSedeLavoro comDAnagraficaSedeLavoro) {
//		getComDAnagraficaSedeLavoros().remove(comDAnagraficaSedeLavoro);
//		comDAnagraficaSedeLavoro.setComTComune(null);
//
//		return comDAnagraficaSedeLavoro;
//	}

//	public List<ComDCapacitaFormativaModa> getComDCapacitaFormativaModas1() {
//		return this.comDCapacitaFormativaModas1;
//	}
//
//	public void setComDCapacitaFormativaModas1(List<ComDCapacitaFormativaModa> comDCapacitaFormativaModas1) {
//		this.comDCapacitaFormativaModas1 = comDCapacitaFormativaModas1;
//	}
//
//	public ComDCapacitaFormativaModa addComDCapacitaFormativaModas1(ComDCapacitaFormativaModa comDCapacitaFormativaModas1) {
//		getComDCapacitaFormativaModas1().add(comDCapacitaFormativaModas1);
//		comDCapacitaFormativaModas1.setComTComune1(this);
//
//		return comDCapacitaFormativaModas1;
//	}
//
//	public ComDCapacitaFormativaModa removeComDCapacitaFormativaModas1(ComDCapacitaFormativaModa comDCapacitaFormativaModas1) {
//		getComDCapacitaFormativaModas1().remove(comDCapacitaFormativaModas1);
//		comDCapacitaFormativaModas1.setComTComune1(null);
//
//		return comDCapacitaFormativaModas1;
//	}

//	public List<ComDCapacitaFormativaModa> getComDCapacitaFormativaModas2() {
//		return this.comDCapacitaFormativaModas2;
//	}
//
//	public void setComDCapacitaFormativaModas2(List<ComDCapacitaFormativaModa> comDCapacitaFormativaModas2) {
//		this.comDCapacitaFormativaModas2 = comDCapacitaFormativaModas2;
//	}
//
//	public ComDCapacitaFormativaModa addComDCapacitaFormativaModas2(ComDCapacitaFormativaModa comDCapacitaFormativaModas2) {
//		getComDCapacitaFormativaModas2().add(comDCapacitaFormativaModas2);
//		comDCapacitaFormativaModas2.setComTComune2(this);
//
//		return comDCapacitaFormativaModas2;
//	}
//
//	public ComDCapacitaFormativaModa removeComDCapacitaFormativaModas2(ComDCapacitaFormativaModa comDCapacitaFormativaModas2) {
//		getComDCapacitaFormativaModas2().remove(comDCapacitaFormativaModas2);
//		comDCapacitaFormativaModas2.setComTComune2(null);
//
//		return comDCapacitaFormativaModas2;
//	}

//	public List<ComDCapacitaFormativaModb> getComDCapacitaFormativaModbs1() {
//		return this.comDCapacitaFormativaModbs1;
//	}
//
//	public void setComDCapacitaFormativaModbs1(List<ComDCapacitaFormativaModb> comDCapacitaFormativaModbs1) {
//		this.comDCapacitaFormativaModbs1 = comDCapacitaFormativaModbs1;
//	}
//
//	public ComDCapacitaFormativaModb addComDCapacitaFormativaModbs1(ComDCapacitaFormativaModb comDCapacitaFormativaModbs1) {
//		getComDCapacitaFormativaModbs1().add(comDCapacitaFormativaModbs1);
//		comDCapacitaFormativaModbs1.setComTComune1(this);
//
//		return comDCapacitaFormativaModbs1;
//	}
//
//	public ComDCapacitaFormativaModb removeComDCapacitaFormativaModbs1(ComDCapacitaFormativaModb comDCapacitaFormativaModbs1) {
//		getComDCapacitaFormativaModbs1().remove(comDCapacitaFormativaModbs1);
//		comDCapacitaFormativaModbs1.setComTComune1(null);
//
//		return comDCapacitaFormativaModbs1;
//	}

//	public List<ComDCapacitaFormativaModb> getComDCapacitaFormativaModbs2() {
//		return this.comDCapacitaFormativaModbs2;
//	}
//
//	public void setComDCapacitaFormativaModbs2(List<ComDCapacitaFormativaModb> comDCapacitaFormativaModbs2) {
//		this.comDCapacitaFormativaModbs2 = comDCapacitaFormativaModbs2;
//	}
//
//	public ComDCapacitaFormativaModb addComDCapacitaFormativaModbs2(ComDCapacitaFormativaModb comDCapacitaFormativaModbs2) {
//		getComDCapacitaFormativaModbs2().add(comDCapacitaFormativaModbs2);
//		comDCapacitaFormativaModbs2.setComTComune2(this);
//
//		return comDCapacitaFormativaModbs2;
//	}
//
//	public ComDCapacitaFormativaModb removeComDCapacitaFormativaModbs2(ComDCapacitaFormativaModb comDCapacitaFormativaModbs2) {
//		getComDCapacitaFormativaModbs2().remove(comDCapacitaFormativaModbs2);
//		comDCapacitaFormativaModbs2.setComTComune2(null);
//
//		return comDCapacitaFormativaModbs2;
//	}

//	public List<ComDLavoratore> getComDLavoratores1() {
//		return this.comDLavoratores1;
//	}
//
//	public void setComDLavoratores1(List<ComDLavoratore> comDLavoratores1) {
//		this.comDLavoratores1 = comDLavoratores1;
//	}
//
//	public ComDLavoratore addComDLavoratores1(ComDLavoratore comDLavoratores1) {
//		getComDLavoratores1().add(comDLavoratores1);
//		comDLavoratores1.setComTComune1(this);
//
//		return comDLavoratores1;
//	}
//
//	public ComDLavoratore removeComDLavoratores1(ComDLavoratore comDLavoratores1) {
//		getComDLavoratores1().remove(comDLavoratores1);
//		comDLavoratores1.setComTComune1(null);
//
//		return comDLavoratores1;
//	}

//	public List<ComDLavoratore> getComDLavoratores2() {
//		return this.comDLavoratores2;
//	}
//
//	public void setComDLavoratores2(List<ComDLavoratore> comDLavoratores2) {
//		this.comDLavoratores2 = comDLavoratores2;
//	}
//
//	public ComDLavoratore addComDLavoratores2(ComDLavoratore comDLavoratores2) {
//		getComDLavoratores2().add(comDLavoratores2);
//		comDLavoratores2.setComTComune2(this);
//
//		return comDLavoratores2;
//	}
//
//	public ComDLavoratore removeComDLavoratores2(ComDLavoratore comDLavoratores2) {
//		getComDLavoratores2().remove(comDLavoratores2);
//		comDLavoratores2.setComTComune2(null);
//
//		return comDLavoratores2;
//	}

//	public List<ComDLavoratore> getComDLavoratores3() {
//		return this.comDLavoratores3;
//	}
//
//	public void setComDLavoratores3(List<ComDLavoratore> comDLavoratores3) {
//		this.comDLavoratores3 = comDLavoratores3;
//	}
//
//	public ComDLavoratore addComDLavoratores3(ComDLavoratore comDLavoratores3) {
//		getComDLavoratores3().add(comDLavoratores3);
//		comDLavoratores3.setComTComune3(this);
//
//		return comDLavoratores3;
//	}
//
//	public ComDLavoratore removeComDLavoratores3(ComDLavoratore comDLavoratores3) {
//		getComDLavoratores3().remove(comDLavoratores3);
//		comDLavoratores3.setComTComune3(null);
//
//		return comDLavoratores3;
//	}

//	public List<ComDLegaleRappr> getComDLegaleRapprs() {
//		return this.comDLegaleRapprs;
//	}
//
//	public void setComDLegaleRapprs(List<ComDLegaleRappr> comDLegaleRapprs) {
//		this.comDLegaleRapprs = comDLegaleRapprs;
//	}
//
//	public ComDLegaleRappr addComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().add(comDLegaleRappr);
//		comDLegaleRappr.setComTComune(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTComune(null);
//
//		return comDLegaleRappr;
//	}

//	public List<ComDSedeLavoro> getComDSedeLavoros() {
//		return this.comDSedeLavoros;
//	}
//
//	public void setComDSedeLavoros(List<ComDSedeLavoro> comDSedeLavoros) {
//		this.comDSedeLavoros = comDSedeLavoros;
//	}
//
//	public ComDSedeLavoro addComDSedeLavoro(ComDSedeLavoro comDSedeLavoro) {
//		getComDSedeLavoros().add(comDSedeLavoro);
//		comDSedeLavoro.setComTComune(this);
//
//		return comDSedeLavoro;
//	}
//
//	public ComDSedeLavoro removeComDSedeLavoro(ComDSedeLavoro comDSedeLavoro) {
//		getComDSedeLavoros().remove(comDSedeLavoro);
//		comDSedeLavoro.setComTComune(null);
//
//		return comDSedeLavoro;
//	}

//	public List<ComDSoggettoAbilitato> getComDSoggettoAbilitatos() {
//		return this.comDSoggettoAbilitatos;
//	}
//
//	public void setComDSoggettoAbilitatos(List<ComDSoggettoAbilitato> comDSoggettoAbilitatos) {
//		this.comDSoggettoAbilitatos = comDSoggettoAbilitatos;
//	}
//
//	public ComDSoggettoAbilitato addComDSoggettoAbilitato(ComDSoggettoAbilitato comDSoggettoAbilitato) {
//		getComDSoggettoAbilitatos().add(comDSoggettoAbilitato);
//		comDSoggettoAbilitato.setComTComune(this);
//
//		return comDSoggettoAbilitato;
//	}
//
//	public ComDSoggettoAbilitato removeComDSoggettoAbilitato(ComDSoggettoAbilitato comDSoggettoAbilitato) {
//		getComDSoggettoAbilitatos().remove(comDSoggettoAbilitato);
//		comDSoggettoAbilitato.setComTComune(null);
//
//		return comDSoggettoAbilitato;
//	}

	public ComTCpi getComTCpi() {
		return this.comTCpi;
	}

	public void setComTCpi(ComTCpi comTCpi) {
		this.comTCpi = comTCpi;
	}

	public ComTProvincia getComTProvincia() {
		return this.comTProvincia;
	}

	public void setComTProvincia(ComTProvincia comTProvincia) {
		this.comTProvincia = comTProvincia;
	}

//	public List<Delega> getDelegas1() {
//		return this.delegas1;
//	}
//
//	public void setDelegas1(List<Delega> delegas1) {
//		this.delegas1 = delegas1;
//	}
//
//	public Delega addDelegas1(Delega delegas1) {
//		getDelegas1().add(delegas1);
//		delegas1.setComTComune1(this);
//
//		return delegas1;
//	}
//
//	public Delega removeDelegas1(Delega delegas1) {
//		getDelegas1().remove(delegas1);
//		delegas1.setComTComune1(null);
//
//		return delegas1;
//	}

//	public List<Delega> getDelegas2() {
//		return this.delegas2;
//	}
//
//	public void setDelegas2(List<Delega> delegas2) {
//		this.delegas2 = delegas2;
//	}
//
//	public Delega addDelegas2(Delega delegas2) {
//		getDelegas2().add(delegas2);
//		delegas2.setComTComune2(this);
//
//		return delegas2;
//	}
//
//	public Delega removeDelegas2(Delega delegas2) {
//		getDelegas2().remove(delegas2);
//		delegas2.setComTComune2(null);
//
//		return delegas2;
//	}

}
