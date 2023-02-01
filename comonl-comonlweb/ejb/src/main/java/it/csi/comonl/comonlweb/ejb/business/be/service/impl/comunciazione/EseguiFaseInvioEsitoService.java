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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.util.Date;

import javax.ejb.SessionContext;
import javax.transaction.SystemException;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseInvioEsitoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.UploadComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.commax.mail.GestioneMail;
import it.csi.comonl.comonlweb.ejb.util.commax.manager.ElaborazioneCommaxHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;

public class EseguiFaseInvioEsitoService
extends BaseComunicazioneService<EseguiFaseInvioEsitoRequest, UploadComunicazioniResponse> {

	private CommonDad commonDad;
	
	private SessionContext sessionContext;
	private UplDocumenti uploadDocumenti;
	private ComunicazioneDad comunicazioneDad;
	private ElaborazioneCommaxHelper elaborazionehelper = new ElaborazioneCommaxHelper();
	
	public EseguiFaseInvioEsitoService(SessionContext sessionContext, ConfigurationHelper configurationHelper, 
			UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, 
			  CommonDad commonDad) {

		super(configurationHelper, comunicazioneDad);
		this.commonDad = commonDad;
		this.uploadDocumenti = uploadDocumenti;
		this.comunicazioneDad = comunicazioneDad;
		this.sessionContext = sessionContext;
	}

	@Override
	protected void execute() {
		String destinatarioMail=null; 
		boolean verifica;
		UplDocumenti uplDoc = null;
		try
		{

			uplDoc = comunicazioneDad.getUplDocumentiById(uploadDocumenti.getId());
			if (!uplDoc.getStatoElaborazione().equals(UtilConstant.STATO_ELABORAZIONE_INVIATE)){
				log.debug("eseguiFaseInvioEsito", "Mi aspetto l'elaborazione nello stato I ed invece ho '" + uplDoc.getStatoElaborazione() + "'. Salto questo passo");
				return;
			}
			// Reperisco l'indirizzo email dal primo elemento con indice 0,
			// tanto sono certo che e' sempre lo stesso anche per gli altri elementi
			destinatarioMail = uplDoc.getMittenteEmail();	
			verifica = (UtilConstant.FLAG_S).equalsIgnoreCase(uplDoc.getFlgVerifica()) ? true : false;
		}
		catch (Exception e)
		{
			throw e;   // Non importa se si e' rotto qui ... Riproveremo un'altra volta.
		}


		if (destinatarioMail==null)
		{
			log.debug("eseguiFaseInvioEsito", "Non sono riuscito a reperire i dati del mittente della mail ... esco!");
		}


		try{

			GestioneMail gestioneMail = new GestioneMail(configurationHelper, commonDad, comunicazioneDad, uplDoc);
			gestioneMail.inviaMail();
		}
		catch (Exception e)
		{   
			log.error("eseguiFaseInvioEsito", "NON SONO RIUSCITO AD INVIARE LA MAIL a " + destinatarioMail + " per id_Upl_documenti "+ uploadDocumenti.getId());
			log.debug("eseguiFaseInvioEsito", e.getMessage());
			return ; // Esco tanto non devo memorizzare che ho mandato la mail

		}
		try{
			uplDoc.setStatoElaborazione(UtilConstant.STATO_ELABORAZIONE_NOTIFICATI);
			uplDoc.setDataElab(new Date());
			sessionContext.getUserTransaction().begin();
			comunicazioneDad.updateStatoUploadDocumenti(uplDoc);
			sessionContext.getUserTransaction().commit();
		}catch (Exception e)
		{
			try {
				sessionContext.getUserTransaction().rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {}
			log.debug("eseguiFaseInvioEsito", "***** ATTENZIONE IMPORTANTISSIMO :NON SONO RIUSCITO A MEMORIZZARE CHE HO GIA MANDATO LA MAIL a "+destinatarioMail+ " per id_Upl_documenti "+uploadDocumenti.getId());
			log.debug("eseguiFaseInvioEsito", e.getMessage());
		}
	}
}
