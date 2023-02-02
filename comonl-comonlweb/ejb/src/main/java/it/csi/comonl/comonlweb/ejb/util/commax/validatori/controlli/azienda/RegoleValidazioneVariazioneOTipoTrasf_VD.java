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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda;

import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatoreDiLavoroDTO;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneVariazioneOTipoTrasf_VD extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		if( isVARDATORI() )
		{
			if( comunicazione.getVariazioneRagioneSociale() != null ) 
			{
				// denominazione precedente
				DatoreDiLavoroDTO datoreLavoroAttuale = identificaDatoreLavoroDTORispettoTipoTracciato();
				String denominazioneAttuale = datoreLavoroAttuale.getDenominazione();
				String denominazionePrecedente = comunicazione.getVariazioneRagioneSociale().getDenominazionePrecedente();
				Date dataVariazione = comunicazione.getVariazioneRagioneSociale().getDataVariazione();
				
				if(isVuoto(denominazionePrecedente) || dataVariazione == null){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_DENOMINAZIONE_PRECEDENTE_O_DATA_VARIAZIONE_RAGIONE_SOCIALE_NON_VALORIZZATA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				
				if( isValorizzato( denominazioneAttuale) && isValorizzato( denominazionePrecedente) )
				{
					if( denominazioneAttuale.equalsIgnoreCase(denominazionePrecedente) )
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_DENOMINAZIONE_PRECEDENTE_DEVE_ESSERE_DIVERSA_DA_DENOMINAZIONE_ATTUALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}	
			}
			
			if( comunicazione.getTrasferimentoRamoAziendale() != null ) 
			{
			
				// codice trasferimento
				String codiceTrasferimento = comunicazione.getTrasferimentoRamoAziendale().getCodTrasferimentoDAzienda();
				
				if( isVuoto(codiceTrasferimento)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_CODICE_TRASFERIMENTO_OBBLIGATORIO_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else
				{
					boolean CodTrasferimentoValido = false;
					TipoTrasferimento tipoTrasferimento = (TipoTrasferimento) decodificaDad.getTfromMin(TipoTrasferimento.class, codiceTrasferimento, null); //FIXME è la data giusta?
					CodTrasferimentoValido = (tipoTrasferimento != null && tipoTrasferimento.getId() !=null ? true : false);
					if(!CodTrasferimentoValido){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_CODICE_TRASFERIMENTO_NON_TROVATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
					else{
						Date dataFineRamoAffitto = comunicazione.getTrasferimentoRamoAziendale().getDataFineRamoAffitto();
						
						if(codiceTrasferimento.equalsIgnoreCase(UtilConstant.CODICE_AFFITTO_RAMO)){
														
							if(dataFineRamoAffitto == null){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_DATA_FINE_RAMO_AFFITTO_OBBLIGATORIA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								
							}
							else{
								Date dataInizio = comunicazione.getTrasferimentoRamoAziendale().getDataInizio();
								
								if(!DateUtilities.isData1InferioreDiData2(dataInizio, dataFineRamoAffitto)){									
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_DATA_FINE_RAMO_AFFITTO_INFERIORE_O_UGUALE_DATA_INIZIO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}
								
							}
							
						}
						else{
							
							if(dataFineRamoAffitto != null){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_DATA_FINE_RAMO_AFFITTO_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								
							}
							
						}
						
					}
					
				}
		
			}
		}
	}
}
