/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.commax.util;

public class ValidazioneMail {
	public static boolean checkMail(String indirizzoMail) {
	    if (indirizzoMail != null && indirizzoMail != "") {
	      if (!controllaIndirizzoEmail(indirizzoMail)) {
	        return false;
	      }
	    }
	    return true;
	  }

	  private static  boolean controllaIndirizzoEmail(String indirizzo) {
	    // Deve esserci un solo carattere '@'
	    if (indirizzo == null)
	      return false;
	    int indice = indirizzo.indexOf("@");
	    if (indice == -1)
	      return false;
	    String dominio = indirizzo.substring(indice + 1, indirizzo.length());
	    indice = dominio.indexOf("@");
	    if (indice != -1)
	      return false;

	    // Deve esserci almeno un carattere '.' nel dominio
	    indice = dominio.indexOf(".");
	    if (indice == -1)
	      return false;

	    //Dopo il punto del dominio deve esserci almeno un altro carattere
	    String dopoDominio = dominio.substring(indice + 1, dominio.length());
	    if (dopoDominio.length() == 0)
	      return false;

	    // Verifica che non ci siano caratteri non validi
	    for (int i = 0; i < indirizzo.length(); i++) {
	      char caratt = indirizzo.charAt(i);
	      if (!Character.isLetterOrDigit(caratt) &&
	          ! ("" + caratt).equalsIgnoreCase(".")
	          && ! ("" + caratt).equalsIgnoreCase("@") &&
	          ! ("" + caratt).equalsIgnoreCase("-")
	          && ! ("" + caratt).equalsIgnoreCase("_"))
	        return false;
	      //if (!((c >= "a" && c <= "z") || (c >= "A" && c <= "Z") || (c >= "0" && c <= "9") || c == "." || c == "@" || c == "-" || c == "_"))	 return false;
	    }

	    // Tutto ok
	    return true;
	  }

}
