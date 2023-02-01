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
package it.csi.comonl.comonlweb.ejb.util.commax.dto;


import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.ApiError;

/**
 * Contiene l'esito dell'elaborazione della comunicazione
 * @version 1.0
 * @created 06-Mar-2008 18:07:28
 */
public class EsitoControlloComunicazione {

    private List<ApiError> elencoErrori;
	
    
    private String nomeFile = null;
   
    
	public EsitoControlloComunicazione(){
      elencoErrori= new ArrayList<ApiError>();
	}

	
	public int getnumErroriBloccanti(){
		return elencoErrori.size();
	}
	
	

	public List<ApiError> getElencoErrori() {
		return elencoErrori;
	}


	public void setElencoErrori(List<ApiError> elencoErrori) {
		this.elencoErrori = elencoErrori;
	}


	public String getNomeFile() {
		return nomeFile;
	}


	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
