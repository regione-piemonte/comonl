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


public class RicercaDelega {
	
	private String cfDelegante;
	private String cfDelegato;
	private String cognomeDelegante;
	private String cognomeDelegato;
	private String nomeDelegante;
	private String nomeDelegato;
	private String cfImpresa;
	private String denominazione;
	private StatoDelega statoDelega;
	public String getCfDelegante() {
		return cfDelegante;
	}
	public void setCfDelegante(String cfDelegante) {
		this.cfDelegante = cfDelegante;
	}
	public String getCfDelegato() {
		return cfDelegato;
	}
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}
	public String getCognomeDelegante() {
		return cognomeDelegante;
	}
	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}
	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}
	public String getNomeDelegante() {
		return nomeDelegante;
	}
	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}
	public String getNomeDelegato() {
		return nomeDelegato;
	}
	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}
	public String getCfImpresa() {
		return cfImpresa;
	}
	public void setCfImpresa(String cfImpresa) {
		this.cfImpresa = cfImpresa;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public StatoDelega getStatoDelega() {
		return statoDelega;
	}
	public void setStatoDelega(StatoDelega statoDelega) {
		this.statoDelega = statoDelega;
	}
	@Override
	public String toString() {
		return "RicercaDelega [cfDelegante=" + cfDelegante + ", cfDelegato=" + cfDelegato + ", cognomeDelegante="
				+ cognomeDelegante + ", cognomeDelegato=" + cognomeDelegato + ", nomeDelegante=" + nomeDelegante
				+ ", nomeDelegato=" + nomeDelegato + ", cfImpresa=" + cfImpresa + ", denominazione=" + denominazione
				+ ", statoDelega=" + statoDelega + "]";
	}
	
}
