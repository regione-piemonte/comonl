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
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cessazionerl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.EntePrevidenziale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Trasformazionerl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.spicom.dto.AgenziaSomministrazioneDTO;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.DatiTrasformazioneDTO;
import it.csi.spicom.dto.DatiVariazioneDTO;
import it.csi.spicom.dto.DittaUtilizzatriceDTO;
import it.csi.spicom.dto.InizioRapportoUniSommDTO;

public class TranscodeUnisomSpicomHelper {

    private String codiceRegionale = null;
    private String tipoTracciato = null;
    private Date dataRiferimento = null;
    private DecodificaDad decodificaDad = null;
    private List<ComDImportErrore> errors = null;
    private ComunicazioneTracciatoUnicoDTO dto = null;
    private Comunicazione comunicazione = null;



    public TranscodeUnisomSpicomHelper(DecodificaDad decodificaDad, List<ComDImportErrore> errors,
	    ComunicazioneTracciatoUnicoDTO dto, Comunicazione comunicazione) {
	super();
	this.decodificaDad = decodificaDad;
	this.errors = errors;
	this.dto = dto;
	this.comunicazione = comunicazione;
	this.codiceRegionale = dto.getDatiInvio().getCodiceComunicazione();
	this.tipoTracciato = dto.getDatiAggiuntivi().getTipoTracciatoXML();
	this.dataRiferimento = dto.getDatiInvio().getDataInvio();
    }

    public void transcodeComunicazioneDTO() {
    	
    

	if (dto.getMissione() != null) {
		transcodeMissioneDTO();

		if (dto.getDittaUtilizzatrice() != null) {
			transcodeDittaUtilizzatriceDTO(dto.getDittaUtilizzatrice());
		}
	}

	if (dto.getDatiInizio() != null) {
	    transcodeInizioRapportoDTO();
	}

	transcodeDatiVariazioneDTO();
    }

