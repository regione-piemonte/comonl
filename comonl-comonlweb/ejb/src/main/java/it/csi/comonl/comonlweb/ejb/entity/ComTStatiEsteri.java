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
 * The persistent class for the COM_T_STATI_ESTERI database table.
 * 
 */
@Entity
@Table(name="COM_T_STATI_ESTERI")
@NamedQuery(name="ComTStatiEsteri.findAll", query="SELECT c FROM ComTStatiEsteri c")
public class ComTStatiEsteri implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTStatiEsteri;
	}

	@Override
	public void setId(Long id) {
		idComTStatiEsteri = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_STATI_ESTERI")
	private Long idComTStatiEsteri;

	@Column(name="COD_NAZIONE_MIN")
	private String codNazioneMin;

	@Column(name="DS_COM_T_STATI_ESTERI")
	private String dsComTStatiEsteri;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="SIGLA_NAZIONE")
	private String siglaNazione;

//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTStatiEsteri1")
//	private List<AnagraficaDelegato> anagraficaDelegatos1;
//
//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTStatiEsteri2")
//	private List<AnagraficaDelegato> anagraficaDelegatos2;
//
//	//bi-directional many-to-one association to AnagraficaDelegato
//	@OneToMany(mappedBy="comTStatiEsteri3")
//	private List<AnagraficaDelegato> anagraficaDelegatos3;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri1")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores1;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri2")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores2;
//
//	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri3")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores3;
//
//	//bi-directional many-to-one association to ComDAnagraficaSedeLavoro
//	@OneToMany(mappedBy="comTStatiEsteri")
//	private List<ComDAnagraficaSedeLavoro> comDAnagraficaSedeLavoros;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri1")
//	private List<ComDLavoratore> comDLavoratores1;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri2")
//	private List<ComDLavoratore> comDLavoratores2;
//
//	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTStatiEsteri3")
//	private List<ComDLavoratore> comDLavoratores3;
//
//	//bi-directional many-to-one association to ComDLegaleRappr
//	@OneToMany(mappedBy="comTStatiEsteri")
//	private List<ComDLegaleRappr> comDLegaleRapprs;
//
//	//bi-directional many-to-one association to ComDSedeLavoro
//	@OneToMany(mappedBy="comTStatiEsteri")
//	private List<ComDSedeLavoro> comDSedeLavoros;
//
//	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="comTStatiEsteri1")
//	private List<Delega> delegas1;
//
//	//bi-directional many-to-one association to Delega
//	@OneToMany(mappedBy="comTStatiEsteri2")
//	private List<Delega> delegas2;

	public ComTStatiEsteri() {
	}

	public Long getIdComTStatiEsteri() {
		return this.idComTStatiEsteri;
	}

	public void setIdComTStatiEsteri(Long idComTStatiEsteri) {
		this.idComTStatiEsteri = idComTStatiEsteri;
	}

	public String getCodNazioneMin() {
		return this.codNazioneMin;
	}

	public void setCodNazioneMin(String codNazioneMin) {
		this.codNazioneMin = codNazioneMin;
	}

	public String getDsComTStatiEsteri() {
		return this.dsComTStatiEsteri;
	}

	public void setDsComTStatiEsteri(String dsComTStatiEsteri) {
		this.dsComTStatiEsteri = dsComTStatiEsteri;
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

	public String getSiglaNazione() {
		return this.siglaNazione;
	}

	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
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
//		anagraficaDelegatos1.setComTStatiEsteri1(this);
//
//		return anagraficaDelegatos1;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos1(AnagraficaDelegato anagraficaDelegatos1) {
//		getAnagraficaDelegatos1().remove(anagraficaDelegatos1);
//		anagraficaDelegatos1.setComTStatiEsteri1(null);
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
//		anagraficaDelegatos2.setComTStatiEsteri2(this);
//
//		return anagraficaDelegatos2;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos2(AnagraficaDelegato anagraficaDelegatos2) {
//		getAnagraficaDelegatos2().remove(anagraficaDelegatos2);
//		anagraficaDelegatos2.setComTStatiEsteri2(null);
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
//		anagraficaDelegatos3.setComTStatiEsteri3(this);
//
//		return anagraficaDelegatos3;
//	}
//
//	public AnagraficaDelegato removeAnagraficaDelegatos3(AnagraficaDelegato anagraficaDelegatos3) {
//		getAnagraficaDelegatos3().remove(anagraficaDelegatos3);
//		anagraficaDelegatos3.setComTStatiEsteri3(null);
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
//		comDAnagraficaLavoratores1.setComTStatiEsteri1(this);
//
//		return comDAnagraficaLavoratores1;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores1(ComDAnagraficaLavoratore comDAnagraficaLavoratores1) {
//		getComDAnagraficaLavoratores1().remove(comDAnagraficaLavoratores1);
//		comDAnagraficaLavoratores1.setComTStatiEsteri1(null);
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
//		comDAnagraficaLavoratores2.setComTStatiEsteri2(this);
//
//		return comDAnagraficaLavoratores2;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores2(ComDAnagraficaLavoratore comDAnagraficaLavoratores2) {
//		getComDAnagraficaLavoratores2().remove(comDAnagraficaLavoratores2);
//		comDAnagraficaLavoratores2.setComTStatiEsteri2(null);
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
//		comDAnagraficaLavoratores3.setComTStatiEsteri3(this);
//
//		return comDAnagraficaLavoratores3;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratores3(ComDAnagraficaLavoratore comDAnagraficaLavoratores3) {
//		getComDAnagraficaLavoratores3().remove(comDAnagraficaLavoratores3);
//		comDAnagraficaLavoratores3.setComTStatiEsteri3(null);
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
//		comDAnagraficaSedeLavoro.setComTStatiEsteri(this);
//
//		return comDAnagraficaSedeLavoro;
//	}
//
//	public ComDAnagraficaSedeLavoro removeComDAnagraficaSedeLavoro(ComDAnagraficaSedeLavoro comDAnagraficaSedeLavoro) {
//		getComDAnagraficaSedeLavoros().remove(comDAnagraficaSedeLavoro);
//		comDAnagraficaSedeLavoro.setComTStatiEsteri(null);
//
//		return comDAnagraficaSedeLavoro;
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
//		comDLavoratores1.setComTStatiEsteri1(this);
//
//		return comDLavoratores1;
//	}
//
//	public ComDLavoratore removeComDLavoratores1(ComDLavoratore comDLavoratores1) {
//		getComDLavoratores1().remove(comDLavoratores1);
//		comDLavoratores1.setComTStatiEsteri1(null);
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
//		comDLavoratores2.setComTStatiEsteri2(this);
//
//		return comDLavoratores2;
//	}
//
//	public ComDLavoratore removeComDLavoratores2(ComDLavoratore comDLavoratores2) {
//		getComDLavoratores2().remove(comDLavoratores2);
//		comDLavoratores2.setComTStatiEsteri2(null);
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
//		comDLavoratores3.setComTStatiEsteri3(this);
//
//		return comDLavoratores3;
//	}
//
//	public ComDLavoratore removeComDLavoratores3(ComDLavoratore comDLavoratores3) {
//		getComDLavoratores3().remove(comDLavoratores3);
//		comDLavoratores3.setComTStatiEsteri3(null);
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
//		comDLegaleRappr.setComTStatiEsteri(this);
//
//		return comDLegaleRappr;
//	}
//
//	public ComDLegaleRappr removeComDLegaleRappr(ComDLegaleRappr comDLegaleRappr) {
//		getComDLegaleRapprs().remove(comDLegaleRappr);
//		comDLegaleRappr.setComTStatiEsteri(null);
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
//		comDSedeLavoro.setComTStatiEsteri(this);
//
//		return comDSedeLavoro;
//	}
//
//	public ComDSedeLavoro removeComDSedeLavoro(ComDSedeLavoro comDSedeLavoro) {
//		getComDSedeLavoros().remove(comDSedeLavoro);
//		comDSedeLavoro.setComTStatiEsteri(null);
//
//		return comDSedeLavoro;
//	}

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
//		delegas1.setComTStatiEsteri1(this);
//
//		return delegas1;
//	}
//
//	public Delega removeDelegas1(Delega delegas1) {
//		getDelegas1().remove(delegas1);
//		delegas1.setComTStatiEsteri1(null);
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
//		delegas2.setComTStatiEsteri2(this);
//
//		return delegas2;
//	}
//
//	public Delega removeDelegas2(Delega delegas2) {
//		getDelegas2().remove(delegas2);
//		delegas2.setComTStatiEsteri2(null);
//
//		return delegas2;
//	}

}
