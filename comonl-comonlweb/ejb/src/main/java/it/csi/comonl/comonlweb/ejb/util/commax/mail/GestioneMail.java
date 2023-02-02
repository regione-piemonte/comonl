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
package it.csi.comonl.comonlweb.ejb.util.commax.mail;

import java.io.File;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.util.filesystem.FileSystemUtil;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;


// Classe per l'invio delle email
public class GestioneMail {
	

	protected final LogUtil log = new LogUtil(GestioneMail.class);
	// Parametri per l'invio dell'email
	private String serverName = null;
	private String port = null;
	private String userId = null;
	private String password = null;
	private String from = null;
	private String to = null;
	private String subject = null;
	private String pathFileRapporto = null;
	private String destinatarioBCC = null;
	private String fileNameElaborati = null;
	private String fileNameScarti = null;
	private String attachment_file_name = null;
	private String body = null; 
	private GeneratoreRapportoElabHelper report = null;
	private boolean verifica = false;
	
	private ComunicazioneDad comunicazioneDad = null;
	private UplDocumenti uplDoc = null;

	// Costruttore di default
	@SuppressWarnings("unchecked")
	public GestioneMail(ConfigurationHelper configurationHelper, CommonDad commonDad, ComunicazioneDad comunicazioneDad, UplDocumenti uplDoc) {

	    if (report == null) {
	    	report = new GeneratoreRapportoElabHelper();
	    }
		this.comunicazioneDad = comunicazioneDad;
		this.uplDoc = uplDoc;
		
		Personalizzazione personalizzazione = commonDad.getPersonalizzazioneByPv(uplDoc.getPv());
		
		if(configurationHelper.getProperty(ConfigurationValue.APPLICATION_RUNTIME).equalsIgnoreCase("DEV")) {
			serverName = configurationHelper.getProperty(ConfigurationValue.MAIL_SMTP_HOST);
			userId = configurationHelper.getProperty(ConfigurationValue.MAIL_USERNAME);
			password = configurationHelper.getProperty(ConfigurationValue.MAIL_PASSWORD);
			port = configurationHelper.getProperty(ConfigurationValue.MAIL_SMTP_PORT);
			
		} else {
			serverName = commonDad.getParametroCommaxById(ParametriConstants.MAIL_SERVER).getValoreParametro();
			userId = personalizzazione.getUserMailName();
			password = personalizzazione.getUserMailPsw();
		}
		
		from = commonDad.getParametroCommaxById(ParametriConstants.MAIL_MITTENTE).getValoreParametro();
		
		pathFileRapporto = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM_EMAIL_ALLEGATI);
		pathFileRapporto = FileSystemUtil.getCommaxBaseDir(pathFileRapporto).getAbsolutePath();
	
		destinatarioBCC = commonDad.getParametroCommaxById(ParametriConstants.MAIL_IN_BCC).getValoreParametro();		
		to = uplDoc.getMittenteEmail();

		fileNameElaborati = "elaborati" + uplDoc.getId().toString() + ".txt"; 
		fileNameScarti = "scarti" + uplDoc.getId().toString() + ".txt";
		
