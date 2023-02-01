/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.spipddbridge;

import java.io.InputStream;

import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.spicom.interf.RisultatoConversioneTracciato;
import it.csi.spicom.interf.ServizioTraduzioneTracciati;
import it.csi.util.performance.StopWatch;


public class AdapterSpiPddBrige {
	
	
	public static void main(String...strings) throws Exception{
		
		String[] args = new String[] {"<note>\r\n"
				+ "<to>Tove</to>\r\n"
				+ "<from>Jani</from>\r\n"
				+ "<heading>Reminder</heading>\r\n"
				+ "<body>Don't forget me this weekend!</body>\r\n"
				+ "</note>"};
		AdapterSpiPddBrige.getInstance().convertiInFormatoUnico(args);
		
	}

	
	protected final LogUtil logger = new LogUtil(getClass());

	private static final AdapterSpiPddBrige instance = new AdapterSpiPddBrige();

	private static final String PD_SERVICE = "/spicom_traduzione_tracciati_PD.xml";

	private static ServizioTraduzioneTracciati service;
	
	
	private AdapterSpiPddBrige() {}

	public static AdapterSpiPddBrige getInstance() {
		return instance;
	}

	/**
	 * Restituisce l'interfaccia della Porta Delegata SOAP
	 */
	private ServizioTraduzioneTracciati getService() throws Exception {
		if (service == null) {
			InputStream pd = this.getClass().getResourceAsStream(PD_SERVICE);
			InfoPortaDelegata info = PDConfigReader.read(pd);
			service = (ServizioTraduzioneTracciati) PDProxy.newInstance(info);
		}
		return service;
	}

	public RisultatoConversioneTracciato[] convertiInFormatoUnico(String[] vTracciatiXml) throws Exception {

		logger.debug("[AdapterSpiPddBrige::convertiInFormatoUnico] BEGIN", "");
		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();

		RisultatoConversioneTracciato[] result = null;
		try {
			result = getService().convertiInFormatoUnico(vTracciatiXml);
		} catch (Exception e) {
			logger.error("Exception---->>>> convertiInFormatoUnico()", e);
			throw e;
		} finally {
			watcher.dumpElapsed("AdapterSpiPddBrige", "convertiInFormatoUnico", "invocazione servizio SPIPDDBRIDGE", "");
			watcher.stop();
			logger.debug("[AdapterSpiPddBrige::convertiInFormatoUnico] END", "");
		}
		return result;

	}

	
}
