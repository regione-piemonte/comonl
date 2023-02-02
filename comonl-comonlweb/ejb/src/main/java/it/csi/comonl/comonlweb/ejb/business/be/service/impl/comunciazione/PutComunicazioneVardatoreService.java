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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorFactory;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutComunicazioneRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutComunicazioneVardatoreResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PutComunicazioneService
 */
public class PutComunicazioneVardatoreService
		extends BaseComunicazioneService<PutComunicazioneRequest, PutComunicazioneVardatoreResponse> {
	
	private WrapperComunicazione wrapperComunicazione;
	private DatoreDad datoreDad;
	private SedeLavoroDad sedeLavoroDad;
	private LegaleRapprDad legaleRapprDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoDad rapportoDad;
	private  TutoreDad tutoreDad;
	private  ControlliDad controlliDad;
	private DecodificaDad decodificaDad;
	
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param comunicazioneDad        the DAD for the comunicazione
	 */
	public PutComunicazioneVardatoreService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, DatoreDad datoreDad, SedeLavoroDad sedeLavoroDad,LegaleRapprDad legaleRapprDad,
			RapportoDad rapportoDad, LavoratoreDad lavoratoreDad,TutoreDad tutoreDad, ControlliDad controlliDad, DecodificaDad decodificaDad) {
		super(configurationHelper, comunicazioneDad);
		this.datoreDad = datoreDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.legaleRapprDad = legaleRapprDad;
		this.rapportoDad = rapportoDad;
		this.lavoratoreDad = lavoratoreDad;
		this.tutoreDad = tutoreDad;
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
	}
	
	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkModel(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }
	
	@Override
	protected void execute() {
		log.error("execute----------->", "Entro nel metodo execute");
		List<ApiError> apiWarnings = new ArrayList<ApiError>();
	
		Validator<Comunicazione> validator = ValidatorFactory.getValidator(wrapperComunicazione, comunicazioneDad, controlliDad, decodificaDad);
		if (!validator.isOk()) {
			response.addApiErrors(validator.getApiErrors());
			return;
		}
		
		apiWarnings.addAll(validator.getApiWarnings());
		Comunicazione comunicazione = validator.getControlObject();// oggetto validato e completato per la persistenza
		response.setApiWarnings(apiWarnings);
		
		Comunicazione comunicazioneDB = comunicazioneDad.getComunicazioneById(comunicazione.getId());
		Comunicazione comunicazionePersist = comunicazioneDad.updateComunicazione(comunicazione);
		
		// TODO chiedere: su cdu è scritto: In fase di modifica di una comunicazione in stato “Inserita” i dati del Codice Fiscale non sono modificabili . Questo vuol dire che il datore non è modificabile?
		// DATORE
		Datore datore = datoreDad.updateDatore(comunicazione.getDatoreAttuale());
		
		legaleRapprDad.update(comunicazione.getDatoreAttuale().getLegaleRappr());
		
		//SEDE LEGALE
		if (comunicazione.getDatoreAttuale().getSedeLegale()!=null) {
			comunicazione.getDatoreAttuale().getSedeLegale().setEmail(trim(comunicazione.getDatoreAttuale().getSedeLegale().getEmail()));
		}
		sedeLavoroDad.updateSedeLavoro(comunicazione.getDatoreAttuale().getSedeLegale());
		
		if(StringUtils.isBlank(datore.getDsVariazioneRagSociale()))
		{
			// DATORE PRECEDENTE
			datoreDad.updateDatore(comunicazione.getDatorePrecedente());
			if (comunicazione.getDatorePrecedente().getSedeLegale()!=null) {
				comunicazione.getDatorePrecedente().getSedeLegale().setEmail(trim(comunicazione.getDatorePrecedente().getSedeLegale().getEmail()));
			}			
			sedeLavoroDad.updateSedeLavoro(comunicazione.getDatorePrecedente().getSedeLegale());
			
//			Map<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>> mapIdSilp = 
//					comunicazione.getRapLavSedeVD().stream().collect(Collectors.groupingBy(a-> a.getSedeLavoroVD()));

			Map<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>> mapIdSilp = new HashMap<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>>();
			for (RapportiLavoratoriSediInteressateVD rapp : comunicazione.getRapLavSedeVD()) {
				if (mapIdSilp.containsKey(rapp.getSedeLavoroVD()))
					mapIdSilp.get(rapp.getSedeLavoroVD()).add(rapp);
				else {
					List<RapportiLavoratoriSediInteressateVD> list = new ArrayList<RapportiLavoratoriSediInteressateVD>();
					list.add(rapp);
					mapIdSilp.put(rapp.getSedeLavoroVD(),list );
				}
			}
			System.out.print(mapIdSilp);
			
			List<RapportiLavoratoriSediInteressateVD> rapLavSedeVD_DB =  comunicazioneDB.getRapLavSedeVD();
			Set<SedeLavoro> setSedeLavoroTODELETE = rapLavSedeVD_DB.stream().collect(Collectors.groupingBy(p -> p.getSedeLavoroVD())).keySet();
			setSedeLavoroTODELETE.removeAll(mapIdSilp.keySet());
			
			Set<Long> setLavoratoreTODELETE = rapLavSedeVD_DB.stream().collect(Collectors.groupingBy(p -> p.getLavoratoreVD().getId())).keySet();
			Set<Long> idLavoratoreCLIENT = comunicazione.getRapLavSedeVD().stream().filter(a->a.getLavoratoreVD().getId()!=null).collect(Collectors.groupingBy(a-> a.getLavoratoreVD().getId())).keySet();
			setLavoratoreTODELETE.removeAll(idLavoratoreCLIENT);
			
			Set<Long> setRapportoTODELETE = rapLavSedeVD_DB.stream().collect(Collectors.groupingBy(p -> p.getRapportoVD().getId())).keySet();
			Set<Long> idRapportoCLIENT = comunicazione.getRapLavSedeVD().stream().filter(a->a.getRapportoVD().getId()!=null).collect(Collectors.groupingBy(a-> a.getRapportoVD().getId())).keySet();
			setRapportoTODELETE.removeAll(idRapportoCLIENT);
			
			// Delete del lavoratore , del rapporto , della relazione con la sede
			// Delete della sede e delle relazioni con i lavoratori 
			
			for(Long idLavoratore: setLavoratoreTODELETE) {
				lavoratoreDad.deleteLavoratore(idLavoratore);
			}
			for(Long idRapporto: setRapportoTODELETE) {
				rapportoDad.deleteRapporto(idRapporto); 
			}
			
			for(SedeLavoro sedeLavoro: setSedeLavoroTODELETE) {
				sedeLavoroDad.deleteSedeLavoro(sedeLavoro.getId(),datore.getId());
			}

			for(Map.Entry<SedeLavoro, List<RapportiLavoratoriSediInteressateVD>> entry : mapIdSilp.entrySet()) {
				
				boolean presenteSedeSuDb = rapLavSedeVD_DB.stream().filter(x -> x.getSedeLavoroVD().getIdSedeSilp().compareTo(entry.getKey().getIdSedeSilp())==0).findAny().orElse(null) != null;
				SedeLavoro sedeOperativa = entry.getKey();
				if (!presenteSedeSuDb)
					sedeOperativa = sedeLavoroDad.insertSedeLavoro(entry.getKey(), datore.getId());
				else
					sedeOperativa = sedeLavoroDad.updateSedeLavoro(entry.getKey());
				
				for(RapportiLavoratoriSediInteressateVD value : entry.getValue()) {
					Rapporto rapporto = value.getRapportoVD();
					rapporto.setComunicazione(comunicazionePersist);
					rapporto.setDtEvento(comunicazione.getDtTrasferimentoVarDatori());
					if(rapporto.getId()!=null) {
						rapporto = rapportoDad.updateRapporto(rapporto);
					}else {
						rapporto = rapportoDad.insertRapporto(rapporto);
					}
					
					Lavoratore lavoratore = value.getLavoratoreVD();
					if (lavoratore.getId()!=null) {
						lavoratore.setCodCapRes(trim(lavoratore.getCodCapRes()));
						lavoratore.setCodCapDom(trim(lavoratore.getCodCapDom()));
						lavoratore = lavoratoreDad.updateLavoratore(lavoratore);
						boolean esisteRelLavSede = rapLavSedeVD_DB.stream().filter(a -> a.getLavoratoreVD().getId().equals(value.getLavoratoreVD().getId()) && a.getSedeLavoroVD().getId().equals(entry.getKey().getId())).findAny().orElse(null) != null;
						if (!esisteRelLavSede) {
							lavoratoreDad.insertSedeLavoroLavoratore(lavoratore.getId(), sedeOperativa.getId());
						}
					}else {
						lavoratore = lavoratoreDad.insertLavoratore(lavoratore, rapporto.getId(), ComonlConstants.TIPO_RAPPORTO_AZIENDA_P, ComonlConstants.TIPO_LAVORATORE_P);
						lavoratoreDad.insertSedeLavoroLavoratore(lavoratore.getId(), sedeOperativa.getId());
					}
						
				}
			}
		}
		
		
		comunicazionePersist.setApiWarnings(response.getApiWarnings());
		response.setComunicazione(comunicazionePersist);
	}
}
