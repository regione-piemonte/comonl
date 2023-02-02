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
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoAllCfImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoAllCfImpresaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoAllCfImpresaRes;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;




/**
 * Retrieves an testataOrdine by its id
 */
public class SetDataAnnullamentoAllCfImpresaService
		extends BaseDelegatoImpresaService<SetDataAnnullamentoAllCfImpresaRequest, SetDataAnnullamentoAllCfImpresaResponse> {
	
	private DelegaDad delegaDad;
	private String cfImpresa;
	private boolean flgAutorizzazione;
	private boolean flgAnullamento;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public SetDataAnnullamentoAllCfImpresaService(ConfigurationHelper configurationHelper, DelegatoImpresaDad delegatoImpresaDad,DelegaDad delegaDad) {
		super(configurationHelper, delegatoImpresaDad);
		this.delegaDad = delegaDad;
	}

	@Override
	protected void checkServiceParams() {
		cfImpresa= request.getCfImpresa();
		checkNotNull(cfImpresa, "cfImpresa");
		flgAutorizzazione= request.getFlgAutorizzazione();
		checkNotNull(flgAutorizzazione, "flgAutorizzazione");
		flgAnullamento= request.getFlgAnnullamento();
		checkNotNull(flgAnullamento, "flgAnullamento");
		
	}

	@Override
	protected void execute() {
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		
		SetDataAnnullamentoAllCfImpresaRes res = new SetDataAnnullamentoAllCfImpresaRes();
		
		if(flgAnullamento) {
			//sto annullando i DelegatoImpresa
			List<DelegatoImpresa> delegatoImpresa = delegatoImpresaDad.getDelegatoImpresaByCfImpresaValidOrNotValid(cfImpresa,true);
			if(!flgAutorizzazione) {
				if(delegatoImpresa != null && delegatoImpresa.size() > 0) {
					String warning ="Attenzione:lo studio/azienda risulta associata a "
												+delegatoImpresa.size()+" consulente/ delegato/persona autorizzata  . Annullare anche il consulente/ delegato/persona autorizzata?";
					res.setWarning(warning);
					response.setSetDataAnnullamentoAllCfImpresaRes(res);
					return;
				}
			}else {
				List<DelegatoImpresa> delegatoImpresaAnnullati = new ArrayList<>();
				for(DelegatoImpresa di: delegatoImpresa) {
					di.setDataAnnullamento(new Date());
					delegatoImpresaAnnullati.add(delegatoImpresaDad.updateDelegatoImpresa(di));
				}
				
				res.setDelegatoImpresas(delegatoImpresaAnnullati);
				response.setSetDataAnnullamentoAllCfImpresaRes(res);
			}
			
		}else {
			List<DelegatoImpresa> delegatoImpresa = delegatoImpresaDad.getDelegatoImpresaByCfImpresaValidOrNotValid(cfImpresa,false);
			List<DelegatoImpresa> delegatoImpresaRipristinati = new ArrayList<>();
			for(DelegatoImpresa di: delegatoImpresa) {
				di.setDataAnnullamento(null);
				delegatoImpresaRipristinati.add(delegatoImpresaDad.updateDelegatoImpresa(di));
			}
			res.setDelegatoImpresas(delegatoImpresaRipristinati);
			response.setSetDataAnnullamentoAllCfImpresaRes(res);
		}
		
		
		
	}

}
