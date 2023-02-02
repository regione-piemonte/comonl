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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_R_DEP_COMMAX_COMUNIC database table.
 * 
 */
@Entity
@Table(name="COM_R_DEP_COMMAX_COMUNIC")
@NamedQuery(name="ComRDepCommaxComunic.findAll", query="SELECT c FROM ComRDepCommaxComunic c")
public class ComRDepCommaxComunic implements Serializable, BaseEntity<ComRDepCommaxComunicPK> {

	@Override
	public ComRDepCommaxComunicPK getId() {
		return id;
	}

	@Override
	public void setId(ComRDepCommaxComunicPK id) {
		this.id = id;
	}


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComRDepCommaxComunicPK id;

	@Column(name="CODICE_COMUNICAZIONE_REG")
	private String codiceComunicazioneReg;

	@Column(name="FLG_PROT_GEST_CORRETTAMENTE")
	private String flgProtGestCorrettamente;

	//bi-directional many-to-one association to ComDComunicazione
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic column: ID_COM_D_COMUNICAZIONE (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_D_COMUNICAZIONE")
	@JoinColumn(name="ID_COM_D_COMUNICAZIONE", insertable=false, updatable=false)
	private ComDComunicazione comDComunicazione;

	//bi-directional many-to-one association to ComDUplDocumenti
	@ManyToOne
	// Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic column: ID_COM_D_UPL_DOCUMENTI (should be mapped with insert="false" update="false")
	// @JoinColumn(name="ID_COM_D_UPL_DOCUMENTI")
	@JoinColumn(name="ID_COM_D_UPL_DOCUMENTI", insertable=false, updatable=false)
	private ComDUplDocumenti comDUplDocumenti;

	public ComRDepCommaxComunic() {
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public String getFlgProtGestCorrettamente() {
		return this.flgProtGestCorrettamente;
	}

	public void setFlgProtGestCorrettamente(String flgProtGestCorrettamente) {
		this.flgProtGestCorrettamente = flgProtGestCorrettamente;
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

	public ComDUplDocumenti getComDUplDocumenti() {
		return this.comDUplDocumenti;
	}

	public void setComDUplDocumenti(ComDUplDocumenti comDUplDocumenti) {
		this.comDUplDocumenti = comDUplDocumenti;
	}

}
