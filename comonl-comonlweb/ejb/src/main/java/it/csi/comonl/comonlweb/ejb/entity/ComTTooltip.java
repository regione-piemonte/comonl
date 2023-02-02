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
 * The persistent class for the COM_T_TOOLTIPS database table.
 * 
 */
@Entity
@Table(name="COM_T_TOOLTIPS")
@NamedQuery(name="ComTTooltip.findAll", query="SELECT c FROM ComTTooltip c")
public class ComTTooltip implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTTooltips;
	}

	@Override
	public void setId(Long id) {
		idComTTooltips = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TOOLTIPS")
	private long idComTTooltips;

	@Column(name="DS_CAMPO")
	private String dsCampo;

	@Column(name="DS_NOME_CAMPO_IN_MASCHERA")
	private String dsNomeCampoInMaschera;

	@Column(name="DS_NOME_FORM")
	private String dsNomeForm;

	@Column(name="DS_TOOLTIP")
	private String dsTooltip;

	@Column(name="FLG_ABILITATO")
	private String flgAbilitato;

	public ComTTooltip() {
	}

	public long getIdComTTooltips() {
		return this.idComTTooltips;
	}

	public void setIdComTTooltips(long idComTTooltips) {
		this.idComTTooltips = idComTTooltips;
	}

	public String getDsCampo() {
		return this.dsCampo;
	}

	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}

	public String getDsNomeCampoInMaschera() {
		return this.dsNomeCampoInMaschera;
	}

	public void setDsNomeCampoInMaschera(String dsNomeCampoInMaschera) {
		this.dsNomeCampoInMaschera = dsNomeCampoInMaschera;
	}

	public String getDsNomeForm() {
		return this.dsNomeForm;
	}

	public void setDsNomeForm(String dsNomeForm) {
		this.dsNomeForm = dsNomeForm;
	}

	public String getDsTooltip() {
		return this.dsTooltip;
	}

	public void setDsTooltip(String dsTooltip) {
		this.dsTooltip = dsTooltip;
	}

	public String getFlgAbilitato() {
		return this.flgAbilitato;
	}

	public void setFlgAbilitato(String flgAbilitato) {
		this.flgAbilitato = flgAbilitato;
	}

}
