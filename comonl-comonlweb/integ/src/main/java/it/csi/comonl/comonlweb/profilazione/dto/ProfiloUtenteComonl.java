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

import java.util.ArrayList;
import java.util.List;

public class ProfiloUtenteComonl {

  // ruoli tornati da iride - AAEP - comonl
  private ProfiloUtente profiloOrchestratore;

  // lista dei ruoli da presentare alla sceltaRuolo
  private List<Profilo> listaRuoliAmmessi;

  // ruolo effettivo
  Profilo profilo;

  public ProfiloUtenteComonl() {
  }

  public ProfiloUtenteComonl(ProfiloUtente profiloOrchestratore) {
    this.profiloOrchestratore = profiloOrchestratore;
  }

  public ProfiloUtente getProfiloOrchestratore() {
    return profiloOrchestratore;
  }

  public void setProfiloOrchestratore(ProfiloUtente profiloOrchestratore) {
    this.profiloOrchestratore = profiloOrchestratore;
  }

  public List<Profilo> getListaRuoliAmmessi() {
    return listaRuoliAmmessi;
  }

  public void setListaRuoliAmmessi(List<Profilo> listaRuoliAmmessi) {
    this.listaRuoliAmmessi = listaRuoliAmmessi;
  }

  public Profilo getProfilo() {
    return profilo;
  }

  public void setProfilo(Profilo profilo) {
    this.profilo = profilo;
  }

  public void inizializzaListaRuoli() {
    listaRuoliAmmessi = new ArrayList<Profilo>();
  }

  public void addRuoloInLista(Profilo profilo) {
    if (listaRuoliAmmessi != null) {
      listaRuoliAmmessi.add(profilo);
    }
    else {
      inizializzaListaRuoli();
      listaRuoliAmmessi.add(profilo);
    }
  }

  @Override
  public String toString() {
    return "ProfiloUtenteComonl [profiloOrchestratore=" + profiloOrchestratore + ", listaRuoliAmmessi=" + listaRuoliAmmessi + ", profilo=" + profilo + "]";
  }

}
