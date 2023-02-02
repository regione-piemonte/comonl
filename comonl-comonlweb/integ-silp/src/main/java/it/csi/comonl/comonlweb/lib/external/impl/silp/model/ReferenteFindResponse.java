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
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.Referente;
import java.util.List;
import javax.validation.constraints.*;

public class ReferenteFindResponse extends CommonResponse  {
  private List<ApiMessage> referenteFindResponseApiMessages = new ArrayList<ApiMessage>();
  private List<Referente> referenti = new ArrayList<Referente>();

  /**
   **/
  
  @JsonProperty("apiMessages")
  public List<ApiMessage> getReferenteFindResponseApiMessages() {
    return referenteFindResponseApiMessages;
  }
  public void setReferenteFindResponseApiMessages(List<ApiMessage> referenteFindResponseApiMessages) {
    this.referenteFindResponseApiMessages = referenteFindResponseApiMessages;
  }

  /**
   **/
  
  @JsonProperty("referenti")
  public List<Referente> getReferenti() {
    return referenti;
  }
  public void setReferenti(List<Referente> referenti) {
    this.referenti = referenti;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenteFindResponse referenteFindResponse = (ReferenteFindResponse) o;
    return Objects.equals(referenteFindResponseApiMessages, referenteFindResponse.referenteFindResponseApiMessages) &&
        Objects.equals(referenti, referenteFindResponse.referenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referenteFindResponseApiMessages, referenti);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenteFindResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    referenteFindResponseApiMessages: ").append(toIndentedString(referenteFindResponseApiMessages)).append("\n");
    sb.append("    referenti: ").append(toIndentedString(referenti)).append("\n");
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
