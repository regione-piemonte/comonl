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
import java.util.List;
import javax.validation.constraints.*;

public class ReferenteParam extends CommonParam  {
  private String cap = null;
  private String denominazione = null;
  private String dsCognome = null;
  private String dsNome = null;
  private Date dtNascita = null;
  private Date dtRichiestaTitoloSoggiorno = null;
  private Date dtScadenzaTitoloSoggiorno = null;
  private String fax = null;
  private String faxAzienda = null;
  private String genere = null;
  private Long id = null;
  private String codMinCittadinanza = null;
  private String idComune = null;
  private String idComuneNasc = null;
  private String codMinMotivoTitoloSoggiorno = null;
  private String idNazione = null;
  private String idNazioneNasc = null;
  private String codMinQuestura = null;
  private Long idSede = null;
  private String codMinTitoloSoggiorno = null;
  private String indirizzo = null;
  private String mail = null;
  private String numCivico = null;
  private String note = null;
  private String numeroTitoloSoggiorno = null;
  private List<String> ruoli = new ArrayList<String>();
  private String sitoWeb = null;
  private String telefono = null;
  private String telefonoAzienda = null;
  private String toponimo = null;

  /**
   **/
  
  @JsonProperty("cap")
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("denominazione")
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
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
  
  @JsonProperty("dsNome")
  public String getDsNome() {
    return dsNome;
  }
  public void setDsNome(String dsNome) {
    this.dsNome = dsNome;
  }

  /**
   **/
  
  @JsonProperty("dtNascita")
  public Date getDtNascita() {
    return dtNascita;
  }
  public void setDtNascita(Date dtNascita) {
    this.dtNascita = dtNascita;
  }

  /**
   **/
  
