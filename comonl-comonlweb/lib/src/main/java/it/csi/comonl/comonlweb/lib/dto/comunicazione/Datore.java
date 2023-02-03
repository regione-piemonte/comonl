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
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;

/**
 * The Class Datore.
 */
public class Datore extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private NaturaGiuridica naturaGiuridica;
	private String codiceFiscale;
	private String dsCognome;
	private String dsDenominazioneDatore;
	private String dsNome;
	private String dsVariazioneRagSociale;
	private Date dtFineContrattoSom;
	private Date dtInizioContrattoSom;
	private String flgAzArtigiana;
	private String flgDenomSilpVariata;
	private String flgNoAaep;
	private String flgPubAmm;
	private String flgUtilEstera;
	private BigDecimal idAziendaSilp;
	private String matricolaInps;
	private String numAgenziaSommin;
	private BigDecimal numContrSomm;
	private String numeroIscrizioneAlbo;
	private String partitaIva;
	private String patInail;
	private Atecofin atecofin;
	
	/* oggetti collegati al datore */
	private LegaleRappr legaleRappr;
	private SedeLavoro sedeLegale;
	private SedeLavoro sedeOperativa;
	

	
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
	 * @return the dsCognome
	 */
	public String getDsCognome() {
		return dsCognome;
	}
	
	/**
	 * @param dsCognome the dsCognome to set
	 */
	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	/**
	 * @return the dsDenominazioneDatore
	 */
	public String getDsDenominazioneDatore() {
		return dsDenominazioneDatore;
	}
	
	/**
	 * @param dsDenominazioneDatore the dsDenominazioneDatore to set
	 */
	public void setDsDenominazioneDatore(String dsDenominazioneDatore) {
		this.dsDenominazioneDatore = dsDenominazioneDatore;
	}

	/**
	 * @return the dsNome
	 */
	public String getDsNome() {
		return dsNome;
	}
	
	/**
	 * @param dsNome the dsNome to set
	 */
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	/**
	 * @return the dsVariazioneRagSociale
	 */
	public String getDsVariazioneRagSociale() {
		return dsVariazioneRagSociale;
	}
	
	/**
	 * @param dsVariazioneRagSociale the dsVariazioneRagSociale to set
	 */
	public void setDsVariazioneRagSociale(String dsVariazioneRagSociale) {
		this.dsVariazioneRagSociale = dsVariazioneRagSociale;
	}

	/**
	 * @return the dtFineContrattoSom
	 */
	public Date getDtFineContrattoSom() {
		return dtFineContrattoSom;
	}
	
	/**
	 * @param dtFineContrattoSom the dtFineContrattoSom to set
	 */
	public void setDtFineContrattoSom(Date dtFineContrattoSom) {
		this.dtFineContrattoSom = dtFineContrattoSom;
	}

	/**
	 * @return the dtInizioContrattoSom
	 */
	public Date getDtInizioContrattoSom() {
		return dtInizioContrattoSom;
	}
	
	/**
	 * @param dtInizioContrattoSom the dtInizioContrattoSom to set
	 */
	public void setDtInizioContrattoSom(Date dtInizioContrattoSom) {
		this.dtInizioContrattoSom = dtInizioContrattoSom;
	}

	/**
	 * @return the flgAzArtigiana
	 */
	public String getFlgAzArtigiana() {
		return flgAzArtigiana;
	}
	
	/**
	 * @param flgAzArtigiana the flgAzArtigiana to set
	 */
	public void setFlgAzArtigiana(String flgAzArtigiana) {
		this.flgAzArtigiana = flgAzArtigiana;
	}

	/**
	 * @return the flgDenomSilpVariata
	 */
	public String getFlgDenomSilpVariata() {
		return flgDenomSilpVariata;
	}
	
	/**
	 * @param flgDenomSilpVariata the flgDenomSilpVariata to set
	 */
	public void setFlgDenomSilpVariata(String flgDenomSilpVariata) {
		this.flgDenomSilpVariata = flgDenomSilpVariata;
	}

	/**
	 * @return the flgNoAaep
	 */
	public String getFlgNoAaep() {
		return flgNoAaep;
	}
	
	/**
	 * @param flgNoAaep the flgNoAaep to set
	 */
	public void setFlgNoAaep(String flgNoAaep) {
		this.flgNoAaep = flgNoAaep;
	}

	/**
	 * @return the flgPubAmm
	 */
	public String getFlgPubAmm() {
		return flgPubAmm;
	}
	
	/**
	 * @param flgPubAmm the flgPubAmm to set
	 */
	public void setFlgPubAmm(String flgPubAmm) {
		this.flgPubAmm = flgPubAmm;
	}

	/**
	 * @return the flgUtilEstera
	 */
	public String getFlgUtilEstera() {
		return flgUtilEstera;
	}
	
	/**
	 * @param flgUtilEstera the flgUtilEstera to set
	 */
	public void setFlgUtilEstera(String flgUtilEstera) {
		this.flgUtilEstera = flgUtilEstera;
	}

	/**
	 * @return the idAziendaSilp
	 */
	public BigDecimal getIdAziendaSilp() {
		return idAziendaSilp;
	}
	
	/**
	 * @param idAziendaSilp the idAziendaSilp to set
	 */
	public void setIdAziendaSilp(BigDecimal idAziendaSilp) {
		this.idAziendaSilp = idAziendaSilp;
	}

	/**
	 * @return the matricolaInps
	 */
	public String getMatricolaInps() {
		return matricolaInps;
	}
	
	/**
	 * @param matricolaInps the matricolaInps to set
	 */
	public void setMatricolaInps(String matricolaInps) {
		this.matricolaInps = matricolaInps;
	}

	/**
	 * @return the numAgenziaSommin
	 */
	public String getNumAgenziaSommin() {
		return numAgenziaSommin;
	}
	
	/**
	 * @param numAgenziaSommin the numAgenziaSommin to set
	 */
	public void setNumAgenziaSommin(String numAgenziaSommin) {
		this.numAgenziaSommin = numAgenziaSommin;
	}

	/**
	 * @return the numContrSomm
	 */
	public BigDecimal getNumContrSomm() {
		return numContrSomm;
	}
	
	/**
	 * @param numContrSomm the numContrSomm to set
	 */
	public void setNumContrSomm(BigDecimal numContrSomm) {
		this.numContrSomm = numContrSomm;
	}

	/**
	 * @return the numeroIscrizioneAlbo
	 */
	public String getNumeroIscrizioneAlbo() {
		return numeroIscrizioneAlbo;
	}
	
	/**
	 * @param numeroIscrizioneAlbo the numeroIscrizioneAlbo to set
	 */
	public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
		this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
	}

	/**
	 * @return the partitaIva
	 */
	public String getPartitaIva() {
		return partitaIva;
	}
	
	/**
	 * @param partitaIva the partitaIva to set
	 */
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	/**
	 * @return the patInail
	 */
	public String getPatInail() {
		if (patInail != null)
			return patInail.trim();
		return patInail;
	}
	
	/**
	 * @param patInail the patInail to set
	 */
	public void setPatInail(String patInail) {
		this.patInail = patInail;
	}

	/**
	 * @return the atecofin
	 */
	public Atecofin getAtecofin() {
		return atecofin;
	}
	
	/**
	 * @param atecofin the atecofin to set
	 */
	public void setAtecofin(Atecofin atecofin) {
		this.atecofin = atecofin;
	}

	public LegaleRappr getLegaleRappr() {
		return legaleRappr;
	}

	public void setLegaleRappr(LegaleRappr legaleRappr) {
		this.legaleRappr = legaleRappr;
	}


	public SedeLavoro getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(SedeLavoro sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public SedeLavoro getSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(SedeLavoro sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public NaturaGiuridica getNaturaGiuridica() {
		return naturaGiuridica;
	}

	public void setNaturaGiuridica(NaturaGiuridica naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	
	
	
}
