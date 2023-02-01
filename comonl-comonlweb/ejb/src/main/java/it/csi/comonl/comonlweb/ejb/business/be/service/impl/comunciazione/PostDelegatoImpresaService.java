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

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegatoImpresaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostDelegatoImpresaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegatoImpresaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaPK;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;


/**
 * Saves an DelegatoImpresa
 */
public class PostDelegatoImpresaService
		extends BaseDelegatoImpresaService<PostDelegatoImpresaRequest, PostDelegatoImpresaResponse> {
	
	private DelegatoImpresa delegatoImpresa;
	private AnagraficaDelegatoDad anagraficaDelegatoDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the configuration helper
	 * @param anagraficaDelegatoDad    the DelegatoImpresaDad DAD
	 */
	public PostDelegatoImpresaService(ConfigurationHelper configurationHelper, DelegatoImpresaDad delegatoImpresaDad,AnagraficaDelegatoDad anagraficaDelegatoDad) {
		super(configurationHelper, delegatoImpresaDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad; 
	}

	@Override
	protected void checkServiceParams() {
		delegatoImpresa = request.getDelegatoImpresa();
		checkModel(delegatoImpresa, "delegatoImpresa");
		checkNotNull(delegatoImpresa.getId().getCfDelegato(), "cfDelegato", true);
		checkNotNull(delegatoImpresa.getId().getTipoAnagrafica(), "tipoAnagrafica", true);
		checkNotNull(delegatoImpresa.getId().getCfImpresa(), "cfImpresa", true);
	}

	@Override
	protected void execute() {
		
		DelegatoImpresaPK id = delegatoImpresa.getId();

		AnagraficaDelegato anagraficaDelegato = anagraficaDelegatoDad.getAnagraficaDelegatoById(id.getCfDelegato(), id.getTipoAnagrafica());
		checkBusinessCondition(anagraficaDelegato!=null, MsgComonl.COMCOME1823.getError());
		
		DelegatoImpresa relPresente = delegatoImpresaDad.getDelegatoImpresaById(id.getCfDelegato(), id.getTipoAnagrafica(), id.getCfImpresa());
		checkBusinessCondition(relPresente==null, MsgComonl.COMCOME1026.getError());
		
		delegatoImpresa = delegatoImpresaDad.insertDelegatoImpresa(delegatoImpresa);
		response.setDelegatoImpresa(delegatoImpresa);
		
	}
}
