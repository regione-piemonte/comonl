/* **************************************************** */
/* Copyright Regione Piemonte - 2022					*/
/* SPDX-License-Identifier: EUPL-1.2-or-later			*/
/* **************************************************** */

package it.csi.comonl.comonlweb.lib.external.impl.silp.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.CallInfoParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.CommonParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.SedeParam;
import javax.validation.constraints.*;

public class AziendaParam extends CommonParam  {
  private String altreInformazioni = null;
  private String codAteco = null;
  private String codCcnl = null;
  private String codFiscale = null;
  private String codFiscaleOld = null;
  private String codNaturaGiuridica = null;
  private String codTipoAzienda = null;
  private String dsAnilt = null;
  private String flgArtigiana = null;
  private String flgMaster = null;
  private String flgPubblicaAmministrazione = null;
  private Long idAzienda = null;
  private String partitaIva = null;
  private String ragioneSociale = null;
  private SedeParam sede = null;

  /**
   **/
  
  @JsonProperty("altreInformazioni")
  public String getAltreInformazioni() {
    return altreInformazioni;
  }
  public void setAltreInformazioni(String altreInformazioni) {
    this.altreInformazioni = altreInformazioni;
  }

  /**
   **/
  
  @JsonProperty("codAteco")
  public String getCodAteco() {
    return codAteco;
  }
  public void setCodAteco(String codAteco) {
    this.codAteco = codAteco;
  }

  /**
   **/
  
  @JsonProperty("codCcnl")
  public String getCodCcnl() {
    return codCcnl;
  }
  public void setCodCcnl(String codCcnl) {
    this.codCcnl = codCcnl;
  }

  /**
   **/
  
  @JsonProperty("codFiscale")
  public String getCodFiscale() {
    return codFiscale;
  }
  public void setCodFiscale(String codFiscale) {
    this.codFiscale = codFiscale;
  }

  /**
   **/
  
  @JsonProperty("codFiscaleOld")
  public String getCodFiscaleOld() {
    return codFiscaleOld;
  }
  public void setCodFiscaleOld(String codFiscaleOld) {
    this.codFiscaleOld = codFiscaleOld;
  }

  /**
   **/
  
  @JsonProperty("codNaturaGiuridica")
  public String getCodNaturaGiuridica() {
    return codNaturaGiuridica;
  }
  public void setCodNaturaGiuridica(String codNaturaGiuridica) {
    this.codNaturaGiuridica = codNaturaGiuridica;
  }

  /**
   **/
  
  @JsonProperty("codTipoAzienda")
  public String getCodTipoAzienda() {
    return codTipoAzienda;
  }
  public void setCodTipoAzienda(String codTipoAzienda) {
    this.codTipoAzienda = codTipoAzienda;
  }

  /**
   **/
  
  @JsonProperty("dsAnilt")
  public String getDsAnilt() {
    return dsAnilt;
  }
  public void setDsAnilt(String dsAnilt) {
    this.dsAnilt = dsAnilt;
  }

  /**
   **/
  
  @JsonProperty("flgArtigiana")
  public String getFlgArtigiana() {
    return flgArtigiana;
  }
  public void setFlgArtigiana(String flgArtigiana) {
    this.flgArtigiana = flgArtigiana;
  }

  /**
   **/
  
  @JsonProperty("flgMaster")
  public String getFlgMaster() {
    return flgMaster;
  }
  public void setFlgMaster(String flgMaster) {
    this.flgMaster = flgMaster;
  }

  /**
   **/
  
  @JsonProperty("flgPubblicaAmministrazione")
  public String getFlgPubblicaAmministrazione() {
    return flgPubblicaAmministrazione;
  }
  public void setFlgPubblicaAmministrazione(String flgPubblicaAmministrazione) {
    this.flgPubblicaAmministrazione = flgPubblicaAmministrazione;
  }

  /**
   **/
  
  @JsonProperty("idAzienda")
  public Long getIdAzienda() {
    return idAzienda;
  }
  public void setIdAzienda(Long idAzienda) {
    this.idAzienda = idAzienda;
  }

  /**
   **/
  
  @JsonProperty("partitaIva")
  public String getPartitaIva() {
    return partitaIva;
  }
  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }

  /**
   **/
  
  @JsonProperty("ragioneSociale")
  public String getRagioneSociale() {
    return ragioneSociale;
  }
  public void setRagioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
  }

  /**
   **/
  
  @JsonProperty("sede")
  public SedeParam getSede() {
    return sede;
  }
  public void setSede(SedeParam sede) {
    this.sede = sede;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AziendaParam aziendaParam = (AziendaParam) o;
    return Objects.equals(altreInformazioni, aziendaParam.altreInformazioni) &&
        Objects.equals(codAteco, aziendaParam.codAteco) &&
        Objects.equals(codCcnl, aziendaParam.codCcnl) &&
        Objects.equals(codFiscale, aziendaParam.codFiscale) &&
        Objects.equals(codFiscaleOld, aziendaParam.codFiscaleOld) &&
        Objects.equals(codNaturaGiuridica, aziendaParam.codNaturaGiuridica) &&
        Objects.equals(codTipoAzienda, aziendaParam.codTipoAzienda) &&
        Objects.equals(dsAnilt, aziendaParam.dsAnilt) &&
        Objects.equals(flgArtigiana, aziendaParam.flgArtigiana) &&
        Objects.equals(flgMaster, aziendaParam.flgMaster) &&
        Objects.equals(flgPubblicaAmministrazione, aziendaParam.flgPubblicaAmministrazione) &&
        Objects.equals(idAzienda, aziendaParam.idAzienda) &&
        Objects.equals(partitaIva, aziendaParam.partitaIva) &&
        Objects.equals(ragioneSociale, aziendaParam.ragioneSociale) &&
        Objects.equals(sede, aziendaParam.sede);
  }

  @Override
  public int hashCode() {
    return Objects.hash(altreInformazioni, codAteco, codCcnl, codFiscale, codFiscaleOld, codNaturaGiuridica, codTipoAzienda, dsAnilt, flgArtigiana, flgMaster, flgPubblicaAmministrazione, idAzienda, partitaIva, ragioneSociale, sede);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AziendaParam {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    altreInformazioni: ").append(toIndentedString(altreInformazioni)).append("\n");
    sb.append("    codAteco: ").append(toIndentedString(codAteco)).append("\n");
    sb.append("    codCcnl: ").append(toIndentedString(codCcnl)).append("\n");
    sb.append("    codFiscale: ").append(toIndentedString(codFiscale)).append("\n");
    sb.append("    codFiscaleOld: ").append(toIndentedString(codFiscaleOld)).append("\n");
    sb.append("    codNaturaGiuridica: ").append(toIndentedString(codNaturaGiuridica)).append("\n");
    sb.append("    codTipoAzienda: ").append(toIndentedString(codTipoAzienda)).append("\n");
    sb.append("    dsAnilt: ").append(toIndentedString(dsAnilt)).append("\n");
    sb.append("    flgArtigiana: ").append(toIndentedString(flgArtigiana)).append("\n");
    sb.append("    flgMaster: ").append(toIndentedString(flgMaster)).append("\n");
    sb.append("    flgPubblicaAmministrazione: ").append(toIndentedString(flgPubblicaAmministrazione)).append("\n");
    sb.append("    idAzienda: ").append(toIndentedString(idAzienda)).append("\n");
    sb.append("    partitaIva: ").append(toIndentedString(partitaIva)).append("\n");
    sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
    sb.append("    sede: ").append(toIndentedString(sede)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
