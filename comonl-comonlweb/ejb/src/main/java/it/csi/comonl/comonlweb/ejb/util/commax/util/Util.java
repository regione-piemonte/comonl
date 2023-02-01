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



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 * 
 * m.corsini classe ereditata dal vecchio
 * 
 */

public class Util {
	
	public static BigDecimal toBD(Long l)
	{
		if(l == null) return null;
		
		return new BigDecimal(l);
	}
	public static java.util.Date getDataOggi() {

		java.util.Date today = new java.util.Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String oggi = sdf.format(today);
			today = sdf.parse(oggi);

		} catch (Exception ex) {
			ex.getMessage();
		}

		return today;
	}
	
	public static String SNtoSINO(String flg) {
		if(flg == null) {
			return null;
		}
		
		if(flg.equalsIgnoreCase("S")) return "SI";
		if(flg.equalsIgnoreCase("N")) return "NO";
		
		throw new RuntimeException("Formato flag errato flg:" +flg);
	}
	
	public static String SINOtoSN(String flg) {
		if(flg == null) {
			return null;
		}
		
		if(flg.equalsIgnoreCase("SI")) return "S";
		if(flg.equalsIgnoreCase("NO")) return "N";
		
		throw new RuntimeException("Formato flag errato flg:" +flg);
	}

	public static java.sql.Date toSqlDate(java.util.Date date) {
		if (date == null)
			return null;
		else
			return new java.sql.Date(date.getTime());
	}

	public static String togliApici(String stringa) {
		if (stringa == null) {
			return null;
		} else {
			return replaceString(stringa, "'", "");
		}
	}

	private static String replaceString(final String aInput,
			final String aOldPattern, final String aNewPattern) {

		final StringBuffer result = new StringBuffer();
		// startIdx and idxOld delimit various chunks of aInput; these
		// chunks always end where aOldPattern begins
		int startIdx = 0;
		int idxOld = 0;
		while ((idxOld = aInput.indexOf(aOldPattern, startIdx)) >= 0) {
			// grab a part of aInput which does not include aOldPattern
			result.append(aInput.substring(startIdx, idxOld));
			// add aNewPattern to take place of aOldPattern
			result.append(aNewPattern);

			// reset the startIdx to just after the current match, to see
			// if there are any further matches
			startIdx = idxOld + aOldPattern.length();
		}
		// the final chunk will go to the end of aInput
		result.append(aInput.substring(startIdx));
		return result.toString();
	}

	public static boolean validaEmail(String email) {
		boolean ok = true;
		// controlla che la e-mail non sia vuota
		if (email == null || email.trim().equals("")) {
			ok = false;
		}

		// controllo della presenza del carattere '@'
		int index = email.indexOf("@", 0);
		String address = "";

		if (index == -1) {
			ok = false;
		} else {
			int size = email.length();
			address = email.substring(index + 1, size);
			String userid = email.substring(0, index);

			// controlla: l'unicita' del carattere "@";
			// - la presenza del "." nella parte DX dell'indirizzo;
			// - l'assenza di spazi e
			// - che la stringa non sia del tipo userid@.address o
			// userid.@address

			// il punto nella seconda parte deve comparire una volta sola
			if ((address != null && !address.trim().equals(""))
					&& (userid != null && !userid.trim().equals(""))) {
				if ((address.indexOf('@') != -1) || (userid.indexOf('@') != -1)
						|| (address.indexOf('.') == -1)
						|| (email.indexOf(" ") != -1)
						|| (email.charAt(index - 1) == '.')
						|| (email.charAt(index + 1) == '.')) {
					ok = false;
				}
			} else {
				ok = false;
			}
		}
		return ok;
	}

	public static String getNumProtLungoSette(BigDecimal numProt) {
		String numero = "";
		if (numProt != null) {
			if (numProt.toString().length() < 7) {
				numero = numProt.toString();
				while (numero.length() < 7) {
					numero = "0" + numero;
				}

			}
		}
		return numero;

	}

	/**
	 * Controlla l'estensione del file firmato
	 * 
	 * @param fileName
	 *            file firmato
	 * @return true se ha estensione .txt
	 */

	public static boolean controllaEstensioneFileUpload(String fileName) {
		boolean ok = true;

		// controllo che all'interno del file ci siano 2 punti
		int punto = 0;
		int indexPrimoPunto = 0;
		int indexSecondoPunto = 0;

		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			if (c == '.') {
				punto += 1;
				if (punto == 1) {
					indexPrimoPunto = i;
				}
				if (punto == 2) {
					indexSecondoPunto = i;
				}
			}
		}
		if (punto != 2) {
			ok = false;
		}
		// se sono stati trovati i due punti all'interno della stringa
		// controllo che la stringa tra i due punti sia <xml>
		else {
			String estensione = fileName.substring(indexPrimoPunto + 1,
					indexSecondoPunto);
			// ---------- controllo estensione -------------------
			if (estensione != null) {
				if (!estensione.equalsIgnoreCase("xml")
						&& !estensione.equalsIgnoreCase("zip")) {
					ok = false;
				}
			}
		}
		return ok;
	}

