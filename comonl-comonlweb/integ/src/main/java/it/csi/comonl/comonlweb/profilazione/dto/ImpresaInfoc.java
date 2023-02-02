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

public class ImpresaInfoc implements Serializable {

  private static final long serialVersionUID = 1L;

  private String annoDenunciaAddetti;

  private String codCausCessaz;

  private String codCausCessazFunSede;

  private String codFonte;

  private String codiceFiscale;

  private String codNaturaGiuridica;

  private String dataCancellazRea;

  private String dataCessaz;

  private String dataCessazFunSede;

  private String dataDenunciaCessaz;

  private String dataDlbIscrAlboArtigiano;

  private String dataInizioAtt;

  private String dataIscrizioneRea;

  private String dataIscrRegistroImpr;

  private String dataUltimoAggiorn;

  private String dataUltimoAggRi;

  private String descrCausCessaz;

  private String descrCausCessazFunSede;

  private String descrFonte;

  private String descrIndicStatoAttiv;

  private String descrIndicTrasfSede;

  private String descrIterIscrAlboArt;

  private String descrNaturaGiuridica;

  private String flgAggiornamento;

  private String flgIterIscrAlboArt;

  private String idAAEPAziendaSL;

  private String idAAEPFonteDatoSL;

  private String impresaCessata;

  private String indicStatoAttiv;

  private String indicTrasfSede;

  private List<ListaSediInfoc> listaSediInfoc;

  private String localizzazPiemonte;

  private String numAddettiFam;

  private String numAddettiSubord;

  private String numeroIscrAlboArtigiano;

  private String numIscrizRea;

  private String numIscrizReaSede;

  private String numRegistroImpr;

  private String partitaIva;

  private String progrSedeSL;

  private String provinciaIscrAlboArtigiano;

  private String ragioneSociale;

  private String siglaProvRea;

  private String siglaProvReaSede;

  public String getAnnoDenunciaAddetti() {
    return annoDenunciaAddetti;
  }

  public void setAnnoDenunciaAddetti(String annoDenunciaAddetti) {
    this.annoDenunciaAddetti = annoDenunciaAddetti;
  }

  public String getCodCausCessaz() {
    return codCausCessaz;
  }

  public void setCodCausCessaz(String codCausCessaz) {
    this.codCausCessaz = codCausCessaz;
  }

  public String getCodCausCessazFunSede() {
    return codCausCessazFunSede;
  }

  public void setCodCausCessazFunSede(String codCausCessazFunSede) {
    this.codCausCessazFunSede = codCausCessazFunSede;
  }

  public String getCodFonte() {
    return codFonte;
  }

