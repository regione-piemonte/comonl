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

import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * The Class AnagraficaDelegato.
 */
public class AnagraficaDelegato extends BaseAuditedDto<AnagraficaDelegatoPK> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long cittadinanza;
	private String capDom;
	private String capRes;
	private String cognome;
	private Date dtInsert;
	private Date dtNascita;
	private Date dtUltMod;
	private String email;
	private String fax;
	private String idUserInsert;
	private String idUserUltMod;
	private String indirizzoDom;
	private String indirizzoRes;
	private String nome;
	private String pivaDelegato;
	private String sesso;
	private String tel;
	private SoggettoAbilitato soggettoAbilitato;
	private Comune comuneDom;
	private Comune comuneRes;
	private Comune comuneNasc;
	private StatiEsteri statiEsteri;
	private StatiEsteri statiEsteriDom;
	private StatiEsteri statiEsteriRes;
	private Personalizzazione personalizzazione;

	private Date dataAnnullamento;

	/**
	 * @return the capDom
	 */
	public String getCapDom() {
		return capDom;
	}

	/**
	 * @param capDom the capDom to set
	 */
	public void setCapDom(String capDom) {
		this.capDom = capDom;
	}

	/**
	 * @return the capRes
	 */
	public String getCapRes() {
		return capRes;
	}

	/**
	 * @param capRes the capRes to set
	 */
	public void setCapRes(String capRes) {
		this.capRes = capRes;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}

	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
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
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}

	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
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
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}

	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}

	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the indirizzoDom
	 */
	public String getIndirizzoDom() {
		return indirizzoDom;
	}

	/**
	 * @param indirizzoDom the indirizzoDom to set
	 */
	public void setIndirizzoDom(String indirizzoDom) {
		this.indirizzoDom = indirizzoDom;
	}

	/**
	 * @return the indirizzoRes
	 */
	public String getIndirizzoRes() {
		return indirizzoRes;
	}

	/**
	 * @param indirizzoRes the indirizzoRes to set
	 */
	public void setIndirizzoRes(String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the pivaDelegato
	 */
	public String getPivaDelegato() {
		return pivaDelegato;
	}

	/**
	 * @param pivaDelegato the pivaDelegato to set
	 */
	public void setPivaDelegato(String pivaDelegato) {
		this.pivaDelegato = pivaDelegato;
	}

	/**
	 * @return the sesso
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param sesso the sesso to set
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the soggettoAbilitato
	 */
	public SoggettoAbilitato getSoggettoAbilitato() {
		return soggettoAbilitato;
	}

	/**
	 * @param soggettoAbilitato the soggettoAbilitato to set
	 */
	public void setSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		this.soggettoAbilitato = soggettoAbilitato;
	}

	/**
	 * @return the personalizzazione
	 */
	public Personalizzazione getPersonalizzazione() {
		return personalizzazione;
	}

	/**
	 * @param personalizzazione the personalizzazione to set
	 */
	public void setPersonalizzazione(Personalizzazione personalizzazione) {
		this.personalizzazione = personalizzazione;
	}

	public Comune getComuneDom() {
		return comuneDom;
	}

	public void setComuneDom(Comune comuneDom) {
		this.comuneDom = comuneDom;
	}

	public Comune getComuneRes() {
		return comuneRes;
	}

	public void setComuneRes(Comune comuneRes) {
		this.comuneRes = comuneRes;
	}

	public Comune getComuneNasc() {
		return comuneNasc;
	}

	public void setComuneNasc(Comune comuneNasc) {
		this.comuneNasc = comuneNasc;
	}

	public StatiEsteri getStatiEsteri() {
		return statiEsteri;
	}

	public void setStatiEsteri(StatiEsteri statiEsteri) {
		this.statiEsteri = statiEsteri;
	}

	public StatiEsteri getStatiEsteriDom() {
		return statiEsteriDom;
	}

	public void setStatiEsteriDom(StatiEsteri statiEsteriDom) {
		this.statiEsteriDom = statiEsteriDom;
	}

	public StatiEsteri getStatiEsteriRes() {
		return statiEsteriRes;
	}

	public void setStatiEsteriRes(StatiEsteri statiEsteriRes) {
		this.statiEsteriRes = statiEsteriRes;
	}

	public Long getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(Long cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

}
