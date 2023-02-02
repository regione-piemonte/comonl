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
import java.util.List;
import javax.validation.constraints.*;

public class SedeResponse extends CommonResponse  {
  private List<ApiMessage> sedeResponseApiMessages = new ArrayList<ApiMessage>();
  private Long id = null;
  private Long idAzienda = null;

  /**
   **/
  
  @JsonProperty("apiMessages")
  public List<ApiMessage> getSedeResponseApiMessages() {
    return sedeResponseApiMessages;
  }
  public void setSedeResponseApiMessages(List<ApiMessage> sedeResponseApiMessages) {
    this.sedeResponseApiMessages = sedeResponseApiMessages;
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
  
  @JsonProperty("idAzienda")
  public Long getIdAzienda() {
    return idAzienda;
  }
  public void setIdAzienda(Long idAzienda) {
    this.idAzienda = idAzienda;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SedeResponse sedeResponse = (SedeResponse) o;
    return Objects.equals(sedeResponseApiMessages, sedeResponse.sedeResponseApiMessages) &&
        Objects.equals(id, sedeResponse.id) &&
        Objects.equals(idAzienda, sedeResponse.idAzienda);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sedeResponseApiMessages, id, idAzienda);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SedeResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    sedeResponseApiMessages: ").append(toIndentedString(sedeResponseApiMessages)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idAzienda: ").append(toIndentedString(idAzienda)).append("\n");
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
