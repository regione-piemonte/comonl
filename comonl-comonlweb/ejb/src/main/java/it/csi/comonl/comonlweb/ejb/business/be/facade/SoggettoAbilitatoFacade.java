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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SoggettoAbilitatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetSoggettoAbilitatoByIdService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetSoggettoAbilitatoBycfSoggettoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostSoggettoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PutSoggettoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.SetDataAnnullamentoDelegatoImpresaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.SetDataAnnullamentoSoggettoAbilitatoService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetSoggettoAbilitatoByIdRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetSoggettoAbilitatoBycfSoggettoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PutSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoSoggettoAbilitatoRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetSoggettoAbilitatoByIdResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetSoggettoAbilitatoBycfSoggettoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PutSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoSoggettoAbilitatoResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SoggettoAbilitato;

@Stateless
public class SoggettoAbilitatoFacade extends BaseFacade{
	
	@Inject
	private SoggettoAbilitatoDad soggettoAbilitatoDad;
	
	@Inject
	private DecodificaDad decodificaDad;
	
	@Inject
	private  AnagraficaDelegatoDad anagrafaicaDelegatoDad;
	
	/**
	 * get a SoggettoAbilitato by cfSoggetto
	 * 
	 * @param cfSoggetto
	 * @return the SoggettoAbilitato
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetSoggettoAbilitatoBycfSoggettoResponse getSoggettoAbilitatoBycfSoggetto(String cfSoggetto) {
		return executeService(new GetSoggettoAbilitatoBycfSoggettoRequest(cfSoggetto),
				new GetSoggettoAbilitatoBycfSoggettoService(configurationHelper, soggettoAbilitatoDad));
	}
	
	/**
	 * get a SoggettoAbilitato by its id
	 * 
	 * @param cfSoggetto
	 * @return the SoggettoAbilitato
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetSoggettoAbilitatoByIdResponse getSoggettoAbilitatoById(Long idSeoggettoAbilitato) {
		return executeService(new GetSoggettoAbilitatoByIdRequest(idSeoggettoAbilitato),
				new GetSoggettoAbilitatoByIdService(configurationHelper, soggettoAbilitatoDad));
	}
	
	/**
	 * Posts a SoggettoAbilitato
	 * 
	 * @param anagraficaDelegato
	 * @return the anagraficaDelegato
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostSoggettoAbilitatoResponse postSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		return executeService(new PostSoggettoAbilitatoRequest(soggettoAbilitato),
				new PostSoggettoAbilitatoService(configurationHelper, soggettoAbilitatoDad, decodificaDad ));
	}
	
	/**
	 * Posts a SoggettoAbilitato
	 * 
	 * @param anagraficaDelegato
	 * @return the anagraficaDelegato
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PutSoggettoAbilitatoResponse putSoggettoAbilitato(SoggettoAbilitato soggettoAbilitato) {
		return executeService(new PutSoggettoAbilitatoRequest(soggettoAbilitato),
				new PutSoggettoAbilitatoService(configurationHelper, soggettoAbilitatoDad, decodificaDad ));
	}
	

	/**
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public SetDataAnnullamentoSoggettoAbilitatoResponse setDataAnnullamentoSoggettoAbilitato(Long idSoggettoAbilitato,Boolean flgAutorizzazione) {
		return executeService(new SetDataAnnullamentoSoggettoAbilitatoRequest(idSoggettoAbilitato,flgAutorizzazione),
				new SetDataAnnullamentoSoggettoAbilitatoService(configurationHelper, soggettoAbilitatoDad, anagrafaicaDelegatoDad ));
	}

}
