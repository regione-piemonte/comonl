/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SILP submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.external.impl.silp.exception;



public class ServiziSilpException extends Exception{

  static final long serialVersionUID = -6248138860682716882L;
  
  private String codice;
  private String descrizione;

  public ServiziSilpException(String codice,String descrizione){
    super("Codice Errore:"+codice+" Messaggio:"+descrizione);
    setCodice(codice);
    setDescrizione(descrizione);
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

}
