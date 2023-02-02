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
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;

/**
 * Regole che si applicano
 * punti 1.1 e 1.2
 * 
 * @author 70493
 *
 */
public class RegoleValidazioneAziendaUtilizzatrice_US extends ControlloAstrattoBloccante 
{
	public void eseguiControllo(List<ApiError>elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{
		if( isUNISOM() ) {
			DittaUtilizzatriceDTO aziendaUtilizzatrice = comunicazione.getDittaUtilizzatrice();

			if( aziendaUtilizzatrice != null)
			{

				//********************************* AZIENDA ******************************************** 

				// codicefiscale
				String campoCodFiscale = aziendaUtilizzatrice.getCodFiscale();

				String AziUtilEsteraInItalia = aziendaUtilizzatrice.getFlgAziUtilEsteraInItalia();

				String capSedeLegale = aziendaUtilizzatrice.getCapSedeLegale();			

				if(UtilConstant.FLAG_SI.equalsIgnoreCase(AziUtilEsteraInItalia)){

					if(isValorizzato(campoCodFiscale)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_CODICE_FISCALE_VALORIZZATO_PER_AZI_ESTERA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					//Sede legale
					String statoEstero = aziendaUtilizzatrice.getComuneSedeLegale();
					if( isValorizzato(statoEstero) )
					{
						boolean esitoStatoEsteroSedeLegale = isStatoEsteroComunitarioValido(statoEstero, decodificaDad, null);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoStatoEsteroSedeLegale, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if(isValorizzato(capSedeLegale)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_CAP_VALORIZZATO_PER_AZI_ESTERA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);						

					}

				}
				else{

					if(isVuoto(campoCodFiscale)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_CODICE_FISCALE_OBBLIGATORIO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
					else{
						boolean isCodiceFiscaleOPartitaIvaCorretto = isCodiceFiscaleOPartitaIvaValido(campoCodFiscale);

						if(!isCodiceFiscaleOPartitaIvaCorretto){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_CODICE_FISCALE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
						else{

							DatoreDiLavoroDTO datoreLavoro = identificaDatoreLavoroDTORispettoTipoTracciato();
							String campoCodFiscaleDatoreLavoro = datoreLavoro.getCodFiscale();

							if( isValorizzato(campoCodFiscaleDatoreLavoro) )
							{
								if( campoCodFiscale.equals(campoCodFiscaleDatoreLavoro) )
								{
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_CODICE_FISCALE_ERRATO_UGUALE_DATORE_LAVORO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}
							}
						}
					}

					//Sede legale
					String idComuneSedeLegale = aziendaUtilizzatrice.getComuneSedeLegale();
					if( isValorizzato(idComuneSedeLegale) )
					{
						boolean esitoComuneSedeLegale = isIdComuneValido(idComuneSedeLegale, decodificaDad, null);
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoComuneSedeLegale, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_COMUNE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					if(isVuoto(capSedeLegale)){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_CAP_OBBLIGATORIO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

				}

				Date dataInizioContrattoSomministrazione = aziendaUtilizzatrice.getDataInizioSomministrazione();

				if(dataInizioContrattoSomministrazione == null){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_DATA_INIZIO_CONTRATTO_SOMMINISTRAZIONE_OBBLIGATORIA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}


				if(isTipoContrattoRichiedeDataFine()){

					Date dataFineContrattoSomministrazione = aziendaUtilizzatrice.getDataFineSomministrazione();

					if(dataFineContrattoSomministrazione == null){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_DATA_FINE_CONTRATTO_SOMMINISTRAZIONE_OBBLIGATORIA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}

				// settore Ateco
				String campoSettoreAteco = aziendaUtilizzatrice.getSettore();
				if( isValorizzato(campoSettoreAteco) )
				{
					boolean esitoAteco = false;
					Atecofin ateco = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, campoSettoreAteco, null);
					esitoAteco = (ateco != null && ateco.getId() !=null ? true : false);
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(esitoAteco, ErrorConstants.AZIENDA_UTILIZZATRICE_CODICE_ATECO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);					
				}

				//pubblica amministrazione
				String pubblicaAmministrazione = aziendaUtilizzatrice.getPubblicaAmministrazione();

				if( isVuoto(pubblicaAmministrazione)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_FLG_PUBBLICA_AMMINISTRAZIONE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else{
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFlagValido_ValoriAmmessi_SI_o_NO(pubblicaAmministrazione), ErrorConstants.AZIENDA_UTILIZZATRICE_FLG_PUBBLICA_AMMINISTRAZIONE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}

				// telefono,fax.email

				String valoriSedeLegale[]={aziendaUtilizzatrice.getTelefonoSedeLegale(),
						aziendaUtilizzatrice.getFaxSedeLegale(),
						aziendaUtilizzatrice.getEmailSedeLegale()};
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeLegale), ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				if( isValorizzato(aziendaUtilizzatrice.getEmailSedeLegale()))
				{
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(aziendaUtilizzatrice.getEmailSedeLegale()), ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_LEGALE_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}

				//********************************* SEDE OPERATIVA ********************************************
				// comune
				String comuneStatoEsteroSedeLavoro = aziendaUtilizzatrice.getComuneSedeLavoro();
				String capSedeOperativa = aziendaUtilizzatrice.getCapSedeLavoro();

				if( isValorizzato(comuneStatoEsteroSedeLavoro) )
				{
					boolean isComuneOStatoEsteroSedeOperativaValido = isComuneOStatoEsteroComunitarioValido(comuneStatoEsteroSedeLavoro, decodificaDad, null);

					if(!isComuneOStatoEsteroSedeOperativaValido){
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_COMUNE_O_STATO_ESTERO_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

					else{

						if(UtilConstant.FLAG_NO.equalsIgnoreCase(AziUtilEsteraInItalia)){

							if(isCodiceStatoEstero(comuneStatoEsteroSedeLavoro) ){
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_STATO_ESTERO_NON_AMMESSO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}

							else{

								if(isCodiceStatoEstero(comuneStatoEsteroSedeLavoro) && isValorizzato(capSedeOperativa)){
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_ESTERA_CON_CAP_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}

								if(isCodiceComune(comuneStatoEsteroSedeLavoro) && isVuoto(capSedeOperativa)){
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_CON_CAP_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}
							}

						}							
					}					
				}

				// telefono,fax.email
				String valoriSedeOperativa[]={aziendaUtilizzatrice.getTelefonoSedeLavoro(),
						aziendaUtilizzatrice.getFaxSedeLavoro(),
						aziendaUtilizzatrice.getEmailSedeLavoro()};

				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isValorizzatoAlmenoUnCampo(valoriSedeOperativa), ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_NESSUN_CAMPO_VALORIZZATO_FAX_TEL_EMAIL, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

				if( isValorizzato(aziendaUtilizzatrice.getEmailSedeLavoro())) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isFormatoMailValido(aziendaUtilizzatrice.getEmailSedeLavoro()), ErrorConstants.AZIENDA_UTILIZZATRICE_SEDE_OPERATIVA_EMAIL_FORMATO_NON_CORRETTO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
			}
		}
	}

	private boolean isTipoContrattoRichiedeDataFine() {
		String tipoContratto = comunicazione.getDatiInizio().getTipologiaContrattuale();

		if (tipoContratto.equalsIgnoreCase(UtilConstant.LAVORO_INTERINALE_O_A_SCOPO_DI_SOMMINISTRAZIONE_A_TEMPO_DETERMINATO) || 
				tipoContratto.equalsIgnoreCase(UtilConstant.LAVORO_INTERMITTENTE_A_TEMPO_DETERMINATO)){
			return true;
		}

		return false;

	}
}
