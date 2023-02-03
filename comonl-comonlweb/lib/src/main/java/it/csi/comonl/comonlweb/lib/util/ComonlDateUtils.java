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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public abstract class ComonlDateUtils {
	public final static String DATE_FORMAT = "dd/MM/yyyy";
	public final static String FULL_DATE_FORMAT = "dd/MM/yyyy HH.mm.ss";
	public final static HashMap<String,String> MESI_CF = new HashMap<String, String>(){{
	       put("A","01"); 
	       put("B","02");
	       put("C","03");
	       put("D","04");
	       put("E","05");
	       put("H","06");
	       put("L","07");
	       put("M","08");
	       put("P","09");
	       put("R","10");
	       put("S","11");
	       put("T","12");
	     }};

	private static SimpleDateFormat myFormat = null;

	public ComonlDateUtils() {
		myFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		myFormat.applyPattern("dd/MM/yyyy");
	}
	
	public static Date get1900() {
		return parseDate("01/01/1900");
	}

	/**
	 * Restituisce una data nel formato passato in input
	 * 
	 * @param date
	 * @param dateFormat
	 * @return String
	 */
	private static String formatDate(Date date, String dateFormat) {
		if (date == null)
			return "";

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);

		return sdf.format(date);
	}

	/**
	 * Restituisce una data in formato gg/mm/aaaa
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DATE_FORMAT);
	}

	/**
	 * Restituisce una data in formato gg/mm/aaaa hh.mm.ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatFullDate(Date date) {
		return formatDate(date, FULL_DATE_FORMAT);
	}

	/**
	 * Restituisce una data in formato gg/mm/aaaa
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(String date) {
		return formatDate(parseDate(date));
	}

	/**
	 * Restituisce una data in formato gg/mm/aaaa hh.mm.ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatFullDate(String date) {
		return formatFullDate(parseDate(date));
	}

	/**
	 * Questo metodo restituisce un oggetto Date partendo da una stringa formattata
	 * secondo il pattern contenuto nella costante DATE_FORMAT
	 *
	 * @param stringDate la stringa contenente la data da parsificare
	 *
	 * @return un'istanza della classe java.util.Date, o <b>null</b> nel caso in cui
	 *         non sia possibile parsificare la stringa
	 */
	public static Date parseDate(String stringDate) {
		return parseDate(stringDate, DATE_FORMAT);
	}

	/**
	 * Questo metodo restituisce un oggetto Date partendo da una stringa formattata
	 * secondo il pattern passato come parametro
	 *
	 * @param stringDate la stringa contenente la data da parsificare
	 * @param dateFormat il pattern con cui effettuare la parsificazione
	 *
	 * @return un'istanza della classe java.util.Date, o <b>null</b> nel caso in cui
	 *         non sia possibile parsificare la stringa
	 */

	public static Date parseDate(String stringDate, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		Date result = sdf.parse(stringDate, new ParsePosition(0));

		// Questo controllo e' reso necessario dal comportamento della SimpleDateFormat
		// sugli anni, con pattern che prevedono gli anni di lunghezza differente da
		// due.
		// In questi casi, viene considerato valido qualsiasi anno, anche di lunghezza
		// superiore a quella prevista dal pattern e fino a nove cifre
		if (result != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			int year = cal.get(Calendar.YEAR);
			char yearSymbol = sdf.getDateFormatSymbols().getLocalPatternChars().charAt(sdf.YEAR_FIELD);
			int yearLength = (dateFormat.lastIndexOf(yearSymbol) - dateFormat.indexOf(yearSymbol)) + 1;
			if (("" + year).length() != yearLength)
				result = null;
		}

		return result;// sdf.parse(stringDate, new ParsePosition(0));
	}

	// Questo metodo si comporta come il compareTo della classe Date, ma al
	// contrario
	// di questo accetta anche un parametro null. La priorita' del null rispetto
	// ai valori non nulli viene stabilita dal flag boolean nullFirst, che settato a
	// true indica che i null precedono gli altri valori
	public static int compareTo(Date data1, Date data2, boolean nullFirst) {
		int result;

		if (data1 != null && data2 != null) {
			result = data1.compareTo(data2);
		} else {
			int nullFirstMultiplier;

			if (nullFirst)
				nullFirstMultiplier = 1;
			else
				nullFirstMultiplier = -1;

			if (data1 == null) {
				if (data2 == null)
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

	public static int extractDayFromDate(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int extractMonthFromDate(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) + 1);
	}

	public static int extractYearFromDate(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Data ora corrente
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Data ora corrente
	 * 
	 * @return
	 */
	public static String getCurrentDateGGMMAAAA() {
		return formatDate(getCurrentDate(), DATE_FORMAT);
	}

	public boolean controllaAnno(String anno) {
		String numeri = "0123456789";
		if (anno.length() != 4)
			return false;
		for (int j = 0; j < 4; j++) {
			boolean ret = false;
			for (int k = 0; k < 10; k++) {
				if (anno.charAt(j) == numeri.charAt(k))
					ret = true;
			}
			if (!ret)
				return false;
		}
		GregorianCalendar ora = new GregorianCalendar();
		if (Integer.parseInt(anno) > ora.get(Calendar.YEAR))
			return false;
		return true;
	}

	public GregorianCalendar convertDataToGregorian(java.sql.Date d) {
		if (d == null)
			return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}

	// aggiunto per accesso AAEP
	public GregorianCalendar convertDataToGregorian(java.util.Date d) {
		if (d == null)
			return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}

	public java.sql.Date convertGregorianToDate(GregorianCalendar gc) {
		if (gc == null)
			return null;
		return new java.sql.Date(gc.getTime().getTime());
	}

	public String dateToString(GregorianCalendar inputDate) {
		if (inputDate != null)
			return myFormat.format(inputDate.getTime());
		else
			return null;
	}

	public String dateToStringConOraMinSec(GregorianCalendar inputDate) {
		if (inputDate != null) {
			myFormat.applyLocalizedPattern("dd/MM/yyyy HH:mm:ss");
			String data = myFormat.format(inputDate.getTime());

			myFormat.applyPattern("dd/MM/yyyy");
			return data;
		}

		else
			return null;
	}

	public static GregorianCalendar stringToDate(String inputString) {
		if (inputString == null)
			return null;
		if (inputString.length() != 10)
			return null;
		if (inputString.charAt(2) != '/' || inputString.charAt(5) != '/')
			return null;
		String giorno = inputString.substring(0, 2);
		String mese = inputString.substring(3, 5);
		String anno = inputString.substring(6, 10);
		int dd, mm, yy;
		try {
			dd = Integer.parseInt(giorno);
			mm = Integer.parseInt(mese);
			yy = Integer.parseInt(anno);
		} catch (NumberFormatException e) {
			return null;
		}
		if (mm < 1 || mm > 12)
			return null;
		if (dd < 1 || dd > 31)
			return null;
		if (yy < 1800 || yy > 2200)
			return null;
		if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && dd > 30)
			return null;
		int giorniFeb;
		if ((yy % 4) == 0) {
			giorniFeb = 29;
			for (int j = 1; j < 4; j++) {
				if (yy == 1600 + 100 * j)
					giorniFeb = 28;
			}
			for (int j = 5; j < 8; j++) {
				if (yy == 1600 + 100 * j)
					giorniFeb = 28;
			}
		} else
			giorniFeb = 28;
		if (mm == 2 && dd > giorniFeb)
			return null;
		GregorianCalendar ret = (GregorianCalendar) GregorianCalendar.getInstance();
		try {
			ret.setTime(myFormat.parse(inputString));
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

}
