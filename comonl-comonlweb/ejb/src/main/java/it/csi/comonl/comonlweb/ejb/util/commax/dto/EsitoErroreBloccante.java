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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;

/**
 * @version 1.0
 * @created 06-Mar-2008 18:07:28
 */
public class EsitoErroreBloccante extends EsitoErrore {

	/**
	 * Nome del file xml scartato
	 */
	public String nomeFileXML;

	public EsitoErroreBloccante(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public String getnomeFileXML(){
		return nomeFileXML;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setnomeFileXML(String newVal){
		nomeFileXML = newVal;
	}

}
