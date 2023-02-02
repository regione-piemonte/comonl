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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.SessionContext;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseInvioASistemiEsterniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.UploadComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.commax.dto.RaggruppamentoDati;
import it.csi.comonl.comonlweb.ejb.util.commax.manager.ElaborazioneCommaxHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class EseguiFaseInvioASistemiEsterniService
extends BaseComunicazioneService<EseguiFaseInvioASistemiEsterniRequest, UploadComunicazioniResponse> {

	private CommonDad commonDad;
	private DecodificaDad decodificaDad;

	private Ruolo ruolo;
	private UplDocumenti uploadDocumenti;
	private ComunicazioneDad comunicazioneDad;
	private AnagraficaDelegatoDad anagraficaDelegatoDad; 
	private ElaborazioneCommaxHelper elaborazionehelper = new ElaborazioneCommaxHelper();
	private SessionContext sessionContext;
	private DecodificaTipoDad decodificaTipoDad;

	public EseguiFaseInvioASistemiEsterniService(SessionContext sessionContext, ConfigurationHelper configurationHelper, 
			Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad, 
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad, DecodificaTipoDad decodificaTipoDad) {

		super(configurationHelper, comunicazioneDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.ruolo = ruolo; 
		this.uploadDocumenti = uploadDocumenti;
		this.comunicazioneDad = comunicazioneDad;
		this.sessionContext = sessionContext;
		this.decodificaTipoDad = decodificaTipoDad;

	}

	@Override
	protected void execute() {
		RaggruppamentoDati dati[]=new RaggruppamentoDati[0];
		UplDocumenti uplDoc = null;

		try{

			uplDoc = comunicazioneDad.getUplDocumentiById(uploadDocumenti.getId());
			if(uplDoc==null)
			{
				// Sicurezza  Mi serve per il passo dopo se è nullo meglio intercettarlo ora che dopo
				throw new Exception("upldoc nullo... mi fermo !");
			}
			// Determino se ho già elaborato questa fase.
			if (!uplDoc.getStatoElaborazione().equals(UtilConstant.STATO_ELABORAZIONE_REGISTRATE))
			{
				log.debug("eseguiFaseInvioASistemiEsterni", "Mi aspetto l'elaborazione nello stato R ed invece ho '"+ uplDoc.getStatoElaborazione()+ "'. Salto questo passo");
				return;
			}
			// Mi ricavo l'elenco delle comunicazioni valide
			List<DepCommaxComunic> elencoComunicazioniValide=comunicazioneDad.getDepCommaxComunicaByIdUplDocumenti(uploadDocumenti.getId());
			//			Debug.print("Devo effettuare l'invio per '"+elencoComunicazioniValide.length+"' valide");
			// Mi ricavo l'elenco dei depositi basandomi sulle comunicazioni valide
			List<DepositoCommax> elementi = comunicazioneDad.estraiDepositiDelleComunicazioniValide(uploadDocumenti.getId());
			ComunicazioneTracciatoUnicoDTO listaOggettoni[];
			if (elementi.size()==0)
			{
				//bypass per spicom. Se non ho comunicazioni non chiamo
				listaOggettoni=new ComunicazioneTracciatoUnicoDTO[0];
			}
			else
			{
				listaOggettoni=elaborazionehelper.estraiOggettoneDaElencoDepositiCommax(elementi);
			}
			dati=RaggruppamentoDati.creaRaggruppamentoDatiAPartireDaListeDiElementi(elencoComunicazioniValide,elementi,listaOggettoni);


		}catch(Exception e){
			log.error("EseguiFaseInvioASistemiEsterniService::execute", e.getMessage());   //  Fino a qui se ho errore non importa tanto non ho scritto niente sul DB.
		}

		/**** FASE CRITICA  NON DEVO ANDARE IN EXCEPTION PER EVITARE DI AVERE ROLLBACK SUL DB***/

		boolean okSuTutteLeComunicazioni=true;   // Usato per aggiornare lo stato da R ad I

		// Aggiunto il numero progressivo della comunicazione, nel caso che il file uploadato sia uno zip.
		Long progressivoComunicazione=null;

		try   // Questa fase assolutamente non mandare eccezioni onde evitare rollback
		{
			for (int k=0;k<dati.length;k++)
			{
				RaggruppamentoDati datoCorrente=dati[k];

				// Chiamo l'altro bean in modo da aprire la transazione.
				boolean esitoElaborazioneComunicazione  = inviaComunicazioneCorrente(ruolo, uplDoc, datoCorrente, decodificaDad,  comunicazioneDad, commonDad, anagraficaDelegatoDad);
				// In ogni caso ho la  transazione del serviceFacadeBean chiusa e quindi isolata 

				okSuTutteLeComunicazioni= okSuTutteLeComunicazioni & esitoElaborazioneComunicazione;  // Anche solo una non fa andare avanti lo stato

				progressivoComunicazione = Long.valueOf(k+1);
				sessionContext.getUserTransaction().begin();
				elaborazionehelper.inserisciEsitoInElaboratiOK(uplDoc, datoCorrente , progressivoComunicazione, comunicazioneDad);
				sessionContext.getUserTransaction().commit();;

			}
			if (okSuTutteLeComunicazioni)
			{
				// Faccio una chiamata al bean per isolare la transazione. Se andrà male qualcosa dopo non mi perdo questa update
				
				uplDoc.setStatoElaborazione(UtilConstant.STATO_ELABORAZIONE_INVIATE);
				sessionContext.getUserTransaction().begin();
				comunicazioneDad.updateStatoUploadDocumenti(uplDoc);
				sessionContext.getUserTransaction().commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try {
				sessionContext.getUserTransaction().rollback();
			} catch (Exception ex) {
				System.out.println("eseguiFaseInvioASistemiEsterni - Errore transazione " + ex.getMessage());
				ex.printStackTrace();
			}
			log.debug("eseguiFaseInvioASistemiEsterni", "Ho una eccezione durante la fase di invio ... Mi fermo qui sarà necessario rielaborare l'upload con "+ uploadDocumenti.getId() );
		}
	}

	private boolean inviaComunicazioneCorrente(Ruolo ruolo, UplDocumenti uplDoc,  RaggruppamentoDati datoCorrente, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad, CommonDad commonDad, AnagraficaDelegatoDad anagraficaDelegatoDad) throws Exception {
		log.debug("inviaComunicazioneCorrente", "Elaboro la comunicazione  con id depositoCommax="+datoCorrente.getDepositoCommax().getId().getIdDepositoCommax() + " e id Comunicazione=" + datoCorrente.getComRDepCommaxComunic().getComunicazione().getId());

		Comunicazione co = comunicazioneDad.getComunicazioneById(datoCorrente.getComRDepCommaxComunic().getComunicazione().getId());

		/****
		 * ATTENZIONE PER LA MANUTENZIONE: Quando mettete mano a questo metodo siate prudenti sulla gestione delle eccezioni!!!
		 * Le letture possono andare in eccezione Se per caso si e' chiamato SPICOM o protocollo non bisogna assolutamente
		 * sollevare eccezioni in quanto genererebbe una rollback con perdita dei codici restituiti dagli altri sistemi 
		 */

		/*** *********************************************************
		 *                   PROTOCOLLO
		 *** *********************************************************/
		try {
			Properties protocollo = new Properties();
			if (elaborazionehelper.isComunicazioneGiaProtocollata(datoCorrente.getComRDepCommaxComunic())==false)  {  // La devo ancora protocollare 

				protocollo=elaborazionehelper.protocolla(co, decodificaDad) ;
				if (protocollo != null) {
					co.setNumProtCom(Long.parseLong(protocollo.getProperty("numero")));
					co.setAnnoProtCom(Long.parseLong(protocollo.getProperty("anno")));
					co.setDtProtocollo(Format.creaJavaUtilDate(protocollo.getProperty("dataProtocollo")));

					datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setNumProtocolloProvinciale(String.valueOf(co.getNumProtCom()));
					datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setAnnoProtocolloProvinciale(co.getAnnoProtCom());
					datoCorrente.getComunicazioneTracciatoUnicoDTO().getDatiAggiuntivi().getDatiAggiuntiviComunicazione().setDataProtocolloProvinciale(co.getDtProtocollo());

				} else {
					log.debug("inviaComunicazioneCorrente", "NON SONO RIUSCITO A PROTOCOLLARE QUESTA COMUNICAZIONE.... PASSO ALLA PROSSIMA!");
				}
				
				if (co.getDtTimbroPostale() == null) {
					co.setDtTimbroPostale(new Date());
				}
				StatoComunicazione statoComunicazione = new StatoComunicazione();
				statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_TRANSITO_ID);
				co.setStatoComunicazione(statoComunicazione);
				co.setDtInvio(new Date());
				
				sessionContext.getUserTransaction().begin();
				elaborazionehelper.aggiornaCheComunicazioneProtocollata(datoCorrente.getComRDepCommaxComunic(), comunicazioneDad, co);
				sessionContext.getUserTransaction().commit();

			} else  {
				protocollo.setProperty("numero", String.valueOf(co.getNumProtCom()));
				protocollo.setProperty("anno", String.valueOf(co.getAnnoProtCom()));
				protocollo.setProperty("dataProtocollo", DateUtil.convertiDataInStringa(co.getDtProtocollo()));
			}
			/*** *********************************************************
			 *                   SPICOM
			 *** **********************************************************/
			if ((elaborazionehelper.isUploadGiaInviatoASpicom(datoCorrente.getComRDepCommaxComunic())==false)) { // Devo inviare a SPICOM
				String codiceComunicazioneReg = null;

				codiceComunicazioneReg = elaborazionehelper.agganciaProtocolloEdInviaComunicazione(ruolo, 
						datoCorrente,	
						protocollo,
						uplDoc, 
						commonDad,
						anagraficaDelegatoDad, 
						co.getDatoreAttuale().getSedeLegale().getComune().getProvincia().getDsTarga());

				if (codiceComunicazioneReg == null) {
					log.debug("inviaComunicazioneCorrente", "NON SONO RIUSCITO AD INVIARE A SPICOM.... PASSO ALLA PROSSIMA!");
				} else {

					co.setCodiceComunicazioneReg(codiceComunicazioneReg);
					StatoComunicazione statoComunicazione = new StatoComunicazione();
					statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
					co.setStatoComunicazione(statoComunicazione);
					co.setDtInvioMinistero(new Date());
					co.setFlgInvioMinistero("S");
					sessionContext.getUserTransaction().begin();
					co = comunicazioneDad.updateComunicazione(co);
					elaborazionehelper.aggiornaInviatoASpicom(datoCorrente.getComRDepCommaxComunic(),co, comunicazioneDad);
					sessionContext.getUserTransaction().commit();
					
					// CREAZIONE PDF E SALVATAGGIO SU FILESYSTEM
					try {
						Comunicazione comunicazioneCompleta = comunicazioneDad.getComunicazioneById(co.getId());
						String basePath = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM_PDF);
						String htmlContent = new StampaComunicazioneByIdService(configurationHelper, comunicazioneDad,decodificaTipoDad, anagraficaDelegatoDad).makePdf(comunicazioneCompleta, ruolo.isOperatoreProvinciale());
						ReportUtilities.makePdfFile(basePath, htmlContent, comunicazioneCompleta.getId().longValue());
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
						System.out.println("StampaComunicazioneByIdService idComunicazione: " + co.getId().longValue() + " - fine");
					}
				}

			}
			else
			{
				log.debug("inviaComunicazioneCorrente", "Ho rilevato che ho gia' inviato a SPICOM con codice regionale "+ datoCorrente.getDepositoCommax().getCodiceComunicazioneReg() + " ... non invio piu'");

			}
		} catch (Exception e) {
			try {
				sessionContext.getUserTransaction().rollback();
				return false;
			} catch (Exception ex) {
				System.out.println("inviaComunicazioneCorrente - Errore transazione " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return true;
	}
}
