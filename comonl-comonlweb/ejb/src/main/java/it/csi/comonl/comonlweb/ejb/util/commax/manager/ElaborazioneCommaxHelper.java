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
package it.csi.comonl.comonlweb.ejb.util.commax.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.ProtocollazioneHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.ControlloreSingolaComunicazione;
import it.csi.comonl.comonlweb.ejb.util.commax.ParametriDiControllo;
import it.csi.comonl.comonlweb.ejb.util.commax.dto.RaggruppamentoDati;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ProtocolloVO;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.comonl.comonlweb.protocollo.hepler.AdapterIup;
import it.csi.comonl.comonlweb.spicom.helper.SpicomIntegrator;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.interf.RisultatoConversioneTracciato;
import it.csi.spicom.util.TUConstants;

public class ElaborazioneCommaxHelper {

	protected String srcPath = this.getClass().getName();
	protected final LogUtil log = new LogUtil(ElaborazioneCommaxHelper.class);

	public ComunicazioneTracciatoUnicoDTO[] convertiXmlInOggettone(String[] tracciati) {

		// Traduco l'xml reperito dal DB invocando l'opportuno metodo di
		// spicom che mi ritornera' un oggetto java.

		List<RisultatoConversioneTracciato> rs = traduzioneTracciato(tracciati);
		ComunicazioneTracciatoUnicoDTO tracciatiRisultanti[] = new ComunicazioneTracciatoUnicoDTO[tracciati.length];

		for (int k = 0; k < tracciati.length; k++) {
			if (rs.get(k).isConversioneEseguitaConSuccesso()) {
				tracciatiRisultanti[k] = rs.get(k).getTraccciatoConvertito();

			} else {
				tracciatiRisultanti[k] = null;
			}
		}
		return tracciatiRisultanti;
	}

	public ComunicazioneTracciatoUnicoDTO convertiXmlInOggettone(String xml) {
		String tracciati[] = new String[1];
		tracciati[0] = xml;
		ComunicazioneTracciatoUnicoDTO[] risultato = convertiXmlInOggettone(tracciati);
		return risultato[0];
	}

	private List<RisultatoConversioneTracciato> traduzioneTracciato(String[] xmlReperito) {
		List<RisultatoConversioneTracciato> risultato;
		try {
			risultato = SpicomIntegrator.getInstance().convertiInFormatoUnico(xmlReperito);
			return risultato;
		} catch (Exception e) {
			log.error("traduzioneTracciato", e.getMessage());
		}
		return null;

	}

	public void popolaCampiOggettone(Ruolo ruolo, ComunicazioneTracciatoUnicoDTO comunicazione, String cfInvio,
			String operatore, Date dataRicezione, String eMail, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		// Popola nella sezione DatiInvio i dati del soggetto abilitato prelevandoli

		if (ruolo.isConsulenteRespo()) {

			SoggettoAbilitato soggettoAbilitato = anagraficaDelegatoDad.getSoggettoAbilitato(ruolo.getCodiceFiscaleAzienda());

			comunicazione.getDatiInvio().setCodFiscaleSoggettoComunicante(soggettoAbilitato.getCfSoggetto());
			comunicazione.getDatiInvio()
					.setSoggettoComunicante(soggettoAbilitato.getTipoSoggettoAbilitato().getCodSoggettoAbilitatoMin());

			if (soggettoAbilitato.getEmail() != null && !soggettoAbilitato.getEmail().equals("")) {
				comunicazione.getDatiInvio().setEmailDelegato(soggettoAbilitato.getEmail());
			} else {
				comunicazione.getDatiInvio().setEmailDelegato(eMail);
			}
		} else {
			comunicazione.getDatiInvio().setCodFiscaleSoggettoComunicante(null);
			comunicazione.getDatiInvio().setSoggettoComunicante(null);
			comunicazione.getDatiInvio().setEmailDelegato(eMail);
		}
		// Anmullo il codice regionale
		comunicazione.getDatiInvio().setCodiceComunicazione(null);
		comunicazione.getDatiInvio()
				.setDataInvio(DateUtil.convertiStringaInData(DateUtil.convertiDataInStringa(dataRicezione)));

	}