		if (UtilConstant.FLAG_S.equalsIgnoreCase(uplDoc.getFlgVerifica())) {
			subject = UtilConstant.SUBJECT_MAIL_VERIFICA + uplDoc.getNomeFileUpload();
			verifica = true;
		} else {
			subject = UtilConstant.SUBJECT_MAIL_ELABORAZIONE + uplDoc.getNomeFileUpload();
			verifica = false;
		}
	}

	private boolean isInit() {
		if (serverName == null || userId == null || password == null || from == null || to == null || subject == null)
			return false;
		else
			return true;
	}

	// Invia l'email
	public void inviaMail() throws Exception {

		// Verifica che i parametri dell'email siano validi
		if (!isInit())
			throw new Exception("Parametri e-mail non corretti.");
		
		serverName = serverName.trim();
		userId = userId.trim();
		password = password.trim();

		// Instaura una sessione con il server
		Properties props = new Properties();
		props.put("mail.smtp.host", serverName);
		if(port != null) {
			port = port.trim();
			props.put("mail.smtp.port", port);
		}
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new AuthenticatorHelper(userId, password));
		try {
			// Crea il messaggio da inviare
			MimeMessage message = new MimeMessage(session);

			// Setta mittente e destinatario
			try {
				message.setFrom(new InternetAddress(from));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setRecipient(Message.RecipientType.BCC, new InternetAddress(destinatarioBCC));
			} catch (MessagingException e) {
				throw new Exception("Indirizzo email non corretto:" + e.getMessage());
			}

			// Setta il titolo, la data ed il contenuto
			message.setSubject(subject);
			message.setSentDate(Calendar.getInstance().getTime());
			preparaMail();

			Multipart multipart = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(body);
			multipart.addBodyPart(mbp1);
		  	
			// Gestione attachments
			MimeBodyPart mbp = new MimeBodyPart();
			File file = null;
			try {
				 file = new File(attachment_file_name);
			} catch (NullPointerException e) {

			}
			if (file!=null && !file.canRead()) {
				log.debug("inviaMail", "NON sono riuscito a trovare il file di attachment: "+ attachment_file_name);
				throw new Exception("Cannot find file: " + file);
			}
			
			if(file!=null){
				FileDataSource fds = new FileDataSource(file);
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(file.getName());
				multipart.addBodyPart(mbp);
			}

			message.setContent(multipart);
			
			try {
				Transport.send(message);
			} catch (SendFailedException e) {
				throw new Exception("Impossibile inviare l'email: " + e);
			}
			
		} catch (MessagingException e) {
			throw new Exception("Impossibile creare l'email: " + e);
		}
	}

	
	private void preparaMail() throws Exception {
		creaReport();
		creabody();
	}


	private void creabody() {
		if(verifica){
	         body = "ATTENZIONE: LA FUNZIONE DI VERIFICA HA LO SCOPO DI VERIFICARE IL FILE E NON PREVEDE L'INOLTRO DELLO STESSO AL MINISTERO\n" +
      		"\n"+
      		"Si informa che e' stata completata la verifica del file in oggetto, con il seguente esito: \n" + "\n" +
     	    "- numero comunicazioni presenti all'interno del file: " + report.getTotaleElaborazioni() + "\n" +
            "- numero comunicazioni verificate con errori: " + report.getTotaleScarti() + "\n" + "\n";
	         
	         if(!report.getTotaleScarti().equals("0")){
	        	 body = body +  "Si fornisce in allegato il report con il dettaglio dell'esito dell'elaborazione. " + "\n" + "\n"; 
	         }
	         body = body + 	"Sistema automatico SIL Piemonte" + "\n" + "\n" +
             "N.B. Questo e' un messaggio inviato automaticamente. Si prega di non rispondere a questa email." ;
			
		}
		else{
         body = "Buongiorno\n" +
         		"\n"+
         		"Si informa che e' stata completata l'elaborazione del file in oggetto, con il seguente esito: \n" + "\n" +
        	    "- numero comunicazioni presenti all'interno del file: " + report.getTotaleElaborazioni() + "\n" +
                "- numero comunicazioni acquisite: " + report.getTotaleElaborazioniOK()+ "\n" +
                "- numero comunicazioni scartate (da inviare nuovamente, a seguito di correzione): " + report.getTotaleScarti() + "\n" + "\n" +
         		"Si fornisce in allegato il report con il dettaglio dell'esito dell'elaborazione. " + "\n" + "\n" +
         		"Sistema automatico SIL Piemonte" + "\n" + "\n" +
                "N.B. Questo e' un messaggio inviato automaticamente. Si prega di non rispondere a questa email." ;
		}
                
		
	}	
	private void creaReport() throws Exception {
		
		File file = report.generaFileRapporto(uplDoc.getId(), pathFileRapporto, fileNameElaborati, fileNameScarti, comunicazioneDad);
		if(file != null){
			attachment_file_name = report.generaFileRapporto(uplDoc.getId(), pathFileRapporto, fileNameElaborati, fileNameScarti, comunicazioneDad).getAbsolutePath();
		}
	}
	
}
