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
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoCDERequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoCDEResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;



/**
 * Retrieves an testataOrdine by its id
 */
public class SetDataAnnullamentoCDEService
		extends BaseAnagraficaDelegatoService<SetDataAnnullamentoCDERequest, SetDataAnnullamentoCDEResponse> {
	
	private String cfDelegato;
	private String tipoAnagrafica;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public SetDataAnnullamentoCDEService(ConfigurationHelper configurationHelper, AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, anagraficaDelegatoDad);
	}

	@Override
	protected void checkServiceParams() {
		cfDelegato= request.getCfDelegato();
		checkNotNull(cfDelegato, "cfDelegato");
		tipoAnagrafica= request.getTipoAnagrafica();
		checkNotNull(tipoAnagrafica, "tipoAnagrafica");
		
	}

	@Override
	protected void execute() {
		AnagraficaDelegato anagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato, tipoAnagrafica);
		
		List<ApiError> apiError = new ArrayList<ApiError>();
		if(anagraficaDelegato == null) {
			apiError.add(MsgComonl.COMCOME0012.getError());
			response.setApiErrors(apiError);
			return;
		}
		
		String tipoAnagrafica = anagraficaDelegato.getId().getTipoAnagrafica();
		
		if( !tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C) && 
			!tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D) &&
			!tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E)
		) {
			apiError.add(MsgComonl.COMCOME0013.getError());
			response.setApiErrors(apiError);
			return;
		}
		
		Date dataAnnullamento = anagraficaDelegato.getDataAnnullamento();
		
		if(dataAnnullamento != null) {
			//ripristino
			
			if (tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C)
					|| tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_DELEGATO_D)) {

				anagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(cfDelegato,tipoAnagrafica);
				if (anagraficaDelegato != null &&  anagraficaDelegato.getDataAnnullamento() == null) {
					
					apiError.add(MsgComonl.COMCOME1027.getError("tipo",tipoAnagrafica.equals(ComonlConstants.TIPOLOGIA_ANAGRAFICA_CONSULENTE_C) ? "Consulente" : "Delegato"));
					response.setApiErrors(apiError);
					return;
				}
			}
			
			dataAnnullamento = null;
			anagraficaDelegato.setDataAnnullamento(dataAnnullamento);
		}else {
			//annullamento
			dataAnnullamento = new Date();
			anagraficaDelegato.setDataAnnullamento(dataAnnullamento);
		}
		
		anagraficaDelegato = anagraficaDelegatoDad.updateAnagraficaDelegato(anagraficaDelegato);
		
		response.setDataCancellazione(dataAnnullamento);
	}

}
