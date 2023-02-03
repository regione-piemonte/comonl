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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;

/**
 * The Class AnagraficaLavoratore.
 */
public class AnagraficaLavoratore extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codCapDom;
	private String codCapRes;
	private String codiceFiscale;
	private String cognome;
	private String dsIndirizzoDom;
	private String dsIndirizzoRes;
	private Date dtNascita;
	private Date dtScadenzaPermessoSogg;
	private String nome;
	private String numeroDocumento;
	private String sesso;
	private String statoCivile;
	private String telDom;
	private Cittadinanza cittadinanza;
	private Comune comune1;
	private Comune comune2;
	private Comune comune3;
	private LivelloStudio livelloStudio;
	private MotivoPermesso motivoPermesso;
	private Questura questura;
	private StatiEsteri statiEsteri1;
	private StatiEsteri statiEsteri2;
	private StatiEsteri statiEsteri3;
	private StatusStraniero statusStraniero;

	/**
	 * @return the codCapDom
	 */
	public String getCodCapDom() {
		return codCapDom;
	}
	
	/**
	 * @param codCapDom the codCapDom to set
	 */
	public void setCodCapDom(String codCapDom) {
		this.codCapDom = codCapDom;
	}

	/**
	 * @return the codCapRes
	 */
	public String getCodCapRes() {
		return codCapRes;
	}
	
	/**
	 * @param codCapRes the codCapRes to set
	 */
	public void setCodCapRes(String codCapRes) {
		this.codCapRes = codCapRes;
	}

	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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
	 * @return the dsIndirizzoDom
	 */
	public String getDsIndirizzoDom() {
		return dsIndirizzoDom;
	}
	
	/**
	 * @param dsIndirizzoDom the dsIndirizzoDom to set
	 */
	public void setDsIndirizzoDom(String dsIndirizzoDom) {
		this.dsIndirizzoDom = dsIndirizzoDom;
	}

	/**
	 * @return the dsIndirizzoRes
	 */
	public String getDsIndirizzoRes() {
		return dsIndirizzoRes;
	}
	
	/**
	 * @param dsIndirizzoRes the dsIndirizzoRes to set
	 */
	public void setDsIndirizzoRes(String dsIndirizzoRes) {
		this.dsIndirizzoRes = dsIndirizzoRes;
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
	 * @return the dtScadenzaPermessoSogg
	 */
	public Date getDtScadenzaPermessoSogg() {
		return dtScadenzaPermessoSogg;
	}
	
	/**
	 * @param dtScadenzaPermessoSogg the dtScadenzaPermessoSogg to set
	 */
	public void setDtScadenzaPermessoSogg(Date dtScadenzaPermessoSogg) {
		this.dtScadenzaPermessoSogg = dtScadenzaPermessoSogg;
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
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
	 * @return the statoCivile
	 */
	public String getStatoCivile() {
		return statoCivile;
	}
	
	/**
	 * @param statoCivile the statoCivile to set
	 */
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	/**
	 * @return the telDom
	 */
	public String getTelDom() {
		return telDom;
	}
	
	/**
	 * @param telDom the telDom to set
	 */
	public void setTelDom(String telDom) {
		this.telDom = telDom;
	}

	/**
	 * @return the cittadinanza
	 */
	public Cittadinanza getCittadinanza() {
		return cittadinanza;
	}
	
	/**
	 * @param cittadinanza the cittadinanza to set
	 */
	public void setCittadinanza(Cittadinanza cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	/**
	 * @return the comune1
	 */
	public Comune getComune1() {
		return comune1;
	}
	
	/**
	 * @param comune1 the comune1 to set
	 */
	public void setComune1(Comune comune1) {
		this.comune1 = comune1;
	}

	/**
	 * @return the comune2
	 */
	public Comune getComune2() {
		return comune2;
	}
	
	/**
	 * @param comune2 the comune2 to set
	 */
	public void setComune2(Comune comune2) {
		this.comune2 = comune2;
	}

	/**
	 * @return the comune3
	 */
	public Comune getComune3() {
		return comune3;
	}
	
	/**
	 * @param comune3 the comune3 to set
	 */
	public void setComune3(Comune comune3) {
		this.comune3 = comune3;
	}

	/**
	 * @return the livelloStudio
	 */
	public LivelloStudio getLivelloStudio() {
		return livelloStudio;
	}
	
	/**
	 * @param livelloStudio the livelloStudio to set
	 */
	public void setLivelloStudio(LivelloStudio livelloStudio) {
		this.livelloStudio = livelloStudio;
	}

	/**
	 * @return the motivoPermesso
	 */
	public MotivoPermesso getMotivoPermesso() {
		return motivoPermesso;
	}
	
	/**
	 * @param motivoPermesso the motivoPermesso to set
	 */
	public void setMotivoPermesso(MotivoPermesso motivoPermesso) {
		this.motivoPermesso = motivoPermesso;
	}

	/**
	 * @return the questura
	 */
	public Questura getQuestura() {
		return questura;
	}
	
	/**
	 * @param questura the questura to set
	 */
	public void setQuestura(Questura questura) {
		this.questura = questura;
	}

	/**
	 * @return the statiEsteri1
	 */
	public StatiEsteri getStatiEsteri1() {
		return statiEsteri1;
	}
	
	/**
	 * @param statiEsteri1 the statiEsteri1 to set
	 */
	public void setStatiEsteri1(StatiEsteri statiEsteri1) {
		this.statiEsteri1 = statiEsteri1;
	}

	/**
	 * @return the statiEsteri2
	 */
	public StatiEsteri getStatiEsteri2() {
		return statiEsteri2;
	}
	
	/**
	 * @param statiEsteri2 the statiEsteri2 to set
	 */
	public void setStatiEsteri2(StatiEsteri statiEsteri2) {
		this.statiEsteri2 = statiEsteri2;
	}

	/**
	 * @return the statiEsteri3
	 */
	public StatiEsteri getStatiEsteri3() {
		return statiEsteri3;
	}
	
	/**
	 * @param statiEsteri3 the statiEsteri3 to set
	 */
	public void setStatiEsteri3(StatiEsteri statiEsteri3) {
		this.statiEsteri3 = statiEsteri3;
	}

	/**
	 * @return the statusStraniero
	 */
	public StatusStraniero getStatusStraniero() {
		return statusStraniero;
	}
	
	/**
	 * @param statusStraniero the statusStraniero to set
	 */
	public void setStatusStraniero(StatusStraniero statusStraniero) {
		this.statusStraniero = statusStraniero;
	}

}
