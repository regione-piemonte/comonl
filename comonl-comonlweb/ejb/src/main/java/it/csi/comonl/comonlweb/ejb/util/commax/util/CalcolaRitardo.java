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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class CalcolaRitardo
{
  private  int GIORNO = 0;
  private  int MESE = 1;
  private  int ANNO = 2;
  private static final int DOMENICA = 1;
  private static final int SABATO   = 7;
  private GregorianCalendar easter = null;
  private GregorianCalendar eastermonday = null;

//  private String [] festivita = {"1-1","6-1","25-4","1-5","2-6","15-8","1-11","8-12","25-12","26-12","31-12"};
  private String [] festivita = {"1-1","6-1","25-4","1-5","2-6","15-8","1-11","8-12","25-12","26-12"};

  public CalcolaRitardo() {
  }

  private int scartaGiorni(GregorianCalendar gc, GregorianCalendar gc2) {

    int annoN = gc.get(Calendar.YEAR);
    System.out.println("****ANNON*****=" + annoN);
    int scarto = 0;

    String[] dataString = null;

    if (easter == null) {
      easter = easterDate(annoN);
      eastermonday = getEasterMonday(easter);
    }

    int inizio = gc.get(Calendar.DAY_OF_MONTH);
    System.out.println("********inizio=" + inizio);
    int maxGiorni = gc2.get(Calendar.DAY_OF_MONTH);
    System.out.println("max giorni: " + maxGiorni);

    for (int j = inizio; j <= maxGiorni; j++) {
      int cg = gc.get(GregorianCalendar.DAY_OF_WEEK);

      dataString = new String[4];
      dataString[GIORNO] = gc.get(gc.DATE) + "";
      dataString[MESE] = (gc.get(gc.MONTH) + 1) + "";
      dataString[ANNO] = annoN + "";

      if (isFestivo(dataString, gc)) {
        scarto = scarto + 1;
      }
      else if (cg == DOMENICA) {
        scarto = scarto + 1;
      }
      else if (cg == SABATO) {
        // Eugenio: Non contiamo il sabato. (In originale non era commentato).
        //scarto = scarto + 1;
      }
      gc.add(gc.DATE, 1);
    }
    return scarto;
  }

// metodo per calcolare la data di pasqua avuto in ingresso un anno

  public GregorianCalendar easterDate(int eY) {

    int prime, dominical;
    int i, j;
    int eMonth = 0, eDay = 0;
    int sundayLetter[] = new int[35];
    int
        goldenNumber[] = {
        14, 3, 0, 11, 0, 19, 8, 0, 16, 5, 0, 13, 2, 0, 10, 0, 18, 7, 0, 15, 4,
        0, 12, 1, 0, 9, 17, 6,
        0, 0, 0, 0, 0, 0, 0};

    //System.out.println("Easter.easterDate called for " + eY);

    prime = (eY + 1) % 19;
    if (prime == 0)
      prime = 19;

    dominical = (eY + (eY / 4) + 6) % 7;

    //System.out.println("Prime is " + prime + ", Dominical is " + dominical);

    for (i = 0; i < 35; i++) {
      sundayLetter[i] = 6 - ( (i + 6 - 4) % 7);
    }

    lookup:for (i = 0; i < 35; i++) {
      if (prime == goldenNumber[i]) {
        for (j = i + 1; j < 35; j++) {
          if (sundayLetter[j] == dominical) {
            //System.out.println("i is " + i + ", j is " + j);
            if (j > 9) {
              eMonth = 4;
              eDay = j - 9;
            }
            else {
              eMonth = 3;
              eDay = j + 22;
            }
            break lookup;
          }
        }
      }
    }

    //System.out.println("Day is " + eDay + ", month is " + eMonth);
    GregorianCalendar eGC = new GregorianCalendar(eY,eMonth-1,eDay);
    return eGC;
  } //fine metodo

  private boolean isFestivo(String[] data, GregorianCalendar date) {

    boolean isFestivo = false;

    String giorno = data[GIORNO];
    String mese = data[MESE];
    String festa = giorno + "-" + mese;

    int meseOggi = date.get(GregorianCalendar.MONTH);
    int meseEaster = easter.get(GregorianCalendar.MONTH);
    int  giornoOggi = date.get(GregorianCalendar.DATE);
    int giornoEaster = easter.get(GregorianCalendar.DATE);
    int annoOggi = date.get(GregorianCalendar.YEAR);
    int annoEaster = easter.get(GregorianCalendar.YEAR);
    int annoEasterM = eastermonday.get(GregorianCalendar.YEAR);
    int meseEasterM = eastermonday.get(GregorianCalendar.MONTH);
    int giornoEasterM = eastermonday.get(GregorianCalendar.DATE);

    if ( (annoOggi == annoEaster && meseOggi == meseEaster &&
          giornoOggi == giornoEaster) ||
        (annoOggi == annoEasterM && meseOggi == meseEasterM &&
         giornoOggi == giornoEasterM)) {
      return true;
    }
    /*  if (date.compareTo(easter) == 0 || date.compareTo(eastermonday) == 0)
        return true;*/

    for (int i = 0; i < festivita.length; i++) {
      if (festa.equals(festivita[i]))
        isFestivo = true;
    }

    return isFestivo;
  } //fine metodo

//calcolo della pasquetta
  private GregorianCalendar getEasterMonday(GregorianCalendar easter) {

    GregorianCalendar easterMon = new GregorianCalendar(easter.get(GregorianCalendar.YEAR),easter.get(GregorianCalendar.MONTH),easter.get(GregorianCalendar.DATE));
    easterMon.add(easter.DATE,1);
    System.out.println("Date is (monday): " + easterMon.get(Calendar.DATE));
    System.out.println("Date is (monday): " + easterMon);
    System.out.println("Year is (monday): " + easterMon.get(GregorianCalendar.YEAR));
    return easterMon;
  }

  private long differenceInDays(GregorianCalendar date1, GregorianCalendar date2) {
    final long msPerDay = 1000 * 60 * 60 * 24;

    final long date1Milliseconds = date1.getTime().getTime();
    final long date2Milliseconds = date2.getTime().getTime();
    final long result = (date1Milliseconds - date2Milliseconds) / msPerDay;

    return result;
  }

  public long calcola(GregorianCalendar gc1, GregorianCalendar gc2) {
    int annoI = gc1.get(gc1.YEAR);
    int annoF = gc2.get(gc2.YEAR);
    long retOre = -99;
    if (annoF == annoI) {
      long diff = differenceInDays(gc2, gc1);
      System.out.println("DIFF=" + diff);
      int scarto = scarta(gc1,gc2);
      System.out.println("SCARTO=" + scarto);
      retOre = (diff - scarto) * 24;
      return retOre;
    }
    else if (annoF > annoI) {
      long diff = differenceInDays(gc2, gc1);
      GregorianCalendar fineI = new GregorianCalendar(annoI, 11, 31);
      int scarto1 = scarta(gc1, fineI);
      GregorianCalendar inizioF = new GregorianCalendar(annoF, 0, 1);
      int scarto2 = scarta(inizioF, gc2);
      retOre = (diff - (scarto1 + scarto2)) * 24;
      return retOre;
    }
    else
      return retOre;
  }

  private int scarta(GregorianCalendar gc1, GregorianCalendar gc2) {
    int scarto=0;
    int annoF = gc2.get(gc2.YEAR);
    int meseI=gc1.get(gc1.MONTH);
    int meseF=gc2.get(gc1.MONTH);
  if(meseI==meseF)
  scarto = scartaGiorni(gc1, gc2);
  else if(meseF>meseI){
    GregorianCalendar tmpcg1 = new GregorianCalendar(annoF, meseI + 1, 1);
    tmpcg1.add(tmpcg1.DATE, -1);
    scarto = scarto + scartaGiorni(gc1, tmpcg1);
    for (int mese = meseI + 1; mese < meseF; mese++) {
      GregorianCalendar tmpcg2 = new GregorianCalendar(annoF, mese, 1);
      GregorianCalendar tmpcg3 = new GregorianCalendar(annoF, mese + 1, 1);
      tmpcg3.add(tmpcg3.DATE, -1);
      scarto = scarto + scartaGiorni(tmpcg2, tmpcg3);
    }
    GregorianCalendar tmpcg4 = new GregorianCalendar(annoF, meseF, 1);
    scarto = scarto + scartaGiorni(tmpcg4, gc2);
  }
  return scarto;
  }


  public boolean checkDomenica(Date data) {
	  GregorianCalendar cal = new GregorianCalendar();
	  cal.setTime(data);
	  
//	  int giornoDellaSettimana = data.getDay(); deprecated
	  int giornoDellaSettimana = cal.get(Calendar.DAY_OF_WEEK);
	  if(giornoDellaSettimana == 1) {
		 return true;
	  }
	  return false;
  }
  
  public boolean checkFestivita(Date data){
	  GregorianCalendar cal0 = new GregorianCalendar();
	  cal0.setTime(data);

//	  int anno = data.getYear(); deprecated
	  int anno = cal0.get(Calendar.YEAR);

	  // domenica
	  if(checkDomenica(data)) {
		  return true;
	  }
	  
	  GregorianCalendar cal = easterDate(anno);
	  // Pasqua
	  if(data == cal.getTime()) {
		  return true;
	  }
	  // pasquetta
	  else if(Format.dateAddDays(data, 1) == Format.dateAddDays(cal.getTime(), 1)) {
		  return true;
	  }
	  // controllo con la lista delle festivita' standard
	  else {
		  String dataString = Format.convertiDataInStringa(data);
		  String giorno = dataString.substring(0, 2);
          String mese = dataString.substring(3, 5);
		  String ddMm = giorno + "-" + mese;
		  
		  for (int i = 0; i < festivita.length; i++) {
			  if (ddMm.equals(festivita[i]))
				  return true;
		  }
	  }
	  return false;
  }
  
}
