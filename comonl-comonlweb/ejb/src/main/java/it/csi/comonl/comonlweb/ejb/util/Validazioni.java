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
package it.csi.comonl.comonlweb.ejb.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Validazioni varie
 */
public class Validazioni {
	// private static LogUtil log = new LogUtil(Validazioni.class);
	/** Pattern for Intervento.cui */
	private static final Pattern[] CONTROLLO_SICUREZZA_CUI = new Pattern[] {
		Pattern.compile("^[a-zA-Z0-9]{21}$"),
	};

	/**
	 * Checks if the codice fiscale is valid
	 * @param codiceFiscale the codiceFiscale
	 * @return whether the data is valid
	 */
	public boolean isValidCodiceFiscale(String codiceFiscale) {
		return isValidMask(
						codiceFiscale,
						"^[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]$",
						Pattern.CASE_INSENSITIVE)
				&& isValidControlCharCodiceFiscale(codiceFiscale);
	}
	
	/**
	 * Checks if the codice fiscale is valid
	 * @param codiceFiscale the codiceFiscale
	 * @return whether the data is valid
	 */
	public boolean isValidCodiceFiscaleNUmerico(String codiceFiscale) {
		return isValidMask(
						codiceFiscale,
						"[0-9]{11}",
						Pattern.CASE_INSENSITIVE)
				&& isValidControlCharCodiceFiscale(codiceFiscale);
	}
	

	/**
	 * Checks the control character
	 * @param codiceFiscale the codice fiscale
	 * @return whether the control character is valid
	 */
	private boolean isValidControlCharCodiceFiscale(String codiceFiscale) {
		return codiceFiscale.charAt(15) == calcControlCharCodiceFiscale(StringUtils.substring(codiceFiscale, 0, 15));
	}

	/**
	 * Computes the control character
	 * @param codiceFiscale the codice fiscale
	 * @return the control character
	 */
	private char calcControlCharCodiceFiscale(String codiceFiscale) {
		int sum = 0;

		for (int i = 0; i < codiceFiscale.length(); i++) {
			char c = codiceFiscale.charAt(i);

			int x = (Character.isDigit(c) ? Character.getNumericValue(c)
					: new String(new char[] { c }).getBytes()[0] - 65);

			sum += ((i + 1) % 2 == 0 ? x : new int[] { 1, 0, 5, 7, 9, 13, 15,
					17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
					25, 24, 23 }[x]);
		}

		return (char) ((sum % 26) + 65);
	}

	/**
	 * Checks whether the mask is valid for the input
	 * @param value the value
	 * @param mask the mask
	 * @return whether the mask is valid
	 */
	public boolean isValidMask(String value, String mask) {
		return isValidMask(value, mask, 0);
	}

	/**
	 * Checks whether the mask is valid for the input
	 * @param value the value
	 * @param mask the mask
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

	
}
