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
 * The persistent class for the COM_T_TIPO_TRACCIATO database table.
 * 
 */
@Entity
@Table(name="COM_T_TIPO_TRACCIATO")
@NamedQuery(name="ComTTipoTracciato.findAll", query="SELECT c FROM ComTTipoTracciato c")
public class ComTTipoTracciato implements Serializable, BaseEntity<String> {

	@Override
	public String getId() {
		return idComTTipoTracciato;
	}

	@Override
	public void setId(String id) {
		idComTTipoTracciato = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_T_TIPO_TRACCIATO")
	private String idComTTipoTracciato;

	@Column(name="DS_COM_T_TIPO_TRACCIATO")
	private String dsComTTipoTracciato;

	//bi-directional many-to-one association to ComDComunicazione
//	@OneToMany(mappedBy="comTTipoTracciato")
//	private List<ComDComunicazione> comDComunicaziones;

	//bi-directional many-to-one association to ComRRegTracciatoContratto
//	@OneToMany(mappedBy="comTTipoTracciato")
//	private List<ComRRegTracciatoContratto> comRRegTracciatoContrattos;

	public ComTTipoTracciato() {
	}

	public String getIdComTTipoTracciato() {
		return this.idComTTipoTracciato;
	}

	public void setIdComTTipoTracciato(String idComTTipoTracciato) {
		this.idComTTipoTracciato = idComTTipoTracciato;
	}

	public String getDsComTTipoTracciato() {
		return this.dsComTTipoTracciato;
	}

	public void setDsComTTipoTracciato(String dsComTTipoTracciato) {
		this.dsComTTipoTracciato = dsComTTipoTracciato;
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
//		comDComunicazione.setComTTipoTracciato(this);
//
//		return comDComunicazione;
//	}
//
//	public ComDComunicazione removeComDComunicazione(ComDComunicazione comDComunicazione) {
//		getComDComunicaziones().remove(comDComunicazione);
//		comDComunicazione.setComTTipoTracciato(null);
//
//		return comDComunicazione;
//	}

//	public List<ComRRegTracciatoContratto> getComRRegTracciatoContrattos() {
//		return this.comRRegTracciatoContrattos;
//	}
//
//	public void setComRRegTracciatoContrattos(List<ComRRegTracciatoContratto> comRRegTracciatoContrattos) {
//		this.comRRegTracciatoContrattos = comRRegTracciatoContrattos;
//	}
//
//	public ComRRegTracciatoContratto addComRRegTracciatoContratto(ComRRegTracciatoContratto comRRegTracciatoContratto) {
//		getComRRegTracciatoContrattos().add(comRRegTracciatoContratto);
//		comRRegTracciatoContratto.setComTTipoTracciato(this);
//
//		return comRRegTracciatoContratto;
//	}
//
//	public ComRRegTracciatoContratto removeComRRegTracciatoContratto(ComRRegTracciatoContratto comRRegTracciatoContratto) {
//		getComRRegTracciatoContrattos().remove(comRRegTracciatoContratto);
//		comRRegTracciatoContratto.setComTTipoTracciato(null);
//
//		return comRRegTracciatoContratto;
//	}

}
