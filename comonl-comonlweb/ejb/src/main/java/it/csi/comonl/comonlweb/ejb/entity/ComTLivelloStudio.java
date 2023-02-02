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
 * The persistent class for the COM_T_LIVELLO_STUDIO database table.
 * 
 */
@Entity
@Table(name="COM_T_LIVELLO_STUDIO")
@NamedQuery(name="ComTLivelloStudio.findAll", query="SELECT c FROM ComTLivelloStudio c")
public class ComTLivelloStudio implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTLivelloStudio;
	}

	@Override
	public void setId(Long id) {
		idComTLivelloStudio = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_LIVELLO_STUDIO")
	private long idComTLivelloStudio;

	@Column(name="CODICE_LIVELLO_MIN")
	private String codiceLivelloMin;

	@Column(name="CODICE_TITOLO_STUDIO")
	private String codiceTitoloStudio;

	@Column(name="DS_TITOLO")
	private String dsTitolo;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_TMST")
	private Date dtTmst;

	@Column(name="ISCED_97")
	private String isced97;

	@Column(name="ISCED_97_LEVEL_PROG_DEST")
	private String isced97LevelProgDest;

	@Column(name="SINONIMO_TITOLO_STUDIO")
	private String sinonimoTitoloStudio;

	@Column(name="TIPO_DI_SCUOLA")
	private String tipoDiScuola;

	@Column(name="TITOLO_DI_STUDIO")
	private String titoloDiStudio;

	//bi-directional many-to-one association to ComDAnagraficaLavoratore
//	@OneToMany(mappedBy="comTLivelloStudio")
//	private List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores;

	//bi-directional many-to-one association to ComDLavoratore
//	@OneToMany(mappedBy="comTLivelloStudio")
//	private List<ComDLavoratore> comDLavoratores;

	//bi-directional many-to-one association to ComTTitoloStudio
//	@OneToMany(mappedBy="comTLivelloStudio")
//	private List<ComTTitoloStudio> comTTitoloStudios;

	public ComTLivelloStudio() {
	}

	public long getIdComTLivelloStudio() {
		return this.idComTLivelloStudio;
	}

	public void setIdComTLivelloStudio(long idComTLivelloStudio) {
		this.idComTLivelloStudio = idComTLivelloStudio;
	}

	public String getCodiceLivelloMin() {
		return this.codiceLivelloMin;
	}

	public void setCodiceLivelloMin(String codiceLivelloMin) {
		this.codiceLivelloMin = codiceLivelloMin;
	}

	public String getCodiceTitoloStudio() {
		return this.codiceTitoloStudio;
	}

	public void setCodiceTitoloStudio(String codiceTitoloStudio) {
		this.codiceTitoloStudio = codiceTitoloStudio;
	}

	public String getDsTitolo() {
		return this.dsTitolo;
	}

	public void setDsTitolo(String dsTitolo) {
		this.dsTitolo = dsTitolo;
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

	public String getIsced97() {
		return this.isced97;
	}

	public void setIsced97(String isced97) {
		this.isced97 = isced97;
	}

	public String getIsced97LevelProgDest() {
		return this.isced97LevelProgDest;
	}

	public void setIsced97LevelProgDest(String isced97LevelProgDest) {
		this.isced97LevelProgDest = isced97LevelProgDest;
	}

	public String getSinonimoTitoloStudio() {
		return this.sinonimoTitoloStudio;
	}

	public void setSinonimoTitoloStudio(String sinonimoTitoloStudio) {
		this.sinonimoTitoloStudio = sinonimoTitoloStudio;
	}

	public String getTipoDiScuola() {
		return this.tipoDiScuola;
	}

	public void setTipoDiScuola(String tipoDiScuola) {
		this.tipoDiScuola = tipoDiScuola;
	}

	public String getTitoloDiStudio() {
		return this.titoloDiStudio;
	}

	public void setTitoloDiStudio(String titoloDiStudio) {
		this.titoloDiStudio = titoloDiStudio;
	}

//	public List<ComDAnagraficaLavoratore> getComDAnagraficaLavoratores() {
//		return this.comDAnagraficaLavoratores;
//	}
//
//	public void setComDAnagraficaLavoratores(List<ComDAnagraficaLavoratore> comDAnagraficaLavoratores) {
//		this.comDAnagraficaLavoratores = comDAnagraficaLavoratores;
//	}
//
//	public ComDAnagraficaLavoratore addComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().add(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTLivelloStudio(this);
//
//		return comDAnagraficaLavoratore;
//	}
//
//	public ComDAnagraficaLavoratore removeComDAnagraficaLavoratore(ComDAnagraficaLavoratore comDAnagraficaLavoratore) {
//		getComDAnagraficaLavoratores().remove(comDAnagraficaLavoratore);
//		comDAnagraficaLavoratore.setComTLivelloStudio(null);
//
//		return comDAnagraficaLavoratore;
//	}

//	public List<ComDLavoratore> getComDLavoratores() {
//		return this.comDLavoratores;
//	}
//
//	public void setComDLavoratores(List<ComDLavoratore> comDLavoratores) {
//		this.comDLavoratores = comDLavoratores;
//	}
//
//	public ComDLavoratore addComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().add(comDLavoratore);
//		comDLavoratore.setComTLivelloStudio(this);
//
//		return comDLavoratore;
//	}
//
//	public ComDLavoratore removeComDLavoratore(ComDLavoratore comDLavoratore) {
//		getComDLavoratores().remove(comDLavoratore);
//		comDLavoratore.setComTLivelloStudio(null);
//
//		return comDLavoratore;
//	}

//	public List<ComTTitoloStudio> getComTTitoloStudios() {
//		return this.comTTitoloStudios;
//	}
//
//	public void setComTTitoloStudios(List<ComTTitoloStudio> comTTitoloStudios) {
//		this.comTTitoloStudios = comTTitoloStudios;
//	}
//
//	public ComTTitoloStudio addComTTitoloStudio(ComTTitoloStudio comTTitoloStudio) {
//		getComTTitoloStudios().add(comTTitoloStudio);
//		comTTitoloStudio.setComTLivelloStudio(this);
//
//		return comTTitoloStudio;
//	}
//
//	public ComTTitoloStudio removeComTTitoloStudio(ComTTitoloStudio comTTitoloStudio) {
//		getComTTitoloStudios().remove(comTTitoloStudio);
//		comTTitoloStudio.setComTLivelloStudio(null);
//
//		return comTTitoloStudio;
//	}

}
