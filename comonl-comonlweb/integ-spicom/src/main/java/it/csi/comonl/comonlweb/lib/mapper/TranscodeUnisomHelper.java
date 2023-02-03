/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SPICOM submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.mapper;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.DatiCessazioneDTO;
import it.csi.spicom.dto.DatiProrogaDTO;
import it.csi.spicom.dto.DatiTrasformazioneDTO;
import it.csi.spicom.dto.DatiVariazioneDTO;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;
import it.csi.spicom.dto.InizioMissioneDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.MissioneDTO;

public class TranscodeUnisomHelper // extends DecodificheMinisterialiHelper
{

	public static void transcodeComunicazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			VariazioneSomm variazioneSomm) {
		//
		if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getAziUtilizzatrice() != null) {
			transcodeDittaUtilizzatrice(comunicazione.getRapporto().getAziUtilizzatrice(), dto);
		}

		if (comunicazione.getMissione() != null) {
			transcodeMissione(comunicazione, dto);
		}

		if (comunicazione.getRapporto() != null) {
			transcodeInizioRapporto(comunicazione, dto);
		}
		transcodeDatiVariazione(comunicazione, dto, variazioneSomm);
	}

	//
	// // DATI VARIAZIONE PER UNISOMM
	private static void transcodeDatiVariazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			VariazioneSomm variazioneSomm) {

		if (variazioneSomm != null) {

			DatiVariazioneDTO datiVariazioneDto = new DatiVariazioneDTO();
			dto.setDatiVariazione(datiVariazioneDto);

			datiVariazioneDto.setCodiceVariazione(variazioneSomm.getCodTipoVariazioneMin());

			String tipoCom = variazioneSomm.getTipoComunicazione().getId();
			
			Rapporto rapportoVariato = getRapportoVariato(comunicazione);

			if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {

				DatiCessazioneDTO datiCessazioneDto = new DatiCessazioneDTO();
				datiVariazioneDto.setDatiCessazione(datiCessazioneDto);

				if (rapportoVariato.getCessazionerl() != null) {

					datiCessazioneDto.setCodiceCausa(rapportoVariato.getCessazionerl().getCodCessazioneMin());
				}
				datiCessazioneDto.setDataCessazione(rapportoVariato.getDtCessazione());

			} else if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) || 
					   tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)) {
				
				if (rapportoVariato.getDtTrasformazione() != null) {
					DatiTrasformazioneDTO datiTrasformazioneDto = new DatiTrasformazioneDTO();
					datiVariazioneDto.setDatiTrasformazione(datiTrasformazioneDto);
					if (rapportoVariato.getTrasformazionerl().getCodTrasformazionirlMin() != null && !""
							.equalsIgnoreCase(rapportoVariato.getTrasformazionerl().getCodTrasformazionirlMin())) {
						datiTrasformazioneDto.setCodiceTrasformazione(
								rapportoVariato.getTrasformazionerl().getCodTrasformazionirlMin());
					}

					datiTrasformazioneDto.setDataTrasformazione(rapportoVariato.getDtTrasformazione());
				}

			} else if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)) {
				if (rapportoVariato.getDtFineProroga() != null) {
					
						DatiProrogaDTO datiProrogaDto = new DatiProrogaDTO();
						datiVariazioneDto.setDatiProroga(datiProrogaDto);
						datiProrogaDto.setDataFineProroga(rapportoVariato.getDtFineProroga());

				}
			}
		}
	}

	// // DATI RAPPORTO
	private static void transcodeInizioRapporto(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {

		InizioRapportoUniSommDTO rappUSDto = new InizioRapportoUniSommDTO();

		if (comunicazione.getDatoreAttuale() != null) {
			if (comunicazione.getDatoreAttuale().getNumAgenziaSommin() != null) {
				rappUSDto.setNumAgenziaSomministrazione(
						Long.parseLong(comunicazione.getDatoreAttuale().getNumAgenziaSommin()));
			}
		}

		// TIPOLOGIA RAPPORTO
		if (comunicazione.getRapporto().getTipoContratti() != null
				&& comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin())) {
			rappUSDto.setTipologiaContrattuale(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin());
		}

		// ENTE PREVIDENZIALE COMBO
		if (comunicazione.getRapporto().getEntePrevidenziale() != null
				&& comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin() != null
				&& !"".equalsIgnoreCase(
						comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin())) {
			rappUSDto.setEntePrevidenziale(
					comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin());
		}

		// ENTE PREVIDENZIALE INSERITO DALL'UTENTE
		if (comunicazione.getRapporto().getCodiceEntePrev() != null) {
			String codiceEntePrevidenziale = comunicazione.getRapporto().getCodiceEntePrev().equals("") ? null
					: comunicazione.getRapporto().getCodiceEntePrev();
			rappUSDto.setCodEntePrevidenziale(ComonlStringUtils.eliminaCaratteriSpeciali(codiceEntePrevidenziale));
		}

		// DATA INIZIO RAPPORTO
		if (comunicazione.getRapporto().getDtInizioRapporto() != null) {
			rappUSDto.setDataInizio(comunicazione.getRapporto().getDtInizioRapporto());
		}

		// DATA FINE RAPPORTO
		if (comunicazione.getRapporto().getDtFineRapporto() != null) {
			rappUSDto.setDataFine(comunicazione.getRapporto().getDtFineRapporto());
		}
		// start - 72160 - COMONL-1360 - data fine periodo formativo
		if (comunicazione.getRapporto().getDtFinePeriodoFormativo() != null) {
			rappUSDto.setDataFinePeriodoFormativo(comunicazione.getRapporto().getDtFinePeriodoFormativo());
		}
		// end - 72160 - COMONL-1360

		// NUMERO MATRICOLA LAVORATORE
		if (comunicazione.getRapporto().getNumMatricolaLav() != null) {
			Long numMatricola = null;
			try {
				numMatricola = Long.parseLong(comunicazione.getRapporto().getNumMatricolaLav());
			}
			catch (Exception err) {
				err.printStackTrace();
			}
			rappUSDto.setNumMatricolaLavoratore(numMatricola);
		}

		if (comunicazione.getRapporto().getNumIndennitaSomm() != null) {
			rappUSDto.setIndennitaDisponibilita(comunicazione.getRapporto().getNumIndennitaSomm().longValue());
		}

		if (comunicazione.getRapporto().getFlgLavInMobilita() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgLavInMobilita())) {
			rappUSDto.setLavInMobilita(ComonlConstants.FLAG_SI_DESC);
		} else {
			rappUSDto.setLavInMobilita(ComonlConstants.FLAG_N_DESC);
		}

		dto.setDatiInizio(rappUSDto);

	}

	// // DATI MISSIONE
	private static void transcodeMissione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {

		MissioneDTO missione = new MissioneDTO();
		dto.setMissione(missione);

		missione.setDatiInizioMissione(new InizioMissioneDTO());

		// AZIENDA UTILIZZATRICE
		if (comunicazione.getMissione().getAziUtilizzatrice() != null) {
			transcodeDittaUtilizzatrice(comunicazione.getMissione().getAziUtilizzatrice(), dto);
		}

		// PAT INAIL
		if (comunicazione.getMissione().getPatInail() != null) {
			missione.getDatiInizioMissione().setDsPatInail(comunicazione.getMissione().getPatInail().toUpperCase());
		}

		// TIPO ORARIO
		if (comunicazione.getMissione().getTipoOrario() != null
				&& comunicazione.getMissione().getTipoOrario().getCodTipoorarioMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getMissione().getTipoOrario().getCodTipoorarioMin())) {

			missione.getDatiInizioMissione()
					.setTipoOrario(comunicazione.getMissione().getTipoOrario().getCodTipoorarioMin());
		}

		// ORE SETTIMANALI
		if (comunicazione.getMissione().getNumOreSettMed() != null) {
			missione.getDatiInizioMissione()
					.setNumOreSettimanaliMedie(comunicazione.getMissione().getNumOreSettMed().longValue());
		}
		// le agevolazioni non ci sono più
		// if (comBo.getMissione().getListaAgevolazioni() != null) {
		// ArrayList listaAgevolazioni =
		// UtilAgevolazioniMultiple.codMinFromArrayListaAgevolazioni(comBo.getMissione().getListaAgevolazioni());
		// missione.getDatiInizioMissione().setAgevolazioni(listaAgevolazioni);
		// }
		missione.getDatiInizioMissione().setAgevolazioni(null);

		// QUALIFICA PROFESSIONALE
		if (comunicazione.getMissione().getIstatLivello5() != null
				&& comunicazione.getMissione().getIstatLivello5().getCodIstat2001livello5Min() != null
				&& !"".equalsIgnoreCase(comunicazione.getMissione().getIstatLivello5().getCodIstat2001livello5Min())) {

			missione.getDatiInizioMissione().setQualificaProfessionale(
					comunicazione.getMissione().getIstatLivello5().getCodIstat2001livello5Min());
		}

		// CCNL APPLICATO
		if (comunicazione.getMissione().getCcnl() != null
				&& comunicazione.getMissione().getCcnl().getCodCcnlMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getMissione().getCcnl().getCodCcnlMin())) {
			missione.getDatiInizioMissione().setCcnlApplicato(comunicazione.getMissione().getCcnl().getCodCcnlMin());
		}
		// FLG SOMMINISTRAZIONE
		if (comunicazione.getFlgSommin() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getFlgSommin())) {
			missione.getDatiInizioMissione().setFlgSomministrazioneTD(ComonlConstants.FLAG_SI_DESC);

		} else {
			missione.getDatiInizioMissione().setFlgSomministrazioneTD(ComonlConstants.FLAG_N_DESC);

		}

		// LIVELLO INQUADRAMENTO
		if (comunicazione.getMissione().getLivelloRetribuzione() != null
				&& comunicazione.getMissione().getLivelloRetribuzione().getCodLivello() != null
				&& !"".equalsIgnoreCase(comunicazione.getMissione().getLivelloRetribuzione().getCodLivello())) {
			missione.getDatiInizioMissione()
					.setLivelloInquadramento(comunicazione.getMissione().getLivelloRetribuzione().getCodLivello());
		} else if (comunicazione.getMissione().getLivelloInquadramento() != null) {
			missione.getDatiInizioMissione()
					.setLivelloInquadramento(comunicazione.getMissione().getLivelloInquadramento());
		}
		// retribuzione
		if (comunicazione.getMissione().getRetribuzioneCompenso() != null) {
			missione.getDatiInizioMissione()
					.setRetribuzione(comunicazione.getMissione().getRetribuzioneCompenso().longValue());
		}
		// flag lavoro in agricoltura
		if (comunicazione.getMissione().getFlgLavoroAgricoltura() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getMissione().getFlgLavoroAgricoltura())) {
			missione.getDatiInizioMissione().setFlgLavoroAgricoltura(ComonlConstants.FLAG_SI_DESC);

		} else {
			missione.getDatiInizioMissione().setFlgLavoroAgricoltura(ComonlConstants.FLAG_N_DESC);

		}

		// NUMERO GIORNATE LAVORATIVE
		if (comunicazione.getMissione().getGiornateLavPreviste() != null) {
			missione.getDatiInizioMissione()
					.setNumGiornateLavorativePreviste(comunicazione.getMissione().getGiornateLavPreviste());
		}
		// DATA FINE MISSIONE
		if (comunicazione.getMissione().getDtFineMissione() != null) {
			missione.getDatiInizioMissione().setDataFineMissione(comunicazione.getMissione().getDtFineMissione());
		}
		// DATA INIZIO MISSIONE
		if (comunicazione.getMissione().getDtInizioMissione() != null) {
			missione.getDatiInizioMissione().setDataInizioMissione(comunicazione.getMissione().getDtInizioMissione());
		}
		// DESCRIZION ATTIVITA'
		if (ComonlUtility.isNotVoid(comunicazione.getMissione().getDsAttivita())) {
			missione.getDatiInizioMissione().setDescrizioneAttivita(
					ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getMissione().getDsAttivita()));
		} else {
			missione.getDatiInizioMissione().setDescrizioneAttivita(null);
		}
		// DESCRIZIONE ATTIVITA' AGRICOLA
		if (ComonlUtility.isNotVoid(comunicazione.getMissione().getDsAttivitaAgricoltura())) {
			missione.getDatiInizioMissione().setDescrizioneAttivitaAgricoltura(ComonlStringUtils
					.eliminaCaratteriSpeciali(comunicazione.getMissione().getDsAttivitaAgricoltura().toUpperCase()));
		} else {
			missione.getDatiInizioMissione().setDescrizioneAttivitaAgricoltura(null);
		}

		// SILICOSI
		if (comunicazione.getMissione().getFlgRischioSilicAsbe() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getMissione().getFlgRischioSilicAsbe())) {
			missione.getDatiInizioMissione().setFlgRischioSilicosiAsbestosi(ComonlConstants.FLAG_SI_DESC);
		} else {
			missione.getDatiInizioMissione().setFlgRischioSilicosiAsbestosi(ComonlConstants.FLAG_N_DESC);
		}

		if (comunicazione.getMissione().getFlgAssunzioneObbligatoria() != null && ComonlConstants.FLAG_S
				.equalsIgnoreCase(comunicazione.getMissione().getFlgAssunzioneObbligatoria())) {
			missione.getDatiInizioMissione().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_SI_DESC);
		} else {
			missione.getDatiInizioMissione().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_N_DESC);
		}

		if (comunicazione.getMissione().getCategLavAssObbl() != null
				&& comunicazione.getMissione().getCategLavAssObbl().getCodCategLavAssObblMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getMissione().getCategLavAssObbl().getCodCategLavAssObblMin())) {

			missione.getDatiInizioMissione().setCategoriaLavoratoreAssunzioneObbligatoria(
					comunicazione.getMissione().getCategLavAssObbl().getCodCategLavAssObblMin());
		}

		// VOCE TARIFFA 1
		if (comunicazione.getMissione().getDsVoceTariffa1() != null) {

			// ************************** PADDING A 4 caratteri
			String voce = comunicazione.getMissione().getDsVoceTariffa1();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);

			missione.getDatiInizioMissione().setVoceTariffa1(vocePad);
		} else {
			missione.getDatiInizioMissione().setVoceTariffa1(("0000"));
		}

		// VOCE TARIFFA 2
		if (comunicazione.getMissione().getDsVoceTariffa2() != null
				&& !comunicazione.getMissione().getDsVoceTariffa2().equals("")) {

			// ************************** PADDING A 4 caratteri
			String voce = comunicazione.getMissione().getDsVoceTariffa2();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);

			missione.getDatiInizioMissione().setVoceTariffa2(vocePad);

		} else {
			missione.getDatiInizioMissione().setVoceTariffa2(null);

		}

		// VOCE TARIFFA 3
		if (comunicazione.getMissione().getDsVoceTariffa3() != null
				&& !comunicazione.getMissione().getDsVoceTariffa3().equals("")) {

			// ************************** PADDING A 4 caratteri
			String voce = comunicazione.getMissione().getDsVoceTariffa3();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);

			missione.getDatiInizioMissione().setVoceTariffa3(vocePad);
		} else {
			missione.getDatiInizioMissione().setVoceTariffa3(null);
		}
	}

	// ditta utilizzatrice
	private static void transcodeDittaUtilizzatrice(Datore aziUtilizzatrice, ComunicazioneTracciatoUnicoDTO dto) {

		dto.setDittaUtilizzatrice(new DittaUtilizzatriceDTO());

		if (aziUtilizzatrice.getCodiceFiscale() != null) {
			dto.getDittaUtilizzatrice().setCodFiscale(
					ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getCodiceFiscale().toUpperCase()));
		}

		dto.getDittaUtilizzatrice().setDenominazione(
				ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getDsDenominazioneDatore()));
		
		// SEDE LAVORO
		SedeLavoro sedeLavoro = null;
		if(aziUtilizzatrice.getSedeOperativa() != null) {
			sedeLavoro = aziUtilizzatrice.getSedeOperativa();
		} else {
			sedeLavoro = aziUtilizzatrice.getSedeLegale();
		}
		
		dto.getDittaUtilizzatrice().setEmailSedeLavoro(sedeLavoro.getEmail() != null 
						? ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getEmail())	: null);
		
		dto.getDittaUtilizzatrice().setFaxSedeLavoro(sedeLavoro.getFax() != null
						? ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getFax()): null);
		
		dto.getDittaUtilizzatrice().setIndirizzoSedeLavoro(sedeLavoro.getIndirizzo() != null
						? ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getIndirizzo())	: null);
		
		dto.getDittaUtilizzatrice().setTelefonoSedeLavoro(sedeLavoro.getTelefono() != null
						? ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getTelefono()) : null);
		
		if(sedeLavoro.getComune() != null && sedeLavoro.getComune().getCodComuneMin() != null 
				&& !"".equalsIgnoreCase(sedeLavoro.getComune().getCodComuneMin())) {
			dto.getDittaUtilizzatrice().setComuneSedeLavoro(sedeLavoro.getComune().getCodComuneMin());
			
		} else if(sedeLavoro.getStatiEsteri() != null && sedeLavoro.getStatiEsteri().getCodNazioneMin() != null 
				&& !"".equalsIgnoreCase(sedeLavoro.getStatiEsteri().getCodNazioneMin())) {
			
			dto.getDittaUtilizzatrice().setComuneSedeLavoro(sedeLavoro.getStatiEsteri().getCodNazioneMin());	
		}	
		
		// SEDE LEGALE
		if(aziUtilizzatrice.getSedeLegale() != null) {
			dto.getDittaUtilizzatrice().setEmailSedeLegale(aziUtilizzatrice.getSedeLegale().getEmail() != null
							? ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getSedeLegale().getEmail()) : null);
			
			dto.getDittaUtilizzatrice().setFaxSedeLegale(aziUtilizzatrice.getSedeLegale().getFax() != null
							? ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getSedeLegale().getFax())	: null);
	
			dto.getDittaUtilizzatrice().setIndirizzoSedeLegale(aziUtilizzatrice.getSedeLegale().getIndirizzo() != null
							? ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getSedeLegale().getIndirizzo()) : null);
			
			dto.getDittaUtilizzatrice().setTelefonoSedeLegale(aziUtilizzatrice.getSedeLegale().getTelefono() != null
							? ComonlStringUtils.eliminaCaratteriSpeciali(aziUtilizzatrice.getSedeLegale().getTelefono()) : null);
			
			if(aziUtilizzatrice.getSedeLegale().getComune() != null && aziUtilizzatrice.getSedeLegale().getComune().getCodComuneMin() != null
					&& !"".equalsIgnoreCase(aziUtilizzatrice.getSedeLegale().getComune().getCodComuneMin())) {
				
				dto.getDittaUtilizzatrice().setComuneSedeLegale(aziUtilizzatrice.getSedeLegale().getComune().getCodComuneMin());
			
			} else if(aziUtilizzatrice.getSedeLegale().getStatiEsteri() != null && aziUtilizzatrice.getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(aziUtilizzatrice.getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
				
				dto.getDittaUtilizzatrice().setComuneSedeLegale(aziUtilizzatrice.getSedeLegale().getStatiEsteri().getCodNazioneMin());
			}
		}

		// ************************** PADDING A 9 caratteri del codice della
		// comunicazione precedente se non nullo
		String padding = "000000000";

		String numeroContrattoSomministrazione = aziUtilizzatrice.getNumContrSomm().toString();

		if (numeroContrattoSomministrazione != null) {
			String codice = null;
			int lunghezzaCodice = numeroContrattoSomministrazione.length();
			padding = padding.substring(0, padding.length() - lunghezzaCodice);
			codice = padding + numeroContrattoSomministrazione;
			dto.getDittaUtilizzatrice().setNumContrattoSomministrazione(Long.parseLong(codice));
		} else {
			dto.getDittaUtilizzatrice().setNumContrattoSomministrazione(Long.parseLong(padding));
		}
		// **************************************************************************************************************

		if (aziUtilizzatrice.getAtecofin() != null && aziUtilizzatrice.getAtecofin().getCodAtecofinMin() != null
				&& !"".equalsIgnoreCase(aziUtilizzatrice.getAtecofin().getCodAtecofinMin())) {
			dto.getDittaUtilizzatrice().setSettore(aziUtilizzatrice.getAtecofin().getCodAtecofinMin());
		}

		if (aziUtilizzatrice.getFlgPubAmm() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(aziUtilizzatrice.getFlgPubAmm())) {
			dto.getDittaUtilizzatrice().setPubblicaAmministrazione(ComonlConstants.FLAG_SI_DESC);
		} else {
			dto.getDittaUtilizzatrice().setPubblicaAmministrazione(ComonlConstants.FLAG_N_DESC);
		}

		if (aziUtilizzatrice.getFlgUtilEstera() != null
				&& ComonlConstants.FLAG_S.equalsIgnoreCase(aziUtilizzatrice.getFlgUtilEstera())) {
			dto.getDittaUtilizzatrice().setFlgAziUtilEsteraInItalia(ComonlConstants.FLAG_SI_DESC);
		} else {
			dto.getDittaUtilizzatrice().setFlgAziUtilEsteraInItalia(ComonlConstants.FLAG_N_DESC);
		}

		dto.getDittaUtilizzatrice().setDataInizioSomministrazione(aziUtilizzatrice.getDtInizioContrattoSom());
		dto.getDittaUtilizzatrice().setDataFineSomministrazione(aziUtilizzatrice.getDtFineContrattoSom());
	}
	
	private static Rapporto getRapportoVariato(Comunicazione comunicazione) {
		
		if(comunicazione.getMissione() != null) {
			if(comunicazione.getMissione().getCessazionerl() != null ||
					comunicazione.getMissione().getTrasformazionerl() != null ||
					comunicazione.getMissione().getDtFineProroga() != null) {
				return comunicazione.getMissione();
			}
		}
		
		if(comunicazione.getRapporto() != null) {
			if(comunicazione.getRapporto().getCessazionerl() != null ||
					comunicazione.getRapporto().getTrasformazionerl() != null ||
					comunicazione.getRapporto().getDtFineProroga() != null) {
				return comunicazione.getRapporto();
			}
		}		
		return null;
	}
}
