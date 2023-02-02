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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.datiinvio;


import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ErrorConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.DatiEssenzialiUnilavVO;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.DatiEssenzialiUnisommVO;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.DatiEssenzialiVO;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.spicom.dto.DatiCessazioneDTO;
import it.csi.spicom.dto.DatiProrogaDTO;
import it.csi.spicom.dto.DatiTrasformazioneDTO;
import it.csi.spicom.dto.DatiVariazioneDTO;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;
import it.csi.spicom.dto.InizioMissioneDTO;
import it.csi.spicom.dto.MissioneDTO;
import it.csi.spicom.util.TUConstants;

public class RegoleValidazioneDatiInvio extends ControlloAstrattoBloccante 
{

	private Long idUplDocumenti;

	public RegoleValidazioneDatiInvio(Long idUplDocumenti){

		this.idUplDocumenti = idUplDocumenti;
	}

	@Override
	public void eseguiControllo(List<ApiError >elencoErrori, CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception
	{

		// tipo comunicazione
		String tipoComunicazioneTU = comunicazione.getDatiInvio().getTipoComunicazione();

		if( isValorizzato(tipoComunicazioneTU))
		{

			boolean isPresenteTipoCom = false;
			TipoComunicazioneTu comunicazioneTU = (TipoComunicazioneTu) decodificaDad.getTfromMin(TipoComunicazioneTu.class, tipoComunicazioneTU, null);
			isPresenteTipoCom = (comunicazioneTU != null && comunicazioneTU.getId() !=null ? true : false);

			if(!isPresenteTipoCom){
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_TIPO_COMUNICAZIONE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
			else{

				if(!(isUNILAV() && tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)) && isTipoComunicazioneTUNonAmmessa(tipoComunicazioneTU)){
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_TIPO_COMUNICAZIONE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				else{
					// codice comunicazione	
					String codiceComunicazione = comunicazione.getDatiInvio().getCodiceComunicazione();

					// codice comunicazione precedente	
					String codiceComunicazionePrecedente = comunicazione.getDatiInvio().getCodiceComunicazionePrecedente();

					// COMONL-1530


					String tipoComunicazione= comunicazione.getDatiInvio().getTipoComunicazione();


					if( isValorizzato(codiceComunicazionePrecedente))
					{
						if(tipoComunicazione.equalsIgnoreCase(UtilConstant.COMUNICAZIONE_PRECEDENTE_AMMESSA_RETTIFICA)
								|| tipoComunicazione.equalsIgnoreCase(UtilConstant.COMUNICAZIONE_PRECEDENTE_AMMESSA_ANNULLO)
								|| tipoComunicazione.equalsIgnoreCase(UtilConstant.COMUNICAZIONE_PRECEDENTE_AMMESSA_URG1)
								|| tipoComunicazione.equalsIgnoreCase(UtilConstant.COMUNICAZIONE_PRECEDENTE_AMMESSA_URG2)
								|| tipoComunicazione.equalsIgnoreCase(UtilConstant.COMUNICAZIONE_PRECEDENTE_AMMESSA_URG3))
						{
						}
						else
						{
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_TIPO_COMUNICAZIONE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}

					if( isValorizzato(codiceComunicazione) && !codiceComunicazione.equals(UtilConstant.CODICE_REGIONALE_NULLO))
					{
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_GIA_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}


					boolean isCodiceComunicazionePrecedenteGiaPresenteSuDB = false;


					if(tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_INVIATA_PER_VIA_TELEMATICA) ||
							//							tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA_TURISMO) ||
							tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_ANNULLAMENTO) || 
							tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_RETTIFICA)){

						if(isVuoto(codiceComunicazionePrecedente)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
						else{

							isCodiceComunicazionePrecedenteGiaPresenteSuDB = commonDad.isCodiceComunicazionePresenteSuDB(codiceComunicazionePrecedente);
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(isCodiceComunicazionePrecedenteGiaPresenteSuDB, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_PRESENTE_SU_DB, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}
					else if(tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_INVIATA_PER_VIA_FAX)){

						if(isValorizzato(codiceComunicazionePrecedente) && !codiceComunicazionePrecedente.equals(UtilConstant.CODICE_REGIONALE_NULLO)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

						}

					} else if (tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA_TURISMO)) {
						//						COMONL-1619
						//						Verifica della compilazione obbligatoria del codice comunicazione precedente in caso di rettifica o 
						//						annullamento o di comunicazione di urgenza inviata per via telematica o comunicazione a 
						//						seguito urgenza inviata per via te-lematica settore turistico art.4 c2 L.183/10, in caso di assunzione. 
						//						Il campo non deve essere valorizzato in tutti gli altri casi.

						if(!isValorizzato(codiceComunicazionePrecedente)){
							RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
						}
					}


					if( isUNILAV())
					{	

						if(tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE))
						{


							if(tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA_TURISMO)){


								if(isValorizzato(codiceComunicazionePrecedente))
								{	

									if(UtilConstant.CODICE_REGIONALE_NULLO.equalsIgnoreCase(codiceComunicazionePrecedente))
									{
										RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_VALIDO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

									}
								}
								else
								{
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_CODICE_COMUNICAZIONE_PRECEDENTE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}
							}



							if(tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_ANNULLAMENTO)){

								Date dataRicezione = comunicazione.getDatiInvio().getDataInvio();

								Date dataDiRiferimento = comunicazione.getDatiInizio().getDataInizio();

								boolean fuoriTermine = isDataRicezioneMaggioreDataRiferimento(dataDiRiferimento, dataRicezione);

								if(fuoriTermine){
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.ANNULLAMENTO_OLTRE_TERMINE_PROCEDERE_CON_CESSAZIONE, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

								}	
							}

						}


						if(TUConstants.TIPO_COMUNICAZIONE_RETTIFICA.equals(tipoComunicazioneTU)) {

							if(isCodiceComunicazionePrecedenteGiaPresenteSuDB){

								DatiEssenzialiUnilavVO datiEssenziali = getDatiEssenzialiUnilav(); 

								Comunicazione comunicazioneFromDb = getDatiComunicazionePrecedente(codiceComunicazionePrecedente, commonDad, comunicazioneDad);							

								DatiEssenzialiUnilavVO datiEssenzialiDB = getDatiEssenzialiUnilavDB(comunicazioneFromDb);

								checkValiditaRettifica(datiEssenzialiDB, datiEssenziali, codiceComunicazionePrecedente, elencoErrori, commonDad);

							}

						}	

						// assunzione per causa forza maggiore
						String flagAssunzioneForzaMaggiore = comunicazione.getDatiInvio().getFlgAssunzionePerCausaForzaMaggiore();
						if( isValorizzato(flagAssunzioneForzaMaggiore))
						{
							if( flagAssunzioneForzaMaggiore.equalsIgnoreCase(UtilConstant.FLAG_SI) || flagAssunzioneForzaMaggiore.equalsIgnoreCase(UtilConstant.FLAG_NO))
							{
								String descAssunzioneForzaMaggiore = comunicazione.getDatiInvio().getDescrizioneCausaForzaMaggiore();
								if(flagAssunzioneForzaMaggiore.equals(UtilConstant.FLAG_SI)&& isVuoto(descAssunzioneForzaMaggiore))
								{
									RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_DESCRIZIONE_FORZA_MAGGIORE_NON_VALORIZZATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
								}
							}
							else
							{
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_FLAG_ASSUNZIONE_FORZA_MAGGIORE_ERRATO, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}					
						}										
					}

					if(isUNISOM()){


						if(TUConstants.TIPO_COMUNICAZIONE_RETTIFICA.equals(tipoComunicazioneTU)){

							if(isCodiceComunicazionePrecedenteGiaPresenteSuDB){

								DatiEssenzialiUnisommVO datiEssenziali = getDatiEssenzialiUniSomm();

								Comunicazione comunicazioneFromDb = getDatiComunicazionePrecedente(codiceComunicazionePrecedente, commonDad, comunicazioneDad);

								DatiEssenzialiUnisommVO datiEssenzialiDB = getDatiEssenzialiUniSommDB(comunicazioneFromDb);

								checkValiditaRettifica(datiEssenzialiDB, datiEssenziali, codiceComunicazionePrecedente, elencoErrori, commonDad);

							}

						}

					}
				}
			}
		}
	}

	private boolean isTipoComunicazioneTUNonAmmessa(String tipoComunicazioneTU) {
		return tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA_TURISMO) ||
				tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_INVIATA_PER_VIA_FAX) ||
				tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_INVIATA_PER_VIA_TELEMATICA) ||
				tipoComunicazioneTU.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZ_DA_TIROCINIO_RAPP_LAV_SUB);
	}


	private DatiEssenzialiUnisommVO getDatiEssenzialiUniSommDB(Comunicazione comunicazioneDB) {

		DatiEssenzialiUnisommVO datiUniSommDB  =  new DatiEssenzialiUnisommVO();




		datiUniSommDB.setCodiceFiscaleAzienda(comunicazioneDB.getDatoreAttuale().getCodiceFiscale()); 
		Lavoratore lav = comunicazioneDB.getLavoratori().get(0);
		datiUniSommDB.setCodiceFiscaleLavoratore(lav.getCodiceFiscale()); 							
		datiUniSommDB.setScadenzaTitoloSoggiorno(lav.getDtScadenzaPermessoSogg()); 														
		datiUniSommDB.setNumTitoloSoggiorno(lav.getNumeroDocumento()); 							
		datiUniSommDB.setSussistenzaSistemazioneAlloggiativa(lav.getFlgSistAlloggiativa()); 							
		datiUniSommDB.setImpegnoPagamentoSpesaRimpatrio(lav.getFlgRimborsoRimpatrio()); 

		datiUniSommDB.setDataInizioRapporto(comunicazioneDB.getRapporto().getDtInizioRapporto()); 
		datiUniSommDB.setDataFineRapporto(comunicazioneDB.getRapporto().getDtFineRapporto());
		datiUniSommDB.setTipologiaContrattuale(comunicazioneDB.getRapporto().getTipoContratti().getCodTipoContrattoMin());
		String statoCodiceTipologiaContrattuale = UtilConstant.STATO_CODICE_VALIDO;
		if (comunicazioneDB.getRapporto().getTipoContratti().getDtFine() != null 
				&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getTipoContratti().getDtFine())) {
			statoCodiceTipologiaContrattuale = UtilConstant.STATO_CODICE_SCADUTO;
		}

		datiUniSommDB.setStatoCodiceTipologiaContrattuale(statoCodiceTipologiaContrattuale);

		datiUniSommDB.setDataTimbroPstale(comunicazioneDB.getDtTimbroPostale());
		datiUniSommDB.setDataFinePeriodoFormativo(comunicazioneDB.getRapporto().getDtFinePeriodoFormativo());


		datiUniSommDB.setCodiceFiscaledittaUtilizzatrice(comunicazioneDB.getRapporto().getAziUtilizzatrice() != null ? comunicazioneDB.getRapporto().getAziUtilizzatrice().getCodiceFiscale() : null);	
		datiUniSommDB.setDataFineMissione(comunicazioneDB.getMissione().getDtFineMissione());
		datiUniSommDB.setTipoOrario(comunicazioneDB.getMissione().getTipoOrario().getCodTipoorarioMin());

		String statoCodiceTipologiaOrario = UtilConstant.STATO_CODICE_VALIDO;
		if (comunicazioneDB.getRapporto().getTipoOrario().getDtFine() != null 
				&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getTipoOrario().getDtFine())) {
			statoCodiceTipologiaContrattuale = UtilConstant.STATO_CODICE_SCADUTO;
		}

		datiUniSommDB.setStatoCodiceTipoOrario(statoCodiceTipologiaOrario);

		datiUniSommDB.setDataFineProrogaMissione(comunicazioneDB.getMissione().getDtFineProroga());

		datiUniSommDB.setDataInizioMissione(comunicazioneDB.getMissione().getDtInizioMissione());


		datiUniSommDB.setDataTrasformazioneMissione(comunicazioneDB.getMissione().getDtTrasformazione());
		if (comunicazioneDB.getMissione().getTrasformazionerl() != null ) {
			datiUniSommDB.setCodiceTrasformazione(comunicazioneDB.getMissione().getTrasformazionerl().getCodTrasformazionirlMin());
			String statoCodiceTrasformazione = UtilConstant.STATO_CODICE_VALIDO;
			if (comunicazioneDB.getMissione().getTrasformazionerl().getDtFine() != null 
					&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getMissione().getTrasformazionerl().getDtFine())) {
				statoCodiceTrasformazione = UtilConstant.STATO_CODICE_SCADUTO;
			}
			datiUniSommDB.setStatoCodiceTrasformazione(statoCodiceTrasformazione);
		}

		datiUniSommDB.setDataCessazioneMissione(comunicazioneDB.getMissione().getDtCessazione());
		if (comunicazioneDB.getRapporto().getCessazionerl() != null) {
			datiUniSommDB.setCodiceCessazione(comunicazioneDB.getMissione().getCessazionerl().getCodCessazioneMin());
			String statoCodiceCessazione = UtilConstant.STATO_CODICE_VALIDO;
			if (comunicazioneDB.getMissione().getCessazionerl().getDtFine() != null 
					&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getMissione().getCessazionerl().getDtFine())) {
				statoCodiceCessazione = UtilConstant.STATO_CODICE_SCADUTO;
			}

			datiUniSommDB.setStatoCodiceCausaCessazione(statoCodiceCessazione);

		}		


		return datiUniSommDB;
	}


	private DatiEssenzialiUnisommVO getDatiEssenzialiUniSomm() {

		String codiceFiscaleAzienda = null;
		String codiceFiscaleLavoratore = null;
		Date scadenzaTitoloSoggiorno = null;														
		String numTitoloSoggiorno = null;							
		String sussistenzaSistemazioneAlloggiativa 	= null;						
		String impegnoPagamentoSpesaRimpatrio = null; 
		Date dataInizioRapporto = null;
		Date dataFineRapporto = null;
		String tipologiaContrattuale = null;


		String codiceFiscaledittaUtilizzatrice = null;
		Date dataInizioMissione = null;
		Date dataFineMissione = null;
		String tipoOrario = null;
		Date dataFineProrogaMissione = null;
		Date dataTrasformazioneMissione = null;
		String codiceTrasformazione = null;
		Date dataCessazioneMissione = null;
		String codiceCessazione = null;
		Date dataFinePeriodoFormativo = null;



		codiceFiscaleAzienda = comunicazione.getDatoreDiLavoro().getCodFiscale();

		codiceFiscaleLavoratore = comunicazione.getLavoratore().getCodFiscale();							
		scadenzaTitoloSoggiorno = comunicazione.getLavoratore().getDataScadenzaPermessoSoggiorno();														
		numTitoloSoggiorno = comunicazione.getLavoratore().getNumeroDocumento();							
		sussistenzaSistemazioneAlloggiativa = comunicazione.getLavoratore().getFlgSussistSistemAllog();							
		impegnoPagamentoSpesaRimpatrio = comunicazione.getLavoratore().getFlgPagamSpesaRimpatrio();


		dataInizioRapporto = comunicazione.getDatiInizio().getDataInizio();
		dataFineRapporto = comunicazione.getDatiInizio().getDataFine();
		dataFinePeriodoFormativo = comunicazione.getDatiInizio().getDataFinePeriodoFormativo();
		tipologiaContrattuale = comunicazione.getDatiInizio().getTipologiaContrattuale();


		//InizioRapportoUniSommDTO inizioRappUniSomm = ((InizioRapportoUniSommDTO)comunicazione.getDatiInizio());

		//dataFineProroga = inizioRappUniSomm.getDataFineProroga();

		//dataCessazione = inizioRappUniSomm.getDataCessazione();

		DittaUtilizzatriceDTO aziendaUtilizzatrice = comunicazione.getDittaUtilizzatrice();

		codiceFiscaledittaUtilizzatrice = aziendaUtilizzatrice.getCodFiscale();

		MissioneDTO dtoMissione = comunicazione.getMissione();

		DatiVariazioneDTO dtoVariazioni = comunicazione.getDatiVariazione();



		if(dtoVariazioni != null){

			DatiProrogaDTO datiProrogaDTO = dtoVariazioni.getDatiProroga();
			DatiTrasformazioneDTO datiTrasformazioneDTO = dtoVariazioni.getDatiTrasformazione();
			DatiCessazioneDTO datiCessazioneDTO = dtoVariazioni.getDatiCessazione();



			if(datiProrogaDTO != null){

				dataFineProrogaMissione = datiProrogaDTO.getDataFineProroga();

			}

			if(datiTrasformazioneDTO != null){

				dataTrasformazioneMissione = datiTrasformazioneDTO.getDataTrasformazione();
				codiceTrasformazione = datiTrasformazioneDTO.getCodiceTrasformazione();

			}

			if(datiCessazioneDTO != null){

				dataCessazioneMissione = datiCessazioneDTO.getDataCessazione();
				codiceCessazione = datiCessazioneDTO.getCodiceCausa();

			}
		}

		if (dtoMissione != null) {

			InizioMissioneDTO dtoInizioMissione = dtoMissione.getDatiInizioMissione();		

			if(dtoInizioMissione != null){

				dataInizioMissione = dtoInizioMissione.getDataInizioMissione();
				dataFineMissione = dtoInizioMissione.getDataFineMissione();
				tipoOrario = dtoInizioMissione.getTipoOrario();

			}
		}



		DatiEssenzialiUnisommVO datiEssenziali = new DatiEssenzialiUnisommVO();

		datiEssenziali.setCodiceFiscaleAzienda(codiceFiscaleAzienda); 
		datiEssenziali.setCodiceFiscaleLavoratore(codiceFiscaleLavoratore); 							
		datiEssenziali.setScadenzaTitoloSoggiorno(scadenzaTitoloSoggiorno); 														
		datiEssenziali.setNumTitoloSoggiorno(numTitoloSoggiorno); 							
		datiEssenziali.setSussistenzaSistemazioneAlloggiativa("SI".equalsIgnoreCase(sussistenzaSistemazioneAlloggiativa) ? "S" : null); 							
		datiEssenziali.setImpegnoPagamentoSpesaRimpatrio("SI".equalsIgnoreCase(impegnoPagamentoSpesaRimpatrio) ? "S" : null); 

		datiEssenziali.setDataInizioRapporto(dataInizioRapporto); 
		datiEssenziali.setDataFineRapporto(dataFineRapporto); 
		datiEssenziali.setTipologiaContrattuale(tipologiaContrattuale); 


		datiEssenziali.setCodiceFiscaledittaUtilizzatrice(codiceFiscaledittaUtilizzatrice);								
		datiEssenziali.setDataInizioMissione(dataInizioMissione);
		datiEssenziali.setDataFineMissione(dataFineMissione);
		datiEssenziali.setTipoOrario(tipoOrario);
		datiEssenziali.setDataFineProrogaMissione(dataFineProrogaMissione);
		datiEssenziali.setDataTrasformazioneMissione(dataTrasformazioneMissione);
		datiEssenziali.setCodiceTrasformazione(codiceTrasformazione);
		datiEssenziali.setDataCessazioneMissione(dataCessazioneMissione);
		datiEssenziali.setCodiceCessazione(codiceCessazione);
		datiEssenziali.setDataFinePeriodoFormativo(dataFinePeriodoFormativo);

		return datiEssenziali;
	}


	private DatiEssenzialiUnilavVO getDatiEssenzialiUnilavDB(Comunicazione comunicazioneDB) {
		DatiEssenzialiUnilavVO datiUnilavDB  =  new DatiEssenzialiUnilavVO();


		if(comunicazioneDB.getLavoratori() != null && comunicazioneDB.getLavoratori().get(0) != null){
			Lavoratore lav = comunicazioneDB.getLavoratori().get(0);

			datiUnilavDB.setCodiceFiscaleAzienda(comunicazioneDB.getDatoreAttuale().getCodiceFiscale()); 
			datiUnilavDB.setCodiceFiscaleLavoratore(lav.getCodiceFiscale()); 							
			datiUnilavDB.setScadenzaTitoloSoggiorno(lav.getDtScadenzaPermessoSogg()); 														
			datiUnilavDB.setNumTitoloSoggiorno(lav.getNumeroDocumento()); 							
			datiUnilavDB.setSussistenzaSistemazioneAlloggiativa(lav.getFlgSistAlloggiativa()); 							
			datiUnilavDB.setImpegnoPagamentoSpesaRimpatrio(lav.getFlgRimborsoRimpatrio()); 
			datiUnilavDB.setDataInizioRapporto(comunicazioneDB.getRapporto().getDtInizioRapporto()); 
			datiUnilavDB.setDataFineRapporto(comunicazioneDB.getRapporto().getDtFineRapporto()); 
			datiUnilavDB.setTipologiaContrattuale(comunicazioneDB.getRapporto().getTipoContratti().getCodTipoContrattoMin());
			String statoCodiceTipologiaContrattuale = UtilConstant.STATO_CODICE_VALIDO;
			if (comunicazioneDB.getRapporto().getTipoContratti().getDtFine() != null 
					&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getTipoContratti().getDtFine())) {
				statoCodiceTipologiaContrattuale = UtilConstant.STATO_CODICE_SCADUTO;
			}

			datiUnilavDB.setStatoCodiceTipologiaContrattuale(statoCodiceTipologiaContrattuale);
			datiUnilavDB.setTipoOrario(comunicazioneDB.getRapporto().getTipoOrario().getCodTipoorarioMin());
			String statoCodiceTipologiaOrario = UtilConstant.STATO_CODICE_VALIDO;
			if (comunicazioneDB.getRapporto().getTipoOrario().getDtFine() != null 
					&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getTipoOrario().getDtFine())) {
				statoCodiceTipologiaOrario = UtilConstant.STATO_CODICE_SCADUTO;
			}
			datiUnilavDB.setStatoCodiceTipoOrario(statoCodiceTipologiaOrario);
			datiUnilavDB.setDataFineProroga(comunicazioneDB.getRapporto().getDtFineProroga());
			datiUnilavDB.setDataTrasformazione(comunicazioneDB.getRapporto().getDtTrasformazione());
			if (comunicazioneDB.getRapporto().getTrasformazionerl() != null ) {
				datiUnilavDB.setCodiceTrasformazione(comunicazioneDB.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin());
				String statoCodiceTrasformazione = UtilConstant.STATO_CODICE_VALIDO;
				if (comunicazioneDB.getRapporto().getTrasformazionerl().getDtFine() != null 
						&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getTrasformazionerl().getDtFine())) {
					statoCodiceTrasformazione = UtilConstant.STATO_CODICE_SCADUTO;
				}
				datiUnilavDB.setStatoCodiceTrasformazione(statoCodiceTrasformazione);
			}
			datiUnilavDB.setCodiceFiscaleDatoreDistaccatario(comunicazioneDB.getRapporto().getDatoreDistaccatario() != null ? comunicazioneDB.getRapporto().getDatoreDistaccatario().getCodiceFiscale() : null); 
			datiUnilavDB.setDataCessazione(comunicazioneDB.getRapporto().getDtCessazione());
			if (comunicazioneDB.getRapporto().getCessazionerl() != null) {
				datiUnilavDB.setCausaCessazione(comunicazioneDB.getRapporto().getCessazionerl().getCodCessazioneMin());
				String statoCodiceCessazione = UtilConstant.STATO_CODICE_VALIDO;
				if (comunicazioneDB.getRapporto().getCessazionerl().getDtFine() != null 
						&& ComonlUtility.confrontaData1MaggioreData2(new Date(), comunicazioneDB.getRapporto().getCessazionerl().getDtFine())) {
					statoCodiceCessazione = UtilConstant.STATO_CODICE_SCADUTO;
				}

				datiUnilavDB.setStatoCodiceCausaCessazione(statoCodiceCessazione);

			}
			datiUnilavDB.setDataTimbroPstale(comunicazioneDB.getDtTimbroPostale());
			datiUnilavDB.setDataFinePeriodoFormativo(comunicazioneDB.getRapporto().getDtFinePeriodoFormativo());
		}
		else if (comunicazione.getLavoratoreCoobbligato() != null ) {

			datiUnilavDB.setCodiceFiscaleLavoratoreCoobbligato(comunicazioneDB.getLavoratoreCoObbligato().getCodiceFiscale()); 							
			datiUnilavDB.setScadenzaTitoloSoggiornoCoobbligato(comunicazioneDB.getLavoratoreCoObbligato().getDtScadenzaPermessoSogg()); 														
			datiUnilavDB.setNumTitoloSoggiornoCoobbligato(comunicazioneDB.getLavoratoreCoObbligato().getNumeroDocumento()); 							
			datiUnilavDB.setSussistenzaSistemazioneAlloggiativaCoobbligato(comunicazioneDB.getLavoratoreCoObbligato().getFlgSistAlloggiativa()); 							
			datiUnilavDB.setImpegnoPagamentoSpesaRimpatrioCoobbligato(comunicazioneDB.getLavoratoreCoObbligato().getFlgRimborsoRimpatrio()); 

		}
		return datiUnilavDB;
	}


	private DatiEssenzialiUnilavVO getDatiEssenzialiUnilav() {

		String codiceFiscaleAzienda  = null;
		String codiceFiscaleLavoratore = null;
		Date scadenzaTitoloSoggiorno = null;														
		String numTitoloSoggiorno = null;							
		String sussistenzaSistemazioneAlloggiativa 	= null;						
		String impegnoPagamentoSpesaRimpatrio = null; 

		String codiceFiscaleLavoratoreCoobbligato = null;							
		Date scadenzaTitoloSoggiornoCoobbligato = null;														
		String numTitoloSoggiornoCoobbligato = null;							
		String sussistenzaSistemazioneAlloggiativaCoobbligato = null; 							
		String impegnoPagamentoSpesaRimpatrioCoobbligato = null;
		Date dataInizioRapporto = null;
		Date dataFineRapporto = null;
		Date dataFinePeriodoFormativo = null;
		String tipologiaContrattuale = null;
		String tipoOrario = null;
		Date dataFineProroga = null;
		Date dataTrasformazione = null;
		String codiceTrasformazione = null;
		String codiceFiscaleDatoreDistaccatario = null;
		Date dataCessazione = null;
		String causaCessazione = null;


		codiceFiscaleAzienda = comunicazione.getDatoreDiLavoro().getCodFiscale();

		codiceFiscaleLavoratore = comunicazione.getLavoratore().getCodFiscale();							
		scadenzaTitoloSoggiorno = comunicazione.getLavoratore().getDataScadenzaPermessoSoggiorno();														
		numTitoloSoggiorno = comunicazione.getLavoratore().getNumeroDocumento();							
		sussistenzaSistemazioneAlloggiativa = comunicazione.getLavoratore().getFlgSussistSistemAllog();							
		impegnoPagamentoSpesaRimpatrio = comunicazione.getLavoratore().getFlgPagamSpesaRimpatrio();



		if(comunicazione.getLavoratoreCoobbligato() != null){

			codiceFiscaleLavoratoreCoobbligato = comunicazione.getLavoratoreCoobbligato().getCodFiscale();							
			scadenzaTitoloSoggiornoCoobbligato = comunicazione.getLavoratoreCoobbligato().getDataScadenzaPermessoSoggiorno();														
			numTitoloSoggiornoCoobbligato = comunicazione.getLavoratoreCoobbligato().getNumeroDocumento();							
			sussistenzaSistemazioneAlloggiativaCoobbligato = comunicazione.getLavoratoreCoobbligato().getFlgSussistSistemAllog();							
			impegnoPagamentoSpesaRimpatrioCoobbligato = comunicazione.getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio();


		}

		dataInizioRapporto = comunicazione.getDatiInizio().getDataInizio();
		dataFineRapporto = comunicazione.getDatiInizio().getDataFine();
		dataFinePeriodoFormativo = comunicazione.getDatiInizio().getDataFinePeriodoFormativo();
		tipologiaContrattuale = comunicazione.getDatiInizio().getTipologiaContrattuale();
		tipoOrario = comunicazione.getDatiInizio().getTipoOrario();

		if(comunicazione.getProroga() != null){
			dataFineProroga = comunicazione.getProroga().getDataFineProroga();
		}

		if(comunicazione.getTrasformazione() != null){

			codiceTrasformazione = comunicazione.getTrasformazione().getCodTrasformazione();
			dataTrasformazione = comunicazione.getTrasformazione().getDataTrasformazione();

			if(comunicazione.getTrasformazione().getDatoreLavoroDistacco() != null){
				codiceFiscaleDatoreDistaccatario = comunicazione.getTrasformazione().getDatoreLavoroDistacco().getCodFiscale();
			}

		}

		if(comunicazione.getCessazione() != null){

			causaCessazione = comunicazione.getCessazione().getCodCausa();
			dataCessazione = comunicazione.getCessazione().getDataCessazione();

		}

		DatiEssenzialiUnilavVO datiEssenziali = new DatiEssenzialiUnilavVO();

		datiEssenziali.setCodiceFiscaleAzienda(codiceFiscaleAzienda); 
		datiEssenziali.setCodiceFiscaleLavoratore(codiceFiscaleLavoratore); 							
		datiEssenziali.setScadenzaTitoloSoggiorno(scadenzaTitoloSoggiorno); 														
		datiEssenziali.setNumTitoloSoggiorno(numTitoloSoggiorno); 							
		datiEssenziali.setSussistenzaSistemazioneAlloggiativa("SI".equalsIgnoreCase(sussistenzaSistemazioneAlloggiativa) ? "S" : null); 							
		datiEssenziali.setImpegnoPagamentoSpesaRimpatrio("SI".equalsIgnoreCase(impegnoPagamentoSpesaRimpatrio) ? "S" : null); 
		datiEssenziali.setCodiceFiscaleLavoratoreCoobbligato(codiceFiscaleLavoratoreCoobbligato); 							
		datiEssenziali.setScadenzaTitoloSoggiornoCoobbligato(scadenzaTitoloSoggiornoCoobbligato); 														
		datiEssenziali.setNumTitoloSoggiornoCoobbligato(numTitoloSoggiornoCoobbligato); 							
		datiEssenziali.setSussistenzaSistemazioneAlloggiativaCoobbligato("SI".equalsIgnoreCase(sussistenzaSistemazioneAlloggiativaCoobbligato) ? "S" : null); 							
		datiEssenziali.setImpegnoPagamentoSpesaRimpatrioCoobbligato("SI".equalsIgnoreCase(impegnoPagamentoSpesaRimpatrioCoobbligato) ? "S" : null); 
		datiEssenziali.setDataInizioRapporto(dataInizioRapporto); 
		datiEssenziali.setDataFineRapporto(dataFineRapporto); 
		datiEssenziali.setTipologiaContrattuale(tipologiaContrattuale); 
		datiEssenziali.setTipoOrario(tipoOrario); 
		datiEssenziali.setDataFineProroga(dataFineProroga);
		datiEssenziali.setDataTrasformazione(dataTrasformazione);
		datiEssenziali.setCodiceTrasformazione(codiceTrasformazione); 
		datiEssenziali.setCodiceFiscaleDatoreDistaccatario(codiceFiscaleDatoreDistaccatario); 
		datiEssenziali.setDataCessazione(dataCessazione);
		datiEssenziali.setCausaCessazione(causaCessazione);
		datiEssenziali.setDataFinePeriodoFormativo(dataFinePeriodoFormativo);

		return datiEssenziali;
	}


	private void checkValiditaRettifica(DatiEssenzialiVO datiEssenzialiDB,	DatiEssenzialiVO datiEssenziali, String codiceComunicazionePrecedente, List<ApiError> elencoErrori, CommonDad commonDad)throws  Exception {


		Date dataRicezione = commonDad.getDataRicezioneUplDocumentiById(idUplDocumenti);
		Date dataDiRiferimento = datiEssenzialiDB.getDataTimbroPstale();


		String numMaxGiorni = commonDad.getParametroCommaxById(ParametriConstants.COMUNICAZIONE_RETTIFICA_FUORI_TERMINE).getValoreParametro(); 

		boolean fuoriTermine = isComunicazioneFuoriTermine(dataDiRiferimento, dataRicezione, Integer.parseInt(numMaxGiorni), false);

		if(fuoriTermine) {

			boolean modificatiDatiEssenziali = !datiEssenziali.equals(datiEssenzialiDB);


			if(modificatiDatiEssenziali) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, ErrorConstants.DATI_INVIO_COMUNICAZIONE_RETTIFICA_SUI_DATI_ESSENZIALI_NON_AMMESSA, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);

			}

		}
	}


	private Comunicazione getDatiComunicazionePrecedente(String codiceComunicazionePrecedente, CommonDad commonDad, ComunicazioneDad comunicazioneDad) throws Exception {

		List<Long> listIdCo = comunicazioneDad.getIdComunicazioneByCodReg(codiceComunicazionePrecedente);
		Comunicazione co = comunicazioneDad.getComunicazioneById(listIdCo.get(0));
		return co;

	}

}


