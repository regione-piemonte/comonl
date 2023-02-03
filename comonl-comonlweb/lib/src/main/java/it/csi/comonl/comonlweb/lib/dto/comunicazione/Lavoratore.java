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
import java.math.BigDecimal;
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
 * The Class Lavoratore.
 */
public class Lavoratore extends BaseDto<Long> implements Serializable {

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
	private String flgRimborsoRimpatrio;
	private String flgSistAlloggiativa;
	private BigDecimal idLavoratoreSilp;
	private String nome;
	private String numeroDocumento;
	private String sesso;
	private String statoCivile;
	private String telDom;
	private Cittadinanza cittadinanza;
	private Comune comuneDom;
	private Comune comuneRes;
	private Comune comuneNasc;
	private LivelloStudio livelloStudio;
	private MotivoPermesso motivoPermesso;
	private Questura questura;
	private StatiEsteri statiEsteriDom;
	private StatiEsteri statiEsteriNasc;
	private StatiEsteri statiEsteriRes;
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
	 * @return the flgRimborsoRimpatrio
	 */
	public String getFlgRimborsoRimpatrio() {
		return flgRimborsoRimpatrio;
	}
	
	/**
	 * @param flgRimborsoRimpatrio the flgRimborsoRimpatrio to set
	 */
	public void setFlgRimborsoRimpatrio(String flgRimborsoRimpatrio) {
		this.flgRimborsoRimpatrio = flgRimborsoRimpatrio;
	}

	/**
	 * @return the flgSistAlloggiativa
	 */
	public String getFlgSistAlloggiativa() {
		return flgSistAlloggiativa;
	}
	
	/**
	 * @param flgSistAlloggiativa the flgSistAlloggiativa to set
	 */
	public void setFlgSistAlloggiativa(String flgSistAlloggiativa) {
		this.flgSistAlloggiativa = flgSistAlloggiativa;
	}

	/**
	 * @return the idLavoratoreSilp
	 */
	public BigDecimal getIdLavoratoreSilp() {
		return idLavoratoreSilp;
	}
	
	/**
	 * @param idLavoratoreSilp the idLavoratoreSilp to set
	 */
	public void setIdLavoratoreSilp(BigDecimal idLavoratoreSilp) {
		this.idLavoratoreSilp = idLavoratoreSilp;
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

	public StatiEsteri getStatiEsteriDom() {
		return statiEsteriDom;
	}

	public void setStatiEsteriDom(StatiEsteri statiEsteriDom) {
		this.statiEsteriDom = statiEsteriDom;
	}

	public StatiEsteri getStatiEsteriNasc() {
		return statiEsteriNasc;
	}

	public void setStatiEsteriNasc(StatiEsteri statiEsteriNasc) {
		this.statiEsteriNasc = statiEsteriNasc;
	}

	public StatiEsteri getStatiEsteriRes() {
		return statiEsteriRes;
	}

	public void setStatiEsteriRes(StatiEsteri statiEsteriRes) {
		this.statiEsteriRes = statiEsteriRes;
	}

	@Override
	public String toString() {
		return "Lavoratore [codCapDom=" + codCapDom + ", codCapRes=" + codCapRes + ", codiceFiscale=" + codiceFiscale
				+ ", cognome=" + cognome + ", dsIndirizzoDom=" + dsIndirizzoDom + ", dsIndirizzoRes=" + dsIndirizzoRes
				+ ", dtNascita=" + dtNascita + ", dtScadenzaPermessoSogg=" + dtScadenzaPermessoSogg
				+ ", flgRimborsoRimpatrio=" + flgRimborsoRimpatrio + ", flgSistAlloggiativa=" + flgSistAlloggiativa
				+ ", idLavoratoreSilp=" + idLavoratoreSilp + ", nome=" + nome + ", numeroDocumento=" + numeroDocumento
				+ ", sesso=" + sesso + ", statoCivile=" + statoCivile + ", telDom=" + telDom + ", cittadinanza="
				+ cittadinanza + ", comuneDom=" + comuneDom + ", comuneRes=" + comuneRes + ", comuneNasc=" + comuneNasc
				+ ", livelloStudio=" + livelloStudio + ", motivoPermesso=" + motivoPermesso + ", questura=" + questura
				+ ", statiEsteriDom=" + statiEsteriDom + ", statiEsteriNasc=" + statiEsteriNasc + ", statiEsteriRes="
				+ statiEsteriRes + ", statusStraniero=" + statusStraniero + "]";
	}
	
	
	
}
