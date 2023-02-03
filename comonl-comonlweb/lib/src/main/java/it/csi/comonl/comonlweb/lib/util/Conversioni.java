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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Conversioni implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Conversioni singleton = null;
	private SimpleDateFormat myFormat = null;

	private Conversioni()
	{
		myFormat = (SimpleDateFormat)DateFormat.getDateInstance();
		myFormat.applyPattern("dd/MM/yyyy");
	}

	public static Conversioni getInstance()
	{
		if (singleton == null) createSingleton();
		return singleton;
	}

	/**
	 * createSingleton
	 * PUCCI: Aggiunta per migliorare la gestione del synchronized
	 * il synchronized e' necessario solo quando si crea l'istanza Singleton
	 * ATTENZIONE: e' necessario controllare prima il NULL per evitare di creare
	 * + volte l'istanza nel caso di caller in coda
	 */
	private synchronized static void createSingleton() {
		if(singleton==null) singleton = new Conversioni();
	}

	/**
	 * converte una stringa in data, e torna null se la stringa non contiene una data esistente in calendario
	 * @param inputString
	 * @return
	 */
	public GregorianCalendar stringToDate(String inputString)
	{
		if (inputString == null) return null;
		if (inputString.length() != 10) return null;
		if (inputString.charAt(2) != '/' || inputString.charAt(5) != '/') return null;
		String giorno = inputString.substring(0, 2);
		String mese = inputString.substring(3, 5);
		String anno = inputString.substring(6, 10);
		int dd, mm, yy;
		try
		{
			dd = Integer.parseInt(giorno);
			mm = Integer.parseInt(mese);
			yy = Integer.parseInt(anno);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
		if (mm < 1 || mm > 12) return null;
		if (dd < 1 || dd > 31) return null;
		if (yy < 1800 || yy > 2200) return null;
		if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && dd > 30) return null;
		int giorniFeb;
		if ((yy % 4) == 0)
		{
			giorniFeb = 29;
			for (int j = 1 ; j < 4; j++)
			{
				if (yy == 1600 + 100 * j) giorniFeb = 28;
			}
			for (int j = 5 ; j < 8 ; j++)
			{
				if (yy == 1600 + 100 * j) giorniFeb = 28;
			}
		}
		else giorniFeb = 28;
		if (mm == 2 && dd > giorniFeb) return null;
		GregorianCalendar ret = (GregorianCalendar)GregorianCalendar.getInstance();
		try
		{
			ret.setTime(myFormat.parse(inputString));
		}
		catch(Exception e)
		{
			ret = null;
		}
		return ret;
	}

	public String dateToString(GregorianCalendar inputDate)
	{
		if(inputDate != null)
			return myFormat.format(inputDate.getTime());
		else return null;
	}

	public String dateToStringConOraMinSec(GregorianCalendar inputDate)
	{
		if(inputDate != null) {
			myFormat.applyLocalizedPattern("dd/MM/yyyy HH:mm:ss");//71862 - COMONL-1228
			String data = myFormat.format(inputDate.getTime());

			myFormat.applyPattern("dd/MM/yyyy");
			return data;
		}

		else return null;
	}

	public Integer stringToInt(String inputString){
		if (inputString == null) return null;
		Integer ret = null;
		try
		{
			ret = Integer.parseInt(inputString);
		}
		catch(Exception e)
		{
			ret = null;
		}
		return ret;
	}

	public Float stringToFloat(String inputString)
	{
		if (inputString == null) return null;
		Float ret = null;
		try
		{
			ret = Float.parseFloat(inputString.replace(',', '.'));
		}
		catch(Exception e)
		{
			ret = null;
		}
		return ret;
	}

	public GregorianCalendar convertDataToGregorian(java.sql.Date d){
		if(d == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}

	// aggiunto per accesso AAEP
	public GregorianCalendar convertDataToGregorian(java.util.Date d){
		if(d == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}

	public java.sql.Date convertGregorianToDate(GregorianCalendar gc){
		if(gc==null) return null;
		java.sql.Date d=new java.sql.Date(gc.getTime().getTime());
		return d;
	}

	public String fillCodiceFiscale (String codicefiscale){

		StringBuffer newcf = new StringBuffer(codicefiscale);
		if (codicefiscale.length() < 16){
			int delta = 16 - codicefiscale.length();
			for (int i=0; i < delta  ;i++ ) {
				newcf.append(" ");
			}
		}
		return newcf.toString();
	}

	// Verifica la correttezza formale di un indirizzo email
	public boolean controllaIndirizzoEmail(String indirizzo)
	{
		// Deve esserci un solo carattere '@'
		if (indirizzo == null) return false;
		int index = indirizzo.indexOf("@");
		if (index == -1) return false;
		String dominio = indirizzo.substring(index + 1, indirizzo.length());
		index = dominio.indexOf("@");
		if (index != -1) return false;

		// Deve esserci almeno un carattere '.' nel dominio
		index = dominio.indexOf(".");
		if (index == -1) return false;

		// Verifica che non ci siano caratteri non validi
		for (int i = 0; i < indirizzo.length(); i++)
		{
			char c = indirizzo.charAt(i);
			if (!Character.isLetterOrDigit(c) && c != '.' && c != '@' && c != '-' && c != '_') return false;
		}

		// Tutto ok
		return true;
	}

	public String controllaApici (String parametro){
		StringBuffer checked = new StringBuffer();
		int i = parametro.length();
		for (int j = 0 ; j<i ; j++){
			checked.append(parametro.charAt(j));
			if (parametro.charAt(j) == '\'' )
				checked.append('\'');
		}
		return checked.toString();
	}

	public String controllaApiciJS (String parametro){
		StringBuffer checked = new StringBuffer();
		int i = parametro.length();
		for (int j = 0 ; j<i ; j++){
			if (parametro.charAt(j) == '\'' )
				checked.append('\\');
			checked.append(parametro.charAt(j));
		}
		return checked.toString();
	}

	public boolean controllaProt(String prot)
	{
		String numeri = "0123456789";
		int i = prot.length();
		if (i > 10) return false;
		for (int j = 0 ; j < i ; j++)
		{
			boolean ret = false;
			for (int k = 0 ; k < 10 ; k++)
			{
				if (prot.charAt(j) == numeri.charAt(k)) ret = true;
			}
			if (!ret) return false;
		}
		return true;
	}

	public boolean controllaAnno(String anno)
	{
		String numeri = "0123456789";
		if(anno.length() != 4) return false;
		for (int j = 0 ; j < 4 ; j++)
		{
			boolean ret = false;
			for(int k = 0 ; k < 10 ; k++)
			{
				if (anno.charAt(j) == numeri.charAt(k)) ret = true;
			}
			if(!ret) return false;
		}
		GregorianCalendar ora = new GregorianCalendar();
		if (Integer.parseInt(anno) > ora.get(GregorianCalendar.YEAR)) return false;
		return true;
	}

	public boolean controllaCAP(String cap){
		String numeri="0123456789";
		int i=cap.length();
		if(i<5) return false;
		for (int j = 0 ; j<i ; j++){
			boolean ret=false;
			for(int k=0;k<10;k++){
				if (cap.charAt(j) == numeri.charAt(k))
					ret = true;
			}
			if(!ret)
				return false;
		}
		return true;
	}

	public boolean controllaTel(String tel){
		String numeri="0123456789 /";
		int i=tel.length();
		if(i>15) return false;
		for (int j = 0 ; j<i ; j++){
			boolean ret=false;
			for(int k=0;k<12;k++){
				if (tel.charAt(j) == numeri.charAt(k))
					ret = true;
			}
			if(!ret)
				return false;
		}
		return true;
	}

	public boolean checkGiornoFineAnno(GregorianCalendar cal) {
		int ultimoGiorno = 31;
		int meseUltimo = 11;  // Calendar.MONTH parte da zero
		//	   Date data = cal.getTime();

		if(cal.get(Calendar.DAY_OF_MONTH) == ultimoGiorno && cal.get(Calendar.MONTH) == meseUltimo) {
			return true;
		}

		return false;
	}
}
