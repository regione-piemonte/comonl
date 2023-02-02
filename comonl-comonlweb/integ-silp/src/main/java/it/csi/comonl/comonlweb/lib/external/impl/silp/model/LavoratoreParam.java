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
import java.util.Date;
import javax.validation.constraints.*;

public class LavoratoreParam extends CommonParam  {
  private String codCapDom = null;
  private String codCapRes = null;
  private String codFiscale = null;
  private Date dataNasc = null;
  private Date dataScadPermSogg = null;
  private String dsCognome = null;
  private String dsIndirizzoDom = null;
  private String dsIndirizzoRes = null;
  private String dsLocalitaDom = null;
  private String dsLocalitaRes = null;
  private String dsMail = null;
  private String dsNome = null;
  private String dsNumCivicoDom = null;
  private String dsNumCivicoRes = null;
  private String dsNumDocPermSogg = null;
  private String dsSitoWeb = null;
  private String dsTelefonoCell = null;
  private String dsTelefonoFax = null;
  private String dsTelefonoRes = null;
  private String flgGenere = null;
  private Long idSilLavAnagrafica = null;
  private String codMinCittadinanza = null;
  private String idSilTComuneDom = null;
  private String idSilTComuneNas = null;
  private String idSilTComuneRes = null;
  private String codMinGradoStudio = null;
  private String codMinMotRilPerm = null;
  private String idSilTNazioneNas = null;
  private String idSilTNazioneRes = null;
  private String codMinQuestura = null;
  private String idSilTStatoCiv = null;
  private String codMinStatusLavExtraUe = null;
  private String idSilTToponimoDom = null;
  private String idSilTToponimoRes = null;

  /**
   **/
  
  @JsonProperty("codCapDom")
  public String getCodCapDom() {
    return codCapDom;
  }
  public void setCodCapDom(String codCapDom) {
    this.codCapDom = codCapDom;
  }

  /**
   **/
  
