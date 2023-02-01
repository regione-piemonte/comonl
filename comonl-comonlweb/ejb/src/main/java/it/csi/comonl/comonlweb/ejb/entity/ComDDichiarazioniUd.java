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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_DICHIARAZIONI_UD database table.
 * 
 */
@Entity
@Table(name="COM_D_DICHIARAZIONI_UD")
@NamedQuery(name="ComDDichiarazioniUd.findAll", query="SELECT c FROM ComDDichiarazioniUd c")
public class ComDDichiarazioniUd implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDComunicazione;
	}

	@Override
	public void setId(Long id) {
		idComDComunicazione = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_COMUNICAZIONE")
	private long idComDComunicazione;

	@Column(name="FLG_DAT_LAV_CONIUGE_LAVORATORE")
	private String flgDatLavConiugeLavoratore;

	@Column(name="FLG_DAT_LAV_CONV_LAVORATORE")
	private String flgDatLavConvLavoratore;

	@Column(name="FLG_DAT_LAV_INVALIDO")
	private String flgDatLavInvalido;

	@Column(name="FLG_DAT_LAV_PARENTE_LAVORATORE")
	private String flgDatLavParenteLavoratore;

	//bi-directional one-to-one association to ComDComunicazione
	@OneToOne
	@JoinColumn(name="ID_COM_D_COMUNICAZIONE")
	private ComDComunicazione comDComunicazione;

	public ComDDichiarazioniUd() {
	}

	public long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}

	public void setIdComDComunicazione(long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public String getFlgDatLavConiugeLavoratore() {
		return this.flgDatLavConiugeLavoratore;
	}

	public void setFlgDatLavConiugeLavoratore(String flgDatLavConiugeLavoratore) {
		this.flgDatLavConiugeLavoratore = flgDatLavConiugeLavoratore;
	}

	public String getFlgDatLavConvLavoratore() {
		return this.flgDatLavConvLavoratore;
	}

	public void setFlgDatLavConvLavoratore(String flgDatLavConvLavoratore) {
		this.flgDatLavConvLavoratore = flgDatLavConvLavoratore;
	}

	public String getFlgDatLavInvalido() {
		return this.flgDatLavInvalido;
	}

	public void setFlgDatLavInvalido(String flgDatLavInvalido) {
		this.flgDatLavInvalido = flgDatLavInvalido;
	}

	public String getFlgDatLavParenteLavoratore() {
		return this.flgDatLavParenteLavoratore;
	}

	public void setFlgDatLavParenteLavoratore(String flgDatLavParenteLavoratore) {
		this.flgDatLavParenteLavoratore = flgDatLavParenteLavoratore;
	}

	public ComDComunicazione getComDComunicazione() {
		return this.comDComunicazione;
	}

	public void setComDComunicazione(ComDComunicazione comDComunicazione) {
		this.comDComunicazione = comDComunicazione;
	}

}