	public List<ApiError> eseguoControlliSuComunicazione(ComunicazioneTracciatoUnicoDTO comunicazione, Long idUpload,
		String codiceFiscaleAzienda, String codiceFiscaleLavoratore, String nomeFileXML, CommonDad commonDad,
		DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad, Ruolo ruolo) {
		List<ApiError> elencoErrori = null;
		try {
			// Lancio il motore dei controlli
			
			ControlloreSingolaComunicazione contr = new ControlloreSingolaComunicazione();

			ParametriDiControllo pc = new ParametriDiControllo();
			pc.setidReport(idUpload);
			pc.setnomeFileXml(nomeFileXML);
			pc.setcomunicazioneCorrenteDTO(comunicazione);
			pc.setCodiceFiscaleAzienda(codiceFiscaleAzienda);
			pc.setCodiceFiscaleLavoratore(codiceFiscaleLavoratore);
			elencoErrori = contr.eseguiControllo(pc, commonDad, decodificaDad, comunicazioneDad, ruolo);

			if (elencoErrori != null && !elencoErrori.isEmpty()) {
				log.debug("eseguoControlliSuComunicazione", "Ho rilevato almeno un errore bloccante. ALT!!!!!");
			} else {
				log.debug("eseguoControlliSuComunicazione", "NON ho trovato motivi per fermarmi. vado avanti!!!!! ");
			}

		} catch (Exception e) {
			log.error("eseguoControlliSuComunicazione", e.getMessage());
		}

		return elencoErrori;
	}

	public ComunicazioneTracciatoUnicoDTO[] estraiOggettoneDaElencoDepositiCommax(List<DepositoCommax> elementi)
			throws Exception {
		String tracciati[] = new String[elementi.size()];
		for (int x = 0; x < elementi.size(); x++) {
			tracciati[x] = new String(elementi.get(x).getBFileCommax());
		}
		ComunicazioneTracciatoUnicoDTO risultato[] = convertiXmlInOggettone(tracciati);

		return risultato;
	}

	public boolean isComunicazioneGiaProtocollata(DepCommaxComunic invioCorrente) throws Exception {
		return StringUtils.isNotEmpty(invioCorrente.getFlgProtGestCorrettamente())
				&& invioCorrente.getFlgProtGestCorrettamente().equals("S");
	}

	public Properties protocolla(Comunicazione comunicazioneDB, DecodificaDad decodificaDad) {

		return ProtocollazioneHelper.protocolla(comunicazioneDB, decodificaDad);
	}

	public void aggiornaCheComunicazioneProtocollata(DepCommaxComunic invioCorrente, ComunicazioneDad comunicazioneDad,
			Comunicazione co) throws Exception {

		// Aggiorno la comunicazione
		comunicazioneDad.updateComunicazione(co);

		// Aggiorno l'esito
		invioCorrente.setFlgProtGestCorrettamente("S");
		comunicazioneDad.updateComRDepCommaxComunic(invioCorrente);
	}

	public boolean isUploadGiaInviatoASpicom(DepCommaxComunic invioCorrente) throws Exception {
		boolean trovatoNumeroRegionale = StringUtils.isNotEmpty(invioCorrente.getCodiceComunicazioneReg());
		return trovatoNumeroRegionale;
	}

