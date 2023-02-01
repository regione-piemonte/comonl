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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.csi.comonl.comonlweb.ejb.business.be.controlli.Validator;
import it.csi.comonl.comonlweb.ejb.business.be.controlli.ValidatorDelega;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAggiornaDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TipoDelega;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;

/**
 * PostRicercaProspetto
 */
public class PostAggiornaDelegaService
		extends BaseDelegaService<PostAggiornaDelegaRequest, PostDelegaResponse> {

	private Delega delega;
	private ControlliDad controlliDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostAggiornaDelegaService(ConfigurationHelper configurationHelper, DelegaDad delegaDad, DecodificaDad decodificaDad, ControlliDad controlliDad) {
		super(configurationHelper, delegaDad, decodificaDad);
		this.controlliDad = controlliDad;
	}

	@Override
	protected void checkServiceParams() {
		delega = request.getDelega();
		checkNotNull(delega, "delega", true);
	}

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");
		
		Validator<Delega> validator = new ValidatorDelega(delega,controlliDad);
		if (!validator.isOk())
		{
			response.setApiErrors(validator.getApiErrors());
			return;
		}

		if (delega.getComDelegato() != null && delega.getComDelegato().getId() != null) {
			
			
			// CAMPO NOT NULL
			delega.setComuneDelegato(delega.getComDelegato().getDsComTComune());
			
			if (delega.getComDelegato().getProvincia() != null)
				// CAMPO NOT NULL
				delega.setPvDelegato(delega.getComDelegato().getProvincia().getDsTarga());
			
			Comune comune = new Comune();
			// CAMPO NOT NULL
			comune.setId(delega.getComDelegato().getId());
			delega.setComDelegato(comune);
		}
		
		
		
		if (delega.getComDelegante() != null && delega.getComDelegante().getId() != null) {
			
			// CAMPO NOT NULL
			delega.setComuneDelegante(delega.getComDelegante().getDsComTComune());
			
			if (delega.getComDelegante().getProvincia() != null)
				// CAMPO NOT NULL
				delega.setPvDelegante(delega.getComDelegante().getProvincia().getDsTarga());
			
			Comune comune = new Comune();
			// CAMPO NOT NULL
			comune.setId(delega.getComDelegante().getId());
			delega.setComDelegante(comune);
		}
		
		if (delega.getStatiEsteriDelegato() != null && delega.getStatiEsteriDelegato().getId() != null) {
			
			delega.setComuneDelegato(delega.getStatiEsteriDelegato().getDsStatiEsteri());
			delega.setPvDelegato("EX");
			
			StatiEsteri statiEsteri = new StatiEsteri();
			statiEsteri.setId(delega.getStatiEsteriDelegato().getId());
			delega.setStatiEsteriDelegato(statiEsteri);
		}
		if (delega.getStatiEsteriDelegante() != null && delega.getStatiEsteriDelegante().getId() != null) {
			
			delega.setComuneDelegato(delega.getStatiEsteriDelegante().getDsStatiEsteri());
			delega.setPvDelegato("EX");
			
			
			StatiEsteri statiEsteri = new StatiEsteri();
			statiEsteri.setId(delega.getStatiEsteriDelegante().getId());
			delega.setStatiEsteriDelegante(statiEsteri);
		}

		
		if (delega.getPersonalizzazione() != null && delega.getPersonalizzazione().getId() != null) {
			Personalizzazione personalizzazione = new Personalizzazione();
			personalizzazione.setId(delega.getPersonalizzazione().getId());
			delega.setPersonalizzazione(personalizzazione);
		}
		if (delega.getStatoDelega() != null && delega.getStatoDelega().getId() != null) {
			StatoDelega statoDelega = new StatoDelega();
			statoDelega.setId(delega.getStatoDelega().getId());
			delega.setStatoDelega(statoDelega);
		}
		else delega.setStatoDelega(null);
		
		if (delega.getTipoDelega() != null && delega.getTipoDelega().getId() != null) {
			TipoDelega tipoDelega = new TipoDelega();
			tipoDelega.setId(delega.getTipoDelega().getId());
			delega.setTipoDelega(tipoDelega);
		}
		else {
			TipoDelega tipoDelega = new TipoDelega();
			tipoDelega.setId("1");
			delega.setTipoDelega(tipoDelega);
		}
				
	
		if (delega.getId() != null && delega.getId().longValue()>0)
			delega = delegaDad.updateDelega(delega);
		else {
			
//			String pv = delega.getPvDelegato();
//			if (pv == null || pv.trim().length()<=0)
//				pv = "XX";
//			
//			Long val = decodificaDad.nextSequenceVal("SEQ_PIEMONTE_" + pv.trim().toUpperCase());
//			delega.setNumeroProtocollo(val);
//			
//			Date today = new Date();
//			Calendar dataProt = GregorianCalendar.getInstance();
//			dataProt.setTime(today);
//			delega.setAnnoProtocollo(new BigDecimal(dataProt.get(Calendar.YEAR)));
//			delega.setDtProtocollo(today);
//			
//			
//			Personalizzazione personalizzazione = new Personalizzazione();
//			personalizzazione.setId(pv);
//			delega.setPersonalizzazione(personalizzazione);
			
			delega = delegaDad.insertDelega(delega);
		}
		response.setDelega(delega);
	}
	

}
