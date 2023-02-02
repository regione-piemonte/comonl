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
import javax.validation.constraints.*;

public class CommonParam   {
  private CallInfoParam callInfoParam = null;

  /**
   **/
  
  @JsonProperty("callInfoParam")
  public CallInfoParam getCallInfoParam() {
    return callInfoParam;
  }
  public void setCallInfoParam(CallInfoParam callInfoParam) {
    this.callInfoParam = callInfoParam;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonParam commonParam = (CommonParam) o;
    return Objects.equals(callInfoParam, commonParam.callInfoParam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callInfoParam);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonParam {\n");
    
    sb.append("    callInfoParam: ").append(toIndentedString(callInfoParam)).append("\n");
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
