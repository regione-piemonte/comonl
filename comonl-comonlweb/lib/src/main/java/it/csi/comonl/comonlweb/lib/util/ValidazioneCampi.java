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
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;



// Classe di utilita' per la validazione dei campi delle interfacce
public class ValidazioneCampi {
	
	private static final LogUtil LOG = new LogUtil(ValidazioneCampi.class);
	
	private static final String EMAIL_PATTERN_OLD = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String EMAIL_PATTERN = "([A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*@[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+(\\.[A-Za-z0-9!#-'\\*\\+\\-/=\\?\\^_`\\{-~]+)*)";
	
	
	private static Pattern pattern;
	private static Matcher matcher;

	// Unica istanza della classe
	private static ValidazioneCampi singleton = null;

	// Per ottenere l'unica istanza della classe
	public static ValidazioneCampi getInstance() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

	/**
	 * createSingleton
	 * PUCCI: Aggiunta per migliorare la gestione del synchronized
	 * il synchronized e' necessario solo quando si crea l'istanza Singleton
	 * ATTENZIONE: e' necessario controllare prima il NULL per evitare di creare
	 * + volte l'istanza
	 */
	private static synchronized void createSingleton() {
		if (singleton == null)
			singleton = new ValidazioneCampi();
	}

	// Costruttore di default
	public ValidazioneCampi() {}



	public boolean checkAnno(String anno) {
		try {
			if (anno != null && anno != "") {
				if (!controllaAnno(anno)) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		catch (Exception ex) {
			LOG.error("ValidazioneCampi::checkAnno", ex.getMessage());
		}
		return true;
	}

	public boolean controllaAnno(String anno) {
		String numeri = "0123456789";
		if (anno.trim().length() != 4) {
			return false;
		}
		//    try {
		boolean ret = false;
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 10; k++) {
				if (anno.charAt(j) == numeri.charAt(k)) {
					ret = true;
					break;
				}
				else {
					ret = false;
				}
				// nel caso in cui al termine di un ciclo di controllo su una cifra dell'anno,
				// si sia trovato un carattere non numerico, si torna false
				if(ret = false && k == 9) {
					return false;
				}
			}
		}
		if (!ret) {
			return false;
		}

		Date fd = new Date();
		String data = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALIAN).format(fd);
		int indice = data.lastIndexOf("/");
		String annoOdierno = data.substring(indice + 1);
		Integer annoOdiernoInt = Integer.parseInt(annoOdierno);
		int annoInt = annoOdiernoInt.intValue();
		if (annoInt >= 80) {
			annoInt = 1900 + annoInt;
		}
		else {
			annoInt = 2000 + annoInt;
		}
		int annoInserito = Integer.parseInt(anno);
		if (annoInserito > annoInt) {
			return false;
		}

