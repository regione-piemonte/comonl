/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.commax.util;

import java.text.*;
import java.util.*;

/**
 * <p>Title: Servizi per Back-Office</p>
 * <p>Description: Servizi per Back-Office </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: CSI</p>
 * @author
 * @version 1.0
 */
public class DateUtil {
  public DateUtil() {
  }

  public static String giveFormattedDate(Long date, String userFormatType) throws
      IllegalArgumentException {
    if (null == date || date.longValue() == 0) {
      return "";
    }

    CustomDate customDate = new CustomDate();
    int formatType = customDate.getFormatType(userFormatType);
    String tmp = null;
    try {
      tmp = customDate.format(date.longValue(), formatType);
      if (tmp == null) {
        tmp = "";
      }
    }
    catch (Exception e0) {
      throw new IllegalArgumentException("Format :" + formatType +
                                         " not supported");
    }
    return tmp;
  }

  public static long getDifference(Date dataConfronto) {

    long dayDiff = 0;
    long numbOfMilliInDay = 86400000;

    Calendar calendar = new GregorianCalendar();
    calendar.setTime(dataConfronto);
    Calendar calendarOdierno = new GregorianCalendar();

    //Controllo che la data di confronto non viene prima della data di sistema(odierna)
    if (dataConfronto != null && calendar.before(calendarOdierno)) {
      dayDiff = -1;
    }
    else {
      dayDiff = (calendar.getTime().getTime() -
                 calendarOdierno.getTime().getTime()) / numbOfMilliInDay;
    }

    return dayDiff;
  }

  /*
       public static void main(String args[]) {

   GregorianCalendar calendario = new GregorianCalendar(2005,8,1);
   DateUtil dataUtil = new DateUtil();
   System.out.println("Difference::"+dataUtil.getDifference(calendario.getTime()));

   String prova = "Organigramma:\n(a)Pippo;\n(b)Pluto;";
   System.out.println("prova::"+StringUtils.replace(prova,"\n","<br>"));
   System.out.println("prova::"+StringUtils.replaceChars(prova,"\n","<br>"));

       }
   */
  public static Date getDate(String dataDaConvertire) {

    String dateFormat = "dd/MM/yyyy hh:mm";
    SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
    Date date = null;
    try {
      date = dateFormatter.parse(dataDaConvertire);
    }
    catch (ParseException ex1) {
    }
    System.out.println("STAMPA DATA : " + date);
    return date;
  }
  
  public static String convertiDataInStringa(Date data) {
	    if (data == null)
	      return null;
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ITALY);
	    return formatter.format(data);
	  }
  public static String convertiDataInStringaConOrario(Date data) {
	  if (data == null)
		  return null;
	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.ITALY);
	  return formatter.format(data);
  }

	  public static Date convertiStringaInData(String data) {
		  Date ris;
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ITALY);
	    	try {
	    		ris = (java.util.Date) formatter.parse(data);
	    		return ris;
			}
	    	catch (ParseException e) {
				e.printStackTrace();
			}
		  return null;
	  }

	  /**
	   * 72160-71862 - 15.01.2014
	   * Metodo che confronta due date solo esclusivamente per quel che riguarda
	   * Giorno-mese-anno.
	   * 
	   * @param data1 prima data da confrontare
	   * @param data2 seconda data da confrontare
	   * @return true se data1 == data2, false altrimenti
	   */
	  public static boolean isData1EqualsData2 (Date data1,Date data2) {

		  Calendar c1 = getCalendar(data1);
		  Calendar c2 = getCalendar(data2);

		  if( c1.equals(c2)  ) {
			  return true;
		  }
		  else {
			  return false;
		  }
	  }
	  
	  /**
	   * 72160-71862 - 15.01.2014
	   * Metodo che prende in input una data e restituisce un calendar 
	   * 
	   * Setta sempre Ora-minuti-secondo a 10:00:00.000
	   * 
	   * @param dt data in ingresso
	   * @return Calendar con ora settata di default a 10:00:00.000
	   */
	  private static Calendar getCalendar(Date dt) {

		  Calendar c = Calendar.getInstance();

		  c.setTime(dt);
		  c.set(Calendar.HOUR_OF_DAY, 10);
		  c.set(Calendar.MINUTE, 0);
		  c.set(Calendar.SECOND, 0);
		  c.set(Calendar.MILLISECOND, 0);

		  return c;
	  }
	  
}
