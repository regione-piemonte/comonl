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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda;


import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Format;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DatoreDomesticoDTO;
import it.csi.spicom.dto.DichiarazioniUnidomDTO;

public class RegoleValidazioneDatoreLavoroDomestico extends ControlloAstrattoBloccante {
	
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		if(isUNIDOM()){
			DatoreDiLavoroDTO datoreLavoro = identificaDatoreLavoroDTORispettoTipoTracciato();
			DichiarazioniUnidomDTO dichiarazioni = comunicazione.getDichiarazioniUnidom();
			String campoCodFiscaleDatoreLavoro = datoreLavoro.getCodFiscale();

			String denominazione = datoreLavoro.getDenominazione();

			DatoreDomesticoDTO datoreDomestico = (DatoreDomesticoDTO)datoreLavoro; 

			String cognome = datoreDomestico.getDsCognome();
			String nome = datoreDomestico.getDsNome();

			boolean esitoDenominazioneCognomeNome = isPresenteDenominazioneOCognomeENome(denominazione, cognome, nome);
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoDenominazioneCognomeNome, ErrorConstants.DATORE_AMMESSI_DENOMINAZIONE_O_COGNOME_E_NOME, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			if(isValorizzato(cognome) && isValorizzato(nome) && campoCodFiscaleDatoreLavoro.length() == 16){
				boolean esitoCongruenzaCfCognomeNome = Format.isCodiceFiscaleCongruenteCognomeNome(campoCodFiscaleDatoreLavoro, cognome, nome);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCongruenzaCfCognomeNome, ErrorConstants.DATORE_INCONGRUENZA_TRA_CODICE_FISCALE_E_COGNOME_NOME, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}

			if(dichiarazioni.getFlgDatoreLavoroConiugeLavoratore().equalsIgnoreCase(UtilConstant.FLAG_SI) ||
					dichiarazioni.getFlgDatoreLavoroParenteLavoratore().equalsIgnoreCase(UtilConstant.FLAG_SI)){

				if(dichiarazioni.getFlgDatoreLavoroInvalido() == null || dichiarazioni.getFlgDatoreLavoroConviventeLavoratore() == null){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_LAVORO_DOMESTICO_FLG_INVALIDITA_E_O_FLG_CONVIVENZA_NON_VALORIZZATI, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}				
			}
		}
	}

}
