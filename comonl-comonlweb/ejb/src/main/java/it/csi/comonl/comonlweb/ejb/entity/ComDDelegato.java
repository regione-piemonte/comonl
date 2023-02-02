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
 * The persistent class for the COM_D_DELEGATO database table.
 * 
 */
@Entity
@Table(name="COM_D_DELEGATO")
@NamedQuery(name="ComDDelegato.findAll", query="SELECT c FROM ComDDelegato c")
public class ComDDelegato implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDDelegato;
	}

	@Override
	public void setId(Long id) {
		idComDDelegato = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_DELEGATO")
	private long idComDDelegato;

	@Column(name="CF_DELEGATO")
	private String cfDelegato;

	@Column(name="COGNOME_DELEGATO_IMPRESA")
	private String cognomeDelegatoImpresa;

	@Column(name="MAIL_DELEGATO_IMPRESA")
	private String mailDelegatoImpresa;

	@Column(name="NOME_DELEGATO_IMPRESA")
	private String nomeDelegatoImpresa;

//	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comDDelegato")
//	private List<ComDComunicazione> comDComunicaziones;

	public ComDDelegato() {
	}

	public long getIdComDDelegato() {
		return this.idComDDelegato;
	}

	public void setIdComDDelegato(long idComDDelegato) {
		this.idComDDelegato = idComDDelegato;
	}

	public String getCfDelegato() {
		return this.cfDelegato;
	}

	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}

	public String getCognomeDelegatoImpresa() {
		return this.cognomeDelegatoImpresa;
	}

	public void setCognomeDelegatoImpresa(String cognomeDelegatoImpresa) {
		this.cognomeDelegatoImpresa = cognomeDelegatoImpresa;
	}

	public String getMailDelegatoImpresa() {
		return this.mailDelegatoImpresa;
	}

	public void setMailDelegatoImpresa(String mailDelegatoImpresa) {
		this.mailDelegatoImpresa = mailDelegatoImpresa;
	}

	public String getNomeDelegatoImpresa() {
		return this.nomeDelegatoImpresa;
	}

	public void setNomeDelegatoImpresa(String nomeDelegatoImpresa) {
		this.nomeDelegatoImpresa = nomeDelegatoImpresa;
	}

//	public List<ComDComunicazione> getComDComunicaziones() {
//		return this.comDComunicaziones;
//	}
//
//	public void setComDComunicaziones(List<ComDComunicazione> comDComunicaziones) {
//		this.comDComunicaziones = comDComunicaziones;
//	}
//
//	public ComDComunicazione addComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().add(comDComunicazione);
//		comDComunicazione.setComDDelegato(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComDDelegato(null);
//
//		return comDComunicazione;
//	}

}
