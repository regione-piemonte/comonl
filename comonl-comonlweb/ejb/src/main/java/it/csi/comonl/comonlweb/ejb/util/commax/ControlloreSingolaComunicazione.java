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
package it.csi.comonl.comonlweb.ejb.util.commax;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.dto.EsitoControlloComunicazione;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstratto;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.elenco.CreatoreElencoControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErroriPK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;


/**
 * @version 1.0
 * @created 06-Mar-2008 18:07:28
 */
public class ControlloreSingolaComunicazione {

	List<ApiError> elencoErrori = new ArrayList<ApiError>();
	
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param gestoreTabelleDecodifica
	 * @param gestoreEsiti
	 */
	public ControlloreSingolaComunicazione() {
		
	}

	/**
	 * 
	 * @param parametro
	 */
	public List<ApiError> eseguiControllo(ParametriDiControllo parametro, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad, Ruolo ruolo)throws Exception {
		// Eseguo le regole su tutti i controlli;
		ControlloAstratto elencoControlli[]=CreatoreElencoControlli.creaElencoControlliConInizializzazione(
										parametro.getidReport(), 
										parametro.getCodiceFiscaleAzienda(),
										parametro.getcomunicazioneCorrenteDTO(), 
										parametro.getnomeFileXml(), 
										commonDad, ruolo);
		
		for (int i = 0; i < elencoControlli.length; i++) {
			elencoControlli[i].eseguiControllo(elencoErrori, commonDad, decodificaDad,comunicazioneDad);
		}
		
		EsitoControlloComunicazione esito=new EsitoControlloComunicazione();
		esito.setElencoErrori(elencoErrori);

		return elencoErrori;
	}
}
