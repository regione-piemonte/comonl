/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.dto;

import java.io.Serializable;

public class ListaSediInfoc implements Serializable {

  private static final long serialVersionUID = 1L;

  private String denominazioneSede;

  private String descrComuneUL;

  private String descrTipoLocalizzazione;

  private String idAAEPAzienda;

  private String idAAEPFonteDato;

  private String indirizzo;

  private String numIscrizREA;

  private String progrSede;

  private String siglaProvCCIAA;

  private String siglaProvUL;

  public String getDenominazioneSede() {
    return denominazioneSede;
  }

  public void setDenominazioneSede(String denominazioneSede) {
    this.denominazioneSede = denominazioneSede;
  }

  public String getDescrComuneUL() {
    return descrComuneUL;
  }

  public void setDescrComuneUL(String descrComuneUL) {
    this.descrComuneUL = descrComuneUL;
  }

  public String getDescrTipoLocalizzazione() {
    return descrTipoLocalizzazione;
  }

  public void setDescrTipoLocalizzazione(String descrTipoLocalizzazione) {
    this.descrTipoLocalizzazione = descrTipoLocalizzazione;
  }

  public String getIdAAEPAzienda() {
    return idAAEPAzienda;
  }

  public void setIdAAEPAzienda(String idAAEPAzienda) {
    this.idAAEPAzienda = idAAEPAzienda;
  }

  public String getIdAAEPFonteDato() {
    return idAAEPFonteDato;
  }

  public void setIdAAEPFonteDato(String idAAEPFonteDato) {
    this.idAAEPFonteDato = idAAEPFonteDato;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getNumIscrizREA() {
    return numIscrizREA;
  }

  public void setNumIscrizREA(String numIscrizREA) {
    this.numIscrizREA = numIscrizREA;
  }

  public String getProgrSede() {
    return progrSede;
  }

  public void setProgrSede(String progrSede) {
    this.progrSede = progrSede;
  }

  public String getSiglaProvCCIAA() {
    return siglaProvCCIAA;
  }

  public void setSiglaProvCCIAA(String siglaProvCCIAA) {
    this.siglaProvCCIAA = siglaProvCCIAA;
  }

  public String getSiglaProvUL() {
    return siglaProvUL;
  }

  public void setSiglaProvUL(String siglaProvUL) {
    this.siglaProvUL = siglaProvUL;
  }

  @Override
  public String toString() {
    return "ListaSediInfoc [denominazioneSede=" + denominazioneSede + ", descrComuneUL=" + descrComuneUL + ", descrTipoLocalizzazione=" + descrTipoLocalizzazione + ", idAAEPAzienda=" + idAAEPAzienda + ", idAAEPFonteDato=" + idAAEPFonteDato + ", indirizzo=" + indirizzo + ", numIscrizREA="
        + numIscrizREA + ", progrSede=" + progrSede + ", siglaProvCCIAA=" + siglaProvCCIAA + ", siglaProvUL=" + siglaProvUL + "]";
  }

}
