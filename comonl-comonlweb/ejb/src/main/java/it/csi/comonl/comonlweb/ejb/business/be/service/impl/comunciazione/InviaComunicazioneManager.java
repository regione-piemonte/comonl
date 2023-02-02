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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.ejb.SessionContext;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaTipoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.util.ProtocollazioneHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.ejb.util.mail.MailHelper;
import it.csi.comonl.comonlweb.ejb.util.report.ReportUtilities;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.VariazioneSomm;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.utils.Format;
import it.csi.comonl.comonlweb.spicom.helper.SpicomIntegrator;

public class InviaComunicazioneManager {
	
	private ComunicazioneDad comunicazioneDad;
	private ConfigurationHelper configurationHelper;
	private CommonDad commonDad;
	private DecodificaTipoDad decodificaTipoDad;
	private DecodificaDad decodificaDad;
	private DatoreDad datoreDad;
	private LegaleRapprDad legaleRapprDad;
	private SedeLavoroDad sedeLavoroDad;
	private RapportoDad rapportoDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private TutoreDad tutoreDad;
	private MailHelper mailHelper;
	private SessionContext sessionContext;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public InviaComunicazioneManager(SessionContext sessionContext, ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad,
			DecodificaTipoDad decodificaTipoDad, DecodificaDad decodificaDad, CommonDad commonDad,
			DatoreDad datoreDad,LegaleRapprDad legaleRapprDad,SedeLavoroDad sedeLavoroDad,RapportoDad rapportoDad,LavoratoreDad lavoratoreDad,
			RapportoLavoratoreDad rapportoLavoratoreDad,TutoreDad tutoreDad , MailHelper mailHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		
		this.sessionContext = sessionContext;
		this.comunicazioneDad = comunicazioneDad;
		this.configurationHelper = configurationHelper;
		this.decodificaTipoDad = decodificaTipoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.datoreDad = datoreDad;
		this.legaleRapprDad = legaleRapprDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.tutoreDad = tutoreDad;
		this.mailHelper = mailHelper;
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
	}

	
	public List<Comunicazione> invia(BigDecimal idComunicazione, Ruolo ruolo) {
		// RICERCA COMUNICAZIONE
		Comunicazione comunicazioneDB = comunicazioneDad.getComunicazioneById(idComunicazione.longValue());
		
		if ("S".equalsIgnoreCase(comunicazioneDB.getFlgMultiLav())) {
			
			InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
					comunicazioneDad, datoreDad, legaleRapprDad, sedeLavoroDad, rapportoDad, lavoratoreDad,
					rapportoLavoratoreDad,tutoreDad);
				
			// CREAZIONE COMUNICAZIONI FIGLIE
			List<Lavoratore> lavoratori = comunicazioneDB.getLavoratori();
			List<Comunicazione> comunicazioniFiglie = new ArrayList<Comunicazione>();
			try {
				sessionContext.getUserTransaction().begin();
				for (Lavoratore lavoratore : lavoratori) {
					Comunicazione comunicazioneLav = comunicazioneDad.getComunicazioneById(idComunicazione.longValue());
					List<Lavoratore> lavList = new ArrayList<Lavoratore>();
					lavList.add(lavoratore);
					comunicazioneLav.setLavoratori(lavList);
					comunicazioneLav.setIdRapportoLavoro(null);
					comunicazioneLav.setFlgMultiLav(ComonlConstants.FLAG_N);
					comunicazioneLav.setIdComDComunicazGruppo(idComunicazione.longValue());
					insertComunicazioneService.setDelegatoPerRuolo(comunicazioneLav, ruolo);
					comunicazioneLav = insertComunicazioneService.insertComunicazione(comunicazioneLav);
					comunicazioniFiglie.add(comunicazioneLav);
				}
				
				// CANCELLAZIONE COMUNICAZIONE PRINCIPALE 
				StatoComunicazione statoComunicazione = new StatoComunicazione();
				statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_CANCELLATA_ID);
				comunicazioneDB.setStatoComunicazione(statoComunicazione);
				comunicazioneDad.updateComunicazione(comunicazioneDB);
				
				sessionContext.getUserTransaction().commit();
			
			} catch(Exception e) {
				try {sessionContext.getUserTransaction().rollback();
				} catch (Exception ee) {}
				
				throw new RuntimeException(e);
			}
				
			// INVIA TUTTE LE COMUNICAZIONI MINISTERO
			List<Comunicazione> comunicazioniInviate = new ArrayList<Comunicazione>();
			for (Comunicazione comDaInviare : comunicazioniFiglie)
				comunicazioniInviate.add(invia(idComunicazione, comDaInviare, ruolo));
			
