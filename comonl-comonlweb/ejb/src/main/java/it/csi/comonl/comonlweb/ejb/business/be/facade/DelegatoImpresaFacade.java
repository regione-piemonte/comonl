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
package it.csi.comonl.comonlweb.ejb.business.be.facade;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.GetDelegatoImpresaSpecService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostDelegatoImpresaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.SetDataAnnullamentoAllCfImpresaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.SetDataAnnullamentoDelegatoImpresaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.GetDelegatoImpresaSpecRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoAllCfImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.SetDataAnnullamentoDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.GetDelegatoImpresaSpecResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoAllCfImpresaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.SetDataAnnullamentoDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;

/**
 * Facade for the /comunicazione path
 */
@Stateless
public class DelegatoImpresaFacade extends BaseFacade {
	// Injection point
	@Inject
	private DelegatoImpresaDad delegatoImpresaDad;
	
	@Inject
	private DelegaDad delegaDad;
	
	@Inject
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	
	/**
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public SetDataAnnullamentoDelegatoImpresaResponse setDataAnnullamentoDelegatoImpresa(String cfDelegato, String tipoAnagrafica,String cfImpresa) {
		return executeService(new SetDataAnnullamentoDelegatoImpresaRequest(cfDelegato,tipoAnagrafica,cfImpresa),
				new SetDataAnnullamentoDelegatoImpresaService(configurationHelper, delegatoImpresaDad,delegaDad ));
	}
	
	
	/**
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public SetDataAnnullamentoAllCfImpresaResponse setDataAnnullamentoAllCfImpresa(String cfImpresa,Boolean flgAutorizzazione,Boolean flgAnnullamento) {
		return executeService(new SetDataAnnullamentoAllCfImpresaRequest(cfImpresa,flgAutorizzazione,flgAnnullamento),
				new SetDataAnnullamentoAllCfImpresaService(configurationHelper, delegatoImpresaDad,delegaDad ));
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.READ)
	public GetDelegatoImpresaSpecResponse getDelegatoImpresaSpec(String cfImpresa) {
		return executeService(
					new GetDelegatoImpresaSpecRequest(cfImpresa), new GetDelegatoImpresaSpecService(configurationHelper, delegatoImpresaDad));
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDelegatoImpresaResponse postDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		return executeService(
					new PostDelegatoImpresaRequest(delegatoImpresa), new PostDelegatoImpresaService(configurationHelper, delegatoImpresaDad, anagraficaDelegatoDad));
	}
	
}
