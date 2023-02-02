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
package it.csi.comonl.comonlweb.ejb.util.commax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAttoL68;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoEntePromTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.ControlliSuOggettoComunicazione;
import it.csi.spicom.dto.AgenziaSomministrazioneDTO;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.DatiApprendistatoDTO;
import it.csi.spicom.dto.DatiCessazioneDTO;
import it.csi.spicom.dto.DatiInizioRapportoDTO;
import it.csi.spicom.dto.DatiProrogaDTO;
import it.csi.spicom.dto.DatiTirocinioDTO;
import it.csi.spicom.dto.DatiTrasformazioneDTO;
import it.csi.spicom.dto.DatiTutoreDTO;
import it.csi.spicom.dto.DatiVariazioneDTO;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.DatoreDomesticoDTO;
import it.csi.spicom.dto.DichiarazioniUnidomDTO;
import it.csi.spicom.dto.EsitoValidazioneComunicazione;
import it.csi.spicom.dto.InizioRapportoUniDomDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;
import it.csi.spicom.dto.InizioRapportoUnilavDTO;
import it.csi.spicom.dto.LavoratoreInteressatoTrasferimentoRamoAziendaleDTO;
import it.csi.spicom.dto.TitoloSoggiorno;
import it.csi.spicom.util.ComunicazioneTUValidator;
import it.csi.spicom.util.TUConstants;

/**
 * Mappers for Spicom integration
 */
public class CtuSpicomToComunicazioneMapper {

	// private static final LogUtil LOG = new LogUtil(ComonlIntegMappers.class);

	private String codiceRegionale = null;
	private String tipoTracciato = null;
	private Date dataRiferimento = null;
	private DecodificaDad decodificaDad = null;
	private List<ComDImportErrore> errors = null;
	private ComunicazioneTracciatoUnicoDTO dtoSpicom = null;

	public Comunicazione toComunicazioneComonl() {
		Comunicazione comunicazione = new Comunicazione();
		
		
		// ****************************************************************************************************************************
		// dati di inizio rapporto
		// ****************************************************************************************************************************
		handleDatiInizioRapporto(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// Dati Invio
		// ****************************************************************************************************************************
		handleDatiDiInvio(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati aggiuntivi
		// ****************************************************************************************************************************

		if (dtoSpicom.getDatiAggiuntivi() != null && dtoSpicom.getDatiAggiuntivi().getChiaveEsterna() != null) {
			comunicazione.setId(dtoSpicom.getDatiAggiuntivi().getChiaveEsterna());
		}

		if (dtoSpicom.getDatoreDiLavoro() != null && dtoSpicom.getDatiAggiuntivi() != null) {

			comunicazione.setDatoreAttuale(
					comunicazione.getDatoreAttuale() != null ? comunicazione.getDatoreAttuale() : new Datore());

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().getIdSilTNaturaGiuridica() != null
					&& !"".equalsIgnoreCase(
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().getIdSilTNaturaGiuridica())) {

				comunicazione.getDatoreAttuale().setNaturaGiuridica(new NaturaGiuridica());

				comunicazione.getDatoreAttuale().getNaturaGiuridica().setNaturaGiuridica(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().getIdSilTNaturaGiuridica());
			}

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().getFlgArtigiano() != null
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviAzienda().getFlgArtigiano())) {
				comunicazione.getDatoreAttuale().setFlgAzArtigiana(ComonlConstants.FLAG_S);
			} else {
				comunicazione.getDatoreAttuale().setFlgAzArtigiana(ComonlConstants.FLAG_N);
			}

			// per integrazione forte
			/*
			 * if(!ControlliSuOggettoComunicazione.getInstance().
			 * checkDiAnnullRettSeguitoUrgenza(comunicazione)) {
			 * impostaDatiAziendaPerIntegrazioneForte(dtoSpicom. getDatiAggiuntivi().
			 * getDatiAggiuntiviAzienda(), comunicazione.getDatoreAttuale(), comunicazione);
			 * }
			 */
		}

