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
import java.util.List;
import javax.validation.constraints.*;

public class CommonResponse   {
  private Boolean esitoPositivo = true;
  private List<ApiMessage> apiMessages = new ArrayList<ApiMessage>();

  /**
   **/
  
  @JsonProperty("esitoPositivo")
  public Boolean isEsitoPositivo() {
    return esitoPositivo;
  }
  public void setEsitoPositivo(Boolean esitoPositivo) {
    this.esitoPositivo = esitoPositivo;
  }

  /**
   **/
  
  @JsonProperty("apiMessages")
  public List<ApiMessage> getApiMessages() {
    return apiMessages;
  }
  public void setApiMessages(List<ApiMessage> apiMessages) {
    this.apiMessages = apiMessages;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonResponse commonResponse = (CommonResponse) o;
    return Objects.equals(esitoPositivo, commonResponse.esitoPositivo) &&
        Objects.equals(apiMessages, commonResponse.apiMessages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(esitoPositivo, apiMessages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonResponse {\n");
    
    sb.append("    esitoPositivo: ").append(toIndentedString(esitoPositivo)).append("\n");
    sb.append("    apiMessages: ").append(toIndentedString(apiMessages)).append("\n");
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
