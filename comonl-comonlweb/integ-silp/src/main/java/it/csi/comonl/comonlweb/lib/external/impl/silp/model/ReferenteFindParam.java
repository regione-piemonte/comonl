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
import javax.validation.constraints.*;

public class ReferenteFindParam extends CommonParam  {
  private String codTipoReferente = null;
  private String dsCognome = null;
  private String dsNome = null;
  private Long idReferente = null;
  private Long idSede = null;

  /**
   **/
  
  @JsonProperty("codTipoReferente")
  public String getCodTipoReferente() {
    return codTipoReferente;
  }
  public void setCodTipoReferente(String codTipoReferente) {
    this.codTipoReferente = codTipoReferente;
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
  
  @JsonProperty("idReferente")
  public Long getIdReferente() {
    return idReferente;
  }
  public void setIdReferente(Long idReferente) {
    this.idReferente = idReferente;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferenteFindParam referenteFindParam = (ReferenteFindParam) o;
    return Objects.equals(codTipoReferente, referenteFindParam.codTipoReferente) &&
        Objects.equals(dsCognome, referenteFindParam.dsCognome) &&
        Objects.equals(dsNome, referenteFindParam.dsNome) &&
        Objects.equals(idReferente, referenteFindParam.idReferente) &&
        Objects.equals(idSede, referenteFindParam.idSede);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codTipoReferente, dsCognome, dsNome, idReferente, idSede);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferenteFindParam {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    codTipoReferente: ").append(toIndentedString(codTipoReferente)).append("\n");
    sb.append("    dsCognome: ").append(toIndentedString(dsCognome)).append("\n");
    sb.append("    dsNome: ").append(toIndentedString(dsNome)).append("\n");
    sb.append("    idReferente: ").append(toIndentedString(idReferente)).append("\n");
    sb.append("    idSede: ").append(toIndentedString(idSede)).append("\n");
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
