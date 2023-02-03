/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.BaseDto;

/**
 * The Class DelegatoIride.
 */
public class DelegatoIride extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String chkStatoAbilitazioneIride;
	private String chkTipologiaRichiesta;
	private String dsDocumento;
	private String dsNoteAbilitazione;
	private Date dtInvioAbilitazioneIride;
	private Date dtNascita;
	private Date dtStatoAbilitazioneIride;
	private String email;
	private String flgInvioAbilitazioneIride;
	private String telefono;
	private AnagraficaDelegato anagraficaDelegato;

	/**
	 * @return the chkStatoAbilitazioneIride
	 */
	public String getChkStatoAbilitazioneIride() {
		return chkStatoAbilitazioneIride;
	}
	
	/**
	 * @param chkStatoAbilitazioneIride the chkStatoAbilitazioneIride to set
	 */
	public void setChkStatoAbilitazioneIride(String chkStatoAbilitazioneIride) {
		this.chkStatoAbilitazioneIride = chkStatoAbilitazioneIride;
	}

	/**
	 * @return the chkTipologiaRichiesta
	 */
	public String getChkTipologiaRichiesta() {
		return chkTipologiaRichiesta;
	}
	
	/**
	 * @param chkTipologiaRichiesta the chkTipologiaRichiesta to set
	 */
	public void setChkTipologiaRichiesta(String chkTipologiaRichiesta) {
		this.chkTipologiaRichiesta = chkTipologiaRichiesta;
	}

	/**
	 * @return the dsDocumento
	 */
	public String getDsDocumento() {
		return dsDocumento;
	}
	
	/**
	 * @param dsDocumento the dsDocumento to set
	 */
	public void setDsDocumento(String dsDocumento) {
		this.dsDocumento = dsDocumento;
	}

	/**
	 * @return the dsNoteAbilitazione
	 */
	public String getDsNoteAbilitazione() {
		return dsNoteAbilitazione;
	}
	
	/**
	 * @param dsNoteAbilitazione the dsNoteAbilitazione to set
	 */
	public void setDsNoteAbilitazione(String dsNoteAbilitazione) {
		this.dsNoteAbilitazione = dsNoteAbilitazione;
	}

	/**
	 * @return the dtInvioAbilitazioneIride
	 */
	public Date getDtInvioAbilitazioneIride() {
		return dtInvioAbilitazioneIride;
	}
	
	/**
	 * @param dtInvioAbilitazioneIride the dtInvioAbilitazioneIride to set
	 */
	public void setDtInvioAbilitazioneIride(Date dtInvioAbilitazioneIride) {
		this.dtInvioAbilitazioneIride = dtInvioAbilitazioneIride;
	}

	/**
	 * @return the dtNascita
	 */
	public Date getDtNascita() {
		return dtNascita;
	}
	
	/**
	 * @param dtNascita the dtNascita to set
	 */
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	/**
	 * @return the dtStatoAbilitazioneIride
	 */
	public Date getDtStatoAbilitazioneIride() {
		return dtStatoAbilitazioneIride;
	}
	
	/**
	 * @param dtStatoAbilitazioneIride the dtStatoAbilitazioneIride to set
	 */
	public void setDtStatoAbilitazioneIride(Date dtStatoAbilitazioneIride) {
		this.dtStatoAbilitazioneIride = dtStatoAbilitazioneIride;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the flgInvioAbilitazioneIride
	 */
	public String getFlgInvioAbilitazioneIride() {
		return flgInvioAbilitazioneIride;
	}
	
	/**
	 * @param flgInvioAbilitazioneIride the flgInvioAbilitazioneIride to set
	 */
	public void setFlgInvioAbilitazioneIride(String flgInvioAbilitazioneIride) {
		this.flgInvioAbilitazioneIride = flgInvioAbilitazioneIride;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the anagraficaDelegato
	 */
	public AnagraficaDelegato getAnagraficaDelegato() {
		return anagraficaDelegato;
	}
	
	/**
	 * @param anagraficaDelegato the anagraficaDelegato to set
	 */
	public void setAnagraficaDelegato(AnagraficaDelegato anagraficaDelegato) {
		this.anagraficaDelegato = anagraficaDelegato;
	}

}
