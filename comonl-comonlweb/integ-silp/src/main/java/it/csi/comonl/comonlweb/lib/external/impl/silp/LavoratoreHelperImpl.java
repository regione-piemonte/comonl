/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SILP submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.external.impl.silp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoriSilpPK;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.silpsv.silpsval.cxfclient.AnagraficaLavoratoreEspansa;
import it.csi.silpsv.silpsval.cxfclient.EstrazioneAnagraficaLavoratore;
import it.csi.silpsv.silpsval.cxfclient.ParamRicercaLavoratoreFilter;
import it.csi.silpsv.silpsval.cxfclient.ResultRicercaLavoratore;
import it.csi.silpsv.silpsval.cxfclient.ServiziSilpException_Exception;

public class LavoratoreHelperImpl {
	protected final LogUtil log = new LogUtil(getClass());
	private Service s = null;

	private static final String CALLER_COMONL = "COMONL"; // configurato da Sacchi

	public LavoratoreSilpEspanso ricercaPerCodiceFiscale(String codiceFiscale) {
		return this.ricercaLavoratorePerCodiceFiscaleCognomeNome(codiceFiscale, null, null);
	}

	public LavoratoreSilpEspanso ricercaLavoratorePerCodiceFiscaleCognomeNome(String codiceFiscale, String cognome,
			String nome) {
		final String methodName = "ricercaPerCodiceFiscale";
		LavoratoreSilpEspanso lavoratoreSilpEspanso = null;

		try {
			EstrazioneAnagraficaLavoratore estrazioneAnagraficaLavoratore = getService();

			ParamRicercaLavoratoreFilter paramRicercaLavoratoreFilter = new ParamRicercaLavoratoreFilter();
			paramRicercaLavoratoreFilter.setCodiceFiscale(codiceFiscale);
			paramRicercaLavoratoreFilter.setMaxNumeroRecord(10);

			List<ResultRicercaLavoratore> resultRicercaLavoratores = estrazioneAnagraficaLavoratore
					.ricercaLavoratorePerDatiAnagrafici(CALLER_COMONL, paramRicercaLavoratoreFilter);
			for (Iterator<ResultRicercaLavoratore> iterator = resultRicercaLavoratores.iterator(); iterator
					.hasNext();) {
				ResultRicercaLavoratore resultRicercaLavoratore = iterator.next();

				// controllo errore
				if (!resultRicercaLavoratore.getCodErrore().equalsIgnoreCase("OK000001")) {
					log.error(methodName, "codErrore: " + resultRicercaLavoratore.getCodErrore());
					log.error(methodName, "descrizioneErrore: " + resultRicercaLavoratore.getDescrizioneErrore());
					return null;
				}
			}

			// Se il servizio restituisce un solo lavoratore il sistema richiama il servizio
			// SEAL: Servizio dettaglio lavoratore per idlavoratore.
			if (ComonlUtility.isNotVoid(resultRicercaLavoratores)) {
				ResultRicercaLavoratore resultRicercaLavoratore = resultRicercaLavoratores.get(0);

				/**
				 * controllo che il nome e il cognome inseriti eventualmente in maschera
				 * coincidano con quelli restituiti dal servizio di silp
				 */
				boolean controlliNome = false;
				boolean controlliCognome = false;
				if (null != cognome) {
					controlliCognome = resultRicercaLavoratore.getCognome().contains(cognome.trim().toUpperCase());
				} else {
					controlliCognome = true;
				}
				if (null != nome) {
					controlliNome = resultRicercaLavoratore.getNome().contains(nome.trim().toUpperCase());
				} else {
					controlliNome = true;
				}

				if (controlliNome && controlliCognome) {

					lavoratoreSilpEspanso = new LavoratoreSilpEspanso();

					AnagraficaLavoratoreEspansa anagraficaLavoratoreEspansoSilp = estrazioneAnagraficaLavoratore
							.visualizzaDettaglioLavoratoreEspanso(CALLER_COMONL,
									resultRicercaLavoratore.getIdLavoratore());

					lavoratoreSilpEspanso
							.setIdSilLavAnagrafica(new BigDecimal(anagraficaLavoratoreEspansoSilp.getIdLavoratore()));
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodMinLivelloIstruzioneCO())) {
						LivelloStudio livStudio = null;
						livStudio = new LivelloStudio();
						livStudio.setCodiceLivelloMin(anagraficaLavoratoreEspansoSilp.getCodMinLivelloIstruzioneCO());
						livStudio.setDsTitolo(anagraficaLavoratoreEspansoSilp.getDescrLivelloIstruzioneCO());
						lavoratoreSilpEspanso.setLivelloStudio(livStudio);
					}

					// Il sistema compila: nome, cognome, genere, data di nascita, comune o stato
					// estero di nascita (codStatoNascita, descrStatoNascita,codComuneNascita,
					// descrComuneNascita)
					lavoratoreSilpEspanso.setNome(anagraficaLavoratoreEspansoSilp.getNome());
					lavoratoreSilpEspanso.setCognome(anagraficaLavoratoreEspansoSilp.getCognome());
					lavoratoreSilpEspanso.setSesso(anagraficaLavoratoreEspansoSilp.getGenere());

					LavoratoriSilpPK ilLavPk = new LavoratoriSilpPK();
					ilLavPk.setId(Integer.parseInt(anagraficaLavoratoreEspansoSilp.getIdLavoratore()));
					ilLavPk.setCodiceFiscale(anagraficaLavoratoreEspansoSilp.getCodiceFiscale());
					lavoratoreSilpEspanso.setId(ilLavPk);

					try {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date date = sdf.parse(anagraficaLavoratoreEspansoSilp.getDataNascita());
						lavoratoreSilpEspanso.setDataNascita(date);
					} catch (Exception e) {
						log.error(methodName, e.getMessage(), e);
					}
					Comune comune = null;
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodComuneNascita())
							|| ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getDescrComuneNascita())) {
						comune = new Comune();
						comune.setCodComuneMin(anagraficaLavoratoreEspansoSilp.getCodComuneNascita());
						comune.setDsComTComune(anagraficaLavoratoreEspansoSilp.getDescrComuneNascita());
						lavoratoreSilpEspanso.setComuneNascita(comune);
					}
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodComuneDomicilio())
							|| ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodComuneDomicilio())) {
						comune = new Comune();
						comune.setCodComuneMin(anagraficaLavoratoreEspansoSilp.getCodComuneDomicilio());
						comune.setDsComTComune(anagraficaLavoratoreEspansoSilp.getDescrComuneDomicilio());
						lavoratoreSilpEspanso.setComuneDomicilio(comune);
					}
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodComuneResidenza())
							|| ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodComuneResidenza())) {
						comune = new Comune();
						comune.setCodComuneMin(anagraficaLavoratoreEspansoSilp.getCodComuneResidenza());
						comune.setDsComTComune(anagraficaLavoratoreEspansoSilp.getDescrComuneResidenza());
						lavoratoreSilpEspanso.setComuneResidenza(comune);
					}

					// stato estero di nascita
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodStatoNascita())
							&& !anagraficaLavoratoreEspansoSilp.getCodStatoNascita()
									.equals(ComonlConstants.NON_DISPONIBILE)) {
						StatiEsteri statiEsteri = new StatiEsteri();
						statiEsteri.setCodNazioneMin(anagraficaLavoratoreEspansoSilp.getCodStatoNascita());
						statiEsteri.setDsStatiEsteri(anagraficaLavoratoreEspansoSilp.getDescrStatoNascita());
						lavoratoreSilpEspanso.setStatiEsteriNascita(statiEsteri);
					}

					// stato estero di Residenza
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodStatoResidenza())
							&& !anagraficaLavoratoreEspansoSilp.getCodStatoResidenza()
									.equals(ComonlConstants.NON_DISPONIBILE)) {
						StatiEsteri statiEsteri = new StatiEsteri();
						statiEsteri.setCodNazioneMin(anagraficaLavoratoreEspansoSilp.getCodStatoResidenza());
						statiEsteri.setDsStatiEsteri(anagraficaLavoratoreEspansoSilp.getDescrStatoResidenza());
						lavoratoreSilpEspanso.setStatiEsteriResidenza(statiEsteri);
					}

					lavoratoreSilpEspanso.setCodCpiCompentenza(anagraficaLavoratoreEspansoSilp.getCodCpiCompentenza());
					lavoratoreSilpEspanso.setCodCpiProprieta(anagraficaLavoratoreEspansoSilp.getCodCpiProprieta());

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodMinCittadinanza())) {
						Cittadinanza laCittadinanza = null;
						laCittadinanza = new Cittadinanza();
						laCittadinanza.setCodCittadinanzaMin(anagraficaLavoratoreEspansoSilp.getCodMinCittadinanza());
						lavoratoreSilpEspanso.setCittadinanza(laCittadinanza);
					}

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodMinQuestura())) {
						Questura laQuestura = null;
						laQuestura = new Questura();
						laQuestura.setCodQuesturaMin(anagraficaLavoratoreEspansoSilp.getCodMinQuestura());
						laQuestura.setDsQuestura(anagraficaLavoratoreEspansoSilp.getDescrQuestura());
						lavoratoreSilpEspanso.setQuestura(laQuestura);
					}

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodMinTipoDocumento())) {
						StatusStraniero loStatus = null;
						loStatus = new StatusStraniero();
						loStatus.setCodStatusMin(anagraficaLavoratoreEspansoSilp.getCodMinTipoDocumento());
						loStatus.setDsComTStatusStraniero(anagraficaLavoratoreEspansoSilp.getDsStatusLavExtraUE());
						lavoratoreSilpEspanso.setStatusStraniero(loStatus);
					}

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodMinMotivoRilascioPermSogg())) {
						MotivoPermesso ilMotivo = new MotivoPermesso();
						ilMotivo.setCodMotivoMin(anagraficaLavoratoreEspansoSilp.getCodMinMotivoRilascioPermSogg());
						ilMotivo.setDsComTMotivoPermesso(
								anagraficaLavoratoreEspansoSilp.getDescrMotivoRilascioPermSogg());
						lavoratoreSilpEspanso.setMotivoPermesso(ilMotivo);
					}

					lavoratoreSilpEspanso.setCodStatoCivile(anagraficaLavoratoreEspansoSilp.getCodStatoCivile());
					if (null != anagraficaLavoratoreEspansoSilp.getDataRichPermSogg()) {
						lavoratoreSilpEspanso.setDataRichPermSogg(
								ComonlDateUtils.parseDate(anagraficaLavoratoreEspansoSilp.getDataRichPermSogg()));
					}
					if (null != anagraficaLavoratoreEspansoSilp.getDataScadPermSogg()) {
						lavoratoreSilpEspanso.setDataScadPermSogg(
								ComonlDateUtils.parseDate(anagraficaLavoratoreEspansoSilp.getDataScadPermSogg()));
					}
					lavoratoreSilpEspanso
							.setDescrCpiCompentenza(anagraficaLavoratoreEspansoSilp.getDescrCpiCompentenza());
					lavoratoreSilpEspanso.setDescrCpiProprieta(anagraficaLavoratoreEspansoSilp.getDescrCpiProprieta());
					lavoratoreSilpEspanso.setDescrFax(anagraficaLavoratoreEspansoSilp.getDescrFax());
					lavoratoreSilpEspanso
							.setDescrNumDocPermSogg(anagraficaLavoratoreEspansoSilp.getDescrNumDocPermSogg());
					lavoratoreSilpEspanso.setDescrStatoCivile(anagraficaLavoratoreEspansoSilp.getDescrStatoCivile());
					lavoratoreSilpEspanso.setDescrWeb(anagraficaLavoratoreEspansoSilp.getDescrWeb());
					lavoratoreSilpEspanso.setDsMail(anagraficaLavoratoreEspansoSilp.getDsMail());
					lavoratoreSilpEspanso
							.setFlagCartaSoggiorno(anagraficaLavoratoreEspansoSilp.getFlagCartaSoggiorno());
					lavoratoreSilpEspanso
							.setNumFamiliariCarico(anagraficaLavoratoreEspansoSilp.getNumFamiliariCarico());
					lavoratoreSilpEspanso.setNumTelCell(anagraficaLavoratoreEspansoSilp.getNumTelCell());
					lavoratoreSilpEspanso.setNumTelDom(anagraficaLavoratoreEspansoSilp.getNumTelDom());
					lavoratoreSilpEspanso.setNumTelRes(anagraficaLavoratoreEspansoSilp.getNumTelRes());

					lavoratoreSilpEspanso.setCapDomicilio(anagraficaLavoratoreEspansoSilp.getCapDomicilio());
					lavoratoreSilpEspanso.setCapResidenza(anagraficaLavoratoreEspansoSilp.getCapResidenza());
					lavoratoreSilpEspanso
							.setIndirizzoDomicilio(anagraficaLavoratoreEspansoSilp.getIndirizzoDomicilio());
					lavoratoreSilpEspanso
							.setIndirizzoResidenza(anagraficaLavoratoreEspansoSilp.getIndirizzoResidenza());

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodToponimoDomicilio())) {
						Toponimo ilTopoDomi = new Toponimo();
						ilTopoDomi.setId(anagraficaLavoratoreEspansoSilp.getCodToponimoDomicilio());
						lavoratoreSilpEspanso.setToponimoDomicilio(ilTopoDomi);
					}
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getCodToponimoResidenza())) {
						Toponimo ilTopoResi = new Toponimo();
						ilTopoResi.setId(anagraficaLavoratoreEspansoSilp.getCodToponimoResidenza());
						lavoratoreSilpEspanso.setToponimoResidenza(ilTopoResi);
					}
					lavoratoreSilpEspanso
							.setNumCivicoDomicilio(anagraficaLavoratoreEspansoSilp.getNumCivicoDomicilio());
					lavoratoreSilpEspanso
							.setNumCivicoResidenza(anagraficaLavoratoreEspansoSilp.getNumCivicoResidenza());

					/* Da silp arriva solamente la sigla provincia che corrisponde alla ds_targa */
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getSiglaProvDomicilio())) {
						Provincia laProvinciaDom = new Provincia();
						laProvinciaDom.setDsTarga(anagraficaLavoratoreEspansoSilp.getSiglaProvDomicilio());
						lavoratoreSilpEspanso.setProvincia(laProvinciaDom);
					}
					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getSiglaProvNascita())) {
						Provincia laProvinciaNascita = new Provincia();
						laProvinciaNascita.setDsTarga(anagraficaLavoratoreEspansoSilp.getSiglaProvNascita());
						lavoratoreSilpEspanso.setProvincia(laProvinciaNascita);
					}

					if (ComonlUtility.isNotVoid(anagraficaLavoratoreEspansoSilp.getSiglaProvResidenza())) {
						Provincia laProvinciaRes = new Provincia();
						laProvinciaRes.setDsTarga(anagraficaLavoratoreEspansoSilp.getSiglaProvResidenza());
						lavoratoreSilpEspanso.setProvincia(laProvinciaRes);
					}
				} else {
					/*
					 * il cognome o/e il nome inseriti per la ricerca non corrispondono ai dati
					 * restituiti da silp e quindi azzero l'oggetto trovato eventualmente su silp
					 * per codice fiscale
					 */
					lavoratoreSilpEspanso = null;
				}
			}

		} catch (

		ServiziSilpException_Exception e) {
			log.error(methodName, e);
			return null;
		} catch (IOException e) {
			log.error(methodName, e);
			return null;
		} catch (Exception e) {
			log.error(methodName, e);
			return null;
		}

		return lavoratoreSilpEspanso;
	}

	public EstrazioneAnagraficaLavoratore getService() throws IOException {
		if (s == null) {
			s = Service.create(getClass().getResource("/silpsv.silpsval.wsdl"),
					new QName("urn:silpsval", "EstrazioneAnagraficaLavoratoreService"));
		}
		EstrazioneAnagraficaLavoratore service = s.getPort(EstrazioneAnagraficaLavoratore.class);
		BindingProvider bp = (BindingProvider) service;
		Map<String, Object> context = bp.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpoint());
		return service;
	}

	public String getEndpoint() throws IOException {
		final String SILP_RESOURCE = "/silp.properties";
		InputStream is = this.getClass().getResourceAsStream(SILP_RESOURCE);
		Properties properties = new Properties();
		properties.load(is);
		String endpoint = properties.getProperty("url_al");
		return endpoint;
	}

}
