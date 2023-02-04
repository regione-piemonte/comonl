/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.web.util.filter.auth.provider.iride;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Identity provider for Shibboleth
 */
public class Identita implements Serializable {
	
	private static final String SLASH = "/";

	private static final int SLASH_CHAR_CODE = 47;

	/** For serialization */
	private static final long serialVersionUID = 8140646703075866441L;

	/** Constant for undefined auth type */
	public static final int AUTENTICAZIONE_USERNAME_PASSWORD_UNVERIFIED = 1;
	/** Constant for username+password auth type */
	public static final int AUTENTICAZIONE_USERNAME_PASSWORD = 2;
	/** Constant for username+password+PIN auth type */
	public static final int AUTENTICAZIONE_USERNAME_PASSWORD_PIN = 4;
	/** Constant for X.509 certificate auth type */
	public static final int AUTENTICAZIONE_CERTIFICATO = 8;
	/** Constant for strong X.509 auth type */
	public static final int AUTENTICAZIONE_CERTIFICATO_FORTE = 16;
	
	private String nome;
	private String cognome;
	private String codFiscale;
	private String idProvider;
	private String timestamp;
	private int livelloAutenticazione;
	private String mac;

	// Helpers for token parsing
	private int tokenParseIndex = -1;
	private String token;

	/**
	 * Empty constructor
	 */
	public Identita() {
		this.codFiscale = null;
		this.nome = null;
		this.cognome = null;
		this.idProvider = null;
		this.timestamp = null;
		this.livelloAutenticazione = 0;
	}

	/**
	 * Full constructor
	 * @param codFiscale the cod fiscale
	 * @param nome the nome
	 * @param cognome the cognome
	 * @param idProvider the id provider
	 * @param timestamp the timestamp
	 * @param livelloAutenticazione the livello autenticazione
	 */
	public Identita(String codFiscale, String nome, String cognome, String idProvider, String timestamp, int livelloAutenticazione) {
		this.codFiscale = codFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.idProvider = idProvider;
		this.timestamp = timestamp;
		this.livelloAutenticazione = livelloAutenticazione;
	}

	/**
	 * Constructor from token
	 * @param token the token
	 * @throws MalformedIdTokenException in case of a malformed token
	 */
	public Identita(String token) throws MalformedIdTokenException {
		this.tokenParseIndex = -1;
		this.token = token;
		
		this.codFiscale = parseSegment();
		this.nome = parseSegment();
		this.cognome = parseSegment();
		this.idProvider = parseSegment();
		this.timestamp = parseSegment();
		this.livelloAutenticazione = Integer.parseInt(parseSegment());
		this.mac = token.substring(tokenParseIndex + 1);
		
		this.tokenParseIndex = -1;
		this.token = null;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the codFiscale
	 */
	public String getCodFiscale() {
		return codFiscale;
	}

	/**
	 * @param codFiscale the codFiscale to set
	 */
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	/**
	 * @return the idProvider
	 */
	public String getIdProvider() {
		return idProvider;
	}

	/**
	 * @param idProvider the idProvider to set
	 */
	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the livelloAutenticazione
	 */
	public int getLivelloAutenticazione() {
		return livelloAutenticazione;
	}

	/**
	 * @param livelloAutenticazione the livelloAutenticazione to set
	 */
	public void setLivelloAutenticazione(int livelloAutenticazione) {
		this.livelloAutenticazione = livelloAutenticazione;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Identita)) {
			return false;
		}
		final Identita other = (Identita) obj;
		
		return new EqualsBuilder()
				.append(this.nome, other.nome)
				.append(this.cognome, other.cognome)
				.append(this.codFiscale, other.codFiscale)
				.append(this.mac, other.mac)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.codFiscale)
				.append(this.nome)
				.append(this.cognome)
				.append(this.mac)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append(this.codFiscale).append(SLASH)
				.append(this.nome).append(SLASH)
				.append(this.cognome).append(SLASH)
				.append(this.idProvider).append(SLASH)
				.append(this.timestamp).append(SLASH)
				.append(this.livelloAutenticazione).append(SLASH)
				.append(this.mac)
				.toString();
	}

	/**
	 * @return the rappresentazione interna
	 */
	public String getRappresentazioneInterna() {
		return new StringBuilder()
				.append(this.codFiscale).append(SLASH)
				.append(this.nome).append(SLASH)
				.append(this.cognome).append(SLASH)
				.append(this.idProvider).append(SLASH)
				.append(this.timestamp).append(SLASH)
				.append(this.livelloAutenticazione)
				.toString();
	}

	/**
	 * Token segment parsing
	 * @return the segment
	 * @throws MalformedIdTokenException in case of a malformed token
	 */
	private String parseSegment() throws MalformedIdTokenException {
		int index = token.indexOf(SLASH_CHAR_CODE, tokenParseIndex + 1);
		if (index == -1) {
			throw new MalformedIdTokenException(token);
		}
		String res = token.substring(tokenParseIndex + 1, index);
		tokenParseIndex = index;
		return res;
	}
}
