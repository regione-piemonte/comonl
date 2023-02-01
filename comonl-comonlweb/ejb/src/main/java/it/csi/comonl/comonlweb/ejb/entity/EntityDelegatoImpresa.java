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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;

/**
 * The persistent class for the DELEGATO_IMPRESA database table.
 * 
 */
@Entity
@Table(name = "DELEGATO_IMPRESA")
@NamedQuery(name = "DelegatoImpresa.findAll", query = "SELECT d FROM EntityDelegatoImpresa d")
public class EntityDelegatoImpresa implements Serializable, BaseEntity<EntityDelegatoImpresaPK> {

	@Override
	public EntityDelegatoImpresaPK getId() {
		return id;
	}

	@Override
	public void setId(EntityDelegatoImpresaPK id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntityDelegatoImpresaPK id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_CARICA")
	private Date dtFineCarica;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ANNULLAMENTO")
	private Date dataAnnullamento;

	@Column(name = "FLG_SCUOLA")
	private String flgScuola;

	@Column(name = "ID_CARICA_PERSONA_PV")
	private String idCaricaPersonaPv;

	@Column(name = "DENOMINAZIONE")
	private String denominazione;

	private String pv;

	// bi-directional many-to-one association to ComDDelegatoImpIride
//	@OneToMany(mappedBy="delegatoImpresa")
//	private List<ComDDelegatoImpIride> comDDelegatoImpIrides;

	// bi-directional many-to-one association to AnagraficaDelegato
	@ManyToOne
	@JoinColumns({
			// Caused by: org.hibernate.MappingException: Repeated column in mapping for
			// entity: it.csi.comonl.comonlweb.ejb.entity.DelegatoImpresa column:
			// CF_DELEGATO (should be mapped with insert="false" update="false")
			// Caused by: org.hibernate.AnnotationException: Mixing insertable and non
			// insertable columns in a property is not allowed:
			// it.csi.comonl.comonlweb.ejb.entity.DelegatoImpresa.anagraficaDelegato

			@JoinColumn(name = "CF_DELEGATO", insertable = false, updatable = false, referencedColumnName = "CF_DELEGATO"),
			@JoinColumn(name = "TIPO_ANAGRAFICA", insertable = false, updatable = false, referencedColumnName = "TIPO_ANAGRAFICA") })
	private EntityAnagraficaDelegato anagraficaDelegato;

	// bi-directional many-to-one association to ComDAnagraficaDatore
	@ManyToOne
	@JoinColumn(name = "ID_COM_D_ANAGRAFICA_DATORE")
	private ComDAnagraficaDatore comDAnagraficaDatore;

	public EntityDelegatoImpresa() {
	}

	public Date getDtFineCarica() {
		return this.dtFineCarica;
	}

	public void setDtFineCarica(Date dtFineCarica) {
		this.dtFineCarica = dtFineCarica;
	}

	public String getFlgScuola() {
		return this.flgScuola;
	}

	public void setFlgScuola(String flgScuola) {
		this.flgScuola = flgScuola;
	}

	public String getIdCaricaPersonaPv() {
		return this.idCaricaPersonaPv;
	}

	public void setIdCaricaPersonaPv(String idCaricaPersonaPv) {
		this.idCaricaPersonaPv = idCaricaPersonaPv;
	}

	public String getPv() {
		return this.pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

//	public List<ComDDelegatoImpIride> getComDDelegatoImpIrides() {
//		return this.comDDelegatoImpIrides;
//	}
//
//	public void setComDDelegatoImpIrides(List<ComDDelegatoImpIride> comDDelegatoImpIrides) {
//		this.comDDelegatoImpIrides = comDDelegatoImpIrides;
//	}
//
//	public ComDDelegatoImpIride addComDDelegatoImpIride(ComDDelegatoImpIride comDDelegatoImpIride) {
//		getComDDelegatoImpIrides().add(comDDelegatoImpIride);
//		comDDelegatoImpIride.setDelegatoImpresa(this);
//
//		return comDDelegatoImpIride;
//	}
//
//	public ComDDelegatoImpIride removeComDDelegatoImpIride(ComDDelegatoImpIride comDDelegatoImpIride) {
//		getComDDelegatoImpIrides().remove(comDDelegatoImpIride);
//		comDDelegatoImpIride.setDelegatoImpresa(null);
//
//		return comDDelegatoImpIride;
//	}

	public EntityAnagraficaDelegato getAnagraficaDelegato() {
		return this.anagraficaDelegato;
	}

	public void setAnagraficaDelegato(EntityAnagraficaDelegato anagraficaDelegato) {
		this.anagraficaDelegato = anagraficaDelegato;
	}

	public ComDAnagraficaDatore getComDAnagraficaDatore() {
		return this.comDAnagraficaDatore;
	}

	public void setComDAnagraficaDatore(ComDAnagraficaDatore comDAnagraficaDatore) {
		this.comDAnagraficaDatore = comDAnagraficaDatore;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

}