	public String agganciaProtocolloEdInviaComunicazione(Ruolo ruolo, RaggruppamentoDati datoCorrente,
			Properties protocollo, UplDocumenti uplDoc, CommonDad commonDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad, String dsTarga) throws Exception {

		ComunicazioneTracciatoUnicoDTO comunicazioneCorrente = datoCorrente.getComunicazioneTracciatoUnicoDTO();
		String eMail = datoCorrente.getDepositoCommax().getEmailUtente();
		comunicazioneCorrente.getDatiInvio().setProtocolloSistema(null);

		// Nel caso di province non presenti sulla tavola personalizzazione occorre
		// settare alcuni campi a null
		if (protocollo != null && protocollo.getProperty("numero") != null
				&& !protocollo.getProperty("numero").equalsIgnoreCase("0")) {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setNumProtocolloProvinciale(String.valueOf(protocollo.getProperty("numero")));
			comunicazioneCorrente.getDatiInvio().setProtocolloSistema(String.valueOf(protocollo.getProperty("numero")));
		} else {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setNumProtocolloProvinciale(null);
		}
		if (protocollo != null && protocollo.getProperty("anno") != null
				&& !protocollo.getProperty("anno").equalsIgnoreCase("0")) {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setAnnoProtocolloProvinciale(Long.valueOf(protocollo.getProperty("anno")));
		} else {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setAnnoProtocolloProvinciale(null);
		}
		if (protocollo != null && protocollo.getProperty("dataProtocollo") != null
				&& !protocollo.getProperty("dataProtocollo").equalsIgnoreCase("0")) {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setDataProtocolloProvinciale(Format.creaJavaUtilDate(protocollo.getProperty("dataProtocollo")));
		} else {
			comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione()
					.setDataProtocolloProvinciale(null);
		}
		// La provincia dovrebbe sempre essere valorizzata.
		comunicazioneCorrente.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setSiglaProvincia(dsTarga);
		comunicazioneCorrente.getDatiAggiuntivi().setIdAppMittente(TUConstants.COMMAX);

		String numVersioneXML = commonDad
				.getParametroCommaxById(ParametriConstants.NUMERO_VERSIONE_TRACCIATO_PER_SPICOM).getValoreParametro();

		comunicazioneCorrente.getDatiAggiuntivi().setNumVersioneXML(numVersioneXML);

		// comunicazioneCorrente.getDatiInvio().setDataInvio(DateUtil.convertiStringaInData(DateUtil.convertiDataInStringa(uplDoc.getDataRicezione())));

		popolaCampiOggettone(ruolo, comunicazioneCorrente, uplDoc.getMittenteCf(), uplDoc.getDsRuoloOperatore(),
				uplDoc.getDataRicezione(), eMail, anagraficaDelegatoDad);

		if (UtilConstant.CODICE_REGIONALE_NULLO
				.equals(comunicazioneCorrente.getDatiInvio().getCodiceComunicazionePrecedente())) {
			comunicazioneCorrente.getDatiInvio().setCodiceComunicazionePrecedente(null);
		}

		comunicazioneCorrente.getDatiAggiuntivi()
				.setChiaveEsterna(datoCorrente.getComRDepCommaxComunic().getComunicazione().getId());

		String codiceComunicazioneReg = SpicomIntegrator.getInstance()
				.inviaComunicazioneProvenienzaCommax(comunicazioneCorrente);

		// Setto il codice comunicazione reperito da SPICOM nell'oggettone , perche' mi
		// serve ad aggiornare gli elaborati OK.
		comunicazioneCorrente.getDatiInvio().setCodiceComunicazione(codiceComunicazioneReg);

		return codiceComunicazioneReg;
	}

	public void aggiornaInviatoASpicom(DepCommaxComunic invioCorrente, Comunicazione co,
			ComunicazioneDad comunicazioneDad) throws Exception {
		comunicazioneDad.updateComunicazione(co);
		invioCorrente.setCodiceComunicazioneReg(co.getCodiceComunicazioneReg());
		comunicazioneDad.updateComRDepCommaxComunic(invioCorrente);
	}

