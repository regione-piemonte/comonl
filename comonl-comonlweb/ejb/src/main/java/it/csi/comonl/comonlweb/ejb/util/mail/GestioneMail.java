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
package it.csi.comonl.comonlweb.ejb.util.mail;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

// Classe per l'invio delle email
public class GestioneMail {

	protected final LogUtil log = new LogUtil(getClass());
	// Parametri per l'invio dell'email
	private String serverName = null;
	private String userId = null;
	private String password = null;
	private String from = null;
	private String to = null;
	private String subject = null;
	private String text = null;
	private boolean mailAbilitata = false;

	// Flag di validita dei parametri dell'email
	boolean init = false;

	// Costruttore di default
	@SuppressWarnings("unchecked")
	public GestioneMail(Properties props) {

		// Esamina le proprieta in ingresso
		Enumeration e = props.propertyNames();

		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = props.getProperty(key);

			if (key.equals("mail.serverName"))
				setServerName(value);
			else if (key.equals("mail.userId"))
				setUserId(value);
			else if (key.equals("mail.password"))
				setPassword(value);
			else if (key.equals("mail.from"))
				setFrom(value);
			else if (key.equals("mail.to"))
				setTo(value);
			else if (key.equals("mail.subject"))
				setSubject(value);
			else if (key.equals("mail.text"))
				setText(value);
			else if (key.equals("mail.abilitazione"))
				if ("S".equalsIgnoreCase(value)) {
					mailAbilitata = true;
				}
		}

		// Verifica che i parametri dell'email siano validi
		if (serverName == null || userId == null || password == null || from == null || to == null || subject == null)
			init = false;
		else
			init = true;
	}

	private boolean isInit() {
		if (serverName == null || userId == null || password == null || from == null || to == null || subject == null
				|| text == null)
			return false;
		else
			return true;
	}

	// Invia l'email
	public void inviaMail() throws Exception {

		if (!mailAbilitata) {
			log.info("[GestioneMail::inviaMail]", "mail disabilitata.");
			return;
		}

		// Verifica che i parametri dell'email siano validi
		if (!isInit())
			throw new Exception("Parametri e-mail non corretti.");

		// Instaura una sessione con il server
		Properties props = new Properties();
		props.put("mail.smtp.host", serverName);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new AuthenticatorHelper(userId, password));
		try {
			// Crea il messaggio da inviare
			MimeMessage message = new MimeMessage(session);

			// Setta mittente e destinatario
			try {
				message.setFrom(new InternetAddress(from));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			} catch (MessagingException e) {
				throw new Exception("Indirizzo email non corretto:" + e.getMessage());
			}

			// Setta il titolo, la data ed il contenuto
			message.setSubject(subject);
			message.setSentDate(Calendar.getInstance().getTime());
			message.setText(text);

			// Invia il messaggio
			try {
				Transport.send(message);
			} catch (SendFailedException e) {
				throw new Exception("Impossibile inviare l'email: " + e);
			}
		} catch (MessagingException e) {
			throw new Exception("Impossibile creare l'email: " + e);
		}
	}

	// Metodi di set per le proprieta
	private void setFrom(String from) {
		this.from = from;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	private void setServerName(String serverName) {
		this.serverName = serverName;
	}

	private void setSubject(String subject) {
		this.subject = subject;
	}

	private void setText(String text) {
		this.text = text;
	}

	public void setTo(String to) {
		this.to = to;
	}

	private void setUserId(String userId) {
		this.userId = userId;
	}

	public void preparaMessaggio(String annoProt, String nProt, String codiceRegionale, String dataProt,
			String cfAzienda, String denomAzienda) {

		StringBuffer messaggio = new StringBuffer();

		messaggio.append(subject + "\n");
		messaggio.append("per l'azienda " + cfAzienda + "\n");
		messaggio.append(denomAzienda + "\n");

		messaggio.append("Protocollo del prospetto (anno/numero):" + "\n");
		messaggio.append(annoProt + " / " + nProt + "\n" + "\n");

		messaggio.append("Data di protocollazione:" + "\n");
		messaggio.append(dataProt + "\n" + "\n");

		messaggio.append("Codice regionale:" + "\n");
		if (codiceRegionale != null) {
			messaggio.append(codiceRegionale + "\n" + "\n" + "\n");
		} else {
			messaggio.append("Non presente." + "\n" + "\n" + "\n");
		}

		messaggio.append("Sistema automatico SIL Piemonte" + "\n");

		setText(messaggio.toString());
	}
}