			// MANDA MAIL
			inviaMail(comunicazioneDB, comunicazioniInviate);
						
			// RITORNO LISTA COMUNICAZIONI INVIATE
			return comunicazioniInviate;
			
		}
		else {
			
			// INVIA MINISTERO
			comunicazioneDB = invia(idComunicazione, comunicazioneDB, ruolo);
			
			// MANDA MAIL (solo se la comunicazione ha il codice regionale)
			if(comunicazioneDB.getCodiceComunicazioneReg() != null && comunicazioneDB.getCodiceComunicazioneReg().trim().length() > 0) {
				inviaMail(comunicazioneDB, null);
			}
			
			// RITORNO COMUNICAZIONE COMPLETA
			List<Comunicazione> ret = new ArrayList<Comunicazione>();
			ret.add(comunicazioneDB);
			return ret;
		}
	}


	private Comunicazione invia(BigDecimal idComunicazione, Comunicazione comunicazioneDB, Ruolo ruolo) {
		
		try{

			// PROTOCOLLO
			if (!(comunicazioneDB.getNumProtCom() != null && comunicazioneDB.getNumProtCom()>0)) {
				//Properties protocollo = protocolla(comunicazioneDB);
				Properties protocollo = ProtocollazioneHelper.protocolla(comunicazioneDB, decodificaDad);
				if (protocollo != null) {
					comunicazioneDB.setNumProtCom(Long.parseLong(protocollo.getProperty("numero")));
					comunicazioneDB.setAnnoProtCom(Long.parseLong(protocollo.getProperty("anno")));
					comunicazioneDB.setDtProtocollo(Format.creaJavaUtilDate(protocollo.getProperty("dataProtocollo")));
					if (comunicazioneDB.getDtTimbroPostale() == null) {
						comunicazioneDB.setDtTimbroPostale(new Date());
					}
				}
			}
			StatoComunicazione statoComunicazione = new StatoComunicazione();
			statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_TRANSITO_ID);
			comunicazioneDB.setStatoComunicazione(statoComunicazione);
			comunicazioneDB.setDtInvio(new Date());
	
			// SALVATAGGIO
			sessionContext.getUserTransaction().begin();
			comunicazioneDB = comunicazioneDad.updateComunicazione(comunicazioneDB);
			sessionContext.getUserTransaction().commit();
			comunicazioneDB = comunicazioneDad.getComunicazioneById(comunicazioneDB.getId());
			
			
			// INVIO
			boolean comunicazioneAnnullo =  comunicazioneDB.getTipoComunicazioneTu()!=null &&
					ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN.equalsIgnoreCase(comunicazioneDB.getTipoComunicazioneTu().getCodTipoComunicazioneMin());
			TipoComunicazione tipo = comunicazioneDB.getTipoComunicazione();
			TipoSomministrazione tipoSomm = comunicazioneDB.getTipoSomministrazione();
			VariazioneSomm variazioneSomm = null;
			if (tipoSomm != null)
				variazioneSomm = decodificaTipoDad.getTipoVariazioneBySommComm(tipoSomm.getId(), tipo.getId());
	
			ComonlsParametri comonlsParametri = commonDad
					.getParametroByDescrizione(ComonlConstants.NUMERO_VERSIONE_TRACCIATO_PER_SPICOM);
			
			String codiceComunicazioneReg = SpicomIntegrator.getInstance().inviaComunicazione(comunicazioneDB,
					ruolo, variazioneSomm, comonlsParametri.getValoreParametro());
			
			if(codiceComunicazioneReg == null) {
				throw new RuntimeException("Errore nell\'invio al ministero");
			}
			comunicazioneDB.setCodiceComunicazioneReg(codiceComunicazioneReg);
			statoComunicazione = new StatoComunicazione();
			statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
			comunicazioneDB.setStatoComunicazione(statoComunicazione);
			comunicazioneDB.setDtInvioMinistero(new Date());
			comunicazioneDB.setFlgInvioMinistero("S");
			
			Comunicazione comunicazionePrecedenteAnnullo = null;
			boolean setFlagCurrentRecord = false;
			if (comunicazioneAnnullo) {
				comunicazionePrecedenteAnnullo = getComunicazionePrecedenteAnnullo(comunicazioneDB);
				setFlagCurrentRecord = comunicazionePrecedenteAnnullo!=null && ComonlConstants.STATO_COMUNICAZIONE_ANNULLATA_ID.compareTo(comunicazionePrecedenteAnnullo.getStatoComunicazione().getId())!=0;
			}
			
			if (setFlagCurrentRecord) {
				comunicazioneDB.setFlgCurrentRecord(null);
			}
	
			// SALVATAGGIO
			sessionContext.getUserTransaction().begin();
			comunicazioneDB = comunicazioneDad.updateComunicazione(comunicazioneDB);
			sessionContext.getUserTransaction().commit();
			comunicazioneDB = comunicazioneDad.getComunicazioneById(comunicazioneDB.getId());
			
			// SET  FLG_CURRENT_RECORD COMUNICAZIONE PRECEDENTE ANNULLO
			if (setFlagCurrentRecord) {
				comunicazionePrecedenteAnnullo.setFlgCurrentRecord(ComonlConstants.FLAG_S);
				statoComunicazione = new StatoComunicazione();
				statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID);
				comunicazionePrecedenteAnnullo.setStatoComunicazione(statoComunicazione);
				sessionContext.getUserTransaction().begin();
				comunicazioneDad.updateComunicazione(comunicazionePrecedenteAnnullo);
				sessionContext.getUserTransaction().commit();
			}
	
			// CREAZIONE PDF E SALVATAGGIO SU FILESYSTEM
			try {
				String basePath = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM_PDF);
				String htmlContent = new StampaComunicazioneByIdService(configurationHelper, comunicazioneDad,decodificaTipoDad, anagraficaDelegatoDad).makePdf(comunicazioneDB, ruolo.isOperatoreProvinciale());
				ReportUtilities.makePdfFile(basePath, htmlContent, idComunicazione.longValue());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				System.out.println("StampaComunicazioneByIdService idComunicazione: " + idComunicazione.longValue() + " - fine");
			}
		
		}
		catch(Exception e) {
			e.printStackTrace();
            try {
            	sessionContext.getUserTransaction().rollback();
            } catch (Exception ex) {
            	System.out.println("InviaComunicazioneManager - Errore transazione " + ex.getMessage());
            	ex.printStackTrace();
            }
         }
		
		
		// RITORNO COMUNICAZIONE COMPLETA
		return comunicazioneDB;
	}
	

	private boolean inPiemonte(SedeLavoro sede) {
		if (sede != null) {
			return (sede.getComune() != null && sede.getComune().getProvincia() != null
					&& sede.getComune().getProvincia().getRegione() != null && ComonlConstants.CODICE_REGIONALE_PIEMONTE
							.equals(sede.getComune().getProvincia().getRegione().getCodRegioneMin()));
		}
		return false;
	}
	
	
	private void inviaMail(Comunicazione comunicazioneDB, List<Comunicazione> comunicazioniInviate) {

		String to = comunicazioneDB.getEmail();
		String subject = "Notifica ricezione Comunicazione";
		if (comunicazioneDB.getTipoComunicazione() != null)
			subject = "Notifica ricezione Comunicazione di " + comunicazioneDB.getTipoComunicazione().getDsComTTipoComunicazione();
		
		StringBuilder message = new StringBuilder();
		
		if ("S".equalsIgnoreCase(comunicazioneDB.getFlgMultiLav()) && comunicazioniInviate != null) {
			// COMUNICAZIONE MULTILAVORATORE
			
			message.append("Oggetto:");
			message.append("\n");
			message.append(subject);
			message.append("\n");
			message.append("\n");
			message.append("per l'azienda con CF: " + comunicazioneDB.getCfImpresa());
			message.append("\n");
			message.append("\n");
			message.append("Identificativo (provincia / anno / numero):");
			String prot = comunicazioneDB.getProvincia().getDsComTProvincia() + " / " + comunicazioneDB.getAnnoProtCom() + " / " + comunicazioneDB.getNumProtCom();
			message.append(prot);
			message.append("\n");
			message.append("\n");
			message.append("e per i seguenti lavoratori :");
			
			
			for (Comunicazione comm : comunicazioniInviate) {
				
				String codFiscale = "";
				if (comm.getLavoratori() != null && comm.getLavoratori().size()>0 && comm.getLavoratori().get(0).getCodiceFiscale() != null)
					codFiscale = comm.getLavoratori().get(0).getCodiceFiscale();
				
				message.append("\n");
				message.append("\n");
				message.append("Codice fiscale : " + codFiscale);
				message.append("Data di Protocollazione:");
				message.append(formatDate(comm.getDtTimbroPostale()));
				message.append("ID Regionale:");
				message.append(comm.getCodiceComunicazioneReg());
				
			}

			message.append("\n");

		}
		else {
			// COMUNICAZIONE SINGOLO LAVORATORE
		
			message.append("Oggetto:");
			message.append("\n");
			message.append(subject);
			message.append("\n");
			message.append("\n");
			message.append("per l'azienda con CF: " + comunicazioneDB.getCfImpresa());
			message.append("\n");
			message.append("\n");
			
			String codFiscale = "";
			if (comunicazioneDB.getLavoratori() != null && comunicazioneDB.getLavoratori().size()>0 && comunicazioneDB.getLavoratori().get(0).getCodiceFiscale() != null)
				codFiscale = comunicazioneDB.getLavoratori().get(0).getCodiceFiscale();
			message.append("e per il lavoratore con CF: " + codFiscale);
			message.append("\n");
			message.append("\n");
			message.append("Protocollo (provincia / anno / numero):");
			
			
			
			String provinciaProtocollazione = null;
			Datore datoreAttuale = comunicazioneDB.getDatoreAttuale();
			if (datoreAttuale != null && datoreAttuale.getSedeOperativa() != null && datoreAttuale.getSedeOperativa().getComune() != null && datoreAttuale.getSedeOperativa().getComune().getProvincia() != null) {
				if (inPiemonte(datoreAttuale.getSedeOperativa())) {
					provinciaProtocollazione = datoreAttuale.getSedeOperativa().getComune().getProvincia().getDsTarga();
				}
			}
			else if (datoreAttuale != null && datoreAttuale.getSedeLegale() != null && datoreAttuale.getSedeLegale().getComune() != null && datoreAttuale.getSedeLegale().getComune().getProvincia() != null) {
				if (inPiemonte(datoreAttuale.getSedeLegale())) {
					provinciaProtocollazione = datoreAttuale.getSedeLegale().getComune().getProvincia().getDsTarga();
				}
			}
			if (provinciaProtocollazione == null || (provinciaProtocollazione != null && provinciaProtocollazione.trim().length()<=0)) {
				if (comunicazioneDB.getProvincia() != null && comunicazioneDB.getProvincia().getDsComTProvincia() != null)
					provinciaProtocollazione = comunicazioneDB.getProvincia().getDsComTProvincia();
			}
			
			if (provinciaProtocollazione == null)
				provinciaProtocollazione = "--";
			
			
			String prot = provinciaProtocollazione + " / " + comunicazioneDB.getAnnoProtCom() + " / " + comunicazioneDB.getNumProtCom();
			message.append(prot);
			message.append("\n");
			message.append("\n");
			message.append("Data di Protocollazione:");
			message.append(formatDate(comunicazioneDB.getDtTimbroPostale()));
			message.append("\n");
			message.append("ID Regionale:");
			message.append(comunicazioneDB.getCodiceComunicazioneReg());
			message.append("\n");
			message.append("\n");
			message.append("\n");
			message.append("Sistema automatico SIL Piemonte");
			message.append("\n");
			
		}

		try {
			List<String> toList = new ArrayList<String>();
			toList.add(to);
			mailHelper.send(toList, subject, message.toString(), null);
		} catch (Exception e) {
			System.out.println("[PostInviaComunicazioneService::inviaMail] ERRORE mail server " +  e.getMessage());
		}

		
	}
	
	
	
	private String formatDate(Date date) {
		if (date != null) {
			try {
				return dateFormat.format(date);
			}
			catch (Exception err) {}
		}
		return "";
	}
	
	private Comunicazione getComunicazionePrecedenteAnnullo(Comunicazione comunicazione) {
		if(comunicazione.getIdComDComunicPrecedente()!=null) {
			Optional<Comunicazione> optional = comunicazioneDad.getComunicazione(comunicazione.getIdComDComunicPrecedente());
			Comunicazione comunicazionePrec = optional.isPresent() ? optional.get() : null;
			if (comunicazionePrec != null && 
					comunicazionePrec.getStatoComunicazione() != null && 
							comunicazionePrec.getStatoComunicazione().getId() != null &&
					ComonlConstants.STATO_COMUNICAZIONE_ANNULLATA_ID.compareTo(comunicazionePrec.getStatoComunicazione().getId())==0)
			{
				return getComunicazionePrecedenteAnnullo(comunicazionePrec);
			}
			else 
			{
				comunicazione = comunicazionePrec;
			}
		}
		return comunicazione;
	}
}
