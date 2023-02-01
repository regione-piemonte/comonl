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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_DELEGATO_IMP_IRIDE database table.
 * 
 */
@Entity
@Table(name="COM_D_DELEGATO_IMP_IRIDE")
@NamedQuery(name="ComDDelegatoImpIride.findAll", query="SELECT c FROM ComDDelegatoImpIride c")
public class ComDDelegatoImpIride implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDDelegatoImpIride;
	}

	@Override
	public void setId(Long id) {
		idComDDelegatoImpIride = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_DELEGATO_IMP_IRIDE")
	private long idComDDelegatoImpIride;

	@Column(name="CAP_IMPRESA")
	private String capImpresa;

	@Column(name="DS_COMUNE_IMPRESA")
	private String dsComuneImpresa;

	@Column(name="DS_DENOMINAZIONE_IMPRESA")
	private String dsDenominazioneImpresa;

	@Column(name="DS_INDIRIZZO_IMPRESA")
	private String dsIndirizzoImpresa;

	@Column(name="DS_PROVINCIA_IMPRESA")
	private String dsProvinciaImpresa;

	@Column(name="FLG_IMPRESA_ABILITANTE")
	private String flgImpresaAbilitante;

	//bi-directional many-to-one association to DelegatoImpresa
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CF_DELEGATO", referencedColumnName="CF_DELEGATO"),
		@JoinColumn(name="CF_IMPRESA", referencedColumnName="CF_IMPRESA"),
		@JoinColumn(name="TIPO_ANAGRAFICA", referencedColumnName="TIPO_ANAGRAFICA")
		})
	private EntityDelegatoImpresa delegatoImpresa;

	public ComDDelegatoImpIride() {
	}

	public long getIdComDDelegatoImpIride() {
		return this.idComDDelegatoImpIride;
	}

	public void setIdComDDelegatoImpIride(long idComDDelegatoImpIride) {
		this.idComDDelegatoImpIride = idComDDelegatoImpIride;
	}

	public String getCapImpresa() {
		return this.capImpresa;
	}

	public void setCapImpresa(String capImpresa) {
		this.capImpresa = capImpresa;
	}

	public String getDsComuneImpresa() {
		return this.dsComuneImpresa;
	}

	public void setDsComuneImpresa(String dsComuneImpresa) {
		this.dsComuneImpresa = dsComuneImpresa;
	}

	public String getDsDenominazioneImpresa() {
		return this.dsDenominazioneImpresa;
	}

	public void setDsDenominazioneImpresa(String dsDenominazioneImpresa) {
		this.dsDenominazioneImpresa = dsDenominazioneImpresa;
	}

	public String getDsIndirizzoImpresa() {
		return this.dsIndirizzoImpresa;
	}

	public void setDsIndirizzoImpresa(String dsIndirizzoImpresa) {
		this.dsIndirizzoImpresa = dsIndirizzoImpresa;
	}

	public String getDsProvinciaImpresa() {
		return this.dsProvinciaImpresa;
	}

	public void setDsProvinciaImpresa(String dsProvinciaImpresa) {
		this.dsProvinciaImpresa = dsProvinciaImpresa;
	}

	public String getFlgImpresaAbilitante() {
		return this.flgImpresaAbilitante;
	}

	public void setFlgImpresaAbilitante(String flgImpresaAbilitante) {
		this.flgImpresaAbilitante = flgImpresaAbilitante;
	}

	public EntityDelegatoImpresa getDelegatoImpresa() {
		return this.delegatoImpresa;
	}

	public void setDelegatoImpresa(EntityDelegatoImpresa delegatoImpresa) {
		this.delegatoImpresa = delegatoImpresa;
	}

}
