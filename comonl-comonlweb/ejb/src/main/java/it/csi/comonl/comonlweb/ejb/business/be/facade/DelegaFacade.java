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

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAggiornaDelegaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostAggiornaStatoDelegaService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.PostRicercaDelegheService;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione.StampaAccreditamentoDelegheService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAggiornaDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAggiornaStatoDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostRicercaDelegheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostStampaRicercaDelegheRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PdfResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegaResponse;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostRicercaDelegheResponse;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaDelega;

/**
 * Facade for the /comunicazione path
 */
@Stateless
public class DelegaFacade extends BaseFacade {
	// Injection point
	@Inject
	private DelegaDad delegaDad;
	
	@Inject
	private DecodificaDad decodificaDad;
	
	@Inject
	private ControlliDad controlliDad;
	
	@Inject
	private CommonDad commonDad;
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public PostRicercaDelegheResponse postRicercaDeleghe(Integer page, Integer limit, String sort, String direction,
			FormRicercaDelega ricercaDelega) {
		return executeService(new PostRicercaDelegheRequest(page, limit, sort, direction, ricercaDelega),
				new PostRicercaDelegheService(configurationHelper, delegaDad, decodificaDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Lock(LockType.WRITE)
	public PdfResponse stampaRicercaDeleghe(FormRicercaDelega ricercaDelega) {
		return executeService(new PostStampaRicercaDelegheRequest(ricercaDelega),
				new StampaAccreditamentoDelegheService(configurationHelper, delegaDad, decodificaDad));
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDelegaResponse aggiornaStatoDelega(Delega delega) {
		return executeService(new PostAggiornaStatoDelegaRequest(delega),
				new PostAggiornaStatoDelegaService(configurationHelper, delegaDad, decodificaDad, commonDad));
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Lock(LockType.WRITE)
	public PostDelegaResponse aggiornaDelega(Delega delega) {
		return executeService(new PostAggiornaDelegaRequest(delega),
				new PostAggiornaDelegaService(configurationHelper, delegaDad, decodificaDad, controlliDad));
	}
}
