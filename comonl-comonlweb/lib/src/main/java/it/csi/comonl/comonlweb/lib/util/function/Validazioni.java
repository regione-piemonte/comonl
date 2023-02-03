/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.util.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Validazioni varie
 */
public class Validazioni {
	// private static LogUtil log = new LogUtil(Validazioni.class);
	/** Pattern for Intervento.cui */
	private static final Pattern[] CONTROLLO_SICUREZZA_CUI = new Pattern[] { Pattern.compile("^[a-zA-Z0-9]{21}$"), };

//	public boolean isValidPartitaIva(String partitaIva) {
////		Il carattere di controllo viene determinato nel modo seguente:
//		int sommaPari = 0;
//		int sommaDispari = 0;
//
//		for (int i = 0; i < partitaIva.length(); i++) {
//			char c = partitaIva.charAt(i);
//			int n = Integer.parseInt("" + c);
//
//// 			1. si sommano i valori di ciascuna delle cinque cifre di ordine dispari, partendo da sinistra;
//			if (((i + 1) % 2) == 1) {
//				sommaDispari += n;
//
//			} else {
////				2. si raddoppia ogni cifra di ordine pari e, se il risultato e' un numero di due cifre, 
////				esso si riduce ad una sola sommando la cifra relativa alle decine e quella relativa alle unità; 
////				si sommano quindi, tutti i precedenti risultati;
//				int pari = n * 2;
//				if (pari > 10) {
//					String sPari = "" + pari;
//					int decine = Integer.parseInt("" + sPari.charAt(0));
//					int unita = Integer.parseInt("" + sPari.charAt(1));
//					pari = decine + unita;
//				}
//				sommaPari += pari;
//			}
//		}
//
////		3. si determina il totale delle due somme di cui sopra;
//		int totale = sommaPari + sommaDispari;
//
////		4. si sottrae da dieci la cifra relativa alle unità del precedente totale.
//		String sTotale = "" + totale;
//		int controlloCalcolato = 10 - Integer.parseInt("" + sTotale.charAt(sTotale.length() - 1));
//
////		Il carattere di controllo e' la cifra relativa alle unità del risultato.
//		int controllo = Integer.parseInt("" + partitaIva.charAt(partitaIva.length() - 1));
//		if (controllo == controlloCalcolato) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * Checks if the codice fiscale is valid
	 * 
	 * @param codiceFiscale the codiceFiscale
	 * @return whether the data is valid
	 */
	public boolean isValidCodiceFiscale(String codiceFiscale) {
		return isValidMask(codiceFiscale,
				"^[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]$",
				Pattern.CASE_INSENSITIVE) && isValidControlCharCodiceFiscale(codiceFiscale);
	}

	/**
	 * Checks the control character
	 * 
	 * @param codiceFiscale the codice fiscale
	 * @return whether the control character is valid
	 */
	private boolean isValidControlCharCodiceFiscale(String codiceFiscale) {
		return codiceFiscale.charAt(15) == calcControlCharCodiceFiscale(StringUtils.substring(codiceFiscale, 0, 15));
	}

	/**
	 * Computes the control character
	 * 
	 * @param codiceFiscale the codice fiscale
	 * @return the control character
	 */
	private char calcControlCharCodiceFiscale(String codiceFiscale) {
		int sum = 0;

		for (int i = 0; i < codiceFiscale.length(); i++) {
			char c = codiceFiscale.charAt(i);

			int x = (Character.isDigit(c) ? Character.getNumericValue(c)
					: new String(new char[] { c }).getBytes()[0] - 65);

			sum += ((i + 1) % 2 == 0 ? x
					: new int[] { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25,
							24, 23 }[x]);
		}

		return (char) ((sum % 26) + 65);
	}

	/**
	 * Checks whether the mask is valid for the input
	 * 
	 * @param value the value
	 * @param mask  the mask
	 * @return whether the mask is valid
	 */
	public boolean isValidMask(String value, String mask) {
		return isValidMask(value, mask, 0);
	}

	/**
	 * Checks whether the mask is valid for the input
	 * 
	 * @param value the value
	 * @param mask  the mask
	 * @param flags the flags
	 * @return whether the mask is valid
	 */
	public boolean isValidMask(String value, String mask, int flags) {
		if (StringUtils.isNotEmpty(value)) {
			Pattern pattern = Pattern.compile(mask, flags);
			Matcher matcher = pattern.matcher(value);
			return matcher.find();
		}
		return true;
	}

	/**
	 * Check for CUI
	 * 
	 * @param cui the cui
	 * @return whether the CUI is valid
	 */
	public boolean controlloCui(String cui) {
		for (Pattern pattern : CONTROLLO_SICUREZZA_CUI) {
			if (!pattern.matcher(cui).matches()) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		String partitaIva = "01812660049"; // AZIENDA AGRICOLA MALVIRA' DEI F.LLI DAMONTE 
//		Validazioni validazioni = new Validazioni();
//		boolean b = validazioni.isValidPartitaIva(partitaIva);
//		System.out.println("validazioni: " + b);
	}

}