  public void setCodFonte(String codFonte) {
    this.codFonte = codFonte;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getCodNaturaGiuridica() {
    return codNaturaGiuridica;
  }

  public void setCodNaturaGiuridica(String codNaturaGiuridica) {
    this.codNaturaGiuridica = codNaturaGiuridica;
  }

  public String getDataCancellazRea() {
    return dataCancellazRea;
  }

  public void setDataCancellazRea(String dataCancellazRea) {
    this.dataCancellazRea = dataCancellazRea;
  }

  public String getDataCessaz() {
    return dataCessaz;
  }

  public void setDataCessaz(String dataCessaz) {
    this.dataCessaz = dataCessaz;
  }

  public String getDataCessazFunSede() {
    return dataCessazFunSede;
  }

  public void setDataCessazFunSede(String dataCessazFunSede) {
    this.dataCessazFunSede = dataCessazFunSede;
  }

  public String getDataDenunciaCessaz() {
    return dataDenunciaCessaz;
  }

  public void setDataDenunciaCessaz(String dataDenunciaCessaz) {
    this.dataDenunciaCessaz = dataDenunciaCessaz;
  }

  public String getDataDlbIscrAlboArtigiano() {
    return dataDlbIscrAlboArtigiano;
  }

  public void setDataDlbIscrAlboArtigiano(String dataDlbIscrAlboArtigiano) {
    this.dataDlbIscrAlboArtigiano = dataDlbIscrAlboArtigiano;
  }

  public String getDataInizioAtt() {
    return dataInizioAtt;
  }

  public void setDataInizioAtt(String dataInizioAtt) {
    this.dataInizioAtt = dataInizioAtt;
  }

  public String getDataIscrizioneRea() {
    return dataIscrizioneRea;
  }

  public void setDataIscrizioneRea(String dataIscrizioneRea) {
    this.dataIscrizioneRea = dataIscrizioneRea;
  }

  public String getDataIscrRegistroImpr() {
    return dataIscrRegistroImpr;
  }

  public void setDataIscrRegistroImpr(String dataIscrRegistroImpr) {
    this.dataIscrRegistroImpr = dataIscrRegistroImpr;
  }

  public String getDataUltimoAggiorn() {
    return dataUltimoAggiorn;
  }

  public void setDataUltimoAggiorn(String dataUltimoAggiorn) {
    this.dataUltimoAggiorn = dataUltimoAggiorn;
  }

  public String getDataUltimoAggRi() {
    return dataUltimoAggRi;
  }

  public void setDataUltimoAggRi(String dataUltimoAggRi) {
    this.dataUltimoAggRi = dataUltimoAggRi;
  }

  public String getDescrCausCessaz() {
    return descrCausCessaz;
  }

  public void setDescrCausCessaz(String descrCausCessaz) {
    this.descrCausCessaz = descrCausCessaz;
  }

  public String getDescrCausCessazFunSede() {
    return descrCausCessazFunSede;
  }

  public void setDescrCausCessazFunSede(String descrCausCessazFunSede) {
    this.descrCausCessazFunSede = descrCausCessazFunSede;
  }

  public String getDescrFonte() {
    return descrFonte;
  }

  public void setDescrFonte(String descrFonte) {
    this.descrFonte = descrFonte;
  }

  public String getDescrIndicStatoAttiv() {
    return descrIndicStatoAttiv;
  }

  public void setDescrIndicStatoAttiv(String descrIndicStatoAttiv) {
    this.descrIndicStatoAttiv = descrIndicStatoAttiv;
  }

  public String getDescrIndicTrasfSede() {
    return descrIndicTrasfSede;
  }

  public void setDescrIndicTrasfSede(String descrIndicTrasfSede) {
    this.descrIndicTrasfSede = descrIndicTrasfSede;
  }

  public String getDescrIterIscrAlboArt() {
    return descrIterIscrAlboArt;
  }

  public void setDescrIterIscrAlboArt(String descrIterIscrAlboArt) {
    this.descrIterIscrAlboArt = descrIterIscrAlboArt;
  }

  public String getDescrNaturaGiuridica() {
    return descrNaturaGiuridica;
  }

  public void setDescrNaturaGiuridica(String descrNaturaGiuridica) {
    this.descrNaturaGiuridica = descrNaturaGiuridica;
  }

  public String getFlgAggiornamento() {
    return flgAggiornamento;
  }

  public void setFlgAggiornamento(String flgAggiornamento) {
    this.flgAggiornamento = flgAggiornamento;
  }

  public String getFlgIterIscrAlboArt() {
    return flgIterIscrAlboArt;
  }

  public void setFlgIterIscrAlboArt(String flgIterIscrAlboArt) {
    this.flgIterIscrAlboArt = flgIterIscrAlboArt;
  }

  public String getIdAAEPAziendaSL() {
    return idAAEPAziendaSL;
  }

  public void setIdAAEPAziendaSL(String idAAEPAziendaSL) {
    this.idAAEPAziendaSL = idAAEPAziendaSL;
  }

  public String getIdAAEPFonteDatoSL() {
    return idAAEPFonteDatoSL;
  }

  public void setIdAAEPFonteDatoSL(String idAAEPFonteDatoSL) {
    this.idAAEPFonteDatoSL = idAAEPFonteDatoSL;
  }

  public String getImpresaCessata() {
    return impresaCessata;
  }

  public void setImpresaCessata(String impresaCessata) {
    this.impresaCessata = impresaCessata;
  }

  public String getIndicStatoAttiv() {
    return indicStatoAttiv;
  }

  public void setIndicStatoAttiv(String indicStatoAttiv) {
    this.indicStatoAttiv = indicStatoAttiv;
  }

  public String getIndicTrasfSede() {
    return indicTrasfSede;
  }

  public void setIndicTrasfSede(String indicTrasfSede) {
    this.indicTrasfSede = indicTrasfSede;
  }

  public List<ListaSediInfoc> getListaSediInfoc() {
    return listaSediInfoc;
  }

  public void setListaSediInfoc(List<ListaSediInfoc> listaSediInfoc) {
    this.listaSediInfoc = listaSediInfoc;
  }

  public String getLocalizzazPiemonte() {
    return localizzazPiemonte;
  }

  public void setLocalizzazPiemonte(String localizzazPiemonte) {
    this.localizzazPiemonte = localizzazPiemonte;
  }

  public String getNumAddettiFam() {
    return numAddettiFam;
  }

  public void setNumAddettiFam(String numAddettiFam) {
    this.numAddettiFam = numAddettiFam;
  }

  public String getNumAddettiSubord() {
    return numAddettiSubord;
  }

  public void setNumAddettiSubord(String numAddettiSubord) {
    this.numAddettiSubord = numAddettiSubord;
  }

  public String getNumeroIscrAlboArtigiano() {
    return numeroIscrAlboArtigiano;
  }

  public void setNumeroIscrAlboArtigiano(String numeroIscrAlboArtigiano) {
    this.numeroIscrAlboArtigiano = numeroIscrAlboArtigiano;
  }

  public String getNumIscrizRea() {
    return numIscrizRea;
  }

  public void setNumIscrizRea(String numIscrizRea) {
    this.numIscrizRea = numIscrizRea;
  }

  public String getNumIscrizReaSede() {
    return numIscrizReaSede;
  }

  public void setNumIscrizReaSede(String numIscrizReaSede) {
    this.numIscrizReaSede = numIscrizReaSede;
  }

  public String getNumRegistroImpr() {
    return numRegistroImpr;
  }

  public void setNumRegistroImpr(String numRegistroImpr) {
    this.numRegistroImpr = numRegistroImpr;
  }

  public String getPartitaIva() {
    return partitaIva;
  }

  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }

