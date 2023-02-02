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

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SoggettoAbilitatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostAnagraficaDelegatoService
 */
public class PutSoggettoAbilitatoService
		extends BaseSoggettoAbilitatoService<PutSoggettoAbilitatoRequest, PutSoggettoAbilitatoResponse> {
	
	private DecodificaDad decodificaDad;
	
	private SoggettoAbilitato soggettoAbilitato;
	 
	
	public PutSoggettoAbilitatoService(ConfigurationHelper configurationHelper, SoggettoAbilitatoDad soggettoAbilitatoDad,DecodificaDad decodificaDad) {
		super(configurationHelper, soggettoAbilitatoDad);
		this.decodificaDad = decodificaDad;
	}
	
	 @Override
		protected void checkServiceParams() {
		 soggettoAbilitato = request.getSoggettoAbilitato();
			checkNotNull(soggettoAbilitato, "soggettoAbilitato", true);
	    }
	
	@Override
	protected void execute() {
		
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		
		Comune comune = soggettoAbilitato.getComune();
		if(comune !=null && comune.getId() == null) {
			checkComuneValidity(comune,apiErrors,MsgComonl.COMCOME1857.getError(),MsgComonl.COMCOME1858.getError());
		}
		
		if(apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
			return;
		}
		
		soggettoAbilitato = soggettoAbilitatoDad.updateSoggettoAbilitato(soggettoAbilitato);
		response.setSoggettoAbilitato(soggettoAbilitato);
	}
	
	private void checkComuneValidity(Comune comune,List<ApiError> apiErrors,ApiError validita,ApiError obbligatorieta) {

		if (comune == null || comune.getCodComuneMin() == null || "".equalsIgnoreCase(comune.getCodComuneMin())
				|| comune.getDsComTComune() == null || "".equalsIgnoreCase(comune.getDsComTComune())) {
			apiErrors.add(obbligatorieta);
			return;
		}
		Long idComune = recuperaIdComune(comune.getCodComuneMin(), comune.getDsComTComune());
		if (idComune == null) {
			apiErrors.add(validita);
			return;
		}
		comune.setId(idComune);

	}

	private Long recuperaIdComune(String codice, String descrizione) {

		Long id = null;

		DecodificaGenerica filtro = new DecodificaGenerica();
		filtro.setCodDecodifica(codice);
		filtro.setDsDecodifica(descrizione);
		List<DecodificaGenerica> decode = decodificaDad.getComuneDecodifica(filtro);
		if (decode != null && decode.size() == 1) {
			id = decode.get(0).getIdDecodifica();
		}
		return id;
	}

}
