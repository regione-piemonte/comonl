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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_T_COMONLS_PARAMETRI database table.
 * 
 */
@Entity
@Table(name="COM_T_COMONLS_PARAMETRI")
@NamedQuery(name="ComTComonlsParametri.findAll", query="SELECT c FROM ComTComonlsParametri c")
public class ComTComonlsParametri implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idParametro;
	}

	@Override
	public void setId(Long id) {
		idParametro = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETRO")
	private long idParametro;

	private String abilitato;

	private String descrizione;

	@Column(name="DS_PARAMETRO")
	private String dsParametro;

	@Column(name="MODIFICABILE_DA_MASCHERA")
	private String modificabileDaMaschera;

	@Column(name="TIPO_PARAMETRO")
	private String tipoParametro;

	@Column(name="VALORE_PARAMETRO")
	private String valoreParametro;

	public ComTComonlsParametri() {
	}

	public long getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(long idParametro) {
		this.idParametro = idParametro;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDsParametro() {
		return this.dsParametro;
	}

	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	public String getModificabileDaMaschera() {
		return this.modificabileDaMaschera;
	}

	public void setModificabileDaMaschera(String modificabileDaMaschera) {
		this.modificabileDaMaschera = modificabileDaMaschera;
	}

	public String getTipoParametro() {
		return this.tipoParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public String getValoreParametro() {
		return this.valoreParametro;
	}

	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}

}
