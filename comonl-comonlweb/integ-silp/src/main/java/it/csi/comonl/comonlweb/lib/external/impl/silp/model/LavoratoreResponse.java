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

public class LavoratoreResponse extends CommonResponse  {
  private Long id = null;
  private Boolean presenteErrore = null;

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
  
  @JsonProperty("presenteErrore")
  public Boolean isPresenteErrore() {
    return presenteErrore;
  }
  public void setPresenteErrore(Boolean presenteErrore) {
    this.presenteErrore = presenteErrore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LavoratoreResponse lavoratoreResponse = (LavoratoreResponse) o;
    return Objects.equals(id, lavoratoreResponse.id) &&
        Objects.equals(presenteErrore, lavoratoreResponse.presenteErrore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, presenteErrore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LavoratoreResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    presenteErrore: ").append(toIndentedString(presenteErrore)).append("\n");
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
