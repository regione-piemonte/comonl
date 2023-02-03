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
package it.csi.comonl.comonlweb.lib.dto.error;

/**
 * COMONL error types
 */
public enum MsgCommax implements ErrorCreator {

	// modulo-oggetto - Errore/Avviso/Prompt 
	
	/* 
	 * ATTENZIONE RICORDARSI DI ANDARE SUL FILE it.json parte angular 
	 * e agginugere eventuali msg inseriti nuovi
	 * */  

	COMXVAL0001 ("CMX-VAL-E-0001", "Inserire un indirizzo email valido"),
	COMXVAL0002 ("CMX-VAL-E-0002", "File passato vuoto"),
	COMXVAL0003 ("CMX-VAL-E-0003", "File passato non valido"),
	COMXVAL0004 ("CMX-VAL-E-0004", "Errore di sistema: Imposibile salvare file su FileSystem"),
	COMXVAL0005 ("CMX-VAL-E-0005", "Il file caricato e' gia' stato processato."),
	COMXVAL0006 ("CMX-VAL-E-0006", "Il file inviato ha dimensioni superiori a quelle consentite, oppure contiene piu' xml del consentito. Numero massimo di file "),
//	COMXVAL0007 ("CMX-VAL-E-0007", "Il file inviato ha dimensioni superiori a quelle consentite, oppure contiene piu' di " + archivioZip.getMaxNumberOfFilesInZip() + " file xml"),
	COMXVAL0008 ("CMX-VAL-E-0008", "L'archivio contiene uno o piu' file non validi"),
//	COMXVAL0009 ("CMX-VAL-E-0009", "IL FILE NON E' STATO VALIDATO DA SPICOM, MANDO MESSAGGIO DI ERRORE"),
	COMXVAL0010 ("CMX-VAL-E-0010", "ERRORE DI SISTEMA"),
	COMXVAL0011 ("CMX-VAL-E-0011", "L'acquisizione del file e' avvenuta con successo. La verifica dei dati trasmessi e' attualmente in corso. Al termine dell'operazione, Vi verra' recapitata via e-mail la ricevuta contenente l'esito dell'elaborazione."),
	COMXVAL0012 ("CMX-VAL-E-0012", "VERIFICA IN CORSO.......Al termine dell'operazione, Vi verra' recapitata via e-mail la ricevuta contenente l'esito della verifica."),
	COMXVAL0013 ("CMX-VAL-E-0013", "Attenzione: la funzione richiamata ha lo scopo di verificare il file e non prevede l'inoltro dello stesso al Ministero."),
	
	;

	private final String code;
	private final String type;
	private final String msgGroup;
	private final String message;

	/**
	 * Private constructor
	 * @param code the code
	 * @param message the message
	 */
	private MsgCommax(String code, String message) {
		this(code, null, message);
	}

	/**
	 * Private constructor
	 * @param code the code
	 * @param group the group
	 * @param message the message
	 */
	private MsgCommax(String code, String msgGroup, String message) {
		this(code, "ERROR", msgGroup, message);
	}
	
	private MsgCommax(String code, String type, String msgGroup, String message) {
		this.code = code;
		this.type = type;
		this.msgGroup = msgGroup;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getGroup() {
		return msgGroup;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
