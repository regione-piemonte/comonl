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
import java.text.SimpleDateFormat;
import java.text.ParsePosition;




/**
 * <p>Title: SILP</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte</p>
 * @author not attributable
 * @version 1.0
 */

public class Format {

	public static int parseInt(String value) {
		int n = 0;
		try {
			n = Integer.parseInt(value);
		}
		catch (NumberFormatException ex) {
			return -1;
		}
		return n;
	}

	public static Long parseLong(String longValue) {
		try {
			return new Long(longValue);
		}
		catch (Exception ex) {
			return null;
		}
	}

	public static String formatLong(Long longValue) {
		try {
			return longValue.toString();
		}
		catch (Exception ex) {
			return "";
		}
	}


	public static boolean data2MaggioreData1(Date data1, Date data2) {
		if(confrontaDate(data1,data2) == 1){
			return true;  
		}

		return false;
	}


	public static int confrontaDate(Date data1, Date data2) {
		try {
			String sData1 = getStringDate(data1);
			String sData2 = getStringDate(data2);
			int dd1 = Integer.parseInt(sData1.substring(0, 2));
			int mm1 = Integer.parseInt(sData1.substring(3, 5));
			int yy1 = Integer.parseInt(sData1.substring(6, 10));
			int dd2 = Integer.parseInt(sData2.substring(0, 2));
			int mm2 = Integer.parseInt(sData2.substring(3, 5));
			int yy2 = Integer.parseInt(sData2.substring(6, 10));
			//se data2 e' maggiore di data1
			if (yy2 > yy1)
				return 1;
			if (yy2 == yy1 && mm2 > mm1)
				return 1;
			if (yy2 == yy1 && mm2 == mm1 && dd2 > dd1)
				return 1;
			//se data2 e' minore di data1
			if (yy2 < yy1)
				return -1;
			if (yy2 == yy1 && mm2 < mm1)
				return -1;
			if (yy2 == yy1 && mm2 == mm1 && dd2 < dd1)
				return -1;
			//altrimenti sono uguali
		}
		catch (Exception ex) {
			return 2;
		}
		return 0;
	}

	// confronto particolare fra due date: oltre all'anno, mese, giorno, anche ore, minuti e secondi
	public static int confrontaDateOreMinutiSecondi(Date d1, Date d2) {
		int ris = 0;
		try {
			if (d1 != null && d2 == null) {
				return -1;
			}
			if (d1 == null && d2 == null) {
				return 0;
			}
			if (d1 == null && d2 != null) {
				return 1;
			}
			ris = 0;
			GregorianCalendar c1 = new GregorianCalendar();
			GregorianCalendar c2 = new GregorianCalendar();
			c1.setTime(d1);
			c2.setTime(d2);
			ris = confrontaDate(d1, d2);
			if (ris == 0) {
				//controllo ore, min, sec
				int ora1 = c1.get(Calendar.HOUR);
				int ora2 = c2.get(Calendar.HOUR);
				int min1 = c1.get(Calendar.MINUTE);
				int min2 = c2.get(Calendar.MINUTE);
				int sec1 = c1.get(Calendar.SECOND);
				int sec2 = c2.get(Calendar.SECOND);
				//controllo ore
				if (ora1 < ora2) {
					ris = 1;
					return ris;
				}
				else if (ora1 == ora2) {
					ris = 0;
				}
				else {
					ris = -1;
					return ris;
				}
				//controllo minuti
				if (ris == 0 && min1 < min2) {
					ris = 1;
					return ris;
				}
				else if (min1 == min2) {
					ris = 0;
				}
				else {
					ris = -1;
					return ris;
				}
				//controllo secondi
				if (ris == 0 && sec1 < sec2) {
					ris = 1;
					return ris;
				}
				else if (sec1 == sec2) {
					ris = 0;
				}
				else {
					ris = -1;
					return ris;
				}
			}
		}
		catch (Exception ex) {
			return 2;
		}
		return ris;
	}

	/** @todo CommonUTI */
	public static Date parseDate(String date) {
		return convertiStringaInData(date);
	}

	public static String formatDate(Date date) {
		return date.toString();
	}

	public static boolean verificaAnnoMinoreCorrente(String anno) {
		String sysdate = convertiDataInStringa(new Date());
		String annoCorrente = sysdate.substring(6);
		if (parseInt(anno) > parseInt(annoCorrente))return false;
		return true;
	}


	/**
	 * Sostituisce la replaceFirst di String
	 *  (in uso solo dalla 1.4)
	 */
	public static String replaceFirstString(
			final String input,
			final String exp,
			final String replacement)
	{
		final StringBuffer result = new StringBuffer();
		int startIdx = 0;
		int idxOld = 0;
		if ( (idxOld = input.indexOf(exp, startIdx)) >= 0)
		{
			// SE l'exp che stiamo cercando esiste --> idxOld
			// conterra' l'indice dell'ultimo char della prima occorrenza
			// l'unica che ci interessa rimpiazzare
			// Per il resto tutto funziona come la replaceString (vedi sotto)
			result.append(input.substring(startIdx, idxOld));
			result.append(replacement);
			startIdx = idxOld + exp.length();
		}
		result.append(input.substring(startIdx));
		return result.toString();
	}


	public static String replaceString(
			final String aInput,
			final String aOldPattern,
			final String aNewPattern) {

		final StringBuffer result = new StringBuffer();
		//startIdx and idxOld delimit various chunks of aInput; these
		//chunks always end where aOldPattern begins
		int startIdx = 0;
		int idxOld = 0;
		while ( (idxOld = aInput.indexOf(aOldPattern, startIdx)) >= 0) {
			//grab a part of aInput which does not include aOldPattern
			result.append(aInput.substring(startIdx, idxOld));
			//add aNewPattern to take place of aOldPattern
			result.append(aNewPattern);

			//reset the startIdx to just after the current match, to see
			//if there are any further matches
			startIdx = idxOld + aOldPattern.length();
		}
		//the final chunk will go to the end of aInput
		result.append(aInput.substring(startIdx));
		return result.toString();
	}

	public static java.util.Date getUtilDate(String data) {
		if (data == null || data.equals(""))
			return null;
		String newDate = data.substring(0, 2) + "/" +
				data.substring(3, 5) + "/" +
				(data.length() == 10 ? data.substring(6, 10) : data.substring(6, 8));
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(newDate, pos);
	}

