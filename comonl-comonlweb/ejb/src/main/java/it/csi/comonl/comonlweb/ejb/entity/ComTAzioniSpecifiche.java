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
 * The persistent class for the COM_T_AZIONI_SPECIFICHE database table.
 * 
 */
@Entity
@Table(name="COM_T_AZIONI_SPECIFICHE")
@NamedQuery(name="ComTAzioniSpecifiche.findAll", query="SELECT c FROM ComTAzioniSpecifiche c")
public class ComTAzioniSpecifiche implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComTAzioniSpecifiche;
	}

	@Override
	public void setId(Long id) {
		idComTAzioniSpecifiche = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_AZIONI_SPECIFICHE")
	private long idComTAzioniSpecifiche;

	@Column(name="DS_CAMPO")
	private String dsCampo;

	@Column(name="DS_NOME_PAGINA")
	private String dsNomePagina;

	@Column(name="DS_VALORE")
	private String dsValore;

	@Column(name="FLG_ABILITATO")
	private String flgAbilitato;

	public ComTAzioniSpecifiche() {
	}

	public long getIdComTAzioniSpecifiche() {
		return this.idComTAzioniSpecifiche;
	}

	public void setIdComTAzioniSpecifiche(long idComTAzioniSpecifiche) {
		this.idComTAzioniSpecifiche = idComTAzioniSpecifiche;
	}

	public String getDsCampo() {
		return this.dsCampo;
	}

	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}

	public String getDsNomePagina() {
		return this.dsNomePagina;
	}

	public void setDsNomePagina(String dsNomePagina) {
		this.dsNomePagina = dsNomePagina;
	}

	public String getDsValore() {
		return this.dsValore;
	}

	public void setDsValore(String dsValore) {
		this.dsValore = dsValore;
	}

	public String getFlgAbilitato() {
		return this.flgAbilitato;
	}

	public void setFlgAbilitato(String flgAbilitato) {
		this.flgAbilitato = flgAbilitato;
	}

}
