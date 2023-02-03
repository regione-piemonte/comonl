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
package it.csi.comonl.comonlweb.lib.util.filesystem;

public class EsitoEstrazioneFilesDaZip {
	
	public static final String ESITO_ESTRAZIONE_OK = "ESITO_ESTRAZIONE_OK"; 
	public static final String ESITO_ESTRAZIONE_KO = "ESITO_ESTRAZIONE_KO";
	
	private String esitoEstrazione = null;

	private String nomeFile = null;
	
	private String estensioneFile = null;
	
	private String contenutoDelFileInFormatoXML = null;

	public String getEsitoEstrazione() {
		return esitoEstrazione;
	}

	public void setEsitoEstrazione(String esitoEstrazione) {
		this.esitoEstrazione = esitoEstrazione;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContenutoDelFileInFormatoXML() {
		return contenutoDelFileInFormatoXML;
	}

	public void setContenutoDelFileInFormatoXML(String contenutoDelFileInFormatoXML) {
		this.contenutoDelFileInFormatoXML = contenutoDelFileInFormatoXML;
	}

	public String getEstensioneFile() {
		return estensioneFile;
	}

	public void setEstensioneFile(String estenioneFile) {
		this.estensioneFile = estenioneFile;
	}
}