	public void inserisciEsitoInElaboratiOK(UplDocumenti uplDoc, RaggruppamentoDati datoCorrente,
			Long progressivoComunicazione, ComunicazioneDad comunicazioneDad) throws Exception {
		try {
			if (datoCorrente != null) {
				String tipoTracciato = datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
						.getTipoTracciatoXML();
				String codiceComunicazioneRegionale = datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiInvio()
						.getCodiceComunicazione();
				DepositoCommax elementoCorrente = datoCorrente.getDepositoCommax();
				Long annoProtocollo = null;
				BigDecimal numeroProtocolloProvinciale = null;
				String codiceFiscaleImpresa = null;
				String codiceFiscaleLavoratore = null;
				String provinciaProtocollo = null;

				ProtocolloVO protocollo = new ProtocolloVO();
				if (datoCorrente.getComunicazioneTracciatoUnicoDTO() != null
						&& datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi() != null
						&& datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
								.getDatiAggiuntiviComunicazione() != null) {

					if (datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
							.getDatiAggiuntiviComunicazione().getAnnoProtocolloProvinciale() != null) {
						annoProtocollo = datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
								.getDatiAggiuntiviComunicazione().getAnnoProtocolloProvinciale();
					} else {
						// Comunicazione senza protocollo provinciale : fuori Piemonte
						annoProtocollo = 0L;
					}

					if (datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
							.getDatiAggiuntiviComunicazione().getNumProtocolloProvinciale() != null) {
						numeroProtocolloProvinciale = new BigDecimal(datoCorrente.getComunicazioneTracciatoUnicoDTO()
								.getDatiAggiuntivi().getDatiAggiuntiviComunicazione().getNumProtocolloProvinciale());
					} else {
						// Comunicazione senza protocollo provinciale : fuori Piemonte
						numeroProtocolloProvinciale = new BigDecimal(0);
					}
					if (datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
							.getDatiAggiuntiviComunicazione().getSiglaProvincia() != null) {
						provinciaProtocollo = datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi()
								.getDatiAggiuntiviComunicazione().getSiglaProvincia();
					} else {
						// La provincia del protocollo non e' valorizzata
						provinciaProtocollo = "XX";
					}

					// Aggiunto il codice fiscale dell'impresa
					if (datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatoreDiLavoro() != null && datoCorrente
							.getComunicazioneTracciatoUnicoDTO().getDatoreDiLavoro().getCodFiscale() != null) {
						codiceFiscaleImpresa = datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatoreDiLavoro()
								.getCodFiscale();
					}
					// Aggiunto il codice fiscale del lavoratore
					if (datoCorrente.getComunicazioneTracciatoUnicoDTO().getLavoratore() != null && datoCorrente
							.getComunicazioneTracciatoUnicoDTO().getLavoratore().getCodFiscale() != null) {
						codiceFiscaleLavoratore = datoCorrente.getComunicazioneTracciatoUnicoDTO().getLavoratore()
								.getCodFiscale();
					}

				} else {
					// Molto male, setto sia l'anno che il numero a 0 e la provincia a XX
					// Probabilmente la comunicazione e' relativa ad aziende fuori dal piemonte.
					annoProtocollo = 0L;
					numeroProtocolloProvinciale = new BigDecimal(0);
					provinciaProtocollo = "XX";
				}
				protocollo.setAnnoProtocollo(annoProtocollo.intValue());
				protocollo.setNumeroProtocollo(numeroProtocolloProvinciale);
				protocollo.setPv(provinciaProtocollo);
				boolean esisteEsito = esisteEsitoInComDElaboratiOK(codiceComunicazioneRegionale, tipoTracciato,
						protocollo, elementoCorrente, codiceFiscaleImpresa, codiceFiscaleLavoratore,
						provinciaProtocollo, comunicazioneDad);

				if (esisteEsito) {
					// Esiste gia' un esito per questa comunicazione
					log.debug("inserisciEsitoInElaboratiOK",
							"Esiste gia' un esito per questa comunicazione, non inserisco niente!");
				} else {
					log.debug("inserisciEsitoInElaboratiOK",
							"NON esiste un esito per questa comunicazione, molto bene, lo inserisco!");
					aggiornaEsitoElaborazione(tipoTracciato, codiceComunicazioneRegionale, protocollo, elementoCorrente,
							codiceFiscaleImpresa, codiceFiscaleLavoratore, progressivoComunicazione, comunicazioneDad);
				}
			}
		} catch (Exception e) {

			log.debug("inserisciEsitoInElaboratiOK",
					"********************* Eccezione :: ElaborazioneCommaxHelper.inserisciEsitoInElaboratiOK ******************");
			log.debug("inserisciEsitoInElaboratiOK",
					"***** *********************************************************************************************");
			log.debug("inserisciEsitoInElaboratiOK",
					"***** ATTENZIONE : NON SONO RIUSCITO A MEMORIZZARE IL RECORD IN COM_D_ELABORATI_OK");
			log.debug("inserisciEsitoInElaboratiOK",
					"***** *********************************************************************************************");
			log.debug("inserisciEsitoInElaboratiOK",
					"***** *********************************************************************************************");
			log.debug("inserisciEsitoInElaboratiOK", e.getMessage());
		}
	}

