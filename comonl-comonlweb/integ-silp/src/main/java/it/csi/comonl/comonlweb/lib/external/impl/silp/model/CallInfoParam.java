/* **************************************************** */
/* Copyright Regione Piemonte - 2022					*/
/* SPDX-License-Identifier: EUPL-1.2-or-later			*/
/* **************************************************** */

package it.csi.comonl.comonlweb.lib.external.impl.silp.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class CallInfoParam   {
  private String codApplicativo = null;
  private String codUser = null;

  /**
   **/
  
  @JsonProperty("codApplicativo")
  public String getCodApplicativo() {
    return codApplicativo;
  }
  public void setCodApplicativo(String codApplicativo) {
    this.codApplicativo = codApplicativo;
  }

  /**
   **/
  
  @JsonProperty("codUser")
  public String getCodUser() {
    return codUser;
  }
  public void setCodUser(String codUser) {
    this.codUser = codUser;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CallInfoParam callInfoParam = (CallInfoParam) o;
    return Objects.equals(codApplicativo, callInfoParam.codApplicativo) &&
        Objects.equals(codUser, callInfoParam.codUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codApplicativo, codUser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CallInfoParam {\n");
    
    sb.append("    codApplicativo: ").append(toIndentedString(codApplicativo)).append("\n");
    sb.append("    codUser: ").append(toIndentedString(codUser)).append("\n");
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
