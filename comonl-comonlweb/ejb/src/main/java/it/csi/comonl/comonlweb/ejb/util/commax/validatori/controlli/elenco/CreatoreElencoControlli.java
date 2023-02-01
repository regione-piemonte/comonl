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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.elenco;


import java.util.ArrayList;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstratto;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.ControlloAstrattoBloccante;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegolaBloccoCompetenzaAzienda;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegolaBloccoTipoTracciatoSconosciuto;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegoleValidazioneAziendaUtilizzatrice_US;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegoleValidazioneDatoreDiLavoro;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegoleValidazioneDatoreDiLavoroPrecedente_VD;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegoleValidazioneDatoreLavoroDomestico;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.azienda.RegoleValidazioneVariazioneOTipoTrasf_VD;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.datiinvio.RegoleValidazioneDatiInvio;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.lavoratore.RegoleValidazioneLavoratore;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.lavoratore.RegoleValidazioneLavoratoreCoobliggato_UL;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.missione.RegoleValidazioneMissione_US;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.missione.RegoleValidazioneVariazione;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.assunzione.RegoleValidazioneRapportoLavoroAssunzione;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.cessazione.RegoleValidazioneRapportoLavoroCessazione;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.proroga.RegoleValidazioneRapportoLavoroProroga;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.tirocinio.RegoleValidazioneRapportoLavoroTirocinio;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.trasformazione.RegoleValidazioneRapportoLavoroTrasformazione;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.unidom.RegolaBloccoUnidom;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.rapportolavoro.urgenza.RegoleValidazioneUrgenza;
import it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli.vardatori.RegoleValidazioneRapportoLavoroVardatori_VD;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.util.TUConstants;

public class CreatoreElencoControlli {
	
	
	public static ArrayList<ControlloAstratto> creaElencoControlli(Long idUplDocumenti) throws Exception
	{
		ArrayList<ControlloAstratto> a=new ArrayList<ControlloAstratto>();
		RegoleValidazioneAziendaUtilizzatrice_US controlloAziUtilizz = new RegoleValidazioneAziendaUtilizzatrice_US();
		RegoleValidazioneDatoreDiLavoro controlloDatoreLavoro = new RegoleValidazioneDatoreDiLavoro();
		RegoleValidazioneDatoreDiLavoroPrecedente_VD controlloDatoreLavoroPrecedente = new RegoleValidazioneDatoreDiLavoroPrecedente_VD();
		RegoleValidazioneVariazioneOTipoTrasf_VD controlloVD = new RegoleValidazioneVariazioneOTipoTrasf_VD();
		RegoleValidazioneDatiInvio controlloDatiInvio = new RegoleValidazioneDatiInvio(idUplDocumenti);
		RegoleValidazioneLavoratore controlloLavoratore = new RegoleValidazioneLavoratore();
		RegoleValidazioneLavoratoreCoobliggato_UL controlloLavoratoreCoobbligato = new RegoleValidazioneLavoratoreCoobliggato_UL();
		RegoleValidazioneMissione_US controlloMissione = new RegoleValidazioneMissione_US();
		RegoleValidazioneRapportoLavoroAssunzione controlloAssunzione = new RegoleValidazioneRapportoLavoroAssunzione();
		RegoleValidazioneRapportoLavoroCessazione controlloCessazione = new RegoleValidazioneRapportoLavoroCessazione();
		RegoleValidazioneRapportoLavoroProroga controlloProroga = new RegoleValidazioneRapportoLavoroProroga();
		RegoleValidazioneRapportoLavoroTrasformazione controlloTrasformazione = new RegoleValidazioneRapportoLavoroTrasformazione();
		RegoleValidazioneRapportoLavoroVardatori_VD controlloVDRappLavoro = new RegoleValidazioneRapportoLavoroVardatori_VD();
		RegoleValidazioneDatoreLavoroDomestico controlloDatoreLavoroDomestico = new RegoleValidazioneDatoreLavoroDomestico();
		RegoleValidazioneVariazione controlloVariazione = new RegoleValidazioneVariazione();
		RegoleValidazioneRapportoLavoroTirocinio controlloTirocinio= new RegoleValidazioneRapportoLavoroTirocinio();
		a.add(controlloAziUtilizz);
		a.add(controlloDatoreLavoro);
		a.add(controlloDatoreLavoroPrecedente);
		a.add(controlloVD);
		a.add(controlloDatiInvio);
		a.add(controlloLavoratore);
		a.add(controlloLavoratoreCoobbligato);
		a.add(controlloMissione);
		a.add(controlloAssunzione);
		a.add(controlloCessazione);
		a.add(controlloProroga);
		a.add(controlloTrasformazione);
		a.add(controlloVDRappLavoro);
		a.add(controlloDatoreLavoroDomestico);
		a.add(controlloVariazione);
		a.add(controlloTirocinio);
		return a;	   
	}
	