		if (dtoSpicom.getDatiAggiuntivi() != null
				&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null) {

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getAnnoProtocolloProvinciale() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
							.getDataProtocolloProvinciale() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
							.getNumProtocolloProvinciale() != null) {

				comunicazione.setAnnoProtCom(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getAnnoProtocolloProvinciale());
				comunicazione.setDtProtocollo(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getDataProtocolloProvinciale());
				comunicazione.setNumProtCom(Long.valueOf(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNumProtocolloProvinciale()));
			}
		}

		// adeguamenti 2016: il codice che viene passato in questo campo e'
		// quello che
		// in passato si trovava in COM_T_NORM_ASS_68
		// mandato a SILP ma non al ministero.
		// adesso mandiamo nei dati del rapporto il codice ministeriale ma
		// continuiamo a
		// mandare SILP il codice vecchio che
		// ora si trova nella tabella COM_T_CATEG_LAV_ASS_OBBL, per la
		// distinzione tra
		// le voci
		// in cui sono divise le descrizini della L68
		DatiInizioRapportoDTO rapDto = dtoSpicom.getDatiInizio();

		if(rapDto != null) {
			if (comunicazione.getRapporto() == null) {
				comunicazione.setRapporto(new Rapporto());
			}

			if (dtoSpicom.getDatiAggiuntivi() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNormAssL68() != null
					&& !"".equalsIgnoreCase(String
							.valueOf(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNormAssL68()))) {

				CategLavAssObbl categLavAssObbl = (CategLavAssObbl) decodificaDad.getTfromMin(CategLavAssObbl.class,
						String.valueOf(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNormAssL68()),
						dataRiferimento);

				if (categLavAssObbl == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_CATEG_LAV_ASS_OBBL,
							String.valueOf(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNormAssL68()),
							"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - NormAssL68");
				}

				comunicazione.getRapporto().setCategLavAssObbl(categLavAssObbl);

			}

			if (dtoSpicom.getDatiAggiuntivi() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getTipoAttoL68() != null) {

				comunicazione.getRapporto().setTipoAttoL68(new TipoAttoL68());

				comunicazione.getRapporto().getTipoAttoL68()
						.setId(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getTipoAttoL68());
			}
		}

		// la TARGA della provincia, obbligatoria per Spicom
		// ValidazioneCampi vc = ValidazioneCampi.getInstance();

		if (comunicazione.getProvincia() == null) {
			comunicazione.setProvincia(new Provincia());
		}
		Provincia provincia = comunicazione.getProvincia();
		// La provincia che va nella CO è dalla sede sedeOperativa per tutte le
		// comunicazioni invece per vardatori dalla sedeLegale
		// raffy - fix per commax: la sigla della provincia potrebbe non
		// esserci. La
		// ricavo con la regola qui sopra
		if (dtoSpicom.getDatiAggiuntivi() != null
				&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null && ComonlUtility
						.isVoid(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getSiglaProvincia())) {

			String codiceComune = estraiCodiceComune();
			Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, codiceComune, dataRiferimento);
			if (comune == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE, codiceComune,
						"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - Comune da datore");
			} else {
				provincia = comune.getProvincia();
			}

		} else {
			if (dtoSpicom.getDatiAggiuntivi() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null
					&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getSiglaProvincia() != null
					&& !"".equalsIgnoreCase(
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getSiglaProvincia())) {

				// raffy La sigla provincia non e' il codice ministeriale. i
				// vuole una query a
				// parte
				provincia = decodificaDad.getProvinciaByDsTarga(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getSiglaProvincia());

				if (provincia == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_PROVINCIA,
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getSiglaProvincia(),
							"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - SiglaProvincia");
				}

			}
		}

		comunicazione.setProvincia(provincia);
		
		//29/09/22 Raffaella - gestione cpi
		String codiceComune = estraiCodiceComune();;
		Comune comuneCpi = (Comune) decodificaDad.getTfromMin(Comune.class, codiceComune, dataRiferimento);
		if (comuneCpi == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE, codiceComune,
					"TO COMUNICAZIONE COMONL Gestione Cpi - Comune da datore");
		}
		if (comuneCpi != null) {
			comunicazione.setCpi(comuneCpi.getCpi());
		} 
		// **************************************************************************************************
		// per integrazione forte

		if (comunicazione.getProvinciaPrec() == null) {
			comunicazione.setProvinciaPrec(new Provincia());
		}

		if (rapDto != null// in caso di var datori, getInizioRapporto e' null
				&& dtoSpicom.getDatiAggiuntivi() != null
				&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione() != null && ComonlUtility.isNotVoid(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoIdRappLavSilp())) {

			comunicazione.getRapporto().setIdStoricoRapportoSilp(
					dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoIdRappLavSilp());
		} else {

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoSiglaProv() != null
					&& !"".equalsIgnoreCase(
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoSiglaProv())) {
				// La sigla provincia non e' il codice ministeriale. Ci vuole
				// una query a parte
				Provincia provinciaPrec = decodificaDad.getProvinciaByDsTarga(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoSiglaProv());

				if (provinciaPrec == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_PROVINCIA,
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoSiglaProv(),
							"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - RiferimentoSiglaProv");
				}
				comunicazione.setProvinciaPrec(provinciaPrec);

			}

			if (dtoSpicom.getDatiInvio().getCodiceComunicazionePrecedente() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatiInvio().getCodiceComunicazionePrecedente())) {
				comunicazione.setCodiceComunRegPrec(dtoSpicom.getDatiInvio().getCodiceComunicazionePrecedente());
			}

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoAnnoProtProv() != null) {
				comunicazione.setAnnoProtComPrec(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getRiferimentoAnnoProtProv());
			}

			// if (dtoSpicom.getTrasformazione() != null
			// && dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente()
			// != null
			// &&
			// !"".equalsIgnoreCase(dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente()))
			// {
			// comunicazione.setCodiceComunRegPrec(dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente());
			// }

		}

		if (dtoSpicom.getLavoratoreCoobbligato() != null
				&& dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getCapResidenza() != null
				&& !"".equalsIgnoreCase(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getCapResidenza())) {
			if (comunicazione.getLavoratoreCoObbligato() == null) {
				comunicazione.setLavoratoreCoObbligato(new Lavoratore());
			}

			comunicazione.getLavoratoreCoObbligato().setCodCapRes(
					dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getCapResidenza());

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getCodComuneResidenza() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato()
							.getCodComuneResidenza())) {

				if (!dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getCodComuneResidenza()
						.startsWith("Z")) {
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, dtoSpicom.getDatiAggiuntivi()
							.getDatiAggiuntiviLavoratoreCoobbligato().getCodComuneResidenza(), dataRiferimento);
					comunicazione.getLavoratoreCoObbligato().setComuneRes(comune);
					if (comune == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato()
										.getCodComuneResidenza(),
								"TO COMUNICAZIONE COMONL DatiAggiuntiviLavoratoreCoobbligato - CodComuneResidenza");
					}
				} else {
					StatiEsteri statiEsteri = (StatiEsteri) decodificaDad
							.getTfromMin(
									StatiEsteri.class, dtoSpicom.getDatiAggiuntivi()
											.getDatiAggiuntiviLavoratoreCoobbligato().getCodComuneResidenza(),
									dataRiferimento);
					if (statiEsteri == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato()
										.getCodComuneResidenza(),
								"TO COMUNICAZIONE COMONL DatiAggiuntiviLavoratoreCoobbligato - CodComuneResidenza");
					}
					comunicazione.getLavoratoreCoObbligato().setStatiEsteriRes(statiEsteri);

				}

				comunicazione.getLavoratoreCoObbligato()
						.setDsIndirizzoRes(ComonlStringUtils.eliminaCaratteriSpeciali(dtoSpicom.getDatiAggiuntivi()
								.getDatiAggiuntiviLavoratoreCoobbligato().getIndirizzoResidenza()));

				// **************************************************************************************************
				// per integrazione forte
				if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato()
						.getIdAnagraficaSilp() != null) {

					if (comunicazione.getLavoratoreCoObbligato() == null) {
						comunicazione.setLavoratoreCoObbligato(new Lavoratore());
					}
					comunicazione.getLavoratoreCoObbligato().setIdLavoratoreSilp(new BigDecimal(dtoSpicom
							.getDatiAggiuntivi().getDatiAggiuntiviLavoratoreCoobbligato().getIdAnagraficaSilp()));
				}
				// **************************************************************************************************

			}
		}

		// ****************************************************************************************************************************
		// dati tutore
		// ****************************************************************************************************************************
		handleDatiTutore(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati blocco tirocinio
		// ****************************************************************************************************************************
		handleDatiBloccoTirocinio(comunicazione, dtoSpicom, decodificaDad, errors);

		// campo tipo tracciato obbligatorio
		// UL (Uni Lav), US (Uni Somm), UG (Urgenza), VD (Var Datori)
		String tipoTracciato = "";
		if (ControlliSuOggettoComunicazione.isSpicom_VARDATORI(dtoSpicom)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID;
		} else if (ControlliSuOggettoComunicazione.isSpicom_UNIURG(dtoSpicom)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_URGENZA_ID;
		} else if (ControlliSuOggettoComunicazione.isSpicom_UNISOMM(dtoSpicom)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID;
			comunicazione.setFlgSommin(ComonlConstants.FLAG_S);
		} else if (ControlliSuOggettoComunicazione.isSpicom_UNIDOM(dtoSpicom)) {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID;
		} else {
			tipoTracciato = ComonlConstants.TIPO_TRACCIATO_UNILAV_ID;
		}
		comunicazione.setTipoTracciato(
				ComonlUtility.isNotVoid(comunicazione.getTipoTracciato()) ? comunicazione.getTipoTracciato()
						: new TipoTracciato());
		comunicazione.getTipoTracciato().setId(tipoTracciato);

		// ****************************************************************************************************************************
		// dichiarazioni ulteriori (caso di tracciato UNIDOM) FIXME il tracciato
		// unidom
		// non dovrebbe esistere più se non in import
		// per le vecchie CO. Commento questa parte perchè questi dati non ci
		// sono- E'
		// da rivedere
		// ****************************************************************************************************************************
		if (tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID)) {
			dtoSpicom.setDichiarazioniUnidom(new DichiarazioniUnidomDTO());

			// if(Varie.isNotVoid(comunicazioneBo.getInizioRapporto().getDatoreConiugeLavoratore()))
			// {
			// dto.getDichiarazioniUnidom().setFlgDatoreLavoroConiugeLavoratore(
			// comunicazioneBo.getInizioRapporto().getDatoreConiugeLavoratore().booleanValue()
			// ? Costanti.SI : Costanti.NO);
			// }
			// if(Varie.isNotVoid(comunicazioneBo.getInizioRapporto().getDatoreConviventeLavoratore()))
			// {
			// dto.getDichiarazioniUnidom().setFlgDatoreLavoroConviventeLavoratore(
			// comunicazioneBo.getInizioRapporto().getDatoreConviventeLavoratore().booleanValue()
			// ? Costanti.SI : Costanti.NO);
			// }
			// if(Varie.isNotVoid(comunicazioneBo.getInizioRapporto().getDatoreInvalido()))
			// {
			// dto.getDichiarazioniUnidom().setFlgDatoreLavoroInvalido(
			// comunicazioneBo.getInizioRapporto().getDatoreInvalido().booleanValue()
			// ?
			// Costanti.SI : Costanti.NO);
			// }
			// if(Varie.isNotVoid(comunicazioneBo.getInizioRapporto().getDatoreParenteLavoratore()))
			// {
			// dto.getDichiarazioniUnidom().setFlgDatoreLavoroParenteLavoratore(
			// comunicazioneBo.getInizioRapporto().getDatoreParenteLavoratore().booleanValue()
			// ? Costanti.SI : Costanti.NO);
			// }
		}

		// **************************************************************************************************

		// ****************************************************************************************************************************
		// dati del datore
		// ****************************************************************************************************************************
		handleDatiDelDatore(comunicazione, dtoSpicom, tipoTracciato, decodificaDad, errors);
		// ****************************************************************************************************************************
		// dati del legale rappresentante e del titolo di soggiorno
		// ****************************************************************************************************************************
		handleLegaleRappAndTitoloSoggiorno(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati del lavoratore
		// ****************************************************************************************************************************
		handleDatiDelLavoratore(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati del lavoratore co-obbligato
		// ****************************************************************************************************************************
		handleDatiDelLavoratoreCoObbligato(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati della cessazione
		// ****************************************************************************************************************************
		handleDatiDellaCessazione(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati della proroga
		// ****************************************************************************************************************************
		handleDatiDellaProroga(comunicazione, dtoSpicom);

		// ****************************************************************************************************************************
		// dati della trasformazione
		// ****************************************************************************************************************************
		handleDatiDellaTrasformazione(comunicazione, dtoSpicom, decodificaDad, errors);

		// ****************************************************************************************************************************
		// dati del trasferimento, distacco
		// ****************************************************************************************************************************
		handleDatiDelTrasferimento(comunicazione, dtoSpicom, decodificaDad, errors);

		setTipoSomministrazione(comunicazione,dtoSpicom);
		
		return comunicazione;
	}

	private String estraiCodiceComune() {
		String codiceComune = null;
		if (ControlliSuOggettoComunicazione.isSpicom_VARDATORI(dtoSpicom)) {
			if (dtoSpicom.getDatoreDiLavoro() != null) {
				codiceComune = dtoSpicom.getDatoreDiLavoro().getComuneSedeLegale();
			} else {
				if (dtoSpicom.getVariazioneRagioneSociale() != null
						&& dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale() != null) {
					codiceComune = dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getComuneSedeLegale();
				}
				if (dtoSpicom.getTrasferimentoRamoAziendale() != null
						&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale() != null) {
					codiceComune = dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
							.getComuneSedeLegale();
				}
			}
		} else {
			codiceComune = dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro();
		}
		return codiceComune;
	}

	protected void handleDatiDelTrasferimento(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {
		// cod trasferimento che valori può avere?
		// FIXME 18/07/2022 - DA RIVEDERE condizione MAI soddisfatta per isSpicom_TRASFERIMENTO
		if (dtoSpicom.getTrasferimentoRamoAziendale() != null
				&& ControlliSuOggettoComunicazione.isSpicom_TRASFERIMENTO(dtoSpicom)) {

			// Settaggio del codice ministeriale del tipo trasferimento
			// ----------------------------------------------------------------------------------

			if (dtoSpicom.getTrasformazione().getCodTrasformazione() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getTrasformazione().getCodTrasformazione())) {

				Trasformazionerl trasformazionerl = (Trasformazionerl) decodificaDad.getTfromMin(Trasformazionerl.class,
						dtoSpicom.getTrasformazione().getCodTrasformazione(), dataRiferimento);

				if (trasformazionerl == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_TRASFORMAZIONERL,
							dtoSpicom.getTrasformazione().getCodTrasformazione(),
							"TRASFERIMENTO Trasformazione - CodTrasformazione");
				}
				comunicazione.getRapporto().setTrasformazionerl(trasformazionerl);
			}

			comunicazione.getRapporto().setDtTrasformazione(dtoSpicom.getTrasformazione().getDataTrasformazione());
			comunicazione.setDataRiferimento(dtoSpicom.getTrasformazione().getDataTrasformazione());

			if (dtoSpicom.getTrasformazione().getFlgDistaccoParziale() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getFlgDistaccoParziale())) {
				comunicazione.getRapporto().setFlgDistaccoParziale(ComonlConstants.FLAG_S);
			} else {
				comunicazione.getRapporto().setFlgDistaccoParziale(ComonlConstants.FLAG_N);
			}

			if (dtoSpicom.getTrasformazione().getFlgDistaccoAziendaEstera() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getFlgDistaccoAziendaEstera())) {
				comunicazione.getRapporto().setFlgDistaccoSuAziEstera(ComonlConstants.FLAG_S);
			} else {
				comunicazione.getRapporto().setFlgDistaccoSuAziEstera(ComonlConstants.FLAG_N);
			}

			Datore datoreDistaccatario = (ComonlUtility.isNotVoid(comunicazione.getRapporto().getDatoreDistaccatario())
					? comunicazione.getRapporto().getDatoreDistaccatario()
					: new Datore());

			if (ComonlConstants.COD_MIN_TIPO_CO_DISTACCO
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getCodTrasformazione())) {

				if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco() != null
						&& dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getCodFiscale() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getCodFiscale())) {
					datoreDistaccatario.setCodiceFiscale(
							dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getCodFiscale().toUpperCase());
				}
				if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco() != null
						&& dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getDenominazione() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getDenominazione())) {
					datoreDistaccatario.setDsDenominazioneDatore(ComonlStringUtils.formatDescrDatore(
							dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getDenominazione()));
				}
				if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco() != null
						&& dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getSettore() != null
						&& !"".equalsIgnoreCase(dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getSettore())) {

					Atecofin atecofin = (Atecofin) decodificaDad.getTfromMin(Atecofin.class,
							dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getSettore(), dataRiferimento);

					if (atecofin == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ATECOFIN,
								dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getSettore(),
								"TRASFERIMENTO DatoreLavoroDistacco - Settore");
					}

					datoreDistaccatario.setAtecofin(atecofin);
				}

				if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco() != null
						&& dtoSpicom.getTrasformazione().getPatInail() != null) {
					datoreDistaccatario.setPatInail(dtoSpicom.getTrasformazione().getPatInail());
				}

				if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco() != null) {

					SedeLavoro sedeLavoroOperativa = (ComonlUtility.isNotVoid(datoreDistaccatario.getSedeOperativa())
							? datoreDistaccatario.getSedeOperativa()
							: new SedeLavoro());
					datoreDistaccatario.setSedeOperativa(sedeLavoroOperativa);
					sedeLavoroOperativa.setFlgSedeLegale(ComonlConstants.FLAG_S);

					if (dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro() != null
							&& !"".equalsIgnoreCase(
									dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro())) {

						if (!dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro()
								.startsWith("Z")) {
							Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
									dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro(),
									dataRiferimento);
							sedeLavoroOperativa.setComune(comune);
							if (comune == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
										dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro(),
										"TO COMUNICAZIONE COMONL DatoreLavoroDistacco - ComuneSedeLavoro");
							}
						} else {
							StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
									dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro(),
									dataRiferimento);
							if (statiEsteri != null) {
								sedeLavoroOperativa.setStatiEsteri(statiEsteri);
							} else {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
										dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getComuneSedeLavoro(),
										"TO COMUNICAZIONE COMONL DatoreLavoroDistacco - ComuneSedeLavoro");
							}

						}

					}

					sedeLavoroOperativa.setCodCap(ComonlUtility
							.isNotVoid(dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getCapSedeLavoro())
									? dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getCapSedeLavoro()
									: null);

					sedeLavoroOperativa.setIndirizzo(
							dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getIndirizzoSedeLavoro());

					sedeLavoroOperativa.setTelefono(ComonlUtility
							.isNotVoid(dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getTelefonoSedeLavoro())
									? dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getTelefonoSedeLavoro()
									: null);

					sedeLavoroOperativa.setFax(ComonlUtility
							.isNotVoid(dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getFaxSedeLavoro())
									? dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getFaxSedeLavoro()
									: null);

					sedeLavoroOperativa.setEmail(ComonlUtility
							.isNotVoid(dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getEmailSedeLavoro())
									? dtoSpicom.getTrasformazione().getDatoreLavoroDistacco().getEmailSedeLavoro()
									: null);
				}

			} else {

				if (dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente() != null
						&& !"".equalsIgnoreCase(dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente())) {

					SedeLavoro sedeLavoroPrecedente = (ComonlUtility.isNotVoid(datoreDistaccatario.getSedeOperativa())
							? datoreDistaccatario.getSedeOperativa()
							: new SedeLavoro());
					datoreDistaccatario.setSedeOperativa(sedeLavoroPrecedente);
					sedeLavoroPrecedente.setFlgSedeLegale(ComonlConstants.FLAG_S);

					if (!dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente().startsWith("Z")) {
						Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
								dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(), dataRiferimento);
						sedeLavoroPrecedente.setComune(comune);
						if (comune == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
									dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(),
									"TO COMUNICAZIONE COMONL ComuneSedeLavoroPrecedente");
						}
					} else {
						StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(), dataRiferimento);
						if (statiEsteri != null) {
							sedeLavoroPrecedente.setStatiEsteri(statiEsteri);
						} else {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
									dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(),
									"TO COMUNICAZIONE COMONL ComuneSedeLavoroPrecedente");
						}

					}

					sedeLavoroPrecedente.setIndirizzo(dtoSpicom.getTrasformazione().getIndirizzoSedeLavoroPrecedente());
				}
			}

			comunicazione.getRapporto().setDatoreDistaccatario(datoreDistaccatario);
		}
	}

	protected void handleDatiDellaTrasformazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dto.getDatiInizio() != null && dto.getDatiVariazione() != null) {
			DatiTrasformazioneDTO datiTrasformazione = dto.getDatiVariazione().getDatiTrasformazione();

			if (datiTrasformazione != null) {

				String codVar = dto.getDatiVariazione().getCodiceVariazione();

				Trasformazionerl trasformazionerl = (Trasformazionerl) decodificaDad.getTfromMin(Trasformazionerl.class,
							datiTrasformazione.getCodiceTrasformazione(),
							dataRiferimento);

					if (trasformazionerl == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TRASFORMAZIONERL,
								dto.getTrasformazione().getCodTrasformazione(), "TRASFORMAZIONE CodTrasformazione");
					}

					if (codVar.equals(UtilConstant.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_COSTANZA_DI_MISSIONE)) {
						comunicazione.getMissione().setDtTrasformazione(datiTrasformazione.getDataTrasformazione());
						comunicazione.getMissione().setTrasformazionerl(trasformazionerl);

					} else if (codVar.equals(UtilConstant.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE)) {
						comunicazione.getRapporto().setDtTrasformazione(datiTrasformazione.getDataTrasformazione());
					}
					else if (codVar.equals(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE)) {
						comunicazione.getMissione().setDtTrasformazione(datiTrasformazione.getDataTrasformazione());
						comunicazione.getMissione().setTrasformazionerl(trasformazionerl);
					}
					else if (codVar.equals(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA)) {
						comunicazione.getRapporto().setDtTrasformazione(datiTrasformazione.getDataTrasformazione());
						comunicazione.getRapporto().setTrasformazionerl(trasformazionerl);
					}
				}
			}

		else if (dto.getTrasformazione() != null) {
			comunicazione.getRapporto().setDtTrasformazione(dto.getTrasformazione().getDataTrasformazione());

			Trasformazionerl trasformazionerl = (Trasformazionerl) decodificaDad.getTfromMin(Trasformazionerl.class,
					dto.getTrasformazione().getCodTrasformazione(),dataRiferimento);

			if (trasformazionerl == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_TRASFORMAZIONERL,
						dto.getTrasformazione().getCodTrasformazione(), "TRASFORMAZIONE CodTrasformazione");
			}

			comunicazione.getRapporto().setTrasformazionerl(trasformazionerl);
			
			// Tesi 21/09/2022
			if (dtoSpicom.getTrasformazione().getFlgDistaccoParziale() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getFlgDistaccoParziale())) {
				comunicazione.getRapporto().setFlgDistaccoParziale(ComonlConstants.FLAG_S);
			} else {
				comunicazione.getRapporto().setFlgDistaccoParziale(ComonlConstants.FLAG_N);
			}
			// Scarponi 29/09/2022
			if (dtoSpicom.getTrasformazione().getFlgDistaccoAziendaEstera() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getFlgDistaccoAziendaEstera())) {
				comunicazione.getRapporto().setFlgDistaccoSuAziEstera(ComonlConstants.FLAG_S);
			} else {
				comunicazione.getRapporto().setFlgDistaccoSuAziEstera(ComonlConstants.FLAG_N);
			}

			// datore
		    if(dto.getTrasformazione().getDatoreLavoroDistacco() != null) {
		    	DatoreDiLavoroDTO datoreDto = dto.getTrasformazione().getDatoreLavoroDistacco();
		    	Datore datore = new Datore();
		    	datore.setCodiceFiscale(datoreDto.getCodFiscale());
		    	datore.setDsDenominazioneDatore(datoreDto.getDenominazione());

		    	if(datoreDto.getSettore() != null) {
			    	Atecofin atecofin = (Atecofin)decodificaDad.getTfromMin(Atecofin.class, datoreDto.getSettore(), dataRiferimento);
			    	datore.setAtecofin(atecofin);
			    	if(atecofin == null) {
			    		addErrorComDImportErrore(errors, ComonlConstants.COM_T_ATECOFIN,
			    				datoreDto.getSettore(), "TRASFORMAZIONE Settore");
			    	}
		    	}

		    	datore.setPatInail(dto.getTrasformazione().getPatInail());

		    	// solo sede operativa
		    	if(datoreDto.getComuneSedeLavoro() != null ) {
		    		SedeLavoro sede = new SedeLavoro();
		    		datore.setSedeLegale(sede);

		    		sede.setEmail(datoreDto.getEmailSedeLavoro());
		    		sede.setCodCap(datoreDto.getCapSedeLavoro());
		    		sede.setFax(datoreDto.getFaxSedeLavoro());
		    		sede.setTelefono(datoreDto.getTelefonoSedeLavoro());
		    		sede.setIndirizzo(datoreDto.getIndirizzoSedeLavoro());
		    		sede.setFlgSedeLegale(UtilConstant.FLAG_S);

		    		if(datoreDto.getComuneSedeLavoro() != null) {

		    			if(datoreDto.getComuneSedeLavoro().startsWith("Z")) {
		    				StatiEsteri stato = (StatiEsteri)decodificaDad.getTfromMin(StatiEsteri.class, datoreDto.getComuneSedeLavoro(), dataRiferimento);
		    				sede.setStatiEsteri(stato);

		    				if(stato == null) {
					    		addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
					    				datoreDto.getComuneSedeLavoro(), "TRASFORMAZIONE ComuneSedeLavoro");
		    				}

		    			} else {
			    			Comune comune = (Comune)decodificaDad.getTfromMin(Comune.class, datoreDto.getComuneSedeLavoro(), dataRiferimento);
			    			sede.setComune(comune);
		    				if(comune == null) {
					    		addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
					    				datoreDto.getComuneSedeLavoro(), "TRASFORMAZIONE ComuneSedeLavoro");
		    				}
		    			}
		    		}
		    	}
		    	comunicazione.getRapporto().setDatoreDistaccatario(datore);
		    }
		    // 18/07/2022 Daniela: if per salvare i dati del trasferimento caso unilav.
		    if(ComonlConstants.TIPO_TRASFORMAZIONERL_MIN_TL
					.equalsIgnoreCase(dtoSpicom.getTrasformazione().getCodTrasformazione())) {
		    	if (dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente() != null
						&& !"".equalsIgnoreCase(dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente())) {

					SedeLavoro sedeLavoroPrecedente =  new SedeLavoro();
					sedeLavoroPrecedente.setFlgSedeLegale(ComonlConstants.FLAG_S);

					if (!dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente().startsWith("Z")) {
						Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
								dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(), dataRiferimento);
						sedeLavoroPrecedente.setComune(comune);
						if (comune == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
									dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(),
									"TO COMUNICAZIONE COMONL ComuneSedeLavoroPrecedente");
						}
					} else {
						StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(), dataRiferimento);
						if (statiEsteri != null) {
							sedeLavoroPrecedente.setStatiEsteri(statiEsteri);
						} else {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
									dtoSpicom.getTrasformazione().getComuneSedeLavoroPrecedente(),
									"TO COMUNICAZIONE COMONL ComuneSedeLavoroPrecedente");
						}
					}
					sedeLavoroPrecedente.setIndirizzo(dtoSpicom.getTrasformazione().getIndirizzoSedeLavoroPrecedente());
					comunicazione.getRapporto().setSedeLavoroPrecedente(sedeLavoroPrecedente);
				}
		    }
		}
	}


	private void setTipoSomministrazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {
/*
0	PRO	2.01	PROROGA  DEL RAPPORTO DI LAVORO IN ASSENZA DI MISSIONE
2	PRO	2.02	PROROGA DEL RAPPORTO DI LAVORO E DELLA MISSIONE
1	PRO	2.03	PROROGA DELLA MISSIONE IN CASO DI RAPPORTO A TEMPO INDETERMINATO
1	TRS	3.01	TRASFORMAZIONE DEL RAPPORTO DI LAVORO IN COSTANZA DI MISSIONE
0	TRS	3.02	TRASFORMAZIONE DEL RAPPORTO DI LAVORO  IN ASSENZA DI MISSIONE
1	TRD	3.03	TRASFERIMENTO ALTRA SEDE LAVORO DITTA UTILIZZATRICE
0	TRD	3.04	TRASFERIMENTO ALTRA SEDE OPERATIVA AGENZIA
1	CES	4.01	CESSAZIONE DELLA MISSIONE
0	CES	4.02	CESSAZIONE DEL RAPPORTO DI LAVORO
 * */
		if (!tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
			return;
		}

		TipoSomministrazione tipoSomministrazione = new TipoSomministrazione();

		if ("ASS".equals(comunicazione.getTipoComunicazione().getId())){
			if (comunicazione.getMissione()!=null) {
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
			}else {
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE);
			}
			comunicazione.setTipoSomministrazione(tipoSomministrazione);
			return;
		}

		DatiVariazioneDTO datiVariazioneDto = dto.getDatiVariazione();
		if (datiVariazioneDto != null) {
			String codVar = datiVariazioneDto.getCodiceVariazione();
			if(UtilConstant.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE.equals(codVar)) {
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE);
			}else if(UtilConstant.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE.equals(codVar)) {
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE);
			}else if(UtilConstant.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE);
			}else if(UtilConstant.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_COSTANZA_DI_MISSIONE.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE);
			}else if(UtilConstant.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE);
			}else if(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE);
			}else if(UtilConstant.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE);
			}else if(UtilConstant.CESSAZIONE_DELLA_MISSIONE.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE_MISSIONE);
			}else if(UtilConstant.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO.equals(codVar)){
				tipoSomministrazione.setId(ComonlConstants.TIPO_SOMMINISTRAZIONE);
			}else {
				tipoSomministrazione = null;
			}
			comunicazione.setTipoSomministrazione(tipoSomministrazione);
		}
	}

	protected void handleDatiDellaProroga(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto) {

		if (dto.getDatiInizio() != null && dto.getDatiVariazione() != null) {
			DatiProrogaDTO datiProroga = dto.getDatiVariazione().getDatiProroga();
			if(datiProroga != null) {
				String codVar = dto.getDatiVariazione().getCodiceVariazione();
				if(codVar.equals(UtilConstant.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE)) {
					comunicazione.getMissione().setDtFineProroga(datiProroga.getDataFineProroga());
					comunicazione.getRapporto().setDtFineProroga(datiProroga.getDataFineProroga());

				} else if(codVar.equals(UtilConstant.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO)){
					comunicazione.getMissione().setDtFineProroga(datiProroga.getDataFineProroga());

				} else if(codVar.equals(UtilConstant.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE)){
					comunicazione.getRapporto().setDtFineProroga(datiProroga.getDataFineProroga());

				}

				if (datiProroga.getDataFineProroga().after(dto.getDatiInvio().getDataInvio())) {
					comunicazione.setDataRiferimento(dto.getDatiInvio().getDataInvio());
				} else {
					comunicazione.setDataRiferimento(datiProroga.getDataFineProroga());
				}
			}
		}
		else if (dto.getProroga() != null) {
			comunicazione.getRapporto().setDtFineProroga(dto.getProroga().getDataFineProroga());
		}
	}

	protected void handleDatiDellaCessazione(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if(dto.getDatiInizio() != null && dto.getDatiVariazione() != null) {
			DatiCessazioneDTO datiCessazione = dto.getDatiVariazione().getDatiCessazione();
			if(datiCessazione != null) {
				String codVar = dto.getDatiVariazione().getCodiceVariazione();

				Cessazionerl cessazionerl = (Cessazionerl) decodificaDad.getTfromMin(Cessazionerl.class,
						datiCessazione.getCodiceCausa(), dataRiferimento);

				if (cessazionerl == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_CESSAZIONERL,
							dto.getCessazione().getCodCausa(), "CESSAZIONE CodCausa");
				}

				if(codVar.equals(UtilConstant.CESSAZIONE_DELLA_MISSIONE)) {
					comunicazione.getMissione().setDtCessazione(datiCessazione.getDataCessazione());
					comunicazione.getMissione().setCessazionerl(cessazionerl);

				} else if(codVar.equals(UtilConstant.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO)){
					comunicazione.getRapporto().setDtCessazione(datiCessazione.getDataCessazione());
					comunicazione.getRapporto().setCessazionerl(cessazionerl);
				}
			}
		}
		else if (dto.getCessazione() != null) {

			comunicazione.getRapporto().setDtCessazione(dto.getCessazione().getDataCessazione());
			Cessazionerl cessazionerl = (Cessazionerl) decodificaDad.getTfromMin(Cessazionerl.class,
					dto.getCessazione().getCodCausa(),dataRiferimento);

			if (cessazionerl == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_CESSAZIONERL,
						dto.getCessazione().getCodCausa(), "CESSAZIONE CodCausa");
			}

			comunicazione.getRapporto().setCessazionerl(cessazionerl);
		}
	}

	protected void handleDatiDelLavoratoreCoObbligato(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dto.getLavoratoreCoobbligato() != null) {

			Lavoratore lavoratoreCoObbligato = (ComonlUtility.isNotVoid(comunicazione.getLavoratoreCoObbligato())
					? comunicazione.getLavoratoreCoObbligato()
					: new Lavoratore());

			lavoratoreCoObbligato.setCodCapDom(dto.getLavoratoreCoobbligato().getCapDomicilio());

			if (dto.getLavoratoreCoobbligato().getCittadinanza() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getCittadinanza())) {

				Cittadinanza cittadinanza = (Cittadinanza) decodificaDad.getTfromMin(Cittadinanza.class,
						dto.getLavoratoreCoobbligato().getCittadinanza(), dataRiferimento);

				if (cittadinanza == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_CITTADINANZA,
							dto.getLavoratoreCoobbligato().getCittadinanza(),
							"LAVORATORECOOBBLIGATO LavoratoreCoobbligato - Cittadinanza");
				}

				lavoratoreCoObbligato.setCittadinanza(cittadinanza);
			}

			lavoratoreCoObbligato.setCodiceFiscale(
					ComonlStringUtils.eliminaCaratteriSpeciali(dto.getLavoratoreCoobbligato().getCodFiscale()));
			lavoratoreCoObbligato.setCognome(
					ComonlStringUtils.eliminaCaratteriSpeciali(dto.getLavoratoreCoobbligato().getCognome()));

			if (dto.getLavoratoreCoobbligato().getComuneDomicilio() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getComuneDomicilio())) {

				Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
						dto.getLavoratoreCoobbligato().getComuneDomicilio(), dataRiferimento);

				if (comune == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
							dto.getLavoratoreCoobbligato().getComuneDomicilio(),
							"LAVORATORECOOBBLIGATO LavoratoreCoobbligato - ComuneDomicilio");
				}

				lavoratoreCoObbligato.setComuneDom(comune);
			}

			if (dto.getLavoratoreCoobbligato() != null
					&& dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita())) {

				if (!dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita().startsWith("Z")) {
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
							dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita(),
							dto.getLavoratoreCoobbligato().getDataNascita());
					lavoratoreCoObbligato.setComuneNasc(comune);
					if (comune == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita(),
								"LAVORATORECOOBBLIGATO LavoratoreCoobbligato - ComuneOStatoEsteroNascita");
					}
				} else {
					StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
							dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita(),
							dto.getLavoratoreCoobbligato().getDataNascita());
					if (statiEsteri != null) {
						lavoratoreCoObbligato.setStatiEsteriNasc(statiEsteri);
					} else {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dto.getLavoratoreCoobbligato().getComuneOStatoEsteroNascita(),
								"LAVORATORECOOBBLIGATO LavoratoreCoobbligato - ComuneOStatoEsteroNascita");
					}

				}

			}

			if (dto.getLavoratoreCoobbligato().getDataNascita() != null) {
				lavoratoreCoObbligato.setDtNascita(dto.getLavoratoreCoobbligato().getDataNascita());
			}

			if (dto.getLavoratoreCoobbligato().getDataScadenzaPermessoSoggiorno() != null) {
				lavoratoreCoObbligato
						.setDtScadenzaPermessoSogg(dto.getLavoratoreCoobbligato().getDataScadenzaPermessoSoggiorno());
			}

			if (dto.getLavoratoreCoobbligato().getIndirizzoDomicilio() != null) {
				lavoratoreCoObbligato.setDsIndirizzoDom(ComonlStringUtils
						.eliminaCaratteriSpeciali(dto.getLavoratoreCoobbligato().getIndirizzoDomicilio()));
			}

			if (dto.getLavoratoreCoobbligato().getLivelloIstruzione() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getLivelloIstruzione())) {

				LivelloStudio livelloStudio = (LivelloStudio) decodificaDad.getTfromMin(LivelloStudio.class,
						dto.getLavoratoreCoobbligato().getLivelloIstruzione(), dataRiferimento);

				if (livelloStudio == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_LIVELLO_STUDIO,
							dto.getLavoratoreCoobbligato().getComuneDomicilio(),
							"LAVORATORECOOBBLIGATO LivelloIstruzione");
				}

				lavoratoreCoObbligato.setLivelloStudio(livelloStudio);

			}

			if (dto.getLavoratoreCoobbligato().getMotivoPermesso() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getMotivoPermesso())) {

				MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(MotivoPermesso.class,
						dto.getLavoratoreCoobbligato().getMotivoPermesso(), dataRiferimento);

				if (motivoPermesso == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_MOTIVO_PERMESSO,
							dto.getLavoratoreCoobbligato().getMotivoPermesso(),
							"LAVORATORECOOBBLIGATO ùMotivoPermesso");
				}

				lavoratoreCoObbligato.setMotivoPermesso(motivoPermesso);

			}

			lavoratoreCoObbligato
					.setNome(ComonlStringUtils.eliminaCaratteriSpeciali(dto.getLavoratoreCoobbligato().getNome()));

			if (dto.getLavoratoreCoobbligato().getNumeroDocumento() != null) {
				lavoratoreCoObbligato
						.setNumeroDocumento(dto.getLavoratoreCoobbligato().getNumeroDocumento().equals("") ? null
								: dto.getLavoratoreCoobbligato().getNumeroDocumento());
			}

			lavoratoreCoObbligato.setSesso(dto.getLavoratoreCoobbligato().getSesso());

			if (dto.getLavoratoreCoobbligato().getTipoDocumento() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getTipoDocumento())) {

				lavoratoreCoObbligato
						.setStatusStraniero(ComonlUtility.isNotVoid(lavoratoreCoObbligato.getStatusStraniero())
								? lavoratoreCoObbligato.getStatusStraniero()
								: new StatusStraniero());

				lavoratoreCoObbligato.getStatusStraniero()
						.setCodStatusMin(dto.getLavoratoreCoobbligato().getTipoDocumento());
			}

			if (dto.getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dto.getLavoratoreCoobbligato().getFlgPagamSpesaRimpatrio())) {
				lavoratoreCoObbligato.setFlgRimborsoRimpatrio(ComonlConstants.FLAG_S);
			}

			if (dto.getLavoratoreCoobbligato().getFlgSussistSistemAllog() != null && ComonlConstants.FLAG_SI_DESC
					.equalsIgnoreCase(dto.getLavoratoreCoobbligato().getFlgSussistSistemAllog())) {
				lavoratoreCoObbligato.setFlgSistAlloggiativa(ComonlConstants.FLAG_S);
			}

			if (dto.getLavoratoreCoobbligato().getQuesturaRilascioTitoloSoggiorno() != null
					&& !"".equalsIgnoreCase(dto.getLavoratoreCoobbligato().getQuesturaRilascioTitoloSoggiorno())) {

				Questura questura = (Questura) decodificaDad.getTfromMin(Questura.class,
						dto.getLavoratoreCoobbligato().getQuesturaRilascioTitoloSoggiorno(), dataRiferimento);

				if (questura == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_QUESTURA,
							dto.getLavoratoreCoobbligato().getQuesturaRilascioTitoloSoggiorno(),
							"LAVORATORECOOBBLIGATO QuesturaRilascioTitoloSoggiorno");
				}

				lavoratoreCoObbligato.setQuestura(questura);
			}

			comunicazione.setLavoratoreCoObbligato(lavoratoreCoObbligato);
		}

	}

	protected void handleDatiDelLavoratore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dto.getLavoratore() != null) {

			comunicazione.setLavoratori(new ArrayList<Lavoratore>());
			// FLAG MULTI LAVORATORE - da spicom è sempre N perchè viene un lavoratore per volta
			comunicazione.setFlgMultiLav(ComonlConstants.FLAG_N);
			
			Lavoratore lavoratore = new Lavoratore();

			Date dataNascita = dto.getLavoratore().getDataNascita();

			lavoratore.setCodCapDom(dto.getLavoratore().getCapDomicilio());

			if (dto.getLavoratore() != null && dto.getLavoratore().getCittadinanza() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getCittadinanza())) {

				Cittadinanza cittadinanza = (Cittadinanza) decodificaDad.getTfromMin(Cittadinanza.class,
						dto.getLavoratore().getCittadinanza(), dataRiferimento);

				if (cittadinanza == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_CITTADINANZA,
							dto.getLavoratore().getCittadinanza(), "LAVORATORE Cittadinanza");
				}

				lavoratore.setCittadinanza(cittadinanza);
			}

			lavoratore.setCodiceFiscale(dto.getLavoratore().getCodFiscale());
			lavoratore.setCognome(dto.getLavoratore().getCognome().trim().toUpperCase());

			if (dto.getLavoratore().getComuneDomicilio() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getComuneDomicilio())) {

				Comune comuneDomicilio = (Comune) decodificaDad.getTfromMin(Comune.class,
						dto.getLavoratore().getComuneDomicilio(), dataRiferimento);

				if (comuneDomicilio == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
							dto.getLavoratore().getComuneDomicilio(), "LAVORATORE ComuneDomicilio");
				}

				lavoratore.setComuneDom(comuneDomicilio);
			}

			if (dto.getLavoratore().getComuneDomicilio() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getComuneDomicilio())) {

				if (!dto.getLavoratore().getComuneDomicilio().startsWith("Z")) {
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
							dto.getLavoratore().getComuneDomicilio(), dataRiferimento);
					lavoratore.setComuneDom(comune);
					if (comune == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dto.getLavoratore().getComuneDomicilio(), "LAVORATORE ComuneDomicilio");
					}
				} else {
					StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
							dto.getLavoratore().getComuneDomicilio(), dataRiferimento);
					if (statiEsteri != null) {
						lavoratore.setStatiEsteriDom(statiEsteri);
					} else {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dto.getLavoratore().getComuneDomicilio(), "LAVORATORE ComuneDomicilio");
					}

				}

			}

			if (dto.getLavoratore().getComuneOStatoEsteroNascita() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getComuneOStatoEsteroNascita())) {

				if (!dto.getLavoratore().getComuneOStatoEsteroNascita().startsWith("Z")) {
					Comune comuneNasc = (Comune) decodificaDad.getTfromMin(Comune.class,
							dto.getLavoratore().getComuneOStatoEsteroNascita(), dto.getLavoratore().getDataNascita());
					
					if(comuneNasc == null) {
						comuneNasc = (Comune) decodificaDad.getTfromMin(Comune.class,
								dto.getLavoratore().getComuneOStatoEsteroNascita(), null);
					}
					
					lavoratore.setComuneNasc(comuneNasc);
					if (comuneNasc == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dto.getLavoratore().getComuneOStatoEsteroNascita(),
								"LAVORATORE ComuneOStatoEsteroNascita");
					}
				} else {
					StatiEsteri statiEsteriNasc = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
							dto.getLavoratore().getComuneOStatoEsteroNascita(), dto.getLavoratore().getDataNascita());
					if(statiEsteriNasc == null) {
						statiEsteriNasc = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dto.getLavoratore().getComuneOStatoEsteroNascita(), null);
					}
					
					if (statiEsteriNasc != null) {
						lavoratore.setStatiEsteriNasc(statiEsteriNasc);
					} else {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dto.getLavoratore().getComuneOStatoEsteroNascita(),
								"LAVORATORE ComuneOStatoEsteroNascita");
					}

				}

			}

			if (dto.getLavoratore().getDataNascita() != null) {
				lavoratore.setDtNascita(dto.getLavoratore().getDataNascita());
			}

			if (dto.getLavoratore().getDataScadenzaPermessoSoggiorno() != null) {
				lavoratore.setDtScadenzaPermessoSogg(dto.getLavoratore().getDataScadenzaPermessoSoggiorno());
			}

			if (dto.getLavoratore().getIndirizzoDomicilio() != null) {
				lavoratore.setDsIndirizzoDom(dto.getLavoratore().getIndirizzoDomicilio());
			}

			if (dto.getLavoratore().getLivelloIstruzione() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getLivelloIstruzione())) {

				LivelloStudio livelloStudio = (LivelloStudio) decodificaDad.getTfromMin(LivelloStudio.class,
						dto.getLavoratore().getLivelloIstruzione(), dataRiferimento);

				if (livelloStudio == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_LIVELLO_STUDIO,
							dto.getLavoratore().getLivelloIstruzione(), "LAVORATORE LivelloIstruzione");
				}

				lavoratore.setLivelloStudio(livelloStudio);

			}

			if (dto.getLavoratore().getMotivoPermesso() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getMotivoPermesso())) {

				MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(MotivoPermesso.class,
						dto.getLavoratore().getMotivoPermesso(), dataRiferimento);

				if (motivoPermesso == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_MOTIVO_PERMESSO,
							dto.getLavoratore().getMotivoPermesso(), "LAVORATORE MotivoPermesso");
				}

				lavoratore.setMotivoPermesso(motivoPermesso);

			}

			// Riccardo Leccese 13/07/2007
			// Aggiunti questi valori perche' necessari e richiesti da SPICOM
			if (dto.getLavoratore().getCapDomicilio() != null) {
				lavoratore.setCodCapDom(dto.getLavoratore().getCapDomicilio());
			}
			if (dto.getLavoratore().getIndirizzoDomicilio() != null) {
				lavoratore.setDsIndirizzoDom(dto.getLavoratore().getIndirizzoDomicilio());
			}

			lavoratore.setNome(ComonlStringUtils.eliminaCaratteriSpeciali(dto.getLavoratore().getNome().trim().toUpperCase()));

			if (dto.getLavoratore().getNumeroDocumento() != null) {
				lavoratore.setNumeroDocumento(dto.getLavoratore().getNumeroDocumento().equals("") ? null
						: dto.getLavoratore().getNumeroDocumento());
			}

			lavoratore.setSesso(dto.getLavoratore().getSesso());

			if (dto.getLavoratore().getTipoDocumento() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getTipoDocumento())) {

				StatusStraniero statusStraniero = (StatusStraniero) decodificaDad.getTfromMin(StatusStraniero.class,
						dto.getLavoratore().getTipoDocumento(), dataRiferimento);

				if (statusStraniero == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATUS_STRANIERO,
							dto.getLavoratore().getTipoDocumento(), "LAVORATORE TipoDocumento");
				}

				lavoratore.setStatusStraniero(statusStraniero);
			}

			if (dto.getLavoratore().getQuesturaRilascioTitoloSoggiorno() != null
					&& !"".equalsIgnoreCase(dto.getLavoratore().getQuesturaRilascioTitoloSoggiorno())) {

				Questura questuraRilascioTitoloSoggiorno = (Questura) decodificaDad.getTfromMin(Questura.class,
						dto.getLavoratore().getQuesturaRilascioTitoloSoggiorno(), dataRiferimento);

				if (questuraRilascioTitoloSoggiorno == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_QUESTURA,
							dto.getLavoratore().getQuesturaRilascioTitoloSoggiorno(),
							"LAVORATORE QuesturaRilascioTitoloSoggiorno");
				}

				lavoratore.setQuestura(questuraRilascioTitoloSoggiorno);
			}

			if (dto.getLavoratore().getFlgPagamSpesaRimpatrio() != null
					&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dto.getLavoratore().getFlgPagamSpesaRimpatrio())) {
				lavoratore.setFlgRimborsoRimpatrio(ComonlConstants.FLAG_S);
			}

			if (dto.getLavoratore().getFlgSussistSistemAllog() != null
					&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dto.getLavoratore().getFlgSussistSistemAllog())) {
				lavoratore.setFlgSistAlloggiativa(ComonlConstants.FLAG_S);
			}

			if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore() != null) {

				lavoratore.setCodCapRes(dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCapResidenza());

				if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza())) {

					if (!dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza()
							.startsWith("Z")) {
						Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
								dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza(),
								dataRiferimento);
						lavoratore.setComuneRes(comune);
						if (comune == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
									dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza(),
									"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - CodComuneResidenza");
						}
					} else {
						StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza(),
								dataRiferimento);
						if (statiEsteri == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
									dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getCodComuneResidenza(),
									"TO COMUNICAZIONE COMONL DatiAggiuntiviComunicazione - CodComuneResidenza");
						}
						lavoratore.setStatiEsteriRes(statiEsteri);

					}
				}

				lavoratore.setDsIndirizzoRes(ComonlStringUtils.eliminaCaratteriSpeciali(
						dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getIndirizzoResidenza()));

				// **************************************************************************************************
				// per integrazione forte

				if (dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getIdAnagraficaSilp() != null) {
					lavoratore.setIdLavoratoreSilp(new BigDecimal(
							dtoSpicom.getDatiAggiuntivi().getDatiAggiuntiviLavoratore().getIdAnagraficaSilp()));
				}

			}
			/*
			else if (lavoratore != null) {

				// TODO LAVORATORE COMUNE RESIDENZA E DOMICILIO

				if (lavoratore.getComuneDom() != null)
					lavoratore.setComuneRes(lavoratore.getComuneDom());
				if (lavoratore.getCodCapDom() != null)
					lavoratore.setCodCapRes(lavoratore.getCodCapDom());
				if (lavoratore.getDsIndirizzoDom() != null)
					lavoratore.setDsIndirizzoRes(lavoratore.getDsIndirizzoDom());
				if (lavoratore.getStatiEsteriDom() != null)
					lavoratore.setStatiEsteriRes(lavoratore.getStatiEsteriDom());
			}
			*/

			comunicazione.getLavoratori().add(lavoratore);
		}
	}

	protected void handleLegaleRappAndTitoloSoggiorno(Comunicazione comunicazione,
			ComunicazioneTracciatoUnicoDTO dtoSpicom, DecodificaDad decodificaDad, List<ComDImportErrore> errors) {
		// legale rapp e dati titolo di soggiorno per datore
		if (!ControlliSuOggettoComunicazione.isSpicom_VARDATORI(dtoSpicom)) {

			if (dtoSpicom.getDatoreDiLavoro() != null) {

				if (comunicazione.getDatoreAttuale() == null) {
					comunicazione.setDatoreAttuale(new Datore());
				}

				if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante() != null && ComonlUtility
						.isNotVoid(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCognome())) {

					// legale rapp
					LegaleRappr legaleRappr = (ComonlUtility
							.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr())
									? comunicazione.getDatoreAttuale().getLegaleRappr()
									: new LegaleRappr());

					// cittadinanza
					if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante() != null
							&& dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCittadinanza() != null
							&& !"".equalsIgnoreCase(
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCittadinanza())) {

						Cittadinanza cittadinanzaLegaleRappresentante = (Cittadinanza) decodificaDad.getTfromMin(
								Cittadinanza.class,
								dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCittadinanza(),
								dataRiferimento);

						if (cittadinanzaLegaleRappresentante == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_CITTADINANZA,
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCittadinanza(),
									"LEGALE RAPPRESENTANTE Cittadinanza");
						}

						legaleRappr.setCittadinanza(cittadinanzaLegaleRappresentante);
					}

					legaleRappr.setDsCognome(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getCognome());// cognome

					// stato o comune di nascita
					if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante() != null
							&& dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
									.getComuneOStatoEsteroNascita() != null
							&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
									.getComuneOStatoEsteroNascita())) {

						if (!dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getComuneOStatoEsteroNascita()
								.startsWith("Z")) {
							Comune comuneLegaleRappresentante = (Comune) decodificaDad.getTfromMin(Comune.class,
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
											.getComuneOStatoEsteroNascita(),
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getDataNascita());
							
							if (comuneLegaleRappresentante == null) {
								comuneLegaleRappresentante = (Comune) decodificaDad.getTfromMin(Comune.class,
										dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
										.getComuneOStatoEsteroNascita(),null);
							}
							
							legaleRappr.setComune(comuneLegaleRappresentante);
							if (comuneLegaleRappresentante == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
										dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
												.getComuneOStatoEsteroNascita(),
										"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
							}
						} else {
							StatiEsteri statiEsteriLegaleRappresentante = (StatiEsteri) decodificaDad.getTfromMin(
									StatiEsteri.class,
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
											.getComuneOStatoEsteroNascita(),
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getDataNascita());
							if (statiEsteriLegaleRappresentante==null) {
								statiEsteriLegaleRappresentante = (StatiEsteri) decodificaDad.getTfromMin(
										StatiEsteri.class, dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getComuneOStatoEsteroNascita(),null);
							}
							
							if (statiEsteriLegaleRappresentante != null) {
								legaleRappr.setStatiEsteri(statiEsteriLegaleRappresentante);
							} else {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
										dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
												.getComuneOStatoEsteroNascita(),
										"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
							}
						}
					}

					legaleRappr.setDtNascita(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getDataNascita());// nascita

					legaleRappr.setDsNome(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getNome());// nome

					legaleRappr.setSesso(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getSesso());// sesso

					if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getSoggiornanteInItalia() != null
							&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro()
									.getLegaleRappresentante().getSoggiornanteInItalia())) {
						legaleRappr.setFlgSoggiornanteItalia(ComonlConstants.FLAG_S);
					} else {
						legaleRappr.setFlgSoggiornanteItalia(ComonlConstants.FLAG_N);
					}

					// titolo di soggiorno
					if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno() != null) {

						// data scadenza
						legaleRappr.setDtScadenzaPermessoSogg(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
								.getTitoloSoggiorno().getDataScadenzaPS());

						// motivo titolo di soggiorno
						if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
								.getMotivoPermesso() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
										.getTitoloSoggiorno().getMotivoPermesso())) {

							MotivoPermesso motivoPermessoLegaleRappresentante = (MotivoPermesso) decodificaDad
									.getTfromMin(
											MotivoPermesso.class, dtoSpicom.getDatoreDiLavoro()
													.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso(),
											dataRiferimento);

							if (motivoPermessoLegaleRappresentante == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_MOTIVO_PERMESSO,
										dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
												.getMotivoPermesso(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - MotivoPermesso");
							}

							legaleRappr.setMotivoPermesso(motivoPermessoLegaleRappresentante);

						}

						if (ComonlUtility.isNotVoid(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
								.getTitoloSoggiorno().getNumeroDocumento())) {

							legaleRappr.setNumeroDocumento(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
									.getTitoloSoggiorno().getNumeroDocumento());// numero
																				// documento
						}

						// questura
						if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
								.getQuestura() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
										.getTitoloSoggiorno().getQuestura())) {

							Questura questuraLegaleRappresentante = (Questura) decodificaDad.getTfromMin(Questura.class,
									dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
											.getQuestura(),
									dataRiferimento);

							if (questuraLegaleRappresentante == null) {
								addErrorComDImportErrore(
										errors, ComonlConstants.COM_T_QUESTURA, dtoSpicom.getDatoreDiLavoro()
												.getLegaleRappresentante().getTitoloSoggiorno().getQuestura(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - Questura");
							}

							legaleRappr.setQuestura(questuraLegaleRappresentante);
						}

						// tipo documento / status straniero
						if (dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
								.getTipoDocumento() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante()
										.getTitoloSoggiorno().getTipoDocumento())) {

							StatusStraniero statusStranieroRappresentante = (StatusStraniero) decodificaDad
									.getTfromMin(
											StatusStraniero.class, dtoSpicom.getDatoreDiLavoro()
													.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento(),
											dataRiferimento);

							if (statusStranieroRappresentante == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATUS_STRANIERO,
										dtoSpicom.getDatoreDiLavoro().getLegaleRappresentante().getTitoloSoggiorno()
												.getTipoDocumento(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - TipoDocumento");
							}

							legaleRappr.setStatusStraniero(statusStranieroRappresentante);
						}
					}

					comunicazione.getDatoreAttuale().setLegaleRappr(legaleRappr);

				}
			}
		} else {// VARDATORI --
			if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale() != null) {

				if (ComonlUtility.isVoid(dtoSpicom.getVariazioneRagioneSociale())) {

					if (comunicazione.getDatoreAttuale() == null) {
						comunicazione.setDatoreAttuale(new Datore());
					}

					LegaleRappr legaleRappr = (ComonlUtility
							.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr())
									? comunicazione.getDatoreAttuale().getLegaleRappr()
									: new LegaleRappr());

					comunicazione.getDatoreAttuale().setLegaleRappr(legaleRappr);

					// Daniela 18/07/2022 NullPointer su  dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante() necessario controllo != null
					if(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()!=null) {

						// cittadinanza
						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante().getCittadinanza() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getTrasferimentoRamoAziendale()
										.getDatoreLavoroAttuale().getLegaleRappresentante().getCittadinanza())) {

							Cittadinanza cittadinanza = (Cittadinanza) decodificaDad.getTfromMin(
									Cittadinanza.class, dtoSpicom.getTrasferimentoRamoAziendale()
											.getDatoreLavoroAttuale().getLegaleRappresentante().getCittadinanza(),
									dataRiferimento);

							if (cittadinanza == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_CITTADINANZA,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getCittadinanza(),
										"LEGALE RAPPRESENTANTE Cittadinanza");
							}

							legaleRappr.setCittadinanza(cittadinanza);

						}

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getCognome() != null) {
							legaleRappr.setDsCognome(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getCognome());
						} // cognome

						// stato o comune di nascita
						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getComuneOStatoEsteroNascita() != null
								&& !"".equalsIgnoreCase(
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getComuneOStatoEsteroNascita())) {

							if (!dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getComuneOStatoEsteroNascita().startsWith("Z")) {
								Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
										dataRiferimento);
								legaleRappr.setComune(comune);
								if (comune == null) {
									addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
											"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
								}
							} else {
								StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
										dataRiferimento);
								if (statiEsteri != null) {
									legaleRappr.setStatiEsteri(statiEsteri);
								} else {
									addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
											"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
								}
							}
						}

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getDataNascita() != null) {
							legaleRappr.setDtNascita(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getDataNascita());
						} // nascita

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getNome() != null) {
							legaleRappr.setDsNome(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getNome());
						} // nome

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getSesso() != null) {
							legaleRappr.setSesso(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getSesso());
						} // sesso

						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
								.getLegaleRappresentante() != null
								&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
										.getLegaleRappresentante().getSoggiornanteInItalia() != null
								&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getSoggiornanteInItalia())) {

							legaleRappr.setFlgSoggiornanteItalia(ComonlConstants.FLAG_S);
						} else {
							legaleRappr.setFlgSoggiornanteItalia(ComonlConstants.FLAG_N);
						}

						// titolo di soggiorno
						if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getTitoloSoggiorno() != null) {

							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
									.setTitoloSoggiorno(new TitoloSoggiorno());
							dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getLegaleRappresentante()
									.getTitoloSoggiorno().setDataScadenzaPS(comunicazione.getDatoreAttuale()
											.getLegaleRappr().getDtScadenzaPermessoSogg());// data
																							// scadenza

							// motivo titolo di soggiorno
							if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso() != null
									&& !"".equalsIgnoreCase(dtoSpicom.getTrasferimentoRamoAziendale()
											.getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno()
											.getMotivoPermesso())) {

								MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(
										MotivoPermesso.class,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso(),
										dataRiferimento);

								if (motivoPermesso == null) {
									addErrorComDImportErrore(errors, ComonlConstants.COM_T_MOTIVO_PERMESSO,
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso(),
											"LEGALE RAPPRESENTANTE TitoloSoggiorno - MotivoPermesso");
								}

								legaleRappr.setMotivoPermesso(motivoPermesso);
							}

							if (ComonlUtility
									.isNotVoid(dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getNumeroDocumento())) {

								legaleRappr.setNumeroDocumento(
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getNumeroDocumento());
								// numero
								// documento
							}

							// questura
							if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getQuestura() != null
									&& !"".equalsIgnoreCase(
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getTitoloSoggiorno().getQuestura())) {

								Questura questura = (Questura) decodificaDad.getTfromMin(Questura.class,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getQuestura(),
										dataRiferimento);

								if (questura == null) {
									addErrorComDImportErrore(errors, ComonlConstants.COM_T_QUESTURA,
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getTitoloSoggiorno().getQuestura(),
											"LEGALE RAPPRESENTANTE TitoloSoggiorno - Questura");
								}

								legaleRappr.setQuestura(questura);
							}

							// tipo documento / status straniero
							if (dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
									.getLegaleRappresentante() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno() != null
									&& dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento() != null
									&& !"".equalsIgnoreCase(dtoSpicom.getTrasferimentoRamoAziendale()
											.getDatoreLavoroAttuale().getLegaleRappresentante().getTitoloSoggiorno()
											.getTipoDocumento())) {

								StatusStraniero statusStraniero = (StatusStraniero) decodificaDad.getTfromMin(
										StatusStraniero.class,
										dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento(),
										dataRiferimento);

								if (statusStraniero == null) {
									addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATUS_STRANIERO,
											dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale()
													.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento(),
											"LEGALE RAPPRESENTANTE TitoloSoggiorno - TipoDocumento");
								}

								legaleRappr.setStatusStraniero(statusStraniero);
							}

						}

						comunicazione.getDatoreAttuale().setLegaleRappr(legaleRappr);
					}
				}

			} else if (dtoSpicom.getVariazioneRagioneSociale() != null ){

				if (ComonlUtility.isNotVoid(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
						.getLegaleRappresentante())) {

					LegaleRappr legaleRappr = (ComonlUtility
							.isNotVoid(comunicazione.getDatoreAttuale().getLegaleRappr())
									? comunicazione.getDatoreAttuale().getLegaleRappr()
									: new LegaleRappr());

					// cittadinanza
					if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale() != null
							&& dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante() != null
							&& dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getCittadinanza() != null
							&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getCittadinanza())) {

						Cittadinanza cittadinanza = (Cittadinanza) decodificaDad
								.getTfromMin(
										Cittadinanza.class, dtoSpicom.getVariazioneRagioneSociale()
												.getDatoreLavoroAttuale().getLegaleRappresentante().getCittadinanza(),
										dataRiferimento);

						if (cittadinanza == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_CITTADINANZA,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getCittadinanza(),
									"LEGALE RAPPRESENTANTE Cittadinanza");
						}

						legaleRappr.setCittadinanza(cittadinanza);
					}

					legaleRappr.setDsCognome(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante().getCognome());// cognome

					// stato o comune di nascita
					if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante() != null
							&& dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getComuneOStatoEsteroNascita() != null
							&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getComuneOStatoEsteroNascita())) {

						if (!dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getComuneOStatoEsteroNascita().startsWith("Z")) {
							Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getDataNascita());
							legaleRappr.setComune(comune);
							if (comune == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
										dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
										"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
							}
						} else {
							StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getDataNascita());
							if (statiEsteri != null) {
								legaleRappr.setStatiEsteri(statiEsteri);
							} else {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
										dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getComuneOStatoEsteroNascita(),
										"LEGALE RAPPRESENTANTE ComuneOStatoEsteroNascita");
							}

						}

					}

					legaleRappr.setDtNascita(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante().getDataNascita());// data nascita

					legaleRappr.setDsNome(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante().getNome());// nome

					legaleRappr.setSesso(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante().getSesso());// sesso

					if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getLegaleRappresentante() != null
							&& dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
									.getLegaleRappresentante().getSoggiornanteInItalia() != null
							&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale()
									.getDatoreLavoroAttuale().getLegaleRappresentante().getSoggiornanteInItalia())) {

						legaleRappr.setFlgSoggiornanteItalia((ComonlConstants.FLAG_S));
					} else {
						legaleRappr.setFlgSoggiornanteItalia((ComonlConstants.FLAG_N));
					}

					// titolo di soggiorno
					if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante()
							.getTitoloSoggiorno() != null) {

						legaleRappr.setDtScadenzaPermessoSogg(
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
										.getLegaleRappresentante().getTitoloSoggiorno().getDataScadenzaPS());// datascadenza

						// motivo titolo di soggiorno
						if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getTitoloSoggiorno().getMotivoPermesso() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
										.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso())) {

							MotivoPermesso motivoPermesso = (MotivoPermesso) decodificaDad.getTfromMin(
									MotivoPermesso.class,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso(),
									dataRiferimento);

							if (motivoPermesso == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_MOTIVO_PERMESSO,
										dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getMotivoPermesso(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - MotivoPermesso");
							}

							legaleRappr.setMotivoPermesso(motivoPermesso);
						}

						if (ComonlUtility.isNotVoid(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.getLegaleRappresentante().getTitoloSoggiorno().getNumeroDocumento())) {

							legaleRappr
									.setNumeroDocumento(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getNumeroDocumento());// numero
							// documento
						}

						// questura
						if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getTitoloSoggiorno().getQuestura() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
										.getLegaleRappresentante().getTitoloSoggiorno().getQuestura())) {

							Questura questura = (Questura) decodificaDad.getTfromMin(Questura.class,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getQuestura(),
									dataRiferimento);

							if (questura == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_QUESTURA,
										dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getQuestura(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - Questura");
							}

							legaleRappr.setQuestura(questura);
						}

						// tipo documento / status straniero
						if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getLegaleRappresentante()
								.getTitoloSoggiorno().getTipoDocumento() != null
								&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
										.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento())) {

							StatusStraniero statusStraniero = (StatusStraniero) decodificaDad.getTfromMin(
									StatusStraniero.class,
									dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
											.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento(),
									dataRiferimento);

							if (statusStraniero == null) {
								addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATUS_STRANIERO,
										dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
												.getLegaleRappresentante().getTitoloSoggiorno().getTipoDocumento(),
										"LEGALE RAPPRESENTANTE TitoloSoggiorno - TipoDocumento");
							}

							legaleRappr.setStatusStraniero(statusStraniero);
						}

					}

					comunicazione.getDatoreAttuale().setLegaleRappr(legaleRappr);

				}

			}

		}
	}

	protected void handleDatiDelDatore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom,
			String tipoTracciato, DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dtoSpicom.getDatoreDiLavoro() != null && !ControlliSuOggettoComunicazione.isSpicom_VARDATORI(dtoSpicom)) {

			if (tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {

				AgenziaSomministrazioneDTO tempUSDto = null;
				if (dtoSpicom.getDatoreDiLavoro() != null) {
					tempUSDto = (AgenziaSomministrazioneDTO) dtoSpicom.getDatoreDiLavoro();
				} else {
					tempUSDto = new AgenziaSomministrazioneDTO();
				}

				Datore datoreAttuale = (comunicazione.getDatoreAttuale() != null ? comunicazione.getDatoreAttuale()
						: new Datore());

				datoreAttuale.setNumeroIscrizioneAlbo(tempUSDto.getNumIscrizioneAlbo().toUpperCase());

				InizioRapportoUniSommDTO inizioRapporto = (InizioRapportoUniSommDTO)dtoSpicom.getDatiInizio();
				comunicazione.setNumAgenzSomministr(inizioRapporto.getNumAgenziaSomministrazione());

				if (tempUSDto.getFlgAgenziaEstera() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(tempUSDto.getFlgAgenziaEstera())) {
					datoreAttuale.setFlgUtilEstera(ComonlConstants.FLAG_S);
				} else {
					datoreAttuale.setFlgUtilEstera(ComonlConstants.FLAG_N);
				}

				comunicazione.setDatoreAttuale(datoreAttuale);

//				if(dtoSpicom.getMissione() != null) {
//					Datore dittaUtilizzagtrice = new Datore();
//					Rapporto missione = new Rapporto();
//					comunicazione.setMissione(null);
//
//					handleMissione(comunicazione, dtoSpicom);
//				}



			} else if (tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_DOMESTICO_ID)) {

				DatoreDomesticoDTO datDomDto = new DatoreDomesticoDTO();
				if (dtoSpicom.getDatoreDiLavoro() != null) {
					datDomDto = (DatoreDomesticoDTO) dtoSpicom.getDatoreDiLavoro();
				}

				Datore datoreDomestico = (ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale())
						? comunicazione.getDatoreAttuale()
						: new Datore());

				datoreDomestico.setDsCognome(ComonlUtility.isNotVoid(datDomDto.getDsCognome())
						? ComonlStringUtils.eliminaCaratteriSpeciali(datDomDto.getDsCognome())
						: null);
				datoreDomestico.setDsNome(ComonlUtility.isNotVoid(datDomDto.getDsNome())
						? ComonlStringUtils.eliminaCaratteriSpeciali(datDomDto.getDsNome())
						: null);

				comunicazione.setDatoreAttuale(datoreDomestico);
			} else {
				comunicazione.setDatoreAttuale(new Datore());
			}

			if (comunicazione.getDatoreAttuale().getSedeOperativa() == null) {
				comunicazione.getDatoreAttuale().setSedeOperativa(new SedeLavoro());
			}
			comunicazione.getDatoreAttuale().getSedeOperativa().setFlgSedeLegale(ComonlConstants.FLAG_N);
			comunicazione.getDatoreAttuale().getSedeOperativa()
					.setCodCap(dtoSpicom.getDatoreDiLavoro().getCapSedeLavoro());

			if (comunicazione.getDatoreAttuale().getSedeLegale() == null) {
				comunicazione.getDatoreAttuale().setSedeLegale(new SedeLavoro());
			}
			comunicazione.getDatoreAttuale().getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);
			comunicazione.getDatoreAttuale().getSedeLegale()
					.setCodCap(dtoSpicom.getDatoreDiLavoro().getCapSedeLegale());

			// 71862 - Gestione codice fiscale per agenzie di somministrazione
			// estera su
			// tracciato unisomm
			// if
			// (tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)
			// FIXME dove trovo questi dati nel datore? per ora prendo dal
			// codice fiscale:
			// ho parlato con Alessio per ora 20/04 non ci sono più quei campi
			// ed è tutto
			// nel CF
			// &&
			// ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getSiglaStatoEstero())
			// &&
			// Util.isNotVoid(comunicazioneBo.getDatore().getCodiceIdentificativoStatoEstero()))
			// {
			// dto.getDatoreDiLavoro().setCodFiscale(
			// Format.eliminaCaratteriSpeciali(comunicazioneBo.getDatore().getSiglaStatoEstero().toUpperCase())
			// +
			// Format.eliminaCaratteriSpeciali(comunicazioneBo.getDatore().getCodiceIdentificativoStatoEstero().toUpperCase()));
			// } else {
			// dto.getDatoreDiLavoro().setCodFiscale(
			// Format.eliminaCaratteriSpeciali(comunicazioneBo.getDatore()
			// .getCodiceFiscale().toUpperCase()));
			// }
			if (dtoSpicom.getDatoreDiLavoro().getCodFiscale() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getCodFiscale())) {
				comunicazione.getDatoreAttuale().setCodiceFiscale(ComonlStringUtils
						.eliminaCaratteriSpeciali(dtoSpicom.getDatoreDiLavoro().getCodFiscale().toUpperCase()));

				comunicazione.setCfImpresa(dtoSpicom.getDatoreDiLavoro().getCodFiscale().toUpperCase());
			}

			if (dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro())) {

				if (!dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro().startsWith("Z")) {
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
							dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), dataRiferimento);
					comunicazione.getDatoreAttuale().getSedeOperativa().setComune(comune);
					if (comune == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), "DATORE ComuneSedeLavoro");
					}
				} else {
					StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
							dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), dataRiferimento);
					if (statiEsteri != null) {
						comunicazione.getDatoreAttuale().getSedeOperativa().setStatiEsteri(statiEsteri);
					} else {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), "DATORE ComuneSedeLavoro");
					}

				}

			}

			if (dtoSpicom.getDatoreDiLavoro().getComuneSedeLegale() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getComuneSedeLegale())) {

				if (!dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro().startsWith("Z")) {
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
							dtoSpicom.getDatoreDiLavoro().getComuneSedeLegale(), dataRiferimento);
					comunicazione.getDatoreAttuale().getSedeLegale().setComune(comune);
					if (comune == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
								dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), "DATORE ComuneSedeLavoro");
					}
				} else {
					StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
							dtoSpicom.getDatoreDiLavoro().getComuneSedeLegale(), dataRiferimento);
					if (statiEsteri != null) {
						comunicazione.getDatoreAttuale().getSedeLegale().setStatiEsteri(statiEsteri);
					} else {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
								dtoSpicom.getDatoreDiLavoro().getComuneSedeLavoro(), "DATORE ComuneSedeLavoro");
					}

				}

			}

			// FIXME il contratto unidom non esiste piu' ... metto sempre la
			// denominazione
			// if(comunicazioneBo.getDatiDiInvio().getContrDom() != null &&
			// comunicazioneBo.getDatiDiInvio().getContrDom().booleanValue()) {
			// dto.getDatoreDiLavoro().setDenominazione(formatDescrDatore(comunicazioneBo.getDatore().getDenominazione()));
			// // COMONL-1572 71441
			// }
			// else {
			// dto.getDatoreDiLavoro().setDenominazione(formatDescrDatore(comunicazioneBo.getDatore().getCognomeDenominazione()));
			// // COMONL-1572 71441
			// }
			comunicazione.getDatoreAttuale().setDsDenominazioneDatore(
					ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getDenominazione()));
			comunicazione.getDatoreAttuale().getSedeOperativa()
					.setEmail(ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getEmailSedeLavoro()));
			comunicazione.getDatoreAttuale().getSedeLegale()
					.setEmail(ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getEmailSedeLegale()));
			comunicazione.getDatoreAttuale().getSedeOperativa()
					.setFax(ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getFaxSedeLavoro()));
			comunicazione.getDatoreAttuale().getSedeLegale()
					.setFax(ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getFaxSedeLegale()));
			comunicazione.getDatoreAttuale().getSedeOperativa().setIndirizzo(
					ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getIndirizzoSedeLavoro()));
			comunicazione.getDatoreAttuale().getSedeLegale().setIndirizzo(
					ComonlStringUtils.formatDescrDatore(dtoSpicom.getDatoreDiLavoro().getIndirizzoSedeLegale()));

			// Riccardo Leccese 13/07/2007
			// Aggiunti questi valori perche' necessari e richiesti da SPICOM
			if (dtoSpicom.getDatoreDiLavoro().getCapSedeLegale() != null) {
				comunicazione.getDatoreAttuale().getSedeLegale()
						.setCodCap(dtoSpicom.getDatoreDiLavoro().getCapSedeLegale());
			}

			if (dtoSpicom.getDatoreDiLavoro().getCapSedeLavoro() != null) {
				comunicazione.getDatoreAttuale().getSedeOperativa()
						.setCodCap(dtoSpicom.getDatoreDiLavoro().getCapSedeLavoro());
			}

			if (dtoSpicom.getDatoreDiLavoro().getSettore() != null
					&& !"".equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getSettore())) {

				Atecofin atecofin = (Atecofin) decodificaDad.getTfromMin(Atecofin.class,
						dtoSpicom.getDatoreDiLavoro().getSettore(), dataRiferimento);

				if (atecofin == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_ATECOFIN,
							dtoSpicom.getDatoreDiLavoro().getSettore(), "DATORE Settore");
				}

				comunicazione.getDatoreAttuale().setAtecofin(atecofin);
			}

			comunicazione.getDatoreAttuale().getSedeOperativa()
					.setTelefono(dtoSpicom.getDatoreDiLavoro().getTelefonoSedeLavoro());
			comunicazione.getDatoreAttuale().getSedeLegale()
					.setTelefono(dtoSpicom.getDatoreDiLavoro().getTelefonoSedeLegale());

			if (!tipoTracciato.equals(ComonlConstants.TIPO_TRACCIATO_UNISOMM_ID)) {
				if (dtoSpicom.getDatoreDiLavoro().getPubblicaAmministrazione() != null && ComonlConstants.FLAG_SI_DESC
						.equalsIgnoreCase(dtoSpicom.getDatoreDiLavoro().getPubblicaAmministrazione())) {
					comunicazione.getDatoreAttuale().setFlgPubAmm(ComonlConstants.FLAG_S);
				} else {
					comunicazione.getDatoreAttuale().setFlgPubAmm(ComonlConstants.FLAG_N);
				}
			}

		// VARDATORI
		} else if (ControlliSuOggettoComunicazione.isSpicom_VARDATORI(dtoSpicom)) {

			comunicazione.setDatoreAttuale(
					ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale()) ? comunicazione.getDatoreAttuale()
							: new Datore());

			comunicazione.getDatoreAttuale()
					.setSedeOperativa(ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getSedeOperativa())
							? comunicazione.getDatoreAttuale().getSedeOperativa()
							: new SedeLavoro());

			comunicazione.getDatoreAttuale().getSedeOperativa().setFlgSedeLegale(ComonlConstants.FLAG_N);

			comunicazione.getDatoreAttuale()
					.setSedeLegale(ComonlUtility.isNotVoid(comunicazione.getDatoreAttuale().getSedeLegale())
							? comunicazione.getDatoreAttuale().getSedeLegale()
							: new SedeLavoro());

			comunicazione.getDatoreAttuale().getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);

			if (dtoSpicom.getTrasferimentoRamoAziendale() != null) {

				if (dtoSpicom.getTrasferimentoRamoAziendale().getCodTrasferimentoDAzienda() != null) {
					TipoTrasferimento tipoTrasferimento = (TipoTrasferimento) decodificaDad.getTfromMin(TipoTrasferimento.class,
							dtoSpicom.getTrasferimentoRamoAziendale().getCodTrasferimentoDAzienda(), dataRiferimento);
					comunicazione.setTipoTrasferimento(tipoTrasferimento);
				}

				comunicazione.setDtTrasferimentoVarDatori(dtoSpicom.getTrasferimentoRamoAziendale().getDataInizio());
				comunicazione.setDtFineAffittoRamo(dtoSpicom.getTrasferimentoRamoAziendale().getDataFineRamoAffitto());

				// DATORE ATTUALE
				mapDatore(comunicazione.getDatoreAttuale(), dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale());
				comunicazione.getDatoreAttuale().getSedeLegale();

//				SedeLavoro sedeOp = comunicazione.getDatoreAttuale().getSedeOperativa();
//				sedeOp.setCodCap(comunicazione.getDatoreAttuale().getSedeLegale().getCodCap());
//				sedeOp.setComune(comunicazione.getDatoreAttuale().getSedeLegale().getComune());
//				sedeOp.setEmail(comunicazione.getDatoreAttuale().getSedeLegale().getEmail());
//				sedeOp.setFax(comunicazione.getDatoreAttuale().getSedeLegale().getFax());
//				sedeOp.setIndirizzo(comunicazione.getDatoreAttuale().getSedeLegale().getIndirizzo());
//				sedeOp.setStatiEsteri(comunicazione.getDatoreAttuale().getSedeLegale().getStatiEsteri());
//				sedeOp.setTelefono(comunicazione.getDatoreAttuale().getSedeLegale().getTelefono());
//				sedeOp.setFlgSedeLegale(ComonlConstants.FLAG_N);

				// DATORE PRECEDENTE
				if(comunicazione.getDatorePrecedente() == null) {
					comunicazione.setDatorePrecedente(new Datore());
				}
				mapDatore(comunicazione.getDatorePrecedente(), dtoSpicom.getTrasferimentoRamoAziendale().getDatoreLavoroPrecedente());
				comunicazione.getDatorePrecedente().setSedeOperativa(null);

				// SEDI E LAVORATORI INTERESSATI
				if(dtoSpicom.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate() != null){

					comunicazione.setRapLavSedeVD(new ArrayList<>());

					for(DatoreDiLavoroInteressatoTrasferimentoRamoAziendaleDTO interessato :
						dtoSpicom.getTrasferimentoRamoAziendale().getVctElencoSediDiLavoroInteressate()) {

						SedeLavoro sede = new SedeLavoro();
						sede.setFlgSedeLegale(ComonlConstants.FLAG_N);
						sede.setCodCap(interessato.getCapSedeLavoro());

						sede.setEmail(interessato.getEmailSedeLavoro());
						sede.setFax(interessato.getFaxSedeLavoro());
						sede.setIndirizzo(interessato.getIndirizzoSedeLavoro());
						sede.setTelefono(interessato.getTelefonoSedeLavoro());

						if (interessato.getComuneSedeLavoro().startsWith("Z")) {
							StatiEsteri statiEsteri =
									(StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class, interessato.getComuneSedeLavoro(), dataRiferimento);
							sede.setStatiEsteri(statiEsteri);
							addErrIfNull(statiEsteri,errors, ComonlConstants.COM_T_STATI_ESTERI, interessato.getComuneSedeLavoro(),
										"DATORE ComuneSedeLavoro");
						}else {
							Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, interessato.getComuneSedeLavoro(),	dataRiferimento);
							sede.setComune(comune);
							addErrIfNull(comune, errors, ComonlConstants.COM_T_COMUNE,interessato.getComuneSedeLavoro(),
										"DATORE ComuneSedeLavoro");
						}

						for(LavoratoreInteressatoTrasferimentoRamoAziendaleDTO lavDto : interessato.getVctElencoLavoratoriInteressati()) {
							RapportiLavoratoriSediInteressateVD rlsVd = new RapportiLavoratoriSediInteressateVD();
							comunicazione.getRapLavSedeVD().add(rlsVd);
							rlsVd.setSedeLavoroVD(sede);

							Lavoratore lavoratore = new Lavoratore();
							rlsVd.setLavoratoreVD(lavoratore);

							if(lavDto.getCittadinanza() != null) {
								Cittadinanza citt = (Cittadinanza)decodificaDad.getTfromMin(Cittadinanza.class, lavDto.getCittadinanza(),	dataRiferimento);
								lavoratore.setCittadinanza(citt);
								addErrIfNull(citt ,errors, ComonlConstants.COM_T_CITTADINANZA, lavDto.getCittadinanza(), "LAVORATORE Cittadinanza");
							}

							lavoratore.setCodCapDom(lavDto.getCapDomicilio());
							lavoratore.setCodiceFiscale(lavDto.getCodFiscale());
							lavoratore.setCognome(lavDto.getCognome());
							if(lavDto.getComuneDomicilio() != null) {
								Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, lavDto.getComuneDomicilio(), dataRiferimento);
								addErrIfNull(comune, errors, ComonlConstants.COM_T_COMUNE,lavDto.getComuneDomicilio(), "LAVORATORE Comune Domicilio");
								lavoratore.setComuneDom(comune);
							}

							if(lavDto.getComuneOStatoEsteroNascita() != null) {
								if(lavDto.getComuneOStatoEsteroNascita().startsWith("Z")) {
									StatiEsteri stato = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class, lavDto.getComuneOStatoEsteroNascita(), null);
									lavoratore.setStatiEsteriNasc(stato);
									addErrIfNull(stato, errors, ComonlConstants.COM_T_STATI_ESTERI,lavDto.getComuneOStatoEsteroNascita(),
											"LAVORATORE stato nascita" );
								} else {
									Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, lavDto.getComuneOStatoEsteroNascita(), lavDto.getDataNascita());
									lavoratore.setComuneNasc(comune);
									addErrIfNull(comune, errors, ComonlConstants.COM_T_COMUNE, lavDto.getComuneOStatoEsteroNascita(),
											"LAVORATORE comune nascita" );
								}
							}

							lavoratore.setDsIndirizzoDom(lavDto.getIndirizzoDomicilio());
							lavoratore.setDtNascita(lavDto.getDataNascita());
							lavoratore.setDtScadenzaPermessoSogg(lavDto.getDataScadenzaPermessoSoggiorno());
							lavoratore.setFlgRimborsoRimpatrio(flg(lavDto.getFlgPagamSpesaRimpatrio()));
							lavoratore.setFlgSistAlloggiativa(flg(lavDto.getFlgSussistSistemAllog()));

							if(lavDto.getLivelloIstruzione() != null) {
								LivelloStudio livelloSt = (LivelloStudio)decodificaDad.getTfromMin(LivelloStudio.class, lavDto.getLivelloIstruzione(), dataRiferimento);
								addErrIfNull(livelloSt, errors, ComonlConstants.COM_T_LIVELLO_STUDIO, lavDto.getLivelloIstruzione(),
										"LAVORATORE livello studio" );
								lavoratore.setLivelloStudio(livelloSt);
							}

							if(lavDto.getMotivoPermesso() != null) {
								MotivoPermesso motivo = (MotivoPermesso)decodificaDad.getTfromMin(MotivoPermesso.class, lavDto.getMotivoPermesso(), dataRiferimento);
								addErrIfNull(motivo, errors, ComonlConstants.COM_T_MOTIVO_PERMESSO, lavDto.getMotivoPermesso(), "LAVORATORE motivo di permesso");
								lavoratore.setMotivoPermesso(motivo);
							}
							lavoratore.setNome(lavDto.getNome());
							lavoratore.setNumeroDocumento(lavDto.getNumeroDocumento());
							lavoratore.setSesso(lavDto.getSesso());

							Rapporto rapporto = new Rapporto();
							rlsVd.setRapportoVD(rapporto);

							InizioRapportoUnilavDTO rappDto = lavDto.getDatiInizio();

							if(rappDto.getCcnlApplicato() != null) {
								Ccnl ccnl = (Ccnl)decodificaDad.getTfromMin(Ccnl.class, rappDto.getCcnlApplicato(), dataRiferimento);
								addErrIfNull(ccnl, errors, ComonlConstants.COM_T_CCNL, rappDto.getCcnlApplicato(), "LAVORATORE CCNL");
								rapporto.setCcnl(ccnl);
							}

							if(rappDto.getEntePrevidenziale() != null) {
								EntePrevidenziale ente = (EntePrevidenziale)decodificaDad.getTfromMin(EntePrevidenziale.class, rappDto.getEntePrevidenziale(), dataRiferimento);
								addErrIfNull(ente, errors, ComonlConstants.COM_T_ENTE_PREVIDENZIALE, rappDto.getEntePrevidenziale(), "LAVORATORE Ente previdenziale");
								rapporto.setEntePrevidenziale(ente);
							}

							rapporto.setCodiceEntePrev(rappDto.getCodEntePrevidenziale());
							rapporto.setDtInizioRapporto(rappDto.getDataInizio());
							rapporto.setDtFineRapporto(rappDto.getDataFine());
							rapporto.setDtFinePeriodoFormativo(rappDto.getDataFinePeriodoFormativo());
							rapporto.setCfSoggPromotoreTirocinio(rappDto.getCFSoggettoPromotoreTirocinio());
							rapporto.setDtLegge68(rappDto.getDataNullaostaLegge68OConvenzione());
							rapporto.setFlgSocioLavoratore(flg(rappDto.getFlgSocioLavoratore()));
							rapporto.setFlgLavInMobilita(flg(rappDto.getLavInMobilita()));
							rapporto.setFlgLavStagionale(flg(rappDto.getLavoroStagionale()));
							rapporto.setFlgAssunzioneObbligatoria(flg(rappDto.getFlgAssunzioneObbligatoria()));

							if(rappDto.getCategoriaLavoratoreAssunzioneObbligatoria() != null) {
								CategLavAssObbl assObb = (CategLavAssObbl)decodificaDad.getTfromMin(CategLavAssObbl.class, rappDto.getCategoriaLavoratoreAssunzioneObbligatoria(), dataRiferimento);
								addErrIfNull(assObb, errors, ComonlConstants.COM_T_CATEG_LAV_ASS_OBBL, rappDto.getCategoriaLavoratoreAssunzioneObbligatoria(), "RAPPORTO assunzione obbligatoria");
								rapporto.setCategLavAssObbl(assObb);
							}

							rapporto.setFlgLavoroAgricoltura(flg(rappDto.getFlgLavoroAgricoltura()));
							rapporto.setGiornateLavPreviste(rappDto.getGiornateLavorativePrevisteAgricoltura());
							rapporto.setTipoLavorazione(rappDto.getTipoLavorazione());

							if(rappDto.getLivelloInquadramento() != null) {
								LivelloRetribuzione retr = (LivelloRetribuzione)decodificaDad.getTfromMin(LivelloRetribuzione.class, rappDto.getLivelloInquadramento(), dataRiferimento);
								rapporto.setLivelloRetribuzione(retr);
							}
							rapporto.setLivelloInquadramento(rappDto.getLivelloInquadramento());
							rapporto.setNumeroAttoLegge68(rappDto.getNumeroAttoLegge68());
							rapporto.setNumOreSettMed(bd(rappDto.getOreSettimanaliMedie()));
							rapporto.setPatInail(rappDto.getPatInail());

							if(rappDto.getQualificaProfessionaleIstat() != null) {
								Istat2001livello5 istat = (Istat2001livello5)decodificaDad.getTfromMin(Istat2001livello5.class, rappDto.getQualificaProfessionaleIstat(), dataRiferimento);
								addErrIfNull(istat, errors, ComonlConstants.COM_T_ISTAT2001LIVELLO5, rappDto.getQualificaProfessionaleIstat(), "RAPPORTO codice istat");
								rapporto.setIstatLivello5(istat);
							}
							rapporto.setRetribuzioneCompenso(bd(rappDto.getRetribuzione()));

							if(rappDto.getTipologiaContrattuale() != null) {
								TipoContratti tContr = (TipoContratti)decodificaDad.getTfromMin(TipoContratti.class, rappDto.getTipologiaContrattuale(), dataRiferimento);
								addErrIfNull(tContr, errors, ComonlConstants.COM_T_TIPO_CONTRATTI, rappDto.getTipologiaContrattuale(), "RAPPORTO tipo contratto");
								rapporto.setTipoContratti(tContr);
							}

							if(rappDto.getTipoOrario() != null) {
								TipoOrario tOra = (TipoOrario)decodificaDad.getTfromMin(TipoOrario.class, rappDto.getTipoOrario(), dataRiferimento);
								addErrIfNull(tOra, errors, ComonlConstants.COM_T_TIPO_CONTRATTI, rappDto.getTipoOrario(), "RAPPORTO tipo orario");
								rapporto.setTipoOrario(tOra);
							}
						}
					}
				}
			}
			else if (dtoSpicom.getVariazioneRagioneSociale() != null) {

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLavoro() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLavoro())) {
					comunicazione.getDatoreAttuale().getSedeOperativa().setCodCap(
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLavoro());
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLegale() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLegale())) {
					comunicazione.getDatoreAttuale().getSedeLegale().setCodCap(
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLegale());
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCodFiscale() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCodFiscale())) {
					comunicazione.getDatoreAttuale().setCodiceFiscale(
							ComonlStringUtils.eliminaCaratteriSpeciali(dtoSpicom.getVariazioneRagioneSociale()
									.getDatoreLavoroAttuale().getCodFiscale().toUpperCase()));

					comunicazione.setCfImpresa(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
							.getCodFiscale().toUpperCase());
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro() != null
						&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.getComuneSedeLavoro())) {

					if (!dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro()
							.startsWith("Z")) {
						Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro(),
								dataRiferimento);
						comunicazione.getDatoreAttuale().getSedeOperativa().setComune(comune);
						if (comune == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE, dtoSpicom
									.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro(),
									"DATORE ComuneSedeLavoro");
						}
					} else {
						StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro(),
								dataRiferimento);
						if (statiEsteri != null) {
							comunicazione.getDatoreAttuale().getSedeOperativa().setStatiEsteri(statiEsteri);
						} else {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI, dtoSpicom
									.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLavoro(),
									"DATORE ComuneSedeLavoro");
						}
					}
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale() != null
						&& !"".equalsIgnoreCase(dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale()
								.getComuneSedeLegale())) {

					if (!dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale()
							.startsWith("Z")) {
						Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale(),
								dataRiferimento);
						comunicazione.getDatoreAttuale().getSedeLegale().setComune(comune);
						if (comune == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE, dtoSpicom
									.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale(),
									"DATORE ComuneSedeLavoro");
						}
					} else {
						StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale(),
								dataRiferimento);
						if (statiEsteri != null) {
							comunicazione.getDatoreAttuale().getSedeLegale().setStatiEsteri(statiEsteri);
						} else {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI, dtoSpicom
									.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getComuneSedeLegale(),
									"DATORE ComuneSedeLavoro");
						}
					}
				}

				comunicazione.getDatoreAttuale().setDsDenominazioneDatore(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getDenominazione()));
				comunicazione.getDatoreAttuale().getSedeOperativa().setEmail(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getEmailSedeLavoro()));
				comunicazione.getDatoreAttuale().getSedeLegale().setEmail(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getEmailSedeLegale()));
				comunicazione.getDatoreAttuale().getSedeOperativa().setFax(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getFaxSedeLavoro()));
				comunicazione.getDatoreAttuale().getSedeLegale().setFax(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getFaxSedeLegale()));
				comunicazione.getDatoreAttuale().getSedeOperativa().setIndirizzo(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getIndirizzoSedeLavoro()));
				comunicazione.getDatoreAttuale().getSedeLegale().setIndirizzo(ComonlStringUtils.formatDescrDatore(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getIndirizzoSedeLegale()));

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLegale() != null) {
					comunicazione.getDatoreAttuale().getSedeLegale().setCodCap(
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLegale());
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLavoro() != null) {
					comunicazione.getDatoreAttuale().getSedeOperativa().setCodCap(
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCapSedeLavoro());
				}

				if (dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getSettore() != null
						&& !"".equalsIgnoreCase(
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getSettore())) {

					Atecofin atecofin = (Atecofin) decodificaDad.getTfromMin(Atecofin.class,
							dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getSettore(),
							dataRiferimento);

					if (atecofin == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ATECOFIN,
								dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getSettore(),
								"DATORE Settore");
					}

					comunicazione.getDatoreAttuale().setAtecofin(atecofin);
				}

				comunicazione.getDatoreAttuale().getSedeOperativa().setTelefono(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getTelefonoSedeLavoro());
				comunicazione.getDatoreAttuale().getSedeLegale().setTelefono(
						dtoSpicom.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getTelefonoSedeLegale());

			}
		}
	}

	protected void handleDatiTutore(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dtoSpicom.getDatiAggiuntivi() != null && dtoSpicom.getDatiAggiuntivi().getDatiApprendistato() != null
				&& dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore() != null) {

			DatiApprendistatoDTO datiApprendistatoDTO = dtoSpicom.getDatiAggiuntivi().getDatiApprendistato();
			DatiTutoreDTO tutoreDTO = dtoSpicom.getDatiAggiuntivi().getDatiApprendistato().getTutore();

			Tutore tutore = (ComonlUtility.isNotVoid(comunicazione.getRapporto().getTutore())
					? comunicazione.getRapporto().getTutore()
					: new Tutore());

			if (datiApprendistatoDTO.getDataVisitaMedica() != null) {
				tutore.setDtVisitaMedica(datiApprendistatoDTO.getDataVisitaMedica());
			}

			if (tutoreDTO.getAnniEsperienza() != null) {
				tutore.setNumAnniEsperienza(BigDecimal.valueOf(tutoreDTO.getAnniEsperienza()));
			}

			if (tutoreDTO.getCodFiscale() != null) {
				tutore.setCfTutore(ComonlStringUtils.eliminaCaratteriSpeciali(tutoreDTO.getCodFiscale()));
			}

			if (tutoreDTO.getCognome() != null) {
				tutore.setCognome(ComonlStringUtils.eliminaCaratteriSpeciali(tutoreDTO.getCognome()));
			}

			if (tutoreDTO.getDataNascita() != null) {
				tutore.setDtNascita(tutoreDTO.getDataNascita());
			}

			if (tutoreDTO.getFlgTitolare() != null
					&& ComonlConstants.FLAG_S.equalsIgnoreCase(tutoreDTO.getFlgTitolare())) {
				tutore.setFlgTitolare(ComonlConstants.FLAG_S);
			} else {
				tutore.setFlgTitolare(ComonlConstants.FLAG_N);
			}

			if (tutoreDTO.getGradoContrattuale() != null && !"".equalsIgnoreCase(tutoreDTO.getGradoContrattuale())) {

				GradoContrattuale gradoContrattuale = (GradoContrattuale) decodificaDad
						.getTfromMin(GradoContrattuale.class, tutoreDTO.getGradoContrattuale(), dataRiferimento);

				if (gradoContrattuale == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_GRADO_CONTRATTUALE,
							tutoreDTO.getGradoContrattuale(), "TUTORE GradoContrattuale");
				}

				tutore.setGradoContrattuale(gradoContrattuale);
			}

			if (tutoreDTO.getLivelloInquadramento() != null) {
				tutore.setDsLivelloInquadramento(tutoreDTO.getLivelloInquadramento());
			}

			if (tutoreDTO.getNome() != null) {
				tutore.setNome(ComonlStringUtils.eliminaCaratteriSpeciali(tutoreDTO.getNome()));
			}

			if (tutoreDTO.getQualificaProfessionaleIstat() != null
					&& !"".equalsIgnoreCase(tutoreDTO.getQualificaProfessionaleIstat())) {

				Istat2001livello5 istat2001livello5 = (Istat2001livello5) decodificaDad.getTfromMin(
						Istat2001livello5.class, tutoreDTO.getQualificaProfessionaleIstat(), dataRiferimento);

				if (istat2001livello5 == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_ISTAT2001LIVELLO5,
							tutoreDTO.getQualificaProfessionaleIstat(), "TUTORE QualificaProfessionaleIstat");
				}

				tutore.setIstat(istat2001livello5);
			}

			if (tutoreDTO.getSesso() != null) {
				tutore.setSesso(tutoreDTO.getSesso().toUpperCase());
			}

			comunicazione.getRapporto().setTutore(tutore);
		}
	}

	protected void handleDatiBloccoTirocinio(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (ControlliSuOggettoComunicazione.isSpicom_UNILAV(dto) && dto.getDatiAggiuntivi() != null
				&& dto.getDatiAggiuntivi().getDatiTirocinio() != null
				&& dto.getDatiAggiuntivi().getDatiTirocinio().getCategoriaTirocinante() != null
				&& !"".equalsIgnoreCase(dto.getDatiAggiuntivi().getDatiTirocinio().getCategoriaTirocinante())
				&& dto.getDatiAggiuntivi().getDatiTirocinio().getTipologiaTirocinio() != null
				&& !"".equalsIgnoreCase(dto.getDatiAggiuntivi().getDatiTirocinio().getTipologiaTirocinio())) {

			DatiTirocinioDTO datiTirocinioDTO = dto.getDatiAggiuntivi().getDatiTirocinio();

			// 72160-1501: gestione della denominazione ente promotore in caso
			// di cpi

			if (datiTirocinioDTO.getTipologiaPromotore() != null) {
				//28/09/22 Anna-> COM_T_TIPO_ENTE_PROM_TIROCINIO - in questa tabella per il cod ministeriale 01 abbiamo 2 valori aperti, 
				// in quel caso forziamo il valore del ministero che è 51
				TipoEntePromTirocinio tipoEntePromTirocinio = null;
				if (ComonlConstants.COD_TIPO_ENTE_PROMOTORE_TIROCINIO_01_MIN.equalsIgnoreCase(datiTirocinioDTO.getTipologiaPromotore())) {
					 tipoEntePromTirocinio = new TipoEntePromTirocinio();
					tipoEntePromTirocinio.setId(ComonlConstants.ID_TIPO_ENTE_PROMOTORE_TIROCINIO_1_COMONL);
				} else {
						tipoEntePromTirocinio = (TipoEntePromTirocinio) decodificaDad.getTfromMin(
							TipoEntePromTirocinio.class, datiTirocinioDTO.getTipologiaPromotore(), dataRiferimento);

					if (tipoEntePromTirocinio == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_ENTE_PROM_TIROCINIO,
								datiTirocinioDTO.getTipologiaPromotore(), "BLOCCO TIROCINIO TipologiaPromotore");
					}
				}
				comunicazione.getRapporto().setTipoEntePromTirocinio(tipoEntePromTirocinio);
			}


			if (datiTirocinioDTO.getTipologiaPromotore() != null &&
					comunicazione.getRapporto().getTipoEntePromTirocinio()!=null &&
					ComonlConstants.ID_TIPO_ENTE_TIROCINIO_CPI.equals(comunicazione.getRapporto().getTipoEntePromTirocinio().getId()) &&
					!StringUtils.isBlank(datiTirocinioDTO.getCodCpiPromotore())
			) {

				// dto.getDatiAggiuntivi().getDatiTirocinio().setAltroSoggettoPromotore(
				// ComonlConstants.CPI_DI +
				// comunicazione.getRapporto().getCpi().getDsComTCpi());
				// FIXME è il dato giusto
				Cpi cpi = (Cpi) decodificaDad.getTfromMin(Cpi.class, datiTirocinioDTO.getCodCpiPromotore(),
						dataRiferimento);

				if (cpi == null) {
					addErrorComDImportErrore(errors, ComonlConstants.COM_T_CPI, datiTirocinioDTO.getCodCpiPromotore(),
							"BLOCCO TIROCINIO CodCpiPromotore");
				}

				comunicazione.getRapporto().setCpi(cpi);
			} else {
				comunicazione.getRapporto().setDsSoggPromTirocinio(datiTirocinioDTO.getAltroSoggettoPromotore());
			}

			// 71862 - Attivita' per gestione pdf piano formativo / nuovi
			// tirocinii - START
			TipologiaTirocinio tipologiaTirocinio = (TipologiaTirocinio) decodificaDad
					.getTfromMin(TipologiaTirocinio.class, datiTirocinioDTO.getTipologiaTirocinio(), dataRiferimento);

			if (tipologiaTirocinio == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPOLOGIA_TIROCINIO,
						datiTirocinioDTO.getTipologiaTirocinio(),
						"BLOCCO TIROCINIO datiTirocinioDTO.getTipologiaTirocinio()");
			}

			comunicazione.getRapporto().setTipologiaTirocinio(tipologiaTirocinio);

			CategTirocinante categTirocinante = (CategTirocinante) decodificaDad.getTfromMin(CategTirocinante.class,
					datiTirocinioDTO.getCategoriaTirocinante(), dataRiferimento);

			if (categTirocinante == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_CATEG_TIROCINANTE,
						datiTirocinioDTO.getCategoriaTirocinante(), "BLOCCO TIROCINIO CategoriaTirocinante");
			}

			comunicazione.getRapporto().setCategTirocinante(categTirocinante);

			// 71862 - Attivita' per gestione pdf piano formativo / nuovi
			// tirocinii - END

			comunicazione.getRapporto().setCfSoggPromotoreTirocinio(datiTirocinioDTO.getCFSoggettoPromotoreTirocinio());
			// xxxxx
			// Adeguamenti
			// 2014
			// -
			// cFSoggettoPromotoreTirocinio

		}

	}

	protected void handleDatiDiInvio(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dto,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		comunicazione.setCodiceComunicazioneReg(codiceRegionale);

		if (dto.getDatiInvio() != null && dto.getDatiInvio().getSoggettoComunicante() != null) {
			TipoSoggettoAbilitato tipoSoggettoAbilitato = (TipoSoggettoAbilitato) decodificaDad.getTfromMin(
					TipoSoggettoAbilitato.class, dto.getDatiInvio().getSoggettoComunicante(), dataRiferimento);

			if (tipoSoggettoAbilitato == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_SOGGETTO_ABILITATO,
						dto.getDatiInvio().getSoggettoComunicante(), "DATI DI INVIO SoggettoComunicante");
			}

			comunicazione.setTipoSoggettoAbilitato(tipoSoggettoAbilitato);
			//28/09/22 Anna-> cod fiscale soggetto se presente tipo soggettto valorizzare anche quel dato
			if (tipoSoggettoAbilitato != null && dto.getDatiInvio().getCodFiscaleSoggettoComunicante() != null) {
				comunicazione.setCodiceFiscaleSoggetto(dto.getDatiInvio().getCodFiscaleSoggettoComunicante());
			} else {
				comunicazione.setCodiceFiscaleSoggetto(null);
			}
			
		}
		if (dto.getDatiInvio() != null && dto.getDatiInvio().getEmailDelegato() != null) {
			if (comunicazione.getTipoSoggettoAbilitato() != null) {
				comunicazione.setEmailsoggetto(dto.getDatiInvio().getEmailDelegato());
				comunicazione.setEmail(dto.getDatiInvio().getEmailDelegato());
			} else {
				comunicazione.setEmail(dto.getDatiInvio().getEmailDelegato());
			}
			
		}
		if (dto.getDatiInvio() != null && dto.getDatiInvio().getCodFiscaleSoggettoComunicante() != null) {
			// comunicazione.getDelegato().setCfDelegato(dto.getDatiInvio().getCodFiscaleSoggettoComunicante());
			comunicazione.setCodiceEnte(dto.getDatiInvio().getCodFiscaleSoggettoComunicante());
		}

		String codRegPrec = dto.getDatiInvio().getCodiceComunicazionePrecedente();
		comunicazione.setCodiceComunRegPrec(codRegPrec);
		// **********************************************************************************************

		// **********************************************************************************************
		comunicazione.setDtInvio(dto.getDatiInvio().getDataInvio());

		comunicazione.setDsCausaForzamaggiore(
				ComonlStringUtils.eliminaCaratteriSpeciali(dto.getDatiInvio().getDescrizioneCausaForzaMaggiore()));

		if (dto.getDatiInvio() != null && dto.getDatiInvio().getFlgAssunzionePerCausaForzaMaggiore() != null
				&& dto.getDatiInvio().getDescrizioneCausaForzaMaggiore() != null && ComonlConstants.FLAG_SI_DESC
						.equalsIgnoreCase(dto.getDatiInvio().getFlgAssunzionePerCausaForzaMaggiore())) {
			comunicazione.setFlgCausaForzamaggiore(ComonlConstants.FLAG_S);
		} else {
			comunicazione.setFlgCausaForzamaggiore(ComonlConstants.FLAG_N);
			comunicazione.setDsCausaForzamaggiore(null);
		}

		if (dto.getDatiInvio() != null && dto.getDatiInvio().getFlgAssunzionePerCausaForzaMaggiore() != null) {
			comunicazione.setDsMotivoUrgenza(dto.getDatiInvio().getMotivoUrgenza());
		}

		// if (dto.getDatiInvio() != null &&
		// dto.getDatiInvio().getProtocolloSistema() != null) {
		// comunicazione.setNumProtCom(Long.parseLong(dto.getDatiInvio().getProtocolloSistema()));
		// }

		// TIPO COMUNICAZIONE_TU
		// di default l'id e' quello della comunicazione 'obbligatoria' standard
		// Long idComTTipoComunicazioneTu =
		// comunicazione.getTipoComunicazioneTu().getId();

		if (dto.getDatiInvio() != null && dto.getDatiInvio().getTipoComunicazione() != null) {
			TipoComunicazioneTu tipoComunicazioneTu = (TipoComunicazioneTu) decodificaDad
					.getTfromMin(TipoComunicazioneTu.class, dto.getDatiInvio().getTipoComunicazione(), dataRiferimento);

			if (tipoComunicazioneTu == null) {
				addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_COMUNICAZIONE_TU,
						dto.getDatiInvio().getTipoComunicazione(), "DATI DI INVIO TipoComunicazione");
			}

			comunicazione.setTipoComunicazioneTu(tipoComunicazioneTu);
			if (ComonlConstants.TIPO_COMUNICAZIONE_RETTIFICA_3_LONG.equals(tipoComunicazioneTu.getId())) {
				comunicazione.setFlgRettifica(ComonlConstants.FLAG_S);
			}
		}
		EsitoValidazioneComunicazione esito = ComunicazioneTUValidator.validaComunicazione(dto);
		String codTrasformazione = null;
		if ((dto.getDatiAggiuntivi().getTipoTracciatoXML().equals(TUConstants.TIPO_TRACCIATO_UNILAV)
				|| dto.getDatiAggiuntivi().getTipoTracciatoXML().equals(TUConstants.TIPO_TRACCIATO_UNIDOM))
				&& esito.getTipoComunicazione().equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)
				&& dto.getTrasformazione() != null) {
			codTrasformazione = dto.getTrasformazione().getCodTrasformazione();
		} else if (dto.getDatiAggiuntivi().getTipoTracciatoXML().equals(TUConstants.TIPO_TRACCIATO_UNISOM)
				&& (esito.getTipoComunicazione().equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)
						|| esito.getTipoComunicazione()
								.equals(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE)
						|| esito.getTipoComunicazione().equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO)
						|| esito.getTipoComunicazione()
								.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_MISSIONE))) {
			codTrasformazione = dto.getDatiVariazione().getDatiTrasformazione().getCodiceTrasformazione();

		}
		String tipoComunicazione = determinaTipoComunicazione(esito, codTrasformazione, dto.getDatiAggiuntivi().getTipoTracciatoXML());
		comunicazione.setTipoComunicazione(new TipoComunicazione());
		comunicazione.getTipoComunicazione().setId(tipoComunicazione);

	}

	protected void handleDatiInizioRapporto(Comunicazione comunicazione, ComunicazioneTracciatoUnicoDTO dtoSpicom,
			DecodificaDad decodificaDad, List<ComDImportErrore> errors) {

		if (dtoSpicom.getDatiInizio() != null) {

			if (ControlliSuOggettoComunicazione.isSpicom_UNISOMM(dtoSpicom)) {

				TranscodeUnisomSpicomHelper transcodeUnisomSpicomHelper = new TranscodeUnisomSpicomHelper(decodificaDad,
						errors, dtoSpicom, comunicazione);
				transcodeUnisomSpicomHelper.transcodeComunicazioneDTO();
			}
			// tipo tracciato UNIDOM
			else if (ControlliSuOggettoComunicazione.isSpicom_UNIDOM(dtoSpicom)) {
				Rapporto rapp = new Rapporto();
				InizioRapportoUniDomDTO rapDto = new InizioRapportoUniDomDTO();
				rapDto = (InizioRapportoUniDomDTO) dtoSpicom.getDatiInizio();

				// DATA INIZIO RAPPORTO
				if (rapDto.getDataInizio() != null) {
					comunicazione.setDataRiferimento(dtoSpicom.getDatiInizio().getDataInizio());
					rapp.setDtInizioRapporto(rapDto.getDataInizio());
				}

				// ENTE PREVIDENZIALE INSERITO DALL'UTENTE
				if (rapDto != null && rapDto.getEntePrevidenziale() != null) {
					EntePrevidenziale entePrevidenziale = (EntePrevidenziale) decodificaDad
							.getTfromMin(EntePrevidenziale.class, rapDto.getEntePrevidenziale(), dataRiferimento);

					if (entePrevidenziale == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ENTE_PREVIDENZIALE,
								rapDto.getEntePrevidenziale(), "InizioRapportoUniDomDTO EntePrevidenziale");
					}

					rapp.setEntePrevidenziale(entePrevidenziale);
				}

				if (rapDto != null && rapDto.getDataFine() != null) {
					rapp.setDtFineRapporto(rapDto.getDataFine());
				}

				if (rapDto != null && rapDto.getCcnlApplicato() != null) {
					Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, rapDto.getCcnlApplicato(),
							dataRiferimento);

					if (ccnl == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_CCNL, rapDto.getCcnlApplicato(),
								"InizioRapportoUniDomDTO CcnlApplicato");
					}

					rapp.setCcnl(ccnl);
				}

				if (ComonlUtility.isNotVoid(rapDto.getFlgCcnlApplicato())
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getFlgCcnlApplicato())) {
					rapp.setFlgCcnlApplicato(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgCcnlApplicato(ComonlConstants.FLAG_N);
				}

				if (ComonlUtility.isNotVoid(rapDto.getLivelloInquadramento())) {

					Long idLivello = decodificaDad.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(
							rapDto.getCcnlApplicato(), rapDto.getLivelloInquadramento(), dataRiferimento);

					if (idLivello != null) {
						LivelloRetribuzione livelloRetribuzione = decodificaDad.getLivelloRetribuzioneById(idLivello);
						if (livelloRetribuzione == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_LIVELLO_RETRIBUZIONE,
									rapDto.getLivelloInquadramento(), "DATI INIZIO RAPPORTO LivelloInquadramento");
						}
						rapp.setLivelloRetribuzione(livelloRetribuzione);
					} else {
						rapp.setLivelloInquadramento(rapDto.getLivelloInquadramento());
					}

				}

				if (rapDto.getOreSettimanaliMedie() != null) {
					rapp.setNumOreSettMed(BigDecimal.valueOf(rapDto.getOreSettimanaliMedie()));
				}

				if (rapDto.getPatInail() != null) {
					rapp.setPatInail(rapDto.getPatInail());
				}

				if (rapDto.getQualificaProfessionaleIstat() != null
						&& !"".equalsIgnoreCase(rapDto.getQualificaProfessionaleIstat())) {

					Istat2001livello5 istat2001livello5 = (Istat2001livello5) decodificaDad.getTfromMin(
							Istat2001livello5.class, rapDto.getQualificaProfessionaleIstat(), dataRiferimento);

					if (istat2001livello5 == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ISTAT2001LIVELLO5,
								rapDto.getQualificaProfessionaleIstat(),
								"InizioRapportoUniDomDTO QualificaProfessionaleIstat");
					}

					rapp.setIstatLivello5(istat2001livello5);
				}

				if (rapDto.getRetribuzione() != null) {
					rapp.setRetribuzioneCompenso(BigDecimal.valueOf(rapDto.getRetribuzione()));
				}

				if (rapDto.getTipologiaContrattuale() != null
						&& !"".equalsIgnoreCase(rapDto.getTipologiaContrattuale())) {

					TipoContratti tipoContratti = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class,
							rapDto.getTipologiaContrattuale(), dataRiferimento);

					if (tipoContratti == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_CONTRATTI,
								rapDto.getTipologiaContrattuale(), "InizioRapportoUniDomDTO TipologiaContrattuale");
					}

					rapp.setTipoContratti(tipoContratti);
				}

				if (rapDto.getTipoOrario() != null && !"".equalsIgnoreCase(rapDto.getTipoOrario())) {

					TipoOrario tipoOrario = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class,
							rapDto.getTipoOrario(), dataRiferimento);

					if (tipoOrario == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_ORARIO, rapDto.getTipoOrario(),
								"InizioRapportoUniDomDTO TipoOrario");
					}

					rapp.setTipoOrario(tipoOrario);
				}

				comunicazione.setRapporto(rapp);
			} else { // UNILAV
				Rapporto rapp = new Rapporto();
				InizioRapportoUnilavDTO rapDto = new InizioRapportoUnilavDTO();
				if (dtoSpicom.getDatiInizio() != null) {
					rapDto = (InizioRapportoUnilavDTO) dtoSpicom.getDatiInizio();
				}

				// CONTRATTO APPLICATO
				if (rapDto != null && rapDto.getCcnlApplicato() != null) {
					Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class, rapDto.getCcnlApplicato(),
							dataRiferimento);

					if (ccnl == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_CCNL, rapDto.getCcnlApplicato(),
								"UNILAV CcnlApplicato)");
					}

					rapp.setCcnl(ccnl);
				}

				// NON ESISTONO PIU' LE AGEVOLAZIONI ... setto a NULL
				if (rapDto != null && rapDto.getCodAgevolazioni() != null) {
					// FIXME andrebbero aggiunte nel dto e nelle entity del
					// rapporto perchè se
					// arriva una co vecchia che le ha non sappiamo dove
					// salvarle
				}

				if (rapDto != null && rapDto.getDataFine() != null) {
					rapp.setDtFineRapporto(rapDto.getDataFine());
				}

				// DATA INIZIO RAPPORTO
				if (rapDto != null && rapDto.getDataInizio() != null) {
					rapp.setDtInizioRapporto(rapDto.getDataInizio());
				}
				if (rapDto != null && rapDto.getDataFinePeriodoFormativo() != null) {
					rapp.setDtFinePeriodoFormativo(rapDto.getDataFinePeriodoFormativo());
				}

				// LEGGE 68
				if (rapDto != null && rapDto.getDataNullaostaLegge68OConvenzione() != null) {
					rapp.setDtLegge68(rapDto.getDataNullaostaLegge68OConvenzione());
				}
				// ENTE PREVIDENZIALE COMBO
				if (rapDto != null && rapDto.getEntePrevidenziale() != null
						&& !"".equalsIgnoreCase(rapDto.getEntePrevidenziale())) {
					EntePrevidenziale entePrevvidenziale = (EntePrevidenziale) decodificaDad
							.getTfromMin(EntePrevidenziale.class, rapDto.getEntePrevidenziale(), dataRiferimento);

					if (entePrevvidenziale == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ENTE_PREVIDENZIALE,
								rapDto.getEntePrevidenziale(), "UNILAV EntePrevidenziale");
					}

					rapp.setEntePrevidenziale(entePrevvidenziale);
				}

				if (rapDto != null && rapDto.getCodEntePrevidenziale() != null
						&& !"".equalsIgnoreCase(rapDto.getCodEntePrevidenziale())) {
					rapp.setCodiceEntePrev(rapDto.getCodEntePrevidenziale());
				}

				if (rapDto != null && rapDto.getFlgSocioLavoratore() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getFlgSocioLavoratore())) {
					rapp.setFlgSocioLavoratore(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgSocioLavoratore(ComonlConstants.FLAG_N);
				}

				if (rapDto != null && rapDto.getLavInMobilita() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getLavInMobilita())) {
					rapp.setFlgLavInMobilita(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgLavInMobilita(ComonlConstants.FLAG_N);
				}

				if (rapDto != null && rapDto.getLavoroStagionale() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getLavoroStagionale())) {
					rapp.setFlgLavStagionale(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgLavStagionale(ComonlConstants.FLAG_N);
				}

				if (rapDto != null && rapDto.getFlgAssunzioneObbligatoria() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getFlgAssunzioneObbligatoria())) {
					rapp.setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_N);
				}

				// adeguamenti 2016: il codice che viene passato in questo campo
				// e' quello che
				// in passato si trovava in COM_T_NORM_ASS_68
				// mandato a SILP ma non al ministero.
				// adesso mandiamo nei dati del rapporto il codice ministeriale
				// ma continuiamo a
				// mandare SILP il codice vecchio che
				// ora si trova nella tabella COM_T_CATEG_LAV_ASS_OBBL, per la
				// distinzione tra
				// le voci
				// in cui sono divise le descrizini della L68
				if (rapDto != null && rapDto.getCategoriaLavoratoreAssunzioneObbligatoria() != null) {
					CategLavAssObbl categLavAssObbl = (CategLavAssObbl) decodificaDad.getTfromMin(CategLavAssObbl.class,
							rapDto.getCategoriaLavoratoreAssunzioneObbligatoria(), dataRiferimento);

					if (categLavAssObbl == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_CATEG_LAV_ASS_OBBL,
								rapDto.getCategoriaLavoratoreAssunzioneObbligatoria(),
								"UNILAV CategoriaLavoratoreAssunzioneObbligatoria");
					}

					rapp.setCategLavAssObbl(categLavAssObbl);
				}

				if (rapDto != null && rapDto.getFlgLavoroAgricoltura() != null
						&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rapDto.getFlgLavoroAgricoltura())) {
					rapp.setFlgLavoroAgricoltura(ComonlConstants.FLAG_S);
				} else {
					rapp.setFlgLavoroAgricoltura(ComonlConstants.FLAG_N);
				}

				if (rapDto != null && rapDto.getGiornateLavorativePrevisteAgricoltura() != null) {
					rapp.setGiornateLavPreviste(rapDto.getGiornateLavorativePrevisteAgricoltura());
				}

				if (rapDto != null && rapDto.getLivelloInquadramento() != null
						&& !"".equalsIgnoreCase(rapDto.getLivelloInquadramento())) {
					Long idLivello = decodificaDad.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(
							rapDto.getCcnlApplicato(), rapDto.getLivelloInquadramento(), dataRiferimento);
					if (idLivello != null) {
						LivelloRetribuzione livelloRetribuzione = decodificaDad.getLivelloRetribuzioneById(idLivello);
						if (livelloRetribuzione == null) {
							addErrorComDImportErrore(errors, ComonlConstants.COM_T_LIVELLO_RETRIBUZIONE,
									rapDto.getLivelloInquadramento(), "UNILAV LivelloInquadramento");
						}
						rapp.setLivelloRetribuzione(livelloRetribuzione);
					} else {
						rapp.setLivelloInquadramento(rapDto.getLivelloInquadramento());
					}
				}

				if (rapDto != null && rapDto.getNumeroAttoLegge68() != null) {
					rapp.setNumeroAttoLegge68(rapDto.getNumeroAttoLegge68());
				}

				if (rapDto != null && rapDto.getOreSettimanaliMedie() != null) {
					rapp.setNumOreSettMed(BigDecimal.valueOf(rapDto.getOreSettimanaliMedie()));
				}

				if (rapDto != null && rapDto.getPatInail() != null) {
					rapp.setPatInail(rapDto.getPatInail());
				}

				if (rapDto != null && rapDto.getQualificaProfessionaleIstat() != null) {

					Istat2001livello5 istat2001livello5 = (Istat2001livello5) decodificaDad.getTfromMin(
							Istat2001livello5.class, rapDto.getQualificaProfessionaleIstat(), dataRiferimento);

					if (istat2001livello5 == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_ISTAT2001LIVELLO5,
								rapDto.getQualificaProfessionaleIstat(), "UNILAV QualificaProfessionaleIstat");
					}

					rapp.setIstatLivello5(istat2001livello5);
				}

				if (rapDto != null && rapDto.getRetribuzione() != null) {
					rapp.setRetribuzioneCompenso(BigDecimal.valueOf(rapDto.getRetribuzione()));
				}

				if (rapDto != null && rapDto.getTipologiaContrattuale() != null
						&& !"".equalsIgnoreCase(rapDto.getTipologiaContrattuale())) {
					TipoContratti tipoContratti = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class,
							rapDto.getTipologiaContrattuale(), dataRiferimento);

					if (tipoContratti == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_CONTRATTI,
								rapDto.getTipologiaContrattuale(), "UNILAV TipologiaContrattuale");
					}

					rapp.setTipoContratti(tipoContratti);
				}

				if (rapDto != null && rapDto.getTipoOrario() != null) {
					TipoOrario tipoOrario = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class,
							rapDto.getTipoOrario(), dataRiferimento);

					if (tipoOrario == null) {
						addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_ORARIO, rapDto.getTipoOrario(),
								"UNILAV TipoOrario");
					}

					rapp.setTipoOrario(tipoOrario);
				}

				if (rapDto != null && rapDto.getTipoLavorazione() != null
						&& !"".equalsIgnoreCase(rapDto.getTipoLavorazione())) {
					rapp.setTipoLavorazione(rapDto.getTipoLavorazione());
				}

				comunicazione.setRapporto(rapp);
			}

		}
	}

	public CtuSpicomToComunicazioneMapper(ComunicazioneTracciatoUnicoDTO dtoSpicom, DecodificaDad decodificaDad) {
		// Prevent instantiation
		this.dtoSpicom = dtoSpicom;
		this.decodificaDad = decodificaDad;
		this.dataRiferimento = dtoSpicom.getDatiInvio().getDataInvio();
		this.errors = new ArrayList<ComDImportErrore>();

		this.codiceRegionale = dtoSpicom.getDatiInvio().getCodiceComunicazione();
		this.tipoTracciato = dtoSpicom.getDatiAggiuntivi().getTipoTracciatoXML();

	}
	
	public CtuSpicomToComunicazioneMapper(ComunicazioneTracciatoUnicoDTO dtoSpicom, DecodificaDad decodificaDad, Date dataRiferimento) {
		// Prevent instantiation
		this.dtoSpicom = dtoSpicom;
		this.decodificaDad = decodificaDad;
		this.dataRiferimento = dataRiferimento;
		this.errors = new ArrayList<ComDImportErrore>();

		this.codiceRegionale = dtoSpicom.getDatiInvio().getCodiceComunicazione();
		this.tipoTracciato = dtoSpicom.getDatiAggiuntivi().getTipoTracciatoXML();
	}

	private void addErrorComDImportErrore(List<ComDImportErrore> errors, String tabellaDestinazione, String datoInput,
			String descrErrore) {

		ComDImportErrore errorToInsert = new ComDImportErrore();
		errorToInsert.setTabellaDestinazione(tabellaDestinazione);
		errorToInsert.setCodComunicazioneReg(codiceRegionale);
		errorToInsert.setIdSpiTrasmissione(BigDecimal.ZERO);
		errorToInsert.setDatoInput(datoInput);
		errorToInsert.setDtElaborazione(new Date());
		errorToInsert.setIdComTTipoTracciato(tipoTracciato);
		errorToInsert.setDsErrore(descrErrore);
		errors.add(errorToInsert);
	}

	private void addErrIfNull(Object obj, List<ComDImportErrore> errors, String tabellaDestinazione, String datoInput,
			String descrErrore) {

		if(obj == null) {
			addErrorComDImportErrore(errors, tabellaDestinazione, datoInput, descrErrore);
		}
	}

	public List<ComDImportErrore> getErrors() {
		return errors;
	}




	private String determinaTipoComunicazione(EsitoValidazioneComunicazione esito, String codTrasf, String tipoTracciatoXML) {
		String tipoComTu = esito.getTipoComunicazione();
		
		// 28/09/22 Anna -> originale UG, importa un UG con tipo ASS -
		// SOLO PER UNIURG che non viene trattato. Deve mettere nel tipoComunicazione URG
		if (TUConstants.TIPO_TRACCIATO_UNIURG.equalsIgnoreCase(tipoTracciatoXML)) {
			return "URG";
		} else if (tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) || tipoComTu
				.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE_CONTESTUALE_A_INIZIO_MISSIONE)) {
			return "ASS";
		} else if (tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA_CONTESTUALE_A_PROROGA_DI_MISSIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA_MISSIONE)) {
			return "PRO";
		} else if (tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_FINE_MISSIONE)) {
			return "CES";
		} else if ((tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_MISSIONE))
				&& !codTrasf.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TL)
				&& !codTrasf.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DL)) {
			return "TRS";
		} else if ((tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_MISSIONE))
				&& (codTrasf.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TL)
						|| codTrasf.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DL))) {
			return "TRD";
		} else if (tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_VARIAZIONE_RAGIONE_SOCIALE)
				|| tipoComTu.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_RAMO_AZIENDALE)) {
			return "VAR";
		} else {
			return null;
		}
	}

	private void mapDatore(Datore datore, DatoreDiLavoroDTO datoreDto) {
		datore.setCodiceFiscale(datoreDto.getCodFiscale().toUpperCase());
		datore.setDsDenominazioneDatore(ComonlStringUtils.formatDescrDatore(datoreDto.getDenominazione()));

		Atecofin atecofin = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, datoreDto.getSettore(),
				dataRiferimento);

		addErrIfNull(atecofin,errors, ComonlConstants.COM_T_ATECOFIN,datoreDto.getSettore(),
					"DATORE Settore");

		datore.setAtecofin(atecofin);

		// SEDE LEGALE
		if(datore.getSedeLegale() == null) {
			datore.setSedeLegale(new SedeLavoro());
		}
		datore.getSedeLegale().setFlgSedeLegale(ComonlConstants.FLAG_S);

		if (datoreDto.getComuneSedeLegale().startsWith("Z")) {
			StatiEsteri statiEsteri = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
					datoreDto.getComuneSedeLegale(),
					dataRiferimento);
			datore.getSedeLegale().setStatiEsteri(statiEsteri);
			addErrIfNull(statiEsteri, errors, ComonlConstants.COM_T_STATI_ESTERI,
						datoreDto.getComuneSedeLegale(),
						"DATORE ComuneSedeLegale");
		} else {
			Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class,
					datoreDto.getComuneSedeLegale(),
					dataRiferimento);
			datore.getSedeLegale().setComune(comune);
			addErrIfNull(comune, errors, ComonlConstants.COM_T_COMUNE,datoreDto.getComuneSedeLegale(),
						"DATORE ComuneSedeLegale");
		}
		datore.getSedeLegale().setEmail(datoreDto.getEmailSedeLegale());
		datore.getSedeLegale().setFax(datoreDto.getFaxSedeLegale());
		datore.getSedeLegale().setTelefono(datoreDto.getTelefonoSedeLegale());
		datore.getSedeLegale().setIndirizzo(datoreDto.getIndirizzoSedeLegale());
		datore.getSedeLegale().setCodCap(datoreDto.getCapSedeLegale());
	}

	// flag da spicom a Comonl
	private String flg(String flg) {
		if(flg == null) return null;

		if(flg.equalsIgnoreCase(ComonlConstants.FLAG_SI_DESC)) {
			return ComonlConstants.FLAG_S;
		} else {
			return ComonlConstants.FLAG_N;
		}
	}

	private BigDecimal bd(Long l) {
		if(l == null) return null;

		return new BigDecimal(l);
	}
}