    //
    // // DATI VARIAZIONE PER UNISOMM
    private void transcodeDatiVariazioneDTO() {

	if (dto.getDatiVariazione() != null) {

	    DatiVariazioneDTO datiVariazioneDto = dto.getDatiVariazione();

	    String tipoCom = datiVariazioneDto.getCodiceVariazione();

	    if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
		if (datiVariazioneDto.getDatiCessazione() != null
			&& datiVariazioneDto.getDatiCessazione().getDataCessazione() != null
			&& datiVariazioneDto.getDatiCessazione().getCodiceCausa() != null) {

		    Cessazionerl cessazionerl = (Cessazionerl) decodificaDad.getTfromMin(Cessazionerl.class,
			    datiVariazioneDto.getDatiCessazione().getCodiceCausa(),
			    dataRiferimento);

		    if (cessazionerl == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_CESSAZIONERL,
				datiVariazioneDto.getDatiCessazione().getCodiceCausa(),
				"TRANSCODE Cessazione - CodiceCausa");
		    }

		    comunicazione.getMissione().setCessazionerl(cessazionerl);
		    comunicazione.getMissione().setDtCessazione(datiVariazioneDto.getDatiCessazione().getDataCessazione());
		    comunicazione.setDataRiferimento(datiVariazioneDto.getDatiCessazione().getDataCessazione());
		}

	    } else if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)) {
		if (datiVariazioneDto.getDatiTrasformazione() != null
			&& datiVariazioneDto.getDatiTrasformazione().getDataTrasformazione() != null
			&& datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione() != null) {

		    Trasformazionerl trasformazionerl = (Trasformazionerl) decodificaDad.getTfromMin(
			    Trasformazionerl.class, datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione(),
			    dataRiferimento);

		    if (trasformazionerl == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_TRASFORMAZIONERL,
				datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione(),
				"TRANSCODE Trasformazione - CodiceTrasformazione");
		    }

		    comunicazione.getMissione().setTrasformazionerl(trasformazionerl);
		    comunicazione.setDataRiferimento(datiVariazioneDto.getDatiTrasformazione().getDataTrasformazione());
		}

	    } else if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_PROROGA)) {
		if (datiVariazioneDto.getDatiProroga() != null
			&& datiVariazioneDto.getDatiProroga().getDataFineProroga() != null) {

		    comunicazione.getMissione()
			    .setDtFineProroga(datiVariazioneDto.getDatiProroga().getDataFineProroga());

		}

		//data inserimento se la data inserimento <= data proroga,  la data fine proroga negli altri casi, per le proroghe
		if (datiVariazioneDto.getDatiProroga().getDataFineProroga().after(dto.getDatiInvio().getDataInvio())) {
		    comunicazione.setDataRiferimento(dto.getDatiInvio().getDataInvio());
		} else {
		    comunicazione.setDataRiferimento(datiVariazioneDto.getDatiProroga().getDataFineProroga());
		}

	    } else if (tipoCom.equalsIgnoreCase(ComonlConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO)) {
		if (comunicazione.getTipoTrasferimento() != null) {
		    DatiTrasformazioneDTO datiTrasformazioneDto = new DatiTrasformazioneDTO();
		    datiVariazioneDto.setDatiTrasformazione(datiTrasformazioneDto);

		    datiTrasformazioneDto.setDataTrasformazione(comunicazione.getMissione().getDtTrasformazione());
		    comunicazione.setDataRiferimento(comunicazione.getMissione().getDtTrasformazione());


		    datiTrasformazioneDto
			    .setCodiceTrasformazione(comunicazione.getTipoTrasferimento().getCodTipotrasferimentoMin());

		}

		if (datiVariazioneDto.getDatiTrasformazione() != null
			&& datiVariazioneDto.getDatiTrasformazione().getDataTrasformazione() != null
			&& datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione() != null) {

		    comunicazione.getMissione()
			    .setDtTrasformazione(datiVariazioneDto.getDatiTrasformazione().getDataTrasformazione());

		    TipoTrasferimento tipoTrasferimento = (TipoTrasferimento) decodificaDad.getTfromMin(
			    TipoTrasferimento.class,
			    datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione(),
			    dataRiferimento);

		    if (tipoTrasferimento == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_TRASFERIMENTO,
				datiVariazioneDto.getDatiTrasformazione().getCodiceTrasformazione(),
				"TRANSCODE Trasformazione - CodiceTrasformazione");
		    }

		    comunicazione.setTipoTrasferimento(tipoTrasferimento);
		}
	    }
	}
    }

    // // DATI RAPPORTO
    private void transcodeInizioRapportoDTO() {
	
		InizioRapportoUniSommDTO rappUSDto = (InizioRapportoUniSommDTO) dto.getDatiInizio();
		comunicazione.setDatoreAttuale(new Datore());
		if (comunicazione.getRapporto() == null) {
			comunicazione.setRapporto(new Rapporto());
		}
	
		if (dto.getDatoreDiLavoro() != null) {
		    if (rappUSDto.getNumAgenziaSomministrazione() != null) {
			comunicazione.getDatoreAttuale()
				.setNumAgenziaSommin(String.valueOf(rappUSDto.getNumAgenziaSomministrazione()));
		    }
		}
	
		// TIPOLOGIA RAPPORTO
		if (rappUSDto.getTipologiaContrattuale() != null
			&& !"".equalsIgnoreCase(rappUSDto.getTipologiaContrattuale())) {
	
		    TipoContratti tipoContratti = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class,
			    rappUSDto.getTipologiaContrattuale(), dataRiferimento);
	
		    if (tipoContratti == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_CONTRATTI,
				rappUSDto.getTipologiaContrattuale(), "TRANSCODE TipologiaContrattuale");
		    }
	
		    comunicazione.getRapporto().setTipoContratti(tipoContratti);
		}
	
		// ENTE PREVIDENZIALE COMBO
		if (rappUSDto.getEntePrevidenziale() != null && !"".equalsIgnoreCase(rappUSDto.getEntePrevidenziale())) {
	
		    EntePrevidenziale entePrevidenziale = (EntePrevidenziale) decodificaDad.getTfromMin(EntePrevidenziale.class,
			    rappUSDto.getEntePrevidenziale(), dataRiferimento);
	
		    if (entePrevidenziale == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_ENTE_PREVIDENZIALE,
				rappUSDto.getEntePrevidenziale(), "TRANSCODE EntePrevidenziale");
		    }
	
		    comunicazione.getRapporto().setEntePrevidenziale(entePrevidenziale);
		}
	
		// ENTE PREVIDENZIALE INSERITO DALL'UTENTE
		if (rappUSDto.getCodEntePrevidenziale() != null) {
		    String codiceEntePrevidenziale = rappUSDto.getCodEntePrevidenziale().equals("") ? null
			    : rappUSDto.getCodEntePrevidenziale();
		    comunicazione.getRapporto()
			    .setCodiceEntePrev(ComonlStringUtils.eliminaCaratteriSpeciali(codiceEntePrevidenziale));
		}
	
		// DATA INIZIO RAPPORTO
		if (rappUSDto.getDataInizio() != null) {
		    comunicazione.getRapporto().setDtInizioRapporto(rappUSDto.getDataInizio());
		}
	
		// DATA FINE RAPPORTO
		if (rappUSDto.getDataFine() != null) {
		    comunicazione.getRapporto().setDtFineRapporto(rappUSDto.getDataFine());
		}
		// start - 72160 - COMONL-1360 - data fine periodo formativo
		if (rappUSDto.getDataFinePeriodoFormativo() != null) {
		    comunicazione.getRapporto().setDtFinePeriodoFormativo(rappUSDto.getDataFinePeriodoFormativo());
		}
		// end - 72160 - COMONL-1360
	
		// NUMERO MATRICOLA LAVORATORE
		if (rappUSDto.getNumMatricolaLavoratore() != null) {
		    comunicazione.getRapporto().setNumMatricolaLav(String.valueOf(rappUSDto.getNumMatricolaLavoratore()));
		}
	
		if (rappUSDto.getIndennitaDisponibilita() != null) {
		    comunicazione.getRapporto().setNumIndennitaSomm(BigDecimal.valueOf(rappUSDto.getIndennitaDisponibilita()));
		}
	
		if (rappUSDto.getLavInMobilita() != null
			&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(rappUSDto.getLavInMobilita())) {
		    comunicazione.getRapporto().setFlgLavInMobilita(ComonlConstants.FLAG_S);
		} else {
		    comunicazione.getRapporto().setFlgLavInMobilita(ComonlConstants.FLAG_N);
		}
	}

    //
    // // DATI MISSIONE
    private void transcodeMissioneDTO() {

	if (dto.getMissione() != null && dto.getMissione().getDatiInizioMissione() != null) {
	    comunicazione.setMissione(new Rapporto());

	    // PAT INAIL
	    if (ComonlUtility.isNotVoid(dto.getMissione().getDatiInizioMissione().getDsPatInail())) {
		comunicazione.getMissione().setPatInail(dto.getMissione().getDatiInizioMissione().getDsPatInail());
	    }

	    // TIPO ORARIO
	    if (ComonlUtility.isNotVoid(dto.getMissione().getDatiInizioMissione().getTipoOrario())) {
		TipoOrario tipoOrario = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class,
			dto.getMissione().getDatiInizioMissione().getTipoOrario(), dataRiferimento);
		if (tipoOrario == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_TIPO_ORARIO,
			    dto.getMissione().getDatiInizioMissione().getTipoOrario(), "MISSIONE TipoOrario");
		}
		comunicazione.getMissione().setTipoOrario(tipoOrario);
	    }

	    // ORE SETTIMANALI
	    if (dto.getMissione().getDatiInizioMissione().getNumOreSettimanaliMedie() != null) {
		comunicazione.getMissione().setNumOreSettMed(
			BigDecimal.valueOf(dto.getMissione().getDatiInizioMissione().getNumOreSettimanaliMedie()));
	    }
	    // le agevolazioni non ci sono più
	    // if (comBo.getMissione().getListaAgevolazioni() != null) {
	    // ArrayList listaAgevolazioni =
	    // UtilAgevolazioniMultiple.codMinFromArrayListaAgevolazioni(comBo.getMissione().getListaAgevolazioni());
	    // missione.getDatiInizioMissione().setAgevolazioni(listaAgevolazioni);
	    // }
	    // missione.getDatiInizioMissione().setAgevolazioni(null);

	    // QUALIFICA PROFESSIONALE
	    if (dto.getMissione().getDatiInizioMissione().getQualificaProfessionale() != null
		    && !"".equalsIgnoreCase(dto.getMissione().getDatiInizioMissione().getQualificaProfessionale())) {

		Istat2001livello5 istat2001livello5 = (Istat2001livello5) decodificaDad.getTfromMin(
			Istat2001livello5.class, dto.getMissione().getDatiInizioMissione().getQualificaProfessionale(), dataRiferimento);
		if (istat2001livello5 == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_ISTAT2001LIVELLO5,
			    dto.getMissione().getDatiInizioMissione().getQualificaProfessionale(),
			    "MISSIONE QualificaProfessionale");
		}

		comunicazione.getMissione().setIstatLivello5(istat2001livello5);
	    }

	    // CCNL APPLICATO
	    if (dto.getMissione().getDatiInizioMissione().getCcnlApplicato() != null
		    && !"".equalsIgnoreCase(dto.getMissione().getDatiInizioMissione().getCcnlApplicato())) {

		Ccnl ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class,
			dto.getMissione().getDatiInizioMissione().getCcnlApplicato(), dataRiferimento);
		
		if(ccnl == null) { // nel caso non trovasse niente relativamente alla data riferimento
			ccnl = (Ccnl) decodificaDad.getTfromMin(Ccnl.class,
					dto.getMissione().getDatiInizioMissione().getCcnlApplicato(),
					null);
		}
		if (ccnl == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_CCNL,
			    dto.getMissione().getDatiInizioMissione().getCcnlApplicato(), "MISSIONE CcnlApplicato");
		}

		comunicazione.getMissione().setCcnl(ccnl);
	    }

	    // FLG SOMMINISTRAZIONE
	    if (dto.getMissione().getDatiInizioMissione().getFlgSomministrazioneTD() != null
		    && ComonlConstants.FLAG_SI_DESC
			    .equalsIgnoreCase(dto.getMissione().getDatiInizioMissione().getFlgSomministrazioneTD())) {
		comunicazione.getMissione().setFlgSommTempoDet(ComonlConstants.FLAG_S);
	    } else {
		comunicazione.getMissione().setFlgSommTempoDet(ComonlConstants.FLAG_N);
	    }

	    // LIVELLO INQUADRAMENTO
	    if (dto.getMissione().getDatiInizioMissione().getLivelloInquadramento() != null
		    && !"".equalsIgnoreCase(dto.getMissione().getDatiInizioMissione().getLivelloInquadramento())) {

		Long idLivello = decodificaDad.determinaIdLivelloRetribuzioneByCodCcnlCodLivello(
			String.valueOf(dto.getMissione().getDatiInizioMissione().getCcnlApplicato()),
			dto.getMissione().getDatiInizioMissione().getLivelloInquadramento(), null);

		if (idLivello != null) {
		    LivelloRetribuzione livelloRetribuzione = decodificaDad.getLivelloRetribuzioneById(idLivello);
		    if (livelloRetribuzione == null) {
			addErrorComDImportErrore(errors, ComonlConstants.COM_T_LIVELLO_RETRIBUZIONE,
				dto.getMissione().getDatiInizioMissione().getLivelloInquadramento(),
				"TRANSCODE LivelloInquadramento");
		    }
		    comunicazione.getMissione().setLivelloRetribuzione(livelloRetribuzione);
		} else {
		    comunicazione.getMissione().setLivelloInquadramento(
			    dto.getMissione().getDatiInizioMissione().getLivelloInquadramento());
		}
	    }

	    // retribuzione
	    if (dto.getMissione().getDatiInizioMissione().getRetribuzione() != null) {
		comunicazione.getMissione().setRetribuzioneCompenso(
			BigDecimal.valueOf(dto.getMissione().getDatiInizioMissione().getRetribuzione()));
	    }

	    // flag lavoro in agricoltura
	    if (dto.getMissione().getDatiInizioMissione().getFlgLavoroAgricoltura() != null
		    && ComonlConstants.FLAG_SI_DESC
			    .equalsIgnoreCase(dto.getMissione().getDatiInizioMissione().getFlgLavoroAgricoltura())) {
		comunicazione.getMissione().setFlgLavoroAgricoltura(ComonlConstants.FLAG_S);
	    } else {
		comunicazione.getMissione().setFlgLavoroAgricoltura(ComonlConstants.FLAG_N);
	    }

	    // NUMERO GIORNATE LAVORATIVE
	    if (dto.getMissione().getDatiInizioMissione().getNumGiornateLavorativePreviste() != null) {
		comunicazione.getMissione().setGiornateLavPreviste(
			dto.getMissione().getDatiInizioMissione().getNumGiornateLavorativePreviste());
	    }

	    // DATA FINE RAPPORTO
	    if (dto.getMissione().getDatiInizioMissione().getDataFineMissione() != null) {
		comunicazione.getMissione()
			.setDtFineMissione(dto.getMissione().getDatiInizioMissione().getDataFineMissione());
	    }

	    // DATA INIZIO RAPPORTO
	    if (dto.getMissione().getDatiInizioMissione().getDataInizioMissione() != null) {
		comunicazione.getMissione()
			.setDtInizioMissione(dto.getMissione().getDatiInizioMissione().getDataInizioMissione());
	    }

	    // DESCRIZIONE ATTIVITA'
	    if (ComonlUtility.isNotVoid(dto.getMissione().getDatiInizioMissione().getDescrizioneAttivita())) {
		comunicazione.getMissione()
			.setDsAttivita(dto.getMissione().getDatiInizioMissione().getDescrizioneAttivita());
	    } else {
		comunicazione.getMissione().setDsAttivita(null);
	    }

	    // DESCRIZIONE ATTIVITA' AGRICOLA
	    if (ComonlUtility
		    .isNotVoid(dto.getMissione().getDatiInizioMissione().getDescrizioneAttivitaAgricoltura())) {
		comunicazione.getMissione().setDsAttivitaAgricoltura(ComonlStringUtils.eliminaCaratteriSpeciali(
			dto.getMissione().getDatiInizioMissione().getDescrizioneAttivitaAgricoltura().toUpperCase()));
	    } else {
		comunicazione.getMissione().setDsAttivitaAgricoltura(null);
	    }

	    // SILICOSI
	    if (dto.getMissione().getDatiInizioMissione().getFlgRischioSilicosiAsbestosi() != null
		    && ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(
			    dto.getMissione().getDatiInizioMissione().getFlgRischioSilicosiAsbestosi())) {
		comunicazione.getMissione().setFlgRischioSilicAsbe(ComonlConstants.FLAG_S);
	    } else {
		comunicazione.getMissione().setFlgRischioSilicAsbe(ComonlConstants.FLAG_N);
	    }

	    if (dto.getMissione().getDatiInizioMissione().getFlgAssunzioneObbligatoria() != null
		    && ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(
			    dto.getMissione().getDatiInizioMissione().getFlgAssunzioneObbligatoria())) {
		comunicazione.getMissione().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_S);
	    } else {
		comunicazione.getMissione().setFlgAssunzioneObbligatoria(ComonlConstants.FLAG_N);
	    }

	    if (dto.getMissione().getDatiInizioMissione().getCategoriaLavoratoreAssunzioneObbligatoria() != null
		    && !"".equalsIgnoreCase(
			    dto.getMissione().getDatiInizioMissione().getCategoriaLavoratoreAssunzioneObbligatoria())) {

		CategLavAssObbl categLavAssObbl = (CategLavAssObbl) decodificaDad.getTfromMin(CategLavAssObbl.class,
			dto.getMissione().getDatiInizioMissione().getCategoriaLavoratoreAssunzioneObbligatoria(), dataRiferimento);
		if (categLavAssObbl == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_CATEG_LAV_ASS_OBBL,
			    dto.getMissione().getDatiInizioMissione().getCategoriaLavoratoreAssunzioneObbligatoria(),
			    "MISSIONE CategoriaLavoratoreAssunzioneObbligatoria");
		}
		comunicazione.getMissione().setCategLavAssObbl(categLavAssObbl);
	    }

	    // VOCE TARIFFA 1
	    if (dto.getMissione().getDatiInizioMissione().getVoceTariffa1() != null) {

			// ************************** PADDING A 4 caratteri
			String voce = dto.getMissione().getDatiInizioMissione().getVoceTariffa1();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);
	
			comunicazione.getMissione().setDsVoceTariffa1(vocePad);
	    } else {
	    	comunicazione.getMissione().setDsVoceTariffa1(("0000"));
	    }

	    // VOCE TARIFFA 2
	    if (dto.getMissione().getDatiInizioMissione().getVoceTariffa2() != null
		    && !dto.getMissione().getDatiInizioMissione().getVoceTariffa2().equals("")) {

			// ************************** PADDING A 4 caratteri
			String voce = dto.getMissione().getDatiInizioMissione().getVoceTariffa2();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);
	
			comunicazione.getMissione().setDsVoceTariffa2(vocePad);

	    } else {
	    	comunicazione.getMissione().setDsVoceTariffa2(null);

	    }

	    // VOCE TARIFFA 3
	    if (dto.getMissione().getDatiInizioMissione().getVoceTariffa3() != null
		    && !dto.getMissione().getDatiInizioMissione().getVoceTariffa3().equals("")) {

			// ************************** PADDING A 4 caratteri
			String voce = dto.getMissione().getDatiInizioMissione().getVoceTariffa3();
			String vocePad = ComonlStringUtils.eliminaCaratteriSpeciali(voce);
			vocePad = ComonlStringUtils.paddingDiStringa(voce, 4);
	
			comunicazione.getMissione().setDsVoceTariffa3(vocePad);
	    } else {
	    	comunicazione.getMissione().setDsVoceTariffa3(null);
	    }
	}

    }

    //
    private void transcodeDittaUtilizzatriceDTO(DittaUtilizzatriceDTO dittaUtilizzatriceDTO) {

	comunicazione.getMissione().setAziUtilizzatrice(new Datore());

	comunicazione.getMissione().getAziUtilizzatrice().setCodiceFiscale(dittaUtilizzatriceDTO.getCodFiscale());

	if (dittaUtilizzatriceDTO.getComuneSedeLavoro() != null
		&& !"".equalsIgnoreCase(dittaUtilizzatriceDTO.getComuneSedeLavoro())) {

	    comunicazione.getMissione().getAziUtilizzatrice().setSedeOperativa(
		    ComonlUtility.isNotVoid(comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa())
			    ? comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
			    : new SedeLavoro());

	    if (!dittaUtilizzatriceDTO.getComuneSedeLavoro().startsWith("Z")) {
		Comune comuneSedeOperativa = (Comune) decodificaDad.getTfromMin(Comune.class,
			dittaUtilizzatriceDTO.getComuneSedeLavoro(), dataRiferimento);
		comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa().setComune(comuneSedeOperativa);
		if (comuneSedeOperativa == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
			    dittaUtilizzatriceDTO.getComuneSedeLavoro(),
			    "TRANSCODE DittaUtilizzatrice - ComuneSedeLavoro");
		}
	    } else {
		StatiEsteri statiEsteriSedeOperativa = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
			dittaUtilizzatriceDTO.getComuneSedeLavoro(), dataRiferimento);
		if (statiEsteriSedeOperativa == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
			    dittaUtilizzatriceDTO.getComuneSedeLavoro(),
			    "TRANSCODE DittaUtilizzatrice - ComuneSedeLavoro");
		}
		comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
			.setStatiEsteri(statiEsteriSedeOperativa);

	    }

	}

	if (dittaUtilizzatriceDTO.getComuneSedeLegale() != null
		&& !"".equalsIgnoreCase(dittaUtilizzatriceDTO.getComuneSedeLegale())) {

	    comunicazione.getMissione().getAziUtilizzatrice().setSedeLegale(
		    ComonlUtility.isNotVoid(comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale())
			    ? comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale()
			    : new SedeLavoro());

	    if (!dittaUtilizzatriceDTO.getComuneSedeLegale().startsWith("Z")) {
		Comune comuneSedeLegale = (Comune) decodificaDad.getTfromMin(Comune.class,
			dittaUtilizzatriceDTO.getComuneSedeLegale(), dataRiferimento);
		comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale().setComune(comuneSedeLegale);
		if (comuneSedeLegale == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_COMUNE,
			    dittaUtilizzatriceDTO.getComuneSedeLegale(),
			    "TRANSCODE DittaUtilizzatrice - ComuneSedeLegale");
		}
	    } else {
		StatiEsteri statiEsteriSedeLegale = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class,
			dittaUtilizzatriceDTO.getComuneSedeLegale(), dataRiferimento);
		if (statiEsteriSedeLegale == null) {
		    addErrorComDImportErrore(errors, ComonlConstants.COM_T_STATI_ESTERI,
			    dittaUtilizzatriceDTO.getComuneSedeLegale(),
			    "TRANSCODE DittaUtilizzatrice - ComuneSedeLegale");
		}
		comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale().setStatiEsteri(statiEsteriSedeLegale);

	    }
	}
	
	comunicazione.getMissione().getAziUtilizzatrice()
		.setDsDenominazioneDatore(dittaUtilizzatriceDTO.getDenominazione());
	comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
		.setEmail(dittaUtilizzatriceDTO.getEmailSedeLavoro() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getEmailSedeLavoro())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale()
		.setEmail(dittaUtilizzatriceDTO.getEmailSedeLegale() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getEmailSedeLegale())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
		.setFax(dittaUtilizzatriceDTO.getFaxSedeLavoro() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getFaxSedeLavoro())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale()
		.setFax(dittaUtilizzatriceDTO.getFaxSedeLegale() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getFaxSedeLegale())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
		.setIndirizzo(dittaUtilizzatriceDTO.getIndirizzoSedeLavoro() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getIndirizzoSedeLavoro())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale()
		.setIndirizzo(dittaUtilizzatriceDTO.getIndirizzoSedeLegale() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getIndirizzoSedeLegale())
			: null);
	
	comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa().setCodCap(dittaUtilizzatriceDTO.getCapSedeLavoro());
	comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale().setCodCap(dittaUtilizzatriceDTO.getCapSedeLegale());
	
	comunicazione.getMissione().getAziUtilizzatrice().setDtInizioContrattoSom(dittaUtilizzatriceDTO.getDataInizioSomministrazione());
	comunicazione.getMissione().getAziUtilizzatrice().setDtFineContrattoSom(dittaUtilizzatriceDTO.getDataFineSomministrazione());


	// ************************** PADDING A 9 caratteri del codice della
	// comunicazione precedente se non nullo
	String padding = "000000000";

	String numeroContrattoSomministrazione = dittaUtilizzatriceDTO.getNumContrattoSomministrazione().toString();

	if (numeroContrattoSomministrazione != null) {
	    String codice = null;
	    int lunghezzaCodice = numeroContrattoSomministrazione.length();
	    padding = padding.substring(0, padding.length() - lunghezzaCodice);
	    codice = padding + numeroContrattoSomministrazione;
	    comunicazione.getMissione().getAziUtilizzatrice().setNumContrSomm(new BigDecimal(codice));
	} else {
	    comunicazione.getMissione().getAziUtilizzatrice().setNumContrSomm(new BigDecimal(padding));
	}

	// **************************************************************************************************************

	if (dittaUtilizzatriceDTO.getSettore() != null && !"".equalsIgnoreCase(dittaUtilizzatriceDTO.getSettore())) {

	    Atecofin atecofin = (Atecofin) decodificaDad.getTfromMin(Atecofin.class, dittaUtilizzatriceDTO.getSettore(),
		    dataRiferimento);

	    if (atecofin == null) {
	    addErrorComDImportErrore(errors, ComonlConstants.COM_T_ATECOFIN, dittaUtilizzatriceDTO.getSettore(),
		    "TRANSCODE DittaUtilizzatrice - Settore");
	    }

	    comunicazione.getMissione().getAziUtilizzatrice().setAtecofin(atecofin);
	}

	comunicazione.getMissione().getAziUtilizzatrice().getSedeOperativa()
		.setTelefono(dittaUtilizzatriceDTO.getTelefonoSedeLavoro() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getTelefonoSedeLavoro())
			: null);
	comunicazione.getMissione().getAziUtilizzatrice().getSedeLegale()
		.setTelefono(dittaUtilizzatriceDTO.getTelefonoSedeLegale() != null
			? ComonlStringUtils.eliminaCaratteriSpeciali(dittaUtilizzatriceDTO.getTelefonoSedeLegale())
			: null);

	if (dittaUtilizzatriceDTO.getPubblicaAmministrazione() != null
		&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dittaUtilizzatriceDTO.getPubblicaAmministrazione())) {
	    comunicazione.getMissione().getAziUtilizzatrice().setFlgPubAmm(ComonlConstants.FLAG_S);
	} else {
	    comunicazione.getMissione().getAziUtilizzatrice().setFlgPubAmm(ComonlConstants.FLAG_N);
	}

	if (dittaUtilizzatriceDTO.getFlgAziUtilEsteraInItalia() != null
		&& ComonlConstants.FLAG_SI_DESC.equalsIgnoreCase(dittaUtilizzatriceDTO.getFlgAziUtilEsteraInItalia())) {
	    comunicazione.getMissione().getAziUtilizzatrice().setFlgUtilEstera(ComonlConstants.FLAG_S);
	} else {
	    comunicazione.getMissione().getAziUtilizzatrice().setFlgUtilEstera(ComonlConstants.FLAG_N);
	}

	comunicazione.getMissione().getAziUtilizzatrice()
		.setDtInizioContrattoSom(dittaUtilizzatriceDTO.getDataInizioSomministrazione());
	comunicazione.getMissione().getAziUtilizzatrice()
		.setDtFineContrattoSom(dittaUtilizzatriceDTO.getDataFineSomministrazione());

    }

    public void addErrorComDImportErrore(List<ComDImportErrore> errors, String tabellaDestinazione,
	    String datoInput, String descrErrore) {

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

}
