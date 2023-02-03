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
package it.csi.comonl.comonlweb.spicom.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.exception.SpicomIntegrationException;
import it.csi.comonl.comonlweb.lib.mapper.ComonlIntegMappers;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.spicom.AppConstants;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.EsitoValidazioneComunicazione;
import it.csi.spicom.interf.EsitoInvioComunicazione;
import it.csi.spicom.interf.RisultatoConversioneTracciato;
import it.csi.spicom.interf.ServizioInvioComunicazioni;
import it.csi.spicom.interf.ServizioTraduzioneTracciati;
import it.csi.spicom.util.ComunicazioneTUValidator;
import it.csi.spicom.util.serialization.ObjectSerializer;
import it.csi.util.performance.StopWatch;

public class SpicomIntegrator {
	
	protected final LogUtil logger = new LogUtil(getClass());

	private static final SpicomIntegrator instance = new SpicomIntegrator();

	private static final String PD_SERVICE_TRADUZIONE = "/defpd_spicom_traduzione_soap.xml";
	private static final String PD_SERVICE_INVIO = "/defpd_spicom_invio_soap.xml";
	
	private static ServizioTraduzioneTracciati serviceTraduzione;
	private static ServizioInvioComunicazioni serviceInvio;

	public static SpicomIntegrator getInstance() {
		return instance;
	}

	/**
	 * Restituisce l'interfaccia della Porta Delegata SOAP
	 */
	public ServizioTraduzioneTracciati getServiceTraduzione() throws Exception {
		logger.debug("[SpicomIntegrator::getService]","START");
		if (serviceTraduzione == null) {

			InputStream pd = this.getClass().getResourceAsStream(PD_SERVICE_TRADUZIONE);
			InfoPortaDelegata info = PDConfigReader.read(pd);
			serviceTraduzione = (ServizioTraduzioneTracciati) PDProxy.newInstance(info);
		}
		logger.debug("[SpicomIntegrator::getService]","END");
		return serviceTraduzione; 
	}
	
	/**
	 * Restituisce l'interfaccia della Porta Delegata SOAP
	 */
	public ServizioInvioComunicazioni getServiceInvio() throws Exception {
		logger.debug("[AdapterSpicom::getService]","START");
		if (serviceInvio == null) {

			InputStream pd = this.getClass().getResourceAsStream(PD_SERVICE_INVIO);
			InfoPortaDelegata info = PDConfigReader.read(pd);
			serviceInvio = (ServizioInvioComunicazioni) PDProxy.newInstance(info);
		}
		logger.debug("[AdapterSpicom::getService]","END");
		return serviceInvio;
	}
	
	/*
	 * Converte il tracciato ministeriale in oggettone
	 */
	public ComunicazioneTracciatoUnicoDTO convertiInFormatoUnico(String xml) {
		logger.debug("[AdapterSpicom::convertiInFormatoUnico]","START");
		StopWatch watcher = new StopWatch(AppConstants.LOGGER);
		watcher.start();

		ComunicazioneTracciatoUnicoDTO tracciatoConvertito = null;
		String[] tracciatiXml = {xml};
		try {
			logger.debug("[AdapterSpicom::convertiInFormatoUnico] xml da convertire ", xml);
			
			RisultatoConversioneTracciato[] oggettiSpicom = getServiceTraduzione().convertiInFormatoUnico2(tracciatiXml);
			
			if(oggettiSpicom != null && oggettiSpicom[0] != null && oggettiSpicom[0].isConversioneEseguitaConSuccesso()) {
				
				tracciatoConvertito = oggettiSpicom[0].getTraccciatoConvertito();
			} else {
				logger.error("[AdapterSpicom::convertiInFormatoUnico] ERRORE " , "Si sono verificati errori nella conversione del tracciato");		
			}
			
		} catch (Exception e) {
			logger.error("[AdapterSpicom::convertiInFormatoUnico] ERRORE " + e.getMessage(), e);		
			throw new SpicomIntegrationException(e);
			
		} finally {
			watcher.dumpElapsed(getClass().getName(), "inviaComunicazione",
					"Invio comunicazione a Spicom " + getClass().getName() + ".inviaComunicazione",
					"");
			watcher.stop();
		}	
		
		return tracciatoConvertito;
	}

