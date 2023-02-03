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

import java.math.BigDecimal;

/**
 * <p>Title: SIGOP</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: CSI Piemonte</p>
 * @author Ivan Morra
 * @version 1.0
 */

import java.text.ParseException;

public abstract class ComonlNumberUtils {

	/**
	 * Somme e sottrazioni vengono arrotondate correttamente
	 * 
	 * @param numero
	 * @param precisione
	 * @return double
	 */
	public static double arrotonda(double numero, int precisione) {
		return new java.math.BigDecimal(numero).setScale(precisione, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String checkNumericField(String field) throws ParseException {
		field = field.replace(',', '.');

		try {
			Double.parseDouble(field);

			// Occorre considerare l'eventualita di numeri con suffissi speciali,
			// riconosciuti da Java come facenti parte del formato numerico
			// (ad esempio, 20d o 134f). Per evitare che la conversione numerica
			// comprenda questi caratteri, la stringa viene spezzata in due
			if (field.length() > 1)
				Double.parseDouble(field.substring(field.length() - 1));

		} catch (NumberFormatException nfe) {
			throw new ParseException(nfe.getMessage(), 0);
		}
		return field;
	}

	// Questo metodo si comporta come il compareTo della classe BigDecimal, ma al
	// contrario
	// di questo accetta anche un parametro null. La priorita' del null rispetto
	// ai valori non nulli viene stabilita dal flag boolean nullFirst, che settato a
	// true indica che i null precedono gli altri valori
	public static int compareTo(BigDecimal decimal1, BigDecimal decimal2, boolean nullFirst) {
		int result;

		if (decimal1 != null && decimal2 != null) {
			result = decimal1.compareTo(decimal2);
		} else {
			int nullFirstMultiplier;

			if (nullFirst)
				nullFirstMultiplier = 1;
			else
				nullFirstMultiplier = -1;

			if (decimal1 == null) {
				if (decimal2 == null)
					result = 0;
				else
					result = -1;
			} else {
				result = 1;
			}

			result = result * nullFirstMultiplier;
		}

		return result;
	}

	// Questo metodo si comporta come il compareTo della classe Long, ma al
	// contrario
	// di questo accetta anche un parametro null. La priorita' del null rispetto
	// ai valori non nulli viene stabilita dal flag boolean nullFirst, che settato a
	// true indica che i null precedono gli altri valori
	public static int compareTo(Long long1, Long long2, boolean nullFirst) {
		int result;

		if (long1 != null && long2 != null) {
			result = long1.compareTo(long2);
		} else {
			int nullFirstMultiplier;

			if (nullFirst)
				nullFirstMultiplier = 1;
			else
				nullFirstMultiplier = -1;

			if (long1 == null) {
				if (long2 == null)
					result = 0;
				else
					result = -1;
			} else {
				result = 1;
			}

			result = result * nullFirstMultiplier;
		}

		return result;
	}

	/**
	 * Somma due numeri
	 * 
	 * @param addendo1
	 * @param addendo2
	 * @return BigDecimal
	 */
	public static BigDecimal add(BigDecimal addendo1, BigDecimal addendo2) {
		addendo1 = (addendo1 == null) ? new BigDecimal("0") : addendo1;
		addendo2 = (addendo2 == null) ? new BigDecimal("0") : addendo2;

		try {
			java.math.BigDecimal sommaBD = new java.math.BigDecimal(addendo1.toString());
			return sommaBD.add(new java.math.BigDecimal(addendo2.toString()));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Converte una stringa in BigDecimal
	 * 
	 * @param numero
	 * @return BigDecimal
	 */
	public static BigDecimal convertStringToBigDecimal(String numero) {
		try {
			return new BigDecimal(numero);
		} catch (Exception ex) {
			return new BigDecimal("0");
		}
	}
}
