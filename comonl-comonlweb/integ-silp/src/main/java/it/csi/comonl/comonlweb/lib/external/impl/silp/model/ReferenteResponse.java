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

public class ReferenteResponse extends CommonResponse  {
  private Referente referente = null;

  /**
   **/
  
  @JsonProperty("referente")
  public Referente getReferente() {
    return referente;
  }
  public void setReferente(Referente referente) {
    this.referente = referente;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenteResponse referenteResponse = (ReferenteResponse) o;
    return Objects.equals(referente, referenteResponse.referente);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referente);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenteResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    referente: ").append(toIndentedString(referente)).append("\n");
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