  @JsonProperty("dtRichiestaTitoloSoggiorno")
  public Date getDtRichiestaTitoloSoggiorno() {
    return dtRichiestaTitoloSoggiorno;
  }
  public void setDtRichiestaTitoloSoggiorno(Date dtRichiestaTitoloSoggiorno) {
    this.dtRichiestaTitoloSoggiorno = dtRichiestaTitoloSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("dtScadenzaTitoloSoggiorno")
  public Date getDtScadenzaTitoloSoggiorno() {
    return dtScadenzaTitoloSoggiorno;
  }
  public void setDtScadenzaTitoloSoggiorno(Date dtScadenzaTitoloSoggiorno) {
    this.dtScadenzaTitoloSoggiorno = dtScadenzaTitoloSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("fax")
  public String getFax() {
    return fax;
  }
  public void setFax(String fax) {
    this.fax = fax;
  }

  /**
   **/
  
  @JsonProperty("faxAzienda")
  public String getFaxAzienda() {
    return faxAzienda;
  }
  public void setFaxAzienda(String faxAzienda) {
    this.faxAzienda = faxAzienda;
  }

  /**
   **/
  
  @JsonProperty("genere")
  public String getGenere() {
    return genere;
  }
  public void setGenere(String genere) {
    this.genere = genere;
  }

  /**
   **/
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
  
  @JsonProperty("idComune")
  public String getIdComune() {
    return idComune;
  }
  public void setIdComune(String idComune) {
    this.idComune = idComune;
  }

  /**
   **/
  
  @JsonProperty("idComuneNasc")
  public String getIdComuneNasc() {
    return idComuneNasc;
  }
  public void setIdComuneNasc(String idComuneNasc) {
    this.idComuneNasc = idComuneNasc;
  }

  /**
   **/
  
  @JsonProperty("codMinMotivoTitoloSoggiorno")
  public String getCodMinMotivoTitoloSoggiorno() {
    return codMinMotivoTitoloSoggiorno;
  }
  public void setCodMinMotivoTitoloSoggiorno(String codMinMotivoTitoloSoggiorno) {
    this.codMinMotivoTitoloSoggiorno = codMinMotivoTitoloSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("idNazione")
  public String getIdNazione() {
    return idNazione;
  }
  public void setIdNazione(String idNazione) {
    this.idNazione = idNazione;
  }

  /**
   **/
  
  @JsonProperty("idNazioneNasc")
  public String getIdNazioneNasc() {
    return idNazioneNasc;
  }
  public void setIdNazioneNasc(String idNazioneNasc) {
    this.idNazioneNasc = idNazioneNasc;
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
  
  @JsonProperty("idSede")
  public Long getIdSede() {
    return idSede;
  }
  public void setIdSede(Long idSede) {
    this.idSede = idSede;
  }

  /**
   **/
  
  @JsonProperty("codMinTitoloSoggiorno")
  public String getCodMinTitoloSoggiorno() {
    return codMinTitoloSoggiorno;
  }
  public void setCodMinTitoloSoggiorno(String codMinTitoloSoggiorno) {
    this.codMinTitoloSoggiorno = codMinTitoloSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  @JsonProperty("mail")
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }

  /**
   **/
  
  @JsonProperty("numCivico")
  public String getNumCivico() {
    return numCivico;
  }
  public void setNumCivico(String numCivico) {
    this.numCivico = numCivico;
  }

  /**
   **/
  
  @JsonProperty("note")
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }

  /**
   **/
  
  @JsonProperty("numeroTitoloSoggiorno")
  public String getNumeroTitoloSoggiorno() {
    return numeroTitoloSoggiorno;
  }
  public void setNumeroTitoloSoggiorno(String numeroTitoloSoggiorno) {
    this.numeroTitoloSoggiorno = numeroTitoloSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("ruoli")
  public List<String> getRuoli() {
    return ruoli;
  }
  public void setRuoli(List<String> ruoli) {
    this.ruoli = ruoli;
  }

  /**
   **/
  
  @JsonProperty("sitoWeb")
  public String getSitoWeb() {
    return sitoWeb;
  }
  public void setSitoWeb(String sitoWeb) {
    this.sitoWeb = sitoWeb;
  }

  /**
   **/
  
  @JsonProperty("telefono")
  public String getTelefono() {
    return telefono;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   **/
  
  @JsonProperty("telefonoAzienda")
  public String getTelefonoAzienda() {
    return telefonoAzienda;
  }
  public void setTelefonoAzienda(String telefonoAzienda) {
    this.telefonoAzienda = telefonoAzienda;
  }

  /**
   **/
  
  @JsonProperty("toponimo")
  public String getToponimo() {
    return toponimo;
  }
  public void setToponimo(String toponimo) {
    this.toponimo = toponimo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenteParam referenteParam = (ReferenteParam) o;
    return Objects.equals(cap, referenteParam.cap) &&
        Objects.equals(denominazione, referenteParam.denominazione) &&
        Objects.equals(dsCognome, referenteParam.dsCognome) &&
        Objects.equals(dsNome, referenteParam.dsNome) &&
        Objects.equals(dtNascita, referenteParam.dtNascita) &&
        Objects.equals(dtRichiestaTitoloSoggiorno, referenteParam.dtRichiestaTitoloSoggiorno) &&
        Objects.equals(dtScadenzaTitoloSoggiorno, referenteParam.dtScadenzaTitoloSoggiorno) &&
        Objects.equals(fax, referenteParam.fax) &&
        Objects.equals(faxAzienda, referenteParam.faxAzienda) &&
        Objects.equals(genere, referenteParam.genere) &&
        Objects.equals(id, referenteParam.id) &&
        Objects.equals(codMinCittadinanza, referenteParam.codMinCittadinanza) &&
        Objects.equals(idComune, referenteParam.idComune) &&
        Objects.equals(idComuneNasc, referenteParam.idComuneNasc) &&
        Objects.equals(codMinMotivoTitoloSoggiorno, referenteParam.codMinMotivoTitoloSoggiorno) &&
        Objects.equals(idNazione, referenteParam.idNazione) &&
        Objects.equals(idNazioneNasc, referenteParam.idNazioneNasc) &&
        Objects.equals(codMinQuestura, referenteParam.codMinQuestura) &&
        Objects.equals(idSede, referenteParam.idSede) &&
        Objects.equals(codMinTitoloSoggiorno, referenteParam.codMinTitoloSoggiorno) &&
        Objects.equals(indirizzo, referenteParam.indirizzo) &&
        Objects.equals(mail, referenteParam.mail) &&
        Objects.equals(numCivico, referenteParam.numCivico) &&
        Objects.equals(note, referenteParam.note) &&
        Objects.equals(numeroTitoloSoggiorno, referenteParam.numeroTitoloSoggiorno) &&
        Objects.equals(ruoli, referenteParam.ruoli) &&
        Objects.equals(sitoWeb, referenteParam.sitoWeb) &&
        Objects.equals(telefono, referenteParam.telefono) &&
        Objects.equals(telefonoAzienda, referenteParam.telefonoAzienda) &&
        Objects.equals(toponimo, referenteParam.toponimo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cap, denominazione, dsCognome, dsNome, dtNascita, dtRichiestaTitoloSoggiorno, dtScadenzaTitoloSoggiorno, fax, faxAzienda, genere, id, codMinCittadinanza, idComune, idComuneNasc, codMinMotivoTitoloSoggiorno, idNazione, idNazioneNasc, codMinQuestura, idSede, codMinTitoloSoggiorno, indirizzo, mail, numCivico, note, numeroTitoloSoggiorno, ruoli, sitoWeb, telefono, telefonoAzienda, toponimo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenteParam {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    dsCognome: ").append(toIndentedString(dsCognome)).append("\n");
    sb.append("    dsNome: ").append(toIndentedString(dsNome)).append("\n");
    sb.append("    dtNascita: ").append(toIndentedString(dtNascita)).append("\n");
    sb.append("    dtRichiestaTitoloSoggiorno: ").append(toIndentedString(dtRichiestaTitoloSoggiorno)).append("\n");
    sb.append("    dtScadenzaTitoloSoggiorno: ").append(toIndentedString(dtScadenzaTitoloSoggiorno)).append("\n");
    sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
    sb.append("    faxAzienda: ").append(toIndentedString(faxAzienda)).append("\n");
    sb.append("    genere: ").append(toIndentedString(genere)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codMinCittadinanza: ").append(toIndentedString(codMinCittadinanza)).append("\n");
    sb.append("    idComune: ").append(toIndentedString(idComune)).append("\n");
    sb.append("    idComuneNasc: ").append(toIndentedString(idComuneNasc)).append("\n");
    sb.append("    codMinMotivoTitoloSoggiorno: ").append(toIndentedString(codMinMotivoTitoloSoggiorno)).append("\n");
    sb.append("    idNazione: ").append(toIndentedString(idNazione)).append("\n");
    sb.append("    idNazioneNasc: ").append(toIndentedString(idNazioneNasc)).append("\n");
    sb.append("    codMinQuestura: ").append(toIndentedString(codMinQuestura)).append("\n");
    sb.append("    idSede: ").append(toIndentedString(idSede)).append("\n");
    sb.append("    codMinTitoloSoggiorno: ").append(toIndentedString(codMinTitoloSoggiorno)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    numCivico: ").append(toIndentedString(numCivico)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    numeroTitoloSoggiorno: ").append(toIndentedString(numeroTitoloSoggiorno)).append("\n");
    sb.append("    ruoli: ").append(toIndentedString(ruoli)).append("\n");
    sb.append("    sitoWeb: ").append(toIndentedString(sitoWeb)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    telefonoAzienda: ").append(toIndentedString(telefonoAzienda)).append("\n");
    sb.append("    toponimo: ").append(toIndentedString(toponimo)).append("\n");
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
