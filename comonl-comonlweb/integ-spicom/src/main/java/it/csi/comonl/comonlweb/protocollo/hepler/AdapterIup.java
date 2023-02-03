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
package it.csi.comonl.comonlweb.protocollo.hepler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import it.csi.comonl.comonlweb.lib.exception.IupIntegrationException;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.comonl.comonlweb.spicom.AppConstants;
import it.csi.iup.iupsv.cxfclient.ArrayOfXsdAnyType;
import it.csi.iup.iupsv.cxfclient.DatiLoginProtocolloDTO;
import it.csi.iup.iupsv.cxfclient.DatiProtocollazioneDTO;
import it.csi.iup.iupsv.cxfclient.FiltroRicercaSoggettiDTO;
import it.csi.iup.iupsv.cxfclient.IupsvSrv;
import it.csi.iup.iupsv.cxfclient.IupsvSrvService;
import it.csi.iup.iupsv.cxfclient.ProtocolloDTO;
import it.csi.iup.iupsv.cxfclient.SoggettoDTO;
import it.csi.iup.iupsv.cxfclient.UtenteProtocolloDTO;

/**
 * @author GTesi
 *
 */
public class AdapterIup {
	
	
	private static final String FILE_PROP_NAME = "/iupconfig.properties";
	protected final LogUtil log = new LogUtil(getClass());

	private static AdapterIup instance;
	
	private static final String COD_APPLICATIVO = "COMONL";
	private static final String IN_ARRIVO = "A";
	private static final String PERSONA_GIURIDICA = "G";
	private static final String REGIONE_PIEMONTE = "rp";
	private static final String MITT_DEST_INTERNI_AOO = "1";

	private static IupsvSrv srv = null;

	protected static final String execution_environment = "execution_environment";
	protected static final String local_environment = "local_environment";

