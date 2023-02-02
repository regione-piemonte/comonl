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

import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreSedeDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetComunicazioneByIdRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetComunicazioneUrgHolderByIdResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSoggettoAbilitato;

/**
 * Retrieves an testataOrdine by its id
 */
public class GetComunicazioneUrgenzaHolderByIdService extends BaseComunicazioneService<GetComunicazioneByIdRequest, GetComunicazioneUrgHolderByIdResponse> {
	
	
	private RapportoDad rapportoDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private ComunicazioneDatoreDad comunicazioneDatoreDad;
	private DatoreSedeDad datoreSedeDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param testataOrdineDad    the testataOrdine DAD
	 */
	public GetComunicazioneUrgenzaHolderByIdService(ConfigurationHelper configurationHelper, ComunicazioneDad comunicazioneDad, 
			RapportoDad rapportoDad, RapportoLavoratoreDad rapportoLavoratoreDad, 
			ComunicazioneDatoreDad comunicazioneDatoreDad, DatoreSedeDad datoreSedeDad) {
		super(configurationHelper, comunicazioneDad);
		this.rapportoDad = rapportoDad;
		this.rapportoLavoratoreDad = rapportoLavoratoreDad;
		this.comunicazioneDatoreDad = comunicazioneDatoreDad;
		this.datoreSedeDad = datoreSedeDad;
	}

	@Override
	protected void checkServiceParams() {
		checkNotNull(request.getId(), "id");
	}

	@Override
	protected void execute() {
		ComunicazioneUrgHolder comunicazioneUrgHolderResponse = new ComunicazioneUrgHolder();
		Comunicazione comunicazione = isEntityPresent(()-> comunicazioneDad.getComunicazione(request.getId()), "comunicazione");
		Rapporto rapporto = rapportoDad.getRapportoByIdComunicazione(comunicazione.getId());
		comunicazioneUrgHolderResponse.setComunicazione(comunicazione);
		comunicazioneUrgHolderResponse.setRapporto(rapporto);
		Lavoratore lavoratore;
		if(rapporto != null) {
			lavoratore = rapportoLavoratoreDad.getLavoratoreByIdRapporto(rapporto.getId());
			comunicazioneUrgHolderResponse.setLavoratore(lavoratore);
		}
		Datore datore = comunicazioneDatoreDad.getDatoreByIdComunicazione(comunicazione.getId());
		comunicazioneUrgHolderResponse.setDatore(datore);
		if(datore != null) {
			SedeLavoro sedeLavoro = datoreSedeDad.getSedeByIdDatore(datore.getId());
			if(sedeLavoro != null) {
				Comune comune = sedeLavoro.getComune();
				comunicazioneUrgHolderResponse.setComune(comune);
			}
		}
		
		TipoSoggettoAbilitato tipoSoggettoAbilitato = comunicazione.getTipoSoggettoAbilitato();
		if(tipoSoggettoAbilitato != null) {
			comunicazioneUrgHolderResponse.setFlgComPerDatore("N");
		}else {
			comunicazioneUrgHolderResponse.setFlgComPerDatore("S");
		}
		
		response.setComunicazioneUrgHolder(comunicazioneUrgHolderResponse);  
	}

	

}
