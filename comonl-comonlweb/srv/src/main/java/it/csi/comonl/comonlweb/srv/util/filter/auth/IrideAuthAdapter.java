/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.srv.util.filter.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import it.csi.comonl.comonlweb.ejb.util.ComonlThreadLocalContainer;
import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

/**
 * Auth adapter for IRIDE
 */
@ApplicationScoped
public class IrideAuthAdapter implements AuthAdapter {

	private static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	private static final LogUtil LOG = new LogUtil(IrideAuthAdapter.class);
//	@Inject private UtenteDad utenteDad;

	@Override
	public Utente processAuth(boolean devMode, UriInfo uriInfo, ContainerRequestContext containerRequest) {
		final String methodName = "processAuth";
		String marker = getToken(containerRequest);
		if (marker != null) {
			return initMarkerIride(normalizeToken(marker));

		} else if (devMode) {
			return initMarkerIride(null);

		} else if (mustCheckPage(uriInfo.getRequestUri())) {
			// Il marcatore deve sempre essere presente altrimenti e' una condizione di
			// errore
			LOG.error(methodName, "Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
			return null;
		}
		return null;
	}

	/**
	 * Ottiene il cookie di autenticazione
	 * 
	 * @param containerRequest la richiesta HTTP
	 * @return il marker
	 */
	public String getToken(ContainerRequestContext containerRequest) {

		MultivaluedMap<String, String> multivaluedMap = containerRequest.getHeaders();
		Set<String> setKey = multivaluedMap.keySet();
		Iterator<String> iterKey = setKey.iterator();
		while (iterKey.hasNext()) {
			String headerName = (String) iterKey.next();
			String headerValue = containerRequest.getHeaderString(headerName);
			LOG.info("getToken", "keys - " + headerName + " = " + headerValue);
		}

		String marker = containerRequest.getHeaderString(AUTH_ID_MARKER);
		if (marker == null) {
			// Null-safety
			return null;
		}
		// Gestione dell'encoding
		return new String(marker.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

	private Utente initMarkerIride(String token) {
		final String methodName = "initMarkerIride";
		Identita identita;
		try {
			if (token != null) {
				LOG.debug(methodName, "token: " + token);
				identita = new Identita(token);
			} else {
				LOG.debug(methodName, "token caricato da file");
				// per il reperimento di un token valido di test si puo' utilizzare
				// http://tst-www.sistemapiemonte.it/routingconf-cons/identita.do
				// - verrà restituito il token
				// - creare un file ‪"token.txt" nella propria "home directory" (es.
				// ‪C:\Users\filip\token.txt)
				// - copiare il token nel file
				String pathHome = System.getProperty("user.home");
				String filenameToken = pathHome + "/token.txt";
				File fileToken = new File(filenameToken);
				String sToken = "AAAAAA00A11F000N/CSI PIEMONTE/DEMO 25/ACTALIS_EU/20210927092053/16/s+D6vE98racLnX/MII7pOw==";
				if (fileToken.exists()) {
					FileReader fr = new FileReader(fileToken);
					BufferedReader br = new BufferedReader(fr);
					sToken = br.readLine();
					if (sToken != null) {
						sToken = sToken.trim();
					}
					br.close();
					fr.close();
					br = null;
					fr = null;
				}

				identita = new Identita(sToken);

				// Dev mode - solo codice fiscale
				// identita.setCodFiscale("AAAAAA00A11B000J");
				// identita.setCodFiscale("AAAAAA00A11R000Z");
			}
		} catch (MalformedIdTokenException e) {
			LOG.error(methodName, "Token non correttamente formattato. " + e.toString(), e);
			return null;

		} catch (Exception e) {
			LOG.error(methodName, "Token non correttamente formattato. " + e.toString(), e);
			return null;
		}

		LOG.trace(methodName, () -> "Caricato marcatore IRIDE: " + identita);
		ComonlThreadLocalContainer.IDENTITA.set(identita);

		// utente non presente nella base dati
//		Utente utente = utenteDad.getUtenteByCf(identita.getCodFiscale()).orElse(null);
//		if(utente == null) {
//			LOG.error(methodName, "Utente non censito");
//			return null;
//		}
		Utente utente = new Utente();
		utente.setCodiceFiscale(identita.getCodFiscale());

		return utente;
	}

	/**
	 * Normalizzazione del token
	 * 
	 * @param token il token
	 * @return il token normalizzato
	 */
	private String normalizeToken(String token) {
		return token;
	}

	/**
	 * Controlla se l'URI debba essere controllato
	 * 
	 * @param requestURI l'URI da controllare
	 * @return se l'URI debba essere controllato
	 */
	private boolean mustCheckPage(URI requestURI) {
		return requestURI != null;
	}
}
