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
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.AddAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.AddAziendaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;



/**
 * Retrieves an testataOrdine by its id
 */
public class AddAziendaService
		extends BaseAnagraficaDelegatoService<AddAziendaRequest, AddAziendaResponse> {
	
	private DelegatoImpresaDad delegatoImpresaDad;
	
	private DelegaDad delegaDad;
	
	private DelegatoImpresa delegatoImpresa;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public AddAziendaService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad,DelegatoImpresaDad delegatoImpresaDad, DelegaDad delegaDad) {
		super(configurationHelper, anagraficaDelegatoDad);
		this.delegatoImpresaDad = delegatoImpresaDad;
		this.delegaDad = delegaDad;
	}

	@Override
	protected void checkServiceParams() {
		delegatoImpresa = request.getDelegatoImpresa();
		checkNotNull(delegatoImpresa, "delegatoImpresa");
	}

	@Override
	protected void execute() {
		List<ApiError> errors = new ArrayList<ApiError>();
		List<ApiError> warnings = new ArrayList<ApiError>();
		String tipoAnagrafica = delegatoImpresa.getId().getTipoAnagrafica();
		String cfImpresa = delegatoImpresa.getId().getCfImpresa();
		String cfDelegato = delegatoImpresa.getId().getCfDelegato();
		
		if(!(tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D) || tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E))) {
			//not valid
			return;
		}
		
		
		
		DelegatoImpresa delegatoImpresaToSearch = delegatoImpresaDad.getDelegatoImpresaById(cfDelegato, tipoAnagrafica, cfImpresa);
		if(delegatoImpresaToSearch != null) {
			errors.add(MsgComonl.COMCOME1026.getError());
			response.setApiErrors(errors);
			return;
		}
		
		
		delegatoImpresa = delegatoImpresaDad.insertDelegatoImpresa(delegatoImpresa);
		if(delegatoImpresa == null) {
			//errore
		}
		
		response.setDelegatoImpresa(delegatoImpresa);
		
		if(tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)) {
			//MSG11 WARNING
			List<Delega> delegas = delegaDad.getDelegaByCfDelegatoCfImpresa(cfDelegato, cfImpresa);
			if(delegas != null && delegas.size() > 0) {
				warnings.add(MsgComonl.COMCOMW1027.getError());
				response.addApiWarnings(warnings);
			}
		}
		
		response.addApiErrors(warnings);
		
	}

}
