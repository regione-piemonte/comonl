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

import java.util.*;

/**
 * <p>Title: Servizi per Back-Office</p>
 * <p>Description: Servizi per Back-Office </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: CSI</p>
 * @author
 * @version 1.0
 */
public class CustomDate {

  public static final int DD_MM_YYYY = 0;
  public static final int DD_MM_YY = 1;
  public static final int MM_DD_YYYY = 2;
  public static final int MM_DD_YY = 3;
  public static final int DD_MMMM_YYYY = 4;
  public static final int DD_MMMM_YY = 5;
  public static final int DD_MMM_YYYY = 6;
  public static final int DD_MMM_YY = 7;
  public static final int YYYY_MM_DD = 8;
  public static final int YY_MM_DD = 9;

  private Vector monthNames = null;

  public CustomDate() {
    monthNames = new Vector(12);
    monthNames.addElement("January");
    monthNames.addElement("February");
    monthNames.addElement("March");
    monthNames.addElement("April");
    monthNames.addElement("May");
    monthNames.addElement("June");
    monthNames.addElement("July");
    monthNames.addElement("August");
    monthNames.addElement("September");
    monthNames.addElement("October");
    monthNames.addElement("November");
    monthNames.addElement("December");
  }

  public int getFormatType(String formatType) {
    if (formatType == null) {
      return YYYY_MM_DD;
    }
    else {
      formatType = formatType.toUpperCase();
    }
    if (formatType.equals("DD/MM/YYYY")) {
      return DD_MM_YYYY;
    }
    if (formatType.equals("DD/MM/YY")) {
      return DD_MM_YY;
    }
    if (formatType.equals("MM/DD/YYYY")) {
      return MM_DD_YYYY;
    }
    if (formatType.equals("MM/DD/YY")) {
      return MM_DD_YY;
    }
    if (formatType.equals("DD MMMM YYYY")) {
      return DD_MMMM_YYYY;
    }
    if (formatType.equals("DD MMMM YY")) {
      return DD_MMMM_YY;
    }
    if (formatType.equals("DD MMM YYYY")) {
      return DD_MMM_YYYY;
    }
    if (formatType.equals("DD MMM YY")) {
      return DD_MMM_YY;
    }
    if (formatType.equals("YYYYMMDD")) {
      return YYYY_MM_DD;
    }
    if (formatType.equals("YYMMDD")) {
      return YY_MM_DD;
    }
    return YYYY_MM_DD;
  }

  public String format(long millis, int formatType) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date(millis));
    int yearValue = calendar.get(Calendar.YEAR);
    int monthValue = calendar.get(Calendar.MONTH);
    int dayValue = calendar.get(Calendar.DAY_OF_MONTH);
    String year = "" + yearValue;
    String month = getMonth("" + (monthValue + 1));
    String day = getDay("" + dayValue);
    return format(year, month, day, formatType);
  }

  public String format(String year, String month, String day, int formatType) {
    int monthValue = 0;
    try {
      monthValue = Integer.parseInt(month) - 1;
      switch (formatType) {
        case DD_MM_YYYY:
          return day + "/" + month + "/" + year;
        case DD_MM_YY:
          return day + "/" + month + "/" + year.substring(2, 4);
        case MM_DD_YYYY:
          return month + "/" + day + "/" + year;
        case MM_DD_YY:
          return month + "/" + day + "/" + year.substring(2, 4);
        case DD_MMMM_YYYY:
          return day + " " + monthNames.elementAt(monthValue) + " " + year;
        case DD_MMMM_YY:
          return day + " " + monthNames.elementAt(monthValue) + " " +
              year.substring(2, 4);
        case DD_MMM_YYYY:
          return day + " " +
              ( (String) monthNames.elementAt(monthValue)).substring(0, 3) +
              " " + year;
        case DD_MMM_YY:
          return day + " " +
              ( (String) monthNames.elementAt(monthValue)).substring(0, 3) +
              " " + year.substring(2, 4);
        case YYYY_MM_DD:
          return year + month + day;
        case YY_MM_DD:
          return year.substring(2, 4) + month + day;
      }
    }
    catch (Exception e) {
      //Debug.println("Exception in format from year/month/day : " + e);
    }
    return null;
  }

  private String getDay(String day) {
    if (day == null) {
      return day;
    }
    if (day.length() < 2) {
      day = "0" + day;
    }
    if (day.length() != 2) {
      return null;
    }
    return day;
  }

  /*
   * Return a 2 characters long string containing the month.
   * If the given value month is not proper it returns null.
   */
  private String getMonth(String month) {
    if (month == null) {
      return month;
    }
    if (month.length() > 2) {
      String tmp = null;
      for (int i = 0; i < monthNames.size(); i++) {
        if ( ( (String) monthNames.elementAt(i)).startsWith(month)) {
          tmp = "" + (i + 1);
        }
      }
      if (tmp == null) {
        return null;
      }
      else {
        month = tmp;
      }
    }
    if (month.length() < 2) {
      month = "0" + month;
    }
    if (month.length() != 2) {
      return null;
    }
    return month;
  }

}
