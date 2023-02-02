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


import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneDatoreDiLavoroPrecedente_VD extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		if( isVARDATORI() && comunicazione.getTrasferimentoRamoAziendale() != null )
		{	
			DatoreDiLavoroDTO datoreLavoroPrecedente = comunicazione.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente();  
			DatoreDiLavoroDTO datoreLavoro = comunicazione.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale();
			// *********************************  AZIENDA  ***************************************************
			// codicefiscale
			String campoCodFiscale = datoreLavoroPrecedente.getCodFiscale();
			String campoCodFiscaleAttuale = datoreLavoro.getCodFiscale();
			if( isValorizzato(campoCodFiscale) )
			{
				boolean esitoCodFisc = isCodiceFiscaleOPartitaIvaValido(campoCodFiscale);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoCodFisc, ErrorConstants.DATORE_PRECEDENTE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				
				if(isValorizzato(campoCodFiscaleAttuale)){					
					if(campoCodFiscale .equalsIgnoreCase(campoCodFiscaleAttuale)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_CODICE_FISCALE_AZIENDA_PRECEDENTE_UGUALE_CODICE_FISCALE_AZIENDA_ATTUALE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}				
			}
			else{
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATORE_PRECEDENTE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}			
			
			// settore Ateco
			String campoSettoreAteco = datoreLavoroPrecedente.getSettore();
			if( isValorizzato(campoSettoreAteco) )
			{
				boolean esitoAteco = false;
				Atecofin ateco = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, campoSettoreAteco, null);
				esitoAteco = (ateco != null && ateco.getId() !=null ? true : false);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoAteco, ErrorConstants.DATORE_PRECEDENTE_CODICE_ATECO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
				
			// ****************************************** SEDE LEGALE  **********************************************
			// comune
			String idComuneSedeLegale = datoreLavoroPrecedente.getComuneSedeLegale();
			if( isValorizzato(idComuneSedeLegale) )
			{
				boolean esitoComuneSedeLegale = isIdComuneValido(idComuneSedeLegale, decodificaDad, null);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneSedeLegale, ErrorConstants.DATORE_PRECEDENTE_SEDE_LEGALE_COMUNE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			
			String valoriSedeLegale[]={datoreLavoroPrecedente.getTelefonoSedeLegale(),
									   datoreLavoroPrecedente.getFaxSedeLegale(),
									   datoreLavoroPrecedente.getEmailSedeLegale()};
			RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeLegale), ErrorConstants.DATORE_PRECEDENTE_SEDE_LEGALE_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			if( isValorizzato(datoreLavoroPrecedente.getEmailSedeLegale()) )
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(datoreLavoroPrecedente.getEmailSedeLegale()), ErrorConstants.DATORE_PRECEDENTE_SEDE_LEGALE_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			 
			// ******************************************* SEDE OPERATIVA  ******************************************
			DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO[] datIntTrasfAziDTO = comunicazione.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate();
			for (int i = 0; i < datIntTrasfAziDTO.length; i++) 
			{
				DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO dtoDatoreLavoroTrasfer = datIntTrasfAziDTO[i];
				String[] arrayParametriPerDescrizoneErrore = generaParametroPerSediLavoroOperativeDifferenti(dtoDatoreLavoroTrasfer);

				// comune
				String idComuneSedeOperativa = dtoDatoreLavoroTrasfer.getComuneSedeLavoro();
				String cap = dtoDatoreLavoroTrasfer.getCapSedeLavoro();
				
				if( isValorizzato(idComuneSedeOperativa) )
				{
					boolean isComuneOStatoEsteroValido = isIdComuneOStatoEsteroValido(idComuneSedeOperativa, decodificaDad, null);


					if(!isComuneOStatoEsteroValido){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.SEDE_OPERATIVA_VARDATORI_COMUNE_O_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

					}
					else{

						if(isCodiceStatoEstero(idComuneSedeOperativa) && isValorizzato(cap)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.SEDE_OPERATIVA_ESTERA_VARDATORI_CON_CAP_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

						if(isCodiceComune(idComuneSedeOperativa) && isVuoto(cap)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.SEDE_OPERATIVA_VARDATORI_CON_CAP_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}

					}

				}				
				
				String valoriSedeOperativa[]={dtoDatoreLavoroTrasfer.getTelefonoSedeLavoro(),
											  dtoDatoreLavoroTrasfer.getFaxSedeLavoro(),
											  dtoDatoreLavoroTrasfer.getEmailSedeLavoro()};

				
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeOperativa), ErrorConstants.DATORE_PRECEDENTE_SEDE_OPERATIVA_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);				
				
				if( isValorizzato( dtoDatoreLavoroTrasfer.getEmailSedeLavoro()) )
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(dtoDatoreLavoroTrasfer.getEmailSedeLavoro()), ErrorConstants.DATORE_PRECEDENTE_SEDE_LEGALE_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}
	}

	private String[] generaParametroPerSediLavoroOperativeDifferenti(DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO datSedeOpera ) 
	{
		String[] result = new String[1];
		result[0] = datSedeOpera.getIndirizzoSedeLavoro() + " " + datSedeOpera.getComuneSedeLavoro() + " " +datSedeOpera.getCapSedeLavoro(); 
		return result;
	}


}
