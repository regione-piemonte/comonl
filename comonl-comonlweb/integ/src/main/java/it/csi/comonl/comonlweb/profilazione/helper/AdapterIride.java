/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION AAEP, IRIDE submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.profilazione.helper;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.profilazione.dto.RuoloIrideListaCasiUso;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.iride2.policy.entity.Actor;
import it.csi.iride2.policy.entity.Application;
import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.entity.UseCase;
import it.csi.iride2.policy.exceptions.InternalException;
import it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService;
import it.csi.util.performance.StopWatch;

/**
 * Recupero dei dati da IRIDE2.pep (PD/PA SOPA)
 */
public class AdapterIride {

	private static final String RUOLO = "ruolo";

	private static final String INFO_PERSONA_GROUP_INFO_PERSONA_CODICE_ENTE = "/info-persona-group/info-persona/CODICE_ENTE";

	private static final String INFO_PERSONA_GROUP_INFO_PERSONA_PROVINCIA = "/info-persona-group/info-persona/PROVINCIA";

	private static final String INFO_PERSONA_GROUP_INFO_PERSONA_MAIL = "/info-persona-group/info-persona/MAIL";

	private static final String INFO_PERSONA_GROUP_INFO_PERSONA = "/info-persona-group/info-persona";

	private static final String UC1_COMONL_ACCESSO = "UC1_COMONL_ACCESSO";

	protected final LogUtil logger = new LogUtil(getClass());

	private static final AdapterIride instance = new AdapterIride();

	private static final String PD_SERVICE = "/iride2_pep_defPD_soap.xml";

	private static PolicyEnforcerBaseService service;
	
	
	private AdapterIride() {}

	public static AdapterIride getInstance() {
		return instance;
	}

	/**
	 * Restituisce l'interfaccia della Porta Delegata SOAP
	 */
	private PolicyEnforcerBaseService getService() throws Exception {
		if (service == null) {
			InputStream pd = this.getClass().getResourceAsStream(PD_SERVICE);
			InfoPortaDelegata info = PDConfigReader.read(pd);
			service = (PolicyEnforcerBaseService) PDProxy.newInstance(info);
		}
		return service;
	}

	public Identita identificaUserPassword(String user, String password) throws Exception {

		logger.debug("[AdapterIride::identificaUserPassword] BEGIN user=" + user, "");
		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();

		Identita result = null;
		try {
			result = getService().identificaUserPassword(user, password);

		} catch (Exception e) {
			logger.error("Exception---->>>> getListRuoliIride()", e);
			throw e;
		} finally {
			watcher.dumpElapsed("AdapterIride", "getListRuoliIride", "invocazione servizio IRIDE", "");
			watcher.stop();
			logger.debug("[AdapterIride::getListRuoliIride] END", "");
		}
		return result;

	}

	/**
	 * Reperisce da Iride una lista di attori con i casi d'uso associati a ciascun
	 * attore.
	 * 
	 * @param identita Identita Iride restituita da Shibboleth
	 * @return
	 */
	public List<RuoloIrideListaCasiUso> getListRuoliIride(Identita identita) throws Exception {
		logger.debug("[AdapterIride::getListRuoliIride] BEGIN identita=" + identita, "");
		StopWatch watcher = new StopWatch(ComonlConstants.LOGGER);
		watcher.start();
		List<RuoloIrideListaCasiUso> result = new ArrayList<>();
		try {
			Application application = new Application(ComonlConstants.APPLICATION_NAME_IRIDE);
			Actor[] elencoAttori = getService().findActorsForPersonaInApplication(identita, application);
			for (Actor loAttoreDaIride : elencoAttori) {
				RuoloIrideListaCasiUso ilRuoloDaAggiungere = new RuoloIrideListaCasiUso();
				ilRuoloDaAggiungere.setActor(loAttoreDaIride);
				if (loAttoreDaIride.getId().equals(ComonlConstants.OPERATORE_APL_COMONL)) {
					recuperaInfoPersona(identita, application, ilRuoloDaAggiungere);
				}
				result.add(ilRuoloDaAggiungere);
			}

		} catch (Exception e) {
			logger.error("Exception---->>>> getListRuoliIride()", e);
			throw e;
		} finally {
			watcher.dumpElapsed("AdapterIride", "getListRuoliIride", "invocazione servizio IRIDE", "");
			watcher.stop();
			logger.debug("[AdapterIride::getListRuoliIride] END", "");
		}
		return result;
	}

	private void recuperaInfoPersona(Identita identita, Application application,
			RuoloIrideListaCasiUso ilRuoloDaAggiungere) throws Exception {
		// solamente in caso di operatore bisogna richiamare il servizio
		// infoPersonaInUseCase
		UseCase useC = new UseCase(application, UC1_COMONL_ACCESSO);
		String infoPersonaInUseCase = getService().getInfoPersonaInUseCase(identita, useC);

//		InfoPersona infoPersona = new InfoPersona();
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		Document doc = dfactory.newDocumentBuilder().parse(new InputSource(new StringReader(infoPersonaInUseCase)));

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		Node infoPers = (Node) xpath.evaluate(INFO_PERSONA_GROUP_INFO_PERSONA, doc, XPathConstants.NODE);
		// Indirizzo e-mail
		Node nodoEmail = (Node) xpath.evaluate(INFO_PERSONA_GROUP_INFO_PERSONA_MAIL, doc, XPathConstants.NODE);
		if (nodoEmail != null)
			ilRuoloDaAggiungere.setMail(ottieniTesto(nodoEmail));

		// Provincia
		Node nodoPv = (Node) xpath.evaluate(INFO_PERSONA_GROUP_INFO_PERSONA_PROVINCIA, doc, XPathConstants.NODE);
		if (nodoPv != null)
			ilRuoloDaAggiungere.setProvincia(ottieniTesto(nodoPv));

		// Codice ente
		Node nodoEnte = (Node) xpath.evaluate(INFO_PERSONA_GROUP_INFO_PERSONA_CODICE_ENTE, doc, XPathConstants.NODE);
		if (nodoEnte != null) {
			// in caso di operatore provinciale, il codice ente non e' valorizzato, e quindi
			// non va cercato
			NamedNodeMap attr = infoPers.getAttributes();
			Node attrRuolo = attr.getNamedItem(RUOLO);
			if (attrRuolo != null && attrRuolo.getNodeValue() != null
					&& !attrRuolo.getNodeValue().equals(ComonlConstants.PROVINCIA_OPERATORE)) {
				ilRuoloDaAggiungere.setCodiceEnte(ottieniTesto(nodoEnte));
			}
		}
		logger.info("----infopersona---->" + ilRuoloDaAggiungere.toString(), "");
	}

	/**
	 * Ottiene il testo da un nodo DOM, si aspetta che il testo ci sia, altrimenti
	 * solleva una Internal Exception. Questo perch$egrave; ci aspettiamo che il
	 * getInfoPersona abbia il testo obbligatorio nelle foglie.
	 * 
	 * @param nodo nodo DOM
	 * @return testo (senza spazi prima e dopo) contenuto nel nodo.
	 * @throws InternalException per un problema generico.
	 */
	private String ottieniTesto(Node nodo) throws Exception {
		NodeList listaFigli = nodo.getChildNodes();
		for (int i = 0; i < listaFigli.getLength(); i++) {
			Node nodoFiglio = listaFigli.item(i);
			if (nodoFiglio.getNodeType() == Node.TEXT_NODE)
				return nodoFiglio.getNodeValue().trim();
		}
		throw new Exception("Formato XML sbagliato!");
	}

}
