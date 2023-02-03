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
package it.csi.comonl.comonlweb.lib.util;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

public abstract class ComonlStringUtils {

	private static final LogUtil LOG = new LogUtil(ComonlStringUtils.class);

	public static String checkNull(String stringa) {
		if (stringa == null)
			stringa = "";
		return stringa;
	}

	public static String checkNull(Object obj) {
		if (obj == null)
			return new String("");
		else
			return obj.toString();
	}

	public static String replace(String text, String repl, String with) {
		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static boolean isStringEmpty(String field) {
		if (field == null)
			field = "";

		return field.length() == 0;
	}

	public static boolean isStringNotEmpty(String field) {
		return !isStringEmpty(field);
	}

	// Questo metodo, copiato dalla classe MessageResource di Struts, e utilizzato
	// per parsificare le stringhe prima di passarle al MessageFormat nel metodo
	// fillParameters, aggiunge l'escape al simbolo di apice singolo (da ' a \').
	public static String escape(String string) {
		if ((string == null) || (string.indexOf('\'') < 0)) {
			return string;
		}

		int n = string.length();
		StringBuffer sb = new StringBuffer(n);

		for (int i = 0; i < n; i++) {
			char ch = string.charAt(i);

			if (ch == '\'') {
				sb.append('\'');
			}

			sb.append(ch);
		}

		return sb.toString();
	}

	public static String fillParameters(String stringa, Object[] argomenti) {
		if (isStringEmpty(stringa))
			return stringa;

		try {
			return MessageFormat.format(escape(stringa), argomenti);
		} catch (IllegalArgumentException iae) {
			return stringa;
		}
	}

	public static Long getLong(String stringa) {
		try {
			return new Long(stringa);
		} catch (Exception exc) {
			return null;
		}
	}

	public static String leftPad(String stringa, int lunghezza, char pad) {
		if (stringa != null) {
			while (stringa.length() < lunghezza) {
				stringa = pad + stringa;
			}
		}
		return stringa;
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}

	public Integer stringToInt(String inputString) {
		if (inputString == null)
			return null;
		Integer ret = null;
		try {
			ret = new Integer(inputString);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public Float stringToFloat(String inputString) {
		if (inputString == null)
			return null;
		Float ret = null;
		try {
			ret = new Float(inputString.replace(',', '.'));
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static String eliminaCaratteriSpeciali (String stringa) {
		if(stringa == null) {
			return null;
		}
		if(stringa.equals("")) {
			return "";
		}

		Iterator elenco = ComonlConstants.ELENCO_CARATTERI_SPECIALI.iterator();
		String carattereSpecialeCorrente;
		while(elenco.hasNext()){
			// Prendo il carattere speciale dall'elenco.
			carattereSpecialeCorrente = (String)elenco.next();
			// Cerco d'assicurarmi che non vi siano spazi.
			carattereSpecialeCorrente=carattereSpecialeCorrente.trim();
			try {
				for (int i = 0; i < stringa.length(); i++) {
					int num = stringa.indexOf(carattereSpecialeCorrente);
					if (num != -1) {
						stringa = stringa.substring(0, num - 1) + stringa.substring(num + 1, stringa.length());
					}
					else {
						// :: Trucchetto
						//    Esco dal ciclo senza continuare a cercare caratteri che non sono piu' presenti
						//    perche' gia' eliminati.
						i = stringa.length();
					}

				}
			}
			catch (Exception ex) {
				LOG.debug("ComonlStringUtils", ex.getMessage());
			}
		}
		return stringa.toUpperCase().trim();
	}

	// ritorna una stringa lunga quanto il numPadding, riempiendo a sinistra con zeri
	public static String paddingDiStringa(String oggetto, int numPadding) {
		String oggPad = "";

		String padding = "";
		for (int i = 0; i < numPadding; i++) {
			padding = padding + "0";
		}

		if (oggetto != null){
			int lunghezzaCodice = oggetto.length();
			padding = padding.substring(0, padding.length() - lunghezzaCodice);
			oggPad = padding + oggetto;
		}

		return oggPad;
	}
	public static String formatDescrDatore(String oriDesc){
		String ret = null;
		
		if(ComonlUtility.isNotVoid(oriDesc)){
			ret = ComonlStringUtils.eliminaCaratteriSpeciali(oriDesc);
			if(ret.length() > 100){
				ret = ret.substring(0, ComonlConstants.DES_DATORE_LAVORO);
			}
		}
		
		return ret;
	}
}
