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
package it.csi.comonl.comonlweb.ejb.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.comonl.comonlweb.protocollo.hepler.AdapterIup;

public class ProtocollazioneHelper {
	
	private static final LogUtil log = new LogUtil(ProtocollazioneHelper.class);
	public static final String NUM_PROTOCOLLO_XX = "0";
	public static final String NUM_PROTOCOLLO_FINTO = "99999999";
	public static final String PROVINCIA_XX = "XX";
	public static final String SISTEMA_SEQ = "N";
	public static final String SISTEMA_IUP = "I";
	
	public static Properties protocolla(Comunicazione comunicazioneDB, DecodificaDad decodificaDad) {

		String provinciaProtocollazione = null;
		Datore datoreAttuale = comunicazioneDB.getDatoreAttuale();
		if (datoreAttuale.getSedeOperativa() != null) {
			if (inPiemonte(datoreAttuale.getSedeOperativa())) {
				provinciaProtocollazione = datoreAttuale.getSedeOperativa().getComune().getProvincia().getDsTarga();
			}
		}
		
		if (provinciaProtocollazione != null) {
			Personalizzazione personalizzazione = decodificaDad.getPersonalizzazioneByPv(provinciaProtocollazione);
			
			// CASO PROVINCIA FUORI PIEMONTE (NON DOVREBBE SUCCEDERE)
			if(personalizzazione == null) {
				log.info("[ProtocollazioneHelper::protocolla]", "Non ci sono personalizzazioni per la provincia" 
						+ provinciaProtocollazione + " verra' usato il protocollo previsto per XX");
				personalizzazione = decodificaDad.getPersonalizzazioneByPv(PROVINCIA_XX);
				if(personalizzazione.getTipoProtocollo() == null) {
					Properties ret = new Properties();
					ret.setProperty("numero", NUM_PROTOCOLLO_XX);
					Date oggi = new Date();
					Calendar dataProt = GregorianCalendar.getInstance();
					dataProt.setTime(oggi);
					
					String anno = Integer.toString(dataProt.get(Calendar.YEAR));
					ret.setProperty("dataProtocollo", Format.convertiDataInStringa(oggi));
					ret.setProperty("anno", anno);
					return ret;
				}		
			}

			// SE IL SISTEMA NON  C'E' OCCORRE USARE LA SEQUENCE
			if(personalizzazione.getTipoProtocollo().equals(SISTEMA_SEQ)) {
				Properties ret = new Properties();

				Long numProt = decodificaDad.getProtocolloDaSequence(provinciaProtocollazione);
				ret.setProperty("numero", numProt.toString());
	
				Date oggi = new Date();
				Calendar dataProt = GregorianCalendar.getInstance();
				dataProt.setTime(oggi);
				
				String anno = Integer.toString(dataProt.get(Calendar.YEAR));
				ret.setProperty("dataProtocollo", Format.convertiDataInStringa(oggi));
				ret.setProperty("anno", anno);
				
				return ret;
				
			// SE IL SISTEMA E'IUP
			} else if (personalizzazione.getTipoProtocollo().equals(SISTEMA_IUP)) {
				AdapterIup iup = AdapterIup.getInstance();
				if (iup == null) {
					log.error("[ProtocollazioneHelper::protocolla]", "ERRORE nella protocollazione");
					return null;
				}
				
				Properties properties = new Properties();
				properties.setProperty("provincia", provinciaProtocollazione);
				properties.setProperty("codiceUtente", personalizzazione.getCUtente());
				properties.setProperty("idAOO", personalizzazione.getIdAaoDestinatario());
				properties.setProperty("denominazioneAOO", personalizzazione.getDenDestinatario()==null? personalizzazione.getIdAaoDestinatario() : personalizzazione.getDenDestinatario());
				properties.setProperty("idEnte", personalizzazione.getIdEnte());
				properties.setProperty("abilitazione", "S");
				properties.setProperty("indiceClassificazionePrincipale", "");
				properties.setProperty("numeroProtocolloFinto", NUM_PROTOCOLLO_XX);
		
				String cfAzienda = comunicazioneDB.getCfImpresa();
				if (cfAzienda == null)
					cfAzienda = comunicazioneDB.getDatoreAttuale() != null ? comunicazioneDB.getDatoreAttuale().getCodiceFiscale() :"";
				properties.setProperty("codFiscale", cfAzienda);
				properties.setProperty("partitaIVA", cfAzienda != null && cfAzienda.length() == 11 ? cfAzienda : "");
				if (datoreAttuale != null) {
					if (datoreAttuale.getDsDenominazioneDatore() != null)
						properties.setProperty("denominazione", datoreAttuale.getDsDenominazioneDatore());
					if (datoreAttuale.getSedeLegale() != null && datoreAttuale.getSedeLegale().getIndirizzo() != null)
						properties.setProperty("indirizzo", datoreAttuale.getSedeLegale().getIndirizzo());
					if (datoreAttuale.getSedeLegale() != null && datoreAttuale.getSedeLegale().getCodCap() != null)
						properties.setProperty("capComune", datoreAttuale.getSedeLegale().getCodCap());
				}
		
				StringBuffer oggetto = new StringBuffer("Comunicazione per l'impresa: ");
				oggetto.append(datoreAttuale.getDsDenominazioneDatore());
				oggetto.append(" alla data del ");
				oggetto.append(Format.getStringDate(comunicazioneDB.getDataRiferimento()));
		
				properties.setProperty("oggetto", oggetto.toString());
				properties.setProperty("tipoDocumento", "");
				
				try {
					return iup.staccaProtocollo(properties);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[ProtocollazioneHelper::protocolla]", "ERRORE nella protocollazione" + e.getMessage());
				}
			} else {
				log.error("[ProtocollazioneHelper::protocolla]", "SISTEMA DI PROTOCOLLAZIONE NON GESTITO");
			}
		} else {
			log.error("[ProtocollazioneHelper::protocolla]", "NESSUNA PROVINCIA PER LA PROTOCOLLAZIONE");			
		}
		
		return null;
	}
	
	private static boolean inPiemonte(SedeLavoro sede) {
		if (sede != null) {
			return (sede.getComune() != null && sede.getComune().getProvincia() != null
					&& sede.getComune().getProvincia().getRegione() != null && ComonlConstants.CODICE_REGIONALE_PIEMONTE
							.equals(sede.getComune().getProvincia().getRegione().getCodRegioneMin()));
		}
		return false;
	}

}