	public static AdapterIup getInstance() {
		if(instance == null) {
			instance = new AdapterIup();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	public Properties staccaProtocollo(Properties p)  throws Exception {
		Properties ret = new Properties();
		
		DatiProtocollazioneDTO datiProtocollazione = new DatiProtocollazioneDTO();
		datiProtocollazione.setTipoProtocollo(IN_ARRIVO);
		ProtocolloDTO prot = new ProtocolloDTO();
		
		try {
			
			// *****************************************************************
            log.info("[ProtocollazioneHelper::staccaProtocollo] START","");
            
			// dati utente
			DatiLoginProtocolloDTO datiLoginProtocollo = new DatiLoginProtocolloDTO();
			datiLoginProtocollo.setLoginProtocollo(COD_APPLICATIVO);
			datiLoginProtocollo.setIdProvincia(p.getProperty("provincia"));

			String abilitazioneProtocollazione = p.getProperty("abilitazione");
			log.info("[ProtocollazioneHelper::staccaProtocollo]"," abilitazione protocollazione = " + abilitazioneProtocollazione);
			if ("S".equalsIgnoreCase(abilitazioneProtocollazione)) {
				
				log.info("[ProtocollazioneHelper::staccaProtocollo]"," protocollazione abilitata!");
				
				datiLoginProtocollo.setLoginProtocollo(p.getProperty("codiceUtente"));
				datiLoginProtocollo.setPasswordProtocollo(null);
				datiLoginProtocollo.setIdAOO(p.getProperty("idAOO"));
				datiLoginProtocollo.setIdEnte(p.getProperty("idEnte"));
				datiLoginProtocollo.setDenominazioneAOO(p.getProperty("denominazioneAOO"));
				datiLoginProtocollo.setIdApplicativo(COD_APPLICATIVO);
				

				UtenteProtocolloDTO utente;
				utente = getService().getDatiUtenteProtocollo(datiLoginProtocollo);
				
				// *****************************************************************
	            log.info("[ProtocollazioneHelper::staccaProtocollo]","staccaProtocollo, 2, dopo getDatiUtenteProtocollo");
				// dati soggetti
				FiltroRicercaSoggettiDTO filtro = new FiltroRicercaSoggettiDTO();
				filtro.setTipoSoggetto(PERSONA_GIURIDICA);
				filtro.setCodFiscale(p.getProperty("codFiscale"));

				ArrayOfXsdAnyType soggettiX = getService().ricercaSoggetti(utente, filtro);
				List soggetti = soggettiX.getItem();
				
				// *****************************************************************
	            log.info("[ProtocollazioneHelper::staccaProtocollo]", "staccaProtocollo, 3, dopo ricercaSoggetti");
				// dati soggetto / insert soggetto
				SoggettoDTO soggettoDTO = new SoggettoDTO();
				soggettoDTO.setTipoSoggetto(PERSONA_GIURIDICA);
				soggettoDTO.setCodFiscale(p.getProperty("codFiscale"));
				soggettoDTO.setPartitaIVA(p.getProperty("partitaIVA"));
				soggettoDTO.setDenominazione(p.getProperty("denominazione"));
				soggettoDTO.setIndirizzo(p.getProperty("indirizzo"));
				soggettoDTO.setCapComune(p.getProperty("capComune"));

				if(soggetti == null || soggetti.size() == 0) {
					soggettoDTO = getService().creaSoggetto(utente, soggettoDTO);
					
		            log.info("[ProtocollazioneHelper::staccaProtocollo]", "staccaProtocollo, 3.5, dopo creaSoggetto");
				}
				else {
					soggettoDTO = (SoggettoDTO) soggetti.get(0);
				}
				
				// *****************************************************************
	            log.info("[ProtocollazioneHelper::staccaProtocollo]", "staccaProtocollo, 4");
				// protocollazione
				datiProtocollazione.setOggetto(p.getProperty("oggetto"));
				datiProtocollazione.setMittenteEsterno(soggettoDTO);
				datiProtocollazione.setTipoDestinatarioInterno(MITT_DEST_INTERNI_AOO);
				datiProtocollazione.setTipoDocumento(""); 
				
				// classificazione
				datiProtocollazione.setIndiceClassificazionePrincipale(p.getProperty("indiceClassificazionePrincipale"));
				
				prot = protocolla(utente, datiProtocollazione);
				
	            log.info("[ProtocollazioneHelper::staccaProtocollo]", "staccaProtocollo, 5, dopo protocolla");

			} else {
				
				log.warn("[ProtocollazioneHelper::staccaProtocollo]",  "protocollazione disabilitata!");
				prot.setNumeroProtocollo(p.getProperty("numeroProtocolloFinto"));
			}
			
		} catch (Exception e) {
            log.error("ProtocollazioneHelper.staccaProtocollo, ERRORE Exception", e);
            throw e;
        }
		
		log.debug("[ProtocollazioneHelper::staccaProtocollo]", " result = " + prot.getNumeroProtocollo());
		log.info("[ProtocollazioneHelper::staccaProtocollo] END", "");
		ret.setProperty("numero", prot.getNumeroProtocollo());
		String anno = null;
		if(prot.getDataProtocollo() != null) {
			anno = Integer.toString(prot.getDataProtocollo().getYear());
			
			ret.setProperty("dataProtocollo", Format.convertiDataInStringa(prot.getDataProtocollo().toGregorianCalendar().getTime()));
		} else {
			Date oggi = new Date();
			Calendar dataProt = GregorianCalendar.getInstance();
			dataProt.setTime(oggi);
			
			anno = Integer.toString(dataProt.get(Calendar.YEAR));
			ret.setProperty("dataProtocollo", Format.convertiDataInStringa(oggi));
		}
		
		ret.setProperty("anno", anno);
		
		return ret;
	}
	
	
	private ProtocolloDTO protocolla(UtenteProtocolloDTO utenteProtocolloInput, DatiProtocollazioneDTO datiProtocollazioneInput) 
	      throws IupIntegrationException, RemoteException {

		    log.info("[IupFactory::protocolla] START ", "");
	
		    it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(AppConstants.PROSPETTO_DISABILI);
		    watcher.start();
	
		    ProtocolloDTO result = null;
		    try {
		    	result = getService().protocolla(utenteProtocolloInput, datiProtocollazioneInput);
		      
		    } catch (Throwable e) {
		      log.error("[IupFactory::protocolla] Exception ", e);
		      throw new RemoteException("Errore generico : " + e.getMessage(), e);
		    
		    } finally {
		      watcher.stop();
		      watcher.dumpElapsed(
		          "IupFactory", 
		          "protocolla()", 
		          "invocazione servizio [IupsvSoapBindingStub]::[protocolla]", 
		          "(valore input omesso)");
	
		      log.debug("[IupFactory::protocolla] utenteProtocollo = ", utenteProtocolloInput);
		      log.info("[IupFactory::protocolla] END ", "");
		    }
	
		    return result;
		}
	
	  
		private IupsvSrv getService() throws MalformedURLException  {
			log.info("[IupFactory::getService] START ", "");
			
			if(srv == null) {
				
				Properties prop = new Properties();
				InputStream fileprop = this.getClass().getResourceAsStream(FILE_PROP_NAME);
				try {
					prop.load(fileprop);
				} catch (IOException e) {
					return null;
				}
				
					QName SERVICE_NAME = new QName("urn:iupsv", "IupsvSrvService");
					
					try {
					IupsvSrv.class.getClassLoader().getResource("iup.iupsv.wsdl").toURI();
	
					URL wsdlURL = IupsvSrv.class.getClassLoader().getResource("iup.iupsv.wsdl").toURI().toURL();
	
					IupsvSrvService ss = new IupsvSrvService(wsdlURL, SERVICE_NAME);
					srv = ss.getIupsv();
					((BindingProvider)srv).getRequestContext()
				    .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, prop.getProperty("iupsrv.url"));
					
				} catch(Exception e) {
					log.error("[IupFactory::getService] ERRORE ", e);
				}
	
				log.debug("[IupFactory::getService] service = ", "");

			}
			log.info("[IupFactory::getService] END ", "");
			
			return srv;
		}

}
