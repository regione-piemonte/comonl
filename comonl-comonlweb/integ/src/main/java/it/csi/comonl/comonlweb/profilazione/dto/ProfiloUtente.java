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
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;

public class ProfiloUtente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceFiscaleUtente;

	private String ruoloUtente;

	private String dsNome;

	private String dsCognome;

	private List<ImpresaInfoc> listaAziendeAAEP;

	private List<RuoloIrideListaCasiUso> listRuoliIride;

	private List<DelegatoImpresa> listaProfiliPersonaAutorizzata;
	private List<DelegatoImpresa> listaProfiliDelegatoResponsabile;
	private List<AnagraficaDelegato> listaProfiliConsulenteResponsabile;

//  public List<ProfileComonl> listaProfiliComonl;

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public String getRuoloUtente() {
		return ruoloUtente;
	}

	public void setRuoloUtente(String ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsCognome() {
		return dsCognome;
	}

	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	public List<ImpresaInfoc> getListaAziendeAAEP() {
		return listaAziendeAAEP;
	}

	public void setListaAziendeAAEP(List<ImpresaInfoc> listaAziendeAAEP) {
		this.listaAziendeAAEP = listaAziendeAAEP;
	}

	public List<RuoloIrideListaCasiUso> getListRuoliIride() {
		return listRuoliIride;
	}

	public void setListRuoliIride(List<RuoloIrideListaCasiUso> listRuoliIride) {
		this.listRuoliIride = listRuoliIride;
	}

//  public List<ProfileComonl> getListaProfiliComonl() {
//    return listaProfiliComonl;
//  }
//
//  public void setListaProfiliComonl(List<ProfileComonl> listaProfiliComonl) {
//    this.listaProfiliComonl = listaProfiliComonl;
//  }

	@Override
	public String toString() {
		return "ProfiloUtente [codiceFiscaleUtente=" + codiceFiscaleUtente + ", ruoloUtente=" + ruoloUtente
				+ ", dsNome=" + dsNome + ", dsCognome=" + dsCognome + ", listaAziendeAAEP=" + listaAziendeAAEP
				+ ", listRuoliIride=" + listRuoliIride + ", listaProfiliDelegatoResponsabile="
				+ listaProfiliDelegatoResponsabile + ", listaProfiliPersonaAutorizzata="
				+ listaProfiliPersonaAutorizzata + ", listaProfiliConsulenteResponsabile="
				+ listaProfiliConsulenteResponsabile + "]";
	}

	public List<DelegatoImpresa> getListaProfiliPersonaAutorizzata() {
		return listaProfiliPersonaAutorizzata;
	}

	public void setListaProfiliPersonaAutorizzata(List<DelegatoImpresa> listaProfiliPersonaAutorizzata) {
		this.listaProfiliPersonaAutorizzata = listaProfiliPersonaAutorizzata;
	}

	public List<DelegatoImpresa> getListaProfiliDelegatoResponsabile() {
		return listaProfiliDelegatoResponsabile;
	}

	public void setListaProfiliDelegatoResponsabile(List<DelegatoImpresa> listaProfiliDelegatoResponsabile) {
		this.listaProfiliDelegatoResponsabile = listaProfiliDelegatoResponsabile;
	}

	public List<AnagraficaDelegato> getListaProfiliConsulenteResponsabile() {
		return listaProfiliConsulenteResponsabile;
	}

	public void setListaProfiliConsulenteResponsabile(List<AnagraficaDelegato> listaProfiliConsulenteResponsabile) {
		this.listaProfiliConsulenteResponsabile = listaProfiliConsulenteResponsabile;
	}

}