	public static String getStringDate(java.util.Date data) {
		if (data == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(data);
	}

	public static String getStringDateOraCorrente(java.util.Date data) {
		if (data == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(data);
	}

	public static java.util.Date convertiStringaInData(String data) {
		if (data == null || data.equals(""))
			return null;
		java.util.Date date = null;
		String newDate = "";
		java.text.SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		java.text.ParsePosition pos = new ParsePosition(0);
		try {
			newDate = data.substring(0, 2) + "/" +
					data.substring(3, 5) + "/" +
					data.substring(6, 10);
			date = formatter.parse(newDate, pos);
		}
		catch (Exception ex) {
			date = null;
		}
		return date;
	}

	public static String convertiDataInStringa(Date date, String delim) {
		if (date == null || date.equals(""))
			return null;
		String data = convertiDataInStringa(date);
		String newDate = "";
		String format = "dd" + delim + "MM" + delim + "yyyy";
		java.text.SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.text.ParsePosition pos = new ParsePosition(0);
		try {
			newDate = data.substring(0, 2) + delim +
					data.substring(3, 5) + delim +
					data.substring(6, 10);
		}
		catch (Exception ex) {
			date = null;
		}
		return newDate;
	}

	//converte in data una stringa che rappresenta un adata e ora nel formato
	//dd/mm/yyyy hh:mm:ss anche con secondi opzionali
	public static java.util.Date convertiStringaConOreMinutiSecondiInData(String
			data) {
		if (data == null || data.trim().equals(""))
			return null;
		GregorianCalendar date = null;
		String giorno = data.substring(0, 2);
		String mese = data.substring(3, 5);
		String anno = data.substring(6, 10);
		String ora = "00";
		String minuti = "00";
		String secondi = "00";
		String sottoStringaOreMinutiSecondi = null;
		int posPrimoSpazio = data.lastIndexOf(" ");
		sottoStringaOreMinutiSecondi = data.substring(posPrimoSpazio + 1);
		ora = sottoStringaOreMinutiSecondi.substring(0, 2);
		minuti = sottoStringaOreMinutiSecondi.substring(3, 5);
		//se la parte contenente ore minuti secondi ha piu' di 5 caratteri vuol dire che ha anche i secondi
		if (sottoStringaOreMinutiSecondi.length() > 5) {
			secondi = sottoStringaOreMinutiSecondi.substring(6, 8);
		}

		try {
			date = new GregorianCalendar(Integer.parseInt(anno),
					Integer.parseInt(mese) - 1,
					Integer.parseInt(giorno),
					Integer.parseInt(ora),
					Integer.parseInt(minuti),
					Integer.parseInt(secondi)
					);
		}
		catch (Exception ex) {
			date = null;
		}
		return date.getTime();
	}

	public static GregorianCalendar convertiStringaInGragorianCalendar(String
			data) {
		if (data == null || data.trim().equals(""))
			return null;
		GregorianCalendar date = null;
		try {
			date = new GregorianCalendar(Integer.parseInt(data.substring(6, 10)),
					Integer.parseInt(data.substring(3, 5)) - 1,
					Integer.parseInt(data.substring(0, 2)));
		}
		catch (Exception ex) {
			date = null;
		}
		return date;
	}

	public static String convertiGragorianCalendarInStringa(GregorianCalendar
			data) {
		if (data == null)
			return null;
		String year = "";
		String actualMonthMax = "" + data.getActualMaximum(Calendar.DAY_OF_MONTH);
		String day = "" + data.get(Calendar.DAY_OF_MONTH);
		if (day.length() == 1)
			day = "0" + day;
		String month = "";
		month = "" + (data.get(Calendar.MONTH) + 1);
		if (month.equalsIgnoreCase("0")) {
			month = "12";
			year = "" + (data.get(Calendar.YEAR));
		}
		else
			year = "" + data.get(Calendar.YEAR);
		if (month.length() == 1)
			month = "0" + month;
		return day + "/" + month + "/" + year;
	}

	public static String convertiDataInStringa(Date data) {
		if (data == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(data);
	}

	public static String convertiDataEOraInStringa(java.util.Date data) {
		if (data == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
		return formatter.format(data);
	}

	/**
	 *   restituisce la DIFFERENZA fra due date in giorni
	 *   ai fini del calcolo della durata di un contratto
	 *   (si somma 1 al risultato della diffDays nornali)
	 *   in tal modo un contratto che inizia e finisce lo stesso giorno
	 *   ha durata 1 giorno e non 0
	 */
	public static int dateDiffDaysPerContratto(java.util.Date d1,
			java.util.Date d2) {
		return Format.dateDiffDays(d1, d2) + 1; // 1 giorno e' la durata minima di un contratto !!!
	}

	/**
	 *   restituisce la DIFFERENZA fra due date in giorni
	 */
	public static int dateDiffDays(java.util.Date d1, java.util.Date d2) {
		int result;
		if (d2.after(d1))
			result = (int) ( (d2.getTime() / 86400000) - (d1.getTime() / 86400000));
		else
			result = (int) ( (d1.getTime() / 86400000) - (d2.getTime() / 86400000));
		return result;
	}

	// aggiunge giorni a una data e restituisce la data
	public static Date dateAddDays(java.util.Date date, int days) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	// aggiunge mesi a una data e restituisce la data
	public static Date dateAddMonth(java.util.Date date, int month) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	// aggiunge anni a una data e restituisce la data
	public static Date dateAddYear(java.util.Date date, int year) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	//  /**
	//   * Converte un BOOLEAN TRUE  'S' definito nel database  in valore booleano
	//   * Regole:
	//   *   'S' -> true
	//   *   altro o null -> false
	//   * @param value
	//   * @return
	//   */
	//  public static boolean convertiDTOBooleanInboolean(String value) {
	//    if (value != null &&
	//        value.equalsIgnoreCase(AbstractDTO.BOOLEAN_TRUE)) {
	//      return true;
	//    }
	//    else {
	//      return false;
	//    }
	//  }

	/**
	 * Versione del metodo convertiDTOBooleanInboolean che restituisce una stringa
	 * @param value
	 * @return 'true' o 'false'
	 */
	//  public static String convertiDTOBooleanInStringaBoolean(String value){
	//    return new Boolean(convertiDTOBooleanInStringaBoolean(value)).toString();
	//  }

	/*
  public static boolean convertiStringInBoolean(String value) {
    if (value != null) {
      return Boolean.valueOf(value).booleanValue();
    }
    else {
      return false;
    }
  }
	 */

	// dal 10 1 2006
	// questo metodo puo' accettare in input
	// FALSE/false/TRUE/true/S/N/s/n/SI/NO/1/0
	//public static boolean convertiStringInBoolean(String value) {
	//  if (value != null) {
	//
	//    value = value.toLowerCase();
	//    if (value.equals("si") || value.equals("s") || value.equals("1") || value.equals("true"))
	//    {
	//      value = "true";
	//    }
	//    else if (value.equals("no") || value.equals("n") || value.equals("0") || value.equals("false"))
	//    {
	//      value = "false";
	//    }
	//    else
	//    {
	//        throw new SysException(new Exception("BaseModel.convertiStringInBoolean: Valore di input:'" + value + "' non gestito. Valori ammessi:si/s/1/true no/n/0/false"));
	//    }
	//
	//    return Boolean.valueOf(value).booleanValue();
	//  }
	//  else {
	//    // lo lascio cosi' per retrocompatibilita' ma e' una vera porcata (antonio)
	//    return false;
	//  }
	//}

	//  public static String convertiStringaBooleanInDTOBoolean(String value) {
	//    boolean valoreBooleano = convertiStringInBoolean(value);
	//    if (valoreBooleano) {
	//      return AbstractDTO.BOOLEAN_TRUE;
	//    }
	//    else {
	//      return AbstractDTO.BOOLEAN_FALSE;
	//    }
	//  }

	//  public static String convertiDTOBooleanInStringaBoolean(String value) {
	//    if (value != null && value.equalsIgnoreCase(AbstractDTO.BOOLEAN_TRUE)) {
	//      return "true";
	//    }
	//    else {
	//      return "false";
	//    }
	//  }

	public static boolean verificaCodiceFiscale(String codice) {
		if (codice == null) {
			codice = "";
		}
		codice = codice.trim();
		//LUNGHEZZA ERRATA
		if (codice.length() != 16) {
			return false;
		}
		//LUNGHEZZA CORRETTA
		else if (codice.length() == 16) {
			String uno = codice.substring(0, 1);
			char unoc = uno.charAt(0);
			String due = codice.substring(1, 2);
			char duec = due.charAt(0);
			String tre = codice.substring(2, 3);
			char trec = tre.charAt(0);
			String quattro = codice.substring(3, 4);
			char quattroc = quattro.charAt(0);
			String cinque = codice.substring(4, 5);
			char cinquec = cinque.charAt(0);
			String sei = codice.substring(5, 6);
			char seic = sei.charAt(0);
			//CONTROLLO FORMALE
			if (Character.isDigit(unoc) ||
					Character.isDigit(duec) ||
					Character.isDigit(trec) ||
					Character.isDigit(quattroc) ||
					Character.isDigit(cinquec) ||
					Character.isDigit(seic) ||
					Character.isLetter(codice.substring(6, 7).charAt(0)) ||
					Character.isLetter(codice.substring(7, 8).charAt(0)) ||
					Character.isDigit(codice.substring(8, 9).charAt(0)) ||
					Character.isLetter(codice.substring(9, 10).charAt(0)) ||
					Character.isLetter(codice.substring(10, 11).charAt(0)) ||
					Character.isDigit(codice.substring(11, 12).charAt(0)) ||
					Character.isLetter(codice.substring(12, 13).charAt(0)) ||
					Character.isLetter(codice.substring(13, 14).charAt(0)) ||
					Character.isLetter(codice.substring(14, 15).charAt(0)) ||
					Character.isDigit(codice.substring(15, 16).charAt(0))) {
				return false;
			}
		}
		return true;
	}

	public static String generaCodiceFiscale(String cognome, String nome,
			String dataNascita, String sesso,
			String codComune,
			String codStatoEstero) {

		String tmpCodiceFiscale = "";
		if (codComune != null && !codComune.equals("")) {
			tmpCodiceFiscale = (calcolaCognome(cognome)) +
					(calcolaNome(nome)) +
					(calcolaDataNasc(dataNascita, sesso)) +
					codComune;
		}
		else if (codStatoEstero != null && !codStatoEstero.equals("")) {
			tmpCodiceFiscale = (calcolaCognome(cognome)) +
					(calcolaNome(nome)) +
					(calcolaDataNasc(dataNascita, sesso)) +
					codStatoEstero;
		}
		// calcolo del CRT
		tmpCodiceFiscale = tmpCodiceFiscale + (calcolaControlCrt(tmpCodiceFiscale));
		return tmpCodiceFiscale;
	}

	public static boolean isCodiceControlloCorretto(String codFisc){
		char crtAttuale = codFisc.charAt(15);
		char crtAtteso = calcolaControlCrt(codFisc);
		if(crtAttuale == crtAtteso){
			return true;
		}
		else 
			return false;	  
	}

	/**
	 * Calcola il carattere di controllo:
	 * La stringa di ritorno sara' costituita da una lettera dell'alfabeto ottenuta dai controlli
	 * che devono essere effettuati sul codice fiscale che viene passato.
	 * Il calcolo e' realizzato secondo una mappa associativa propria dell'algoritmo di calcolo.
	 * @param codFisc Codice Fiscale calcolato con cognome,nome,data di nascita e codice comune
	 * @return stringa con il carattere di controllo del CF
	 */
	private static char calcolaControlCrt(String codFisc) {
		int counter = 0; // serve per accumulare il codice numerico
		int offset = 0;
		// I primi 8 caratteri dispari...
		for (int i = 0; i < 8; i++, offset += 2) {
			char tmpChar = codFisc.charAt(offset);
			if (tmpChar == 'A' || tmpChar == '0')
				counter += 1;
			else if (tmpChar == 'B' || tmpChar == '1')
				counter += 0;
			else if (tmpChar == 'C' || tmpChar == '2')
				counter += 5;
			else if (tmpChar == 'D' || tmpChar == '3')
				counter += 7;
			else if (tmpChar == 'E' || tmpChar == '4')
				counter += 9;
			else if (tmpChar == 'F' || tmpChar == '5')
				counter += 13;
			else if (tmpChar == 'G' || tmpChar == '6')
				counter += 15;
			else if (tmpChar == 'H' || tmpChar == '7')
				counter += 17;
			else if (tmpChar == 'I' || tmpChar == '8')
				counter += 19;
			else if (tmpChar == 'J' || tmpChar == '9')
				counter += 21;
			else if (tmpChar == 'K')
				counter += 2;
			else if (tmpChar == 'L')
				counter += 4;
			else if (tmpChar == 'M')
				counter += 18;
			else if (tmpChar == 'N')
				counter += 20;
			else if (tmpChar == 'O')
				counter += 11;
			else if (tmpChar == 'P')
				counter += 3;
			else if (tmpChar == 'Q')
				counter += 6;
			else if (tmpChar == 'R')
				counter += 8;
			else if (tmpChar == 'S')
				counter += 12;
			else if (tmpChar == 'T')
				counter += 14;
			else if (tmpChar == 'U')
				counter += 16;
			else if (tmpChar == 'V')
				counter += 10;
			else if (tmpChar == 'W')
				counter += 22;
			else if (tmpChar == 'X')
				counter += 25;
			else if (tmpChar == 'Y')
				counter += 24;
			else if (tmpChar == 'Z')
				counter += 23;
		}

		// I primi 7 caratteri pari
		offset = 1;
		for (int j = 0; j < 7; j++, offset += 2) {
			char tmpChar = codFisc.charAt(offset);
			if (Character.isDigit(tmpChar))
				counter += tmpChar - '0';

			else if (tmpChar == 'A')
				counter += 0;
			else if (tmpChar == 'B')
				counter += 1;
			else if (tmpChar == 'C')
				counter += 2;
			else if (tmpChar == 'D')
				counter += 3;
			else if (tmpChar == 'E')
				counter += 4;
			else if (tmpChar == 'F')
				counter += 5;
			else if (tmpChar == 'G')
				counter += 6;
			else if (tmpChar == 'H')
				counter += 7;
			else if (tmpChar == 'I')
				counter += 8;
			else if (tmpChar == 'J')
				counter += 9;
			else if (tmpChar == 'K')
				counter += 10;
			else if (tmpChar == 'L')
				counter += 11;
			else if (tmpChar == 'M')
				counter += 12;
			else if (tmpChar == 'N')
				counter += 13;
			else if (tmpChar == 'O')
				counter += 14;
			else if (tmpChar == 'P')
				counter += 15;
			else if (tmpChar == 'Q')
				counter += 16;
			else if (tmpChar == 'R')
				counter += 17;
			else if (tmpChar == 'S')
				counter += 18;
			else if (tmpChar == 'T')
				counter += 19;
			else if (tmpChar == 'U')
				counter += 20;
			else if (tmpChar == 'V')
				counter += 21;
			else if (tmpChar == 'W')
				counter += 22;
			else if (tmpChar == 'X')
				counter += 23;
			else if (tmpChar == 'Y')
				counter += 24;
			else if (tmpChar == 'Z')
				counter += 25;
		}
		// il codiceCrt e' un numero tra 0 e 25
		int codiceCrt = counter % 26;

		// il codice risultante e' il (codiceCrt+1)-esimo carattere dell'alfabeto
		return (char) ('A' + codiceCrt);
	}

	/**
	 * Calcola i caratteri derivanti dal nome:
	 * - tutte le vocali presenti nel nome vengono memorizzate in una variabile
	 * - tutte le consonanti presenti nel nome vengono memorizzate in una variabile
	 * La stringa di ritorno sara' costituita da:
	 * <ul>
	 * <li> TRE CONSONANTI (la prima,la terza e la quarta):
	 * nel caso in cui le consonanti sono piu' di 3
	 * <li> TRE CONSONANTI (la prima,la seconda,la terza)
	 * nel caso in cui le consonanti sono 3
	 * <li> DUE CONSONANTI E UNA VOCALE :
	 * nel caso in cui le consonanti sono 2 ed e' stata trovata almeno 1 vocale
	 * <li> DUE CONSONANTI E X :
	 * nel caso in cui le consonanti sono 2 e non sono state trovate vocali
	 * <li> UNA CONSUNANTE E DUE VOCALI :
	 * nel caso in cui c'e' 1 sola consonante e le vocali sono almeno 2
	 * <li> UNA CONSONANTE,UNA VOCALE E X:
	 * nel caso un cui c'e' 1 sola consonante e 1 sola vocale
	 * <li> DUE VOCALI E X
	 * nel caso in cui non ci sono consonanti
	 * </ul>
	 * @param nome Nome da cui devono derivare i secondi 3 caratteri del CF
	 * @return stringa con i secondi 3 caratteri del codice fiscale
	 */

	public static String calcolaNome(String nome) {
		String tmpText = new String();
		String tmpConsonanti = new String();
		String tmpVocali = new String();
		String nomeU = nome.toUpperCase();
		int lungh = nomeU.length();
		char currChar;
		for (int i = 0; i < lungh; i++) {
			currChar = nomeU.charAt(i);
			if (currChar == 'A' ||
					currChar == 'Á' ||
					currChar == 'À' ||
					currChar == 'E' ||
					currChar == 'È' ||
					currChar == 'É' ||
					currChar == 'I' ||
					currChar == 'Í' ||
					currChar == 'Ì' ||
					currChar == 'O' ||
					currChar == 'Ó' ||
					currChar == 'Ò' ||
					currChar == 'U' ||
					currChar == 'Ú' ||
					currChar == 'Ù'
					) {
				tmpVocali = tmpVocali + (currChar);

			}
			else if ( (currChar != ' ') && currChar != '\'') {
				tmpConsonanti = tmpConsonanti + (currChar);

			}
		}
		if (tmpConsonanti.length() > 3)
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(2) +
			tmpConsonanti.charAt(3);
		else if (tmpConsonanti.length() == 3)
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1) +
			tmpConsonanti.charAt(2);
		else if (tmpConsonanti.length() == 2) {
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1);
			// nel caso di nome con due sole consonanti non calcolo esattamente il codice fiscale
			if (tmpVocali.length() > 1 || tmpVocali.length() == 1)
				tmpText = tmpText + tmpVocali.charAt(0);
			else
				tmpText = tmpText + 'X';
		}
		else if (tmpConsonanti.length() == 1) {
			tmpText = tmpText + tmpConsonanti.charAt(0);
			if (tmpVocali.length() > 1)
				tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			else {
				tmpText = tmpText + tmpVocali.charAt(0);
				tmpText = tmpText + 'X';
			}
		}
		else if(tmpVocali.length() >= 3) {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1) + tmpVocali.charAt(2);
		}
		else {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			tmpText = tmpText + 'X';
		}
		return tmpText;
	}

	/**
	 * Calcola i caratteri derivanti dalla data di nascita e sesso:
	 * La stringa di ritorno sara' costituita da:
	 * <ul>
	 * <li> ANNO : dato dall'ultimo e dal penultimo carattere della data di nascita
	 * <li> MESE : si memorizza in una variabile il quarto e il quinto carattere della data di nascita,
	 * a seconda del numero trovato si avra' una lettera dell'alfabeto corrispondente
	 * <li> GIORNO:
	 * nel caso in cui il sesso e' 'M' e' dato dal primo e secondo carattere della data di nascita
	 * nel caso in cui il sesso e' 'F' e' dato dal numero prima ricavato + 40
	 * </ul>
	 * @param dataNascita Data da cui devono derivare i caratteri 7 - 12 del CF
	 * @return stringa con i caratteri 7 - 12 del codice fiscale
	 */

	private static final char[] mese =
			new char[] {
		'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

	public static String calcolaDataNasc(String dataNascita, String sesso) {
		String tmpMese = new String();
		String tmpGiorno = new String();
		String tmpTextGG = new String();
		String tmpText = new String();
		int length = dataNascita.length();

		// calcolo della porzione di codice che descrive la data di nascita a partire
		// da una data nel formato dd-mm-aaaa:
		// Anno: gli ultimi due caratteri
		tmpText = tmpText + dataNascita.charAt(length - 2) +
				dataNascita.charAt(length - 1);

		// Mese: il terzo e il quarto carattere
		tmpMese = tmpMese + dataNascita.charAt(3) + dataNascita.charAt(4);
		tmpText += mese[ ( (Integer.parseInt(tmpMese)) - 1)];

		// Giorno: i primi due caratteri (il numero deve essere aumentato di 40
		// se il sesso e' femminile
		tmpGiorno = tmpGiorno + dataNascita.charAt(0) + dataNascita.charAt(1);
		if (sesso.charAt(0) == 'F')
			tmpTextGG += ( (Integer.parseInt(tmpGiorno)) + 40);
		else
			tmpTextGG += tmpGiorno;

		// riempio se necessario con uno zero a sinistra
		if (tmpTextGG.length() == 1)
			tmpText += '0';

		tmpText += tmpTextGG;
		return tmpText;
	}

	/**
	 * Calcola i caratteri derivanti dal cognome:
	 * - tutte le vocali presenti nel cognome vengono memorizzate in una variabile
	 * - tutte le consonanti presenti nel cognome vengono memorizzate in una variabile
	 * La stringa di ritorno sara' costituita da:
	 * <ul>
	 * <li> TRE CONSONANTI (le prime tre):
	 * nel caso in cui le consonanti sono almeno 3
	 * <li> DUE CONSONANTI E UNA VOCALE :
	 * nel caso in cui le consonanti sono 2 ed e' stata trovata almeno 1 vocale
	 * <li> DUE CONSONANTI E X :
	 * nel caso in cui le consonanti sono 2 e non sono state trovate vocali
	 * <li> UNA CONSUNANTE E DUE VOCALI :
	 * nel caso in cui c'e' 1 sola consonante e le vocali sono almeno 2
	 * <li> UNA CONSONANTE,UNA VOCALE E X:
	 * nel caso un cui c'eì 1 sola consonante e 1 sola vocale
	 * <li> DUE VOCALI E X
	 * nel caso in cui non ci sono consonanti
	 * </ul>
	 * @param cognome Cognome da cui devono derivare i primi 3 caratteri del CF
	 * @return stringa con i primi 3 caratteri del codice fiscale
	 */

	public static String calcolaCognome(String cognome) {
		String tmpText = new String();
		String tmpConsonanti = new String();
		String tmpVocali = new String();

		String cognomeU = cognome.toUpperCase();
		char currChar;
		int lungh = cognomeU.length();
		for (int i = 0; i < lungh; i++) {
			currChar = cognomeU.charAt(i);
			if (currChar == 'A' ||
					currChar == 'Á' ||
					currChar == 'À' ||
					currChar == 'E' ||
					currChar == 'È' ||
					currChar == 'É' ||
					currChar == 'I' ||
					currChar == 'Í' ||
					currChar == 'Ì' ||
					currChar == 'O' ||
					currChar == 'Ó' ||
					currChar == 'Ò' ||
					currChar == 'U' ||
					currChar == 'Ú' ||
					currChar == 'Ù'
					) {
				tmpVocali = tmpVocali + (currChar);

			}
			else if ( (currChar != ' ') && currChar != '\'') {
				//  else if((currChar != ' ' ) )
				tmpConsonanti = tmpConsonanti + (currChar);

			}

		}
		if (tmpConsonanti.length() > 2)
			tmpText = tmpText + tmpConsonanti.charAt(0) +
			tmpConsonanti.charAt(1) +
			tmpConsonanti.charAt(2);
		else if (tmpConsonanti.length() == 2) {
			tmpText = tmpText + tmpConsonanti.charAt(0) +
					tmpConsonanti.charAt(1);

			//nel caso di cognome con sole due consonanti non calcolo esattamente il codice fiscale
			if (tmpVocali.length() > 1 || tmpVocali.length() == 1)
				tmpText = tmpText + tmpVocali.charAt(0);
			else
				tmpText = tmpText + 'X';
		}
		else if (tmpConsonanti.length() == 1) {
			tmpText = tmpText + tmpConsonanti.charAt(0);
			if (tmpVocali.length() > 1)
				tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			else {
				tmpText = tmpText + tmpVocali.charAt(0);
				tmpText = tmpText + 'X';
			}
		}

		else if(tmpVocali.length() >= 3) {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1) + tmpVocali.charAt(2);
		}

		else {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			tmpText = tmpText + 'X';
		}

		return tmpText;
	}

	/**
	 * Funzione che dato in input l'anno ritorna il giorno di Pasqua.
	 * @param N Anno
	 * @return
	 */
	public static Date calcolaPasqua(int N) {
		Date pasqua = null;
		String gg;
		String mm;
		String aaaa = N + "";
		int aaaaLunghezza = aaaa.length();
		for (int i = aaaaLunghezza; i < 4; i++)
			aaaa = "0" + aaaa;

		long mese;
		long giorno;
		int G = N % 19;
		long C = N / 100;
		long H = (C - C / 4 - (8 * C + 13) / 25 + 19 * G + 15) % 30;
		long I = H - (H / 28) * (1 - (H / 28) * (29 / (H + 1)) * ( (21 - G) / 11));
		long J = (N + N / 4 + I + 2 - C + C / 4) % 7;
		long L = I - J;

		long mL = 3 + (L + 40) / 44;
		long mI = Math.round(3 + (L + 40) / 44);
		if (mL < mI)
			mese = mI - 1;
		else
			mese = mI;
		mm = mese + "";
		int mmLunghezza = mm.length();
		for (int i = mmLunghezza; i < 2; i++)
			mm = "0" + mm;
		long gL = L + 28 - 31 * (mese / 4);
		long gI = Math.round(L + 28 - 31 * (mese / 4));
		if (gL < gI)
			giorno = gI - 1;
		else
			giorno = gI;
		gg = giorno + "";
		int ggLunghezza = gg.length();
		for (int i = ggLunghezza; i < 2; i++)
			gg = "0" + gg;

		if (giorno == 0 || mese == 0)
			return null;
		java.text.SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		java.text.ParsePosition pos = new ParsePosition(0);
		pasqua = formatter.parse(gg + "/" + mm + "/" + aaaa, pos);
		return pasqua;
	}

	/**
	 * Funzione che dato in input l'anno ritorna il giorno e mese di Pasqua.
	 * @param N Anno
	 * @return
	 */
	public static String calcolaPasqua2(int N) {
		String gg;
		String mm;
		String aaaa = N + "";
		int aaaaLunghezza = aaaa.length();
		for (int i = aaaaLunghezza; i < 4; i++)
			aaaa = "0" + aaaa;

		long mese;
		long giorno;
		int G = N % 19;
		long C = N / 100;
		long H = (C - C / 4 - (8 * C + 13) / 25 + 19 * G + 15) % 30;
		long I = H - (H / 28) * (1 - (H / 28) * (29 / (H + 1)) * ( (21 - G) / 11));
		long J = (N + N / 4 + I + 2 - C + C / 4) % 7;
		long L = I - J;

		long mL = 3 + (L + 40) / 44;
		long mI = Math.round(3 + (L + 40) / 44);
		if (mL < mI)
			mese = mI - 1;
		else
			mese = mI;
		mm = mese + "";
		int mmLunghezza = mm.length();
		for (int i = mmLunghezza; i < 2; i++)
			mm = "0" + mm;
		long gL = L + 28 - 31 * (mese / 4);
		long gI = Math.round(L + 28 - 31 * (mese / 4));
		if (gL < gI)
			giorno = gI - 1;
		else
			giorno = gI;
		gg = giorno + "";
		int ggLunghezza = gg.length();
		for (int i = ggLunghezza; i < 2; i++)
			gg = "0" + gg;

		if (giorno == 0 || mese == 0)
			return null;
		return gg + "-" + mm;
	}

	/**
	 * Funzione che dato in input l'anno ritorna la data del giorno di pasquetta
	 * @param N Anno
	 * @return
	 */
	public static Date calcolaPasquetta(int N) {
		Date pasqua = calcolaPasqua(N);
		if (pasqua != null)
			return dateAddDays(calcolaPasqua(N), 1);
		else
			return null;
	}

	/**
	 * Funzione che dato in input l'anno ritorna la data del giorno di pasquetta
	 * @param N Anno
	 * @return
	 */
	public static String calcolaPasquetta2(int N) {
		Date pasqua = calcolaPasqua(N);
		Calendar cal = new GregorianCalendar();
		cal.setTime(pasqua);
		cal.add(Calendar.DATE, 1);
		String dayToCompare = cal.get(Calendar.DAY_OF_MONTH) + "";
		if (dayToCompare.length() == 1)
			dayToCompare = "0" + dayToCompare;
		String monthToCompare = cal.get(Calendar.MONTH) + "";
		if (monthToCompare.length() == 1)
			monthToCompare = "0" + monthToCompare;
		String pasquetta = dayToCompare + "-" + monthToCompare;
		if (!pasquetta.trim().equalsIgnoreCase("-"))
			return pasquetta;
		else
			return "";
	}

	/**
	 * Funzione che converte i minuti di una giornata in formato HH:MM
	 * @param minuti: minuti dalla mezzanotte. Range di valori: da 0 a 1439
	 * @return Stringa in formato HH:MM (Compresi di 0). Restiutisce null se l'input e' null
	 */
	public static String convertiMinutiInOra(Integer minuti) {
		if ( (minuti == null) || (minuti.equals("")) || (minuti.intValue() < 0) ||
				(minuti.intValue() > 1439))
			return null;
		int minutes = minuti.intValue();
		int mm = minutes % 60;
		int hh = minutes / 60;
		String mmS = (mm < 10) ? "0" + String.valueOf(mm) : String.valueOf(mm);
		String hhS = (hh < 10) ? "0" + String.valueOf(hh) : String.valueOf(hh);
		return hhS + ":" + mmS;
	}

	/**
	 * Funzione che coverte una stringa dal formato HH:MM in minuti dalla mezzanotte
	 * @param ora Stringa in formato HH:MM
	 * @return minuti dalla mezzanotte. Restiutisce null se l'input e' null
	 */
	public static Integer convertiOraInMinuti(String ora) {
		if (ora == null)
			return null;
		try {
			StringTokenizer st = new StringTokenizer(ora, ":");

			String[] arr = new String[2];
			arr[0] = st.nextToken();
			arr[1] = st.nextToken();

			int hh = Integer.parseInt(arr[0]);
			int mm = Integer.parseInt(arr[1]);
			if ( (mm < 0) || (mm > 59) || (hh < 0) || (hh > 23)) {
				return null;
			}
			int minutes = hh * 60;
			minutes += mm;
			return new Integer(minutes);

		}
		// In caso di anomalia ritorno null
		catch (Exception ex) {
			return null;
		}

	}

	/**
	 * Restituisce true se l'ora e' minore o uguale a max e maggiore o uguale a min
	 * @param ora
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean oraCompresaTra(String ora, String min, String max) {
		Integer oraInt = Format.convertiOraInMinuti(ora);
		Integer minInt = new Integer(min);
		Integer maxInt = new Integer(max);
		if (minInt.intValue() <= oraInt.intValue() &&
				maxInt.intValue() >= oraInt.intValue())
			return true;
		else
			return false;
	}

	/*
    Calcolo mesi commerciali

   NOTA:  questo metodo implementa il calcolo dei mesi commerciali come
         previsto dalla legge...
         caso particolare--> il calcolo dei mesiCommerciali per gli
         stati occupazionali del silp per cui SI E' STABILITO col dominio
         di utilizzare un algoritmo leggermente diverso (vedi calcolaMesiCommercialiPerStatoOccupazionale)

    La durata dello stato di disoccupazione si calcola in mesi commerciali.
    I periodi inferiori a giorni quindici, all'interno di un unico mese, non si computano,
   mentre i periodi superiori a giorni quindici si computano come un mese intero.

    Esempi:
    Data inizio = 12/10/2003, Data inserimento = 16/05/2003
    Risultato = 8 mesi, in quanto si calcola sia ottobre che maggio in
    quanto il periodo e' superiore a 15 giorni
    Data inizio 18/10/2003, Data inserimento = 16/05/2003
    Risultato = 7 mesi, in quanto non si calcola  ottobre e si tiene
    conto di maggio
    Data inizio 18/10/2003, Data inserimento = 12/05/2003
    Risultato = 6 mesi, in quanto non si calcolano sia  ottobre che maggio


   public static int calcolaMesiCommerciali(String dInizioVal , String dFineVal)
   {
     java.util.Date dInizio = convertiStringaInData(dInizioVal);
     java.util.Date dFine = convertiStringaInData(dFineVal);
     int mesiCommerciali = 0;
     Calendar dInizioCal = new GregorianCalendar();
     dInizioCal.setTime(dInizio);
     Calendar dFineCal = new GregorianCalendar();
     dFineCal.setTime(dFine);

       int giornoInizio = dInizioCal.get(Calendar.DATE);
       int giornoFine = dFineCal.get(Calendar.DATE);
       mesiCommerciali = getElapsedMonths( dInizioVal, dFineVal);
       // Caso calcolare tutti e due i periodi estremi
       if( giornoInizio <= 15 && giornoFine > 15 )
       {
         mesiCommerciali = mesiCommerciali + 1; // 30
       }
       // Caso calcolare nessuno dei periodi estremi
       if( giornoInizio > 15 && giornoFine <= 15 )
       {
         mesiCommerciali = mesiCommerciali - 1; // 30
       }

     return mesiCommerciali;
   }
	 */

	/*
   QUESTA FUNZIONE va utilizzata per il calcolo dei mesi commerciali SEMPRE
   quando si e' nell'ambito SILP / STATI OCCUPAZIONALI (quindi anche provvedimenti..)


   Modificare la classe Format aggiungendo il metodo
   public static int calcolaMesiCommercialiPerStatoOccupazionale(Date dataDa, Date dataA){
   1) Se il mese della dataA corrisponde al mese corrente trasformare
      la dataA nella data corrispondente all'ultimo giorno del mese precedente
   2) confrontare le due date dopo la modifica
   3) se la dataA risulta minore della dataDa restituire 0
   4) altrimenti richiamare il metodo calcolaMesiCommerciali con la dateFine modificata e restituire il valore restituito da calcolaMesiCommerciali
   }

   questa e' una funzione assurda
   restituisce sempre 0 e non fa quello che deve
   non utilizzare mai piu' per nessun motivo !!!
   antonio - 13 01 2005
     public static int calcolaMesiCommercialiPerStatoOccupazionale(String dataAttuale , String dataDa, String dataA){
       int retValue = 0;

       Date dataInizio =    convertiStringaInData(dataDa);
       Date dataFine =      convertiStringaInData(dataA);
       Date dataCorrente =   convertiStringaInData(dataAttuale);

       // RECUPERO IL  MESE CORRENTE
       Calendar dataOdierna = new GregorianCalendar();
       dataOdierna.setTime(dataCorrente);
       int meseCorrente = dataOdierna.get(Calendar.MONTH);

       // RECUEPRO IL MESE DELLA DATA A
       Calendar dataFinale = new GregorianCalendar();
       dataFinale.setTime(dataFine);
       int meseFinale = dataFinale.get(Calendar.MONTH);
       int annoFinale = dataFinale.get(Calendar.YEAR);

       // MESE CORRENTE UGUALE AL MESE DELLA DATA FINALE
       if( meseCorrente == meseFinale )
       {
         // Prendo i giorni del mese e li sottraggo alla data
         int giorniAttualiDataFine = dataFinale.get(Calendar.DAY_OF_MONTH);
         dataFinale.add(Calendar.DATE , -giorniAttualiDataFine );

         String a = convertiDataInStringa(dataFinale.getTime());
         Date dataFineModificata = dataFinale.getTime();
         // Se la data fine risulta anteriore alla data Inizio
         if( dataFineModificata.before(dataInizio) )
           return 0;
         else
   calcolaMesiCommerciali( dataDa , convertiDataInStringa(dataFineModificata) );
       }
       return retValue;
     }

     // anno bisestile
     public static boolean bisestile(int data)
     {
       return (data%4==0)&&(!(data%100==0)||(data%400==0));
     }

     // calcola giorni del mese
     public static int calcolaGiorniDelMese(int m, int a)
     {
       //calcolo dei giorni del mese
       int giorni_del_mese = 0;
       if (m==2)
               if (bisestile(a))
                       giorni_del_mese = 29;
               else
                       giorni_del_mese = 28;
       else
               if (m==4 || m==6 || m==9 || m==11)//mesi con 30 giorni
                       giorni_del_mese = 30;
               else
                       giorni_del_mese = 31;
       return giorni_del_mese;
     }
	 */


	/**
	 * Restituisce (GregorianCalendar) l'ultimo giorno del mese precedente a quello in
	 * ingresso.
	 * @param mese
	 * @return
	 */
	private Calendar getUltimoGiornoMese(int mese, int anno) {
		Calendar ultimaData = new GregorianCalendar();
		// settare l'anno
		int ultimoGiorno = ultimaData.getActualMaximum(mese);
		return ultimaData;

	}


	//  NZA FRA DUE DATE IN MESI , concordate con Veneruo-Farinasso
	// da usare negli esoneri parziali

	public static int differenzaFraDueDate_inMesi( String dataInizio , String dataFine )
	{
		int mesiTotali = 0;
		// ********************** CONVERSIONI  *******************************
		Date dInizio = convertiStringaInData(dataInizio);
		Date dFine = convertiStringaInData(dataFine);

		Calendar dataIniziale = new GregorianCalendar();
		dataIniziale.setTime(dInizio);

		Calendar dataFinale = new GregorianCalendar();
		dataFinale.setTime(dFine);



		// ************** Variabili DataInizio ********************************
		int annoIniziale = dataIniziale.get(Calendar.YEAR);
		int meseIniziale = dataIniziale.get(Calendar.MONTH);
		int giornoIniziale = dataIniziale.get(Calendar.DATE);
		int giorniDelMeseIniziale = dataIniziale.getActualMaximum(Calendar.DAY_OF_MONTH);

		// ************** Variabili DataFine ********************************
		int annoFinale = dataFinale.get(Calendar.YEAR);
		int meseFinale = dataFinale.get(Calendar.MONTH);
		int giornoFinale = dataFinale.get(Calendar.DATE);
		int giorniDelMeseFinale = dataFinale.getActualMaximum(Calendar.DAY_OF_MONTH);


		return mesiTotali;
	}



	private static int elapsed(GregorianCalendar g1, GregorianCalendar g2,
			int type) {
		GregorianCalendar gc1, gc2;
		int elapsed = 0;
		// Create copies since we will be clearing/adding
		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		}
		else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}
		if (type == Calendar.MONTH || type == Calendar.YEAR) {
			gc1.clear(Calendar.DATE);
			gc2.clear(Calendar.DATE);
		}
		if (type == Calendar.YEAR) {
			gc1.clear(Calendar.MONTH);
			gc2.clear(Calendar.MONTH);
		}
		// normalizzo i giorni del mese
		gc1.set(Calendar.DAY_OF_MONTH, 1);
		gc2.set(Calendar.DAY_OF_MONTH, 1);