  @JsonProperty("codCapRes")
  public String getCodCapRes() {
    return codCapRes;
  }
  public void setCodCapRes(String codCapRes) {
    this.codCapRes = codCapRes;
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
  
  @JsonProperty("dataNasc")
  public Date getDataNasc() {
    return dataNasc;
  }
  public void setDataNasc(Date dataNasc) {
    this.dataNasc = dataNasc;
  }

  /**
   **/
  
  @JsonProperty("dataScadPermSogg")
  public Date getDataScadPermSogg() {
    return dataScadPermSogg;
  }
  public void setDataScadPermSogg(Date dataScadPermSogg) {
    this.dataScadPermSogg = dataScadPermSogg;
  }

  /**
   **/
  
  @JsonProperty("dsCognome")
  public String getDsCognome() {
    return dsCognome;
  }
  public void setDsCognome(String dsCognome) {
    this.dsCognome = dsCognome;
  }

  /**
   **/
  
  @JsonProperty("dsIndirizzoDom")
  public String getDsIndirizzoDom() {
    return dsIndirizzoDom;
  }
  public void setDsIndirizzoDom(String dsIndirizzoDom) {
    this.dsIndirizzoDom = dsIndirizzoDom;
  }

  /**
   **/
  
  @JsonProperty("dsIndirizzoRes")
  public String getDsIndirizzoRes() {
    return dsIndirizzoRes;
  }
  public void setDsIndirizzoRes(String dsIndirizzoRes) {
    this.dsIndirizzoRes = dsIndirizzoRes;
  }

  /**
   **/
  
  @JsonProperty("dsLocalitaDom")
  public String getDsLocalitaDom() {
    return dsLocalitaDom;
  }
  public void setDsLocalitaDom(String dsLocalitaDom) {
    this.dsLocalitaDom = dsLocalitaDom;
  }

  /**
   **/
  
  @JsonProperty("dsLocalitaRes")
  public String getDsLocalitaRes() {
    return dsLocalitaRes;
  }
  public void setDsLocalitaRes(String dsLocalitaRes) {
    this.dsLocalitaRes = dsLocalitaRes;
  }

  /**
   **/
  
  @JsonProperty("dsMail")
  public String getDsMail() {
    return dsMail;
  }
  public void setDsMail(String dsMail) {
    this.dsMail = dsMail;
  }

  /**
   **/
  
  @JsonProperty("dsNome")
  public String getDsNome() {
    return dsNome;
  }
  public void setDsNome(String dsNome) {
    this.dsNome = dsNome;
  }

  /**
   **/
  
  @JsonProperty("dsNumCivicoDom")
  public String getDsNumCivicoDom() {
    return dsNumCivicoDom;
  }
  public void setDsNumCivicoDom(String dsNumCivicoDom) {
    this.dsNumCivicoDom = dsNumCivicoDom;
  }

  /**
   **/
  
  @JsonProperty("dsNumCivicoRes")
  public String getDsNumCivicoRes() {
    return dsNumCivicoRes;
  }
  public void setDsNumCivicoRes(String dsNumCivicoRes) {
    this.dsNumCivicoRes = dsNumCivicoRes;
  }

  /**
   **/
  
  @JsonProperty("dsNumDocPermSogg")
  public String getDsNumDocPermSogg() {
    return dsNumDocPermSogg;
  }
  public void setDsNumDocPermSogg(String dsNumDocPermSogg) {
    this.dsNumDocPermSogg = dsNumDocPermSogg;
  }

  /**
   **/
  
  @JsonProperty("dsSitoWeb")
  public String getDsSitoWeb() {
    return dsSitoWeb;
  }
  public void setDsSitoWeb(String dsSitoWeb) {
    this.dsSitoWeb = dsSitoWeb;
  }

  /**
   **/
  
  @JsonProperty("dsTelefonoCell")
  public String getDsTelefonoCell() {
    return dsTelefonoCell;
  }
  public void setDsTelefonoCell(String dsTelefonoCell) {
    this.dsTelefonoCell = dsTelefonoCell;
  }

  /**
   **/
  
  @JsonProperty("dsTelefonoFax")
  public String getDsTelefonoFax() {
    return dsTelefonoFax;
  }
  public void setDsTelefonoFax(String dsTelefonoFax) {
    this.dsTelefonoFax = dsTelefonoFax;
  }

  /**
   **/
  
  @JsonProperty("dsTelefonoRes")
  public String getDsTelefonoRes() {
    return dsTelefonoRes;
  }
  public void setDsTelefonoRes(String dsTelefonoRes) {
    this.dsTelefonoRes = dsTelefonoRes;
  }

  /**
   **/
  
  @JsonProperty("flgGenere")
  public String getFlgGenere() {
    return flgGenere;
  }
  public void setFlgGenere(String flgGenere) {
    this.flgGenere = flgGenere;
  }

  /**
   **/
  
  @JsonProperty("idSilLavAnagrafica")
  public Long getIdSilLavAnagrafica() {
    return idSilLavAnagrafica;
  }
  public void setIdSilLavAnagrafica(Long idSilLavAnagrafica) {
    this.idSilLavAnagrafica = idSilLavAnagrafica;
  }

  /**
   **/
  
  @JsonProperty("codMinCittadinanza")
  public String getCodMinCittadinanza() {
    return codMinCittadinanza;
  }
  public void setCodMinCittadinanza(String codMinCittadinanza) {
    this.codMinCittadinanza = codMinCittadinanza;
  }

  /**
   **/
  
  @JsonProperty("idSilTComuneDom")
  public String getIdSilTComuneDom() {
    return idSilTComuneDom;
  }
  public void setIdSilTComuneDom(String idSilTComuneDom) {
    this.idSilTComuneDom = idSilTComuneDom;
  }

  /**
   **/
  
  @JsonProperty("idSilTComuneNas")
  public String getIdSilTComuneNas() {
    return idSilTComuneNas;
  }
  public void setIdSilTComuneNas(String idSilTComuneNas) {
    this.idSilTComuneNas = idSilTComuneNas;
  }

  /**
   **/
  
  @JsonProperty("idSilTComuneRes")
  public String getIdSilTComuneRes() {
    return idSilTComuneRes;
  }
  public void setIdSilTComuneRes(String idSilTComuneRes) {
    this.idSilTComuneRes = idSilTComuneRes;
  }

  /**
   **/
  
  @JsonProperty("codMinGradoStudio")
  public String getCodMinGradoStudio() {
    return codMinGradoStudio;
  }
  public void setCodMinGradoStudio(String codMinGradoStudio) {
    this.codMinGradoStudio = codMinGradoStudio;
  }

  /**
   **/
  
  @JsonProperty("codMinMotRilPerm")
  public String getCodMinMotRilPerm() {
    return codMinMotRilPerm;
  }
  public void setCodMinMotRilPerm(String codMinMotRilPerm) {
    this.codMinMotRilPerm = codMinMotRilPerm;
  }

  /**
   **/
  
  @JsonProperty("idSilTNazioneNas")
  public String getIdSilTNazioneNas() {
    return idSilTNazioneNas;
  }
  public void setIdSilTNazioneNas(String idSilTNazioneNas) {
    this.idSilTNazioneNas = idSilTNazioneNas;
  }

  /**
   **/
  
  @JsonProperty("idSilTNazioneRes")
  public String getIdSilTNazioneRes() {
    return idSilTNazioneRes;
  }
  public void setIdSilTNazioneRes(String idSilTNazioneRes) {
    this.idSilTNazioneRes = idSilTNazioneRes;
  }

  /**
   **/
  
  @JsonProperty("codMinQuestura")
  public String getCodMinQuestura() {
    return codMinQuestura;
  }
  public void setCodMinQuestura(String codMinQuestura) {
    this.codMinQuestura = codMinQuestura;
  }

  /**
   **/
  
  @JsonProperty("idSilTStatoCiv")
  public String getIdSilTStatoCiv() {
    return idSilTStatoCiv;
  }
  public void setIdSilTStatoCiv(String idSilTStatoCiv) {
    this.idSilTStatoCiv = idSilTStatoCiv;
  }

  /**
   **/
  
  @JsonProperty("codMinStatusLavExtraUe")
  public String getCodMinStatusLavExtraUe() {
    return codMinStatusLavExtraUe;
  }
  public void setCodMinStatusLavExtraUe(String codMinStatusLavExtraUe) {
    this.codMinStatusLavExtraUe = codMinStatusLavExtraUe;
  }

  /**
   **/
  
  @JsonProperty("idSilTToponimoDom")
  public String getIdSilTToponimoDom() {
    return idSilTToponimoDom;
  }
  public void setIdSilTToponimoDom(String idSilTToponimoDom) {
    this.idSilTToponimoDom = idSilTToponimoDom;
  }

  /**
   **/
  
  @JsonProperty("idSilTToponimoRes")
  public String getIdSilTToponimoRes() {
    return idSilTToponimoRes;
  }
  public void setIdSilTToponimoRes(String idSilTToponimoRes) {
    this.idSilTToponimoRes = idSilTToponimoRes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LavoratoreParam lavoratoreParam = (LavoratoreParam) o;
    return Objects.equals(codCapDom, lavoratoreParam.codCapDom) &&
        Objects.equals(codCapRes, lavoratoreParam.codCapRes) &&
        Objects.equals(codFiscale, lavoratoreParam.codFiscale) &&
        Objects.equals(dataNasc, lavoratoreParam.dataNasc) &&
        Objects.equals(dataScadPermSogg, lavoratoreParam.dataScadPermSogg) &&
        Objects.equals(dsCognome, lavoratoreParam.dsCognome) &&
        Objects.equals(dsIndirizzoDom, lavoratoreParam.dsIndirizzoDom) &&
        Objects.equals(dsIndirizzoRes, lavoratoreParam.dsIndirizzoRes) &&
        Objects.equals(dsLocalitaDom, lavoratoreParam.dsLocalitaDom) &&
        Objects.equals(dsLocalitaRes, lavoratoreParam.dsLocalitaRes) &&
        Objects.equals(dsMail, lavoratoreParam.dsMail) &&
        Objects.equals(dsNome, lavoratoreParam.dsNome) &&
        Objects.equals(dsNumCivicoDom, lavoratoreParam.dsNumCivicoDom) &&
        Objects.equals(dsNumCivicoRes, lavoratoreParam.dsNumCivicoRes) &&
        Objects.equals(dsNumDocPermSogg, lavoratoreParam.dsNumDocPermSogg) &&
        Objects.equals(dsSitoWeb, lavoratoreParam.dsSitoWeb) &&
        Objects.equals(dsTelefonoCell, lavoratoreParam.dsTelefonoCell) &&
        Objects.equals(dsTelefonoFax, lavoratoreParam.dsTelefonoFax) &&
        Objects.equals(dsTelefonoRes, lavoratoreParam.dsTelefonoRes) &&
        Objects.equals(flgGenere, lavoratoreParam.flgGenere) &&
        Objects.equals(idSilLavAnagrafica, lavoratoreParam.idSilLavAnagrafica) &&
        Objects.equals(codMinCittadinanza, lavoratoreParam.codMinCittadinanza) &&
        Objects.equals(idSilTComuneDom, lavoratoreParam.idSilTComuneDom) &&
        Objects.equals(idSilTComuneNas, lavoratoreParam.idSilTComuneNas) &&
        Objects.equals(idSilTComuneRes, lavoratoreParam.idSilTComuneRes) &&
        Objects.equals(codMinGradoStudio, lavoratoreParam.codMinGradoStudio) &&
        Objects.equals(codMinMotRilPerm, lavoratoreParam.codMinMotRilPerm) &&
        Objects.equals(idSilTNazioneNas, lavoratoreParam.idSilTNazioneNas) &&
        Objects.equals(idSilTNazioneRes, lavoratoreParam.idSilTNazioneRes) &&
        Objects.equals(codMinQuestura, lavoratoreParam.codMinQuestura) &&
        Objects.equals(idSilTStatoCiv, lavoratoreParam.idSilTStatoCiv) &&
        Objects.equals(codMinStatusLavExtraUe, lavoratoreParam.codMinStatusLavExtraUe) &&
        Objects.equals(idSilTToponimoDom, lavoratoreParam.idSilTToponimoDom) &&
        Objects.equals(idSilTToponimoRes, lavoratoreParam.idSilTToponimoRes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codCapDom, codCapRes, codFiscale, dataNasc, dataScadPermSogg, dsCognome, dsIndirizzoDom, dsIndirizzoRes, dsLocalitaDom, dsLocalitaRes, dsMail, dsNome, dsNumCivicoDom, dsNumCivicoRes, dsNumDocPermSogg, dsSitoWeb, dsTelefonoCell, dsTelefonoFax, dsTelefonoRes, flgGenere, idSilLavAnagrafica, codMinCittadinanza, idSilTComuneDom, idSilTComuneNas, idSilTComuneRes, codMinGradoStudio, codMinMotRilPerm, idSilTNazioneNas, idSilTNazioneRes, codMinQuestura, idSilTStatoCiv, codMinStatusLavExtraUe, idSilTToponimoDom, idSilTToponimoRes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LavoratoreParam {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    codCapDom: ").append(toIndentedString(codCapDom)).append("\n");
    sb.append("    codCapRes: ").append(toIndentedString(codCapRes)).append("\n");
    sb.append("    codFiscale: ").append(toIndentedString(codFiscale)).append("\n");
    sb.append("    dataNasc: ").append(toIndentedString(dataNasc)).append("\n");
    sb.append("    dataScadPermSogg: ").append(toIndentedString(dataScadPermSogg)).append("\n");
    sb.append("    dsCognome: ").append(toIndentedString(dsCognome)).append("\n");
    sb.append("    dsIndirizzoDom: ").append(toIndentedString(dsIndirizzoDom)).append("\n");
    sb.append("    dsIndirizzoRes: ").append(toIndentedString(dsIndirizzoRes)).append("\n");
    sb.append("    dsLocalitaDom: ").append(toIndentedString(dsLocalitaDom)).append("\n");
    sb.append("    dsLocalitaRes: ").append(toIndentedString(dsLocalitaRes)).append("\n");
    sb.append("    dsMail: ").append(toIndentedString(dsMail)).append("\n");
    sb.append("    dsNome: ").append(toIndentedString(dsNome)).append("\n");
    sb.append("    dsNumCivicoDom: ").append(toIndentedString(dsNumCivicoDom)).append("\n");
    sb.append("    dsNumCivicoRes: ").append(toIndentedString(dsNumCivicoRes)).append("\n");
    sb.append("    dsNumDocPermSogg: ").append(toIndentedString(dsNumDocPermSogg)).append("\n");
    sb.append("    dsSitoWeb: ").append(toIndentedString(dsSitoWeb)).append("\n");
    sb.append("    dsTelefonoCell: ").append(toIndentedString(dsTelefonoCell)).append("\n");
    sb.append("    dsTelefonoFax: ").append(toIndentedString(dsTelefonoFax)).append("\n");
    sb.append("    dsTelefonoRes: ").append(toIndentedString(dsTelefonoRes)).append("\n");
    sb.append("    flgGenere: ").append(toIndentedString(flgGenere)).append("\n");
    sb.append("    idSilLavAnagrafica: ").append(toIndentedString(idSilLavAnagrafica)).append("\n");
    sb.append("    codMinCittadinanza: ").append(toIndentedString(codMinCittadinanza)).append("\n");
    sb.append("    idSilTComuneDom: ").append(toIndentedString(idSilTComuneDom)).append("\n");
    sb.append("    idSilTComuneNas: ").append(toIndentedString(idSilTComuneNas)).append("\n");
    sb.append("    idSilTComuneRes: ").append(toIndentedString(idSilTComuneRes)).append("\n");
    sb.append("    codMinGradoStudio: ").append(toIndentedString(codMinGradoStudio)).append("\n");
    sb.append("    codMinMotRilPerm: ").append(toIndentedString(codMinMotRilPerm)).append("\n");
    sb.append("    idSilTNazioneNas: ").append(toIndentedString(idSilTNazioneNas)).append("\n");
    sb.append("    idSilTNazioneRes: ").append(toIndentedString(idSilTNazioneRes)).append("\n");
    sb.append("    codMinQuestura: ").append(toIndentedString(codMinQuestura)).append("\n");
    sb.append("    idSilTStatoCiv: ").append(toIndentedString(idSilTStatoCiv)).append("\n");
    sb.append("    codMinStatusLavExtraUe: ").append(toIndentedString(codMinStatusLavExtraUe)).append("\n");
    sb.append("    idSilTToponimoDom: ").append(toIndentedString(idSilTToponimoDom)).append("\n");
    sb.append("    idSilTToponimoRes: ").append(toIndentedString(idSilTToponimoRes)).append("\n");
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