		return true;
	}

	public boolean checkProt(String protocollo) {
		try {
			if (protocollo != null && protocollo != "") {
				if (!controllaProt(protocollo)) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		catch (Exception ex) {
			LOG.error("ValidazioneCampi::checkProt", ex.getMessage());
		}
		return true;
	}

	public boolean controllaProt(String prot) {
		String numeri = "0123456789";
		int i = prot.length();
		if (i > 10)
			return false;
		for (int j = 0; j < i; j++) {
			boolean ret = false;
			for (int k = 0; k < 10; k++) {
				if (prot.charAt(j) == numeri.charAt(k))
					ret = true;
			}
			if (!ret)
				return false;
		}
		return true;
	}

	public boolean checkMail(String indirizzoMail) {
		if (indirizzoMail != null && !indirizzoMail.equals("")) {
			if(!controllaIndirizzoEmailSecondoRegExp(indirizzoMail)) {
				return false;
			}
		}
		return true;
	}

	public boolean controllaIndirizzoEmail(String indirizzo) {
		// Non deve essere null;
		// devono esserci almeno 3 caratteri (prima della chiocciola, chiocciola e dopo la chiocciola);
		// deve esserci uno e un solo carattere '@' 
		if (indirizzo == null || indirizzo.trim().length() < 3)
			return false;
		int indice = indirizzo.indexOf("@");

		if (indice == -1 || StringUtils.countMatches(indirizzo, "@") != 1)
			return false;

		// blocco di controllo sui caratteri
		String listaDaControllare = indirizzo.toLowerCase();
		String lettere = ".@abcdefghilmnopqrstuvzxywjk0123456789!#$%&'*+-/=?^_`{|}~";
		for(int i = 0; i < listaDaControllare.length(); i++) {
			boolean ris = false;
			for (int j = 0; j < lettere.length(); j++) {
				if (listaDaControllare.charAt(i) == lettere.charAt(j)) {
					ris = true;
					break;
				}
			}
			if(!ris)
				return false;
		}

		String dominio = indirizzo.substring(indice + 1, indirizzo.length());
		indice = dominio.indexOf("@");
		if (indice != -1)
			return false;

		// il carattere iniziale, finale, prima e dopo la "@" non possono essere punti '.'
		indice = indirizzo.indexOf("@");
		char inizio = indirizzo.charAt(0);
		char fine = indirizzo.charAt(indirizzo.length() - 1);
		char preChiocciola = indirizzo.charAt(indice - 1);
		char postChiocciola = indirizzo.charAt(indice + 1);
		String listaDaControllare2 = ("" + inizio + fine + preChiocciola + postChiocciola).toLowerCase();
		for(int i = 0; i < 4; i++ ) {
			//    	boolean ris = false;
			if (listaDaControllare2.charAt(i) == '.') {
				//			ris = true;
				return false;
			}
		}

		// Deve esserci almeno un carattere '.' nel dominio
		indice = dominio.indexOf(".");
		if (indice == -1)
			return false;


		// non sono ammessi piu' caratteri '.' consecutivi
		if(StringUtils.countMatches(indirizzo, "..") > 0) {
			return false;
		}


		// Tutto ok
		return true;
	}

	public boolean controllaIndirizzoEmailSecondoRegExp(String indirizzo) {

		pattern = Pattern.compile(EMAIL_PATTERN);
		if (ComonlUtility.isNotVoid(indirizzo)) {
			indirizzo = indirizzo.trim();
			matcher = pattern.matcher(indirizzo);
			return matcher.matches();
		}
		return true;
	}

	public boolean checkPatInail(String pat) {
		// controllo della validita' del PAT INAIL
		if (pat.length() > 10 || pat.trim().length() < 8 || !controllaNumero(pat)) {
			return false;
		}
		return true;
	}

	// inizio modifica conti
	public boolean checkMatricolaInps(String matricola) {
		// controllo della validita' della matricola INPS
		if (matricola.length() < 10 || !controllaPrimiDieciValoriInps(matricola)) {
			return false;
		}
		else {
			try {
				int totPari = 0;
				int totDisp = 0;
				String nona_Decima_Cifra = matricola.substring(8, 10);
				totPari = Integer.parseInt(matricola.substring(0, 1)) +
						Integer.parseInt(matricola.substring(2, 3)) +
						Integer.parseInt(matricola.substring(4, 5)) +
						Integer.parseInt(matricola.substring(6, 7));

				String totPariStr = Integer.toString(totPari);
				if (totPariStr.length() == 2) {
					totPariStr = totPariStr.substring(1, 2);
				}
				else {
					totPariStr = totPariStr.substring(0, 1);
				}

				totDisp = Integer.parseInt(matricola.substring(1, 2)) +
						Integer.parseInt(matricola.substring(3, 4)) +
						Integer.parseInt(matricola.substring(5, 6)) +
						Integer.parseInt(matricola.substring(7, 8));

				String totDispStr = Integer.toString(totDisp);
				if (totDispStr.length() == 2) {
					totDispStr = totDispStr.substring(1, 2);
				}
				else {
					totDispStr = totDispStr.substring(0, 1);
				}

				String mat = totPariStr.concat(totDispStr);

				if (mat.equals("0") ||
						// Contini: il caso 00 e' possibile, solo non deve essere consentito
						// l'inserimento di 10 zeri. Questo controllo e' fatto in
						// controllaPrimiDieciValoriInps
						//        	mat.equals("00") ||
						!nona_Decima_Cifra.equalsIgnoreCase(mat)) {
					return false;
				}
			}
			catch (NumberFormatException ex) {
				LOG.error("ValidazioneCampi::checkMatricolaInps", ex.getMessage());
				return false;
			}
		}
		return true;
	}

	public boolean controllaPrimiDieciValoriInps(String inps) {
		try {
			boolean ret;
			boolean nonTuttiZeri = false;
			String numeri = "0123456789";
			int i = 10;
			int j;
			int k;
			//     var i = 10;
			for (j = 0; j < i; j++) {
				ret = false;
				for (k = 0; k < 10; k++) {
					if (inps.charAt(j) == numeri.charAt(k)) {
						ret = true;
					}
					// controllo che l'inps non sia formata di soli zeri
					if(inps.charAt(j) != '0') {
						nonTuttiZeri = true;
					}
				}
				if (!ret || !nonTuttiZeri) {
					return false;
				}
			}
			return true;
		}
		catch (Exception ex) {
			LOG.error("ValidazioneCampi::controllaPrimiDieciValoriInps", ex.getMessage());
			return false;
		}
	}

	public boolean controllaPrimiOttoValoriInps(String inps) {
		try {
			boolean ret;
			String numeri = "0123456789";
			int i = 8;
			int j;
			int k;
			//     var i = 10;
			for (j = 0; j < i; j++) {
				ret = false;
				for (k = 0; k < 10; k++) {
					if (inps.charAt(j) == numeri.charAt(k)) {
						ret = true;
					}
				}
				if (!ret) {
					return false;
				}
			}
			return true;
		}
		catch (Exception ex) {
			LOG.error("ValidazioneCampi::controllaPrimiOttoValoriInps", ex.getMessage());
			return false;
		}
	}

	// fine modifica conti

	public static boolean controllaData(String data) {
		if (data.length() != 10)
			return false;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			formatter .setLenient(false) ;
			formatter.parse(data);
			return true;
		} catch ( Exception exception) {
			return false;
		}
	}

	public boolean controllaNumero(String numero) {
		Long numeroLong = 0L;
		try {
			numeroLong = Long.parseLong(numero.trim());
		}
		catch (NumberFormatException ex) {
			LOG.error("ValidazioneCampi::controllaNumero", ex.getMessage());
			return false;
		}

		if (numeroLong.longValue() < 0) {
			return false;
		}
		return true;
	}

	public boolean controllaNumeroIntero(String numero) {
		try {
			Integer i = Integer.parseInt(numero);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	public boolean controllaNumeroRelativo(String numero) {
		Long numeroLong = 0L;
		try {
			numeroLong = Long.parseLong(numero.trim());
		}
		catch (NumberFormatException ex) {
			LOG.error("ValidazioneCampi::controllaNumeroRelativo", ex.getMessage());
			return false;
		}
		return true;
	}

	public boolean controllaLegge68NumAtto(String valore) {
		boolean soloNum = false;
		String subStrNumerica = "";
		String subStrCaratteri = "";
		// il formato e': 99999999 [da 1 a 8 numeri]
		// oppure 99999999/AAAAAAAA [da 1 a 8 numeri + / + da 1 a 8 lettere]

		int indice = valore.indexOf("/");
		if (indice == -1){
			// allora devono essere solo numeri
			soloNum = true;
			subStrNumerica = valore;
		}
		else{
			subStrNumerica = valore.substring(0, indice - 1);
			subStrCaratteri = valore.substring(indice + 1, valore.length());
		}

		if(subStrNumerica.length() > 8){
			return false;
		}
		if(subStrCaratteri.length() > 8){
			return false;
		}

		// ******* numeri
		String numeri = "0123456789";
		int i = subStrNumerica.length();
		int j;
		int k;
		boolean ret;
		//     var i = 10;

		for (j = 0; j < i; j++) {
			ret = false;
			for (k = 0; k < 10; k++) {
				if (subStrNumerica.charAt(j) == numeri.charAt(k)) {
					ret = true;
				}
			}
			if (!ret) {
				return false;
			}
		}

		// ******* caratteri
		if(!soloNum){
			for (int c = 0; c < subStrCaratteri.length(); c++) {
				if (subStrCaratteri.toLowerCase().charAt(c) < 'a' ||
						subStrCaratteri.toLowerCase().charAt(c) > 'z') {
					return false;
				}
			}
		}
		return true;
	}



	public boolean controllaNumeroTelefonico(String numero) {
		Integer num;
		boolean trovatoSeparatore = false;

		if(numero == null || numero.trim().length() == 0) {
			return false;
		}

		try {
			for (int i = 0; i < numero.length(); i++) {
				if (numero.charAt(i) == ' ' || numero.charAt(i) == '/' ||
						numero.charAt(i) == '-') {

					if (trovatoSeparatore) {
						return false;
					}
					trovatoSeparatore = true;

					if (i == numero.length() - 1) {
						numero = numero.substring(0, numero.length() - 1);
					}
					else {
						numero = numero.substring(0, i) +
								numero.substring(i + 1, numero.length());
						i--;
					}
				}
				else {
					char character = numero.charAt(i); 
					num = (int) character;
					if (num.intValue() < 48 || num.intValue() > 57) {
						return false;
					}
				}
			}
		}
		catch (NumberFormatException ex) {
			LOG.error("ValidazioneCampi::controllaNumeroTelefonico", ex.getMessage());
			return false;
		}
		return true;
	}

	public static boolean isOreSettimanaliInRange(String oreSettimanali) {
		int tempInt = Integer.parseInt(oreSettimanali.trim());
		if (tempInt >= 40 || tempInt < 1)
			return false;
		return true;
	}

	public boolean isCorrectFormatRetribuzioneLordaGiornaliera(String retribuzioneLordaMensile) {

		//#STL retribuzioneLordaMensile = retribuzioneLordaMensile.trim();
		try
		{
			Integer.parseInt(retribuzioneLordaMensile);
		}
		catch (Exception ex)
		{
			return false;
		}

		// #STL commentato perche' lo fa dopo
		//  if (retribuzioneLordaMensile.length() > 9) {
		//    return false;
		//  }   
		// Controllo che il campo abbia il formato corretto -- 999999,99 (sono ammessi importi di 6 cifre senza decimali)
		if (retribuzioneLordaMensile.indexOf(",") != -1) {
			// La retribuzione e' stata specificata con la virgola
			return false;

		}
		else if (retribuzioneLordaMensile.length() > 9) {
			return false;
		}
		else if (retribuzioneLordaMensile.length() <= 9) {
			if (!this.controllaNumero(retribuzioneLordaMensile)) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}

	// controlla che il campo sia di 20 caratteri + data (gg/mm/aaaa)
	public static boolean checkNumIscrizioneAlbo(String numIscrAlbo) {
		if(numIscrAlbo != null && !numIscrAlbo.equals("") && numIscrAlbo.length() == 20) {
			String subStrNumData = numIscrAlbo.substring(10, numIscrAlbo.length());

			Conversioni c = Conversioni.getInstance();
			if(c.stringToDate(subStrNumData) == null) {
				return false;
			}
			// Tutto ok
			return true;
		}
		return false;
	}

	public boolean campiUgualiFraLoro(String c1, String c2) {
		if((c1 == null && c2 == null) || (c1 == null && c2 != null && c2.equals("")) ||
				(c1 != null && c1.equals("") && c2 == null)) {
			return true;
		}
		else {
			try {
				if(c1.equals(c2)) {
					return true;
				}
			}
			catch(Exception e) {
				LOG.info("ValidazioneCampi::controllaPrimiDieciValoriInps", " - c1 " + c1);
				LOG.info("ValidazioneCampi::controllaPrimiDieciValoriInps", " - c2 " + c2);
				LOG.error("ValidazioneCampi::campiUgualiFraLoro", e.getMessage());
			}
		}

		return false;
	}



	public static boolean isValidFormatoData (String dataInput) {

		if (ComonlUtility.convertiStringaInData(dataInput) == null) {
			// Se la data inserita non rispetta il formato corretto oppure e' null
			return false;
		}
		return true;
	}
	public static boolean  isValidEsistenzaData (String dataInput) {

		if (ComonlUtility.isNotVoid(dataInput) && !controllaData(dataInput)) {
			return false;
		}
		return true;

	}


	public static boolean isDataInInputVerificata (String dataInput) {

		if (ComonlUtility.isNotVoid(dataInput) 
				&& isValidEsistenzaData(dataInput)
				&& isValidFormatoData(dataInput) ) {

			return true;
		}
		return false;
	}

	public static boolean isFlagValido_ValoriAmmessi_SI_o_NO( String flag ) {
		boolean result = true;
		if( ComonlUtility.isVoid(flag) ) {
			result = true;	
		} else {
			if( flag.equals(ComonlConstants.FLAG_S ) || flag.equals(ComonlConstants.FLAG_N ) ) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;	
	}
	
	
	
	//  public static boolean isCausaCessazioneScaduta (String input) {
	//	  boolean isScaduta = false;
	//	  
	//	  
	//	  CessazioneBD cessBD = new CessazioneBD();
	//		try {
	//			ComTCessazionerlDTO cess = cessBD.findByKeyAndScaduta(input);
	//			if (Varie.isNotVoid(cess) && Varie.isNotVoid(cess.getDtFine())) {
	//				return true;
	//			}
	//			
	//		} catch (Exception e) {
	//			  DisplayMessageLog.error("[ValidazioneCampi::isCausaCessazioneScaduta]", e);
	//
	//			return false;
	//		}
	//	  return isScaduta;
	//  }
	//  public static boolean isTipoTrasformazioneScaduta (String input) {
	//	  boolean isScaduta = false;
	//	  
	//	  
	//	  TrasformazioneBD trsBD = new TrasformazioneBD();
	//	  try {
	//		  ComTTrasformazionerlDTO trs = trsBD.findByKeyAndScaduta(input);
	//		  if (Varie.isNotVoid(trs) && Varie.isNotVoid(trs.getDtFine())) {
	//			  isScaduta = true;
	//		  }
	//		  
	//	  } catch (Exception e) {
	//		  DisplayMessageLog.error("[ValidazioneCampi::isTipoTrasformazioneScaduta]", e);
	//
	//		  return false;
	//	  }
	//	  return isScaduta;
	//  }
	

}