		while (gc1.before(gc2)) {
			gc1.add(type, 1);
			elapsed++;
		}

		return elapsed;
	}

	public static int getElapsedMonths(String d1Str, String d2Str) {
		java.util.Date d1 = convertiStringaInData(d1Str);
		java.util.Date d2 = convertiStringaInData(d2Str);

		GregorianCalendar data1 = new GregorianCalendar();
		data1.setTime(d1);
		GregorianCalendar data2 = new GregorianCalendar();
		data2.setTime(d2);

		return elapsed(data1, data2, Calendar.MONTH);
	}

	public static String calendarToString(Calendar c) {
		return c.get(Calendar.YEAR) + "/" +
				c.get(Calendar.MONTH) + "/" +
				c.get(Calendar.DATE);
	}

	//  public StringBuffer formattazioneHTML(Collection collezione) {
	//    StringBuffer ritorno = new StringBuffer(
	//        "<HTML><HEAD><TITLE></TITLE></HEAD><BODY><TABLE>");
	//    Iterator iterarore = collezione.iterator();
	//    while (iterarore.hasNext()) {
	//      EsitoInserimentoDTO dto = (EsitoInserimentoDTO) iterarore.next();
	//      ritorno = ritorno.append("<TR><TD>");
	//      ritorno = ritorno.append(dto.getIdClassificazione());
	//      ritorno = ritorno.append("</TD><TD>");
	//      ritorno = ritorno.append(dto.getIdComunicazione());
	//      ritorno = ritorno.append("</TD><TD>");
	//      ritorno = ritorno.append(dto.getMessaggi());
	//      ritorno = ritorno.append("</TD></TR>");
	//    }
	//    ritorno = ritorno.append("</TABLE></BODY></HTML>");
	//    return ritorno;
	//  }

	//  public StringBuffer formattazioneHTMLSelezionabile(Collection collezione) {
	//    StringBuffer ritorno = new StringBuffer(
	//        "<HTML><HEAD><TITLE></TITLE></HEAD><BODY><TABLE>");
	//    Iterator iterarore = collezione.iterator();
	//    while (iterarore.hasNext()) {
	//      EsitoInserimentoDTO dto = (EsitoInserimentoDTO) iterarore.next();
	//      ritorno = ritorno.append("<TR><TD>");
	//      ritorno = ritorno.append(dto.getIdClassificazione());
	//      ritorno = ritorno.append("</TD><TD>");
	//      ritorno = ritorno.append(dto.getIdComunicazione());
	//      ritorno = ritorno.append("</TD><TD>");
	//      ritorno = ritorno.append(dto.getMessaggi());
	//      ritorno = ritorno.append("</TD></TR>");
	//    }
	//    ritorno = ritorno.append("</TABLE></BODY></HTML>");
	//    return ritorno;
	//  }

	/**
	 * Calcola la differenza tra la data di fine e la data inizio
	 * restituendo valori negativi nel caso in cui la data fine
	 * sia minore della data inizio
	 * dataFine
	 * dataInizio
	 *
	 * restituisce (dataFine - dataInizio)
	 *
	 */
	static public int dateDiffDaysConSegno(Date dataFine, Date dataInizio) {
		int result;
		result = (int) ( (dataFine.getTime() / 86400000) -
				(dataInizio.getTime() / 86400000));
		return result;
	}

	/**
	 *  Restituisce un intero contenente l'eta' calcolata ad una
	 *   certa data di riferimento (per l'eta' al momento corrente
	 *   si passa la sysdate)
	 *  Non lancia eccezioni: se il calcolo fallisce per qualche motivo
	 * l'eta' restituita e' un insensato  -1
	 * @param dataNascita
	 * @param dataRiferimento
	 * @return
	 */
	public static int calcolaEtaAData(Date dataNascita, Date dataRiferimento) {
		int etaCalcolata = 0;
		try
		{
			String sNascita = convertiDataInStringa(dataNascita);
			String sRif = convertiDataInStringa(dataRiferimento);

			int annoN = Integer.parseInt(sNascita.substring(6,10));
			int annoR = Integer.parseInt(sRif.substring(6,10));
			int meseN = Integer.parseInt(sNascita.substring(3,5));
			int meseR = Integer.parseInt(sRif.substring(3,5));
			int giornoN = Integer.parseInt(sNascita.substring(0,2));
			int giornoR = Integer.parseInt(sRif.substring(0,2));
			if(annoR>=annoN){
				if(meseR>meseN)
				{
					etaCalcolata = annoR - annoN;
				}
				else if(meseR==meseN)
				{
					if(giornoR>=giornoN)
						etaCalcolata=annoR-annoN;
					else
						etaCalcolata=(annoR-annoN)-1;
				}
				else if(meseR<meseN)
				{
					etaCalcolata = (annoR - annoN) - 1;
				}
			}
		}catch(Exception e){
			etaCalcolata = -1;
		}
		return etaCalcolata;
	}

	public static int differenzaTra_DateEspresseaInAnni(Date dataPrecedente, Date dataSuccessiva) {
		int differenzaAnni = 0;
		try
		{
			String dataPrec = convertiDataInStringa(dataPrecedente);
			String dataSucc = convertiDataInStringa(dataSuccessiva);

			int annoN = Integer.parseInt(dataPrec.substring(6,10));
			int annoR = Integer.parseInt(dataSucc.substring(6,10));

			if(annoR>=annoN){

				differenzaAnni = annoR - annoN;
			}

		}catch(Exception e){
			differenzaAnni = -1;
		}
		return differenzaAnni;
	}

	/**
	 * Se in input riceve un flg S decodifica SI in tutti gli altri casi NO.
	 * Utile in qualche visualizzazione che debba essere comprensibile!
	 * @param flg
	 * @return
	 */
	//  public static String decodificaFlgInSIeNO(String flg){
	//    String value = "";
	//    if (flg != null && flg.equalsIgnoreCase(Costanti.SI))
	//    {
	//      value = "SI";
	//    }
	//    else
	//    {
	//      value = "NO";
	//    }
	//    return value;
	//  }

	public static void main(String[] args)
	{

		/*
    Date inizio = convertiStringaInData("25/05/2005");
    //Date fine = convertiStringaInData("28/06/2005");
    Date fine = convertiStringaInData("01/06/2005");
    int diff = dateDiffDaysPerContratto(inizio,fine);
    Date limiteContratto = dateAddMonth(inizio,4);
    System.out.println(">>>>"+convertiDataInStringa(limiteContratto));
    if(limiteContratto.before(fine)){
      System.out.println(">>>>prima");
    }
    else if(limiteContratto.compareTo(fine) == 0){
      System.out.println(">>>>uguale");
    }
    else if(limiteContratto.after(fine)){
      System.out.println(">>>>dopo");
    } */

		String dataInizio = "01/02/2002";
		String dataFine = "01/02/2004";

		int result = Format.differenzaFraDueDate_inMesi(dataInizio,dataFine);
		System.out.println(" <<<<<<<<<<<<<  DIFFERENZA FRA DUE DATE IN MESI >>>>>>>>>>>");
		System.out.println(" *************  numero MESI : " + result + " ************** ");



		Format.controlloCF("IAINTN56E11D761D");
		Format.isCodiceFiscaleCongruenteCognomeNome("IAINTN56E11D761D", "IAIA", "ANTONIO");


		Format.controlloCF("CAOAIE77E49Z210Z");
		Format.isCodiceFiscaleCongruenteCognomeNome("CAOAIE77E49Z210Z", "CAO", "AIE");





	}

	public static byte[] convertiStringaInArrayDiBytes(String szValue) {
		// (dalla classe serializer.URLDecodeBytes )
		int iReadChars = 0;
		int iChar;
		int iByteCount = 0;
		byte[] abResult = new byte[szValue.length()];

		while (iReadChars < szValue.length()) {
			iChar = (int) szValue.charAt(iReadChars);
			// Add iChar to accumulation buffer
			abResult[iByteCount ++] = (byte) iChar;
			iReadChars ++; // increments read characters count
		}

		// trim the resulting byte[]
		byte[] abResultTrimmed = new byte[iByteCount];
		System.arraycopy(abResult, 0, abResultTrimmed, 0, iByteCount);
		return (abResultTrimmed);
	}

	public static boolean controlloCF(String codiceFiscale) {
		codiceFiscale = codiceFiscale.toUpperCase();

		// prima verifica che il carattere di controllo corrisponda ai primi 15 caratteri
		char crt = calcolaControlCrt(codiceFiscale);

		if (codiceFiscale.charAt(15) != crt) {
			return false;
		}

		codiceFiscale = getCodiceFiscaleRipulitoDaOmocodia(codiceFiscale);

		return verificaCodiceFiscale(codiceFiscale);

	}

	public static String getCodiceFiscaleRipulitoDaOmocodia(String codiceFiscale) {
		// controllo per CF con omocodia, ovvero con almeno uno fra i 7 caratteri normalmente numerici,
		// impostato a non numerico
		boolean cfConOmocodia = false;
		try{
			int[] posizioniNumeriche = {6, 7, 9, 10, 12, 13, 14};
			for (int i = 0; i < posizioniNumeriche.length; i++) {
				char car = codiceFiscale.charAt(posizioniNumeriche[i]);
				Integer num = new Integer(car);
				if (num.intValue() < 48 || num.intValue() > 57) {
					cfConOmocodia = true;
				}
			}
		}
		catch (NumberFormatException ex) {
			cfConOmocodia = true;
		}

		if(cfConOmocodia){
			System.out.println("OMOCODICO");

			// se il carattere di controllo corrisponde, allora ripulisco
			codiceFiscale = controllaEPulisciCFConOmocodia(codiceFiscale);

		}
		return codiceFiscale;
	}

	public static String generaCodiceFiscale(String cognome, String nome) {
		String tmpCodiceFiscale = "";
		tmpCodiceFiscale = (calcolaCognome(cognome)) + (calcolaNome(nome));
		return tmpCodiceFiscale;
	}

	public static String controllaEPulisciCFConOmocodia(String codiceFiscale) {
		// stringa per controllo e calcolo omocodia
		String omoCodici = "LMNPQRSTUV";
		codiceFiscale = codiceFiscale.toUpperCase();
		char[] cCodice = codiceFiscale.toCharArray();

		// check della correttezza formale del codice fiscale
		// elimino dalla stringa gli eventuali caratteri utilizzati negli
		// spazi riservati ai 7 che sono diventati carattere in caso di omocodia
		for (int k = 6; k < 15; k++) {
			if ( (k == 8) || (k == 11))
				continue;
			int x = (omoCodici.indexOf(cCodice[k]));
			if (x != -1)
				cCodice[k] = Integer.toString(x).charAt(0);
		}
		codiceFiscale = new String(cCodice);
		return codiceFiscale;
	}

	public static boolean isCodiceFiscaleCongruenteCognomeNome(String CodiceFiscale, String cognome, String nome) {		
		if(CodiceFiscale.substring(0,6).equalsIgnoreCase(generaCodiceFiscale(cognome, nome))){
			return true;
		}
		else
			return false;
	}

	public static int dateDiffInMesi(java.util.Date d1, java.util.Date d2) {
		int result;
		if (d2.after(d1))
			result = (int) ( (d2.getTime() / 86400000) - (d1.getTime() / 86400000));
		else
			result = (int) ( (d1.getTime() / 86400000) - (d2.getTime() / 86400000));
		result = result / 30;
		return result;
	}
	
	public static String toString(Object in ) {
		return in!=null?in.toString():null;
	}
}