	public static ControlloAstratto[] creaElencoControlliConInizializzazione(
			Long idReport, 
			String codiceFiscaleAzienda, 
			ComunicazioneTracciatoUnicoDTO comunicazione,
			String nomeFileXML,
			CommonDad commonDad, Ruolo ruolo
			) throws Exception
	{
		
		String tipoSelezionato = comunicazione.getDatiAggiuntivi().getTipoTracciatoXML();
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNIURG)){
			
			RegoleValidazioneUrgenza controlloUrgenza = new RegoleValidazioneUrgenza();
			
			controlloUrgenza.setComunicazione(comunicazione);
			controlloUrgenza.setidReport(idReport);
			controlloUrgenza.setnomeFileXML(nomeFileXML);
			
			ControlloAstratto elenco[] = {controlloUrgenza};
			
			return elenco;
		
		}

		
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNIDOM)){
			
            
            String flgUnidom = commonDad.getParametroCommaxById(ParametriConstants.IS_UNIDOM_ABILITATO).getValoreParametro();			
            
            if((UtilConstant.FLAG_N).equalsIgnoreCase(flgUnidom)){
            	RegolaBloccoUnidom bloccoUnidom = new RegolaBloccoUnidom();

            	bloccoUnidom.setComunicazione(comunicazione);
            	bloccoUnidom.setidReport(idReport);
            	bloccoUnidom.setnomeFileXML(nomeFileXML);

            	ControlloAstratto elenco[] = {bloccoUnidom};

            	return elenco;
            }		
		}
		
		String codiceFiscaleDatore = null;
		
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_VARDATORI)){

			if(comunicazione.getTrasferimentoRamoAziendale() != null){
				codiceFiscaleDatore = comunicazione.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale().getCodFiscale();
			}
			else{

				comunicazione.getVariazioneRagioneSociale().getDatoreLavoroAttuale().getCodFiscale();
			}
		}
		else{
			codiceFiscaleDatore = comunicazione.getDatoreDiLavoro().getCodFiscale();
		}

		if(!ruolo.isAmministratore() &&
				!ruolo.isConsulenteRespo() && 
				!ruolo.isOperatoreProvinciale() &&
				codiceFiscaleAzienda != null && !codiceFiscaleAzienda.equalsIgnoreCase(codiceFiscaleDatore)){
			RegolaBloccoCompetenzaAzienda bloccoCompetenzaAzienda = new RegolaBloccoCompetenzaAzienda();
			
			bloccoCompetenzaAzienda.setComunicazione(comunicazione);
			bloccoCompetenzaAzienda.setidReport(idReport);
			bloccoCompetenzaAzienda.setnomeFileXML(nomeFileXML);

        	ControlloAstratto elenco[] = {bloccoCompetenzaAzienda};

        	return elenco;
		}
		
		
		//***********************************
		
		if(comunicazione.getDatoreDiLavoro()!= null &&	
				comunicazione.getLavoratore()!= null && 
				comunicazione.getDatiInvio()!= null){


			if (comunicazione.getDatiInvio().getTipoComunicazione().equalsIgnoreCase("05")){

				if (!(comunicazione.getDatiInizio()!=null &&
						comunicazione.getProroga()==null &&
						comunicazione.getTrasformazione()==null &&
						comunicazione.getCessazione()==null)){

					RegolaBloccoTipoTracciatoSconosciuto bloccoTipoTracciatoSconosciuto = new RegolaBloccoTipoTracciatoSconosciuto();

					bloccoTipoTracciatoSconosciuto.setComunicazione(comunicazione);
					bloccoTipoTracciatoSconosciuto.setidReport(idReport);
					bloccoTipoTracciatoSconosciuto.setnomeFileXML(nomeFileXML);

					ControlloAstratto elenco[] = {bloccoTipoTracciatoSconosciuto};

					return elenco;

				}
			}
		}
	
		//************************************
		
		ArrayList a=creaElencoControlli(idReport);
		ControlloAstratto elenco[]=(ControlloAstratto[])a.toArray(new ControlloAstratto[0]);
		
		// Istanza della classe dove tutti gli esiti memorizzano i loro errori
		
		for (int i = 0; i < elenco.length; i++) {
			elenco[i].setComunicazione(comunicazione);
			elenco[i].setidReport(idReport);
			
			//Nel caso del bloccante devo anche impostare il nome del file XML
			if (elenco[i] instanceof ControlloAstrattoBloccante)
			{
			  ((ControlloAstrattoBloccante)elenco[i]).setnomeFileXML(nomeFileXML);
			}
		}
		
		return elenco;
	}
			
}