	private boolean esisteEsitoInComDElaboratiOK(String codiceComunicazioneRegionale, String tipoTracciato,
			ProtocolloVO protocollo, DepositoCommax elementoCorrente, String codiceFiscaleImpresa,
			String codiceFiscaleLavoratore, String provinciaProtocollo, ComunicazioneDad comunicazioneDad) {
		boolean result = false;
		ElaboratiOk esito = new ElaboratiOk();
		esito.setAnnoProtCom(new BigDecimal(protocollo.getAnnoProtocollo()));
		esito.setUplDocumenti(new UplDocumenti());
		esito.getUplDocumenti().setId(elementoCorrente.getIdComDUplDocumenti().longValue());
		esito.setNroProtCom(protocollo.getNumeroProtocollo().toString());
		esito.setNomeFileXml(elementoCorrente.getNomeFileXmlSingolo());
		esito.setCfImpresa(codiceFiscaleImpresa);
		esito.setCfLavoratore(codiceFiscaleLavoratore);
		esito.setPv(provinciaProtocollo);

		try {
			result = comunicazioneDad.esisteEsitoInComDElaboratiOk(esito);
			log.debug("esisteEsitoInComDElaboratiOK", "esiste esito = " + result);
		} catch (Exception e) {
			log.debug("esisteEsitoInComDElaboratiOK", "Non sono riuscito a verificare se esisteva gia' un esito");
			log.debug("esisteEsitoInComDElaboratiOK", e.getMessage());
		}
		return result;
	}

	public void aggiornaEsitoElaborazione(String tipoTracciato, String codiceComunicazioneRegionale,
			ProtocolloVO protocollo, DepositoCommax elementoCorrente, String codiceFiscaleImpresa,
			String codiceFiscaleLavoratore, Long progressivoComunicazione, ComunicazioneDad comunicazioneDad)
			throws Exception {
		String nomeFileXML = elementoCorrente.getNomeFileXmlSingolo();

		ElaboratiOk dtoElaborato = new ElaboratiOk();
		dtoElaborato.setProgCom(progressivoComunicazione);
		dtoElaborato.setAnnoProtCom(new BigDecimal(protocollo.getAnnoProtocollo()));
		dtoElaborato.setNroProtCom(protocollo.getNumeroProtocolloString());
		dtoElaborato.setCodiceComunicazioneReg(codiceComunicazioneRegionale);
		dtoElaborato.setCfLavoratore(codiceFiscaleLavoratore);
		dtoElaborato.setCfImpresa(codiceFiscaleImpresa);
		dtoElaborato.setPv(protocollo.getPv());
		dtoElaborato.setUplDocumenti(new UplDocumenti());
		dtoElaborato.getUplDocumenti().setId(elementoCorrente.getIdComDUplDocumenti().longValue());
		dtoElaborato.setNomeFileXml(nomeFileXML);
		dtoElaborato.setTipoCom(tipoTracciato);
		comunicazioneDad.insertElaboratiOk(dtoElaborato);
	}
}
