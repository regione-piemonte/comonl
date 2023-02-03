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
package it.csi.comonl.comonlweb.lib.util.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Helper for dates conversion
 */
public final class DateConvertHelper {

	/** The format-dateFormat map */
	private static final ThreadLocal<Map<String, SimpleDateFormat>> DATE_FORMATS = ThreadLocal.withInitial(HashMap::new);

	/** Private constructor */
	private DateConvertHelper() {
		// Prevent instantiation
	}

	/**
	 * Default date formatting
	 * @param date the date
	 * @return the string in YYYY/MM/DD format
	 */
	public static String defaultDateFormat(Date date) {
		return formatDate(date, "yyyy/MM/dd");
	}
	/**
	 * Default time formatting
	 * @param date the date
	 * @return the string in HH:mm:ss format
	 */
	public static String defaultTimeFormat(Date date) {
		return formatDate(date, "HH:mm:ss");
	}
	/**
	 * Default timestamp formatting
	 * @param date the date
	 * @return the string in yyyy/MM/DD HH:mm:ss format
	 */
	public static String defaultTimestampFormat(Date date) {
		return formatDate(date, "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * Add a quantity of fields to the date
	 * @param starting the starting date
	 * @param field the field to add
	 * @param quantity the quantity to add
	 * @return the new date
	 * @see Calendar#add(int, int)
	 */
	public static Date addToDate(Date starting, int field, int quantity) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(starting);
		cal.add(field, quantity);
		return cal.getTime();
	}

	/**
	 * Extracts a field from the date
	 * @param date the date
	 * @param field the field to extract
	 * @return the extracted field
	 */
	public static int extractField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	/**
	 * Gets the dates betewwn two given dates
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the dates in between
	 */
	public static List<Date> getDatesBetween(Date startDate, Date endDate) {
		long delta = Math.abs(endDate.getTime() - startDate.getTime());
		long days = TimeUnit.DAYS.convert(delta, TimeUnit.MILLISECONDS);
		return DateConvertHelper.getDatesAfter(startDate, days);
	}
	/**
	 * Gets the dates after the given date, for the given number of days
	 * @param startDate the start date
	 * @param days the number of days
	 * @return the dates after
	 */
	public static List<Date> getDatesAfter(Date startDate, long days) {
		return IntStream.iterate(0, i -> i + 1)
				.limit(days)
				.mapToObj(i -> DateConvertHelper.addToDate(startDate, Calendar.DAY_OF_YEAR, i))
				.collect(Collectors.toList());
	}

	/**
	 * Date formatting with given pattern
	 * @param date the date
	 * @param format the pattern
	 * @return the formatted date
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat simpleDateFormat = DATE_FORMATS.get().computeIfAbsent(format, SimpleDateFormat::new);
		return simpleDateFormat.format(date);
	}

	/**
	 * Date parsing with given pattern
	 * @param date the date
	 * @param format the pattern
	 * @return the parsed date
	 */
	public static Date parseDate(String date, String format) {
		SimpleDateFormat simpleDateFormat = DATE_FORMATS.get().computeIfAbsent(format, SimpleDateFormat::new);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Cannot parse date \"" + date + "\" in format \"" + format + "\"");
		}
	}

	/**
	 * Prints a month as a string
	 * @param date the date
	 * @param locale locale
	 * @return the month as a string
	 */
	public static String printMonth(Date date, Locale locale) {
		int monthNumber = DateConvertHelper.extractField(date, Calendar.MONTH);
		Month month = Month.of(monthNumber + 1);
		return month.getDisplayName(TextStyle.FULL, locale);
	}
}
