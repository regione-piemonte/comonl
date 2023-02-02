/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;

public class Profilo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cfUtente;

	private String denominazioneAzienda;

	private String ruolo;

	private String codiceFiscale;

	private String cognome;

	private String nome;

	private boolean flgImpresaAccentrata;

	private List<String> listaCasiUso;

	private String email;
	private String codiceEnteOperatoreApl;

	private TipoSoggettoAbilitato tipoSoggettoAbilitato;

	// serve per la visulizzazione nella form
	Map<String, String> radio = new HashMap<>();

	public boolean isActorInUseCase(String useCase) {
		if (listaCasiUso != null) {
			for (int i = 0; i < listaCasiUso.size(); i++) {
				String caso = listaCasiUso.get(i);
				if (useCase.equalsIgnoreCase(caso)) {
					return true;
				}
			}
		}

		return false;
	}

	public String getCfUtente() {
		return cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getDenominazioneAzienda() {
		return denominazioneAzienda;
	}

	public void setDenominazioneAzienda(String denominazioneAzienda) {
		this.denominazioneAzienda = denominazioneAzienda;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Map<String, String> getRadio() {
		return radio;
	}

	public void setRadio(Map<String, String> radio) {
		this.radio = radio;
	}

	public List<String> getListaCasiUso() {
		return listaCasiUso;
	}

	public void setListaCasiUso(List<String> listaCasiUso) {
		this.listaCasiUso = listaCasiUso;
	}

	@Override
	public String toString() {
		return "Profilo [cfUtente=" + cfUtente + ", denominazioneAzienda=" + denominazioneAzienda + ", ruolo=" + ruolo
				+ ", codiceFiscale=" + codiceFiscale + ", cognome=" + cognome + ", nome=" + nome + ", listaCasiUso="
				+ listaCasiUso + ", radio=" + radio + "]";
	}

	public boolean isFlgImpresaAccentrata() {
		return flgImpresaAccentrata;
	}

	public void setFlgImpresaAccentrata(boolean flgImpresaAccentrata) {
		this.flgImpresaAccentrata = flgImpresaAccentrata;
	}

	public String getCodiceEnteOperatoreApl() {
		return codiceEnteOperatoreApl;
	}

	public void setCodiceEnteOperatoreApl(String codiceEnteOperatoreApl) {
		this.codiceEnteOperatoreApl = codiceEnteOperatoreApl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoSoggettoAbilitato getTipoSoggettoAbilitato() {
		return tipoSoggettoAbilitato;
	}

	public void setTipoSoggettoAbilitato(TipoSoggettoAbilitato tipoSoggettoAbilitato) {
		this.tipoSoggettoAbilitato = tipoSoggettoAbilitato;
	}

}