  public String getProgrSedeSL() {
    return progrSedeSL;
  }

  public void setProgrSedeSL(String progrSedeSL) {
    this.progrSedeSL = progrSedeSL;
  }

  public String getProvinciaIscrAlboArtigiano() {
    return provinciaIscrAlboArtigiano;
  }

  public void setProvinciaIscrAlboArtigiano(String provinciaIscrAlboArtigiano) {
    this.provinciaIscrAlboArtigiano = provinciaIscrAlboArtigiano;
  }

  public String getRagioneSociale() {
    return ragioneSociale;
  }

  public void setRagioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
  }

  public String getSiglaProvRea() {
    return siglaProvRea;
  }

  public void setSiglaProvRea(String siglaProvRea) {
    this.siglaProvRea = siglaProvRea;
  }

  public String getSiglaProvReaSede() {
    return siglaProvReaSede;
  }

  public void setSiglaProvReaSede(String siglaProvReaSede) {
    this.siglaProvReaSede = siglaProvReaSede;
  }

  @Override
  public String toString() {
    return "ImpresaInfoc [annoDenunciaAddetti=" + annoDenunciaAddetti + ", codCausCessaz=" + codCausCessaz + ", codCausCessazFunSede=" + codCausCessazFunSede + ", codFonte=" + codFonte + ", codiceFiscale=" + codiceFiscale + ", codNaturaGiuridica=" + codNaturaGiuridica + ", dataCancellazRea="
        + dataCancellazRea + ", dataCessaz=" + dataCessaz + ", dataCessazFunSede=" + dataCessazFunSede + ", dataDenunciaCessaz=" + dataDenunciaCessaz + ", dataDlbIscrAlboArtigiano=" + dataDlbIscrAlboArtigiano + ", dataInizioAtt=" + dataInizioAtt + ", dataIscrizioneRea=" + dataIscrizioneRea
        + ", dataIscrRegistroImpr=" + dataIscrRegistroImpr + ", dataUltimoAggiorn=" + dataUltimoAggiorn + ", dataUltimoAggRi=" + dataUltimoAggRi + ", descrCausCessaz=" + descrCausCessaz + ", descrCausCessazFunSede=" + descrCausCessazFunSede + ", descrFonte=" + descrFonte + ", descrIndicStatoAttiv="
        + descrIndicStatoAttiv + ", descrIndicTrasfSede=" + descrIndicTrasfSede + ", descrIterIscrAlboArt=" + descrIterIscrAlboArt + ", descrNaturaGiuridica=" + descrNaturaGiuridica + ", flgAggiornamento=" + flgAggiornamento + ", flgIterIscrAlboArt=" + flgIterIscrAlboArt + ", idAAEPAziendaSL="
        + idAAEPAziendaSL + ", idAAEPFonteDatoSL=" + idAAEPFonteDatoSL + ", impresaCessata=" + impresaCessata + ", indicStatoAttiv=" + indicStatoAttiv + ", indicTrasfSede=" + indicTrasfSede + ", listaSediInfoc=" + listaSediInfoc + ", localizzazPiemonte=" + localizzazPiemonte + ", numAddettiFam="
        + numAddettiFam + ", numAddettiSubord=" + numAddettiSubord + ", numeroIscrAlboArtigiano=" + numeroIscrAlboArtigiano + ", numIscrizRea=" + numIscrizRea + ", numIscrizReaSede=" + numIscrizReaSede + ", numRegistroImpr=" + numRegistroImpr + ", partitaIva=" + partitaIva + ", progrSedeSL="
        + progrSedeSL + ", provinciaIscrAlboArtigiano=" + provinciaIscrAlboArtigiano + ", ragioneSociale=" + ragioneSociale + ", siglaProvRea=" + siglaProvRea + ", siglaProvReaSede=" + siglaProvReaSede + "]";
  }


}
