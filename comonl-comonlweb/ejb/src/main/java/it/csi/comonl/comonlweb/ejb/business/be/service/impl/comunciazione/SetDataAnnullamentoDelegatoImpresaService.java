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
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;




/**
 * Retrieves an testataOrdine by its id
 */
public class SetDataAnnullamentoDelegatoImpresaService
		extends BaseDelegatoImpresaService<SetDataAnnullamentoDelegatoImpresaRequest, SetDataAnnullamentoDelegatoImpresaResponse> {
	
	private DelegaDad delegaDad;
	
	private String cfDelegato;
	private String tipoAnagrafica;
	private String cfImpresa;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public SetDataAnnullamentoDelegatoImpresaService(ConfigurationHelper configurationHelper, DelegatoImpresaDad delegatoImpresaDad,DelegaDad delegaDad) {
		super(configurationHelper, delegatoImpresaDad);
		this.delegaDad = delegaDad;
	}

	@Override
	protected void checkServiceParams() {
		cfDelegato= request.getCfDelegato();
		checkNotNull(cfDelegato, "cfDelegato");
		tipoAnagrafica= request.getTipoAnagrafica();
		checkNotNull(tipoAnagrafica, "tipoAnagrafica");
		cfImpresa= request.getCfImpresa();
		checkNotNull(cfImpresa, "cfImpresa");
		
	}

	@Override
	protected void execute() {
		DelegatoImpresa delegatoImpresa = delegatoImpresaDad.getDelegatoImpresaById(cfDelegato, tipoAnagrafica, cfImpresa);
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		
		List<ApiError> apiWarnings = new ArrayList<ApiError>();
		
		Date dataAnnullamento = delegatoImpresa.getDataAnnullamento();
		
		if(dataAnnullamento == null) {
			//sto annullando 
			if(tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)) {
				
				if(delegaDad.isPresenteDelegaValida(cfDelegato, cfImpresa)) {
					apiErrors.add(MsgComonl.COMCOME1023.getError());
					response.setApiErrors(apiErrors);
					return;
				}
			}
			Date sysDate =new Date();
			delegatoImpresa.setDataAnnullamento(sysDate);
		}else {
			//sto ripristinando
			if(tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)) {
				
				if(delegaDad.isPresenteDelegaRevocata(cfDelegato, cfImpresa)) {
					apiWarnings.add(MsgComonl.COMCOMW1024.getError());
					response.setApiErrors(apiWarnings);
				}
			}
			
			delegatoImpresa.setDataAnnullamento(null);
		}
		
		
		delegatoImpresa = delegatoImpresaDad.updateDelegatoImpresa(delegatoImpresa);
		
		response.setDataCancellazione(delegatoImpresa.getDataAnnullamento());
		
	}

}
