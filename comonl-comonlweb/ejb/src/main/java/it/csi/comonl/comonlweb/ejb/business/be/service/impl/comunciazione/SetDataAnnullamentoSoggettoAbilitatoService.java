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
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SoggettoAbilitatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SetDataAnnullamentoSoggettoAbilitatoRes;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;




/**
 * Retrieves an testataOrdine by its id
 */
public class SetDataAnnullamentoSoggettoAbilitatoService
		extends BaseSoggettoAbilitatoService<SetDataAnnullamentoSoggettoAbilitatoRequest, SetDataAnnullamentoSoggettoAbilitatoResponse> {
	
	private DelegaDad delegaDad;
	private Long idSoggettoAbilitato;
	private Boolean flgAutorizzazione;
	
	private  AnagraficaDelegatoDad anagrafaicaDelegatoDad;
	

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the AnagraficaDelegatoDad DAD
	 */
	public SetDataAnnullamentoSoggettoAbilitatoService(ConfigurationHelper configurationHelper, SoggettoAbilitatoDad soggettoAbilitatoDad, AnagraficaDelegatoDad anagrafaicaDelegatoDad) {
		super(configurationHelper, soggettoAbilitatoDad);
		this.anagrafaicaDelegatoDad = anagrafaicaDelegatoDad;
		
	}

	@Override
	protected void checkServiceParams() {
		idSoggettoAbilitato= request.getIdSoggettoAbilitato();
		checkNotNull(idSoggettoAbilitato, "idSoggettoAbilitato");
		flgAutorizzazione= request.getFlgAutorizzazione();
		checkNotNull(flgAutorizzazione, "flgAutorizzazione");
	}

	@Override
	protected void execute() {
		SoggettoAbilitato soggettoAbilitato = soggettoAbilitatoDad.getById(idSoggettoAbilitato);
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		
		List<ApiError> apiWarnings = new ArrayList<ApiError>();
		
		Date dataAnnullamento = soggettoAbilitato.getDataAnnullamento();
		
		SetDataAnnullamentoSoggettoAbilitatoRes setDataAnnullamentoSoggettoAbilitatoRes = new SetDataAnnullamentoSoggettoAbilitatoRes();
		
		List<AnagraficaDelegato> anagraficaDelegatos = anagrafaicaDelegatoDad.getAnagraficaDelegatoNotCanceledByIdSoggettoAbilitato(idSoggettoAbilitato);
		if(!flgAutorizzazione) {
			/* Non E’ possibile annullare il SoggettoAbilitato se ci sono dei consulenti collegati.
			 * Se anagraficaDelegatos.size() > 0, 
			 * significa che ci sono delle AnagrafichaDelegato collegate e ancora valide(ovvero snagraficaDelegato.soggettoAbilitato != null e snagraficaDelegato.dataAnnullamento == null) 
			 * e non è ancora stata data l'autorizzazione per annullarle(flgAutorizzazione == false); dunque non si annulla nemmeno il SoggettoAbilitato
			 */
			if(anagraficaDelegatos != null && anagraficaDelegatos.size() > 0){
				//warning
				apiWarnings.add(MsgComonl.COMCOMW1201.getError());
				setDataAnnullamentoSoggettoAbilitatoRes.setWarnings(apiWarnings);
				setDataAnnullamentoSoggettoAbilitatoRes.setDataAnnullamento(new Date());
				response.setSetDataAnnullamentoSoggettoAbilitatoRes(setDataAnnullamentoSoggettoAbilitatoRes);
				return;
			}else {
				flgAutorizzazione = true;
			}
		}
		if(flgAutorizzazione){
			
			if(dataAnnullamento == null) {
				//sto annullando 
				if(anagraficaDelegatos != null && anagraficaDelegatos.size() > 0){
					for(AnagraficaDelegato ad: anagraficaDelegatos) {
						ad.setDataAnnullamento(new Date());
						anagrafaicaDelegatoDad.updateAnagraficaDelegato(ad);
					}
				}
				
				
				dataAnnullamento = new Date();
				
			}else {
				//sto ripristinando
				dataAnnullamento = null;
				
			}
			soggettoAbilitato.setDataAnnullamento(dataAnnullamento);
			soggettoAbilitato = soggettoAbilitatoDad.updateSoggettoAbilitato(soggettoAbilitato);
			setDataAnnullamentoSoggettoAbilitatoRes.setDataAnnullamento(soggettoAbilitato.getDataAnnullamento());
			response.setSetDataAnnullamentoSoggettoAbilitatoRes(setDataAnnullamentoSoggettoAbilitatoRes);
			
		}
		
	}

}
