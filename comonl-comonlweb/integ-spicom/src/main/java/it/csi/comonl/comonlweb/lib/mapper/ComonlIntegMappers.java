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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.ControlliSuOggettoComunicazione;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.spicom.dto.AgenziaSomministrazioneDTO;
import it.csi.spicom.dto.CessazioneDTO;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.DatiAggiuntiviAziendaDTO;
import it.csi.spicom.dto.DatiAggiuntiviComunicazioneDTO;
import it.csi.spicom.dto.DatiAggiuntiviLavoratoreDTO;
import it.csi.spicom.dto.DatiAggiuntiviTracciatoDTO;
import it.csi.spicom.dto.DatiApprendistatoDTO;
import it.csi.spicom.dto.DatiInvioDTO;
import it.csi.spicom.dto.DatiTirocinioDTO;
import it.csi.spicom.dto.DatiTutoreDTO;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.DatoreDomesticoDTO;
import it.csi.spicom.dto.DichiarazioniUnidomDTO;
import it.csi.spicom.dto.InizioRapportoUniDomDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.dto.LavoratoreDTO;
import it.csi.spicom.dto.LavoratoreInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.LegaleRappresentante;
import it.csi.spicom.dto.ProrogaDTO;
import it.csi.spicom.dto.TitoloSoggiorno;
import it.csi.spicom.dto.TrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.TrasformazioneDTO;
import it.csi.spicom.dto.VariazioneRagioneSocialeDTO;
import it.csi.spicom.util.TUConstants;

/**
 * Mappers for Spicom integration
 */
public final class ComonlIntegMappers {

	//	private static final LogUtil LOG = new LogUtil(ComonlIntegMappers.class);

	public ComunicazioneTracciatoUnicoDTO toComunicazioneSpicom (Comunicazione comunicazione, Ruolo ruolo,  VariazioneSomm variazioneSomm, String numeroVersioneXML) {
		ComunicazioneTracciatoUnicoDTO dtoSpicom = new ComunicazioneTracciatoUnicoDTO();

		handleDatiInizioRapporto(comunicazione, dtoSpicom, variazioneSomm);

		// ****************************************************************************************************************************
		// Dati Invio
		// ****************************************************************************************************************************
		handleDatiDiInvio(comunicazione, ruolo, dtoSpicom); //FIXME non riesco a trovarei dati corrispondenti a quelli  del comonl vecchio


		// ****************************************************************************************************************************
		// dati aggiuntivi
		// ****************************************************************************************************************************
		DatiAggiuntiviTracciatoDTO datiAggiuntivi = new DatiAggiuntiviTracciatoDTO();
		if(comunicazione.getId() != null){
			datiAggiuntivi.setChiaveEsterna(comunicazione.getId());
		}

		dtoSpicom.setDatiAggiuntivi(datiAggiuntivi);

		if(comunicazione.getDatoreAttuale() != null) {
			dtoSpicom.getDatiAggiuntivi().setDatiAggiuntiviAzienda(new DatiAggiuntiviAziendaDTO());

			if(comunicazione.getDatoreAttuale().getNaturaGiuridica() != null 
					&& comunicazione.getDatoreAttuale().getNaturaGiuridica().getNaturaGiuridica() != null
					&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getNaturaGiuridica().getNaturaGiuridica()) ){

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().setIdSilTNaturaGiuridica(comunicazione.getDatoreAttuale().getNaturaGiuridica().getNaturaGiuridica());
			}

			if(comunicazione.getDatoreAttuale().getFlgAzArtigiana() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgAzArtigiana())) {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().setFlgArtigiano(ComonlConstants.FLAG_S);
			}
			else {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().setFlgArtigiano(ComonlConstants.FLAG_N);
			}

