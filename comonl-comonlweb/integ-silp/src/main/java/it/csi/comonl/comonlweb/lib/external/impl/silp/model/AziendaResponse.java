/* **************************************************** */
/* Copyright Regione Piemonte - 2022					*/
/* SPDX-License-Identifier: EUPL-1.2-or-later			*/
/* **************************************************** */

package it.csi.comonl.comonlweb.lib.external.impl.silp.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiMessage;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.CommonResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.SedeResponse;
import java.util.List;
import javax.validation.constraints.*;

public class AziendaResponse extends CommonResponse  {
  private SedeResponse sede = null;
  private String codiceFiscale = null;
  private Long id = null;

  /**
   **/
  
  @JsonProperty("sede")
  public SedeResponse getSede() {
    return sede;
  }
  public void setSede(SedeResponse sede) {
    this.sede = sede;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AziendaResponse aziendaResponse = (AziendaResponse) o;
    return Objects.equals(sede, aziendaResponse.sede) &&
        Objects.equals(codiceFiscale, aziendaResponse.codiceFiscale) &&
        Objects.equals(id, aziendaResponse.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sede, codiceFiscale, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AziendaResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    sede: ").append(toIndentedString(sede)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
