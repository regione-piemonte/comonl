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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_DELEGATO_IRIDE database table.
 * 
 */
@Entity
@Table(name="COM_D_DELEGATO_IRIDE")
@NamedQuery(name="ComDDelegatoIride.findAll", query="SELECT c FROM ComDDelegatoIride c")
public class ComDDelegatoIride implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDDelegatoIride;
	}

	@Override
	public void setId(Long id) {
		idComDDelegatoIride = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_DELEGATO_IRIDE")
	private long idComDDelegatoIride;

	@Column(name="CHK_STATO_ABILITAZIONE_IRIDE")
	private String chkStatoAbilitazioneIride;

	@Column(name="CHK_TIPOLOGIA_RICHIESTA")
	private String chkTipologiaRichiesta;

	@Column(name="DS_DOCUMENTO")
	private String dsDocumento;

	@Column(name="DS_NOTE_ABILITAZIONE")
	private String dsNoteAbilitazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INVIO_ABILITAZIONE_IRIDE")
	private Date dtInvioAbilitazioneIride;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_STATO_ABILITAZIONE_IRIDE")
	private Date dtStatoAbilitazioneIride;

	private String email;

	@Column(name="FLG_INVIO_ABILITAZIONE_IRIDE")
	private String flgInvioAbilitazioneIride;

	private String telefono;

	//bi-directional many-to-one association to AnagraficaDelegato
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CF_DELEGATO", referencedColumnName="CF_DELEGATO"),
		@JoinColumn(name="TIPO_ANAGRAFICA", referencedColumnName="TIPO_ANAGRAFICA")
		})
	private EntityAnagraficaDelegato anagraficaDelegato;

	public ComDDelegatoIride() {
	}

	public long getIdComDDelegatoIride() {
		return this.idComDDelegatoIride;
	}

	public void setIdComDDelegatoIride(long idComDDelegatoIride) {
		this.idComDDelegatoIride = idComDDelegatoIride;
	}

	public String getChkStatoAbilitazioneIride() {
		return this.chkStatoAbilitazioneIride;
	}

	public void setChkStatoAbilitazioneIride(String chkStatoAbilitazioneIride) {
		this.chkStatoAbilitazioneIride = chkStatoAbilitazioneIride;
	}

	public String getChkTipologiaRichiesta() {
		return this.chkTipologiaRichiesta;
	}

	public void setChkTipologiaRichiesta(String chkTipologiaRichiesta) {
		this.chkTipologiaRichiesta = chkTipologiaRichiesta;
	}

	public String getDsDocumento() {
		return this.dsDocumento;
	}

	public void setDsDocumento(String dsDocumento) {
		this.dsDocumento = dsDocumento;
	}

	public String getDsNoteAbilitazione() {
		return this.dsNoteAbilitazione;
	}

	public void setDsNoteAbilitazione(String dsNoteAbilitazione) {
		this.dsNoteAbilitazione = dsNoteAbilitazione;
	}

	public Date getDtInvioAbilitazioneIride() {
		return this.dtInvioAbilitazioneIride;
	}

	public void setDtInvioAbilitazioneIride(Date dtInvioAbilitazioneIride) {
		this.dtInvioAbilitazioneIride = dtInvioAbilitazioneIride;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtStatoAbilitazioneIride() {
		return this.dtStatoAbilitazioneIride;
	}

	public void setDtStatoAbilitazioneIride(Date dtStatoAbilitazioneIride) {
		this.dtStatoAbilitazioneIride = dtStatoAbilitazioneIride;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFlgInvioAbilitazioneIride() {
		return this.flgInvioAbilitazioneIride;
	}

	public void setFlgInvioAbilitazioneIride(String flgInvioAbilitazioneIride) {
		this.flgInvioAbilitazioneIride = flgInvioAbilitazioneIride;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public EntityAnagraficaDelegato getAnagraficaDelegato() {
		return this.anagraficaDelegato;
	}

	public void setAnagraficaDelegato(EntityAnagraficaDelegato anagraficaDelegato) {
		this.anagraficaDelegato = anagraficaDelegato;
	}

}
