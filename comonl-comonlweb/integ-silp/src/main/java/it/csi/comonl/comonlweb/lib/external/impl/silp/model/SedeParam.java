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

public class SedeParam extends CommonParam  {
  private String cap = null;
  private String codClasseAmpiezza = null;
  private String codComune = null;
  private String codNazione = null;
  private Long codTipoSede = null;
  private String codToponimo = null;
  private Date dataInizioAttivita = null;
  private Date dataRifNumDipendenti = null;
  private String denominazione = null;
  private String email = null;
  private String fax = null;
  private String flgValida = null;
  private Long idSede = null;
  private Long idSedeLegaleOld = null;
  private String inail = null;
  private String indirizzo = null;
  private String inps = null;
  private String localita = null;
  private String motivoFlgValida = null;
  private String numAgenziaSomm = null;
  private String numCivico = null;
  private String numDipendenti = null;
  private String telefono = null;

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
  
  @JsonProperty("codClasseAmpiezza")
  public String getCodClasseAmpiezza() {
    return codClasseAmpiezza;
  }
  public void setCodClasseAmpiezza(String codClasseAmpiezza) {
    this.codClasseAmpiezza = codClasseAmpiezza;
  }

  /**
   **/
  
  @JsonProperty("codComune")
  public String getCodComune() {
    return codComune;
  }
  public void setCodComune(String codComune) {
    this.codComune = codComune;
  }

  /**
   **/
  
  @JsonProperty("codNazione")
  public String getCodNazione() {
    return codNazione;
  }
  public void setCodNazione(String codNazione) {
    this.codNazione = codNazione;
  }

  /**
   **/
  
  @JsonProperty("codTipoSede")
  public Long getCodTipoSede() {
    return codTipoSede;
  }
  public void setCodTipoSede(Long codTipoSede) {
    this.codTipoSede = codTipoSede;
  }

  /**
   **/
  
  @JsonProperty("codToponimo")
  public String getCodToponimo() {
    return codToponimo;
  }
  public void setCodToponimo(String codToponimo) {
    this.codToponimo = codToponimo;
  }

  /**
   **/
  
  @JsonProperty("dataInizioAttivita")
  public Date getDataInizioAttivita() {
    return dataInizioAttivita;
  }
  public void setDataInizioAttivita(Date dataInizioAttivita) {
    this.dataInizioAttivita = dataInizioAttivita;
  }

  /**
   **/
  
