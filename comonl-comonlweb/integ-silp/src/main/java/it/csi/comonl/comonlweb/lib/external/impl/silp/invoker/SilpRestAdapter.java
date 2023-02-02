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
package it.csi.comonl.comonlweb.lib.external.impl.silp.invoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.LavoratoreSilpEspanso;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.AziendaParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.AziendaResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.CallInfoParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.LavoratoreParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.LavoratoreResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.Referente;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteDeleteParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteFindParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteFindResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.SedeParam;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public class SilpRestAdapter {

	private String REST_URI;
	private String REST_USER;
	private String REST_PASSWORD;

	private static final String COD_APPLICATIVO = "COMONL";

	private static SilpRestAdapter adapter;
	private CallInfoParam callInfo;
	private Client client;
	private Authenticator authenticator;

	// =========================================================================
	// MAIN
	public static void main(String args[]) {

		try {

			// LegaleRappr referente =
			// SilpRestAdapter.getInstance().getReferente("MRNGTN75E09L219W",new
			// BigDecimal(1870688));
			// System.out.println(referente);

			ReferenteResponse referente = SilpRestAdapter.getInstance().saveReferente("MRNGTN75E09L219W",
					new LegaleRappr());
			System.out.println(referente);

		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	public static SilpRestAdapter getInstance() {
		if (adapter == null)
			adapter = new SilpRestAdapter();
		return adapter;
	}

	private SilpRestAdapter() {
		try {
			final String SILP_RESOURCE = "/silp.properties";
			InputStream is = this.getClass().getResourceAsStream(SILP_RESOURCE);
			Properties properties = new Properties();
			properties.load(is);
			REST_URI = properties.getProperty("url_rest");
			REST_USER = properties.getProperty("user_rest");
			REST_PASSWORD = properties.getProperty("password_rest");
		} catch (Exception err) {
			throw new RuntimeException("Errore nella lettura delle properties per la connessione al servizio rest");
		}
		callInfo = new CallInfoParam();
		callInfo.setCodApplicativo(COD_APPLICATIVO);
		authenticator = new Authenticator(REST_USER, REST_PASSWORD);
		JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		client = ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider));
	}

	public BigDecimal saveLavoratore(String userCodiceFiscale, LavoratoreSilpEspanso lavoratore)
			throws SilpBusinessException {

		callInfo.setCodUser(userCodiceFiscale);
		LavoratoreParam paramLAvoratore = toLavoratoreParam(lavoratore);
		// impostato di default fino a quando non verra' tolta l'obbligatorieta' del
		// campo su silp.
//		param.setIdSilTStatoCiv("C");

		paramLAvoratore.setCallInfoParam(callInfo);

		Response r = client.register(authenticator).target(REST_URI + "lavoratori/save")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(paramLAvoratore, MediaType.APPLICATION_JSON));

		LavoratoreResponse resp = r.readEntity(LavoratoreResponse.class);
		System.out.println(resp);

		if (!resp.isEsitoPositivo()) {
			throw new SilpBusinessException("saveLavoratore", resp.getApiMessages());
		}

		return toBigDecimal(resp.getId());
	}

	public BigDecimal saveAzienda(String userCodiceFiscale, DatiAzienda datiAzienda) throws SilpBusinessException {

		AziendaParam paramAzienda = toAziendaParam(datiAzienda);

		callInfo.setCodUser(userCodiceFiscale);
		paramAzienda.setCallInfoParam(callInfo);

		Response response = client.register(authenticator).target(REST_URI + "aziende/create")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(paramAzienda, MediaType.APPLICATION_JSON));

		AziendaResponse resp = response.readEntity(AziendaResponse.class);
		System.out.println(resp);

		if (!resp.isEsitoPositivo()) {
			throw new SilpBusinessException("saveAzienda", resp.getApiMessages());
		}

		// viene restituito l'id della azienda o della sede inseriti su silp
		return new BigDecimal(resp.getId());
	}

	public LegaleRappr getReferente(String userCodiceFiscale, BigDecimal idSede) throws SilpBusinessException {
		ReferenteFindParam param = new ReferenteFindParam();
		callInfo.setCodUser(userCodiceFiscale);
		param.setCallInfoParam(callInfo);
		param.setIdSede(toLong(idSede));
		param.setCodTipoReferente("L");

		Response r = client.register(authenticator).target(REST_URI + "referenti/find")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(param, MediaType.APPLICATION_JSON));

		ReferenteFindResponse resp = r.readEntity(ReferenteFindResponse.class);
		System.out.println(resp);

		if (!resp.isEsitoPositivo()) {
			throw new SilpBusinessException("getReferente", resp.getApiMessages());
		}

		if (resp.getReferenti() != null && resp.getReferenti().size() > 0) {

			for (Referente re : resp.getReferenti()) {
				System.out.println(re);
			}
			LegaleRappr ilLegaleTrovato = toLegaleRapprFromReferenteSilp(resp.getReferenti().get(0));

			return ilLegaleTrovato;
		}

		return null;
	}

	public ReferenteResponse saveReferente(String userCodiceFiscale, LegaleRappr ilLegale)
			throws SilpBusinessException {

		ReferenteParam paramReferente = toReferenteParam(ilLegale);
		callInfo.setCodUser(userCodiceFiscale);
		paramReferente.setCallInfoParam(callInfo);

		Response r = client.register(authenticator).target(REST_URI + "referenti/save")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(paramReferente, MediaType.APPLICATION_JSON));
		System.out.println(r);
		ReferenteResponse resp = r.readEntity(ReferenteResponse.class);
		System.out.println(resp);

		if (!resp.isEsitoPositivo()) {
			throw new SilpBusinessException("saveReferente", resp.getApiMessages());
		}
		// viene restituito l'id della azienda o della sede inseriti su silp
		return resp;
	}

	public boolean deleteReferente(String userCodiceFiscale, BigDecimal idReferente) throws SilpBusinessException {

		ReferenteDeleteParam paramReferente = new ReferenteDeleteParam();
		paramReferente.setId(toLong(idReferente));
		callInfo.setCodUser(userCodiceFiscale);
		paramReferente.setCallInfoParam(callInfo);

		Response r = client.register(authenticator).target(REST_URI + "referenti/delete")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(paramReferente, MediaType.APPLICATION_JSON));
		System.out.println(r);

		return true;
	}

	// ==========================================================================
	// MAPPER

	private LavoratoreParam toLavoratoreParam(LavoratoreSilpEspanso lavoratore) {
		LavoratoreParam param = new LavoratoreParam();

		if (lavoratore != null) {
			if (lavoratore.getId() != null)
				param.setCodFiscale(lavoratore.getId().getCodiceFiscale());

			if (null != lavoratore.getCognome() && lavoratore.getCognome().length() > 0) {
				param.setDsCognome(lavoratore.getCognome().trim());
			}
			if (null != lavoratore.getNome() && lavoratore.getNome().length() > 0) {
				param.setDsNome(lavoratore.getNome().trim());
			}

			param.setIdSilLavAnagrafica(toLong(lavoratore.getIdSilLavAnagrafica()));

			param.setCodCapDom(lavoratore.getCapDomicilio());
			param.setCodCapRes(lavoratore.getCapResidenza());
			param.setDataNasc(lavoratore.getDataNascita());
			param.setDataScadPermSogg(lavoratore.getDataScadPermSogg());
			param.setDsIndirizzoDom(lavoratore.getIndirizzoDomicilio());
			param.setDsNumCivicoDom(lavoratore.getNumCivicoDomicilio());
			param.setDsIndirizzoRes(lavoratore.getIndirizzoResidenza());
			param.setDsNumCivicoRes(lavoratore.getNumCivicoResidenza());
			param.setDsNumDocPermSogg(lavoratore.getDescrNumDocPermSogg());
			param.setFlgGenere(lavoratore.getSesso());

			if (lavoratore.getToponimoDomicilio() != null) {
				param.setIdSilTToponimoDom(lavoratore.getToponimoDomicilio().getId());
			}

			if (lavoratore.getToponimoResidenza() != null) {
				param.setIdSilTToponimoRes(lavoratore.getToponimoResidenza().getId());
			}

			if (lavoratore.getCittadinanza() != null)
				param.setCodMinCittadinanza(lavoratore.getCittadinanza().getCodCittadinanzaMin());

			if (lavoratore.getComuneDomicilio() != null && null != lavoratore.getComuneDomicilio().getCodComuneMin())
				param.setIdSilTComuneDom(lavoratore.getComuneDomicilio().getCodComuneMin());
			if (lavoratore.getComuneNascita() != null && null != lavoratore.getComuneNascita().getCodComuneMin())
				param.setIdSilTComuneNas(lavoratore.getComuneNascita().getCodComuneMin());
			if (lavoratore.getComuneResidenza() != null && null != lavoratore.getComuneResidenza().getCodComuneMin())
				param.setIdSilTComuneRes(lavoratore.getComuneResidenza().getCodComuneMin());
			if (lavoratore.getStatiEsteriNascita() != null
					&& null != lavoratore.getStatiEsteriNascita().getCodNazioneMin())
				param.setIdSilTNazioneNas(lavoratore.getStatiEsteriNascita().getCodNazioneMin());
			if (lavoratore.getStatiEsteriResidenza() != null
					&& null != lavoratore.getStatiEsteriResidenza().getCodNazioneMin())
				param.setIdSilTNazioneRes(lavoratore.getStatiEsteriResidenza().getCodNazioneMin());
			if (lavoratore.getMotivoPermesso() != null)
				param.setCodMinMotRilPerm(lavoratore.getMotivoPermesso().getCodMotivoMin());

			if (lavoratore.getQuestura() != null)
				param.setCodMinQuestura(lavoratore.getQuestura().getCodQuesturaMin());

			param.setIdSilTStatoCiv(lavoratore.getCodStatoCivile());

			if (lavoratore.getLivelloStudio() != null) {
				param.setCodMinGradoStudio(lavoratore.getLivelloStudio().getCodiceLivelloMin());
			}

			if (lavoratore.getStatusStraniero() != null)
				param.setCodMinStatusLavExtraUe(lavoratore.getStatusStraniero().getCodStatusMin());
		}
		return param;
	}

	private AziendaParam toAziendaParam(DatiAzienda datiAzienda) {
		AziendaParam paramAzienda = new AziendaParam();

		if (datiAzienda != null) {
			if (null != datiAzienda.getIdAziendaSilp()) {
				paramAzienda.setIdAzienda(Long.parseLong(datiAzienda.getIdAziendaSilp()));
				paramAzienda.setFlgMaster("S");
			}
			paramAzienda.setCodFiscale(datiAzienda.getCfAzienda());
			paramAzienda.setPartitaIva(datiAzienda.getPartitaIva());
			paramAzienda.setRagioneSociale(datiAzienda.getDenominazioneDatoreLavoro());
			paramAzienda.setAltreInformazioni(datiAzienda.getAltreInformazioni());
			if (datiAzienda.getAtecofin() != null) {
				paramAzienda.setCodAteco(datiAzienda.getAtecofin().getCodAtecofinMin());
			}
			if (datiAzienda.getCcnl() != null) {
				paramAzienda.setCodCcnl(datiAzienda.getCcnl().getCodCcnlMin());
			}
			if (datiAzienda.getNaturaGiuridica() != null) {
				paramAzienda.setCodNaturaGiuridica(datiAzienda.getNaturaGiuridica().getId());
			}
			paramAzienda.setFlgArtigiana(datiAzienda.getFlagArtigiana());
			if (ComonlConstants.FLAG_S.equals(datiAzienda.getFlgPubblicaAmm())) {
				paramAzienda.setFlgPubblicaAmministrazione("S");
			} else {
				paramAzienda.setFlgPubblicaAmministrazione("N");
			}
			// numero iscrizione albo e data
			paramAzienda.setDsAnilt(datiAzienda.getCodLavoroTemp());

			paramAzienda.setCodTipoAzienda(datiAzienda.getFlagTipoAzienda());

			paramAzienda.setCodTipoAzienda("G");

			// settaggio dell'eventuale sede da modificare/inserire

			if (datiAzienda.getSedeLegale() != null
					&& (datiAzienda.getSedeLegale().getComune() != null
							&& datiAzienda.getSedeLegale().getComune().getCodComuneMin() != null)
					|| (datiAzienda.getSedeLegale().getStatiEsteri() != null
							&& datiAzienda.getSedeLegale().getStatiEsteri().getCodNazioneMin() != null)) {
				SedeParam laSedeParam = toSedeParam(datiAzienda);
				paramAzienda.setSede(laSedeParam);
			}
		}
		return paramAzienda;
	}

	private SedeParam toSedeParam(DatiAzienda azienda) {

		SedeParam paramSede = new SedeParam();
		Sede sede = azienda.getSedeLegale();

		if (sede.getIdSedeSilp() != null && sede.getIdSedeSilp().trim().length() > 0) {
			paramSede.setIdSede(Long.parseLong(sede.getIdSedeSilp()));
		}
		if (sede.isSedeLegale()) {
			paramSede.setCodTipoSede(1L);
			// dall'elenco delle sedi legate all'azienda bisogna ricercare la vecchia sede
			// legale e prendere l'id per passarlo
			// al servizio di silp come sede legale old per farla diventare operativa e non
			// ritrovarsi più sedi legali
			if (azienda.getElencoSedi() != null) {
				for (Sede unaSede : azienda.getElencoSedi()) {
					if (unaSede.isSedeLegale()) {
						paramSede.setIdSedeLegaleOld(Long.parseLong(unaSede.getIdSedeSilp()));
						break;
					}
				}
			}
		} else if (sede.isSedeOperativa()) {
			paramSede.setCodTipoSede(2L);
		}
		if (null != sede.getStatiEsteri() && null != sede.getStatiEsteri().getCodNazioneMin()) {
			paramSede.setCodNazione(sede.getStatiEsteri().getCodNazioneMin());
		}
		if (sede.getComune() != null && null != sede.getComune().getCodComuneMin()) {
			paramSede.setCodComune(sede.getComune().getCodComuneMin());
		}
		if (sede.getToponimoSede() != null && sede.getToponimoSede().getIdComTToponimo() != null) {
			paramSede.setCodToponimo(sede.getToponimoSede().getIdComTToponimo());
		}
		paramSede.setIndirizzo(sede.getIndirizzo());
		paramSede.setNumCivico(sede.getNumeroCivico());
		paramSede.setCap(sede.getCap());
		paramSede.setTelefono(sede.getTelefono());
		paramSede.setFax(sede.getFax());
		paramSede.setEmail(sede.getEmail());
		paramSede.setFlgValida(sede.getFlgValida());
		if (sede.getFlgValida().equals("N")) {
			paramSede.setMotivoFlgValida(sede.getMotivoInvalida());
		} else {
			paramSede.setMotivoFlgValida(null);
		}

		paramSede.setDenominazione(azienda.getDenominazioneDatoreLavoro());
		paramSede.setInail(sede.getPatInail());
		paramSede.setNumAgenziaSomm(sede.getNumAgenziaSomministrazione());

		return paramSede;
	}

	private ReferenteParam toReferenteParam(LegaleRappr legale) {
		ReferenteParam ref = new ReferenteParam();

		if (null != legale.getDsCognome() && legale.getDsCognome().length() > 0) {
			ref.setDsCognome(legale.getDsCognome().trim());
		}
		if (null != legale.getDsNome() && legale.getDsNome().length() > 0) {
			ref.setDsNome(legale.getDsNome().trim());
		}

		ref.setGenere(legale.getSesso());
		ref.setDtNascita(legale.getDtNascita());
		if (null != legale.getStatiEsteri() && null != legale.getStatiEsteri().getCodNazioneMin()) {
			ref.setIdNazioneNasc(legale.getStatiEsteri().getCodNazioneMin());
		}
		if (null != legale.getStatusStraniero() && null != legale.getStatusStraniero().getCodStatusMin()) {
			ref.setCodMinTitoloSoggiorno(legale.getStatusStraniero().getCodStatusMin());
		}
		if (null != legale.getMotivoPermesso() && null != legale.getMotivoPermesso().getCodMotivoMin()) {
			ref.setCodMinMotivoTitoloSoggiorno(legale.getMotivoPermesso().getCodMotivoMin());
		}

		if (legale.getComune() != null && null != legale.getComune().getCodComuneMin()) {
			ref.setIdComuneNasc(legale.getComune().getCodComuneMin());
		}

		if (legale.getCittadinanza() != null && null != legale.getCittadinanza().getCodCittadinanzaMin()) {
			ref.setCodMinCittadinanza(legale.getCittadinanza().getCodCittadinanzaMin());
		}
		if (legale.getQuestura() != null && null != legale.getQuestura().getCodQuesturaMin()) {
			ref.setCodMinQuestura(legale.getQuestura().getCodQuesturaMin());
		}
		if (legale.getIdSedeAssociata() != null) {
			ref.setIdSede(Long.parseLong(legale.getIdSedeAssociata()));
		}

		ref.setNumeroTitoloSoggiorno(legale.getNumeroDocumento());
		ref.setDtScadenzaTitoloSoggiorno(legale.getDtScadenzaPermessoSogg());
		List<String> iRuoli = new ArrayList<>();
		iRuoli.add("L");
		ref.setRuoli(iRuoli);
//		ref.setMail("pippo@pluto.it");
		if (null != legale.getIdLegaleRapprSilp()) {
			ref.setId(toLong(legale.getIdLegaleRapprSilp()));
		}

		return ref;
	}

	private LegaleRappr toLegaleRapprFromReferenteSilp(Referente ref) {
		LegaleRappr rappr = new LegaleRappr();
		if (null != ref.getCodMinCittadinanza()) {
			Cittadinanza laCittadinanza = new Cittadinanza();
			laCittadinanza.setCodCittadinanzaMin(ref.getCodMinCittadinanza());
			rappr.setCittadinanza(laCittadinanza);
		}
		if (null != ref.getIdComuneNasc()) {
			Comune ilComune = new Comune();
			ilComune.setCodComuneMin(ref.getIdComuneNasc());
			rappr.setComune(ilComune);
		}
		if (null != ref.getCodMinMotivoTitoloSoggiorno()) {
			MotivoPermesso ilMotivo = new MotivoPermesso();
			ilMotivo.setCodMotivoMin(ref.getCodMinMotivoTitoloSoggiorno());
			rappr.setMotivoPermesso(ilMotivo);
		}
		if (null != ref.getIdNazioneNasc()) {
			StatiEsteri loStato = new StatiEsteri();
			loStato.setCodNazioneMin(ref.getIdNazioneNasc());
			rappr.setStatiEsteri(loStato);
		}
		if (null != ref.getCodMinQuestura()) {
			Questura laQuesta = new Questura();
			laQuesta.setCodQuesturaMin(ref.getCodMinQuestura());
			rappr.setQuestura(laQuesta);
		}
		if (null != ref.getCodMinTitoloSoggiorno()) {
			StatusStraniero ilTitolo = new StatusStraniero();
			ilTitolo.setCodStatusMin(ref.getCodMinTitoloSoggiorno());
			rappr.setStatusStraniero(ilTitolo);
		}
		rappr.setDsCognome(ref.getDsCognome());
		rappr.setDsNome(ref.getDsNome());
		rappr.setDtNascita(ref.getDtNascita());
		rappr.setDtScadenzaPermessoSogg(ref.getDtScadenzaTitoloSoggiorno());
		rappr.setNumeroDocumento(ref.getNumeroTitoloSoggiorno());
		rappr.setSesso(ref.getGenere());
		rappr.setIdLegaleRapprSilp(toBigDecimal(ref.getId()));
		rappr.setIdSedeAssociata(ref.getIdSede().toString());
		return rappr;
	}

	// =========================================================================
	// INNER - CLASSES

	private static class Authenticator implements ClientRequestFilter {

		private final String user;
		private final String password;

		public Authenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		public void filter(ClientRequestContext requestContext) throws IOException {
			MultivaluedMap<String, Object> headers = requestContext.getHeaders();
			final String basicAuthentication = getBasicAuthentication();
			headers.add("Authorization", basicAuthentication);

		}

		private String getBasicAuthentication() {
			String token = this.user + ":" + this.password;
			try {
				return "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException ex) {
				throw new IllegalStateException("Cannot encode with UTF-8", ex);
			}
		}
	}

	// =====================================================
	// MOCK

	private AziendaParam getAziendaParamMock() {

		AziendaParam param = new AziendaParam();
		param.setIdAzienda(2158535L);
		param.setCodFiscale("01295820334");
		param.setPartitaIva("01295820334");
		param.setCodAteco("2559");
		param.setCodCcnl("BC0N");
		param.setRagioneSociale("RAGIONE SOCIALE TEST");
		param.setCodNaturaGiuridica("DI");
		param.setFlgArtigiana("N");
		param.setCodTipoAzienda("G");
		param.setDsAnilt("DS ANILT TEST");
		param.setAltreInformazioni("ALTRE INFO TEST");

		SedeParam sede = new SedeParam();
		sede.setDenominazione("prova");
		sede.setCodTipoSede(1L);
		sede.setCodComune("L219");
		sede.setCodToponimo("PZA");
		sede.setIndirizzo("CAVOUR");
		sede.setNumCivico("10");
		sede.setCap("28069");
		sede.setLocalita("LOCALITA TEST");
		sede.setInps("COD INPS");
		sede.setInail("COD INAIL");
		sede.setFax("0321777666");
		sede.setTelefono("0321777666");
		sede.setEmail("sede.legale@gmail.it");
		sede.setDataInizioAttivita(new Date());
		sede.setDataRifNumDipendenti(new Date());
		sede.setFlgValida("S");

		param.setSede(sede);

		return param;
	}

	public static BigDecimal toBigDecimal(Number n) {
		if (n == null)
			return null;
		return new BigDecimal(n.toString());
	}

	public static Long toLong(BigDecimal n) {
		if (n == null)
			return null;
		return n.longValue();
	}

}
