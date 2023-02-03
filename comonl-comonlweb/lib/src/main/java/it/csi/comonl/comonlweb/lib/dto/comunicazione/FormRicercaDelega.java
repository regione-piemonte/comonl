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

import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;


public class FormRicercaDelega {
	private String codiceFiscaleDelegato;
	private String cognomeDelegato;
	private String nomeDelegato;
	private String codiceFiscaleDelegante;
	private String cognomeDelegante;
	private String nomeDelegante;
	private String codiceFiscaleImpresa;
	private String denominazione;
	private List<StatoDelega> statoDelegas;
	
	public String getCodiceFiscaleDelegato() {
		return codiceFiscaleDelegato;
	}
	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}
	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}
	public String getNomeDelegato() {
		return nomeDelegato;
	}
	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}
	public String getCodiceFiscaleDelegante() {
		return codiceFiscaleDelegante;
	}
	public void setCodiceFiscaleDelegante(String codiceFiscaleDelegante) {
		this.codiceFiscaleDelegante = codiceFiscaleDelegante;
	}
	public String getCognomeDelegante() {
		return cognomeDelegante;
	}
	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}
	public String getNomeDelegante() {
		return nomeDelegante;
	}
	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}
	public String getCodiceFiscaleImpresa() {
		return codiceFiscaleImpresa;
	}
	public void setCodiceFiscaleImpresa(String codiceFiscaleImpresa) {
		this.codiceFiscaleImpresa = codiceFiscaleImpresa;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public List<StatoDelega> getStatoDelegas() {
		return statoDelegas;
	}
	public void setStatoDelegas(List<StatoDelega> statoDelegas) {
		this.statoDelegas = statoDelegas;
	}
	@Override
	public String toString() {
		return "FormRicercaDelega [codiceFiscaleDelegato=" + codiceFiscaleDelegato + ", cognomeDelegato="
				+ cognomeDelegato + ", nomeDelegato=" + nomeDelegato + ", codiceFiscaleDelegante="
				+ codiceFiscaleDelegante + ", cognomeDelegante=" + cognomeDelegante + ", nomeDelegante=" + nomeDelegante
				+ ", codiceFiscaleImpresa=" + codiceFiscaleImpresa + ", denominazione=" + denominazione
				+ ", StatoDelega=" + statoDelegas + "]";
	}
	
	
}