  @JsonProperty("dataRifNumDipendenti")
  public Date getDataRifNumDipendenti() {
    return dataRifNumDipendenti;
  }
  public void setDataRifNumDipendenti(Date dataRifNumDipendenti) {
    this.dataRifNumDipendenti = dataRifNumDipendenti;
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
  
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
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
  
  @JsonProperty("flgValida")
  public String getFlgValida() {
    return flgValida;
  }
  public void setFlgValida(String flgValida) {
    this.flgValida = flgValida;
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
  
  @JsonProperty("idSedeLegaleOld")
  public Long getIdSedeLegaleOld() {
    return idSedeLegaleOld;
  }
  public void setIdSedeLegaleOld(Long idSedeLegaleOld) {
    this.idSedeLegaleOld = idSedeLegaleOld;
  }

  /**
   **/
  
  @JsonProperty("inail")
  public String getInail() {
    return inail;
  }
  public void setInail(String inail) {
    this.inail = inail;
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
  
  @JsonProperty("inps")
  public String getInps() {
    return inps;
  }
  public void setInps(String inps) {
    this.inps = inps;
  }

  /**
   **/
  
  @JsonProperty("localita")
  public String getLocalita() {
    return localita;
  }
  public void setLocalita(String localita) {
    this.localita = localita;
  }

  /**
   **/
  
  @JsonProperty("motivoFlgValida")
  public String getMotivoFlgValida() {
    return motivoFlgValida;
  }
  public void setMotivoFlgValida(String motivoFlgValida) {
    this.motivoFlgValida = motivoFlgValida;
  }

  /**
   **/
  
  @JsonProperty("numAgenziaSomm")
  public String getNumAgenziaSomm() {
    return numAgenziaSomm;
  }
  public void setNumAgenziaSomm(String numAgenziaSomm) {
    this.numAgenziaSomm = numAgenziaSomm;
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
  
  @JsonProperty("numDipendenti")
  public String getNumDipendenti() {
    return numDipendenti;
  }
  public void setNumDipendenti(String numDipendenti) {
    this.numDipendenti = numDipendenti;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SedeParam sedeParam = (SedeParam) o;
    return Objects.equals(cap, sedeParam.cap) &&
        Objects.equals(codClasseAmpiezza, sedeParam.codClasseAmpiezza) &&
        Objects.equals(codComune, sedeParam.codComune) &&
        Objects.equals(codNazione, sedeParam.codNazione) &&
        Objects.equals(codTipoSede, sedeParam.codTipoSede) &&
        Objects.equals(codToponimo, sedeParam.codToponimo) &&
        Objects.equals(dataInizioAttivita, sedeParam.dataInizioAttivita) &&
        Objects.equals(dataRifNumDipendenti, sedeParam.dataRifNumDipendenti) &&
        Objects.equals(denominazione, sedeParam.denominazione) &&
        Objects.equals(email, sedeParam.email) &&
        Objects.equals(fax, sedeParam.fax) &&
        Objects.equals(flgValida, sedeParam.flgValida) &&
        Objects.equals(idSede, sedeParam.idSede) &&
        Objects.equals(idSedeLegaleOld, sedeParam.idSedeLegaleOld) &&
        Objects.equals(inail, sedeParam.inail) &&
        Objects.equals(indirizzo, sedeParam.indirizzo) &&
        Objects.equals(inps, sedeParam.inps) &&
        Objects.equals(localita, sedeParam.localita) &&
        Objects.equals(motivoFlgValida, sedeParam.motivoFlgValida) &&
        Objects.equals(numAgenziaSomm, sedeParam.numAgenziaSomm) &&
        Objects.equals(numCivico, sedeParam.numCivico) &&
        Objects.equals(numDipendenti, sedeParam.numDipendenti) &&
        Objects.equals(telefono, sedeParam.telefono);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cap, codClasseAmpiezza, codComune, codNazione, codTipoSede, codToponimo, dataInizioAttivita, dataRifNumDipendenti, denominazione, email, fax, flgValida, idSede, idSedeLegaleOld, inail, indirizzo, inps, localita, motivoFlgValida, numAgenziaSomm, numCivico, numDipendenti, telefono);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SedeParam {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    codClasseAmpiezza: ").append(toIndentedString(codClasseAmpiezza)).append("\n");
    sb.append("    codComune: ").append(toIndentedString(codComune)).append("\n");
    sb.append("    codNazione: ").append(toIndentedString(codNazione)).append("\n");
    sb.append("    codTipoSede: ").append(toIndentedString(codTipoSede)).append("\n");
    sb.append("    codToponimo: ").append(toIndentedString(codToponimo)).append("\n");
    sb.append("    dataInizioAttivita: ").append(toIndentedString(dataInizioAttivita)).append("\n");
    sb.append("    dataRifNumDipendenti: ").append(toIndentedString(dataRifNumDipendenti)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
    sb.append("    flgValida: ").append(toIndentedString(flgValida)).append("\n");
    sb.append("    idSede: ").append(toIndentedString(idSede)).append("\n");
    sb.append("    idSedeLegaleOld: ").append(toIndentedString(idSedeLegaleOld)).append("\n");
    sb.append("    inail: ").append(toIndentedString(inail)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    inps: ").append(toIndentedString(inps)).append("\n");
    sb.append("    localita: ").append(toIndentedString(localita)).append("\n");
    sb.append("    motivoFlgValida: ").append(toIndentedString(motivoFlgValida)).append("\n");
    sb.append("    numAgenziaSomm: ").append(toIndentedString(numAgenziaSomm)).append("\n");
    sb.append("    numCivico: ").append(toIndentedString(numCivico)).append("\n");
    sb.append("    numDipendenti: ").append(toIndentedString(numDipendenti)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
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