			// per integrazione forte
			if(!ControlliSuOggettoComunicazione.getInstance().checkDiAnnullRettSeguitoUrgenza(comunicazione)) {
				impostaDatiAziendaPerIntegrazioneForte(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda(), comunicazione.getDatoreAttuale(), comunicazione);
			}

		}
		dtoSpicom.getDatiAggiuntivi().setDatiAggiuntiviComunicazione(new DatiAggiuntiviComunicazioneDTO());

		if(comunicazione.getAnnoProtCom() != null 
				&& comunicazione.getDtProtocollo() != null 
				&& comunicazione.getNumProtCom() != null) {
			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setAnnoProtocolloProvinciale(comunicazione.getAnnoProtCom());
			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setDataProtocolloProvinciale(comunicazione.getDtProtocollo());
			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setNumProtocolloProvinciale(String.valueOf(comunicazione.getNumProtCom()));
		}

		//adeguamenti 2016: il codice che viene passato in questo campo e' quello che in passato si trovava in COM_T_NORM_ASS_68
		//mandato a SILP ma non al ministero. 
		//adesso mandiamo nei dati del rapporto il codice ministeriale ma continuiamo a mandare  SILP il codice vecchio che 
		//ora si trova nella tabella COM_T_CATEG_LAV_ASS_OBBL, per la distinzione tra le voci 
		//in cui sono divise le descrizini della L68
		if(comunicazione.getRapporto() != null) {
			if (comunicazione.getRapporto().getCategLavAssObbl() != null
					&& comunicazione.getRapporto().getCategLavAssObbl().getCodNormAss68Min() != null
					&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCategLavAssObbl().getCodNormAss68Min())) {

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setNormAssL68(Long.parseLong(comunicazione.getRapporto().getCategLavAssObbl().getCodNormAss68Min()));

			}

			if(comunicazione.getRapporto().getTipoAttoL68() != null
					&& comunicazione.getRapporto().getTipoAttoL68().getId() != null
					&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoAttoL68().getId()) ){

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setTipoAttoL68(comunicazione.getRapporto().getTipoAttoL68().getId());
			}
		}

		// la TARGA della provincia, obbligatoria per Spicom
		ValidazioneCampi vc = ValidazioneCampi.getInstance();


		if(comunicazione.getProvincia() != null ) {
			if(comunicazione.getProvincia().getDsTarga() != null 
					&& !"".equalsIgnoreCase(comunicazione.getProvincia().getDsTarga())) {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setSiglaProvincia(comunicazione.getProvincia().getDsTarga());
			}
			else { //FIXME è il dato giusto?
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setSiglaProvincia(comunicazione.getProvincia().getCodProvinciaMin());
			}
		}


		// **************************************************************************************************
		// per integrazione forte

		if(!ControlliSuOggettoComunicazione.getInstance().checkDiAnnullRettSeguitoUrgenza(comunicazione)) {

			// solo per com diverse da annullamento / rettifica / a seguito d'urgenza

			if(comunicazione.getRapporto() != null &&	// in caso di var datori, getInizioRapporto e' null
					ComonlUtility.isNotVoid(comunicazione.getRapporto().getIdStoricoRapportoSilp())) {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoIdRappLavSilp(comunicazione.getRapporto().getIdStoricoRapportoSilp());
			}
			else {

				if(comunicazione.getProvinciaPrec() != null 
						&& comunicazione.getProvinciaPrec().getDsTarga() != null 
						&& !"".equalsIgnoreCase(comunicazione.getProvinciaPrec().getDsTarga())) {
					dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoSiglaProv(comunicazione.getProvinciaPrec().getDsTarga());
				}
				else {

					if(comunicazione.getProvincia() != null) {
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoSiglaProv(comunicazione.getProvincia().getDsTarga());
					}
				}
				
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoNumProtProv(String.valueOf(comunicazione.getNumProtComPrec()));

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoAnnoProtProv(comunicazione.getAnnoProtComPrec());

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setRiferimentoCodReg(comunicazione.getCodiceComunRegPrec());
			}
		}

		if(comunicazione.getLavoratori() != null && !comunicazione.getLavoratori().isEmpty()){

			Lavoratore lavoratore = ((Lavoratore)comunicazione.getLavoratori().get(0));

			dtoSpicom.getDatiAggiuntivi().setDatiAggiuntiviLavoratore(new DatiAggiuntiviLavoratoreDTO());
			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setCapResidenza(lavoratore.getCodCapRes());
			if (lavoratore.getComuneRes() != null ) {

				if (lavoratore.getComuneRes().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(lavoratore.getComuneRes().getCodComuneMin())) {
					dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setCodComuneResidenza(lavoratore.getComuneRes().getCodComuneMin());
				}
			} else if (lavoratore.getStatiEsteriRes() != null
					&& lavoratore.getStatiEsteriRes().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriRes().getCodNazioneMin())) {

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setCodComuneResidenza(lavoratore.getStatiEsteriRes().getCodNazioneMin());
			} 


			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setIndirizzoResidenza(ComonlStringUtils.eliminaCaratteriSpeciali(lavoratore.getDsIndirizzoRes()));

			// **************************************************************************************************
			// per integrazione forte
			if(!ControlliSuOggettoComunicazione.getInstance().checkDiAnnullRettSeguitoUrgenza(comunicazione)) {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setIdAnagraficaSilp(lavoratore.getIdLavoratoreSilp() == null ? null : lavoratore.getIdLavoratoreSilp().toString());
			}
			// **************************************************************************************************

		}

		if(comunicazione.getLavoratoreCoObbligato() != null) {
			dtoSpicom.getDatiAggiuntivi().setDatiAggiuntiviLavoratoreCoobbligato(new DatiAggiuntiviLavoratoreDTO());
			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setCapResidenza(comunicazione.getLavoratoreCoObbligato().getCodCapRes());
			if (comunicazione.getLavoratoreCoObbligato().getComuneRes() != null ) {

				if (comunicazione.getLavoratoreCoObbligato().getComuneRes().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getComuneRes().getCodComuneMin())) {
					dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setCodComuneResidenza(comunicazione.getLavoratoreCoObbligato().getComuneRes().getCodComuneMin());
				}
			} else if (comunicazione.getLavoratoreCoObbligato().getStatiEsteriRes() != null
					&& comunicazione.getLavoratoreCoObbligato().getStatiEsteriRes().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getStatiEsteriRes().getCodNazioneMin())) {

				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setCodComuneResidenza(comunicazione.getLavoratoreCoObbligato().getStatiEsteriRes().getCodNazioneMin());
			} 


			dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setIndirizzoResidenza(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getLavoratoreCoObbligato().getDsIndirizzoRes()));


			// **************************************************************************************************
			// per integrazione forte
			if(!ControlliSuOggettoComunicazione.getInstance().checkDiAnnullRettSeguitoUrgenza(comunicazione)) {
				dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setIdAnagraficaSilp(comunicazione.getLavoratoreCoObbligato().getIdLavoratoreSilp().toString());
			}
			// **************************************************************************************************

		}
		// **************************************************************************************************
		// per integrazione forte
		if(!ControlliSuOggettoComunicazione.getInstance().checkDiAnnullRettSeguitoUrgenza(comunicazione)) {

			// questo blocco per memorizzare gli id e la stringa variazione per azi utilizz/prec/distacco
			// oppure mettere tutto in DatoreDiLavoroDTO
			dtoSpicom.getDatiAggiuntivi().setDatiAggiuntiviAziendaAccessoria(new DatiAggiuntiviAziendaDTO());
			

			if (comunicazione.getRapporto() != null) {
				impostaDatiAziendaPerIntegrazioneForte(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAziendaAccessoria(),
						comunicazione.getRapporto().getAziUtilizzatrice(), comunicazione);
				impostaDatiAziendaPerIntegrazioneForte(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAziendaAccessoria(),
						comunicazione.getDatorePrecedente(), comunicazione);
				impostaDatiAziendaPerIntegrazioneForte(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAziendaAccessoria(),
						comunicazione.getRapporto().getTrasformazionerl(), comunicazione);
			}
		}
		// **************************************************************************************************

		// ****************************************************************************************************************************
		// dati tutore 
		// ****************************************************************************************************************************
		handleDatiTutore(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati blocco tirocinio
		// ****************************************************************************************************************************
		handleDatiBloccoTirocinio(comunicazione, dtoSpicom);

		// mittente = Comonls
		dtoSpicom.getDatiAggiuntivi().setIdAppMittente(ComonlConstants.SPICOM_MITTENTE);

		// Contini: viene impostato in spicomClient.ComunicazioneTUValidator.popolaVersioneTracciato
		// modifica, impostiamo il numero di versione per indicare a Spicom che il DTO si riferisce ad una versione specifica
		// (serve per evitare che Spicom non risce a capire che versione sia il DTO e mandi a Silp una versione errata)
		dtoSpicom.getDatiAggiuntivi().setNumVersioneXML(numeroVersioneXML);

		// se impostato a true, richiama la validazione della libreria (di Simone)
		dtoSpicom.getDatiAggiuntivi().setRichiestaValidazioneMinisteriale(true);

		// campo tipo tracciato obbligatorio
		// UL (Uni Lav), US (Uni Somm), UG (Urgenza), VD (Var Datori)
		String tipoTracciato = "";
		if(ControlliSuOggettoComunicazione.isVARDATORI(comunicazione)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID;
		}
		else if(ControlliSuOggettoComunicazione.isUNIURG(comunicazione)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_URGENZA_ID;
		}
		else if(ControlliSuOggettoComunicazione.isUNISOMM(comunicazione)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID;
		}
		else if(ControlliSuOggettoComunicazione.isUNIDOM(comunicazione)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID;
		}
		else {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_UNILAV_ID;
		}
		dtoSpicom.getDatiAggiuntivi().setTipoTracciatoXML(tipoTracciato);

		// **********************************************************************************************
		// per NON mandare il tracciato al ministero in caso di comunicazione proveniente da Commas
		if(comunicazione.getTipoProvenienza() != null 
				&& comunicazione.getTipoProvenienza().getId() != null
				&& comunicazione.getTipoProvenienza().getId().equals(ComonlConstants.TIPO_PROVENIENZA_COMMAS_VER_2)) {
			String [] s = {TUConstants.MINISTERO};
			dtoSpicom.getDatiAggiuntivi().setVctApplicativiNonDestinatari(s);
		}
		// **********************************************************************************************

		// ****************************************************************************************************************************
		// dichiarazioni ulteriori (caso di tracciato UNIDOM) FIXME il tracciato unidom non dovrebbe esistere più se non in import
		// per le vecchie CO. Commento questa parte perchè questi dati non ci sono- E' da rivedere
		// ****************************************************************************************************************************
		if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID)) {
			dtoSpicom.setDichiarazioniUnidom(new DichiarazioniUnidomDTO());

		}

		// **************************************************************************************************

		// ****************************************************************************************************************************
		// dati del datore
		// ****************************************************************************************************************************
		handleDatiDelDatore(comunicazione, dtoSpicom, tipoTracciato);
		// ****************************************************************************************************************************
		// dati del legale rappresentante e del titolo di soggiorno
		// ****************************************************************************************************************************
		handleLegaleRappAndTitoloSoggiorno(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati del lavoratore
		// ****************************************************************************************************************************
		handleDatiDelLavoratore(comunicazione, dtoSpicom, vc);

		// ****************************************************************************************************************************
		// dati del lavoratore co-obbligato
		// ****************************************************************************************************************************
		handleDatiDelLavoratoreCoObbligato(comunicazione, dtoSpicom, vc);

		// ****************************************************************************************************************************
		// dati della cessazione
		// ****************************************************************************************************************************
		handleDatiDellaCessazione(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati della proroga
		// ****************************************************************************************************************************
		handleDatiDellaProroga(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati della trasformazione
		// ****************************************************************************************************************************
		handleDatiDellaTrasformazione(comunicazione, dtoSpicom);


		// ****************************************************************************************************************************
		// dati del trasferimento, distacco
		// ****************************************************************************************************************************
		handleDatiDelTrasferimento(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati del VARDATORI
		// ****************************************************************************************************************************
		handleDatiVardatori(comunicazione, dtoSpicom);


		impostaCapPerStatiEsteri(dtoSpicom, comunicazione);
		impostaCfAziUtilizzatriceStatiEsteri(dtoSpicom, comunicazione);


		return dtoSpicom;

	}
	
	
	private ComunicazioneTracciatoUnicoDTO impostaCfAziUtilizzatriceStatiEsteri(ComunicazioneTracciatoUnicoDTO dto, Comunicazione comBo) {
		
		// azi utilizz
		if(dto.getDittaUtilizzatrice() != null) {
			
			if ("S".equalsIgnoreCase(dto.getDittaUtilizzatrice().getFlgAziUtilEsteraInItalia()) || "SI".equalsIgnoreCase(dto.getDittaUtilizzatrice().getFlgAziUtilEsteraInItalia()))
				dto.getDittaUtilizzatrice().setCodFiscale(null);
		}

		return dto;
	}


	private ComunicazioneTracciatoUnicoDTO impostaCapPerStatiEsteri(ComunicazioneTracciatoUnicoDTO dto, Comunicazione comBo) {
		// datore
		if(dto.getDatoreDiLavoro() != null 
				&& comBo.getDatoreAttuale().getSedeLegale() != null
				&& comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri() !=null
				&& comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
				&&!"".equalsIgnoreCase(comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
			if (ControlliSuOggettoComunicazione.isUNISOMM(comBo)) {
				// se unisomm
				dto.getDatoreDiLavoro().setCapSedeLegale(null);		 
			} else {
				dto.getDatoreDiLavoro().setCapSedeLegale(ComonlConstants.CAP_DEFAULT);
			}
		}

		if(dto.getDatoreDiLavoro() != null) {
			SedeLavoro sedeLavoro = null;
			if(comBo.getDatoreAttuale().getSedeOperativa() != null) {
				sedeLavoro = comBo.getDatoreAttuale().getSedeOperativa();
			} else {
				sedeLavoro = comBo.getDatoreAttuale().getSedeLegale();
			}

			if(sedeLavoro != null
					&& sedeLavoro.getStatiEsteri() !=null
					&& sedeLavoro.getStatiEsteri().getCodNazioneMin() != null
					&&!"".equalsIgnoreCase(sedeLavoro.getStatiEsteri().getCodNazioneMin())) {
				if (ControlliSuOggettoComunicazione.isUNISOMM(comBo)) {
					dto.getDatoreDiLavoro().setCapSedeLavoro(null);
				} else {
					dto.getDatoreDiLavoro().setCapSedeLavoro(ComonlConstants.CAP_DEFAULT);
				}
			}
		}

		// azi utilizz
		if(dto.getDittaUtilizzatrice() != null) {
			Datore dittaUtilizzatrice = null;
			if(comBo.getRapporto().getAziUtilizzatrice() != null) {
				dittaUtilizzatrice = comBo.getRapporto().getAziUtilizzatrice();
			} else if (comBo.getMissione() != null && comBo.getMissione().getAziUtilizzatrice() != null) {
				dittaUtilizzatrice = comBo.getMissione().getAziUtilizzatrice();
			}
			
			SedeLavoro sedeLavoro = null;
			if(dittaUtilizzatrice.getSedeOperativa() != null) {
				sedeLavoro = dittaUtilizzatrice.getSedeOperativa();
			} else {
				sedeLavoro = dittaUtilizzatrice.getSedeLegale();
			}
			
			dto.getDittaUtilizzatrice().setCapSedeLavoro(sedeLavoro == null ? null : sedeLavoro.getCodCap());
			
			dto.getDittaUtilizzatrice().setCapSedeLegale(dittaUtilizzatrice.getSedeLegale() == null ? null :
				dittaUtilizzatrice.getSedeLegale().getCodCap());
		}

		if(dto.getLavoratore() != null) {

			Lavoratore lavoratore = ((Lavoratore)comBo.getLavoratori().get(0));

			// lav
			if(lavoratore.getStatiEsteriDom() != null
					&& lavoratore.getStatiEsteriDom().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriDom().getCodNazioneMin())) {
				dto.getLavoratore().setCapDomicilio(ComonlConstants.CAP_DEFAULT); 
			}
		}

		if(dto.getDatiAggiuntivi() != null && dto.getDatiAggiuntivi().getDatiAggiuntiviLavoratore() != null) {

			Lavoratore lavoratore = ((Lavoratore)comBo.getLavoratori().get(0));

			if(lavoratore.getStatiEsteriRes() != null
					&& lavoratore.getStatiEsteriRes().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriRes().getCodNazioneMin())) {
				dto.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().setCapResidenza(ComonlConstants.CAP_DEFAULT);
			}

		}

		// lav co-obb
		if(dto.getLavoratoreCoobbligato() != null 
				&& comBo.getLavoratoreCoObbligato() != null
				&& comBo.getLavoratoreCoObbligato().getStatiEsteriDom() != null
				&& comBo.getLavoratoreCoObbligato().getStatiEsteriDom().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(comBo.getLavoratoreCoObbligato().getStatiEsteriDom().getCodNazioneMin())) {
			dto.getLavoratoreCoobbligato().setCapDomicilio(ComonlConstants.CAP_DEFAULT); 
		}

		if(dto.getDatiAggiuntivi() != null && dto.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato() != null 
				&& comBo.getLavoratoreCoObbligato() != null
				&& comBo.getLavoratoreCoObbligato().getStatiEsteriRes() != null
				&& comBo.getLavoratoreCoObbligato().getStatiEsteriRes().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(comBo.getLavoratoreCoObbligato().getStatiEsteriRes().getCodNazioneMin())) {
			dto.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().setCapResidenza(ComonlConstants.CAP_DEFAULT);
		}


		// var dat
		if(dto.getTrasferimentoRamoAziendale() != null 
				&& dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale() != null 
				&& comBo.getDatoreAttuale().getSedeLegale() != null
				&& comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri() != null
				&& comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(comBo.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
			dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setCapSedeLegale(ComonlConstants.CAP_DEFAULT);
		}

		if(dto.getTrasferimentoRamoAziendale() != null &&
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente() != null && comBo.getDatorePrecedente().getSedeLegale() != null
				&& comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri() != null
				&& comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
			dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente().setCapSedeLegale(ComonlConstants.CAP_DEFAULT);
		}

		if(dto.getTrasferimentoRamoAziendale() != null) {
			for(int i = 0; i < dto.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate().length; i++) {
				SedeLavoro sede = (SedeLavoro) comBo.getRapLavSedeVD().get(i).getSedeLavoroVD();
				if(sede.getStatiEsteri() != null
						&& sede.getStatiEsteri().getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(sede.getStatiEsteri().getCodNazioneMin())) {
					DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO[] sedi = 
							dto.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate();
					sedi[i].setCapSedeLavoro(null);	
				}
			}
		}

		// var rag soc //FIXME potrebbe essere un errore già dal comonl vecchio perchè prende il datore precedete e lo mette nel datore attuale
		if(dto.getVariazioneRagioneSociale() != null &&
				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale() != null && comBo.getDatorePrecedente() != null 
				&& comBo.getDatorePrecedente().getSedeLegale() != null
				&& comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri() != null
				&& comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(comBo.getDatorePrecedente().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
			dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setCapSedeLegale(ComonlConstants.CAP_DEFAULT);
		}

		if(dto.getTrasformazione() != null && dto.getTrasformazione().getDatoreLavoroDistacco() != null 
				&& comBo.getRapporto() != null
				&& comBo.getRapporto().getDatoreDistaccatario() != null) {
				
			SedeLavoro sedeLavoro = null;
			if(comBo.getRapporto().getDatoreDistaccatario().getSedeOperativa() != null) {
				sedeLavoro = comBo.getRapporto().getDatoreDistaccatario().getSedeOperativa();
			} else {
				sedeLavoro = comBo.getRapporto().getDatoreDistaccatario().getSedeLegale();
			}
				
			if( sedeLavoro != null
				&& sedeLavoro.getStatiEsteri() != null
				&& sedeLavoro.getStatiEsteri().getCodNazioneMin() != null
				&& !"".equalsIgnoreCase(sedeLavoro.getStatiEsteri().getCodNazioneMin())) {
				
				dto.getTrasformazione().getDatoreLavoroDistacco().setCapSedeLavoro(null); 	
			}
		}

		return dto;
	}

	protected void handleDatiVardatori(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {

		if (ControlliSuOggettoComunicazione.isVARDATORI(comunicazione)) {

			// se non e' una variazione della ragione sociale
			if (comunicazione.getDatoreAttuale() != null) {
					
				if (comunicazione.getDatoreAttuale().getDsVariazioneRagSociale() == null
							|| "".equalsIgnoreCase(comunicazione.getDatoreAttuale().getDsVariazioneRagSociale())) {

				if (dto.getTrasferimentoRamoAziendale() == null) {
					dto.setTrasferimentoRamoAziendale(new TrasferimentoRamoAziendaleDTO());
				}

				if (comunicazione.getTipoTrasferimento() != null
						&& comunicazione.getTipoTrasferimento().getCodTipotrasferimentoMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getTipoTrasferimento().getCodTipotrasferimentoMin())) {

					dto.getTrasferimentoRamoAziendale().setCodTrasferimentoDAzienda(
							comunicazione.getTipoTrasferimento().getCodTipotrasferimentoMin());
				}
				if (comunicazione.getDtTrasferimentoVarDatori() != null) {
					dto.getTrasferimentoRamoAziendale().setDataInizio(comunicazione.getDtTrasferimentoVarDatori());
				}
				if (comunicazione.getDtFineAffittoRamo() != null) {
					dto.getTrasferimentoRamoAziendale().setDataFineRamoAffitto(comunicazione.getDtFineAffittoRamo());
				}

				// azienda attuale
				// solo dati sede legale
				if (dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale() == null) {
					dto.getTrasferimentoRamoAziendale().setDatoreLavoroAttuale(new DatoreDiLavoroDTO());
				}
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
						.setCapSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale() != null
								? comunicazione.getDatoreAttuale().getSedeLegale().getCodCap()
								: null);

				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
						.setCodFiscale(comunicazione.getDatoreAttuale().getCodiceFiscale().toUpperCase());

				if (comunicazione.getDatoreAttuale().getSedeLegale().getComune() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setComuneSedeLegale(
							comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin());
				} else if (comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setComuneSedeLegale(
							comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin());
				}

				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setDenominazione(ComonlStringUtils
						.formatDescrDatore(comunicazione.getDatoreAttuale().getDsDenominazioneDatore()));
				
				
				
				if (comunicazione.getDatoreAttuale().getSedeLegale().getEmail() != null) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setEmailSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getEmail().trim());
				}
				
				
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
						.setFaxSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getFax());
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
						.setIndirizzoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getIndirizzo());

				if (comunicazione.getDatoreAttuale().getAtecofin() != null
						&& comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
							.setSettore(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin());
				}

				dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
						.setTelefonoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getTelefono());

				if (comunicazione.getDatoreAttuale().getFlgPubAmm() != null
						&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgPubAmm())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
							.setPubblicaAmministrazione(ComonlConstants.FLAG_SI_DESC);
				} else {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
							.setPubblicaAmministrazione(ComonlConstants.FLAG_N_DESC);
				}

				// azienda precedente
				// solo dati sede legale
				dto.getTrasferimentoRamoAziendale().setDatoreLavoroPrecedente(new DatoreDiLavoroDTO());
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
						.setCapSedeLegale(comunicazione.getDatorePrecedente().getSedeLegale().getCodCap());
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
						.setCodFiscale(comunicazione.getDatorePrecedente().getCodiceFiscale().toUpperCase());

				if (comunicazione.getDatorePrecedente().getSedeLegale().getComune() != null
						&& comunicazione.getDatorePrecedente().getSedeLegale().getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatorePrecedente().getSedeLegale().getComune().getCodComuneMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente().setComuneSedeLegale(
							comunicazione.getDatorePrecedente().getSedeLegale().getComune().getCodComuneMin());
				}

				// Variazione: non c'e' piu' lo stato estero,
				// ma solo per comunicazioni diverse da annullamento
				else if (comunicazione.getDatorePrecedente().getSedeLegale().getStatiEsteri() != null
						&& comunicazione.getDatorePrecedente().getSedeLegale().getStatiEsteri()
								.getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getDatorePrecedente().getSedeLegale().getStatiEsteri()
								.getCodNazioneMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente().setComuneSedeLegale(
							comunicazione.getDatorePrecedente().getSedeLegale().getStatiEsteri().getCodNazioneMin());
				}

				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente().setDenominazione(ComonlStringUtils
						.formatDescrDatore(comunicazione.getDatorePrecedente().getDsDenominazioneDatore()));
				
				
				if (comunicazione.getDatorePrecedente().getSedeLegale().getEmail() != null) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente().setEmailSedeLegale(comunicazione.getDatorePrecedente().getSedeLegale().getEmail().trim());
				}
				
				
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
						.setFaxSedeLegale(comunicazione.getDatorePrecedente().getSedeLegale().getFax());
				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
						.setIndirizzoSedeLegale(comunicazione.getDatorePrecedente().getSedeLegale().getIndirizzo());

				if (comunicazione.getDatorePrecedente().getAtecofin() != null
						&& comunicazione.getDatorePrecedente().getAtecofin().getCodAtecofinMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatorePrecedente().getAtecofin().getCodAtecofinMin())) {
					dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
							.setSettore(comunicazione.getDatorePrecedente().getAtecofin().getCodAtecofinMin());
				}

				dto.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente()
						.setTelefonoSedeLegale(comunicazione.getDatorePrecedente().getSedeLegale().getTelefono());

				// sedi e lav interessati
				if (comunicazione.getRapLavSedeVD() != null && comunicazione.getRapLavSedeVD().size() > 0) {

					Map<Long, DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO> sMap = new HashMap<>();
					Map<Long, Map<Long, LavoratoreInteressatoTrasferimentoRamoAziendaleDTO>> sedeLav = new HashMap<>();

					for (int i = 0; i < comunicazione.getRapLavSedeVD().size(); i++) {
						// SEDE
						SedeLavoro sede = comunicazione.getRapLavSedeVD().get(i).getSedeLavoroVD();
						DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO dtoSede = null;
						Map<Long, LavoratoreInteressatoTrasferimentoRamoAziendaleDTO> lMap = null;

						if (sMap.containsKey(sede.getId())) {
							dtoSede = sMap.get(sede.getId());
							lMap = sedeLav.get(sede.getId());
						} else {
							dtoSede = new DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO();
							lMap = new HashMap<>();
							sMap.put(sede.getId(), dtoSede);

							dtoSede.setCapSedeLavoro(sede.getCodCap());

							if (sede.getComune() != null && sede.getComune().getCodComuneMin() != null
									&& !"".equalsIgnoreCase(sede.getComune().getCodComuneMin())) {

								dtoSede.setComuneSedeLavoro(sede.getComune().getCodComuneMin());
							} else if (sede.getStatiEsteri() != null && sede.getStatiEsteri().getCodNazioneMin() != null
									&& !"".equalsIgnoreCase(sede.getStatiEsteri().getCodNazioneMin())) {

								dtoSede.setComuneSedeLavoro(sede.getStatiEsteri().getCodNazioneMin());
							}
							if (sede.getEmail() != null)
								dtoSede.setEmailSedeLavoro(sede.getEmail().trim());
							
							dtoSede.setFaxSedeLavoro(sede.getFax());
							dtoSede.setIndirizzoSedeLavoro(
									ComonlStringUtils.eliminaCaratteriSpeciali(sede.getIndirizzo()));
							dtoSede.setTelefonoSedeLavoro(sede.getTelefono());

							if (comunicazione.getDatoreAttuale().getAtecofin() != null
									&& comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin() != null
									&& !"".equalsIgnoreCase(
											comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin())) {
								dtoSede.setSettore(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin());
							}

							if (sede.getIdSedeSilp() != null)
								dtoSede.setIdAziSedeOperativaSilp(sede.getIdSedeSilp().toString());
							dtoSede.setVariazione(
									this.getDatiVariazionePerSilp(comunicazione.getDatoreAttuale(), comunicazione));
						}
						// LAVORATORE
						Lavoratore lavoratore = comunicazione.getRapLavSedeVD().get(i).getLavoratoreVD();

						LavoratoreInteressatoTrasferimentoRamoAziendaleDTO dtoLavoratore = new LavoratoreInteressatoTrasferimentoRamoAziendaleDTO();

						lMap.put(lavoratore.getId(), dtoLavoratore);
						sedeLav.put(sede.getId(), lMap);

						dtoLavoratore.setCapDomicilio(lavoratore.getComuneDom().getCodCap());
						dtoLavoratore.setCodFiscale(ComonlStringUtils
								.eliminaCaratteriSpeciali(lavoratore.getCodiceFiscale().toUpperCase()));
						dtoLavoratore.setCognome(ComonlStringUtils.eliminaCaratteriSpeciali(lavoratore.getCognome()));

						if (lavoratore.getComuneDom() != null && lavoratore.getComuneDom().getCodComuneMin() != null
								&& !"".equalsIgnoreCase(lavoratore.getComuneDom().getCodComuneMin())) {

							dtoLavoratore.setComuneDomicilio(lavoratore.getComuneDom().getCodComuneMin());
						} else if (lavoratore.getStatiEsteriDom() != null
								&& lavoratore.getStatiEsteriDom().getCodNazioneMin() != null
								&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriDom().getCodNazioneMin())) {

							dtoLavoratore.setComuneDomicilio(lavoratore.getStatiEsteriDom().getCodNazioneMin());
						}
						dtoLavoratore.setIndirizzoDomicilio(lavoratore.getDsIndirizzoDom());
						dtoLavoratore.setNome(lavoratore.getNome());
						dtoLavoratore.setNumeroDocumento(lavoratore.getNumeroDocumento());
						dtoLavoratore.setSesso(lavoratore.getSesso());

						// RAPPORTO

						dtoLavoratore.setDatiInizio(new InizioRapportoUnilavDTO());
						Rapporto rapporto = comunicazione.getRapLavSedeVD().get(i).getRapportoVD();

						if (rapporto.getCcnl() != null && rapporto.getCcnl().getCodCcnlMin() != null
								&& !"".equalsIgnoreCase(rapporto.getCcnl().getCodCcnlMin())) {
							dtoLavoratore.getDatiInizio().setCcnlApplicato(rapporto.getCcnl().getCodCcnlMin());
						}

						// non ci sono più le agevolazioni verso il minstero
						dtoLavoratore.getDatiInizio().setAgevolazioni(null);

						if (rapporto.getEntePrevidenziale() != null
								&& rapporto.getEntePrevidenziale().getCodEntePrevidenzialeMin() != null
								&& !"".equalsIgnoreCase(rapporto.getEntePrevidenziale().getCodEntePrevidenzialeMin())) {

							dtoLavoratore.getDatiInizio().setCodEntePrevidenziale(
									rapporto.getEntePrevidenziale().getCodEntePrevidenzialeMin());
							dtoLavoratore.getDatiInizio()
									.setEntePrevidenziale(rapporto.getEntePrevidenziale().getCodEntePrevidenzialeMin());
						}

						if (rapporto.getDtFineRapporto() != null) {
							dtoLavoratore.getDatiInizio().setDataFine(rapporto.getDtFineRapporto());
						}
						if (rapporto.getDtInizioRapporto() != null) {
							dtoLavoratore.getDatiInizio().setDataInizio(rapporto.getDtInizioRapporto());
						}
						if (rapporto.getDtFinePeriodoFormativo() != null) {
							dtoLavoratore.getDatiInizio()
									.setDataFinePeriodoFormativo(rapporto.getDtFinePeriodoFormativo());
						}
						if (rapporto.getCfSoggPromotoreTirocinio() != null) {
							dtoLavoratore.getDatiInizio()
									.setCFSoggettoPromotoreTirocinio(rapporto.getCfSoggPromotoreTirocinio());
						}
						if (rapporto.getDtLegge68() != null) {
							dtoLavoratore.getDatiInizio().setDataNullaostaLegge68OConvenzione(rapporto.getDtLegge68());
						}

						if (rapporto.getFlgSocioLavoratore() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgSocioLavoratore())) {
							dtoLavoratore.getDatiInizio().setFlgSocioLavoratore(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoLavoratore.getDatiInizio().setFlgSocioLavoratore(ComonlConstants.FLAG_N_DESC);
						}

						if (rapporto.getFlgLavInMobilita() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgLavInMobilita())) {
							dtoLavoratore.getDatiInizio().setLavInMobilita(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoLavoratore.getDatiInizio().setLavInMobilita(ComonlConstants.FLAG_N_DESC);
						}
						if (rapporto.getFlgLavStagionale() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgLavStagionale())) {
							dtoLavoratore.getDatiInizio().setLavoroStagionale(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoLavoratore.getDatiInizio().setLavoroStagionale(ComonlConstants.FLAG_N_DESC);
						}
						if (rapporto.getFlgAssunzioneObbligatoria() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgAssunzioneObbligatoria())) {
							dtoLavoratore.getDatiInizio().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoLavoratore.getDatiInizio().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_N_DESC);
						}
						// adeguamenti 2016: il codice che viene passato in questo campo e' quello che
						// in passato si trovava in COM_T_NORM_ASS_68
						// mandato a SILP ma non al ministero.
						// adesso mandiamo nei dati del rapporto il codice ministeriale ma continuiamo a
						// mandare SILP il codice vecchio che
						// ora si trova nella tabella COM_T_CATEG_LAV_ASS_OBBL, per la distinzione tra
						// le voci
						// in cui sono divise le descrizini della L68
						if (rapporto.getCategLavAssObbl() != null
								&& rapporto.getCategLavAssObbl().getCodCategLavAssObblMin() != null
								&& !"".equalsIgnoreCase(rapporto.getCategLavAssObbl().getCodCategLavAssObblMin())) {
							dtoLavoratore.getDatiInizio().setCategoriaLavoratoreAssunzioneObbligatoria(
									rapporto.getCategLavAssObbl().getCodCategLavAssObblMin());
						}
						if (rapporto.getFlgLavoroAgricoltura() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(rapporto.getFlgLavoroAgricoltura())) {
							dtoLavoratore.getDatiInizio().setFlgLavoroAgricoltura(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoLavoratore.getDatiInizio().setFlgLavoroAgricoltura(ComonlConstants.FLAG_N_DESC);
						}

						if (rapporto.getGiornateLavPreviste() != null) {
							dtoLavoratore.getDatiInizio()
									.setGiornateLavorativePrevisteAgricoltura(rapporto.getGiornateLavPreviste());
						}
						if (rapporto.getTipoLavorazione() != null && !rapporto.getTipoLavorazione().equals("")) {
							dtoLavoratore.getDatiInizio().setTipoLavorazione(rapporto.getTipoLavorazione());
						}
						if (rapporto.getLivelloRetribuzione() != null
								&& rapporto.getLivelloRetribuzione().getCodLivello() != null
								&& !"".equalsIgnoreCase(rapporto.getLivelloRetribuzione().getCodLivello())) {
							dtoLavoratore.getDatiInizio()
									.setLivelloInquadramento(rapporto.getLivelloRetribuzione().getCodLivello());
						} else if (rapporto.getLivelloInquadramento() != null) {
							dtoLavoratore.getDatiInizio().setLivelloInquadramento(rapporto.getLivelloInquadramento());
						}

						if (ComonlUtility.isNotVoid(rapporto.getNumeroAttoLegge68())) {
							dtoLavoratore.getDatiInizio().setNumeroAttoLegge68(rapporto.getNumeroAttoLegge68());
						}
						if (rapporto.getNumOreSettMed() != null) {
							dtoLavoratore.getDatiInizio()
									.setOreSettimanaliMedie(rapporto.getNumOreSettMed().longValue());
						}
						if (ComonlUtility.isNotVoid(rapporto.getPatInail())) {
							dtoLavoratore.getDatiInizio().setPatInail(rapporto.getPatInail());
						}
						if (rapporto.getIstatLivello5() != null
								&& rapporto.getIstatLivello5().getCodIstat2001livello5Min() != null
								&& !"".equalsIgnoreCase(rapporto.getIstatLivello5().getCodIstat2001livello5Min())) {
							dtoLavoratore.getDatiInizio().setQualificaProfessionaleIstat(
									rapporto.getIstatLivello5().getCodIstat2001livello5Min());
						}

						dtoLavoratore.getDatiInizio().setRetribuzione(rapporto.getRetribuzioneCompenso().longValue());

						if (rapporto.getTipoContratti() != null
								&& rapporto.getTipoContratti().getCodTipoContrattoMin() != null
								&& !"".equalsIgnoreCase(rapporto.getTipoContratti().getCodTipoContrattoMin())) {

							dtoLavoratore.getDatiInizio()
									.setTipologiaContrattuale(rapporto.getTipoContratti().getCodTipoContrattoMin());
						}

						// TIPO ORARIO
						if (rapporto.getTipoOrario() != null && rapporto.getTipoOrario().getCodTipoorarioMin() != null
								&& !"".equalsIgnoreCase(rapporto.getTipoOrario().getCodTipoorarioMin())) {
							dtoLavoratore.getDatiInizio().setTipoOrario(rapporto.getTipoOrario().getCodTipoorarioMin());
						}
					}

					sMap.forEach((k, v) -> {
						if (sedeLav.containsKey(k)) {
							v.setVctElencoLavoratoriInteressati(sedeLav.get(k).values()
									.toArray(new LavoratoreInteressatoTrasferimentoRamoAziendaleDTO[0]));
						}
					});

					dto.getTrasferimentoRamoAziendale().setVctElencoSediDiLavoroInteressate(
							sMap.values().toArray(new DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO[0]));

				} 
				
				
				else { // altrimenti, e' una variazione di ragione sociale
					if (dto.getVariazioneRagioneSociale() == null) {
						dto.setVariazioneRagioneSociale(new VariazioneRagioneSocialeDTO());
					}

					if (dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale() == null) {
						dto.getVariazioneRagioneSociale().setDatoreLavoroAttuale(new DatoreDiLavoroDTO());
					}

					if (comunicazione.getDtTrasferimentoVarDatori() != null) {
						dto.getVariazioneRagioneSociale()
								.setDataVariazione(comunicazione.getDtTrasferimentoVarDatori());
					}

					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setCapSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap());
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setCodFiscale(comunicazione.getDatoreAttuale().getCodiceFiscale().toUpperCase());

					if (comunicazione.getDatoreAttuale().getSedeLegale().getComune() != null
							&& comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin() != null
							&& !"".equalsIgnoreCase(
									comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin())) {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setComuneSedeLegale(
								comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin());
					} else if (comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri() != null
							&& comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri()
									.getCodNazioneMin() != null
							&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri()
									.getCodNazioneMin())) {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setComuneSedeLegale(
								comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin());
					}

					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setDenominazione(ComonlStringUtils
							.formatDescrDatore(comunicazione.getDatoreAttuale().getDsDenominazioneDatore()));
					
					if (comunicazione.getDatoreAttuale().getSedeLegale().getEmail() != null) {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setEmailSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getEmail().trim());
					}
					
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setFaxSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getFax());
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setIndirizzoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getIndirizzo());

					if (comunicazione.getDatoreAttuale().getAtecofin() != null
							&& comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin() != null
							&& !"".equalsIgnoreCase(
									comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin())) {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.setSettore(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin());
					}

					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setTelefonoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getTelefono());

					if (comunicazione.getDatoreAttuale() != null) {
						dto.getVariazioneRagioneSociale().setDenominazionePrecedente(ComonlStringUtils
								.formatDescrDatore(comunicazione.getDatoreAttuale().getDsVariazioneRagSociale()));
					}

					if (comunicazione.getDatoreAttuale().getFlgPubAmm() != null && ComonlConstants.FLAG_S
							.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgPubAmm())) {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.setPubblicaAmministrazione(ComonlConstants.FLAG_SI_DESC);
					} else {
						dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.setPubblicaAmministrazione(ComonlConstants.FLAG_N_DESC);
					}
				}
			}else {
				// Daniela: dati per vd variazione sociale 
				if (dto.getVariazioneRagioneSociale() == null) {
					dto.setVariazioneRagioneSociale(new VariazioneRagioneSocialeDTO());
				}

				if (dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale() == null) {
					dto.getVariazioneRagioneSociale().setDatoreLavoroAttuale(new DatoreDiLavoroDTO());
				}

				if (comunicazione.getDtTrasferimentoVarDatori() != null) {
					dto.getVariazioneRagioneSociale()
							.setDataVariazione(comunicazione.getDtTrasferimentoVarDatori());
				}

				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.setCapSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap());
				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.setCodFiscale(comunicazione.getDatoreAttuale().getCodiceFiscale().toUpperCase());

				if (comunicazione.getDatoreAttuale().getSedeLegale().getComune() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin())) {
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setComuneSedeLegale(
							comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin());
				} else if (comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri()
								.getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri()
								.getCodNazioneMin())) {
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setComuneSedeLegale(
							comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin());
				}

				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setDenominazione(ComonlStringUtils
						.formatDescrDatore(comunicazione.getDatoreAttuale().getDsDenominazioneDatore()));
				
				if (comunicazione.getDatoreAttuale().getSedeLegale().getEmail() != null)
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setEmailSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getEmail().trim());
				
				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.setFaxSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getFax());
				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.setIndirizzoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getIndirizzo());

				if (comunicazione.getDatoreAttuale().getAtecofin() != null
						&& comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin() != null
						&& !"".equalsIgnoreCase(
								comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin())) {
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setSettore(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin());
				}

				dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.setTelefonoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getTelefono());

				if (comunicazione.getDatoreAttuale() != null) {
					dto.getVariazioneRagioneSociale().setDenominazionePrecedente(ComonlStringUtils
							.formatDescrDatore(comunicazione.getDatoreAttuale().getDsVariazioneRagSociale()));
				}

				if (comunicazione.getDatoreAttuale().getFlgPubAmm() != null && ComonlConstants.FLAG_S
						.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgPubAmm())) {
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setPubblicaAmministrazione(ComonlConstants.FLAG_SI_DESC);
				} else {
					dto.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.setPubblicaAmministrazione(ComonlConstants.FLAG_N_DESC);
				}
				
				
			}
		}
		}
	}

	protected void handleDatiDelTrasferimento(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {
		if(ControlliSuOggettoComunicazione.isTRASFERIMENTO(comunicazione)) {
			if(ControlliSuOggettoComunicazione.getInstance().checkComunicazioneMiss_SommEMiss(comunicazione)){  
			}
			else {
				dto.setTrasformazione(new TrasformazioneDTO());

				// Settaggio del codice ministeriale del tipo trasferimento
				// ----------------------------------------------------------------------------------
				if (comunicazione.getRapporto().getTrasformazionerl() != null
						&& comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin())) {
					dto.getTrasformazione().setCodTrasformazione(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin());
				}
				// ---------------------------------------------------------------------------------

				dto.getTrasformazione().setDataTrasformazione(
						comunicazione.getRapporto().getDtTrasformazione());
					
				// TODO  JAGOOOOOOOOOOo

				if(comunicazione.getRapporto().getFlgDistaccoParziale() != null &&
						ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgDistaccoParziale())) {
					dto.getTrasformazione().setFlgDistaccoParziale(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					dto.getTrasformazione().setFlgDistaccoParziale(ComonlConstants.FLAG_N_DESC);
				}

				if(comunicazione.getRapporto().getFlgDistaccoSuAziEstera() != null &&
						ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgDistaccoSuAziEstera())) {
					dto.getTrasformazione().setFlgDistaccoAziendaEstera(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					dto.getTrasformazione().setFlgDistaccoAziendaEstera(ComonlConstants.FLAG_N_DESC);
				}

				if(ComonlConstants.COD_MIN_TIPO_CO_DISTACCO.equalsIgnoreCase(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin()) ) {

					dto.getTrasformazione().setDatoreLavoroDistacco(new DatoreDiLavoroDTO());
					
					if(comunicazione.getRapporto().getDatoreDistaccatario() != null) {

						if(comunicazione.getRapporto().getDatoreDistaccatario().getCodiceFiscale() != null
								&& !"".equalsIgnoreCase(comunicazione.getRapporto().getDatoreDistaccatario().getCodiceFiscale()) ){
							dto.getTrasformazione().getDatoreLavoroDistacco().setCodFiscale(comunicazione.getRapporto().getDatoreDistaccatario().getCodiceFiscale().toUpperCase());
						}
	
						if(comunicazione.getRapporto().getDatoreDistaccatario().getDsDenominazioneDatore() != null
								&& !"".equalsIgnoreCase(comunicazione.getRapporto().getDatoreDistaccatario().getDsDenominazioneDatore()) ){
							dto.getTrasformazione().getDatoreLavoroDistacco().setDenominazione(ComonlStringUtils.formatDescrDatore(
									comunicazione.getRapporto().getDatoreDistaccatario().getDsDenominazioneDatore()));
						}
						if(comunicazione.getRapporto().getDatoreDistaccatario().getAtecofin() != null
								&& comunicazione.getRapporto().getDatoreDistaccatario().getAtecofin().getCodAtecofinMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getRapporto().getDatoreDistaccatario().getAtecofin().getCodAtecofinMin()) ) {
							dto.getTrasformazione().getDatoreLavoroDistacco().setSettore(comunicazione.getRapporto().getDatoreDistaccatario().getAtecofin().getCodAtecofinMin());
						}	
	
						if(comunicazione.getRapporto().getDatoreDistaccatario().getPatInail() != null ) {
							dto.getTrasformazione().setPatInail(comunicazione.getRapporto().getDatoreDistaccatario().getPatInail());
						}
	
						SedeLavoro sedeDistaccatario = null;
						if(comunicazione.getRapporto().getDatoreDistaccatario().getSedeOperativa() != null) {
							sedeDistaccatario = comunicazione.getRapporto().getDatoreDistaccatario().getSedeOperativa();
						} else {
							sedeDistaccatario = comunicazione.getRapporto().getDatoreDistaccatario().getSedeLegale();
						}
						
						if(sedeDistaccatario != null) {
	
							if (sedeDistaccatario.getComune() != null
									&& sedeDistaccatario.getComune().getCodComuneMin() != null
									&& !"".equalsIgnoreCase(sedeDistaccatario.getComune().getCodComuneMin())) {
								dto.getTrasformazione().getDatoreLavoroDistacco().setComuneSedeLavoro(sedeDistaccatario.getComune().getCodComuneMin());
	
							} else if(sedeDistaccatario.getStatiEsteri() != null
									&& sedeDistaccatario.getStatiEsteri().getCodNazioneMin() != null
									&& !"".equalsIgnoreCase(sedeDistaccatario.getStatiEsteri().getCodNazioneMin())) {
	
								dto.getTrasformazione().getDatoreLavoroDistacco().setComuneSedeLavoro(sedeDistaccatario.getStatiEsteri().getCodNazioneMin());
							}
	
							dto.getTrasformazione().getDatoreLavoroDistacco().setCapSedeLavoro(
									ComonlUtility.isNotVoid(sedeDistaccatario.getCodCap()) ?
											sedeDistaccatario.getCodCap() : null);
							dto.getTrasformazione().getDatoreLavoroDistacco().setIndirizzoSedeLavoro(sedeDistaccatario.getIndirizzo());
							dto.getTrasformazione().getDatoreLavoroDistacco().setTelefonoSedeLavoro(
									ComonlUtility.isNotVoid(sedeDistaccatario.getTelefono()) ?
											sedeDistaccatario.getTelefono() : null);
							dto.getTrasformazione().getDatoreLavoroDistacco().setFaxSedeLavoro(
									ComonlUtility.isNotVoid(sedeDistaccatario.getFax()) ?
											sedeDistaccatario.getFax(): null);
							
							
							if (sedeDistaccatario.getEmail() != null)
								dto.getTrasformazione().getDatoreLavoroDistacco().setEmailSedeLavoro(ComonlUtility.isNotVoid(sedeDistaccatario.getEmail()) ? sedeDistaccatario.getEmail().trim(): null);
						} 
					}
				}
				else {

					if (comunicazione.getRapporto().getSedeLavoroPrecedente() != null) {

						if (comunicazione.getRapporto().getSedeLavoroPrecedente().getComune() != null
								&& comunicazione.getRapporto().getSedeLavoroPrecedente().getComune().getCodComuneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getRapporto().getSedeLavoroPrecedente().getComune().getCodComuneMin())) {
							dto.getTrasformazione().setComuneSedeLavoroPrecedente(comunicazione.getRapporto().getSedeLavoroPrecedente().getComune().getCodComuneMin());

						} else if (comunicazione.getRapporto().getSedeLavoroPrecedente().getStatiEsteri() != null
								&& comunicazione.getRapporto().getSedeLavoroPrecedente().getStatiEsteri().getCodNazioneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getRapporto().getSedeLavoroPrecedente().getStatiEsteri().getCodNazioneMin()) ) {
							dto.getTrasformazione().setComuneSedeLavoroPrecedente(comunicazione.getRapporto().getSedeLavoroPrecedente().getStatiEsteri().getCodNazioneMin());
						}
						dto.getTrasformazione().setIndirizzoSedeLavoroPrecedente(comunicazione.getRapporto().getSedeLavoroPrecedente().getIndirizzo());
					}
				}
			}
		}
	}

	protected void handleDatiDellaTrasformazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {
		if(comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtTrasformazione() != null
				&& ControlliSuOggettoComunicazione.isTRASFORMAZIONE(comunicazione)) {

			if(ControlliSuOggettoComunicazione.getInstance().checkComunicazioneMiss_SommEMiss(comunicazione)){  
			}
			else {
				dto.setTrasformazione(new TrasformazioneDTO());

				// Settaggio del codice ministeriale del tipo trasformazione
				// ----------------------------------------------------------------------------------
				if (comunicazione.getRapporto().getTrasformazionerl() != null
						&& comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin())) {
					dto.getTrasformazione().setCodTrasformazione(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin());
				}

				dto.getTrasformazione().setDataTrasformazione(comunicazione.getRapporto().getDtTrasformazione());
			}
		}
	}	

	protected void handleDatiDellaProroga(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {
		if(comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtFineProroga() != null) {

			if(ControlliSuOggettoComunicazione.getInstance().checkComunicazioneMiss_SommEMiss(comunicazione)
					|| ControlliSuOggettoComunicazione.getInstance().checkComunicazioneSomm_SommEMiss(comunicazione)){  
			}

			// semplice proroga
			else {
				dto.setProroga(new ProrogaDTO());
				dto.getProroga().setDataFineProroga(comunicazione.getRapporto().getDtFineProroga());  
			}
		}
	}


	protected void handleDatiDellaCessazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {
		if(comunicazione.getRapporto() != null && comunicazione.getRapporto().getDtCessazione() != null) {

			if(ControlliSuOggettoComunicazione.getInstance().checkComunicazioneMiss_SommEMiss(comunicazione)) {

			}

			// semplice cessazione
			else if(comunicazione.getTipoSomministrazione() == null ||
					!ComonlConstants.TIPO_SOMMINISTRAZIONE.equals(comunicazione.getTipoSomministrazione().getId())) {
				dto.setCessazione(new CessazioneDTO());

				if(comunicazione.getRapporto() != null 
						&& comunicazione.getRapporto().getCessazionerl() != null
						&& comunicazione.getRapporto().getCessazionerl().getCodCessazioneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCessazionerl().getCodCessazioneMin())) {
					dto.getCessazione().setCodCausa(comunicazione.getRapporto().getCessazionerl().getCodCessazioneMin());
				}
				dto.getCessazione().setDataCessazione(comunicazione.getRapporto().getDtCessazione());
			}

		}
	}
	protected void handleDatiDelLavoratoreCoObbligato(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto, ValidazioneCampi vc) {

		if(comunicazione.getLavoratoreCoObbligato() != null) {
			dto.setLavoratoreCoobbligato(new LavoratoreDTO());

			dto.getLavoratoreCoobbligato().setCapDomicilio(comunicazione.getLavoratoreCoObbligato().getCodCapDom());

			if(comunicazione.getLavoratoreCoObbligato().getCittadinanza() != null
					&& comunicazione.getLavoratoreCoObbligato().getCittadinanza().getCodCittadinanzaMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getCittadinanza().getCodCittadinanzaMin())) {
				dto.getLavoratoreCoobbligato().setCittadinanza(comunicazione.getLavoratoreCoObbligato().getCittadinanza().getCodCittadinanzaMin());
			}

			dto.getLavoratoreCoobbligato().setCodFiscale(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getLavoratoreCoObbligato().getCodiceFiscale().toUpperCase()));
			dto.getLavoratoreCoobbligato().setCognome(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getLavoratoreCoObbligato().getCognome()));

			if(comunicazione.getLavoratoreCoObbligato().getComuneDom() != null
					&& comunicazione.getLavoratoreCoObbligato().getComuneDom().getCodComuneMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getComuneDom().getCodComuneMin())) {
				dto.getLavoratoreCoobbligato().setComuneDomicilio(comunicazione.getLavoratoreCoObbligato().getComuneDom().getCodComuneMin());
			}

			if(comunicazione.getLavoratoreCoObbligato().getComuneNasc() != null 
					&& comunicazione.getLavoratoreCoObbligato().getComuneNasc().getCodComuneMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getComuneNasc().getCodComuneMin())) {

				dto.getLavoratoreCoobbligato().setComuneOStatoEsteroNascita(comunicazione.getLavoratoreCoObbligato().getComuneNasc().getCodComuneMin());
			}
			else if (comunicazione.getLavoratoreCoObbligato().getStatiEsteriNasc() != null
					&& comunicazione.getLavoratoreCoObbligato().getStatiEsteriNasc().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getStatiEsteriNasc().getCodNazioneMin())) {
				dto.getLavoratoreCoobbligato().setComuneOStatoEsteroNascita(comunicazione.getLavoratoreCoObbligato().getStatiEsteriNasc().getCodNazioneMin());
			}

			if(comunicazione.getLavoratoreCoObbligato().getDtNascita() != null) {
				dto.getLavoratoreCoobbligato().setDataNascita(comunicazione.getLavoratoreCoObbligato().getDtNascita());
			}

			if(comunicazione.getLavoratoreCoObbligato().getDtScadenzaPermessoSogg() != null) {
				dto.getLavoratoreCoobbligato().setDataScadenzaPermessoSoggiorno(comunicazione.getLavoratoreCoObbligato().getDtScadenzaPermessoSogg());
			}

			if(comunicazione.getLavoratoreCoObbligato().getDsIndirizzoDom() != null) {
				dto.getLavoratoreCoobbligato().setIndirizzoDomicilio(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getLavoratoreCoObbligato().getDsIndirizzoDom()));
			}

			if(comunicazione.getLavoratoreCoObbligato().getLivelloStudio() != null
					&& comunicazione.getLavoratoreCoObbligato().getLivelloStudio().getCodiceLivelloMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getLivelloStudio().getCodiceLivelloMin())) {
				dto.getLavoratoreCoobbligato().setLivelloIstruzione(comunicazione.getLavoratoreCoObbligato().getLivelloStudio().getCodiceLivelloMin());
			}

			if(comunicazione.getLavoratoreCoObbligato().getMotivoPermesso() != null
					&& comunicazione.getLavoratoreCoObbligato().getMotivoPermesso().getCodMotivoMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getMotivoPermesso().getCodMotivoMin())) {
				dto.getLavoratoreCoobbligato().setMotivoPermesso(comunicazione.getLavoratoreCoObbligato().getMotivoPermesso().getCodMotivoMin());
			}

			dto.getLavoratoreCoobbligato().setNome(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getLavoratoreCoObbligato().getNome()));

			if(comunicazione.getLavoratoreCoObbligato().getNumeroDocumento() != null) {
				dto.getLavoratoreCoobbligato().setNumeroDocumento(
						comunicazione.getLavoratoreCoObbligato().getNumeroDocumento().equals("") ? null :
							comunicazione.getLavoratoreCoObbligato().getNumeroDocumento());
			}
			dto.getLavoratoreCoobbligato().setSesso(comunicazione.getLavoratoreCoObbligato().getSesso());

			if(comunicazione.getLavoratoreCoObbligato().getStatusStraniero() != null
					&& comunicazione.getLavoratoreCoObbligato().getStatusStraniero().getCodStatusMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getStatusStraniero().getCodStatusMin())) {
				dto.getLavoratoreCoobbligato().setTipoDocumento(comunicazione.getLavoratoreCoObbligato().getStatusStraniero().getCodStatusMin());
			}

			if(comunicazione.getLavoratoreCoObbligato().getFlgRimborsoRimpatrio() != null &&
					ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getFlgRimborsoRimpatrio())) {
				dto.getLavoratoreCoobbligato().setFlgPagamSpesaRimpatrio(ComonlConstants.FLAG_SI_DESC);
			}
			if(comunicazione.getLavoratoreCoObbligato().getFlgSistAlloggiativa() != null &&
					ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getFlgSistAlloggiativa())) {
				dto.getLavoratoreCoobbligato().setFlgSussistSistemAllog(ComonlConstants.FLAG_SI_DESC);
			}

			if (comunicazione.getLavoratoreCoObbligato().getQuestura() != null
					&& comunicazione.getLavoratoreCoObbligato().getQuestura().getCodQuesturaMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getLavoratoreCoObbligato().getQuestura().getCodQuesturaMin())) {
				dto.getLavoratoreCoobbligato().setQuesturaRilascioTitoloSoggiorno(comunicazione.getLavoratoreCoObbligato().getQuestura().getCodQuesturaMin());
			}
		}
	}

	protected void handleDatiDelLavoratore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto, ValidazioneCampi vc) {
		if(comunicazione.getLavoratori() != null && !comunicazione.getLavoratori().isEmpty()){

			Lavoratore lavoratore = ((Lavoratore)comunicazione.getLavoratori().get(0));

			dto.setLavoratore(new LavoratoreDTO());

			dto.getLavoratore().setCapDomicilio(lavoratore.getCodCapDom());

			if(lavoratore.getCittadinanza() != null
					&& lavoratore.getCittadinanza().getCodCittadinanzaMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getCittadinanza().getCodCittadinanzaMin()))  {

				dto.getLavoratore().setCittadinanza(lavoratore.getCittadinanza().getCodCittadinanzaMin());
			}

			dto.getLavoratore().setCodFiscale(lavoratore.getCodiceFiscale().toUpperCase());
			dto.getLavoratore().setCognome(lavoratore.getCognome());

			if(lavoratore.getComuneDom() !=  null
					&& lavoratore.getComuneDom().getCodComuneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getComuneDom().getCodComuneMin())) {
				dto.getLavoratore().setComuneDomicilio(lavoratore.getComuneDom().getCodComuneMin());
			} else if (lavoratore.getStatiEsteriDom() != null
					&& lavoratore.getStatiEsteriDom().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriDom().getCodNazioneMin())) {
				dto.getLavoratore().setComuneDomicilio(lavoratore.getStatiEsteriDom().getCodNazioneMin());
			}


			if(lavoratore.getComuneNasc() !=  null
					&& lavoratore.getComuneNasc().getCodComuneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getComuneNasc().getCodComuneMin())) {

				dto.getLavoratore().setComuneOStatoEsteroNascita(lavoratore.getComuneNasc().getCodComuneMin());
			}
			else if (lavoratore.getStatiEsteriNasc() != null
					&& lavoratore.getStatiEsteriNasc().getCodNazioneMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatiEsteriNasc().getCodNazioneMin())) {
				dto.getLavoratore().setComuneOStatoEsteroNascita(lavoratore.getStatiEsteriNasc().getCodNazioneMin());
			}

			if(lavoratore.getDtNascita() != null) {
				dto.getLavoratore().setDataNascita(lavoratore.getDtNascita());
			}

			if(lavoratore.getDtScadenzaPermessoSogg() != null) {
				dto.getLavoratore().setDataScadenzaPermessoSoggiorno(lavoratore.getDtScadenzaPermessoSogg());
			}

			if(lavoratore.getDsIndirizzoDom() != null) {
				dto.getLavoratore().setIndirizzoDomicilio(lavoratore.getDsIndirizzoDom());
			}

			if(lavoratore.getLivelloStudio() != null
					&& lavoratore.getLivelloStudio().getCodiceLivelloMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getLivelloStudio().getCodiceLivelloMin())) {
				dto.getLavoratore().setLivelloIstruzione(lavoratore.getLivelloStudio().getCodiceLivelloMin());
			}

			if(lavoratore.getMotivoPermesso() != null
					&& lavoratore.getMotivoPermesso().getCodMotivoMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getMotivoPermesso().getCodMotivoMin())) {
				dto.getLavoratore().setMotivoPermesso(lavoratore.getMotivoPermesso().getCodMotivoMin());
			}

			// Riccardo Leccese 13/07/2007
			// Aggiunti questi valori perche' necessari e richiesti da SPICOM
			if(lavoratore.getCodCapDom() != null) {
				dto.getLavoratore().setCapDomicilio(lavoratore.getCodCapDom());
			}
			if(lavoratore.getDsIndirizzoDom() != null) {
				dto.getLavoratore().setIndirizzoDomicilio(lavoratore.getDsIndirizzoDom());
			}

			dto.getLavoratore().setNome(lavoratore.getNome());

			if(lavoratore.getNumeroDocumento() != null) {
				dto.getLavoratore().setNumeroDocumento(lavoratore.getNumeroDocumento().equals("") ? null :
					lavoratore.getNumeroDocumento());
			}

			dto.getLavoratore().setSesso(lavoratore.getSesso());

			if(lavoratore.getStatusStraniero() != null
					&& lavoratore.getStatusStraniero().getCodStatusMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getStatusStraniero().getCodStatusMin())) {
				dto.getLavoratore().setTipoDocumento(lavoratore.getStatusStraniero().getCodStatusMin());
			}

			if (lavoratore.getQuestura() != null
					&& lavoratore.getQuestura().getCodQuesturaMin() != null
					&& !"".equalsIgnoreCase(lavoratore.getQuestura().getCodQuesturaMin()) ) {
				dto.getLavoratore().setQuesturaRilascioTitoloSoggiorno(lavoratore.getQuestura().getCodQuesturaMin());
			}

			if(lavoratore.getFlgRimborsoRimpatrio() != null 
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(lavoratore.getFlgRimborsoRimpatrio())) {
				dto.getLavoratore().setFlgPagamSpesaRimpatrio(ComonlConstants.FLAG_SI_DESC);
			}
			if(lavoratore.getFlgSistAlloggiativa() != null
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(lavoratore.getFlgSistAlloggiativa()) ){
				dto.getLavoratore().setFlgSussistSistemAllog(ComonlConstants.FLAG_SI_DESC);
			}
		}
	}


	protected void handleLegaleRappAndTitoloSoggiorno(Comunicazione comunicazione,
			ComunicazioneTracciatoUnicoDTO dtoSpicom) {
		//legale rapp e dati titolo di soggiorno per datore
		if (!ControlliSuOggettoComunicazione.isVARDATORI(comunicazione) ) {

			if(comunicazione.getDatoreAttuale() != null) {

				if (dtoSpicom.getDatoreDiLavoro() == null ) {
					dtoSpicom.setDatoreDiLavoro(new DatoreDiLavoroDTO());
				}

				if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
						&& ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome())) {

					//legale rapp
					dtoSpicom.getDatoreDiLavoro().setLegaleRappresentante(new LegaleRappresentante());

					//cittadinanza
					if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin() != null
							&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin())) {

						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setCittadinanza(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin());
					}

					dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setCognome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome());//cognome

					//stato o comune di nascita
					if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin() != null
							&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin())) {

						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setComuneOStatoEsteroNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin());
					} else if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune() != null 
							&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin() != null
							&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin())) {
						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setComuneOStatoEsteroNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin());
					}

					dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setDataNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getDtNascita());// data nascita

					dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setNome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsNome());//nome

					dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setSesso(comunicazione.getDatoreAttuale().getLegaleRappr().getSesso());//sesso

					if (comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia() != null
							&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia())) {
						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_SI_DESC);
					} else {
						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_N_DESC);
					}

					//titolo di soggiorno
					if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null) {

						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().setTitoloSoggiorno(new TitoloSoggiorno());
						dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno().setDataScadenzaPS(comunicazione.getDatoreAttuale().getLegaleRappr().getDtScadenzaPermessoSogg());// data scadenza

						//motivo titolo di soggiorno
						if (comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin())) {
							dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno().setMotivoPermesso(comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin());
						}

						if (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento())) {
							dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno().setNumeroDocumento(comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento());//numero documento
						}

						//questura
						if (comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin())) {
							dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno().setQuestura(comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin());
						}

						//tipo documento / status straniero
						if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin())) {
							dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno().setTipoDocumento(comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin());
						}
					}
				}
			}
		} else {//VARDATORI --
			if(comunicazione.getDatoreAttuale() != null) {

				if(ComonlUtility.isVoid(comunicazione.getDatoreAttuale().getDsVariazioneRagSociale()) ) {

					if (dtoSpicom.getTrasferimentoRamoAziendale() == null) {
						dtoSpicom.setTrasferimentoRamoAziendale(new TrasferimentoRamoAziendaleDTO());
					}

					if (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome())) {

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale() == null) {
							dtoSpicom.getTrasferimentoRamoAziendale().setDatoreLavoroAttuale(new DatoreDiLavoroDTO());
						}

						//legale rapp
						dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().setLegaleRappresentante(new LegaleRappresentante());

						//cittadinanza
						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin())) {
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setCittadinanza(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin());
						}

						dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setCognome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome());//cognome

						//stato o comune di nascita
						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin())) {
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setComuneOStatoEsteroNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin());
						} else if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin())) {
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setComuneOStatoEsteroNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin());
						}

						dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setDataNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getDtNascita());// data nascita

						dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setNome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsNome());//nome

						dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setSesso(comunicazione.getDatoreAttuale().getLegaleRappr().getSesso());//sesso

						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia())) {

							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_N_DESC);
						}

						//titolo di soggiorno
						if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null) {

							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().setTitoloSoggiorno(new TitoloSoggiorno());
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setDataScadenzaPS(
									comunicazione.getDatoreAttuale().getLegaleRappr().getDtScadenzaPermessoSogg());// data scadenza

							//motivo titolo di soggiorno
							if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin())) {
								dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setMotivoPermesso(
										comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin());
							}


							if (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento())) {
								dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setNumeroDocumento(
										comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento());//numero documento
							}

							//questura
							if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin())) {
								dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setQuestura(
										comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin());
							}

							//tipo documento / status straniero
							if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin())) {
								dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setTipoDocumento(
										comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin());
							}
						}
					}

				} else {

					if (dtoSpicom.getVariazioneRagioneSociale() == null) {
						dtoSpicom.setVariazioneRagioneSociale(new VariazioneRagioneSocialeDTO());
					}

					if (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome())) {

						if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale() == null) {
							dtoSpicom.getVariazioneRagioneSociale().setDatoreLavoroAttuale(new DatoreDiLavoroDTO());
						}

						//legale rapp
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().setLegaleRappresentante(new LegaleRappresentante());

						//cittadinanza
						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin())) {
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setCittadinanza(comunicazione.getDatoreAttuale().getLegaleRappr().getCittadinanza().getCodCittadinanzaMin());
						}

						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setCognome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsCognome());//cognome

						//stato o comune di nascita
						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin())) {
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setComuneOStatoEsteroNascita(
									comunicazione.getDatoreAttuale().getLegaleRappr().getStatiEsteri().getCodNazioneMin());
						} else if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune() != null
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin() != null
								&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin())) {
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setComuneOStatoEsteroNascita(
									comunicazione.getDatoreAttuale().getLegaleRappr().getComune().getCodComuneMin());
						}

						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setDataNascita(comunicazione.getDatoreAttuale().getLegaleRappr().getDtNascita());// data nascita

						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setNome(comunicazione.getDatoreAttuale().getLegaleRappr().getDsNome());//nome

						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setSesso(comunicazione.getDatoreAttuale().getLegaleRappr().getSesso());//sesso

						if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
								&& comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia() != null
								&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getFlgSoggiornanteItalia())) {

							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_SI_DESC);
						} else {
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setSoggiornanteInItalia(ComonlConstants.FLAG_N_DESC);
						}

						//titolo di soggiorno
						if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null) {

							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().setTitoloSoggiorno(new TitoloSoggiorno());
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setDataScadenzaPS(
									comunicazione.getDatoreAttuale().getLegaleRappr().getDtScadenzaPermessoSogg());// data scadenza

							//motivo titolo di soggiorno
							if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin())) {
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setMotivoPermesso(
										comunicazione.getDatoreAttuale().getLegaleRappr().getMotivoPermesso().getCodMotivoMin());
							}

							if (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento())) {
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setNumeroDocumento(
										comunicazione.getDatoreAttuale().getLegaleRappr().getNumeroDocumento());//numero documento
							}

							//questura
							if (comunicazione.getDatoreAttuale().getLegaleRappr() != null 
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin())) {
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setQuestura(
										comunicazione.getDatoreAttuale().getLegaleRappr().getQuestura().getCodQuesturaMin());
							}

							//tipo documento / status straniero
							if (comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero() != null
									&& comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin() != null
									&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin())) {
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno().setTipoDocumento(
										comunicazione.getDatoreAttuale().getLegaleRappr().getStatusStraniero().getCodStatusMin());
							}

						}
					}
				}

			}
		}
	}

	private void handleDatiDelDatore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom,
			String tipoTracciato) {
		if(comunicazione.getDatoreAttuale() != null && !ControlliSuOggettoComunicazione.isVARDATORI(comunicazione)) {

			if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
				AgenziaSomministrazioneDTO tempUSDto = new AgenziaSomministrazioneDTO();

				tempUSDto.setNumIscrizioneAlbo(comunicazione.getDatoreAttuale().getNumeroIscrizioneAlbo().toUpperCase());
				if (comunicazione.getDatoreAttuale().getFlgUtilEstera() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgUtilEstera())) {
					tempUSDto.setFlgAgenziaEstera(ComonlConstants.FLAG_SI_DESC);
				} else {
					tempUSDto.setFlgAgenziaEstera(ComonlConstants.FLAG_N_DESC);
				}
				dtoSpicom.setDatoreDiLavoro(tempUSDto);
			}
			else if(tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID)) {
				DatoreDomesticoDTO datDomDto = new DatoreDomesticoDTO();
				datDomDto.setDsCognome(ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getDsCognome()) ?
						ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDatoreAttuale().getDsCognome()) : null);
				datDomDto.setDsNome(ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getDsNome()) ?
						ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDatoreAttuale().getDsNome()) : null);
				dtoSpicom.setDatoreDiLavoro(datDomDto);
			}
			else {
				dtoSpicom.setDatoreDiLavoro(new DatoreDiLavoroDTO());
			}
			dtoSpicom.getDatoreDiLavoro().setCodFiscale(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDatoreAttuale().getCodiceFiscale().toUpperCase()));
			dtoSpicom.getDatoreDiLavoro().setDenominazione(ComonlStringUtils.formatDescrDatore(comunicazione.getDatoreAttuale().getDsDenominazioneDatore())); 

			SedeLavoro sedeLavoro = null;
			if(comunicazione.getDatoreAttuale().getSedeOperativa() != null) {
				sedeLavoro = comunicazione.getDatoreAttuale().getSedeOperativa();
			} else {
				sedeLavoro = comunicazione.getDatoreAttuale().getSedeLegale();
			}

			if (sedeLavoro != null) {
				dtoSpicom.getDatoreDiLavoro().setCapSedeLavoro(sedeLavoro.getCodCap());
				
				if (sedeLavoro.getComune() != null
						&& sedeLavoro.getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(sedeLavoro.getComune().getCodComuneMin())) {
					dtoSpicom.getDatoreDiLavoro().setComuneSedeLavoro(sedeLavoro.getComune().getCodComuneMin());
					
				} else if (sedeLavoro.getStatiEsteri() != null
						&& sedeLavoro.getStatiEsteri().getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(sedeLavoro.getStatiEsteri().getCodNazioneMin())) {
					dtoSpicom.getDatoreDiLavoro().setComuneSedeLavoro(sedeLavoro.getStatiEsteri().getCodNazioneMin());
				}
				
				dtoSpicom.getDatoreDiLavoro().setEmailSedeLavoro(ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getEmail()));
				dtoSpicom.getDatoreDiLavoro().setFaxSedeLavoro(sedeLavoro.getFax());
				dtoSpicom.getDatoreDiLavoro().setIndirizzoSedeLavoro(ComonlStringUtils.eliminaCaratteriSpeciali(sedeLavoro.getIndirizzo()));
				if(sedeLavoro.getCodCap() != null) {
					dtoSpicom.getDatoreDiLavoro().setCapSedeLavoro(sedeLavoro.getCodCap());
				}
				dtoSpicom.getDatoreDiLavoro().setTelefonoSedeLavoro(sedeLavoro.getTelefono());
			}

			if (comunicazione.getDatoreAttuale().getSedeLegale() != null) {
				dtoSpicom.getDatoreDiLavoro().setCapSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap());

				if (comunicazione.getDatoreAttuale().getSedeLegale().getComune() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin())) {
					dtoSpicom.getDatoreDiLavoro().setComuneSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getComune().getCodComuneMin());
				
				} else if (comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri() != null
						&& comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin())) {
					dtoSpicom.getDatoreDiLavoro().setComuneSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri().getCodNazioneMin());
				}
				
				dtoSpicom.getDatoreDiLavoro().setEmailSedeLegale(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDatoreAttuale().getSedeLegale().getEmail()));
				dtoSpicom.getDatoreDiLavoro().setFaxSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getFax());
				dtoSpicom.getDatoreDiLavoro().setIndirizzoSedeLegale(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDatoreAttuale().getSedeLegale().getIndirizzo()));
				if(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap()!= null) {
					dtoSpicom.getDatoreDiLavoro().setCapSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap());
				}
				dtoSpicom.getDatoreDiLavoro().setTelefonoSedeLegale(comunicazione.getDatoreAttuale().getSedeLegale().getTelefono());
			}

			if(comunicazione.getDatoreAttuale().getAtecofin() != null 
					&& comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin() != null
					&& !"".equalsIgnoreCase(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin())) {
				dtoSpicom.getDatoreDiLavoro().setSettore(comunicazione.getDatoreAttuale().getAtecofin().getCodAtecofinMin());
			}

			if(!tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
				if(comunicazione.getDatoreAttuale().getFlgPubAmm() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getDatoreAttuale().getFlgPubAmm()) ){
					dtoSpicom.getDatoreDiLavoro().setPubblicaAmministrazione(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					dtoSpicom.getDatoreDiLavoro().setPubblicaAmministrazione(ComonlConstants.FLAG_N_DESC);
				}
			}
		}
	}

	protected void handleDatiTutore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom) {
		if(comunicazione.getRapporto() != null && comunicazione.getRapporto().getTutore()  != null) {
			dtoSpicom.getDatiAggiuntivi().setDatiApprendistato(new DatiApprendistatoDTO());

			Tutore tutore = comunicazione.getRapporto().getTutore();

			if(tutore.getDtVisitaMedica() != null) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().setDataVisitaMedica(tutore.getDtVisitaMedica());
			}
			dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().setTutore(new DatiTutoreDTO());

			if(tutore.getNumAnniEsperienza() != null) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setAnniEsperienza(tutore.getNumAnniEsperienza().longValue());
			}
			if(tutore.getCfTutore() != null) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setCodFiscale(ComonlStringUtils.eliminaCaratteriSpeciali(tutore.getCfTutore().toUpperCase()));
			}
			if(tutore.getCognome() != null) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setCognome(ComonlStringUtils.eliminaCaratteriSpeciali(tutore.getCognome()));
			}
			if(tutore.getDtNascita() != null) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setDataNascita(tutore.getDtNascita());
			}
			if(tutore.getFlgTitolare() != null
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(tutore.getFlgTitolare())) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setFlgTitolare(ComonlConstants.FLAG_S);
			} else {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setFlgTitolare(ComonlConstants.FLAG_N);
			}
			if(tutore.getGradoContrattuale() != null
					&& tutore.getGradoContrattuale().getCodGradoContrattuale() != null
					&& !"".equalsIgnoreCase(tutore.getGradoContrattuale().getCodGradoContrattuale())) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setGradoContrattuale(tutore.getGradoContrattuale().getCodGradoContrattuale());
			}
			if(tutore.getDsLivelloInquadramento() != null) {
				if (tutore.getDsLivelloInquadramento().length() >=6)
					dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setLivelloInquadramento(tutore.getDsLivelloInquadramento().substring(0, 6));
				else dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setLivelloInquadramento(tutore.getDsLivelloInquadramento());
			}
			if(tutore.getNome()!= null){
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setNome(ComonlStringUtils.eliminaCaratteriSpeciali(tutore.getNome()));
			}
			if(tutore.getIstat() != null
					&& tutore.getIstat().getCodIstat2001livello5Min() != null
					&& !"".equalsIgnoreCase(tutore.getIstat().getCodIstat2001livello5Min())) {
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setQualificaProfessionaleIstat(tutore.getIstat().getCodIstat2001livello5Min());
			}
			if(tutore.getSesso() != null){
				dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore().setSesso(tutore.getSesso().toUpperCase());
			}
		}
	}

	protected void handleDatiBloccoTirocinio (Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {

		if (ControlliSuOggettoComunicazione.isUNILAV(comunicazione) && 
				comunicazione.getRapporto() != null 
				&& comunicazione.getRapporto().getCategTirocinante()  != null
				&& comunicazione.getRapporto().getCategTirocinante().getCodCategTirocinanteMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCategTirocinante().getCodCategTirocinanteMin())
				&& comunicazione.getRapporto().getTipologiaTirocinio()  != null
				&& comunicazione.getRapporto().getTipologiaTirocinio().getCodTipologiaTirocinioMin() != null
				&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipologiaTirocinio().getCodTipologiaTirocinioMin())) {

			dto.getDatiAggiuntivi().setDatiTirocinio(new DatiTirocinioDTO());

			if(comunicazione.getRapporto() != null) {

				//72160-1501: gestione della denominazione ente promotore in caso di cpi
				if (comunicazione.getRapporto().getTipoEntePromTirocinio() != null
						&& ComonlConstants.ID_TIPO_ENTE_TIROCINIO_CPI.equals(comunicazione.getRapporto().getTipoEntePromTirocinio().getId())) {
					dto.getDatiAggiuntivi().getDatiTirocinio().setAltroSoggettoPromotore(ComonlConstants.CPI_DI + comunicazione.getRapporto().getCpi().getDsComTCpi()); //FIXME è il dato giusto
					dto.getDatiAggiuntivi().getDatiTirocinio().setCodCpiPromotore(comunicazione.getRapporto().getCpi().getCodCpi());
				} else {
					dto.getDatiAggiuntivi().getDatiTirocinio().setAltroSoggettoPromotore(comunicazione.getRapporto().getDsSoggPromTirocinio());
				}
				if (comunicazione.getRapporto().getTipoEntePromTirocinio() != null) {
					dto.getDatiAggiuntivi().getDatiTirocinio().setTipologiaPromotore(comunicazione.getRapporto().getTipoEntePromTirocinio().getCodiceEntePromTirocinioMin());
				}


				//71862 - Attivita' per gestione pdf piano formativo / nuovi tirocinii - START
				if (comunicazione.getRapporto().getTipologiaTirocinio() != null
						&& comunicazione.getRapporto().getTipologiaTirocinio().getCodTipologiaTirocinioMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipologiaTirocinio().getCodTipologiaTirocinioMin()) ) {

					dto.getDatiAggiuntivi().getDatiTirocinio().setTipologiaTirocinio(comunicazione.getRapporto().getTipologiaTirocinio().getCodTipologiaTirocinioMin());
				}

				if (comunicazione.getRapporto().getCategTirocinante() != null
						&& comunicazione.getRapporto().getCategTirocinante().getCodCategTirocinanteMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCategTirocinante().getCodCategTirocinanteMin()) ) {

					dto.getDatiAggiuntivi().getDatiTirocinio().setCategoriaTirocinante(comunicazione.getRapporto().getCategTirocinante().getCodCategTirocinanteMin());
				}
				//71862 - Attivita' per gestione pdf piano formativo / nuovi tirocinii - END

				dto.getDatiAggiuntivi().getDatiTirocinio().setCFSoggettoPromotoreTirocinio(
						comunicazione.getRapporto().getCfSoggPromotoreTirocinio());//xxxxx Adeguamenti 2014 - cFSoggettoPromotoreTirocinio

			}

		}

	}
	protected void handleDatiDiInvio(Comunicazione comunicazione, Ruolo ruolo, ComunicazioneTracciatoUnicoDTO dto) {
			dto.setDatiInvio(new DatiInvioDTO());

			// porzione da eseguire solo in caso di invio standard di comunicazione a Spicom...
			if(ruolo != null) {

				if(ruolo.isConsulenteRespo()) {
					dto.getDatiInvio().setCodFiscaleSoggettoComunicante(comunicazione.getCodiceEnte());				
					dto.getDatiInvio().setSoggettoComunicante(comunicazione.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin());
					
					if(comunicazione.getDelegato() != null) {
						if (comunicazione.getDelegato().getMailDelegatoImpresa() != null)
							dto.getDatiInvio().setEmailDelegato(comunicazione.getDelegato().getMailDelegatoImpresa().trim());
					} else {
						if (comunicazione.getEmail() != null)
							dto.getDatiInvio().setEmailDelegato(comunicazione.getEmail().trim());
					}
				}

				else if(!ruolo.isAmministratore()) {

					dto.getDatiInvio().setSoggettoComunicante(ComonlConstants.CODICE_SERVIZI_COMPETENTI);
					dto.getDatiInvio().setCodFiscaleSoggettoComunicante(comunicazione.getCodiceEnte());
					if (comunicazione.getEmail() != null)
						dto.getDatiInvio().setEmailDelegato(comunicazione.getEmail().trim());
				}

				else {

					dto.getDatiInvio().setCodFiscaleSoggettoComunicante(comunicazione.getCodiceEnte());
					if (comunicazione.getTipoSoggettoAbilitato() != null) {
						dto.getDatiInvio().setSoggettoComunicante(comunicazione.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin());
					} else {
						dto.getDatiInvio().setSoggettoComunicante(ComonlConstants.CODICE_SERVIZI_COMPETENTI);
					}

					if (comunicazione.getEmailsoggetto() != null)
						dto.getDatiInvio().setEmailDelegato(comunicazione.getEmailsoggetto().trim());

				}
			}
			// ***********************************************************************************************
			// ... in caso di procedura di recovery, per cui non ho il ruolo dell'operatore,
			// i due dati sono stati recuperati dal db, se esistono
			else {
				dto.getDatiInvio().setCodFiscaleSoggettoComunicante(comunicazione.getCodiceFiscaleSoggetto());
				
				if(comunicazione.getTipoSoggettoAbilitato() !=  null
						&& comunicazione.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin()) ) {
					dto.getDatiInvio().setSoggettoComunicante(comunicazione.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin());
				}
				
				if (comunicazione.getEmailsoggetto() != null)
					dto.getDatiInvio().setEmailDelegato(comunicazione.getEmailsoggetto().trim());
			}
			// ***********************************************************************************************


			// in ogni caso in cui la mail delagato non sia stata valorizzata mediante gli algoritmi di sopra,
			// la prendo dai dati di invio poiche' e' diventata obbligatoria per il ministero
			if(dto.getDatiInvio().getEmailDelegato() == null || dto.getDatiInvio().getEmailDelegato().equals("")) {
				if ( comunicazione.getEmail() != null)
					dto.getDatiInvio().setEmailDelegato(comunicazione.getEmail().trim());
			}

			String codRegPrec = comunicazione.getCodiceComunRegPrec();

			if (codRegPrec == null || codRegPrec.length() != 16) {
				String paddingRettAnn = ComonlConstants.URGENZA_CODICE_REG_RETT_ANN_URGFAX;
				if(comunicazione.getFlgRettifica() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getFlgRettifica())) {
					dto.getDatiInvio().setCodiceComunicazionePrecedente(paddingRettAnn);
				}
				else {
					dto.getDatiInvio().setCodiceComunicazionePrecedente(null);
				}
			}
			else {
				dto.getDatiInvio().setCodiceComunicazionePrecedente(codRegPrec);
			}
			// **********************************************************************************************


			// **********************************************************************************************
			// data di invio, troncata a gg/MM/aaaa,
			// senza ore, min, sec per evitare disallineamenti con l'orario presente sul sever in ricezione al MINISTERO
			java.util.Date data;
			if(comunicazione.getDtTimbroPostale() != null) {
				data = comunicazione.getDtTimbroPostale(); 
			}
			else {
				data = new Date();
			}

			String dataS = Format.convertiDataEOraInStringa(data);
			Date d = Format.creaJavaUtilDate(dataS);
			dto.getDatiInvio().setDataInvio(d);

			dto.getDatiInvio().setDescrizioneCausaForzaMaggiore(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getDsCausaForzamaggiore()));

			if(comunicazione.getDsCausaForzamaggiore() != null 
					&& comunicazione.getFlgCausaForzamaggiore() != null
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getFlgCausaForzamaggiore()) ){
				dto.getDatiInvio().setFlgAssunzionePerCausaForzaMaggiore(ComonlConstants.FLAG_SI_DESC);
			}
			else {
				dto.getDatiInvio().setFlgAssunzionePerCausaForzaMaggiore(ComonlConstants.FLAG_N_DESC);
				dto.getDatiInvio().setDescrizioneCausaForzaMaggiore(null);
			}

			dto.getDatiInvio().setMotivoUrgenza(comunicazione.getDsMotivoUrgenza());

			if(comunicazione.getNumProtCom() != null) {
				dto.getDatiInvio().setProtocolloSistema(String.valueOf(comunicazione.getNumProtCom()));
			}

			//    	 TIPO COMUNICAZIONE_TU
			// di default l'id e' quello della comunicazione 'obbligatoria' standard
			Long idComTTipoComunicazioneTu = comunicazione.getTipoComunicazioneTu().getId();
			dto.getDatiInvio().setTipoComunicazione(comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin());

			//71862 - versione
			if (ControlliSuOggettoComunicazione.isVARDATORI(comunicazione)) {
				dto.getDatiInvio().setVersione(TUConstants.VERSIONE_VARDATORI);
			} else if (ControlliSuOggettoComunicazione.isUNILAV(comunicazione)) {
				dto.getDatiInvio().setVersione(TUConstants.VERSIONE_UNILAV);
			} else if (ControlliSuOggettoComunicazione.isUNISOMM(comunicazione)) {
				dto.getDatiInvio().setVersione(TUConstants.VERSIONE_UNISOMM);
			} else if (ControlliSuOggettoComunicazione.isUNIURG(comunicazione)) {
				dto.getDatiInvio().setVersione(TUConstants.VERSIONE_UNIURG);
			} else {
				dto.getDatiInvio().setVersione(null);//se arriviamo qui c'e' un problema, spicom rispondera' negativamente...
			}
			//71862 - versione
	}


	protected void handleDatiInizioRapporto(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto, VariazioneSomm variazioneSomm) {
		if(comunicazione.getRapporto() != null) {

			if(ControlliSuOggettoComunicazione.isUNISOMM(comunicazione)) {

				TranscodeUnisomHelper.transcodeComunicazione(comunicazione, dto, variazioneSomm); 
			}	    	  
			// tipo tracciato UNIDOM
			else if(ControlliSuOggettoComunicazione.isUNIDOM(comunicazione)) {
				InizioRapportoUniDomDTO rappDto = new InizioRapportoUniDomDTO();

				// ENTE PREVIDENZIALE INSERITO DALL'UTENTE
				if(comunicazione.getRapporto().getEntePrevidenziale() != null && comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin() != null) {
					rappDto.setCodEntePrevidenziale(ComonlStringUtils.eliminaCaratteriSpeciali(comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin()));
				}

				// DATA FINE RAPPORTO: non serve piu', in caso di TRASFORMAZIONE o TRASFERIMENTO
				if(comunicazione.getRapporto().getDtFineRapporto() != null && 
						!ControlliSuOggettoComunicazione.isTRASFORMAZIONE(comunicazione) &&
						!ControlliSuOggettoComunicazione.isTRASFERIMENTO(comunicazione)) {
					rappDto.setDataFine(comunicazione.getRapporto().getDtFineRapporto());
				}

				// DATA INIZIO RAPPORTO
				if(comunicazione.getRapporto().getDtInizioRapporto() != null) {
					rappDto.setDataInizio(comunicazione.getRapporto().getDtInizioRapporto());
				}

				if(ComonlUtility.isNotVoid(comunicazione.getRapporto().getFlgCcnlApplicato()) &&
						ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgCcnlApplicato()) ) {
					rappDto.setFlgCcnlApplicato(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					rappDto.setFlgCcnlApplicato(ComonlConstants.FLAG_N_DESC);
				}
				if(ComonlUtility.isNotVoid(comunicazione.getRapporto().getLivelloInquadramento()) ){
					rappDto.setLivelloInquadramento(comunicazione.getRapporto().getLivelloInquadramento());
				}

				if(comunicazione.getRapporto().getNumOreSettMed() != null) {
					rappDto.setOreSettimanaliMedie(comunicazione.getRapporto().getNumOreSettMed().longValue());
				}

				if(comunicazione.getRapporto().getPatInail() != null){
					rappDto.setPatInail(comunicazione.getRapporto().getPatInail());
				}

				if(comunicazione.getRapporto().getIstatLivello5() != null 
						&& comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min()) ) {
					rappDto.setQualificaProfessionaleIstat(comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min());
				}

				if(comunicazione.getRapporto().getRetribuzioneCompenso() != null) { 
					rappDto.setRetribuzione(comunicazione.getRapporto().getRetribuzioneCompenso().longValue());
				}

				if(comunicazione.getRapporto().getTipoContratti() != null 
						&& comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin()) ){
					rappDto.setTipologiaContrattuale(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin());
				}

				if(comunicazione.getRapporto().getTipoOrario() != null
						&& comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin())) {
					rappDto.setTipoOrario(comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin());
				}

				dto.setDatiInizio(rappDto);
			}  else { //UNILAV
				// Se e' un rapporto di tipo UL istanzio un oggetto di tipo InizioRapportoUnilavDTO
				InizioRapportoUnilavDTO rappDto = new InizioRapportoUnilavDTO();

				// CONTRATTO APPLICATO
				if(comunicazione.getRapporto().getCcnl() != null
						&& comunicazione.getRapporto().getCcnl().getCodCcnlMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCcnl().getCodCcnlMin())){
					rappDto.setCcnlApplicato(comunicazione.getRapporto().getCcnl().getCodCcnlMin());
				}

				//NON ESISTONO PIU' LE AGEVOLAZIONI ... setto a NULL
				rappDto.setAgevolazioni(null);


				//start - 72160 - COMONL-1360 - nota su data fine rapporto e data fine periodo formativo
				//il dato deve essere mandato X UNILAV solo in caso di ASSUNZIONE-CESSAZIONE E TRASFERIMENTO caso di DISTACCO
				// DATA FINE RAPPORTO
				if ( comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) 
						|| comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE) 
						|| (comunicazione.getRapporto() != null && comunicazione.getRapporto().getTrasformazionerl() != null
						&& ComonlConstants.COD_MIN_TIPO_CO_DISTACCO.equals(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin())) 
						) {
					if(comunicazione.getRapporto().getDtFineRapporto() != null ) {
						rappDto.setDataFine(comunicazione.getRapporto().getDtFineRapporto());
					}
				}
				//end   - 72160 - COMONL-1360

				// DATA INIZIO RAPPORTO
				if(comunicazione.getRapporto().getDtInizioRapporto() != null) {
					rappDto.setDataInizio(comunicazione.getRapporto().getDtInizioRapporto());
				}
				//start - 72160 - COMONL-1360 - nota su data fine rapporto e data fine periodo formativo
				//il dato deve essere mandato X UNILAV solo in caso di ASSUNZIONE-CESSAZIONE 
				// DATA FINE PERIODO FORMATIVO
				if (comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) 
						|| comunicazione.getTipoComunicazione().getId().equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE) ) {
					if(comunicazione.getRapporto().getDtFinePeriodoFormativo() != null) {
						rappDto.setDataFinePeriodoFormativo(comunicazione.getRapporto().getDtFinePeriodoFormativo());
					}
				}
				//end   - 72160 - COMONL-1360

				// LEGGE 68
				if(comunicazione.getRapporto().getDtLegge68() != null) {
					rappDto.setDataNullaostaLegge68OConvenzione(comunicazione.getRapporto().getDtLegge68());
				}
				// ENTE PREVIDENZIALE COMBO
				if(comunicazione.getRapporto().getEntePrevidenziale() != null 
						&& comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin())){
					rappDto.setEntePrevidenziale(comunicazione.getRapporto().getEntePrevidenziale().getCodEntePrevidenzialeMin());
				}

				if(comunicazione.getRapporto().getCodiceEntePrev() != null) {
					String codiceEntePrevidenziale = comunicazione.getRapporto().getCodiceEntePrev().equals("") ?
							null : comunicazione.getRapporto().getCodiceEntePrev();
					rappDto.setCodEntePrevidenziale(ComonlStringUtils.eliminaCaratteriSpeciali(codiceEntePrevidenziale));
				}

				if(comunicazione.getRapporto().getFlgSocioLavoratore() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgSocioLavoratore()) ){
					rappDto.setFlgSocioLavoratore(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					rappDto.setFlgSocioLavoratore(ComonlConstants.FLAG_N_DESC);
				}

				if(comunicazione.getRapporto().getFlgLavInMobilita() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgLavInMobilita()) ){
					rappDto.setLavInMobilita(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					rappDto.setLavInMobilita(ComonlConstants.FLAG_N_DESC);
				}
				if(comunicazione.getRapporto().getFlgLavStagionale() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgLavStagionale() )) {
					rappDto.setLavoroStagionale(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					rappDto.setLavoroStagionale(ComonlConstants.FLAG_N_DESC);
				}
				if (comunicazione.getRapporto().getFlgAssunzioneObbligatoria() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgAssunzioneObbligatoria() ))  {
					rappDto.setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_SI_DESC);
				} else {
					rappDto.setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_N_DESC);
				}


				//adeguamenti 2016: il codice che viene passato in questo campo e' quello che in passato si trovava in COM_T_NORM_ASS_68
				//mandato a SILP ma non al ministero. 
				//adesso mandiamo nei dati del rapporto il codice ministeriale ma continuiamo a mandare  SILP il codice vecchio che 
				//ora si trova nella tabella COM_T_CATEG_LAV_ASS_OBBL, per la distinzione tra le voci 
				//in cui sono divise le descrizini della L68
				if(comunicazione.getRapporto().getCategLavAssObbl() != null 
						&&  comunicazione.getRapporto().getCategLavAssObbl().getCodCategLavAssObblMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getCategLavAssObbl().getCodCategLavAssObblMin()) )  {
					rappDto.setCategoriaLavoratoreAssunzioneObbligatoria(comunicazione.getRapporto().getCategLavAssObbl().getCodCategLavAssObblMin());
				}				  

				if(comunicazione.getRapporto().getFlgLavoroAgricoltura() != null && ComonlConstants.FLAG_S.equalsIgnoreCase(comunicazione.getRapporto().getFlgLavoroAgricoltura())) {
					rappDto.setFlgLavoroAgricoltura(ComonlConstants.FLAG_SI_DESC);
				}
				else {
					rappDto.setFlgLavoroAgricoltura(ComonlConstants.FLAG_N_DESC);
				}

				if(comunicazione.getRapporto().getGiornateLavPreviste() != null) {
					rappDto.setGiornateLavorativePrevisteAgricoltura(comunicazione.getRapporto().getGiornateLavPreviste().longValue());
				}
				if(comunicazione.getRapporto().getLivelloRetribuzione() != null 
						&&  comunicazione.getRapporto().getLivelloRetribuzione().getCodLivello() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getLivelloRetribuzione().getCodLivello())){
					rappDto.setLivelloInquadramento(comunicazione.getRapporto().getLivelloRetribuzione().getCodLivello());
				} else if (comunicazione.getRapporto().getLivelloInquadramento() != null){
					rappDto.setLivelloInquadramento(comunicazione.getRapporto().getLivelloInquadramento() );
				}

				rappDto.setNumeroAttoLegge68(comunicazione.getRapporto().getNumeroAttoLegge68());

				if(comunicazione.getRapporto().getNumOreSettMed() != null) {
					rappDto.setOreSettimanaliMedie(comunicazione.getRapporto().getNumOreSettMed().longValue());
				}

				if(comunicazione.getRapporto().getPatInail() != null) {
					rappDto.setPatInail(comunicazione.getRapporto().getPatInail());
				}

				if(comunicazione.getRapporto().getIstatLivello5() != null
						&& comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min() != null
						& !"".equalsIgnoreCase(comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min())) {

					rappDto.setQualificaProfessionaleIstat(comunicazione.getRapporto().getIstatLivello5().getCodIstat2001livello5Min());
				}
				if(comunicazione.getRapporto().getRetribuzioneCompenso() != null) { 
					rappDto.setRetribuzione(comunicazione.getRapporto().getRetribuzioneCompenso().longValue());
				}

				if(comunicazione.getRapporto().getTipoContratti() != null 
						&& comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin()) ){
					rappDto.setTipologiaContrattuale(comunicazione.getRapporto().getTipoContratti().getCodTipoContrattoMin());
				}

				if(comunicazione.getRapporto().getTipoOrario() != null
						&& comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin() != null
						&& !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin())) {
					rappDto.setTipoOrario(comunicazione.getRapporto().getTipoOrario().getCodTipoorarioMin());
				}

				if(comunicazione.getRapporto().getTipoLavorazione() != null && !"".equalsIgnoreCase(comunicazione.getRapporto().getTipoLavorazione()) ){
					rappDto.setTipoLavorazione(comunicazione.getRapporto().getTipoLavorazione());
				}

				dto.setDatiInizio(rappDto);
			}

		}
	}


	private void impostaDatiAziendaPerIntegrazioneForte(DatiAggiuntiviAziendaDTO aziDto, Object bo, Comunicazione comunicazione) {
		// controllo preventivo che il bo sia popolato
		if(bo != null) {
			if(bo instanceof Datore) {
				Datore castBo = ((Datore) bo);
				aziDto.setIdAziAnagraficaSilp(ComonlUtility.isNotVoid(castBo.getIdAziendaSilp()) ?  castBo.getIdAziendaSilp().longValue() : null);
				if (castBo.getSedeLegale() !=  null && castBo.getSedeLegale().getIdSedeSilp() != null) {
					aziDto.setIdAziSedeLagaleSilp(castBo.getSedeLegale().getIdSedeSilp().longValue());
				} else {
					aziDto.setIdAziSedeLagaleSilp(null);
				} 
				
				SedeLavoro sedeLavoro = null;
				if (castBo.getSedeOperativa() != null) {
					sedeLavoro = castBo.getSedeOperativa();
				} else {
					sedeLavoro = castBo.getSedeLegale();
				}
				
				if (sedeLavoro !=  null && sedeLavoro.getIdSedeSilp() != null) {
					aziDto.setIdAziSedeOperativaSilp(sedeLavoro.getIdSedeSilp().longValue());
				} else {
					aziDto.setIdAziSedeOperativaSilp(null);
				} 
			}
			else {
				Trasformazionerl castBo = ((Trasformazionerl) bo); //FIXME sono i dati giusti?
				boolean trasferimento = castBo.getCodTrasformazionirlMin().equals(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_TL);
				if (trasferimento) {
					aziDto.setIdAziAnagraficaSilp(comunicazione.getDatoreAttuale().getIdAziendaSilp() != null ? comunicazione.getDatoreAttuale().getIdAziendaSilp().longValue() : null);
					aziDto.setIdAziSedeOperativaSilp(comunicazione.getDatoreAttuale() != null 
							&& comunicazione.getDatoreAttuale().getSedeLegale() != null 
							&&   comunicazione.getDatoreAttuale().getSedeLegale().getIdSedeSilp() != null ? comunicazione.getDatoreAttuale().getSedeLegale().getIdSedeSilp().longValue() : null) ;
				}
			}
			aziDto.setVariazione(this.getDatiVariazionePerSilp(bo, comunicazione));
		}
	}

	private String getDatiVariazionePerSilp(Object bo, Comunicazione co) {
		// stringa di esattamente 5 caratteri in ordine predefinito
		String variazione = "";

		// controllo preventivo che il bo sia popolato
		if(bo != null) {
			if(bo instanceof Datore) {
				Datore d = (Datore) bo;
				String var1 = d.getFlgDenomSilpVariata();
				if(ComonlUtility.isVoid(var1)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var1.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}

				String var2 = d.getSedeLegale() != null ? d.getSedeLegale().getFlgComuneSilpVariato() : null;

				if(ComonlUtility.isVoid(var2)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var2.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}

				String var3 = d.getSedeLegale() != null ? d.getSedeLegale().getFlgIndirizzoSilpVariato() : null;

				if(ComonlUtility.isVoid(var3)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var3.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}


				String var4 = d.getSedeOperativa() != null ? d.getSedeOperativa().getFlgComuneSilpVariato() : null;

				if(ComonlUtility.isVoid(var4)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var4.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}

				String var5 = d.getSedeOperativa() != null ? d.getSedeOperativa().getFlgIndirizzoSilpVariato() : null;

				if(ComonlUtility.isVoid(var5)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var5.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}
					
			}
			else {
				Trasformazionerl d = (Trasformazionerl) bo;

				boolean distacco = d.getCodTrasformazionirlMin().equals(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_DL);
				boolean trasferimento = d.getCodTrasformazionirlMin().equals(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_TL);

				if(distacco){
					// DISTACCO
					// sono valorizzati il primo e gli ultimi 2
					String var1 = co.getDatoreAttuale().getFlgDenomSilpVariata();
					if(ComonlUtility.isVoid(var1)) {
						variazione += ComonlConstants.VARIAZIONE_NULLA;
					}
					else {
						variazione += var1.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
					}
					variazione += ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					// TRASFERIMENTO
					// i primi tre valori sono nulli, non essendoci denominazione, comune sede legale e
					// indirizzo sede legale da poter modificare
					variazione += ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA;
				}

				String var4 = co.getDatoreAttuale().getSedeLegale() != null? co.getDatoreAttuale().getSedeLegale().getFlgComuneSilpVariato() : null;
				if(ComonlUtility.isVoid(var4)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var4.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}

				String var5 = co.getDatoreAttuale().getSedeLegale() != null? co.getDatoreAttuale().getSedeLegale().getFlgIndirizzoSilpVariato() : null;
				if(ComonlUtility.isVoid(var5)) {
					variazione += ComonlConstants.VARIAZIONE_NULLA;
				}
				else {
					variazione += var5.equals(ComonlConstants.FLAG_S) ? ComonlConstants.FLAG_SI_DESC : ComonlConstants.FLAG_N_DESC;
				}
			}
		}
		// se il bo non e' popolato, la stringa e' non valorizzata per ogni suo elemento
		else {
			variazione = ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA +
					ComonlConstants.VARIAZIONE_NULLA + ComonlConstants.VARIAZIONE_NULLA;
		}
		return variazione;
	}

}