	/*
	 * Converte il tracciato ministeriale in oggettone
	 */
	public List<RisultatoConversioneTracciato> convertiInFormatoUnico(String[] xmls) {
		logger.debug("[AdapterSpicom::convertiInFormatoUnico]","START");
		StopWatch watcher = new StopWatch(AppConstants.LOGGER);
		watcher.start();
		List<RisultatoConversioneTracciato> listaRitornoSpicom = new ArrayList<RisultatoConversioneTracciato>();
		ComunicazioneTracciatoUnicoDTO tracciatoConvertito = null;
		try {
			if(logger.isDebugEnabled()) {
				for(String xml : xmls) {
					logger.debug("[AdapterSpicom::convertiInFormatoUnico] xml da convertire ", xml);
				}
			}
			
			RisultatoConversioneTracciato[] oggettiSpicom = getServiceTraduzione().convertiInFormatoUnico2(xmls);
			
			
			for(RisultatoConversioneTracciato ret : oggettiSpicom) {
				if(ret.isConversioneEseguitaConSuccesso()) {
					ret.setTraccciatoConvertito((ComunicazioneTracciatoUnicoDTO)ObjectSerializer.xmlToObject(ret.getTracciatoInUscita())); // per problemi di desializzazione della PA
				}
				listaRitornoSpicom.add(ret);
			}
			
			
		} catch (Exception e) {
			logger.error("[AdapterSpicom::convertiInFormatoUnico] ERRORE " + e.getMessage(), e);		
			throw new SpicomIntegrationException(e);
			
		} finally {
			watcher.dumpElapsed(getClass().getName(), "inviaComunicazione",
					"Invio comunicazione a Spicom " + getClass().getName() + ".inviaComunicazione",
					"");
			watcher.stop();
		}	
		
		return listaRitornoSpicom;
	}

	
	// restituisce il codice regionale
	/**
	 * @param comunicazione oggetto comunicazione
	 * @param ruolo
	 * @param variazioneSomm in caso di somministrazione va reperito da COM_T_VARIAZIONE_SOMM filtrando per tipoComunicazione e tipoSomministrazione
	 * per controllare se la comunicazione è unisomm usare il metodo ControlliSuOggettoComunicazione.isUNISOMM(comunicazione)
	 * @param versioneXML NUMERO_VERSIONE_TRACCIATO_PER_SPICOM su tabella COM_T_COMONLS_PARAMETRI
	 * @return
	 * @throws SpicomIntegrationException
	 */
	public String inviaComunicazione(Comunicazione comunicazione, Ruolo ruolo, VariazioneSomm variazioneSomm, String versioneXML) throws SpicomIntegrationException {
		logger.debug("[AdapterSpicom::inviaComunicazione]","START");
		StopWatch watcher = new StopWatch(AppConstants.LOGGER);
		watcher.start();

		String ret = null;
		try {
			logger.debug("[AdapterSpicom::inviaComunicazione] invio comunicazione id ", comunicazione.getId());
			logger.debug("[AdapterSpicom::inviaComunicazione] ruolo", ruolo.getIlRuolo());
			
			ComonlIntegMappers comunicazioneMepper = new ComonlIntegMappers();
			ComunicazioneTracciatoUnicoDTO comunicazioneSpicom = comunicazioneMepper.toComunicazioneSpicom(comunicazione, ruolo, variazioneSomm, versioneXML);			
			
			EsitoValidazioneComunicazione esitoValidazione = ComunicazioneTUValidator.validaComunicazione(comunicazioneSpicom);
			if(!esitoValidazione.isComunicazioneValida()) {
				throw new SpicomIntegrationException(esitoValidazione.getEsitoValidazione());
			}
						
			String xml = ObjectSerializer.objectToXml(comunicazioneSpicom).replaceAll("java.sql.Date", "java.util.Date");
			
			logger.debug("[AdapterSpicom::inviaComunicazione]", xml);
			
			EsitoInvioComunicazione esito = getServiceInvio().inviaComunicazioneProspettoXML(xml);
			
			ret = esito.getIdentificativoRegionale();
			
			logger.info("[AdapterSpicom::inviaComunicazione] codice regionale ", esito.getIdentificativoRegionale());
			logger.info("[AdapterSpicom::inviaComunicazione] codice spicom ", esito.getIdentificativoSpicom());		
			
		} catch (Exception e) {
			logger.error("[AdapterSpicom::inviaComunicazione] ERRORE " + e.getMessage(), e);		
			throw new SpicomIntegrationException(e);
			
		} finally {
			watcher.dumpElapsed(getClass().getName(), "inviaComunicazione",
					"Invio comunicazione a Spicom " + getClass().getName() + ".inviaComunicazione",
					"");
			watcher.stop();
		}
		
		logger.debug("[AdapterSpicom::inviaComunicazione]","END");

		return ret;
	}
	public String inviaComunicazioneProvenienzaCommax(ComunicazioneTracciatoUnicoDTO comunicazioneSpicom) throws SpicomIntegrationException {
		logger.debug("[AdapterSpicom::inviaComunicazioneProvenienzaCommax]","START");
		StopWatch watcher = new StopWatch(AppConstants.LOGGER);
		watcher.start();

		String ret = null;
		try {
			
			
			String xml = ObjectSerializer.objectToXml(comunicazioneSpicom).replaceAll("java.sql.Date", "java.util.Date");
			
			logger.debug("[AdapterSpicom::inviaComunicazione]", xml);

			EsitoInvioComunicazione esito = getServiceInvio().inviaComunicazioneProspettoXML(xml);
			
			ret = esito.getIdentificativoRegionale();
			
			logger.info("[AdapterSpicom::inviaComunicazioneProvenienzaCommax] codice regionale ", esito.getIdentificativoRegionale());
			logger.info("[AdapterSpicom::inviaComunicazioneProvenienzaCommax] codice spicom ", esito.getIdentificativoSpicom());		
			
		} catch (Exception e) {
			logger.error("[AdapterSpicom::inviaComunicazioneProvenienzaCommax] ERRORE " + e.getMessage(), e);		
			throw new SpicomIntegrationException(e);
			
		} finally {
			watcher.dumpElapsed(getClass().getName(), "inviaComunicazioneProvenienzaCommax",
					"Invio comunicazione a Spicom " + getClass().getName() + ".inviaComunicazioneProvenienzaCommax",
					"");
			watcher.stop();
		}
		
		logger.debug("[AdapterSpicom::inviaComunicazioneProvenienzaCommax]","END");

		return ret;
	}
}
