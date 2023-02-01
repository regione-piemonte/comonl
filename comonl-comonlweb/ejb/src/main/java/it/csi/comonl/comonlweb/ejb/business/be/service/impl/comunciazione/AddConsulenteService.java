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

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.AddConsulenteRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.AddConsulenteResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetDelegatoImpresaByIdAnagraficaDelegatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;



/**
 * Retrieves an testataOrdine by its id
 */
public class AddConsulenteService
		extends BaseAnagraficaDelegatoService<AddConsulenteRequest, AddConsulenteResponse> {
	
	private String cfDelegato;
	private Long idSoggettoAbilitato;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public AddConsulenteService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfDelegato = request.getCfDelegato();
		idSoggettoAbilitato = request.getIdSoggettoAbilitato();
		checkNotNull(cfDelegato, "cfDelegato");
		checkNotNull(idSoggettoAbilitato, "idSoggettoAbilitato");
	}

	@Override
	protected void execute() {
	
		AnagraficaDelegato anagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato, ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C);
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		
		if(anagraficaDelegato == null) {
			apiErrors.add(MsgComonl.COMCOME0009.getError());
			response.setApiErrors(apiErrors);
			return;
			
		}
		SoggettoAbilitato soggettoAbilitato = anagraficaDelegato.getSoggettoAbilitato();
		
		
		if(soggettoAbilitato != null) {
			Long id = soggettoAbilitato.getId();
			if(id.equals(idSoggettoAbilitato)) {
				apiErrors.add(MsgComonl.COMCOME0011.getError());
			}else {
				apiErrors.add(MsgComonl.COMCOME0010.getError());
			}
			
			//Se mi dice si 
			/*
			SoggettoAbilitato sa =new SoggettoAbilitato();
			sa.setId(idSoggettoAbilitato);
			anagraficaDelegato.setSoggettoAbilitato(soggettoAbilitato);*/
			//update
			
			response.setApiErrors(apiErrors);
			return;
		}
		
		soggettoAbilitato = new SoggettoAbilitato();
		soggettoAbilitato.setId(idSoggettoAbilitato);
		
		anagraficaDelegato.setSoggettoAbilitato(soggettoAbilitato);
		anagraficaDelegato = anagraficaDelegatoDad.updateAnagraficaDelegato(anagraficaDelegato);
		
		response.setAnagraficaDelegato(anagraficaDelegato);
	}

}
