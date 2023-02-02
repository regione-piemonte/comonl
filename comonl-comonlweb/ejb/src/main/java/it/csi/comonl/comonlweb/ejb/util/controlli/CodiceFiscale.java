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
package it.csi.comonl.comonlweb.ejb.util.controlli;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

// Classe di utilita' per i controlli sui codici fiscali
public class CodiceFiscale {
	private static final LogUtil LOG = new LogUtil(CodiceFiscale.class);
	
	// Unica istanza della classe
	private static CodiceFiscale singleton = null;

	final static String consonanti = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
	final static String numeri = "0123456789";
	// var strMese = new
	// Array('','A','B','C','D','E','H','L','M','P','R','S','T'); uguale a
	// tabCF2 xro' c'e' ''

	// Tabella per la verifica del posizionamento di lettere e numeri
	int[] tabCF1 = { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0 };

	// Tabelle per la verifica del mese
	char[] tabCF2 = { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
	int[] tabCF3 = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static final char[] mese = new char[] { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };

	// Per ottenere l'unica istanza della classe
	public static CodiceFiscale getInstance() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

	/**
	 * createSingleton PUCCI: Aggiunta per migliorare la gestione del
	 * synchronized il synchronized e' necessario solo quando si crea l'istanza
	 * Singleton ATTENZIONE: e' necessario controllare prima il NULL per evitare
	 * di creare + volte l'istanza
	 */
	private static synchronized void createSingleton() {
		if (singleton == null)
			singleton = new CodiceFiscale();
	}

	// Costruttore di default
	public CodiceFiscale() {
	}

	public boolean controllaCodiceFiscaleSoggetto(String codice, DecodificaDad decodificaDad) {
		if (codice == null || codice.trim().equals("") || codice.trim().length() < 11) {
			return false;
		}

		String trimCodice = codice.trim();

		switch (trimCodice.length()) {
		case 11:
			return controllaPartitaIVA(trimCodice);

		case 16:
			return controllaCF(trimCodice, decodificaDad);

		default:
			return isCodiceFiscaleInternazionaleValido(trimCodice);
		}

	}

	// Controlla la correttezza di una partita iva
	public boolean controllaPartitaIVA(String partitaIVA) {

		// if (isCodiceFiscaleInternazionaleValido(partitaIVA)) {//71862 -
		// COMONL-1194
		// return true;
		// } else {
		// Verifica la lunghezza della stringa
		try {
			if (partitaIVA.length() != 11) {
				return false;
			}
			if (StringUtils.containsOnly(partitaIVA, "0")) {
				return false;
			}

			// Verifica che la stringa sia composta da sole cifre
			char c;
			for (int i = 0; i < 11; i++) {
				c = partitaIVA.charAt(i);
				if ((byte) c < 48 || (byte) c > 57) {
					return false;
				}
			}

			// Algoritmo di calcolo dell'ultima cifra
			int Vd, Vp;
			int ctrl = 0;
			String Wk = "";
			for (int i = 0; i < 9; i += 2) {
				Vd = (byte) partitaIVA.charAt(i) - 48;
				Vp = 2 * ((byte) partitaIVA.charAt(i + 1) - 48);
				Wk = (Vp < 10) ? "0" + String.valueOf(Vp) : String.valueOf(Vp);
				ctrl += Vd + (byte) Wk.charAt(0) - 48 + (byte) Wk.charAt(1) - 48;
			}

			if (ctrl < 10) {
				Wk = "0" + String.valueOf(ctrl);
			} else if (ctrl > 99) {
				Wk = String.valueOf(ctrl).substring(0, 1);
			} else {
				Wk = String.valueOf(ctrl);
			}
			// Verifica la correttezza dell'ultima cifra
			if ((byte) partitaIVA.charAt(10) - 48 != (10 - ((byte) Wk.charAt(1) - 48)) % 10) {
				return false;
			}
		} catch (Exception ex) {
			LOG.error("CodiceFiscale::controllaPartitaIVA", ex.getMessage());
			return false;

		}
		// }

		// Tutto ok
		return true;
	}

	// Controlla la correttezza di un codice fiscale

	public boolean isCodiceFiscaleInternazionaleValido(String codiceFiscale) {
		if (ComonlUtility.isVoid(codiceFiscale)) {
			return false;
		} else {
			String tmpCodiceFiscale = codiceFiscale.trim().toUpperCase();
			if (tmpCodiceFiscale.length() < 3) {
				return false;
			} else {
				String siglaStatoEstero = tmpCodiceFiscale.substring(0, 2);
				String a = siglaStatoEstero.substring(0, 1);
				String b = siglaStatoEstero.substring(1, 2);
				if ("QWERTYUIOPASDFGHJKLZXCVBNM".indexOf(a) > 0 && "QWERTYUIOPASDFGHJKLZXCVBNM".indexOf(b) > 0) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public boolean controllaCF(String codiceFiscale, DecodificaDad decodificaDad) {

		// Verifica la lunghezza della stringa
		try {
			if (codiceFiscale.length() != 16) {
				return false;
			}

			// prima verifica che il carattere di controllo corrisponda ai primi
			// 15 caratteri
			char crt = calcolaControlCrt(codiceFiscale.toUpperCase());
			if (codiceFiscale.toUpperCase().charAt(15) != crt) {
				return false;
			}

			// controllo per CF con omocodia, ovvero con almeno uno fra i 7
			// caratteri normalmente numerici,
			// impostato a non numerico
			boolean cfConOmocodia = false;
			try {
				int[] posizioniNumeriche = { 6, 7, 9, 10, 12, 13, 14 };
				for (int i = 0; i < posizioniNumeriche.length; i++) {
					char car = codiceFiscale.charAt(posizioniNumeriche[i]);
					Integer num = Integer.parseInt(String.valueOf(car));
					if (num.intValue() < 48 || num.intValue() > 57) {
						cfConOmocodia = true;
					}
				}
			} catch (NumberFormatException ex) {
				cfConOmocodia = true;
			}

			if (cfConOmocodia) {

				// se il carattere di controllo corrisponde, allora ripulisco
				codiceFiscale = controllaEPulisciCFConOmocodia(codiceFiscale);
			}

			// Per ogni carattere verifica la disposizione di lettere e numeri
			int tmp;
			char c;
			for (int i = 0; i < 16; i++) {
				c = codiceFiscale.toUpperCase().charAt(i);
				if ((byte) c > 64 && (byte) c < 91) {
					tmp = 0;
				} else if ((byte) c > 47 && (byte) c < 58) {
					tmp = 1;
				} else {
					tmp = 2;
				}
				if (tabCF1[i] != tmp) {
					return false;
				}
			}

			// Verifica il mese
			int mese = 0;
			boolean found = false;
			for (mese = 0; mese < 12; mese++) {
				if (tabCF2[mese] == codiceFiscale.toUpperCase().charAt(8)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}

			// Estrae giorno e anno e verifica la data
			int giorno = Integer.parseInt(codiceFiscale.substring(9, 11));
			if (giorno > 40) {
				giorno = giorno - 40;
			}
			int anno = Integer.parseInt(codiceFiscale.substring(6, 8));
			if (anno % 4 == 0) {
				tabCF3[1] = 29;
			}
			if (giorno > tabCF3[mese]) {
				return false;
			}

			// controlla codice comune JIRA-1547 71441
			// inizio
			if(!checkCodiceComune(codiceFiscale.substring(11, 15), decodificaDad)){
				return false;
			}

		} catch (NumberFormatException ex) {
			LOG.error("CodiceFiscale::controllaCF", ex.getMessage());
		}

		return true;
	}


	// C_G_C metodi di controllo di congruenza tra CF e cognome e nome
	// Controlla la correttezza di un codice fiscale in base ai dati anagrafici
	// passati
	public boolean controlloCF(String cognome, String nome, String codiceFiscale, DecodificaDad decodificaDad) {
		cognome = cognome.toUpperCase();
		nome = nome.toUpperCase();
		codiceFiscale = codiceFiscale.toUpperCase();

		try {

			// prima verifica che il carattere di controllo corrisponda ai primi
			// 15 caratteri
			char crt = calcolaControlCrt(codiceFiscale);

			if (codiceFiscale.charAt(15) != crt) {
				return false;
			}

			// controllo per CF con omocodia, ovvero con almeno uno fra i 7
			// caratteri normalmente numerici,
			// impostato a non numerico
			boolean cfConOmocodia = false;
			try {
				int[] posizioniNumeriche = { 6, 7, 9, 10, 12, 13, 14 };
				for (int i = 0; i < posizioniNumeriche.length; i++) {
					char car = codiceFiscale.charAt(posizioniNumeriche[i]);
					Integer num = Integer.parseInt(String.valueOf(car));
					if (num.intValue() < 48 || num.intValue() > 57) {
						cfConOmocodia = true;
					}
				}
			} catch (NumberFormatException ex) {
				cfConOmocodia = true;
			}

			if (cfConOmocodia) {
				// // prima verifica che il carattere di controllo corrisponda
				// ai primi 15 caratteri
				// char crt = calcolaControlCrt(codiceFiscale);
				// if (codiceFiscale.charAt(15) != crt) {
				// return false;
				// }
				// se il carattere di controllo corrisponde, allora ripulisco
				codiceFiscale = controllaEPulisciCFConOmocodia(codiceFiscale);
				// cfConOmocodia = true;
			}
			// String cfCalcolato = calcolaCodiceFiscale(cognome, nome,
			// dataNascita, sesso,
			// luogoNascita);
			String cfCalcolato = generaCodiceFiscale(cognome, nome);

			// JIRA-1547 71441
			if(!checkCodiceComune(codiceFiscale.substring(11, 15), decodificaDad)){
				return false;
			}
			
			if (cfConOmocodia) {
				// controllo solo dei primi 6 caratteri
				if (cfCalcolato.substring(0, 6).toUpperCase().equalsIgnoreCase(codiceFiscale.substring(0, 6).toUpperCase())) {
					return true;
				}
			} else {
				if (cfCalcolato.substring(0, 6).toUpperCase().equalsIgnoreCase(codiceFiscale.substring(0, 6).toUpperCase())) {
					return true;
				}

			}
			
		} catch (Exception ex) {
			LOG.error("CodiceFiscale::controlloCF", ex.getMessage());
		}
		return false;
	}

	// C_G_C metodi di controllo di congruenza tra CF e dati anagraficia
	// Controlla la correttezza di un codice fiscale in base ai dati anagrafici
	// passati
	public boolean checkCF(String cognome, String nome, String dataNascita, String sesso, String luogoNascita, String codiceFiscale) {
		cognome = cognome.toUpperCase();
		nome = nome.toUpperCase();
		codiceFiscale = codiceFiscale.toUpperCase();
		LOG.debug("CodiceFiscale::checkCF", "cognome = " + cognome);
		LOG.debug("CodiceFiscale::checkCF", "nome = " + nome);
		LOG.debug("CodiceFiscale::checkCF", "codiceFiscale = " + codiceFiscale);
		LOG.debug("CodiceFiscale::checkCF", "dataNascita = " + dataNascita);
		LOG.debug("CodiceFiscale::checkCF", "luogoNascita = " + luogoNascita);
		LOG.debug("CodiceFiscale::checkCF", "sesso = " + sesso);

		try {

			// prima verifica che il carattere di controllo corrisponda ai primi
			// 15 caratteri
			char crt = calcolaControlCrt(codiceFiscale);
			if (codiceFiscale.charAt(15) != crt) {
				return false;
			}

			// controllo per CF con omocodia, ovvero con almeno uno fra i 7
			// caratteri normalmente numerici,
			// impostato a non numerico
			boolean cfConOmocodia = false;
			try {
				int[] posizioniNumeriche = { 6, 7, 9, 10, 12, 13, 14 };
				for (int i = 0; i < posizioniNumeriche.length; i++) {
					char car = codiceFiscale.charAt(posizioniNumeriche[i]);
					Integer num = Integer.parseInt(String.valueOf(car));
					if (num.intValue() < 48 || num.intValue() > 57) {
						cfConOmocodia = true;
					}
				}
			} catch (NumberFormatException ex) {
				cfConOmocodia = true;
			}

			if (cfConOmocodia) {
				// // prima verifica che il carattere di controllo corrisponda
				// ai primi 15 caratteri
				// char crt = calcolaControlCrt(codiceFiscale);
				// if (codiceFiscale.charAt(15) != crt) {
				// return false;
				// }
				// se il carattere di controllo corrisponde, allora ripulisco
				codiceFiscale = controllaEPulisciCFConOmocodia(codiceFiscale);
				// cfConOmocodia = true;
			}

			// String cfCalcolato = calcolaCodiceFiscale(cognome, nome,
			// dataNascita, sesso, luogoNascita);
			String cfCalcolato = generaCodiceFiscale(cognome, nome, dataNascita, sesso, luogoNascita);


			if (cfConOmocodia) {
				// controllo solo dei primi 15 caratteri, dato che il carattere
				// di controllo e' diverso,
				// trattandosi di cf con omocodia
				if (cfCalcolato.substring(0, 14).toUpperCase().equalsIgnoreCase(codiceFiscale.substring(0, 14).toUpperCase())) {
					return true;
				}
			} else {
				if (cfCalcolato.substring(0, 15).toUpperCase().equalsIgnoreCase(codiceFiscale.substring(0, 15).toUpperCase())) {
					return true;
				}

			}
		} catch (Exception ex) {
			LOG.error("CodiceFiscale::checkCF", ex.getMessage());
		}
		return false;
	}

	// Controllo codice fiscale Tutore
	public boolean checkCFPersonaSenzaComune(String cognome, String nome, String dataNascita, String sesso, String codiceFiscale) {
		try {
			cognome = cognome.toUpperCase();
			nome = nome.toUpperCase();
			codiceFiscale = codiceFiscale.toUpperCase();

			// prima verifica che il carattere di controllo corrisponda ai primi
			// 15 caratteri
			char crt = calcolaControlCrt(codiceFiscale);
			if (codiceFiscale.charAt(15) != crt) {
				return false;
			}

			// controllo per CF con omocodia, ovvero con almeno il penultimo
			// carattere non numerico
			char carControllo = codiceFiscale.charAt(14);
			if ((byte) carControllo > 64 && (byte) carControllo < 91) {
				codiceFiscale = controllaEPulisciCFConOmocodia(codiceFiscale);
			}

			String cfCalcolato = generaCodiceFiscale(cognome, nome, dataNascita, sesso, null);

			if (cfCalcolato.substring(0, 10).toUpperCase().equalsIgnoreCase(codiceFiscale.substring(0, 10).toUpperCase())) {
				return true;
			}
		} catch (Exception ex) {
			LOG.error("CodiceFiscale::checkCFPersonaSenzaComune", ex.getMessage());
		}
		return false;
	}

	// contini: versione bacata !!!
	public String calcolaCodiceFiscale(String cognome, String nome, String dataNascita, String sesso, String luogoNascita) {
		String cf = calcolaCognome(cognome);
		cf += calcolaNome(nome);
		// prelevo l'indice del primo '/'
		int indiceSlash1 = dataNascita.indexOf("/");
		String ggNascita = dataNascita.substring(0, indiceSlash1);
		// prelevo l'indice dell'ultimo '/'
		int indiceSlash2 = dataNascita.lastIndexOf("/");
		String mmNascita = dataNascita.substring(indiceSlash1 + 1, indiceSlash2);
		int mmNascitaInt = Integer.parseInt(mmNascita);
		String aaNascita = dataNascita.substring(indiceSlash2 + 1);

		/*
		 * var arrDataNascita = dataNascita.split(dateSep); var myInt;
		 * myInt=eval(arrDataNascita[1]); cf +=
		 * CalcolaNascita(arrDataNascita[0],
		 * strMese[myInt],arrDataNascita[2],sesso);
		 */

		cf += calcolaNascita(ggNascita, tabCF2[mmNascitaInt - 1], aaNascita, sesso);
		cf += luogoNascita;
		cf += calcolaK(cf);
		return cf;
	}

	public String calcolaNascita(String giorno, char mese, String anno, String sesso) {
		String code = "";
		code += anno.substring(2, 4) + mese;
		if (sesso.equals("M"))
			code += giorno;
		else {
			int giornoNas = Integer.parseInt(giorno) + 40;
			code += giornoNas;
		}
		return code;
	}

	public char calcolaK(String cf) {
		// var somma = 0, k;
		int somma = 0;
		char k;
		// var arrPari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String arrPari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String[][] arrDispari = new String[36][2];
		arrDispari[0][0] = "0";
		arrDispari[0][1] = "1";
		arrDispari[1][0] = "1";
		arrDispari[1][1] = "0";
		arrDispari[2][0] = "2";
		arrDispari[2][1] = "5";
		arrDispari[3][0] = "3";
		arrDispari[3][1] = "7";
		arrDispari[4][0] = "4";
		arrDispari[4][1] = "9";
		arrDispari[5][0] = "5";
		arrDispari[5][1] = "13";
		arrDispari[6][0] = "6";
		arrDispari[6][1] = "15";
		arrDispari[7][0] = "7";
		arrDispari[7][1] = "17";
		arrDispari[8][0] = "8";
		arrDispari[8][1] = "19";
		arrDispari[9][0] = "9";
		arrDispari[9][1] = "21";
		arrDispari[10][0] = "A";
		arrDispari[10][1] = "1";
		arrDispari[11][0] = "B";
		arrDispari[11][1] = "0";
		arrDispari[12][0] = "C";
		arrDispari[12][1] = "5";
		arrDispari[13][0] = "D";
		arrDispari[13][1] = "7";
		arrDispari[14][0] = "E";
		arrDispari[14][1] = "9";
		arrDispari[15][0] = "F";
		arrDispari[15][1] = "13";
		arrDispari[16][0] = "G";
		arrDispari[16][1] = "15";
		arrDispari[17][0] = "H";
		arrDispari[17][1] = "17";
		arrDispari[18][0] = "I";
		arrDispari[18][1] = "19";
		arrDispari[19][0] = "J";
		arrDispari[19][1] = "21";
		arrDispari[20][0] = "K";
		arrDispari[20][1] = "2";
		arrDispari[21][0] = "L";
		arrDispari[21][1] = "4";
		arrDispari[22][0] = "M";
		arrDispari[22][1] = "18";
		arrDispari[23][0] = "N";
		arrDispari[23][1] = "20";
		arrDispari[24][0] = "O";
		arrDispari[24][1] = "11";
		arrDispari[25][0] = "P";
		arrDispari[25][1] = "3";
		arrDispari[26][0] = "Q";
		arrDispari[26][1] = "6";
		arrDispari[27][0] = "R";
		arrDispari[27][1] = "8";
		arrDispari[28][0] = "S";
		arrDispari[28][1] = "12";
		arrDispari[29][0] = "T";
		arrDispari[29][1] = "14";
		arrDispari[30][0] = "U";
		arrDispari[30][1] = "16";
		arrDispari[31][0] = "V";
		arrDispari[31][1] = "10";
		arrDispari[32][0] = "W";
		arrDispari[32][1] = "22";
		arrDispari[33][0] = "X";
		arrDispari[33][1] = "25";
		arrDispari[34][0] = "Y";
		arrDispari[34][1] = "24";
		arrDispari[35][0] = "Z";
		arrDispari[35][1] = "23";

		/*
		 * var arrDispari = new Array( Array(0,1), Array(1,0), Array(2,5),
		 * Array(3,7), Array(4,9), Array(5,13), Array(6,15), Array(7,17),
		 * Array(8,19), Array(9,21), Array("A",1), Array("B",0), Array("C",5),
		 * Array("D",7), Array("E",9), Array("F",13), Array("G",15),
		 * Array("H",17), Array("I",19), Array("J",21), Array("K",2),
		 * Array("L",4), Array("M",18), Array("N",20), Array("O",11),
		 * Array("P",3), Array("Q",6), Array("R",8), Array("S",12),
		 * Array("T",14), Array("U",16), Array("V",10), Array("W",22),
		 * Array("X",25), Array("Y",24), Array("Z",23) );
		 */

		for (int i = 0; i < cf.length(); i += 2) {
			for (int j = 0; j < arrDispari.length; j++) {
				if (i == 0) {
				}
				if (cf.substring(i, i + 1).toUpperCase().equalsIgnoreCase(arrDispari[j][0])) {
					int num =  Integer.parseInt(arrDispari[j][1]);
					somma += num;
					break;
				}
			}
		}

		for (int i = 1; i < cf.length(); i += 2) {
			try {
				Integer numero = Integer.parseInt(cf.substring(i, i + 1));
				somma += numero.intValue();
			} catch (NumberFormatException e) {
				int indice = arrPari.indexOf(cf.substring(i, i + 1));
				somma += indice;
			}
		}
		int k1 = somma % 26;
		k = arrPari.charAt(k1);
		return k;
	}


	public String getConsonanti(String cognome) {
		String cns = "";
		for (int i = 0; i < cognome.length(); i++)
			if (consonanti.indexOf(cognome.substring(i, i + 1)) != -1)
				cns += cognome.substring(i, i + 1);
		return cns.toUpperCase();
	}

	public String getVocali(String cognome) {
		String voc = "";
		for (int i = 0; i < cognome.length(); i++)
			if (consonanti.indexOf(cognome.substring(i, i + 1)) == -1 && cognome.substring(i, i + 1) != " ")
				voc += cognome.substring(i, i + 1);
		return voc.toUpperCase();
	}

	// public String calcolaNome(String nome){
	// String code = "";
	// String cons = getConsonanti(nome);
	// if (cons.length() > 3)
	// code = cons.substring(0, 1) + cons.substring(2, 3) + cons.substring(3,
	// 4);
	// else if (cons.length() == 3)
	// code = cons;
	// else
	// {
	// code = cons + getVocali(nome).substring(0, 3 - cons.length());
	// if (code.length() < 3)
	// for (int i=code.length(); i<3; i++)
	// code += "X";
	// }
	// return code;
	// }

	// C_G_C metodi di controllo di congruenza tra CF e dati anagraficia

	/**
	 * Estrazione Anno di nascita: anno = sottostringa(codice_fiscale , dal
	 * carattere 7 al carattere 8)
	 *
	 * Estrazione Mese di nascita: mese = sottostringa(codice_fiscale ,
	 * carattere 9) E' un carattere, che assume i seguenti significati A ->
	 * gennaio B -> febbraio C -> marzo D -> aprile E -> maggio H -> giugno L ->
	 * luglio M -> agosto P -> settembre R -> ottobre S -> novembre T ->
	 * dicembre
	 *
	 * Estrazione Giorno di nascita: giorno = sottostrigna (codice_fiscale , dal
	 * carattere 10 al carattere 11) se (giorno > 40) allora giorno = giorno -
	 * 40
	 *
	 */
	// inizio modifica alfarano
	public String generaCodiceFiscale(String cognome, String nome) {
		String tmpCodiceFiscale = "";
		/*
		 * if (codComune != null && !codComune.equals("")) { tmpCodiceFiscale =
		 * (calcolaCognome(cognome)) + (calcolaNome(nome)) +
		 * (calcolaDataNasc(dataNascita, sesso)) + codComune; } else {
		 */
		// caso di controllo CF tutore, per il quale non ho il codice comune di
		// nascita
		// e non devo calcolare il carattere di controllo
		tmpCodiceFiscale = (calcolaCognome(cognome)) + (calcolaNome(nome));

		return tmpCodiceFiscale;
	}

	// ************************************************************************************************
	// inizio modifica contini
	public String generaCodiceFiscale(String cognome, String nome, String dataNascita, String sesso, String codComune
	// , String codStatoEstero
	) {
		String tmpCodiceFiscale = "";
		if (codComune != null && !codComune.equals("")) {
			tmpCodiceFiscale = (calcolaCognome(cognome)) + (calcolaNome(nome)) + (calcolaDataNasc(dataNascita, sesso)) + codComune;
		} else {
			// caso di controllo CF tutore, per il quale non ho il codice comune
			// di nascita
			// e non devo calcolare il carattere di controllo
			tmpCodiceFiscale = (calcolaCognome(cognome)) + (calcolaNome(nome)) + (calcolaDataNasc(dataNascita, sesso));
			return tmpCodiceFiscale;
		}
		// else if (codStatoEstero != null && !codStatoEstero.equals("")) {
		// tmpCodiceFiscale = (calcolaCognome(cognome)) +
		// (calcolaNome(nome)) +
		// (calcolaDataNasc(dataNascita, sesso)) +
		// codStatoEstero;
		// }
		// calcolo del CRT
		tmpCodiceFiscale = tmpCodiceFiscale + (calcolaControlCrt(tmpCodiceFiscale));
		return tmpCodiceFiscale;
	}

	/**
	 * Calcola il carattere di controllo: La stringa di ritorno sara' costituita
	 * da una lettera dell'alfabeto ottenuta dai controlli che devono essere
	 * effettuati sul codice fiscale che viene passato. Il calcolo e' realizzato
	 * secondo una mappa associativa propria dell'algoritmo di calcolo.
	 * 
	 * @param codFisc
	 *            Codice Fiscale calcolato con cognome,nome,data di nascita e
	 *            codice comune
	 * @return stringa con il carattere di controllo del CF
	 */
	private char calcolaControlCrt(String codFisc) {
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
	 * Calcola i caratteri derivanti dal nome: - tutte le vocali presenti nel
	 * nome vengono memorizzate in una variabile - tutte le consonanti presenti
	 * nel nome vengono memorizzate in una variabile La stringa di ritorno sara'
	 * costituita da:
	 * <ul>
	 * <li>TRE CONSONANTI (la prima,la terza e la quarta): nel caso in cui le
	 * consonanti sono piu' di 3
	 * <li>TRE CONSONANTI (la prima,la seconda,la terza) nel caso in cui le
	 * consonanti sono 3
	 * <li>DUE CONSONANTI E UNA VOCALE : nel caso in cui le consonanti sono 2 ed
	 * e' stata trovata almeno 1 vocale
	 * <li>DUE CONSONANTI E X : nel caso in cui le consonanti sono 2 e non sono
	 * state trovate vocali
	 * <li>UNA CONSUNANTE E DUE VOCALI : nel caso in cui c'e' 1 sola consonante e
	 * le vocali sono almeno 2
	 * <li>UNA CONSONANTE,UNA VOCALE E X: nel caso un cui c'e' 1 sola consonante
	 * e 1 sola vocale
	 * <li>DUE VOCALI E X nel caso in cui non ci sono consonanti
	 * </ul>
	 * 
	 * @param nome
	 *            Nome da cui devono derivare i secondi 3 caratteri del CF
	 * @return stringa con i secondi 3 caratteri del codice fiscale
	 */

	private String calcolaNome(String nome) {
		String tmpText = new String();
		String tmpConsonanti = new String();
		String tmpVocali = new String();
		String nomeU = nome.toUpperCase();
		int lungh = nomeU.length();
		char currChar;
		for (int i = 0; i < lungh; i++) {
			currChar = nomeU.charAt(i);
			if (currChar == 'A' || currChar == 'Á' || currChar == 'À' || currChar == 'E' || currChar == 'È' || currChar == 'É' || currChar == 'I' || currChar == 'Í' || currChar == 'Ì'
					|| currChar == 'O' || currChar == 'Ó' || currChar == 'Ò' || currChar == 'U' || currChar == 'Ú' || currChar == 'Ù') {
				tmpVocali = tmpVocali + (currChar);

			} else if ((currChar != ' ') && currChar != '\'' && currChar != '.') {
				tmpConsonanti = tmpConsonanti + (currChar);

			}
		}
		if (tmpConsonanti.length() > 3)
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(2) + tmpConsonanti.charAt(3);
		else if (tmpConsonanti.length() == 3)
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1) + tmpConsonanti.charAt(2);
		else if (tmpConsonanti.length() == 2) {
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1);
			// nel caso di nome con due sole consonanti non calcolo esattamente
			// il codice fiscale
			if (tmpVocali.length() > 1 || tmpVocali.length() == 1)
				tmpText = tmpText + tmpVocali.charAt(0);
			else
				tmpText = tmpText + 'X';
		} else if (tmpConsonanti.length() == 1) {
			tmpText = tmpText + tmpConsonanti.charAt(0);
			if (tmpVocali.length() > 1)
				tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			else {
				tmpText = tmpText + tmpVocali.charAt(0);
				tmpText = tmpText + 'X';
			}
		} else if (tmpVocali.length() >= 3) {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1) + tmpVocali.charAt(2);
		}

		// else {
		// tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
		// tmpText = tmpText + 'X';
		// }
		else if (tmpVocali.length() >= 2) {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			tmpText = tmpText + 'X';
		} else {
			tmpText = tmpText + tmpVocali.charAt(0);
			tmpText = tmpText + 'X' + 'X';
		}

		return tmpText;
	}

	/**
	 * Calcola i caratteri derivanti dalla data di nascita e sesso: La stringa
	 * di ritorno sara' costituita da:
	 * <ul>
	 * <li>ANNO : dato dall'ultimo e dal penultimo carattere della data di
	 * nascita
	 * <li>MESE : si memorizza in una variabile il quarto e il quinto carattere
	 * della data di nascita, a seconda del numero trovato si avra' una lettera
	 * dell'alfabeto corrispondente
	 * <li>GIORNO: nel caso in cui il sesso e' 'M' e' dato dal primo e secondo
	 * carattere della data di nascita nel caso in cui il sesso e' 'F' e' dato dal
	 * numero prima ricavato + 40
	 * </ul>
	 * 
	 * @param dataNascita
	 *            Data da cui devono derivare i caratteri 7 - 12 del CF
	 * @return stringa con i caratteri 7 - 12 del codice fiscale
	 */

	private String calcolaDataNasc(String dataNascita, String sesso) {
		String tmpMese = new String();
		String tmpGiorno = new String();
		String tmpTextGG = new String();
		String tmpText = new String();
		int length = dataNascita.length();

		// calcolo della porzione di codice che descrive la data di nascita a
		// partire
		// da una data nel formato dd-mm-aaaa:
		// Anno: gli ultimi due caratteri
		tmpText = tmpText + dataNascita.charAt(length - 2) + dataNascita.charAt(length - 1);

		// Mese: il terzo e il quarto carattere
		tmpMese = tmpMese + dataNascita.charAt(3) + dataNascita.charAt(4);
		tmpText += mese[((Integer.parseInt(tmpMese)) - 1)];

		// Giorno: i primi due caratteri (il numero deve essere aumentato di 40
		// se il sesso e' femminile
		tmpGiorno = tmpGiorno + dataNascita.charAt(0) + dataNascita.charAt(1);
		if (sesso.charAt(0) == 'F' || sesso.charAt(0) == 'f')
			tmpTextGG += ((Integer.parseInt(tmpGiorno)) + 40);
		else
			tmpTextGG += tmpGiorno;

		// riempio se necessario con uno zero a sinistra
		if (tmpTextGG.length() == 1)
			tmpText += '0';

		tmpText += tmpTextGG;
		return tmpText;
	}

	/**
	 * Calcola i caratteri derivanti dal cognome: - tutte le vocali presenti nel
	 * cognome vengono memorizzate in una variabile - tutte le consonanti
	 * presenti nel cognome vengono memorizzate in una variabile La stringa di
	 * ritorno sara' costituita da:
	 * <ul>
	 * <li>TRE CONSONANTI (le prime tre): nel caso in cui le consonanti sono
	 * almeno 3
	 * <li>DUE CONSONANTI E UNA VOCALE : nel caso in cui le consonanti sono 2 ed
	 * e' stata trovata almeno 1 vocale
	 * <li>DUE CONSONANTI E X : nel caso in cui le consonanti sono 2 e non sono
	 * state trovate vocali
	 * <li>UNA CONSUNANTE E DUE VOCALI : nel caso in cui c'e' 1 sola consonante e
	 * le vocali sono almeno 2
	 * <li>UNA CONSONANTE,UNA VOCALE E X: nel caso un cui c'e' 1 sola consonante
	 * e 1 sola vocale
	 * <li>DUE VOCALI E X nel caso in cui non ci sono consonanti
	 * </ul>
	 * 
	 * @param cognome
	 *            Cognome da cui devono derivare i primi 3 caratteri del CF
	 * @return stringa con i primi 3 caratteri del codice fiscale
	 */

	private String calcolaCognome(String cognome) {
		String tmpText = new String();
		String tmpConsonanti = new String();
		String tmpVocali = new String();

		String cognomeU = cognome.toUpperCase();
		char currChar;
		int lungh = cognomeU.length();
		for (int i = 0; i < lungh; i++) {
			currChar = cognomeU.charAt(i);
			if (currChar == 'A' || currChar == 'Á' || currChar == 'À' || currChar == 'E' || currChar == 'È' || currChar == 'É' || currChar == 'I' || currChar == 'Í' || currChar == 'Ì'
					|| currChar == 'O' || currChar == 'Ó' || currChar == 'Ò' || currChar == 'U' || currChar == 'Ú' || currChar == 'Ù') {
				tmpVocali = tmpVocali + (currChar);
			} else if ((currChar != ' ') && currChar != '\'' && currChar != '.') {
				// else if((currChar != ' ' ) )
				tmpConsonanti = tmpConsonanti + (currChar);
			}
		}

		if (tmpConsonanti.length() > 2)
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1) + tmpConsonanti.charAt(2);
		else if (tmpConsonanti.length() == 2) {
			tmpText = tmpText + tmpConsonanti.charAt(0) + tmpConsonanti.charAt(1);

			// nel caso di cognome con sole due consonanti non calcolo
			// esattamente il codice fiscale
			if (tmpVocali.length() > 1 || tmpVocali.length() == 1)
				tmpText = tmpText + tmpVocali.charAt(0);
			else
				tmpText = tmpText + 'X';
		} else if (tmpConsonanti.length() == 1) {
			tmpText = tmpText + tmpConsonanti.charAt(0);
			if (tmpVocali.length() > 1)
				tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			else {
				tmpText = tmpText + tmpVocali.charAt(0);
				tmpText = tmpText + 'X';
			}
		}
		// Contini: caso di cognome di sole vocali
		else if (tmpVocali.length() >= 3) {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1) + tmpVocali.charAt(2);
		} else {
			tmpText = tmpText + tmpVocali.charAt(0) + tmpVocali.charAt(1);
			tmpText = tmpText + 'X';
		}

		return tmpText;
	}

	// fine modifica contini

	public String controllaEPulisciCFConOmocodia(String codiceFiscale) {
		// stringa per controllo e calcolo omocodia
		String omoCodici = "LMNPQRSTUV";
		codiceFiscale = codiceFiscale.toUpperCase();
		char[] cCodice = codiceFiscale.toCharArray();

		// check della correttezza formale del codice fiscale
		// elimino dalla stringa gli eventuali caratteri utilizzati negli
		// spazi riservati ai 7 che sono diventati carattere in caso di omocodia
		for (int k = 6; k < 15; k++) {
			if ((k == 8) || (k == 11))
				continue;
			int x = (omoCodici.indexOf(cCodice[k]));
			if (x != -1)
				cCodice[k] = Integer.toString(x).charAt(0);
		}
		// codiceFiscale = cCodice.toString();
		codiceFiscale = new String(cCodice);
		return codiceFiscale;
	}

	public boolean checkCodiceFiscaleNumerico(String cf) {
		// controllo della validita' del cf in formato numerico di 11 valori

		ValidazioneCampi val = ValidazioneCampi.getInstance();

		if (cf.length() != 11 || !val.controllaNumero(cf)) {
			return false;
		} else {
			try {
				int totPari = 0;
				int totDisp = 0;
				int tot = 0;
				String totStr = "";
				String totUnitaStr = "";

				String undicesima_cifra = cf.substring(10, 11);

				for (int i = 1; i < cf.length() - 1; i++) {
					int cifraPari = Integer.parseInt(cf.substring(i, i + 1)) * 2;
					if (cifraPari > 9) {
						String cifraPariStr = Integer.toString(cifraPari);
						String cifraPariStr1 = cifraPariStr.substring(0, 1);
						String cifraPariStr2 = cifraPariStr.substring(1, 2);
						cifraPari = Integer.parseInt(cifraPariStr1) + Integer.parseInt(cifraPariStr2);
					}

					totPari += cifraPari;
					i++;
				}

				for (int i = 0; i < cf.length() - 1; i++) {
					int cifraDispari = Integer.parseInt(cf.substring(i, i + 1));
					totDisp += cifraDispari;
					i++;
				}

				tot = totPari + totDisp;

				if (tot > 9) {
					totStr = Integer.toString(tot);
					totUnitaStr = totStr.substring(1, 2);
					tot = Integer.parseInt(totUnitaStr);
				}

				tot = 10 - tot;
				if (tot > 9) {
					totStr = Integer.toString(tot);
					totUnitaStr = totStr.substring(1, 2);
					tot = Integer.parseInt(totUnitaStr);
				}

				totStr = Integer.toString(tot);

				if (!undicesima_cifra.equalsIgnoreCase(totStr)) {
					return false;
				}
			} catch (NumberFormatException ex) {
				LOG.error("CodiceFiscale::checkCodiceFiscaleNumerico", ex.getMessage());
				return false;
			}
		}
		return true;
	}
	
	// JIRA-1547 71441
	private boolean checkCodiceComune(String codiceComune, DecodificaDad decodificaDad){
		Comune comuneSingolo = null;
		StatiEsteri statoEstero = null;
		
		try {
			if (!codiceComune.startsWith("Z") && !codiceComune.startsWith("z")) {
				comuneSingolo = decodificaDad.getComuneByCodComuneMin(codiceComune.toUpperCase());
				if (comuneSingolo == null || comuneSingolo.getCodComuneMin() == null || comuneSingolo.getCodComuneMin().equals("")) {
					return false;
				}
			} else {
				statoEstero = decodificaDad.getStatoEsteroByCodStatoMin(codiceComune.toUpperCase());
				if (statoEstero == null || statoEstero.getCodNazioneMin() == null || statoEstero.getCodNazioneMin().equals("")) {
					return false;
				}
			}

		} catch (Exception e) {
			LOG.error("CodiceFiscale::checkCodiceComune", e.getMessage());
			LOG.error("CodiceFiscale::checkCodiceComune", "Eccezione: non possibile reperire comune nascita dal codice " + codiceComune);
			return false;
		}
		return true;
	}

	
}
