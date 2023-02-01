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

import javax.persistence.*;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the NATURA_GIURIDICA database table.
 * 
 */
@Entity
@Table(name="NATURA_GIURIDICA")
@NamedQuery(name="NaturaGiuridica.findAll", query="SELECT n FROM EntityNaturaGiuridica n")
public class EntityNaturaGiuridica implements Serializable, BaseEntity<String> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="C_NATURA_GIURIDICA", unique=true, nullable=false, length=3)
	private String naturaGiuridica;

	@Column(name="DESCRIZIONE", length=150)
	private String descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_VLDT")
	private Date dtFineVldt;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_VLDT")
	private Date dtInizioVldt;

	@Column(name="I_BUSARL", length=1)
	private String busarl;

	@Column(name="I_BUSC", length=1)
	private String busc;

	@Column(name="I_REGOLARIZZAZIONE", length=1)
	private String regolarizzazione;

	@Column(name="T_ISCRIZIONE", length=1)
	private String iscrizione;

	public EntityNaturaGiuridica() {
	}

	public String getNaturaGiuridica() {
		return naturaGiuridica;
	}

	public void setNaturaGiuridica(String naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDtFineVldt() {
		return dtFineVldt;
	}

	public void setDtFineVldt(Date dtFineVldt) {
		this.dtFineVldt = dtFineVldt;
	}

	public Date getDtInizioVldt() {
		return dtInizioVldt;
	}

	public void setDtInizioVldt(Date dtInizioVldt) {
		this.dtInizioVldt = dtInizioVldt;
	}

	public String getBusarl() {
		return busarl;
	}

	public void setBusarl(String busarl) {
		this.busarl = busarl;
	}

	public String getBusc() {
		return busc;
	}

	public void setBusc(String busc) {
		this.busc = busc;
	}

	public String getRegolarizzazione() {
		return regolarizzazione;
	}

	public void setRegolarizzazione(String regolarizzazione) {
		this.regolarizzazione = regolarizzazione;
	}

	public String getIscrizione() {
		return iscrizione;
	}

	public void setIscrizione(String iscrizione) {
		this.iscrizione = iscrizione;
	}

	@Override
	public String getId() {
		return this.naturaGiuridica;
	}

	@Override
	public void setId(String id) {
		this.naturaGiuridica = id;
	}
}
