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

import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;

/**
 * The Class AnagraficaDatore.
 */
public class AnagraficaDatore extends BaseDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String cNaturaGiuridica;
	private String codiceFiscale;
	private String dsDenominazioneDatore;
	private String dsVariazioneRagSociale;
	private String flgAzArtigiana;
	private String flgNoAaep;
	private String matricolaInps;
	private String numAgenziaSommin;
	private String numeroIscrizioneAlbo;
	private String partitaIva;
	private Atecofin atecofin;

	/**
	 * @return the cNaturaGiuridica
	 */
	public String getCNaturaGiuridica() {
		return cNaturaGiuridica;
	}
	
	/**
	 * @param cNaturaGiuridica the cNaturaGiuridica to set
	 */
	public void setCNaturaGiuridica(String cNaturaGiuridica) {
		this.cNaturaGiuridica = cNaturaGiuridica;
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

}