//	public static EstensioneFileDto restituisciEstensioneFileUpload(
//			String fileName) {
//		EstensioneFileDto result = new EstensioneFileDto();
//
//		if (!fileName.toLowerCase().endsWith(".p7m")) {
//			result
//					.setEsitoVerificaEstensioneFile(UtilKeyMessage.ERRORE_UPLOAD_FILE_EXTENSION);
//		} else {
//			String estNonFirmato = fileName.substring(fileName.length() - 7,
//					fileName.length() - 4);
//
//			if (!"xml".equalsIgnoreCase(estNonFirmato)
//					&& !"zip".equalsIgnoreCase(estNonFirmato)) {
//				result
//						.setEsitoVerificaEstensioneFile(UtilKeyMessage.ERRORE_UPLOAD_FILE_SUB_EXTENSION);
//			} else {
//				result.setEsitoVerificaEstensioneFile("OK");
//				result.setEstensioneFile(estNonFirmato);
//			}
//		}
//
//		return result;
//	}

	public static boolean checkMatricolaInps(String matricola) {
		// controllo della validita' della matricola INPS
		if (matricola.length() < 10
				|| !controllaPrimiDieciValoriInps(matricola)) {
			return false;
		} else {
			try {
				int totPari = 0;
				int totDisp = 0;
				String nona_Decima_Cifra = matricola.substring(8, 10);
				totPari = Integer.parseInt(matricola.substring(0, 1))
						+ Integer.parseInt(matricola.substring(2, 3))
						+ Integer.parseInt(matricola.substring(4, 5))
						+ Integer.parseInt(matricola.substring(6, 7));

				String totPariStr = Integer.toString(totPari);
				if (totPariStr.length() == 2) {
					totPariStr = totPariStr.substring(1, 2);
				} else {
					totPariStr = totPariStr.substring(0, 1);
				}

				totDisp = Integer.parseInt(matricola.substring(1, 2))
						+ Integer.parseInt(matricola.substring(3, 4))
						+ Integer.parseInt(matricola.substring(5, 6))
						+ Integer.parseInt(matricola.substring(7, 8));

				String totDispStr = Integer.toString(totDisp);
				if (totDispStr.length() == 2) {
					totDispStr = totDispStr.substring(1, 2);
				} else {
					totDispStr = totDispStr.substring(0, 1);
				}

				String mat = totPariStr.concat(totDispStr);

				if (mat.equals("0") ||
				// Contini: il caso 00 e' possibile, solo non deve essere
				// consentito
						// l'inserimento di 10 zeri. Questo controllo e' fatto in
						// controllaPrimiDieciValoriInps
						// mat.equals("00") ||
						!nona_Decima_Cifra.equalsIgnoreCase(mat)) {
					return false;
				}
			} catch (NumberFormatException ex) {
				System.out.println("checkMatricolaInps, ci sono errori.");
				return false;
			}
		}
		return true;
	}

	public static boolean controllaPrimiDieciValoriInps(String inps) {
		try {
			boolean ret;
			boolean nonTuttiZeri = false;
			String numeri = "0123456789";
			int i = 10;
			int j;
			int k;
			// var i = 10;
			for (j = 0; j < i; j++) {
				ret = false;
				for (k = 0; k < 10; k++) {
					if (inps.charAt(j) == numeri.charAt(k)) {
						ret = true;
					}
					// controllo che l'inps non sia formata di soli zeri
					if (inps.charAt(j) != '0') {
						nonTuttiZeri = true;
					}
				}
				if (!ret || !nonTuttiZeri) {
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			System.out
					.println("controllaPrimiDieciValoriInps, ci sono caratteri nelle prime 10 cifre.");
			return false;
		}
	}

//	public static String estraiComunePerDb(String comuneQuery,
//			String comuneQueryEstero, boolean stoCercandoIlComune)
//			throws CommassDAOSysException {
//
//
//		String ritorno = null;
//		QueryTabDecodificaGenerico dao = new QueryTabDecodificaGenerico();
//		dao.setQuery(comuneQuery);
//		String comunePerDb = dao.lanciaQueryString();
//		dao.setQuery(comuneQueryEstero);
//		String statoEsteroPerDb = dao.lanciaQueryString();
//		Debug.print("---------- comunePerDb = " + comunePerDb);
//		Debug.print("---------- comuneEsteroPerDb = " + statoEsteroPerDb);
//		if (stoCercandoIlComune) {
//			ritorno = comunePerDb;
//		} else {
//			ritorno = statoEsteroPerDb;
//		}
//		Debug.print("ritorno: " + ritorno);
//		return ritorno;
//	}
//
//	public static void soggettoAbilitato(ProfiloIride profilo,
//			ComDComunicazioneDTO comDComunicazioneDTO)
//			throws CommassDAOSysException {
//		comDComunicazioneDTO.setCodiceEnte(profilo.getCodiceFiscale());
//		String CFSoggettoAbilitato = profilo.getCodiceFiscale();
//		String CFSoggettoAbilitatoQuery = UtilConstant.QUERY_CF_SOGG_ABILIT
//				+ "'" + CFSoggettoAbilitato + "'";
//		QueryTabDecodificaGenerico dao = new QueryTabDecodificaGenerico();
//		/* TODO: flag accentramento de che? */
//		String flgAccentrQuery = UtilConstant.QUERY_FLG_ACCENTRAMENTO + "'"
//				+ CFSoggettoAbilitato + "'";
//		dao.setQuery(CFSoggettoAbilitatoQuery);
//		String CFSoggettoAbilitatoDb = dao.lanciaQueryString();
//
//		if (CFSoggettoAbilitato != null) {
//			comDComunicazioneDTO.setCodiceFiscaleSoggetto(CFSoggettoAbilitato);
//			String IDSoggettoAbilitatoQuery = UtilConstant.QUERY_ID_SOGG_ABILIT
//					+ "'" + CFSoggettoAbilitato + "'";
//			dao.setQuery(IDSoggettoAbilitatoQuery);
//			Long IDSoggettoAbilitatoDb = dao.lanciaQueryLong();
//			if (IDSoggettoAbilitatoDb != null) {
//				comDComunicazioneDTO
//						.setIdComTipoSoggettoAbilitato(IDSoggettoAbilitatoDb);
//			}
//		}
//	}
//	


	public static String estraiNomeFile(String nomeFileUpload) {
		String nomeFileEstratto = nomeFileUpload;
		int i=0;
		int index = 0;
		if(nomeFileUpload == null || nomeFileUpload.equals("")){
			return nomeFileEstratto;
		}
		// Pezza!!!!!!, se il file uploadato e' un file singolo occorre rimuovere il prefisso,
		// altrimenti non e' necessario perche' vuol dire che il file era contenuto in uno zip e quindi non aveva prefisso
		if (nomeFileUpload.startsWith("COM-20"))
		{
				while(i<4){
					index = nomeFileEstratto.indexOf('-');
					nomeFileEstratto = nomeFileEstratto.substring(index+1);
					i++;
				}
		}
		
		return nomeFileEstratto;
	}
	
	public static  String removeInvalidXMLCharacters(String xml) {
		
		char current;
		
		int l = xml.length();
		
		StringBuffer out = new StringBuffer(l); 
		
		if (xml == null || ("".equals(xml))){
			return "";
		}
		
		for (int i = 0; i < l; i++) {
			current = xml.charAt(i);
			if (current >= 32 && current <= 127  ||
			    current == 224 ||
				current == 232 ||
			    current == 233 ||
				current == 236 ||
				current == 242 ||
				current == 249){
				out.append(current);
			}
		}		
				
		return out.toString();
	}
	
	public static final Vector tokenizerForParameterValues (String strToSplit, String separator) {

		StringTokenizer sT = new StringTokenizer(strToSplit, separator);
		Vector v = new Vector();

		while (sT.hasMoreTokens()) {
			String tmp = sT.nextToken();
			if (tmp.length() != 0) {
				v.add(tmp);
			}
		}

		return v;
	}
	public static boolean isNotVoid(Object objIn){
		return !isVoid(objIn);
	}
	public static boolean isVoid(Object objIn){
		// oggetto nullo 
		if(objIn == null){
			return true;
		}	 
		// stringa vuota
		else if(objIn instanceof String && ((String)objIn).trim().equals("")){
			return true;
		}
		// Long
		else if(objIn instanceof Long && ((Long) objIn).longValue() == 0) {
			return true;
		}
		// Boolean: se non e' nullo,
		else if(objIn instanceof Boolean) {
			try {
				boolean b = ((Boolean) objIn).booleanValue();
				return false;
			}
			catch(Exception e) {
				return true;
			}
		}
		//collection nulla o vuota
		else if(objIn instanceof Collection && (objIn == null || ((Collection) objIn).size() == 0)){
			return true;
		}
		// java.util.date
		else if(objIn instanceof java.util.Date && ((java.util.Date) objIn).getTime() == 0) {
			return true;
		}
		else if(objIn instanceof java.sql.Date && ((java.sql.Date) objIn).getTime() == 0) {
			return true;
		}
		// gregoriaCal
		else if(objIn instanceof GregorianCalendar && ((GregorianCalendar) objIn).getTime() == null) {
			return true;
		}
		return false;
	}
	public static String addSpaceAtTheEndOfAllLines(String str ) throws IOException {
		
		StringBuffer sb = null;
		
		if (str != null) {
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new StringReader(str));
			
			loopAndAdd(sb, br);
		}
		
		if (sb != null) {
			return sb.toString();
		}
		return str;
	}

	private static void loopAndAdd(StringBuffer sb, BufferedReader br)
			throws IOException {
		String line;
		while((line=br.readLine())!= null){
			String lineToAdd = line.trim();
			lineToAdd += "  ";
			sb.append(lineToAdd);
		}
	}
	public static StringBuffer addSpaceAtTheEndOfAllLines(InputStream is ) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		
		if (is != null) {
			sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			loopAndAdd(sb, br);
		}
		return sb;
	}
	
	
	public static String utftoasci(String s){
		  final StringBuffer sb = new StringBuffer( s.length() * 2 );

		  final StringCharacterIterator iterator = new StringCharacterIterator( s );

		  char ch = iterator.current();

		  while( ch != StringCharacterIterator.DONE ){
		   if(Character.getNumericValue(ch)>0){
		    sb.append( ch );
		   }else{
		    boolean f=false;
		    if(Character.toString(ch).equals("Ê")){sb.append("E");f=true;}
		    if(Character.toString(ch).equals("È")){sb.append("E'");f=true;}
		    if(Character.toString(ch).equals("ë")){sb.append("e");f=true;}
		    if(Character.toString(ch).equals("é")){sb.append("e'");f=true;}
		    if(Character.toString(ch).equals("è")){sb.append("e'");f=true;}
		    if(Character.toString(ch).equals("è")){sb.append("e'");f=true;}
		    if(Character.toString(ch).equals("Â")){sb.append("A");f=true;}
		    if(Character.toString(ch).equals("ä")){sb.append("a");f=true;}
		    if(Character.toString(ch).equals("ß")){sb.append("ss");f=true;}
		    if(Character.toString(ch).equals("Ç")){sb.append("C");f=true;}
		    if(Character.toString(ch).equals("Ö")){sb.append("O");f=true;}
		    if(Character.toString(ch).equals("º")){sb.append("");f=true;}
		    if(Character.toString(ch).equals("Ó")){sb.append("O'");f=true;}
		    if(Character.toString(ch).equals("ª")){sb.append("");f=true;}
		    if(Character.toString(ch).equals("º")){sb.append("");f=true;}
		    if(Character.toString(ch).equals("Ñ")){sb.append("N");f=true;}
		    if(Character.toString(ch).equals("É")){sb.append("E'");f=true;}
		    if(Character.toString(ch).equals("Ä")){sb.append("A");f=true;}
		    if(Character.toString(ch).equals("Å")){sb.append("A");f=true;}
		    if(Character.toString(ch).equals("À")){sb.append("A'");f=true;}
		    if(Character.toString(ch).equals("ä")){sb.append("a");f=true;}
		    if(Character.toString(ch).equals("Ü")){sb.append("U");f=true;}
		    if(Character.toString(ch).equals("ö")){sb.append("o");f=true;}
		    if(Character.toString(ch).equals("ü")){sb.append("u");f=true;}
		    if(Character.toString(ch).equals("á")){sb.append("a'");f=true;}
		    if(Character.toString(ch).equals("Ó")){sb.append("O'");f=true;}
		    if(Character.toString(ch).equals("É")){sb.append("E'");f=true;}
		    if(Character.toString(ch).equals("Ã")){sb.append("A");f=true;}
		    if(!f){
		     sb.append(ch);
		    }
		   }
		   ch = iterator.next();
		  }
		  return sb.toString();
	}
	public static boolean isVuoto(String campo)
	{
//		if( campo == null || campo.equalsIgnoreCase(""))
		if( campo == null || campo.equalsIgnoreCase("") || campo.trim().length()==0)
			return true;
		else
			return false;
	}
}
